package com.google.android.gms.appstate;

import com.google.android.gms.internal.fo;

/* JADX INFO: loaded from: classes.dex */
public final class a implements AppState {
    private final int wr;
    private final String ws;
    private final byte[] wt;
    private final boolean wu;
    private final String wv;
    private final byte[] ww;

    public a(AppState appState) {
        this.wr = appState.getKey();
        this.ws = appState.getLocalVersion();
        this.wt = appState.getLocalData();
        this.wu = appState.hasConflict();
        this.wv = appState.getConflictVersion();
        this.ww = appState.getConflictData();
    }

    static int a(AppState appState) {
        return fo.hashCode(Integer.valueOf(appState.getKey()), appState.getLocalVersion(), appState.getLocalData(), Boolean.valueOf(appState.hasConflict()), appState.getConflictVersion(), appState.getConflictData());
    }

    static boolean a(AppState appState, Object obj) {
        if (!(obj instanceof AppState)) {
            return false;
        }
        if (appState == obj) {
            return true;
        }
        AppState appState2 = (AppState) obj;
        return fo.equal(Integer.valueOf(appState2.getKey()), Integer.valueOf(appState.getKey())) && fo.equal(appState2.getLocalVersion(), appState.getLocalVersion()) && fo.equal(appState2.getLocalData(), appState.getLocalData()) && fo.equal(Boolean.valueOf(appState2.hasConflict()), Boolean.valueOf(appState.hasConflict())) && fo.equal(appState2.getConflictVersion(), appState.getConflictVersion()) && fo.equal(appState2.getConflictData(), appState.getConflictData());
    }

    static String b(AppState appState) {
        return fo.e(appState).a("Key", Integer.valueOf(appState.getKey())).a("LocalVersion", appState.getLocalVersion()).a("LocalData", appState.getLocalData()).a("HasConflict", Boolean.valueOf(appState.hasConflict())).a("ConflictVersion", appState.getConflictVersion()).a("ConflictData", appState.getConflictData()).toString();
    }

    @Override // com.google.android.gms.common.data.Freezable
    /* JADX INFO: renamed from: dt, reason: merged with bridge method [inline-methods] */
    public AppState freeze() {
        return this;
    }

    public boolean equals(Object obj) {
        return a(this, obj);
    }

    @Override // com.google.android.gms.appstate.AppState
    public byte[] getConflictData() {
        return this.ww;
    }

    @Override // com.google.android.gms.appstate.AppState
    public String getConflictVersion() {
        return this.wv;
    }

    @Override // com.google.android.gms.appstate.AppState
    public int getKey() {
        return this.wr;
    }

    @Override // com.google.android.gms.appstate.AppState
    public byte[] getLocalData() {
        return this.wt;
    }

    @Override // com.google.android.gms.appstate.AppState
    public String getLocalVersion() {
        return this.ws;
    }

    @Override // com.google.android.gms.appstate.AppState
    public boolean hasConflict() {
        return this.wu;
    }

    public int hashCode() {
        return a(this);
    }

    @Override // com.google.android.gms.common.data.Freezable
    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return b(this);
    }
}
