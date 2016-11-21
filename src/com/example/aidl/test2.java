package com.example.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class test2 extends Service{

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return new com.aidl.test2.Stub() {
			
			@Override
			public void getName() throws RemoteException {
				// TODO Auto-generated method stub
			}
		};
	}

}
