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

package org.ofbiz.order.shoppingcart.shipping;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.order.order.OrderReadHelper;
import org.ofbiz.order.shoppingcart.ShoppingCart;
import org.ofbiz.product.store.ProductStoreWorker;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ModelService;
import org.ofbiz.service.ServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ShippingEvents - Events used for processing shipping fees
 */
public class ShippingEvents {

    public static final String module = ShippingEvents.class.getName();

    public static String getShipEstimate(HttpServletRequest request, HttpServletResponse response) {
        ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("shoppingCart");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        GenericDelegator delegator = (GenericDelegator) request.getAttribute("delegator");

        int shipGroups = cart.getShipGroupSize();
        for (int i = 0; i < shipGroups; i++) {
            Map result = getShipGroupEstimate(dispatcher, delegator, cart, i);
            ServiceUtil.getMessages(request, result, null, "", "", "", "", null, null);
            if (result.get(ModelService.RESPONSE_MESSAGE).equals(ModelService.RESPOND_ERROR)) {
                return "error";
            }

            Double shippingTotal = (Double) result.get("shippingTotal");
            if (shippingTotal == null) {
                shippingTotal = new Double(0.00);
            }
            cart.setItemShipGroupEstimate(shippingTotal.doubleValue(), i);
        }

        // all done
        return "success";
    }

    public static Map getShipGroupEstimate(LocalDispatcher dispatcher, GenericDelegator delegator, ShoppingCart cart, int groupNo) {
        // check for shippable items
        if (!cart.shippingApplies()) {
            Map responseResult = ServiceUtil.returnSuccess();
            responseResult.put("shippingTotal", new Double(0.00));
            return responseResult;
        }

        String shipmentMethodTypeId = cart.getShipmentMethodTypeId(groupNo);
        String carrierPartyId = cart.getCarrierPartyId(groupNo);

        return getShipGroupEstimate(dispatcher, delegator, cart.getOrderType(), shipmentMethodTypeId, carrierPartyId, null,
                cart.getShippingContactMechId(groupNo), cart.getProductStoreId(), cart.getSupplierPartyId(groupNo), cart.getShippableItemInfo(groupNo),
                cart.getShippableWeight(groupNo), cart.getShippableQuantity(groupNo), cart.getShippableTotal(groupNo));
    }

    public static Map getShipEstimate(LocalDispatcher dispatcher, GenericDelegator delegator, OrderReadHelper orh, String shipGroupSeqId) {
        // check for shippable items
        if (!orh.shippingApplies()) {
            Map responseResult = ServiceUtil.returnSuccess();
            responseResult.put("shippingTotal", new Double(0.00));
            return responseResult;
        }

        GenericValue shipGroup = orh.getOrderItemShipGroup(shipGroupSeqId);
        String shipmentMethodTypeId = shipGroup.getString("shipmentMethodTypeId");
        String carrierRoleTypeId = shipGroup.getString("carrierRoleTypeId");
        String carrierPartyId = shipGroup.getString("carrierPartyId");
        String supplierPartyId = shipGroup.getString("supplierPartyId");

        GenericValue shipAddr = orh.getShippingAddress(shipGroupSeqId);
        if (shipAddr == null) {
            return UtilMisc.toMap("shippingTotal", new Double(0));
        }

        String contactMechId = shipAddr.getString("contactMechId");
        return getShipGroupEstimate(dispatcher, delegator, orh.getOrderTypeId(), shipmentMethodTypeId, carrierPartyId, carrierRoleTypeId,
                contactMechId, orh.getProductStoreId(), supplierPartyId, orh.getShippableItemInfo(shipGroupSeqId), orh.getShippableWeightBd(shipGroupSeqId).doubleValue(),
                orh.getShippableQuantityBd(shipGroupSeqId).doubleValue(), orh.getShippableTotalBd(shipGroupSeqId).doubleValue());
    }

    // Legacy version does not have support for drop ship estimates using supplierPartyId
    public static Map getShipGroupEstimate(LocalDispatcher dispatcher, GenericDelegator delegator, String orderTypeId,
            String shipmentMethodTypeId, String carrierPartyId, String carrierRoleTypeId, String shippingContactMechId,
            String productStoreId, List itemInfo, double shippableWeight, double shippableQuantity,
            double shippableTotal) {
        return getShipGroupEstimate(dispatcher, delegator, orderTypeId, shipmentMethodTypeId, carrierPartyId,
                carrierRoleTypeId, shippingContactMechId, productStoreId, null, itemInfo,
                shippableWeight, shippableQuantity, shippableTotal);
    }

