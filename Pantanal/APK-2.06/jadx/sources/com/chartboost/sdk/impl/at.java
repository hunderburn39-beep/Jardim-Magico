package com.chartboost.sdk.impl;

import android.graphics.Bitmap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

/* JADX INFO: loaded from: classes.dex */
public class at implements af {
    private final as a;
    private final PriorityBlockingQueue<ac<?>> b = new PriorityBlockingQueue<>();
    private final Set<ar> c = new HashSet();
    private final int d;

    public at(as asVar, int i) {
        this.a = asVar;
        this.d = i;
        a();
    }

    @Override // com.chartboost.sdk.impl.af
    public void a() {
        if (this.c.isEmpty()) {
            for (int i = 0; i < this.d; i++) {
                ar arVarA = this.a.a(this, this.b);
                this.c.add(arVarA);
                arVarA.start();
            }
        }
    }

    @Override // com.chartboost.sdk.impl.af
    public <T> void a(ac<T> acVar) {
        this.b.add(acVar);
    }

    @Override // com.chartboost.sdk.impl.af
    public void a(String str, ag<Bitmap> agVar, ab abVar) {
        a(new ap(str, agVar, abVar));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.chartboost.sdk.impl.af
    public void a(String str) {
        a(new ac<String>(ac.a.POST, str, null, 0 == true ? 1 : 0) { // from class: com.chartboost.sdk.impl.at.1
            @Override // com.chartboost.sdk.impl.ac
            public ae<String> a(ah ahVar) {
                return ae.b();
            }

            @Override // com.chartboost.sdk.impl.ac
            public void a(String str2) {
            }
        });
    }
}
