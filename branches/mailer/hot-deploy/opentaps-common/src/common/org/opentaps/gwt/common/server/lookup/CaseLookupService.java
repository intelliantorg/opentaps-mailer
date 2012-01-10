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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javolution.util.FastMap;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.party.party.PartyHelper;
import org.opentaps.common.util.ConvertMapToString;
import org.opentaps.common.util.ICompositeValue;
import org.opentaps.domain.base.entities.CustRequest;
import org.opentaps.domain.base.entities.CustRequestAndRole;
import org.opentaps.foundation.entity.EntityInterface;
import org.opentaps.foundation.infrastructure.InfrastructureException;
import org.opentaps.gwt.common.client.lookup.configuration.CaseLookupConfiguration;
import org.opentaps.gwt.common.client.lookup.configuration.OpportunityLookupConfiguration;
import org.opentaps.gwt.common.server.HttpInputProvider;
import org.opentaps.gwt.common.server.InputProviderInterface;

/**
 * The RPC service used to populate the ActivityListView.
 */
public class CaseLookupService extends EntityLookupService {

    private static final String MODULE = CaseLookupService.class.getName();

    private static List<String> BY_BASIC_FILTERS = Arrays.asList(CaseLookupConfiguration.INOUT_CUST_REQT_ID,
    																CaseLookupConfiguration.INOUT_CUST_REQT_NAME,
    																CaseLookupConfiguration.INOUT_CASE_ACCOUNT);
    private static List<String> BY_ADVANCED_FILTERS = Arrays.asList(CaseLookupConfiguration.INOUT_CUST_REQT_ID,
    																	CaseLookupConfiguration.INOUT_CUST_REQT_NAME,
    																	CaseLookupConfiguration.INOUT_CASE_CONTACT,
    																	CaseLookupConfiguration.INOUT_CASE_STATUS_ID,
    																	CaseLookupConfiguration.INOUT_CREATED_BY_USER_LOGIN,
    																	CaseLookupConfiguration.INOUT_CASE_PRIORITY,
    																	CaseLookupConfiguration.INOUT_CASE_TYPE,
    																	CaseLookupConfiguration.INOUT_CASE_DATE,
    																	CaseLookupConfiguration.INOUT_CASE_DATE_FROM,
    																	CaseLookupConfiguration.INOUT_CASE_DATE_TO);

    private boolean activeOnly = false;

    /**
     * Creates a new <code>ActivityLookupService</code> instance.
     * @param provider an <code>InputProviderInterface</code> value
     */
    public CaseLookupService(InputProviderInterface provider) {
        super(provider, CaseLookupConfiguration.LIST_OUT_FIELDS);
    }
    
