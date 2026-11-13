package com.google.android.gms.internal;

import android.net.Uri;
import android.util.Log;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class kf implements com.google.android.gms.wearable.c {
    private byte[] Nf;
    private Map<String, com.google.android.gms.wearable.d> adD;
    private Set<String> adE;
    private Uri mUri;

    public kf(com.google.android.gms.wearable.c cVar) {
        this.mUri = cVar.getUri();
        this.Nf = cVar.getData();
        HashMap map = new HashMap();
        for (Map.Entry<String, com.google.android.gms.wearable.d> entry : cVar.ma().entrySet()) {
            if (entry.getKey() != null) {
                map.put(entry.getKey(), entry.getValue().freeze());
            }
        }
        this.adD = Collections.unmodifiableMap(map);
        this.adE = Collections.unmodifiableSet(cVar.mb());
    }

    @Override // com.google.android.gms.wearable.c
    public byte[] getData() {
        return this.Nf;
    }

    @Override // com.google.android.gms.wearable.c
    public Uri getUri() {
        return this.mUri;
    }

    @Override // com.google.android.gms.common.data.Freezable
    public boolean isDataValid() {
        return true;
    }

    @Override // com.google.android.gms.wearable.c
    public Map<String, com.google.android.gms.wearable.d> ma() {
        return this.adD;
    }

    @Override // com.google.android.gms.wearable.c
    @Deprecated
    public Set<String> mb() {
        return this.adE;
    }

    @Override // com.google.android.gms.common.data.Freezable
    /* JADX INFO: renamed from: mg, reason: merged with bridge method [inline-methods] */
    public com.google.android.gms.wearable.c freeze() {
        return this;
    }

    public String toString() {
        return toString(Log.isLoggable("DataItem", 3));
    }

    public String toString(boolean verbose) {
        StringBuilder sb = new StringBuilder("DataItemEntity[");
        sb.append("@");
        sb.append(Integer.toHexString(hashCode()));
        sb.append(",dataSz=" + (this.Nf == null ? "null" : Integer.valueOf(this.Nf.length)));
        sb.append(", numAssets=" + this.adD.size());
        sb.append(", uri=" + this.mUri);
        if (!verbose) {
            sb.append("]");
            return sb.toString();
        }
        sb.append("\n  tags=[");
        boolean z = false;
        for (String str : this.adE) {
            if (z) {
                sb.append(", ");
            } else {
                z = true;
            }
            sb.append(str);
        }
        sb.append("]\n  assets: ");
        for (String str2 : this.adD.keySet()) {
            sb.append("\n    " + str2 + ": " + this.adD.get(str2));
        }
        sb.append("\n  ]");
        return sb.toString();
    }
}
