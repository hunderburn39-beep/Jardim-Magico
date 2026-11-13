package com.google.android.gms.analytics;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

/* JADX INFO: loaded from: classes.dex */
class ah implements n {
    private final Context mContext;
    private GoogleAnalytics sX;
    private final String vI;
    private final HttpClient vJ;
    private URL vK;

    ah(HttpClient httpClient, Context context) {
        this(httpClient, GoogleAnalytics.getInstance(context), context);
    }

    ah(HttpClient httpClient, GoogleAnalytics googleAnalytics, Context context) {
        this.mContext = context.getApplicationContext();
        this.vI = a("GoogleAnalytics", "3.0", Build.VERSION.RELEASE, ak.a(Locale.getDefault()), Build.MODEL, Build.ID);
        this.vJ = httpClient;
        this.sX = googleAnalytics;
    }

    private void a(ab abVar, URL url, boolean z) {
        URL url2;
        if (TextUtils.isEmpty(abVar.cU()) || !db()) {
            return;
        }
        if (url == null) {
            try {
                url2 = this.vK != null ? this.vK : new URL("https://ssl.google-analytics.com/collect");
            } catch (MalformedURLException e) {
                return;
            }
        } else {
            url2 = url;
        }
        HttpHost httpHost = new HttpHost(url2.getHost(), url2.getPort(), url2.getProtocol());
        try {
            HttpEntityEnclosingRequest httpEntityEnclosingRequestC = c(abVar.cU(), url2.getPath());
            if (httpEntityEnclosingRequestC != null) {
                httpEntityEnclosingRequestC.addHeader("Host", httpHost.toHostString());
                if (aa.cT()) {
                    a(httpEntityEnclosingRequestC);
                }
                if (z) {
                    q.p(this.mContext);
                }
                HttpResponse httpResponseExecute = this.vJ.execute(httpHost, httpEntityEnclosingRequestC);
                int statusCode = httpResponseExecute.getStatusLine().getStatusCode();
                HttpEntity entity = httpResponseExecute.getEntity();
                if (entity != null) {
                    entity.consumeContent();
                }
                if (statusCode != 200) {
                    aa.z("Bad response: " + httpResponseExecute.getStatusLine().getStatusCode());
                }
            }
        } catch (ClientProtocolException e2) {
            aa.z("ClientProtocolException sending monitoring hit.");
        } catch (IOException e3) {
            aa.z("Exception sending monitoring hit: " + e3.getClass().getSimpleName());
            aa.z(e3.getMessage());
        }
    }

    private void a(HttpEntityEnclosingRequest httpEntityEnclosingRequest) {
        int iAvailable;
        StringBuffer stringBuffer = new StringBuffer();
        for (Header header : httpEntityEnclosingRequest.getAllHeaders()) {
            stringBuffer.append(header.toString()).append("\n");
        }
        stringBuffer.append(httpEntityEnclosingRequest.getRequestLine().toString()).append("\n");
        if (httpEntityEnclosingRequest.getEntity() != null) {
            try {
                InputStream content = httpEntityEnclosingRequest.getEntity().getContent();
                if (content != null && (iAvailable = content.available()) > 0) {
                    byte[] bArr = new byte[iAvailable];
                    content.read(bArr);
                    stringBuffer.append("POST:\n");
                    stringBuffer.append(new String(bArr)).append("\n");
                }
            } catch (IOException e) {
                aa.y("Error Writing hit to log...");
            }
        }
        aa.y(stringBuffer.toString());
    }

    private HttpEntityEnclosingRequest c(String str, String str2) {
        BasicHttpEntityEnclosingRequest basicHttpEntityEnclosingRequest;
        if (TextUtils.isEmpty(str)) {
            aa.z("Empty hit, discarding.");
            return null;
        }
        String str3 = str2 + "?" + str;
        if (str3.length() < 2036) {
            basicHttpEntityEnclosingRequest = new BasicHttpEntityEnclosingRequest("GET", str3);
        } else {
            basicHttpEntityEnclosingRequest = new BasicHttpEntityEnclosingRequest("POST", str2);
            try {
                basicHttpEntityEnclosingRequest.setEntity(new StringEntity(str));
            } catch (UnsupportedEncodingException e) {
                aa.z("Encoding error, discarding hit");
                return null;
            }
        }
        basicHttpEntityEnclosingRequest.addHeader("User-Agent", this.vI);
        return basicHttpEntityEnclosingRequest;
    }

