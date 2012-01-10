/*
 * Copyright (c) 2008 - 2009 Open Source Strategies, Inc.
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

package org.opentaps.domain.container;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.jvnet.inflector.Noun;
import org.ofbiz.base.container.Container;
import org.ofbiz.base.container.ContainerConfig;
import org.ofbiz.base.container.ContainerException;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.ObjectType;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.base.util.UtilXml;
import org.ofbiz.base.util.template.FreeMarkerWorker;
import org.ofbiz.entity.GenericDelegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.model.ModelEntity;
import org.ofbiz.entity.model.ModelField;
import org.ofbiz.entity.model.ModelKeyMap;
import org.ofbiz.entity.model.ModelReader;
import org.ofbiz.entity.model.ModelRelation;
import org.ofbiz.entity.model.ModelUtil;
import org.ofbiz.entity.model.ModelViewEntity;
import org.ofbiz.entity.model.ModelViewEntity.ModelAlias;
import org.ofbiz.entity.model.ModelViewEntity.ModelMemberEntity;
import org.ofbiz.entity.model.ModelViewEntity.ModelViewLink;
import org.ofbiz.service.ServiceDispatcher;

import freemarker.template.TemplateException;

/**
 * Some utility routines for loading seed data.
 */
public class PojoGeneratorContainer implements Container {

    private static final String MODULE = PojoGeneratorContainer.class.getName();
    private static final String containerName = "pojo-generator-container";

    private static final String fileExtension = ".java";

    /** Config file. */
    private String configFile = null;

    /** The path of output. */
    private String outputPath = null;

    /** Java class FTL Template. */
    private String template = null;

    /** Hibernate IdClass FTL Template. */
    private String pkTemplate = null;

    /** Hibernate configuration FTL Template. */
    private String hibernateCfgTemplate = null;

    /**
     * Creates a new <code>PojoGeneratorContainer</code> instance.
     */
    public PojoGeneratorContainer() {
        super();
    }

