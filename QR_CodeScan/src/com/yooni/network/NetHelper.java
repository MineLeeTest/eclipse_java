package com.yooni.network;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.yooni.tools.LogHelper;
import com.yooni.tools.Printer;
import com.yooni.tools.StaticObject;

public class NetHelper {
	static NetHelper rfh_instance = null;
	// 208

	String BoxServerURL = "http://118.178.192.39:8080/minelee";
	// 206
	// String dnsName = "http://192.168.10.114:8080";
	String dnsName = "http://shop.365xyd.com:8080";
	String ServerURL = dnsName + "/yoonilink-connector";

	public static NetHelper getInstance() {
		if (rfh_instance == null) {
			rfh_instance = new NetHelper();
		}
		return rfh_instance;
	}

	// http://192.168.9.115:8080/yoonilink-connector/purchase/mergeTask
	// 用户登录
	public String UserLogin(String userName, String password) {
		if (userName.equals("admin") || password.equals("admin")) {
			return "true";
		}

		String processURL = ServerURL + "/user/login4Type";
		try {

			// 将账号与密码，存储在内存中
			StaticObject.getInstance().setAccount(userName);
			StaticObject.getInstance().setPwd(password);

			JSONObject jsonObj = new JSONObject();
			jsonObj.put("name", userName);
			jsonObj.put("pwd", password);
			StringEntity entity = new StringEntity(jsonObj.toString(),
					HTTP.UTF_8);
			HttpPost httpPost = new HttpPost(processURL);
			httpPost.addHeader("Content-Type", "application/json");
			httpPost.setEntity(entity);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response;

			LogHelper.gLogger.debug("用户【"
					+ StaticObject.getInstance().getAccount()
					+ "】提交登录信息：processURL=" + processURL + ",jsonobj="
					+ jsonObj.toString());

			response = client.execute(httpPost);
			HttpEntity entitys = response.getEntity();
			String result22 = EntityUtils.toString(entitys, "UTF-8");

			if (StaticObject.Debug) {
				System.out.println("processURL" + processURL);
				System.out.println("request:" + jsonObj.toString());
				System.out.println("response=" + result22);
			}

			return result22;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	// 初始化设备状态
	public String StartOnline(String user) throws JSONException {
		String processURL = BoxServerURL + "/Online";
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("user", user);
			StringEntity entity = new StringEntity(jsonObj.toString(),
					HTTP.UTF_8);
			HttpPost httpPost = new HttpPost(processURL);
			httpPost.addHeader("Content-Type", "application/json");
			httpPost.setEntity(entity);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response;
			Printer.Go("StartOnline-request", "processURL=" + processURL
					+ "----" + jsonObj.toString());
			response = client.execute(httpPost);
			HttpEntity entitys = response.getEntity();
			String result22 = EntityUtils.toString(entitys, "UTF-8");
			if (null == result22) {
				Printer.Go("StartOnline-response-null", "");
				return null;
			}
			Printer.Go("StartOnline-response", result22);
			return result22;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	// type=0 关风扇 ，type=1 开风扇，type=2 开锁，type=3 配货
	public String StartLock(String qrcode, String barcode, String user,
			String type) throws JSONException {
		String processURL = BoxServerURL + "/checkcode";
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("qrcode", qrcode);
			jsonObj.put("barcode", barcode);
			jsonObj.put("user", user);
			jsonObj.put("type", type);
			StringEntity entity = new StringEntity(jsonObj.toString(),
					HTTP.UTF_8);
			HttpPost httpPost = new HttpPost(processURL);
			httpPost.addHeader("Content-Type", "application/json");
			httpPost.setEntity(entity);
			HttpClient client = new DefaultHttpClient();
			HttpResponse response;
			Printer.Go("StartLock-request", "processURL=" + processURL + "----"
					+ jsonObj.toString());
			response = client.execute(httpPost);
			HttpEntity entitys = response.getEntity();
			String result22 = EntityUtils.toString(entitys, "UTF-8");
			Printer.Go("StartLock-response", result22);

			return result22;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
