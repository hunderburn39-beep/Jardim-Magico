package com.tapjoy.internal;

import android.graphics.Bitmap;
import java.io.IOException;
import java.io.InputStream;
import java.net.ContentHandler;
import java.net.URLConnection;

/* JADX INFO: loaded from: classes.dex */
public final class u extends ContentHandler {
    public static final u a = new u();

    @Override // java.net.ContentHandler
    public final /* synthetic */ Object getContent(URLConnection uRLConnection) {
        return a(uRLConnection);
    }

    private u() {
    }

    private static Bitmap a(URLConnection uRLConnection) throws IOException {
        InputStream inputStream = uRLConnection.getInputStream();
        try {
            return v.a.b(inputStream);
        } finally {
            inputStream.close();
        }
    }

    public static Bitmap a(InputStream inputStream) throws IOException {
        try {
            return v.a.b(inputStream);
        } finally {
            inputStream.close();
        }
    }
}
