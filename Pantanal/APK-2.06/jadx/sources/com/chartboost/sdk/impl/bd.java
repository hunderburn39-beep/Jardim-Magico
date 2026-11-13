package com.chartboost.sdk.impl;

import android.content.Context;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public final class bd extends View {
    private boolean a;

    public bd(Context context) {
        super(context);
        this.a = false;
        setFocusable(false);
        setBackgroundColor(-1442840576);
    }

    public void a(bc bcVar) {
        if (!this.a) {
            bcVar.a(true, this);
            this.a = true;
        }
    }
}
