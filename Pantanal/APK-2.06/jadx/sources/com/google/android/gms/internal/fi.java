package com.google.android.gms.internal;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import com.google.android.gms.common.GooglePlayServicesUtil;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
public class fi {
    private static final Uri DF = Uri.parse("http://plus.google.com/");
    private static final Uri DG = DF.buildUpon().appendPath("circles").appendPath("find").build();

    public static Intent am(String str) {
        Uri uriFromParts = Uri.fromParts("package", str, null);
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(uriFromParts);
        return intent;
    }

    private static Uri an(String str) {
        return Uri.parse("market://details").buildUpon().appendQueryParameter(TMXConstants.TAG_TILE_ATTRIBUTE_ID, str).build();
    }

    public static Intent ao(String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(an(str));
        intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE);
        intent.addFlags(AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END);
        return intent;
    }

    public static Intent eS() {
        return new Intent("android.settings.DATE_SETTINGS");
    }
}
