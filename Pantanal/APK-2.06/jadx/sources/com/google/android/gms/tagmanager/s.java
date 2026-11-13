package com.google.android.gms.tagmanager;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class s extends aj {
    private final a Xo;
    private static final String ID = com.google.android.gms.internal.a.FUNCTION_CALL.toString();
    private static final String Xn = com.google.android.gms.internal.b.FUNCTION_CALL_NAME.toString();
    private static final String WC = com.google.android.gms.internal.b.ADDITIONAL_PARAMS.toString();

    public interface a {
        Object b(String str, Map<String, Object> map);
    }

    public s(a aVar) {
        super(ID, Xn);
        this.Xo = aVar;
    }

    @Override // com.google.android.gms.tagmanager.aj
    public boolean jX() {
        return false;
    }

    @Override // com.google.android.gms.tagmanager.aj
    public com.google.android.gms.internal.d.a x(Map<String, com.google.android.gms.internal.d.a> map) {
        String strJ = dh.j(map.get(Xn));
        HashMap map2 = new HashMap();
        com.google.android.gms.internal.d.a aVar = map.get(WC);
        if (aVar != null) {
            Object objO = dh.o(aVar);
            if (!(objO instanceof Map)) {
                bh.z("FunctionCallMacro: expected ADDITIONAL_PARAMS to be a map.");
                return dh.lT();
            }
            for (Map.Entry entry : ((Map) objO).entrySet()) {
                map2.put(entry.getKey().toString(), entry.getValue());
            }
        }
        try {
            return dh.r(this.Xo.b(strJ, map2));
        } catch (Exception e) {
            bh.z("Custom macro/tag " + strJ + " threw exception " + e.getMessage());
            return dh.lT();
        }
    }
}
