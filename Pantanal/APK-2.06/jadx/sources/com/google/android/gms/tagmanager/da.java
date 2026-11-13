package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
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
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

/* JADX INFO: loaded from: classes.dex */
class da implements ab {
    private final Context aac;
    private final String aat = a("GoogleTagManager", "4.00", Build.VERSION.RELEASE, b(Locale.getDefault()), Build.MODEL, Build.ID);
    private final HttpClient aau;
    private a aav;

    public interface a {
        void a(ap apVar);

        void b(ap apVar);

        void c(ap apVar);
    }

    da(HttpClient httpClient, Context context, a aVar) {
        this.aac = context.getApplicationContext();
        this.aau = httpClient;
        this.aav = aVar;
    }

    private HttpEntityEnclosingRequest a(URL url) {
        BasicHttpEntityEnclosingRequest basicHttpEntityEnclosingRequest;
        URISyntaxException e;
        try {
            basicHttpEntityEnclosingRequest = new BasicHttpEntityEnclosingRequest("GET", url.toURI().toString());
            try {
                basicHttpEntityEnclosingRequest.addHeader("User-Agent", this.aat);
            } catch (URISyntaxException e2) {
                e = e2;
                bh.z("Exception sending hit: " + e.getClass().getSimpleName());
                bh.z(e.getMessage());
            }
        } catch (URISyntaxException e3) {
            basicHttpEntityEnclosingRequest = null;
            e = e3;
        }
        return basicHttpEntityEnclosingRequest;
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
                bh.y("Error Writing hit to log...");
            }
        }
        bh.y(stringBuffer.toString());
    }

    static String b(Locale locale) {
        if (locale == null || locale.getLanguage() == null || locale.getLanguage().length() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(locale.getLanguage().toLowerCase());
        if (locale.getCountry() != null && locale.getCountry().length() != 0) {
            sb.append("-").append(locale.getCountry().toLowerCase());
        }
        return sb.toString();
    }

    String a(String str, String str2, String str3, String str4, String str5, String str6) {
        return String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", str, str2, str3, str4, str5, str6);
    }

    @Override // com.google.android.gms.tagmanager.ab
    public boolean ch() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.aac.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        bh.y("...no network connectivity");
        return false;
    }

    URL d(ap apVar) {
        try {
            return new URL(apVar.kE());
        } catch (MalformedURLException e) {
            bh.w("Error trying to parse the GTM url.");
            return null;
        }
    }

    @Override // com.google.android.gms.tagmanager.ab
    public void d(List<ap> list) {
        boolean z;
        int iMin = Math.min(list.size(), 40);
        boolean z2 = true;
        int i = 0;
        while (i < iMin) {
            ap apVar = list.get(i);
            URL urlD = d(apVar);
            if (urlD == null) {
                bh.z("No destination: discarding hit.");
                this.aav.b(apVar);
                z = z2;
            } else {
                HttpEntityEnclosingRequest httpEntityEnclosingRequestA = a(urlD);
                if (httpEntityEnclosingRequestA == null) {
                    this.aav.b(apVar);
                    z = z2;
                } else {
                    HttpHost httpHost = new HttpHost(urlD.getHost(), urlD.getPort(), urlD.getProtocol());
                    httpEntityEnclosingRequestA.addHeader("Host", httpHost.toHostString());
                    a(httpEntityEnclosingRequestA);
                    if (z2) {
                        try {
                            bn.p(this.aac);
                            z2 = false;
                        } catch (ClientProtocolException e) {
                            bh.z("ClientProtocolException sending hit; discarding hit...");
                            this.aav.b(apVar);
                            z = z2;
                        } catch (IOException e2) {
                            bh.z("Exception sending hit: " + e2.getClass().getSimpleName());
                            bh.z(e2.getMessage());
                            this.aav.c(apVar);
                            z = z2;
                        }
                    }
                    HttpResponse httpResponseExecute = this.aau.execute(httpHost, httpEntityEnclosingRequestA);
                    int statusCode = httpResponseExecute.getStatusLine().getStatusCode();
                    HttpEntity entity = httpResponseExecute.getEntity();
                    if (entity != null) {
                        entity.consumeContent();
                    }
                    if (statusCode != 200) {
                        bh.z("Bad response: " + httpResponseExecute.getStatusLine().getStatusCode());
                        this.aav.c(apVar);
                    } else {
                        this.aav.a(apVar);
                    }
                    z = z2;
                }
            }
            i++;
            z2 = z;
        }
    }
}
