package com.ats.manoharadmin.models;

public class SkuItemDtl {
	
	private int skuDetailId;
	private float rateTypeMrp;
	private int rateTypeId;
	private int skuId;
	private int skuRateTypeId;
	private String rateTypeName;
	
	public int getSkuRateTypeId() {
		return skuRateTypeId;
	}
	public void setSkuRateTypeId(int skuRateTypeId) {
		this.skuRateTypeId = skuRateTypeId;
	}
	public String getRateTypeName() {
		return rateTypeName;
	}
	public void setRateTypeName(String rateTypeName) {
		this.rateTypeName = rateTypeName;
	}
	public int getSkuDetailId() {
		return skuDetailId;
	}
	public void setSkuDetailId(int skuDetailId) {
		this.skuDetailId = skuDetailId;
	}
	public float getRateTypeMrp() {
		return rateTypeMrp;
	}
	public void setRateTypeMrp(float rateTypeMrp) {
		this.rateTypeMrp = rateTypeMrp;
	}
	public int getRateTypeId() {
		return rateTypeId;
	}
	public void setRateTypeId(int rateTypeId) {
		this.rateTypeId = rateTypeId;
	}
	public int getSkuId() {
		return skuId;
	}
	public void setSkuId(int skuId) {
		this.skuId = skuId;
	}
	@Override
	public String toString() {
		return "SkuItemDtl [skuDetailId=" + skuDetailId + ", rateTypeMrp=" + rateTypeMrp + ", rateTypeId=" + rateTypeId
				+ ", skuId=" + skuId + "]";
	}
	
	
	
}
