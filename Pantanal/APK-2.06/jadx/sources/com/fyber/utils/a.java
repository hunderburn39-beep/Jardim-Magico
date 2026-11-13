package com.fyber.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.WindowManager;
import com.fyber.Fyber;

/* JADX INFO: compiled from: AbstractClickthroughBrowserListener.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class a implements com.fyber.c.d.d.b {
    protected abstract String a();

    protected abstract void a(com.fyber.c.d.d dVar, com.fyber.c.a.a aVar, Uri uri);

    @Override // com.fyber.c.d.d.b
    public final boolean a(final com.fyber.c.d.d dVar, com.fyber.c.a.a aVar, String str) {
        if (u.a(str)) {
            return false;
        }
        Uri uri = Uri.parse(str);
        if (l.a(dVar.getContext(), "android.intent.action.VIEW", uri)) {
            a(dVar, aVar, uri);
        } else {
            a(dVar.getContext(), Fyber.Settings.UIStringIdentifier.RV_REDIRECT_ERROR, "Keep watching", new DialogInterface.OnClickListener() { // from class: com.fyber.utils.a.1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    dVar.c();
                }
            }, "Close Video", new DialogInterface.OnClickListener() { // from class: com.fyber.utils.a.2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    dVar.a("redirect_fail", "Redirection Error");
                }
            });
        }
        return true;
    }

    protected final void a(Context context, Fyber.Settings.UIStringIdentifier uIStringIdentifier, String str, DialogInterface.OnClickListener onClickListener, String str2, DialogInterface.OnClickListener onClickListener2) {
        String strA = r.a(Fyber.Settings.UIStringIdentifier.RV_REDIRECT_DIALOG_TITLE);
        String strA2 = r.a(uIStringIdentifier);
        try {
            new AlertDialog.Builder(context).setTitle(strA).setCancelable(false).setMessage(strA2).setPositiveButton(str, onClickListener).setNegativeButton(str2, onClickListener2).create().show();
        } catch (WindowManager.BadTokenException e) {
            FyberLogger.e(a(), "Couldn't show error dialog. Not displayed error message is: " + strA2, e);
        }
    }
}
