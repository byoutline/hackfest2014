package com.byoutline.hackfest.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.byoutline.secretsauce.utils.FontCache;

import com.byoutline.hackfest.R;


public class RadioButtonFont extends RadioButton {
    private String mDefaultFont;
    private String mCheckedFont;

    public RadioButtonFont(Context context) {
        super(context);
        setFont("");
    }

    public RadioButtonFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(attrs);
    }

    public RadioButtonFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(attrs);
    }

    private void setCustomFont(AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }
        setPaintFlags(getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG | Paint.DEV_KERN_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RadioButtonFont);
        mDefaultFont = a.getString(R.styleable.RadioButtonFont_defaultFont);
        mCheckedFont = a.getString(R.styleable.RadioButtonFont_checkedFont);
        setFont(mDefaultFont);
        a.recycle();
    }

    @Override
    public void toggle() {
        super.toggle();
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        setFont(checked ? mCheckedFont : mDefaultFont);
    }

    public void setFont(String font) {
        if (font == null || TextUtils.isEmpty(font)) {
            return;
        }
        Typeface tf = FontCache.get(font, getContext());
        if (tf != null) {
            setTypeface(tf);
        }
    }
}