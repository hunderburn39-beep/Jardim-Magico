package com.tapjoy.internal;

/* JADX INFO: loaded from: classes.dex */
public final class hi {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private final String c;

    public final String toString() {
        return super.toString() + "[charsetName=" + this.c + "]";
    }

    public static char[] a(byte[] bArr) {
        int i = 0;
        char[] cArr = a;
        int length = bArr.length;
        char[] cArr2 = new char[length << 1];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i + 1;
            cArr2[i] = cArr[(bArr[i2] & 240) >>> 4];
            i = i3 + 1;
            cArr2[i3] = cArr[bArr[i2] & 15];
        }
        return cArr2;
    }
}
