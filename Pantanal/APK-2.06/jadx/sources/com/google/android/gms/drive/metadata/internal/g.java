package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collection;

/* JADX INFO: loaded from: classes.dex */
public class g<T extends Parcelable> extends com.google.android.gms.drive.metadata.b<T> {
    public g(String str, int i) {
        super(str, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.drive.metadata.a
    public void a(Bundle bundle, Collection<T> collection) {
        bundle.putParcelableArrayList(getName(), new ArrayList<>(collection));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.drive.metadata.a
    /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
    public Collection<T> e(Bundle bundle) {
        return bundle.getParcelableArrayList(getName());
    }
}
