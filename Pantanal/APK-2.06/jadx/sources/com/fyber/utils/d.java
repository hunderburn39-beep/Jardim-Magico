package com.fyber.utils;

/* JADX INFO: compiled from: Dispatchable.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class d implements Runnable {
    public abstract void a();

    @Override // java.lang.Runnable
    public void run() {
        a();
    }
}
