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
import org.opentaps.domain.base.entities.WorkEffortAndPartyAssign;
import org.opentaps.foundation.entity.EntityInterface;
import org.opentaps.foundation.infrastructure.InfrastructureException;
import org.opentaps.foundation.repository.RepositoryException;
import org.opentaps.gwt.common.client.lookup.configuration.ActivityLookupConfiguration;
import org.opentaps.gwt.common.client.lookup.configuration.OpportunityLookupConfiguration;
import org.opentaps.gwt.common.client.lookup.configuration.PartyLookupConfiguration;
import org.opentaps.gwt.common.server.HttpInputProvider;
import org.opentaps.gwt.common.server.InputProviderInterface;

/**
 * The RPC service used to populate the ActivityListView.
 */
public class ActivityLookupService extends EntityLookupService {

    private static final String MODULE = ActivityLookupService.class.getName();

    private static List<String> BY_BASIC_FILTERS = Arrays.asList(ActivityLookupConfiguration.INOUT_WORKEFFORT_NAME,
    																ActivityLookupConfiguration.INOUT_STATUS_ID	);
    
    private static List<String> BY_ADVANCED_FILTERS = Arrays.asList(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID,
    																	ActivityLookupConfiguration.INOUT_WORKEFFORT_PURPOSE_TYPE_ID,
    																	ActivityLookupConfiguration.INOUT_WORKEFFORT_TYPE_ID,
    																	ActivityLookupConfiguration.INOUT_CURRENT_STATUS_ID,
    																	ActivityLookupConfiguration.INOUT_STATUS_ID,
    																	ActivityLookupConfiguration.INOUT_PARTY_ID,
    																	ActivityLookupConfiguration.INOUT_LEAD_PARTY_ID,
    																	ActivityLookupConfiguration.INOUT_ACCOUNT_PARTY_ID,
    																	ActivityLookupConfiguration.INOUT_CONTACT_PARTY_ID,
    																	ActivityLookupConfiguration.INOUT_WORKEFFORT_NAME,
    																	ActivityLookupConfiguration.INOUT_ESTIMATED_START_DATE,
    																	ActivityLookupConfiguration.INOUT_ESTIMATED_COMPLETION_DATE,
    																	ActivityLookupConfiguration.INOUT_ACTUAL_START_DATE,
    																	ActivityLookupConfiguration.INOUT_ACTUAL_COMPLETION_DATE,
    																	ActivityLookupConfiguration.IN_ESTIMATED_START_DATE_FROM,
    																	ActivityLookupConfiguration.IN_ESTIMATED_START_DATE_TO,
    																	ActivityLookupConfiguration.IN_ESTIMATED_COMPLETION_DATE_FROM,
    																	ActivityLookupConfiguration.IN_ESTIMATED_COMPLETION_DATE_TO,
    																	ActivityLookupConfiguration.IN_ACTUAL_START_DATE_FROM,
    																	ActivityLookupConfiguration.IN_ACTUAL_START_DATE_TO,
    																	ActivityLookupConfiguration.IN_ACTUAL_COMPLETION_DATE_FROM,
    																	ActivityLookupConfiguration.IN_ACTUAL_COMPLETION_DATE_TO,
    																	ActivityLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN,
    																	ActivityLookupConfiguration.INOUT_ASSIGNED_TO,
    																	ActivityLookupConfiguration.INOUT_ASSIGNED_TEAM_PARTY_ID);

    private boolean activeOnly = false;

    /**
     * Creates a new <code>ActivityLookupService</code> instance.
     * @param provider an <code>InputProviderInterface</code> value
     */
    public ActivityLookupService(InputProviderInterface provider) {
        super(provider, ActivityLookupConfiguration.LIST_OUT_FIELDS);
    }
    
