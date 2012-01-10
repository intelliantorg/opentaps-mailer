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

package org.ofbiz.product.catalog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.StringUtil;
import org.ofbiz.base.util.UtilHttp;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.webapp.website.WebSiteWorker;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.product.category.CategoryWorker;
import org.ofbiz.product.store.ProductStoreWorker;

/**
 * CatalogWorker - Worker class for catalog related functionality
 */
public class CatalogWorker {
    
    public static final String module = CatalogWorker.class.getName();

    public static String getWebSiteId(ServletRequest request) {
        return WebSiteWorker.getWebSiteId(request);
    }
    
    public static GenericValue getWebSite(ServletRequest request) {
        return WebSiteWorker.getWebSite(request);
    }

    public static List getAllCatalogIds(ServletRequest request) {        
        List catalogIds = new ArrayList();
        List catalogs = null;
        GenericDelegator delegator = (GenericDelegator) request.getAttribute("delegator");
        try {
            catalogs = delegator.findAll("ProdCatalog", UtilMisc.toList("catalogName"));
        } catch (GenericEntityException e) {
            Debug.logError(e, "Error looking up all catalogs", module);
        }
        if (catalogs != null) {
            Iterator i = catalogs.iterator();
            while (i.hasNext()) {
                GenericValue c = (GenericValue) i.next();
                catalogIds.add(c.getString("prodCatalogId"));
            }
        }
        return catalogIds;
    }
    
    public static List getStoreCatalogs(ServletRequest request) {
        String productStoreId = ProductStoreWorker.getProductStoreId(request);
        GenericDelegator delegator = (GenericDelegator) request.getAttribute("delegator");
        return getStoreCatalogs(delegator, productStoreId);
    }

    public static List getStoreCatalogs(GenericDelegator delegator, String productStoreId) {
        try {
            return EntityUtil.filterByDate(delegator.findByAndCache("ProductStoreCatalog", UtilMisc.toMap("productStoreId", productStoreId), UtilMisc.toList("sequenceNum", "prodCatalogId")), true);
        } catch (GenericEntityException e) {
            Debug.logError(e, "Error looking up store catalogs for store with id " + productStoreId, module);
        }
        return null;
    }

    public static List getPartyCatalogs(ServletRequest request) {
        HttpSession session = ((HttpServletRequest) request).getSession();
        GenericValue userLogin = (GenericValue) session.getAttribute("userLogin");
        if (userLogin == null) userLogin = (GenericValue) session.getAttribute("autoUserLogin");
        if (userLogin == null) return null;
        String partyId = userLogin.getString("partyId");
        if (partyId == null) return null;
        GenericDelegator delegator = (GenericDelegator) request.getAttribute("delegator");
        return getPartyCatalogs(delegator, partyId);
    }

    public static List getPartyCatalogs(GenericDelegator delegator, String partyId) {
        if (delegator == null || partyId == null) {
            return null;
        }

        try {
            return EntityUtil.filterByDate(delegator.findByAndCache("ProdCatalogRole", UtilMisc.toMap("partyId", partyId, "roleTypeId", "CUSTOMER"), UtilMisc.toList("sequenceNum", "prodCatalogId")), true);
        } catch (GenericEntityException e) {
            Debug.logError(e, "Error looking up ProdCatalog Roles for party with id " + partyId, module);
        }
        return null;
    }
    
    public static List getProdCatalogCategories(ServletRequest request, String prodCatalogId, String prodCatalogCategoryTypeId) {
        GenericDelegator delegator = (GenericDelegator) request.getAttribute("delegator");
        return getProdCatalogCategories(delegator, prodCatalogId, prodCatalogCategoryTypeId);
    }

