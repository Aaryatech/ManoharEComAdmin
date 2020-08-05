package com.ats.manoharadmin.models;

public class ItemBuyGetFreeOffer {

	private int offerDetailId;
	private int primaryItemId;
	private String primaryItemName;
	private float primaryQty;
	private int secondaryItemId;
	private String secondaryItemName;
	private float secondaryQty;
	
	

	public ItemBuyGetFreeOffer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public ItemBuyGetFreeOffer(int offerDetailId, int primaryItemId, String primaryItemName, float primaryQty,
			int secondaryItemId, String secondaryItemName, float secondaryQty) {
		this.offerDetailId = offerDetailId;
		this.primaryItemId = primaryItemId;
		this.primaryItemName = primaryItemName;
		this.primaryQty = primaryQty;
		this.secondaryItemId = secondaryItemId;
		this.secondaryItemName = secondaryItemName;
		this.secondaryQty = secondaryQty;
	}



	public int getOfferDetailId() {
		return offerDetailId;
	}

	public void setOfferDetailId(int offerDetailId) {
		this.offerDetailId = offerDetailId;
	}

	public int getPrimaryItemId() {
		return primaryItemId;
	}

	public void setPrimaryItemId(int primaryItemId) {
		this.primaryItemId = primaryItemId;
	}

	public String getPrimaryItemName() {
		return primaryItemName;
	}

	public void setPrimaryItemName(String primaryItemName) {
		this.primaryItemName = primaryItemName;
	}

	public float getPrimaryQty() {
		return primaryQty;
	}

	public void setPrimaryQty(float primaryQty) {
		this.primaryQty = primaryQty;
	}

	public int getSecondaryItemId() {
		return secondaryItemId;
	}

	public void setSecondaryItemId(int secondaryItemId) {
		this.secondaryItemId = secondaryItemId;
	}

	public String getSecondaryItemName() {
		return secondaryItemName;
	}

	public void setSecondaryItemName(String secondaryItemName) {
		this.secondaryItemName = secondaryItemName;
	}

	public float getSecondaryQty() {
		return secondaryQty;
	}

	public void setSecondaryQty(float secondaryQty) {
		this.secondaryQty = secondaryQty;
	}

	@Override
	public String toString() {
		return "ItemBuyGetFreeOffer [offerDetailId=" + offerDetailId + ", primaryItemId=" + primaryItemId
				+ ", primaryItemName=" + primaryItemName + ", primaryQty=" + primaryQty + ", secondaryItemId="
				+ secondaryItemId + ", secondaryItemName=" + secondaryItemName + ", secondaryQty=" + secondaryQty + "]";
	}

}
