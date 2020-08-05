package com.ats.manoharadmin.models;

import java.util.List;

public class GetItemListForOfferDetail {

	private List<ItemListForOfferDetail> itemList;
	private List<Category> catList;

	public List<ItemListForOfferDetail> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemListForOfferDetail> itemList) {
		this.itemList = itemList;
	}

	public List<Category> getCatList() {
		return catList;
	}

	public void setCatList(List<Category> catList) {
		this.catList = catList;
	}

	@Override
	public String toString() {
		return "GetItemListForOfferDetail [itemList=" + itemList + ", catList=" + catList + "]";
	}

}
