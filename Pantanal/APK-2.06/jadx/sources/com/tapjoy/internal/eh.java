package com.tapjoy.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/* JADX INFO: loaded from: classes.dex */
public final class eh {
    private static eh a = new eh();

    public static InputStream a(String str) throws IOException {
        URLConnection uRLConnectionOpenConnection = new URL(str).openConnection();
        uRLConnectionOpenConnection.connect();
        return uRLConnectionOpenConnection.getInputStream();
    }

    public static URLConnection a(URL url) {
        return url.openConnection();
    }
}
