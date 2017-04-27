package com.devin.utilscenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.devin.util.Logger;
import com.devin.util.PinYinUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.d(PinYinUtils.chinese2PinYin("我是示例"));
    }
}