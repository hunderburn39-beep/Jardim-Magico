package com.fyber.c.b;

import android.content.Context;
import android.widget.RelativeLayout;

/* JADX INFO: compiled from: CloseXButtonLayout.java */
/* JADX INFO: loaded from: classes.dex */
public final class b extends com.fyber.c.a {
    public b(Context context) {
        super(context);
        int iA = a(15);
        c cVar = new c(context, 4.5f);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(iA, iA);
        layoutParams.addRule(13, -1);
        cVar.setLayoutParams(layoutParams);
        setPadding(this.a, this.a, this.a, this.a);
        addView(cVar);
    }
}
