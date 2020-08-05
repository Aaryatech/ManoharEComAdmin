package com.ats.manoharadmin.models;

import java.util.List;

public class GetConfiguredItemsList {

	List<GetItem> itemsList;
	List<Category> catList;
	
	public List<GetItem> getItemsList() {
		return itemsList;
	}
	public void setItemsList(List<GetItem> itemsList) {
		this.itemsList = itemsList;
	}
	public List<Category> getCatList() {
		return catList;
	}
	public void setCatList(List<Category> catList) {
		this.catList = catList;
	}
	@Override
	public String toString() {
		return "GetConfiguredItemsList [itemsList=" + itemsList + ", catList=" + catList + "]";
	}
	
}
