package com.fyber.ads.ofw.a;

import android.app.Activity;
import android.net.Uri;
import android.webkit.WebView;
import com.fyber.Fyber;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.r;
import com.fyber.utils.x;
import java.util.Locale;

/* JADX INFO: compiled from: ActivityOfferWebClient.java */
/* JADX INFO: loaded from: classes.dex */
public final class a extends x {
    private boolean a;

    public a(Activity activity, boolean z) {
        super(activity);
        this.a = z;
    }

    @Override // com.fyber.utils.x
    protected final void a(int i, String str) {
        boolean z;
        Activity activityA = a();
        if (activityA != null) {
            activityA.setResult(i);
            if (str == null) {
                z = true;
            } else {
                z = this.a;
                if (!a(str)) {
                    return;
                }
            }
            FyberLogger.i("ActivityOfferWebClient", "Should close: " + this.a + ", will close activity: " + z);
            if (z) {
                activityA.finish();
            }
        }
    }

    @Override // com.fyber.utils.x
    protected final void a(String str, Uri uri) {
    }

    @Override // com.fyber.utils.x
    protected final void b() {
    }

    @Override // android.webkit.WebViewClient
    public final void onReceivedError(WebView webView, int i, String str, String str2) {
        Fyber.Settings.UIStringIdentifier uIStringIdentifier;
        FyberLogger.e("ActivityOfferWebClient", String.format(Locale.ENGLISH, "OfferWall WebView triggered an error. Error code: %d, error description: %s. Failing URL: %s", Integer.valueOf(i), str, str2));
        switch (i) {
            case -7:
            case -2:
                uIStringIdentifier = Fyber.Settings.UIStringIdentifier.ERROR_LOADING_OFFERWALL_NO_INTERNET_CONNECTION;
                break;
            default:
                uIStringIdentifier = Fyber.Settings.UIStringIdentifier.ERROR_LOADING_OFFERWALL;
                break;
        }
        b(r.a(uIStringIdentifier));
    }
}
