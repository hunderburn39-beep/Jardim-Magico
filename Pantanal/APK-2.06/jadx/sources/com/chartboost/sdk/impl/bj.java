package com.chartboost.sdk.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tapjoy.TJAdUnitConstants;

/* JADX INFO: loaded from: classes.dex */
public final class bj extends LinearLayout {
    private TextView a;
    private bl b;

    public bj(Context context) {
        super(context);
        a(context);
    }

    @SuppressLint({"SetTextI18n"})
    private void a(Context context) {
        setGravity(17);
        this.a = new TextView(getContext());
        this.a.setTextColor(-1);
        this.a.setTextSize(2, 16.0f);
        this.a.setTypeface(null, 1);
        this.a.setText(TJAdUnitConstants.SPINNER_TITLE);
        this.a.setGravity(17);
        this.b = new bl(getContext());
        addView(this.a);
        addView(this.b);
        a();
    }

    public void a() {
        removeView(this.a);
        removeView(this.b);
        float f = getContext().getResources().getDisplayMetrics().density;
        int iRound = Math.round(20.0f * f);
        setOrientation(1);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.setMargins(iRound, iRound, iRound, 0);
        addView(this.a, layoutParams);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, Math.round(f * 32.0f));
        layoutParams2.setMargins(iRound, iRound, iRound, iRound);
        addView(this.b, layoutParams2);
    }
}