    /**
     * AJAX event to perform lookups on Accounts.
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return the resulting JSON response
     * @throws InfrastructureException if an error occurs
     * @throws RepositoryException 
     * @throws GenericEntityException 
     */
    public static String findActivities(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException, RepositoryException, GenericEntityException {
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        ActivityLookupService service = new ActivityLookupService(provider);
        service.findActivities(WorkEffortAndPartyAssign.class);
        
//        List l = service.getResults();
//        List l2 = new ArrayList();
//        
//        for (int i = 0; i < l.size(); i++) {
//        	WorkEffortAndPartyAssign entity = (WorkEffortAndPartyAssign)l.get(i);        	
//        	Debug.logInfo("11111111111111 = "+entity.getCurrentStatusItem().getDescription());
//        	break;
//		}
        
        
        
        //service.getResults().add()
        // Debug.logInfo("########################"+service.makeCalculatedField(new HashMap<String, ConvertMapToString>().put("",ConvertMapToString)));
        return makeLookupResponse(json, ActivityLookupConfiguration.INOUT_WORKEFFORT_ID, service, request.getSession(true).getServletContext());
    }
    
    private String getPartyCar(String workEffortId) throws GenericEntityException {
    	return getAssignedType(workEffortId, "CAR");
    }
    
    private String getPartyDriver(String workEffortId) throws GenericEntityException {
    	return getAssignedType(workEffortId, "DRIVER");
    }
    
    private String getLead(String workEffortId) throws GenericEntityException {
    	return getAssignedType(workEffortId, "PROSPECT");
    }
    
    private String getContact(String workEffortId) throws GenericEntityException {
    	return getAssignedType(workEffortId, "CONTACT");
    }
    
    private String getAccount(String workEffortId) throws GenericEntityException {
    	return getAssignedType(workEffortId, "ACCOUNT");
    }
    
    private String getAssignedType(String workEffortId, String roleType) throws GenericEntityException {
    	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");	
    	String partyId = "";
    	Map<String, String> m = new HashMap<String, String>();
    	m.put("workEffortId", workEffortId);
    	m.put("roleTypeId", roleType);
		List<GenericValue> list = delegator.findByAnd("WorkEffortPartyAssignment", m);
		if (list.size() > 0) {
			for(int i=0; i < list.size(); i++) {
				GenericValue gv = (GenericValue)list.get(i);
				partyId = gv.getString("partyId");
			}
		}
		if(partyId.equals("")){
			return "";
		}else{
			return PartyHelper.getPartyName(delegator, partyId, false)+"("+partyId+")";
		}
    }
    
    private String getPurpose(String workEffortPurposeTypeId) throws GenericEntityException {
    	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
    	return (String) delegator.findByPrimaryKey("WorkEffortPurposeType", UtilMisc.toMap("workEffortPurposeTypeId", workEffortPurposeTypeId)).get("description");
    }
    
    private static String makeLookupResponse(JsonResponse json, String entityIdFieldName, ActivityLookupService service, ServletContext servletContext) throws GenericEntityException {
        List<? extends EntityInterface> results = service.getResults();
        if (results == null) {
            return json.makeErrorResponse(service.getLastException());
        }
        List<String> columnHeaders = new ArrayList<String>();
        columnHeaders.addAll(ActivityLookupConfiguration.LIST_OUT_FIELDS);
        columnHeaders.add(ActivityLookupConfiguration.INOUT_CAR_FULL_NAME);
        columnHeaders.add(ActivityLookupConfiguration.INOUT_DRIVER_FULL_NAME);
        columnHeaders.add(ActivityLookupConfiguration.INOUT_LEAD_FULL_NAME);
        columnHeaders.add(ActivityLookupConfiguration.INOUT_CONTACT_FULL_NAME);
        columnHeaders.add(ActivityLookupConfiguration.INOUT_ACCOUNT_FULL_NAME);
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
    		String workEffortId = e.getString(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID);
            String partyCarName = getPartyCar(workEffortId);
            String partyDriverName = getPartyDriver(workEffortId);
            String partyLeadName = getLead(workEffortId);
            String partyContactName = getContact(workEffortId);
            String partyAccountName = getAccount(workEffortId);

            /** VB - below statements do not add new key to the object. 
             *  These merely update values for pre existing keys. 
             *  To add a new key, update the entity in entitymodel.xml
             **/
            e.set(ActivityLookupConfiguration.INOUT_CAR_FULL_NAME, partyCarName);
            e.set(ActivityLookupConfiguration.INOUT_DRIVER_FULL_NAME, partyDriverName);
            e.set(ActivityLookupConfiguration.INOUT_LEAD_FULL_NAME, partyLeadName);
            e.set(ActivityLookupConfiguration.INOUT_CONTACT_FULL_NAME, partyContactName);
            e.set(ActivityLookupConfiguration.INOUT_ACCOUNT_FULL_NAME, partyAccountName);
        }
    }
    
