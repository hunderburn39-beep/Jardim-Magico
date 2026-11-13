package com.google.android.gms.analytics;

import android.text.TextUtils;
import com.heyzap.http.AsyncHttpResponseHandler;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class y {
    static String a(x xVar, long j) {
        StringBuilder sb = new StringBuilder();
        sb.append(xVar.cO());
        if (xVar.cQ() > 0) {
            long jCQ = j - xVar.cQ();
            if (jCQ >= 0) {
                sb.append("&qt").append("=").append(jCQ);
            }
        }
        sb.append("&z").append("=").append(xVar.cP());
        return sb.toString();
    }

    static String encode(String input) {
        try {
            return URLEncoder.encode(input, AsyncHttpResponseHandler.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("URL encoding failed for: " + input);
        }
    }

    static Map<String, String> v(Map<String, String> map) {
        HashMap map2 = new HashMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().startsWith("&") && entry.getValue() != null) {
                String strSubstring = entry.getKey().substring(1);
                if (!TextUtils.isEmpty(strSubstring)) {
                    map2.put(strSubstring, entry.getValue());
                }
            }
        }
        return map2;
    }
}
