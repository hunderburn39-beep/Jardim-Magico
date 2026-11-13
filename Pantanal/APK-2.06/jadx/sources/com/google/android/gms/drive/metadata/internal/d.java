package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public class d extends com.google.android.gms.drive.metadata.a<Integer> {
    public d(String str, int i) {
        super(str, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.drive.metadata.a
    public void a(Bundle bundle, Integer num) {
        bundle.putInt(getName(), num.intValue());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.drive.metadata.a
    /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
    public Integer b(DataHolder dataHolder, int i, int i2) {
        return Integer.valueOf(dataHolder.getInteger(getName(), i, i2));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.drive.metadata.a
    /* JADX INFO: renamed from: h, reason: merged with bridge method [inline-methods] */
    public Integer e(Bundle bundle) {
        return Integer.valueOf(bundle.getInt(getName()));
    }
}
