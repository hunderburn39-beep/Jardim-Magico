package com.tapjoy;

import android.content.Context;
import android.content.Intent;
import com.heyzap.http.AsyncHttpResponseHandler;
import com.tapjoy.internal.fu;
import com.tapjoy.internal.l;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/* JADX INFO: loaded from: classes.dex */
public class InstallReferrerReceiver extends l {
    @Override // com.tapjoy.internal.l, android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String strA = fu.a(context, intent);
        int iForward = forward(context, intent);
        if (intent.getBooleanExtra("fiverocks:verify", false) && isOrderedBroadcast()) {
            setResultCode(iForward + 1);
            if (strA != null) {
                try {
                    setResultData("http://play.google.com/store/apps/details?id=" + context.getPackageName() + "&referrer=" + URLEncoder.encode(strA, AsyncHttpResponseHandler.DEFAULT_CHARSET));
                } catch (UnsupportedEncodingException e) {
                }
            }
        }
    }
}
