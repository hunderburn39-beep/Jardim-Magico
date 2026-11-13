package com.fyber.cache;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import com.fyber.cache.internal.a;
import com.fyber.cache.internal.c;
import com.fyber.cache.internal.e;
import com.fyber.cache.internal.f;
import com.fyber.mediation.b;
import com.fyber.utils.FyberLogger;

/* JADX INFO: loaded from: classes.dex */
public class CacheManager {
    private static CacheManager a = new CacheManager();
    private boolean e = false;
    private boolean f = false;
    private boolean g = false;
    private f b = f.a;
    private e d = e.a;
    private a c = a.a;

    private CacheManager() {
    }

    public static CacheManager a() {
        return a;
    }

    public final void a(Context context) {
        final Context applicationContext = context.getApplicationContext();
        new Thread(new Runnable() { // from class: com.fyber.cache.CacheManager.1
            @Override // java.lang.Runnable
            public final void run() {
                if (CacheManager.this.b.equals(f.a)) {
                    CacheManager.this.b = new f(applicationContext);
                    CacheManager.this.d = new e(applicationContext);
                }
                if (CacheManager.this.b.d()) {
                    applicationContext.startService(new Intent(applicationContext, (Class<?>) CacheVideoDownloadService.class));
                } else {
                    FyberLogger.i("FybCacheManager", "Cache is not enabled.");
                }
            }
        }).start();
    }

    public final a b() {
        return this.c;
    }

    public final f c() {
        return this.b;
    }

    public final e d() {
        return this.d;
    }

    public final void a(a aVar) {
        this.c = aVar;
    }

    public final void a(boolean z) {
        this.e = z;
    }

    public final Uri a(String str, Context context) {
        Uri uriBuild;
        FyberLogger.i("FybCacheManager", "Getting URI for URL - " + str);
        c cVarA = a.b.a(str);
        if (cVarA != null && cVarA.c() == 2 && cVarA.a().exists()) {
            uriBuild = Uri.fromFile(cVarA.a());
        } else {
            this.g = true;
            d(context);
            uriBuild = Uri.parse(str);
            if (uriBuild.isRelative() || (Build.VERSION.SDK_INT == 10 && uriBuild.getScheme().equals("https"))) {
                uriBuild = uriBuild.buildUpon().scheme("http").build();
            }
        }
        FyberLogger.i("FybCacheManager", "URI = " + uriBuild);
        return uriBuild;
    }

    public static void startPrecaching(Context context) {
        resumeDownloads(context);
        b.a.a();
    }

    public static void resumeDownloads(Context context) {
        a.f = false;
        a.e(context);
    }

    public static void pauseDownloads(Context context) {
        a.f = true;
        a.d(context);
    }

    public static void unregisterOnVideoCachedListener(OnVideoCachedListener onVideoCachedListener, Context context) {
        try {
            context.unregisterReceiver(onVideoCachedListener);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public static String b(Context context) {
        return context.getApplicationContext().getPackageName() + ".FyberDownloadsFinished";
    }

    public final boolean e() {
        return this.f || this.g;
    }

    public final void c(Context context) {
        this.g = false;
        e(context);
    }

    private void d(Context context) {
        if (this.e) {
            Context applicationContext = context.getApplicationContext();
            FyberLogger.i("FybCacheManager", "The download service is running, let's cancel current downloads");
            Intent intent = new Intent(applicationContext, (Class<?>) CacheVideoDownloadService.class);
            intent.putExtra("action.to.perform", 10);
            applicationContext.startService(intent);
        }
    }

    private void e(Context context) {
        if (this.e) {
            Context applicationContext = context.getApplicationContext();
            Intent intent = new Intent(applicationContext, (Class<?>) CacheVideoDownloadService.class);
            intent.putExtra("action.to.perform", 20);
            applicationContext.startService(intent);
        }
    }

    public static boolean hasCachedVideos() {
        return a.b != f.a && a.b.c() > 0;
    }

    public static void registerOnVideoCachedListener(OnVideoCachedListener onVideoCachedListener, Context context) {
        context.registerReceiver(onVideoCachedListener, new IntentFilter(b(context)));
    }
}
