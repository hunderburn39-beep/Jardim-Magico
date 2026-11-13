package com.google.android.gms.common.images;

import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.ImageView;
import com.google.android.gms.internal.fa;
import com.google.android.gms.internal.fb;
import com.google.android.gms.internal.fu;
import com.google.android.gms.internal.gr;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public final class ImageManager {
    private static final Object BY = new Object();
    private static HashSet<Uri> BZ = new HashSet<>();
    private static ImageManager Ca;
    private static ImageManager Cb;
    private final b Cd;
    private final fa Ce;
    private final Map<com.google.android.gms.common.images.a, ImageReceiver> Cf;
    private final Map<Uri, ImageReceiver> Cg;
    private final Context mContext;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final ExecutorService Cc = Executors.newFixedThreadPool(4);

    private final class ImageReceiver extends ResultReceiver {
        private final ArrayList<com.google.android.gms.common.images.a> Ch;
        private final Uri mUri;

        ImageReceiver(Uri uri) {
            super(new Handler(Looper.getMainLooper()));
            this.mUri = uri;
            this.Ch = new ArrayList<>();
        }

        public void b(com.google.android.gms.common.images.a aVar) {
            fb.aj("ImageReceiver.addImageRequest() must be called in the main thread");
            this.Ch.add(aVar);
        }

        public void c(com.google.android.gms.common.images.a aVar) {
            fb.aj("ImageReceiver.removeImageRequest() must be called in the main thread");
            this.Ch.remove(aVar);
        }

        public void ey() {
            Intent intent = new Intent("com.google.android.gms.common.images.LOAD_IMAGE");
            intent.putExtra("com.google.android.gms.extras.uri", this.mUri);
            intent.putExtra("com.google.android.gms.extras.resultReceiver", this);
            intent.putExtra("com.google.android.gms.extras.priority", 3);
            ImageManager.this.mContext.sendBroadcast(intent);
        }

        @Override // android.os.ResultReceiver
        public void onReceiveResult(int resultCode, Bundle resultData) {
            ImageManager.this.Cc.execute(ImageManager.this.new c(this.mUri, (ParcelFileDescriptor) resultData.getParcelable("com.google.android.gms.extra.fileDescriptor")));
        }
    }

    public interface OnImageLoadedListener {
        void onImageLoaded(Uri uri, Drawable drawable, boolean z);
    }

    private static final class a {
        static int a(ActivityManager activityManager) {
            return activityManager.getLargeMemoryClass();
        }
    }

    private static final class b extends fu<com.google.android.gms.common.images.a.C0034a, Bitmap> {
        public b(Context context) {
            super(w(context));
        }

        private static int w(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            return (int) (((((context.getApplicationInfo().flags & 1048576) != 0) && gr.fu()) ? a.a(activityManager) : activityManager.getMemoryClass()) * 1048576 * 0.33f);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.fu
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public int sizeOf(com.google.android.gms.common.images.a.C0034a c0034a, Bitmap bitmap) {
            return bitmap.getHeight() * bitmap.getRowBytes();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.fu
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void entryRemoved(boolean z, com.google.android.gms.common.images.a.C0034a c0034a, Bitmap bitmap, Bitmap bitmap2) {
            super.entryRemoved(z, c0034a, bitmap, bitmap2);
        }
    }

    private final class c implements Runnable {
        private final ParcelFileDescriptor Cj;
        private final Uri mUri;

        public c(Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
            this.mUri = uri;
            this.Cj = parcelFileDescriptor;
        }

        @Override // java.lang.Runnable
        public void run() {
            fb.ak("LoadBitmapFromDiskRunnable can't be executed in the main thread");
            boolean z = false;
            Bitmap bitmapDecodeFileDescriptor = null;
            if (this.Cj != null) {
                try {
                    bitmapDecodeFileDescriptor = BitmapFactory.decodeFileDescriptor(this.Cj.getFileDescriptor());
                } catch (OutOfMemoryError e) {
                    Log.e("ImageManager", "OOM while loading bitmap for uri: " + this.mUri, e);
                    z = true;
                }
                try {
                    this.Cj.close();
                } catch (IOException e2) {
                    Log.e("ImageManager", "closed failed", e2);
                }
            }
            CountDownLatch countDownLatch = new CountDownLatch(1);
            ImageManager.this.mHandler.post(ImageManager.this.new f(this.mUri, bitmapDecodeFileDescriptor, z, countDownLatch));
            try {
                countDownLatch.await();
            } catch (InterruptedException e3) {
                Log.w("ImageManager", "Latch interrupted while posting " + this.mUri);
            }
        }
    }

    private final class d implements Runnable {
        private final com.google.android.gms.common.images.a Ck;

        public d(com.google.android.gms.common.images.a aVar) {
            this.Ck = aVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            fb.aj("LoadImageRunnable must be executed on the main thread");
            ImageReceiver imageReceiver = (ImageReceiver) ImageManager.this.Cf.get(this.Ck);
            if (imageReceiver != null) {
                ImageManager.this.Cf.remove(this.Ck);
                imageReceiver.c(this.Ck);
            }
            com.google.android.gms.common.images.a.C0034a c0034a = this.Ck.Cm;
            if (c0034a.uri == null) {
                this.Ck.a(ImageManager.this.mContext, ImageManager.this.Ce, true);
                return;
            }
            Bitmap bitmapA = ImageManager.this.a(c0034a);
            if (bitmapA != null) {
                this.Ck.a(ImageManager.this.mContext, bitmapA, true);
                return;
            }
            this.Ck.a(ImageManager.this.mContext, ImageManager.this.Ce);
            ImageReceiver imageReceiver2 = (ImageReceiver) ImageManager.this.Cg.get(c0034a.uri);
            if (imageReceiver2 == null) {
                imageReceiver2 = ImageManager.this.new ImageReceiver(c0034a.uri);
                ImageManager.this.Cg.put(c0034a.uri, imageReceiver2);
            }
            imageReceiver2.b(this.Ck);
            if (!(this.Ck instanceof com.google.android.gms.common.images.a.c)) {
                ImageManager.this.Cf.put(this.Ck, imageReceiver2);
            }
            synchronized (ImageManager.BY) {
                if (!ImageManager.BZ.contains(c0034a.uri)) {
                    ImageManager.BZ.add(c0034a.uri);
                    imageReceiver2.ey();
                }
            }
        }
    }

    private static final class e implements ComponentCallbacks2 {
        private final b Cd;

        public e(b bVar) {
            this.Cd = bVar;
        }

        @Override // android.content.ComponentCallbacks
        public void onConfigurationChanged(Configuration newConfig) {
        }

        @Override // android.content.ComponentCallbacks
        public void onLowMemory() {
            this.Cd.evictAll();
        }

        @Override // android.content.ComponentCallbacks2
        public void onTrimMemory(int level) {
            if (level >= 60) {
                this.Cd.evictAll();
            } else if (level >= 20) {
                this.Cd.trimToSize(this.Cd.size() / 2);
            }
        }
    }

    private final class f implements Runnable {
        private final CountDownLatch AD;
        private boolean Cl;
        private final Bitmap mBitmap;
        private final Uri mUri;

        public f(Uri uri, Bitmap bitmap, boolean z, CountDownLatch countDownLatch) {
            this.mUri = uri;
            this.mBitmap = bitmap;
            this.Cl = z;
            this.AD = countDownLatch;
        }

        private void a(ImageReceiver imageReceiver, boolean z) {
            ArrayList arrayList = imageReceiver.Ch;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                com.google.android.gms.common.images.a aVar = (com.google.android.gms.common.images.a) arrayList.get(i);
                if (z) {
                    aVar.a(ImageManager.this.mContext, this.mBitmap, false);
                } else {
                    aVar.a(ImageManager.this.mContext, ImageManager.this.Ce, false);
                }
                if (!(aVar instanceof com.google.android.gms.common.images.a.c)) {
                    ImageManager.this.Cf.remove(aVar);
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            fb.aj("OnBitmapLoadedRunnable must be executed in the main thread");
            boolean z = this.mBitmap != null;
            if (ImageManager.this.Cd != null) {
                if (this.Cl) {
                    ImageManager.this.Cd.evictAll();
                    System.gc();
                    this.Cl = false;
                    ImageManager.this.mHandler.post(this);
                    return;
                }
                if (z) {
                    ImageManager.this.Cd.put(new com.google.android.gms.common.images.a.C0034a(this.mUri), this.mBitmap);
                }
            }
            ImageReceiver imageReceiver = (ImageReceiver) ImageManager.this.Cg.remove(this.mUri);
            if (imageReceiver != null) {
                a(imageReceiver, z);
            }
            this.AD.countDown();
            synchronized (ImageManager.BY) {
                ImageManager.BZ.remove(this.mUri);
            }
        }
    }

    private ImageManager(Context context, boolean withMemoryCache) {
        this.mContext = context.getApplicationContext();
        if (withMemoryCache) {
            this.Cd = new b(this.mContext);
            if (gr.fx()) {
                ev();
            }
        } else {
            this.Cd = null;
        }
        this.Ce = new fa();
        this.Cf = new HashMap();
        this.Cg = new HashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap a(com.google.android.gms.common.images.a.C0034a c0034a) {
        if (this.Cd == null) {
            return null;
        }
        return this.Cd.get(c0034a);
    }

    public static ImageManager a(Context context, boolean z) {
        if (z) {
            if (Cb == null) {
                Cb = new ImageManager(context, true);
            }
            return Cb;
        }
        if (Ca == null) {
            Ca = new ImageManager(context, false);
        }
        return Ca;
    }

    public static ImageManager create(Context context) {
        return a(context, false);
    }

    private void ev() {
        this.mContext.registerComponentCallbacks(new e(this.Cd));
    }

    public void a(com.google.android.gms.common.images.a aVar) {
        fb.aj("ImageManager.loadImage() must be called in the main thread");
        new d(aVar).run();
    }

    public void loadImage(ImageView imageView, int resId) {
        a(new com.google.android.gms.common.images.a.b(imageView, resId));
    }

    public void loadImage(ImageView imageView, Uri uri) {
        a(new com.google.android.gms.common.images.a.b(imageView, uri));
    }

    public void loadImage(ImageView imageView, Uri uri, int defaultResId) {
        com.google.android.gms.common.images.a.b bVar = new com.google.android.gms.common.images.a.b(imageView, uri);
        bVar.J(defaultResId);
        a(bVar);
    }

    public void loadImage(OnImageLoadedListener listener, Uri uri) {
        a(new com.google.android.gms.common.images.a.c(listener, uri));
    }

    public void loadImage(OnImageLoadedListener listener, Uri uri, int defaultResId) {
        com.google.android.gms.common.images.a.c cVar = new com.google.android.gms.common.images.a.c(listener, uri);
        cVar.J(defaultResId);
        a(cVar);
    }
}
