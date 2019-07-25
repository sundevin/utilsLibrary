package com.devin.utilscenter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.devin.util.ImageLoader;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        textView.setText("具体请查看Java doc");
        String url="http://f.hiphotos.baidu.com/image/pic/item/a71ea8d3fd1f4134d244519d2b1f95cad0c85ee5.jpg";

        ImageLoader.loadBorderCircle(url,2, Color.WHITE,imageView);
    }

    private void initView() {
        textView =  findViewById(R.id.textView);
        imageView =  findViewById(R.id.imageView);
    }
}