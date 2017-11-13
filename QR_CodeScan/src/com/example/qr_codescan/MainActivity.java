package com.example.qr_codescan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		((TextView) findViewById(R.id.LLTVTitle)).setText("主界面");
		context = MainActivity.this;
	}

	public void Click(View v) {
		if (v.getId() == R.id.MBTNSorting) {
			startActivity(new Intent(context, MainSortingActivity.class));
		}
		if (v.getId() == R.id.MBTNAdministration) {
			startActivity(new Intent(context, MainAdministratorActivity.class));
		}
		if (v.getId() == R.id.MBTNDistribution) {
			startActivity(new Intent(context, MainDistributionsActivity.class));
		}
		this.finish();
	}
}
