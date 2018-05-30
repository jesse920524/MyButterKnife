package com.example.administrator.annotationtest201805.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.annotationtest201805.R;
import com.example.administrator.annotationtest201805.annotation.bindClick.OnClick_Reflect;
import com.example.module_annotation.BindView;
import com.example.module_annotation.OnClick;
import com.example.module_annotation_api.BindingApi;

/**
 * Created by Jesse Fu on 2018/5/25.
 *
 * 测试类, 用于测试编译时注解
 * @see {modlue_annotation, module_compiler, module_annotation_api}
 */

public class SecondActvity extends AppCompatActivity {
    private static final String TAG = "SecondActvity";

    public static void actionStart(@NonNull Context context){
        Intent intent = new Intent(context, SecondActvity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.tv_sec)
    TextView mTvDesc;
    @BindView(R.id.btn_sec)
    Button mBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        BindingApi.inject(this);

    }

    @OnClick(R.id.btn_sec)
    void onClickBtn(View view){
        Log.d(TAG, "onClickBtn: exec");
        mTvDesc.setText("aa!");

    }
}
