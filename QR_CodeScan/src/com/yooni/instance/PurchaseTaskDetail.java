package com.yooni.instance;

import java.io.Serializable;
import java.util.Date;

public class PurchaseTaskDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String taskDetailId;
	private String taskId;
	private String productName;
	private String productType;
	//状态 
	private int status;
	//网站需求数量
	private double orderQuantity;
	//网站需求单位
	private String orderUnit;
	//计划采购数量
	private double planQuantity;
	//计划采购单位
	private String planUnit;
	//实际采购数量 
	private double realQuantity;
	//实际采购单位 份，斤，两
	private String realUnit;
	// 单价
	private double price;
	// 总价
	private double amount;
	//创建者
	private String creator;
	//创建时间
	private String createDate;
	//采购者
	private String purchaser;
	//采购时间
	private Date purchaseDate;

	private int purchaseStatus;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getPurchaseStatus() {
		return purchaseStatus;
	}

	public void setPurchaseStatus(int purchaseStatus) {
		this.purchaseStatus = purchaseStatus;
	}

	public PurchaseTaskDetail() {

	}

	public String getTaskDetailId() {
		return taskDetailId;
	}

	public void setTaskDetailId(String taskDetailId) {
		this.taskDetailId = taskDetailId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(double orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public String getOrderUnit() {
		return orderUnit;
	}

	public void setOrderUnit(String orderUnit) {
		this.orderUnit = orderUnit;
	}

	public double getPlanQuantity() {
		return planQuantity;
	}

	public void setPlanQuantity(double planQuantity) {
		this.planQuantity = planQuantity;
	}

	public String getPlanUnit() {
		return planUnit;
	}

	public void setPlanUnit(String planUnit) {
		this.planUnit = planUnit;
	}

	public double getRealQuantity() {
		return realQuantity;
	}

	public void setRealQuantity(double realQuantity) {
		this.realQuantity = realQuantity;
	}

	public String getRealUnit() {
		return realUnit;
	}

	public void setRealUnit(String realUnit) {
		this.realUnit = realUnit;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
}
