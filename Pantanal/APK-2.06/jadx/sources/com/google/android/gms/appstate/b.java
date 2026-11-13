package com.google.android.gms.appstate;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public final class b extends com.google.android.gms.common.data.b implements AppState {
    b(DataHolder dataHolder, int i) {
        super(dataHolder, i);
    }

    @Override // com.google.android.gms.common.data.Freezable
    /* JADX INFO: renamed from: dt, reason: merged with bridge method [inline-methods] */
    public AppState freeze() {
        return new a(this);
    }

    @Override // com.google.android.gms.common.data.b
    public boolean equals(Object obj) {
        return a.a(this, obj);
    }

    @Override // com.google.android.gms.appstate.AppState
    public byte[] getConflictData() {
        return getByteArray("conflict_data");
    }

    @Override // com.google.android.gms.appstate.AppState
    public String getConflictVersion() {
        return getString("conflict_version");
    }

    @Override // com.google.android.gms.appstate.AppState
    public int getKey() {
        return getInteger("key");
    }

    @Override // com.google.android.gms.appstate.AppState
    public byte[] getLocalData() {
        return getByteArray("local_data");
    }

    @Override // com.google.android.gms.appstate.AppState
    public String getLocalVersion() {
        return getString("local_version");
    }

    @Override // com.google.android.gms.appstate.AppState
    public boolean hasConflict() {
        return !ai("conflict_version");
    }

    @Override // com.google.android.gms.common.data.b
    public int hashCode() {
        return a.a(this);
    }

    public String toString() {
        return a.b(this);
    }
}
