package com.tapjoy.internal;

/* JADX INFO: loaded from: classes.dex */
public final class gx {
    public float a;
    public int b;

    public static gx a(String str) {
        if (cr.c(str)) {
            return null;
        }
        try {
            gx gxVar = new gx();
            int length = str.length();
            char cCharAt = str.charAt(length - 1);
            if (cCharAt == 'w') {
                gxVar.a = Float.valueOf(str.substring(0, length - 1)).floatValue();
                gxVar.b = 1;
            } else if (cCharAt == 'h') {
                gxVar.a = Float.valueOf(str.substring(0, length - 1)).floatValue();
                gxVar.b = 2;
            } else {
                gxVar.a = Float.valueOf(str).floatValue();
                gxVar.b = 0;
            }
            return gxVar;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public final float a(float f, float f2) {
        if (this.b == 1) {
            return (this.a * f) / 100.0f;
        }
        if (this.b == 2) {
            return (this.a * f2) / 100.0f;
        }
        return this.a;
    }
}