    /**
     * AJAX event to perform lookups on Accounts.
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return the resulting JSON response
     * @throws InfrastructureException if an error occurs
     */
    public static String createEvent(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException {
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        PartyLookupService service = new PartyLookupService(provider);
        //service.findLeads();
        return json.makeLookupResponse(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID, service, request.getSession(true).getServletContext());
    }
    
    /**
     * Sets if the lookup methods should filter active parties only, defaults to <code>true</code>.
     * @param bool a <code>boolean</code> value
     */
    public void setActiveOnly(boolean bool) {
        this.activeOnly = bool;
    }

    private <T extends EntityInterface> List<T> findActivities(Class<T> entity) {
    	// add rule that causes formated primary phone to be added to result
        class ActivityStatus extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(ActivityLookupConfiguration.INOUT_CURRENT_STATUS_ID);
            	
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
                s.add(ActivityLookupConfiguration.INOUT_CURRENT_STATUS_ID);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcField = FastMap.newInstance();
        calcField.put("activityStatus", new ActivityStatus());
        makeCalculatedField(calcField);
        
        
        class ActivityPurpose extends ConvertMapToString implements ICompositeValue {
            @Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(ActivityLookupConfiguration.INOUT_WORKEFFORT_PURPOSE_TYPE_ID);
            	String result = "";
	        	try {
	        		result = getPurpose(s);
				} catch (GenericEntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	return result;
            }

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(ActivityLookupConfiguration.INOUT_WORKEFFORT_PURPOSE_TYPE_ID);                
                return s;
            }
        }
        Map<String, ConvertMapToString> calcFieldPurpose = FastMap.newInstance();
        calcFieldPurpose.put("description", new ActivityPurpose());
        makeCalculatedField(calcFieldPurpose);  
        
        
//================================ Owner Name==========================================
        
        class ActivityCreatedByName extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @SuppressWarnings("unchecked")
			@Override
            public String convert(Map<String, ?> value) {
            	String s= "";
            	if(value.get(ActivityLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN)!=null){
            		 s = (String) value.get(ActivityLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN);
            	}
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
            	
            }
            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(ActivityLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcOppOwnerNameField = FastMap.newInstance();
        calcOppOwnerNameField.put(ActivityLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN_DESC, new ActivityCreatedByName());
        makeCalculatedField(calcOppOwnerNameField);   
             
        
        //================================ AssignedTo==========================================
        
        class ActivityAssignedTo extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @SuppressWarnings("unchecked")
			@Override
            public String convert(Map<String, ?> value) {
            	
            	if(value.get(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID)!=null && !(value.get(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID)).equals("")){
            		 String s= "";
            		 String partyName="";
            		 s = (String) value.get(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID);
            		 Debug.logInfo("+++++++++++++++++++++++++++++++++++++++++++++s=> "+s, MODULE);
            		 GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");	
            		 
            		 try {
            			 List partyRoles = UtilMisc.toList("CAL_OWNER");
            			// add each role type id (ACCOUNT, CONTACT, etc) to an OR condition list
            		        List roleCondList = new ArrayList();
            		        for (Iterator iter = partyRoles.iterator(); iter.hasNext(); ) {
            		            String roleTypeId = (String) iter.next();
            		            roleCondList.add(new EntityExpr("roleTypeId", EntityOperator.EQUALS, roleTypeId));
            		        }
            		        EntityConditionList roleEntityCondList = new EntityConditionList(roleCondList, EntityOperator.OR);

            		        // roleEntityCondList AND workEffortId = ${workEffortId} AND filterByDateExpr
            		        EntityConditionList mainCondList = new EntityConditionList(UtilMisc.toList(
            		                    roleEntityCondList,
            		                    new EntityExpr("workEffortId", EntityOperator.EQUALS, s), 
            		                    EntityUtil.getFilterByDateExpr()
            		                    ), EntityOperator.AND);

            		        List parties = delegator.findByCondition("WorkEffortPartyAssignment", mainCondList, null,
            		                null,
            		                null, // fields to order by (unimportant here)
            		                new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true));
//            		        List parties = partiesIt.getCompleteList();
//            		        partiesIt.close();
            			 
            		        List<GenericValue> ownerParties = EntityUtil.filterByDate(parties);
            		        if (UtilValidate.isEmpty(ownerParties)) {
            		            Debug.logInfo("No owner parties found for activity [" + s + "]", MODULE);
            		            return null;
            		        } else if (ownerParties.size() > 1) {
            		            Debug.logInfo("More than one owner party found for activity [" + s + "].  Only the first party will be returned, but the parties are " + EntityUtil.getFieldListFromEntityList(ownerParties, "partyId", false), MODULE);
            		        }

            		        GenericValue responsibleParty =  EntityUtil.getFirst(ownerParties);
            			 
						partyName = PartyHelper.getPartyName(responsibleParty, false);
						return partyName;
					} catch (GenericEntityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return "";
					}
				}else{
					return "";
				}
			}
            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcOppAssignedToField = FastMap.newInstance();
        calcOppAssignedToField.put(OpportunityLookupConfiguration.INOUT_ASSIGNED_TO_DESC, new ActivityAssignedTo());
        makeCalculatedField(calcOppAssignedToField);
        
        
//================================ Party Name==========================================
        
        class ActivityPartyName extends ConvertMapToString implements ICompositeValue {

            @Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(ActivityLookupConfiguration.INOUT_PARTY_ID);            	
            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");	
            	return PartyHelper.getPartyName(delegator, s, false)+"("+s+")";
            }

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(ActivityLookupConfiguration.INOUT_PARTY_ID);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcOppPartyNameField = FastMap.newInstance();
        calcOppPartyNameField.put(ActivityLookupConfiguration.INOUT_PARTY_FULL_NAME, new ActivityPartyName());
        makeCalculatedField(calcOppPartyNameField);
        
       
//================================ Car Name==========================================
        
        class ActivityPartyCar extends ConvertMapToString implements ICompositeValue {

            @SuppressWarnings("unchecked")
			@Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID);
            	String result = "";
            	try {
            		result = getPartyCar(s);
				} catch (GenericEntityException e) {
					e.printStackTrace();
				}
				return result; 
			}
            
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcOppActivityPartyCar = FastMap.newInstance();
        calcOppActivityPartyCar.put(ActivityLookupConfiguration.INOUT_CAR_FULL_NAME, new ActivityPartyCar());
        makeCalculatedField(calcOppActivityPartyCar);
        
    
//================================ Driver Name==========================================
        
