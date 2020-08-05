package com.ats.manoharadmin.models;

public class GetProductRelatedList {
	
	private int relatedProductId;
	private int productId;
	private String productName;
	private String relatedProductIds;
	private String relatedProduct;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getRelatedProductIds() {
		return relatedProductIds;
	}
	public void setRelatedProductIds(String relatedProductIds) {
		this.relatedProductIds = relatedProductIds;
	}
	public String getRelatedProduct() {
		return relatedProduct;
	}
	public void setRelatedProduct(String relatedProduct) {
		this.relatedProduct = relatedProduct;
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
		return "GetProductRelatedList [relatedProductId=" + relatedProductId + ", productId=" + productId
				+ ", productName=" + productName + ", relatedProductIds=" + relatedProductIds + ", relatedProduct="
				+ relatedProduct + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + "]";
	}
	
}
