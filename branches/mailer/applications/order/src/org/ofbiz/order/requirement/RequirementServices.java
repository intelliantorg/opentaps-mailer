/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/

/* This file has been modified by Open Source Strategies, Inc. */

package org.ofbiz.order.requirement;

import java.util.*;
import java.sql.Timestamp;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.ofbiz.base.util.*;
import org.ofbiz.entity.condition.*;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 * Requirement Services
 */

public class RequirementServices {

    public static final String module = RequirementServices.class.getName();
    public static final String resource_error = "OrderErrorUiLabels";

    public static Map getRequirementsForSupplier(DispatchContext ctx, Map context) {
        GenericDelegator delegator = ctx.getDelegator();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        Locale locale = (Locale) context.get("locale");

        EntityCondition requirementConditions = (EntityCondition) context.get("requirementConditions");
        String partyId = (String) context.get("partyId");
        String unassignedRequirements = (String) context.get("unassignedRequirements");
        String currencyUomId = (String) context.get("currencyUomId");
        List statusIds = (List) context.get("statusIds");
        try {
            List orderBy = UtilMisc.toList("partyId", "requirementId");
            List conditions = UtilMisc.toList(
                    new EntityExpr("requirementTypeId", EntityOperator.EQUALS, "PRODUCT_REQUIREMENT"),
                    EntityUtil.getFilterByDateExpr()
                    );
            if (statusIds != null && statusIds.size() > 0) {
                conditions.add( new EntityExpr("statusId", EntityOperator.IN, statusIds) );
            } else {
                conditions.add( new EntityExpr("statusId", EntityOperator.EQUALS, "REQ_APPROVED") );
            }
            if (requirementConditions != null) conditions.add(requirementConditions);

            // we're either getting the requirements for a given supplier, unassigned requirements, or requirements for all suppliers
            if (UtilValidate.isNotEmpty(partyId)) {
                conditions.add( new EntityExpr("partyId", EntityOperator.EQUALS, partyId) );
                conditions.add( new EntityExpr("roleTypeId", EntityOperator.EQUALS, "SUPPLIER") );
            } else if (UtilValidate.isNotEmpty(unassignedRequirements)) {
                conditions.add( new EntityExpr("partyId", EntityOperator.EQUALS, null) );
            } else {
                conditions.add( new EntityExpr("roleTypeId", EntityOperator.EQUALS, "SUPPLIER") );
            }
            List requirementAndRoles = delegator.findByAnd("RequirementAndRole", conditions, orderBy);

            // maps to cache the associated suppliers and products data, so we don't do redundant DB and service requests
            Map suppliers = FastMap.newInstance();
            Map gids = FastMap.newInstance();
            Map inventories = FastMap.newInstance();
            Map productsSold = FastMap.newInstance();

            // to count quantity, running total, and distinct products in list
            double quantity = 0.0;
            double amountTotal = 0.0;
            Set products = new HashSet();

            // time period to count products ordered from, six months ago and the 1st of that month
            Timestamp timePeriodStart = UtilDateTime.getMonthStart(UtilDateTime.nowTimestamp(), 0, -6);

            // join in fields with extra data about the suppliers and products
            List requirements = FastList.newInstance();
            for (Iterator iter = requirementAndRoles.iterator(); iter.hasNext(); ) {
                Map union = FastMap.newInstance();
                GenericValue requirement = (GenericValue) iter.next();
                String productId = requirement.getString("productId");
                partyId = requirement.getString("partyId");
                String facilityId = requirement.getString("facilityId");
                double requiredQuantity = requirement.getDouble("quantity").doubleValue();

                // get an available supplier product, preferably the one with the smallest minimum quantity to order, followed by price
                String supplierKey =  partyId + "^" + productId;
                GenericValue supplierProduct = (GenericValue) suppliers.get(supplierKey);
                if (supplierProduct == null) {
                    conditions = UtilMisc.toList(
                            // TODO: it is possible to restrict to quantity > minimumOrderQuantity, but then the entire requirement must be skipped
                            new EntityExpr("partyId", EntityOperator.EQUALS, partyId),
                            new EntityExpr("productId", EntityOperator.EQUALS, productId),
                            EntityUtil.getFilterByDateExpr("availableFromDate", "availableThruDate")
                            );
                    supplierProduct = EntityUtil.getFirst( delegator.findByAnd("SupplierProduct", conditions, UtilMisc.toList("minimumOrderQuantity", "lastPrice")) );
                    suppliers.put(supplierKey, supplierProduct);
                }

                // add our supplier product and cost of this line to the data
                if (supplierProduct != null) {
                    union.putAll(supplierProduct.getAllFields());
                    double lastPrice = supplierProduct.getDouble("lastPrice").doubleValue();
                    amountTotal += lastPrice * requiredQuantity;
                }

                // for good identification, get the UPCA type (UPC code)
                GenericValue gid = (GenericValue) gids.get(productId);
                if (gid == null) {
                    gid = delegator.findByPrimaryKey("GoodIdentification", UtilMisc.toMap("goodIdentificationTypeId", "UPCA", "productId", requirement.get("productId")));
                    gids.put(productId, gid);
                }
                if (gid != null) union.put("idValue", gid.get("idValue"));

                // the ATP and QOH quantities
                if (UtilValidate.isNotEmpty(facilityId)) {
                    String inventoryKey = facilityId + "^" + productId;
                    Map inventory = (Map) inventories.get(inventoryKey);
                    if (inventory == null) {
                        inventory = dispatcher.runSync("getInventoryAvailableByFacility", UtilMisc.toMap("productId", productId, "facilityId", facilityId));
                        if (ServiceUtil.isError(inventory)) {
                            return inventory;
                        }
                        inventories.put(inventoryKey, inventory);
                    }
                    if (inventory != null) {
                        union.put("qoh", inventory.get("quantityOnHandTotal"));
                        union.put("atp", inventory.get("availableToPromiseTotal"));
                    }
                }

                // how many of the products were sold (note this is for a fixed time period across all product stores)
                Double sold = (Double) productsSold.get(productId);
                if (sold == null) {
                    EntityCondition prodConditions = new EntityConditionList( UtilMisc.toList(
                                new EntityExpr("productId", EntityOperator.EQUALS, productId),
                                new EntityExpr("orderTypeId", EntityOperator.EQUALS, "SALES_ORDER"),
                                new EntityExpr("orderStatusId", EntityOperator.NOT_IN, UtilMisc.toList("ORDER_REJECTED", "ORDER_CANCELLED")),
                                new EntityExpr("orderItemStatusId", EntityOperator.NOT_IN, UtilMisc.toList("ITEM_REJECTED", "ITEM_CANCELLED")),
                                new EntityExpr("orderDate", EntityOperator.GREATER_THAN_EQUAL_TO, timePeriodStart)
                                ), EntityOperator.AND);
                    GenericValue count = EntityUtil.getFirst( delegator.findByCondition("OrderItemQuantityReportGroupByProduct", prodConditions, UtilMisc.toList("quantityOrdered"), null) );
                    if (count != null) {
                        sold = count.getDouble("quantityOrdered");
                        if (sold != null) productsSold.put(productId, sold);
                    }
                }
                if (sold != null) {
                    union.put("qtySold", sold);
                }

                // keep a running total of distinct products and quantity to order
                if (requirement.getDouble("quantity") == null) requirement.put("quantity", new Double("1")); // default quantity = 1
                quantity += requiredQuantity;
                products.add(productId);

                // add all the requirement fields last, to overwrite any conflicting fields
                union.putAll(requirement.getAllFields());
                requirements.add(union);
            }

            Map results = ServiceUtil.returnSuccess();
            results.put("requirementsForSupplier", requirements);
            results.put("distinctProductCount", new Integer(products.size()));
            results.put("quantityTotal", new Double(quantity));
            results.put("amountTotal", new Double(amountTotal));
            return results;
        } catch (GenericServiceException e) {
            Debug.logError(e, module);
            return ServiceUtil.returnError(UtilProperties.getMessage(resource_error, "OrderServiceExceptionSeeLogs", locale));
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
            return ServiceUtil.returnError(UtilProperties.getMessage(resource_error, "OrderEntityExceptionSeeLogs", locale));
        }
    }

