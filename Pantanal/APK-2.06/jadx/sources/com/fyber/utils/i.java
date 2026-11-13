package com.fyber.utils;

import com.heyzap.http.AsyncHttpResponseHandler;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

/* JADX INFO: compiled from: HttpConnection.java */
/* JADX INFO: loaded from: classes.dex */
public final class i extends b<i, String> {
    public static i b(String str) throws MalformedURLException {
        return new i(str);
    }

    private i(String str) throws MalformedURLException {
        super(str);
    }

    @Override // com.fyber.utils.b
    protected final /* synthetic */ String a(HttpURLConnection httpURLConnection) throws IOException {
        InputStream inputStreamC = c(httpURLConnection);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int i = inputStreamC.read(bArr);
            if (i > 0) {
                byteArrayOutputStream.write(bArr, 0, i);
            } else {
                byteArrayOutputStream.close();
                return new String(byteArrayOutputStream.toByteArray(), AsyncHttpResponseHandler.DEFAULT_CHARSET);
            }
        }
    }
}
