package com.tapjoy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.text.TextUtils;
import com.tapjoy.internal.eh;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class TapjoyCache {
    public static final String CACHE_DIRECTORY_NAME = "Tapjoy/Cache/";
    public static final int CACHE_LIMIT = -1;
    private Context b;
    private TapjoyCacheMap c;
    private Vector d;
    private ExecutorService e;
    private File f;
    public static boolean unit_test_mode = false;
    private static TapjoyCache a = null;

    public TapjoyCache(Context applicationContext) {
        if (a == null || unit_test_mode) {
            a = this;
            this.b = applicationContext;
            this.c = new TapjoyCacheMap(applicationContext, -1);
            this.d = new Vector();
            this.e = Executors.newFixedThreadPool(5);
            if (Environment.getExternalStorageDirectory() != null) {
                TapjoyUtil.deleteFileOrDirectory(new File(Environment.getExternalStorageDirectory(), "tapjoy"));
                TapjoyUtil.deleteFileOrDirectory(new File(Environment.getExternalStorageDirectory(), "tjcache/tmp/"));
            }
            this.f = new File(this.b.getFilesDir() + "/Tapjoy/Cache/");
            if (!this.f.exists()) {
                if (this.f.mkdirs()) {
                    TapjoyLog.d("TapjoyCache", "Created directory at: " + this.f.getPath());
                } else {
                    TapjoyLog.e("TapjoyCache", "Error initalizing cache");
                    a = null;
                }
            }
            a();
        }
    }

    private void a() {
        SharedPreferences sharedPreferences = this.b.getSharedPreferences(TapjoyConstants.PREF_TAPJOY_CACHE, 0);
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            File file = new File(entry.getKey());
            if (file.exists() && file.isFile()) {
                TapjoyCachedAssetData tapjoyCachedAssetDataFromRawJSONString = TapjoyCachedAssetData.fromRawJSONString(entry.getValue().toString());
                if (tapjoyCachedAssetDataFromRawJSONString != null) {
                    TapjoyLog.d("TapjoyCache", "Loaded Asset: " + tapjoyCachedAssetDataFromRawJSONString.getAssetURL());
                    String strB = b(tapjoyCachedAssetDataFromRawJSONString.getAssetURL());
                    if (strB == null || "".equals(strB) || strB.length() <= 0) {
                        TapjoyLog.e("TapjoyCache", "Removing asset because deserialization failed.");
                        editorEdit.remove(entry.getKey()).commit();
                    } else if (tapjoyCachedAssetDataFromRawJSONString.getTimeOfDeathInSeconds() < System.currentTimeMillis() / 1000) {
                        TapjoyLog.d("TapjoyCache", "Asset expired, removing from cache: " + tapjoyCachedAssetDataFromRawJSONString.getAssetURL());
                        if (tapjoyCachedAssetDataFromRawJSONString.getLocalFilePath() != null && tapjoyCachedAssetDataFromRawJSONString.getLocalFilePath().length() > 0) {
                            TapjoyUtil.deleteFileOrDirectory(new File(tapjoyCachedAssetDataFromRawJSONString.getLocalFilePath()));
                        }
                    } else {
                        this.c.put(strB, tapjoyCachedAssetDataFromRawJSONString);
                    }
                } else {
                    TapjoyLog.e("TapjoyCache", "Removing asset because deserialization failed.");
                    editorEdit.remove(entry.getKey()).commit();
                }
            } else {
                TapjoyLog.d("TapjoyCache", "Removing reference to missing asset: " + entry.getKey());
                editorEdit.remove(entry.getKey()).commit();
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.tapjoy.TapjoyCache$1] */
    public void cacheAssetGroup(final JSONArray assetGroup, final TJCacheListener tapjoyCacheListener) {
        if (assetGroup != null && assetGroup.length() > 0) {
            new Thread() { // from class: com.tapjoy.TapjoyCache.1
                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    TapjoyLog.d("TapjoyCache", "Starting to cache asset group size of " + assetGroup.length());
                    ArrayList arrayList = new ArrayList();
                    int i = 1;
                    for (int i2 = 0; i2 < assetGroup.length(); i2++) {
                        try {
                            Future futureCacheAssetFromJSONObject = TapjoyCache.this.cacheAssetFromJSONObject(assetGroup.getJSONObject(i2));
                            if (futureCacheAssetFromJSONObject != null) {
                                arrayList.add(futureCacheAssetFromJSONObject);
                            }
                        } catch (JSONException e) {
                            TapjoyLog.e("TapjoyCache", "Failed to load JSON object from JSONArray");
                        }
                    }
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        try {
                            i = !((Boolean) ((Future) it.next()).get()).booleanValue() ? 2 : i;
                        } catch (InterruptedException e2) {
                            TapjoyLog.e("TapjoyCache", "Caching thread failed: " + e2.toString());
                            i = 2;
                        } catch (ExecutionException e3) {
                            TapjoyLog.e("TapjoyCache", "Caching thread failed: " + e3.toString());
                            i = 2;
                        }
                    }
                    TapjoyLog.d("TapjoyCache", "Finished caching group");
                    if (tapjoyCacheListener != null) {
                        tapjoyCacheListener.onCachingComplete(i);
                    }
                }
            }.start();
        } else if (tapjoyCacheListener != null) {
            tapjoyCacheListener.onCachingComplete(1);
        }
    }

    public Future cacheAssetFromJSONObject(JSONObject assetData) {
        try {
            String string = assetData.getString("url");
            Long.valueOf(86400L);
            return cacheAssetFromURL(string, assetData.optString(TapjoyConstants.TJC_PLACEMENT_OFFER_ID), Long.valueOf(assetData.optLong(TapjoyConstants.TJC_TIME_TO_LIVE)).longValue());
        } catch (JSONException e) {
            TapjoyLog.e("TapjoyCache", "Required parameters to cache an asset from JSON is not present");
            return null;
        }
    }

    public Future cacheAssetFromURL(String assetURLString, String offerId, long timeToLive) {
        try {
            URL url = new URL(assetURLString);
            if (this.d.contains(b(assetURLString))) {
                TapjoyLog.d("TapjoyCache", "URL is already in the process of being cached: " + assetURLString);
                return null;
            }
            return startCachingThread(url, offerId, timeToLive);
        } catch (MalformedURLException e) {
            TapjoyLog.d("TapjoyCache", "Invalid cache assetURL");
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(String str) {
        if (str.startsWith("//")) {
            str = "http:" + str;
        }
        try {
            return new URL(str).getFile();
        } catch (MalformedURLException e) {
            TapjoyLog.e("TapjoyCache", "Invalid URL " + str);
            return "";
        }
    }

    public Future startCachingThread(URL assetURL, String offerId, long timeToLive) {
        if (assetURL != null) {
            return this.e.submit(new CacheAssetThread(assetURL, offerId, timeToLive));
        }
        return null;
    }

    public void clearTapjoyCache() {
        TapjoyLog.d("TapjoyCache", "Cleaning Tapjoy cache!");
        TapjoyUtil.deleteFileOrDirectory(this.f);
        if (this.f.mkdirs()) {
            TapjoyLog.d("TapjoyCache", "Created new cache directory at: " + this.f.getPath());
        }
        this.c = new TapjoyCacheMap(this.b, -1);
    }

    public boolean removeAssetFromCache(String url) {
        String strB = b(url);
        return (strB == "" || this.c.remove((Object) strB) == null) ? false : true;
    }

    public boolean isURLDownloading(String url) {
        String strB;
        return (this.d == null || (strB = b(url)) == "" || !this.d.contains(strB)) ? false : true;
    }

    public boolean isURLCached(String url) {
        return this.c.get(b(url)) != null;
    }

    public TapjoyCachedAssetData getCachedDataForURL(String url) {
        String strB = b(url);
        if (strB != "") {
            return (TapjoyCachedAssetData) this.c.get(strB);
        }
        return null;
    }

    public TapjoyCacheMap getCachedData() {
        return this.c;
    }

    public String getPathOfCachedURL(String url) {
        String strB = b(url);
        if (strB != "" && this.c.containsKey(strB)) {
            TapjoyCachedAssetData tapjoyCachedAssetData = (TapjoyCachedAssetData) this.c.get(strB);
            if (new File(tapjoyCachedAssetData.getLocalFilePath()).exists()) {
                return tapjoyCachedAssetData.getLocalURL();
            }
            getInstance().removeAssetFromCache(url);
            return url;
        }
        return url;
    }

    public String cachedAssetsToJSON() {
        JSONObject jSONObject = new JSONObject();
        for (Map.Entry entry : this.c.entrySet()) {
            try {
                jSONObject.put(((String) entry.getKey()).toString(), ((TapjoyCachedAssetData) entry.getValue()).toRawJSONString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONObject.toString();
    }

    public String getCachedOfferIDs() {
        ArrayList arrayList = new ArrayList();
        if (this.c == null) {
            return "";
        }
        Iterator it = this.c.entrySet().iterator();
        while (it.hasNext()) {
            String offerId = ((TapjoyCachedAssetData) ((Map.Entry) it.next()).getValue()).getOfferId();
            if (offerId != null && offerId.length() != 0 && !arrayList.contains(offerId)) {
                arrayList.add(offerId);
            }
        }
        return TextUtils.join(",", arrayList);
    }

    public void printCacheInformation() {
        TapjoyLog.d("TapjoyCache", "------------- Cache Data -------------");
        TapjoyLog.d("TapjoyCache", "Number of files in cache: " + this.c.size());
        TapjoyLog.d("TapjoyCache", "Cache Size: " + TapjoyUtil.fileOrDirectorySize(this.f));
        TapjoyLog.d("TapjoyCache", "--------------------------------------");
    }

    public static TapjoyCache getInstance() {
        return a;
    }

    public static void setInstance(TapjoyCache cache) {
        a = cache;
    }

    public class CacheAssetThread implements Callable {
        private URL b;
        private String c;
        private long d;

        public CacheAssetThread(URL assetURL, String offerId, long timeToLive) {
            this.b = assetURL;
            this.c = offerId;
            this.d = timeToLive;
            if (this.d <= 0) {
                this.d = 86400L;
            }
            TapjoyCache.this.d.add(TapjoyCache.b(this.b.toString()));
        }

        /* JADX WARN: Code duplicated, block: B:113:? A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:82:0x01e5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:84:0x01e0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v0 */
        /* JADX WARN: Type inference failed for: r2v1 */
        /* JADX WARN: Type inference failed for: r2v10 */
        /* JADX WARN: Type inference failed for: r2v11 */
        /* JADX WARN: Type inference failed for: r2v13, types: [java.lang.StringBuilder] */
        /* JADX WARN: Type inference failed for: r2v2, types: [java.io.BufferedOutputStream] */
        /* JADX WARN: Type inference failed for: r2v20 */
        /* JADX WARN: Type inference failed for: r2v21 */
        /* JADX WARN: Type inference failed for: r2v22 */
        /* JADX WARN: Type inference failed for: r2v23 */
        /* JADX WARN: Type inference failed for: r2v3, types: [java.io.BufferedOutputStream] */
        /* JADX WARN: Type inference failed for: r2v4, types: [java.io.BufferedInputStream] */
        /* JADX WARN: Type inference failed for: r2v5 */
        /* JADX WARN: Type inference failed for: r2v7 */
        /* JADX WARN: Type inference failed for: r2v8 */
        /* JADX WARN: Type inference failed for: r2v9 */
        /* JADX WARN: Type inference failed for: r3v11 */
        /* JADX WARN: Type inference failed for: r3v12, types: [java.io.BufferedInputStream] */
        /* JADX WARN: Type inference failed for: r3v13, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r3v3, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r3v4 */
        /* JADX WARN: Type inference failed for: r3v5 */
        /* JADX WARN: Type inference failed for: r3v6 */
        /* JADX WARN: Type inference failed for: r3v7, types: [java.io.BufferedInputStream] */
        /* JADX WARN: Type inference failed for: r3v8, types: [java.io.BufferedInputStream] */
        /* JADX WARN: Type inference failed for: r3v9 */
        @Override // java.util.concurrent.Callable
        public Boolean call() throws Throwable {
            BufferedOutputStream bufferedOutputStream;
            boolean z;
            ?? sb = 0;
            sb = 0;
            ?? r2 = 0;
            sb = 0;
            sb = 0;
            String strB = TapjoyCache.b(this.b.toString());
            if (TapjoyCache.this.c.containsKey(strB)) {
                if (new File(((TapjoyCachedAssetData) TapjoyCache.this.c.get(strB)).getLocalFilePath()).exists()) {
                    if (this.d != 0) {
                        ((TapjoyCachedAssetData) TapjoyCache.this.c.get(strB)).resetTimeToLive(this.d);
                    } else {
                        ((TapjoyCachedAssetData) TapjoyCache.this.c.get(strB)).resetTimeToLive(86400L);
                    }
                    TapjoyLog.d("TapjoyCache", "Reseting time to live for " + this.b.toString());
                    TapjoyCache.this.d.remove(strB);
                    return true;
                }
                TapjoyCache.getInstance().removeAssetFromCache(strB);
            }
            System.currentTimeMillis();
            try {
                File file = new File(TapjoyCache.this.f + "/" + TapjoyUtil.SHA256(strB));
                ?? bufferedInputStream = " to ";
                TapjoyLog.d("TapjoyCache", "Downloading and caching asset from: " + this.b + " to " + file);
                try {
                    try {
                        URLConnection uRLConnectionA = eh.a(this.b);
                        uRLConnectionA.setConnectTimeout(15000);
                        uRLConnectionA.setReadTimeout(30000);
                        uRLConnectionA.connect();
                        bufferedInputStream = new BufferedInputStream(uRLConnectionA.getInputStream());
                        try {
                            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                            try {
                                TapjoyUtil.writeFileToDevice(bufferedInputStream, bufferedOutputStream);
                                try {
                                    bufferedInputStream.close();
                                } catch (IOException e) {
                                }
                                try {
                                    bufferedOutputStream.close();
                                } catch (IOException e2) {
                                }
                                TapjoyCachedAssetData tapjoyCachedAssetData = new TapjoyCachedAssetData(this.b.toString(), file.getAbsolutePath(), this.d);
                                if (this.c != null) {
                                    tapjoyCachedAssetData.setOfferID(this.c);
                                }
                                TapjoyCache.this.c.put(strB, tapjoyCachedAssetData);
                                TapjoyCache.this.d.remove(strB);
                                bufferedInputStream = "----- Download complete -----";
                                sb = new StringBuilder("----- Download complete -----");
                                TapjoyLog.d("TapjoyCache", sb.append(tapjoyCachedAssetData.toString()).toString());
                                z = true;
                            } catch (SocketTimeoutException e3) {
                                e = e3;
                                r2 = bufferedInputStream;
                                try {
                                    TapjoyLog.e("TapjoyCache", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.NETWORK_ERROR, "Network timeout during caching: " + e.toString()));
                                    TapjoyCache.this.d.remove(strB);
                                    TapjoyUtil.deleteFileOrDirectory(file);
                                    if (r2 != 0) {
                                        try {
                                            r2.close();
                                        } catch (IOException e4) {
                                        }
                                    }
                                    if (bufferedOutputStream == null) {
                                        return false;
                                    }
                                    try {
                                        bufferedOutputStream.close();
                                        return false;
                                    } catch (IOException e5) {
                                        return false;
                                    }
                                } catch (Throwable th) {
                                    th = th;
                                    bufferedInputStream = r2;
                                    sb = bufferedOutputStream;
                                    if (bufferedInputStream != 0) {
                                        try {
                                            bufferedInputStream.close();
                                        } catch (IOException e6) {
                                        }
                                    }
                                    if (sb != 0) {
                                        throw th;
                                    }
                                    try {
                                        sb.close();
                                        throw th;
                                    } catch (IOException e7) {
                                        throw th;
                                    }
                                }
                            } catch (Exception e8) {
                                e = e8;
                                sb = bufferedOutputStream;
                                TapjoyLog.e("TapjoyCache", "Error caching asset: " + e.toString());
                                TapjoyCache.this.d.remove(strB);
                                TapjoyUtil.deleteFileOrDirectory(file);
                                z = false;
                                if (bufferedInputStream != 0) {
                                    try {
                                        bufferedInputStream.close();
                                    } catch (IOException e9) {
                                    }
                                }
                                if (sb != 0) {
                                    try {
                                        sb.close();
                                    } catch (IOException e10) {
                                    }
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                sb = bufferedOutputStream;
                                if (bufferedInputStream != 0) {
                                    bufferedInputStream.close();
                                }
                                if (sb != 0) {
                                    throw th;
                                }
                                sb.close();
                                throw th;
                            }
                        } catch (SocketTimeoutException e11) {
                            e = e11;
                            bufferedOutputStream = null;
                            r2 = bufferedInputStream;
                        } catch (Exception e12) {
                            e = e12;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                    }
                } catch (SocketTimeoutException e13) {
                    e = e13;
                    bufferedOutputStream = null;
                } catch (Exception e14) {
                    e = e14;
                    bufferedInputStream = 0;
                } catch (Throwable th4) {
                    th = th4;
                    bufferedInputStream = 0;
                }
                return z;
            } catch (Exception e15) {
                TapjoyCache.this.d.remove(strB);
                return false;
            }
        }
    }
}
