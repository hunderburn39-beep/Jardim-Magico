package com.google.android.gms.tagmanager;

import android.content.Context;
import com.heyzap.http.AsyncHttpResponseHandler;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/* JADX INFO: loaded from: classes.dex */
class y implements aq {
    private static y XM;
    private static final Object sf = new Object();
    private String XN;
    private String XO;
    private ar XP;
    private cf Xa;

    private y(Context context) {
        this(as.H(context), new cv());
    }

    y(ar arVar, cf cfVar) {
        this.XP = arVar;
        this.Xa = cfVar;
    }

    public static aq F(Context context) {
        y yVar;
        synchronized (sf) {
            if (XM == null) {
                XM = new y(context);
            }
            yVar = XM;
        }
        return yVar;
    }

    @Override // com.google.android.gms.tagmanager.aq
    public boolean bz(String str) {
        if (!this.Xa.cS()) {
            bh.z("Too many urls sent too quickly with the TagManagerSender, rate limiting invoked.");
            return false;
        }
        if (this.XN != null && this.XO != null) {
            try {
                str = this.XN + "?" + this.XO + "=" + URLEncoder.encode(str, AsyncHttpResponseHandler.DEFAULT_CHARSET);
                bh.y("Sending wrapped url hit: " + str);
            } catch (UnsupportedEncodingException e) {
                bh.c("Error wrapping URL for testing.", e);
                return false;
            }
        }
        this.XP.bC(str);
        return true;
    }
}
