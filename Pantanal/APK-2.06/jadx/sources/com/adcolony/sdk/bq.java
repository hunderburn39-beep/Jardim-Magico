package com.adcolony.sdk;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
class bq extends bx {
    ArrayList<bp> a;

    bq(by byVar) {
        super(byVar);
        this.a = new ArrayList<>();
    }

    public void a(bp bpVar) {
        synchronized (this.a) {
            this.a.add(bpVar);
            c();
        }
    }

    protected void a() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.a) {
            arrayList.addAll(this.a);
            this.a.clear();
        }
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < arrayList.size()) {
                bp bpVar = (bp) arrayList.get(i2);
                if (bpVar != null) {
                    try {
                        bpVar.a();
                        synchronized (bpVar) {
                            try {
                                if (!bpVar.e()) {
                                    bpVar.wait();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    @Override // com.adcolony.sdk.bx
    public void b() {
        a();
    }
}
