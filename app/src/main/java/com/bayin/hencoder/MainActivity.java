package com.bayin.hencoder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bayin.hencoder.view.ThumbLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ThumbLayout thumbsLayout1 = (ThumbLayout) findViewById(R.id.thumbLayout1);
        ThumbLayout thumbsLayout2 = (ThumbLayout) findViewById(R.id.thumbLayout2);
        ThumbLayout thumbsLayout3 = (ThumbLayout) findViewById(R.id.thumbLayout3);
        ThumbLayout thumbsLayout4 = (ThumbLayout) findViewById(R.id.thumbLayout4);
        ThumbLayout thumbsLayout5 = (ThumbLayout) findViewById(R.id.thumbLayout5);
        thumbsLayout1.setNumber(8);
        thumbsLayout2.setNumber(19);
        thumbsLayout3.setNumber(99);
        thumbsLayout4.setNumber(1029);
        thumbsLayout5.setNumber(9999);

        thumbsLayout2.setChecked(true);
    }
}
