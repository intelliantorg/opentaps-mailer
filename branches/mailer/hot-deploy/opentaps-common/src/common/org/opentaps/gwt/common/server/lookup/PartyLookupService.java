/*
 * Copyright (c) 2006 - 2009 Open Source Strategies, Inc.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the Honest Public License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Honest Public License for more details.
 *
 * You should have received a copy of the Honest Public License
 * along with this program; if not, write to Funambol,
 * 643 Bair Island Road, Suite 305 - Redwood City, CA 94063, USA
 */

package org.opentaps.gwt.common.server.lookup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javolution.util.FastMap;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.party.party.PartyHelper;
import org.opentaps.common.util.ConvertMapToString;
import org.opentaps.common.util.ICompositeValue;
import org.opentaps.common.util.UtilCommon;
import org.opentaps.domain.base.entities.PartyFromByRelnAndContactInfoAndPartyClassification;
import org.opentaps.domain.base.entities.PartyRoleNameDetailSupplementalData;
import org.opentaps.foundation.entity.EntityInterface;
import org.opentaps.foundation.infrastructure.InfrastructureException;
import org.opentaps.foundation.repository.RepositoryException;
import org.opentaps.gwt.common.client.lookup.UtilLookup;
import org.opentaps.gwt.common.client.lookup.configuration.ActivityLookupConfiguration;
import org.opentaps.gwt.common.client.lookup.configuration.OpportunityLookupConfiguration;
import org.opentaps.gwt.common.client.lookup.configuration.PartyLookupConfiguration;
import org.opentaps.gwt.common.server.HttpInputProvider;
import org.opentaps.gwt.common.server.InputProviderInterface;

/**
 * The RPC service used to populate the PartyListView and Party autocompleters widgets.
 */
public class PartyLookupService extends EntityLookupAndSuggestService {

    private static final String MODULE = PartyLookupService.class.getName();

    private static final EntityCondition CONTACT_CONDITIONS = new EntityExpr("roleTypeIdFrom", EntityOperator.EQUALS, "CONTACT");
    private static final EntityCondition ACCOUNT_CONDITIONS = new EntityExpr("roleTypeIdFrom", EntityOperator.EQUALS, "ACCOUNT");
    private static final EntityCondition LEAD_CONDITIONS = new EntityExpr("roleTypeIdFrom", EntityOperator.EQUALS, "PROSPECT");
    private static final EntityCondition LEAD_CONDITIONS_FOR_OPPORTUNITY = new EntityExpr("statusId", EntityOperator.EQUALS, "PTYLEAD_QUALIFIED");
    private static final EntityCondition PARTNER_CONDITIONS = new EntityExpr("roleTypeIdFrom", EntityOperator.EQUALS, "PARTNER");
    private static final EntityCondition SUPPLIER_CONDITIONS = new EntityExpr("roleTypeId", EntityOperator.EQUALS, "SUPPLIER");

    private static List<String> BY_ID_FILTERS = Arrays.asList(PartyLookupConfiguration.INOUT_PARTY_ID);
    private static List<String> BY_NAME_FILTERS = Arrays.asList(PartyLookupConfiguration.INOUT_GROUP_NAME,
                                                                  PartyLookupConfiguration.INOUT_COMPANY_NAME,
                                                                  PartyLookupConfiguration.INOUT_FIRST_NAME,
                                                                  PartyLookupConfiguration.INOUT_LAST_NAME);
    private static List<String> BY_PHONE_FILTERS = Arrays.asList(PartyLookupConfiguration.INOUT_PHONE_COUNTRY_CODE,
                                                                   PartyLookupConfiguration.INOUT_PHONE_AREA_CODE,
                                                                   PartyLookupConfiguration.INOUT_PHONE_NUMBER);
    
    private static List<String> BY_ADVANCED_FILTERS = Arrays.asList(PartyLookupConfiguration.INOUT_PARTY_ID,
    																  PartyLookupConfiguration.INOUT_FIRST_NAME,
                                                                      PartyLookupConfiguration.INOUT_LAST_NAME,
                                                                      PartyLookupConfiguration.IN_CLASSIFICATION,
                                                                      PartyLookupConfiguration.INOUT_ADDRESS,
                                                                      PartyLookupConfiguration.INOUT_CITY,
                                                                      PartyLookupConfiguration.INOUT_COUNTRY,
                                                                      PartyLookupConfiguration.INOUT_STATE1,
                                                                      PartyLookupConfiguration.INOUT_POSTAL_CODE,
                                                                      PartyLookupConfiguration.INOUT_STATUS,
                                                                      PartyLookupConfiguration.INOUT_DATA_SOURCE,
                                                                      PartyLookupConfiguration.INOUT_PHONE_COUNTRY_CODE,
                                                                      PartyLookupConfiguration.INOUT_PHONE_AREA_CODE,
                                                                      PartyLookupConfiguration.INOUT_PHONE_NUMBER,
                                                                      PartyLookupConfiguration.INOUT_EMAIL,
                                                                      PartyLookupConfiguration.INOUT_ASSIGNED_TO,
                                                                      PartyLookupConfiguration.INOUT_PARENT_ACCOUNT,
                                                                      PartyLookupConfiguration.INOUT_INITIAL_ACCOUNT_FOR_CONTACT,
                                                                      PartyLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN,
                                                                      PartyLookupConfiguration.INOUT_ASSIGNED_TEAM_PARTY_ID,
                                                                      PartyLookupConfiguration.INOUT_COUNTRY_NAME,
                                                                      PartyLookupConfiguration.INOUT_GROUP_NAME,
//                                                                      PartyLookupConfiguration.INOUT_ACCOUNT_WEBSITE,
//                                                                      PartyLookupConfiguration.INOUT_ACCOUNT_ANNUAL_REVENEU,
//                                                                      PartyLookupConfiguration.INOUT_ACCOUNT_SIC_CODE,
                                                                      PartyLookupConfiguration.INOUT_ACCOUNT_RATING,
//                                                                      PartyLookupConfiguration.INOUT_ACCOUNT_TICKER_SYMBOL,
                                                                      PartyLookupConfiguration.INOUT_ACCOUNT_INDUSTRY,
//                                                                      PartyLookupConfiguration.INOUT_ACCOUNT_NUMBER_OF_EMP,
                                                                      PartyLookupConfiguration.INOUT_ACCOUNT_OWNERSHIP,
                                                                      PartyLookupConfiguration.INOUT_ACCOUNT_TYPE
    																	);

    private boolean activeOnly = false;

    /**
     * Creates a new <code>PartyLookupService</code> instance.
     * @param provider an <code>InputProviderInterface</code> value
     */
    public PartyLookupService(InputProviderInterface provider) {
        super(provider, PartyLookupConfiguration.LIST_OUT_FIELDS);
    }

