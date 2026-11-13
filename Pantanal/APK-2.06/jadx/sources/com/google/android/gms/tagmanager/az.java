package com.google.android.gms.tagmanager;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
class az extends aj {
    private static final String ID = com.google.android.gms.internal.a.JOINER.toString();
    private static final String XQ = com.google.android.gms.internal.b.ARG0.toString();
    private static final String Ym = com.google.android.gms.internal.b.ITEM_SEPARATOR.toString();
    private static final String Yn = com.google.android.gms.internal.b.KEY_VALUE_SEPARATOR.toString();
    private static final String Yo = com.google.android.gms.internal.b.ESCAPE.toString();

    private enum a {
        NONE,
        URL,
        BACKSLASH
    }

    public az() {
        super(ID, XQ);
    }

    private String a(String str, a aVar, Set<Character> set) {
        switch (aVar) {
            case URL:
                try {
                    return dk.cd(str);
                } catch (UnsupportedEncodingException e) {
                    bh.b("Joiner: unsupported encoding", e);
                    return str;
                }
            case BACKSLASH:
                String strReplace = str.replace("\\", "\\\\");
                Iterator<Character> it = set.iterator();
                while (true) {
                    String str2 = strReplace;
                    if (!it.hasNext()) {
                        return str2;
                    }
                    String string = it.next().toString();
                    strReplace = str2.replace(string, "\\" + string);
                }
                break;
            default:
                return str;
        }
    }

    private void a(StringBuilder sb, String str, a aVar, Set<Character> set) {
        sb.append(a(str, aVar, set));
    }

    private void a(Set<Character> set, String str) {
        for (int i = 0; i < str.length(); i++) {
            set.add(Character.valueOf(str.charAt(i)));
        }
    }

    @Override // com.google.android.gms.tagmanager.aj
    public boolean jX() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.aj
    public com.google.android.gms.internal.d.a x(Map<String, com.google.android.gms.internal.d.a> map) {
        HashSet hashSet;
        a aVar;
        com.google.android.gms.internal.d.a aVar2 = map.get(XQ);
        if (aVar2 == null) {
            return dh.lT();
        }
        com.google.android.gms.internal.d.a aVar3 = map.get(Ym);
        String strJ = aVar3 != null ? dh.j(aVar3) : "";
        com.google.android.gms.internal.d.a aVar4 = map.get(Yn);
        String strJ2 = aVar4 != null ? dh.j(aVar4) : "=";
        a aVar5 = a.NONE;
        com.google.android.gms.internal.d.a aVar6 = map.get(Yo);
        if (aVar6 != null) {
            String strJ3 = dh.j(aVar6);
            if ("url".equals(strJ3)) {
                aVar = a.URL;
                hashSet = null;
            } else {
                if (!"backslash".equals(strJ3)) {
                    bh.w("Joiner: unsupported escape type: " + strJ3);
                    return dh.lT();
                }
                aVar = a.BACKSLASH;
                hashSet = new HashSet();
                a(hashSet, strJ);
                a(hashSet, strJ2);
                hashSet.remove('\\');
            }
        } else {
            hashSet = null;
            aVar = aVar5;
        }
        StringBuilder sb = new StringBuilder();
        switch (aVar2.type) {
            case 2:
                boolean z = true;
                com.google.android.gms.internal.d.a[] aVarArr = aVar2.fO;
                int length = aVarArr.length;
                int i = 0;
                while (i < length) {
                    com.google.android.gms.internal.d.a aVar7 = aVarArr[i];
                    if (!z) {
                        sb.append(strJ);
                    }
                    a(sb, dh.j(aVar7), aVar, hashSet);
                    i++;
                    z = false;
                }
                break;
            case 3:
                for (int i2 = 0; i2 < aVar2.fP.length; i2++) {
                    if (i2 > 0) {
                        sb.append(strJ);
                    }
                    String strJ4 = dh.j(aVar2.fP[i2]);
                    String strJ5 = dh.j(aVar2.fQ[i2]);
                    a(sb, strJ4, aVar, hashSet);
                    sb.append(strJ2);
                    a(sb, strJ5, aVar, hashSet);
                }
                break;
            default:
                a(sb, dh.j(aVar2), aVar, hashSet);
                break;
        }
        return dh.r(sb.toString());
    }
}
