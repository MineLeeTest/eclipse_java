package com.yooni.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.yooni.tools.Printer;

public class SocThread extends Thread {
	private String ip = "118.178.192.39";
	private int port = 5001;
	private String TAG = "socket thread";
	private int timeout = 5000;

	public Socket client = null;
	PrintWriter out;
	BufferedReader in;
	public boolean isRun = true;
	Handler inHandler;
	Handler outHandler;
	Context ctx;
	private String TAG1 = "===Send===";
	SharedPreferences sp;

	public SocThread(Handler handlerin, Handler handlerout, Context context) {
		inHandler = handlerin;
		outHandler = handlerout;
		ctx = context;
		Printer.Go(TAG, "创建线程socket");
	}

	/**
	 * 连接socket服务器
	 */
	public void conn() {

		try {
			initdate();
			Printer.Go(TAG, "连接中……");
			client = new Socket(ip, port);
			client.setSoTimeout(timeout);// 设置阻塞时间
			Printer.Go(TAG, "连接成功");
			in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					client.getOutputStream())), true);
			Printer.Go(TAG, "输入输出流获取成功");
		} catch (UnknownHostException e) {
			Printer.Go(TAG, "连接错误UnknownHostException 重新获取");
			e.printStackTrace();
			conn();
		} catch (IOException e) {
			Printer.Go(TAG, "连接服务器io错误");
			e.printStackTrace();
		} catch (Exception e) {
			Printer.Go(TAG, "连接服务器错误Exception" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void initdate() {
		sp = ctx.getSharedPreferences("SP", ctx.MODE_PRIVATE);
		ip = sp.getString("ipstr", ip);
		port = Integer.parseInt(sp.getString("port", String.valueOf(port)));
		Printer.Go(TAG, "获取到ip端口:" + ip + ";" + port);
	}

	/**
	 * 实时接受数据
	 */
	@Override
	public void run() {
		Printer.Go(TAG, "线程socket开始运行");
		conn();
		Printer.Go(TAG, "1.run开始");
		String line = "";
		while (isRun) {
			try {
				if (client != null) {
					Printer.Go(TAG, "2.检测数据");
					while ((line = in.readLine()) != null) {
						Printer.Go(TAG,
								"3.getdata" + line + " len=" + line.length());
						Printer.Go(TAG, "4.start set Message");
						Message msg = inHandler.obtainMessage();
						msg.obj = line;
						inHandler.sendMessage(msg);// 结果返回给UI处理
						Printer.Go(TAG1, "5.send to handler");
					}

				} else {
					Printer.Go(TAG, "没有可用连接");
					conn();
				}
				Thread.sleep(25000);
			} catch (Exception e) {
				Printer.Go(TAG, "数据接收错误" + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * 发送数据
	 * 
	 * @param mess
	 */
	public void Send(String mess) {
		try {
			if (client != null) {
				Printer.Go(TAG1, "发送" + mess + "至"
						+ client.getInetAddress().getHostAddress() + ":"
						+ String.valueOf(client.getPort()));
				out.println(mess);
				out.flush();
				Printer.Go(TAG1, "发送成功");
				Message msg = outHandler.obtainMessage();
				msg.obj = mess;
				msg.what = 1;
				outHandler.sendMessage(msg);// 结果返回给UI处理
			} else {
				Printer.Go(TAG, "client 不存在");
				Message msg = outHandler.obtainMessage();
				msg.obj = mess;
				msg.what = 0;
				outHandler.sendMessage(msg);// 结果返回给UI处理
				Printer.Go(TAG, "连接不存在重新连接");
				conn();
			}

		} catch (Exception e) {
			Printer.Go(TAG1, "send error");
			e.printStackTrace();
		} finally {
			Printer.Go(TAG1, "发送完毕");

		}
	}

	/**
	 * 关闭连接
	 */
	public void close() {
		try {
			if (client != null) {
				Printer.Go(TAG, "close in");
				in.close();
				Printer.Go(TAG, "close out");
				out.close();
				Printer.Go(TAG, "close client");
				client.close();
			}
		} catch (Exception e) {
			Printer.Go(TAG, "close err");
			e.printStackTrace();
		}

	}

	// Handler handlerReceiver = new Handler() {
	// @Override
	// public void handleMessage(Message msg) {
	// try {
	// Printer.Go(TAG, "mhandler接收到msg=" + msg.what);
	// if (msg.obj != null) {
	// String s = msg.obj.toString();
	// if (s.trim().length() > 0) {
	// Printer.Go(TAG, "mhandler接收到obj=" + s);
	// Printer.Go(TAG, "开始更新UI");
	// tv1.append("Server:" + s);
	// Printer.Go(TAG, "更新UI完毕");
	// } else {
	// Log.i(TAG, "没有数据返回不更新");
	// }
	// }
	// } catch (Exception ee) {
	// Printer.Go(TAG, "加载过程出现异常");
	// ee.printStackTrace();
	// }
	// }
	// };
	// Handler handlerSender = new Handler() {
	// @Override
	// public void handleMessage(Message msg) {
	// try {
	// Printer.Go(TAG, "mhandlerSend接收到msg.what=" + msg.what);
	// String s = msg.obj.toString();
	// if (msg.what == 1) {
	// tv1.append("\n ME: " + s + "      发送成功");
	// // Messager.show(context, "箱盒已全部打开！");
	// } else {
	// tv1.append("\n ME: " + s + "     发送失败");
	// }
	// } catch (Exception ee) {
	// Printer.Go(TAG, "加载过程出现异常");
	// ee.printStackTrace();
	// }
	// }
	// };
	// socketThread = new SocThread(handlerReceiver, handlerSender, context);
	// socketThread.start();

}
