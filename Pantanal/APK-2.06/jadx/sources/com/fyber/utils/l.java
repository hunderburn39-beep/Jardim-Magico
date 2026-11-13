package com.fyber.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/* JADX INFO: compiled from: IntentHelper.java */
/* JADX INFO: loaded from: classes.dex */
public final class l {
    public static boolean a(Context context, String str, Uri uri) {
        Intent intent = new Intent(str);
        if (uri != null) {
            intent.setData(uri);
        }
        return context.getPackageManager().queryIntentActivities(intent, 65536).size() > 0;
    }
}
