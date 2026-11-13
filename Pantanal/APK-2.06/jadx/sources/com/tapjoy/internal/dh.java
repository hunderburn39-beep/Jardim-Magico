package com.tapjoy.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public abstract class dh implements du {
    public int a = 0;

    public final byte[] a() {
        try {
            byte[] bArr = new byte[b()];
            dm dmVar = new dm(bArr, bArr.length);
            a(dmVar);
            if (dmVar.c == null) {
                if (dmVar.a - dmVar.b == 0) {
                    return bArr;
                }
                throw new IllegalStateException("Did not write as much data as expected.");
            }
            throw new UnsupportedOperationException("spaceLeft() can only be called on CodedOutputStreams that are writing to a flat array.");
        } catch (IOException e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    @Override // com.tapjoy.internal.du
    public final void a(OutputStream outputStream) throws IOException {
        int iB = b();
        dm dmVarA = dm.a(outputStream, iB <= 4096 ? iB : 4096);
        a(dmVarA);
        dmVarA.a();
    }

    public static abstract class a implements du.a {
        @Override // 
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public abstract a clone();

        public static void a(Iterable iterable, Collection collection) {
            if (iterable instanceof ds) {
                a(((ds) iterable).a());
                collection.addAll((Collection) iterable);
            } else {
                if (iterable instanceof Collection) {
                    a(iterable);
                    collection.addAll((Collection) iterable);
                    return;
                }
                for (Object obj : iterable) {
                    if (obj == null) {
                        throw new NullPointerException();
                    }
                    collection.add(obj);
                }
            }
        }

        private static void a(Iterable iterable) {
            Iterator it = iterable.iterator();
            while (it.hasNext()) {
                if (it.next() == null) {
                    throw new NullPointerException();
                }
            }
        }
    }
}
