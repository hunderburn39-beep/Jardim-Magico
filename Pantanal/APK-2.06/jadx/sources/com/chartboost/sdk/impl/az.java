package com.chartboost.sdk.impl;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.chartboost.sdk.Libraries.CBLogging;
import com.tapjoy.TJAdUnitConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class az extends SurfaceView implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback, bb.a {
    private final String a;
    private Uri b;
    private Map<String, String> c;
    private int d;
    private int e;
    private int f;
    private SurfaceHolder g;
    private MediaPlayer h;
    private int i;
    private int j;
    private int k;
    private int l;
    private MediaPlayer.OnCompletionListener m;
    private MediaPlayer.OnPreparedListener n;
    private int o;
    private MediaPlayer.OnErrorListener p;
    private int q;

    public az(Context context) {
        super(context);
        this.a = "VideoSurfaceView";
        this.e = 0;
        this.f = 0;
        this.g = null;
        this.h = null;
        f();
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int iMin;
        int iMin2;
        int defaultSize = getDefaultSize(0, widthMeasureSpec);
        int defaultSize2 = getDefaultSize(0, heightMeasureSpec);
        if (this.i <= 0 || this.j <= 0) {
            iMin = defaultSize2;
            iMin2 = defaultSize;
        } else {
            iMin = Math.min(defaultSize2, Math.round((this.j / this.i) * defaultSize));
            iMin2 = Math.min(defaultSize, Math.round(defaultSize2 * (this.i / this.j)));
        }
        setMeasuredDimension(iMin2, iMin);
    }

    private void f() {
        this.i = 0;
        this.j = 0;
        getHolder().addCallback(this);
        getHolder().setType(3);
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

    public void a(Uri uri, Map<String, String> map) {
        this.b = uri;
        this.c = map;
        this.q = 0;
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
            try {
                this.h = new MediaPlayer();
                this.h.setOnPreparedListener(this);
                this.h.setOnVideoSizeChangedListener(this);
                this.d = -1;
                this.h.setOnCompletionListener(this);
                this.h.setOnErrorListener(this);
                this.h.setOnBufferingUpdateListener(this);
                this.o = 0;
                this.h.setDisplay(this.g);
                this.h.setAudioStreamType(3);
                this.h.setScreenOnWhilePlaying(true);
                FileInputStream fileInputStream = new FileInputStream(new File(this.b.toString()));
                this.h.setDataSource(fileInputStream.getFD());
                fileInputStream.close();
                this.h.prepareAsync();
                this.e = 1;
            } catch (IOException e) {
                CBLogging.d("VideoSurfaceView", "Unable to open content: " + this.b, e);
                this.e = -1;
                this.f = -1;
                onError(this.h, 1, 0);
            } catch (IllegalArgumentException e2) {
                CBLogging.d("VideoSurfaceView", "Unable to open content: " + this.b, e2);
                this.e = -1;
                this.f = -1;
                onError(this.h, 1, 0);
            }
        }
    }

    @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        this.i = mp.getVideoWidth();
        this.j = mp.getVideoHeight();
        if (this.i != 0 && this.j != 0) {
            getHolder().setFixedSize(this.i, this.j);
        }
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mp) {
        this.e = 2;
        this.i = mp.getVideoWidth();
        this.j = mp.getVideoHeight();
        if (this.n != null) {
            this.n.onPrepared(this.h);
        }
        int i = this.q;
        if (i != 0) {
            a(i);
        }
        if (this.i != 0 && this.j != 0) {
            getHolder().setFixedSize(this.i, this.j);
            if (this.k == this.i && this.l == this.j && this.f == 3) {
                a();
                return;
            }
            return;
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
            if (this.m != null) {
                this.m.onCompletion(this.h);
            }
        }
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
        CBLogging.a("VideoSurfaceView", "Error: " + framework_err + "," + impl_err);
        this.e = -1;
        this.f = -1;
        if (this.p == null || this.p.onError(this.h, framework_err, impl_err)) {
        }
        return true;
    }

    @Override // android.media.MediaPlayer.OnBufferingUpdateListener
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        this.o = percent;
    }

    @Override // com.chartboost.sdk.impl.bb.a
    public void a(MediaPlayer.OnPreparedListener onPreparedListener) {
        this.n = onPreparedListener;
    }

    @Override // com.chartboost.sdk.impl.bb.a
    public void a(MediaPlayer.OnCompletionListener onCompletionListener) {
        this.m = onCompletionListener;
    }

    @Override // com.chartboost.sdk.impl.bb.a
    public void a(MediaPlayer.OnErrorListener onErrorListener) {
        this.p = onErrorListener;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        this.k = w;
        this.l = h;
        boolean z = this.f == 3;
        boolean z2 = this.i == w && this.j == h;
        if (this.h != null && z && z2) {
            if (this.q != 0) {
                a(this.q);
            }
            a();
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder holder) {
        this.g = holder;
        g();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.g = null;
        a(true);
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
        if (h()) {
            this.h.start();
            this.e = 3;
        }
        this.f = 3;
    }

    @Override // com.chartboost.sdk.impl.bb.a
    public void b() {
        if (h() && this.h.isPlaying()) {
            this.h.pause();
            this.e = 4;
        }
        this.f = 4;
    }

    @Override // com.chartboost.sdk.impl.bb.a
    public int c() {
        if (h()) {
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
        if (h()) {
            return this.h.getCurrentPosition();
        }
        return 0;
    }

    @Override // com.chartboost.sdk.impl.bb.a
    public void a(int i) {
        if (h()) {
            this.h.seekTo(i);
            this.q = 0;
        } else {
            this.q = i;
        }
    }

    @Override // com.chartboost.sdk.impl.bb.a
    public boolean e() {
        return h() && this.h.isPlaying();
    }

    private boolean h() {
        return (this.h == null || this.e == -1 || this.e == 0 || this.e == 1) ? false : true;
    }

    @Override // com.chartboost.sdk.impl.bb.a
    public void a(int i, int i2) {
    }
}
