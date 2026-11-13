package com.adcolony.sdk;

import android.app.Activity;
import com.tapjoy.TJAdUnitConstants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class a {
    static final int a = 0;
    static final int b = 1;
    static final int c = 0;
    static final int d = 1;
    static final int e = 2;
    static final int f = 3;
    static final int g = 4;
    static final int h = 5;
    static final int i = 6;
    static final int j = 7;
    static final boolean k = false;
    static Activity l;
    static h m;
    static boolean n;
    static boolean o;

    a() {
    }

    static void a(Activity activity, AdColonyAppOptions adColonyAppOptions, boolean z) {
        l = activity;
        if (m == null) {
            m = new h();
            m.a(adColonyAppOptions, z);
        } else {
            m.a(adColonyAppOptions);
        }
        au.b.execute(new Runnable() { // from class: com.adcolony.sdk.a.1
            @Override // java.lang.Runnable
            public void run() {
                a.m.a(a.l, (z) null);
            }
        });
        u.c.b("Configuring AdColony");
        m.E = false;
        m.f.j = true;
        m.f.l = true;
        m.f.r = false;
        m.f.a(false);
    }

    public static void a(String str) {
        if (l != null) {
            try {
                String[] list = l.getAssets().list(str);
                if (list.length == 0) {
                    c(str);
                    return;
                }
                File file = new File(m.k.b + str);
                if (!file.exists()) {
                    file.mkdir();
                }
                for (String str2 : list) {
                    a(str + "/" + str2);
                }
            } catch (IOException e2) {
                u.h.a("Failed copy hardcoded ad unit with error:").b(e2.getMessage());
            }
        }
    }

    private static void c(String str) {
        try {
            InputStream inputStreamOpen = l.getAssets().open(str);
            FileOutputStream fileOutputStream = new FileOutputStream(m.k.b + str);
            byte[] bArr = new byte[1024];
            while (true) {
                int i2 = inputStreamOpen.read(bArr);
                if (i2 != -1) {
                    fileOutputStream.write(bArr, 0, i2);
                } else {
                    inputStreamOpen.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return;
                }
            }
        } catch (Exception e2) {
            u.h.a("Failed copy hardcoded ad unit file named: ").a(str).a(" with error:").b(e2.getMessage());
        }
    }

    static void a(Activity activity) {
        l = activity;
    }

    static void a(String str, ac acVar) {
        m.d.a(str, acVar);
    }

    static ac a(String str, ac acVar, boolean z) {
        m.d.a(str, acVar);
        return acVar;
    }

    static void b(String str, ac acVar) {
        m.d.b(str, acVar);
    }

    static void a() {
        m.d.b();
    }

    static void a(String str, String str2) {
        if (str2 != null) {
            a(str, s.a(str2));
        } else {
            a(str, (JSONObject) null);
        }
    }

    static void a(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            try {
                jSONObject = s.a();
            } catch (JSONException e2) {
                u.h.a("JSON error from ADC.java's send_message(): ").b(e2.toString());
                return;
            }
        }
        jSONObject.put("m_type", str);
        m.d.a(jSONObject);
    }

    static void b(String str) {
        try {
            z zVar = new z("CustomMessage.send", 0);
            zVar.b.put(TJAdUnitConstants.String.MESSAGE, str);
            zVar.b();
        } catch (JSONException e2) {
            u.h.a("JSON error from ADC.java's send_custom_message(): ").b(e2.toString());
        }
    }
}
