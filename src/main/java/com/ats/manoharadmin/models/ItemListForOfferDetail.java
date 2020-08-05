package com.ats.manoharadmin.models;

public class ItemListForOfferDetail {

	private int itemDId;
	private int itemId;
	private String itemName;
	private String itemDesc;
	private int catId;
	private int offerDetailId;
	private float disc;
	private int checked;

	public int getItemDId() {
		return itemDId;
	}

	public void setItemDId(int itemDId) {
		this.itemDId = itemDId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public int getOfferDetailId() {
		return offerDetailId;
	}

	public void setOfferDetailId(int offerDetailId) {
		this.offerDetailId = offerDetailId;
	}

	public float getDisc() {
		return disc;
	}

	public void setDisc(float disc) {
		this.disc = disc;
	}

	public int getChecked() {
		return checked;
	}

	public void setChecked(int checked) {
		this.checked = checked;
	}

	@Override
	public String toString() {
		return "ItemListForOfferDetail [itemDId=" + itemDId + ", itemId=" + itemId + ", itemName=" + itemName
				+ ", itemDesc=" + itemDesc + ", catId=" + catId + ", offerDetailId=" + offerDetailId + ", disc=" + disc
				+ ", checked=" + checked + "]";
	}

}
