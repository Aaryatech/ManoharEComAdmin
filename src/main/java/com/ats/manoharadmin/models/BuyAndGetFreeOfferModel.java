package com.ats.manoharadmin.models;

import java.util.List;

public class BuyAndGetFreeOfferModel {

	private int id;
	private String primaryName;
	private int type;

	List<SecondaryItemModel> secondaryItemList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrimaryName() {
		return primaryName;
	}

	public void setPrimaryName(String primaryName) {
		this.primaryName = primaryName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<SecondaryItemModel> getSecondaryItemList() {
		return secondaryItemList;
	}

	public void setSecondaryItemList(List<SecondaryItemModel> secondaryItemList) {
		this.secondaryItemList = secondaryItemList;
	}

	@Override
	public String toString() {
		return "BuyAndGetFreeOfferModel [id=" + id + ", primaryName=" + primaryName + ", type=" + type
				+ ", secondaryItemList=" + secondaryItemList + "]";
	}

}
