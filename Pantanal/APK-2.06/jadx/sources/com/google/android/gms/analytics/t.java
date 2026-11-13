package com.google.android.gms.analytics;

import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import com.google.android.gms.internal.ef;
import com.heyzap.http.AsyncHttpClient;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/* JADX INFO: loaded from: classes.dex */
class t extends Thread implements f {
    private static t tA;
    private volatile boolean mClosed;
    private final Context mContext;
    private volatile String su;
    private volatile ag tB;
    private final LinkedBlockingQueue<Runnable> tw;
    private volatile boolean tx;
    private volatile List<ef> ty;
    private volatile String tz;

    private t(Context context) {
        super("GAThread");
        this.tw = new LinkedBlockingQueue<>();
        this.tx = false;
        this.mClosed = false;
        if (context != null) {
            this.mContext = context.getApplicationContext();
        } else {
            this.mContext = context;
        }
        start();
    }

    static int H(String str) {
        int i = 1;
        if (!TextUtils.isEmpty(str)) {
            i = 0;
            for (int length = str.length() - 1; length >= 0; length--) {
                char cCharAt = str.charAt(length);
                i = ((i << 6) & 268435455) + cCharAt + (cCharAt << 14);
                int i2 = 266338304 & i;
                if (i2 != 0) {
                    i ^= i2 >> 21;
                }
            }
        }
        return i;
    }

    private String a(Throwable th) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        th.printStackTrace(printStream);
        printStream.flush();
        return new String(byteArrayOutputStream.toByteArray());
    }

    static t q(Context context) {
        if (tA == null) {
            tA = new t(context);
        }
        return tA;
    }

    static String r(Context context) {
        String str = null;
        try {
            FileInputStream fileInputStreamOpenFileInput = context.openFileInput("gaInstallData");
            byte[] bArr = new byte[8192];
            int i = fileInputStreamOpenFileInput.read(bArr, 0, 8192);
            if (fileInputStreamOpenFileInput.available() > 0) {
                aa.w("Too much campaign data, ignoring it.");
                fileInputStreamOpenFileInput.close();
                context.deleteFile("gaInstallData");
            } else {
                fileInputStreamOpenFileInput.close();
                context.deleteFile("gaInstallData");
                if (i <= 0) {
                    aa.z("Campaign file is empty.");
                } else {
                    String str2 = new String(bArr, 0, i);
                    aa.x("Campaign found: " + str2);
                    str = str2;
                }
            }
        } catch (FileNotFoundException e) {
            aa.x("No campaign data found.");
        } catch (IOException e2) {
            aa.w("Error reading campaign data.");
            context.deleteFile("gaInstallData");
        }
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String r(Map<String, String> map) {
        return (!map.containsKey("useSecure") || ak.d(map.get("useSecure"), true)) ? "https:" : "http:";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean s(Map<String, String> map) {
        if (map.get("&sf") == null) {
            return false;
        }
        double dA = ak.a(map.get("&sf"), 100.0d);
        if (dA < 100.0d && H(map.get("&cid")) % AsyncHttpClient.DEFAULT_SOCKET_TIMEOUT >= dA * 100.0d) {
            aa.y(String.format("%s hit sampled out", map.get("&t") == null ? "unknown" : map.get("&t")));
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void t(Map<String, String> map) {
        m mVarM = a.m(this.mContext);
        ak.a(map, "&adid", mVarM.getValue("&adid"));
        ak.a(map, "&ate", mVarM.getValue("&ate"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void u(Map<String, String> map) {
        g gVarCa = g.ca();
        ak.a(map, "&an", gVarCa.getValue("&an"));
        ak.a(map, "&av", gVarCa.getValue("&av"));
        ak.a(map, "&aid", gVarCa.getValue("&aid"));
        ak.a(map, "&aiid", gVarCa.getValue("&aiid"));
        map.put("&v", "1");
    }

    void a(Runnable runnable) {
        this.tw.add(runnable);
    }

    @Override // com.google.android.gms.analytics.f
    public void bR() {
        a(new Runnable() { // from class: com.google.android.gms.analytics.t.3
            @Override // java.lang.Runnable
            public void run() {
                t.this.tB.bR();
            }
        });
    }

    @Override // com.google.android.gms.analytics.f
    public void bW() {
        a(new Runnable() { // from class: com.google.android.gms.analytics.t.2
            @Override // java.lang.Runnable
            public void run() {
                t.this.tB.bW();
            }
        });
    }

    @Override // com.google.android.gms.analytics.f
    public void bY() {
        a(new Runnable() { // from class: com.google.android.gms.analytics.t.4
            @Override // java.lang.Runnable
            public void run() {
                t.this.tB.bY();
            }
        });
    }

    @Override // com.google.android.gms.analytics.f
    public LinkedBlockingQueue<Runnable> bZ() {
        return this.tw;
    }

    @Override // com.google.android.gms.analytics.f
    public Thread getThread() {
        return this;
    }

    protected void init() {
        this.tB.cp();
        this.ty = new ArrayList();
        this.ty.add(new ef("appendVersion", "&_v".substring(1), "ma4.0.1"));
        this.ty.add(new ef("appendQueueTime", "&qt".substring(1), null));
        this.ty.add(new ef("appendCacheBuster", "&z".substring(1), null));
    }

    @Override // com.google.android.gms.analytics.f
    public void q(Map<String, String> map) {
        final HashMap map2 = new HashMap(map);
        String str = map.get("&ht");
        if (str != null) {
            try {
                Long.valueOf(str);
            } catch (NumberFormatException e) {
                str = null;
            }
        }
        if (str == null) {
            map2.put("&ht", Long.toString(System.currentTimeMillis()));
        }
        a(new Runnable() { // from class: com.google.android.gms.analytics.t.1
            @Override // java.lang.Runnable
            public void run() {
                if (TextUtils.isEmpty((CharSequence) map2.get("&cid"))) {
                    map2.put("&cid", t.this.su);
                }
                if (GoogleAnalytics.getInstance(t.this.mContext).getAppOptOut() || t.this.s(map2)) {
                    return;
                }
                if (!TextUtils.isEmpty(t.this.tz)) {
                    u.cy().t(true);
                    map2.putAll(new HitBuilders.HitBuilder().setCampaignParamsFromUrl(t.this.tz).build());
                    u.cy().t(false);
                    t.this.tz = null;
                }
                t.this.u(map2);
                t.this.t(map2);
                t.this.tB.b(y.v(map2), Long.valueOf((String) map2.get("&ht")).longValue(), t.this.r((Map<String, String>) map2), t.this.ty);
            }
        });
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        Process.setThreadPriority(10);
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            aa.z("sleep interrupted in GAThread initialize");
        }
        try {
            if (this.tB == null) {
                this.tB = new s(this.mContext, this);
            }
            init();
            this.su = h.cb().getValue("&cid");
            if (this.su == null) {
                this.tx = true;
            }
            this.tz = r(this.mContext);
            aa.y("Initialized GA Thread");
        } catch (Throwable th) {
            aa.w("Error initializing the GAThread: " + a(th));
            aa.w("Google Analytics will not start up.");
            this.tx = true;
        }
        while (!this.mClosed) {
            try {
                try {
                    Runnable runnableTake = this.tw.take();
                    if (!this.tx) {
                        runnableTake.run();
                    }
                } catch (InterruptedException e2) {
                    aa.x(e2.toString());
                }
            } catch (Throwable th2) {
                aa.w("Error on GAThread: " + a(th2));
                aa.w("Google Analytics is shutting down.");
                this.tx = true;
            }
        }
    }
}
