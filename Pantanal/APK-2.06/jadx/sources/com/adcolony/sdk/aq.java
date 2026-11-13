package com.adcolony.sdk;

import android.util.Log;
import java.util.HashMap;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
class aq {
    String d;
    volatile boolean e;
    final String a = "AdColonyTest";
    boolean f = true;
    int g = 0;
    int h = 0;
    HashMap<Integer, ar> b = new HashMap<>();
    LinkedList<ar> c = new LinkedList<>();

    aq() {
    }

    void a(boolean z) {
        if (z) {
            this.g++;
        } else {
            this.h++;
        }
        if (this.c.peek() != null) {
            ar arVarRemove = this.c.remove();
            Log.i("AdColonyTest", "Starting test for " + arVarRemove.b);
            arVarRemove.a();
        } else {
            Log.i("AdColonyTest", "TEST PASS COMPLETED");
            Log.i("AdColonyTest", "PASSED: " + this.g);
            Log.i("AdColonyTest", "FAILED: " + this.h);
            this.f = true;
        }
    }

    void a(ar arVar) {
        if (!this.f) {
            this.c.add(arVar);
            return;
        }
        this.f = false;
        Log.i("AdColonyTest", "Starting test for " + arVar.b);
        arVar.a();
    }

    void a(int i, Runnable runnable, String str) {
        ar arVar = new ar(runnable, str, i);
        this.b.put(Integer.valueOf(i), arVar);
        a(arVar);
    }
}
