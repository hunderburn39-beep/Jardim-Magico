package org.anddev.andengine.util;

import android.app.Dialog;

/* JADX INFO: loaded from: classes.dex */
public class DialogUtils {
    public static void keepScreenOn(Dialog pDialog) {
        pDialog.getWindow().addFlags(128);
    }
}
