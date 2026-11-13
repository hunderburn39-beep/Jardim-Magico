package com.google.android.gms.internal;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public final class bz {
    public static boolean a(Context context, cb cbVar, ci ciVar) {
        if (cbVar == null) {
            dw.z("No intent data for launcher overlay.");
            return false;
        }
        Intent intent = new Intent();
        if (TextUtils.isEmpty(cbVar.nO)) {
            dw.z("Open GMSG did not contain a URL.");
            return false;
        }
        if (TextUtils.isEmpty(cbVar.mimeType)) {
            intent.setData(Uri.parse(cbVar.nO));
        } else {
            intent.setDataAndType(Uri.parse(cbVar.nO), cbVar.mimeType);
        }
        intent.setAction("android.intent.action.VIEW");
        if (!TextUtils.isEmpty(cbVar.packageName)) {
            intent.setPackage(cbVar.packageName);
        }
        if (!TextUtils.isEmpty(cbVar.nP)) {
            String[] strArrSplit = cbVar.nP.split("/", 2);
            if (strArrSplit.length < 2) {
                dw.z("Could not parse component name from open GMSG: " + cbVar.nP);
                return false;
            }
            intent.setClassName(strArrSplit[0], strArrSplit[1]);
        }
        try {
            dw.y("Launching an intent: " + intent);
            context.startActivity(intent);
            ciVar.U();
            return true;
        } catch (ActivityNotFoundException e) {
            dw.z(e.getMessage());
            return false;
        }
    }
}
