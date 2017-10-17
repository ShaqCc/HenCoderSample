package com.bayin.hencoder.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bayin.hencoder.R;

/****************************************
 * 功能说明:
 *
 * Author: Created by bayin on 2017/10/13.
 ****************************************/

public class ThumbLayout extends LinearLayout implements View.OnClickListener {

    private ThumbView mThumbview;
    private NumberLayout mNumberView;

    public ThumbLayout(Context context) {
        this(context, null);
    }

    public ThumbLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThumbLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.give_thumbsup_layout, this, true);
        mThumbview = (ThumbView) findViewById(R.id.ThumbView);
        mNumberView = (NumberLayout) findViewById(R.id.NumberView);
        setOnClickListener(this);
    }

    /**
     * 点赞
     */
    public void thumbsUp() {
        mThumbview.thumbsUp();
        mNumberView.thumbsAdd();
    }

    /**
     * 取消赞
     */
    public void thumbsCancel() {
        mThumbview.thumbsCancel();
        mNumberView.thumbsReduce();
    }

    /**
     * 设置状态
     *
     * @param check 是否点赞
     */
    public void setChecked(boolean check) {

    }

    public void setNumber(int number) {
        mNumberView.setNumber(number);
    }

    /**
     * @return 当前状态
     */
    public boolean isChecked() {
        return mThumbview.isChecked();
    }

    @Override
    public void onClick(View v) {
        if (isChecked()) {
            thumbsCancel();
        } else thumbsUp();
    }
}
