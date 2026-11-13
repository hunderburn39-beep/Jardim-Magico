package com.google.android.gms.tagmanager;

import android.net.Uri;
import com.heyzap.http.AsyncHttpResponseHandler;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/* JADX INFO: loaded from: classes.dex */
class cd {
    private static cd YP;
    private volatile String WJ;
    private volatile a YQ;
    private volatile String YR;
    private volatile String YS;

    enum a {
        NONE,
        CONTAINER,
        CONTAINER_DEBUG
    }

    cd() {
        clear();
    }

    private String bI(String str) {
        return str.split("&")[0].split("=")[1];
    }

    private String h(Uri uri) {
        return uri.getQuery().replace("&gtm_debug=x", "");
    }

    static cd kT() {
        cd cdVar;
        synchronized (cd.class) {
            if (YP == null) {
                YP = new cd();
            }
            cdVar = YP;
        }
        return cdVar;
    }

    void clear() {
        this.YQ = a.NONE;
        this.YR = null;
        this.WJ = null;
        this.YS = null;
    }

    synchronized boolean g(Uri uri) {
        boolean z = true;
        synchronized (this) {
            try {
                String strDecode = URLDecoder.decode(uri.toString(), AsyncHttpResponseHandler.DEFAULT_CHARSET);
                if (strDecode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_auth=\\S+&gtm_preview=\\d+(&gtm_debug=x)?$")) {
                    bh.y("Container preview url: " + strDecode);
                    if (strDecode.matches(".*?&gtm_debug=x$")) {
                        this.YQ = a.CONTAINER_DEBUG;
                    } else {
                        this.YQ = a.CONTAINER;
                    }
                    this.YS = h(uri);
                    if (this.YQ == a.CONTAINER || this.YQ == a.CONTAINER_DEBUG) {
                        this.YR = "/r?" + this.YS;
                    }
                    this.WJ = bI(this.YS);
                } else if (!strDecode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_preview=$")) {
                    bh.z("Invalid preview uri: " + strDecode);
                    z = false;
                } else if (bI(uri.getQuery()).equals(this.WJ)) {
                    bh.y("Exit preview mode for container: " + this.WJ);
                    this.YQ = a.NONE;
                    this.YR = null;
                } else {
                    z = false;
                }
            } catch (UnsupportedEncodingException e) {
                z = false;
            }
        }
        return z;
    }

    String getContainerId() {
        return this.WJ;
    }

    a kU() {
        return this.YQ;
    }

    String kV() {
        return this.YR;
    }
}
