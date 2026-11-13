package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.google.android.gms.ads.AdActivity;
import com.google.android.gms.drive.DriveFile;
import com.heyzap.http.AsyncHttpResponseHandler;

/* JADX INFO: loaded from: classes.dex */
public class cc extends ck.a {
    private dz lC;
    private final Activity nS;
    private ce nT;
    private cg nU;
    private c nV;
    private ch nW;
    private boolean nX;
    private FrameLayout nY;
    private WebChromeClient.CustomViewCallback nZ;
    private boolean oa = false;
    private boolean ob = false;
    private RelativeLayout oc;

    private static final class a extends Exception {
        public a(String str) {
            super(str);
        }
    }

    private static final class b extends RelativeLayout {
        private final dr kF;

        public b(Context context, String str) {
            super(context);
            this.kF = new dr(context, str);
        }

        @Override // android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent event) {
            this.kF.c(event);
            return false;
        }
    }

    private static final class c {
        public final int index;
        public final ViewGroup.LayoutParams oe;
        public final ViewGroup of;

        public c(dz dzVar) throws a {
            this.oe = dzVar.getLayoutParams();
            ViewParent parent = dzVar.getParent();
            if (!(parent instanceof ViewGroup)) {
                throw new a("Could not get the parent of the WebView for an overlay.");
            }
            this.of = (ViewGroup) parent;
            this.index = this.of.indexOfChild(dzVar);
            this.of.removeView(dzVar);
            dzVar.p(true);
        }
    }

    public cc(Activity activity) {
        this.nS = activity;
    }

    private static RelativeLayout.LayoutParams a(int i, int i2, int i3, int i4) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(i3, i4);
        layoutParams.setMargins(i, i2, 0, 0);
        layoutParams.addRule(10);
        layoutParams.addRule(9);
        return layoutParams;
    }

    public static void a(Context context, ce ceVar) {
        Intent intent = new Intent();
        intent.setClassName(context, AdActivity.CLASS_NAME);
        intent.putExtra("com.google.android.gms.ads.internal.overlay.useClientJar", ceVar.kK.rt);
        ce.a(intent, ceVar);
        intent.addFlags(AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END);
        if (!(context instanceof Activity)) {
            intent.addFlags(DriveFile.MODE_READ_ONLY);
        }
        context.startActivity(intent);
    }

    private void aN() {
        if (!this.nS.isFinishing() || this.ob) {
            return;
        }
        this.ob = true;
        if (this.nS.isFinishing()) {
            if (this.lC != null) {
                this.lC.bF();
                this.oc.removeView(this.lC);
                if (this.nV != null) {
                    this.lC.p(false);
                    this.nV.of.addView(this.lC, this.nV.index, this.nV.oe);
                }
            }
            if (this.nT == null || this.nT.oi == null) {
                return;
            }
            this.nT.oi.V();
        }
    }

    private void j(boolean z) throws a {
        if (!this.nX) {
            this.nS.requestWindowFeature(1);
        }
        Window window = this.nS.getWindow();
        window.setFlags(1024, 1024);
        setRequestedOrientation(this.nT.orientation);
        if (Build.VERSION.SDK_INT >= 11) {
            dw.v("Enabling hardware acceleration on the AdActivity window.");
            ds.a(window);
        }
        this.oc = new b(this.nS, this.nT.or);
        this.oc.setBackgroundColor(-16777216);
        this.nS.setContentView(this.oc);
        N();
        boolean zBP = this.nT.oj.bI().bP();
        if (z) {
            this.lC = dz.a(this.nS, this.nT.oj.R(), true, zBP, null, this.nT.kK);
            this.lC.bI().a(null, null, this.nT.ok, this.nT.oo, true, this.nT.oq);
            this.lC.bI().a(new ea.a() { // from class: com.google.android.gms.internal.cc.1
                @Override // com.google.android.gms.internal.ea.a
                public void a(dz dzVar) {
                    dzVar.bG();
                }
            });
            if (this.nT.nO != null) {
                this.lC.loadUrl(this.nT.nO);
            } else {
                if (this.nT.on == null) {
                    throw new a("No URL or HTML to display in ad overlay.");
                }
                this.lC.loadDataWithBaseURL(this.nT.ol, this.nT.on, "text/html", AsyncHttpResponseHandler.DEFAULT_CHARSET, null);
            }
        } else {
            this.lC = this.nT.oj;
            this.lC.setContext(this.nS);
        }
        this.lC.a(this);
        ViewParent parent = this.lC.getParent();
        if (parent != null && (parent instanceof ViewGroup)) {
            ((ViewGroup) parent).removeView(this.lC);
        }
        this.oc.addView(this.lC, -1, -1);
        if (!z) {
            this.lC.bG();
        }
        h(zBP);
    }

    @Override // com.google.android.gms.internal.ck
    public void N() {
        this.nX = true;
    }

    public void a(View view, WebChromeClient.CustomViewCallback customViewCallback) {
        this.nY = new FrameLayout(this.nS);
        this.nY.setBackgroundColor(-16777216);
        this.nY.addView(view, -1, -1);
        this.nS.setContentView(this.nY);
        N();
        this.nZ = customViewCallback;
    }

    public cg aK() {
        return this.nU;
    }

    public void aL() {
        if (this.nT != null) {
            setRequestedOrientation(this.nT.orientation);
        }
        if (this.nY != null) {
            this.nS.setContentView(this.oc);
            N();
            this.nY.removeAllViews();
            this.nY = null;
        }
        if (this.nZ != null) {
            this.nZ.onCustomViewHidden();
            this.nZ = null;
        }
    }

    public void aM() {
        this.oc.removeView(this.nW);
        h(true);
    }

    public void b(int i, int i2, int i3, int i4) {
        if (this.nU != null) {
            this.nU.setLayoutParams(a(i, i2, i3, i4));
        }
    }

    public void c(int i, int i2, int i3, int i4) {
        if (this.nU == null) {
            this.nU = new cg(this.nS, this.lC);
            this.oc.addView(this.nU, 0, a(i, i2, i3, i4));
            this.lC.bI().q(false);
        }
    }

    public void close() {
        this.nS.finish();
    }

    public void h(boolean z) {
        this.nW = new ch(this.nS, z ? 50 : 32);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(10);
        layoutParams.addRule(z ? 11 : 9);
        this.nW.i(this.nT.om);
        this.oc.addView(this.nW, layoutParams);
    }

    public void i(boolean z) {
        if (this.nW != null) {
            this.nW.i(z);
        }
    }

    @Override // com.google.android.gms.internal.ck
    public void onCreate(Bundle savedInstanceState) {
        this.oa = savedInstanceState != null ? savedInstanceState.getBoolean("com.google.android.gms.ads.internal.overlay.hasResumed", false) : false;
        try {
            this.nT = ce.a(this.nS.getIntent());
            if (this.nT == null) {
                throw new a("Could not get info for ad overlay.");
            }
            if (savedInstanceState == null) {
                if (this.nT.oi != null) {
                    this.nT.oi.W();
                }
                if (this.nT.op != 1 && this.nT.oh != null) {
                    this.nT.oh.P();
                }
            }
            switch (this.nT.op) {
                case 1:
                    j(false);
                    return;
                case 2:
                    this.nV = new c(this.nT.oj);
                    j(false);
                    return;
                case 3:
                    j(true);
                    return;
                case 4:
                    if (this.oa) {
                        this.nS.finish();
                        return;
                    } else {
                        if (bz.a(this.nS, this.nT.og, this.nT.oo)) {
                            return;
                        }
                        this.nS.finish();
                        return;
                    }
                default:
                    throw new a("Could not determine ad overlay type.");
            }
        } catch (a e) {
            dw.z(e.getMessage());
            this.nS.finish();
        }
    }

    @Override // com.google.android.gms.internal.ck
    public void onDestroy() {
        if (this.nU != null) {
            this.nU.destroy();
        }
        if (this.lC != null) {
            this.oc.removeView(this.lC);
        }
        aN();
    }

    @Override // com.google.android.gms.internal.ck
    public void onPause() {
        if (this.nU != null) {
            this.nU.pause();
        }
        aL();
        if (this.lC != null && (!this.nS.isFinishing() || this.nV == null)) {
            dq.a(this.lC);
        }
        aN();
    }

    @Override // com.google.android.gms.internal.ck
    public void onRestart() {
    }

    @Override // com.google.android.gms.internal.ck
    public void onResume() {
        if (this.nT != null && this.nT.op == 4) {
            if (this.oa) {
                this.nS.finish();
            } else {
                this.oa = true;
            }
        }
        if (this.lC != null) {
            dq.b(this.lC);
        }
    }

    @Override // com.google.android.gms.internal.ck
    public void onSaveInstanceState(Bundle outBundle) {
        outBundle.putBoolean("com.google.android.gms.ads.internal.overlay.hasResumed", this.oa);
    }

    @Override // com.google.android.gms.internal.ck
    public void onStart() {
    }

    @Override // com.google.android.gms.internal.ck
    public void onStop() {
        aN();
    }

    public void setRequestedOrientation(int requestedOrientation) {
        this.nS.setRequestedOrientation(requestedOrientation);
    }
}