        class ActivityPartyDriver extends ConvertMapToString implements ICompositeValue {

			@Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID);
            	String result = "";
            	try {
            		result = getPartyDriver(s);
				} catch (GenericEntityException e) {
					e.printStackTrace();
				}
				return result;
            }

            
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcOppActivityPartyDriver = FastMap.newInstance();
        calcOppActivityPartyDriver.put(ActivityLookupConfiguration.INOUT_DRIVER_FULL_NAME, new ActivityPartyDriver());
        makeCalculatedField(calcOppActivityPartyDriver);
        
      
//================================ Internal Party Name==========================================
        
        class ActivityPartyInternal extends ConvertMapToString implements ICompositeValue {

            @SuppressWarnings("unchecked")
			@Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID);            	
            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");	
            	String partyId = "";
            	Map m = new HashMap();
            	List list = new ArrayList();
            	m.put("workEffortId", s);
            	m.put("roleTypeId", "CAL_OWNER");
            	try {
					list = delegator.findByAnd("WorkEffortPartyAssignment", m);
					if(list.size()>0){
						for(int i=0;i<list.size();i++){
							GenericValue gv = (GenericValue)list.get(i);
							partyId = gv.getString("partyId");
							//return PartyHelper.getPartyName(delegator, partyId, false)+"("+partyId+")";
						}
					}
				} catch (GenericEntityException e) {
					e.printStackTrace();
				}
				if(partyId.equals("")){
					return "";
				}else{
					return PartyHelper.getPartyName(delegator, partyId, false)+"("+partyId+")";
				}
            }

            
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcOppActivityPartyInternal = FastMap.newInstance();
        calcOppActivityPartyInternal.put(ActivityLookupConfiguration.INOUT_INTERNAL_PARTY_FULL_NAME, new ActivityPartyInternal());
        makeCalculatedField(calcOppActivityPartyInternal);    
        
  
//================================ Lead Party Name==========================================
        
        class ActivityPartyLead extends ConvertMapToString implements ICompositeValue {

            @SuppressWarnings("unchecked")
			@Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID);
            	String result = "";
            	try {
            		result = getLead(s);
				} catch (GenericEntityException e) {
					e.printStackTrace();
				}
				return result;
            }

            
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcOppActivityPartyLead = FastMap.newInstance();
        calcOppActivityPartyLead.put(ActivityLookupConfiguration.INOUT_LEAD_FULL_NAME, new ActivityPartyLead());
        makeCalculatedField(calcOppActivityPartyLead);    
        
        
//================================ Contact Party Name==========================================
        
        class ActivityPartyContact extends ConvertMapToString implements ICompositeValue {

            @SuppressWarnings("unchecked")
			@Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID);            	
            	String result = "";
            	try {
            		result = getContact(s);
				} catch (GenericEntityException e) {
					e.printStackTrace();
				}
				return result;
            }

            
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcOppActivityPartyContact = FastMap.newInstance();
        calcOppActivityPartyContact.put(ActivityLookupConfiguration.INOUT_CONTACT_FULL_NAME, new ActivityPartyContact());
        makeCalculatedField(calcOppActivityPartyContact);    
        
     
//================================ Contact Party Name==========================================
        
        class ActivityPartyAccount extends ConvertMapToString implements ICompositeValue {

            @SuppressWarnings("unchecked")
			@Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID);            	
            	String result = "";
            	try {
            		result = getAccount(s);
				} catch (GenericEntityException e) {
					e.printStackTrace();
				}
				return result;
            }

            
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcOppActivityPartyAccount = FastMap.newInstance();
        calcOppActivityPartyAccount.put(ActivityLookupConfiguration.INOUT_ACCOUNT_FULL_NAME, new ActivityPartyAccount());
        makeCalculatedField(calcOppActivityPartyAccount);    
             
       
        
        
        
