package com.fyber.cache;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import com.fyber.Fyber;
import com.fyber.cache.internal.ConfigurationBroadcastReceiver;
import com.fyber.cache.internal.f;
import com.fyber.cache.internal.g;
import com.fyber.cache.internal.h;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.e;
import com.fyber.utils.i;
import com.fyber.utils.j;
import com.fyber.utils.s;
import com.tapjoy.TapjoyConstants;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class CacheVideoDownloadService extends Service {
    private static ConfigurationBroadcastReceiver a;
    private b b;
    private c c;
    private a d;
    private boolean e = false;
    private volatile int f = d.a;
    private volatile boolean g = false;
    private BroadcastReceiver h = new BroadcastReceiver() { // from class: com.fyber.cache.CacheVideoDownloadService.1
        private boolean b = true;

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            NetworkInfo networkInfo;
            if (intent.getExtras() != null && (networkInfo = (NetworkInfo) intent.getExtras().get("networkInfo")) != null) {
                if (this.b) {
                    this.b = false;
                    return;
                }
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    com.fyber.cache.internal.a.EnumC0023a enumC0023aE = CacheVideoDownloadService.e(CacheVideoDownloadService.this);
                    if (enumC0023aE != null) {
                        CacheVideoDownloadService.this.d.removeMessages(1310);
                        FyberLogger.i("CacheVideoDownloadService", "Network connection changed to " + enumC0023aE.name());
                        CacheVideoDownloadService.this.d.sendMessageDelayed(CacheVideoDownloadService.this.d.obtainMessage(1310, enumC0023aE), 1000L);
                        return;
                    }
                    return;
                }
                FyberLogger.i("CacheVideoDownloadService", "Connectivity lost");
                if (!CacheVideoDownloadService.this.d.hasMessages(1300)) {
                    CacheVideoDownloadService.this.d.sendMessageAtFrontOfQueue(CacheVideoDownloadService.this.d.obtainMessage(1300));
                }
            }
        }
    };

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    private static final class d {
        public static final int a = 1;
        public static final int b = 2;
        public static final int c = 3;
        private static final /* synthetic */ int[] d = {a, b, c};
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        if (a == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(a());
            a = new ConfigurationBroadcastReceiver();
            getApplicationContext().registerReceiver(a, intentFilter);
        }
        HandlerThread handlerThread = new HandlerThread("ServiceDispatcherThread", 1);
        handlerThread.start();
        this.b = new b(handlerThread.getLooper());
        if (CacheManager.a().c().d()) {
            CacheManager.a().a(true);
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        FyberLogger.i("CacheVideoDownloadService", "onStartCommand called on the service");
        if (intent != null) {
            int intExtra = intent.getIntExtra("action.to.perform", 0);
            if (!CacheManager.a().c().d() || (!this.e && intExtra != 0 && this.f == d.a)) {
                this.b.sendEmptyMessage(100);
                return 2;
            }
            switch (intExtra) {
                case 10:
                    this.f = d.b;
                    this.b.sendEmptyMessage(300);
                    break;
                case TapjoyConstants.MEDIATION_TIMEOUT_INTERVAL /* 20 */:
                    int i3 = this.f;
                    this.f = d.a;
                    if (CacheManager.a().e()) {
                        this.f = d.c;
                    } else if (i3 == d.c || !this.e) {
                        this.b.sendEmptyMessage(10);
                    } else {
                        this.d.sendEmptyMessage(200);
                    }
                    break;
                default:
                    if (Fyber.getConfigs().d()) {
                        if (CacheManager.a().e()) {
                            this.f = d.c;
                        } else {
                            this.b.sendEmptyMessage(10);
                        }
                    } else {
                        FyberLogger.i("CacheVideoDownloadService", "The SDK appears to not have been started yet...");
                        this.b.sendEmptyMessage(100);
                    }
                    break;
            }
            return 2;
        }
        FyberLogger.e("CacheVideoDownloadService", "Intent is null. Service is unable to start.");
        if (this.b != null) {
            this.b.sendEmptyMessage(100);
            return 2;
        }
        stopSelf();
        return 2;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        FyberLogger.i("CacheVideoDownloadService", "The service will shutdown");
        CacheManager.a().a(false);
        if (this.b != null) {
            this.b.getLooper().quit();
        }
        if (this.e) {
            this.d.getLooper().quit();
            this.c.getLooper().quit();
            getApplicationContext().unregisterReceiver(this.h);
        }
    }

    private final class b extends Handler {
        public b(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            switch (message.what) {
                case 10:
                    CacheVideoDownloadService.this.f = d.c;
                    a();
                    if (!CacheVideoDownloadService.this.e) {
                        CacheVideoDownloadService.c(CacheVideoDownloadService.this);
                    }
                    CacheVideoDownloadService.this.c.sendEmptyMessage(10);
                    break;
                case 100:
                    a();
                    Intent intent = new Intent(CacheManager.b(CacheVideoDownloadService.this.getApplicationContext()));
                    CacheManager.a();
                    intent.putExtra(OnVideoCachedListener.EXTRA_VIDEOS_AVAILABLE, CacheManager.hasCachedVideos());
                    CacheVideoDownloadService.this.sendBroadcast(intent);
                    CacheVideoDownloadService.this.stopSelf();
                    break;
                case 300:
                    a();
                    break;
            }
        }

        private void a() {
            if (CacheVideoDownloadService.this.c != null) {
                CacheVideoDownloadService.this.c.a();
            }
        }
    }

    private final class c extends Handler {
        private j.a b;
        private boolean c;

        public c(Looper looper) {
            super(looper);
            this.c = false;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            String strC;
            switch (message.what) {
                case 10:
                    FyberLogger.i("CacheVideoDownloadService", "Download handler - Downloading config...");
                    try {
                        String strG = s.a(e.a("precaching"), Fyber.getConfigs().e()).g();
                        FyberLogger.i("CacheVideoDownloadService", "Download handler - Config will be fetched from - " + strG);
                        i iVarA = i.b(strG).a();
                        int iB = iVarA.b();
                        if (iB < 200 || iB >= 300) {
                            strC = null;
                        } else {
                            strC = iVarA.c();
                        }
                    } catch (IOException | NullPointerException e) {
                        FyberLogger.e("CacheVideoDownloadService", "An error occurred", e);
                    }
                    CacheVideoDownloadService.this.d.obtainMessage(1100, strC).sendToTarget();
                    break;
                case 200:
                    CacheVideoDownloadService.this.g = true;
                    FyberLogger.i("CacheVideoDownloadService", "Download handler - Downloading video...");
                    com.fyber.cache.internal.c cVar = (com.fyber.cache.internal.c) message.obj;
                    int iA = a(cVar);
                    FyberLogger.i("CacheVideoDownloadService", "Download handler - Video state = " + iA);
                    CacheVideoDownloadService.this.g = false;
                    Message messageObtainMessage = CacheVideoDownloadService.this.d.obtainMessage(1160, message.arg1, iA, cVar.b());
                    if (this.c) {
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("canceled", true);
                        messageObtainMessage.setData(bundle);
                        this.c = false;
                    }
                    messageObtainMessage.sendToTarget();
                    break;
            }
        }

        private synchronized int a(com.fyber.cache.internal.c cVar) {
            int i;
            FyberLogger.i("CacheVideoDownloadService", "Downloading video from URL: " + cVar.b());
            File fileA = cVar.a();
            if (a(fileA)) {
                if (fileA.canWrite()) {
                    try {
                        this.b = j.a(cVar.b(), fileA).a(cVar.c() == 1 || fileA.length() > 0).a().c();
                        try {
                            if (this.b.a()) {
                                this.b = null;
                                CacheManager.a().d().b();
                                i = 2;
                            }
                        } catch (j.a.C0028a | j.a.b e) {
                            this.b = null;
                            i = 4;
                        }
                    } catch (IOException e2) {
                        FyberLogger.i("CacheVideoDownloadService", "Video downloading from URL: " + cVar.b() + " has been interrupted.");
                        FyberLogger.e("CacheVideoDownloadService", "An error occurred while downloading the videos: " + e2.getMessage());
                    }
                } else {
                    FyberLogger.i("CacheVideoDownloadService", "No permission granted to write to: " + fileA.getAbsolutePath());
                }
                this.b = null;
                i = 1;
            } else {
                i = 4;
            }
            return i;
        }

        private static boolean a(@NonNull File file) {
            if (!file.exists()) {
                try {
                    if (!file.createNewFile()) {
                        FyberLogger.i("CacheVideoDownloadService", "Cache File with path: " + file.getAbsolutePath() + " has not been created");
                        return false;
                    }
                } catch (IOException e) {
                    FyberLogger.i("CacheVideoDownloadService", "impossible to create cache File with path: " + file.getAbsolutePath());
                    FyberLogger.i("CacheVideoDownloadService", "error creating cache File: " + e.getMessage());
                    return false;
                }
            }
            return true;
        }

        public final void a() {
            removeMessages(200);
            removeMessages(10);
            if (this.b != null) {
                this.c = true;
                FyberLogger.i("CacheVideoDownloadService", "Download handler - canceling downloads");
                this.b.b();
                this.b = null;
            }
        }
    }

    private final class a extends Handler {
        private SparseArray<h> b;
        private int c;

        public a(Looper looper) {
            super(looper);
            this.b = new SparseArray<>();
            this.c = 0;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int iB;
            boolean z = false;
            switch (message.what) {
                case 200:
                    if (CacheVideoDownloadService.this.f == d.a) {
                        if (!b()) {
                            if (!CacheVideoDownloadService.this.g) {
                                a();
                            }
                        } else {
                            FyberLogger.i("CacheVideoDownloadService", "No more videos to download \\m/");
                            CacheVideoDownloadService.this.b.sendEmptyMessage(100);
                        }
                    }
                    break;
                case 1100:
                    Object obj = message.obj;
                    CacheVideoDownloadService.this.f = d.a;
                    this.c = 0;
                    this.b.clear();
                    if (obj != null) {
                        FyberLogger.i("CacheVideoDownloadService", "Cache config received, parsing...");
                        g gVarA = g.a(obj.toString());
                        com.fyber.cache.internal.b bVarA = gVarA.a();
                        if (bVarA != null) {
                            FyberLogger.i("CacheVideoDownloadService", "Creating cache configuration object");
                            com.fyber.cache.internal.a aVarA = bVarA.a();
                            CacheManager.a().a(aVarA);
                            ArrayList<h> arrayListB = gVarA.b();
                            com.fyber.cache.internal.a.EnumC0023a enumC0023aE = CacheVideoDownloadService.e(CacheVideoDownloadService.this);
                            if (enumC0023aE != null) {
                                FyberLogger.i("CacheVideoDownloadService", "Max downloading this many videos on this network type: " + aVarA.a(enumC0023aE).a());
                                int iC = aVarA.c();
                                if (arrayListB.isEmpty()) {
                                    z = true;
                                } else {
                                    a(arrayListB);
                                    c();
                                    CacheManager.a().c().a(iC);
                                    sendEmptyMessage(200);
                                }
                            } else {
                                FyberLogger.i("CacheVideoDownloadService", "Network connectivity was lost or this device has issues accessing network data info.");
                                FyberLogger.i("CacheVideoDownloadService", "Videos will not be queued for download.");
                                z = true;
                            }
                        } else {
                            z = true;
                        }
                    } else {
                        z = true;
                    }
                    com.fyber.cache.internal.a aVarB = CacheManager.a().b();
                    if (aVarB == com.fyber.cache.internal.a.a) {
                        iB = 3600;
                    } else {
                        iB = aVarB.b();
                        FyberLogger.i("CacheVideoDownloadService", "Cache configuration exists, refresh interval is " + iB);
                    }
                    CacheVideoDownloadService.b(CacheVideoDownloadService.this, iB);
                    if (z) {
                        CacheVideoDownloadService.this.b.sendEmptyMessage(100);
                    }
                    break;
                case 1160:
                    String string = message.obj.toString();
                    int i = message.arg1;
                    int i2 = message.arg2;
                    f fVarC = CacheManager.a().c();
                    com.fyber.cache.internal.c cVarA = fVarC.a(string);
                    if (cVarA != null) {
                        FyberLogger.i("CacheVideoDownloadService", "Updating entry " + cVarA.b() + " with state " + i2);
                        cVarA.a(i2);
                        fVarC.b();
                        if (i2 == 2) {
                            FyberLogger.i("CacheVideoDownloadService", "Reason - File fully downloaded");
                            a(i);
                        } else if (i2 == 4) {
                            FyberLogger.i("CacheVideoDownloadService", "Reason - File not downloadable (file not found or no space left)");
                            a(i);
                        } else if (cVarA.d() > 10) {
                            FyberLogger.i("CacheVideoDownloadService", "Reason - Maximum retry count reached");
                            a(i);
                        }
                    } else {
                        FyberLogger.i("CacheVideoDownloadService", "There was no cache entry for the url: " + string);
                        a(i);
                    }
                    if (!message.getData().getBoolean("canceled", false)) {
                        sendEmptyMessage(200);
                    }
                    break;
                case 1300:
                    CacheVideoDownloadService.this.b.sendEmptyMessage(300);
                    break;
                case 1310:
                    sendEmptyMessage(200);
                    break;
            }
        }

        private void a(int i) {
            FyberLogger.i("CacheVideoDownloadService", "Removing videos to cache, entry num " + i);
            this.b.put(i, null);
            this.c++;
        }

        private void a(List<h> list) {
            int iC = CacheManager.a().b().c();
            int i = 0;
            for (h hVar : list) {
                if (i == iC) {
                    FyberLogger.i("CacheVideoDownloadService", String.format(Locale.ENGLISH, "There are %d videos to download and the maximum cache slots size is %d", Integer.valueOf(list.size()), Integer.valueOf(iC)));
                    FyberLogger.i("CacheVideoDownloadService", "Trimming the list of new videos to download to " + iC);
                    return;
                } else {
                    this.b.put(i, hVar);
                    i++;
                }
            }
        }

        private boolean a() {
            com.fyber.cache.internal.a.EnumC0023a enumC0023aE = CacheVideoDownloadService.e(CacheVideoDownloadService.this);
            if (enumC0023aE != null) {
                FyberLogger.i("CacheVideoDownloadService", "Queuing video for network " + enumC0023aE.name());
                CacheManager cacheManagerA = CacheManager.a();
                com.fyber.cache.internal.a aVarB = cacheManagerA.b();
                int iC = aVarB.c();
                f fVarC = cacheManagerA.c();
                int iA = aVarB.a(enumC0023aE).a();
                for (int i = 0; i < this.b.size() && i < iA; i++) {
                    h hVar = this.b.get(i);
                    if (hVar != null) {
                        FyberLogger.i("CacheVideoDownloadService", "Queuing video entry for ad_id " + hVar.a() + " and URL " + hVar.b());
                        boolean z = fVarC.a(hVar.b()) == null;
                        com.fyber.cache.internal.c cVarA = fVarC.a(hVar);
                        int iC2 = cVarA.c();
                        if (iC2 == 0 || iC2 == 1) {
                            if (z) {
                                fVarC.b(fVarC.a().size() - iC);
                            }
                            cVarA.a(3);
                            Message messageObtainMessage = CacheVideoDownloadService.this.c.obtainMessage(200, cVarA);
                            messageObtainMessage.arg1 = i;
                            messageObtainMessage.sendToTarget();
                            return true;
                        }
                        if (iC2 == 4) {
                            FyberLogger.i("CacheVideoDownloadService", "The file is marked as NOT_DOWNLOADABLE. Removing it from the current download list.");
                            a(i);
                        } else if (iC2 == 2) {
                            FyberLogger.i("CacheVideoDownloadService", "The file is marked as DOWNLOAD_COMPLETED. Removing it from the current download list.");
                            a(i);
                        } else {
                            FyberLogger.i("CacheVideoDownloadService", "This cache entry will not be queued for download. Current state: " + iC2);
                        }
                    }
                }
            }
            FyberLogger.i("CacheVideoDownloadService", "No videos to be queued for download at the moment");
            if (b()) {
                CacheVideoDownloadService.this.b.sendEmptyMessage(100);
            }
            return false;
        }

        private boolean b() {
            return this.c == this.b.size();
        }

        private void c() {
            FyberLogger.i("CacheVideoDownloadService", "Checking videos already available locally...");
            f fVarC = CacheManager.a().c();
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < this.b.size()) {
                    h hVar = this.b.get(i2);
                    if (hVar != null) {
                        FyberLogger.i("CacheVideoDownloadService", "Video entry for ad_id " + hVar.a() + " and url " + hVar.b());
                        com.fyber.cache.internal.c cVarA = fVarC.a(hVar.b());
                        if (cVarA != null) {
                            FyberLogger.i("CacheVideoDownloadService", "A cache entry already exists for url - " + cVarA.b());
                            if (cVarA.a(hVar)) {
                                FyberLogger.i("CacheVideoDownloadService", "Video entry successfully added to cache entry");
                            } else {
                                FyberLogger.i("CacheVideoDownloadService", "The video entry was already part of this cache entry");
                            }
                            cVarA.e();
                            fVarC.b();
                            if (cVarA.c() == 2) {
                                FyberLogger.i("CacheVideoDownloadService", "Cache entry is already fully downloaded");
                                FyberLogger.i("CacheVideoDownloadService", "Removing URL " + cVarA.b() + " from the new downloads list");
                                this.b.put(i2, null);
                                this.c++;
                            }
                        }
                    }
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        }
    }

    private String a() {
        return getPackageName() + ".cache.DONE_PRECACHING";
    }

    static /* synthetic */ void c(CacheVideoDownloadService cacheVideoDownloadService) {
        if (cacheVideoDownloadService.e) {
            return;
        }
        HandlerThread handlerThread = new HandlerThread("ServiceDownloadThread", 1);
        handlerThread.start();
        cacheVideoDownloadService.c = cacheVideoDownloadService.new c(handlerThread.getLooper());
        HandlerThread handlerThread2 = new HandlerThread("ServiceDecisionThread", 1);
        handlerThread2.start();
        cacheVideoDownloadService.d = cacheVideoDownloadService.new a(handlerThread2.getLooper());
        cacheVideoDownloadService.getApplicationContext().registerReceiver(cacheVideoDownloadService.h, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        cacheVideoDownloadService.e = true;
    }

    static /* synthetic */ com.fyber.cache.internal.a.EnumC0023a e(CacheVideoDownloadService cacheVideoDownloadService) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) cacheVideoDownloadService.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            switch (activeNetworkInfo.getType()) {
                case 0:
                    return com.fyber.cache.internal.a.EnumC0023a.CELLULAR;
                case 1:
                    return com.fyber.cache.internal.a.EnumC0023a.WIFI;
            }
        }
        return null;
    }

    static /* synthetic */ void b(CacheVideoDownloadService cacheVideoDownloadService, int i) {
        Intent intent = new Intent(cacheVideoDownloadService.a());
        if (i < 300) {
            i = 300;
        }
        intent.putExtra("refresh.interval", i);
        FyberLogger.i("CacheVideoDownloadService", "Creating broadcast receiver with refresh interval = " + i);
        cacheVideoDownloadService.sendBroadcast(intent);
    }
}
