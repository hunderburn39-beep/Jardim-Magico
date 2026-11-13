package com.google.android.gms.internal;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import com.heyzap.http.AsyncHttpResponseHandler;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class cs extends Cdo implements cu.a, ea.a {
    private final bq ky;
    private final dz lC;
    private final Context mContext;
    private bj mR;
    private final cr.a oG;
    private final cx.a oI;
    private final l oJ;
    private Cdo oK;
    private cz oL;
    private bh oN;
    private bn oO;
    private final Object oH = new Object();
    private final Object li = new Object();
    private boolean oM = false;

    private static final class a extends Exception {
        private final int oS;

        public a(String str, int i) {
            super(str);
            this.oS = i;
        }

        public int getErrorCode() {
            return this.oS;
        }
    }

    public cs(Context context, cx.a aVar, l lVar, dz dzVar, bq bqVar, cr.a aVar2) {
        this.ky = bqVar;
        this.oG = aVar2;
        this.lC = dzVar;
        this.mContext = context;
        this.oI = aVar;
        this.oJ = lVar;
    }

    private ak a(cx cxVar) throws a {
        if (this.oL.pr == null) {
            throw new a("The ad response must specify one of the supported ad sizes.", 0);
        }
        String[] strArrSplit = this.oL.pr.split(TMXConstants.TAG_OBJECT_ATTRIBUTE_X);
        if (strArrSplit.length != 2) {
            throw new a("Could not parse the ad size from the ad response: " + this.oL.pr, 0);
        }
        try {
            int i = Integer.parseInt(strArrSplit[0]);
            int i2 = Integer.parseInt(strArrSplit[1]);
            for (ak akVar : cxVar.kN.lU) {
                float f = this.mContext.getResources().getDisplayMetrics().density;
                int i3 = akVar.width == -1 ? (int) (akVar.widthPixels / f) : akVar.width;
                int i4 = akVar.height == -2 ? (int) (akVar.heightPixels / f) : akVar.height;
                if (i == i3 && i2 == i4) {
                    return new ak(akVar, cxVar.kN.lU);
                }
            }
            throw new a("The ad size from the ad response was not one of the requested sizes: " + this.oL.pr, 0);
        } catch (NumberFormatException e) {
            throw new a("Could not parse the ad size from the ad response: " + this.oL.pr, 0);
        }
    }

    private void a(cx cxVar, long j) throws a {
        synchronized (this.oH) {
            this.oN = new bh(this.mContext, cxVar, this.ky, this.mR);
        }
        this.oO = this.oN.a(j, 60000L);
        switch (this.oO.nw) {
            case 0:
                return;
            case 1:
                throw new a("No fill from any mediation ad networks.", 3);
            default:
                throw new a("Unexpected mediation result: " + this.oO.nw, 0);
        }
    }

    private void aZ() throws a {
        if (this.oL.errorCode == -3) {
            return;
        }
        if (TextUtils.isEmpty(this.oL.pm)) {
            throw new a("No fill from ad server.", 3);
        }
        if (this.oL.po) {
            try {
                this.mR = new bj(this.oL.pm);
            } catch (JSONException e) {
                throw new a("Could not parse mediation config: " + this.oL.pm, 0);
            }
        }
    }

    private void b(long j) throws a {
        dv.rp.post(new Runnable() { // from class: com.google.android.gms.internal.cs.3
            @Override // java.lang.Runnable
            public void run() {
                synchronized (cs.this.li) {
                    if (cs.this.oL.errorCode != -2) {
                        return;
                    }
                    cs.this.lC.bI().a(cs.this);
                    if (cs.this.oL.errorCode == -3) {
                        dw.y("Loading URL in WebView: " + cs.this.oL.ol);
                        cs.this.lC.loadUrl(cs.this.oL.ol);
                    } else {
                        dw.y("Loading HTML in WebView.");
                        cs.this.lC.loadDataWithBaseURL(dq.r(cs.this.oL.ol), cs.this.oL.pm, "text/html", AsyncHttpResponseHandler.DEFAULT_CHARSET, null);
                    }
                }
            }
        });
        e(j);
    }

    private void d(long j) throws a {
        while (f(j)) {
            if (this.oL != null) {
                synchronized (this.oH) {
                    this.oK = null;
                }
                if (this.oL.errorCode != -2 && this.oL.errorCode != -3) {
                    throw new a("There was a problem getting an ad response. ErrorCode: " + this.oL.errorCode, this.oL.errorCode);
                }
                return;
            }
        }
        throw new a("Timed out waiting for ad response.", 2);
    }

    private void e(long j) throws a {
        while (f(j)) {
            if (this.oM) {
                return;
            }
        }
        throw new a("Timed out waiting for WebView to finish loading.", 2);
    }

    private boolean f(long j) throws a {
        long jElapsedRealtime = 60000 - (SystemClock.elapsedRealtime() - j);
        if (jElapsedRealtime <= 0) {
            return false;
        }
        try {
            this.li.wait(jElapsedRealtime);
            return true;
        } catch (InterruptedException e) {
            throw new a("Ad request cancelled.", -1);
        }
    }

    @Override // com.google.android.gms.internal.cu.a
    public void a(cz czVar) {
        synchronized (this.li) {
            dw.v("Received ad response.");
            this.oL = czVar;
            this.li.notify();
        }
    }

    @Override // com.google.android.gms.internal.ea.a
    public void a(dz dzVar) {
        synchronized (this.li) {
            dw.v("WebView finished loading.");
            this.oM = true;
            this.li.notify();
        }
    }

    @Override // com.google.android.gms.internal.Cdo
    public void aY() {
        long j;
        ak akVar;
        JSONObject jSONObject;
        synchronized (this.li) {
            dw.v("AdLoaderBackgroundTask started.");
            cx cxVar = new cx(this.oI, this.oJ.y().a(this.mContext));
            int errorCode = -2;
            try {
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                Cdo cdoA = cu.a(this.mContext, cxVar, this);
                synchronized (this.oH) {
                    this.oK = cdoA;
                    if (this.oK == null) {
                        throw new a("Could not start the ad request service.", 0);
                    }
                }
                d(jElapsedRealtime);
                long jElapsedRealtime2 = SystemClock.elapsedRealtime();
                aZ();
                ak akVarA = cxVar.kN.lU != null ? a(cxVar) : null;
                if (this.oL.po) {
                    a(cxVar, jElapsedRealtime);
                } else if (this.oL.pu) {
                    c(jElapsedRealtime);
                } else {
                    b(jElapsedRealtime);
                }
                j = jElapsedRealtime2;
                akVar = akVarA;
            } catch (a e) {
                errorCode = e.getErrorCode();
                if (errorCode == 3 || errorCode == -1) {
                    dw.x(e.getMessage());
                } else {
                    dw.z(e.getMessage());
                }
                if (this.oL == null) {
                    this.oL = new cz(errorCode);
                } else {
                    this.oL = new cz(errorCode, this.oL.ni);
                }
                dv.rp.post(new Runnable() { // from class: com.google.android.gms.internal.cs.1
                    @Override // java.lang.Runnable
                    public void run() {
                        cs.this.onStop();
                    }
                });
                j = -1;
                akVar = null;
            }
            if (TextUtils.isEmpty(this.oL.pw)) {
                jSONObject = null;
            } else {
                try {
                    jSONObject = new JSONObject(this.oL.pw);
                } catch (Exception e2) {
                    dw.b("Error parsing the JSON for Active View.", e2);
                    jSONObject = null;
                }
            }
            final dh dhVar = new dh(cxVar.pg, this.lC, this.oL.ne, errorCode, this.oL.nf, this.oL.pq, this.oL.orientation, this.oL.ni, cxVar.pj, this.oL.po, this.oO != null ? this.oO.nx : null, this.oO != null ? this.oO.ny : null, this.oO != null ? this.oO.nz : null, this.mR, this.oO != null ? this.oO.nA : null, this.oL.pp, akVar, this.oL.pn, j, this.oL.ps, this.oL.pt, jSONObject);
            dv.rp.post(new Runnable() { // from class: com.google.android.gms.internal.cs.2
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (cs.this.li) {
                        cs.this.oG.a(dhVar);
                    }
                }
            });
        }
    }

    protected void c(long j) throws a {
        int i;
        int i2;
        ak akVarR = this.lC.R();
        if (akVarR.lT) {
            i = this.mContext.getResources().getDisplayMetrics().widthPixels;
            i2 = this.mContext.getResources().getDisplayMetrics().heightPixels;
        } else {
            i = akVarR.widthPixels;
            i2 = akVarR.heightPixels;
        }
        final ct ctVar = new ct(this, this.lC, i, i2);
        dv.rp.post(new Runnable() { // from class: com.google.android.gms.internal.cs.4
            @Override // java.lang.Runnable
            public void run() {
                synchronized (cs.this.li) {
                    if (cs.this.oL.errorCode != -2) {
                        return;
                    }
                    cs.this.lC.bI().a(cs.this);
                    ctVar.b(cs.this.oL);
                }
            }
        });
        e(j);
        if (ctVar.bc()) {
            dw.v("Ad-Network indicated no fill with passback URL.");
            throw new a("AdNetwork sent passback url", 3);
        }
        if (!ctVar.bd()) {
            throw new a("AdNetwork timed out", 2);
        }
    }

    @Override // com.google.android.gms.internal.Cdo
    public void onStop() {
        synchronized (this.oH) {
            if (this.oK != null) {
                this.oK.cancel();
            }
            this.lC.stopLoading();
            dq.a(this.lC);
            if (this.oN != null) {
                this.oN.cancel();
            }
        }
    }
}
