package com.tapjoy.internal;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import com.heyzap.http.AsyncHttpClient;
import com.tapjoy.TapjoyConstants;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public final class fx {
    public static final String a = UUID.randomUUID().toString();
    private static fx d;
    public final gl.z.a b;
    final ge c;
    private final gl.l.a e;
    private final gl.a.C0148a f;
    private final Context g;

    public static synchronized fx a(Context context) {
        if (d == null) {
            d = new fx(context, ge.a(context));
        }
        return d;
    }

    private fx(Context context, ge geVar) {
        String strEncodeToString;
        gi.a();
        this.e = gl.l.J();
        this.f = gl.a.n();
        this.b = gl.z.N();
        gl.l.a aVarC = this.e.c("11.9.0/Android");
        aVarC.b |= 16;
        aVarC.g = "Android";
        String str = Build.VERSION.RELEASE;
        if (str == null) {
            throw new NullPointerException();
        }
        aVarC.b |= 32;
        aVarC.h = str;
        String str2 = Build.MANUFACTURER;
        if (str2 == null) {
            throw new NullPointerException();
        }
        aVarC.b |= 4;
        aVarC.e = str2;
        String str3 = Build.MODEL;
        if (str3 == null) {
            throw new NullPointerException();
        }
        aVarC.b |= 8;
        aVarC.f = str3;
        aVarC.a(Locale.getDefault().toString()).b(TimeZone.getDefault().getID());
        Context applicationContext = context.getApplicationContext();
        this.g = applicationContext;
        String string = Settings.Secure.getString(applicationContext.getContentResolver(), TapjoyConstants.TJC_ANDROID_ID);
        String strB = !"9774d56d682e549c".equals(string) ? cr.b(string) : null;
        if (strB == null) {
            File fileA = w.a(applicationContext);
            strB = aa.a(new File(fu.c(applicationContext), "deviceid"), fileA != null ? new File(fileA, ".io.5rocks") : null);
        }
        gl.l.a aVar = this.e;
        if (strB == null) {
            throw new NullPointerException();
        }
        aVar.b |= 2;
        aVar.d = strB;
        String strA = ab.a(applicationContext);
        if (strA != null) {
            gl.l.a aVar2 = this.e;
            String lowerCase = strA.replace(":", "").toLowerCase(Locale.US);
            if (lowerCase == null) {
                throw new NullPointerException();
            }
            aVar2.b |= 1;
            aVar2.c = lowerCase;
        }
        PackageManager packageManager = applicationContext.getPackageManager();
        TelephonyManager telephonyManager = (TelephonyManager) applicationContext.getSystemService("phone");
        if (telephonyManager != null) {
            String simCountryIso = telephonyManager.getSimCountryIso();
            if (!cr.c(simCountryIso)) {
                gl.l.a aVar3 = this.e;
                String upperCase = simCountryIso.toUpperCase(Locale.US);
                if (upperCase == null) {
                    throw new NullPointerException();
                }
                aVar3.b |= 16384;
                aVar3.k = upperCase;
            }
            String networkCountryIso = telephonyManager.getNetworkCountryIso();
            if (!cr.c(networkCountryIso)) {
                gl.l.a aVar4 = this.e;
                String upperCase2 = networkCountryIso.toUpperCase(Locale.US);
                if (upperCase2 == null) {
                    throw new NullPointerException();
                }
                aVar4.b |= 32768;
                aVar4.l = upperCase2;
            }
            if (packageManager.checkPermission("android.permission.READ_PHONE_STATE", applicationContext.getPackageName()) == 0) {
                try {
                    String deviceId = telephonyManager.getDeviceId();
                    if (!cr.c(deviceId)) {
                        gl.l.a aVar5 = this.e;
                        if (deviceId == null) {
                            throw new NullPointerException();
                        }
                        aVar5.b |= 65536;
                        aVar5.m = deviceId;
                    }
                } catch (SecurityException e) {
                } catch (RuntimeException e2) {
                }
            }
        }
        String packageName = applicationContext.getPackageName();
        gl.l.a aVar6 = this.e;
        if (packageName == null) {
            throw new NullPointerException();
        }
        aVar6.b |= 2048;
        aVar6.i = packageName;
        gl.l.a aVar7 = this.e;
        Signature[] signatureArrE = ae.e(packageManager, packageName);
        if (signatureArrE == null || signatureArrE.length <= 0) {
            strEncodeToString = null;
        } else {
            strEncodeToString = Base64.encodeToString(cl.a(signatureArrE[0].toByteArray()), 2);
        }
        String strA2 = cr.a(strEncodeToString);
        if (strA2 == null) {
            throw new NullPointerException();
        }
        aVar7.b |= 4096;
        aVar7.j = strA2;
        gl.a.C0148a c0148a = this.f;
        String strA3 = ae.a(packageManager, packageName);
        if (strA3 == null) {
            throw new NullPointerException();
        }
        c0148a.b |= 1;
        c0148a.c = strA3;
        this.f.a(ae.b(packageManager, packageName));
        String installerPackageName = packageManager.getInstallerPackageName(packageName);
        if (!cr.c(installerPackageName)) {
            gl.a.C0148a c0148a2 = this.f;
            if (installerPackageName == null) {
                throw new NullPointerException();
            }
            c0148a2.b |= 8;
            c0148a2.e = installerPackageName;
        }
        String strA4 = a(packageManager, packageName);
        if (!cr.c(strA4)) {
            gl.a.C0148a c0148a3 = this.f;
            if (strA4 == null) {
                throw new NullPointerException();
            }
            c0148a3.b |= 16;
            c0148a3.f = strA4;
        }
        a();
        this.c = geVar;
        String strA5 = this.c.c.a();
        if (strA5 != null && strA5.length() > 0) {
            this.e.c(strA5 + " 11.9.0/Android");
        }
        String strB2 = this.c.b();
        if (strB2 != null) {
            this.b.a(strB2);
        }
        gl.z.a aVar8 = this.b;
        ge geVar2 = this.c;
        long jC = geVar2.b.getLong("it", 0L);
        if (jC == 0) {
            Context context2 = geVar2.a;
            jC = ae.c(context2.getPackageManager(), context2.getPackageName());
            if (jC == 0) {
                jC = fu.d(geVar2.a).lastModified();
                if (jC == 0) {
                    Context context3 = geVar2.a;
                    jC = new File(ae.d(context3.getPackageManager(), context3.getPackageName())).lastModified();
                    if (jC == 0) {
                        jC = System.currentTimeMillis();
                    }
                }
            }
            geVar2.b.edit().putLong("it", jC).commit();
        }
        aVar8.a(jC);
        int iB = this.c.f.b();
        this.b.a(a(7, iB));
        this.b.b(a(30, iB));
        int iB2 = this.c.h.b();
        if (iB2 > 0) {
            this.b.d(iB2);
        }
        long jA = this.c.i.a();
        if (jA > 0) {
            this.b.b(jA);
        }
        long jA2 = this.c.j.a();
        if (jA2 > 0) {
            this.b.c(jA2);
        }
        long jA3 = this.c.k.a();
        if (jA3 > 0) {
            this.b.d(jA3);
        }
        String strA6 = this.c.l.a();
        if (strA6 != null) {
            this.b.b(strA6);
        }
        int iB3 = this.c.m.b();
        if (iB3 > 0) {
            this.b.e(iB3);
        }
        double dA = this.c.n.a();
        if (dA != 0.0d) {
            this.b.a(dA);
        }
        long jA4 = this.c.o.a();
        if (jA4 > 0) {
            this.b.e(jA4);
        }
        double dA2 = this.c.p.a();
        if (dA2 != 0.0d) {
            this.b.b(dA2);
        }
        String strA7 = this.c.g.a();
        if (strA7 != null) {
            try {
                gl.u uVarA = gl.u.a(Base64.decode(strA7, 2));
                gl.z.a aVar9 = this.b;
                aVar9.c = Collections.emptyList();
                aVar9.b &= -17;
                gl.z.a aVar10 = this.b;
                List list = uVarA.c;
                aVar10.e();
                dh.a.a(list, aVar10.c);
            } catch (IOException e3) {
                this.c.g.c();
            } catch (IllegalArgumentException e4) {
                this.c.g.c();
            }
        }
        String strA8 = this.c.q.a();
        if (strA8 != null) {
            this.f.a(strA8);
        } else {
            this.f.f();
        }
        String strA9 = this.c.r.a();
        if (strA9 != null) {
            this.b.d(strA9);
        } else {
            this.b.j();
        }
        int iIntValue = this.c.s.a().intValue();
        if (iIntValue != -1) {
            this.b.f(iIntValue);
        } else {
            this.b.l();
        }
        int iIntValue2 = this.c.t.a().intValue();
        if (iIntValue2 != -1) {
            this.b.g(iIntValue2);
        } else {
            this.b.n();
        }
        String strA10 = this.c.u.a();
        if (strA10 != null) {
            this.b.e(strA10);
        } else {
            this.b.p();
        }
        String strA11 = this.c.v.a();
        if (strA11 != null) {
            this.b.f(strA11);
        } else {
            this.b.r();
        }
        String strA12 = this.c.w.a();
        if (strA12 != null) {
            this.b.g(strA12);
        } else {
            this.b.t();
        }
        String strA13 = this.c.x.a();
        if (strA13 != null) {
            this.b.h(strA13);
        } else {
            this.b.v();
        }
        String strA14 = this.c.y.a();
        if (strA14 != null) {
            this.b.i(strA14);
        } else {
            this.b.x();
        }
        String strA15 = this.c.z.a();
        if (strA15 != null) {
            try {
                gl.x xVarA = gl.x.a(Base64.decode(strA15, 2));
                this.b.y();
                this.b.a((Iterable) xVarA.c);
            } catch (IOException e5) {
                this.c.z.c();
            } catch (IllegalArgumentException e6) {
                this.c.z.c();
            }
        }
        String strA16 = this.c.A.a();
        boolean zBooleanValue = this.c.B.a().booleanValue();
        if (strA16 != null) {
            this.b.c(strA16);
            this.b.a(zBooleanValue);
        } else {
            this.b.g();
            this.b.h();
        }
        this.b.b(this.c.C.b());
        new Thread(new Runnable() { // from class: com.tapjoy.internal.fx.1
            @Override // java.lang.Runnable
            public final void run() {
                ey eyVar = new ey();
                boolean zA = eyVar.a(fx.this.g);
                synchronized (fx.this) {
                    try {
                        if (!zA) {
                            fx.this.b.g();
                            fx.this.b.h();
                            fx.this.c.A.a(null);
                            fx.this.c.B.a(false);
                            gi.a(null, false);
                        } else {
                            String strA17 = cr.a(eyVar.a);
                            boolean z = eyVar.b;
                            String strA18 = fx.this.c.A.a();
                            fx.this.b.c(strA17);
                            fx.this.b.a(z);
                            fx.this.c.A.a(strA17);
                            fx.this.c.B.a(z);
                            gi.a(strA17, z);
                            if (!cr.c(strA18) && !strA17.equals(strA18)) {
                                fx.this.c.a(false);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }).start();
    }

    private static String a(PackageManager packageManager, String str) {
        Object obj;
        try {
            Bundle bundle = packageManager.getApplicationInfo(str, 128).metaData;
            if (bundle != null && (obj = bundle.get("com.tapjoy.appstore")) != null) {
                return obj.toString().trim();
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return null;
    }

    final void a() {
        Window window;
        synchronized (this) {
            try {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                ((WindowManager) this.g.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
                Activity activityA = fm.a();
                if (activityA != null && (window = activityA.getWindow()) != null) {
                    int i = displayMetrics.heightPixels;
                    Rect rect = new Rect();
                    window.getDecorView().getWindowVisibleDisplayFrame(rect);
                    displayMetrics.heightPixels = i - rect.top;
                }
                this.e.a(displayMetrics.densityDpi);
                this.e.b(displayMetrics.widthPixels);
                this.e.c(displayMetrics.heightPixels);
            } catch (Exception e) {
            }
        }
    }

    public final gl.n b() {
        gl.n nVarD;
        synchronized (this) {
            this.e.a(Locale.getDefault().toString()).b(TimeZone.getDefault().getID());
            long jCurrentTimeMillis = System.currentTimeMillis() - 259200000;
            boolean z = false;
            for (int iF = this.b.f() - 1; iF >= 0; iF--) {
                if (this.b.c(iF).c <= jCurrentTimeMillis) {
                    gl.z.a aVar = this.b;
                    aVar.e();
                    aVar.c.remove(iF);
                    z = true;
                }
            }
            if (z) {
                f();
            }
            gl.n.a aVarH = gl.n.h();
            gl.l lVarD = this.e.d();
            if (lVarD.c()) {
                aVarH.c = lVarD;
                aVarH.b |= 1;
                gl.a aVarD = this.f.d();
                if (aVarD.c()) {
                    aVarH.d = aVarD;
                    aVarH.b |= 2;
                    gl.z zVarD = this.b.d();
                    if (zVarD.c()) {
                        aVarH.e = zVarD;
                        aVarH.b |= 4;
                        nVarD = aVarH.d();
                        if (!nVarD.c()) {
                            throw new dz();
                        }
                    } else {
                        throw new dz();
                    }
                } else {
                    throw new dz();
                }
            } else {
                throw new dz();
            }
        }
        return nVarD;
    }

    final String c() {
        String strA;
        synchronized (this) {
            strA = this.c.d.a();
        }
        return strA;
    }

    /* JADX WARN: Code duplicated, block: B:27:0x012d A[Catch: all -> 0x0102, LOOP:0: B:25:0x0125->B:27:0x012d, LOOP_END, TryCatch #0 {, blocks: (B:4:0x0005, B:7:0x0031, B:8:0x004b, B:10:0x005b, B:11:0x005f, B:13:0x0061, B:14:0x0098, B:15:0x009b, B:16:0x00a6, B:19:0x00ad, B:33:0x015f, B:34:0x0163, B:23:0x0105, B:25:0x0125, B:27:0x012d, B:28:0x0135, B:37:0x016c), top: B:39:0x0005 }] */
    /* JADX WARN: Code duplicated, block: B:30:0x0157  */
    /* JADX WARN: Code duplicated, block: B:31:0x015a  */
    public final gl.p d() {
        gl.p pVarD;
        Calendar calendar;
        long timeInMillis;
        int i;
        int i2;
        int i3;
        gl.p.a aVarK;
        int i4 = 1;
        synchronized (this) {
            Calendar calendar2 = Calendar.getInstance();
            int i5 = (calendar2.get(1) * AsyncHttpClient.DEFAULT_SOCKET_TIMEOUT) + (calendar2.get(2) * 100) + 100 + calendar2.get(5);
            int iIntValue = this.c.e.a().intValue();
            if (iIntValue != i5) {
                if (iIntValue == 0) {
                    this.b.a(1);
                    this.b.b(1);
                    aVarK = gl.p.k();
                    aVarK.a("fq7_0_1");
                    aVarK.b("fq30_0_1");
                } else {
                    int iIntValue2 = this.c.f.a().intValue();
                    int iA = a(7, iIntValue2);
                    int iA2 = a(30, iIntValue2);
                    Calendar calendar3 = Calendar.getInstance();
                    calendar3.set(iIntValue / AsyncHttpClient.DEFAULT_SOCKET_TIMEOUT, ((iIntValue / 100) % 100) - 1, iIntValue % 100);
                    int iSignum = Integer.signum(calendar2.get(1) - calendar3.get(1));
                    switch (iSignum) {
                        case -1:
                            Calendar calendar4 = (Calendar) calendar3.clone();
                            calendar4.set(calendar2.get(1), calendar2.get(2), calendar2.get(5));
                            calendar = calendar4;
                            timeInMillis = calendar3.getTimeInMillis();
                            i = 0;
                            while (calendar.getTimeInMillis() < timeInMillis) {
                                calendar.add(5, 1);
                                i++;
                            }
                            if (iSignum > 0) {
                                i2 = -i;
                            } else {
                                i2 = i;
                            }
                            break;
                        case 0:
                        default:
                            i2 = calendar2.get(6) - calendar3.get(6);
                            break;
                        case 1:
                            Calendar calendar5 = (Calendar) calendar2.clone();
                            calendar5.set(calendar3.get(1), calendar3.get(2), calendar3.get(5));
                            calendar = calendar5;
                            timeInMillis = calendar2.getTimeInMillis();
                            i = 0;
                            while (calendar.getTimeInMillis() < timeInMillis) {
                                calendar.add(5, 1);
                                i++;
                            }
                            if (iSignum > 0) {
                                i2 = -i;
                            } else {
                                i2 = i;
                            }
                            break;
                    }
                    if (Math.abs(i2) >= 30) {
                        i3 = 0;
                    } else if (i2 >= 0) {
                        i3 = iIntValue2 << i2;
                    } else {
                        i3 = iIntValue2 >> (-i2);
                    }
                    i4 = i3 | 1;
                    int iA3 = a(7, i4);
                    int iA4 = a(30, i4);
                    this.b.a(iA3);
                    this.b.b(iA4);
                    aVarK = gl.p.k();
                    aVarK.a("fq7_" + iA + "_" + iA3);
                    aVarK.b("fq30_" + iA2 + "_" + iA4);
                }
                this.c.e.a(i5);
                this.c.f.a(i4);
                pVarD = aVarK != null ? aVarK.d() : null;
            } else {
                pVarD = null;
            }
        }
        return pVarD;
    }

    private static int a(int i, int i2) {
        return Integer.bitCount(((1 << i) - 1) & i2);
    }

    final boolean a(String str, long j, boolean z) {
        synchronized (this) {
            int iF = this.b.f();
            for (int i = 0; i < iF; i++) {
                gl.t tVarC = this.b.c(i);
                if (tVarC.f().equals(str)) {
                    if (!z) {
                        return false;
                    }
                    gl.z.a aVar = this.b;
                    gl.t.a aVarA = gl.t.a(tVarC).a(j);
                    aVar.e();
                    aVar.c.set(i, aVarA.d());
                    f();
                    return true;
                }
            }
            gl.z.a aVar2 = this.b;
            gl.t.a aVarI = gl.t.i();
            if (str == null) {
                throw new NullPointerException();
            }
            aVarI.b |= 1;
            aVarI.c = str;
            gl.t.a aVarA2 = aVarI.a(j);
            aVar2.e();
            aVar2.c.add(aVarA2.d());
            f();
            return true;
        }
    }

    private void f() {
        gl.u.a aVarE = gl.u.e();
        List listUnmodifiableList = Collections.unmodifiableList(this.b.c);
        aVarE.e();
        dh.a.a(listUnmodifiableList, aVarE.b);
        gl.u uVarD = aVarE.d();
        if (uVarD.c()) {
            this.c.g.a(Base64.encodeToString(uVarD.a(), 2));
            return;
        }
        throw new dz();
    }

    /* JADX WARN: Code duplicated, block: B:15:0x002f  */
    public final boolean a(String str) {
        boolean zE;
        String str2;
        synchronized (this) {
            this.c.q.a(str);
            if (str != null) {
                if (this.f.e()) {
                    gl.a.C0148a c0148a = this.f;
                    Object obj = c0148a.d;
                    if (!(obj instanceof String)) {
                        dk dkVar = (dk) obj;
                        String strE = dkVar.e();
                        if (dkVar.f()) {
                            c0148a.d = strE;
                        }
                        str2 = strE;
                    } else {
                        str2 = (String) obj;
                    }
                    if (str2.equals(str)) {
                        zE = false;
                    } else {
                        zE = true;
                    }
                } else {
                    zE = true;
                }
                this.f.a(str);
            } else {
                zE = this.f.e();
                this.f.f();
            }
        }
        return zE;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x002f  */
    public final boolean b(String str) {
        boolean zI;
        String str2;
        synchronized (this) {
            this.c.r.a(str);
            if (str != null) {
                if (this.b.i()) {
                    gl.z.a aVar = this.b;
                    Object obj = aVar.f;
                    if (!(obj instanceof String)) {
                        dk dkVar = (dk) obj;
                        String strE = dkVar.e();
                        if (dkVar.f()) {
                            aVar.f = strE;
                        }
                        str2 = strE;
                    } else {
                        str2 = (String) obj;
                    }
                    if (str2.equals(str)) {
                        zI = false;
                    } else {
                        zI = true;
                    }
                } else {
                    zI = true;
                }
                this.b.d(str);
            } else {
                zI = this.b.i();
                this.b.j();
            }
        }
        return zI;
    }

    public final boolean a(int i) {
        boolean zK;
        synchronized (this) {
            this.c.s.a(i);
            if (i != -1) {
                zK = (this.b.k() && this.b.g == i) ? false : true;
                this.b.f(i);
            } else {
                zK = this.b.k();
                this.b.l();
            }
        }
        return zK;
    }

    public final boolean b(int i) {
        boolean zM;
        synchronized (this) {
            this.c.t.a(i);
            if (i != -1) {
                zM = (this.b.m() && this.b.h == i) ? false : true;
                this.b.g(i);
            } else {
                zM = this.b.m();
                this.b.n();
            }
        }
        return zM;
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0037  */
    /* JADX WARN: Code duplicated, block: B:40:0x0080  */
    /* JADX WARN: Code duplicated, block: B:57:0x00c7  */
    /* JADX WARN: Code duplicated, block: B:74:0x010f  */
    /* JADX WARN: Code duplicated, block: B:91:0x0157  */
    public final boolean a(int i, String str) {
        boolean zW;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        synchronized (this) {
            try {
                switch (i) {
                    case 1:
                        this.c.u.a(str);
                        if (str != null) {
                            if (this.b.o()) {
                                gl.z.a aVar = this.b;
                                Object obj = aVar.i;
                                if (!(obj instanceof String)) {
                                    dk dkVar = (dk) obj;
                                    String strE = dkVar.e();
                                    if (dkVar.f()) {
                                        aVar.i = strE;
                                    }
                                    str6 = strE;
                                } else {
                                    str6 = (String) obj;
                                }
                                if (str6.equals(str)) {
                                    zW = false;
                                } else {
                                    zW = true;
                                }
                            } else {
                                zW = true;
                            }
                            this.b.e(str);
                        } else {
                            zW = this.b.o();
                            this.b.p();
                        }
                        break;
                    case 2:
                        this.c.v.a(str);
                        if (str != null) {
                            if (this.b.q()) {
                                gl.z.a aVar2 = this.b;
                                Object obj2 = aVar2.j;
                                if (!(obj2 instanceof String)) {
                                    dk dkVar2 = (dk) obj2;
                                    String strE2 = dkVar2.e();
                                    if (dkVar2.f()) {
                                        aVar2.j = strE2;
                                    }
                                    str5 = strE2;
                                } else {
                                    str5 = (String) obj2;
                                }
                                if (str5.equals(str)) {
                                    zW = false;
                                } else {
                                    zW = true;
                                }
                            } else {
                                zW = true;
                            }
                            this.b.f(str);
                        } else {
                            zW = this.b.q();
                            this.b.r();
                        }
                        break;
                    case 3:
                        this.c.w.a(str);
                        if (str != null) {
                            if (this.b.s()) {
                                gl.z.a aVar3 = this.b;
                                Object obj3 = aVar3.k;
                                if (!(obj3 instanceof String)) {
                                    dk dkVar3 = (dk) obj3;
                                    String strE3 = dkVar3.e();
                                    if (dkVar3.f()) {
                                        aVar3.k = strE3;
                                    }
                                    str4 = strE3;
                                } else {
                                    str4 = (String) obj3;
                                }
                                if (str4.equals(str)) {
                                    zW = false;
                                } else {
                                    zW = true;
                                }
                            } else {
                                zW = true;
                            }
                            this.b.g(str);
                        } else {
                            zW = this.b.s();
                            this.b.t();
                        }
                        break;
                    case 4:
                        this.c.x.a(str);
                        if (str != null) {
                            if (this.b.u()) {
                                gl.z.a aVar4 = this.b;
                                Object obj4 = aVar4.l;
                                if (!(obj4 instanceof String)) {
                                    dk dkVar4 = (dk) obj4;
                                    String strE4 = dkVar4.e();
                                    if (dkVar4.f()) {
                                        aVar4.l = strE4;
                                    }
                                    str3 = strE4;
                                } else {
                                    str3 = (String) obj4;
                                }
                                if (str3.equals(str)) {
                                    zW = false;
                                } else {
                                    zW = true;
                                }
                            } else {
                                zW = true;
                            }
                            this.b.h(str);
                        } else {
                            zW = this.b.u();
                            this.b.v();
                        }
                        break;
                    case 5:
                        this.c.y.a(str);
                        if (str != null) {
                            if (this.b.w()) {
                                gl.z.a aVar5 = this.b;
                                Object obj5 = aVar5.m;
                                if (!(obj5 instanceof String)) {
                                    dk dkVar5 = (dk) obj5;
                                    String strE5 = dkVar5.e();
                                    if (dkVar5.f()) {
                                        aVar5.m = strE5;
                                    }
                                    str2 = strE5;
                                } else {
                                    str2 = (String) obj5;
                                }
                                if (str2.equals(str)) {
                                    zW = false;
                                } else {
                                    zW = true;
                                }
                            } else {
                                zW = true;
                            }
                            this.b.i(str);
                        } else {
                            zW = this.b.w();
                            this.b.x();
                        }
                        break;
                    default:
                        zW = false;
                        break;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return zW;
    }

    public final Set e() {
        HashSet hashSet;
        synchronized (this) {
            hashSet = new HashSet(this.b.n.b());
        }
        return hashSet;
    }

    public final boolean c(String str) {
        synchronized (this) {
            for (int iF = this.b.f() - 1; iF >= 0; iF--) {
                gl.t tVarC = this.b.c(iF);
                if (tVarC.f().equals(str)) {
                    gl.t tVarD = gl.t.a(tVarC).b(System.currentTimeMillis()).d();
                    gl.z.a aVar = this.b;
                    if (tVarD == null) {
                        throw new NullPointerException();
                    }
                    aVar.e();
                    aVar.c.set(iF, tVarD);
                    f();
                    return true;
                }
            }
            return false;
        }
    }

    public final boolean a(boolean z) {
        boolean z2;
        synchronized (this) {
            this.c.C.a(z);
            z2 = z != this.b.o;
            this.b.b(z);
        }
        return z2;
    }
}
