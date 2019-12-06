package com.example.jingbin.cloudreader.view;

import android.content.Context;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * @author jingbin
 * @data 2019-12-06
 * @description 中等的黑
 */
public class ThinBoldSpan extends CharacterStyle {

    private Context context;
    private float f;

    public ThinBoldSpan(Context context, float f) {
        this.context = context;
        this.f = f;
    }

    public static SpannableString getDefaultSpanString(Context context, String s) {
        return getSpanString(context, s, 0.5f);
    }

    public static SpannableString getSpanString(Context context, String s, float f) {
        if (context == null || s == null) {
            return new SpannableString("");
        }
        SpannableString spannableString = new SpannableString(s);
        ThinBoldSpan span = new ThinBoldSpan(context, f);
        spannableString.setSpan(span, 0, s.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        tp.setStyle(Paint.Style.FILL_AND_STROKE);
        //控制字体加粗的程度
        tp.setStrokeWidth(dpToPx(context, f));
    }

    public int dpToPx(Context context, float dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }
}

