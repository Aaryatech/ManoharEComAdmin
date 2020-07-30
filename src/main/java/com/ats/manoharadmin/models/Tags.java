package com.ats.manoharadmin.models;

public class Tags {

		private int tagId;
		private String tagName;
		private String tagDesc;
		private int tagIsActive;
		private int tagDeleteStatus;
		private float tagSortNumber;
		private int exInt1;
		private int exInt2;
		private String exVar1;
		private String exVar2;

		public int getTagId() {
			return tagId;
		}

		public void setTagId(int tagId) {
			this.tagId = tagId;
		}

		public String getTagName() {
			return tagName;
		}

		public void setTagName(String tagName) {
			this.tagName = tagName;
		}

		public String getTagDesc() {
			return tagDesc;
		}

		public void setTagDesc(String tagDesc) {
			this.tagDesc = tagDesc;
		}

		public int getTagIsActive() {
			return tagIsActive;
		}

		public void setTagIsActive(int tagIsActive) {
			this.tagIsActive = tagIsActive;
		}

		public int getTagDeleteStatus() {
			return tagDeleteStatus;
		}

		public void setTagDeleteStatus(int tagDeleteStatus) {
			this.tagDeleteStatus = tagDeleteStatus;
		}

		public float getTagSortNumber() {
			return tagSortNumber;
		}

		public void setTagSortNumber(float tagSortNumber) {
			this.tagSortNumber = tagSortNumber;
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
			return "Tags [tagId=" + tagId + ", tagName=" + tagName + ", tagDesc=" + tagDesc + ", tagIsActive="
					+ tagIsActive + ", tagDeleteStatus=" + tagDeleteStatus + ", tagSortNumber=" + tagSortNumber
					+ ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + "]";
		}

}
