package com.fyber.utils;

import com.fyber.Fyber;
import java.util.Collections;
import java.util.Map;

/* JADX INFO: compiled from: GlobalParamsProvider.java */
/* JADX INFO: loaded from: classes.dex */
public final class g implements o {
    @Override // com.fyber.utils.o
    public final Map<String, String> a() {
        Map<String, String> mapC = Fyber.getConfigs().c();
        if (m.a(mapC)) {
            return Collections.emptyMap();
        }
        return mapC;
    }
}
