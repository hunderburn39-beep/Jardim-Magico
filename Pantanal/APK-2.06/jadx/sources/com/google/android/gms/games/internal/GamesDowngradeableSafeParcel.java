package com.google.android.gms.games.internal;

import com.google.android.gms.internal.fe;
import com.google.android.gms.internal.go;

/* JADX INFO: loaded from: classes.dex */
public abstract class GamesDowngradeableSafeParcel extends fe {
    protected static boolean c(Integer num) {
        if (num == null) {
            return false;
        }
        return go.aa(num.intValue());
    }
}
