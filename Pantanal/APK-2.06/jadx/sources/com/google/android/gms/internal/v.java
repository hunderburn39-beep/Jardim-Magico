package com.google.android.gms.internal;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;
import java.util.ArrayList;
import java.util.HashSet;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
public class v extends ap.a implements az, bc, bk, cf, ci, cr.a, dl, u {
    private boolean kC;
    private final bq ky;
    private final b kz;
    private final ComponentCallbacks kD = new ComponentCallbacks() { // from class: com.google.android.gms.internal.v.1
        @Override // android.content.ComponentCallbacks
        public void onConfigurationChanged(Configuration newConfig) {
            if (v.this.kz == null || v.this.kz.kO == null || v.this.kz.kO.oj == null) {
                return;
            }
            v.this.kz.kO.oj.bE();
        }

        @Override // android.content.ComponentCallbacks
        public void onLowMemory() {
        }
    };
    private final x kA = new x(this);
    private final aa kB = new aa();

    private static final class a extends ViewSwitcher {
        private final dr kF;

        public a(Context context) {
            super(context);
            this.kF = new dr(context);
        }

        @Override // android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent event) {
            this.kF.c(event);
            return false;
        }
    }

    private static final class b {
        public final a kG;
        public final String kH;
        public final Context kI;
        public final l kJ;
        public final dx kK;
        public ao kL;
        public Cdo kM;
        public ak kN;
        public dh kO;
        public di kP;
        public ar kQ;
        public co kR;
        public dm kS = null;
        private HashSet<di> kT = null;

        public b(Context context, ak akVar, String str, dx dxVar) {
            if (akVar.lT) {
                this.kG = null;
            } else {
                this.kG = new a(context);
                this.kG.setMinimumWidth(akVar.widthPixels);
                this.kG.setMinimumHeight(akVar.heightPixels);
                this.kG.setVisibility(4);
            }
            this.kN = akVar;
            this.kH = str;
            this.kI = context;
            this.kJ = new l(k.a(dxVar.rq, context));
            this.kK = dxVar;
        }

        public void a(HashSet<di> hashSet) {
            this.kT = hashSet;
        }

        public HashSet<di> ak() {
            return this.kT;
        }
    }

    public v(Context context, ak akVar, String str, bq bqVar, dx dxVar) {
        this.kz = new b(context, akVar, str, dxVar);
        this.ky = bqVar;
        dw.x("Use AdRequest.Builder.addTestDevice(\"" + dv.l(context) + "\") to get test ads on this device.");
        dq.i(context);
        S();
    }

    private void S() {
        if (Build.VERSION.SDK_INT < 14 || this.kz == null || this.kz.kI == null) {
            return;
        }
        this.kz.kI.registerComponentCallbacks(this.kD);
    }

    private void T() {
        if (Build.VERSION.SDK_INT < 14 || this.kz == null || this.kz.kI == null) {
            return;
        }
        this.kz.kI.unregisterComponentCallbacks(this.kD);
    }

    private void a(int i) {
        dw.z("Failed to load ad: " + i);
        if (this.kz.kL != null) {
            try {
                this.kz.kL.onAdFailedToLoad(i);
            } catch (RemoteException e) {
                dw.c("Could not call AdListener.onAdFailedToLoad().", e);
            }
        }
    }

    private void ad() {
        dw.x("Ad closing.");
        if (this.kz.kL != null) {
            try {
                this.kz.kL.onAdClosed();
            } catch (RemoteException e) {
                dw.c("Could not call AdListener.onAdClosed().", e);
            }
        }
    }

    private void ae() {
        dw.x("Ad leaving application.");
        if (this.kz.kL != null) {
            try {
                this.kz.kL.onAdLeftApplication();
            } catch (RemoteException e) {
                dw.c("Could not call AdListener.onAdLeftApplication().", e);
            }
        }
    }

    private void af() {
        dw.x("Ad opening.");
        if (this.kz.kL != null) {
            try {
                this.kz.kL.onAdOpened();
            } catch (RemoteException e) {
                dw.c("Could not call AdListener.onAdOpened().", e);
            }
        }
    }

    private void ag() {
        dw.x("Ad finished loading.");
        if (this.kz.kL != null) {
            try {
                this.kz.kL.onAdLoaded();
            } catch (RemoteException e) {
                dw.c("Could not call AdListener.onAdLoaded().", e);
            }
        }
    }

    private boolean ah() {
        boolean z = true;
        if (!dq.a(this.kz.kI.getPackageManager(), this.kz.kI.getPackageName(), "android.permission.INTERNET")) {
            if (!this.kz.kN.lT) {
                dv.a(this.kz.kG, this.kz.kN, "Missing internet permission in AndroidManifest.xml.", "Missing internet permission in AndroidManifest.xml. You must have the following declaration: <uses-permission android:name=\"android.permission.INTERNET\" />");
            }
            z = false;
        }
        if (!dq.h(this.kz.kI)) {
            if (!this.kz.kN.lT) {
                dv.a(this.kz.kG, this.kz.kN, "Missing AdActivity with android:configChanges in AndroidManifest.xml.", "Missing AdActivity with android:configChanges in AndroidManifest.xml. You must have the following declaration within the <application> element: <activity android:name=\"com.google.android.gms.ads.AdActivity\" android:configChanges=\"keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize\" />");
            }
            z = false;
        }
        if (!z && !this.kz.kN.lT) {
            this.kz.kG.setVisibility(0);
        }
        return z;
    }

    private void ai() {
        if (this.kz.kO == null) {
            dw.z("Ad state was null when trying to ping click URLs.");
            return;
        }
        dw.v("Pinging click URLs.");
        this.kz.kP.bl();
        if (this.kz.kO.ne != null) {
            dq.a(this.kz.kI, this.kz.kK.rq, this.kz.kO.ne);
        }
        if (this.kz.kO.qt == null || this.kz.kO.qt.ne == null) {
            return;
        }
        bo.a(this.kz.kI, this.kz.kK.rq, this.kz.kO, this.kz.kH, false, this.kz.kO.qt.ne);
    }

    private void aj() {
        if (this.kz.kO != null) {
            this.kz.kO.oj.destroy();
            this.kz.kO = null;
        }
    }

    private void b(View view) {
        this.kz.kG.addView(view, new ViewGroup.LayoutParams(-2, -2));
    }

    private void b(boolean z) {
        if (this.kz.kO == null) {
            dw.z("Ad state was null when trying to ping impression URLs.");
            return;
        }
        dw.v("Pinging Impression URLs.");
        this.kz.kP.bk();
        if (this.kz.kO.nf != null) {
            dq.a(this.kz.kI, this.kz.kK.rq, this.kz.kO.nf);
        }
        if (this.kz.kO.qt != null && this.kz.kO.qt.nf != null) {
            bo.a(this.kz.kI, this.kz.kK.rq, this.kz.kO, this.kz.kH, z, this.kz.kO.qt.nf);
        }
        if (this.kz.kO.nx == null || this.kz.kO.nx.na == null) {
            return;
        }
        bo.a(this.kz.kI, this.kz.kK.rq, this.kz.kO, this.kz.kH, z, this.kz.kO.nx.na);
    }

    private boolean b(dh dhVar) {
        if (dhVar.po) {
            try {
                View view = (View) com.google.android.gms.dynamic.e.d(dhVar.ny.getView());
                View nextView = this.kz.kG.getNextView();
                if (nextView != null) {
                    this.kz.kG.removeView(nextView);
                }
                try {
                    b(view);
                } catch (Throwable th) {
                    dw.c("Could not add mediation view to view hierarchy.", th);
                    return false;
                }
            } catch (RemoteException e) {
                dw.c("Could not get View from mediation adapter.", e);
                return false;
            }
        } else if (dhVar.qu != null) {
            dhVar.oj.a(dhVar.qu);
            this.kz.kG.removeAllViews();
            this.kz.kG.setMinimumWidth(dhVar.qu.widthPixels);
            this.kz.kG.setMinimumHeight(dhVar.qu.heightPixels);
            b(dhVar.oj);
        }
        if (this.kz.kG.getChildCount() > 1) {
            this.kz.kG.showNext();
        }
        if (this.kz.kO != null) {
            View nextView2 = this.kz.kG.getNextView();
            if (nextView2 instanceof dz) {
                ((dz) nextView2).a(this.kz.kI, this.kz.kN);
            } else if (nextView2 != null) {
                this.kz.kG.removeView(nextView2);
            }
            if (this.kz.kO.ny != null) {
                try {
                    this.kz.kO.ny.destroy();
                } catch (RemoteException e2) {
                    dw.z("Could not destroy previous mediation adapter.");
                }
            }
        }
        this.kz.kG.setVisibility(0);
        return true;
    }

    private cx.a c(ah ahVar) {
        PackageInfo packageInfo;
        Bundle bundle;
        ApplicationInfo applicationInfo = this.kz.kI.getApplicationInfo();
        try {
            packageInfo = this.kz.kI.getPackageManager().getPackageInfo(applicationInfo.packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        if (this.kz.kN.lT || this.kz.kG.getParent() == null) {
            bundle = null;
        } else {
            int[] iArr = new int[2];
            this.kz.kG.getLocationOnScreen(iArr);
            int i = iArr[0];
            int i2 = iArr[1];
            DisplayMetrics displayMetrics = this.kz.kI.getResources().getDisplayMetrics();
            int width = this.kz.kG.getWidth();
            int height = this.kz.kG.getHeight();
            int i3 = (!this.kz.kG.isShown() || i + width <= 0 || i2 + height <= 0 || i > displayMetrics.widthPixels || i2 > displayMetrics.heightPixels) ? 0 : 1;
            bundle = new Bundle(5);
            bundle.putInt(TMXConstants.TAG_OBJECT_ATTRIBUTE_X, i);
            bundle.putInt(TMXConstants.TAG_OBJECT_ATTRIBUTE_Y, i2);
            bundle.putInt("width", width);
            bundle.putInt("height", height);
            bundle.putInt("visible", i3);
        }
        String strBs = dj.bs();
        this.kz.kP = new di(strBs, this.kz.kH);
        this.kz.kP.f(ahVar);
        return new cx.a(bundle, ahVar, this.kz.kN, this.kz.kH, applicationInfo, packageInfo, strBs, dj.qK, this.kz.kK, dj.a(this, strBs));
    }

    @Override // com.google.android.gms.internal.u
    public void P() {
        ai();
    }

    @Override // com.google.android.gms.internal.ap
    public com.google.android.gms.dynamic.d Q() {
        fq.aj("getAdFrame must be called on the main UI thread.");
        return com.google.android.gms.dynamic.e.h(this.kz.kG);
    }

    @Override // com.google.android.gms.internal.ap
    public ak R() {
        fq.aj("getAdSize must be called on the main UI thread.");
        return this.kz.kN;
    }

    @Override // com.google.android.gms.internal.ci
    public void U() {
        ae();
    }

    @Override // com.google.android.gms.internal.cf
    public void V() {
        this.kB.d(this.kz.kO);
        if (this.kz.kN.lT) {
            aj();
        }
        this.kC = false;
        ad();
        this.kz.kP.bm();
    }

    @Override // com.google.android.gms.internal.cf
    public void W() {
        if (this.kz.kN.lT) {
            b(false);
        }
        this.kC = true;
        af();
    }

    @Override // com.google.android.gms.internal.bk
    public void X() {
        P();
    }

    @Override // com.google.android.gms.internal.bk
    public void Y() {
        V();
    }

    @Override // com.google.android.gms.internal.bk
    public void Z() {
        U();
    }

    @Override // com.google.android.gms.internal.ap
    public void a(ak akVar) {
        fq.aj("setAdSize must be called on the main UI thread.");
        this.kz.kN = akVar;
        if (this.kz.kO != null) {
            this.kz.kO.oj.a(akVar);
        }
        if (this.kz.kG.getChildCount() > 1) {
            this.kz.kG.removeView(this.kz.kG.getNextView());
        }
        this.kz.kG.setMinimumWidth(akVar.widthPixels);
        this.kz.kG.setMinimumHeight(akVar.heightPixels);
        this.kz.kG.requestLayout();
    }

    @Override // com.google.android.gms.internal.ap
    public void a(ao aoVar) {
        fq.aj("setAdListener must be called on the main UI thread.");
        this.kz.kL = aoVar;
    }

    @Override // com.google.android.gms.internal.ap
    public void a(ar arVar) {
        fq.aj("setAppEventListener must be called on the main UI thread.");
        this.kz.kQ = arVar;
    }

    @Override // com.google.android.gms.internal.ap
    public void a(co coVar) {
        fq.aj("setInAppPurchaseListener must be called on the main UI thread.");
        this.kz.kR = coVar;
    }

    @Override // com.google.android.gms.internal.cr.a
    public void a(dh dhVar) {
        int i;
        int i2 = 0;
        this.kz.kM = null;
        if (dhVar.errorCode != -2 && dhVar.errorCode != 3) {
            dj.b(this.kz.ak());
        }
        if (dhVar.errorCode == -1) {
            return;
        }
        boolean z = dhVar.pg.extras != null ? dhVar.pg.extras.getBoolean("_noRefresh", false) : false;
        if (this.kz.kN.lT) {
            dq.a(dhVar.oj);
        } else if (!z) {
            if (dhVar.ni > 0) {
                this.kA.a(dhVar.pg, dhVar.ni);
            } else if (dhVar.qt != null && dhVar.qt.ni > 0) {
                this.kA.a(dhVar.pg, dhVar.qt.ni);
            } else if (!dhVar.po && dhVar.errorCode == 2) {
                this.kA.d(dhVar.pg);
            }
        }
        if (dhVar.errorCode == 3 && dhVar.qt != null && dhVar.qt.ng != null) {
            dw.v("Pinging no fill URLs.");
            bo.a(this.kz.kI, this.kz.kK.rq, dhVar, this.kz.kH, false, dhVar.qt.ng);
        }
        if (dhVar.errorCode != -2) {
            a(dhVar.errorCode);
            return;
        }
        if (!this.kz.kN.lT) {
            if (!b(dhVar)) {
                a(0);
                return;
            } else if (this.kz.kG != null) {
                this.kz.kG.kF.t(dhVar.pt);
            }
        }
        if (this.kz.kO != null && this.kz.kO.nA != null) {
            this.kz.kO.nA.a((bk) null);
        }
        if (dhVar.nA != null) {
            dhVar.nA.a(this);
        }
        this.kB.d(this.kz.kO);
        this.kz.kO = dhVar;
        if (dhVar.qu != null) {
            this.kz.kN = dhVar.qu;
        }
        this.kz.kP.h(dhVar.qv);
        this.kz.kP.i(dhVar.qw);
        this.kz.kP.m(this.kz.kN.lT);
        this.kz.kP.n(dhVar.po);
        if (!this.kz.kN.lT) {
            b(false);
        }
        if (this.kz.kS == null) {
            this.kz.kS = new dm(this.kz.kH);
        }
        if (dhVar.qt != null) {
            i = dhVar.qt.nj;
            i2 = dhVar.qt.nk;
        } else {
            i = 0;
        }
        this.kz.kS.a(i, i2);
        if (!this.kz.kN.lT && dhVar.oj != null && (dhVar.oj.bI().bP() || dhVar.qs != null)) {
            ab abVarA = this.kB.a(this.kz.kN, this.kz.kO);
            if (dhVar.oj.bI().bP() && abVarA != null) {
                abVarA.a(new w(dhVar.oj));
            }
        }
        this.kz.kO.oj.bE();
        ag();
    }

    @Override // com.google.android.gms.internal.bc
    public void a(String str, ArrayList<String> arrayList) {
        if (this.kz.kR == null) {
            dw.z("InAppPurchaseListener is not set");
            return;
        }
        try {
            this.kz.kR.a(new cm(str, arrayList, this.kz.kI, this.kz.kK.rq));
        } catch (RemoteException e) {
            dw.z("Could not start In-App purchase.");
        }
    }

    @Override // com.google.android.gms.internal.dl
    public void a(HashSet<di> hashSet) {
        this.kz.a(hashSet);
    }

    @Override // com.google.android.gms.internal.ap
    public boolean a(ah ahVar) {
        dz dzVarA;
        dz dzVar;
        fq.aj("loadAd must be called on the main UI thread.");
        if (this.kz.kM != null) {
            dw.z("An ad request is already in progress. Aborting.");
            return false;
        }
        if (this.kz.kN.lT && this.kz.kO != null) {
            dw.z("An interstitial is already loading. Aborting.");
            return false;
        }
        if (!ah()) {
            return false;
        }
        dw.x("Starting ad request.");
        this.kA.cancel();
        cx.a aVarC = c(ahVar);
        if (this.kz.kN.lT) {
            dz dzVarA2 = dz.a(this.kz.kI, this.kz.kN, false, false, this.kz.kJ, this.kz.kK);
            dzVarA2.bI().a(this, null, this, this, true, this);
            dzVar = dzVarA2;
        } else {
            View nextView = this.kz.kG.getNextView();
            if (nextView instanceof dz) {
                dzVarA = (dz) nextView;
                dzVarA.a(this.kz.kI, this.kz.kN);
            } else {
                if (nextView != null) {
                    this.kz.kG.removeView(nextView);
                }
                dzVarA = dz.a(this.kz.kI, this.kz.kN, false, false, this.kz.kJ, this.kz.kK);
                if (this.kz.kN.lU == null) {
                    b(dzVarA);
                }
            }
            dzVarA.bI().a(this, this, this, this, false, this);
            dzVar = dzVarA;
        }
        this.kz.kM = cr.a(this.kz.kI, aVarC, this.kz.kJ, dzVar, this.ky, this);
        return true;
    }

    @Override // com.google.android.gms.internal.bk
    public void aa() {
        W();
    }

    @Override // com.google.android.gms.internal.bk
    public void ab() {
        if (this.kz.kO != null) {
            dw.z("Mediation adapter " + this.kz.kO.nz + " refreshed, but mediation adapters should never refresh.");
        }
        b(true);
        ag();
    }

    @Override // com.google.android.gms.internal.ap
    public void ac() {
        fq.aj("recordManualImpression must be called on the main UI thread.");
        if (this.kz.kO == null) {
            dw.z("Ad state was null when trying to ping manual tracking URLs.");
            return;
        }
        dw.v("Pinging manual tracking URLs.");
        if (this.kz.kO.pq != null) {
            dq.a(this.kz.kI, this.kz.kK.rq, this.kz.kO.pq);
        }
    }

    public void b(ah ahVar) {
        Object parent = this.kz.kG.getParent();
        if ((parent instanceof View) && ((View) parent).isShown() && dq.by() && !this.kC) {
            a(ahVar);
        } else {
            dw.x("Ad is not visible. Not refreshing ad.");
            this.kA.d(ahVar);
        }
    }

    @Override // com.google.android.gms.internal.ap
    public void destroy() {
        fq.aj("destroy must be called on the main UI thread.");
        T();
        this.kz.kL = null;
        this.kz.kQ = null;
        this.kA.cancel();
        stopLoading();
        if (this.kz.kG != null) {
            this.kz.kG.removeAllViews();
        }
        if (this.kz.kO != null && this.kz.kO.oj != null) {
            this.kz.kO.oj.destroy();
        }
        if (this.kz.kO == null || this.kz.kO.ny == null) {
            return;
        }
        try {
            this.kz.kO.ny.destroy();
        } catch (RemoteException e) {
            dw.z("Could not destroy mediation adapter.");
        }
    }

    @Override // com.google.android.gms.internal.ap
    public boolean isReady() {
        fq.aj("isLoaded must be called on the main UI thread.");
        return this.kz.kM == null && this.kz.kO != null;
    }

    @Override // com.google.android.gms.internal.az
    public void onAppEvent(String name, String info) {
        if (this.kz.kQ != null) {
            try {
                this.kz.kQ.onAppEvent(name, info);
            } catch (RemoteException e) {
                dw.c("Could not call the AppEventListener.", e);
            }
        }
    }

    @Override // com.google.android.gms.internal.ap
    public void pause() {
        fq.aj("pause must be called on the main UI thread.");
        if (this.kz.kO != null) {
            dq.a(this.kz.kO.oj);
        }
        if (this.kz.kO != null && this.kz.kO.ny != null) {
            try {
                this.kz.kO.ny.pause();
            } catch (RemoteException e) {
                dw.z("Could not pause mediation adapter.");
            }
        }
        this.kA.pause();
    }

    @Override // com.google.android.gms.internal.ap
    public void resume() {
        fq.aj("resume must be called on the main UI thread.");
        if (this.kz.kO != null) {
            dq.b(this.kz.kO.oj);
        }
        if (this.kz.kO != null && this.kz.kO.ny != null) {
            try {
                this.kz.kO.ny.resume();
            } catch (RemoteException e) {
                dw.z("Could not resume mediation adapter.");
            }
        }
        this.kA.resume();
    }

    @Override // com.google.android.gms.internal.ap
    public void showInterstitial() {
        fq.aj("showInterstitial must be called on the main UI thread.");
        if (!this.kz.kN.lT) {
            dw.z("Cannot call showInterstitial on a banner ad.");
            return;
        }
        if (this.kz.kO == null) {
            dw.z("The interstitial has not loaded.");
            return;
        }
        if (this.kz.kO.oj.bL()) {
            dw.z("The interstitial is already showing.");
            return;
        }
        this.kz.kO.oj.p(true);
        if (this.kz.kO.oj.bI().bP() || this.kz.kO.qs != null) {
            ab abVarA = this.kB.a(this.kz.kN, this.kz.kO);
            if (this.kz.kO.oj.bI().bP() && abVarA != null) {
                abVarA.a(new w(this.kz.kO.oj));
            }
        }
        if (!this.kz.kO.po) {
            cc.a(this.kz.kI, new ce(this, this, this, this.kz.kO.oj, this.kz.kO.orientation, this.kz.kK, this.kz.kO.pt));
            return;
        }
        try {
            this.kz.kO.ny.showInterstitial();
        } catch (RemoteException e) {
            dw.c("Could not show interstitial.", e);
            aj();
        }
    }

    @Override // com.google.android.gms.internal.ap
    public void stopLoading() {
        fq.aj("stopLoading must be called on the main UI thread.");
        if (this.kz.kO != null) {
            this.kz.kO.oj.stopLoading();
            this.kz.kO = null;
        }
        if (this.kz.kM != null) {
            this.kz.kM.cancel();
        }
    }
}