    // note that this service is designed to work only when a sales order status changes from CREATED -> APPROVED because HOLD -> APPROVED is too complex
    public static Map createAutoRequirementsForOrder(DispatchContext ctx, Map context) {
        GenericDelegator delegator = ctx.getDelegator();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        GenericValue userLogin = (GenericValue) context.get("userLogin");

        String orderId = (String) context.get("orderId");
        try {
            GenericValue order = delegator.findByPrimaryKey("OrderHeader", UtilMisc.toMap("orderId", orderId));
            GenericValue productStore = order.getRelatedOneCache("ProductStore");
            String facilityId = productStore.getString("inventoryFacilityId");
            List orderItems = order.getRelated("OrderItem");
            for (Iterator iter = orderItems.iterator(); iter.hasNext(); ) {
                GenericValue item = (GenericValue) iter.next();
                GenericValue product = item.getRelatedOne("Product");
                if (product == null) continue;
                if (! "PRODRQM_AUTO".equals(product.get("requirementMethodEnumId"))) continue;

                Double quantity = item.getDouble("quantity");
                Double cancelQuantity = item.getDouble("cancelQuantity");
                Double required = new Double( quantity.doubleValue() - (cancelQuantity == null ? 0.0 : cancelQuantity.doubleValue()) );
                if (required.doubleValue() <= 0.0) continue;

                Map input = UtilMisc.toMap("userLogin", userLogin, "facilityId", facilityId, "productId", product.get("productId"), "quantity", required, "requirementTypeId", "PRODUCT_REQUIREMENT");
                Map results = dispatcher.runSync("createRequirement", input);
                if (ServiceUtil.isError(results)) return results;
                String requirementId = (String) results.get("requirementId");

                input = UtilMisc.toMap("userLogin", userLogin, "orderId", order.get("orderId"), "orderItemSeqId", item.get("orderItemSeqId"), "requirementId", requirementId, "quantity", required);
                results = dispatcher.runSync("createOrderRequirementCommitment", input);
                if (ServiceUtil.isError(results)) return results;
            }
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
        } catch (GenericServiceException e) {
            Debug.logError(e, module);
        }
        return ServiceUtil.returnSuccess();
    }