    /**
     * AJAX event to perform lookups on Contacts.
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return the resulting JSON response
     * @throws InfrastructureException if an error occurs
     */
    public static String findContacts(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException {
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        PartyLookupService service = new PartyLookupService(provider);
        Debug.logInfo("################found contacts ......"+service.findContacts().size(), MODULE);
        return json.makeLookupResponse(PartyLookupConfiguration.INOUT_PARTY_ID, service, request.getSession(true).getServletContext());
    }

    /**
     * AJAX event to suggest Contacts.
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return the resulting JSON response
     * @throws InfrastructureException if an error occurs
     */
    public static String suggestContacts(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException {
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        PartyLookupService service = new PartyLookupService(provider);
        service.suggestContacts();
        return json.makeSuggestResponse(PartyLookupConfiguration.INOUT_PARTY_ID, service);
    }

    /**
     * AJAX event to perform lookups on Accounts.
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return the resulting JSON response
     * @throws InfrastructureException if an error occurs
     */
    public static String findAccounts(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException {
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        PartyLookupService service = new PartyLookupService(provider);
        Debug.logInfo("################found accounts ......"+service.findAccounts().size(), MODULE);
        return json.makeLookupResponse(PartyLookupConfiguration.INOUT_PARTY_ID, service, request.getSession(true).getServletContext());
    }

    /**
     * AJAX event to suggest Accounts.
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return the resulting JSON response
     * @throws InfrastructureException if an error occurs
     */
    public static String suggestAccounts(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException {
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        PartyLookupService service = new PartyLookupService(provider);
        service.suggestAccounts();
        return json.makeSuggestResponse(PartyLookupConfiguration.INOUT_PARTY_ID, service);
    }

    /**
     * AJAX event to perform lookups on Accounts.
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return the resulting JSON response
     * @throws InfrastructureException if an error occurs
     */
    public static String findLeads(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException, GenericEntityException {
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        PartyLookupService service = new PartyLookupService(provider);
        Debug.logInfo("################found leads ......"+service.findLeads().size(),MODULE);
        return makeLookupResponse(json, PartyLookupConfiguration.INOUT_PARTY_ID, service, request.getSession(true).getServletContext());
    }
    
    /**
     * AJAX event to suggest Leads.
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return the resulting JSON response
     * @throws InfrastructureException if an error occurs
     */
    public static String suggestLeads(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException {
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        PartyLookupService service = new PartyLookupService(provider);
        service.suggestLeads();
        return json.makeSuggestResponse(PartyLookupConfiguration.INOUT_PARTY_ID, service);
    }
    
    /**
     * AJAX event to suggest Leads.
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return the resulting JSON response
     * @throws InfrastructureException if an error occurs
     */
    public static String suggestLeadsForOpp(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException {
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        PartyLookupService service = new PartyLookupService(provider);
        service.suggestLeadsForOpp();
        return json.makeSuggestResponse(PartyLookupConfiguration.INOUT_PARTY_ID, service);
    }
    
    
    /**
     * AJAX event to perform lookups on Accounts.
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return the resulting JSON response
     * @throws InfrastructureException if an error occurs
     */
    public static String findPartners(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException {
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        PartyLookupService service = new PartyLookupService(provider);
        service.findPartners();
        return json.makeLookupResponse(PartyLookupConfiguration.INOUT_PARTY_ID, service, request.getSession(true).getServletContext());
    }

    /**
     * AJAX event to perform lookups on Suppliers.
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return the resulting JSON response
     * @throws InfrastructureException if an error occurs
     */
    public static String findSuppliers(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException {
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        PartyLookupService service = new PartyLookupService(provider);
        service.findSuppliers();
        return json.makeLookupResponse(PartyLookupConfiguration.INOUT_PARTY_ID, service, request.getSession(true).getServletContext());
    }
    
    private static String makeLookupResponse(JsonResponse json, String entityIdFieldName, PartyLookupService service, ServletContext servletContext) throws GenericEntityException {
        List<? extends EntityInterface> results = service.getResults();
        if (results == null) {
            return json.makeErrorResponse(service.getLastException());
        }
        List<String> columnHeaders = new ArrayList<String>();
        columnHeaders.addAll(PartyLookupConfiguration.LIST_OUT_FIELDS);
        columnHeaders.add(PartyLookupConfiguration.INOUT_DATA_SOURCE);
        columnHeaders.remove(PartyLookupConfiguration.INOUT_GROUP_NAME);
        columnHeaders.remove(PartyLookupConfiguration.OUT_PHONE_ID);
        columnHeaders.remove(PartyLookupConfiguration.OUT_ADDRESS_ID);
        if (service.isExportToExcel()) {
        	service.modifyServiceResults();
            return json.makeExcelExport(results, columnHeaders, servletContext);
        } else {
            return json.makeResponse(results, entityIdFieldName, service);
        }
    }
    
    private void modifyServiceResults() throws GenericEntityException {
    	List<? extends EntityInterface> results = getResults();	
    	for (EntityInterface e : results) {
    		String partyId = e.getString(PartyLookupConfiguration.INOUT_PARTY_ID);
    		String parentPartyId = e.getString(PartyLookupConfiguration.INOUT_PARENT_ACCOUNT);
            String datasources = getDataSourcesAsString(partyId);
            
            /** VB - below statements do not add new key to the object. 
             *  These merely update values for pre existing keys. 
             *  To add a new key, update the entity in entitymodel.xml
             **/
            String partyName = getPartyName(parentPartyId);
            e.set(PartyLookupConfiguration.INOUT_PARENT_ACCOUNT, partyName);
            e.set(PartyLookupConfiguration.INOUT_DATA_SOURCE, datasources);
        }
    }
    
    private String getPartyName(String partyId) throws GenericEntityException {
    	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
    	String result = PartyHelper.getPartyName(delegator, partyId, false);
    	if (result != null && !result.equals("")) {
    		result += " (" + partyId + ")";
    		return result;
    	} else {
    		return "";	
    	}
    }

    private String getDataSourcesAsString(String partyId) throws GenericEntityException {
    	StringBuffer dataSourcesAsString = new StringBuffer();
    	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
    	List<GenericValue> sources = delegator.findByAnd("PartyDataSource", UtilMisc.toMap("partyId", partyId), UtilMisc.toList("fromDate DESC"));
    	for (GenericValue source : sources) {
    		GenericValue dataSource = source.getRelatedOne("DataSource");
    	    if (dataSource != null) {
    	        dataSourcesAsString.append(dataSource.get("description", getProvider().getLocale()));
    	        dataSourcesAsString.append(", ");
    	    }
    	}
    	if (dataSourcesAsString.length() > 2) 
    		return dataSourcesAsString.toString().substring(0, dataSourcesAsString.length() - 2);
    	return dataSourcesAsString.toString();
    }
    
      
    
    /**
     * Finds a list of <code>Account</code>.
     * @return the list of <code>Account</code>, or <code>null</code> if an error occurred
     */
    public List<PartyFromByRelnAndContactInfoAndPartyClassification> findAccounts() {
    	
    	
    	//***********Country FullName**************************
    	class AccountCountry extends ConvertMapToString implements ICompositeValue {
    		@Override
            public String convert(Map<String, ?> value) {
    			
    			if(value.get(PartyLookupConfiguration.INOUT_COUNTRY)!=null){
    				String  s = (String) value.get(PartyLookupConfiguration.INOUT_COUNTRY);
    			
            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
            	String res="";
            	try {
            		if(!s.equals("")){
            			res = (String) delegator.findByPrimaryKey("Geo", UtilMisc.toMap("geoId", s)).get("geoName");
            		}
				} catch (GenericEntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	return res;
    			}else{
    				return "";
    			}
            }

            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(PartyLookupConfiguration.INOUT_COUNTRY_NAME);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcStatusFieldAcc = FastMap.newInstance();
        calcStatusFieldAcc.put("geoName", new AccountCountry());
        makeCalculatedField(calcStatusFieldAcc);
    	
    	// add rule that causes formated status to be added to result
        class AccountIndustry extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(PartyLookupConfiguration.INOUT_ACCOUNT_INDUSTRY);
            	
            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
            	String res="";
            	try {
            		GenericValue enumEntry = delegator.findByPrimaryKey("Enumeration", UtilMisc.toMap("enumId", s));
            		if(enumEntry!=null){
            			res = (String) enumEntry.get("description");
            		}
				} catch (GenericEntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	return res;
            }

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(PartyLookupConfiguration.INOUT_ACCOUNT_INDUSTRY);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcIndustryField = FastMap.newInstance();
        calcIndustryField.put(PartyLookupConfiguration.OUT_ACCOUNT_INDUSTRY_DESC, new AccountIndustry());
        makeCalculatedField(calcIndustryField);
        
        // add rule that causes account ownership to be added to result
        class AccountOwnership extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(PartyLookupConfiguration.INOUT_ACCOUNT_OWNERSHIP);
            	
            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
            	String res="";
            	try {
            		GenericValue enumEntry = delegator.findByPrimaryKey("Enumeration", UtilMisc.toMap("enumId", s));
            		if(enumEntry!=null){
            			res = (String) enumEntry.get("description");
            		}
				} catch (GenericEntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	return res;
            }

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(PartyLookupConfiguration.INOUT_ACCOUNT_OWNERSHIP);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcOwnershipField = FastMap.newInstance();
        calcOwnershipField.put(PartyLookupConfiguration.OUT_ACCOUNT_OWNERSHIP_DESC, new AccountOwnership());
        makeCalculatedField(calcOwnershipField);
        
        // add rule that causes formatted account type to be added to result
        class AccountType extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(PartyLookupConfiguration.INOUT_ACCOUNT_TYPE);
            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
            	String res="";
            	try {
            		GenericValue enumEntry = delegator.findByPrimaryKey("Enumeration", UtilMisc.toMap("enumId", s));
            		if(enumEntry!=null){
            			res = (String) enumEntry.get("description");
            		}
				} catch (GenericEntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	return res;
            }

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(PartyLookupConfiguration.INOUT_ACCOUNT_TYPE);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcAccTypeField = FastMap.newInstance();
        calcAccTypeField.put(PartyLookupConfiguration.OUT_ACCOUNT_TYPE_DESC, new AccountType());
        makeCalculatedField(calcAccTypeField);
        
        
        // add rule that causes formatted account type to be added to result
        class AccountAssignedTo extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(PartyLookupConfiguration.INOUT_ASSIGNED_TO);
            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
            	return PartyHelper.getPartyName(delegator, s, false);
            }

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(PartyLookupConfiguration.INOUT_ASSIGNED_TO);                
                return s;
            }
        }

        Map<String, ConvertMapToString> accAssignedToField = FastMap.newInstance();
        accAssignedToField.put(PartyLookupConfiguration.INOUT_ASSIGNED_TO_DESC, new AccountAssignedTo());
        makeCalculatedField(accAssignedToField);
        
        return findParties(PartyFromByRelnAndContactInfoAndPartyClassification.class, ACCOUNT_CONDITIONS);
    }

    
    
    
    
    
    
    /**
     * Finds a list of <code>Contact</code>.
     * @return the list of <code>Contact</code>, or <code>null</code> if an error occurred
     */
    public List<PartyFromByRelnAndContactInfoAndPartyClassification> findContacts() {
    	
    	
    	
    	
    	
    	//***********Country FullName**************************
    	class ContactCountry extends ConvertMapToString implements ICompositeValue {
    		@Override
            public String convert(Map<String, ?> value) {
    			String s = "";
    			if(value.get(PartyLookupConfiguration.INOUT_COUNTRY)!=null){
    				 s = (String) value.get(PartyLookupConfiguration.INOUT_COUNTRY);
    			}
            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
            	String res="";
            	try {
            		if(!s.equals("")){
            			res = (String) delegator.findByPrimaryKey("Geo", UtilMisc.toMap("geoId", s)).get("geoName");
            		}
				} catch (GenericEntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	return res;
            }

            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(PartyLookupConfiguration.INOUT_COUNTRY_NAME);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcStatusFieldContry = FastMap.newInstance();
        calcStatusFieldContry.put("geoName", new ContactCountry());
        makeCalculatedField(calcStatusFieldContry);
    	
    	
    	// add rule that causes formated initial account to be added to result
        class AccountsForContact extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
        	@Override
            public String convert(Map<String, ?> value) {
            	String contactPartyId = (String) value.get(PartyLookupConfiguration.INOUT_PARTY_ID);
            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
            	String res="0";
            	try {
            		List prEntry = delegator.findByAnd("PartyRelationship", UtilMisc.toMap("partyIdFrom", contactPartyId,"roleTypeIdFrom","CONTACT","roleTypeIdTo","ACCOUNT"));
            		if(prEntry!=null){
            			res = ((Integer)prEntry.size()).toString();
            		}
				} catch (GenericEntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	return res;
            }

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(PartyLookupConfiguration.INOUT_PARTY_ID);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcAccForContactField = FastMap.newInstance();
        calcAccForContactField.put(PartyLookupConfiguration.OUT_NUMBER_OF_ACCOUNTS_FOR_CONTACT, new AccountsForContact());
        makeCalculatedField(calcAccForContactField);
        
        return findParties(PartyFromByRelnAndContactInfoAndPartyClassification.class, CONTACT_CONDITIONS);
    }

    /**
     * Finds a list of <code>Lead</code>.
     * @return the list of <code>Lead</code>, or <code>null</code> if an error occurred
     */
    public List<PartyFromByRelnAndContactInfoAndPartyClassification> findLeads() {
    	

        	
    	
    	//***********Country FullName**************************
    	class LeadCountry extends ConvertMapToString implements ICompositeValue {
    		@Override
            public String convert(Map<String, ?> value) {
    			String s = "";
    			if(value.get(PartyLookupConfiguration.INOUT_COUNTRY)!=null){
    				 s = (String) value.get(PartyLookupConfiguration.INOUT_COUNTRY);
    			}
            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
            	String res="";
            	try {
            		if(s!=null && !s.equals("")){
            			Debug.logInfo("*********************************COUNTRY=> "+s, MODULE);
            			res = (String) delegator.findByPrimaryKey("Geo", UtilMisc.toMap("geoId", s)).get("geoName");
            		}
				} catch (GenericEntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	return res;
            }

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(PartyLookupConfiguration.INOUT_COUNTRY_NAME);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcStatusFieldCon = FastMap.newInstance();
        calcStatusFieldCon.put(PartyLookupConfiguration.INOUT_COUNTRY_NAME, new LeadCountry());
        makeCalculatedField(calcStatusFieldCon);
    	
    	
      //***********State FullName**************************
    /*	class LeadState extends ConvertMapToString implements ICompositeValue {
    		@Override
            public String convert(Map<String, ?> value) {
    			String s = "";
    			if(value.get("stateProvinceGeoId")!=null){
    				 s = (String) value.get("stateProvinceGeoId");
    				 Debug.logInfo("*********************************STATE111=> "+s);
    			}
            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
            	String res="";
            	try {
            		if(s!=null && !s.equals("")){
            			Debug.logInfo("*********************************STATE=> "+s);
            			res = (String) delegator.findByPrimaryKey("Geo", UtilMisc.toMap("geoId", s)).get("geoName");
            			Debug.logInfo("*********************************STATE222=> "+res.toLowerCase());
            		}
				} catch (GenericEntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	return res;
            }

            
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add("geoName");                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcStateFieldCon = FastMap.newInstance();
        calcStateFieldCon.put(PartyLookupConfiguration.INOUT_STATE_NAME, new LeadState());
        makeCalculatedField(calcStateFieldCon);*/
    	
        
    	
    	// add rule that causes formated status to be added to result
        class LeadStatus extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(PartyLookupConfiguration.INOUT_STATUS);
            	
            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
            	String res="";
            	try {
            		res = (String) delegator.findByPrimaryKey("StatusItem", UtilMisc.toMap("statusId", s)).get("description");
				} catch (GenericEntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	return res;
            }

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(PartyLookupConfiguration.INOUT_STATUS);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcStatusField = FastMap.newInstance();
        calcStatusField.put("leadStatus", new LeadStatus());
        makeCalculatedField(calcStatusField);
        
        // add rule that causes formated data source to be added to result
        class LeadDataSource extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @Override
            public String convert(Map<String, ?> value) {
//            	String partyId = (String) value.get(PartyLookupConfiguration.INOUT_PARTY_ID);
//            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
//            	String res="";
//            	try {
//            		List pdsEntry = delegator.findByAnd("PartyDataSource", UtilMisc.toMap("partyId", partyId));
//            		if(pdsEntry!=null && pdsEntry.size()>0){
//	            		String dataSourceId = (String) ((GenericValue)pdsEntry.get(0)).get("dataSourceId");
//	            		res = (String) delegator.findByPrimaryKey("DataSource", UtilMisc.toMap("dataSourceId", dataSourceId)).get("description");
//	            	}
//				} catch (GenericEntityException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//            	return res;
//            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
            	String partyId = (String)value.get(PartyLookupConfiguration.INOUT_PARTY_ID);
            	String result = "";
            	if(partyId != null) {
//            	String dataSourceId = (String) value.get(PartyLookupConfiguration.INOUT_DATA_SOURCE);
	            	try{
	            		result = getDataSourcesAsString(partyId);
//	            		return (String) delegator.findByPrimaryKey("DataSource", UtilMisc.toMap("dataSourceId", dataSourceId)).get("description");
					} catch (GenericEntityException e) {
						e.printStackTrace();
					}
            	}
            	return result;
            }

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
//            public LinkedHashSet<String> getFields() {
//                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
//                s.add(PartyLookupConfiguration.INOUT_PARTY_ID);                
//                return s;
//            }
            
            public LinkedHashSet<String> getFields() {
              LinkedHashSet<String> s = new LinkedHashSet<String>(1);
              s.add(PartyLookupConfiguration.INOUT_DATA_SOURCE);                
              return s;
          }
        }

        Map<String, ConvertMapToString> calcSourceField = FastMap.newInstance();
        calcSourceField.put(PartyLookupConfiguration.OUT_LEAD_DATA_SOURCE, new LeadDataSource());
        makeCalculatedField(calcSourceField);
        
        return findParties(PartyFromByRelnAndContactInfoAndPartyClassification.class, LEAD_CONDITIONS);
    }

    /**
     * Finds a list of <code>Partner</code>.
     * @return the list of <code>Partner</code>, or <code>null</code> if an error occurred
     */
    public List<PartyFromByRelnAndContactInfoAndPartyClassification> findPartners() {
        return findParties(PartyFromByRelnAndContactInfoAndPartyClassification.class, PARTNER_CONDITIONS);
    }

    /**
     * Finds a list of <code>Supplier</code>.
     * @return the list of <code>Supplier</code>, or <code>null</code> if an error occurred
     */
    public List<PartyRoleNameDetailSupplementalData> findSuppliers() {
        // suppliers don't have relationships and classifications, so use the basic party lookup entity
        // also note that to be able to change the entity like this, its fields must be coherent with PartyLookupConfiguration
        setActiveOnly(false);
        return findParties(PartyRoleNameDetailSupplementalData.class, SUPPLIER_CONDITIONS);
    }

    /**
     * Suggests a list of <code>Account</code>.
     * @return the list of <code>Account</code>, or <code>null</code> if an error occurred
     */
    public List<PartyFromByRelnAndContactInfoAndPartyClassification> suggestAccounts() {
        return suggestParties(ACCOUNT_CONDITIONS);
    }

    /**
     * Suggests a list of contacts.
     * @return the list of <code>Contact</code>, or <code>null</code> if an error occurred
     */
    public List<PartyFromByRelnAndContactInfoAndPartyClassification> suggestContacts() {
        return suggestParties(CONTACT_CONDITIONS);
    }

    /**
     * Suggests a list of leads.
     * @return the list of <code>Contact</code>, or <code>null</code> if an error occurred
     */
    public List<PartyFromByRelnAndContactInfoAndPartyClassification> suggestLeads() {
        return suggestParties(LEAD_CONDITIONS);
    }

    
    /**
     * Suggests a list of leads.
     * @return the list of <code>Contact</code>, or <code>null</code> if an error occurred
     */
    public List<PartyFromByRelnAndContactInfoAndPartyClassification> suggestLeadsForOpp() {
        return suggestParties(LEAD_CONDITIONS_FOR_OPPORTUNITY);
    }

    
    /**
     * Sets if the lookup methods should filter active parties only, defaults to <code>true</code>.
     * @param bool a <code>boolean</code> value
     */
    public void setActiveOnly(boolean bool) {
        this.activeOnly = bool;
    }

    private List<PartyFromByRelnAndContactInfoAndPartyClassification> suggestParties(EntityCondition roleCondition) {
        String searchString = getSuggestQuery();
        /* VB - 
         * If there's no auto complete string, there's no duplicates, as it uses search where everything is handled. 
         * cannot use this. 
         **/
        if (searchString == null) {
            return findAllParties(PartyFromByRelnAndContactInfoAndPartyClassification.class, roleCondition);
        }
        int size = -1;
        searchString = searchString.toUpperCase();
        try {
            List<EntityCondition> conditions = new ArrayList<EntityCondition>();
            conditions.add(roleCondition);
            if (activeOnly) {
                conditions.add(EntityUtil.getFilterByDateExpr());
            }
            /* VB - 
             * For currently responsible sales rep, partyRelationshipTypeId is RESPOSIBLE_FOR and
             * thruDate is null, whereas its not null previously responsible sales rep.
             **/
            conditions.add(new EntityExpr("thruDate", EntityOperator.EQUALS, null));
            conditions.add(new EntityExpr("partyRelationshipTypeId", true, EntityOperator.EQUALS, "RESPONSIBLE_FOR", true));
            /* VB - 
             * This is a very expensive logic, at present brings all the rows for a particular party (ACCOUNT for eg.), loops over it.
             * The drop down applies no logic of team based security.
             * @Todo -
             * 1. Team based security, show parties belonging to his/ her team.
             * 2. Instead of traversing in java, below logic should be implemented in SQL.
             * 
             **/
            List<PartyFromByRelnAndContactInfoAndPartyClassification> allParties = getRepository().findList(PartyFromByRelnAndContactInfoAndPartyClassification.class, new EntityConditionList(conditions, EntityOperator.AND), getFields(), getPager().getSortList());
            if (allParties != null) {
            	size = allParties.size();
            }
            List<PartyFromByRelnAndContactInfoAndPartyClassification> parties = new ArrayList<PartyFromByRelnAndContactInfoAndPartyClassification>(size);
            // counts the number of records found matching the query
            int matchCount = 0;

            String fullName, firstName, lastName, groupName, compositeName;
            for (PartyFromByRelnAndContactInfoAndPartyClassification party : allParties) {
                if (matchCount > UtilLookup.SUGGEST_MAX_RESULTS) {
                    break;
                }
                // search the full name
                fullName = "";
                firstName = party.getFirstName();
                if (firstName != null) {
                    fullName = firstName;
                }

                lastName = party.getLastName();
                if (lastName != null) {
                    fullName = fullName + " " + lastName;
                }

                fullName = fullName.toUpperCase();
                if (fullName.indexOf(searchString) > -1) {
                    parties.add(party);
                    matchCount++;
                    continue;
                }

                // search the group name
                groupName = party.getGroupName();
                if (groupName == null) {
                    groupName = "";
                }
                groupName = groupName.toUpperCase();
                if (groupName.indexOf(searchString) > -1) {
                    parties.add(party);
                    matchCount++;
                    continue;
                }

                // search the composite name (incidentally, this also matches partyId)
                compositeName = groupName;
                if (fullName.trim().length() > 0) {
                    compositeName = compositeName + " " + fullName;
                }
                compositeName = compositeName + " (" + party.getPartyId().toUpperCase() + ")";
                if (compositeName.indexOf(searchString) > -1) {
                    parties.add(party);
                    matchCount++;
                    continue;
                }
            }
            // get paginated results
            paginateResults(parties);
        } catch (RepositoryException e) {
            Debug.logError(e, MODULE);
            return null;
        }
        return getResults();
    }

    @Override
    public String makeSuggestDisplayedText(EntityInterface party) {
        StringBuffer sb = new StringBuffer();
        String firstName = party.getString("firstName");
        String middleName = party.getString("middleName");
        String lastName = party.getString("lastName");
        String groupName = party.getString("groupName");
        String partyId = party.getString("partyId");
        if (UtilValidate.isNotEmpty(groupName)) {
            sb.append(groupName);
        } else {
            sb.append(firstName);
            if (UtilValidate.isNotEmpty(middleName)) {
                sb.append(" ").append(middleName);
            }
            if (UtilValidate.isNotEmpty(lastName)) {
                sb.append(" ").append(lastName);
            }
        }
        sb.append(" (").append(partyId).append(")");

        return sb.toString();
    }

    private <T extends EntityInterface> List<T> findParties(Class<T> entity, EntityCondition roleCondition) {
    	Debug.logInfo("----------------------listing filters------------------1111111111111111111112222222222", MODULE);
        // add rule that causes formated primary phone to be added to result
        class PhoneNumberSortable extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @Override
            public String convert(Map<String, ?> value) {
                StringBuilder sb = new StringBuilder();
                String s = (String) value.get(PartyLookupConfiguration.INOUT_PHONE_COUNTRY_CODE);
                if (UtilValidate.isNotEmpty(s)) {
                    sb.append(s);
                }
                s = (String) value.get(PartyLookupConfiguration.INOUT_PHONE_AREA_CODE);
                if (UtilValidate.isNotEmpty(s)) {
                    sb.append(" ").append(s);
                }
                s = (String) value.get(PartyLookupConfiguration.INOUT_PHONE_NUMBER);
                if (UtilValidate.isNotEmpty(s)) {
                    sb.append(" ").append(s);
                }
                String phoneNumber = sb.toString();
                if (UtilValidate.isNotEmpty(phoneNumber)) {
                    return phoneNumber.trim();
                } else {
                    return "";
                }
            }

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(3);
                s.add(PartyLookupConfiguration.INOUT_PHONE_COUNTRY_CODE);
                s.add(PartyLookupConfiguration.INOUT_PHONE_AREA_CODE);
                s.add(PartyLookupConfiguration.INOUT_PHONE_NUMBER);
                return s;
            }
        }

        Map<String, ConvertMapToString> calcField = FastMap.newInstance();
        calcField.put("formatedPrimaryPhone", new PhoneNumberSortable());
        makeCalculatedField(calcField);
        
    //**************************** TEAM NAME*******************************************************    
        class TeamName extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @SuppressWarnings("unchecked")
			@Override
            public String convert(Map<String, ?> value) {
            	
            	if(value.get(PartyLookupConfiguration.INOUT_ASSIGNED_TEAM_PARTY_ID)!=null && !(value.get(PartyLookupConfiguration.INOUT_ASSIGNED_TEAM_PARTY_ID)).equals("")){
            		String s= "";
            		 s = (String) value.get(PartyLookupConfiguration.INOUT_ASSIGNED_TEAM_PARTY_ID);
            	
	            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");	
	            	Debug.logInfo("@@@@@@@@@@@@@@@@@@@@@@@@@************************ SSS=> "+s, MODULE);
	            	Map m = new HashMap();
	            	m.put("partyId", s);
	            	GenericValue gv = null;
	            	String teamName = null;
	            	try {
						gv = delegator.findByPrimaryKey("PartyGroup", m);
						teamName = gv.getString("groupName");
						Debug.logInfo("@@@@@@@@@@@@@@@@@@@@@@@@@************************ teamName=> "+teamName, MODULE);
					} catch (GenericEntityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	return teamName ;
            	}else{
            		return "";
            	}
            		
            }
            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(PartyLookupConfiguration.INOUT_ASSIGNED_TEAM_PARTY_ID);                
                return s;
            }
        }

        Map<String, ConvertMapToString> teamNameField = FastMap.newInstance();
        teamNameField.put(PartyLookupConfiguration.INOUT_ASSIGNED_TEAM_DESC, new TeamName());
        makeCalculatedField(teamNameField);   
             
        
//================================ Owner Name==========================================
        
        class OwnerName extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @SuppressWarnings("unchecked")
			@Override
            public String convert(Map<String, ?> value) {
            	
            	if(value.get(PartyLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN)!=null && !(value.get(PartyLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN)).equals("")){
            		String s = (String) value.get(ActivityLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN);
            	
            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");	
            	Debug.logInfo("@@@@@@@@@@@@@@@@@@@@@@@@@************************ SSS=> "+s, MODULE);
            	Map m = new HashMap();
            	m.put("userLoginId", s);
            	GenericValue gv = null;
            	String ownerPartyId = null;
            	try {
					gv = delegator.findByPrimaryKey("UserLogin", m);
					ownerPartyId = gv.getString("partyId");
					Debug.logInfo("@@@@@@@@@@@@@@@@@@@@@@@@@************************ ownerPartyId=> "+ownerPartyId, MODULE);
				} catch (GenericEntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	return PartyHelper.getPartyName(delegator, ownerPartyId, false);
            	}else{
            		return "";
            	}
            }
            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(PartyLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcLeadOwnerNameField = FastMap.newInstance();
        calcLeadOwnerNameField.put(PartyLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN_DESC, new OwnerName());
        makeCalculatedField(calcLeadOwnerNameField);   
               
        
        
    	
      //**************************** ASSIGNED PARTY NAME*******************************************************    
        class AssignedPartyName extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @SuppressWarnings("unchecked")
			@Override
            public String convert(Map<String, ?> value) {
            	
            	if(value.get(PartyLookupConfiguration.INOUT_ASSIGNED_TO)!=null && !(value.get(PartyLookupConfiguration.INOUT_ASSIGNED_TO)).equals("")){
            		String s = (String) value.get(PartyLookupConfiguration.INOUT_ASSIGNED_TO);
            	
	            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");	
	            	Debug.logInfo("@@@@@@@@@@@@@@@@@@@@@@@@@************************ AAA=> "+s, MODULE);
	            	return PartyHelper.getPartyName(delegator, s, false);
            	}else{
            		return "";
            	}
            		
            }
            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(PartyLookupConfiguration.INOUT_ASSIGNED_TO);                
                return s;
            }
        }

        Map<String, ConvertMapToString> assNameField = FastMap.newInstance();
        assNameField.put(PartyLookupConfiguration.INOUT_ASSIGNED_TO_DESC, new AssignedPartyName());
        makeCalculatedField(assNameField);   
                
        
        
        
        
        
     // add rule that causes formated status to be added to result
        class StateProvinceName extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @Override
            public String convert(Map<String, ?> value) {
            	String con = "";
            	if(value.get(PartyLookupConfiguration.INOUT_COUNTRY)!=null){
	   				 con = (String) value.get(PartyLookupConfiguration.INOUT_COUNTRY);
	   			
	            	String s="";
	            	if(value.get(PartyLookupConfiguration.INOUT_STATE)!=null){
	   				 s = (String) value.get(PartyLookupConfiguration.INOUT_STATE);
	   				 if((con.trim()).equals("IND")){
		   				 s = "IN-"+s;
		   				 Debug.logInfo("*********************************STATE111=> "+s, MODULE);
	   				 } 
	            	}
	            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
	            	String res="";
	            	try {
	            		GenericValue enumEntry = delegator.findByPrimaryKey("Geo", UtilMisc.toMap("geoId", s));
	            		if(enumEntry!=null){
	            			res = (String) enumEntry.get("geoName");
	            		}
					} catch (GenericEntityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	return res;
	           }else{
	           	return "";
	          }
            }
            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add("stateProvinceGeoId");                
                return s;
            }
        }
        
        Map<String, ConvertMapToString> calcStateProvNameField = FastMap.newInstance();
        calcStateProvNameField.put(PartyLookupConfiguration.INOUT_STATE_NAME, new StateProvinceName());
        makeCalculatedField(calcStateProvNameField);

        EntityCondition condition = roleCondition;

        // select parties assigned to current user or his team according to view preferences.
        if (getProvider().parameterIsPresent(PartyLookupConfiguration.IN_RESPONSIBILTY)) {
            if (getProvider().getUser().getOfbizUserLogin() != null) {
                String userId = getProvider().getUser().getOfbizUserLogin().getString("partyId");
                String viewPref = getProvider().getParameter(PartyLookupConfiguration.IN_RESPONSIBILTY);
                if (PartyLookupConfiguration.MY_VALUES.equals(viewPref)) {
                    // my parties
                    condition = new EntityConditionList(
                            Arrays.asList(
                                    condition,
                                    new EntityExpr("partyIdTo", EntityOperator.EQUALS, userId),
                                    new EntityExpr("partyRelationshipTypeId", EntityOperator.IN, Arrays.asList("RESPONSIBLE_FOR"))
                            ),
                            EntityOperator.AND
                    );
                } else if (PartyLookupConfiguration.TEAM_VALUES.equals(viewPref)) {
                    // my teams parties
                    condition = new EntityConditionList(
                            Arrays.asList(
                                    condition,
                                    new EntityExpr("partyIdTo", EntityOperator.EQUALS, userId),
                                    new EntityExpr("partyRelationshipTypeId", EntityOperator.IN, Arrays.asList("RESPONSIBLE_FOR", "ASSIGNED_TO"))
                            ),
                            EntityOperator.AND
                    );
                } /*else{
                	// my teams parties
                    condition = new EntityConditionList(
                            Arrays.asList(
                                    condition,
                                    new EntityExpr("partyIdTo", EntityOperator.EQUALS, userId),
                                    new EntityExpr("partyRelationshipTypeId", EntityOperator.IN, Arrays.asList("RESPONSIBLE_FOR", "ASSIGNED_TO"))
                            ),
                            EntityOperator.AND
                    );
                }*/
            } else {
                Debug.logError("Current session do not have any UserLogin set.", MODULE);
            }
        }/*else{
        	String userId = getProvider().getUser().getOfbizUserLogin().getString("partyId");
        	// my teams parties
            condition = new EntityConditionList(
                    Arrays.asList(
                            condition,
                            new EntityExpr("partyIdTo", EntityOperator.EQUALS, userId),
                            new EntityExpr("partyRelationshipTypeId", EntityOperator.IN, Arrays.asList("RESPONSIBLE_FOR", "ASSIGNED_TO"))
                    ),
                    EntityOperator.AND
            );
        }*/
        
        Debug.logInfo("----------------------listing filters------------------11111111111111111111122222222224444444444444", MODULE);

        if (getProvider().oneParameterIsPresent(BY_ADVANCED_FILTERS)) {
        	Debug.logInfo("----------------------listing filters------------------1111111111111111111113333333333333", MODULE);
            return findPartiesBy(entity, condition, BY_ADVANCED_FILTERS);
        }
        
        if (getProvider().oneParameterIsPresent(BY_ID_FILTERS)) {
            return findPartiesBy(entity, condition, BY_ID_FILTERS);
        }

        if (getProvider().oneParameterIsPresent(BY_NAME_FILTERS)) {
            return findPartiesBy(entity, condition, BY_NAME_FILTERS);
        }

        if (getProvider().oneParameterIsPresent(BY_PHONE_FILTERS)) {
            return findPartiesBy(entity, condition, BY_PHONE_FILTERS);
        }

        return findAllParties(entity, condition);
    }

    private <T extends EntityInterface> List<T> findAllParties(Class<T> entity, EntityCondition roleCondition) {
    	Debug.logInfo("----------------------listing filters------------findAllParties------1111111111111111111112222222222"+activeOnly, MODULE);
        //List<EntityCondition> conditions = Arrays.asList(roleCondition);
    	List<EntityCondition> conditions = new ArrayList<EntityCondition>();
    	if(roleCondition!=null){
    		conditions.add(roleCondition);
    	}
        conditions.add(new EntityExpr("partyRelationshipTypeId",  EntityOperator.EQUALS, "RESPONSIBLE_FOR"));
        
        
        String userId = getProvider().getUser().getOfbizUserLogin().getString("partyId");
        GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
        Collection<String> teamMembers=null;
		try {
			teamMembers = getTeamMembersForPartyId(userId, delegator);
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(teamMembers!=null){
			List<String> teamMemberPartyIds = Arrays.asList(teamMembers.toArray(new String[teamMembers.size()]));
			conditions.add(new EntityExpr("partyIdTo", false, EntityOperator.IN, teamMemberPartyIds, false));
			//conditions.add(new EntityExpr("partyRelationshipTypeId", true, EntityOperator.EQUALS, "ASSIGNED_TO", true));
		}
		
		
        
        if (activeOnly) {
            conditions.add(EntityUtil.getFilterByDateExpr());
        }
        conditions.add(new EntityExpr("thruDate", EntityOperator.EQUALS, null));
        return findList(entity, new EntityConditionList(conditions, EntityOperator.AND));
    }

    @SuppressWarnings("unchecked")
	private <T extends EntityInterface> List<T> findPartiesBy(Class<T> entity, EntityCondition roleCondition, List<String> filters) {
    	
    	Debug.logInfo("----------------------listing filters------------------"+activeOnly, MODULE);
    	for (int i = 0; i < filters.size(); i++) {
			Debug.logInfo(filters.get(i), MODULE);
		}
    	
        List<EntityCondition> conds = new ArrayList<EntityCondition>();
        
        
        String userId = getProvider().getUser().getOfbizUserLogin().getString("partyId");
        GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
        Collection<String> teamMembers=null;
		try {
			teamMembers = getTeamMembersForPartyIdExt(userId, delegator, filters);
		} catch (GenericEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(teamMembers!=null){
			List<String> teamMemberPartyIds = Arrays.asList(teamMembers.toArray(new String[teamMembers.size()]));
			conds.add(new EntityExpr("partyIdTo", false, EntityOperator.IN, teamMemberPartyIds, false));
			//conds.add(new EntityExpr("partyRelationshipTypeId", true, EntityOperator.EQUALS, "ASSIGNED_TO", true));
		}
		
		
        if (activeOnly) {
            conds.add(EntityUtil.getFilterByDateExpr());
        }
        if(roleCondition!=null){
        	conds.add(roleCondition);
        }
        boolean flag = false;
        
        List<String> finalFilters = new ArrayList<String>(); 
        
        for (String filter : filters) {
            if (getProvider().parameterIsPresent(filter)) {
            	Debug.logInfo("##############lookup#########################=> "+filter, MODULE);
            	Debug.logInfo("##############lookup value#########################=> "+getProvider().getParameter(filter), MODULE);
            	
            	if(filter.equals("createdByUserLogin")){
            		Map m = new HashMap();
            		List l = new ArrayList();
            		String loginId="";
            		String partyId = getProvider().getParameter(filter);
            		Debug.logInfo("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&partyId=> "+partyId, MODULE);
            		GenericValue gv = null;
            		delegator = GenericDelegator.getGenericDelegator("default");
            		m.put("partyId", partyId);
            		try {
						l = delegator.findByAnd("UserLogin", m);
						if(l.size()>0){
							gv = (GenericValue)l.get(0);
							loginId = gv.getString("userLoginId");
							Debug.logInfo("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&loginId=> "+loginId, MODULE);
							conds.add(new EntityExpr(filter, true, EntityOperator.LIKE, loginId+"%", true));
						}
					} catch (GenericEntityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
            	else if(filter.equals("partyIdTo")){
            		flag = true;
            		conds.add(new EntityExpr("partyIdTo", true, EntityOperator.EQUALS, getProvider().getParameter(filter), true));
            		conds.add(new EntityExpr("partyRelationshipTypeId", true, EntityOperator.EQUALS, "RESPONSIBLE_FOR", true));
            	}
            	else if(filter.equals("teamPartyIdTo")){
//            		flag = true;
//            		conds.add(new EntityExpr("partyIdTo", true, EntityOperator.EQUALS, getProvider().getParameter(filter), true));
//            		conds.add(new EntityExpr("roleTypeIdTo", true, EntityOperator.EQUALS, "ACCOUNT_TEAM", true));
//            		conds.add(new EntityExpr("partyRelationshipTypeId", true, EntityOperator.EQUALS, "ASSIGNED_TO", true));
            	}
            	else{            
            		finalFilters.add(filter);
            	}
        
            }
        }
       if(!flag){
    	    conds.add(new EntityExpr("partyRelationshipTypeId", true, EntityOperator.EQUALS, "RESPONSIBLE_FOR", true));
       }
       conds.add(new EntityExpr("thruDate", EntityOperator.EQUALS, null));
       return findListWithFilters(entity, conds, finalFilters);
    }

    
    public static final List TEAM_SECURITY_GROUPS = UtilMisc.toList("SALES_MANAGER", "SALES_REP", "SALES_REP_LIMITED", "CSR");
    public static Collection getTeamsForPartyId(String partyId, GenericDelegator delegator) throws GenericEntityException {
        EntityCondition conditions = new EntityConditionList( UtilMisc.toList(
                    new EntityExpr("roleTypeIdFrom", EntityOperator.EQUALS, "ACCOUNT_TEAM"),
                    new EntityExpr("partyIdTo", EntityOperator.EQUALS, partyId),
                    new EntityExpr("partyRelationshipTypeId", EntityOperator.EQUALS, "ASSIGNED_TO"),
                    new EntityExpr("securityGroupId", EntityOperator.IN, TEAM_SECURITY_GROUPS),
                    EntityUtil.getFilterByDateExpr()
                    ), EntityOperator.AND);
        List relationships = delegator.findByCondition("PartyRelationship", conditions, null, null, null, UtilCommon.DISTINCT_READ_OPTIONS);
        Set partyIds = new HashSet();
        for (Iterator iter = relationships.iterator(); iter.hasNext(); ) {
            GenericValue relationship = (GenericValue) iter.next();
            partyIds.add(relationship.get("partyIdFrom"));
        }
        return partyIds;
    }

    public static List<GenericValue> getActiveTeamMembers(Collection<String> teamPartyIds, GenericDelegator delegator) throws GenericEntityException {
        // this might happen if there are no teams set up yet
        if (UtilValidate.isEmpty(teamPartyIds)) {
            Debug.logInfo("No team partyIds set, so getActiveTeamMembers returns null", MODULE);
            return null;
        }

        EntityCondition orConditions =  new EntityConditionList( UtilMisc.toList(
                    new EntityExpr("securityGroupId", EntityOperator.EQUALS, "SALES_MANAGER"),
                    new EntityExpr("securityGroupId", EntityOperator.EQUALS, "SALES_REP"),
                    new EntityExpr("securityGroupId", EntityOperator.EQUALS, "SALES_REP_LIMITED"),
                    new EntityExpr("securityGroupId", EntityOperator.EQUALS, "CSR")
                    ), EntityOperator.OR);
        EntityCondition conditions = new EntityConditionList( UtilMisc.toList(
                    new EntityExpr("roleTypeIdFrom", EntityOperator.EQUALS, "ACCOUNT_TEAM"),
                    new EntityExpr("partyIdFrom", EntityOperator.IN, teamPartyIds),
                    new EntityExpr("partyRelationshipTypeId", EntityOperator.EQUALS, "ASSIGNED_TO"),
                    orConditions,
                    // new EntityExpr("securityGroupId", EntityOperator.IN, TEAM_SECURITY_GROUPS),  XXX TODO: found bug in mysql: this is not equivalent to using the or condition!
                    EntityUtil.getFilterByDateExpr()
                    ), EntityOperator.AND);
        List teamMembersIterator = delegator.findByCondition(
                "PartyToSummaryByRelationship", 
                conditions, 
                null, 
                Arrays.asList("partyId", "firstName", "lastName"), 
                Arrays.asList("firstName", "lastName"), 
                new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true)
                );
//        List<GenericValue> resultList = teamMembersIterator.getCompleteList();
//        teamMembersIterator.close();
        return teamMembersIterator;
    }

    private Collection<String> getTeamMembersForPartyId(String partyId, GenericDelegator delegator) throws GenericEntityException {
//        Collection teamPartyIds = getTeamsForPartyId(partyId, delegator);
//        if (teamPartyIds.size() == 0) return teamPartyIds;
//
//        List relationships = getActiveTeamMembers(teamPartyIds, delegator);
//        Set<String> partyIds = new HashSet<String>();
//        for (Iterator iter = relationships.iterator(); iter.hasNext(); ) {
//            GenericValue relationship = (GenericValue) iter.next();
//            partyIds.add(relationship.getString("partyId"));
//        }
//        return partyIds;
    	return getTeamMembersForPartyIdExt(partyId, delegator, null);
    }   
    
    private Collection<String> getTeamMembersForPartyIdExt(String userId, GenericDelegator delegator, List<String> filters) throws GenericEntityException {
        Collection<String> teamPartyIds = null;
        String filterValue = getProvider().getParameter(PartyLookupConfiguration.INOUT_ASSIGNED_TEAM_PARTY_ID);
        if (filters != null && filters.contains(PartyLookupConfiguration.INOUT_ASSIGNED_TEAM_PARTY_ID) 
        		&& filterValue != null && !filterValue.equals("")) {
//        	VB :- can also use getProvider().parameterIsPresent(ActivityLookupConfiguration.INOUT_ASSIGNED_TEAM_PARTY_ID)
        	teamPartyIds = new HashSet<String>();
        	teamPartyIds.add(filterValue);
//        	Debug.logInfo("getTeamMembersForPartyIdExt() Team Filter present >> " + filterValue);
        } else {
        	teamPartyIds = getTeamsForPartyId(userId, delegator);
            if (teamPartyIds.size() == 0) return teamPartyIds;
//        	Debug.logInfo("getTeamMembersForPartyIdExt() Team Filter absent >> " + teamPartyIds);
        }
        List<GenericValue> relationships = getActiveTeamMembers(teamPartyIds, delegator);
        Set<String> partyIds = new HashSet<String>();
        for (Iterator<GenericValue> iter = relationships.iterator(); iter.hasNext(); ) {
            GenericValue relationship = (GenericValue) iter.next();
            partyIds.add(relationship.getString("partyId"));
        }
        return partyIds;
    } 
}

