package com.google.android.gms.wearable;

import android.net.Uri;
import com.google.android.gms.common.data.Freezable;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public interface c extends Freezable<c> {
    byte[] getData();

    Uri getUri();

    Map<String, d> ma();

    @Deprecated
    Set<String> mb();
}
