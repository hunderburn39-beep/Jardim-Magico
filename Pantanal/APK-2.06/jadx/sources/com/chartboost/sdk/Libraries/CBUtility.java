package com.chartboost.sdk.Libraries;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.view.Display;
import android.view.WindowManager;
import com.chartboost.sdk.Chartboost;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public final class CBUtility {
    private CBUtility() {
    }

    public static float a(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int a(int i, Context context) {
        return Math.round(i * a(context));
    }

    public static float a(float f, Context context) {
        return a(context) * f;
    }

    public static e a() {
        boolean z;
        boolean z2 = true;
        Context context = com.chartboost.sdk.i.m;
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        int rotation = defaultDisplay.getRotation();
        if (defaultDisplay.getWidth() == defaultDisplay.getHeight()) {
            z = context.getResources().getConfiguration().orientation != 2;
        } else {
            z = defaultDisplay.getWidth() < defaultDisplay.getHeight();
        }
        if (rotation == 0 || rotation == 2) {
            z2 = z;
        } else if (z) {
            z2 = false;
        }
        if (z2) {
            switch (rotation) {
                case 1:
                    return e.g;
                case 2:
                    return e.PORTRAIT_REVERSE;
                case 3:
                    return e.h;
                default:
                    return e.PORTRAIT;
            }
        }
        switch (rotation) {
            case 1:
                return e.e;
            case 2:
                return e.LANDSCAPE_REVERSE;
            case 3:
                return e.f;
            default:
                return e.LANDSCAPE;
        }
    }

    public static void throwProguardError(Exception ex) {
        if (ex instanceof NoSuchMethodException) {
            CBLogging.b("CBUtility", "Chartboost library error! Have you used proguard on your application? Make sure to add the line '-keep class com.chartboost.sdk.** { *; }' to your proguard config file.");
        } else if (ex != null && ex.getMessage() != null) {
            CBLogging.b("CBUtility", ex.getMessage());
        } else {
            CBLogging.b("CBUtility", "Unknown Proguard error");
        }
    }

    public static String b() {
        Object[] objArr = new Object[3];
        objArr[0] = "Chartboost-Android-SDK";
        objArr[1] = com.chartboost.sdk.i.d == null ? "" : com.chartboost.sdk.i.d;
        objArr[2] = "6.6.0";
        return String.format("%s %s %s", objArr);
    }

    public static Handler c() {
        return com.chartboost.sdk.impl.g.a().a;
    }

    public static boolean d() {
        return f() || g() || h();
    }

    public static String e() {
        SimpleDateFormat simpleDateFormat;
        if (Build.VERSION.SDK_INT >= 18) {
            simpleDateFormat = new SimpleDateFormat("ZZZZ", Locale.US);
        } else {
            simpleDateFormat = new SimpleDateFormat("'GMT'ZZZZ", Locale.US);
        }
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(new Date());
    }

    private static boolean f() {
        String str = Build.TAGS;
        return str != null && str.contains("test-keys");
    }

    private static boolean g() {
        return new File("/system/app/Superuser.apk").exists();
    }

    private static boolean h() {
        for (String str : new String[]{"/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su"}) {
            if (new File(str).exists()) {
                return true;
            }
        }
        return false;
    }

    public static void a(Activity activity, com.chartboost.sdk.Model.a.b bVar, com.chartboost.sdk.Model.c cVar) {
        if (activity != null) {
            if ((bVar == com.chartboost.sdk.Model.a.b.WEB && cVar.u && cVar.y) || (bVar == com.chartboost.sdk.Model.a.b.NATIVE && cVar.e && cVar.h)) {
                e eVarA = a();
                if (eVarA == e.PORTRAIT) {
                    activity.setRequestedOrientation(1);
                    return;
                }
                if (eVarA == e.PORTRAIT_REVERSE) {
                    activity.setRequestedOrientation(9);
                } else if (eVarA == e.LANDSCAPE) {
                    activity.setRequestedOrientation(0);
                } else {
                    activity.setRequestedOrientation(8);
                }
            }
        }
    }

    public static void b(Activity activity, com.chartboost.sdk.Model.a.b bVar, com.chartboost.sdk.Model.c cVar) {
        if (activity != null) {
            if ((bVar == com.chartboost.sdk.Model.a.b.WEB && cVar.u && cVar.y) || (bVar == com.chartboost.sdk.Model.a.b.NATIVE && cVar.e && cVar.h)) {
                activity.setRequestedOrientation(-1);
            }
        }
    }

    @TargetApi(19)
    public static void a(Activity activity) {
        if (activity == null || !com.chartboost.sdk.i.g) {
            if ((activity.getWindow().getAttributes().flags & 1024) != 0) {
                CBLogging.d("CBUtility", "Attempting to show Status and Navigation bars on a fullscreen activity. Please change your Chartboost activity theme to: \"@android:style/Theme.Translucent\"` in your Manifest file");
                return;
            }
            return;
        }
        com.chartboost.sdk.impl.g gVarA = com.chartboost.sdk.impl.g.a();
        if (gVarA.a(11)) {
            int i = 0;
            if (gVarA.a(14)) {
                i = 2;
                if (gVarA.a(16)) {
                    i = 1798;
                    if (gVarA.a(19)) {
                        i = 5894;
                    }
                }
            }
            activity.getWindow().getDecorView().setSystemUiVisibility(i);
        }
    }

    public static boolean a(Chartboost.CBFramework cBFramework) {
        return com.chartboost.sdk.i.d != null && com.chartboost.sdk.i.d == cBFramework;
    }
}
