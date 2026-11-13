package com.tapjoy.internal;

import com.tapjoy.TapjoyConstants;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class gy extends cd {
    @Override // com.tapjoy.internal.ce
    public final String b() {
        return "POST";
    }

    @Override // com.tapjoy.internal.ce
    public final String d() {
        return "application/json";
    }

    @Override // com.tapjoy.internal.ce
    public Map e() {
        Map mapE = super.e();
        fu fuVarA = fu.a();
        mapE.put("sdk_ver", fuVarA.m + "/Android");
        mapE.put(TapjoyConstants.TJC_API_KEY, fuVarA.l);
        if (fr.a) {
            mapE.put(TapjoyConstants.TJC_DEBUG, true);
        }
        return mapE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tapjoy.internal.ce
    public Object f() throws Exception {
        try {
            return super.f();
        } catch (Exception e) {
            new Object[1][0] = this;
            throw e;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tapjoy.internal.cd
    public Object a(br brVar) {
        brVar.s();
        return null;
    }
}
