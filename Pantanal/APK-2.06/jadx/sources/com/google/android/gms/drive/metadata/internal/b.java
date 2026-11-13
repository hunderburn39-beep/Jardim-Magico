package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public class b extends com.google.android.gms.drive.metadata.c<Date> {
    public b(String str, int i) {
        super(str, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.drive.metadata.a
    public void a(Bundle bundle, Date date) {
        bundle.putLong(getName(), date.getTime());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.drive.metadata.a
    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
    public Date b(DataHolder dataHolder, int i, int i2) {
        return new Date(dataHolder.getLong(getName(), i, i2));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.drive.metadata.a
    /* JADX INFO: renamed from: g, reason: merged with bridge method [inline-methods] */
    public Date e(Bundle bundle) {
        return new Date(bundle.getLong(getName()));
    }
}
