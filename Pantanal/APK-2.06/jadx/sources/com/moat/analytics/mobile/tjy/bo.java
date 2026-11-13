package com.moat.analytics.mobile.tjy;

import android.view.ViewGroup;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
class bo implements Iterable {
    private final ViewGroup a;

    private bo(ViewGroup viewGroup) {
        this.a = viewGroup;
    }

    @Override // java.lang.Iterable
    public Iterator iterator() {
        return new bp(this);
    }
}
