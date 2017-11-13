package com.yooni.instance;

import java.util.List;

public class PurchaseTaskDetailList {
	private int count;
	private List<PurchaseTaskDetail> details;
	
	public PurchaseTaskDetailList() {
	}
	
	public PurchaseTaskDetailList(List<PurchaseTaskDetail> details) {
		this.details = details;
		this.count = details.size();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<PurchaseTaskDetail> getDetails() {
		return details;
	}

	public void setDetails(List<PurchaseTaskDetail> details) {
		this.details = details;
	}
}
