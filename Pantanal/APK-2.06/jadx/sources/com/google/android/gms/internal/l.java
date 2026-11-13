package com.google.android.gms.internal;

import android.content.Context;
import android.net.Uri;
import android.view.MotionEvent;

/* JADX INFO: loaded from: classes.dex */
public class l {
    private h kg;
    private String kd = "googleads.g.doubleclick.net";
    private String ke = "/pagead/ads";
    private String[] kf = {".doubleclick.net", ".googleadservices.com", ".googlesyndication.com"};
    private final g kh = new g();

    public l(h hVar) {
        this.kg = hVar;
    }

    private Uri a(Uri uri, Context context, String str, boolean z) throws m {
        try {
            if (uri.getQueryParameter("ms") != null) {
                throw new m("Query parameter already exists: ms");
            }
            return a(uri, "ms", z ? this.kg.a(context, str) : this.kg.a(context));
        } catch (UnsupportedOperationException e) {
            throw new m("Provided Uri is not in a valid state");
        }
    }

    private Uri a(Uri uri, String str, String str2) throws UnsupportedOperationException {
        String string = uri.toString();
        int iIndexOf = string.indexOf("&adurl");
        if (iIndexOf == -1) {
            iIndexOf = string.indexOf("?adurl");
        }
        return iIndexOf != -1 ? Uri.parse(string.substring(0, iIndexOf + 1) + str + "=" + str2 + "&" + string.substring(iIndexOf + 1)) : uri.buildUpon().appendQueryParameter(str, str2).build();
    }

    public Uri a(Uri uri, Context context) throws m {
        try {
            return a(uri, context, uri.getQueryParameter("ai"), true);
        } catch (UnsupportedOperationException e) {
            throw new m("Provided Uri is not in a valid state");
        }
    }

    public void a(MotionEvent motionEvent) {
        this.kg.a(motionEvent);
    }

    public boolean a(Uri uri) {
        if (uri == null) {
            throw new NullPointerException();
        }
        try {
            String host = uri.getHost();
            for (String str : this.kf) {
                if (host.endsWith(str)) {
                    return true;
                }
            }
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public h y() {
        return this.kg;
    }
}
