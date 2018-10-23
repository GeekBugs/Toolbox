package com.f1reking.toolbox;

import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * TextView 文本显示工具类
 * Created by F1ReKing on 2016/6/7.
 */
public class TextViewUtils {

    public TextViewUtils() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * 给同一个TextView设置不同的颜色
     *
     * @param textView 文本控件
     * @param str 设置的文本
     * @param start 显示其他颜色的起始位置
     * @param end 显示其他颜色的终止位置
     * @param colorResId 颜色的资源id
     */
    public static void setDiffColorText(TextView textView, String str, int start, int end, int colorResId) {
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        ForegroundColorSpan spanColor =
            new ForegroundColorSpan(ContextCompat.getColor(textView.getContext(), colorResId));
        builder.setSpan(spanColor, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(builder);
    }

    /**
     * 同一个TextView设置不同字体大小
     *
     * @param textView 文本控件
     * @param str 设置的文本
     * @param start 显示其他字体大小的起始位置
     * @param end 显示其他字体大小的终止位置
     * @param textSize 字体大小
     */
    public static void setDiffSizeText(TextView textView, String str, int start, int end, int textSize) {
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        AbsoluteSizeSpan spanSize = new AbsoluteSizeSpan(DensityUtils.sp2px(textView.getContext(), textSize));
        builder.setSpan(spanSize, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(builder);
    }

    /**
     * 同一个TextView设置不同字体颜色以及字体大小
     *
     * @param textView 文本控件
     * @param str 设置的文本
     * @param start 显示其他字体大小的起始位置
     * @param end 显示其他字体大小的终止位置
     * @param colorResId 颜色的资源id
     * @param textSize 字体大小
     */
    public static void setDiffColorAndSizeText(TextView textView, String str, int start, int end, int colorResId,
        int textSize) {
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        ForegroundColorSpan spanColor =
            new ForegroundColorSpan(ContextCompat.getColor(textView.getContext(), colorResId));
        AbsoluteSizeSpan spanSize = new AbsoluteSizeSpan(DensityUtils.sp2px(textView.getContext(), textSize));
        builder.setSpan(spanColor, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        builder.setSpan(spanSize, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(builder);
    }
}
