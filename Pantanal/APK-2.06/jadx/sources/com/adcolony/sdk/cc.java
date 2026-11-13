package com.adcolony.sdk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class cc implements Serializable {
    static final String a = "meta";
    static final String b = "actions";
    static final String c = "messageId";
    static final String d = "sendId";
    b e;
    List<a> f;
    String g;
    String h;

    cc(JSONObject jSONObject) {
        try {
            this.g = jSONObject.getString(c);
            this.h = jSONObject.getString(d);
            this.e = new b(jSONObject.getJSONObject("onClick"));
            JSONArray jSONArray = jSONObject.getJSONArray(b);
            this.f = new ArrayList();
            for (int i = 0; i < jSONArray.length(); i++) {
                this.f.add(new a(b(jSONArray.getJSONObject(i))));
            }
        } catch (JSONException e) {
            bw.b("[YvolverPushMetaData-ERR]", "caught meta exception: " + e, true);
        }
    }

    cc(b bVar, List<a> list) {
        this.e = bVar;
        this.f = list;
    }

    String a() {
        return this.e.d;
    }

    String b() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(c, this.g);
            jSONObject.put(d, this.h);
            if (this.e != null) {
                jSONObject.put("onClick", this.e.a());
            }
            String strC = c();
            if (strC != null && !strC.isEmpty()) {
                jSONObject.put(b, strC);
            }
            return jSONObject.toString();
        } catch (JSONException e) {
            bw.b("PushMetaData", "caught JSONException:" + e.getMessage(), true);
            return "";
        }
    }

    private String c() {
        if (this.f == null) {
            return "";
        }
        JSONArray jSONArray = new JSONArray();
        int i = 0;
        Iterator<a> it = this.f.iterator();
        while (true) {
            int i2 = i;
            if (it.hasNext()) {
                jSONArray.put(it.next().a());
                i = i2 + 1;
            } else {
                return jSONArray.toString();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static HashMap<String, Serializable> b(JSONObject jSONObject) throws JSONException {
        HashMap<String, Serializable> map = new HashMap<>();
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            Object obj = jSONObject.get(next);
            if (obj instanceof Serializable) {
                map.put(next, (Serializable) obj);
            } else if (obj instanceof JSONArray) {
                ArrayList arrayList = new ArrayList();
                JSONArray jSONArray = (JSONArray) obj;
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(jSONArray.get(i));
                }
                map.put(next, arrayList);
            } else {
                map.put(next, b((JSONObject) obj));
            }
        }
        return map;
    }

    class b implements Serializable {
        static final String a = "onClick";
        static final String b = "method";
        static final String c = "parameters";
        String d;
        HashMap<String, Serializable> e;

        b(JSONObject jSONObject) throws JSONException {
            this.d = jSONObject.getString("method");
            this.e = cc.b(jSONObject.getJSONObject(c));
        }

        b(String str, HashMap<String, Serializable> map) {
            this.d = str;
            this.e = map;
        }

        String a() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("method", this.d);
                jSONObject.put(c, this.e);
                return jSONObject.toString();
            } catch (JSONException e) {
                return "";
            }
        }
    }

    class a implements Serializable {
        static final String a = "action";
        HashMap<String, Serializable> b;

        a(HashMap<String, Serializable> map) {
            this.b = map;
        }

        String a() {
            return "{" + this.b + "}";
        }
    }
}
