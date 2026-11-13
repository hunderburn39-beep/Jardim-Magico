package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.text.TextUtils;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyConstants;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class dd {
    private static final SimpleDateFormat pH = new SimpleDateFormat("yyyyMMdd");

    public static cz a(Context context, cx cxVar, String str) {
        cz czVar;
        List<String> list;
        List<String> list2;
        List<String> list3;
        try {
            JSONObject jSONObject = new JSONObject(str);
            String strOptString = jSONObject.optString("ad_base_url", null);
            String strOptString2 = jSONObject.optString("ad_url", null);
            String strOptString3 = jSONObject.optString("ad_size", null);
            String strOptString4 = jSONObject.optString("ad_html", null);
            long j = -1;
            String strOptString5 = jSONObject.optString("debug_dialog", null);
            long j2 = jSONObject.has("interstitial_timeout") ? (long) (jSONObject.getDouble("interstitial_timeout") * 1000.0d) : -1L;
            String strOptString6 = jSONObject.optString("orientation", null);
            int iBz = -1;
            if ("portrait".equals(strOptString6)) {
                iBz = dq.bA();
            } else if (TJAdUnitConstants.String.LANDSCAPE.equals(strOptString6)) {
                iBz = dq.bz();
            }
            if (TextUtils.isEmpty(strOptString4)) {
                if (TextUtils.isEmpty(strOptString2)) {
                    dw.z("Could not parse the mediation config: Missing required ad_html or ad_url field.");
                    return new cz(0);
                }
                cz czVarA = dc.a(context, cxVar.kK.rq, strOptString2);
                strOptString = czVarA.ol;
                strOptString4 = czVarA.pm;
                j = czVarA.ps;
                czVar = czVarA;
            } else {
                if (TextUtils.isEmpty(strOptString)) {
                    dw.z("Could not parse the mediation config: Missing required ad_base_url field");
                    return new cz(0);
                }
                czVar = null;
            }
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("click_urls");
            List<String> linkedList = czVar == null ? null : czVar.ne;
            if (jSONArrayOptJSONArray != null) {
                if (linkedList == null) {
                    linkedList = new LinkedList<>();
                }
                for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                    linkedList.add(jSONArrayOptJSONArray.getString(i));
                }
                list = linkedList;
            } else {
                list = linkedList;
            }
            JSONArray jSONArrayOptJSONArray2 = jSONObject.optJSONArray("impression_urls");
            List<String> linkedList2 = czVar == null ? null : czVar.nf;
            if (jSONArrayOptJSONArray2 != null) {
                if (linkedList2 == null) {
                    linkedList2 = new LinkedList<>();
                }
                for (int i2 = 0; i2 < jSONArrayOptJSONArray2.length(); i2++) {
                    linkedList2.add(jSONArrayOptJSONArray2.getString(i2));
                }
                list2 = linkedList2;
            } else {
                list2 = linkedList2;
            }
            JSONArray jSONArrayOptJSONArray3 = jSONObject.optJSONArray("manual_impression_urls");
            List<String> linkedList3 = czVar == null ? null : czVar.pq;
            if (jSONArrayOptJSONArray3 != null) {
                if (linkedList3 == null) {
                    linkedList3 = new LinkedList<>();
                }
                for (int i3 = 0; i3 < jSONArrayOptJSONArray3.length(); i3++) {
                    linkedList3.add(jSONArrayOptJSONArray3.getString(i3));
                }
                list3 = linkedList3;
            } else {
                list3 = linkedList3;
            }
            if (czVar != null) {
                if (czVar.orientation != -1) {
                    iBz = czVar.orientation;
                }
                if (czVar.pn > 0) {
                    j2 = czVar.pn;
                }
            }
            String strOptString7 = jSONObject.optString("active_view");
            boolean zOptBoolean = jSONObject.optBoolean("ad_is_javascript", false);
            return new cz(strOptString, strOptString4, list, list2, j2, false, -1L, list3, -1L, iBz, strOptString3, j, strOptString5, zOptBoolean, zOptBoolean ? jSONObject.optString("ad_passback_url", null) : null, strOptString7);
        } catch (JSONException e) {
            dw.z("Could not parse the mediation config: " + e.getMessage());
            return new cz(0);
        }
    }

    public static String a(cx cxVar, dg dgVar, Location location, String str) {
        try {
            HashMap map = new HashMap();
            if (str != null && str.trim() != "") {
                map.put("eid", str);
            }
            if (cxVar.pf != null) {
                map.put("ad_pos", cxVar.pf);
            }
            a((HashMap<String, Object>) map, cxVar.pg);
            map.put("format", cxVar.kN.lS);
            if (cxVar.kN.width == -1) {
                map.put("smart_w", "full");
            }
            if (cxVar.kN.height == -2) {
                map.put("smart_h", "auto");
            }
            if (cxVar.kN.lU != null) {
                StringBuilder sb = new StringBuilder();
                for (ak akVar : cxVar.kN.lU) {
                    if (sb.length() != 0) {
                        sb.append("|");
                    }
                    sb.append(akVar.width == -1 ? (int) (akVar.widthPixels / dgVar.qp) : akVar.width);
                    sb.append(TMXConstants.TAG_OBJECT_ATTRIBUTE_X);
                    sb.append(akVar.height == -2 ? (int) (akVar.heightPixels / dgVar.qp) : akVar.height);
                }
                map.put("sz", sb);
            }
            map.put("slotname", cxVar.kH);
            map.put("pn", cxVar.applicationInfo.packageName);
            if (cxVar.ph != null) {
                map.put("vc", Integer.valueOf(cxVar.ph.versionCode));
            }
            map.put("ms", cxVar.pi);
            map.put("seq_num", cxVar.pj);
            map.put(TapjoyConstants.TJC_SESSION_ID, cxVar.pk);
            map.put("js", cxVar.kK.rq);
            a((HashMap<String, Object>) map, dgVar);
            if (cxVar.pg.versionCode >= 2 && cxVar.pg.lP != null) {
                a((HashMap<String, Object>) map, cxVar.pg.lP);
            }
            if (cxVar.versionCode >= 2) {
                map.put("quality_signals", cxVar.pl);
            }
            if (dw.n(2)) {
                dw.y("Ad Request JSON: " + dq.p(map).toString(2));
            }
            return dq.p(map).toString();
        } catch (JSONException e) {
            dw.z("Problem serializing ad request to JSON: " + e.getMessage());
            return null;
        }
    }

    private static void a(HashMap<String, Object> map, Location location) {
        HashMap map2 = new HashMap();
        Float fValueOf = Float.valueOf(location.getAccuracy() * 1000.0f);
        Long lValueOf = Long.valueOf(location.getTime() * 1000);
        Long lValueOf2 = Long.valueOf((long) (location.getLatitude() * 1.0E7d));
        Long lValueOf3 = Long.valueOf((long) (location.getLongitude() * 1.0E7d));
        map2.put("radius", fValueOf);
        map2.put(TJAdUnitConstants.String.LAT, lValueOf2);
        map2.put(TJAdUnitConstants.String.LONG, lValueOf3);
        map2.put("time", lValueOf);
        map.put("uule", map2);
    }

    private static void a(HashMap<String, Object> map, ah ahVar) {
        String strBx = dn.bx();
        if (strBx != null) {
            map.put("abf", strBx);
        }
        if (ahVar.lH != -1) {
            map.put("cust_age", pH.format(new Date(ahVar.lH)));
        }
        if (ahVar.extras != null) {
            map.put("extras", ahVar.extras);
        }
        if (ahVar.lI != -1) {
            map.put("cust_gender", Integer.valueOf(ahVar.lI));
        }
        if (ahVar.lJ != null) {
            map.put("kw", ahVar.lJ);
        }
        if (ahVar.lL != -1) {
            map.put("tag_for_child_directed_treatment", Integer.valueOf(ahVar.lL));
        }
        if (ahVar.lK) {
            map.put("adtest", "on");
        }
        if (ahVar.versionCode >= 2) {
            if (ahVar.lM) {
                map.put("d_imp_hdr", 1);
            }
            if (!TextUtils.isEmpty(ahVar.lN)) {
                map.put("ppid", ahVar.lN);
            }
            if (ahVar.lO != null) {
                a(map, ahVar.lO);
            }
        }
        if (ahVar.versionCode < 3 || ahVar.lQ == null) {
            return;
        }
        map.put("url", ahVar.lQ);
    }

    private static void a(HashMap<String, Object> map, av avVar) {
        String str;
        String str2 = null;
        if (Color.alpha(avVar.mq) != 0) {
            map.put("acolor", m(avVar.mq));
        }
        if (Color.alpha(avVar.backgroundColor) != 0) {
            map.put("bgcolor", m(avVar.backgroundColor));
        }
        if (Color.alpha(avVar.mr) != 0 && Color.alpha(avVar.ms) != 0) {
            map.put("gradientto", m(avVar.mr));
            map.put("gradientfrom", m(avVar.ms));
        }
        if (Color.alpha(avVar.mt) != 0) {
            map.put("bcolor", m(avVar.mt));
        }
        map.put("bthick", Integer.toString(avVar.mu));
        switch (avVar.mv) {
            case 0:
                str = "none";
                break;
            case 1:
                str = "dashed";
                break;
            case 2:
                str = "dotted";
                break;
            case 3:
                str = "solid";
                break;
            default:
                str = null;
                break;
        }
        if (str != null) {
            map.put("btype", str);
        }
        switch (avVar.mw) {
            case 0:
                str2 = "light";
                break;
            case 1:
                str2 = "medium";
                break;
            case 2:
                str2 = "dark";
                break;
        }
        if (str2 != null) {
            map.put("callbuttoncolor", str2);
        }
        if (avVar.mx != null) {
            map.put("channel", avVar.mx);
        }
        if (Color.alpha(avVar.my) != 0) {
            map.put("dcolor", m(avVar.my));
        }
        if (avVar.mz != null) {
            map.put("font", avVar.mz);
        }
        if (Color.alpha(avVar.mA) != 0) {
            map.put("hcolor", m(avVar.mA));
        }
        map.put("headersize", Integer.toString(avVar.mB));
        if (avVar.mC != null) {
            map.put("q", avVar.mC);
        }
    }

    private static void a(HashMap<String, Object> map, dg dgVar) {
        map.put("am", Integer.valueOf(dgVar.pZ));
        map.put("cog", l(dgVar.qa));
        map.put("coh", l(dgVar.qb));
        if (!TextUtils.isEmpty(dgVar.qc)) {
            map.put("carrier", dgVar.qc);
        }
        map.put("gl", dgVar.qd);
        if (dgVar.qe) {
            map.put("simulator", 1);
        }
        map.put("ma", l(dgVar.qf));
        map.put("sp", l(dgVar.qg));
        map.put("hl", dgVar.qh);
        if (!TextUtils.isEmpty(dgVar.qi)) {
            map.put("mv", dgVar.qi);
        }
        map.put("muv", Integer.valueOf(dgVar.qj));
        if (dgVar.qk != -2) {
            map.put("cnt", Integer.valueOf(dgVar.qk));
        }
        map.put("gnt", Integer.valueOf(dgVar.ql));
        map.put("pt", Integer.valueOf(dgVar.qm));
        map.put("rm", Integer.valueOf(dgVar.qn));
        map.put("riv", Integer.valueOf(dgVar.qo));
        map.put("u_sd", Float.valueOf(dgVar.qp));
        map.put("sh", Integer.valueOf(dgVar.qr));
        map.put("sw", Integer.valueOf(dgVar.qq));
    }

    private static Integer l(boolean z) {
        return Integer.valueOf(z ? 1 : 0);
    }

    private static String m(int i) {
        return String.format(Locale.US, "#%06x", Integer.valueOf(16777215 & i));
    }
}
