package org.opentaps.domain.base.entities;

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

// DO NOT EDIT THIS FILE!  THIS IS AUTO GENERATED AND WILL GET WRITTEN OVER PERIODICALLY WHEN THE DATA MODEL CHANGES
// EXTEND THIS CLASS INSTEAD.

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javolution.util.FastMap;

import org.opentaps.foundation.entity.Entity;
import org.opentaps.foundation.entity.EntityFieldInterface;
import org.opentaps.foundation.repository.RepositoryException;
import org.opentaps.foundation.repository.RepositoryInterface;
import javax.persistence.*;
import java.lang.String;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Auto generated base entity ShoppingListItem.
 */
@javax.persistence.Entity
@Table(name="SHOPPING_LIST_ITEM")
@IdClass(ShoppingListItemPk.class)
public class ShoppingListItem extends Entity {
static {
java.util.Map<String, String> fields = new java.util.HashMap<String, String>();
        fields.put("shoppingListId", "SHOPPING_LIST_ID");
        fields.put("shoppingListItemSeqId", "SHOPPING_LIST_ITEM_SEQ_ID");
        fields.put("productId", "PRODUCT_ID");
        fields.put("quantity", "QUANTITY");
        fields.put("reservStart", "RESERV_START");
        fields.put("reservLength", "RESERV_LENGTH");
        fields.put("reservPersons", "RESERV_PERSONS");
        fields.put("quantityPurchased", "QUANTITY_PURCHASED");
        fields.put("lastUpdatedStamp", "LAST_UPDATED_STAMP");
        fields.put("lastUpdatedTxStamp", "LAST_UPDATED_TX_STAMP");
        fields.put("createdStamp", "CREATED_STAMP");
        fields.put("createdTxStamp", "CREATED_TX_STAMP");
fieldMapColumns.put("ShoppingListItem", fields);
}
  public static enum Fields implements EntityFieldInterface<ShoppingListItem> {
    shoppingListId("shoppingListId"),
    shoppingListItemSeqId("shoppingListItemSeqId"),
    productId("productId"),
    quantity("quantity"),
    reservStart("reservStart"),
    reservLength("reservLength"),
    reservPersons("reservPersons"),
    quantityPurchased("quantityPurchased"),
    lastUpdatedStamp("lastUpdatedStamp"),
    lastUpdatedTxStamp("lastUpdatedTxStamp"),
    createdStamp("createdStamp"),
    createdTxStamp("createdTxStamp");
    private final String fieldName;
    private Fields(String name) { fieldName = name; }
    /** {@inheritDoc} */
    public String getName() { return fieldName; }
    /** {@inheritDoc} */
    public String asc() { return fieldName + " ASC"; }
    /** {@inheritDoc} */
    public String desc() { return fieldName + " DESC"; }
  }

    @Id
    
    @Column(name="SHOPPING_LIST_ID")
    private String shoppingListId;
    @Id
    
    @Column(name="SHOPPING_LIST_ITEM_SEQ_ID")
    private String shoppingListItemSeqId;
    
    @Column(name="PRODUCT_ID")
    private String productId;
    
    @Column(name="QUANTITY")
    private BigDecimal quantity;
    
    @Column(name="RESERV_START")
    private Timestamp reservStart;
    
    @Column(name="RESERV_LENGTH")
    private BigDecimal reservLength;
    
    @Column(name="RESERV_PERSONS")
    private BigDecimal reservPersons;
    
    @Column(name="QUANTITY_PURCHASED")
    private BigDecimal quantityPurchased;
    
    @Column(name="LAST_UPDATED_STAMP")
    private Timestamp lastUpdatedStamp;
    
    @Column(name="LAST_UPDATED_TX_STAMP")
    private Timestamp lastUpdatedTxStamp;
    
    @Column(name="CREATED_STAMP")
    private Timestamp createdStamp;
    
