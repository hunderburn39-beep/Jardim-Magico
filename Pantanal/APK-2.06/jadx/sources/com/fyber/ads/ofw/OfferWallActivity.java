package com.fyber.ads.ofw;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.fyber.Fyber;
import com.fyber.a.a;
import com.fyber.cache.CacheManager;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.e;
import com.fyber.utils.h;
import com.fyber.utils.i;
import com.fyber.utils.r;
import com.fyber.utils.s;
import com.fyber.utils.y;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class OfferWallActivity extends Activity {
    public static final String EXTRA_KEYS_VALUES_MAP_KEY = "EXTRA_KEY_VALUES_MAP";
    public static final String EXTRA_PLACEMENT_ID_KEY = "EXTRA_PLACEMENT_ID_KEY";
    public static final String EXTRA_SHOULD_CLOSE_ON_REDIRECT_KEY = "EXTRA_SHOULD_CLOSE_ON_REDIRECT_KEY";
    public static final int RESULT_CODE_NO_STATUS_CODE = -10;
    public static final String TAG = "OfferWallActivity";
    private boolean a;
    private ProgressDialog b;
    private AlertDialog c;
    private a d;
    private com.fyber.ads.ofw.a.a e;
    private String f;
    protected Map<String, String> mCustomKeysValues;
    protected WebView mWebView;

    static /* synthetic */ ProgressDialog b(OfferWallActivity offerWallActivity) {
        offerWallActivity.b = null;
        return null;
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (h.e()) {
            getWindow().requestFeature(1);
            this.b = new ProgressDialog(this);
            this.b.setOwnerActivity(this);
            this.b.setIndeterminate(true);
            this.b.setMessage(r.a(Fyber.Settings.UIStringIdentifier.LOADING_OFFERWALL));
            this.b.show();
            fetchPassedExtras();
            this.mWebView = new WebView(getApplicationContext());
            this.mWebView.setScrollBarStyle(0);
            setContentView(this.mWebView);
            this.mWebView.getSettings().setJavaScriptEnabled(true);
            y.a(this.mWebView.getSettings());
            y.a(this.mWebView);
            this.e = new com.fyber.ads.ofw.a.a(this, this.a);
            this.mWebView.setWebViewClient(this.e);
            this.mWebView.setWebChromeClient(new WebChromeClient() { // from class: com.fyber.ads.ofw.OfferWallActivity.1
                @Override // android.webkit.WebChromeClient
                public final void onProgressChanged(WebView webView, int i) {
                    if (i > 50 && OfferWallActivity.this.b != null) {
                        OfferWallActivity.this.b.dismiss();
                        OfferWallActivity.b(OfferWallActivity.this);
                    }
                    super.onProgressChanged(webView, i);
                }
            });
            return;
        }
        setResult(-20);
        finish();
    }

    protected void fetchPassedExtras() {
        Intent intent = getIntent();
        if (!Fyber.getConfigs().d()) {
            SharedPreferences preferences = getPreferences(0);
            String string = preferences.getString("app.id.key", "");
            String string2 = preferences.getString("user.id.key", "");
            String string3 = preferences.getString("security.token.key", "");
            boolean z = preferences.getBoolean("precaching.enabled", false);
            Fyber fyberWithSecurityToken = Fyber.with(string, this).withUserId(string2).withSecurityToken(string3);
            if (z) {
                fyberWithSecurityToken.withManualPrecaching();
            }
            fyberWithSecurityToken.start();
            this.d = Fyber.getConfigs().e();
            getPreferences(0).edit().clear().commit();
        }
        this.d = Fyber.getConfigs().e();
        this.a = intent.getBooleanExtra(EXTRA_SHOULD_CLOSE_ON_REDIRECT_KEY, shouldCloseOnRedirectDefault());
        Serializable serializableExtra = intent.getSerializableExtra(EXTRA_KEYS_VALUES_MAP_KEY);
        if (serializableExtra instanceof HashMap) {
            this.mCustomKeysValues = (HashMap) serializableExtra;
        }
        String stringExtra = intent.getStringExtra(EXTRA_PLACEMENT_ID_KEY);
        if (stringExtra != null) {
            this.f = stringExtra;
        }
    }

    @Override // android.app.Activity
    protected void onPause() {
        if (this.c != null) {
            this.c.dismiss();
            this.c = null;
        }
        if (this.b != null) {
            this.b.dismiss();
            this.b = null;
        }
        getPreferences(0).edit().putString("app.id.key", this.d.a()).putString("user.id.key", this.d.b()).putString("security.token.key", this.d.c()).putBoolean("precaching.enabled", CacheManager.a().e()).apply();
        super.onPause();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        try {
            String strG = s.a(e.a("ofw"), this.d).e().b(this.f).a(this.mCustomKeysValues).a().g();
            FyberLogger.d(TAG, "Offer Wall request url: " + strG);
            this.mWebView.loadUrl(strG, i.d());
        } catch (RuntimeException e) {
            FyberLogger.e(TAG, "An exception occurred when launching the Offer Wall", e);
            this.e.b(e.getMessage());
        }
    }

    public boolean shouldCloseOnRedirectDefault() {
        return false;
    }
}
