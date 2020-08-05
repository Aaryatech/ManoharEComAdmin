package com.ats.manoharadmin.models;

public class ItemRateIdBean {
	private int rateId;
	private int itemId;
	private float rate;
	private String rateName;
	
	public int getRateId() {
		return rateId;
	}
	public void setRateId(int rateId) {
		this.rateId = rateId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public String getRateName() {
		return rateName;
	}
	public void setRateName(String rateName) {
		this.rateName = rateName;
	}
	@Override
	public String toString() {
		return "ItemRateIdBean [rateId=" + rateId + ", itemId=" + itemId + ", rate=" + rate + ", rateName=" + rateName
				+ "]";
	}
		
}
