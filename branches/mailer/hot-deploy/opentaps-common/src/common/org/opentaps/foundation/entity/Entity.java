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

// A small part of this file came from Apache Ofbiz and has been modified by Open Source Strategies, Inc.

package org.opentaps.foundation.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.opentaps.foundation.repository.RepositoryException;
import org.opentaps.foundation.repository.RepositoryInterface;

/** {@inheritDoc} */
public class Entity implements EntityInterface {

    private static final String MODULE = Entity.class.getName();

    /** List the fields used by the Ofbiz entity engine. */
    public static final List<String> STAMP_FIELDS = Arrays.asList("lastUpdatedStamp", "lastUpdatedTxStamp", "createdStamp", "createdTxStamp");

    protected boolean isView = false;
    protected String baseEntityName = "NOT CONFIGURED";
    protected String resourceName;
    protected List<String> primaryKeyNames;
    protected RepositoryInterface repository;

    /** Map the fields used by the hibernate view entity engine. */
    public static Map<String, Map<String, String>> fieldMapColumns = new TreeMap<String, Map<String, String>>();

    /**
     * This method is called before object fields receive values in <code>fromMap()</code><br/>
     * Default implementation does nothing.
     */
    protected void preInit() { }

    /** {@inheritDoc} */
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        postInit();
        // this should be extended by the sub-classes to match their actual fields to a Map
    }

    /** {@inheritDoc} */
    public void fromEntity(EntityInterface entity) {
        fromMap(entity.toMap());
    }

    /**
     * This method is called after object fields receive values in <code>fromMap()</code><br/>
     * Default implementation does nothing.
     */
    protected void postInit() { }

    /** {@inheritDoc} */
    public Map<String, Object> toMap() {
        // this should also be extended by the sub-classes to match their actual fields to a Map
        return new HashMap<String, Object>();
    }

    /**
     * Returns a <code>Map</code> with only the listed fields.
     * One application is to build a Set of entities that have distinct values for a sub set of their fields
     * @param fields list of field names to include
     * @return a <code>Map</code> with only the listed fields
     * @see #getDistinctFieldValues
     */
    public Map<String, Object> toMap(Iterable<String> fields) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (String field : fields) {
            map.put(field, get(field));
        }
        return map;
    }

    /**
     * Returns a <code>Map</code> with of this entity without the Ofbiz fields.
     * @return a <code>Map</code> with of this entity without the Ofbiz fields
     */
    public Map<String, Object> toMapNoStamps() {
        Map<String, Object> map = toMap();
        for (String field : STAMP_FIELDS) {
            map.remove(field);
        }
        return map;
    }

    /**
     * Builds a <code>Set</code> of entities that have distinct values for a sub set of their fields.
     * The resulting <code>Set</code> values are ordered the same as the input list of entities.
     * @param entities a list of entities
     * @param fields a list of fields to consider when comparing the entities
     * @return the resulting <code>Set</code> of entities that are distinct for the given list of fields
     */
    public static Set<Map<String, Object>> getDistinctFieldValues(Iterable<? extends Entity> entities, Iterable<String> fields) {
        Set<Map<String, Object>> distinctEntities = new LinkedHashSet<Map<String, Object>>();
        for (Entity e : entities) {
            distinctEntities.add(e.toMap(fields));
        }
        return distinctEntities;
    }

    /**
     * Builds a <code>Set</code> of distinct entity values for one their fields.
     * The resulting <code>Set</code> values are ordered the same as the input list of entities.
     * @param <T> the entity class
     * @param entities a list of entities
     * @param field the field to consider when comparing the entities
     * @return the resulting <code>Set</code> of entities that are distinct for the given field
     */
    public static <T extends EntityInterface> Set<Object> getDistinctFieldValues(Iterable<T> entities, EntityFieldInterface<? super T> field) {
        Set<Object> distinctValues = new LinkedHashSet<Object>();
        for (EntityInterface e : entities) {
            distinctValues.add(e.get(field.getName()));
        }
        return distinctValues;
    }

    /**
     * Builds a <code>Set</code> of distinct entity values for one their fields.
     * The resulting <code>Set</code> values are ordered the same as the input list of entities.
     * @param <T> the entity class
     * @param <T2> the field class
     * @param fieldType the field class
     * @param entities a list of entities
     * @param field the field to consider when comparing the entities
     * @return the resulting <code>Set</code> of entities that are distinct for the given field
     */
    public static <T extends EntityInterface, T2 extends Object> Set<T2> getDistinctFieldValues(Class<T2> fieldType, Iterable<T> entities, EntityFieldInterface<? super T> field) {
        return getDistinctFieldValues(fieldType, entities, field.getName());
    }

    /**
     * Builds a <code>Set</code> of distinct entity values for one their fields.
     * The resulting <code>Set</code> values are ordered the same as the input list of entities.
     * @param <T> the entity class
     * @param <T2> the field class
     * @param fieldType the field class
     * @param entities a list of entities
     * @param fieldName the field to consider when comparing the entities
     * @return the resulting <code>Set</code> of entities that are distinct for the given field
     */
    @SuppressWarnings("unchecked")
    public static <T extends EntityInterface, T2 extends Object> Set<T2> getDistinctFieldValues(Class<T2> fieldType, Iterable<T> entities, String fieldName) {
        Set<T2> distinctValues = new LinkedHashSet<T2>();
        for (EntityInterface e : entities) {
            distinctValues.add((T2) e.get(fieldName));
        }
        return distinctValues;
    }

    /**
     * Builds a <code>List</code> of entity values for one their fields.
     * The resulting <code>List</code> is ordered the same as the input list of entities.
     * @param <T> the entity class
     * @param entities a list of entities
     * @param field the field to get
     * @return the resulting <code>List</code> of field values
     */
    public static <T extends EntityInterface> List<Object> getFieldValues(Iterable<T> entities, EntityFieldInterface<? super T> field) {
        List<Object> values = new ArrayList<Object>();
        for (EntityInterface e : entities) {
            values.add(e.get(field.getName()));
        }
        return values;
    }

    /**
     * Builds a <code>List</code> of entity values for one their fields.
     * The resulting <code>List</code> is ordered the same as the input list of entities.
     * @param <T> the entity class
     * @param <T2> the field class
     * @param fieldType the field class
     * @param entities a list of entities
     * @param field the field to get
     * @return the resulting <code>List</code> of field values
     */
    public static <T extends EntityInterface, T2 extends Object> List<T2> getFieldValues(Class<T2> fieldType, Iterable<T> entities, EntityFieldInterface<? super T> field) {
        return getFieldValues(fieldType, entities, field.getName());
    }

    /**
     * Builds a <code>List</code> of entity values for one their fields.
     * The resulting <code>List</code> is ordered the same as the input list of entities.
     * @param <T> the entity class
     * @param <T2> the field class
     * @param fieldType the field class
     * @param entities a list of entities
     * @param fieldName the field to get
     * @return the resulting <code>List</code> of field values
     */
    @SuppressWarnings("unchecked")
    public static <T extends EntityInterface, T2 extends Object> List<T2> getFieldValues(Class<T2> fieldType, Iterable<T> entities, String fieldName) {
        List<T2> distinctValues = new ArrayList<T2>();
        for (EntityInterface e : entities) {
            distinctValues.add((T2) e.get(fieldName));
        }
        return distinctValues;
    }

    /** {@inheritDoc} */
    public Object get(String fieldName) {
        return toMap().get(fieldName);
    }

    /**
     * Provides backward compatibility with ofbiz way of localizing entity strings
     * by returning localized value of field fieldName for null resource.
     * @param fieldName the field to retrieve
     * @param locale the user locale
     * @return the value for that field, localized if possible
    */
    public Object get(String fieldName, Locale locale) {
        return get(fieldName, null, locale);
    }

    /**
     * Provides backward compatibility with ofbiz way of localizing entity strings
     * by returning the localized value of the field with fieldName (ie, "invoiceTypeId") with resource (ie, "FinancialsUiLabels").
     * @param fieldName the field to retrieve
     * @param resource a specific resource file to use
     * @param locale the user locale
     * @return the value for that field, localized if poss
     */
    public Object get(String fieldName, String resource, Locale locale) {
        Object fieldValue = null;
        try {
            fieldValue = get(fieldName);
        } catch (IllegalArgumentException e) {
            fieldValue = null;
        }

        if (UtilValidate.isEmpty(resource)) {
            resource = getResourceName();
            // still empty? return the fieldValue
            if (UtilValidate.isEmpty(resource)) {
                //Debug.logError("Empty resource name for entity " + getBaseEntityName(), module);
                return fieldValue;
            }
        }

        ResourceBundle bundle = null;
        try {
            bundle = UtilProperties.getResourceBundle(resource, locale);
        } catch (IllegalArgumentException e) {
            bundle = null;
        }
        if (bundle == null) {
            Debug.logWarning("Tried to getResource value for field named " + fieldName + " but no resource was found with the name " + resource + " in the locale " + locale, MODULE);
            return fieldValue;
        }

        StringBuffer keyBuffer = new StringBuffer();
        // start with the Entity Name
        keyBuffer.append(getBaseEntityName());
        // next add the Field Name
        keyBuffer.append('.');
        keyBuffer.append(fieldName);
        // finish off by adding the PK or the value if no PK is set
        if (getPrimaryKeyNames() != null) {
            for (String pk : getPrimaryKeyNames()) {
                keyBuffer.append('.');
                keyBuffer.append(get(pk));
            }
        } else {
            keyBuffer.append('.');
            keyBuffer.append(fieldValue);
        }

        String bundleKey = keyBuffer.toString();

        Object resourceValue = null;
        try {
            resourceValue = bundle.getObject(bundleKey);
        } catch (MissingResourceException e) {
            Debug.logWarning("Could not find resource value : " + bundleKey, MODULE);
        }
        if (resourceValue == null) {
            return fieldValue;
        } else {
            return resourceValue;
        }
    }

    /** {@inheritDoc} */
    public String getString(String fieldName) {
        return (String) get(fieldName);
    }

    /** {@inheritDoc} */
    public Boolean getBoolean(String fieldName) {
        return (Boolean) get(fieldName);
    }

    /** {@inheritDoc} */
    public Double getDouble(String fieldName) {
        return (Double) get(fieldName);
    }

    /** {@inheritDoc} */
    public Float getFloat(String fieldName) {
        return (Float) get(fieldName);
    }

    /** {@inheritDoc} */
    public Long getLong(String fieldName) {
        return (Long) get(fieldName);
    }

    /** {@inheritDoc} */
    public BigDecimal getBigDecimal(String fieldName) {
        return (BigDecimal) get(fieldName);
    }

    /** {@inheritDoc} */
    public Timestamp getTimestamp(String fieldName) {
        return (Timestamp) get(fieldName);
    }

    /** {@inheritDoc} */
    public void set(String fieldName, Object value) {
        // get the Map of all fields, change the one we're setting, and then set all fields to the modified map
        Map<String, Object> mapValue = toMap();
        mapValue.put(fieldName, value);
        fromMap(mapValue);
    }

    /**
     * This is a special method for converting a <code>Map</code> field's value to <code>BigDecimal</code>, because the ofbiz entity engine defines
     * floating point types as <code>Double</code>, so by default they are cast as <code>Double</code>.
     * @param value the value object to convert to a <code>BigDecimal</code>
     * @return the <code>BigDecimal</code> value or <code>null</code>
     */
    public BigDecimal convertToBigDecimal(Object value) {
        if (value instanceof Double) {
            return BigDecimal.valueOf(((Double) value).doubleValue());
        } else if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else {
            // this should not happen
            return null;
        }
    }

    /** {@inheritDoc} */
    public void initRepository(RepositoryInterface repository) {
        this.repository = repository;
    }

    /** {@inheritDoc} */
    public RepositoryInterface getBaseRepository() {
        return this.repository;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        return hashCode() == obj.hashCode();
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int hash = 5381;

        Map<String, Object> fields = toMap();
        assert fields != null;

        // build reference string
        String str = getClass().getName();
        Iterator<Object> iterator = fields.values().iterator();
        while (iterator.hasNext()) {
            Object s = iterator.next();
            if (s != null) {
                str += s.toString();
            }
        }

        // calculate hash of string
        for (int i = 0; i < str.length(); i++) {
           hash = ((hash << 5) + hash) + str.charAt(i);
        }

        return hash;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return ("Entity [" + getBaseEntityName() + "] with fields " + toMap());
    }

    /** {@inheritDoc} */
    public String getBaseEntityName() {
        return baseEntityName;
    }

    /**
     * Gets the resource used to localized values for this entity.
     * @return the resource name
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * Sets the resource used to localized values for this entity.
     * @param resourceName the resource name
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /** {@inheritDoc} */
    public boolean isView() {
        return isView;
    }

    /**
     * Get the list of fields forming the primary key for this entity.
     * @return the list of field names forming the primary key
     */
    public List<String> getPrimaryKeyNames() {
        return primaryKeyNames;
    }

    /** {@inheritDoc} */
    public String getNextSeqId() {
        return repository.getNextSeqId(this);
    }

    /** {@inheritDoc} */
    public void setNextSubSeqId(String sequenceFieldName, int numericPadding) throws RepositoryException {
        setNextSubSeqId(sequenceFieldName, numericPadding, 1);
    }

    /** {@inheritDoc} */
    public void setNextSubSeqId(String sequenceFieldName, int numericPadding, int incrementBy) throws RepositoryException {
        set(sequenceFieldName, repository.getNextSubSeqId(this, sequenceFieldName, numericPadding, incrementBy));
    }

    // get related methods

    /**
     * Gets the entity related to this entity by the given relation.
     * @param relation the name of the relation between the two entities
     * @return an <code>EntityInterface</code> value
     * @throws RepositoryException if an error occurs
     */
    public EntityInterface getRelatedOne(String relation) throws RepositoryException {
        return repository.getRelatedOne(relation, this);
    }

    /**
     * Gets the entity related to this entity, where the relation name match the related entity name.
     * @param <T> class of entity to return
     * @param entityName the name the related entity
     * @return an <code>EntityInterface</code> value
     * @throws RepositoryException if an error occurs
     */
    public <T extends Entity> T getRelatedOne(Class<T> entityName) throws RepositoryException {
        return repository.getRelatedOne(entityName, this);
    }

    /**
     * Gets the entity related to this entity, given both the related entity name and the relation name.
     * @param <T> class of entity to return
     * @param entityName the name the related entity
     * @param relation the name of the relation between the two entities
     * @return an <code>EntityInterface</code> value
     * @throws RepositoryException if an error occurs
     */
    public <T extends Entity> T getRelatedOne(Class<T> entityName, String relation) throws RepositoryException {
        return repository.getRelatedOne(entityName, relation, this);
    }

    /**
     * Gets the entity related to this entity by the given relation using the cache.
     * @param relation the name of the relation between the two entities
     * @return an <code>EntityInterface</code> value
     * @throws RepositoryException if an error occurs
     */
    public EntityInterface getRelatedOneCache(String relation) throws RepositoryException {
        return repository.getRelatedOneCache(relation, this);
    }

    /**
     * Gets the entity related to this entity using the cache, where the relation name match the related entity name.
     * @param <T> class of entity to return
     * @param entityName the name the related entity
     * @return an <code>EntityInterface</code> value
     * @throws RepositoryException if an error occurs
     */
    public <T extends Entity> T getRelatedOneCache(Class<T> entityName) throws RepositoryException {
        return repository.getRelatedOneCache(entityName, this);
    }

    /**
     * Gets the entity related to this entity using the cache, given both the related entity name and the relation name.
     * @param <T> class of entity to return
     * @param entityName the name the related entity
     * @param relation the name of the relation between the two entities
     * @return an <code>EntityInterface</code> value
     * @throws RepositoryException if an error occurs
     */
    public <T extends Entity> T getRelatedOneCache(Class<T> entityName, String relation) throws RepositoryException {
        return repository.getRelatedOneCache(entityName, relation, this);
    }

    /**
     * Gets the list of related entities to this entity by the given relation name.
     * @param relation the name of the relation between the two entities
     * @return a list of <code>EntityInterface</code> value
     * @throws RepositoryException if an error occurs
     */
    public List<? extends EntityInterface> getRelated(String relation) throws RepositoryException {
        return repository.getRelated(relation, this);
    }

    /**
     * Gets the list of related entities to this entity, where the relation name match the related entity name.
     * @param <T> class of entity to return
     * @param entityName the name of the related entities
     * @return a list of <code>EntityInterface</code> value
     * @throws RepositoryException if an error occurs
     */
    public <T extends Entity> List<T> getRelated(Class<T> entityName) throws RepositoryException {
        return repository.getRelated(entityName, this);
    }

    /**
     * Gets the list of related entities to this entity, where the relation name match the related entity name.
     * @param <T> class of entity to return
     * @param entityName the name of the related entities
     * @param orderBy the fields of the related entity to order the query by; may be null; optionally add a " ASC" for ascending or " DESC" for descending
     * @return a list of <code>EntityInterface</code> value
     * @throws RepositoryException if an error occurs
     */
    public <T extends Entity> List<T> getRelated(Class<T> entityName, List<String> orderBy) throws RepositoryException {
        return repository.getRelated(entityName, this, orderBy);
    }

    /**
     * Gets the list of related entities to this entity, given both the related entity name and the relation name.
     * @param <T> class of entity to return
     * @param entityName the name of the related entities
     * @param relation the name of the relation between the entities
     * @return a list of <code>EntityInterface</code> value
     * @throws RepositoryException if an error occurs
     */
    public <T extends Entity> List<T> getRelated(Class<T> entityName, String relation) throws RepositoryException {
        return repository.getRelated(entityName, relation, this);
    }

    /**
     * Gets the list of related entities to this entity, given both the related entity name and the relation name.
     * @param <T> class of entity to return
     * @param entityName the name of the related entities
     * @param relation the name of the relation between the entities
     * @param orderBy the fields of the related entity to order the query by; may be null; optionally add a " ASC" for ascending or " DESC" for descending
     * @return a list of <code>EntityInterface</code> value
     * @throws RepositoryException if an error occurs
     */
    public <T extends Entity> List<T> getRelated(Class<T> entityName, String relation, List<String> orderBy) throws RepositoryException {
        return repository.getRelated(entityName, relation, this, orderBy);
    }

}