    @Override // com.google.android.gms.analytics.n
    public void F(String str) {
        try {
            this.vK = new URL(str);
        } catch (MalformedURLException e) {
            this.vK = null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:33:0x00eb A[Catch: ClientProtocolException -> 0x0111, IOException -> 0x011d, TryCatch #2 {ClientProtocolException -> 0x0111, IOException -> 0x011d, blocks: (B:30:0x00d1, B:31:0x00d7, B:33:0x00eb, B:36:0x00f2), top: B:47:0x00d1 }] */
    /* JADX WARN: Code duplicated, block: B:36:0x00f2 A[Catch: ClientProtocolException -> 0x0111, IOException -> 0x011d, TRY_LEAVE, TryCatch #2 {ClientProtocolException -> 0x0111, IOException -> 0x011d, blocks: (B:30:0x00d1, B:31:0x00d7, B:33:0x00eb, B:36:0x00f2), top: B:47:0x00d1 }] */
    @Override // com.google.android.gms.analytics.n
    public int a(List<x> list, ab abVar, boolean z) {
        HttpResponse httpResponseExecute;
        int statusCode;
        HttpEntity entity;
        int i;
        URL url;
        int i2 = 0;
        int iMin = Math.min(list.size(), 40);
        abVar.c("_hr", list.size());
        int i3 = 0;
        URL url2 = null;
        boolean z2 = true;
        int i4 = 0;
        while (i4 < iMin) {
            x xVar = list.get(i4);
            URL urlA = a(xVar);
            if (urlA == null) {
                if (aa.cT()) {
                    aa.z("No destination: discarding hit: " + xVar.cO());
                } else {
                    aa.z("No destination: discarding hit.");
                }
                i3++;
                URL url3 = url2;
                i = i2 + 1;
                url = url3;
            } else {
                HttpHost httpHost = new HttpHost(urlA.getHost(), urlA.getPort(), urlA.getProtocol());
                String path = urlA.getPath();
                String strA = TextUtils.isEmpty(xVar.cO()) ? "" : y.a(xVar, System.currentTimeMillis());
                HttpEntityEnclosingRequest httpEntityEnclosingRequestC = c(strA, path);
                if (httpEntityEnclosingRequestC == null) {
                    i3++;
                    i = i2 + 1;
                    url = urlA;
                } else {
                    httpEntityEnclosingRequestC.addHeader("Host", httpHost.toHostString());
                    if (aa.cT()) {
                        a(httpEntityEnclosingRequestC);
                    }
                    if (strA.length() > 8192) {
                        aa.z("Hit too long (> 8192 bytes)--not sent");
                        i3++;
                    } else if (this.sX.isDryRunEnabled()) {
                        aa.x("Dry run enabled. Hit not actually sent.");
                    } else if (z2) {
                        try {
                            q.p(this.mContext);
                            z2 = false;
                            httpResponseExecute = this.vJ.execute(httpHost, httpEntityEnclosingRequestC);
                            statusCode = httpResponseExecute.getStatusLine().getStatusCode();
                            entity = httpResponseExecute.getEntity();
                            if (entity != null) {
                                entity.consumeContent();
                            }
                            if (statusCode != 200) {
                                aa.z("Bad response: " + httpResponseExecute.getStatusLine().getStatusCode());
                            }
                        } catch (ClientProtocolException e) {
                            aa.z("ClientProtocolException sending hit; discarding hit...");
                            abVar.c("_hd", i3);
                        } catch (IOException e2) {
                            aa.z("Exception sending hit: " + e2.getClass().getSimpleName());
                            aa.z(e2.getMessage());
                            abVar.c("_de", 1);
                            abVar.c("_hd", i3);
                            abVar.c("_hs", i2);
                            a(abVar, urlA, z2);
                            return i2;
                        }
                    } else {
                        httpResponseExecute = this.vJ.execute(httpHost, httpEntityEnclosingRequestC);
                        statusCode = httpResponseExecute.getStatusLine().getStatusCode();
                        entity = httpResponseExecute.getEntity();
                        if (entity != null) {
                            entity.consumeContent();
                        }
                        if (statusCode != 200) {
                            aa.z("Bad response: " + httpResponseExecute.getStatusLine().getStatusCode());
                        }
                    }
                    abVar.c("_td", strA.getBytes().length);
                    i = i2 + 1;
                    url = urlA;
                }
            }
            i4++;
            i2 = i;
            url2 = url;
        }
        abVar.c("_hd", i3);
        abVar.c("_hs", i2);
        if (z) {
            a(abVar, url2, z2);
        }
        return i2;
    }

    String a(String str, String str2, String str3, String str4, String str5, String str6) {
        return String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", str, str2, str3, str4, str5, str6);
    }

    URL a(x xVar) {
        if (this.vK != null) {
            return this.vK;
        }
        try {
            return new URL("http:".equals(xVar.cR()) ? "http://www.google-analytics.com/collect" : "https://ssl.google-analytics.com/collect");
        } catch (MalformedURLException e) {
            aa.w("Error trying to parse the hardcoded host url. This really shouldn't happen.");
            return null;
        }
    }

    @Override // com.google.android.gms.analytics.n
    public boolean ch() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        aa.y("...no network connectivity");
        return false;
    }

    boolean db() {
        return Math.random() * 100.0d <= 1.0d;
    }
}
