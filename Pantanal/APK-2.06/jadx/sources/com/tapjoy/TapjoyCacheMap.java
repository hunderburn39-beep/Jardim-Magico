package com.tapjoy;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
public class TapjoyCacheMap extends ConcurrentHashMap {
    private Context a;
    private int b;

    public TapjoyCacheMap(Context applicationContext, int cacheLimit) {
        this.b = -1;
        this.a = applicationContext;
        this.b = cacheLimit;
    }

    private String a() {
        String str;
        long j;
        long j2 = -1;
        String str2 = "";
        for (Map.Entry entry : entrySet()) {
            long timestampInSeconds = ((TapjoyCachedAssetData) entry.getValue()).getTimestampInSeconds();
            if (j2 == 0 || timestampInSeconds < j2) {
                str = (String) entry.getKey();
                j = timestampInSeconds;
            } else {
                str = str2;
                j = j2;
            }
            j2 = j;
            str2 = str;
        }
        return str2;
    }

    @Override // java.util.concurrent.ConcurrentHashMap, java.util.AbstractMap, java.util.Map
    public TapjoyCachedAssetData put(String key, TapjoyCachedAssetData assetData) {
        TapjoyLog.d("TapjoyCacheMap", "TapjoyCacheMap::put() -- key: " + key + " assetURL: " + assetData.getAssetURL());
        if (assetData == null || assetData.getTimeOfDeathInSeconds() <= System.currentTimeMillis() / 1000) {
            return null;
        }
        if (size() == this.b) {
            remove((Object) a());
        }
        SharedPreferences.Editor editorEdit = this.a.getSharedPreferences(TapjoyConstants.PREF_TAPJOY_CACHE, 0).edit();
        editorEdit.putString(assetData.getLocalFilePath(), assetData.toRawJSONString());
        editorEdit.commit();
        return (TapjoyCachedAssetData) super.put(key, assetData);
    }

    @Override // java.util.concurrent.ConcurrentHashMap, java.util.AbstractMap, java.util.Map
    public TapjoyCachedAssetData remove(Object key) {
        if (!containsKey(key)) {
            return null;
        }
        SharedPreferences.Editor editorEdit = this.a.getSharedPreferences(TapjoyConstants.PREF_TAPJOY_CACHE, 0).edit();
        editorEdit.remove(((TapjoyCachedAssetData) get(key)).getLocalFilePath());
        editorEdit.commit();
        String localFilePath = ((TapjoyCachedAssetData) get(key)).getLocalFilePath();
        if (localFilePath != null && localFilePath.length() > 0) {
            TapjoyUtil.deleteFileOrDirectory(new File(localFilePath));
        }
        TapjoyLog.d("TapjoyCacheMap", "TapjoyCacheMap::remove() -- key: " + key);
        return (TapjoyCachedAssetData) super.remove(key);
    }

    @Override // java.util.concurrent.ConcurrentHashMap, java.util.Map, java.util.concurrent.ConcurrentMap
    public boolean replace(String key, TapjoyCachedAssetData oldValue, TapjoyCachedAssetData newValue) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.concurrent.ConcurrentHashMap, java.util.Map, java.util.concurrent.ConcurrentMap
    public TapjoyCachedAssetData replace(String key, TapjoyCachedAssetData value) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.concurrent.ConcurrentHashMap, java.util.AbstractMap, java.util.Map
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
