package com.example.qr_codescan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.yooni.network.NetHelper;
import com.yooni.tools.LogHelper;
import com.yooni.tools.Messager;
import com.yooni.tools.StaticObject;

public class LoginActivity extends Activity {
	private EditText ETaccount, ETpwd;
	private ProgressDialog progressDialog = null;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_login);
		ETaccount = (EditText) this.findViewById(R.id.ULETAccount);
		ETpwd = (EditText) this.findViewById(R.id.ULETPwd);
		// 初始化日志文件
		LogHelper.configLog();
		LogHelper.gLogger.debug("程序开启");
		context = LoginActivity.this;
	}

	public void Click(View v) {
		if (v.getId() == R.id.ULBTNLogin) {
			final String account = ETaccount.getText().toString().trim();
			final String pwd = ETpwd.getText().toString().trim();
			if (account.length() < 2) {
				Messager.show(this, "账号不能为空！");
				return;
			}
			if (pwd.length() < 2) {
				Messager.show(this, "密码不能为空！");
				return;
			}

			progressDialog = ProgressDialog.show(LoginActivity.this, "登录",
					"登录中，请稍后", true);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						String rulse = NetHelper.getInstance().UserLogin(
								account, pwd);
						Message msgs = new Message();
						msgs.obj = rulse;
						HandlerTimer.sendMessage(msgs);
					} catch (Exception e) {

					}
				}
			}).start();

		} else if (v.getId() == R.id.ULBTNExit) {
			System.exit(0);
		}

	}

	Handler HandlerTimer = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			progressDialog.dismiss();
			if (null == msg.obj) {
				Messager.show(LoginActivity.this, "1000");
				LogHelper.gLogger.debug("2000");
				StaticObject.getInstance().setAccount("");
				StaticObject.getInstance().setPwd("");
				return;
			}
			if (msg.obj.toString().length() < 2) {
				LogHelper.gLogger.debug("1000");
				StaticObject.getInstance().setType(
						Integer.valueOf(msg.obj.toString()));
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
							if (null == result) {
								msg.arg1 = -1;
							} else {
								if ("ALREAD_ONLINE".equals(result)
										|| "TRUE".equals(result)) {
									msg.arg1 = 0;
								} else {
									msg.arg1 = -1;
								}
							}
						} catch (Exception e) {
							msg.arg1 = -1;
						} finally {
							HandlerOnline.sendMessage(msg);
						}
					}
				}).start();

			} else {
				Messager.show(LoginActivity.this, "40000");
				StaticObject.getInstance().setAccount("");
				StaticObject.getInstance().setPwd("");
				LogHelper.gLogger.debug("50000");
			}
			super.handleMessage(msg);

		}
	};
	Handler HandlerOnline = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			progressDialog.dismiss();
			if (msg.arg1 == -1) {
				Messager.show(context, "服务器未运行，请确认后重试！");
			} else {

				startActivity(new Intent(LoginActivity.this, MainActivity.class));
				LoginActivity.this.finish();
			}
		}
	};
}
