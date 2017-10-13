package com.bayin.hencoder.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bayin.hencoder.R;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/10/13.
 ****************************************/

public class ThumbView extends FrameLayout {
    private CheckBox mCheckBox;
    private AnimatorSet mSet;
    private ImageView mThumbLightView;
    private ObjectAnimator mAppearAnim;
    private ObjectAnimator mDismissAnim;

    public ThumbView(@NonNull Context context) {
        this(context, null);
    }

    public ThumbView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThumbView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.thumb_layout, this, true);
        initialize();
    }

    private void initialize() {
        setWillNotDraw(false);
        mCheckBox = (CheckBox) findViewById(R.id.thumb_checkbox);
        mCheckBox.setClickable(false);
        mCheckBox.setEnabled(false);
        initAnimation();
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSet.start();
            }
        });
        mThumbLightView = (ImageView) findViewById(R.id.iv_thumblight);
//        mCheckBox.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        scaleSmall(v);
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        scaleBig(v);
//                        break;
//                }
//                return false;
//            }
//        });
    }

    private void initAnimation() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mCheckBox, "scaleX", 0.8f, 1.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mCheckBox, "scaleY", 0.8f, 1.2f, 1f);

        mAppearAnim = ObjectAnimator.ofFloat(mThumbLightView, "alpha", 0f, 1f);
        mAppearAnim.setDuration(100);
//        mAppearAnim.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                mThumbLightView.setVisibility(VISIBLE);
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
        mDismissAnim = ObjectAnimator.ofFloat(mThumbLightView, "alpha", 1f, 0f);
        mDismissAnim.setDuration(100);

        scaleX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                if (mCheckBox.isChecked()) {
                    if (fraction > 0.5) {
                        mThumbLightView.setVisibility(VISIBLE);
                    }
                } else {
                    if (fraction > 0.2) {
                        mThumbLightView.setVisibility(INVISIBLE);
                    }
                }
            }
        });
        mSet = new AnimatorSet();
        mSet.setDuration(300);
        mSet.playTogether(scaleX, scaleY);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    /**
     * 点赞动画
     */
    public void thumbsUp() {
        mCheckBox.setChecked(true);
    }

    /**
     * 取消点赞
     */
    public void thumbsCancel() {
        mCheckBox.setChecked(false);
    }

    /**
     * @return 点赞状态
     */
    public boolean isChecked() {
        return mCheckBox.isChecked();
    }
}
