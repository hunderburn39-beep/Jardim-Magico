package com.adcolony.sdk;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class ar {
    final String a = "AdColonyTest";
    String b;
    Runnable c;
    int d;

    ar(Runnable runnable, String str, int i) {
        this.c = runnable;
        this.b = str;
        this.d = i;
    }

    void a() {
        this.c.run();
    }

    void b() {
        Log.i("AdColonyTest", "Test for " + this.b + " has completed.");
        Log.i("AdColonyTest", "Passed: true");
        Log.i("AdColonyTest", "---------------------------------------------------------");
        a.m.n.a(true);
    }

    void a(String str) {
        Log.i("AdColonyTest", "Test for " + this.b + " has completed.");
        Log.i("AdColonyTest", "Passed: false");
        Log.i("AdColonyTest", "Reason: " + str);
        Log.i("AdColonyTest", "---------------------------------------------------------");
        a.m.n.a(false);
    }
}
