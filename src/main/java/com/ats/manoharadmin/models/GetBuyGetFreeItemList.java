package com.ats.manoharadmin.models;

import java.util.List;

public class GetBuyGetFreeItemList {

	private List<ItemBuyGetFreeOffer> itemList;
	private List<Integer> idList;

	public List<ItemBuyGetFreeOffer> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemBuyGetFreeOffer> itemList) {
		this.itemList = itemList;
	}

	public List<Integer> getIdList() {
		return idList;
	}

	public void setIdList(List<Integer> idList) {
		this.idList = idList;
	}

	@Override
	public String toString() {
		return "GetBuyGetFreeItemList [itemList=" + itemList + ", idList=" + idList + "]";
	}

}
