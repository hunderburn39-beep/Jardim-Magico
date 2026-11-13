package com.fyber.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.fyber.Fyber;
import com.tapjoy.mraid.controller.Abstract;
import java.lang.ref.WeakReference;

/* JADX INFO: compiled from: WebClient.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class x extends WebViewClient {
    private WeakReference<Activity> a;

    protected abstract void a(int i, String str);

    protected abstract void a(String str, Uri uri);

    protected abstract void b();

    public x(Activity activity) {
        this.a = new WeakReference<>(activity);
    }

    protected Activity a() {
        return this.a.get();
    }

    private static String a(String str, String str2) {
        return Uri.parse(str).getQueryParameter(str2);
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        int i;
        FyberLogger.i("WebClient", "shouldOverrideUrlLoading called with url: " + str);
        if (str.startsWith("sponsorpay://")) {
            Uri uri = Uri.parse(str);
            String host = uri.getHost();
            if (host.equals(Abstract.EXIT)) {
                if (n.b(21)) {
                    CookieSyncManager.getInstance().sync();
                }
                String strA = a(str, "status");
                if (strA != null) {
                    i = Integer.parseInt(strA);
                } else {
                    i = -10;
                }
                String strA2 = a(str, "url");
                FyberLogger.i("WebClient", "Overriding. Target Url: " + strA2);
                String strA3 = a(str, "tracking_url");
                if (StringUtils.notNullNorEmpty(strA3)) {
                    FyberLogger.i("WebClient", "Overriding. Tracking Url: " + strA3);
                    com.fyber.b.h.a(strA3);
                }
                a(i, strA2);
            } else {
                a(host, uri);
            }
            return true;
        }
        FyberLogger.i("WebClient", "Not overriding");
        return false;
    }

    protected final boolean a(String str) {
        Activity activityA = a();
        if (activityA == null || str == null) {
            return false;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri uri = Uri.parse(str);
        intent.setData(uri);
        try {
            activityA.startActivity(intent);
            b();
            return true;
        } catch (ActivityNotFoundException e) {
            if (!uri.getScheme().equalsIgnoreCase("market") || l.a(a(), "android.intent.action.VIEW", Uri.parse("market://search?q=pname:com.google"))) {
                return false;
            }
            FyberLogger.e("WebClient", "Play Store is not installed on this device...");
            c();
            return false;
        }
    }

    protected void c() {
        b(r.a(Fyber.Settings.UIStringIdentifier.ERROR_PLAY_STORE_UNAVAILABLE));
    }

    public final void b(String str) {
        String strA = r.a(Fyber.Settings.UIStringIdentifier.ERROR_DIALOG_TITLE);
        String strA2 = r.a(Fyber.Settings.UIStringIdentifier.DISMISS_ERROR_DIALOG);
        AlertDialog.Builder builder = new AlertDialog.Builder(a());
        builder.setTitle(strA);
        builder.setMessage(str);
        builder.setNegativeButton(strA2, new DialogInterface.OnClickListener() { // from class: com.fyber.utils.x.1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (x.this.a() != null) {
                    x.this.a().finish();
                }
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.setOwnerActivity(a());
        try {
            alertDialogCreate.show();
        } catch (WindowManager.BadTokenException e) {
            FyberLogger.e("WebClient", "Couldn't show error dialog. Not displayed error message is: " + str, e);
        }
    }
}