    public static List getProdCatalogCategories(GenericDelegator delegator, String prodCatalogId, String prodCatalogCategoryTypeId) {
        try {
            List prodCatalogCategories = EntityUtil.filterByDate(delegator.findByAndCache("ProdCatalogCategory",
                        UtilMisc.toMap("prodCatalogId", prodCatalogId),
                        UtilMisc.toList("sequenceNum", "productCategoryId")), true);

            if (UtilValidate.isNotEmpty(prodCatalogCategoryTypeId) && prodCatalogCategories != null) {
                prodCatalogCategories = EntityUtil.filterByAnd(prodCatalogCategories,
                            UtilMisc.toMap("prodCatalogCategoryTypeId", prodCatalogCategoryTypeId));
            }
            return prodCatalogCategories;
        } catch (GenericEntityException e) {
            Debug.logError(e, "Error looking up ProdCatalogCategories for prodCatalog with id " + prodCatalogId, module);
        }
        return null;
    }

    /**
     * Retrieves the current prodCatalogId.  First it will attempt to find it from a special
     * request parameter or session attribute named CURRENT_CATALOG_ID.  Failing that, it will
     * get the first catalog from the database as specified in getCatalogIdsAvailable().  
     * If this behavior is undesired, give the user a selectable list of catalogs.
     */
    public static String getCurrentCatalogId(ServletRequest request) {
        HttpSession session = ((HttpServletRequest) request).getSession();
        Map requestParameters = UtilHttp.getParameterMap((HttpServletRequest) request);
        String prodCatalogId = null;
        boolean fromSession = false;

        // first see if a new catalog was specified as a parameter
        prodCatalogId = (String) requestParameters.get("CURRENT_CATALOG_ID");
        // if no parameter, try from session
        if (prodCatalogId == null) {
            prodCatalogId = (String) session.getAttribute("CURRENT_CATALOG_ID");
            if (prodCatalogId != null) fromSession = true;
        }
        // get it from the database
        if (prodCatalogId == null) {
            List catalogIds = getCatalogIdsAvailable(request);
            if (catalogIds != null && catalogIds.size() > 0) prodCatalogId = (String) catalogIds.get(0);
        }

        if (!fromSession) {
            if (Debug.verboseOn()) Debug.logVerbose("[CatalogWorker.getCurrentCatalogId] Setting new catalog name: " + prodCatalogId, module);
            session.setAttribute("CURRENT_CATALOG_ID", prodCatalogId);
            CategoryWorker.setTrail(request, new ArrayList());
        }
        return prodCatalogId;
    }
    
    public static List getCatalogIdsAvailable(ServletRequest request) {
        List partyCatalogs = getPartyCatalogs(request);
        List storeCatalogs = getStoreCatalogs(request);
        return getCatalogIdsAvailable(partyCatalogs, storeCatalogs);
    }

    public static List getCatalogIdsAvailable(GenericDelegator delegator, String productStoreId, String partyId) {
        List storeCatalogs = getStoreCatalogs(delegator, productStoreId);
        List partyCatalogs = getPartyCatalogs(delegator, partyId);
        return getCatalogIdsAvailable(partyCatalogs, storeCatalogs);
    }
    
    public static List getCatalogIdsAvailable(List partyCatalogs, List storeCatalogs) {
        List categoryIds = new LinkedList();
        List allCatalogLinks = new ArrayList((storeCatalogs == null ? 0 : storeCatalogs.size()) + (partyCatalogs == null ? 0 : partyCatalogs.size()));
        if (partyCatalogs != null) allCatalogLinks.addAll(partyCatalogs);
        if (storeCatalogs != null) allCatalogLinks.addAll(storeCatalogs);
        
        if (allCatalogLinks.size() > 0) {
            Iterator aclIter = allCatalogLinks.iterator();
            while (aclIter.hasNext()) {
                GenericValue catalogLink = (GenericValue) aclIter.next();
                categoryIds.add(catalogLink.getString("prodCatalogId"));
            }
        }
        return categoryIds;
    }
    
    public static String getCatalogName(ServletRequest request) {
        return getCatalogName(request, getCurrentCatalogId(request));
    }

