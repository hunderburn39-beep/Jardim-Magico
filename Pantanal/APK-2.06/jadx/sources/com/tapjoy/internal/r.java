package com.tapjoy.internal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.tapjoy.TJAdUnitConstants;
import java.sql.Timestamp;

/* JADX INFO: loaded from: classes.dex */
public abstract class r extends s {
    public final Context a;
    public final t b;
    private boolean c = false;

    public r(Context context, t tVar) {
        this.a = context.getApplicationContext();
        this.b = tVar;
    }

    private boolean b() {
        boolean zE = this.b.e(this.a);
        if (zE) {
            long jF = this.b.f(this.a);
            if (jF != 0 && System.currentTimeMillis() > jF) {
                new Object[1][0] = new Timestamp(jF);
                return false;
            }
        }
        return zE;
    }

    public final boolean a() {
        String strA = this.b.a(this.a);
        if (cr.c(strA)) {
            return false;
        }
        a(strA);
        return true;
    }

    public final void a(String str) {
        String strB = this.b.b(this.a);
        if (strB.length() == 0) {
            e(str);
            return;
        }
        String strA = this.b.a(this.a);
        if (!str.equals(strA)) {
            Object[] objArr = {strA, str, strB};
            e(str);
            return;
        }
        int iD = this.b.d(this.a);
        int iA = ae.a(this.a);
        if (iD != Integer.MIN_VALUE && iD != iA) {
            Object[] objArr2 = {Integer.valueOf(iD), Integer.valueOf(iA), strB};
            e(str);
        } else if (this.b.c(this.a)) {
            new Object[1][0] = strB;
            e(str);
        } else if (!b()) {
            Object[] objArr3 = {str, strB};
            a(this.a, strB);
        } else {
            Object[] objArr4 = {str, strB};
        }
    }

    private void e(String str) {
        this.b.a(this.a, str);
        this.b.a(this.a, true);
        if ((this.c || !b(this.a, str)) && c(this.a, str)) {
        }
    }

    private static boolean b(Context context, String str) {
        try {
            int iB = ae.b(context.getPackageManager(), GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE);
            if (iB >= 3159130) {
                Object[] objArr = {Integer.valueOf(iB), str};
                Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
                intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE);
                b(context, intent);
                intent.putExtra("sender", str);
                return context.startService(intent) != null;
            }
            new Object[1][0] = Integer.valueOf(iB);
            return false;
        } catch (RuntimeException e) {
        }
    }

    private static boolean c(Context context, String str) {
        try {
            Object[] objArr = {Integer.valueOf(ae.b(context.getPackageManager(), "com.google.android.gsf")), str};
            Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
            intent.setPackage("com.google.android.gsf");
            b(context, intent);
            intent.putExtra("sender", str);
            return context.startService(intent) != null;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private static void b(Context context, Intent intent) {
        intent.putExtra("app", PendingIntent.getBroadcast(context, 0, new Intent(), 0));
    }

    public final void a(Context context) {
        this.b.b(context, GamesStatusCodes.STATUS_ACHIEVEMENT_UNLOCK_FAILURE);
    }

    public final boolean a(Intent intent) {
        boolean zD;
        String action = intent.getAction();
        if ("com.google.android.c2dm.intent.REGISTRATION".equals(action)) {
            String stringExtra = intent.getStringExtra("registration_id");
            String stringExtra2 = intent.getStringExtra("unregistered");
            String stringExtra3 = intent.getStringExtra(TJAdUnitConstants.String.VIDEO_ERROR);
            if (stringExtra != null) {
                if (stringExtra.length() > 0) {
                    if (stringExtra.startsWith("|")) {
                        return false;
                    }
                    f(stringExtra);
                    return false;
                }
                if (stringExtra2 == null && stringExtra3 == null && !this.c) {
                    this.c = true;
                    a();
                    return true;
                }
            }
            if (stringExtra2 == null) {
                if (stringExtra3 == null) {
                    return false;
                }
                if (GoogleCloudMessaging.ERROR_SERVICE_NOT_AVAILABLE.equals(stringExtra3)) {
                    zD = c(stringExtra3);
                } else {
                    if (!this.c) {
                        c(stringExtra3);
                        this.c = true;
                        a();
                    } else {
                        zD = d(stringExtra3);
                    }
                    return true;
                }
                if (zD) {
                    int iG = this.b.g(this.a);
                    new Object[1][0] = Integer.valueOf(iG);
                    Intent intent2 = new Intent("com.google.android.gcm.intent.RETRY");
                    intent2.setPackage(this.a.getPackageName());
                    ((AlarmManager) this.a.getSystemService("alarm")).set(3, SystemClock.elapsedRealtime() + ((long) iG), PendingIntent.getBroadcast(this.a, 0, intent2, 0));
                    if (iG < 3600000) {
                        this.b.b(this.a, iG * 2);
                    }
                }
                return true;
            }
            String strB = this.b.b(this.a);
            a(this.a);
            this.b.b(this.a, false);
            this.b.b(this.a, "");
            this.b.a(this.a, true);
            b(strB);
            return false;
        }
        if ("com.google.android.c2dm.intent.RECEIVE".equals(action)) {
            String stringExtra4 = intent.getStringExtra("message_type");
            if (stringExtra4 != null) {
                if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(stringExtra4)) {
                    String stringExtra5 = intent.getStringExtra("total_deleted");
                    if (stringExtra5 == null) {
                        return false;
                    }
                    try {
                        int i = Integer.parseInt(stringExtra5);
                        new Object[1][0] = Integer.valueOf(i);
                        a(i);
                        return false;
                    } catch (NumberFormatException e) {
                        new Object[1][0] = stringExtra5;
                        return false;
                    }
                }
                new Object[1][0] = stringExtra4;
                return false;
            }
            return a(this.a, intent);
        }
        if (!"com.google.android.gcm.intent.RETRY".equals(action)) {
            new Object[1][0] = action;
            return false;
        }
        String str = intent.getPackage();
        if (str == null || !str.equals(this.a.getPackageName())) {
            new Object[1][0] = str;
            return false;
        }
        a();
        return true;
    }

    private synchronized void f(String str) {
        a(this.a);
        this.b.a(this.a, false);
        this.b.a(this.a, ae.a(this.a));
        if (!str.equals(this.b.b(this.a))) {
            new Object[1][0] = str;
            this.b.b(this.a, false);
            this.b.b(this.a, str);
            a(this.a, str);
        } else if (!b()) {
            new Object[1][0] = str;
            a(this.a, str);
        } else {
            new Object[1][0] = str;
        }
    }
}
