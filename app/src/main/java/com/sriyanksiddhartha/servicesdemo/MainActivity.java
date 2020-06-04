package com.sriyanksiddhartha.servicesdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * 	Author: Sriyank Siddhartha
 *
 * 	Module 6: Working with Bound Service
 *
 *		"AFTER" project
 * */
public class MainActivity extends AppCompatActivity {

	private TextView txvIntentServiceResult, txvStartedServiceResult;

	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		txvIntentServiceResult = (TextView) findViewById(R.id.txvIntentServiceResult);
		txvStartedServiceResult = (TextView) findViewById(R.id.txvStartedServiceResult);
	}




	public void startIntentService(View view) {

		ResultReceiver myResultReceiver = new MyResultReceiver(null);

		Intent intent = new Intent(this, MyIntentService.class);
		intent.putExtra("sleepTime", 10);
		intent.putExtra("receiver", myResultReceiver);
		startService(intent);
	}



	// To receive the data back from MyIntentService.java using ResultReceiver
	private class MyResultReceiver extends ResultReceiver {

		public MyResultReceiver(Handler handler) {
			super(handler);
		}

		@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {
				super.onReceiveResult(resultCode, resultData);

				Log.i("MyResultreceiver", Thread.currentThread().getName());

				if (resultCode == 18 && resultData != null) {

					final String result = resultData.getString("resultIntentService");


					handler.post(new Runnable() {
						@Override
						public void run() {
							Log.i("MyHandler", Thread.currentThread().getName());
							txvIntentServiceResult.setText(result);
						}
					});
				}
		}
	}
}
