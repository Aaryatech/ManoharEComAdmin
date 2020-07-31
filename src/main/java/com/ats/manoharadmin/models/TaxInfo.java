package com.ats.manoharadmin.models;

public class TaxInfo {
	
	private int taxInfoId;
	private String taxTitle;
	private String hsn;	
	private float sgstPer;
	private float cgstPer;
	private float igstPer;	
	private int delStatus;
	private int companyId;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;

	public int getTaxInfoId() {
		return taxInfoId;
	}

	public void setTaxInfoId(int taxInfoId) {
		this.taxInfoId = taxInfoId;
	}

	public String getTaxTitle() {
		return taxTitle;
	}

	public void setTaxTitle(String taxTitle) {
		this.taxTitle = taxTitle;
	}

	public String getHsn() {
		return hsn;
	}

	public void setHsn(String hsn) {
		this.hsn = hsn;
	}

	public float getSgstPer() {
		return sgstPer;
	}

	public void setSgstPer(float sgstPer) {
		this.sgstPer = sgstPer;
	}

	public float getCgstPer() {
		return cgstPer;
	}

	public void setCgstPer(float cgstPer) {
		this.cgstPer = cgstPer;
	}

	public float getIgstPer() {
		return igstPer;
	}

	public void setIgstPer(float igstPer) {
		this.igstPer = igstPer;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
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
		return "TaxInfo [taxInfoId=" + taxInfoId + ", taxTitle=" + taxTitle + ", hsn=" + hsn + ", sgstPer=" + sgstPer
				+ ", cgstPer=" + cgstPer + ", igstPer=" + igstPer + ", delStatus=" + delStatus + ", companyId="
				+ companyId + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2
				+ "]";
	}
	
	
}
