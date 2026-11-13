package com.tapjoy.internal;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Base64;
import com.tapjoy.TapjoyConstants;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public final class fu {
    private static final fu q;
    private static fu r;
    private static Handler w;
    private static File x;
    public gd b;
    public Context e;
    public fx f;
    public ft g;
    public gh h;
    public fs i;
    public String j;
    public boolean k;
    public String l;
    public String m;
    public String o;
    private String u;
    private String v;
    public boolean c = false;
    private boolean s = false;
    public String d = null;
    private boolean t = false;
    public boolean n = false;
    public fv p = fv.a((fj) null);
    public final gc a = new gc(this);

    static {
        fu fuVar = new fu();
        q = fuVar;
        r = fuVar;
    }

    public static fu a() {
        return r;
    }

    private fu() {
    }

    final synchronized void b(Context context) {
        if (this.e == null) {
            Context applicationContext = context.getApplicationContext();
            this.e = applicationContext;
            eu.a().a(applicationContext);
            this.f = fx.a(applicationContext);
            File file = new File(c(applicationContext), "events2");
            if (this.i == null) {
                this.i = new fs(file);
            }
            this.g = new ft(this.f, this.i);
            this.h = new gh(this.g);
            this.b = new gd(applicationContext);
            fa.a(new fc(new File(c(applicationContext), "usages"), this.g));
            gp gpVar = gp.a;
            gpVar.b = applicationContext.getApplicationContext();
            gpVar.c = applicationContext.getSharedPreferences("tapjoyCacheDataMMF2E", 0);
            gpVar.d = applicationContext.getSharedPreferences("tapjoyCacheDataMMF2U", 0);
            gpVar.a();
        }
    }

    public final gl.n a(boolean z) {
        if (z) {
            this.f.a();
        }
        return this.f.b();
    }

    public final synchronized void b() {
        if (this.k) {
            fw.b(this.e).e(this.d);
            a((String) null);
        }
    }

    public final synchronized void a(final String str) {
        if (this.k) {
            if (str == null && this.o != null) {
                str = this.o;
            }
            this.o = null;
            if (str != null) {
                gl.n nVarB = this.f.b();
                fr.a("GCM registration id of device {} updated for sender {}: {}", nVarB.c.h(), this.d, str);
                new hb(nVarB, str).a(new cj() { // from class: com.tapjoy.internal.fu.1
                    @Override // com.tapjoy.internal.cj
                    public final void a(ce ceVar) {
                    }

                    @Override // com.tapjoy.internal.cj
                    public final /* synthetic */ void a(ce ceVar, Object obj) {
                        fw fwVarB = fw.b(fu.this.e);
                        String str2 = str;
                        long jCurrentTimeMillis = 0 != 0 ? System.currentTimeMillis() + 0 : 0L;
                        if (!str2.equals(fwVarB.b.b(fwVarB.a))) {
                            new Object[1][0] = str2;
                            return;
                        }
                        fwVarB.b.b(fwVarB.a, true);
                        if (jCurrentTimeMillis != 0) {
                            new Object[1][0] = new Timestamp(jCurrentTimeMillis);
                        }
                        fwVarB.b.a(fwVarB.a, jCurrentTimeMillis);
                    }
                }, ce.a);
            }
        } else if (str != null) {
            this.o = str;
        }
    }

    public static void a(GLSurfaceView gLSurfaceView) {
        if (fr.a(gLSurfaceView, "setGLSurfaceView: The given GLSurfaceView was null")) {
            fm.a(gLSurfaceView);
        }
    }

    public final Set c() {
        return !c("getUserTags") ? new HashSet() : this.f.e();
    }

    /* JADX WARN: Code duplicated, block: B:35:0x008a A[Catch: all -> 0x006b, TRY_LEAVE, TryCatch #0 {, blocks: (B:23:0x0049, B:25:0x004f, B:27:0x0065, B:28:0x006a, B:32:0x006e, B:33:0x0088, B:35:0x008a), top: B:37:0x0049 }] */
    public final void a(Set set) {
        if (c("setUserTags")) {
            if (set != null && !set.isEmpty()) {
                HashSet hashSet = new HashSet();
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    if (str != null) {
                        String strTrim = str.trim();
                        if (!strTrim.isEmpty() && strTrim.length() <= 200) {
                            hashSet.add(strTrim);
                            if (hashSet.size() >= 200) {
                                break;
                            }
                        }
                    }
                }
                set = hashSet;
            }
            fx fxVar = this.f;
            synchronized (fxVar) {
                if (set == null) {
                    fxVar.c.z.c();
                    fxVar.b.y();
                } else if (!set.isEmpty()) {
                    gl.x.a aVarE = gl.x.e();
                    aVarE.e();
                    dh.a.a(set, aVarE.b);
                    gl.x xVarD = aVarE.d();
                    if (xVarD.c()) {
                        fxVar.c.z.a(Base64.encodeToString(xVarD.a(), 2));
                        fxVar.b.y();
                        fxVar.b.a((Iterable) set);
                    } else {
                        throw new dz();
                    }
                } else {
                    fxVar.c.z.c();
                    fxVar.b.y();
                }
            }
        }
    }

    public final synchronized void a(Context context, String str, String str2, String str3, String str4, String str5) {
        boolean z;
        boolean z2;
        synchronized (this) {
            if (!this.k) {
                b(context);
                if (fr.a(this.e != null, "The given context was null")) {
                    if (str4 != null && str4.length() == 24 && str4.matches("[0-9a-f]{24}")) {
                        z = true;
                    } else {
                        fr.b("Invalid App ID: {}", str4);
                        z = false;
                    }
                    if (z) {
                        if (str5 != null && str5.length() == 20 && str5.matches("[0-9A-Za-z\\-_]{20}")) {
                            z2 = true;
                        } else {
                            fr.b("Invalid App Key: {}", str5);
                            z2 = false;
                        }
                        if (z2) {
                            this.l = str;
                            this.m = str2;
                            this.u = str4;
                            this.v = str5;
                            try {
                                ci ciVar = new ci("TapjoySDK " + str2 + " (" + Build.MODEL + "; Android " + Build.VERSION.RELEASE + "; " + Locale.getDefault() + ")", new URL(str3));
                                ce.b = ciVar;
                                ce.a = Executors.newCachedThreadPool();
                                fs fsVar = this.i;
                                fsVar.b = ciVar;
                                fsVar.a();
                                new Object[1][0] = str3;
                                this.k = true;
                                fy fyVar = new fy(d(this.e));
                                if (!(fyVar.b() != null) && fyVar.a()) {
                                    ft ftVar = this.g;
                                    ftVar.a(ftVar.a(gl.i.APP, "install"));
                                }
                                fx fxVar = this.f;
                                if (!cr.c(str4) && !str4.equals(fxVar.c.D.a())) {
                                    fxVar.c.D.a(str4);
                                    fxVar.c.a(false);
                                }
                                b();
                            } catch (MalformedURLException e) {
                                throw new IllegalArgumentException(e);
                            }
                        }
                    }
                }
            }
        }
    }

    public final boolean b(String str) {
        if ((this.k || this.j != null) && this.e != null) {
            return true;
        }
        if (fr.a) {
            fr.b(str + ": Should be called after initializing the SDK");
        }
        return false;
    }

    public final boolean c(String str) {
        if (this.e != null) {
            return true;
        }
        if (fr.a) {
            fr.b(str + ": Should be called after initializing the SDK");
        }
        return false;
    }

    public final boolean d() {
        return this.h != null && this.h.b.get();
    }

    public final boolean e() {
        boolean z;
        gh ghVar = this.h;
        if (ghVar.c != null) {
            ghVar.c.cancel(false);
            ghVar.c = null;
        }
        if (ghVar.b.compareAndSet(false, true)) {
            fr.a("New session started");
            ft ftVar = ghVar.a;
            gl.p pVarD = ftVar.a.d();
            fx fxVar = ftVar.a;
            synchronized (fxVar) {
                int iB = fxVar.c.h.b() + 1;
                fxVar.c.h.a(iB);
                fxVar.b.d(iB);
            }
            gl.c.a aVarA = ftVar.a(gl.i.APP, "bootup");
            ftVar.c = SystemClock.elapsedRealtime();
            if (pVarD != null) {
                if (pVarD == null) {
                    throw new NullPointerException();
                }
                aVarA.l = pVarD;
                aVarA.b |= 65536;
            }
            ftVar.a(aVarA);
            eo.c.notifyObservers();
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            return false;
        }
        gc gcVar = this.a;
        synchronized (gcVar) {
            gcVar.b = null;
        }
        gp.a.a();
        return true;
    }

    final void a(Map map) {
        ft ftVar = this.g;
        gl.c.a aVarA = ftVar.a(gl.i.CAMPAIGN, "impression");
        if (map != null) {
            aVarA.a(bl.a((Object) map));
        }
        ftVar.a(aVarA);
    }

    final void a(Map map, long j) {
        ft ftVar = this.g;
        gl.c.a aVarD = ftVar.a(gl.i.CAMPAIGN, "view").d(j);
        if (map != null) {
            aVarD.a(bl.a((Object) map));
        }
        ftVar.a(aVarD);
    }

    final void a(Map map, String str) {
        ft ftVar = this.g;
        gl.c.a aVarA = ftVar.a(gl.i.CAMPAIGN, "click");
        LinkedHashMap linkedHashMap = new LinkedHashMap(map);
        linkedHashMap.put("region", str);
        aVarA.a(bl.a((Object) linkedHashMap));
        ftVar.a(aVarA);
    }

    public static synchronized void a(Runnable runnable) {
        if (w == null) {
            w = new Handler(Looper.getMainLooper());
        }
        w.post(runnable);
    }

    static synchronized File c(Context context) {
        if (x == null) {
            x = context.getDir("fiverocks", 0);
        }
        return x;
    }

    static File d(Context context) {
        return new File(c(context), "install");
    }

    public static String a(Context context, Intent intent) {
        String strA = f.a(intent);
        if (strA != null) {
            fu fuVar = r;
            fuVar.b(context);
            if (cr.c(fuVar.f.c()) || intent.getBooleanExtra("fiverocks:force", false)) {
                fx fxVar = fuVar.f;
                synchronized (fxVar) {
                    fxVar.c.d.a(strA);
                    fxVar.b.a(strA);
                }
                if (strA.length() > 0) {
                    ft ftVar = fuVar.g;
                    ftVar.a(ftVar.a(gl.i.APP, TapjoyConstants.TJC_REFERRER));
                }
            }
        }
        return strA;
    }

    public static fu a(Context context) {
        fu fuVar = r;
        fuVar.b(context);
        return fuVar;
    }
}
