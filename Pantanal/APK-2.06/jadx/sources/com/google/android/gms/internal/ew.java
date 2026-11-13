package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.ads.AdSize;
import com.google.android.gms.common.images.WebImage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ew {
    private static final er zb = new er("MetadataUtils");
    private static final String[] Ac = {"Z", "+hh", "+hhmm", "+hh:mm"};
    private static final String Ad = "yyyyMMdd'T'HHmmss" + Ac[0];

    public static String a(Calendar calendar) {
        if (calendar == null) {
            zb.b("Calendar object cannot be null", new Object[0]);
            return null;
        }
        String str = Ad;
        if (calendar.get(11) == 0 && calendar.get(12) == 0 && calendar.get(13) == 0) {
            str = "yyyyMMdd";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        simpleDateFormat.setTimeZone(calendar.getTimeZone());
        String str2 = simpleDateFormat.format(calendar.getTime());
        return str2.endsWith("+0000") ? str2.replace("+0000", Ac[0]) : str2;
    }

    public static void a(List<WebImage> list, JSONObject jSONObject) {
        try {
            list.clear();
            JSONArray jSONArray = jSONObject.getJSONArray("images");
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                try {
                    list.add(new WebImage(jSONArray.getJSONObject(i)));
                } catch (IllegalArgumentException e) {
                }
            }
        } catch (JSONException e2) {
        }
    }

    public static void a(JSONObject jSONObject, List<WebImage> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        JSONArray jSONArray = new JSONArray();
        Iterator<WebImage> it = list.iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next().dB());
        }
        try {
            jSONObject.put("images", jSONArray);
        } catch (JSONException e) {
        }
    }

    public static Calendar ac(String str) {
        if (TextUtils.isEmpty(str)) {
            zb.b("Input string is empty or null", new Object[0]);
            return null;
        }
        String strAd = ad(str);
        if (TextUtils.isEmpty(strAd)) {
            zb.b("Invalid date format", new Object[0]);
            return null;
        }
        String strAe = ae(str);
        String str2 = "yyyyMMdd";
        if (!TextUtils.isEmpty(strAe)) {
            strAd = strAd + "T" + strAe;
            str2 = strAe.length() == "HHmmss".length() ? "yyyyMMdd'T'HHmmss" : Ad;
        }
        Calendar gregorianCalendar = GregorianCalendar.getInstance();
        try {
            gregorianCalendar.setTime(new SimpleDateFormat(str2).parse(strAd));
            return gregorianCalendar;
        } catch (ParseException e) {
            zb.b("Error parsing string: %s", e.getMessage());
            return null;
        }
    }

    private static String ad(String str) {
        if (TextUtils.isEmpty(str)) {
            zb.b("Input string is empty or null", new Object[0]);
            return null;
        }
        try {
            return str.substring(0, "yyyyMMdd".length());
        } catch (IndexOutOfBoundsException e) {
            zb.c("Error extracting the date: %s", e.getMessage());
            return null;
        }
    }

    private static String ae(String str) {
        if (TextUtils.isEmpty(str)) {
            zb.b("string is empty or null", new Object[0]);
            return null;
        }
        int iIndexOf = str.indexOf(84);
        int i = iIndexOf + 1;
        if (iIndexOf != "yyyyMMdd".length()) {
            zb.b("T delimeter is not found", new Object[0]);
            return null;
        }
        try {
            String strSubstring = str.substring(i);
            if (strSubstring.length() == "HHmmss".length()) {
                return strSubstring;
            }
            switch (strSubstring.charAt("HHmmss".length())) {
                case '+':
                case '-':
                    if (af(strSubstring)) {
                        return strSubstring.replaceAll("([\\+\\-]\\d\\d):(\\d\\d)", "$1$2");
                    }
                    return null;
                case AdSize.LARGE_AD_HEIGHT /* 90 */:
                    if (strSubstring.length() == "HHmmss".length() + Ac[0].length()) {
                        return strSubstring.substring(0, strSubstring.length() - 1) + "+0000";
                    }
                    return null;
                default:
                    return null;
            }
        } catch (IndexOutOfBoundsException e) {
            zb.b("Error extracting the time substring: %s", e.getMessage());
            return null;
        }
    }

    private static boolean af(String str) {
        int length = str.length();
        int length2 = "HHmmss".length();
        return length == Ac[1].length() + length2 || length == Ac[2].length() + length2 || length == length2 + Ac[3].length();
    }
}
