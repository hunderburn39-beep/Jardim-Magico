package com.adcolony.sdk;

import android.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.heyzap.http.AsyncHttpResponseHandler;
import com.tapjoy.TJAdUnitConstants;
import java.util.Map;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
class bz extends Activity {
    private FrameLayout a;
    private LinearLayout b;
    private WebView c;
    private FrameLayout.LayoutParams d;
    private ImageView e;
    private WebSettings f;
    private ci g;

    bz() {
    }

    @Override // android.app.Activity
    @SuppressLint({"NewApi", "RtlHardcoded"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        this.a = new FrameLayout(this);
        this.b = new LinearLayout(this);
        this.b.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        this.a.addView(this.b);
        int i = getResources().getConfiguration().orientation;
        if (bv.aN().aw().F()) {
            setRequestedOrientation(14);
            if (i == 0) {
                this.b.setOrientation(0);
            } else if (i == 1) {
                this.b.setOrientation(1);
            }
        }
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
        this.a.setBackgroundColor(0);
        setContentView(this.a, layoutParams);
        this.c = new WebView(this);
        this.g = bv.aN().aC().p();
        this.g.a(new b(this.c, this));
        bw.b(a(), "show toastModal was called", true);
        int iA = (int) ((30.0f * ck.a()) + 0.5f);
        int iA2 = (int) ((ck.a() * 30.0f) + 0.5f);
        this.d = new FrameLayout.LayoutParams(iA, iA2, 53);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams();
        layoutParams2.format = -2;
        layoutParams2.width = -1;
        layoutParams2.height = -1;
        window.setAttributes(layoutParams2);
        window.setBackgroundDrawableResource(R.color.transparent);
        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            this.c.restoreState(savedInstanceState);
        }
        this.c.setVerticalScrollBarEnabled(false);
        this.c.setHorizontalScrollBarEnabled(false);
        this.c.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        this.g.a();
        this.a.addView(this.c);
        this.c.setBackgroundColor(Color.argb(1, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK));
        this.f = this.c.getSettings();
        this.f.setDomStorageEnabled(true);
        this.f.setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        this.f.setAllowFileAccess(true);
        this.f.setAppCacheEnabled(true);
        this.f.setJavaScriptEnabled(true);
        this.f.setCacheMode(-1);
        this.c.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.c.addJavascriptInterface(new ba(new a(this.c)), "Android");
        this.c.loadDataWithBaseURL("http://www.yvolver.com", this.g.c(), "text/html", AsyncHttpResponseHandler.DEFAULT_CHARSET, null);
        this.c.setWebViewClient(new WebViewClient() { // from class: com.adcolony.sdk.bz.1
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (view != null) {
                    view.setAlpha(1.0f);
                }
            }
        });
        this.e = new ImageView(bv.aN().l());
        be.a aVarB = bv.aN().av().b("catalog_close_button");
        if (aVarB != null) {
            byte[] bArrDecode = Base64.decode(aVarB.b(), 0);
            this.e.setImageBitmap(BitmapFactory.decodeByteArray(bArrDecode, 0, bArrDecode.length));
        }
        int iQ = bv.aN().Q();
        int iR = bv.aN().R();
        int iIntValue = bv.aN().at().containsKey("xPos") ? ((Integer) bv.aN().at().get("xPos")).intValue() : iQ;
        int iIntValue2 = bv.aN().at().containsKey("yPos") ? ((Integer) bv.aN().at().get("yPos")).intValue() : iR;
        bw.b(a(), "modal buttonOffset=(" + iIntValue + ", " + iIntValue2 + ")", true);
        int iA3 = (int) (iIntValue * ck.a());
        int iA4 = (int) (iIntValue2 * ck.a());
        this.d.width = iA;
        this.d.height = iA2;
        this.d.gravity = 8388661;
        this.d.rightMargin = iA3;
        this.d.topMargin = iA4;
        this.a.addView(this.e);
        this.e.setLayoutParams(this.d);
        this.e.setVisibility(0);
        this.e.setOnClickListener(new View.OnClickListener() { // from class: com.adcolony.sdk.bz.2
            @Override // android.view.View.OnClickListener
            public void onClick(View arg0) {
                bv.aN().aC().a(ci.a.USER_CLOSE_BUTTON);
            }
        });
        this.g.v();
    }

    class b implements ci.d {
        private WebView b;
        private Context c;

        public b(WebView webView, Context context) {
            this.b = null;
            this.c = null;
            this.b = webView;
            this.c = context;
        }

        @Override // com.adcolony.sdk.ci.d
        public WebView a() {
            return this.b;
        }

        @Override // com.adcolony.sdk.ci.d
        public Context b() {
            return this.c;
        }

        @Override // com.adcolony.sdk.ci.d
        public void c() {
            bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.bz.b.1
                @Override // java.lang.Runnable
                public void run() {
                    if (b.this.b != null) {
                        ck.a(b.this.b, "javascript:finishDigitalRedemptionTransaction()");
                    }
                }
            });
        }

        @Override // com.adcolony.sdk.ci.d
        public void a(final bk bkVar) {
            bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.bz.b.2
                @Override // java.lang.Runnable
                public void run() {
                    bz.this.f.setJavaScriptEnabled(false);
                    bz.this.c.stopLoading();
                    bz.this.c.invalidate();
                    bz.this.c.removeAllViews();
                    bz.this.a.removeAllViews();
                    bz.this.a.invalidate();
                    bz.this.c.destroy();
                    bz.this.a = null;
                    bz.this.finish();
                    System.gc();
                    bkVar.a(bn.a.YVOLVER_ERROR_NONE);
                }
            });
        }
    }

    class a implements cn {
        private WebView b;

        public a(WebView webView) {
            this.b = null;
            this.b = webView;
        }

        @Override // com.adcolony.sdk.cn
        public WebView a() {
            return this.b;
        }

        @Override // com.adcolony.sdk.cn
        public void a(final Map<String, Object> map) {
            bz.this.runOnUiThread(new Runnable() { // from class: com.adcolony.sdk.bz.a.1
                @Override // java.lang.Runnable
                public void run() {
                    if (map.containsKey(TMXConstants.TAG_OBJECT_ATTRIBUTE_X) && map.containsKey(TMXConstants.TAG_OBJECT_ATTRIBUTE_Y)) {
                        Number number = (Number) map.get(TMXConstants.TAG_OBJECT_ATTRIBUTE_X);
                        Number number2 = (Number) map.get(TMXConstants.TAG_OBJECT_ATTRIBUTE_Y);
                        int iIntValue = number.intValue();
                        int iIntValue2 = number2.intValue();
                        bz.this.d.rightMargin = (int) (iIntValue * ck.a());
                        bz.this.d.topMargin = (int) (iIntValue2 * ck.a());
                        bz.this.d.gravity = 53;
                    }
                    if (map.containsKey(TJAdUnitConstants.String.ENABLED)) {
                        if (((Boolean) map.get(TJAdUnitConstants.String.ENABLED)).booleanValue()) {
                            bz.this.e.setVisibility(0);
                        } else {
                            bz.this.e.setVisibility(4);
                        }
                    }
                    bz.this.e.setLayoutParams(bz.this.d);
                }
            });
        }

        @Override // com.adcolony.sdk.cn
        public void b() {
            bv.aN().aC().l();
        }

        @Override // com.adcolony.sdk.cn
        public void c() {
            bv.aN().aC().m();
        }

        @Override // com.adcolony.sdk.cn
        public void a(String str, String str2) {
            bw.b(bz.this.a(), "openCatalogItem was called", true);
            bv.aN().aC().a(str, str2, bz.this.g);
        }
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.c.saveState(outState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a() {
        return "AdColonyPubServices";
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        bv.aN().aC().a(ci.a.HARDWARE_BACK_BUTTON);
    }
}
