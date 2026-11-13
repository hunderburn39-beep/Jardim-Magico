package com.google.android.gms.internal;

import com.google.android.gms.internal.kp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class kp<M extends kp<M>> extends kt {
    protected List<kv> adU;

    public final <T> T a(kq<M, T> kqVar) {
        return kqVar.f(this.adU);
    }

    @Override // com.google.android.gms.internal.kt
    public void a(ko koVar) throws IOException {
        int size = this.adU == null ? 0 : this.adU.size();
        for (int i = 0; i < size; i++) {
            kv kvVar = this.adU.get(i);
            koVar.da(kvVar.tag);
            koVar.p(kvVar.adZ);
        }
    }

    protected final boolean a(kn knVar, int i) throws IOException {
        int position = knVar.getPosition();
        if (!knVar.cQ(i)) {
            return false;
        }
        if (this.adU == null) {
            this.adU = new ArrayList();
        }
        this.adU.add(new kv(i, knVar.h(position, knVar.getPosition() - position)));
        return true;
    }

    @Override // com.google.android.gms.internal.kt
    protected int mx() {
        int size = this.adU == null ? 0 : this.adU.size();
        int iDb = 0;
        for (int i = 0; i < size; i++) {
            kv kvVar = this.adU.get(i);
            iDb = iDb + ko.db(kvVar.tag) + kvVar.adZ.length;
        }
        return iDb;
    }
}
