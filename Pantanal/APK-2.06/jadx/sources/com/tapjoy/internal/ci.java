package com.tapjoy.internal;

import com.google.android.gms.wallet.WalletConstants;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class ci implements ch {
    private final String a;
    private final URL b;

    public ci(String str, URL url) {
        this.a = str;
        this.b = url;
    }

    @Override // com.tapjoy.internal.ch
    public final Object a(ce ceVar) throws IOException {
        URI uri;
        URL url = new URL(this.b, ceVar.c());
        String strB = ceVar.b();
        if ("GET".equals(strB) || "DELETE".equals(strB)) {
            Map mapE = ceVar.e();
            if (!mapE.isEmpty()) {
                url = new URL(url, url.getPath() + "?" + ei.a(mapE));
            }
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) eh.a(url);
        httpURLConnection.setRequestMethod(strB);
        httpURLConnection.setRequestProperty("User-Agent", this.a);
        for (Map.Entry entry : ceVar.a().entrySet()) {
            httpURLConnection.setRequestProperty((String) entry.getKey(), entry.getValue().toString());
        }
        if (!"GET".equals(strB) && !"DELETE".equals(strB)) {
            if ("POST".equals(strB) || "PUT".equals(strB)) {
                String strD = ceVar.d();
                if (strD == null) {
                    ei.a(httpURLConnection, "application/x-www-form-urlencoded", ei.a(ceVar.e()), co.c);
                } else if ("application/json".equals(strD)) {
                    ei.a(httpURLConnection, "application/json; charset=utf-8", bl.a((Object) ceVar.e()), co.c);
                } else {
                    throw new IllegalArgumentException("Unknown content type: " + strD);
                }
            } else {
                throw new IllegalArgumentException("Unknown method: " + strB);
            }
        }
        httpURLConnection.connect();
        switch (httpURLConnection.getResponseCode()) {
            case 200:
            case 201:
            case WalletConstants.ERROR_CODE_BUYER_ACCOUNT_ERROR /* 409 */:
                InputStream inputStream = httpURLConnection.getInputStream();
                try {
                    try {
                        uri = httpURLConnection.getURL().toURI();
                        break;
                    } catch (URISyntaxException e) {
                        uri = null;
                    }
                    return ceVar.a(uri, inputStream);
                } finally {
                    inputStream.close();
                }
            default:
                throw new IOException("Unexpected status code: " + httpURLConnection.getResponseCode());
        }
    }
}
