package com.devin.utilscenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;
    private View image;
    private LinearLayout ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        textView.setText("具体请查看Java doc");
    }

    private void initView() {
        textView = (TextView) findViewById(R.id.textView);
        ll = (LinearLayout) findViewById(R.id.ll);
    }
}