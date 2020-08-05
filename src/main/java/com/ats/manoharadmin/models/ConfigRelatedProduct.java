package com.ats.manoharadmin.models;

public class ConfigRelatedProduct {
	
	private int relatedProductId;
	private int productId;
	private String configrRelatedProductIds;
	private int delStatus;
	private int isActive;
	private int makerUserId;
	private String makerDatetime;
	private String updatedDateTime;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;

	public int getRelatedProductId() {
		return relatedProductId;
	}

	public void setRelatedProductId(int relatedProductId) {
		this.relatedProductId = relatedProductId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getConfigrRelatedProductIds() {
		return configrRelatedProductIds;
	}

	public void setConfigrRelatedProductIds(String configrRelatedProductIds) {
		this.configrRelatedProductIds = configrRelatedProductIds;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getMakerUserId() {
		return makerUserId;
	}

	public void setMakerUserId(int makerUserId) {
		this.makerUserId = makerUserId;
	}

	public String getMakerDatetime() {
		return makerDatetime;
	}

	public void setMakerDatetime(String makerDatetime) {
		this.makerDatetime = makerDatetime;
	}

	public String getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(String updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public int getExInt1() {
		return exInt1;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}

	public int getExInt2() {
		return exInt2;
	}

	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public String getExVar2() {
		return exVar2;
	}

	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}

	@Override
	public String toString() {
		return "ConfigRelatedProduct [relatedProductId=" + relatedProductId + ", productId=" + productId
				+ ", configrRelatedProductIds=" + configrRelatedProductIds + ", delStatus=" + delStatus + ", isActive="
				+ isActive + ", makerUserId=" + makerUserId + ", makerDatetime=" + makerDatetime + ", updatedDateTime="
				+ updatedDateTime + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2="
				+ exVar2 + "]";
	}
	
	
}
