package com.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.camrea.CameraScanActivity;
import com.example.aidl.test2;
import com.main.view.SkuAdapter;
import com.mayintao.test.R;
import com.aidl.AbcTestAidl;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, SkuCallBack, OnScrollListener {
	
	private TextView		aaaa111;
	
	private TextView		mUrlTextView = null;
	
	private EditText		mPhoneNumberEditText = null;
	
	private EditText		mEmailEditText = null;
	
	private boolean 		canEdit = false;
	
	private Button			mButton2 = null;
	
	private Button 			mAidlButton = null;

	public AbcTestAidl 		service;
	
	private AdditionServiceConnection	mAdditionServiceConnection = null;
	
	private Button			mScanButton = null;
	
	private Button			mselect_date_button = null;
	
	private TextView        select_date_textView = null;
	
	private Button			mselect_time_button = null;
	
	private TextView        select_time_textView = null;
	
	private ListView		mListView = null;
	
	private SkuAdapter		mSkuAdapter = null;
	
	private List<SKUData> 		mList = new ArrayList<SKUData>();
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		aaaa111 = (TextView)findViewById(R.id.aaaa111);
		aaaa111.setOnClickListener(this);
		mPhoneNumberEditText = (EditText)findViewById(R.id.phone_editText);
		mEmailEditText = (EditText)findViewById(R.id.email_editText);
		mEmailEditText.setEnabled(false);
		mButton2 = (Button)findViewById(R.id.button2);
		mButton2.setOnClickListener(this);
		mAidlButton = (Button)findViewById(R.id.button3);
		mAidlButton.setOnClickListener(this);
		mScanButton = (Button)findViewById(R.id.scan_button);
		mScanButton.setOnClickListener(this);
		mUrlTextView = (TextView)findViewById(R.id.url_textView);
		mselect_date_button = (Button)findViewById(R.id.select_date_button);
		mselect_date_button.setOnClickListener(this);
		select_date_textView = (TextView)findViewById(R.id.select_date_textView);
		mselect_time_button = (Button)findViewById(R.id.select_time_button);
		mselect_time_button.setOnClickListener(this);
		select_time_textView = (TextView)findViewById(R.id.select_time_textView);
		initService();
		mListView = (ListView)findViewById(R.id.listView);
		mListView.setOnScrollListener(this);
		mList = new ArrayList<SKUData>();
		SKUData data;
		for (int i = 0; i < 12; i++) {
			data = new SKUData();
			data.canEdit = false;
			data.str1 = "a" + i;
			data.str2 = "b" + i;
			mList.add(data);
		}
		mSkuAdapter = new SkuAdapter(MainActivity.this, mList, this);
		mListView.setAdapter(mSkuAdapter);
	}

	private void initService() {
		// TODO Auto-generated method stub
		mAdditionServiceConnection = new AdditionServiceConnection();
		//Intent i = new Intent();
        //i.setClassName("com.mayintao.yintong.test", AbcTestAidl.class.getName());
        boolean ret = bindService(new Intent(MainActivity.this, test2.class), mAdditionServiceConnection
        		, Context.BIND_AUTO_CREATE);
        //boolean ret = bindService(i, mAdditionServiceConnection, Context.BIND_AUTO_CREATE);
        Toast.makeText(MainActivity.this, String.valueOf(ret), Toast.LENGTH_SHORT).show();
	}
	
	private class AdditionServiceConnection implements ServiceConnection{

		@Override
		public void onServiceConnected(ComponentName name, IBinder boundService) {
			// TODO Auto-generated method stub
			service = com.aidl.AbcTestAidl.Stub.asInterface((IBinder)boundService);
	        Toast.makeText(MainActivity.this, "Service connected", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			 service = null;
		     Toast.makeText(MainActivity.this, "Service disconnected", Toast.LENGTH_LONG).show();
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.aaaa111:
			/*Intent intent = new Intent();
			intent.setAction("android.intent.action.YSD0PAY");
			//intent.putExtra("amount", mNumber.getText().toString().replace(" ", ""));
			intent.putExtra("amount", "1");
			startActivityForResult(intent, 1);*/
			String phone = mPhoneNumberEditText.getText().toString();
			String regex = "\\w{11}";
			if (phone.matches(regex)) {
				Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();
			}else {
				Toast.makeText(MainActivity.this, "不是正确的电话号码", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.button2:
			/*String email = mEmailEditText.getText().toString();
			String regexEmail = "\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}";
			if (email.matches(regexEmail)) {
				Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();
			}else {
				Toast.makeText(MainActivity.this, "不是正确的email地址", Toast.LENGTH_SHORT).show();
			}*/
			if (canEdit) {
				mEmailEditText.setEnabled(false);
				canEdit = false;
			}else {
				mEmailEditText.setEnabled(true);
				canEdit = true;
			}
			break;
		case R.id.select_date_button:
			DatePickerDialog a = new DatePickerDialog(this, mDateSetListener, 2016, 9, 18);
			a.show();
			break;
		case R.id.select_time_button:
			TimePickerDialog b = new TimePickerDialog(this, timeSetListener, 7, 7, true);
			b.show();
			break;
		case R.id.button3:
			try {
				int getData = service.addNumber(1, 6);
				Log.i("", String.valueOf(getData));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.scan_button:
			Intent intent = new Intent(MainActivity.this, CameraScanActivity.class);
			startActivityForResult(intent, 0);
			break;

		default:
			break;
		}
	}
	
	private final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
	    @Override
	    public void onDateSet(DatePicker view, int year, int monthOfYear,
	            int dayOfMonth) {
	        int mYear = year;
	        String mm;
	        String dd;
	        int mMonth = monthOfYear + 1;
	        mm = String.valueOf(mMonth);
	        if (mm.length() < 2)
	            mm = "0" + mm;
	        int mDay = dayOfMonth;
	        dd = String.valueOf(mDay);
	        if (dd.length() < 2)
	            dd = "0" + dd;
	        select_time_textView.setText(String.valueOf(mYear) + "-" + mm + "-" + dd);
	        TimePickerDialog b = new TimePickerDialog(MainActivity.this, timeSetListener, 7, 7, true);
			b.show();
	    }
	};
	
	private final TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hour, int minute) {
			// TODO Auto-generated method stub
			String hourStr = String.valueOf(hour);
			 if (hourStr.length() < 2)
				 hourStr = "0" + hourStr;
			 String minuteStr = String.valueOf(minute);
			 if (minuteStr.length() < 2)
				 minuteStr = "0" + minuteStr;
			select_date_textView.setText(hourStr + ":" + minuteStr);
		}
	};
	
	private String getTimeMS(){
		String str = "2016-08-2 12:09";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return "";
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case 0:
			if (null != data) {
				if (null != data.getExtras()) {
					if (null != data.getExtras().get("url")) {
						String url = data.getExtras().get("url").toString();
						mUrlTextView.setText(url);
					}
				}
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	private void releaseService() {
		if (null != mAdditionServiceConnection) {
			unbindService(mAdditionServiceConnection);
	        mAdditionServiceConnection = null;
		}
    }
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		releaseService();
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void getEditPosition(int position) {
		// TODO Auto-generated method stub
		SKUData getData = mList.get(position);
		if (getData.canEdit) {
			getData.canEdit = false;
		}else {
			getData.canEdit = true;
		}
		mSkuAdapter.notifyDataSetChanged();
	}

	@Override
	public void onScroll(AbsListView arg0, int position, int arg2, int arg3) {
		// TODO Auto-generated method stub
		Log.i("", "");
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

}
