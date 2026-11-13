package com.fyber.ads.interstitials.b;

import android.app.Activity;
import android.content.Intent;
import com.fyber.ads.interstitials.InterstitialActivity;
import com.fyber.ads.interstitials.InterstitialAd;
import com.fyber.utils.FyberLogger;
import com.google.android.gms.drive.DriveFile;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: compiled from: InterstitialClient.java */
/* JADX INFO: loaded from: classes.dex */
public final class b {
    private static final b a = new b();
    private AtomicReference<com.fyber.ads.a.b> b = new AtomicReference<>(com.fyber.ads.a.b.READY_TO_CHECK_OFFERS);
    private a c;

    public static boolean a(com.fyber.ads.a.b bVar) {
        a.b.getAndSet(bVar);
        return true;
    }

    public static com.fyber.ads.a.b a() {
        return a.b.get();
    }

    public static void a(a aVar) {
        a.c = aVar;
    }

    public static void a(InterstitialAd interstitialAd, Activity activity) {
        if (!a(interstitialAd)) {
            if (a.c != null) {
                a.c.a("It is not possible to show Interstitials at this moment");
            } else {
                FyberLogger.w("InterstitialClient", "There was an issue with a missing internal ad");
            }
        }
        Intent intent = new Intent(activity, (Class<?>) InterstitialActivity.class);
        intent.setFlags(DriveFile.MODE_READ_ONLY);
        activity.getApplicationContext().startActivity(intent);
    }

    public static boolean a(InterstitialAd interstitialAd) {
        return a.c != null && a.c.i().equals(interstitialAd);
    }

    public static a b() {
        return a.c;
    }
}
