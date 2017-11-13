package com.yooni.instance;

import java.io.Serializable;
import java.util.Date;

public class PurchaseTask implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String taskId;
	private String code;
	//任务名称
	private String taskName;
	//状态
	private int status;
	// 创建日期
	private Date createDate;
	// 创建者
	private String creator;
	// 执行时间
	private Date purchaseDate;
	private String remark;

	public PurchaseTask() {

	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
