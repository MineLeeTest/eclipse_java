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

public class MainSortingActivity extends Activity {
	private final static int SCANNIN_GREQUEST_CODE = 1;
	Context context;
	private TextView tvQRcode, tvBarcode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_sorting);
		((TextView) findViewById(R.id.LLTVTitle)).setText("分拣");
		tvBarcode = (TextView) findViewById(R.id.MSTVBarcode);
		tvQRcode = (TextView) findViewById(R.id.MSTVQRcode);
		context = MainSortingActivity.this;
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
				} else {
					tvBarcode.setText("" + scanCode);

				}
			}
			break;
		}
	}

	public void Click(View v) {
		if (v.getId() == R.id.MSBTNQRscan) {
			Intent intent = new Intent();
			intent.setClass(context, QRCaptureActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
		}
		if (v.getId() == R.id.MSBTNSort) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					Message msg = new Message();
					try {
						String result = NetHelper.getInstance().StartLock(
								tvQRcode.getText().toString(),
								tvBarcode.getText().toString(),
								StaticObject.getInstance().getAccount(), "3");
						if ("TRUE".equals(result)) {
							msg.arg1 = 1;
						} else if ("FALSE".equals(result)) {
							msg.arg1 = 0;
						} else if ("STOP".equals(result)) {
							msg.arg1 = 2;
						} else if ("WRONG_LIMIT".equals(result)) {
							msg.arg1 = 6;
						} else {
							msg.arg1 = -1;
						}
					} catch (Exception e) {
						msg.arg1 = -1;
					} finally {
						HandlerTimer.sendMessage(msg);
					}
				}
			}).start();
		}

	}

	Handler HandlerTimer = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.arg1 == 1) {
				Messager.show(context, "配货成功！");
			} else if (msg.arg1 == 2 || msg.arg1 == 6) {
				Messager.show(context, "您没有该权限！");
			} else if (msg.arg1 == 0) {
				Messager.show(context, "配货失败！");
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
		MainSortingActivity.this.finish();
	}
}
