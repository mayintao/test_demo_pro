package com.com.baseclass;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mayintao.test.R;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setTitle(CharSequence title) {
        TextView mTitleTextView = (TextView)findViewById(R.id.title_textView);
        if (!TextUtils.isEmpty(title)){
            mTitleTextView.setText(title);
        }else{
            super.setTitle(title);
        }
    }
}
