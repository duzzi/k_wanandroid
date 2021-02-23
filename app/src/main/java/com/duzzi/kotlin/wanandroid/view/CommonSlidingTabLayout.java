package com.duzzi.kotlin.wanandroid.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.core.content.ContextCompat;

import com.duzzi.kotlin.wanandroid.R;
import com.flyco.tablayout.SlidingTabLayout;

public class CommonSlidingTabLayout extends SlidingTabLayout {

    public CommonSlidingTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setIndicatorWidth(24); // 设置指示器宽度（单位dp）
        setIndicatorHeight(3); // 设置指示器高度（单位dp）
        setIndicatorCornerRadius(1.5f); // 设置指示器圆角半径（单位dp）
        int colorStart = ContextCompat.getColor(getContext(), R.color.colorAccent);
        int colorEnd = ContextCompat.getColor(getContext(), R.color.colorAccent);
        setIndicatorColors(new int[]{colorStart, colorEnd}); // 设置指示器颜色
        setTextSelectColor(ContextCompat.getColor(getContext(), R.color.primaryText)); // 设置选中后文字颜色
        setTextUnselectColor(ContextCompat.getColor(getContext(), R.color.primaryText)); // 设置不选中时文字颜色
        setTextBold(TEXT_BOLD_WHEN_SELECT); // 设置文字加粗
        setTabSpaceEqual(false);
    }
}
