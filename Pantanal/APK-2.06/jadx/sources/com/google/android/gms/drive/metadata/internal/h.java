package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import android.os.Parcelable;
import java.util.Collection;

/* JADX INFO: loaded from: classes.dex */
public abstract class h<T extends Parcelable> extends com.google.android.gms.drive.metadata.a<T> {
    public h(String str, int i) {
        super(str, i);
    }

    public h(String str, Collection<String> collection, int i) {
        super(str, collection, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.drive.metadata.a
    public void a(Bundle bundle, T t) {
        bundle.putParcelable(getName(), t);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.drive.metadata.a
    /* JADX INFO: renamed from: k, reason: merged with bridge method [inline-methods] */
    public T e(Bundle bundle) {
        return (T) bundle.getParcelable(getName());
    }
}
