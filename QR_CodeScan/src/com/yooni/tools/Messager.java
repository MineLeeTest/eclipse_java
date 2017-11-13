package com.yooni.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qr_codescan.R;

@SuppressLint("ResourceAsColor")
public class Messager {
	public static void show(Context context, String text) {
		Toast toastCustom = new Toast(context);
		ViewGroup root = (ViewGroup) ((Activity) context)
				.findViewById(R.id.toast_layout_root);
		View layout = LayoutInflater.from(context).inflate(
				R.layout.tools_alert_messager_show, root);
		TextView texts = (TextView) layout.findViewById(R.id.text);
		texts.setText(text);
		toastCustom.setView(layout);
		toastCustom.setDuration(Toast.LENGTH_SHORT);
		toastCustom.setGravity(Gravity.BOTTOM, 0, 0);
		toastCustom.show();

	}

}
