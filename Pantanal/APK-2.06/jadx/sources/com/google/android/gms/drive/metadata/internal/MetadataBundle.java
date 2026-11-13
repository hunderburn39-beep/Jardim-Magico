package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.fq;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class MetadataBundle implements SafeParcelable {
    public static final Parcelable.Creator<MetadataBundle> CREATOR = new f();
    final Bundle FQ;
    final int xH;

    MetadataBundle(int versionCode, Bundle valueBundle) {
        this.xH = versionCode;
        this.FQ = (Bundle) fq.f(valueBundle);
        this.FQ.setClassLoader(getClass().getClassLoader());
        ArrayList arrayList = new ArrayList();
        for (String str : this.FQ.keySet()) {
            if (c.ax(str) == null) {
                arrayList.add(str);
                Log.w("MetadataBundle", "Ignored unknown metadata field in bundle: " + str);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            this.FQ.remove((String) it.next());
        }
    }

    private MetadataBundle(Bundle valueBundle) {
        this(1, valueBundle);
    }

    public static <T> MetadataBundle a(MetadataField<T> metadataField, T t) {
        MetadataBundle metadataBundleFT = fT();
        metadataBundleFT.b(metadataField, t);
        return metadataBundleFT;
    }

    public static MetadataBundle a(MetadataBundle metadataBundle) {
        return new MetadataBundle(new Bundle(metadataBundle.FQ));
    }

    public static MetadataBundle fT() {
        return new MetadataBundle(new Bundle());
    }

    public <T> T a(MetadataField<T> metadataField) {
        return metadataField.d(this.FQ);
    }

    public <T> void b(MetadataField<T> metadataField, T t) {
        if (c.ax(metadataField.getName()) == null) {
            throw new IllegalArgumentException("Unregistered field: " + metadataField.getName());
        }
        metadataField.a(t, this.FQ);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MetadataBundle)) {
            return false;
        }
        MetadataBundle metadataBundle = (MetadataBundle) obj;
        Set<String> setKeySet = this.FQ.keySet();
        if (!setKeySet.equals(metadataBundle.FQ.keySet())) {
            return false;
        }
        for (String str : setKeySet) {
            if (!fo.equal(this.FQ.get(str), metadataBundle.FQ.get(str))) {
                return false;
            }
        }
        return true;
    }

    public Set<MetadataField<?>> fU() {
        HashSet hashSet = new HashSet();
        Iterator<String> it = this.FQ.keySet().iterator();
        while (it.hasNext()) {
            hashSet.add(c.ax(it.next()));
        }
        return hashSet;
    }

    public int hashCode() {
        int iHashCode = 1;
        Iterator<String> it = this.FQ.keySet().iterator();
        while (true) {
            int i = iHashCode;
            if (!it.hasNext()) {
                return i;
            }
            iHashCode = this.FQ.get(it.next()).hashCode() + (i * 31);
        }
    }

    public String toString() {
        return "MetadataBundle [values=" + this.FQ + "]";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        f.a(this, dest, flags);
    }
}
