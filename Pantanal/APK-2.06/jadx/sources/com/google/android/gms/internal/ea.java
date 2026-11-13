package com.google.android.gms.internal;

import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ea extends WebViewClient {
    protected final dz lC;
    private az mF;
    private bc mP;
    private a oW;
    private u rB;
    private cf rC;
    private boolean rE;
    private ci rF;
    private final HashMap<String, bb> rA = new HashMap<>();
    private final Object li = new Object();
    private boolean rD = false;

    public interface a {
        void a(dz dzVar);
    }

    public ea(dz dzVar, boolean z) {
        this.lC = dzVar;
        this.rE = z;
    }

    private static boolean c(Uri uri) {
        String scheme = uri.getScheme();
        return "http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme);
    }

    private void d(Uri uri) {
        String path = uri.getPath();
        bb bbVar = this.rA.get(path);
        if (bbVar == null) {
            dw.y("No GMSG handler found for GMSG: " + uri);
            return;
        }
        Map<String, String> mapB = dq.b(uri);
        if (dw.n(2)) {
            dw.y("Received GMSG: " + path);
            for (String str : mapB.keySet()) {
                dw.y("  " + str + ": " + mapB.get(str));
            }
        }
        bbVar.b(this.lC, mapB);
    }

    public final void a(cb cbVar) {
        boolean zBL = this.lC.bL();
        a(new ce(cbVar, (!zBL || this.lC.R().lT) ? this.rB : null, zBL ? null : this.rC, this.rF, this.lC.bK()));
    }

    protected void a(ce ceVar) {
        cc.a(this.lC.getContext(), ceVar);
    }

    public final void a(a aVar) {
        this.oW = aVar;
    }

    public void a(u uVar, cf cfVar, az azVar, ci ciVar, boolean z, bc bcVar) {
        a("/appEvent", new ay(azVar));
        a("/canOpenURLs", ba.mH);
        a("/click", ba.mI);
        a("/close", ba.mJ);
        a("/customClose", ba.mK);
        a("/httpTrack", ba.mL);
        a("/log", ba.mM);
        a("/open", new bd(bcVar));
        a("/touch", ba.mN);
        a("/video", ba.mO);
        this.rB = uVar;
        this.rC = cfVar;
        this.mF = azVar;
        this.mP = bcVar;
        this.rF = ciVar;
        q(z);
    }

    public final void a(String str, bb bbVar) {
        this.rA.put(str, bbVar);
    }

    public final void a(boolean z, int i) {
        a(new ce((!this.lC.bL() || this.lC.R().lT) ? this.rB : null, this.rC, this.rF, this.lC, z, i, this.lC.bK()));
    }

    public final void a(boolean z, int i, String str) {
        boolean zBL = this.lC.bL();
        a(new ce((!zBL || this.lC.R().lT) ? this.rB : null, zBL ? null : this.rC, this.mF, this.rF, this.lC, z, i, str, this.lC.bK(), this.mP));
    }

    public final void a(boolean z, int i, String str, String str2) {
        boolean zBL = this.lC.bL();
        a(new ce((!zBL || this.lC.R().lT) ? this.rB : null, zBL ? null : this.rC, this.mF, this.rF, this.lC, z, i, str, str2, this.lC.bK(), this.mP));
    }

    public final void aM() {
        synchronized (this.li) {
            this.rD = false;
            this.rE = true;
            final cc ccVarBH = this.lC.bH();
            if (ccVarBH != null) {
                if (dv.bD()) {
                    ccVarBH.aM();
                } else {
                    dv.rp.post(new Runnable() { // from class: com.google.android.gms.internal.ea.1
                        @Override // java.lang.Runnable
                        public void run() {
                            ccVarBH.aM();
                        }
                    });
                }
            }
        }
    }

    public boolean bP() {
        boolean z;
        synchronized (this.li) {
            z = this.rE;
        }
        return z;
    }

    @Override // android.webkit.WebViewClient
    public final void onLoadResource(WebView webView, String url) {
        dw.y("Loading resource: " + url);
        Uri uri = Uri.parse(url);
        if ("gmsg".equalsIgnoreCase(uri.getScheme()) && "mobileads.google.com".equalsIgnoreCase(uri.getHost())) {
            d(uri);
        }
    }

    @Override // android.webkit.WebViewClient
    public final void onPageFinished(WebView webView, String url) {
        if (this.oW != null) {
            this.oW.a(this.lC);
            this.oW = null;
        }
    }

    public final void q(boolean z) {
        this.rD = z;
    }

    public final void reset() {
        synchronized (this.li) {
            this.rA.clear();
            this.rB = null;
            this.rC = null;
            this.oW = null;
            this.mF = null;
            this.rD = false;
            this.rE = false;
            this.mP = null;
            this.rF = null;
        }
    }

    @Override // android.webkit.WebViewClient
    public final boolean shouldOverrideUrlLoading(WebView webView, String url) {
        Uri uri;
        dw.y("AdWebView shouldOverrideUrlLoading: " + url);
        Uri uriA = Uri.parse(url);
        if ("gmsg".equalsIgnoreCase(uriA.getScheme()) && "mobileads.google.com".equalsIgnoreCase(uriA.getHost())) {
            d(uriA);
        } else {
            if (this.rD && webView == this.lC && c(uriA)) {
                return super.shouldOverrideUrlLoading(webView, url);
            }
            if (this.lC.willNotDraw()) {
                dw.z("AdWebView unable to handle URL: " + url);
            } else {
                try {
                    l lVarBJ = this.lC.bJ();
                    if (lVarBJ != null && lVarBJ.a(uriA)) {
                        uriA = lVarBJ.a(uriA, this.lC.getContext());
                    }
                    uri = uriA;
                } catch (m e) {
                    dw.z("Unable to append parameter to URL: " + url);
                    uri = uriA;
                }
                a(new cb("android.intent.action.VIEW", uri.toString(), null, null, null, null, null));
            }
        }
        return true;
    }
}
