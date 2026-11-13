package com.tapjoy.internal;

import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class ft {
    final fx a;
    final fs b;
    long c;
    private int d = 1;
    private final gl.f.a e = gl.f.j();

    ft(fx fxVar, fs fsVar) {
        this.a = fxVar;
        this.b = fsVar;
    }

    public final void a(String str, String str2, double d, String str3, String str4, String str5) {
        int iB;
        double dA;
        fx fxVar = this.a;
        synchronized (fxVar) {
            SharedPreferences.Editor editorA = fxVar.c.a();
            if (str2.equals(fxVar.c.l.a())) {
                iB = fxVar.c.m.b() + 1;
                fxVar.c.m.a(editorA, iB);
                dA = fxVar.c.n.a() + d;
                fxVar.c.n.a(editorA, dA);
                editorA.commit();
            } else {
                fxVar.c.l.a(editorA, str2);
                fxVar.c.m.a(editorA, 1);
                fxVar.c.n.a(editorA, d);
                fxVar.c.o.a(editorA);
                fxVar.c.p.a(editorA);
                editorA.commit();
                fxVar.b.b(str2);
                gl.z.a aVar = fxVar.b;
                aVar.b &= -4097;
                aVar.d = 0L;
                gl.z.a aVar2 = fxVar.b;
                aVar2.b &= -8193;
                aVar2.e = 0.0d;
                iB = 1;
                dA = d;
            }
            fxVar.b.e(iB);
            fxVar.b.a(dA);
        }
        gl.c.a aVarA = a(gl.i.APP, "purchase");
        gl.r.a aVarC = gl.r.C();
        if (str == null) {
            throw new NullPointerException();
        }
        aVarC.b |= 1;
        aVarC.c = str;
        if (str2 != null) {
            if (str2 == null) {
                throw new NullPointerException();
            }
            aVarC.b |= 8;
            aVarC.d = str2;
        }
        aVarC.a(d);
        if (str5 != null) {
            if (str5 == null) {
                throw new NullPointerException();
            }
            aVarC.b |= 1024;
            aVarC.e = str5;
        }
        if (str3 != null) {
            if (str3 == null) {
                throw new NullPointerException();
            }
            aVarC.b |= 4096;
            aVarC.f = str3;
        }
        if (str4 != null) {
            if (str4 == null) {
                throw new NullPointerException();
            }
            aVarC.b |= 8192;
            aVarC.g = str4;
        }
        gl.r rVarD = aVarC.d();
        if (rVarD.c()) {
            aVarA.k = rVarD;
            aVarA.b |= 8192;
            a(aVarA);
            fx fxVar2 = this.a;
            long j = aVarA.e;
            synchronized (fxVar2) {
                SharedPreferences.Editor editorA2 = fxVar2.c.a();
                fxVar2.c.o.a(editorA2, j);
                fxVar2.c.p.a(editorA2, d);
                editorA2.commit();
                fxVar2.b.e(j);
                fxVar2.b.b(d);
            }
            return;
        }
        throw new dz();
    }

    public final void a(String str, String str2, String str3, String str4, Map map) {
        gl.c.a aVarA = a(gl.i.CUSTOM, str2);
        if (str != null) {
            if (str == null) {
                throw new NullPointerException();
            }
            aVarA.b |= 131072;
            aVarA.m = str;
        }
        if (str3 != null) {
            if (str3 == null) {
                throw new NullPointerException();
            }
            aVarA.b |= AccessibilityEventCompat.TYPE_GESTURE_DETECTION_START;
            aVarA.n = str3;
        }
        if (str4 != null) {
            if (str4 == null) {
                throw new NullPointerException();
            }
            aVarA.b |= AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END;
            aVarA.o = str4;
        }
        if (map != null) {
            for (Map.Entry entry : map.entrySet()) {
                aVarA.a(gl.j.h().a((String) entry.getKey()).a(((Long) entry.getValue()).longValue()));
            }
        }
        a(aVarA);
    }

    public final void a(String str, String str2, int i, long j, long j2, Map map) {
        gl.c.a aVarA = a(gl.i.USAGES, str);
        if (str2 == null) {
            throw new NullPointerException();
        }
        aVarA.b |= AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_END;
        aVarA.p = str2;
        aVarA.b(i);
        aVarA.e(j);
        aVarA.f(j2);
        if (map != null) {
            for (Map.Entry entry : map.entrySet()) {
                aVarA.a(gl.j.h().a((String) entry.getKey()).a(((Long) entry.getValue()).longValue()));
            }
        }
        a(aVarA);
    }

    public final gl.c.a a(gl.i iVar, String str) {
        gl.n nVarB = this.a.b();
        gl.c.a aVarL = gl.c.L();
        String str2 = fx.a;
        if (str2 == null) {
            throw new NullPointerException();
        }
        aVarL.b |= 16;
        aVarL.f = str2;
        gl.c.a aVarA = aVarL.a(iVar);
        if (str == null) {
            throw new NullPointerException();
        }
        aVarA.b |= 2;
        aVarA.d = str;
        if (y.c()) {
            aVarA.a(y.b()).b(System.currentTimeMillis());
        } else {
            aVarA.a(System.currentTimeMillis()).c(SystemClock.elapsedRealtime());
        }
        gl.l lVar = nVarB.c;
        if (lVar == null) {
            throw new NullPointerException();
        }
        aVarA.g = lVar;
        aVarA.b |= 128;
        gl.a aVar = nVarB.d;
        if (aVar == null) {
            throw new NullPointerException();
        }
        aVarA.h = aVar;
        aVarA.b |= 256;
        gl.z zVar = nVarB.e;
        if (zVar == null) {
            throw new NullPointerException();
        }
        aVarA.i = zVar;
        aVarA.b |= 512;
        return aVarA;
    }

    public final synchronized void a(gl.c.a aVar) {
        String str;
        String str2;
        if (aVar.c != gl.i.USAGES) {
            int i = this.d;
            this.d = i + 1;
            aVar.a(i);
            if (this.e.c()) {
                gl.f fVarD = this.e.d();
                if (fVarD.c()) {
                    aVar.j = fVarD;
                    aVar.b |= 4096;
                } else {
                    throw new dz();
                }
            }
            this.e.a(aVar.c);
            gl.f.a aVar2 = this.e;
            Object obj = aVar.d;
            if (!(obj instanceof String)) {
                dk dkVar = (dk) obj;
                String strE = dkVar.e();
                if (dkVar.f()) {
                    aVar.d = strE;
                }
                str = strE;
            } else {
                str = (String) obj;
            }
            if (str == null) {
                throw new NullPointerException();
            }
            aVar2.b |= 2;
            aVar2.c = str;
            if ((aVar.b & 131072) == 131072) {
                gl.f.a aVar3 = this.e;
                Object obj2 = aVar.m;
                if (!(obj2 instanceof String)) {
                    dk dkVar2 = (dk) obj2;
                    String strE2 = dkVar2.e();
                    if (dkVar2.f()) {
                        aVar.m = strE2;
                    }
                    str2 = strE2;
                } else {
                    str2 = (String) obj2;
                }
                if (str2 == null) {
                    throw new NullPointerException();
                }
                aVar3.b |= 4;
                aVar3.d = str2;
            } else {
                gl.f.a aVar4 = this.e;
                aVar4.b &= -5;
                aVar4.d = gl.f.d().i();
            }
        }
        fs fsVar = this.b;
        gl.c cVarD = aVar.d();
        if (!cVarD.c()) {
            throw new dz();
        }
        try {
            gg ggVar = fsVar.a;
            synchronized (ggVar.a) {
                try {
                    ggVar.b.add(cVarD);
                } catch (Exception e) {
                    ggVar.a();
                    try {
                        ggVar.b.add(cVarD);
                    } catch (Exception e2) {
                    }
                }
            }
            if (fsVar.b != null) {
                if (fr.a || cVarD.c != gl.i.CUSTOM) {
                    fsVar.a(true);
                } else {
                    fsVar.a(false);
                }
            } else {
                fsVar.a.flush();
            }
        } catch (Exception e3) {
        }
    }
}
