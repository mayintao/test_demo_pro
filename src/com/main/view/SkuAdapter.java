package com.main.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.main.SKUData;
import com.main.SkuCallBack;
import com.mayintao.test.R;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

public class SkuAdapter extends BaseAdapter implements OnClickListener{
	
	private Context 			mContext = null;
	
	private List<SKUData> 		mList = new ArrayList<SKUData>();
	
	private LayoutInflater      mLayoutInflater = null;
	
	private ViewHolder			mViewHolder = null;
	
	private SkuCallBack			mSkuCallBack = null;
	
	private SKUData				mCurrentSKUData = null;
	
	private HashMap<Integer, String> hashMap1 = new HashMap<Integer, String>();  
	
	private HashMap<Integer, String> hashMap2 = new HashMap<Integer, String>();  
	
	
	public SkuAdapter() {
		// TODO Auto-generated constructor stub
	}
	
	public SkuAdapter(Context context, List<SKUData> list, SkuCallBack callback) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mList = list;
		this.mSkuCallBack = callback;
		mLayoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (null != mList) {
			return mList.size();
		}else {
			return 0;
		}
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		if (null != mList) {
			return mList.get(arg0);
		}else {
			return null;
		}
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View covertView, ViewGroup viewGroup) {
		// TODO Auto-generated method stub
		mCurrentSKUData = mList.get(position);
		Log.i("TAG", position + " str1:" + mCurrentSKUData.str1);
		Log.i("TAG", position + " str2:" + mCurrentSKUData.str2);
		mViewHolder = new ViewHolder();
		covertView = mLayoutInflater.inflate(R.layout.view_sku_list, null);
		mViewHolder.text1 = (EditText)covertView.findViewById(R.id.editText1);
		mViewHolder.text2 = (EditText)covertView.findViewById(R.id.editText2);
		mViewHolder.editButton = (Button)covertView.findViewById(R.id.edit_button);
		mViewHolder.text1.setTag(position);
		mViewHolder.text2.setTag(position);
		mViewHolder.text1.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				hashMap1.put(position, s.toString());  
			}
		});
		mViewHolder.text1.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				mViewHolder.text1.requestFocus();
				mViewHolder.text2.clearFocus();
				return false;
			}
		});
		mViewHolder.text2.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				hashMap2.put(position, s.toString());  
			}
		});
		mViewHolder.text2.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				mViewHolder.text2.requestFocus();
				mViewHolder.text1.clearFocus();
				return false;
			}
		});
		if (mCurrentSKUData.canEdit) {
			mViewHolder.text1.setEnabled(true);
			mViewHolder.text2.setEnabled(true);
		}else {
			mViewHolder.text1.setEnabled(false);
			mViewHolder.text2.setEnabled(false);
		}
		if(hashMap1.get(position) != null){
			mViewHolder.text1.setText(hashMap1.get(position));
			mCurrentSKUData.str1 = hashMap1.get(position);
        } 
		if(hashMap2.get(position) != null){
			mViewHolder.text2.setText(hashMap2.get(position));
			mCurrentSKUData.str2 = hashMap2.get(position);
        } 
		mViewHolder.editButton.setTag(position);
		mViewHolder.editButton.setOnClickListener(this);
		return covertView;
	}
	
	public class ViewHolder{
		
		private EditText	text1;
		
		private EditText	text2;
		
		private Button 		editButton;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.edit_button:
			mSkuCallBack.getEditPosition(Integer.valueOf(v.getTag().toString()));
			break;

		default:
			break;
		}
	}

}
