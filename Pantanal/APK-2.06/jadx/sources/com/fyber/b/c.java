package com.fyber.b;

import android.support.annotation.NonNull;
import com.fyber.Fyber;
import com.fyber.ads.a.c;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.StringUtils;
import com.fyber.utils.s;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: AdRequesterNetworkOperation.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class c<V extends com.fyber.ads.a.c<V>> extends g<List<V>> {
    protected boolean a;
    protected final String b;

    @NonNull
    protected abstract V a(String str, String str2);

    protected c(@NonNull s sVar, @NonNull String str) {
        super(sVar);
        this.a = false;
        this.b = str;
        this.c = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.fyber.b.g
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public List<V> a(com.fyber.utils.i iVar) throws Exception {
        String strB;
        int iC;
        LinkedList linkedList = new LinkedList();
        int iB = iVar.b();
        if (iB < 200 || iB > 299) {
            throw new com.fyber.exceptions.a("server_" + iB, "ERROR - Status code returned by the server - " + iB);
        }
        String strC = iVar.c();
        if (StringUtils.notNullNorEmpty(strC)) {
            if (!this.a) {
                strB = null;
                iC = 0;
            } else {
                com.fyber.utils.h hVarA = Fyber.getConfigs().a();
                strB = hVarA.b();
                iC = hVarA.c();
            }
            FyberLogger.d(a_(), "Parsing ads response\n" + strC);
            try {
                JSONArray jSONArray = new JSONObject(strC).getJSONArray("ads");
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    com.fyber.ads.a.c cVarA = a(jSONObject.getString("provider_type"), jSONObject.getString("ad_id"));
                    a(cVarA, jSONObject.optJSONObject("tracking_params"));
                    JSONArray jSONArrayNames = jSONObject.names();
                    for (int i2 = 0; i2 < jSONArrayNames.length(); i2++) {
                        String string = jSONArrayNames.getString(i2);
                        if (!string.equals("ad_id") && !string.equals("provider_type") && !string.equals("tracking_params") && !jSONObject.isNull(string)) {
                            cVarA.a(string, jSONObject.get(string));
                        }
                    }
                    if (this.a) {
                        if (!cVarA.c().containsKey("orientation")) {
                            cVarA.a("orientation", (Object) strB);
                        }
                        cVarA.a("rotation", (Object) Integer.toString(iC));
                    }
                    linkedList.add(cVarA);
                }
            } catch (JSONException e) {
                FyberLogger.e(a_(), e.getMessage(), e);
                throw new com.fyber.exceptions.a("json_parsing", e.getMessage());
            }
        }
        return linkedList;
    }

    private void a(V v, JSONObject jSONObject) {
        if (jSONObject != null) {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                try {
                    Object obj = jSONObject.get(next);
                    if (obj != null) {
                        v.a(next, obj.toString());
                    }
                } catch (JSONException e) {
                    FyberLogger.e(a_(), e.getMessage());
                }
            }
        }
    }
}
