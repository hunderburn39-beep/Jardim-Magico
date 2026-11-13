package com.adcolony.sdk;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class av extends TextureView implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, TextureView.SurfaceTextureListener {
    private boolean A;
    private boolean B;
    private boolean C;
    private boolean D;
    private boolean E;
    private boolean F;
    private String G;
    private String H;
    private FileInputStream I;
    private z J;
    private c K;
    private Surface L;
    private SurfaceTexture M;
    private RectF N;
    private a O;
    private JSONObject P;
    private ExecutorService Q;
    int a;
    boolean b;
    boolean c;
    Context d;
    MediaPlayer e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private float m;
    private float n;
    private float o;
    private float p;
    private float q;
    private float r;
    private int s;
    private boolean t;
    private Paint u;
    private Paint v;
    private double w;
    private double x;
    private long y;
    private boolean z;

    av(Context context, z zVar, int i, c cVar) {
        super(context);
        this.t = true;
        this.u = new Paint();
        this.v = new Paint(1);
        this.N = new RectF();
        this.P = s.a();
        this.Q = Executors.newSingleThreadExecutor();
        this.K = cVar;
        this.J = zVar;
        this.j = i;
        this.d = context;
        setSurfaceTextureListener(this);
    }

    void a() {
        if (this.M != null) {
            this.C = true;
        }
        this.Q.shutdown();
    }

    void b() {
        this.H = s.b(this.J.b, "ad_session_id");
        this.f = s.c(this.J.b, TMXConstants.TAG_OBJECT_ATTRIBUTE_X);
        this.g = s.c(this.J.b, TMXConstants.TAG_OBJECT_ATTRIBUTE_Y);
        this.h = s.c(this.J.b, "width");
        this.i = s.c(this.J.b, "height");
        this.E = s.d(this.J.b, "enable_timer");
        this.G = s.b(this.J.b, "filepath");
        this.k = s.c(this.J.b, "video_width");
        this.l = s.c(this.J.b, "video_height");
        u.b.b("Original video dimensions = " + this.k + TMXConstants.TAG_OBJECT_ATTRIBUTE_X + this.l);
        setVisibility(4);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(this.h, this.i);
        layoutParams.setMargins(this.f, this.g, 0, 0);
        layoutParams.gravity = 0;
        this.K.addView(this, layoutParams);
        this.e = new MediaPlayer();
        this.B = false;
        try {
            if (!this.G.startsWith("http")) {
                this.I = new FileInputStream(this.G);
                this.e.setDataSource(this.I.getFD());
            } else {
                this.D = true;
                this.e.setDataSource(this.G);
            }
            this.e.setOnErrorListener(this);
            this.e.setOnPreparedListener(this);
            this.e.setOnCompletionListener(this);
            this.e.prepareAsync();
        } catch (IOException e) {
            u.g.a("Failed to create/prepare MediaPlayer: ").b(e.toString());
            e();
        }
        this.K.o.add(com.adcolony.sdk.a.a("VideoView.play", new ac() { // from class: com.adcolony.sdk.av.1
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                if (av.this.a(zVar)) {
                    av.this.c();
                }
            }
        }, true));
        this.K.o.add(com.adcolony.sdk.a.a("VideoView.set_bounds", new ac() { // from class: com.adcolony.sdk.av.2
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                if (av.this.a(zVar)) {
                    av.this.b(zVar);
                }
            }
        }, true));
        this.K.o.add(com.adcolony.sdk.a.a("VideoView.set_visible", new ac() { // from class: com.adcolony.sdk.av.3
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                if (av.this.a(zVar)) {
                    av.this.c(zVar);
                }
            }
        }, true));
        this.K.o.add(com.adcolony.sdk.a.a("VideoView.pause", new ac() { // from class: com.adcolony.sdk.av.4
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                if (av.this.a(zVar)) {
                    av.this.d();
                }
            }
        }, true));
        this.K.o.add(com.adcolony.sdk.a.a("VideoView.seek_to_time", new ac() { // from class: com.adcolony.sdk.av.5
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                if (av.this.a(zVar)) {
                    av.this.e(zVar);
                }
            }
        }, true));
        this.K.o.add(com.adcolony.sdk.a.a("VideoView.set_volume", new ac() { // from class: com.adcolony.sdk.av.6
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                if (av.this.a(zVar)) {
                    av.this.d(zVar);
                }
            }
        }, true));
        this.K.p.add("VideoView.play");
        this.K.p.add("VideoView.set_bounds");
        this.K.p.add("VideoView.set_visible");
        this.K.p.add("VideoView.pause");
        this.K.p.add("VideoView.seek_to_time");
        this.K.p.add("VideoView.set_volume");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(z zVar) {
        return s.c(zVar.b, TMXConstants.TAG_TILE_ATTRIBUTE_ID) == this.j && s.c(zVar.b, "container_id") == this.K.n && s.b(zVar.b, "ad_session_id").equals(this.K.m);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture texture, int w, int h) {
        if (texture == null) {
            u.g.b("Null texture provided by system's onSurfaceTextureAvailable.");
            return;
        }
        this.L = new Surface(texture);
        this.e.setSurface(this.L);
        this.M = texture;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture texture) {
        this.M = texture;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
        this.M = texture;
        if (!this.C) {
            return false;
        }
        texture.release();
        return true;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int w, int h) {
        this.M = texture;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEventCompat.ACTION_MASK;
        if (action != 0 && action != 1 && action != 3 && action != 2 && action != 5 && action != 6) {
            return false;
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        JSONObject jSONObjectA = s.a();
        s.b(jSONObjectA, "view_id", this.j);
        s.a(jSONObjectA, "ad_session_id", this.H);
        s.b(jSONObjectA, "container_x", this.f + x);
        s.b(jSONObjectA, "container_y", this.g + y);
        s.b(jSONObjectA, "view_x", x);
        s.b(jSONObjectA, "view_y", y);
        s.b(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, this.K.n);
        switch (action) {
            case 0:
                new z("AdContainer.on_touch_began", this.K.l, jSONObjectA).b();
                break;
            case 1:
                if (!this.K.q) {
                    com.adcolony.sdk.a.m.r = com.adcolony.sdk.a.m.g.e.get(this.H);
                }
                new z("AdContainer.on_touch_ended", this.K.l, jSONObjectA).b();
                break;
            case 2:
                new z("AdContainer.on_touch_moved", this.K.l, jSONObjectA).b();
                break;
            case 3:
                new z("AdContainer.on_touch_cancelled", this.K.l, jSONObjectA).b();
                break;
            case 5:
                int action2 = (event.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                s.b(jSONObjectA, "container_x", ((int) event.getX(action2)) + this.f);
                s.b(jSONObjectA, "container_y", ((int) event.getY(action2)) + this.g);
                s.b(jSONObjectA, "view_x", (int) event.getX(action2));
                s.b(jSONObjectA, "view_y", (int) event.getY(action2));
                new z("AdContainer.on_touch_began", this.K.l, jSONObjectA).b();
                break;
            case 6:
                int action3 = (event.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                s.b(jSONObjectA, "container_x", ((int) event.getX(action3)) + this.f);
                s.b(jSONObjectA, "container_y", ((int) event.getY(action3)) + this.g);
                s.b(jSONObjectA, "view_x", (int) event.getX(action3));
                s.b(jSONObjectA, "view_y", (int) event.getY(action3));
                if (!this.K.q) {
                    com.adcolony.sdk.a.m.r = com.adcolony.sdk.a.m.g.e.get(this.H);
                }
                new z("AdContainer.on_touch_ended", this.K.l, jSONObjectA).b();
                break;
        }
        return true;
    }

    @Override // android.view.View
    public void onMeasure(int width_measure_spec, int height_measure_spec) {
        double d = ((double) this.h) / ((double) this.k);
        double d2 = ((double) this.i) / ((double) this.l);
        if (d <= d2) {
            d2 = d;
        }
        int i = (int) (((double) this.k) * d2);
        int i2 = (int) (d2 * ((double) this.l));
        u.d.a("setMeasuredDimension to ").a(i).a(" by ").b(i2);
        setMeasuredDimension(i, i2);
        if (this.D) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
            layoutParams.width = i;
            layoutParams.height = i2;
            layoutParams.gravity = 17;
            layoutParams.setMargins(0, 0, 0, 0);
            setLayoutParams(layoutParams);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        JSONObject jSONObjectA = s.a();
        s.a(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, this.H);
        new z("AdSession.on_error", this.K.l, jSONObjectA).b();
        this.b = true;
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mp, int what, int extra) {
        e();
        u.g.a("MediaPlayer error: ").a(what).a(",").b(extra);
        return true;
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mp) {
        this.B = true;
        if (this.D) {
            this.k = mp.getVideoWidth();
            this.l = mp.getVideoHeight();
            onMeasure(this.k, this.l);
            u.d.a("MediaPlayer getVideoWidth = ").b(mp.getVideoWidth());
            u.d.a("MediaPlayer getVideoHeight = ").b(mp.getVideoHeight());
        }
        JSONObject jSONObjectA = s.a();
        s.b(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, this.j);
        s.b(jSONObjectA, "container_id", this.K.n);
        s.a(jSONObjectA, "ad_session_id", this.H);
        u.b.b("ADCVideoView is prepared");
        new z("VideoView.on_ready", this.K.l, jSONObjectA).b();
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mp) {
        this.b = true;
        this.w = this.x;
        s.b(this.P, TMXConstants.TAG_TILE_ATTRIBUTE_ID, this.j);
        s.b(this.P, "container_id", this.K.n);
        s.a(this.P, "ad_session_id", this.H);
        s.a(this.P, "elapsed", this.w);
        s.a(this.P, "duration", this.x);
        new z("VideoView.on_progress", this.K.l, this.P).b();
    }

    private void f() {
        try {
            this.Q.submit(new Runnable() { // from class: com.adcolony.sdk.av.7
                @Override // java.lang.Runnable
                public void run() {
                    av.this.y = 0L;
                    while (!av.this.b && !av.this.A && com.adcolony.sdk.a.l != null) {
                        if (!av.this.b && !av.this.C) {
                            if (av.this.e.isPlaying()) {
                                if (av.this.y == 0 && com.adcolony.sdk.a.o) {
                                    av.this.y = System.currentTimeMillis();
                                }
                                av.this.z = true;
                                av.this.w = ((double) av.this.e.getCurrentPosition()) / 1000.0d;
                                av.this.x = ((double) av.this.e.getDuration()) / 1000.0d;
                                if (System.currentTimeMillis() - av.this.y > 1000 && !av.this.F && com.adcolony.sdk.a.o) {
                                    if (av.this.w != 0.0d) {
                                        av.this.F = true;
                                    } else {
                                        u.h.b("getCurrentPosition() not working, firing AdSession.on_error");
                                        av.this.e();
                                    }
                                }
                                if (av.this.E) {
                                    if (av.this.t) {
                                        av.this.q = (float) (360.0d / av.this.x);
                                        av.this.r = com.adcolony.sdk.a.m.m.j();
                                        av.this.v.setColor(-3355444);
                                        av.this.v.setShadowLayer((int) (av.this.r * 2.0f), BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, -16777216);
                                        av.this.v.setTextAlign(Paint.Align.CENTER);
                                        av.this.v.setLinearText(true);
                                        av.this.v.setTextSize(12.0f * av.this.r);
                                        av.this.u.setStyle(Paint.Style.STROKE);
                                        float f = av.this.r * 2.0f > 6.0f ? 6.0f : av.this.r * 2.0f;
                                        if (f < 4.0f) {
                                            f = 4.0f;
                                        }
                                        av.this.u.setStrokeWidth(f);
                                        av.this.u.setShadowLayer((int) (3.0f * av.this.r), BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, -16777216);
                                        av.this.u.setColor(-3355444);
                                        Rect rect = new Rect();
                                        av.this.v.getTextBounds("0123456789", 0, 9, rect);
                                        av.this.o = rect.height();
                                        if (com.adcolony.sdk.a.l != null) {
                                            com.adcolony.sdk.a.l.runOnUiThread(new Runnable() { // from class: com.adcolony.sdk.av.7.1
                                                @Override // java.lang.Runnable
                                                public void run() {
                                                    av.this.O = av.this.new a(av.this.d);
                                                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) (av.this.o * 4.0f), (int) (av.this.o * 4.0f));
                                                    layoutParams.setMargins(0, av.this.K.k - ((int) (av.this.o * 4.0f)), 0, 0);
                                                    layoutParams.gravity = 0;
                                                    av.this.K.addView(av.this.O, layoutParams);
                                                }
                                            });
                                        }
                                        av.this.t = false;
                                    }
                                    av.this.s = (int) (av.this.x - av.this.w);
                                    av.this.m = (int) av.this.o;
                                    av.this.n = (int) (av.this.o * 3.0f);
                                    av.this.N.set(av.this.m - (av.this.o / 2.0f), av.this.n - (av.this.o * 2.0f), av.this.m + (av.this.o * 2.0f), av.this.n + (av.this.o / 2.0f));
                                    av.this.p = (float) (((double) av.this.q) * (av.this.x - av.this.w));
                                }
                            }
                            if (av.this.z && !av.this.b && !av.this.A) {
                                s.b(av.this.P, TMXConstants.TAG_TILE_ATTRIBUTE_ID, av.this.j);
                                s.b(av.this.P, "container_id", av.this.K.n);
                                s.a(av.this.P, "ad_session_id", av.this.H);
                                s.a(av.this.P, "elapsed", av.this.w);
                                s.a(av.this.P, "duration", av.this.x);
                                new z("VideoView.on_progress", av.this.K.l, av.this.P).b();
                            }
                            if (av.this.c || com.adcolony.sdk.a.l.isFinishing()) {
                                av.this.c = false;
                                u.d.b("MediaPlayer stopped and released.");
                                if (!av.this.b) {
                                    av.this.e.stop();
                                }
                                av.this.b = true;
                                av.this.e.release();
                                return;
                            }
                            try {
                                Thread.sleep(50L);
                            } catch (InterruptedException e) {
                                av.this.e();
                                u.g.b("InterruptedException in ADCVideoView's update thread.");
                            }
                        } else {
                            return;
                        }
                    }
                    if (av.this.c) {
                        u.d.b("MediaPlayer stopped and released.");
                        if (!av.this.b) {
                            av.this.e.stop();
                        }
                        av.this.b = true;
                        av.this.e.release();
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            e();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(z zVar) {
        this.f = s.c(zVar.b, TMXConstants.TAG_OBJECT_ATTRIBUTE_X);
        this.g = s.c(zVar.b, TMXConstants.TAG_OBJECT_ATTRIBUTE_Y);
        this.h = s.c(zVar.b, "width");
        this.i = s.c(zVar.b, "height");
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        layoutParams.setMargins(this.f, this.g, 0, 0);
        layoutParams.width = this.h;
        layoutParams.height = this.i;
        setLayoutParams(layoutParams);
        if (this.E && this.O != null) {
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams((int) (this.o * 4.0f), (int) (this.o * 4.0f));
            layoutParams2.setMargins(0, this.K.k - ((int) (this.o * 4.0f)), 0, 0);
            layoutParams2.gravity = 0;
            this.O.setLayoutParams(layoutParams2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(z zVar) {
        if (s.d(zVar.b, "visible")) {
            setVisibility(0);
            if (this.E && this.O != null) {
                this.O.setVisibility(0);
                return;
            }
            return;
        }
        setVisibility(4);
        if (this.E && this.O != null) {
            this.O.setVisibility(4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean d(z zVar) {
        if (!this.B) {
            return false;
        }
        float fE = (float) s.e(zVar.b, "volume");
        this.e.setVolume(fE, fE);
        return true;
    }

    boolean c() {
        if (!this.B) {
            return false;
        }
        if (!this.A && com.adcolony.sdk.a.o) {
            this.e.start();
            f();
            u.b.b("MediaPlayer is prepared - ADCVideoView play() called.");
        } else if (!this.b && com.adcolony.sdk.a.o) {
            this.e.start();
            this.A = false;
            if (!this.Q.isShutdown()) {
                f();
            }
            if (this.O != null) {
                this.O.invalidate();
            }
        }
        setWillNotDraw(false);
        return true;
    }

    boolean d() {
        if (!this.B) {
            u.f.b("ADCVideoView pause() called while MediaPlayer is not prepared.");
            return false;
        }
        if (!this.z) {
            u.d.b("Ignoring ADCVideoView pause due to invalid MediaPlayer state.");
            return false;
        }
        this.a = this.e.getCurrentPosition();
        this.x = this.e.getDuration();
        this.e.pause();
        this.A = true;
        u.b.b("Video view paused");
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean e(z zVar) {
        if (!this.B) {
            return false;
        }
        if (this.b) {
            this.b = false;
        }
        this.e.seekTo(s.c(zVar.b, "time") * 1000);
        JSONObject jSONObjectA = s.a();
        s.b(jSONObjectA, TMXConstants.TAG_TILE_ATTRIBUTE_ID, this.j);
        s.a(jSONObjectA, "ad_session_id", this.H);
        s.a(jSONObjectA, "success", true);
        zVar.a(jSONObjectA).b();
        return true;
    }

    private class a extends View {
        a(Context context) {
            super(context);
            setWillNotDraw(false);
            try {
                getClass().getMethod("setLayerType", Integer.TYPE, Paint.class).invoke(this, 1, null);
            } catch (Exception e) {
            }
        }

        @Override // android.view.View
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawArc(av.this.N, 270.0f, av.this.p, false, av.this.u);
            canvas.drawText("" + av.this.s, av.this.N.centerX(), (float) (((double) av.this.N.centerY()) + (((double) av.this.v.getFontMetrics().bottom) * 1.35d)), av.this.v);
            invalidate();
        }
    }
}