    public static String getCatalogName(ServletRequest request, String prodCatalogId) {
        if (prodCatalogId == null || prodCatalogId.length() <= 0) return null;
        GenericDelegator delegator = (GenericDelegator) request.getAttribute("delegator");

        try {
            GenericValue prodCatalog = delegator.findByPrimaryKeyCache("ProdCatalog", UtilMisc.toMap("prodCatalogId", prodCatalogId));

            if (prodCatalog != null) {
                return prodCatalog.getString("catalogName");
            }
        } catch (GenericEntityException e) {
            Debug.logError(e, "Error looking up name for prodCatalog with id " + prodCatalogId, module);
        }

        return null;
    }

    public static String getContentPathPrefix(ServletRequest request) {
        GenericValue prodCatalog = getProdCatalog(request, getCurrentCatalogId(request));

        if (prodCatalog == null) return "";
        String contentPathPrefix = prodCatalog.getString("contentPathPrefix");

        return StringUtil.cleanUpPathPrefix(contentPathPrefix);
    }
        
    public static String getTemplatePathPrefix(ServletRequest request) {
        GenericValue prodCatalog = getProdCatalog(request, getCurrentCatalogId(request));

        if (prodCatalog == null) return "";
        String templatePathPrefix = prodCatalog.getString("templatePathPrefix");

        return StringUtil.cleanUpPathPrefix(templatePathPrefix);
    }

    public static GenericValue getProdCatalog(ServletRequest request) {
        return getProdCatalog(request, getCurrentCatalogId(request));
    }

    public static GenericValue getProdCatalog(ServletRequest request, String prodCatalogId) {
        if (prodCatalogId == null || prodCatalogId.length() <= 0) return null;
        GenericDelegator delegator = (GenericDelegator) request.getAttribute("delegator");

        try {
            return delegator.findByPrimaryKeyCache("ProdCatalog", UtilMisc.toMap("prodCatalogId", prodCatalogId));
        } catch (GenericEntityException e) {
            Debug.logError(e, "Error looking up name for prodCatalog with id " + prodCatalogId, module);
            return null;
        }
    }
    
    public static String getCatalogTopCategoryId(ServletRequest request) {
        return getCatalogTopCategoryId(request, getCurrentCatalogId(request));
    }
        
    public static String getCatalogTopCategoryId(ServletRequest request, String prodCatalogId) {
        if (prodCatalogId == null || prodCatalogId.length() <= 0) return null;

        List prodCatalogCategories = getProdCatalogCategories(request, prodCatalogId, "PCCT_BROWSE_ROOT");

        if (prodCatalogCategories != null && prodCatalogCategories.size() > 0) {
            GenericValue prodCatalogCategory = EntityUtil.getFirst(prodCatalogCategories);

            return prodCatalogCategory.getString("productCategoryId");
        } else {
            return null;
        }
    }
    
    public static String getCatalogSearchCategoryId(ServletRequest request) {
        return getCatalogSearchCategoryId(request, getCurrentCatalogId(request));
    }
        
    public static String getCatalogSearchCategoryId(ServletRequest request, String prodCatalogId) {
        return getCatalogSearchCategoryId((GenericDelegator) request.getAttribute("delegator"), prodCatalogId);
    }
    public static String getCatalogSearchCategoryId(GenericDelegator delegator, String prodCatalogId) {
        if (prodCatalogId == null || prodCatalogId.length() <= 0) return null;

        List prodCatalogCategories = getProdCatalogCategories(delegator, prodCatalogId, "PCCT_SEARCH");
        if (prodCatalogCategories != null && prodCatalogCategories.size() > 0) {
            GenericValue prodCatalogCategory = EntityUtil.getFirst(prodCatalogCategories);
            return prodCatalogCategory.getString("productCategoryId");
        } else {
            return null;
        }
    }

    public static String getCatalogViewAllowCategoryId(GenericDelegator delegator, String prodCatalogId) {
        if (prodCatalogId == null || prodCatalogId.length() <= 0) return null;

        List prodCatalogCategories = getProdCatalogCategories(delegator, prodCatalogId, "PCCT_VIEW_ALLW");
        if (prodCatalogCategories != null && prodCatalogCategories.size() > 0) {
            GenericValue prodCatalogCategory = EntityUtil.getFirst(prodCatalogCategories);
            return prodCatalogCategory.getString("productCategoryId");
        } else {
            return null;
        }
    }

