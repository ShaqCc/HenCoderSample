package com.bayin.hencoder.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.bayin.hencoder.R;
import com.bayin.hencoder.Utils;

/**
 * Created by Administrator on 2017/10/18.
 */

public class Thumbs extends View {
    private static final String TAG = "Thumbs";
    private boolean isChecked = false;
    private Bitmap bmpLight;
    private Bitmap bmpThumbsDefault;
    private Bitmap bmpThumbsChecked;
    private Paint mPaint;
    private Paint mTextPaint;
    private int textSize = 15;

    private int padding;
    private int number = 0;
    private float textStartX;
    private float textStartY;

    public Thumbs(Context context) {
        this(context, null);
    }

    public Thumbs(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Thumbs(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.Thumbs);
        isChecked = attributes.getBoolean(R.styleable.Thumbs_thumbed, false);
        number = attributes.getInt(R.styleable.Thumbs_number, 0);
        attributes.recycle();

        bmpLight = BitmapFactory.decodeResource(context.getResources(), R.mipmap.thumb_light);
        bmpThumbsDefault = BitmapFactory.decodeResource(context.getResources(), R.drawable.thumb_default);
        bmpThumbsChecked = BitmapFactory.decodeResource(context.getResources(), R.drawable.thumb_checked);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(Color.parseColor("#666666"));
        mTextPaint.setStyle(Paint.Style.FILL);

        padding = Utils.dip2px(context, 10f);
        initLocation();
    }

    /**
     * 计算定位
     */
    private void initLocation() {
        //文字位置
        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        metrics.descent = textStartY;
        textStartX = bmpThumbsDefault.getWidth() + padding + Utils.dip2px(getContext(), 10);
//        textStartY = padding + bmpLight.getHeight() + bmpThumbsDefault.getHeight()-metrics.bottom;
        textStartY = padding + bmpLight.getHeight() + bmpThumbsDefault.getHeight()-Utils.dip2px(getContext(),2);
        Log.d(TAG, "文字起始位置" + textStartX + "   " + textStartY);

    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        invalidate();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画大拇指
        if (isChecked) {
            canvas.drawBitmap(bmpLight, padding + Utils.dip2px(getContext(), 3), padding, mPaint);
            canvas.drawBitmap(bmpThumbsChecked, padding, bmpLight.getHeight() + padding, mPaint);
        } else {
            canvas.drawBitmap(bmpThumbsDefault, padding, bmpLight.getHeight() + padding, mPaint);
        }
        canvas.drawLine(0,textStartY,100,textStartY,mTextPaint);
        //画字
        canvas.drawText(String.valueOf(number), textStartX, textStartY, mTextPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        String s = String.valueOf(number);
        int textWidth = (int) mTextPaint.measureText(s, 0, s.length());

        setMeasuredDimension(bmpThumbsDefault.getWidth() + textWidth + padding * 2 + Utils.dip2px(getContext(), 10),
                bmpThumbsDefault.getHeight() + bmpLight.getHeight() + padding * 2);
    }
}
