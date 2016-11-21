package com.main.MyClass;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.mayintao.test.R;

public class Main3Activity extends Activity {

    private TextView mTextViews = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        initView();
        
    }

    /**
     * 初始化view
     *
     */
    private void initView() {
        mTextViews = (TextView)findViewById(R.id.main3_textView);
        getInitData();
    }

    private void getInitData() {
        String data = getIntent().getExtras().get("CONTENT").toString();
        if (!TextUtils.isEmpty(data)){
            mTextViews.setText(data);
        }
    }


}
