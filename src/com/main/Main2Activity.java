package com.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.main.MyClass.Main3Activity;
import com.main.adapter.MenuAdapter;
import com.main.module.MyMenuListModule;
import com.mayintao.test.R;

import com.com.baseclass.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageButton mBackButton = null;

    private ImageButton mSearchButton = null;

    //签到LinearLayout
    private LinearLayout mSignedLinearLayout = null;

    //消费LinearLayout
    private LinearLayout mPayLinearLayout = null;

    //撤销LinearLayout
    private LinearLayout mCancelPayLinearLayout = null;

    //策划菜单
    private DrawerLayout mDrawerLayout = null;

    //菜单
    private ListView mListView = null;

    private MenuAdapter mMenuAdapter = null;

    private List<MyMenuListModule>  mList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main2);

        setTitle("欢迎使用银加收银");

        initView();

        initData();
    }


    /**
     * 初始化view
     *
     */
    private void initView() {
        mBackButton = (ImageButton)findViewById(R.id.back_imageButton);
        //mBackButton.setBackground(getResources().getDrawable(R.drawable.ic_launcher));
        mBackButton.setOnClickListener(this);
        mSearchButton = (ImageButton)findViewById(R.id.next_imageButton);
        //mSearchButton.setBackground(getResources().getDrawable(R.drawable.ic_launcher));
        mSearchButton.setOnClickListener(this);
        mSignedLinearLayout = (LinearLayout)findViewById(R.id.up_left_layout);
        mSignedLinearLayout.setOnClickListener(this);
        mPayLinearLayout = (LinearLayout)findViewById(R.id.up_middle_layout);
        mPayLinearLayout.setOnClickListener(this);
        mCancelPayLinearLayout = (LinearLayout)findViewById(R.id.up_right_layout);
        mCancelPayLinearLayout.setOnClickListener(this);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mListView = (ListView)findViewById(R.id.left_drawer);
    }

    /**
     * 初始化数据
     *
     */
    private void initData() {
        mList = new ArrayList<MyMenuListModule>();
        getData();
        mMenuAdapter = new MenuAdapter(this, mList);
        mListView.setAdapter(mMenuAdapter);
        mListView.setOnItemClickListener(this);
    }

    /**
     * 获取菜单数据
     *
     */
    private void getData() {
        for (int i = 0; i < 5; i++){
            MyMenuListModule module = new MyMenuListModule();
            module.text = "菜单" + i;
            mList.add(module);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_imageButton:

                break;
            case R.id.next_imageButton:

                break;
            case R.id.up_left_layout:
                //签到
                callPay(new Intent(), 50);
                break;
            case R.id.up_middle_layout:
                //消费
                Intent intent = new Intent();
                intent.putExtra("amount", 1000);//金额统一以分为单位,long类型
                callPay(intent, 101);
                break;
            case R.id.up_right_layout:
                FlingFromLeft();
                break;
        }
    }

    /**
     * 从左侧滑出
     *
     */
    private void FlingFromLeft() {

    }


    private void callPay(Intent intent, int transType) {
        intent.setAction("com.ys.smartpos.pay.sdk");
        intent.putExtra("transType", transType);
        startActivityForResult(intent, transType);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (null != mList){
            MyMenuListModule getItem = mList.get(i);
            Toast.makeText(this,getItem.text,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
            intent.putExtra("CONTENT", getItem.text);
            startActivity(intent);
        }
    }
}