//================================ Customer Name==========================================
        
        class ActivityPartyCustomer extends ConvertMapToString implements ICompositeValue {

            @SuppressWarnings("unchecked")
			@Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID);            	
            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");	
            	String partyId = "";
            	String roleTypeId = "";
            	Map m = new HashMap();
            	Map m1 = new HashMap();
            	List l = new ArrayList();
            	List list = new ArrayList();
            	m1.put("workEffortId", s);
            	try {
					l = delegator.findByAnd("WorkEffortPartyAssignment", m1);
				
					for(int j=0;j<l.size();j++){
						GenericValue gv = (GenericValue)l.get(j);
						roleTypeId = gv.getString("roleTypeId");
					
		            	Debug.logInfo("%%%%%%%%%%%%%%%%%%%%%%%%%ROLE TYPE ID=> "+roleTypeId, MODULE);
		            	
		            	if(roleTypeId.equals("PROSPECT") || roleTypeId.equals("CONTACT") || roleTypeId.equals("ACCOUNT")){
			            	m.put("workEffortId", s);
			            	m.put("roleTypeId", roleTypeId);
			            	
								list = delegator.findByAnd("WorkEffortPartyAssignment", m);
								if(list.size()>0){
									for(int i=0;i<list.size();i++){
										GenericValue gv1 = (GenericValue)list.get(i);
										partyId = gv1.getString("partyId");
										//return PartyHelper.getPartyName(delegator, partyId, false)+"("+partyId+")";
									}
								}
							 
		            	}
					}
            	}catch(Exception e){
            		e.printStackTrace();
            	}
				if(partyId.equals("")){
					return "";
				}else{
					return PartyHelper.getPartyName(delegator, partyId, false)+"("+partyId+")";
				}
            }

            
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(ActivityLookupConfiguration.INOUT_WORKEFFORT_ID);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcOppActivityPartyCustomer = FastMap.newInstance();
        calcOppActivityPartyCustomer.put(ActivityLookupConfiguration.INOUT_CUSTOMER_FULL_NAME, new ActivityPartyCustomer());
        makeCalculatedField(calcOppActivityPartyCustomer);    
            
        
        
        
    //================================ Estimated Start Date==========================================    
        class ActivityEstimatedDate extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @Override
            public String convert(Map<String, ?> value) {            	
            	String dateValue="";
            	if(value.get(ActivityLookupConfiguration.INOUT_ESTIMATED_START_DATE)!=null){
            		dateValue = value.get(ActivityLookupConfiguration.INOUT_ESTIMATED_START_DATE).toString();
            	}
            	return dateValue;
            }

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(ActivityLookupConfiguration.INOUT_ESTIMATED_START_DATE);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcECDFieldEstDate = FastMap.newInstance();
        calcECDFieldEstDate.put(ActivityLookupConfiguration.INOUT_ESTIMATED_START_DATE, new ActivityEstimatedDate());
        makeCalculatedField(calcECDFieldEstDate);
        
        
      //================================ Estimated Completion Date==========================================    
        class ActivityEstimatedComDate extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @Override
            public String convert(Map<String, ?> value) {  
            	String dateValue="";
            	if(value.get(ActivityLookupConfiguration.INOUT_ESTIMATED_COMPLETION_DATE)!=null){
            		dateValue = value.get(ActivityLookupConfiguration.INOUT_ESTIMATED_COMPLETION_DATE).toString();
            	}
            	return dateValue;
            	
            }

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(ActivityLookupConfiguration.INOUT_ESTIMATED_COMPLETION_DATE);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcECDFieldEstCloseDate = FastMap.newInstance();
        calcECDFieldEstCloseDate.put(ActivityLookupConfiguration.INOUT_ESTIMATED_COMPLETION_DATE, new ActivityEstimatedComDate());
        makeCalculatedField(calcECDFieldEstCloseDate);
        
    	
    
        //================================ Actual Start Date==========================================    
        class ActivityActualDate extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @Override
            public String convert(Map<String, ?> value) {    
            	String dateValue="";
            	if(value.get(ActivityLookupConfiguration.INOUT_ACTUAL_START_DATE)!=null){
            		dateValue =  value.get(ActivityLookupConfiguration.INOUT_ACTUAL_START_DATE).toString();
            	}
            	return dateValue;
            }

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(ActivityLookupConfiguration.INOUT_ACTUAL_START_DATE);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcECDFieldActDate = FastMap.newInstance();
        calcECDFieldActDate.put(ActivityLookupConfiguration.INOUT_ACTUAL_START_DATE, new ActivityActualDate());
        makeCalculatedField(calcECDFieldActDate);
        
        
      //================================ Actual Completion Date==========================================    
        class ActivityActualComDate extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @Override
            public String convert(Map<String, ?> value) {  
            	String dateValue="";
            	if(value.get(ActivityLookupConfiguration.INOUT_ACTUAL_COMPLETION_DATE)!=null){
            		dateValue = value.get(ActivityLookupConfiguration.INOUT_ACTUAL_COMPLETION_DATE).toString();
            	}
            	return dateValue;
            }

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(ActivityLookupConfiguration.INOUT_ACTUAL_COMPLETION_DATE);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcECDFieldActCloseDate = FastMap.newInstance();
        calcECDFieldActCloseDate.put(ActivityLookupConfiguration.INOUT_ACTUAL_COMPLETION_DATE, new ActivityActualComDate());
        makeCalculatedField(calcECDFieldActCloseDate);
        
        
        
        EntityCondition condition = null;
        
