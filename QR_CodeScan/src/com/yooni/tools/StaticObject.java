package com.yooni.tools;

public class StaticObject {

	// 测试版本
	public static boolean Debug = true;

	static StaticObject rfh_instance = null;
	private static String account = "";
	private static String pwd = "";
	private static String taskId = "";
//	（类型，1=管理员，2=配送员，3=分拣员，4=采购管理员，5=采购员，6=普通用户）
	private static int type = 0;

	public   int getType() {
		return type;
	}

	public   void setType(int type) {
		StaticObject.type = type;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		StaticObject.taskId = taskId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		StaticObject.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		StaticObject.pwd = pwd;
	}

	public static StaticObject getInstance() {
		if (rfh_instance == null) {
			rfh_instance = new StaticObject();
		}
		return rfh_instance;
	}
}
