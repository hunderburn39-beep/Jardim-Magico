package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
class cn implements Runnable {
    private final String WJ;
    private volatile String Xg;
    private final bm Zd;
    private final String Ze;
    private bg<com.google.android.gms.internal.c.j> Zf;
    private volatile r Zg;
    private volatile String Zh;
    private final Context mContext;

    cn(Context context, String str, bm bmVar, r rVar) {
        this.mContext = context;
        this.Zd = bmVar;
        this.WJ = str;
        this.Zg = rVar;
        this.Ze = "/r?id=" + str;
        this.Xg = this.Ze;
        this.Zh = null;
    }

    public cn(Context context, String str, r rVar) {
        this(context, str, new bm(), rVar);
    }

    private boolean kW() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        bh.y("...no network connectivity");
        return false;
    }

    private void kX() {
        if (!kW()) {
            this.Zf.a(bg.a.NOT_AVAILABLE);
            return;
        }
        bh.y("Start loading resource from network ...");
        String strKY = kY();
        bl blVarKH = this.Zd.kH();
        try {
            try {
                InputStream inputStreamBD = blVarKH.bD(strKY);
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    cq.b(inputStreamBD, byteArrayOutputStream);
                    com.google.android.gms.internal.c.j jVarB = com.google.android.gms.internal.c.j.b(byteArrayOutputStream.toByteArray());
                    bh.y("Successfully loaded supplemented resource: " + jVarB);
                    if (jVarB.fK == null && jVarB.fJ.length == 0) {
                        bh.y("No change for container: " + this.WJ);
                    }
                    this.Zf.i(jVarB);
                    blVarKH.close();
                    bh.y("Load resource from network finished.");
                } catch (IOException e) {
                    bh.c("Error when parsing downloaded resources from url: " + strKY + " " + e.getMessage(), e);
                    this.Zf.a(bg.a.SERVER_ERROR);
                    blVarKH.close();
                }
            } catch (FileNotFoundException e2) {
                bh.z("No data is retrieved from the given url: " + strKY + ". Make sure container_id: " + this.WJ + " is correct.");
                this.Zf.a(bg.a.SERVER_ERROR);
                blVarKH.close();
            } catch (IOException e3) {
                bh.c("Error when loading resources from url: " + strKY + " " + e3.getMessage(), e3);
                this.Zf.a(bg.a.IO_ERROR);
                blVarKH.close();
            }
        } catch (Throwable th) {
            blVarKH.close();
            throw th;
        }
    }

    void a(bg<com.google.android.gms.internal.c.j> bgVar) {
        this.Zf = bgVar;
    }

    void bJ(String str) {
        bh.v("Setting previous container version: " + str);
        this.Zh = str;
    }

    void bu(String str) {
        if (str == null) {
            this.Xg = this.Ze;
        } else {
            bh.v("Setting CTFE URL path: " + str);
            this.Xg = str;
        }
    }

    String kY() {
        String str = this.Zg.kn() + this.Xg + "&v=a65833898";
        if (this.Zh != null && !this.Zh.trim().equals("")) {
            str = str + "&pv=" + this.Zh;
        }
        return cd.kT().kU().equals(cd.a.CONTAINER_DEBUG) ? str + "&gtm_debug=x" : str;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.Zf == null) {
            throw new IllegalStateException("callback must be set before execute");
        }
        this.Zf.kl();
        kX();
    }
}
