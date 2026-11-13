package com.google.android.gms.tagmanager;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/* JADX INFO: loaded from: classes.dex */
class cg extends aj {
    private static final String ID = com.google.android.gms.internal.a.REGEX_GROUP.toString();
    private static final String YZ = com.google.android.gms.internal.b.ARG0.toString();
    private static final String Za = com.google.android.gms.internal.b.ARG1.toString();
    private static final String Zb = com.google.android.gms.internal.b.IGNORE_CASE.toString();
    private static final String Zc = com.google.android.gms.internal.b.GROUP.toString();

    public cg() {
        super(ID, YZ, Za);
    }

    @Override // com.google.android.gms.tagmanager.aj
    public boolean jX() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.aj
    public com.google.android.gms.internal.d.a x(Map<String, com.google.android.gms.internal.d.a> map) {
        int iIntValue;
        com.google.android.gms.internal.d.a aVar = map.get(YZ);
        com.google.android.gms.internal.d.a aVar2 = map.get(Za);
        if (aVar == null || aVar == dh.lT() || aVar2 == null || aVar2 == dh.lT()) {
            return dh.lT();
        }
        int i = dh.n(map.get(Zb)).booleanValue() ? 66 : 64;
        com.google.android.gms.internal.d.a aVar3 = map.get(Zc);
        if (aVar3 != null) {
            Long l = dh.l(aVar3);
            if (l == dh.lO()) {
                return dh.lT();
            }
            iIntValue = l.intValue();
            if (iIntValue < 0) {
                return dh.lT();
            }
        } else {
            iIntValue = 1;
        }
        try {
            String strJ = dh.j(aVar);
            String strGroup = null;
            Matcher matcher = Pattern.compile(dh.j(aVar2), i).matcher(strJ);
            if (matcher.find() && matcher.groupCount() >= iIntValue) {
                strGroup = matcher.group(iIntValue);
            }
            return strGroup == null ? dh.lT() : dh.r(strGroup);
        } catch (PatternSyntaxException e) {
            return dh.lT();
        }
    }
}
