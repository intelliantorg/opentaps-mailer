/*
 * Copyright (c) 2009 - 2009 Open Source Strategies, Inc.
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
 * along with this program; if not, write to Fumble,
 * 643 Bair Island Road, Suite 305 - Redwood City, CA 94063, USA
 */
package org.opentaps.domain.organization;

import java.util.List;
import java.util.Map;

import javolution.util.FastMap;
import org.opentaps.domain.base.entities.Enumeration;
import org.opentaps.foundation.entity.Entity;
import org.opentaps.foundation.entity.EntityFieldInterface;
import org.opentaps.foundation.repository.RepositoryInterface;

/**
 * A virtual entity used to store a Tag configuration for an organization and usage type.
 */
public class AccountingTagConfigurationForOrganizationAndUsage extends Entity {

    public static enum Fields implements EntityFieldInterface<AccountingTagConfigurationForOrganizationAndUsage> {
        index("index"),
        type("type"),
        description("description"),
        tagValues("tagValues"),
        isRequired("isRequired");
        private final String fieldName;
        private Fields(String name) { fieldName = name; }
        /** {@inheritDoc} */
        public String getName() { return fieldName; }
        /** {@inheritDoc} */
        public String asc() { return fieldName + " ASC"; }
        /** {@inheritDoc} */
        public String desc() { return fieldName + " DESC"; }
    }

    private Integer index;
    private String type;
    private String description;
    private String isRequired;
    private List<Enumeration> tagValues;

    /**
     * Default constructor.
     */
    public AccountingTagConfigurationForOrganizationAndUsage() {
        super();
        this.baseEntityName = null;
        this.isView = true;
        this.isRequired = "N";
    }

    /**
     * Constructor with a repository.
     * @param repository a <code>RepositoryInterface</code> value
     */
    public AccountingTagConfigurationForOrganizationAndUsage(RepositoryInterface repository) {
        this();
        initRepository(repository);
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTagValues(List<Enumeration> tagValues) {
        this.tagValues = tagValues;
    }

    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    public Integer getIndex() {
        return index;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public List<Enumeration> getTagValues() {
        return tagValues;
    }

    public String getIsRequired() {
        return isRequired;
    }

    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setIndex((Integer) mapValue.get("index"));
        setType((String) mapValue.get("type"));
        setDescription((String) mapValue.get("description"));
        setTagValues((List<Enumeration>) mapValue.get("tagValues"));
        setIsRequired((String) mapValue.get("isRequired"));
        postInit();
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> mapValue = new FastMap<String, Object>();
        mapValue.put("index", getIndex());
        mapValue.put("type", getType());
        mapValue.put("description", getDescription());
        mapValue.put("tagValues", getTagValues());
        mapValue.put("isRequired", getIsRequired());
        return mapValue;
    }
}
