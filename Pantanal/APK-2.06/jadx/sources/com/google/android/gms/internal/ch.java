package com.google.android.gms.internal;

import android.R;
import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

/* JADX INFO: loaded from: classes.dex */
public final class ch extends FrameLayout implements View.OnClickListener {
    private final Activity nS;
    private final ImageButton oB;

    public ch(Activity activity, int i) {
        super(activity);
        this.nS = activity;
        setOnClickListener(this);
        this.oB = new ImageButton(activity);
        this.oB.setImageResource(R.drawable.btn_dialog);
        this.oB.setBackgroundColor(0);
        this.oB.setOnClickListener(this);
        this.oB.setPadding(0, 0, 0, 0);
        int iA = dv.a(activity, i);
        addView(this.oB, new FrameLayout.LayoutParams(iA, iA, 17));
    }

    public void i(boolean z) {
        this.oB.setVisibility(z ? 4 : 0);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        this.nS.finish();
    }
}
