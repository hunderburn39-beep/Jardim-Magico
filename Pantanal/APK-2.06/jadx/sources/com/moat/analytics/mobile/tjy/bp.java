package com.moat.analytics.mobile.tjy;

import android.view.View;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
class bp implements Iterator {
    final /* synthetic */ bo a;
    private int b;

    private bp(bo boVar) {
        this.a = boVar;
        this.b = -1;
    }

    @Override // java.util.Iterator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public View next() {
        this.b++;
        return this.a.a.getChildAt(this.b);
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.b + 1 < this.a.a.getChildCount();
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Not implemented. Under development.");
    }
}