    public static String getCatalogPurchaseAllowCategoryId(GenericDelegator delegator, String prodCatalogId) {
        if (prodCatalogId == null || prodCatalogId.length() <= 0) return null;

        List prodCatalogCategories = getProdCatalogCategories(delegator, prodCatalogId, "PCCT_PURCH_ALLW");
        if (prodCatalogCategories != null && prodCatalogCategories.size() > 0) {
            GenericValue prodCatalogCategory = EntityUtil.getFirst(prodCatalogCategories);
            return prodCatalogCategory.getString("productCategoryId");
        } else {
            return null;
        }
    }

    public static String getCatalogPromotionsCategoryId(ServletRequest request) {
        return getCatalogPromotionsCategoryId(request, getCurrentCatalogId(request));
    }
         
    public static String getCatalogPromotionsCategoryId(ServletRequest request, String prodCatalogId) {
        if (prodCatalogId == null || prodCatalogId.length() <= 0) return null;

        List prodCatalogCategories = getProdCatalogCategories(request, prodCatalogId, "PCCT_PROMOTIONS");

        if (prodCatalogCategories != null && prodCatalogCategories.size() > 0) {
            GenericValue prodCatalogCategory = EntityUtil.getFirst(prodCatalogCategories);

            return prodCatalogCategory.getString("productCategoryId");
        } else {
            return null;
        }
    }

    public static boolean getCatalogQuickaddUse(ServletRequest request) {
        return getCatalogQuickaddUse(request, getCurrentCatalogId(request));
    }

    public static boolean getCatalogQuickaddUse(ServletRequest request, String prodCatalogId) {
        if (prodCatalogId == null || prodCatalogId.length() <= 0) return false;
        GenericDelegator delegator = (GenericDelegator) request.getAttribute("delegator");

        try {
            GenericValue prodCatalog = delegator.findByPrimaryKeyCache("ProdCatalog", UtilMisc.toMap("prodCatalogId", prodCatalogId));

            if (prodCatalog != null) {
                return "Y".equals(prodCatalog.getString("useQuickAdd"));
            }
        } catch (GenericEntityException e) {
            Debug.logError(e, "Error looking up name for prodCatalog with id " + prodCatalogId, module);
        }
        return false;
    }
               
    public static String getCatalogQuickaddCategoryPrimary(ServletRequest request) {
        return getCatalogQuickaddCategoryPrimary(request, getCurrentCatalogId(request));
    }
                   
    public static String getCatalogQuickaddCategoryPrimary(ServletRequest request, String prodCatalogId) {
        if (prodCatalogId == null || prodCatalogId.length() <= 0) return null;

        List prodCatalogCategories = getProdCatalogCategories(request, prodCatalogId, "PCCT_QUICK_ADD");

        if (prodCatalogCategories != null && prodCatalogCategories.size() > 0) {
            GenericValue prodCatalogCategory = EntityUtil.getFirst(prodCatalogCategories);

            return prodCatalogCategory.getString("productCategoryId");
        } else {
            return null;
        }
    }
          
    public static Collection getCatalogQuickaddCategories(ServletRequest request) {
        return getCatalogQuickaddCategories(request, getCurrentCatalogId(request));
    }
                
    public static Collection getCatalogQuickaddCategories(ServletRequest request, String prodCatalogId) {
        if (prodCatalogId == null || prodCatalogId.length() <= 0) return null;

        Collection categoryIds = new LinkedList();

        Collection prodCatalogCategories = getProdCatalogCategories(request, prodCatalogId, "PCCT_QUICK_ADD");

        if (prodCatalogCategories != null && prodCatalogCategories.size() > 0) {
            Iterator pccIter = prodCatalogCategories.iterator();

            while (pccIter.hasNext()) {
                GenericValue prodCatalogCategory = (GenericValue) pccIter.next();

                categoryIds.add(prodCatalogCategory.getString("productCategoryId"));
            }
        }

        return categoryIds;
    }                        
}
