package com.example.qr_codescan;

import com.yooni.network.NetHelper;
import com.yooni.tools.MathTool;
import com.yooni.tools.Messager;
import com.yooni.tools.StaticObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

public class MainDistributionsActivity extends Activity {
	Context context;
	private final static int SCANNIN_GREQUEST_CODE = 1;
	private TextView tvQRcode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_distributions);
		tvQRcode = (TextView) findViewById(R.id.MATVQRcode);
		((TextView) findViewById(R.id.LLTVTitle)).setText("配送");
		context = MainDistributionsActivity.this;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		startActivity(new Intent(context, MainActivity.class));
		MainDistributionsActivity.this.finish();
	}

	public void Click(View v) {
		if (v.getId() == R.id.MABTNQRscan) {
			Intent intent = new Intent();
			intent.setClass(context, QRCaptureActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
		}

		if (v.getId() == R.id.MABTNUnlockNow) {
			OpenLock();
			return;
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
					tvQRcode.setText(scanCode);
					Messager.show(context, "已获取管理员控制权限！");
				}
			}
			break;
		}
	}
}
