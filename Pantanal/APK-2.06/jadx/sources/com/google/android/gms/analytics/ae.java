package com.google.android.gms.analytics;

import android.content.Context;
import android.util.DisplayMetrics;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
class ae implements m {
    private static Object sf = new Object();
    private static ae vH;
    private final Context mContext;

    protected ae(Context context) {
        this.mContext = context;
    }

    public static ae cZ() {
        ae aeVar;
        synchronized (sf) {
            aeVar = vH;
        }
        return aeVar;
    }

    public static void n(Context context) {
        synchronized (sf) {
            if (vH == null) {
                vH = new ae(context);
            }
        }
    }

    public boolean C(String str) {
        return "&sr".equals(str);
    }

    protected String da() {
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels + TMXConstants.TAG_OBJECT_ATTRIBUTE_X + displayMetrics.heightPixels;
    }

    @Override // com.google.android.gms.analytics.m
    public String getValue(String field) {
        if (field != null && field.equals("&sr")) {
            return da();
        }
        return null;
    }
}
