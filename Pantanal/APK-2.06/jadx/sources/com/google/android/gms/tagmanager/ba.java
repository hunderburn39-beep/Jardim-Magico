package com.google.android.gms.tagmanager;

import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class ba {
    public static cq.c bG(String str) throws JSONException {
        com.google.android.gms.internal.d.a aVarK = k(new JSONObject(str));
        cq.d dVarLh = cq.c.lh();
        for (int i = 0; i < aVarK.fP.length; i++) {
            dVarLh.a(cq.a.ld().b(com.google.android.gms.internal.b.INSTANCE_NAME.toString(), aVarK.fP[i]).b(com.google.android.gms.internal.b.FUNCTION.toString(), dh.bX(m.ka())).b(m.kb(), aVarK.fQ[i]).lg());
        }
        return dVarLh.lk();
    }

    private static com.google.android.gms.internal.d.a k(Object obj) throws JSONException {
        return dh.r(l(obj));
    }

    static Object l(Object obj) throws JSONException {
        if (obj instanceof JSONArray) {
            throw new RuntimeException("JSONArrays are not supported");
        }
        if (JSONObject.NULL.equals(obj)) {
            throw new RuntimeException("JSON nulls are not supported");
        }
        if (!(obj instanceof JSONObject)) {
            return obj;
        }
        JSONObject jSONObject = (JSONObject) obj;
        HashMap map = new HashMap();
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            map.put(next, l(jSONObject.get(next)));
        }
        return map;
    }
}
