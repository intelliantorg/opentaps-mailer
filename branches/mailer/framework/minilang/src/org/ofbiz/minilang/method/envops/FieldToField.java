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

package org.ofbiz.minilang.method.envops;

import java.util.*;

import org.w3c.dom.*;
import org.ofbiz.base.util.*;
import org.ofbiz.minilang.*;
import org.ofbiz.minilang.method.*;
import org.ofbiz.entity.*;
import org.ofbiz.entity.model.*;

/**
 * Copies a map field to a map field
 */
public class FieldToField extends MethodOperation {
    
    public static final String module = FieldToField.class.getName();
    
    ContextAccessor mapAcsr;
    ContextAccessor fieldAcsr;
    ContextAccessor toMapAcsr;
    ContextAccessor toFieldAcsr;

    public FieldToField(Element element, SimpleMethod simpleMethod) {
        super(element, simpleMethod);
        mapAcsr = new ContextAccessor(element.getAttribute("map-name"));
        fieldAcsr = new ContextAccessor(element.getAttribute("field-name"));
        toMapAcsr = new ContextAccessor(element.getAttribute("to-map-name"));
        toFieldAcsr = new ContextAccessor(element.getAttribute("to-field-name"));

        // set toMapAcsr and toFieldAcsr to their defualt values of mapAcsr and fieldAcsr if empty
        if (toMapAcsr.isEmpty()) {
            toMapAcsr = mapAcsr;
        }
        if (toFieldAcsr.isEmpty()) {
            toFieldAcsr = fieldAcsr;
        }
    }

    public boolean exec(MethodContext methodContext) {
        Object fieldVal = null;

        if (!mapAcsr.isEmpty()) {
            Map fromMap = (Map) mapAcsr.get(methodContext);

            if (fromMap == null) {
                if (Debug.infoOn()) Debug.logInfo("Map not found with name " + mapAcsr + ", not copying from this map", module);
                return true;
            }

            fieldVal = fieldAcsr.get(fromMap, methodContext);
        } else {
            // no map name, try the env
            fieldVal = fieldAcsr.get(methodContext);
        }

        if (fieldVal == null) {
            if (Debug.verboseOn()) Debug.logVerbose("Field value not found with name " + fieldAcsr + " in Map with name " + mapAcsr + ", not copying field", module);
            return true;
        }

        // note that going to an env field will only work if it came from an env 
        // field because if not specified the to-map-name will be set to the map-name
        // to go from a map field to an env field, use the field-to-env operation
        Map toMap = null;

        if (!toMapAcsr.isEmpty()) {
            toMap = (Map) toMapAcsr.get(methodContext);
            if (toMap == null) {
                if (Debug.verboseOn()) Debug.logVerbose("Map not found with name " + toMapAcsr + ", creating new map", module);
                toMap = new HashMap();
                toMapAcsr.put(methodContext, toMap);
            }

            // if our map is a GenericValue, convert the input data to the correct type of the GenericValue fields
            if (toMap instanceof GenericValue) {
                GenericValue tmpVal = (GenericValue) toMap;
                ModelField modelField = tmpVal.getModelEntity().getField(toFieldAcsr.toString());
                if (modelField != null) {
                    try {
                        // we need the ModelFieldType because it stores the Java lang type of the field (seems to be only way to do this)
                        ModelFieldType type = tmpVal.getDelegator().getEntityFieldType(tmpVal.getModelEntity(), modelField.getType());
                        if ((type != null) && !(fieldVal.getClass().getName().equals(type.getJavaType()))) {
                            // convert with null format and locale for now (should add as attributes of field-to-field)
                            // the last argument tells simpleTypeConvert not to throw a GeneralException when the conversion fails (fieldVal will be unchanged)
                            fieldVal = ObjectType.simpleTypeConvert(fieldVal, type.getJavaType(), null, null, false);
                        }
                    } catch (GenericEntityException e) {
                        Debug.logError(e, "Failed to convert " + fieldVal.getClass().getName() + " of from-field input into " 
                                + modelField.getType() + " needed by " + tmpVal.getEntityName() + "." + modelField.getName(), module);
                    } catch (GeneralException e) {
                        // this should not happen if we pass false to simpleTypeConvert, log anyway
                        Debug.logError(e, "field-to-field conversion error: " + e.getMessage(), module);
                    }
                } 
            }
            toFieldAcsr.put(toMap, fieldVal, methodContext);
        } else {
            // no to-map, so put in env
            toFieldAcsr.put(methodContext, fieldVal);
        }

        return true;
    }

    public String rawString() {
        return "<field-to-field field-name=\"" + this.fieldAcsr + "\" map-name=\"" + this.mapAcsr + "\" to-field-name=\"" + this.toFieldAcsr + "\" to-map-name=\"" + this.toMapAcsr + "\"/>";
    }
    public String expandedString(MethodContext methodContext) {
        // TODO: something more than a stub/dummy
        return this.rawString();
    }
}
