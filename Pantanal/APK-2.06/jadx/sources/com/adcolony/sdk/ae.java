package com.adcolony.sdk;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.tapjoy.TapjoyConstants;

/* JADX INFO: loaded from: classes.dex */
class ae {
    final int a = 30;
    String b = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  x          xxxxxxx                          xxxx x                          xxxxx";
    String c = "0123456789ABCDEF";
    String d = "0123456789abcdef";

    ae() {
    }

    boolean a() {
        NetworkInfo activeNetworkInfo;
        if (a.l == null || (activeNetworkInfo = ((ConnectivityManager) a.l.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo()) == null) {
            return false;
        }
        return activeNetworkInfo.getType() == 1;
    }

    boolean b() {
        NetworkInfo activeNetworkInfo;
        if (a.l == null || (activeNetworkInfo = ((ConnectivityManager) a.l.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo()) == null) {
            return false;
        }
        int type = activeNetworkInfo.getType();
        return type == 0 || type >= 2;
    }

    String c() {
        if (a()) {
            return TapjoyConstants.TJC_CONNECTION_TYPE_WIFI;
        }
        if (b()) {
            return "cell";
        }
        return "none";
    }

    String a(String str) {
        if (str == null) {
            str = "null";
        }
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt < 128 && this.b.charAt(cCharAt) == ' ') {
                sb.append(cCharAt);
            } else {
                sb.append('%');
                int i2 = (cCharAt >> 4) & 15;
                int i3 = cCharAt & 15;
                if (i2 < 10) {
                    sb.append((char) (i2 + 48));
                } else {
                    sb.append((char) ((i2 + 65) - 10));
                }
                if (i3 < 10) {
                    sb.append((char) (i3 + 48));
                } else {
                    sb.append((char) ((i3 + 65) - 10));
                }
            }
        }
        return sb.toString();
    }

    int a(char c) {
        int iIndexOf = this.c.indexOf(c);
        if (iIndexOf < 0) {
            int iIndexOf2 = this.d.indexOf(c);
            if (iIndexOf2 < 0) {
                return 0;
            }
            return iIndexOf2;
        }
        return iIndexOf;
    }

    String b(String str) {
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        int i = 0;
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '%') {
                char cCharAt2 = i + 1 < length ? str.charAt(i + 1) : '0';
                char cCharAt3 = i + 2 < length ? str.charAt(i + 2) : '0';
                i += 2;
                sb.append((char) (a(cCharAt3) | (a(cCharAt2) << 8)));
            } else {
                sb.append(cCharAt);
            }
            i++;
        }
        return sb.toString();
    }
}
