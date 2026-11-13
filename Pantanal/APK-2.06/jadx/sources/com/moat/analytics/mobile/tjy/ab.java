package com.moat.analytics.mobile.tjy;

import com.heyzap.http.AsyncHttpClient;
import com.heyzap.http.AsyncHttpResponseHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/* JADX INFO: loaded from: classes.dex */
enum ab implements aa {
    instance;

    private String a(InputStream inputStream) throws IOException {
        char[] cArr = new char[256];
        StringBuilder sb = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, AsyncHttpResponseHandler.DEFAULT_CHARSET);
        int i = 0;
        do {
            int i2 = inputStreamReader.read(cArr, 0, 256);
            if (i2 <= 0) {
                break;
            }
            i += i2;
            sb.append(cArr, 0, i2);
        } while (i < 1024);
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.io.IOException] */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r4v0, types: [com.moat.analytics.mobile.tjy.ab] */
    @Override // com.moat.analytics.mobile.tjy.aa
    public final com.moat.analytics.mobile.tjy.base.functional.a a(String str) {
        com.moat.analytics.mobile.tjy.base.functional.a aVarA;
        ?? e = 0;
        e = 0;
        e = 0;
        try {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setReadTimeout(AsyncHttpClient.DEFAULT_SOCKET_TIMEOUT);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() >= 400) {
                    aVarA = com.moat.analytics.mobile.tjy.base.functional.a.a();
                } else {
                    e = httpURLConnection.getInputStream();
                    aVarA = com.moat.analytics.mobile.tjy.base.functional.a.a(a(e));
                    if (e != 0) {
                        try {
                            e.close();
                        } catch (IOException e2) {
                            e = e2;
                        }
                    }
                }
            } catch (IOException e3) {
                aVarA = com.moat.analytics.mobile.tjy.base.functional.a.a();
                if (e != 0) {
                    try {
                        e.close();
                    } catch (IOException e4) {
                    }
                }
            }
            return aVarA;
        } catch (Throwable th) {
            if (e != 0) {
                try {
                    e.close();
                } catch (IOException e5) {
                }
            }
            throw th;
        }
    }
}
