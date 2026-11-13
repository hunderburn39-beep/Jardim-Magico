package com.tapjoy.internal;

import com.heyzap.http.AsyncHttpResponseHandler;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public final class dp {
    public static final byte[] a;
    public static final ByteBuffer b;

    public interface a {
        int a();
    }

    public interface b {
    }

    public static String b(byte[] bArr) {
        try {
            return new String(bArr, AsyncHttpResponseHandler.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported?", e);
        }
    }

    public static int a(long j) {
        return (int) ((j >>> 32) ^ j);
    }

    public static int a(boolean z) {
        return z ? 1231 : 1237;
    }

    public static int a(a aVar) {
        return aVar.a();
    }

    static {
        byte[] bArr = new byte[0];
        a = bArr;
        b = ByteBuffer.wrap(bArr);
    }

    public static boolean a(byte[] bArr) {
        return eb.a(bArr, 0, bArr.length);
    }
}
