package com.adcolony.sdk;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
class ch {
    ch() {
    }

    static String a(String str, int i) {
        int iCeil = (int) Math.ceil(((double) str.length()) / ((double) i));
        char[] charArray = str.toCharArray();
        int i2 = iCeil;
        int i3 = 0;
        for (int i4 = 0; i4 < str.length(); i4++) {
            if (i4 % i == 0) {
                charArray[i4] = str.charAt(i3);
                i3++;
            } else {
                charArray[i4] = str.charAt(i2);
                i2++;
            }
        }
        return new String(charArray);
    }

    private String a() {
        return "AdColonyPubServices";
    }

    static ArrayList<String> a(String str) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(b(str));
        arrayList.add(c(str));
        arrayList.add(d(str));
        arrayList.add(e(str));
        arrayList.add(f(str));
        arrayList.add(g(str));
        arrayList.add(h(str));
        arrayList.add(i(str));
        return arrayList;
    }

    static String b(String str, int i) {
        return ck.c(a(str, i));
    }

    static String b(String str) {
        return a(str, 2);
    }

    static String c(String str) {
        return b(str, 2);
    }

    static String d(String str) {
        return a(str, 3);
    }

    static String e(String str) {
        return b(str, 3);
    }

    static String f(String str) {
        return a(str, 4);
    }

    static String g(String str) {
        return b(str, 4);
    }

    static String h(String str) {
        return a(str, 5);
    }

    static String i(String str) {
        return b(str, 5);
    }
}
