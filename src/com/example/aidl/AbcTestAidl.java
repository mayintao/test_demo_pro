package com.example.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class AbcTestAidl extends Service{

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return new com.aidl.AbcTestAidl.Stub() {
			
			@Override
			public void setSize(long size) throws RemoteException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public int getLevel() throws RemoteException {
				// TODO Auto-generated method stub
				int number = (int)Math.random();
				return number;
			}
			
			@Override
			public String getAge(String name) throws RemoteException {
				// TODO Auto-generated method stub
				String retName = name + "return from test";
				return retName;
			}
			
			@Override
			public int addNumber(int a, int b) throws RemoteException {
				// TODO Auto-generated method stub
				int c = a + b;
				return c;
			}
		};
	}
}
