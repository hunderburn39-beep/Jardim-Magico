package com.chartboost.sdk.impl;

import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
class a implements Comparable<a> {
    final int a;
    final String b;
    final String c;
    final String d;
    final AtomicInteger e;

    a(int i, String str, String str2, String str3, AtomicInteger atomicInteger) {
        this.a = i;
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = atomicInteger;
        atomicInteger.incrementAndGet();
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(a aVar) {
        return aVar.a - this.a;
    }
}
