package com.tapjoy;

import android.net.Uri;
import java.util.Map;
import java.util.TreeSet;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class HmacSignature {
    private String a;
    private String b;

    public HmacSignature(String method, String secret) {
        this.a = method;
        this.b = secret;
    }

    public String sign(String url, Map map) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(this.b.getBytes(), this.a);
            Mac mac = Mac.getInstance(this.a);
            mac.init(secretKeySpec);
            Uri uri = Uri.parse(url);
            String str = uri.getScheme() + "://" + uri.getHost();
            if (!((uri.getScheme().equals("http") && uri.getPort() == 80) || (uri.getScheme().equals("https") && uri.getPort() == 443)) && -1 != uri.getPort()) {
                str = str + ":" + uri.getPort();
            }
            String str2 = "POST&" + Uri.encode(str.toLowerCase() + uri.getPath()) + "&" + Uri.encode(a(map));
            TapjoyLog.v("HmacSignature", "Base Url: " + str2);
            byte[] bArrDoFinal = mac.doFinal(str2.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bArrDoFinal) {
                String hexString = Integer.toHexString(b & 255);
                if (hexString.length() == 1) {
                    sb.append('0');
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean matches(String url, Map map, String signature) {
        return sign(url, map).equals(signature);
    }

    private static String a(Map map) {
        TreeSet<String> treeSet = new TreeSet(map.keySet());
        StringBuilder sb = new StringBuilder();
        for (String str : treeSet) {
            sb.append(str + "=" + ((String) map.get(str)) + "&");
        }
        sb.deleteCharAt(sb.lastIndexOf("&"));
        TapjoyLog.v("HmacSignature", "Unhashed String: " + sb.toString());
        return sb.toString();
    }
}
