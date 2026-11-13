package com.chartboost.sdk.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chartboost.sdk.Libraries.CBUtility;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"ViewConstructor"})
public class m extends p {
    private LinearLayout b;
    private be c;
    private TextView d;

    public m(Context context, l lVar) {
        super(context, lVar);
    }

    @Override // com.chartboost.sdk.impl.p
    protected View a() {
        Context context = getContext();
        int iRound = Math.round(getContext().getResources().getDisplayMetrics().density * 8.0f);
        this.b = new LinearLayout(context);
        this.b.setOrientation(0);
        this.b.setGravity(17);
        int iA = CBUtility.a(36, context);
        this.c = new be(context);
        this.c.setPadding(iRound, iRound, iRound, iRound);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(iA, iA);
        this.c.setScaleType(ImageView.ScaleType.FIT_CENTER);
        this.d = new TextView(context);
        this.d.setPadding(iRound / 2, iRound, iRound, iRound);
        this.d.setTextColor(-15264491);
        this.d.setTextSize(2, 16.0f);
        this.d.setTypeface(null, 1);
        this.d.setGravity(17);
        this.b.addView(this.c, layoutParams);
        this.b.addView(this.d, new LinearLayout.LayoutParams(-2, -1));
        return this.b;
    }

    public void a(com.chartboost.sdk.Libraries.i iVar) {
        this.c.a(iVar);
        this.c.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    public void a(String str) {
        this.d.setText(str);
    }

    @Override // com.chartboost.sdk.impl.p
    protected int b() {
        return 48;
    }
}
