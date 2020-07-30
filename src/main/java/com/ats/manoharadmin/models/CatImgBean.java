package com.ats.manoharadmin.models;

import java.util.List;

public class CatImgBean {
	private Category category;
	private List<Images> imgList;
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public List<Images> getImgList() {
		return imgList;
	}
	public void setImgList(List<Images> imgList) {
		this.imgList = imgList;
	}
	@Override
	public String toString() {
		return "CatImgBean [category=" + category + ", imgList=" + imgList + "]";
	}
	
	
}