    public static Map getShipGroupEstimate(LocalDispatcher dispatcher, GenericDelegator delegator, String orderTypeId,
            String shipmentMethodTypeId, String carrierPartyId, String carrierRoleTypeId, String shippingContactMechId,
            String productStoreId, String supplierPartyId, List itemInfo, double shippableWeight, double shippableQuantity,
            double shippableTotal) {
        String standardMessage = "A problem occurred calculating shipping. Fees will be calculated offline.";
        List errorMessageList = new ArrayList();

        if (shipmentMethodTypeId == null || carrierPartyId == null) {
            if ("SALES_ORDER".equals(orderTypeId)) {
                errorMessageList.add("Please Select Your Shipping Method.");
                return ServiceUtil.returnError(errorMessageList);
            } else {
                return ServiceUtil.returnSuccess();
            }
        }

        if (carrierRoleTypeId == null) {
            carrierRoleTypeId = "CARRIER";
        }

        if (shippingContactMechId == null) {
            errorMessageList.add("Please Select Your Shipping Address.");
            return ServiceUtil.returnError(errorMessageList);
        }

        // if as supplier is associated, then we have a drop shipment and should use the origin shipment address of it
        String shippingOriginContactMechId = null;
        if (supplierPartyId != null) {
            try {
                GenericValue originAddress = getShippingOriginContactMech(delegator, supplierPartyId);
                if (originAddress == null) {
                    return ServiceUtil.returnError("Cannot find the origin shipping address (SHIP_ORIG_LOCATION) for the supplier with ID ["+supplierPartyId+"].  Will not be able to calculate drop shipment estimate.");
                }
                shippingOriginContactMechId = originAddress.getString("contactMechId");
            } catch (GeneralException e) {
                return ServiceUtil.returnError(standardMessage);
            }
        }

        // no shippable items; we won't change any shipping at all
        if (shippableQuantity == 0) {
            Map result = ServiceUtil.returnSuccess();
            result.put("shippingTotal", new Double(0));
            return result;
        }

        // check for an external service call
        GenericValue storeShipMethod = ProductStoreWorker.getProductStoreShipmentMethod(delegator, productStoreId,
                shipmentMethodTypeId, carrierPartyId, carrierRoleTypeId);

        if (storeShipMethod == null) {
            errorMessageList.add("No applicable shipment method found.");
            return ServiceUtil.returnError(errorMessageList);
        }

        // the initial amount before manual estimates
        double shippingTotal = 0.00;

        // prepare the service invocation fields
        Map serviceFields = new HashMap();
        serviceFields.put("initialEstimateAmt", new Double(shippingTotal));
        serviceFields.put("shippableTotal", new Double(shippableTotal));
        serviceFields.put("shippableQuantity", new Double(shippableQuantity));
        serviceFields.put("shippableWeight", new Double(shippableWeight));        
        serviceFields.put("shippableItemInfo", itemInfo);
        serviceFields.put("productStoreId", productStoreId);
        serviceFields.put("carrierRoleTypeId", "CARRIER");
        serviceFields.put("carrierPartyId", carrierPartyId);
        serviceFields.put("shipmentMethodTypeId", shipmentMethodTypeId);
        serviceFields.put("shippingContactMechId", shippingContactMechId);
        serviceFields.put("shippingOriginContactMechId", shippingOriginContactMechId);

        // call the external shipping service
        try {
            Double externalAmt = getExternalShipEstimate(dispatcher, storeShipMethod, serviceFields);
            if (externalAmt != null) {
                shippingTotal += externalAmt.doubleValue();
            }
        } catch (GeneralException e) {
            return ServiceUtil.returnError(standardMessage);
        }

        // update the initial amount
        serviceFields.put("initialEstimateAmt", new Double(shippingTotal));

        // call the generic estimate service
        try {
            Double genericAmt = getGenericShipEstimate(dispatcher, storeShipMethod, serviceFields);
            if (genericAmt != null) {
                shippingTotal += genericAmt.doubleValue();
            }
        } catch (GeneralException e) {
            return ServiceUtil.returnError(standardMessage);
        }

        // return the totals
        Map responseResult = ServiceUtil.returnSuccess();
        responseResult.put("shippingTotal", new Double(shippingTotal));
        return responseResult;
    }

