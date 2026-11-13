package com.fyber.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

/* JADX INFO: compiled from: SignatureTools.java */
/* JADX INFO: loaded from: classes.dex */
public final class q {
    public static String a(Map<String, String> map, String str) {
        Iterator it = new TreeSet(map.keySet()).iterator();
        String string = "";
        while (true) {
            String str2 = string;
            if (it.hasNext()) {
                String str3 = (String) it.next();
                String str4 = map.get(str3);
                StringBuilder sbAppend = new StringBuilder().append(str2);
                Object[] objArr = new Object[2];
                objArr[0] = str3;
                if (str4 == null) {
                    str4 = "";
                }
                objArr[1] = str4;
                string = sbAppend.append(String.format("%s=%s&", objArr)).toString();
            } else {
                return a(str2, str);
            }
        }
    }

    public static String a(String str, String str2) {
        return a(str + str2);
    }

    public static String a(String str) {
        try {
            byte[] bArrDigest = MessageDigest.getInstance("SHA1").digest(str.getBytes());
            Formatter formatter = new Formatter();
            for (byte b : bArrDigest) {
                formatter.format("%02x", Byte.valueOf(b));
            }
            String string = formatter.toString();
            formatter.close();
            return string;
        } catch (NoSuchAlgorithmException e) {
            FyberLogger.e("UrlBuilder", "SHA1 algorithm not available.", e);
            return "nosha1";
        }
    }
}
