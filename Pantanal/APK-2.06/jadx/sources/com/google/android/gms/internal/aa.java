package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class aa implements ac {
    private final Object li = new Object();
    private WeakHashMap<dh, ab> lj = new WeakHashMap<>();
    private ArrayList<ab> lk = new ArrayList<>();

    public ab a(ak akVar, dh dhVar) {
        ab abVar;
        synchronized (this.li) {
            if (c(dhVar)) {
                abVar = this.lj.get(dhVar);
            } else {
                abVar = new ab(akVar, dhVar);
                abVar.a(this);
                this.lj.put(dhVar, abVar);
                this.lk.add(abVar);
            }
        }
        return abVar;
    }

    @Override // com.google.android.gms.internal.ac
    public void a(ab abVar) {
        synchronized (this.li) {
            if (!abVar.at()) {
                this.lk.remove(abVar);
            }
        }
    }

    public boolean c(dh dhVar) {
        boolean z;
        synchronized (this.li) {
            ab abVar = this.lj.get(dhVar);
            z = abVar != null && abVar.at();
        }
        return z;
    }

    public void d(dh dhVar) {
        synchronized (this.li) {
            ab abVar = this.lj.get(dhVar);
            if (abVar != null) {
                abVar.ar();
            }
        }
    }
}
