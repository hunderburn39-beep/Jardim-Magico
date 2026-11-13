package com.google.android.gms.internal;

/* JADX INFO: loaded from: classes.dex */
public final class bl extends bs.a {
    private final Object li = new Object();
    private bn.a nl;
    private bk nm;

    @Override // com.google.android.gms.internal.bs
    public void P() {
        synchronized (this.li) {
            if (this.nm != null) {
                this.nm.X();
            }
        }
    }

    public void a(bk bkVar) {
        synchronized (this.li) {
            this.nm = bkVar;
        }
    }

    public void a(bn.a aVar) {
        synchronized (this.li) {
            this.nl = aVar;
        }
    }

    @Override // com.google.android.gms.internal.bs
    public void onAdClosed() {
        synchronized (this.li) {
            if (this.nm != null) {
                this.nm.Y();
            }
        }
    }

    @Override // com.google.android.gms.internal.bs
    public void onAdFailedToLoad(int error) {
        synchronized (this.li) {
            if (this.nl != null) {
                this.nl.f(error == 3 ? 1 : 2);
                this.nl = null;
            }
        }
    }

    @Override // com.google.android.gms.internal.bs
    public void onAdLeftApplication() {
        synchronized (this.li) {
            if (this.nm != null) {
                this.nm.Z();
            }
        }
    }

    @Override // com.google.android.gms.internal.bs
    public void onAdLoaded() {
        synchronized (this.li) {
            if (this.nl != null) {
                this.nl.f(0);
                this.nl = null;
            } else {
                if (this.nm != null) {
                    this.nm.ab();
                }
            }
        }
    }

    @Override // com.google.android.gms.internal.bs
    public void onAdOpened() {
        synchronized (this.li) {
            if (this.nm != null) {
                this.nm.aa();
            }
        }
    }
}