    public static Double getGenericShipEstimate(LocalDispatcher dispatcher, GenericValue storeShipMeth, Map context) throws GeneralException {
        // invoke the generic estimate service next -- append to estimate amount
        Map genericEstimate = null;
        Double genericShipAmt = null;
        try {
            genericEstimate = dispatcher.runSync("calcShipmentCostEstimate", context);
        } catch (GenericServiceException e) {
            Debug.logError(e, "Shipment Service Error", module);
            throw new GeneralException();
        }
        if (ServiceUtil.isError(genericEstimate) || ServiceUtil.isFailure(genericEstimate)) {
            Debug.logError(ServiceUtil.getErrorMessage(genericEstimate), module);
            throw new GeneralException();
        } else {
            genericShipAmt = (Double) genericEstimate.get("shippingEstimateAmount");
        }
        return genericShipAmt;
    }

    public static Double getExternalShipEstimate(LocalDispatcher dispatcher, GenericValue storeShipMeth, Map context) throws GeneralException {
        // invoke the external shipping estimate service
        Double externalShipAmt = null;
        if (storeShipMeth.get("serviceName") != null) {
            String serviceName = storeShipMeth.getString("serviceName");
            String configProps = storeShipMeth.getString("configProps");
            if (UtilValidate.isNotEmpty(serviceName)) {
                // prepare the external service context
                context.put("serviceConfigProps", configProps);

                // invoke the service
                Map serviceResp = null;
                try {
                    Debug.log("Service : " + serviceName + " / " + configProps + " -- " + context, module);
                    // because we don't want to blow up too big or rollback the transaction when this happens, always have it run in its own transaction...
                    serviceResp = dispatcher.runSync(serviceName, context, 0, true);
                } catch (GenericServiceException e) {
                    Debug.logError(e, "Shipment Service Error", module);
                    throw new GeneralException();
                }
                if (ServiceUtil.isError(serviceResp)) {
                    String errMsg = "Error getting external shipment cost estimate: " + ServiceUtil.getErrorMessage(serviceResp); 
                    Debug.logError(errMsg, module);
                    throw new GeneralException(errMsg);
                } else if (ServiceUtil.isFailure(serviceResp)) {
                    String errMsg = "Failure getting external shipment cost estimate: " + ServiceUtil.getErrorMessage(serviceResp); 
                    Debug.logError(errMsg, module);
                    // should not throw an Exception here, otherwise getShipGroupEstimate would return an error, causing all sorts of services like add or update order item to abort
                } else {
                    externalShipAmt = (Double) serviceResp.get("shippingEstimateAmount");
                }
            }
        }
        return externalShipAmt;
    }

    /**
     * Attempts to get the supplier's shipping origin address and failing that, the general location.
     */
    public static GenericValue getShippingOriginContactMech(GenericDelegator delegator, String supplierPartyId) throws GeneralException {
        List conditions = UtilMisc.toList(
                new EntityExpr("partyId", EntityOperator.EQUALS, supplierPartyId),
                new EntityExpr("contactMechTypeId", EntityOperator.EQUALS, "POSTAL_ADDRESS"),
                new EntityExpr("contactMechPurposeTypeId", EntityOperator.IN, UtilMisc.toList("SHIP_ORIG_LOCATION", "GENERAL_LOCATION")),
                EntityUtil.getFilterByDateExpr("contactFromDate", "contactThruDate"),
                EntityUtil.getFilterByDateExpr("purposeFromDate", "purposeThruDate")
        );
        List<GenericValue> addresses = delegator.findByAnd("PartyContactWithPurpose", conditions, UtilMisc.toList("contactMechPurposeTypeId DESC"));

        GenericValue generalAddress = null;
        GenericValue originAddress = null;
        for (GenericValue address : addresses) {
            if ("GENERAL_LOCATION".equals(address.get("contactMechPurposeTypeId")))
                generalAddress = address;
            else if ("SHIP_ORIG_LOCATION".equals(address.get("contactMechPurposeTypeId")))
                originAddress = address;
        }
        return originAddress != null ? originAddress : generalAddress;
    }
}