    @Column(name="CREATED_TX_STAMP")
    private Timestamp createdTxStamp;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="SHOPPING_LIST_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private ShoppingList shoppingList = null;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="PRODUCT_ID", insertable=false, updatable=false)
    @org.hibernate.annotations.Generated(
        org.hibernate.annotations.GenerationTime.ALWAYS
    )
    private Product product = null;
    private transient List<ShoppingListItemSurvey> shoppingListItemSurveys = null;

  /**
   * Default constructor.
   */
  public ShoppingListItem() {
      super();
      this.baseEntityName = "ShoppingListItem";
      this.isView = false;
      
      this.primaryKeyNames = new ArrayList<String>();
      this.primaryKeyNames.add("shoppingListId");this.primaryKeyNames.add("shoppingListItemSeqId");
  }

  /**
   * Constructor with a repository.
   * @param repository a <code>RepositoryInterface</code> value
   */
  public ShoppingListItem(RepositoryInterface repository) {
      this();
      initRepository(repository);
  }

    /**
     * Auto generated value setter.
     * @param shoppingListId the shoppingListId to set
     */
    public void setShoppingListId(String shoppingListId) {
        this.shoppingListId = shoppingListId;
    }
    /**
     * Auto generated value setter.
     * @param shoppingListItemSeqId the shoppingListItemSeqId to set
     */
    public void setShoppingListItemSeqId(String shoppingListItemSeqId) {
        this.shoppingListItemSeqId = shoppingListItemSeqId;
    }
    /**
     * Auto generated value setter.
     * @param productId the productId to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }
    /**
     * Auto generated value setter.
     * @param quantity the quantity to set
     */
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
    /**
     * Auto generated value setter.
     * @param reservStart the reservStart to set
     */
    public void setReservStart(Timestamp reservStart) {
        this.reservStart = reservStart;
    }
    /**
     * Auto generated value setter.
     * @param reservLength the reservLength to set
     */
    public void setReservLength(BigDecimal reservLength) {
        this.reservLength = reservLength;
    }
    /**
     * Auto generated value setter.
     * @param reservPersons the reservPersons to set
     */
    public void setReservPersons(BigDecimal reservPersons) {
        this.reservPersons = reservPersons;
    }
    /**
     * Auto generated value setter.
     * @param quantityPurchased the quantityPurchased to set
     */
    public void setQuantityPurchased(BigDecimal quantityPurchased) {
        this.quantityPurchased = quantityPurchased;
    }
    /**
     * Auto generated value setter.
     * @param lastUpdatedStamp the lastUpdatedStamp to set
     */
    public void setLastUpdatedStamp(Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    /**
     * Auto generated value setter.
     * @param lastUpdatedTxStamp the lastUpdatedTxStamp to set
     */
    public void setLastUpdatedTxStamp(Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    /**
     * Auto generated value setter.
     * @param createdStamp the createdStamp to set
     */
    public void setCreatedStamp(Timestamp createdStamp) {
        this.createdStamp = createdStamp;
    }
    /**
     * Auto generated value setter.
     * @param createdTxStamp the createdTxStamp to set
     */
    public void setCreatedTxStamp(Timestamp createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }

    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getShoppingListId() {
        return this.shoppingListId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getShoppingListItemSeqId() {
        return this.shoppingListItemSeqId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>String</code>
     */
    public String getProductId() {
        return this.productId;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getQuantity() {
        return this.quantity;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getReservStart() {
        return this.reservStart;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getReservLength() {
        return this.reservLength;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getReservPersons() {
        return this.reservPersons;
    }
    /**
     * Auto generated value accessor.
     * @return <code>BigDecimal</code>
     */
    public BigDecimal getQuantityPurchased() {
        return this.quantityPurchased;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getLastUpdatedStamp() {
        return this.lastUpdatedStamp;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getLastUpdatedTxStamp() {
        return this.lastUpdatedTxStamp;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getCreatedStamp() {
        return this.createdStamp;
    }
    /**
     * Auto generated value accessor.
     * @return <code>Timestamp</code>
     */
    public Timestamp getCreatedTxStamp() {
        return this.createdTxStamp;
    }

    /**
     * Auto generated method that gets the related <code>ShoppingList</code> by the relation named <code>ShoppingList</code>.
     * @return the <code>ShoppingList</code>
     * @throws RepositoryException if an error occurs
     */
    public ShoppingList getShoppingList() throws RepositoryException {
        if (this.shoppingList == null) {
            this.shoppingList = getRelatedOne(ShoppingList.class, "ShoppingList");
        }
        return this.shoppingList;
    }
    /**
     * Auto generated method that gets the related <code>Product</code> by the relation named <code>Product</code>.
     * @return the <code>Product</code>
     * @throws RepositoryException if an error occurs
     */
    public Product getProduct() throws RepositoryException {
        if (this.product == null) {
            this.product = getRelatedOne(Product.class, "Product");
        }
        return this.product;
    }
    /**
     * Auto generated method that gets the related <code>ShoppingListItemSurvey</code> by the relation named <code>ShoppingListItemSurvey</code>.
     * @return the list of <code>ShoppingListItemSurvey</code>
     * @throws RepositoryException if an error occurs
     */
    public List<? extends ShoppingListItemSurvey> getShoppingListItemSurveys() throws RepositoryException {
        if (this.shoppingListItemSurveys == null) {
            this.shoppingListItemSurveys = getRelated(ShoppingListItemSurvey.class, "ShoppingListItemSurvey");
        }
        return this.shoppingListItemSurveys;
    }

    /**
     * Auto generated value setter.
     * @param shoppingList the shoppingList to set
    */
    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }
    /**
     * Auto generated value setter.
     * @param product the product to set
    */
    public void setProduct(Product product) {
        this.product = product;
    }
    /**
     * Auto generated value setter.
     * @param shoppingListItemSurveys the shoppingListItemSurveys to set
    */
    public void setShoppingListItemSurveys(List<ShoppingListItemSurvey> shoppingListItemSurveys) {
        this.shoppingListItemSurveys = shoppingListItemSurveys;
    }


    /** {@inheritDoc} */
    @Override
    public void fromMap(Map<String, Object> mapValue) {
        preInit();
        setShoppingListId((String) mapValue.get("shoppingListId"));
        setShoppingListItemSeqId((String) mapValue.get("shoppingListItemSeqId"));
        setProductId((String) mapValue.get("productId"));
        setQuantity(convertToBigDecimal(mapValue.get("quantity")));
        setReservStart((Timestamp) mapValue.get("reservStart"));
        setReservLength(convertToBigDecimal(mapValue.get("reservLength")));
        setReservPersons(convertToBigDecimal(mapValue.get("reservPersons")));
        setQuantityPurchased(convertToBigDecimal(mapValue.get("quantityPurchased")));
        setLastUpdatedStamp((Timestamp) mapValue.get("lastUpdatedStamp"));
        setLastUpdatedTxStamp((Timestamp) mapValue.get("lastUpdatedTxStamp"));
        setCreatedStamp((Timestamp) mapValue.get("createdStamp"));
        setCreatedTxStamp((Timestamp) mapValue.get("createdTxStamp"));
        postInit();
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> mapValue = new FastMap<String, Object>();
        mapValue.put("shoppingListId", getShoppingListId());
        mapValue.put("shoppingListItemSeqId", getShoppingListItemSeqId());
        mapValue.put("productId", getProductId());
        mapValue.put("quantity", getQuantity());
        mapValue.put("reservStart", getReservStart());
        mapValue.put("reservLength", getReservLength());
        mapValue.put("reservPersons", getReservPersons());
        mapValue.put("quantityPurchased", getQuantityPurchased());
        mapValue.put("lastUpdatedStamp", getLastUpdatedStamp());
        mapValue.put("lastUpdatedTxStamp", getLastUpdatedTxStamp());
        mapValue.put("createdStamp", getCreatedStamp());
        mapValue.put("createdTxStamp", getCreatedTxStamp());
        return mapValue;
    }


}