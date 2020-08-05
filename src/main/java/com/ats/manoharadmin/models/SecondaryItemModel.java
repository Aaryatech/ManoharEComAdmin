package com.ats.manoharadmin.models;

public class SecondaryItemModel {

	private int secId;
	private String secName;
	private float qty;

	public int getSecId() {
		return secId;
	}

	public void setSecId(int secId) {
		this.secId = secId;
	}

	public String getSecName() {
		return secName;
	}

	public void setSecName(String secName) {
		this.secName = secName;
	}

	public float getQty() {
		return qty;
	}

	public void setQty(float qty) {
		this.qty = qty;
	}

	@Override
	public String toString() {
		return "SecondaryItemModel [secId=" + secId + ", secName=" + secName + ", qty=" + qty + "]";
	}

}
