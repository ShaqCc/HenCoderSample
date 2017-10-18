package com.bayin.hencoder.view;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayin.hencoder.R;

import java.util.ArrayList;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/10/17.
 ****************************************/

public class NumberLayout extends FrameLayout {

    private LinearLayout mAboveLayout;
    private LinearLayout mBelowLayout;
    private int number = 0;
    private long DURATION = 500;
    private AnimationSet mSet1;
    private AnimationSet mSet2;
    private AnimationSet mSet3;
    private AnimationSet mSet4;
    private Handler mHandler = new Handler();
    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        if (checked) {
            refreshView(mBelowLayout, number);
            refreshView(mAboveLayout, number - 1);
            setChildVisible(mAboveLayout, false);
        } else {
            refreshView(mAboveLayout, number);
            refreshView(mBelowLayout, number + 1);
            setChildVisible(mBelowLayout, false);
        }
    }

    public NumberLayout(@NonNull Context context) {
        this(context, null);
    }

    public NumberLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.number_layout, this, true);
        mAboveLayout = (LinearLayout) findViewById(R.id.ll_above);
        mBelowLayout = (LinearLayout) findViewById(R.id.ll_below);
        init();
    }

    private void init() {
        setChecked(isChecked());
        initAnimation();
    }

    /**
     * 初始化4个动画
     * 1.当前数字上升
     * 2.当前数字下降
     * 3.下面的数字上升
     * 4.下面的数字下降
     */
    private void initAnimation() {
        /*可以公用的动画*/
        //透明
        AlphaAnimation alphaTrans = new AlphaAnimation(1f, 0.2f);
        alphaTrans.setFillAfter(true);
        //缩小
        ScaleAnimation scaleSmall = new ScaleAnimation(1f, 0.9f, 1f, 0.9f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleSmall.setFillAfter(true);
        //不透明
        AlphaAnimation alphaSolid = new AlphaAnimation(0.2f, 1f);
        alphaSolid.setFillAfter(true);
        //放大
        ScaleAnimation scaleBig = new ScaleAnimation(0.9f, 1f, 0.9f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleBig.setFillAfter(true);
        //1.当前数字上升
        TranslateAnimation y1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1f);
        y1.setFillAfter(true);
        mSet1 = new AnimationSet(true);
        mSet1.addAnimation(y1);
        mSet1.addAnimation(alphaTrans);
        mSet1.addAnimation(scaleSmall);
        mSet1.setDuration(DURATION);
        mSet1.setFillAfter(true);
        //2.当前数字下降
        TranslateAnimation y2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f);
        y2.setFillAfter(true);
        mSet2 = new AnimationSet(true);
        mSet2.addAnimation(y2);
        mSet2.addAnimation(scaleBig);
        mSet2.addAnimation(alphaSolid);
        mSet2.setDuration(DURATION);
        mSet2.setFillAfter(true);
        //3.下面的数字上升
        TranslateAnimation y3 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f);
        ScaleAnimation scale12To1 = new ScaleAnimation(1.2f, 1f, 1.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale12To1.setFillAfter(true);
        y3.setFillAfter(true);
        mSet3 = new AnimationSet(true);
        mSet3.addAnimation(y3);
        mSet3.addAnimation(scale12To1);
        mSet3.addAnimation(alphaSolid);
        mSet3.setDuration(DURATION);
        mSet3.setFillAfter(true);
        //4.下面的数字下降
        TranslateAnimation y4 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1f);
        y4.setFillAfter(true);
        mSet4 = new AnimationSet(true);
        mSet4.addAnimation(y4);
        mSet4.addAnimation(scaleBig);
        mSet4.addAnimation(alphaTrans);
        mSet4.setDuration(DURATION);
        mSet4.setFillAfter(true);
    }

    /**
     * 更新ui
     */
    private void refreshView(ViewGroup parent, int number) {
        parent.removeAllViews();
        String num = String.valueOf(number);
        for (int i = 0; i < num.length(); i++) {
            TextView textView = new TextView(getContext());
            textView.setText(num.charAt(i) + "");
            parent.addView(textView);
        }
    }

    public void setNumber(int number) {
        this.number = number;
        init();
    }

    public int getNumber() {
        return this.number;
    }

    public void thumbsAdd() {
        //计算哪几个view要动
        AddAnimation();
        number++;
    }

    public void thumbsReduce() {
        reduceAnimation();
        number--;
    }

    private void setChildVisible(ViewGroup parent, boolean visible) {

        for (int i = 0; i < parent.getChildCount(); i++) {
            if (visible) {
                parent.getChildAt(i).setVisibility(VISIBLE);
            } else {
                parent.getChildAt(i).setVisibility(INVISIBLE);
            }
        }
    }

    private void AddAnimation() {
        int temp = number + 1;
        //判断加1后，位数是否相同，不相同，所有view都要移动
        String strCurrent = String.valueOf(number);
        String strNext = String.valueOf(temp);

        if (strCurrent.length() != strNext.length()) {
            //加1之后位数改变了
            mAboveLayout.startAnimation(mSet1);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBelowLayout.startAnimation(mSet3);
                    setChildVisible(mBelowLayout, true);
                }
            }, DURATION / 2);
        } else {
            ArrayList<View> viewList1 = new ArrayList<>();
            final ArrayList<View> viewList2 = new ArrayList<>();
            //位数相同，计算哪几位不同，就移动哪几个
            for (int i = strCurrent.length() - 1; i >= 0; i--) {
                if (strCurrent.charAt(i) != strNext.charAt(i)) {
                    viewList1.add(mAboveLayout.getChildAt(i));
                    viewList2.add(mBelowLayout.getChildAt(i));
                } else break;
            }

            //开启动画
            for (int i = 0; i < viewList1.size(); i++) {
                viewList1.get(i).startAnimation(mSet1);
                final int t = i;
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewList2.get(t).startAnimation(mSet3);
                        viewList2.get(t).setVisibility(VISIBLE);
                    }
                }, DURATION / 2);
            }
        }
    }


    /**
     * 执行取消赞的动画
     */
    private void reduceAnimation() {
        int temp = number - 1;
        //判断加1后，位数是否相同，不相同，所有view都要移动
        String strCurrent = String.valueOf(number);
        String strNext = String.valueOf(temp);

        if (strCurrent.length() != strNext.length()) {
            //加1之后位数改变了
            mBelowLayout.startAnimation(mSet4);
            mBelowLayout.setVisibility(VISIBLE);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAboveLayout.startAnimation(mSet2);
                }
            }, DURATION / 2);
        } else {
            final ArrayList<View> viewList1 = new ArrayList<>();
            final ArrayList<View> viewList2 = new ArrayList<>();
            //位数相同，计算哪几位不同，就移动哪几个
            for (int i = strCurrent.length() - 1; i >= 0; i--) {
                if (strCurrent.charAt(i) != strNext.charAt(i)) {
                    viewList1.add(mAboveLayout.getChildAt(i));
                    viewList2.add(mBelowLayout.getChildAt(i));
                } else break;
            }

            //开启动画
            for (int i = 0; i < viewList1.size(); i++) {
                viewList2.get(i).startAnimation(mSet4);
                final int t = i;
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewList1.get(t).startAnimation(mSet2);
                    }
                }, DURATION / 2);
            }
        }
    }
}
