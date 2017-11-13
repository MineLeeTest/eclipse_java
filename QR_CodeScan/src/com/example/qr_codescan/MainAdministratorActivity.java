package com.example.qr_codescan;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.yooni.network.NetHelper;
import com.yooni.tools.MathTool;
import com.yooni.tools.Messager;
import com.yooni.tools.StaticObject;

public class MainAdministratorActivity extends Activity {
	private final static int SCANNIN_GREQUEST_CODE = 1;
	Context context;
	private TextView tvQRcode, tv1;
	Socket socket;
	String HOST = "118.178.192.39";
	int PORT = 5001;
	BufferedReader in;
	PrintWriter out;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_administrator);
		tvQRcode = (TextView) findViewById(R.id.MDTVQRcode);
		((TextView) findViewById(R.id.LLTVTitle)).setText("管理");
		tv1 = (TextView) this.findViewById(R.id.MDTVMsgShow);
		context = MainAdministratorActivity.this;

	}

	ProgressDialog progressDialog;

	public void Click(View v) {
		if (v.getId() == R.id.MDBTNQRscan) {
			Intent intent = new Intent();
			intent.setClass(MainAdministratorActivity.this,
					QRCaptureActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
		}
		if (v.getId() == R.id.MDBTNUnlock) {
			OpenLock();
		}
		if (v.getId() == R.id.MDBTNCloseFan) {
			FanOpreate(0);
		}
		if (v.getId() == R.id.MDBTNOpenFan) {
			FanOpreate(1);
		}
	}

	private void OpenLock() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				try {
					String result = NetHelper.getInstance().StartLock(
							tvQRcode.getText().toString(), "",
							StaticObject.getInstance().getAccount(), "2");
					if ("FALSE".equals(result)) {
						msg.arg1 = 0;
					} else if ("TRUE".equals(result)) {
						msg.arg1 = 1;
					} else if ("STOP".equals(result)) {
						msg.arg1 = 2;
					} else if ("OPEN_DONE".equals(result)) {
						msg.arg1 = 3;
					} else if ("OPEN_FILED".equals(result)) {
						msg.arg1 = 4;
					} else if ("OFFLINE".equals(result)) {
						msg.arg1 = 5;
					} else if ("WRONG_LIMIT".equals(result)) {
						msg.arg1 = 6;
					} else {
						msg.arg1 = -1;
					}
				} catch (Exception e) {
					msg.arg1 = -1;
				} finally {
					HandlerOpenLock.sendMessage(msg);
				}
			}
		}).start();
	}

	Handler HandlerOpenLock = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.arg1 == 1 || msg.arg1 == 3) {
				Messager.show(context, "开锁成功！");
			} else if (msg.arg1 == 2 || msg.arg1 == 6) {
				Messager.show(context, "您没有开锁权限！");
			} else if (msg.arg1 == 5) {
				Messager.show(context, "DTU设备离线，重启该设备中请稍后！");
				// 激活设备开始上线
				new Thread(new Runnable() {
					@Override
					public void run() {
						Message msg = new Message();
						try {
							String result = NetHelper.getInstance()
									.StartOnline(
											StaticObject.getInstance()
													.getAccount());
						} catch (Exception e) {
							msg.arg1 = -1;
						} finally {
						}
					}
				}).start();
			} else if (msg.arg1 == 0 || msg.arg1 == 4) {
				Messager.show(context, "开锁失败！");
			} else if (msg.arg1 == -1) {
				Messager.show(context, "联网失败，请确认当前网络状态后重试！");
			}
		}
	};

	// type=1 开风扇
	// type=0 关风扇
	private void FanOpreate(final int type) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				msg.arg2 = type;
				try {
					String result = NetHelper.getInstance().StartLock(
							tvQRcode.getText().toString(), "",
							StaticObject.getInstance().getAccount(), type + "");
					if ("FALSE".equals(result)) {
						msg.arg1 = 0;
					} else if ("TRUE".equals(result)) {
						msg.arg1 = 1;
					} else if ("STOP".equals(result)) {
						msg.arg1 = 2;
					} else if ("OPEN_DONE".equals(result)) {
						msg.arg1 = 3;
					} else if ("OPEN_FILED".equals(result)) {
						msg.arg1 = 4;
					} else if ("OFFLINE".equals(result)) {
						msg.arg1 = 5;
					} else if ("WRONG_LIMIT".equals(result)) {
						msg.arg1 = 6;
					} else {
						msg.arg1 = -1;
					}
				} catch (Exception e) {
					msg.arg1 = -1;
				} finally {
					HandlerFanOpreate.sendMessage(msg);
				}
			}
		}).start();
	}

	Handler HandlerFanOpreate = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.arg1 == 1 || msg.arg1 == 3) {
				if (msg.arg2 == 1) {
					Messager.show(context, "制冷开启成功！");
				} else {
					Messager.show(context, "制冷关闭成功！");
				}

			} else if (msg.arg1 == 2 || msg.arg1 == 6) {
				Messager.show(context, "您没有该项权限！");
			} else if (msg.arg1 == 5) {
				Messager.show(context, "DTU设备离线，重启该设备中请稍后！");
				// 激活设备开始上线
				new Thread(new Runnable() {
					@Override
					public void run() {
						Message msg = new Message();
						try {
							String result = NetHelper.getInstance()
									.StartOnline(
											StaticObject.getInstance()
													.getAccount());
						} catch (Exception e) {
							msg.arg1 = -1;
						} finally {
						}
					}
				}).start();
			} else if (msg.arg1 == 0 || msg.arg1 == 4) {

				if (msg.arg2 == 1) {
					Messager.show(context, "制冷开启失败！");
				} else {
					Messager.show(context, "制冷关闭失败！");
				}
			} else if (msg.arg1 == -1) {
				Messager.show(context, "联网失败，请确认当前网络状态后重试！");
			}
		}
	};

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		startActivity(new Intent(context, MainActivity.class));
		MainAdministratorActivity.this.finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				String scanCode = bundle.getString("result");
				if (!MathTool.isNumeric(scanCode)) {
					// 二维码扫描
					tvQRcode.setText("" + scanCode);
					Messager.show(context, "已获取控制权限！");
				}
			}
			break;
		}
	}
}
