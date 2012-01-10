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

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.party.party.PartyHelper;
import org.opentaps.common.util.UtilCommon;
import org.opentaps.domain.base.entities.PartyRole;
import org.opentaps.foundation.entity.EntityInterface;
import org.opentaps.foundation.infrastructure.InfrastructureException;
import org.opentaps.gwt.common.client.lookup.configuration.ResourceLookupConfiguration;
import org.opentaps.gwt.common.server.HttpInputProvider;
import org.opentaps.gwt.common.server.InputProviderInterface;

/**
 * The RPC service used to populate industry autocompleters widgets.
 */
public class ResourceLookupService extends EntityLookupAndSuggestService {
	
	private static final String MODULE = ResourceLookupService.class.getName();

	private Date requestedStartDate; 
	private Date requestedCloseDate;
	private Locale locale;
	
    @SuppressWarnings("deprecation")
	protected ResourceLookupService(InputProviderInterface provider) {
        super(provider,
        		Arrays.asList(ResourceLookupConfiguration.INOUT_PARTY_ID));
        locale = provider.getLocale();
		if (locale == null) {
			locale = Calendar.getAvailableLocales()[0];
		}
        String dateParam = provider.getParameter(ResourceLookupConfiguration.INOUT_ESTIMATED_START_DATE_FROM);
        String timeParam = provider.getParameter(ResourceLookupConfiguration.INOUT_ESTIMATED_START_TIME_FROM);
        String durationParam = provider.getParameter(ResourceLookupConfiguration.INOUT_DURATION);
        Debug.logInfo("suggestResources() estimatedStartDateFrom >> " + dateParam, MODULE);
        Debug.logInfo("suggestResources() timeParam >> " + timeParam, MODULE);
        Debug.logInfo("suggestResources() durationParam >> " + durationParam, MODULE);
        //Eg. Tue Jul 07 2009 00:00:00 GMT+0530 (IST)
        
        if(dateParam != null) {
	        String date = dateParam;
	        String dateFormatString = UtilDateTime.getDateFormat(locale);
	        try {
		        if (dateParam.indexOf("GMT") != -1) {
	//	        	Thu Jun 10 2010 00:00:00 GMT+0530 (IST)
		        	dateFormatString = "E MMM dd yyyy HH:mm:ss";
		        	date = dateParam.substring(0, dateParam.lastIndexOf(":")+3);
		        } else if (dateParam.indexOf("-") != -1) {
//		        	YYYY-MM-dd
//	        		2010-05-11 04:30:00.0
//		        	This format will be coming from viewActivity page as stored in DB, ideally should have been handled from source itself.
		        	SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		        	Date dateTemp = sdfSource.parse(date);
		        	SimpleDateFormat sdfDestination = new SimpleDateFormat(dateFormatString);
		        	date = sdfDestination.format(dateTemp);
		        	SimpleDateFormat sdfDestination2 = new SimpleDateFormat("hh:mm a");
		        	timeParam = sdfDestination2.format(dateTemp);
		        }
		        Debug.logInfo("suggestResources() dateToParse >> " + date, MODULE);
		        Debug.logInfo("suggestResources() dateFormatString >> " + dateFormatString, MODULE);
		        DateFormat myDateFormat = new SimpleDateFormat(dateFormatString);        
	        	requestedStartDate = myDateFormat.parse(date);
	        } catch (ParseException e) {
	             Debug.logInfo("Invalid Date Parser Exception ", MODULE);
	             e.printStackTrace();
	        }
        }else{
        	requestedStartDate = new Date();
        }
        Debug.logInfo("suggestResources() requestedStartDate >> " + requestedStartDate, MODULE);
        if(timeParam!=null){
        	SimpleDateFormat myDateFormat = new SimpleDateFormat("hh:mm a");
            Date date=null;
            try {
            	date = myDateFormat.parse(timeParam);
            	
            } catch (ParseException e) {
                 Debug.logInfo("Invalid Date Parser Exception ", MODULE);
                 e.printStackTrace();
            }
            requestedStartDate.setHours(date.getHours());
            requestedStartDate.setMinutes(date.getMinutes());
        }
        
        Debug.logInfo("suggestResources() duration >> " + durationParam, MODULE);
        if(durationParam!=null){
        	Timestamp ts = new Timestamp(requestedStartDate.getTime());        	
        	requestedCloseDate = UtilCommon.getEndTimestamp(ts, durationParam, Calendar.getAvailableLocales()[0], Calendar.getInstance().getTimeZone());
        }else{
        	Timestamp ts = new Timestamp(requestedStartDate.getTime());        	
        	requestedCloseDate = UtilCommon.getEndTimestamp(ts, "1:00:00", Calendar.getAvailableLocales()[0], Calendar.getInstance().getTimeZone());
        }
        Debug.logInfo("suggestResources() requestedCloseDate >> " + requestedCloseDate, MODULE);
    }

