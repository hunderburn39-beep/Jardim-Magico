package com.chartboost.sdk.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.ExploreByTouchHelper;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"ViewConstructor"})
public class o extends LinearLayout {
    final l a;
    final bf b;
    private LinearLayout c;
    private be d;
    private TextView e;
    private int f;

    public o(Context context, l lVar) {
        super(context);
        this.f = ExploreByTouchHelper.INVALID_ID;
        this.a = lVar;
        int iRound = Math.round(context.getResources().getDisplayMetrics().density * 8.0f);
        setOrientation(1);
        setGravity(17);
        this.c = new LinearLayout(context);
        this.c.setGravity(17);
        this.c.setOrientation(0);
        this.c.setPadding(iRound, iRound, iRound, iRound);
        this.d = new be(context);
        this.d.setScaleType(ImageView.ScaleType.FIT_CENTER);
        this.d.setPadding(0, 0, iRound, 0);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        lVar.a(layoutParams, lVar.I, 1.0f);
        this.e = new TextView(getContext());
        this.e.setTextColor(-1);
        this.e.setTypeface(null, 1);
        this.e.setGravity(17);
        this.e.setTextSize(2, com.chartboost.sdk.f.a(context) ? 26.0f : 16.0f);
        this.c.addView(this.d, layoutParams);
        this.c.addView(this.e, new LinearLayout.LayoutParams(-2, -2));
        this.b = new bf(getContext()) { // from class: com.chartboost.sdk.impl.o.1
            @Override // com.chartboost.sdk.impl.bf
            protected void a(MotionEvent motionEvent) {
                o.this.b.setEnabled(false);
                o.this.a.e().h();
            }
        };
        this.b.setContentDescription("CBWatch");
        this.b.setPadding(0, 0, 0, iRound);
        this.b.a(ImageView.ScaleType.FIT_CENTER);
        this.b.setPadding(iRound, iRound, iRound, iRound);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
        lVar.a(layoutParams2, lVar.H, 1.0f);
        if (lVar.I.e()) {
            this.d.a(lVar.I);
        }
        if (lVar.H.e()) {
            this.b.a(lVar.H);
        }
        addView(this.c, new LinearLayout.LayoutParams(-2, -2));
        addView(this.b, layoutParams2);
        a();
    }

    public void a(boolean z) {
        setBackgroundColor(z ? -16777216 : this.f);
    }

    public void a(String str, int i) {
        this.e.setText(str);
        this.f = i;
        a(this.a.t());
    }

    public void a() {
        a(this.a.t());
    }
}
