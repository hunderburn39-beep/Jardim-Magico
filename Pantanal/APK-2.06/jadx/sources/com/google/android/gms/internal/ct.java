package com.google.android.gms.internal;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import com.heyzap.http.AsyncHttpResponseHandler;

/* JADX INFO: loaded from: classes.dex */
public class ct implements Runnable {
    private final int kr;
    private final int ks;
    protected final dz lC;
    private final Handler oT;
    private final long oU;
    private long oV;
    private ea.a oW;
    protected boolean oX;
    protected boolean oY;

    protected final class a extends AsyncTask<Void, Void, Boolean> {
        private final WebView oZ;
        private Bitmap pa;

        public a(WebView webView) {
            this.oZ = webView;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public synchronized Boolean doInBackground(Void... voidArr) {
            boolean zValueOf;
            int width = this.pa.getWidth();
            int height = this.pa.getHeight();
            if (width == 0 || height == 0) {
                zValueOf = false;
            } else {
                int i = 0;
                for (int i2 = 0; i2 < width; i2 += 10) {
                    for (int i3 = 0; i3 < height; i3 += 10) {
                        if (this.pa.getPixel(i2, i3) != 0) {
                            i++;
                        }
                    }
                }
                zValueOf = Boolean.valueOf(((double) i) / (((double) (width * height)) / 100.0d) > 0.1d);
            }
            return zValueOf;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onPostExecute(Boolean bool) {
            ct.c(ct.this);
            if (bool.booleanValue() || ct.this.bc() || ct.this.oV <= 0) {
                ct.this.oY = bool.booleanValue();
                ct.this.oW.a(ct.this.lC);
            } else if (ct.this.oV > 0) {
                if (dw.n(2)) {
                    dw.v("Ad not detected, scheduling another run.");
                }
                ct.this.oT.postDelayed(ct.this, ct.this.oU);
            }
        }

        @Override // android.os.AsyncTask
        protected synchronized void onPreExecute() {
            this.pa = Bitmap.createBitmap(ct.this.kr, ct.this.ks, Bitmap.Config.ARGB_8888);
            this.oZ.setVisibility(0);
            this.oZ.measure(View.MeasureSpec.makeMeasureSpec(ct.this.kr, 0), View.MeasureSpec.makeMeasureSpec(ct.this.ks, 0));
            this.oZ.layout(0, 0, ct.this.kr, ct.this.ks);
            this.oZ.draw(new Canvas(this.pa));
            this.oZ.invalidate();
        }
    }

    public ct(ea.a aVar, dz dzVar, int i, int i2) {
        this(aVar, dzVar, i, i2, 200L, 50L);
    }

    public ct(ea.a aVar, dz dzVar, int i, int i2, long j, long j2) {
        this.oU = j;
        this.oV = j2;
        this.oT = new Handler(Looper.getMainLooper());
        this.lC = dzVar;
        this.oW = aVar;
        this.oX = false;
        this.oY = false;
        this.ks = i2;
        this.kr = i;
    }

    static /* synthetic */ long c(ct ctVar) {
        long j = ctVar.oV - 1;
        ctVar.oV = j;
        return j;
    }

    public void a(cz czVar, ee eeVar) {
        this.lC.setWebViewClient(eeVar);
        this.lC.loadDataWithBaseURL(TextUtils.isEmpty(czVar.ol) ? null : dq.r(czVar.ol), czVar.pm, "text/html", AsyncHttpResponseHandler.DEFAULT_CHARSET, null);
    }

    public void b(cz czVar) {
        a(czVar, new ee(this, this.lC, czVar.pv));
    }

    public void ba() {
        this.oT.postDelayed(this, this.oU);
    }

    public synchronized void bb() {
        this.oX = true;
    }

    public synchronized boolean bc() {
        return this.oX;
    }

    public boolean bd() {
        return this.oY;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.lC == null || bc()) {
            this.oW.a(this.lC);
        } else {
            new a(this.lC).execute(new Void[0]);
        }
    }
}
