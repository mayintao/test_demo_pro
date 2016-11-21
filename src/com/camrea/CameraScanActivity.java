package com.camrea;

import java.io.IOException;
import java.util.Vector;


import com.camrea.zxing.camera.CameraManager;
import com.camrea.zxing.decoding.CaptureActivityHandler;
import com.camrea.zxing.decoding.InactivityTimer;
import com.camrea.zxing.view.ViewfinderView;
import com.mayintao.test.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.Toast;

public class CameraScanActivity extends Activity implements Callback, PreviewCallback {
	
	private SurfaceView			mSurfaceView = null;
	
	private ViewfinderView 		viewfinderView;
	
	private boolean hasSurface;

	private SurfaceHolder surfaceHolder;
	
	private CaptureActivityHandler handler;
	
	private Vector<BarcodeFormat> decodeFormats;
	
	private String characterSet;
	
	private InactivityTimer inactivityTimer;
	
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_scan);
		
		CameraManager.init(getApplication());
		
		initView();
		
		initData();
		
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);		
	}

	private void initData() {
		// TODO Auto-generated method stub
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		mSurfaceView = (SurfaceView)findViewById(R.id.preview_view);
		surfaceHolder = mSurfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;
		
		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}
	
	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}
	
	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}
	
	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera_scan, menu);
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		hasSurface = false;
	}

	@Override
	public void onPreviewFrame(byte[] arg0, Camera arg1) {
		// TODO Auto-generated method stub
		Log.i("", String.valueOf(arg0));
	}
	
	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		String resultString = result.getText();
		Toast.makeText(CameraScanActivity.this, resultString, Toast.LENGTH_SHORT).show();
		playBeepSoundAndVibrate();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
		Intent intent = new Intent();
		intent.putExtra("url", resultString);
		setResult(0, intent);
		CameraScanActivity.this.finish();
	}
	
	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}
	
	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}
	
	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

}
