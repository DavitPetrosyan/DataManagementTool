package com.davit.ymlparser.model;

import java.util.List;

/**
 * author by davitpetrosyan on 11/29/17.
 */
public class Offer {

	private String id;
	private Integer groupId;
	private Boolean isAvailable;

	private Double price;
	private String currencyId;
	private Integer categoryId;
	private String name;
	private List<String> pictureURLs;
	private String description;
	private String vendor;
	private String vendorCode;
	private Integer stock;
	private List<DetailedParam> params;



	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getPictureURLs() {
		return pictureURLs;
	}

	public void setPictureURLs(List<String> pictureURLs) {
		this.pictureURLs = pictureURLs;
	}

	public List<DetailedParam> getParams() {
		return params;
	}

	public void setParams(List<DetailedParam> params) {
		this.params = params;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
}
