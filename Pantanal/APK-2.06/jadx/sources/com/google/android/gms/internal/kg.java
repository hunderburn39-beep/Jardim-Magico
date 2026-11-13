package com.google.android.gms.internal;

import android.net.Uri;
import com.google.android.gms.common.data.DataHolder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class kg extends com.google.android.gms.common.data.b implements com.google.android.gms.wearable.c {
    private final int LE;

    public kg(DataHolder dataHolder, int i, int i2) {
        super(dataHolder, i);
        this.LE = i2;
    }

    @Override // com.google.android.gms.wearable.c
    public byte[] getData() {
        return getByteArray("data");
    }

    @Override // com.google.android.gms.wearable.c
    public Uri getUri() {
        return Uri.parse(getString("path"));
    }

    @Override // com.google.android.gms.wearable.c
    public Map<String, com.google.android.gms.wearable.d> ma() {
        HashMap map = new HashMap(this.LE);
        for (int i = 0; i < this.LE; i++) {
            ke keVar = new ke(this.BB, this.BD + i);
            if (keVar.mc() != null) {
                map.put(keVar.mc(), keVar);
            }
        }
        return map;
    }

    @Override // com.google.android.gms.wearable.c
    @Deprecated
    public Set<String> mb() {
        HashSet hashSet = new HashSet();
        String string = getString("tags");
        if (string != null) {
            String[] strArrSplit = string.split("\\|");
            for (String str : strArrSplit) {
                hashSet.add(str);
            }
        }
        return hashSet;
    }

    @Override // com.google.android.gms.common.data.Freezable
    /* JADX INFO: renamed from: mg, reason: merged with bridge method [inline-methods] */
    public com.google.android.gms.wearable.c freeze() {
        return new kf(this);
    }
}
