package com.tapjoy.internal;

import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class gk {
    public static String a(gl.l lVar) {
        bl blVarB = new bl().c().a("sdk").b(lVar.C()).a("os_name").b(lVar.n()).a("os_ver").b(lVar.p()).a("device_id").b(lVar.h()).a("device_maker").b(lVar.j()).a("device_model").b(lVar.l()).a(TapjoyConstants.TJC_PACKAGE_ID).b(lVar.y()).a(TapjoyConstants.TJC_PACKAGE_SIGN).b(lVar.A()).a(TapjoyConstants.TJC_DEVICE_DISPLAY_DENSITY).a(lVar.c).a(TapjoyConstants.TJC_DEVICE_DISPLAY_WIDTH).a(lVar.d).a(TapjoyConstants.TJC_DEVICE_DISPLAY_HEIGHT).a(lVar.e).a("locale").b(lVar.u()).a(TapjoyConstants.TJC_DEVICE_TIMEZONE).b(lVar.w());
        if (lVar.e()) {
            blVarB.a("mac").b(lVar.f());
        }
        if (lVar.D()) {
            blVarB.a(TapjoyConstants.TJC_DEVICE_COUNTRY_SIM).b(lVar.E());
        }
        if (lVar.F()) {
            blVarB.a("country_net").b(lVar.G());
        }
        if (lVar.H()) {
            blVarB.a("imei").b(lVar.I());
        }
        return blVarB.d().toString();
    }

    public static String a(gl.a aVar) {
        bl blVarC = new bl().c();
        if (aVar.e()) {
            blVarC.a(TapjoyConstants.TJC_PACKAGE_VERSION).b(aVar.f());
        }
        if (aVar.g()) {
            blVarC.a(TapjoyConstants.TJC_PACKAGE_REVISION).a(aVar.c);
        }
        if (aVar.h()) {
            blVarC.a("data_ver").b(aVar.i());
        }
        if (aVar.j()) {
            blVarC.a(TapjoyConstants.TJC_INSTALLER).b(aVar.k());
        }
        if (aVar.l()) {
            blVarC.a(TapjoyConstants.TJC_STORE).b(aVar.m());
        }
        return blVarC.d().toString();
    }

    public static String a(gl.z zVar) {
        return a(zVar, null);
    }

    private static String a(gl.z zVar, gl.c cVar) {
        String strB;
        bl blVarC = new bl().c();
        if (zVar.e()) {
            blVarC.a(TapjoyConstants.TJC_INSTALLED).a(zVar.c);
        }
        if (zVar.f()) {
            blVarC.a(TapjoyConstants.TJC_REFERRER).b(zVar.g());
        }
        if (zVar.u()) {
            blVarC.a("idfa").b(zVar.v());
            if (zVar.w() && zVar.o) {
                blVarC.a("idfa_optout").a(1L);
            }
        } else if (cVar != null && cVar.j() && fx.a.equals(cVar.k()) && (strB = gi.b()) != null) {
            blVarC.a("idfa").b(strB);
            if (gi.c()) {
                blVarC.a("idfa_optout").a(1L);
            }
        }
        if (zVar.h()) {
            blVarC.a(TapjoyConstants.TJC_USER_WEEKLY_FREQUENCY).a(Math.max(zVar.d, 1));
        }
        if (zVar.i()) {
            blVarC.a(TapjoyConstants.TJC_USER_MONTHLY_FREQUENCY).a(Math.max(zVar.e, 1));
        }
        if (zVar.j() > 0) {
            ArrayList arrayList = new ArrayList(zVar.j());
            for (gl.t tVar : zVar.f) {
                if (tVar.h()) {
                    arrayList.add(tVar.f());
                }
            }
            if (!arrayList.isEmpty()) {
                blVarC.a("push").a();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    blVarC.b((String) it.next());
                }
                blVarC.b();
            }
        }
        blVarC.a("session").c();
        if (zVar.k()) {
            blVarC.a("total_count").a(zVar.g);
        }
        if (zVar.l()) {
            blVarC.a("total_length").a(zVar.h);
        }
        if (zVar.m()) {
            blVarC.a("last_at").a(zVar.i);
        }
        if (zVar.n()) {
            blVarC.a("last_length").a(zVar.j);
        }
        blVarC.d();
        blVarC.a("purchase").c();
        if (zVar.o()) {
            blVarC.a("currency").b(zVar.p());
        }
        if (zVar.q()) {
            blVarC.a("total_count").a(zVar.k);
        }
        if (zVar.r()) {
            blVarC.a("total_price").a(zVar.l);
        }
        if (zVar.s()) {
            blVarC.a("last_at").a(zVar.m);
        }
        if (zVar.t()) {
            blVarC.a("last_price").a(zVar.n);
        }
        blVarC.d();
        if (zVar.x()) {
            blVarC.a("user_id").b(zVar.y());
        }
        if (zVar.z()) {
            blVarC.a(TapjoyConstants.TJC_USER_LEVEL).a(zVar.p);
        }
        if (zVar.A()) {
            blVarC.a(TapjoyConstants.TJC_USER_FRIEND_COUNT).a(zVar.q);
        }
        if (zVar.B()) {
            blVarC.a(TapjoyConstants.TJC_USER_VARIABLE_1).b(zVar.C());
        }
        if (zVar.D()) {
            blVarC.a(TapjoyConstants.TJC_USER_VARIABLE_2).b(zVar.E());
        }
        if (zVar.F()) {
            blVarC.a(TapjoyConstants.TJC_USER_VARIABLE_3).b(zVar.G());
        }
        if (zVar.H()) {
            blVarC.a(TapjoyConstants.TJC_USER_VARIABLE_4).b(zVar.I());
        }
        if (zVar.J()) {
            blVarC.a(TapjoyConstants.TJC_USER_VARIABLE_5).b(zVar.K());
        }
        if (zVar.L() > 0) {
            blVarC.a("tags").a((Collection) zVar.r);
        }
        if (zVar.s) {
            blVarC.a("push_optout").a(1L);
        }
        return blVarC.d().toString();
    }

    private static String a(gl.c cVar, boolean z, boolean z2, boolean z3) {
        bl blVarB = new bl().c().a("type").b(a(cVar.c)).a("name").b(cVar.g());
        blVarB.a("time");
        if (cVar.i()) {
            blVarB.a(cVar.d);
            blVarB.a("systime").a(cVar.e);
        } else if (y.c() && cVar.j() && cVar.l() && fx.a.equals(cVar.k())) {
            blVarB.a(y.a(cVar.f));
            blVarB.a("systime").a(cVar.d);
        } else {
            blVarB.a(cVar.d);
        }
        if (cVar.m()) {
            blVarB.a("duration").a(cVar.g);
        }
        if (!z && cVar.n()) {
            blVarB.a(TJAdUnitConstants.String.VIDEO_INFO).a((bp) new bq(a(cVar.h)));
        }
        if (!z2 && cVar.o()) {
            blVarB.a("app").a((bp) new bq(a(cVar.i)));
        }
        if (!z3 && cVar.p()) {
            blVarB.a("user").a((bp) new bq(a(cVar.j, cVar)));
        }
        if (cVar.r()) {
            blVarB.a("event_seq").a(cVar.l);
        }
        if (cVar.s()) {
            bl blVarA = blVarB.a("event_prev");
            gl.f fVar = cVar.m;
            bl blVarB2 = new bl().c().a("type").b(a(fVar.c)).a("name").b(fVar.g());
            if (fVar.h()) {
                blVarB2.a("category").b(fVar.i());
            }
            blVarA.a((bp) new bq(blVarB2.d().toString()));
        }
        if (cVar.t()) {
            bl blVarA2 = blVarB.a("purchase");
            gl.r rVar = cVar.n;
            bl blVarB3 = new bl().c().a("product_id").b(rVar.f());
            if (rVar.g()) {
                blVarB3.a("product_quantity").a(rVar.c);
            }
            if (rVar.h()) {
                blVarB3.a("product_price").a(rVar.d);
            }
            if (rVar.i()) {
                blVarB3.a("product_price_currency").b(rVar.j());
            }
            if (rVar.w()) {
                blVarB3.a("currency_price").b(rVar.x());
            }
            if (rVar.k()) {
                blVarB3.a("product_type").b(rVar.l());
            }
            if (rVar.m()) {
                blVarB3.a("product_title").b(rVar.n());
            }
            if (rVar.o()) {
                blVarB3.a("product_description").b(rVar.p());
            }
            if (rVar.q()) {
                blVarB3.a("transaction_id").b(rVar.r());
            }
            if (rVar.s()) {
                blVarB3.a("transaction_state").a(rVar.e);
            }
            if (rVar.t()) {
                blVarB3.a("transaction_date").a(rVar.f);
            }
            if (rVar.u()) {
                blVarB3.a("campaign_id").b(rVar.v());
            }
            if (rVar.y()) {
                blVarB3.a("receipt").b(rVar.z());
            }
            if (rVar.A()) {
                blVarB3.a("signature").b(rVar.B());
            }
            blVarA2.a((bp) new bq(blVarB3.d().toString()));
        }
        if (cVar.u()) {
            blVarB.a("exception").b(cVar.v());
        }
        try {
            if (cVar.y()) {
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                if (cVar.w()) {
                    br.b(cVar.x()).a(linkedHashMap);
                }
                gl.p pVar = cVar.o;
                if (pVar.e()) {
                    linkedHashMap.put("fq7_change", pVar.f());
                }
                if (pVar.g()) {
                    linkedHashMap.put("fq30_change", pVar.h());
                }
                if (pVar.i()) {
                    linkedHashMap.put(TJAdUnitConstants.PARAM_PUSH_ID, pVar.j());
                }
                blVarB.a("meta").a((Map) linkedHashMap);
            } else if (cVar.w()) {
                blVarB.a("meta").a((bp) new bq(cVar.x()));
            }
        } catch (IOException e) {
        }
        if (cVar.G()) {
            blVarB.a("dimensions").a((bp) new bq(cVar.H()));
        }
        if (cVar.I()) {
            blVarB.a("count").a(cVar.q);
        }
        if (cVar.J()) {
            blVarB.a("first_time").a(cVar.r);
        }
        if (cVar.K()) {
            blVarB.a("last_time").a(cVar.s);
        }
        if (cVar.z()) {
            blVarB.a("category").b(cVar.A());
        }
        if (cVar.B()) {
            blVarB.a("p1").b(cVar.C());
        }
        if (cVar.D()) {
            blVarB.a("p2").b(cVar.E());
        }
        if (cVar.F() > 0) {
            blVarB.a("values").c();
            for (gl.j jVar : cVar.p) {
                blVarB.a(jVar.f()).a(jVar.c);
            }
            blVarB.d();
        }
        return blVarB.d().toString();
    }

    public static String a(gl.d dVar) {
        gl.l lVar;
        boolean z;
        gl.a aVar;
        boolean z2;
        gl.z zVar;
        boolean z3;
        gl.z zVar2 = null;
        bl blVarA = new bl().a();
        gl.a aVar2 = null;
        gl.l lVar2 = null;
        for (gl.c cVar : dVar.c) {
            if (lVar2 == null || (cVar.n() && !lVar2.equals(cVar.h))) {
                lVar = cVar.h;
                z = false;
            } else {
                z = true;
                lVar = lVar2;
            }
            if (aVar2 == null || (cVar.o() && !aVar2.equals(cVar.i))) {
                aVar = cVar.i;
                z2 = false;
            } else {
                z2 = true;
                aVar = aVar2;
            }
            if (zVar2 == null || (cVar.p() && !zVar2.equals(cVar.j))) {
                zVar = cVar.j;
                z3 = false;
            } else {
                zVar = zVar2;
                z3 = true;
            }
            blVarA.a((bp) new bq(a(cVar, z, z2, z3)));
            zVar2 = zVar;
            aVar2 = aVar;
            lVar2 = lVar;
        }
        return blVarA.b().toString();
    }

    private static String a(gl.i iVar) {
        switch (iVar) {
            case APP:
                return "app";
            case CAMPAIGN:
                return "campaign";
            case CUSTOM:
                return "custom";
            case USAGES:
                return "usages";
            default:
                throw new RuntimeException();
        }
    }
}
