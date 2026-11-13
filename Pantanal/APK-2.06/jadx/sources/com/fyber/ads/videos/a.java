package com.fyber.ads.videos;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import com.fyber.Fyber;
import com.fyber.utils.StringUtils;

/* JADX INFO: compiled from: RewardedVideoClickthroughBrowserListener.java */
/* JADX INFO: loaded from: classes.dex */
public final class a extends com.fyber.utils.a {
    @Override // com.fyber.utils.a
    protected final void a(final com.fyber.c.d.d dVar, final com.fyber.c.a.a aVar, final Uri uri) {
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.fyber.ads.videos.a.1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dVar.getContext().startActivity(new Intent("android.intent.action.VIEW", uri));
            }
        };
        DialogInterface.OnClickListener onClickListener2 = new DialogInterface.OnClickListener() { // from class: com.fyber.ads.videos.a.2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (aVar != null) {
                    aVar.invalidate();
                } else {
                    dVar.c();
                }
            }
        };
        String string = uri.toString();
        if (StringUtils.notNullNorEmpty(string) && string.startsWith("market://")) {
            a(dVar.getContext(), Fyber.Settings.UIStringIdentifier.RV_REDIRECT_DIALOG_MESSAGE_MARKET, "Yes", onClickListener, "No", onClickListener2);
        } else {
            a(dVar.getContext(), Fyber.Settings.UIStringIdentifier.RV_REDIRECT_DIALOG_MESSAGE_DEFAULT, "Yes", onClickListener, "No", onClickListener2);
        }
    }

    @Override // com.fyber.utils.a
    protected final String a() {
        return "RewardedVideoClickthroughBrowserListener";
    }
}
