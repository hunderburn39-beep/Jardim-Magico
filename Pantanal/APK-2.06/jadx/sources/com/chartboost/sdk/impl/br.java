package com.chartboost.sdk.impl;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class br {
    public static final char a = File.separatorChar;
    public static final String b;

    static {
        bt btVar = new bt(4);
        PrintWriter printWriter = new PrintWriter(btVar);
        printWriter.println();
        b = btVar.toString();
        printWriter.close();
    }

    public static void a(InputStream inputStream) {
        a((Closeable) inputStream);
    }

    public static void a(OutputStream outputStream) {
        a((Closeable) outputStream);
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    public static byte[] b(InputStream inputStream) throws IOException {
        bs bsVar = new bs();
        a(inputStream, bsVar);
        return bsVar.a();
    }

    public static byte[] a(InputStream inputStream, long j) throws IOException {
        if (j > 2147483647L) {
            throw new IllegalArgumentException("Size cannot be greater than Integer max value: " + j);
        }
        return a(inputStream, (int) j);
    }

    public static byte[] a(InputStream inputStream, int i) throws IOException {
        int i2 = 0;
        if (i < 0) {
            throw new IllegalArgumentException("Size must be equal or greater than zero: " + i);
        }
        if (i == 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[i];
        while (i2 < i) {
            int i3 = inputStream.read(bArr, i2, i - i2);
            if (i3 == -1) {
                break;
            }
            i2 += i3;
        }
        if (i2 != i) {
            throw new IOException("Unexpected readed size. current: " + i2 + ", excepted: " + i);
        }
        return bArr;
    }

    public static int a(InputStream inputStream, OutputStream outputStream) throws IOException {
        long jB = b(inputStream, outputStream);
        if (jB > 2147483647L) {
            return -1;
        }
        return (int) jB;
    }

    public static long b(InputStream inputStream, OutputStream outputStream) throws IOException {
        return a(inputStream, outputStream, new byte[4096]);
    }

    public static long a(InputStream inputStream, OutputStream outputStream, byte[] bArr) throws IOException {
        long j = 0;
        while (true) {
            int i = inputStream.read(bArr);
            if (-1 != i) {
                outputStream.write(bArr, 0, i);
                j += (long) i;
            } else {
                return j;
            }
        }
    }
}
