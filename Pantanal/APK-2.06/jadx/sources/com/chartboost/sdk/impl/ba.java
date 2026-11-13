package com.chartboost.sdk.impl;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.view.Surface;
import android.view.TextureView;
import com.chartboost.sdk.Libraries.CBLogging;
import com.tapjoy.TJAdUnitConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ba extends TextureView implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener, TextureView.SurfaceTextureListener, bb.a {
    private final String a;
    private Uri b;
    private Map<String, String> c;
    private int d;
    private int e;
    private int f;
    private Surface g;
    private MediaPlayer h;
    private int i;
    private int j;
    private MediaPlayer.OnCompletionListener k;
    private MediaPlayer.OnPreparedListener l;
    private int m;
    private MediaPlayer.OnErrorListener n;
    private int o;

    public ba(Context context) {
        super(context);
        this.a = "VideoTextureView";
        this.e = 0;
        this.f = 0;
        this.g = null;
        this.h = null;
        f();
    }

    private void f() {
        this.i = 0;
        this.j = 0;
        setSurfaceTextureListener(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        this.e = 0;
        this.f = 0;
    }

    @Override // com.chartboost.sdk.impl.bb.a
    public void a(Uri uri) {
        a(uri, (Map<String, String>) null);
    }

    @Override // com.chartboost.sdk.impl.bb.a
    public void a(int i, int i2) {
        if (this.i != 0 && this.j != 0 && i != 0 && i2 != 0) {
            float fMin = Math.min(i / this.i, i2 / this.j);
            float f = this.i * fMin;
            float f2 = fMin * this.j;
            Matrix matrix = new Matrix();
            matrix.setScale(f / i, f2 / i2, i / 2.0f, i2 / 2.0f);
            setTransform(matrix);
        }
    }

    public void a(Uri uri, Map<String, String> map) {
        this.b = uri;
        this.c = map;
        this.o = 0;
        g();
        requestLayout();
        invalidate();
    }

    private void g() {
        if (this.b != null && this.g != null) {
            Intent intent = new Intent("com.android.music.musicservicecommand");
            intent.putExtra(TJAdUnitConstants.String.COMMAND, "pause");
            getContext().sendBroadcast(intent);
            a(false);
            h();
            try {
                this.h = new MediaPlayer();
                this.h.setOnPreparedListener(this);
                this.h.setOnVideoSizeChangedListener(this);
                this.d = -1;
                this.h.setOnCompletionListener(this);
                this.h.setOnErrorListener(this);
                this.h.setOnBufferingUpdateListener(this);
                this.m = 0;
                FileInputStream fileInputStream = new FileInputStream(new File(this.b.toString()));
                this.h.setDataSource(fileInputStream.getFD());
                fileInputStream.close();
                this.h.setSurface(this.g);
                this.h.setAudioStreamType(3);
                this.h.setScreenOnWhilePlaying(true);
                this.h.prepareAsync();
                this.e = 1;
            } catch (IOException e) {
                CBLogging.d("VideoTextureView", "Unable to open content: " + this.b, e);
                this.e = -1;
                this.f = -1;
                onError(this.h, 1, 0);
            } catch (IllegalArgumentException e2) {
                CBLogging.d("VideoTextureView", "Unable to open content: " + this.b, e2);
                this.e = -1;
                this.f = -1;
                onError(this.h, 1, 0);
            }
        }
    }

    @TargetApi(14)
    private void h() {
        try {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(this.b.toString());
            if (Build.VERSION.SDK_INT >= 14) {
                String strExtractMetadata = mediaMetadataRetriever.extractMetadata(19);
                String strExtractMetadata2 = mediaMetadataRetriever.extractMetadata(18);
                this.j = (int) Float.parseFloat(strExtractMetadata);
                this.i = (int) Float.parseFloat(strExtractMetadata2);
            } else {
                this.i = 0;
                this.j = 0;
                CBLogging.d("play video", "Unable to determine video height and width.  Supported on >= ICE_CREAM_SANDWICH only.");
            }
        } catch (Exception e) {
            CBLogging.d("play video", "read size error", e);
        }
    }

    @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        this.i = mp.getVideoWidth();
        this.j = mp.getVideoHeight();
        if (this.i != 0 && this.j != 0) {
            a(getWidth(), getHeight());
        }
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mp) {
        this.e = 2;
        this.i = mp.getVideoWidth();
        this.j = mp.getVideoHeight();
        if (this.l != null) {
            this.l.onPrepared(this.h);
        }
        int i = this.o;
        if (i != 0) {
            a(i);
        }
        if (this.f == 3) {
            a();
        }
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mp) {
        this.f = 5;
        if (this.e != 5) {
            this.e = 5;
            if (this.k != null) {
                this.k.onCompletion(this.h);
            }
        }
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
        CBLogging.a("VideoTextureView", "Error: " + framework_err + "," + impl_err);
        if (framework_err == 100) {
            g();
        } else {
            this.e = -1;
            this.f = -1;
            if (this.n == null || this.n.onError(this.h, framework_err, impl_err)) {
            }
        }
        return true;
    }

    @Override // android.media.MediaPlayer.OnBufferingUpdateListener
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        this.m = percent;
    }

    @Override // com.chartboost.sdk.impl.bb.a
    public void a(MediaPlayer.OnPreparedListener onPreparedListener) {
        this.l = onPreparedListener;
    }

    @Override // com.chartboost.sdk.impl.bb.a
    public void a(MediaPlayer.OnCompletionListener onCompletionListener) {
        this.k = onCompletionListener;
    }

    @Override // com.chartboost.sdk.impl.bb.a
    public void a(MediaPlayer.OnErrorListener onErrorListener) {
        this.n = onErrorListener;
    }

    private void a(boolean z) {
        if (this.h != null) {
            this.h.reset();
            this.h.release();
            this.h = null;
            this.e = 0;
            if (z) {
                this.f = 0;
            }
        }
    }

    @Override // com.chartboost.sdk.impl.bb.a
    public void a() {
        if (i()) {
            this.h.start();
            this.e = 3;
        }
        this.f = 3;
    }

    @Override // com.chartboost.sdk.impl.bb.a
    public void b() {
        if (i() && this.h.isPlaying()) {
            this.h.pause();
            this.e = 4;
        }
        this.f = 4;
    }

    @Override // com.chartboost.sdk.impl.bb.a
    public int c() {
        if (i()) {
            if (this.d > 0) {
                return this.d;
            }
            this.d = this.h.getDuration();
            return this.d;
        }
        this.d = -1;
        return this.d;
    }

    @Override // com.chartboost.sdk.impl.bb.a
    public int d() {
        if (i()) {
            return this.h.getCurrentPosition();
        }
        return 0;
    }

    @Override // com.chartboost.sdk.impl.bb.a
    public void a(int i) {
        if (i()) {
            this.h.seekTo(i);
            this.o = 0;
        } else {
            this.o = i;
        }
    }

    @Override // com.chartboost.sdk.impl.bb.a
    public boolean e() {
        return i() && this.h.isPlaying();
    }

    private boolean i() {
        return (this.h == null || this.e == -1 || this.e == 0 || this.e == 1) ? false : true;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int w, int h) {
        this.g = new Surface(surface);
        g();
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        this.g = null;
        a(true);
        return true;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int w, int h) {
        boolean z = this.f == 3;
        if (this.h != null && z) {
            if (this.o != 0) {
                a(this.o);
            }
            a();
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }
}
