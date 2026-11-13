package com.chartboost.sdk.impl;

import android.content.SharedPreferences;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Model.CBError;
import com.tapjoy.TJAdUnitConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ak {
    public final com.chartboost.sdk.Libraries.c a;
    final ai b;
    final ay c;
    public final AtomicReference<com.chartboost.sdk.Model.c> d;
    final SharedPreferences e;
    public final com.chartboost.sdk.Tracking.a f;
    final af g;
    private final List<Runnable> h = new ArrayList();

    public ak(af afVar, ai aiVar, ay ayVar, AtomicReference<com.chartboost.sdk.Model.c> atomicReference, SharedPreferences sharedPreferences) {
        this.a = (com.chartboost.sdk.Libraries.c) com.chartboost.sdk.g.a().a(new com.chartboost.sdk.Libraries.c(this, sharedPreferences));
        this.g = afVar;
        this.b = aiVar;
        this.c = ayVar;
        this.d = atomicReference;
        this.e = sharedPreferences;
        this.f = (com.chartboost.sdk.Tracking.a) com.chartboost.sdk.g.a().a(new com.chartboost.sdk.Tracking.a(this, atomicReference, sharedPreferences));
    }

    void a(aj ajVar, ah ahVar, CBError cBError, boolean z) {
        if (ajVar != null) {
            com.chartboost.sdk.Libraries.d.a[] aVarArr = new com.chartboost.sdk.Libraries.d.a[5];
            aVarArr[0] = com.chartboost.sdk.Libraries.d.a("endpoint", ajVar.d());
            aVarArr[1] = com.chartboost.sdk.Libraries.d.a("statuscode", ahVar == null ? "None" : Integer.valueOf(ahVar.b()));
            aVarArr[2] = com.chartboost.sdk.Libraries.d.a(TJAdUnitConstants.String.VIDEO_ERROR, cBError == null ? "None" : cBError.a());
            aVarArr[3] = com.chartboost.sdk.Libraries.d.a("errorDescription", cBError == null ? "None" : cBError.b());
            aVarArr[4] = com.chartboost.sdk.Libraries.d.a("retryCount", (Object) 0);
            this.f.a("request_manager", "request", z ? "success" : "failure", (String) null, (String) null, (String) null, com.chartboost.sdk.Libraries.d.a(aVarArr));
        }
    }

    protected void a(aj ajVar, aj.a aVar) {
        if (ajVar != null) {
            if (!this.b.b()) {
                CBError cBError = new CBError(CBError.a.INTERNET_UNAVAILABLE, "Internet Unavailable");
                if (!ajVar.e()) {
                    a(ajVar, null, cBError, false);
                    if (aVar != null) {
                        CBLogging.b("Network failure", String.format("request %s failed with error : %s", ajVar.d(), cBError.b()));
                        aVar.a((JSONObject) com.chartboost.sdk.g.a().a(new JSONObject()), ajVar, cBError);
                        return;
                    }
                    return;
                }
                return;
            }
            this.g.a(new c(ac.a.POST, String.format("%s%s", "https://live.chartboost.com", ajVar.b()), ajVar));
        }
    }

    private class c extends ac<b> {
        private final aj i;

        public c(ac.a aVar, String str, aj ajVar) {
            super(aVar, str, null, null);
            this.i = ajVar;
        }

        @Override // com.chartboost.sdk.impl.ac
        public aq a() {
            this.i.a();
            String str = com.chartboost.sdk.i.k;
            String strB = com.chartboost.sdk.Libraries.b.b(com.chartboost.sdk.Libraries.b.a(String.format(Locale.US, "%s %s\n%s\n%s", this.b, this.i.b(), com.chartboost.sdk.i.l, this.i.c()).getBytes()));
            HashMap map = new HashMap();
            map.put("Accept", "application/json");
            map.put("X-Chartboost-Client", CBUtility.b());
            map.put("X-Chartboost-API", "6.6.0");
            map.put("X-Chartboost-App", str);
            map.put("X-Chartboost-Signature", strB);
            return new aq(map, (this.i.f() == null ? "" : this.i.f().toString()).getBytes(), "application/json");
        }

        @Override // com.chartboost.sdk.impl.ac
        public ac.b b() {
            return this.i.h();
        }

        @Override // com.chartboost.sdk.impl.ac
        public ae<b> a(ah ahVar) {
            JSONObject jSONObjectA;
            Exception e;
            CBError cBError;
            JSONObject jSONObject = null;
            jSONObject = null;
            cBError = null;
            CBError cBError2 = null;
            int iB = ahVar.b();
            if (iB > 300 && iB < 200) {
                CBLogging.b("CBRequestManager", "Request " + this.i.d() + " failed. Response code: " + iB);
                cBError = new CBError(CBError.a.NETWORK_FAILURE, "Request failed. Response code: " + iB + " is not valid ");
            } else {
                try {
                    byte[] bArrA = ahVar.a();
                    String str = bArrA != null ? new String(bArrA) : null;
                    if (str != null) {
                        jSONObjectA = g.a().a(str);
                        try {
                            com.chartboost.sdk.Libraries.f.a aVarG = this.i.g();
                            CBLogging.c("CBRequestManager", "Request " + this.i.d() + " succeeded. Response code: " + iB + ", body: " + jSONObjectA.toString(4));
                            if (jSONObjectA.optInt("status") == 404) {
                                cBError2 = new CBError(CBError.a.HTTP_NOT_FOUND, "404 error from server");
                            } else {
                                StringBuilder sb = new StringBuilder();
                                if (aVarG != null && !aVarG.a(jSONObjectA, sb)) {
                                    cBError2 = new CBError(CBError.a.UNEXPECTED_RESPONSE, "Json response failed validation");
                                    CBLogging.b("CBRequestManager", "Json response failed validation: " + sb.toString());
                                }
                            }
                            cBError = cBError2;
                            jSONObject = jSONObjectA;
                        } catch (Exception e2) {
                            e = e2;
                            CBError cBError3 = new CBError(CBError.a.MISCELLANEOUS, e.getLocalizedMessage());
                            com.chartboost.sdk.Tracking.a.a(getClass(), "parseServerResponse", e);
                            jSONObject = jSONObjectA;
                            cBError = cBError3;
                        }
                    } else {
                        cBError = new CBError(CBError.a.INVALID_RESPONSE, "Response is not a valid json object");
                    }
                } catch (Exception e3) {
                    jSONObjectA = null;
                    e = e3;
                }
            }
            if (jSONObject != null && cBError == null) {
                return ae.a(new b(jSONObject, ahVar));
            }
            return ae.a((Exception) new a(cBError));
        }

        @Override // com.chartboost.sdk.impl.ac
        public void a(b bVar) {
            if (this.i.i() != null && bVar != null) {
                this.i.i().a(bVar.a, this.i);
            }
            if (!this.i.e()) {
                ak.this.a(this.i, bVar.b, null, true);
            }
        }

        @Override // com.chartboost.sdk.impl.ac
        public void a(ad adVar) {
            CBError cBError;
            if (adVar != null) {
                if (adVar.b instanceof a) {
                    cBError = ((a) adVar.b).a;
                } else if (adVar.b != null && (adVar.b.getCause() instanceof a)) {
                    cBError = ((a) adVar.b.getCause()).a;
                } else {
                    cBError = new CBError(CBError.a.NETWORK_FAILURE, adVar.a());
                }
                JSONObject jSONObjectA = null;
                try {
                    if (adVar.a != null && adVar.a.a() != null && adVar.a.a().length > 0) {
                        jSONObjectA = g.a().a(new String(adVar.a.a()));
                    }
                } catch (Exception e) {
                    CBLogging.d("CBRequestManager", "unable to read error json", e);
                }
                if (jSONObjectA == null) {
                    jSONObjectA = (JSONObject) com.chartboost.sdk.g.a().a(new JSONObject());
                }
                if (adVar.a != null && adVar.a.b() == 200) {
                    a(new b(jSONObjectA, adVar.a));
                    return;
                }
                if (this.i.i() != null) {
                    this.i.i().a(jSONObjectA, this.i, cBError);
                }
                if (!this.i.e()) {
                    ak.this.a(this.i, adVar.a, cBError, false);
                }
            }
        }
    }

    public static class a extends RuntimeException {
        final CBError a;

        public a(CBError cBError) {
            this.a = cBError;
        }
    }

    public static class b {
        final JSONObject a;
        final ah b;

        public b(JSONObject jSONObject, ah ahVar) {
            this.a = jSONObject;
            this.b = ahVar;
        }
    }

    public Map<String, String> a() {
        HashMap map = new HashMap();
        String string = Integer.toString(this.b.a());
        map.put("X-Chartboost-App", com.chartboost.sdk.i.k);
        map.put("X-Chartboost-Client", CBUtility.b());
        map.put("X-Chartboost-Reachability", string);
        return map;
    }
}
