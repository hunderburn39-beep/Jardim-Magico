package com.fyber.utils;

import android.support.annotation.NonNull;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: ViewStringMap.java */
/* JADX INFO: loaded from: classes.dex */
public final class w implements Map<String, String> {
    private final Map<String, Object> a;

    @Override // java.util.Map
    public final /* bridge */ /* synthetic */ String put(String str, String str2) {
        return null;
    }

    @Override // java.util.Map
    public final /* bridge */ /* synthetic */ String remove(Object obj) {
        return null;
    }

    public w(Map<String, Object> map) {
        this.a = map;
    }

    @Override // java.util.Map
    public final void clear() {
    }

    @Override // java.util.Map
    public final boolean containsKey(Object obj) {
        return this.a.containsKey(obj);
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        return false;
    }

    @Override // java.util.Map
    @NonNull
    public final Set<Map.Entry<String, String>> entrySet() {
        return null;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.a.isEmpty();
    }

    @Override // java.util.Map
    @NonNull
    public final Set<String> keySet() {
        return this.a.keySet();
    }

    @Override // java.util.Map
    public final void putAll(Map<? extends String, ? extends String> map) {
    }

    @Override // java.util.Map
    public final int size() {
        return this.a.size();
    }

    @Override // java.util.Map
    @NonNull
    public final Collection<String> values() {
        return Collections.emptyList();
    }

    @Override // java.util.Map
    public final /* synthetic */ String get(Object obj) {
        Object obj2 = this.a.get(obj);
        if (obj2 != null) {
            return obj2.toString();
        }
        return null;
    }
}
