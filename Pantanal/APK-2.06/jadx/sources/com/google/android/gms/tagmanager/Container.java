package com.google.android.gms.tagmanager;

import android.content.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class Container {
    private final String WJ;
    private final DataLayer WK;
    private cs WL;
    private volatile long WO;
    private final Context mContext;
    private Map<String, FunctionCallMacroCallback> WM = new HashMap();
    private Map<String, FunctionCallTagCallback> WN = new HashMap();
    private volatile String WP = "";

    public interface FunctionCallMacroCallback {
        Object getValue(String str, Map<String, Object> map);
    }

    public interface FunctionCallTagCallback {
        void execute(String str, Map<String, Object> map);
    }

    private class a implements s.a {
        private a() {
        }

        @Override // com.google.android.gms.tagmanager.s.a
        public Object b(String str, Map<String, Object> map) {
            FunctionCallMacroCallback functionCallMacroCallbackBn = Container.this.bn(str);
            if (functionCallMacroCallbackBn == null) {
                return null;
            }
            return functionCallMacroCallbackBn.getValue(str, map);
        }
    }

    private class b implements s.a {
        private b() {
        }

        @Override // com.google.android.gms.tagmanager.s.a
        public Object b(String str, Map<String, Object> map) {
            FunctionCallTagCallback functionCallTagCallbackBo = Container.this.bo(str);
            if (functionCallTagCallbackBo != null) {
                functionCallTagCallbackBo.execute(str, map);
            }
            return dh.lS();
        }
    }

    Container(Context context, DataLayer dataLayer, String containerId, long lastRefreshTime, com.google.android.gms.internal.c.j resource) {
        this.mContext = context;
        this.WK = dataLayer;
        this.WJ = containerId;
        this.WO = lastRefreshTime;
        a(resource.fK);
        if (resource.fJ != null) {
            a(resource.fJ);
        }
    }

    Container(Context context, DataLayer dataLayer, String containerId, long lastRefreshTime, cq.c resource) {
        this.mContext = context;
        this.WK = dataLayer;
        this.WJ = containerId;
        this.WO = lastRefreshTime;
        a(resource);
    }

    private void a(com.google.android.gms.internal.c.f fVar) {
        if (fVar == null) {
            throw new NullPointerException();
        }
        try {
            a(cq.b(fVar));
        } catch (cq.g e) {
            bh.w("Not loading resource: " + fVar + " because it is invalid: " + e.toString());
        }
    }

    private void a(cq.c cVar) {
        this.WP = cVar.getVersion();
        a(new cs(this.mContext, cVar, this.WK, new a(), new b(), bq(this.WP)));
    }

    private synchronized void a(cs csVar) {
        this.WL = csVar;
    }

    private void a(com.google.android.gms.internal.c.i[] iVarArr) {
        ArrayList arrayList = new ArrayList();
        for (com.google.android.gms.internal.c.i iVar : iVarArr) {
            arrayList.add(iVar);
        }
        kd().e(arrayList);
    }

    private synchronized cs kd() {
        return this.WL;
    }

    FunctionCallMacroCallback bn(String str) {
        FunctionCallMacroCallback functionCallMacroCallback;
        synchronized (this.WM) {
            functionCallMacroCallback = this.WM.get(str);
        }
        return functionCallMacroCallback;
    }

    FunctionCallTagCallback bo(String str) {
        FunctionCallTagCallback functionCallTagCallback;
        synchronized (this.WN) {
            functionCallTagCallback = this.WN.get(str);
        }
        return functionCallTagCallback;
    }

    void bp(String str) {
        kd().bp(str);
    }

    ag bq(String str) {
        if (cd.kT().kU().equals(cd.a.CONTAINER_DEBUG)) {
        }
        return new bq();
    }

    public boolean getBoolean(String key) {
        cs csVarKd = kd();
        if (csVarKd == null) {
            bh.w("getBoolean called for closed container.");
            return dh.lQ().booleanValue();
        }
        try {
            return dh.n(csVarKd.bR(key).getObject()).booleanValue();
        } catch (Exception e) {
            bh.w("Calling getBoolean() threw an exception: " + e.getMessage() + " Returning default value.");
            return dh.lQ().booleanValue();
        }
    }

    public String getContainerId() {
        return this.WJ;
    }

    public double getDouble(String key) {
        cs csVarKd = kd();
        if (csVarKd == null) {
            bh.w("getDouble called for closed container.");
            return dh.lP().doubleValue();
        }
        try {
            return dh.m(csVarKd.bR(key).getObject()).doubleValue();
        } catch (Exception e) {
            bh.w("Calling getDouble() threw an exception: " + e.getMessage() + " Returning default value.");
            return dh.lP().doubleValue();
        }
    }

    public long getLastRefreshTime() {
        return this.WO;
    }

    public long getLong(String key) {
        cs csVarKd = kd();
        if (csVarKd == null) {
            bh.w("getLong called for closed container.");
            return dh.lO().longValue();
        }
        try {
            return dh.l(csVarKd.bR(key).getObject()).longValue();
        } catch (Exception e) {
            bh.w("Calling getLong() threw an exception: " + e.getMessage() + " Returning default value.");
            return dh.lO().longValue();
        }
    }

    public String getString(String key) {
        cs csVarKd = kd();
        if (csVarKd == null) {
            bh.w("getString called for closed container.");
            return dh.lS();
        }
        try {
            return dh.j(csVarKd.bR(key).getObject());
        } catch (Exception e) {
            bh.w("Calling getString() threw an exception: " + e.getMessage() + " Returning default value.");
            return dh.lS();
        }
    }

    public boolean isDefault() {
        return getLastRefreshTime() == 0;
    }

    String kc() {
        return this.WP;
    }

    public void registerFunctionCallMacroCallback(String customMacroName, FunctionCallMacroCallback customMacroCallback) {
        if (customMacroCallback == null) {
            throw new NullPointerException("Macro handler must be non-null");
        }
        synchronized (this.WM) {
            this.WM.put(customMacroName, customMacroCallback);
        }
    }

    public void registerFunctionCallTagCallback(String customTagName, FunctionCallTagCallback customTagCallback) {
        if (customTagCallback == null) {
            throw new NullPointerException("Tag callback must be non-null");
        }
        synchronized (this.WN) {
            this.WN.put(customTagName, customTagCallback);
        }
    }

    void release() {
        this.WL = null;
    }

    public void unregisterFunctionCallMacroCallback(String customMacroName) {
        synchronized (this.WM) {
            this.WM.remove(customMacroName);
        }
    }

    public void unregisterFunctionCallTagCallback(String customTagName) {
        synchronized (this.WN) {
            this.WN.remove(customTagName);
        }
    }
}
