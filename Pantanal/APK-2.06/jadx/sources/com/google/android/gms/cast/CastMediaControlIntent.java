package com.google.android.gms.cast;

import android.text.TextUtils;
import java.util.Collection;

/* JADX INFO: loaded from: classes.dex */
public final class CastMediaControlIntent {
    public static final String ACTION_SYNC_STATUS = "com.google.android.gms.cast.ACTION_SYNC_STATUS";
    public static final String CATEGORY_CAST = "com.google.android.gms.cast.CATEGORY_CAST";
    public static final String DEFAULT_MEDIA_RECEIVER_APPLICATION_ID = "CC1AD845";
    public static final int ERROR_CODE_REQUEST_FAILED = 1;
    public static final int ERROR_CODE_SESSION_START_FAILED = 2;
    public static final int ERROR_CODE_TEMPORARILY_DISCONNECTED = 3;
    public static final String EXTRA_CAST_APPLICATION_ID = "com.google.android.gms.cast.EXTRA_CAST_APPLICATION_ID";
    public static final String EXTRA_CAST_RELAUNCH_APPLICATION = "com.google.android.gms.cast.EXTRA_CAST_RELAUNCH_APPLICATION";
    public static final String EXTRA_CAST_STOP_APPLICATION_WHEN_SESSION_ENDS = "com.google.android.gms.cast.EXTRA_CAST_STOP_APPLICATION_WHEN_SESSION_ENDS";
    public static final String EXTRA_CUSTOM_DATA = "com.google.android.gms.cast.EXTRA_CUSTOM_DATA";
    public static final String EXTRA_DEBUG_LOGGING_ENABLED = "com.google.android.gms.cast.EXTRA_DEBUG_LOGGING_ENABLED";
    public static final String EXTRA_ERROR_CODE = "com.google.android.gms.cast.EXTRA_ERROR_CODE";

    private CastMediaControlIntent() {
    }

    private static String a(String str, String str2, Collection<String> collection) throws IllegalArgumentException {
        StringBuffer stringBuffer = new StringBuffer(str);
        if (str2 != null) {
            if (!str2.matches("[A-F0-9]+")) {
                throw new IllegalArgumentException("Invalid application ID: " + str2);
            }
            stringBuffer.append("/").append(str2);
        }
        if (collection != null) {
            if (collection.isEmpty()) {
                throw new IllegalArgumentException("Must specify at least one namespace");
            }
            for (String str3 : collection) {
                if (TextUtils.isEmpty(str3) || str3.trim().equals("")) {
                    throw new IllegalArgumentException("Namespaces must not be null or empty");
                }
            }
            if (str2 == null) {
                stringBuffer.append("/");
            }
            stringBuffer.append("/").append(TextUtils.join(",", collection));
        }
        return stringBuffer.toString();
    }

    public static String categoryForCast(String applicationId) throws IllegalArgumentException {
        if (applicationId == null) {
            throw new IllegalArgumentException("applicationId cannot be null");
        }
        return a(CATEGORY_CAST, applicationId, null);
    }

    public static String categoryForCast(String applicationId, Collection<String> namespaces) {
        if (applicationId == null) {
            throw new IllegalArgumentException("applicationId cannot be null");
        }
        if (namespaces == null) {
            throw new IllegalArgumentException("namespaces cannot be null");
        }
        return a(CATEGORY_CAST, applicationId, namespaces);
    }

    public static String categoryForCast(Collection<String> namespaces) throws IllegalArgumentException {
        if (namespaces == null) {
            throw new IllegalArgumentException("namespaces cannot be null");
        }
        return a(CATEGORY_CAST, null, namespaces);
    }

    public static String categoryForRemotePlayback() {
        return a("com.google.android.gms.cast.CATEGORY_CAST_REMOTE_PLAYBACK", null, null);
    }

    public static String categoryForRemotePlayback(String applicationId) throws IllegalArgumentException {
        if (TextUtils.isEmpty(applicationId)) {
            throw new IllegalArgumentException("applicationId cannot be null or empty");
        }
        return a("com.google.android.gms.cast.CATEGORY_CAST_REMOTE_PLAYBACK", applicationId, null);
    }
}