    /**
     * AJAX event to suggest ActivityStatus.
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return the resulting JSON response
     * @throws InfrastructureException if an error occurs
     * @throws GenericEntityException 
     */
    public static String suggestCars(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException, GenericEntityException{
    	InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        ResourceLookupService service = new ResourceLookupService(provider);
        service.suggestCars();
        return json.makeSuggestResponse(ResourceLookupConfiguration.INOUT_PARTY_ID, service);
    }

    /**
     * Gets all party classifications.
     * @return the list of party classifications <code>GenericValue</code>
     * @throws GenericEntityException 
     */
	public List<PartyRole> suggestCars() throws GenericEntityException {
    	return suggestResource("CAR");
    }
    
    private List<PartyRole> suggestResource(String resourceType) throws GenericEntityException {
    	GenericDelegator delegator = GenericDelegator.getGenericDelegator("default");
    	List<String> partyCondList = new ArrayList<String>(); 
    	List<GenericValue> resources = delegator.findByAnd("PartyRole", UtilMisc.toList(new EntityExpr("roleTypeId", EntityOperator.EQUALS, resourceType)));
    	Debug.logInfo("suggestResource() " + resourceType + " in db =========== " + resources.size(), MODULE);
    	for(GenericValue resource : resources) {
    		String partyId = resource.getString(ResourceLookupConfiguration.INOUT_PARTY_ID);
    		Debug.logInfo("suggestResource() current partyId >> " + partyId, MODULE);
	    	List conditionList = UtilMisc.toList(
	                new EntityExpr("partyId", EntityOperator.EQUALS, partyId),
	                new EntityConditionList(
	                    UtilMisc.toList(
	                        new EntityExpr("currentStatusId", EntityOperator.EQUALS, "EVENT_SCHEDULED"),
	                        new EntityExpr("currentStatusId", EntityOperator.EQUALS, "EVENT_STARTED")
	                        ), EntityOperator.OR
	                    ));
	    	EntityConditionList cond = new EntityConditionList(conditionList, EntityOperator.AND);
	    	List<GenericValue> wepas = delegator.findByCondition("WorkEffortAndPartyAssign", cond, null, null);
    		boolean available = true;
    		if(UtilValidate.isEmpty(wepas)) {
//    			* resource is absolutely free, add to the list.
    			Debug.logInfo("suggestResource() resource " + partyId + " not engaged anywhere...hence available.", MODULE);
    			partyCondList.add(partyId);
    		} else {
    			Debug.logInfo("suggestResource() resource " + partyId + " engaged in " + wepas.size() + " activities.", MODULE);
    			for(GenericValue we : wepas) {
    				String workEffortId = (String)we.get("workEffortId");
    				Debug.logInfo("suggestResource() The workEffortId >> " + workEffortId, MODULE);
	    			String activityState = (String)we.get("currentStatusId");
	    			Date estimatedStartDate = (Date)we.get("estimatedStartDate");
	    			Date estimatedCompletionDate = (Date)we.get("estimatedCompletionDate");
	    			if (activityState.equalsIgnoreCase("EVENT_SCHEDULED")) {
	    				available = isAvailable(estimatedStartDate, estimatedCompletionDate);
	    			} if (activityState.equalsIgnoreCase("EVENT_STARTED")) {
//	    				* If the event is started, actual completion date would be NULL, but duration would be (Scheduled completion - Scheduled start)
		    			Date actualStartDate = (Date)we.get("actualStartDate");
		    			Timestamp actualStart = new Timestamp(actualStartDate.getTime());
		    			Timestamp start = new Timestamp(estimatedStartDate.getTime());
		    			Timestamp stop = new Timestamp(estimatedCompletionDate.getTime());
		    			String duration = UtilCommon.getDuration(start, stop, Calendar.getInstance().getTimeZone(), locale);
		    			Debug.logInfo("suggestResource() the duration >> " + duration, MODULE);
		    			Date expectedCompletionDate = UtilCommon.getEndTimestamp(actualStart, duration, locale, Calendar.getInstance().getTimeZone());
	    				available = isAvailable(actualStartDate, expectedCompletionDate);
	    			}
	    			if(!available) {
//	    				* if resource is available for even 1 activity, no need to dig further.
	    				break;
	    			}
    			}
    			if(available) {
    				Debug.logInfo("suggestResource() resource " + partyId + " is available.", MODULE);
    				partyCondList.add(partyId);
    			} else {
    				Debug.logInfo("suggestResource() resource " + resourceType + " " + partyId + " not available.", MODULE);
    			}
    		}
		}
    	EntityConditionList conditions = new EntityConditionList(
    			UtilMisc.toList(
    				new EntityExpr("partyId", EntityOperator.IN, partyCondList),
    				new EntityExpr("roleTypeId", EntityOperator.EQUALS, resourceType)    			 
    			 ),
    			 EntityOperator.AND);
    	List<PartyRole> resourcesList = findList(PartyRole.class,conditions);
		Debug.logInfo("suggestResource() resourcesList size >> " + resourcesList.size() + " for resourceType " + resourceType, MODULE);
    	return resourcesList;
    }  
    
    /**
     * Checks for availability.
     * @return availability status <code>boolean</code>
     */
    private boolean isAvailable(Date start, Date stop) {
    	Debug.logInfo("suggestResource() start >> " + start, MODULE);
    	Debug.logInfo("suggestResource() stop >> " + stop, MODULE);
    	boolean available = true;
    	if(
			requestedStartDate.compareTo(start) == 0 && 
			requestedCloseDate.compareTo(stop) == 0) {
//			* If input start & end dates are exactly same as any of existing estimated start & end dates. 
    		Debug.logInfo("suggestResource() CAUGHT in CASE I", MODULE);
			available = false;
		} else if(
			requestedStartDate.before(start) && 
			requestedCloseDate.before(stop) && 
			requestedCloseDate.after(start)) {
//			* If input start date b4 existing estimated start, but input end date in b/w existing start & end dates.
			Debug.logInfo("suggestResource() CAUGHT in CASE II", MODULE);
			available = false;
		} else if(
			requestedStartDate.after(start) &&
			requestedStartDate.before(stop) && 
			(requestedCloseDate.before(stop) || 
				requestedCloseDate.compareTo(stop) == 0 || 
				requestedCloseDate.after(stop))) {
//			* If 
			Debug.logInfo("suggestResource() CAUGHT in CASE III", MODULE);
			available = false;
		} else if(
			requestedStartDate.before(start) &&
			requestedStartDate.before(stop) && 
			requestedCloseDate.after(start) && 
			requestedCloseDate.after(stop)) {
			Debug.logInfo("suggestResource() CAUGHT in CASE IV", MODULE);
			available = false;
		}
    	return available;
    }
    
    public static String suggestDrivers(HttpServletRequest request, HttpServletResponse response) throws InfrastructureException, GenericEntityException{
        InputProviderInterface provider = new HttpInputProvider(request);
        JsonResponse json = new JsonResponse(response);
        ResourceLookupService service = new ResourceLookupService(provider);
        service.suggestDrivers();
        return json.makeSuggestResponse(ResourceLookupConfiguration.INOUT_PARTY_ID, service);
    }

    /**
     * Gets all party classifications.
     * @return the list of party classifications <code>GenericValue</code>
     * @throws GenericEntityException 
     */
	public List<PartyRole> suggestDrivers() throws GenericEntityException {
    	return suggestResource("DRIVER");
    }

    @Override
    public String makeSuggestDisplayedText(EntityInterface resource) {
    	String partyId = resource.getString(ResourceLookupConfiguration.INOUT_PARTY_ID);    	
        return PartyHelper.getPartyName(GenericDelegator.getGenericDelegator("default"), partyId, false);
    }
}
