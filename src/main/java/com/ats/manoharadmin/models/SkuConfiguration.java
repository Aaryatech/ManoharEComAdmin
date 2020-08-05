package com.ats.manoharadmin.models;

public class SkuConfiguration {	
	
	private int skuId;
	private int itemId;
	private String skuName;
	private int skuRateTypeId;
	private float skuRate;
	private int skuStockQty;
	private int saleUom;
	private String stockToSaleUom;
	private int delStatus;
	private int isActive;
	private int exInt1;
	private int exInt2;	
	private String exVar1;
	private String exVar2;
	private String exVar3;
	private float exFloat1;
	private float exFloat2;
	private int companyId;

	public int getSkuId() {
		return skuId;
	}

	public void setSkuId(int skuId) {
		this.skuId = skuId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public int getSkuRateTypeId() {
		return skuRateTypeId;
	}

	public void setSkuRateTypeId(int skuRateTypeId) {
		this.skuRateTypeId = skuRateTypeId;
	}

	public float getSkuRate() {
		return skuRate;
	}

	public void setSkuRate(float skuRate) {
		this.skuRate = skuRate;
	}

	public int getSkuStockQty() {
		return skuStockQty;
	}

	public void setSkuStockQty(int skuStockQty) {
		this.skuStockQty = skuStockQty;
	}

	public int getSaleUom() {
		return saleUom;
	}

	public void setSaleUom(int saleUom) {
		this.saleUom = saleUom;
	}

	public String getStockToSaleUom() {
		return stockToSaleUom;
	}

	public void setStockToSaleUom(String stockToSaleUom) {
		this.stockToSaleUom = stockToSaleUom;
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

	public String getExVar3() {
		return exVar3;
	}

	public void setExVar3(String exVar3) {
		this.exVar3 = exVar3;
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

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "SkuConfiguration [skuId=" + skuId + ", itemId=" + itemId + ", skuName=" + skuName + ", skuRateTypeId="
				+ skuRateTypeId + ", skuRate=" + skuRate + ", skuStockQty=" + skuStockQty + ", saleUom=" + saleUom
				+ ", stockToSaleUom=" + stockToSaleUom + ", delStatus=" + delStatus + ", isActive=" + isActive
				+ ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2
				+ ", exVar3=" + exVar3 + ", exFloat1=" + exFloat1 + ", exFloat2=" + exFloat2 + ", companyId="
				+ companyId + "]";
	}
	
	
}
