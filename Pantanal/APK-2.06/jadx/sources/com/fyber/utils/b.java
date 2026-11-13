package com.fyber.utils;

import android.net.Uri;
import android.text.TextUtils;
import com.fyber.user.User;
import com.fyber.utils.b;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: AbstractHttpConnection.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class b<T extends b<T, V>, V> {
    private static CookieStore i;
    protected URL a;
    protected int c;
    protected Map<String, List<String>> d;
    protected V e;
    private List<b<T, V>.a> j;
    protected boolean b = false;
    protected boolean f = true;
    protected boolean g = true;
    protected boolean h = true;

    protected abstract V a(HttpURLConnection httpURLConnection) throws IOException;

    public static void a(CookieStore cookieStore) {
        i = cookieStore;
    }

    protected b(String str) throws MalformedURLException {
        Uri uri = Uri.parse(str);
        this.a = new URL((uri.isRelative() ? uri.buildUpon().scheme("http").build() : uri).toString());
    }

    public final T a(String str, String str2) {
        if (this.j == null) {
            this.j = new LinkedList();
        }
        this.j.add(new a(str, str2));
        return this;
    }

    public T a() throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) this.a.openConnection();
        if (this.j != null) {
            for (b<T, V>.a aVar : this.j) {
                httpURLConnection.addRequestProperty(aVar.a, aVar.b);
            }
        }
        if (this.g) {
            String strMapToString = User.mapToString();
            if (StringUtils.notNullNorEmpty(strMapToString)) {
                httpURLConnection.addRequestProperty("X-User-Data", strMapToString);
            }
        }
        if (this.f && i != null) {
            synchronized (i) {
                try {
                    List<HttpCookie> list = i.get(this.a.toURI());
                    if (list.size() > 0) {
                        httpURLConnection.addRequestProperty("Cookie", TextUtils.join(";", list));
                    }
                } catch (URISyntaxException e) {
                    FyberLogger.d("AbstractHttpConnection", e.getMessage());
                }
            }
        }
        try {
            this.c = httpURLConnection.getResponseCode();
        } catch (IOException e2) {
            FyberLogger.i("AbstractHttpConnection", e2.getMessage());
            this.c = httpURLConnection.getResponseCode();
        }
        this.d = Collections.unmodifiableMap(httpURLConnection.getHeaderFields());
        this.e = a(httpURLConnection);
        if (this.f) {
            e();
        }
        if (this.h) {
            b(httpURLConnection);
        }
        this.b = true;
        return this;
    }

    protected static void b(HttpURLConnection httpURLConnection) {
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }

    protected InputStream c(HttpURLConnection httpURLConnection) throws IOException {
        try {
            return httpURLConnection.getInputStream();
        } catch (IOException e) {
            return httpURLConnection.getErrorStream();
        }
    }

    public final List<String> a(String str) throws IOException {
        if (!this.b) {
            throw new IOException("The connection has not been opened yet.");
        }
        return this.d.get(str);
    }

    public final int b() throws IOException {
        if (!this.b) {
            throw new IOException("The connection has not been opened yet.");
        }
        return this.c;
    }

    public final V c() throws IOException {
        if (!this.b) {
            throw new IOException("The connection has not been opened yet.");
        }
        return this.e;
    }

    public static Map<String, String> d() {
        HashMap map = new HashMap();
        String strMapToString = User.mapToString();
        if (StringUtils.nullOrEmpty(strMapToString)) {
            strMapToString = "";
        }
        map.put("X-User-Data", strMapToString);
        return map;
    }

    private synchronized void e() {
        if (i != null) {
            synchronized (i) {
                b("Set-Cookie");
                b("Set-Cookie2");
            }
        }
    }

    private void b(String str) {
        List<String> list = this.d.get(str);
        if (list != null) {
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                try {
                    for (HttpCookie httpCookie : HttpCookie.parse(it.next())) {
                        if (StringUtils.nullOrEmpty(httpCookie.getDomain())) {
                            httpCookie.setDomain(this.a.getHost());
                        }
                        i.add(this.a.toURI(), httpCookie);
                    }
                } catch (URISyntaxException e) {
                    FyberLogger.d("AbstractHttpConnection", e.getMessage());
                }
            }
        }
    }

    /* JADX INFO: compiled from: AbstractHttpConnection.java */
    private class a {
        String a;
        String b;

        public a(String str, String str2) {
            this.a = str;
            this.b = str2;
        }
    }
}
