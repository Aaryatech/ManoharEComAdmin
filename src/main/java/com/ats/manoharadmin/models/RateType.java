package com.ats.manoharadmin.models;

public class RateType {
	
	private int skuRateTypeId;
	private String rateTypeName;
	private int companyId;
	private int delStatus;
	private int exInt1;
	private int exInt2;
	private float exFloat1;
	private float exFloat2;
	private String exVar1;
	private String exVar2;
	private String exVar3;

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

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
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

	public float getExFloat1() {
		return exFloat1;
	}

	public void setExFloat1(float exFloat1) {
		this.exFloat1 = exFloat1;
	}

	public float getExFloat2() {
		return exFloat2;
	}

	public void setExFloat2(float exFloat2) {
		this.exFloat2 = exFloat2;
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

	public String getExVar3() {
		return exVar3;
	}

	public void setExVar3(String exVar3) {
		this.exVar3 = exVar3;
	}

	@Override
	public String toString() {
		return "RateType [skuRateTypeId=" + skuRateTypeId + ", rateTypeName=" + rateTypeName + ", companyId="
				+ companyId + ", delStatus=" + delStatus + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exFloat1="
				+ exFloat1 + ", exFloat2=" + exFloat2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exVar3="
				+ exVar3 + "]";
	}
	
	
}
