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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
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
import org.ofbiz.base.util.UtilDateTime;
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
import org.opentaps.domain.base.entities.SalesOpportunitySearch;
import org.opentaps.foundation.entity.EntityInterface;
import org.opentaps.foundation.infrastructure.InfrastructureException;
import org.opentaps.foundation.repository.RepositoryException;
import org.opentaps.gwt.common.client.lookup.configuration.OpportunityLookupConfiguration;
import org.opentaps.gwt.common.client.lookup.configuration.PartyLookupConfiguration;
import org.opentaps.gwt.common.server.HttpInputProvider;
import org.opentaps.gwt.common.server.InputProviderInterface;


/**
 * The RPC service used to populate the OpportunityListView.
 */
public class OpportunityLookupService extends EntityLookupService {

    private static final String MODULE = OpportunityLookupService.class.getName();

    private static List<String> BY_BASIC_FILTERS = Arrays.asList(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ID,
    															 OpportunityLookupConfiguration.INOUT_OPPORTUNITY_NUMBER,
    															 OpportunityLookupConfiguration.INOUT_OPPORTUNITY_STAGE,
    															 OpportunityLookupConfiguration.INOUT_OPPORTUNITY_TYPE);
    private static List<String> BY_ADVANCED_FILTERS = Arrays.asList(
    																OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ID,
    																OpportunityLookupConfiguration.INOUT_OPPORTUNITY_NUMBER,
    																OpportunityLookupConfiguration.INOUT_OPPORTUNITY_STAGE,
			 														OpportunityLookupConfiguration.INOUT_OPPORTUNITY_MODEL_OF_INTEREST,
			 														OpportunityLookupConfiguration.INOUT_OPPORTUNITY_PROBABILITY,
			 														OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ESTIMATED_CLOSE_DATE,
			 														OpportunityLookupConfiguration.IN_OPPORTUNITY_ESTIMATED_CLOSE_DATE_FROM,
			 														OpportunityLookupConfiguration.IN_OPPORTUNITY_ESTIMATED_CLOSE_DATE_TO,
			 														OpportunityLookupConfiguration.OUT_OPPORTUNITY_CREATED_DATE,
			 														OpportunityLookupConfiguration.INOUT_OPPORTUNITY_EXCHANGE_OLD_CAR,
			 														OpportunityLookupConfiguration.IN_OPPORTUNITY_CREATED_DATE_FROM,
			 														OpportunityLookupConfiguration.IN_OPPORTUNITY_CREATED_DATE_TO,
			 														OpportunityLookupConfiguration.INOUT_PARTY_ID,
			 														OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ACCOUNT,
			 														OpportunityLookupConfiguration.INOUT_OPPORTUNITY_LEAD,
			 														OpportunityLookupConfiguration.INOUT_OPPORTUNITY_CONTACT,
			 														OpportunityLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN,
			 														OpportunityLookupConfiguration.INOUT_ASSIGNED_TO,
			 														OpportunityLookupConfiguration.INOUT_ASSIGNED_TEAM_PARTY_ID,
			 														OpportunityLookupConfiguration.INOUT_OPPORTUNITY_NEXT_STEP,
			 														OpportunityLookupConfiguration.INOUT_DATA_SOURCE);

    private boolean activeOnly = false;

    /**
     * Creates a new <code>OpportunityLookupService</code> instance.
     * @param provider an <code>InputProviderInterface</code> value
     */
    public OpportunityLookupService(InputProviderInterface provider) {
        super(provider, OpportunityLookupConfiguration.LIST_OUT_FIELDS);
    }
    
