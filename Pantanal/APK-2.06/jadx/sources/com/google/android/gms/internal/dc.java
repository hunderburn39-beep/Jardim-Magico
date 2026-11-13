package com.google.android.gms.internal;

import android.content.Context;
import android.location.Location;
import android.os.SystemClock;
import android.text.TextUtils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class dc extends db.a {
    private static final Object px = new Object();
    private static dc py;
    private final Context mContext;
    private final ax pA;
    private final bf pz;

    private dc(Context context, ax axVar, bf bfVar) {
        this.mContext = context;
        this.pz = bfVar;
        this.pA = axVar;
    }

    private static cz a(final Context context, ax axVar, bf bfVar, final cx cxVar) {
        String string;
        dw.v("Starting ad request from service.");
        bfVar.init();
        dg dgVar = new dg(context);
        if (dgVar.qk == -1) {
            dw.v("Device is offline.");
            return new cz(2);
        }
        final de deVar = new de(cxVar.applicationInfo.packageName);
        if (cxVar.pg.extras != null && (string = cxVar.pg.extras.getString("_ad")) != null) {
            return dd.a(context, cxVar, string);
        }
        Location locationA = bfVar.a(250L);
        final String strAH = axVar.aH();
        String strA = dd.a(cxVar, dgVar, locationA, axVar.aI());
        if (strA == null) {
            return new cz(0);
        }
        final ea.a aVarP = p(strA);
        dv.rp.post(new Runnable() { // from class: com.google.android.gms.internal.dc.1
            @Override // java.lang.Runnable
            public void run() {
                dz dzVarA = dz.a(context, new ak(), false, false, null, cxVar.kK);
                dzVarA.setWillNotDraw(true);
                deVar.b(dzVarA);
                ea eaVarBI = dzVarA.bI();
                eaVarBI.a("/invalidRequest", deVar.pK);
                eaVarBI.a("/loadAdURL", deVar.pL);
                eaVarBI.a("/log", ba.mM);
                eaVarBI.a(aVarP);
                dw.v("Loading the JS library.");
                dzVarA.loadUrl(strAH);
            }
        });
        String strBj = deVar.bj();
        return TextUtils.isEmpty(strBj) ? new cz(deVar.getErrorCode()) : a(context, cxVar.kK.rq, strBj);
    }

    public static cz a(Context context, String str, String str2) {
        try {
            df dfVar = new df();
            URL url = new URL(str2);
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            URL url2 = url;
            int i = 0;
            while (true) {
                HttpURLConnection httpURLConnection = (HttpURLConnection) url2.openConnection();
                try {
                    dq.a(context, str, false, httpURLConnection);
                    int responseCode = httpURLConnection.getResponseCode();
                    Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
                    if (responseCode >= 200 && responseCode < 300) {
                        String string = url2.toString();
                        String strA = dq.a(new InputStreamReader(httpURLConnection.getInputStream()));
                        a(string, headerFields, strA, responseCode);
                        dfVar.a(string, headerFields, strA);
                        cz czVarG = dfVar.g(jElapsedRealtime);
                        httpURLConnection.disconnect();
                        return czVarG;
                    }
                    a(url2.toString(), headerFields, (String) null, responseCode);
                    if (responseCode < 300 || responseCode >= 400) {
                        dw.z("Received error HTTP response code: " + responseCode);
                        cz czVar = new cz(0);
                        httpURLConnection.disconnect();
                        return czVar;
                    }
                    String headerField = httpURLConnection.getHeaderField("Location");
                    if (TextUtils.isEmpty(headerField)) {
                        dw.z("No location header to follow redirect.");
                        cz czVar2 = new cz(0);
                        httpURLConnection.disconnect();
                        return czVar2;
                    }
                    url2 = new URL(headerField);
                    i++;
                    if (i > 5) {
                        dw.z("Too many redirects.");
                        cz czVar3 = new cz(0);
                        httpURLConnection.disconnect();
                        return czVar3;
                    }
                    dfVar.e(headerFields);
                    httpURLConnection.disconnect();
                } catch (Throwable th) {
                    httpURLConnection.disconnect();
                    throw th;
                }
            }
        } catch (IOException e) {
            dw.z("Error while connecting to ad server: " + e.getMessage());
            return new cz(2);
        }
    }

    public static dc a(Context context, ax axVar, bf bfVar) {
        dc dcVar;
        synchronized (px) {
            if (py == null) {
                py = new dc(context.getApplicationContext(), axVar, bfVar);
            }
            dcVar = py;
        }
        return dcVar;
    }

    private static void a(String str, Map<String, List<String>> map, String str2, int i) {
        if (dw.n(2)) {
            dw.y("Http Response: {\n  URL:\n    " + str + "\n  Headers:");
            if (map != null) {
                for (String str3 : map.keySet()) {
                    dw.y("    " + str3 + ":");
                    Iterator<String> it = map.get(str3).iterator();
                    while (it.hasNext()) {
                        dw.y("      " + it.next());
                    }
                }
            }
            dw.y("  Body:");
            if (str2 != null) {
                for (int i2 = 0; i2 < Math.min(str2.length(), 100000); i2 += 1000) {
                    dw.y(str2.substring(i2, Math.min(str2.length(), i2 + 1000)));
                }
            } else {
                dw.y("    null");
            }
            dw.y("  Response Code:\n    " + i + "\n}");
        }
    }

    private static ea.a p(final String str) {
        return new ea.a() { // from class: com.google.android.gms.internal.dc.2
            @Override // com.google.android.gms.internal.ea.a
            public void a(dz dzVar) {
                String str2 = String.format("javascript:%s(%s);", "AFMA_buildAdURL", str);
                dw.y("About to execute: " + str2);
                dzVar.loadUrl(str2);
            }
        };
    }

    @Override // com.google.android.gms.internal.db
    public cz b(cx cxVar) {
        return a(this.mContext, this.pA, this.pz, cxVar);
    }
}