    // note that this service is designed to work only when a sales order status changes from CREATED -> APPROVED because HOLD -> APPROVED is too complex
    public static Map createATPRequirementsForOrder(DispatchContext ctx, Map context) {
        GenericDelegator delegator = ctx.getDelegator();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        GenericValue userLogin = (GenericValue) context.get("userLogin");

        /*
         * The strategy in this service is to begin making requirements when the product falls below the
         * ProductFacility.minimumStock.  Because the minimumStock is an upper bound, the quantity to be required
         * is either that required to bring the ATP back up to the minimumStock level or the amount ordered,
         * whichever is less.
         *
         * If there is a way to support reorderQuantity without losing the order item -> requirement association data,
         * then this service should be updated.
         *
         * The result is that this service generates many small requirements when stock levels are low for a product,
         * which is perfectly fine since the system is capable of creating POs in bulk from aggregate requirements.
         * The only concern would be a UI to manage numerous requirements with ease, preferrably by aggregating
         * on productId.
         */
        String orderId = (String) context.get("orderId");
        try {
            GenericValue order = delegator.findByPrimaryKey("OrderHeader", UtilMisc.toMap("orderId", orderId));
            GenericValue productStore = order.getRelatedOneCache("ProductStore");
            String facilityId = productStore.getString("inventoryFacilityId");
            List orderItems = order.getRelated("OrderItem");
            for (Iterator iter = orderItems.iterator(); iter.hasNext(); ) {
                GenericValue item = (GenericValue) iter.next();
                GenericValue product = item.getRelatedOne("Product");
                if (product == null) continue;
                if (! "PRODRQM_ATP".equals(product.get("requirementMethodEnumId"))) continue;

                Double quantity = item.getDouble("quantity");
                Double cancelQuantity = item.getDouble("cancelQuantity");
                double ordered = quantity.doubleValue() - (cancelQuantity == null ? 0.0 : cancelQuantity.doubleValue());
                if (ordered <= 0.0) continue;

                // get the minimum stock for this facility (don't do anything if not configured)
                GenericValue productFacility = delegator.findByPrimaryKey("ProductFacility", UtilMisc.toMap("facilityId", facilityId, "productId", product.get("productId")));
                if (productFacility == null || productFacility.get("minimumStock") == null) continue;
                double minimumStock = productFacility.getDouble("minimumStock").doubleValue();

                // get the facility ATP for product, which should be updated for this item's reservation
                Map results = dispatcher.runSync("getInventoryAvailableByFacility", UtilMisc.toMap("userLogin", userLogin, "productId", product.get("productId"), "facilityId", facilityId));
                if (ServiceUtil.isError(results)) return results;
                double atp = ((Double) results.get("availableToPromiseTotal")).doubleValue(); // safe since this is a required OUT param

                // count all current requirements for this product
                double pendingRequirements = 0.0;
                List conditions = UtilMisc.toList(
                        new EntityExpr("facilityId", EntityOperator.EQUALS, facilityId),
                        new EntityExpr("productId", EntityOperator.EQUALS, product.get("productId")),
                        new EntityExpr("requirementTypeId", EntityOperator.EQUALS, "PRODUCT_REQUIREMENT"),
                        new EntityExpr("statusId", EntityOperator.NOT_EQUAL, "REQ_ORDERED"),
                        new EntityExpr("statusId", EntityOperator.NOT_EQUAL, "REQ_REJECTED")
                );
                List requirements = delegator.findByAnd("Requirement", conditions);
                for (Iterator riter = requirements.iterator(); riter.hasNext(); ) {
                    GenericValue requirement = (GenericValue) riter.next();
                    pendingRequirements += (requirement.get("quantity") == null ? 0.0 : requirement.getDouble("quantity").doubleValue());
                }

                // the minimum stock is an upper bound, therefore we either require up to the minimum stock or the input required quantity, whichever is less
                double shortfall = minimumStock - atp - pendingRequirements;
                double required = Math.min(ordered, shortfall);
                if (required <= 0.0) continue;

                Map input = UtilMisc.toMap("userLogin", userLogin, "facilityId", facilityId, "productId", product.get("productId"), "quantity", new Double(required), "requirementTypeId", "PRODUCT_REQUIREMENT");
                results = dispatcher.runSync("createRequirement", input);
                if (ServiceUtil.isError(results)) return results;
                String requirementId = (String) results.get("requirementId");

                input = UtilMisc.toMap("userLogin", userLogin, "orderId", order.get("orderId"), "orderItemSeqId", item.get("orderItemSeqId"), "requirementId", requirementId, "quantity", new Double(required));
                results = dispatcher.runSync("createOrderRequirementCommitment", input);
                if (ServiceUtil.isError(results)) return results;
            }
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
        } catch (GenericServiceException e) {
            Debug.logError(e, module);
        }
        return ServiceUtil.returnSuccess();
    }
}

