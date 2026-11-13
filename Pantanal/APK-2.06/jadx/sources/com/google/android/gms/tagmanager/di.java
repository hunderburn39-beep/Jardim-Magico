package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.analytics.Tracker;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
class di extends df {
    private static final String ID = com.google.android.gms.internal.a.UNIVERSAL_ANALYTICS.toString();
    private static final String aaO = com.google.android.gms.internal.b.ACCOUNT.toString();
    private static final String aaP = com.google.android.gms.internal.b.ANALYTICS_PASS_THROUGH.toString();
    private static final String aaQ = com.google.android.gms.internal.b.ANALYTICS_FIELDS.toString();
    private static final String aaR = com.google.android.gms.internal.b.TRACK_TRANSACTION.toString();
    private static final String aaS = com.google.android.gms.internal.b.TRANSACTION_DATALAYER_MAP.toString();
    private static final String aaT = com.google.android.gms.internal.b.TRANSACTION_ITEM_DATALAYER_MAP.toString();
    private static Map<String, String> aaU;
    private static Map<String, String> aaV;
    private final DataLayer WK;
    private final Set<String> aaW;
    private final de aaX;

    public di(Context context, DataLayer dataLayer) {
        this(context, dataLayer, new de(context));
    }

    di(Context context, DataLayer dataLayer, de deVar) {
        super(ID, new String[0]);
        this.WK = dataLayer;
        this.aaX = deVar;
        this.aaW = new HashSet();
        this.aaW.add("");
        this.aaW.add("0");
        this.aaW.add("false");
    }

    private Map<String, String> H(Map<String, com.google.android.gms.internal.d.a> map) {
        com.google.android.gms.internal.d.a aVar = map.get(aaS);
        if (aVar != null) {
            return c(aVar);
        }
        if (aaU == null) {
            HashMap map2 = new HashMap();
            map2.put("transactionId", "&ti");
            map2.put("transactionAffiliation", "&ta");
            map2.put("transactionTax", "&tt");
            map2.put("transactionShipping", "&ts");
            map2.put("transactionTotal", "&tr");
            map2.put("transactionCurrency", "&cu");
            aaU = map2;
        }
        return aaU;
    }

    private Map<String, String> I(Map<String, com.google.android.gms.internal.d.a> map) {
        com.google.android.gms.internal.d.a aVar = map.get(aaT);
        if (aVar != null) {
            return c(aVar);
        }
        if (aaV == null) {
            HashMap map2 = new HashMap();
            map2.put("name", "&in");
            map2.put("sku", "&ic");
            map2.put("category", "&iv");
            map2.put("price", "&ip");
            map2.put("quantity", "&iq");
            map2.put("currency", "&cu");
            aaV = map2;
        }
        return aaV;
    }

    private void a(Tracker tracker, Map<String, com.google.android.gms.internal.d.a> map) {
        String strCc = cc("transactionId");
        if (strCc == null) {
            bh.w("Cannot find transactionId in data layer.");
            return;
        }
        LinkedList linkedList = new LinkedList();
        try {
            Map<String, String> mapP = p(map.get(aaQ));
            mapP.put("&t", "transaction");
            for (Map.Entry<String, String> entry : H(map).entrySet()) {
                b(mapP, entry.getValue(), cc(entry.getKey()));
            }
            linkedList.add(mapP);
            List<Map<String, String>> listLU = lU();
            if (listLU != null) {
                for (Map<String, String> map2 : listLU) {
                    if (map2.get("name") == null) {
                        bh.w("Unable to send transaction item hit due to missing 'name' field.");
                        return;
                    }
                    Map<String, String> mapP2 = p(map.get(aaQ));
                    mapP2.put("&t", "item");
                    mapP2.put("&ti", strCc);
                    for (Map.Entry<String, String> entry2 : I(map).entrySet()) {
                        b(mapP2, entry2.getValue(), map2.get(entry2.getKey()));
                    }
                    linkedList.add(mapP2);
                }
            }
            Iterator it = linkedList.iterator();
            while (it.hasNext()) {
                tracker.send((Map) it.next());
            }
        } catch (IllegalArgumentException e) {
            bh.b("Unable to send transaction", e);
        }
    }

    private void b(Map<String, String> map, String str, String str2) {
        if (str2 != null) {
            map.put(str, str2);
        }
    }

    private Map<String, String> c(com.google.android.gms.internal.d.a aVar) {
        Object objO = dh.o(aVar);
        if (!(objO instanceof Map)) {
            return null;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : ((Map) objO).entrySet()) {
            linkedHashMap.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return linkedHashMap;
    }

    private String cc(String str) {
        Object obj = this.WK.get(str);
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    private boolean e(Map<String, com.google.android.gms.internal.d.a> map, String str) {
        com.google.android.gms.internal.d.a aVar = map.get(str);
        if (aVar == null) {
            return false;
        }
        return dh.n(aVar).booleanValue();
    }

    private List<Map<String, String>> lU() {
        Object obj = this.WK.get("transactionProducts");
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof List)) {
            throw new IllegalArgumentException("transactionProducts should be of type List.");
        }
        Iterator it = ((List) obj).iterator();
        while (it.hasNext()) {
            if (!(it.next() instanceof Map)) {
                throw new IllegalArgumentException("Each element of transactionProducts should be of type Map.");
            }
        }
        return (List) obj;
    }

    private Map<String, String> p(com.google.android.gms.internal.d.a aVar) {
        Map<String, String> mapC;
        if (aVar != null && (mapC = c(aVar)) != null) {
            String str = mapC.get("&aip");
            if (str != null && this.aaW.contains(str.toLowerCase())) {
                mapC.remove("&aip");
            }
            return mapC;
        }
        return new HashMap();
    }

    @Override // com.google.android.gms.tagmanager.df
    public void z(Map<String, com.google.android.gms.internal.d.a> map) {
        Tracker trackerBU = this.aaX.bU("_GTM_DEFAULT_TRACKER_");
        if (e(map, aaP)) {
            trackerBU.send(p(map.get(aaQ)));
        } else if (e(map, aaR)) {
            a(trackerBU, map);
        } else {
            bh.z("Ignoring unknown tag.");
        }
    }
}
