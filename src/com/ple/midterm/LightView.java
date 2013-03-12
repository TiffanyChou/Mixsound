package com.ple.midterm;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AlphaAnimation;
public class LightView extends View {

    private AlphaAnimation mAnimation;
    private Random random = new Random();

    // 繪圖顏色
    private int mColor = Color.BLACK;
    private int[] mColors = new int[] {
            Color.rgb(237,  26,  61), // 紅
            Color.rgb(255, 183,  76), // 橙
            Color.rgb(255, 212,   0), // 黃
            Color.rgb(  0, 128,   0), // 綠
            Color.rgb(  0, 154, 214), // 藍
            Color.rgb( 35,  71, 148), // 靛
            Color.rgb(167,  87, 168)  // 紫
    };

    public LightView(Context context) {
        super(context);

        // 產生漸漸透明的 AlphaAnimation
        mAnimation = new AlphaAnimation(1, 0);
        // 設定動3秒
        mAnimation.setDuration(3000);
        // 結束後，不回到原始狀態
        mAnimation.setFillAfter(true);
    }

    public void randomDraw() {
        // 為了在動畫中能運作下個動畫
        // 所以先取消
        clearAnimation();
        // 隨機選取顏色
        mColor = mColors[random.nextInt(mColors.length - 1)];
        // 開始動畫
        startAnimation(mAnimation);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 指定畫面塗滿指定顏色
        canvas.drawColor(mColor);
    }
}