    /**
     * AJAX event to perform lookups on Opportunities.
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return the resulting JSON response
     * @throws InfrastructureException if an error occurs
     * @throws RepositoryException 
     * @throws GenericEntityException 
     */
    public static String findOpportunities(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException, RepositoryException, GenericEntityException {
    	InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        OpportunityLookupService service = new OpportunityLookupService(provider);
        Debug.logInfo("The params >> " + provider.getParameterMap(), MODULE);
        int count = service.findOpportunities(SalesOpportunitySearch.class).size();
        Debug.logInfo("Number of opportunities found = " + count, MODULE);
//        if (service.isExportToExcel()) {
//        	service.modifyServiceResults();
//        }
//        return json.makeLookupResponse(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ID, service, request.getSession(true).getServletContext());
        return makeLookupResponse(json, OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ID, service, request.getSession(true).getServletContext());
    }
    
    private static String makeLookupResponse(JsonResponse json, String entityIdFieldName, OpportunityLookupService service, ServletContext servletContext) throws GenericEntityException {
        List<? extends EntityInterface> results = service.getResults();
        if (results == null) {
            return json.makeErrorResponse(service.getLastException());
        }
        List<String> columnHeaders = Arrays.asList(
				OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ID,
				OpportunityLookupConfiguration.INOUT_OPPORTUNITY_NUMBER,
				OpportunityLookupConfiguration.INOUT_OPPORTUNITY_STAGE,
				OpportunityLookupConfiguration.INOUT_OPPORTUNITY_MODEL_OF_INTEREST,
				OpportunityLookupConfiguration.INOUT_OPPORTUNITY_PROBABILITY,
				OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ESTIMATED_CLOSE_DATE,
				OpportunityLookupConfiguration.OUT_OPPORTUNITY_CREATED_STAMP,
				OpportunityLookupConfiguration.INOUT_OPPORTUNITY_EXCHANGE_OLD_CAR,
				OpportunityLookupConfiguration.INOUT_OPPORTUNITY_LEAD,
				OpportunityLookupConfiguration.INOUT_OPPORTUNITY_LEAD_NAME,
				OpportunityLookupConfiguration.INOUT_OPPORTUNITY_CONTACT,
				OpportunityLookupConfiguration.INOUT_OPPORTUNITY_CONTACT_NAME,
				OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ACCOUNT,
				OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ACCOUNT_NAME,
				OpportunityLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN,
				OpportunityLookupConfiguration.INOUT_ASSIGNED_TO,
				OpportunityLookupConfiguration.INOUT_OPPORTUNITY_NEXT_STEP,
				OpportunityLookupConfiguration.INOUT_DATA_SOURCE);
        if (service.isExportToExcel()) {
        	service.modifyServiceResults();
            return json.makeExcelExport(results, columnHeaders, servletContext);
        } else {
            return json.makeResponse(results, entityIdFieldName, service);
        }
    }
    
    private void modifyServiceResults() throws GenericEntityException {
    	List<? extends EntityInterface> results = getResults();	
    	Debug.logInfo("modifyServiceResults >> " + results.get(0), MODULE);
    	for (EntityInterface e : results) {
    		String salesOpportunityId = e.getString(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ID);
    		String opportunityStageId = e.getString(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_STAGE);
    		String userLoginId = e.getString(OpportunityLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN);
            
    		String modelOfInt = getModelOfInt(e.getString(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_MODEL_OF_INTEREST));
            String datasources = getOppDataSourcesAsString(salesOpportunityId);
            String oppStage = getOppSourceStage(opportunityStageId);
            
            String accountPartyId = getPartyId(salesOpportunityId, OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ACCOUNT_TYPE);
            String accountName = getPartyName(accountPartyId);
            
            String leadPartyId = getPartyId(salesOpportunityId, OpportunityLookupConfiguration.INOUT_OPPORTUNITY_LEAD_TYPE);
            String leadName = getPartyName(leadPartyId);
            
            String contactPartyId = getPartyId(salesOpportunityId, OpportunityLookupConfiguration.INOUT_OPPORTUNITY_CONTACT_TYPE);
            String contactName = getPartyName(contactPartyId);
            
            String assignedTo = getOppAssignedTo(salesOpportunityId);
            String ownedBy = getOppOwnerName(userLoginId);
            
            /** VB - below statements do not add new key to the object. 
             *  These merely update values for pre existing keys. 
             *  To add a new key, update the entity SalesOpportunitySearch in entitymodel.xml
             **/
            e.set(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_MODEL_OF_INTEREST, modelOfInt);
            e.set(OpportunityLookupConfiguration.INOUT_DATA_SOURCE, datasources);
            e.set(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_STAGE, oppStage);
            
            e.set(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_LEAD, leadPartyId);
            e.set(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_LEAD_NAME, leadName);
            
            e.set(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_CONTACT, contactPartyId);
            e.set(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_CONTACT_NAME, contactName);
            
            e.set(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ACCOUNT, accountPartyId);
            e.set(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ACCOUNT_NAME, accountName);

            e.set(OpportunityLookupConfiguration.INOUT_ASSIGNED_TO, assignedTo);
            e.set(OpportunityLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN, ownedBy);
        }
    }
    
    private String getModelOfInt(String value) throws GenericEntityException {
    	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
		GenericValue enumEntry = delegator.findByPrimaryKey("Enumeration", UtilMisc.toMap("enumId", value));
		if(enumEntry != null){
			return (String) enumEntry.get("description");
		}
		return "";
    }
    
    private String getOppDataSourcesAsString(String salesOpportunityId) throws GenericEntityException {
    	StringBuffer dataSourcesAsString = new StringBuffer();
    	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
    	List<GenericValue> sources = delegator.findByAnd("SalesOpportunityDataSource", UtilMisc.toMap("salesOpportunityId", salesOpportunityId), UtilMisc.toList("fromDate DESC"));
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
    
    private String getOppSourceStage(String oppStageId) throws GenericEntityException {
    	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
    	GenericValue enumEntry = delegator.findByPrimaryKey("SalesOpportunityStage", UtilMisc.toMap("opportunityStageId", oppStageId.trim()));
		if(enumEntry != null) {
			return (String) enumEntry.get("description");
		}
		return "";
    }
    
    private String getOppType(String oppTypeId) throws GenericEntityException {
    	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
    	GenericValue enumEntry = delegator.findByPrimaryKey("Enumeration", UtilMisc.toMap("enumId", oppTypeId));
		if(enumEntry != null) {
			return (String) enumEntry.get("description");
		}
		return "";
    }
    
    /** VB - Method bifurcated specifically for pinnacle requirements. They wish to use excel export and input the exported data into S-Cube. */
    private String getPartyName(String salesOpportunityId, String partyType) throws GenericEntityException {
    	String partyId = getPartyId(salesOpportunityId, partyType);
		return getPartyName(partyId);
    }
    
    private String getPartyName(String partyId) throws GenericEntityException {
    	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
    	return PartyHelper.getPartyName(delegator, partyId, false);
    }
    
    private String getPartyId(String salesOpportunityId, String partyType) throws GenericEntityException {
    	String partyId = "";
    	List parties = null;
    	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
    	parties = getOpportunityPartiesByRole(delegator, salesOpportunityId, partyType);
		 if (parties != null && parties.size() > 0)
			 partyId = (String)parties.get(0);
		 return partyId;
    }
    
    private List getOpportunityPartiesByRole(GenericDelegator delegator, String salesOpportunityId, String roleTypeId) throws GenericEntityException {
        List maps = delegator.findByAnd("SalesOpportunityRole", UtilMisc.toMap("roleTypeId", roleTypeId, "salesOpportunityId", salesOpportunityId));
        List results = new ArrayList();
        for (Iterator iter = maps.iterator(); iter.hasNext(); ) {
            GenericValue map = (GenericValue) iter.next();
            results.add(map.getString("partyId"));
        }
        return results;
    }
    
    private String getOppAssignedTo(String salesOpportunityId) throws GenericEntityException {
    	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");	
		Map input = UtilMisc.toMap("partyRelationshipTypeId", "RESPONSIBLE_FOR", "salesOpportunityId", salesOpportunityId);            		        
        List relationships = delegator.findByAnd("SalesOpportunityRole", input);
        List activeRelationships = EntityUtil.filterByDate(relationships, UtilDateTime.nowTimestamp());

        // if none are found, log a message about this and return null
        if (activeRelationships.size() == 0) {
            Debug.logInfo("No active Opportunity Relationships found with relationship [RESPONSIBLE_FOR] for opportunity [" + salesOpportunityId + "]", MODULE);
            return null;
        }
        // return the related party with partyId = partyRelationship.partyIdTo
        GenericValue opportunityRelationship = (GenericValue) activeRelationships.get(0);
        GenericValue responsibleParty = delegator.findByPrimaryKey("PartySummaryCRMView", UtilMisc.toMap("partyId", opportunityRelationship.getString("partyId"))); 
		return PartyHelper.getPartyName(responsibleParty, false);
    }
    
    private String getOppOwnerName(String userLoginId) throws GenericEntityException {
    	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");	
    	Map<String, String> fields = new HashMap<String, String>();
    	fields.put("userLoginId", userLoginId);
    	String ownerName = "";
    	try {
    		GenericValue genericValue = delegator.findByPrimaryKey("UserLogin", fields);
    		String ownerPartyId = genericValue.getString("partyId");
    		ownerName = PartyHelper.getPartyName(delegator, ownerPartyId, false);
		} catch (GenericEntityException e) {
			e.printStackTrace();
		}
    	return ownerName;
    }
    
    /**
     * Sets if the lookup methods should filter active parties only, defaults to <code>true</code>.
     * @param bool a <code>boolean</code> value
     */
    public void setActiveOnly(boolean bool) {
        this.activeOnly = bool;
    }

    private <T extends EntityInterface> List<T> findOpportunities(Class<T> entity) {
    	Debug.logInfo("findOpportunities() with entity = " + entity, MODULE);
    	class OpportunityModelOfInterest extends ConvertMapToString implements ICompositeValue {
            @Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_MODEL_OF_INTEREST);
            	String res="";
            	if(s != null){
            		Debug.logInfo("@@@@@@@@@@@@@@@@@@@@@@@@@@@INOUT_OPPORTUNITY_MODEL_OF_INTEREST=> " + s, MODULE);
	            	try {
	            		res = getModelOfInt(s);
					} catch (GenericEntityException e) {
						e.printStackTrace();
					}
            	}
            	return res;
            }

            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_MODEL_OF_INTEREST);                
                return s;
            }
        }
        Map<String, ConvertMapToString> calcModelOfInterestField = FastMap.newInstance();
        calcModelOfInterestField.put(OpportunityLookupConfiguration.OUT_OPPORTUNITY_MODEL_OF_INTEREST_DESC, new OpportunityModelOfInterest());
        makeCalculatedField(calcModelOfInterestField);
        
       class OpportunityDataSource extends ConvertMapToString implements ICompositeValue {
            @Override
            public String convert(Map<String, ?> value) {
            	String salesOpportunityid = (String)value.get(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ID);
            	try {
            		return getOppDataSourcesAsString(salesOpportunityid);
				} catch (GenericEntityException e) {
					e.printStackTrace();
				}
				return "";
            }
            
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(OpportunityLookupConfiguration.INOUT_DATA_SOURCE);                
                return s;
            }
        }
        Map<String, ConvertMapToString> calcDataSourceField = FastMap.newInstance();
        calcDataSourceField.put(OpportunityLookupConfiguration.INOUT_DATA_SOURCE_DESC, new OpportunityDataSource());
        makeCalculatedField(calcDataSourceField);

       class OpportunityStage extends ConvertMapToString implements ICompositeValue {
            @Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_STAGE);;
            	String res="";
            	if(s != null){
	            	try {
	            		res = getOppSourceStage(s);
					} catch (GenericEntityException e) {
						e.printStackTrace();
					}
            	}
            	return res;
            }

            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_STAGE);                
                return s;
            }
        }
        Map<String, ConvertMapToString> calcStageField = FastMap.newInstance();
        calcStageField.put(OpportunityLookupConfiguration.OUT_OPPORTUNITY_STAGE_DESCRIPTION, new OpportunityStage());
        makeCalculatedField(calcStageField);
        
        class OpportunityType extends ConvertMapToString implements ICompositeValue {
            @Override
            public String convert(Map<String, ?> value) {
            	String s= null;
            	String res="";
            	if(value.get(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_TYPE)!=null){
            		 s = (String) value.get(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_TYPE);
	            	try {
	            		res = getOppType(s);
					} catch (GenericEntityException e) {
						e.printStackTrace();
					}
            	}
            	return res;
            }

            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_TYPE);                
                return s;
            }
        }
        Map<String, ConvertMapToString> calcTypeField = FastMap.newInstance();
        calcTypeField.put(OpportunityLookupConfiguration.OUT_OPPORTUNITY_TYPE_DESC, new OpportunityType());
        makeCalculatedField(calcTypeField);
        
        class OpportunityAccountName extends ConvertMapToString implements ICompositeValue {
			@Override
            public String convert(Map<String, ?> value) {
            	String salesOpportunityId = (String) value.get(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ID);
            	String accountName = "";
            	if(salesOpportunityId !=null && !salesOpportunityId.equals("")) {
            		 try {
            			 accountName = getPartyName(salesOpportunityId, OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ACCOUNT_TYPE);
					} catch (GenericEntityException e) {
						e.printStackTrace();
					}
					return accountName;
					
				}else{
	            	return "";
	            }
            }
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(OpportunityLookupConfiguration.INOUT_PARTY_ID);                
                return s;
            }
        }
        Map<String, ConvertMapToString> calcOppAccountNameField = FastMap.newInstance();
        calcOppAccountNameField.put(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ACCOUNT_TYPE, new OpportunityAccountName());
        makeCalculatedField(calcOppAccountNameField);
        
        class OpportunityLeadName extends ConvertMapToString implements ICompositeValue {
			@Override
            public String convert(Map<String, ?> value) {
            	String salesOpportunityId = (String) value.get(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ID);
            	String leadName = "";
            	if(salesOpportunityId !=null && !salesOpportunityId.equals("")) {
            		 try {
            			 leadName = getPartyName(salesOpportunityId, OpportunityLookupConfiguration.INOUT_OPPORTUNITY_LEAD_TYPE);
					} catch (GenericEntityException e) {
						e.printStackTrace();
					}
	            }
				return leadName;	
            }

            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(OpportunityLookupConfiguration.INOUT_PARTY_ID);                
                return s;
            }
        }
        Map<String, ConvertMapToString> calcOppLeadNameField = FastMap.newInstance();
        calcOppLeadNameField.put(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_LEAD_TYPE, new OpportunityLeadName());
        makeCalculatedField(calcOppLeadNameField);
        
        class OpportunityContactName extends ConvertMapToString implements ICompositeValue {
			@Override
            public String convert(Map<String, ?> value) {
            	String salesOpportunityId = (String) value.get(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ID);
            	String contactName = "";
            	if(salesOpportunityId !=null && !salesOpportunityId.equals("")) {
            		 try {
            			 contactName = getPartyName(salesOpportunityId, OpportunityLookupConfiguration.INOUT_OPPORTUNITY_CONTACT_TYPE);
					} catch (GenericEntityException e) {
						e.printStackTrace();
					}
	            }
				return contactName;
            }
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(OpportunityLookupConfiguration.INOUT_PARTY_ID);                
                return s;
            }
        }
        Map<String, ConvertMapToString> calcOppContactNameField = FastMap.newInstance();
        calcOppContactNameField.put(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_CONTACT_TYPE, new OpportunityContactName());
        makeCalculatedField(calcOppContactNameField);
        
        class OpportunityOwnerName extends ConvertMapToString implements ICompositeValue {
			@Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(OpportunityLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN);
            	String result = "";
            	if(s != null && !s.equals("")) {
            		try {
            			result = getOppOwnerName(s);
            		} catch (GenericEntityException e) {
            			e.printStackTrace();
            		}
            	}
            	return result;
            } 	
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(OpportunityLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN);                
                return s;
            }
        }
        Map<String, ConvertMapToString> calcOppOwnerNameField = FastMap.newInstance();
        calcOppOwnerNameField.put(OpportunityLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN_DESC, new OpportunityOwnerName());
        makeCalculatedField(calcOppOwnerNameField);   
      
        class OpportunityAssignedTo extends ConvertMapToString implements ICompositeValue {
			@Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ID);
       		 	String partyName="";
            	if(s != null && !s.equals("")) {
            		 try {
            			partyName = getOppAssignedTo(s);
					} catch (GenericEntityException e) {
						e.printStackTrace();
					}
				}
            	return partyName;
			}
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ID);                
                return s;
            }
        }
        Map<String, ConvertMapToString> calcOppAssignedToField = FastMap.newInstance();
        calcOppAssignedToField.put(OpportunityLookupConfiguration.INOUT_ASSIGNED_TO_DESC, new OpportunityAssignedTo());
        makeCalculatedField(calcOppAssignedToField);
        
        class OpportunityEstimatedCloseDate extends ConvertMapToString implements ICompositeValue {

            @Override
            public String convert(Map<String, ?> value) {            	
            	return value.get(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ESTIMATED_CLOSE_DATE).toString();
            }

            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ESTIMATED_CLOSE_DATE);                
                return s;
            }
        }
        Map<String, ConvertMapToString> calcECDField = FastMap.newInstance();
        calcECDField.put(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ESTIMATED_CLOSE_DATE, new OpportunityEstimatedCloseDate());
        makeCalculatedField(calcECDField);
        
        class OpportunityCreationDate extends ConvertMapToString implements ICompositeValue {
            @Override
            public String convert(Map<String, ?> value) {            	
            	return value.get(OpportunityLookupConfiguration.OUT_OPPORTUNITY_CREATED_STAMP).toString();
            }

            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(OpportunityLookupConfiguration.OUT_OPPORTUNITY_CREATED_STAMP);                
                return s;
            }
        }
        Map<String, ConvertMapToString> opportunityCreationDate = FastMap.newInstance();
        opportunityCreationDate.put(OpportunityLookupConfiguration.OUT_OPPORTUNITY_CREATED_DATE, new OpportunityCreationDate());
        makeCalculatedField(opportunityCreationDate);
        
        class OpportunityExchangeOldCar extends ConvertMapToString implements ICompositeValue {
            @Override
            public String convert(Map<String, ?> value) {
            	return value.get(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_EXCHANGE_OLD_CAR).toString();
            }

            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_EXCHANGE_OLD_CAR);                
                return s;
            }
        }
        Map<String, ConvertMapToString> opportunityExchangeOldCar = FastMap.newInstance();
        opportunityExchangeOldCar.put(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_EXCHANGE_OLD_CAR, new OpportunityExchangeOldCar());
        makeCalculatedField(opportunityExchangeOldCar);
        
        EntityCondition condition = null;
        
        
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
                                    new EntityExpr("partyRelationshipTypeId", EntityOperator.IN, Arrays.asList("RESPONSIBLE_FOR")),
                                    new EntityExpr("roleTypeId", EntityOperator.IN, Arrays.asList("ACCOUNT", "PROSPECT"))
                            ),
                            EntityOperator.AND
                    );
                } else if (PartyLookupConfiguration.TEAM_VALUES.equals(viewPref)) {
                	 // my teams parties
                    condition = new EntityConditionList(
                            Arrays.asList(
                                    condition,
                                    new EntityExpr("partyIdTo", EntityOperator.EQUALS, userId),
                                    new EntityExpr("partyRelationshipTypeId", EntityOperator.IN, Arrays.asList("RESPONSIBLE_FOR", "ASSIGNED_TO")),
                                    new EntityExpr("roleTypeId", EntityOperator.IN, Arrays.asList("ACCOUNT", "PROSPECT"))
                            ),
                            EntityOperator.AND
                    );
                }
            } else {
                Debug.logError("Current session do not have any UserLogin set.", MODULE);
            }
        }
        
        if (getProvider().oneParameterIsPresent(BY_ADVANCED_FILTERS)) {
        	Debug.logInfo("Searching >> BY_ADVANCED_FILTERS", MODULE);
            return findOpportunitiesBy(entity, condition, BY_ADVANCED_FILTERS);
        } else if (getProvider().oneParameterIsPresent(BY_BASIC_FILTERS)) {
        	Debug.logInfo("Searching >> BY_BASIC_FILTERS", MODULE);
            return findOpportunitiesBy(entity, condition, BY_BASIC_FILTERS);
        }
        Debug.logInfo("The Conditions >> " + condition, MODULE);
        return findAllOpportunities(entity, condition);
    }

    private <T extends EntityInterface> List<T> findAllOpportunities(Class<T> entity, EntityCondition roleCondition) {
    	List<EntityCondition> conditions = new ArrayList<EntityCondition>();
    	if(roleCondition!=null){
    		conditions.add(roleCondition);
    	}
        Debug.logInfo("findAllOpportunities() cond >> " + conditions, MODULE);
        return findList(entity, new EntityConditionList(conditions, EntityOperator.AND));
    }

    @SuppressWarnings("unchecked")
	private <T extends EntityInterface> List<T> findOpportunitiesBy(Class<T> entity, EntityCondition roleCondition, List<String> filters) {
    	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
        List<EntityCondition> conds = new ArrayList<EntityCondition>();
        if(roleCondition!=null){
        	conds.add(roleCondition);
        }
        String loggedInUserId = getProvider().getUser().getOfbizUserLogin().getString("partyId");
        List<String> finalFilters = new ArrayList<String>(); 
        String filterValue = "";
        boolean assignedToFlag = false; /** This keeps track of filters on individual OR team. */ 
        
        for (String filter : filters) {
        	Debug.logInfo("Input Filter >> " + filter, MODULE);
            if (getProvider().parameterIsPresent(filter)) {
            	filterValue = getProvider().getParameter(filter);
            	Debug.logInfo(" Value >> " + filterValue, MODULE);
            	if(filter.equals(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ACCOUNT)) {
            		conds.add(new EntityExpr("sor2PartyId", EntityOperator.EQUALS, filterValue));
            		conds.add(new EntityExpr("roleTypeId", EntityOperator.EQUALS, OpportunityLookupConfiguration.INOUT_OPPORTUNITY_ACCOUNT_TYPE));
            	} else if(filter.equals(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_CONTACT)) {
            		conds.add(new EntityExpr("sor2PartyId", EntityOperator.EQUALS, filterValue));
            		conds.add(new EntityExpr("roleTypeId", EntityOperator.EQUALS, OpportunityLookupConfiguration.INOUT_OPPORTUNITY_CONTACT_TYPE));
            	} else if(filter.equals(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_LEAD)) {
            		conds.add(new EntityExpr("sor2PartyId", EntityOperator.EQUALS, filterValue));
            		conds.add(new EntityExpr("roleTypeId", EntityOperator.EQUALS, OpportunityLookupConfiguration.INOUT_OPPORTUNITY_LEAD_TYPE));
            	} else if(filter.equals(OpportunityLookupConfiguration.IN_OPPORTUNITY_ESTIMATED_CLOSE_DATE_FROM)) {    
            		String estimatedCloseDate = filterValue;
                    String date = estimatedCloseDate.substring(0, estimatedCloseDate.lastIndexOf(":") + 3);
        	        DateFormat myDateFormat = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss");
        	        Date estCloseDate = new Date();
        	        try {
        	        	estCloseDate = myDateFormat.parse(date);
        	        } catch (ParseException e) {
        	             Debug.logInfo("Invalid Date Parser Exception ", MODULE);
        	             e.printStackTrace();
        	        }
            	    myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                	conds.add(new EntityExpr("estimatedCloseDate", true, EntityOperator.GREATER_THAN_EQUAL_TO, myDateFormat.format(estCloseDate) + "%", false));
            	} else if(filter.equals(OpportunityLookupConfiguration.IN_OPPORTUNITY_ESTIMATED_CLOSE_DATE_TO)) {            
            		String estimatedCloseDate = filterValue;
                    String date = estimatedCloseDate.substring(0, estimatedCloseDate.lastIndexOf(":") + 3);
        	        DateFormat myDateFormat = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss");
        	        Date estCloseDate = new Date();
        	        try {
        	        	estCloseDate = myDateFormat.parse(date);
        	        } catch (ParseException e) {
        	             Debug.logInfo("Invalid Date Parser Exception ", MODULE);
        	             e.printStackTrace();
        	        }
        	        myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                	conds.add(new EntityExpr("estimatedCloseDate", true, EntityOperator.LESS_THAN_EQUAL_TO, myDateFormat.format(estCloseDate) + "%", false));
            	} else if(filter.equals(OpportunityLookupConfiguration.IN_OPPORTUNITY_CREATED_DATE_FROM)) {    
            		String pinkCardCreatedDateFrom = filterValue;
                    String date = pinkCardCreatedDateFrom.substring(0, pinkCardCreatedDateFrom.lastIndexOf(":") + 3);
        	        DateFormat myDateFormat = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss");
        	        Date cardCreationDate = new Date();
        	        try {
        	        	cardCreationDate = myDateFormat.parse(date);
        	        } catch (ParseException e) {
        	             Debug.logInfo("Invalid Date Parser Exception ", MODULE);
        	             e.printStackTrace();
        	        }
            	    myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                	conds.add(new EntityExpr("createdStamp", true, EntityOperator.GREATER_THAN_EQUAL_TO, myDateFormat.format(cardCreationDate) + "%", false));
            	} else if(filter.equals(OpportunityLookupConfiguration.IN_OPPORTUNITY_CREATED_DATE_TO)) {            
            		String pinkCardCreatedDateTo = filterValue;
                    String date = pinkCardCreatedDateTo.substring(0, pinkCardCreatedDateTo.lastIndexOf(":") + 3);
        	        DateFormat myDateFormat = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss");
        	        Date cardCreationDate = new Date();
        	        try {
        	        	cardCreationDate = myDateFormat.parse(date);
        	        } catch (ParseException e) {
        	             Debug.logInfo("Invalid Date Parser Exception ", MODULE);
        	             e.printStackTrace();
        	        }
        	        myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                	conds.add(new EntityExpr("createdStamp", true, EntityOperator.LESS_THAN_EQUAL_TO, myDateFormat.format(cardCreationDate) + "%", false));
            	} else if(filter.equals(OpportunityLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN)) {
            		String partyId = filterValue;
            		try {
						List<GenericValue> list = delegator.findByAnd("UserLogin", UtilMisc.toMap("partyId", partyId));
						if(list != null && list.size() > 0) {
		            		GenericValue value = (GenericValue)list.get(0);
							conds.add(new EntityExpr(filter, false, EntityOperator.LIKE, value.getString("userLoginId") + "%", false));
						}
					} catch (GenericEntityException e) {
						e.printStackTrace();
					}
				} else if(filter.equals(OpportunityLookupConfiguration.INOUT_ASSIGNED_TO)) {
					assignedToFlag = true;
            		conds.add(new EntityExpr("sor1PartyId", false, EntityOperator.EQUALS, filterValue, false));
            	} else if(filter.equals(OpportunityLookupConfiguration.INOUT_ASSIGNED_TEAM_PARTY_ID)) {
        			assignedToFlag = true;
            		try {
//            			* this is commented as its correct to fetch data of all team members, better logic is implemented in activity lookup service
//            			Collection<String> teamMembers = getTeamMembersForPartyId(loggedInUserId, delegator);
            			List<String>teamMemberPartyIds = getActiveTeamMates(filterValue);
                		if(teamMemberPartyIds != null){
//                			List<String> teamMemberPartyIds = Arrays.asList(teamMembers.toArray(new String[teamMembers.size()]));
                			conds.add(new EntityExpr("sor1PartyId", false, EntityOperator.IN, teamMemberPartyIds, false));
                		}
            		} catch (GenericEntityException e) {
            			e.printStackTrace();
            		}
            	} else if (filter.equals(OpportunityLookupConfiguration.INOUT_DATA_SOURCE)) {
            		try {
            			List<GenericValue> salesOpportunityIds = delegator.findByAnd("SalesOpportunityDataSource", UtilMisc.toMap("dataSourceId", filterValue));
            			List<String> salesOpportunityIdsAsString = new ArrayList<String>();
            			for(GenericValue salesOpportunityId : salesOpportunityIds) {
            				salesOpportunityIdsAsString.add(salesOpportunityId.getString("salesOpportunityId"));
            			}
            			conds.add(new EntityExpr("salesOpportunityId", EntityOperator.IN, salesOpportunityIdsAsString));
            		} catch(GenericEntityException e) {
            			e.printStackTrace();
            		}
            	} else if (filter.equals(OpportunityLookupConfiguration.INOUT_OPPORTUNITY_EXCHANGE_OLD_CAR)) {
            		try {
            			List<GenericValue> enumerations = delegator.findByAnd("Enumeration", UtilMisc.toMap("ENUM_ID", filterValue));
            			String enumerationAsString = "";
            			/** VB - this loop will never execute more than once. */
            			for(GenericValue enumeration : enumerations) {
            				enumerationAsString = enumeration.getString("enumCode");
            			}
            			conds.add(new EntityExpr("exchangeOldCar", EntityOperator.LIKE, enumerationAsString));
            		} catch(GenericEntityException e) {
            			e.printStackTrace();
            		}
            	} else {            
            		finalFilters.add(filter);
            	}
            } else {
            	Debug.logInfo(" not found !", MODULE);
            }
        }
        
        if (assignedToFlag) {
    		conds.add(new EntityExpr("sor1ThruDate", EntityOperator.EQUALS, null));
        }
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

    public static Collection<String> getTeamMembersForPartyId(String partyId, GenericDelegator delegator) throws GenericEntityException {
        Collection teamPartyIds = getTeamsForPartyId(partyId, delegator);
        if (teamPartyIds.size() == 0) return teamPartyIds;

        List relationships = getActiveTeamMembers(teamPartyIds, delegator);
        Set<String> partyIds = new HashSet<String>();
        for (Iterator iter = relationships.iterator(); iter.hasNext(); ) {
            GenericValue relationship = (GenericValue) iter.next();
            partyIds.add(relationship.getString("partyId"));
        }
        return partyIds;
    }
    
//    finds team members for the team id
    private List<String> getActiveTeamMates(String teamPartyId) throws GenericEntityException {
    	Collection<String> teamPartyIds = new ArrayList<String>();
    	teamPartyIds.add(teamPartyId);
    	GenericDelegator delegator = getProvider().getInfrastructure().getDelegator();
    	List<GenericValue> relationships = getActiveTeamMembers(teamPartyIds, delegator);
        List<String> partyIds = new ArrayList<String>();
        for (Iterator iter = relationships.iterator(); iter.hasNext(); ) {
            GenericValue relationship = (GenericValue) iter.next();
            partyIds.add(relationship.getString("partyId"));
        }
        return partyIds;
    	
    } 
}

