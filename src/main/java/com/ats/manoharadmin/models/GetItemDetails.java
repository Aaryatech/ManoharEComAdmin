package com.ats.manoharadmin.models;

public class GetItemDetails {

	private int itemId;
	private String itemCode;
	private String itemName;
	private int cntSkuId;
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getCntSkuId() {
		return cntSkuId;
	}
	public void setCntSkuId(int cntSkuId) {
		this.cntSkuId = cntSkuId;
	}
	@Override
	public String toString() {
		return "GetItemDetails [itemId=" + itemId + ", itemCode=" + itemCode + ", itemName=" + itemName + ", cntSkuId="
				+ cntSkuId + "]";
	}
	
	
}
