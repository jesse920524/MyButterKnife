package com.example.administrator.annotationtest201805.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.annotationtest201805.R;
import com.example.administrator.annotationtest201805.annotation.bindClick.BindOnClickApi;
import com.example.administrator.annotationtest201805.annotation.bindClick.OnClick_Reflect;
import com.example.administrator.annotationtest201805.annotation.bindId.BindId;
import com.example.administrator.annotationtest201805.annotation.bindId.BindIdApi;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @BindId(R.id.btn_main_1)
    private Button mBtn1;
    @BindId(R.id.btn_main_2)
    private Button mBtn2;

    private String arg = "arg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BindIdApi.bind(this);
        BindOnClickApi.bind(this);
        initViews();
    }


    private void initViews() {

    }

    @OnClick_Reflect(R.id.btn_main_1)
    private void onClickBtn1(View view){
        Log.d(TAG, "onClickBtn1: exec");

        SecondActvity.actionStart(MainActivity.this);
    }

    @OnClick_Reflect(R.id.btn_main_2)
    private void onClickBtn2(View view){
        Log.d(TAG, "onClickBtn2: exec");
    }
}
