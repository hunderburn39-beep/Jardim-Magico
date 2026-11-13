package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class i extends com.google.android.gms.drive.metadata.b<String> {
    public i(String str, int i) {
        super(str, i);
    }

    public static final Collection<String> ay(String str) throws JSONException {
        if (str == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        JSONArray jSONArray = new JSONArray(str);
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(jSONArray.getString(i));
        }
        return Collections.unmodifiableCollection(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.drive.metadata.a
    public void a(Bundle bundle, Collection<String> collection) {
        bundle.putStringArrayList(getName(), new ArrayList<>(collection));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.drive.metadata.b, com.google.android.gms.drive.metadata.a
    /* JADX INFO: renamed from: c */
    public Collection<String> b(DataHolder dataHolder, int i, int i2) {
        try {
            return ay(dataHolder.getString(getName(), i, i2));
        } catch (JSONException e) {
            throw new IllegalStateException("DataHolder supplied invalid JSON", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.drive.metadata.a
    /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
    public Collection<String> e(Bundle bundle) {
        return bundle.getStringArrayList(getName());
    }
}