    /**
     * AJAX event to perform lookups on Accounts.
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return the resulting JSON response
     * @throws InfrastructureException if an error occurs
     */
    public static String findCases(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException {
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        CaseLookupService service = new CaseLookupService(provider); 
        System.out.println("##############################"+service.findCases(CustRequestAndRole.class).size());
        return json.makeLookupResponse(CaseLookupConfiguration.INOUT_CUST_REQT_ID, service, request.getSession(true).getServletContext());
    }
    
    /**
     * Sets if the lookup methods should filter active parties only, defaults to <code>true</code>.
     * @param bool a <code>boolean</code> value
     */
    public void setActiveOnly(boolean bool) {
        this.activeOnly = bool;
    }

    private <T extends EntityInterface> List<T> findCases(Class<T> entity) {
        
        EntityCondition condition = null;
        
        
        //add rule that causes formated status to be added to result
        class CaseStatus extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(CaseLookupConfiguration.INOUT_CASE_STATUS_ID);
            	
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
                s.add(CaseLookupConfiguration.INOUT_CASE_STATUS_ID);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcStatusField = FastMap.newInstance();
        calcStatusField.put(CaseLookupConfiguration.INOUT_CASE_STATUS_DESC, new CaseStatus());
        makeCalculatedField(calcStatusField);
        
        
   //****************************** Case Priority******************************************************    
        class CasePriority extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @SuppressWarnings("unchecked")
			@Override
            public String convert(Map<String, ?> value) {
            	String s = null;
            	Long s1 = null;
            	if(value.get(CaseLookupConfiguration.INOUT_CASE_PRIORITY)!=null){
            	s1 = (Long)value.get(CaseLookupConfiguration.INOUT_CASE_PRIORITY);
            	s = s1.toString();
            	}
            	Debug.logInfo("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{=>S "+s, MODULE);
            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
            	String res="";
            	try {
            		Map mp = new HashMap();
            		List lst = new ArrayList();
            		if(s!=null){
	            		mp.put("enumTypeId", "PRIORITY_LEV");
	            		mp.put("enumCode", s.trim());
	            		lst = delegator.findByAnd("Enumeration", mp);
	            		Debug.logInfo("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{List=> "+lst, MODULE);
	            		if(lst.size()>0){
	            			GenericValue gv = (GenericValue)lst.get(0);
	            			res = gv.getString("description");
	            		}
            		}
            		//res = (String) delegator.findByAnd("Enumeration", UtilMisc.toMap("enumTypeId", "PRIORITY_LEV","enumCode", s)).getString("description");
            		Debug.logInfo("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{res=> "+res, MODULE);
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
                s.add(CaseLookupConfiguration.INOUT_CASE_PRIORITY);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcPriorityField = FastMap.newInstance();
        calcPriorityField.put(CaseLookupConfiguration.INOUT_CASE_PRIORITY_DESC, new CasePriority());
        makeCalculatedField(calcPriorityField);
        
        
        
        // add rule that causes formatted account type to be added to result
        class CaseType extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(CaseLookupConfiguration.INOUT_CASE_TYPE);
            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
            	String res="";
            	try {
            		GenericValue enumEntry = delegator.findByPrimaryKey("CustRequestType", UtilMisc.toMap("custRequestTypeId", s));
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
                s.add(CaseLookupConfiguration.INOUT_CASE_TYPE);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcTypeField = FastMap.newInstance();
        calcTypeField.put(CaseLookupConfiguration.INOUT_CASE_TYPE_DESC, new CaseType());
        makeCalculatedField(calcTypeField);
        

     // add rule that causes formatted account type to be added to result
        class CaseAccount extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(CaseLookupConfiguration.INOUT_CUST_REQT_ID);
            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
            	String res="";
            	try {
            		Class utilCase = Class.forName("com.opensourcestrategies.crmsfa.cases.UtilCase");
            		Method meth = utilCase.getMethod("getCasePrimaryAccountPartyId", new Class[]{GenericDelegator.class,String.class});
            		res = (String)meth.invoke(utilCase, new Object[]{delegator,s});
					//res = UtilCase.getCasePrimaryAccountPartyId(delegator, s);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
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
                s.add(CaseLookupConfiguration.INOUT_CUST_REQT_ID);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcCaseAccountField = FastMap.newInstance();
        calcCaseAccountField.put(CaseLookupConfiguration.INOUT_CASE_ACCOUNT, new CaseAccount());
        makeCalculatedField(calcCaseAccountField);    
        
        
 //================================ Party Name==========================================
        
        class CasePartyName extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @Override
            public String convert(Map<String, ?> value) {
            	String s = (String) value.get(CaseLookupConfiguration.INOUT_CASE_CONTACT);            	
            	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");	
            	Debug.logInfo("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@partyId=> "+s, MODULE);
            	return PartyHelper.getPartyName(delegator, s, false)+"("+s+")";
            }

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(CaseLookupConfiguration.INOUT_CASE_CONTACT);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcOppPartyNameField = FastMap.newInstance();
        calcOppPartyNameField.put(CaseLookupConfiguration.INOUT_CASE_PARTY_NAME, new CasePartyName());
        makeCalculatedField(calcOppPartyNameField);
        
        
        class CaseCreatedDate extends ConvertMapToString implements ICompositeValue {

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ConvertMapToString#convert(java.util.Map)
             */
            @Override
            public String convert(Map<String, ?> value) {            	
            	return value.get(CaseLookupConfiguration.INOUT_CASE_DATE).toString();
            }

            /* (non-Javadoc)
             * @see org.opentaps.common.util.ICompositeValue#getFields()
             */
            public LinkedHashSet<String> getFields() {
                LinkedHashSet<String> s = new LinkedHashSet<String>(1);
                s.add(CaseLookupConfiguration.INOUT_CASE_DATE);                
                return s;
            }
        }

        Map<String, ConvertMapToString> calcECDField = FastMap.newInstance();
        calcECDField.put(CaseLookupConfiguration.INOUT_CASE_DATE, new CaseCreatedDate());
        makeCalculatedField(calcECDField);
        
        
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
            return findCasesBy(entity, condition, BY_ADVANCED_FILTERS);
        }
        
        if (getProvider().oneParameterIsPresent(BY_BASIC_FILTERS)) {
            return findCasesBy(entity, condition, BY_BASIC_FILTERS);
        }       

        return findAllCases(entity, condition);
    }

    private <T extends EntityInterface> List<T> findAllCases(Class<T> entity, EntityCondition roleCondition) {
        //List<EntityCondition> conditions = Arrays.asList(roleCondition);
    	List<EntityCondition> conditions = new ArrayList<EntityCondition>();
        if (activeOnly) {
            conditions.add(EntityUtil.getFilterByDateExpr());
        }
        Debug.logInfo("@@@@@@@@@@@@@@@@@@@@@"+entity+" "+conditions, MODULE);
        List l = findList(entity, new EntityConditionList(conditions, EntityOperator.AND));
        Debug.logInfo("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+l, MODULE);
        Debug.logInfo("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+l.size(), MODULE);
        return l;
    }

    private <T extends EntityInterface> List<T> findCasesBy(Class<T> entity, EntityCondition roleCondition, List<String> filters) {
        List<EntityCondition> conds = new ArrayList<EntityCondition>();
        if (activeOnly) {
            conds.add(EntityUtil.getFilterByDateExpr());
        }
        
        List<String> finalFilters = new ArrayList<String>(); 
        List<String> partyConds = new ArrayList<String>();
        
        for (String filter : filters) {
            if (getProvider().parameterIsPresent(filter)) {
            	Debug.logInfo("##############lookup#########################=> "+filter, MODULE);
            	Debug.logInfo("##############lookup value#########################=> "+getProvider().getParameter(filter), MODULE);
            	if(filter.equals("accountPartyId") || filter.equals("fromPartyId")){
		        	partyConds.add(getProvider().getParameter(filter));
	        	}
            	else if(filter.equals("createdDateFrom")){            		
            		String createdDate = getProvider().getParameter("createdDateFrom");
                    String date = createdDate.substring(0, createdDate.lastIndexOf(":")+3);
            	        
            	        DateFormat myDateFormat = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss");
            	        Date estCloseDate = new Date();
            	        try {
            	        	estCloseDate = myDateFormat.parse(date);
            	        } catch (ParseException e) {
            	             Debug.logInfo("Invalid Date Parser Exception ", MODULE);
            	             e.printStackTrace();
            	        }
                    
            	    myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Debug.logInfo("(((((((((((((((((((((((((((((((((((((((( ========> "+myDateFormat.format(estCloseDate), MODULE);
                		
                	conds.add(new EntityExpr("createdDate", true, EntityOperator.GREATER_THAN_EQUAL_TO, myDateFormat.format(estCloseDate)+"%", false));
                	Debug.logInfo("(((((((((((((((((((((((((((((((((((((((( ========>CONDS=> "+conds, MODULE);
            	}
            	else if(filter.equals("createdDateTo")){            		
            		String createdDate = getProvider().getParameter("createdDateTo");
                    String date = createdDate.substring(0, createdDate.lastIndexOf(":")+3);
            	        
            	        DateFormat myDateFormat = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss");
            	        Date estCloseDate = new Date();
            	        try {
            	        	estCloseDate = myDateFormat.parse(date);
            	        } catch (ParseException e) {
            	             Debug.logInfo("Invalid Date Parser Exception ", MODULE);
            	             e.printStackTrace();
            	        }
                    
            	    myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Debug.logInfo("(((((((((((((((((((((((((((((((((((((((( ========> "+myDateFormat.format(estCloseDate), MODULE);
                		
                	conds.add(new EntityExpr("createdDate", true, EntityOperator.LESS_THAN_EQUAL_TO, myDateFormat.format(estCloseDate)+"%", false));
                	Debug.logInfo("(((((((((((((((((((((((((((((((((((((((( ========>CONDS=> "+conds, MODULE);
            	}
            	else{            
            		finalFilters.add(filter);
            	}
                
            }
        }
        if(partyConds.size()>0){
        	conds.add(new EntityExpr("partyId", true, EntityOperator.IN, partyConds, false));
        }
          //conds.add(roleCondition);
        return findListWithFilters(entity, conds, finalFilters);
    }


}

