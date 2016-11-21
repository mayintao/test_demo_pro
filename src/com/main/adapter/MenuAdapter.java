package com.main.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.main.Main2Activity;
import com.main.module.MyMenuListModule;
import com.mayintao.test.R;

import java.util.List;

/**
 * Created by mayt on 2016/10/12.
 */

public class MenuAdapter extends BaseAdapter {

    private Context mContext = null;

    private List<MyMenuListModule> mLists = null;

    private LayoutInflater mLayoutInflater = null;

    private ViewHolder  mViewHolder = null;

    public MenuAdapter(Context context, List<MyMenuListModule> list) {
        this.mContext = context;
        this.mLists = list;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        if (null != mLists){
            return mLists.size();
        }else{
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        if (null != mLists){
            return mLists.get(i);
        }else{
            return null;
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (null == view){
            mViewHolder = new ViewHolder();
            view = mLayoutInflater.inflate(R.layout.flip_menu_layout, null);
            mViewHolder.mTextView = (TextView) view.findViewById(R.id.adapter_textView);
            view.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) view.getTag();
        }
        String content = mLists.get(position).text;
        if (!TextUtils.isEmpty(content)){
            mViewHolder.mTextView.setText(content);
        }
        return view;
    }

    private class ViewHolder{

        private TextView mTextView;
    }
}
