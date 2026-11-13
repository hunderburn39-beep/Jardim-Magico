package com.google.android.gms.internal;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class kw {
    public static final int[] aea = new int[0];
    public static final long[] aeb = new long[0];
    public static final float[] aec = new float[0];
    public static final double[] aed = new double[0];
    public static final boolean[] aee = new boolean[0];
    public static final String[] aef = new String[0];
    public static final byte[][] aeg = new byte[0][];
    public static final byte[] aeh = new byte[0];

    public static final int b(kn knVar, int i) throws IOException {
        int i2 = 1;
        int position = knVar.getPosition();
        knVar.cQ(i);
        while (knVar.ms() > 0 && knVar.mh() == i) {
            knVar.cQ(i);
            i2++;
        }
        knVar.cT(position);
        return i2;
    }

    static int de(int i) {
        return i & 7;
    }

    public static int df(int i) {
        return i >>> 3;
    }

    static int l(int i, int i2) {
        return (i << 3) | i2;
    }
}
