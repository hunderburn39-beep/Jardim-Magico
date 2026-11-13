package com.tapjoy.internal;

import android.util.Base64;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;
import java.util.zip.CRC32;

/* JADX INFO: loaded from: classes.dex */
public final class el {
    public final a a;
    public final String b;
    public final String c;
    public final String d;
    private final String e;
    private final int f;

    public enum a {
        SDK_ANDROID((byte) 2),
        RPC_ANALYTICS((byte) 49);

        public byte a;

        a(byte b2) {
            this.a = b2;
        }

        public static a a(byte b2) {
            for (a aVar : values()) {
                if (aVar.a == b2) {
                    return aVar;
                }
            }
            return null;
        }
    }

    public el(String str) {
        int length = str.length();
        if (!str.matches("[A-Za-z0-9\\-_]*") || length < 60 || (length & 3) != 0) {
            throw new IllegalArgumentException("The given API key was malformed.");
        }
        try {
            byte[] bArrDecode = Base64.decode(str, 8);
            int length2 = bArrDecode.length;
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArrDecode);
            byteBufferWrap.order(ByteOrder.BIG_ENDIAN);
            int length3 = bArrDecode.length - 4;
            int i = byteBufferWrap.getInt(length3);
            CRC32 crc32 = new CRC32();
            crc32.update(bArrDecode, 0, length3);
            if (i != ((int) crc32.getValue())) {
                throw new IllegalArgumentException("The given API key was invalid.");
            }
            this.e = str;
            this.b = new UUID(byteBufferWrap.getLong(0), byteBufferWrap.getLong(8)).toString();
            this.f = byteBufferWrap.get(16);
            this.a = a.a(byteBufferWrap.get(17));
            this.c = str.substring(24, 44);
            if (this.f == 1) {
                this.d = null;
                return;
            }
            if (this.f == 2 && this.a == a.SDK_ANDROID) {
                if (length2 < 57) {
                    throw new IllegalArgumentException("The given API key was invalid.");
                }
                byte[] bArr = new byte[12];
                System.arraycopy(bArrDecode, 33, bArr, 0, 12);
                this.d = new String(hi.a(bArr));
                return;
            }
            throw new IllegalArgumentException("The given API key was not supported.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The given API key was malformed.", e);
        }
    }

    public final boolean equals(Object o) {
        if (o instanceof el) {
            return this.e.equals(((el) o).e);
        }
        return false;
    }

    public final String toString() {
        return this.e;
    }
}
