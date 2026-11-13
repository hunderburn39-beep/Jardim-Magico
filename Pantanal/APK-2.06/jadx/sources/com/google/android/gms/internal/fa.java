package com.google.android.gms.internal;

import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: classes.dex */
public final class fa extends fu<a, Drawable> {

    public static final class a {
        public final int CR;
        public final int CS;

        public a(int i, int i2) {
            this.CR = i;
            this.CS = i2;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof a)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            a aVar = (a) obj;
            return aVar.CR == this.CR && aVar.CS == this.CS;
        }

        public int hashCode() {
            return fo.hashCode(Integer.valueOf(this.CR), Integer.valueOf(this.CS));
        }
    }

    public fa() {
        super(10);
    }
}
