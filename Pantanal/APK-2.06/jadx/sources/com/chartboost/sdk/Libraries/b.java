package com.chartboost.sdk.Libraries;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    private b() {
    }

    public static synchronized byte[] a(byte[] bArr) {
        byte[] bArrDigest = null;
        synchronized (b.class) {
            if (bArr != null) {
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
                    messageDigest.update(bArr);
                    bArrDigest = messageDigest.digest();
                } catch (NoSuchAlgorithmException e) {
                    com.chartboost.sdk.Tracking.a.a(b.class, "sha1", e);
                } catch (Exception e2) {
                    com.chartboost.sdk.Tracking.a.a(b.class, "sha1", e2);
                }
            }
        }
        return bArrDigest;
    }

    public static String b(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return String.format(Locale.US, "%0" + (bArr.length << 1) + TMXConstants.TAG_OBJECT_ATTRIBUTE_X, new BigInteger(1, bArr));
    }
}