    /** {@inheritDoc} */
    public void init(String[] args, String configFile) throws ContainerException {
        this.configFile = configFile;
        // disable job scheduler, JMS listener and startup services
        ServiceDispatcher.enableJM(false);
        ServiceDispatcher.enableJMS(false);
        ServiceDispatcher.enableSvcs(false);

        // parse arguments here if needed
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public boolean start() throws ContainerException {
        ContainerConfig.Container cfg = ContainerConfig.getContainer(containerName, configFile);
        ContainerConfig.Container.Property delegatorNameProp = cfg.getProperty("delegator-name");
        ContainerConfig.Container.Property outputPathProp = cfg.getProperty("output-path");
        ContainerConfig.Container.Property templateProp = cfg.getProperty("template");
        ContainerConfig.Container.Property pkTemplateProp = cfg.getProperty("pkTemplate");
        ContainerConfig.Container.Property hibernateCfgTemplateProp = cfg.getProperty("hibernateCfgTemplate");
        ContainerConfig.Container.Property hibernateCfgProp = cfg.getProperty("hibernateCfgPath");
        String delegatorNameToUse = null;

        // get delegator to use from the container configuration
        if (delegatorNameProp == null || delegatorNameProp.value == null || delegatorNameProp.value.length() == 0) {
            throw new ContainerException("Invalid delegator-name defined in container configuration");
        } else {
            delegatorNameToUse = delegatorNameProp.value;
        }

        // get the delegator
        GenericDelegator delegator = GenericDelegator.getGenericDelegator(delegatorNameToUse);
        if (delegator == null) {
            throw new ContainerException("Invalid delegator name: " + delegatorNameToUse);
        }

        // get output path to use from the container configuration
        if (outputPathProp == null || outputPathProp.value == null || outputPathProp.value.length() == 0) {
            throw new ContainerException("Invalid output-name defined in container configuration");
        } else {
            outputPath = outputPathProp.value;
        }

        // check the output path
        File outputDir = new File(outputPath);
        if (!outputDir.canWrite()) {
            throw new ContainerException("Unable to use output path: [" + outputPath + "], it is not writable");
        }

        // get the template file to use from the container configuration
        if (templateProp == null || templateProp.value == null || templateProp.value.length() == 0) {
            throw new ContainerException("Invalid template defined in container configuration");
        } else {
            template = templateProp.value;
        }

        // check the template file
        File templateFile = new File(template);
        if (!templateFile.canRead()) {
            throw new ContainerException("Unable to read template file: [" + template + "]");
        }

        // get the primay key template file to use from the container configuration
        if (pkTemplateProp == null || pkTemplateProp.value == null || pkTemplateProp.value.length() == 0) {
            throw new ContainerException("Invalid pk template defined in container configuration");
        } else {
            pkTemplate = pkTemplateProp.value;
        }

        // check the pk template file
        File pkTemplateFile = new File(pkTemplate);
        if (!pkTemplateFile.canRead()) {
            throw new ContainerException("Unable to read pk template file: [" + pkTemplate + "]");
        }

        // get the primay key template file to use from the container configuration
        if (hibernateCfgTemplateProp == null || hibernateCfgTemplateProp.value == null || hibernateCfgTemplateProp.value.length() == 0) {
            throw new ContainerException("Invalid hibernate cfg template defined in container configuration");
        } else {
            hibernateCfgTemplate = hibernateCfgTemplateProp.value;
        }

        // check the pk template file
        File cfgTemplateFile = new File(hibernateCfgTemplate);
        if (!cfgTemplateFile.canRead()) {
            throw new ContainerException("Unable to read hibernate cfg template file: [" + hibernateCfgTemplate + "]");
        }

        // get entities list
        ModelReader modelReader = delegator.getModelReader();
        Collection<String> entities = null;
        try {
            entities = modelReader.getEntityNames();
        } catch (GenericEntityException e) {
            Debug.logError(e, "Error getting the entities list from delegator " + delegatorNameToUse, MODULE);
        }

        // record errors for summary
        List<String> errorEntities = new LinkedList<String>();
        // record view entities
        List<String> viewEntities = new LinkedList<String>();

        int totalGeneratedClasses = 0;
        if (entities != null && entities.size() > 0) {
            Debug.logImportant("=-=-=-=-=-=-= Generating the POJO entities ...", MODULE);
            for (String entityName : entities) {
                ModelEntity modelEntity = null;
                try {
                    modelEntity = modelReader.getModelEntity(entityName);
                    if (modelEntity != null && modelEntity instanceof ModelViewEntity) {
                        viewEntities.add(entityName);
                    }
                } catch (GenericEntityException e) {
                    Debug.logError(e, MODULE);
                }
            }
            for (String entityName : entities) {
                ModelEntity modelEntity = null;
                try {
                    modelEntity = modelReader.getModelEntity(entityName);
                } catch (GenericEntityException e) {
                    Debug.logError(e, MODULE);
                }

                if (modelEntity == null) {
                    errorEntities.add(entityName);
                    Debug.logError("Error getting the entity model from delegator " + delegatorNameToUse + " and entity " + entityName, MODULE);
                    continue;
                }

                // could be an object, but is just a Map for simplicity
                Map<String, Object> entityInfo = new HashMap<String, Object>();
                // entity columns what used in entity field
                List<String> entityColumns = new ArrayList<String>();
                boolean isView = false;
                if ((modelEntity instanceof ModelViewEntity)) {
                    isView = true;
                }
                // get name of the entity
                entityName = modelEntity.getEntityName();
                entityInfo.put("name", entityName);
                entityInfo.put("tableName", modelEntity.getPlainTableName());
                entityInfo.put("isView", isView);
                entityInfo.put("resourceName", modelEntity.getDefaultResourceName());
                entityInfo.put("primaryKeys", modelEntity.getPkFieldNames());
                entityInfo.put("viewEntities" , viewEntities);
                // get all the fields of the entity which become members of the class
                List<String> fieldNames = modelEntity.getAllFieldNames();
                entityInfo.put("fields", fieldNames);

                //get all the columns of the entity
                Map<String, String> columnNames = new TreeMap<String, String>();
                for (String fieldName : fieldNames) {
                    String columnName = modelEntity.getColNameOrAlias(fieldName);
                    columnNames.put(fieldName, columnName);
                    entityColumns.add(columnName);
                }
                entityInfo.put("columnNames", columnNames);
                // distinct types for the import section
                Set<String> types = new TreeSet<String>();

                // a type for each field during declarations
                Map<String, String> fieldTypes = new TreeMap<String, String>();

                // names of the get and set methods
                Map<String, String> getMethodNames = new TreeMap<String, String>();
                Map<String, String> setMethodNames = new TreeMap<String, String>();
                Map<String, List<String>> validatorMaps = new TreeMap<String, List<String>>();

                // now go through all the fields
                boolean hasError = false;
                for (String fieldName : fieldNames) {
                    // use the model field to figure out the Java type
                    ModelField modelField = modelEntity.getField(fieldName);
                    String type = null;
                    try {
                        // this converts String to java.lang.String, Timestamp to java.sql.Timestamp, etc.
                        type = ObjectType.loadClass(delegator.getEntityFieldType(modelEntity, modelField.getType()).getJavaType()).getName();
                    } catch (Exception e) {
                        Debug.logError(e, MODULE);
                    }

                    if (type == null) {
                        errorEntities.add(entityName);
                        Debug.logError("Error getting the type of the field " + fieldName + " of entity " + entityName + " for delegator " + delegatorNameToUse, MODULE);
                        hasError = true;
                        break;
                    }

                    // make all Doubles BigDecimals -- in the entity model fieldtype XML files, all floating points are defined as Doubles
                    // and there is a GenericEntity.getBigDecimal method which converts them to BigDecimal.  We will make them all BigDecimals
                    // and then use the Entity.convertToBigDecimal method
                    if ("java.lang.Double".equals(type)) {
                        type = "java.math.BigDecimal";
                    }

                    // make all Object field to byte[]
                    if ("java.lang.Object".equals(type)) {
                        type = "byte[]";
                    } else {
                        types.add(type);
                    }
                    // this is the short type: ie, java.lang.String -> String; java.util.HashMap -> HashMap, etc.
                    String shortType = type;
                    int idx = type.lastIndexOf(".");
                    if (idx > 0) {
                        shortType = type.substring(idx + 1);
                    }
                    fieldTypes.put(fieldName, shortType);

                    // accessor method names
                    try {
                        getMethodNames.put(fieldName, getterName(fieldName));
                        setMethodNames.put(fieldName, setterName(fieldName));
                    } catch (IllegalArgumentException e) {
                        errorEntities.add(entityName);
                        Debug.logError(e, MODULE);
                        hasError = true;
                        break;
                    }
                    if (modelField.getValidatorsSize() > 0) {
                        List<String> validators = new ArrayList<String>();
                        for (int i = 0; i < modelField.getValidatorsSize(); i++) {
                            String validator = modelField.getValidator(i);
                            validators.add(validator);
                        }
                        validatorMaps.put(fieldName, validators);
                    }
                }

                if (hasError) {
                    continue;
                }
                // get the relations
                List<Map<String, String>> relations = new ArrayList<Map<String, String>>();
                Iterator<ModelRelation> relationsIter = modelEntity.getRelationsIterator();
                while (relationsIter.hasNext()) {
                    ModelRelation modelRelation = relationsIter.next();
                    Map<String, String> relationInfo = new TreeMap<String, String>();
                    relationInfo.put("entityName", modelRelation.getRelEntityName());
                    // the string to put in the getRelated() method, title is "" if null
                    relationInfo.put("relationName", modelRelation.getTitle() + modelRelation.getRelEntityName());
                    // the names are pluralized for relation of type many
                    String accessorName = modelRelation.getTitle() + modelRelation.getRelEntityName();
                    if ("many".equals(modelRelation.getType())) {
                        relationInfo.put("type", "many");
                        try {
                            accessorName = Noun.pluralOf(accessorName, Locale.ENGLISH);
                        } catch (Exception e) {
                            Debug.logWarning("For entity " + entityName + ", could not get the plural of " + accessorName + ", falling back to " + accessorName + "s.", MODULE);
                            accessorName = accessorName + "s";
                        }
                    } else {
                        // we do not care about the difference between one and one-nofk types
                        relationInfo.put("type", "one");
                    }

                    // check if the accessor conflicts with an already defined field
                    String fieldName = accessorName.substring(0, 1).toLowerCase() + accessorName.substring(1);
                    if (fieldNames.contains(fieldName)) {
                        String oldFieldName = fieldName;
                        accessorName = "Related" + accessorName;
                        fieldName = accessorName.substring(0, 1).toLowerCase() + accessorName.substring(1);
                        Debug.logWarning("For entity " + entityName + ", field " + oldFieldName + " already defined, using " + fieldName + " instead.", MODULE);
                    }
                    String fkName = modelRelation.getFkName();
                    relationInfo.put("accessorName", accessorName);
                    relationInfo.put("fieldName", fieldName);
                    relationInfo.put("fkName", fkName);
                    if (modelRelation.getKeyMapsSize() == 1) {
                        //relation child Entity field
                        String joinField = "";
                        //relation parent Entity field
                        String mappedByFieldId = "";
                        if ("many".equals(modelRelation.getType())) {
                            joinField = modelRelation.getKeyMap(0).getRelFieldName();
                            mappedByFieldId  = modelRelation.getKeyMap(0).getFieldName();
                        } else {
                            joinField = modelRelation.getKeyMap(0).getFieldName();
                            mappedByFieldId  = modelRelation.getKeyMap(0).getRelFieldName();
                        }
                        // get relation db column name
                        String columnName = ModelUtil.javaNameToDbName(UtilXml.checkEmpty(joinField));
                        //adjust this column if use for other relation, if used we should mapped with insert="false" update="false"
                        if (!entityColumns.contains(columnName)) {
                            entityColumns.add(columnName);
                            relationInfo.put("isRepeated", "N");
                        } else {
                            relationInfo.put("isRepeated", "Y");
                        }
                        relationInfo.put("joinColumn", columnName);
                        //if this property is collection, and have only one primary key, and find it in child property
                        // this is useful for hibernate's cascading feature
                        if ("many".equals(modelRelation.getType()) && modelEntity.getPkFieldNames().size() == 1
                                && modelEntity.getPkFieldNames().contains(mappedByFieldId)) {
                            try {
                                ModelEntity refEntity = modelReader.getModelEntity(modelRelation.getRelEntityName());
                                // manyToOne field
                                String refField = "";
                                Iterator it = refEntity.getRelationsIterator();
                                while (it.hasNext()) {
                                    ModelRelation relation = (ModelRelation) it.next();
                                    //if relation map modelRelation
                                    if (relation.getRelEntityName().equals(entityName) && relation.getKeyMapsSize() == 1
                                            && relation.getKeyMap(0).getFieldName().equals(joinField)) {
                                        //get access name
                                        String aName = relation.getTitle() + relation.getRelEntityName();
                                        //get ref field name
                                        refField = aName.substring(0, 1).toLowerCase() + aName.substring(1);
                                        break;
                                    }
                                }
                                if (refEntity.getPkFieldNames().contains(joinField) && !refField.equals("")) {
                                    // if this collection should be a cascade collection property
                                    relationInfo.put("itemName", itemName(fieldName));
                                    relationInfo.put("refField", refField);
                                    //put add item method of collection
                                    relationInfo.put("addMethodName", addName(fieldName));
                                    //put remove item method of collection
                                    relationInfo.put("removeMethodName", removeName(fieldName));
                                    //put clear item method of collection
                                    relationInfo.put("clearMethodName", clearName(fieldName));
                                }
                            } catch (GenericEntityException e) {
                                Debug.logError(e, MODULE);
                            }
                        }
                    }
                    relations.add(relationInfo);
                }
                entityInfo.put("relations", relations);
                entityInfo.put("types", types);
                entityInfo.put("fieldTypes", fieldTypes);
                entityInfo.put("getMethodNames", getMethodNames);
                entityInfo.put("setMethodNames", setMethodNames);
                entityInfo.put("validatorMaps", validatorMaps);

                // map view-entity to @NamedNativeQuery
                if (isView) {
                    //pk fields of the first entity as view-entity pk
                    List<String> viewEntityPks = new LinkedList<String>();
                    StringBuffer query = new StringBuffer();
                    query.append("SELECT ");
                    // field <-> column alias mapping
                    Map<String, String> fieldMapAlias = new TreeMap<String, String>();
                    // field <-> column name mapping
                    Map<String, String> fieldMapColumns = new TreeMap<String, String>();
                    List<String> relationAlias = new LinkedList<String>();
                    ModelViewEntity modelViewEntity = (ModelViewEntity) modelEntity;
                    Iterator aliasIter = modelViewEntity.getAliasesIterator();
                    // iterate all fields, construct select sentence
                    while (aliasIter.hasNext()) {
                        ModelAlias alias = (ModelAlias) aliasIter.next();
                        String columnName = ModelUtil.javaNameToDbName(alias.getField());
                        if (fieldMapAlias.size() > 0) {
                            query.append(",");
                        }
                        //iterator field, such as "P.PARTY_ID \"partyId\"
                        query.append(alias.getEntityAlias() + "." + columnName + " AS \\\"" + alias.getField() + "\\\"");
                        String colAlias = alias.getColAlias();
                        String field = ModelUtil.dbNameToVarName(alias.getColAlias());
                        // put field-colAlias mapping
                        fieldMapAlias.put(field, colAlias);
                        // put field-column mapping
                        fieldMapColumns.put(field, alias.getEntityAlias() + "." + columnName);
                    }
                    // iterate all entities, construct from sentence
                    for (int i = 0; i < modelViewEntity.getAllModelMemberEntities().size(); i++) {
                        ModelMemberEntity modelMemberEntity = (ModelMemberEntity) modelViewEntity.getAllModelMemberEntities().get(i);
                        String tableName = ModelUtil.javaNameToDbName(modelMemberEntity.getEntityName());
                        String tableAlias = modelMemberEntity.getEntityAlias();
                        relationAlias.add(tableAlias);
                        if (i == 0) {
                            // main table
                            query.append(" FROM " + tableName + " " + tableAlias);
                            // get all the pk fields of the first entity which become members of the class
                            try {
                                ModelEntity firstEntity = modelReader.getModelEntity(modelMemberEntity.getEntityName());
                                 Iterator it = firstEntity.getPksIterator();
                                 while (it.hasNext()) {
                                     ModelField field = (ModelField) it.next();
                                     //just need one pk, else secondary pk would be null
                                     if (fieldMapAlias.containsKey(field.getName()) && viewEntityPks.size()==0) {
                                         viewEntityPks.add(field.getName());
                                     }
                                 }
                            } catch (GenericEntityException e) {
                                Debug.logError(e, MODULE);
                            }
                        } else {
                            // join table
                            Iterator viewLinkIter = modelViewEntity.getViewLinksIterator();
                            while (viewLinkIter.hasNext()) {
                                ModelViewLink modelViewLink = (ModelViewLink) viewLinkIter.next();
                                if (relationAlias.contains(modelViewLink.getEntityAlias())
                                        && relationAlias.contains(modelViewLink.getRelEntityAlias())
                                        && (tableAlias.equals(modelViewLink.getEntityAlias()) || tableAlias.equals(modelViewLink.getRelEntityAlias()))
                                ) {
                                    // adjust if left join or inner join
                                    String joinType = modelViewLink.isRelOptional() ? " LEFT JOIN " : " INNER JOIN ";
                                    query.append(joinType + tableName + " " + tableAlias);
                                    for (int k = 0; k < modelViewLink.getKeyMapsSize(); k++) {
                                        ModelKeyMap modelKeyMap = modelViewLink.getKeyMap(k);
                                        //get join conditions
                                        String joinCondition = modelViewLink.getEntityAlias() + "." + ModelUtil.javaNameToDbName(modelKeyMap.getFieldName())
                                        + " = " + modelViewLink.getRelEntityAlias() + "." + ModelUtil.javaNameToDbName(modelKeyMap.getRelFieldName());
                                        if (k == 0) {
                                            // if it is first join condition
                                            query.append(" ON " + joinCondition);
                                        } else {
                                            // if it isn't first join condition
                                            query.append(" AND " + joinCondition);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // if no pk for this viewEntity, then give a random field, because hibernate annotation need at least one pk filed.
                    if (viewEntityPks.size() == 0) {
                        viewEntityPks.add(fieldNames.get(0));
                    }
                    entityInfo.put("query", query.toString());
                    entityInfo.put("fieldMapAlias", fieldMapAlias);
                    entityInfo.put("fieldMapColumns", fieldMapColumns);
                    entityInfo.put("viewEntityPks", viewEntityPks);
                }
                // render it as FTL
                Writer writer = new StringWriter();
                try {
                    FreeMarkerWorker.renderTemplateAtLocation(template, entityInfo, writer);
                } catch (MalformedURLException e) {
                    Debug.logError(e, MODULE);
                    errorEntities.add(entityName);
                    break;
                } catch (TemplateException e) {
                    Debug.logError(e, MODULE);
                    errorEntities.add(entityName);
                    break;
                } catch (IOException e) {
                    Debug.logError(e, MODULE);
                    errorEntities.add(entityName);
                    break;
                } catch (IllegalArgumentException e) {
                    Debug.logError(e, MODULE);
                    errorEntities.add(entityName);
                    break;
                }

                // write it as a Java file
                File file = new File(outputPath + entityName + fileExtension);
                try {
                    FileUtils.writeStringToFile(file, writer.toString(), "UTF-8");
                } catch (IOException e) {
                    Debug.logError(e, "Aborting, error writing file " + outputPath + entityName + fileExtension, MODULE);
                    errorEntities.add(entityName);
                    break;
                }
                // pk class info
                Map<String, Object> pkInfo = null;
                if (!isView && modelEntity.getPkFieldNames().size() > 1) {
                    //if entity has more than one pk
                    pkInfo = new TreeMap<String, Object>();
                    // get all the pk fields of the entity which become members of the class
                    List<String> primaryKeys = modelEntity.getPkFieldNames();
                    // a type for each pk during declarations
                    Map<String, String> pkTypes = new TreeMap<String, String>();
                    // names of the get and set methods
                    Map<String, String> getPkMethodNames = new TreeMap<String, String>();
                    Map<String, String> setPkMethodNames = new TreeMap<String, String>();
                    // distinct types for the import section
                    Set<String> pkFieldTypes = new TreeSet<String>();
                    Iterator<ModelField> pksIter = modelEntity.getPksIterator();
                    while (pksIter.hasNext()) {
                        String type = null;
                        ModelField modelField = pksIter.next();
                        getPkMethodNames.put(modelField.getName(), getterName(modelField.getName()));
                        setPkMethodNames.put(modelField.getName(), setterName(modelField.getName()));
                        try {
                            // this converts String to java.lang.String, Timestamp to java.sql.Timestamp, etc.
                            type = ObjectType.loadClass(delegator.getEntityFieldType(modelEntity, modelField.getType()).getJavaType()).getName();
                        } catch (Exception e) {
                            Debug.logError(e, MODULE);
                        }

                        if (type == null) {
                            errorEntities.add(entityName);
                            Debug.logError("Error getting the type of the field " + modelField.getName() + " of entity " + entityName + " for delegator " + delegatorNameToUse, MODULE);
                            hasError = true;
                            break;
                        }

                        // make all Doubles BigDecimals -- in the entity model fieldtype XML files, all floating points are defined as Doubles
                        // and there is a GenericEntity.getBigDecimal method which converts them to BigDecimal.  We will make them all BigDecimals
                        // and then use the Entity.convertToBigDecimal method
                        if ("java.lang.Double".equals(type)) {
                            type = "java.math.BigDecimal";
                        }
                        pkFieldTypes.add(type);
                        // this is the short type: ie, java.lang.String -> String; java.util.HashMap -> HashMap, etc.
                        String shortType = type;
                        int idx = type.lastIndexOf(".");
                        if (idx > 0) {
                            shortType = type.substring(idx + 1);
                        }
                        pkTypes.put(modelField.getName(), shortType);
                    }
                    pkInfo.put("name", modelEntity.getEntityName() + "Pk");
                    pkInfo.put("primaryKeys", primaryKeys);
                    pkInfo.put("pkTypes", pkTypes);
                    pkInfo.put("getPkMethodNames", getPkMethodNames);
                    pkInfo.put("setPkMethodNames", setPkMethodNames);
                    pkInfo.put("pkFieldTypes", pkFieldTypes);
                    pkInfo.put("columnNames", columnNames);
                }
                if (pkInfo != null) {
                 // render pk class as FTL
                    Writer pkWriter = new StringWriter();
                    try {
                        FreeMarkerWorker.renderTemplateAtLocation(pkTemplate, pkInfo, pkWriter);
                    } catch (MalformedURLException e) {
                        Debug.logError(e, MODULE);
                        errorEntities.add(entityName);
                        break;
                    } catch (TemplateException e) {
                        Debug.logError(e, MODULE);
                        errorEntities.add(entityName);
                        break;
                    } catch (IOException e) {
                        Debug.logError(e, MODULE);
                        errorEntities.add(entityName);
                        break;
                    } catch (IllegalArgumentException e) {
                        Debug.logError(e, MODULE);
                        errorEntities.add(entityName);
                        break;
                    }

                    // write it as a Java file (PK)
                    File pkFile = new File(outputPath + entityName + "Pk" + fileExtension);
                    try {
                        FileUtils.writeStringToFile(pkFile, pkWriter.toString(), "UTF-8");
                    } catch (IOException e) {
                        Debug.logError(e, "Aborting, error writing file " + outputPath + entityName + "Pk" + fileExtension, MODULE);
                        errorEntities.add(entityName);
                        break;
                    }
                }
                // increment counter
                totalGeneratedClasses++;

            }

            // render hibernate.cfg.xml by FTL
            Writer cfgWriter = new StringWriter();
            Map<String, Object> cfgInfo = new HashMap<String, Object>();
            cfgInfo.put("entities", entities);
            try {
                FreeMarkerWorker.renderTemplateAtLocation(hibernateCfgTemplate, cfgInfo, cfgWriter);
            } catch (MalformedURLException e1) {
                Debug.logError(e1, MODULE);
            } catch (TemplateException e1) {
                Debug.logError(e1, MODULE);
            } catch (IOException e1) {
                Debug.logError(e1, MODULE);
            }
            // get hibernate.cfg.xml output path to use from the container configuration
            String hibernateCfgPath = null;
            if (hibernateCfgProp == null || hibernateCfgProp.value == null || hibernateCfgProp.value.length() == 0) {
                throw new ContainerException("Invalid hibernate cfg path defined in container configuration");
            } else {
                hibernateCfgPath = hibernateCfgProp.value;
            }
            // write it as a hibernate.cfg.xml
            File pkFile = new File(hibernateCfgPath);
            try {
                FileUtils.writeStringToFile(pkFile, cfgWriter.toString(), "UTF-8");
            } catch (IOException e) {
                Debug.logError(e, "Aborting, error writing file " + hibernateCfgPath, MODULE);
            }
        } else {
            Debug.logImportant("=-=-=-=-=-=-= No entity found.", MODULE);
        }

        // error summary
        if (errorEntities.size() > 0) {
            Debug.logImportant("The following entities could not be generated:", MODULE);
            for (String name : errorEntities) {
                Debug.logImportant(name, MODULE);
            }
        }

        Debug.logImportant("=-=-=-=-=-=-= Finished the POJO entities generation with " + totalGeneratedClasses + " classes generated.", MODULE);

        if (errorEntities.size() > 0) {
            return false;
        }

        return true;
    }




    /** {@inheritDoc} */
    public void stop() throws ContainerException { }

    /**
     * Standardize accessor method names.  If fieldName is orderId, will return "prefix" + OrderId.
     *
     * @param prefix prefix to the method name, for example "get" or "set"
     * @param fieldName name of the field the accessor method access
     * @return the accessor method name
     */
    public static String accessorMethodName(String prefix, String fieldName) {
        if (UtilValidate.isEmpty(fieldName)) {
            throw new IllegalArgumentException("methodName called for null or empty fieldName");
        } else {
            return prefix + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        }
    }

    /**
     * Standardize accessor collection method names.  If fieldName is items, will return "prefix" + Item.
     *
     * @param prefix prefix to the method name, for example "add" or "remove"
     * @param fieldName name of the field the accessor method access
     * @return the accessor method name
     */
    public static String accessorCollectionMethodName(String prefix, String fieldName) {
        if (UtilValidate.isEmpty(fieldName)) {
            throw new IllegalArgumentException("methodName called for null or empty fieldName");
        } else {
            return prefix + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1, fieldName.length() - 1);
        }
    }

    /**
     * Standardize getter method names.
     *
     * @param fieldName name of the field the accessor method access
     * @return the getter method name
     */
    public static String getterName(String fieldName) {
        return accessorMethodName("get", fieldName);
    }


    /**
     * Standardize setter method names.
     *
     * @param fieldName name of the field the accessor method access
     * @return the setter method name
     */
    public static String setterName(String fieldName) {
        return accessorMethodName("set", fieldName);
    }

    /**
     * Standardize add method of Collection property names.
     *
     * @param fieldName name of the field the add method access
     * @return the method name
     */
    public static String addName(String fieldName) {
        return accessorCollectionMethodName("add", fieldName);
    }

    /**
     * Standardize remove method of Collection property names.
     *
     * @param fieldName name of the field the remove method access
     * @return the method name
     */
    public static String removeName(String fieldName) {
        return accessorCollectionMethodName("remove", fieldName);
    }

    /**
     * Standardize clear method of Collection property names.
     *
     * @param fieldName name of the field the clear method access
     * @return the method name
     */
    public static String clearName(String fieldName) {
        return accessorCollectionMethodName("clear", fieldName);
    }

    /**
     * get item of Collection property names.
     *
     * @param fieldName name of the field the clear method access
     * @return the item field name
     */
    public static String itemName(String fieldName) {
        if (UtilValidate.isEmpty(fieldName)) {
            throw new IllegalArgumentException("methodName called for null or empty fieldName");
        } else {
            return fieldName.substring(0, fieldName.length() - 1);
        }
    }
}