//
//        // select parties assigned to current user or his team according to view preferences.
//        if (getProvider().parameterIsPresent(ActivityLookupConfiguration.IN_RESPONSIBILTY)) {
//            if (getProvider().getUser().getOfbizUserLogin() != null) {
//                String userId = getProvider().getUser().getOfbizUserLogin().getString("partyId");
//                String viewPref = getProvider().getParameter(ActivityLookupConfiguration.IN_RESPONSIBILTY);
//                if (ActivityLookupConfiguration.MY_VALUES.equals(viewPref)) {
//                    // my parties
//                    condition = new EntityConditionList(
//                            Arrays.asList(
//                                    condition,
//                                    new EntityExpr("partyIdTo", EntityOperator.EQUALS, userId),
//                                    new EntityExpr("partyRelationshipTypeId", EntityOperator.IN, Arrays.asList("RESPONSIBLE_FOR"))
//                            ),
//                            EntityOperator.AND
//                    );
//                } else if (ActivityLookupConfiguration.TEAM_VALUES.equals(viewPref)) {
//                    // my teams parties
//                    condition = new EntityConditionList(
//                            Arrays.asList(
//                                    condition,
//                                    new EntityExpr("partyIdTo", EntityOperator.EQUALS, userId),
//                                    new EntityExpr("partyRelationshipTypeId", EntityOperator.IN, Arrays.asList("RESPONSIBLE_FOR", "ASSIGNED_TO"))
//                            ),
//                            EntityOperator.AND
//                    );
//                }
//            } else {
//                Debug.logError("Current session do not have any UserLogin set.", MODULE);
//            }
//        }
        
        if (getProvider().oneParameterIsPresent(BY_ADVANCED_FILTERS)) {
        	Debug.logInfo("==================================>inside Adv.........", MODULE);
            return findActivitiesBy(entity, condition, BY_ADVANCED_FILTERS);
        }
        
        if (getProvider().oneParameterIsPresent(BY_BASIC_FILTERS)) {
        	Debug.logInfo("==================================>inside Basic.........", MODULE);
            return findActivitiesBy(entity, condition, BY_BASIC_FILTERS);
        }
       
        Debug.logInfo("==================================>inside ALL.........", MODULE);
        return findAllActivities(entity, condition);
    }

    private <T extends EntityInterface> List<T> findAllActivities(Class<T> entity, EntityCondition roleCondition) {
        //List<EntityCondition> conditions = Arrays.asList(roleCondition);
    	List<EntityCondition> conditions = new ArrayList<EntityCondition>();
    	if(roleCondition!=null){
    		conditions.add(roleCondition);
    	}
    	
    	
    	
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
			conditions.add(new EntityExpr("partyId", false, EntityOperator.IN, teamMemberPartyIds, false));
			//conds.add(new EntityExpr("partyRelationshipTypeId", true, EntityOperator.EQUALS, "ASSIGNED_TO", true));
		}
		
		
		
        if (activeOnly) {
            conditions.add(EntityUtil.getFilterByDateExpr());
        }
        conditions.add(new EntityExpr("thruDate", EntityOperator.EQUALS, null));
        return findList(entity, new EntityConditionList(conditions, EntityOperator.AND));
    }

    @SuppressWarnings("unchecked")
	private <T extends EntityInterface> List<T> findActivitiesBy(Class<T> entity, EntityCondition roleCondition, List<String> filters) {
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
			conds.add(new EntityExpr("partyId", false, EntityOperator.IN, teamMemberPartyIds, false));
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
        List<String> partyConds = new ArrayList<String>();
         
        for (String filter : filters) {
        	
        	if (getProvider().parameterIsPresent(filter)) {
	        	Debug.logInfo("##############lookup#########################=> "+filter, MODULE);
	        	Debug.logInfo("##############lookup value#########################=> "+getProvider().getParameter(filter), MODULE);
	        	
		        if(filter.equals("accountPartyId") || filter.equals("leadPartyId") || filter.equals("contactPartyId")){
		        	partyConds.add(getProvider().getParameter(filter));
	        	}
		        else if(filter.equals("estimatedStartDateFrom")){
	        		String estimatedStartDateFrom = getProvider().getParameter("estimatedStartDateFrom");
	                String dateFrom = estimatedStartDateFrom.substring(0, estimatedStartDateFrom.lastIndexOf(":")+3);
	    	        
	    	        DateFormat myDateFormatFrom = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss");
	    	        Date estStartDateFrom = new Date();
	    	        try {
	    	        	estStartDateFrom = myDateFormatFrom.parse(dateFrom);
	    	        } catch (ParseException e) {
	    	             Debug.logInfo("Invalid Date Parser Exception ", MODULE);
	    	             e.printStackTrace();
	    	        }
	            
	    	        myDateFormatFrom = new SimpleDateFormat("yyyy-MM-dd");
	                Debug.logInfo("((((((((((((((((((((((((((((((((((((((((FROM ========"+myDateFormatFrom.format(estStartDateFrom), MODULE);
	                conds.add(new EntityExpr("estimatedStartDate", true, EntityOperator.GREATER_THAN_EQUAL_TO, myDateFormatFrom.format(estStartDateFrom)+"%", false));
	        	}
		        else if(filter.equals("estimatedStartDateTo")){
	        		String estimatedStartDateTo = getProvider().getParameter("estimatedStartDateTo");
	                String dateTo = estimatedStartDateTo.substring(0, estimatedStartDateTo.lastIndexOf(":")+3);
	    	        
	    	        DateFormat myDateFormatTo = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss");
	    	        Date estStartDateTo = new Date();
	    	        try {
	    	        	estStartDateTo = myDateFormatTo.parse(dateTo);
	    	        } catch (ParseException e) {
	    	             Debug.logInfo("Invalid Date Parser Exception ", MODULE);
	    	             e.printStackTrace();
	    	        }
	            
	    	        myDateFormatTo = new SimpleDateFormat("yyyy-MM-dd");
	                Debug.logInfo("((((((((((((((((((((((((((((((((((((((((TO ========"+myDateFormatTo.format(estStartDateTo), MODULE);
	                conds.add(new EntityExpr("estimatedStartDate", true, EntityOperator.LESS_THAN_EQUAL_TO, myDateFormatTo.format(estStartDateTo)+"%", false));
	        	}
		        else if(filter.equals("estimatedCloseDateFrom")){
	        		String estimatedCloseDateFrom = getProvider().getParameter("estimatedCloseDateFrom");
	                String dateFrom = estimatedCloseDateFrom.substring(0, estimatedCloseDateFrom.lastIndexOf(":")+3);
	    	        
	    	        DateFormat myDateFormatFrom = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss");
	    	        Date estCloseDateFrom = new Date();
	    	        try {
	    	        	estCloseDateFrom = myDateFormatFrom.parse(dateFrom);
	    	        } catch (ParseException e) {
	    	             Debug.logInfo("Invalid Date Parser Exception ", MODULE);
	    	             e.printStackTrace();
	    	        }
	            
	    	        myDateFormatFrom = new SimpleDateFormat("yyyy-MM-dd");
	                Debug.logInfo("((((((((((((((((((((((((((((((((((((((((FROM ========"+myDateFormatFrom.format(estCloseDateFrom), MODULE);
	                conds.add(new EntityExpr("estimatedCompletionDate", true, EntityOperator.GREATER_THAN_EQUAL_TO, myDateFormatFrom.format(estCloseDateFrom)+"%", false));
	        	}
		        else if(filter.equals("estimatedCloseDateTo")){
	        		String estimatedCloseDateTo = getProvider().getParameter("estimatedCloseDateTo");
	                String dateTo = estimatedCloseDateTo.substring(0, estimatedCloseDateTo.lastIndexOf(":")+3);
	    	        
	    	        DateFormat myDateFormatTo = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss");
	    	        Date estCloseDateTo = new Date();
	    	        try {
	    	        	estCloseDateTo = myDateFormatTo.parse(dateTo);
	    	        } catch (ParseException e) {
	    	             Debug.logInfo("Invalid Date Parser Exception ", MODULE);
	    	             e.printStackTrace();
	    	        }
	            
	    	        myDateFormatTo = new SimpleDateFormat("yyyy-MM-dd");
	                Debug.logInfo("((((((((((((((((((((((((((((((((((((((((TO ========"+myDateFormatTo.format(estCloseDateTo), MODULE);
	                conds.add(new EntityExpr("estimatedCompletionDate", true, EntityOperator.LESS_THAN_EQUAL_TO, myDateFormatTo.format(estCloseDateTo)+"%", false));
	        	}
		        else if(filter.equals("actualStartDateFrom")){
	        		String actualStartDateFrom = getProvider().getParameter("actualStartDateFrom");
	                String dateFrom = actualStartDateFrom.substring(0, actualStartDateFrom.lastIndexOf(":")+3);
	    	        
	    	        DateFormat myDateFormatFrom = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss");
	    	        Date estStartDateFrom = new Date();
	    	        try {
	    	        	estStartDateFrom = myDateFormatFrom.parse(dateFrom);
	    	        } catch (ParseException e) {
	    	             Debug.logInfo("Invalid Date Parser Exception ", MODULE);
	    	             e.printStackTrace();
	    	        }
	            
	    	        myDateFormatFrom = new SimpleDateFormat("yyyy-MM-dd");
	                Debug.logInfo("((((((((((((((((((((((((((((((((((((((((FROM ========"+myDateFormatFrom.format(estStartDateFrom), MODULE);
	                conds.add(new EntityExpr("actualStartDate", true, EntityOperator.GREATER_THAN_EQUAL_TO, myDateFormatFrom.format(estStartDateFrom)+"%", false));
	        	}
		        else if(filter.equals("actualStartDateTo")){
	        		String actualStartDateTo = getProvider().getParameter("actualStartDateTo");
	                String dateTo = actualStartDateTo.substring(0, actualStartDateTo.lastIndexOf(":")+3);
	    	        
	    	        DateFormat myDateFormatTo = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss");
	    	        Date estStartDateTo = new Date();
	    	        try {
	    	        	estStartDateTo = myDateFormatTo.parse(dateTo);
	    	        } catch (ParseException e) {
	    	             Debug.logInfo("Invalid Date Parser Exception ", MODULE);
	    	             e.printStackTrace();
	    	        }
	            
	    	        myDateFormatTo = new SimpleDateFormat("yyyy-MM-dd");
	                Debug.logInfo("((((((((((((((((((((((((((((((((((((((((TO ========"+myDateFormatTo.format(estStartDateTo), MODULE);
	                conds.add(new EntityExpr("actualStartDate", true, EntityOperator.LESS_THAN_EQUAL_TO, myDateFormatTo.format(estStartDateTo)+"%", false));
	        	}
		        else if(filter.equals("actualCloseDateFrom")){
	        		String actualCloseDateFrom = getProvider().getParameter("actualCloseDateFrom");
	                String dateFrom = actualCloseDateFrom.substring(0, actualCloseDateFrom.lastIndexOf(":")+3);
	    	        
	    	        DateFormat myDateFormatFrom = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss");
	    	        Date estCloseDateFrom = new Date();
	    	        try {
	    	        	estCloseDateFrom = myDateFormatFrom.parse(dateFrom);
	    	        } catch (ParseException e) {
	    	             Debug.logInfo("Invalid Date Parser Exception ", MODULE);
	    	             e.printStackTrace();
	    	        }
	            
	    	        myDateFormatFrom = new SimpleDateFormat("yyyy-MM-dd");
	                Debug.logInfo("((((((((((((((((((((((((((((((((((((((((FROM ========"+myDateFormatFrom.format(estCloseDateFrom), MODULE);
	                conds.add(new EntityExpr("actualCompletionDate", true, EntityOperator.GREATER_THAN_EQUAL_TO, myDateFormatFrom.format(estCloseDateFrom)+"%", false));
	        	}
		        else if(filter.equals("actualCloseDateTo")){
	        		String actualCloseDateTo = getProvider().getParameter("actualCloseDateTo");
	                String dateTo = actualCloseDateTo.substring(0, actualCloseDateTo.lastIndexOf(":")+3);
	    	        
	    	        DateFormat myDateFormatTo = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss");
	    	        Date estCloseDateTo = new Date();
	    	        try {
	    	        	estCloseDateTo = myDateFormatTo.parse(dateTo);
	    	        } catch (ParseException e) {
	    	             Debug.logInfo("Invalid Date Parser Exception ", MODULE);
	    	             e.printStackTrace();
	    	        }
	            
	    	        myDateFormatTo = new SimpleDateFormat("yyyy-MM-dd");
	                Debug.logInfo("((((((((((((((((((((((((((((((((((((((((TO ========"+myDateFormatTo.format(estCloseDateTo), MODULE);
	                conds.add(new EntityExpr("actualCompletionDate", true, EntityOperator.LESS_THAN_EQUAL_TO, myDateFormatTo.format(estCloseDateTo)+"%", false));
	        	}
		        else if(filter.equals("createdByUserLogin")){
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
            		conds.add(new EntityExpr("partyId", true, EntityOperator.EQUALS, getProvider().getParameter(filter), true));
            		conds.add(new EntityExpr("roleTypeId", true, EntityOperator.EQUALS, "CAL_OWNER", true));
            	}
            	else if(filter.equals("teamPartyIdTo")){
//            		VB :- Not sure on the purpose of flag.
//            		flag = true;
//            		conds.add(new EntityExpr("partyId", true, EntityOperator.EQUALS, getProvider().getParameter(filter), true));
//            		conds.add(new EntityExpr("roleTypeId", true, EntityOperator.EQUALS, "ACCOUNT_TEAM", true));
//            		conds.add(new EntityExpr("partyRelationshipTypeId", true, EntityOperator.EQUALS, "ASSIGNED_TO", true));
            	}
		        else{
	        		finalFilters.add(filter);
	        	}
        	}
        }
//      VB :- THRU DATE IS NULL for currently assigned Party
        conds.add(new EntityExpr("thruDate", EntityOperator.EQUALS, null));
        if(partyConds.size()>0){
        	conds.add(new EntityExpr("partyId", true, EntityOperator.IN, partyConds, false));
        }
       /* if(!flag){
    	    conds.add(new EntityExpr("partyRelationshipTypeId", true, EntityOperator.EQUALS, "RESPONSIBLE_FOR", true));
       }*/
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
        String filterValue = getProvider().getParameter(ActivityLookupConfiguration.INOUT_ASSIGNED_TEAM_PARTY_ID);
        if (filters != null && filters.contains(ActivityLookupConfiguration.INOUT_ASSIGNED_TEAM_PARTY_ID) 
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
        List relationships = getActiveTeamMembers(teamPartyIds, delegator);
        Set<String> partyIds = new HashSet<String>();
        for (Iterator iter = relationships.iterator(); iter.hasNext(); ) {
            GenericValue relationship = (GenericValue) iter.next();
            partyIds.add(relationship.getString("partyId"));
        }
        return partyIds;
    } 
}

