package com.fyber.c.d;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.ExploreByTouchHelper;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.VideoView;
import com.fyber.Fyber;
import com.fyber.ads.videos.RewardedVideoActivity;
import com.fyber.cache.CacheManager;
import com.fyber.mediation.MediationUserActivityListener;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.StringUtils;
import com.fyber.utils.l;
import com.fyber.utils.n;
import com.fyber.utils.r;
import com.fyber.utils.u;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.r7games.slotpantanal.utils.IabHelper;
import com.tapjoy.TJAdUnitConstants;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: VideoPlayerView.java */
/* JADX INFO: loaded from: classes.dex */
public final class d extends FrameLayout implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, View.OnClickListener, View.OnTouchListener, com.fyber.c.d.a.a.InterfaceC0021a, MediationUserActivityListener, Runnable {
    private boolean A;
    private int B;
    private int C;
    private com.fyber.c.d.a.a D;
    private InterfaceC0022d E;
    private boolean F;
    private boolean G;
    private boolean H;
    private FrameLayout I;
    private Activity a;
    private String b;
    private VideoView c;
    private MediaPlayer d;
    private int e;
    private com.fyber.c.e.b f;
    private com.fyber.c.b.a g;
    private Integer h;
    private Float i;
    private com.fyber.c.c.b j;
    private long k;
    private boolean l;
    private String m;
    private TextView n;
    private String o;
    private volatile boolean p;
    private volatile int q;
    private boolean r;
    private String s;
    private com.fyber.c.a.a t;
    private b u;
    private int v;
    private ScheduledExecutorService w;
    private com.fyber.c.d.b x;
    private final c y;
    private boolean z;

    /* JADX INFO: compiled from: VideoPlayerView.java */
    public interface b {
        boolean a(d dVar, com.fyber.c.a.a aVar, String str);
    }

    /* JADX INFO: renamed from: com.fyber.c.d.d$d, reason: collision with other inner class name */
    /* JADX INFO: compiled from: VideoPlayerView.java */
    public interface InterfaceC0022d {
        void a(int i, String str);
    }

    /* synthetic */ d(Activity activity, a aVar, byte b2) {
        this(activity, aVar);
    }

    static /* synthetic */ boolean c(d dVar) {
        dVar.G = false;
        return false;
    }

    static /* synthetic */ boolean k(d dVar) {
        dVar.z = true;
        return true;
    }

    static /* synthetic */ boolean n(d dVar) {
        dVar.p = true;
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private d(Activity activity, a aVar) {
        super(activity);
        boolean zA = false;
        this.l = false;
        this.p = false;
        this.v = -1;
        this.z = false;
        this.A = false;
        this.B = 0;
        this.F = true;
        this.G = false;
        this.H = false;
        this.a = activity;
        this.x = aVar.b;
        this.b = aVar.c;
        String str = aVar.d;
        if (!StringUtils.nullOrEmpty(str)) {
            if (u.a(str)) {
                zA = true;
            } else {
                zA = l.a(getContext(), "android.intent.action.VIEW", Uri.parse(str));
            }
        }
        if (zA) {
            this.m = aVar.d;
            this.o = aVar.f;
        }
        this.s = aVar.e;
        this.r = aVar.a;
        this.E = aVar.g;
        this.i = aVar.h;
        this.u = aVar.i;
        this.y = new c(this);
    }

    public final void b() {
        this.f.a();
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public final void onPrepared(MediaPlayer mediaPlayer) {
        FyberLogger.d("VideoPlayerView", "onPrepared()");
        j();
        if (!this.p) {
            this.d = mediaPlayer;
            this.c.start();
            this.D.a();
            i();
            this.e = this.c.getDuration();
            this.D.a(this.e);
            this.j = new com.fyber.c.c.b(this.a, this.e);
            int iApplyDimension = (int) TypedValue.applyDimension(1, 60.0f, getResources().getDisplayMetrics());
            this.j.setLayoutParams(new FrameLayout.LayoutParams(iApplyDimension, iApplyDimension, 51));
            this.x.a(this.e);
            this.k = (long) Math.min(((double) this.e) * 0.25d, 15000.0d);
            addView(this.j);
            float fFloatValue = this.i != null ? this.i.floatValue() : h();
            int i = 0;
            if (fFloatValue == -1.0f) {
                i = this.e + 1000;
            } else if (fFloatValue >= BitmapDescriptorFactory.HUE_RED && fFloatValue < 1.0f) {
                i = (int) (this.e * fFloatValue);
            } else if (fFloatValue >= 1.0f) {
                i = (int) (1000.0f * fFloatValue);
            }
            FyberLogger.d("VideoPlayerView", "Delay for close button - " + i + "ms");
            this.h = Integer.valueOf(i);
            if (this.h.intValue() == 0) {
                g();
                this.z = true;
            }
            this.w = Executors.newSingleThreadScheduledExecutor();
            this.w.scheduleAtFixedRate(this, 0L, 50L, TimeUnit.MILLISECONDS);
            if (this.n != null && !a(this.n)) {
                addView(this.n);
            }
        }
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public final void onCompletion(MediaPlayer mediaPlayer) {
        FyberLogger.d("VideoPlayerView", "onCompletion() - mediaPlayer = " + mediaPlayer);
        this.c.stopPlayback();
        e();
        d();
        if (mediaPlayer != null) {
            this.x.b();
            a(1, (String) null);
        } else {
            a("unknown", "onCompletion - video playing more than total duration");
        }
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        String str;
        FyberLogger.e("VideoPlayerView", "An error occurred, error: " + i);
        this.c.stopPlayback();
        switch (i2) {
            case ExploreByTouchHelper.INVALID_ID /* -2147483648 */:
                str = "media_error_system";
                break;
            case IabHelper.IABHELPER_INVALID_CONSUMPTION /* -1010 */:
                str = "media_error_unsupported";
                break;
            case IabHelper.IABHELPER_MISSING_TOKEN /* -1007 */:
                str = "media_error_malformed";
                break;
            case IabHelper.IABHELPER_SEND_INTENT_FAILED /* -1004 */:
                str = "media_error_io";
                break;
            case -110:
                str = "media_error_time_out";
                break;
            default:
                str = "media_error_unknown";
                break;
        }
        this.x.a(str);
        j();
        a(com.fyber.c.d.c.f);
        this.H = true;
        return true;
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        FyberLogger.d("VideoPlayerView", "onTouch()");
        if (StringUtils.notNullNorEmpty(this.m) && !this.l && this.d != null) {
            this.d.pause();
            this.l = true;
            if (((Boolean) this.n.getTag()).booleanValue()) {
                this.n.setTag(false);
                this.n.setVisibility(8);
            }
            this.x.c();
            if (u.a(this.m)) {
                if (this.t != null) {
                    this.t.setVisibility(0);
                } else {
                    this.t = new com.fyber.c.a.a(this.a, this.m);
                    this.t.a(new com.fyber.c.a.a.b() { // from class: com.fyber.c.d.d.1
                        @Override // com.fyber.c.a.a.b
                        public final void a() {
                            d.this.c();
                        }
                    });
                    this.t.a(new com.fyber.c.a.a.InterfaceC0020a() { // from class: com.fyber.c.d.d.2
                        @Override // com.fyber.c.a.a.InterfaceC0020a
                        public final void a() {
                            d.this.a("redirect_fail", "Redirection Error");
                        }
                    });
                    this.t.a(new com.fyber.c.a.a.c() { // from class: com.fyber.c.d.d.3
                        @Override // com.fyber.c.a.a.c
                        public final boolean a(com.fyber.c.a.a aVar, String str) {
                            return d.this.u != null && d.this.u.a(d.this, aVar, str);
                        }
                    });
                    this.a.addContentView(this.t, new FrameLayout.LayoutParams(-1, -1));
                }
                a((View) this.g, false);
                return true;
            }
            if (this.u != null) {
                this.u.a(this, null, this.m);
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (!this.l) {
            b("abort_btn", "closed button was pressed");
        }
    }

    @Override // com.fyber.mediation.MediationUserActivityListener
    public final void notifyOnUserLeft() {
        a("app_background", "notifyOnUserLeft()");
    }

    @Override // com.fyber.mediation.MediationUserActivityListener
    public final boolean notifyOnBackPressed() {
        if (this.A) {
            return false;
        }
        if (!this.l) {
            if (this.z) {
                if (getVisibility() != 0 || this.d == null) {
                    a("back_btn", "back button was pressed");
                    return false;
                }
                b("back_btn", "back button was pressed");
            }
        } else if (this.t != null && !this.t.c()) {
            c();
        }
        return true;
    }

    public final void c() {
        if (this.t != null) {
            this.t.setVisibility(8);
            this.t.a();
        }
        if (this.d != null) {
            this.d.start();
        }
        a((View) this.g, true);
        this.l = false;
    }

    private void d() {
        if (this.w != null && !this.w.isShutdown()) {
            this.w.shutdownNow();
        }
        CacheManager.a().c(getContext());
    }

    @Override // java.lang.Runnable
    public final void run() {
        int orientation = this.a.getWindowManager().getDefaultDisplay().getOrientation();
        if (this.C != orientation) {
            this.B++;
            if (this.B == 6) {
                this.B = 0;
                this.C = orientation;
                return;
            }
            return;
        }
        if (this.H) {
            return;
        }
        if (this.d.isPlaying() || n.a()) {
            if (this.q > this.e + TJAdUnitConstants.DEFAULT_VOLUME_CHECK_INTERVAL) {
                onCompletion(null);
            }
            this.q = this.c.getCurrentPosition();
            if (((Boolean) this.I.getTag()).booleanValue() && this.q > 120) {
                this.I.setTag(false);
                a(com.fyber.c.d.c.h);
            }
            this.v++;
            if (this.v == 20) {
                this.v = 0;
            }
            if ((this.v != 0 && this.v != 10) || !this.D.a(this.q, this.F, k())) {
                a(com.fyber.c.d.c.c);
                if (this.v == 0) {
                    this.x.b(this.q);
                }
                if (this.n != null && ((Boolean) this.n.getTag()).booleanValue() && this.q > this.k) {
                    a(com.fyber.c.d.c.e);
                }
                if (this.g != null && ((Boolean) this.g.getTag()).booleanValue() && this.q > this.h.intValue()) {
                    a(com.fyber.c.d.c.d);
                }
            }
        }
    }

    public final void a(String str, String str2) {
        FyberLogger.d("VideoPlayerView", "video cancelling: " + str2);
        this.x.d();
        j();
        d();
        e();
        a(2, str);
    }

    private void e() {
        this.A = true;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            f();
        } else {
            a(com.fyber.c.d.c.i);
        }
        this.c = null;
        this.n = null;
        this.g = null;
        this.j = null;
        this.d = null;
        this.f = null;
        this.a = null;
        if (this.t == null) {
            return;
        }
        this.t.b();
        this.t = null;
    }

    private void a(int i, String str) {
        if (this.E != null) {
            this.E.a(i, str);
            this.E = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup != null) {
            viewGroup.removeView(this);
        }
        if (this.f != null) {
            this.f.b();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        this.g.setTag(false);
        if (!a(this.g)) {
            addView(this.g);
        }
    }

    private float h() {
        try {
            Object obj = this.a.getPackageManager().getApplicationInfo(this.a.getPackageName(), 128).metaData.get("FYBVideoPlayerOptionCloseButtonDelay");
            if (obj != null) {
                return Float.parseFloat(obj.toString());
            }
        } catch (PackageManager.NameNotFoundException | NullPointerException | NumberFormatException e) {
            FyberLogger.e("VideoPlayerView", "Failed to load meta-data from Manifest: " + e.getMessage());
        }
        return BitmapDescriptorFactory.HUE_RED;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        if (this.f != null) {
            this.f.setVisibility(8);
        }
    }

    private void b(String str, String str2) {
        if (this.r) {
            this.d.pause();
            this.G = true;
            a(r.a(Fyber.Settings.UIStringIdentifier.RV_ALERT_DIALOG_EXIT_VIDEO_TEXT), this.s, r.a(Fyber.Settings.UIStringIdentifier.RV_ALERT_DIALOG_CLOSE_VIDEO_TEXT), r.a(Fyber.Settings.UIStringIdentifier.RV_ALERT_DIALOG_RESUME_VIDEO_TEXT), str);
            return;
        }
        a(str, str2);
    }

    @Override // com.fyber.c.d.a.a.InterfaceC0021a
    public final void a(boolean z) {
        FyberLogger.i("VideoPlayerView", "onBufferingStateChanged - state = " + z);
        if (z) {
            a(com.fyber.c.d.c.b);
            if (n.a()) {
                this.d.pause();
                this.F = false;
                return;
            }
            return;
        }
        a(com.fyber.c.d.c.a);
        if (!k()) {
            this.d.start();
        }
        if (n.a()) {
            this.F = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, String str2, String str3, String str4, final String str5) {
        if (!this.a.isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.a);
            if (StringUtils.notNullNorEmpty(str4)) {
                builder.setPositiveButton(str4, new DialogInterface.OnClickListener() { // from class: com.fyber.c.d.d.5
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        d.c(d.this);
                        if (d.this.F) {
                            d.this.d.start();
                        }
                    }
                });
            }
            builder.setTitle(str).setMessage(str2).setCancelable(false).setNegativeButton(str3, new DialogInterface.OnClickListener() { // from class: com.fyber.c.d.d.6
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    d.this.a(str5, "Close button on the dialog was pressed");
                }
            }).create().show();
        }
    }

    private static boolean a(View view) {
        return view.getParent() != null;
    }

    private void j() {
        this.y.removeMessages(com.fyber.c.d.c.g - 1);
    }

    private void a(int i) {
        this.y.sendEmptyMessage(i - 1);
    }

    private boolean k() {
        return this.l || this.G;
    }

    private static void a(View view, boolean z) {
        if (view != null) {
            view.setClickable(z);
        }
    }

    /* JADX INFO: compiled from: VideoPlayerView.java */
    private static class c extends Handler {
        private final WeakReference<d> a;

        c(d dVar) {
            this.a = new WeakReference<>(dVar);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = com.fyber.c.d.c.a()[message.what];
            d dVar = this.a.get();
            if (dVar != null) {
                switch (AnonymousClass7.a[i - 1]) {
                    case 1:
                        d.f(dVar);
                        break;
                    case 2:
                        d.g(dVar);
                        FyberLogger.d("VideoPlayerView", "Buffering video");
                        break;
                    case 3:
                        dVar.i();
                        FyberLogger.d("VideoPlayerView", "No longer buffering video");
                        break;
                    case 4:
                        if (dVar.j != null) {
                            dVar.j.a(dVar.q);
                        }
                        break;
                    case 5:
                        d.k(dVar);
                        dVar.g();
                        break;
                    case 6:
                        d.m(dVar);
                        break;
                    case 7:
                        dVar.i();
                        dVar.a(r.a(Fyber.Settings.UIStringIdentifier.RV_ALERT_DIALOG_TITLE), r.a(Fyber.Settings.UIStringIdentifier.RV_ERROR_DIALOG_MESSAGE_DEFAULT), "OK", (String) null, TJAdUnitConstants.String.VIDEO_ERROR);
                        break;
                    case 8:
                        FyberLogger.i("VideoPlayerView", "displayErrorLoadingDialog(): Error Loading video");
                        d.n(dVar);
                        d.o(dVar);
                        dVar.a(r.a(Fyber.Settings.UIStringIdentifier.RV_ALERT_DIALOG_TITLE), r.a(Fyber.Settings.UIStringIdentifier.RV_ALERT_DIALOG_MESSAGE), "OK", (String) null, TJAdUnitConstants.String.VIDEO_ERROR);
                        break;
                    case 9:
                        dVar.f();
                        break;
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.fyber.c.d.d$7, reason: invalid class name */
    /* JADX INFO: compiled from: VideoPlayerView.java */
    static /* synthetic */ class AnonymousClass7 {
        static final /* synthetic */ int[] a = new int[com.fyber.c.d.c.a().length];

        static {
            try {
                a[com.fyber.c.d.c.h - 1] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[com.fyber.c.d.c.b - 1] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[com.fyber.c.d.c.a - 1] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[com.fyber.c.d.c.c - 1] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[com.fyber.c.d.c.d - 1] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[com.fyber.c.d.c.e - 1] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                a[com.fyber.c.d.c.f - 1] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                a[com.fyber.c.d.c.g - 1] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                a[com.fyber.c.d.c.i - 1] = 9;
            } catch (NoSuchFieldError e9) {
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (!k()) {
            super.onLayout(z, i, i2, i3, i4);
        }
    }

    /* JADX INFO: compiled from: VideoPlayerView.java */
    public static class a {
        private boolean a = true;
        private com.fyber.c.d.b b;
        private String c;
        private String d;
        private String e;
        private String f;
        private InterfaceC0022d g;
        private Float h;
        private b i;

        public final a a(com.fyber.c.d.b bVar) {
            this.b = bVar;
            return this;
        }

        public final a a(String str) {
            this.c = str;
            return this;
        }

        public final a b(String str) {
            this.d = str;
            return this;
        }

        public final a c(String str) {
            this.f = str;
            return this;
        }

        public final a d(String str) {
            this.e = str;
            return this;
        }

        public final a a(boolean z) {
            this.a = z;
            return this;
        }

        public final a e(String str) {
            if (StringUtils.notNullNorEmpty(str)) {
                this.a = Boolean.parseBoolean(str);
            }
            return this;
        }

        public final a a(Float f) {
            this.h = f;
            return this;
        }

        public final a a(InterfaceC0022d interfaceC0022d) {
            this.g = interfaceC0022d;
            return this;
        }

        public final a a(b bVar) {
            this.i = bVar;
            return this;
        }

        public final d a(@NonNull Activity activity) {
            return new d(activity, this, (byte) 0);
        }
    }

    public final void a() {
        this.c = new VideoView(this.a);
        this.c.setContentDescription("videoPlayer");
        this.g = new com.fyber.c.b.a(this.a);
        int iA = this.g.a();
        this.g.setLayoutParams(new FrameLayout.LayoutParams(iA, iA, 53));
        this.g.setTag(true);
        this.f = new com.fyber.c.e.b(this.a);
        this.f.setLayoutParams(new FrameLayout.LayoutParams(-2, -2, 17));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        layoutParams.gravity = 17;
        this.c.setLayoutParams(layoutParams);
        this.I = new FrameLayout(this.a);
        this.I.setBackgroundColor(-16777216);
        this.I.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        this.I.setTag(true);
        setBackgroundColor(-16777216);
        addView(this.c);
        addView(this.I);
        addView(this.f);
        if (StringUtils.notNullNorEmpty(this.m)) {
            this.n = new TextView(this.a);
            this.n.setTag(true);
            this.n.setGravity(17);
            this.n.setLayoutParams(new FrameLayout.LayoutParams(-1, (int) TypedValue.applyDimension(1, 30.0f, getResources().getDisplayMetrics()), 80));
            if (StringUtils.notNullNorEmpty(this.o)) {
                this.n.setText(this.o);
            } else {
                this.n.setText(r.a(Fyber.Settings.UIStringIdentifier.RV_CLICKTHROUGH_HINT));
            }
            this.n.setBackgroundColor(-1304543682);
            this.n.setTextColor(-1);
            this.n.setTextSize(1, 14.0f);
            this.n.setContentDescription("clickThroughHint");
        }
        Uri uriA = CacheManager.a().a(this.b, getContext());
        this.c.setVideoURI(uriA);
        this.x.a(this.b, uriA.toString(), CacheManager.a().b().a());
        this.D = new com.fyber.c.d.a.a(this);
        this.c.requestFocus();
        this.y.sendEmptyMessageDelayed(com.fyber.c.d.c.g - 1, 15000L);
        this.c.setOnPreparedListener(this);
        this.c.setOnCompletionListener(this);
        this.c.setOnErrorListener(this);
        this.c.setOnTouchListener(this);
        this.g.setOnClickListener(this);
        if (!(this.a instanceof RewardedVideoActivity)) {
            return;
        }
        ((RewardedVideoActivity) this.a).setRewardedVideoListener(this);
    }

    static /* synthetic */ void f(d dVar) {
        dVar.removeView(dVar.I);
    }

    static /* synthetic */ void g(d dVar) {
        if (dVar.f == null) {
            return;
        }
        dVar.f.setVisibility(0);
    }

    static /* synthetic */ void m(d dVar) {
        dVar.n.setTag(false);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.7f, BitmapDescriptorFactory.HUE_RED);
        alphaAnimation.setDuration(1000L);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.fyber.c.d.d.4
            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationEnd(Animation animation) {
                d.this.n.setVisibility(8);
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationRepeat(Animation animation) {
            }
        });
        dVar.n.startAnimation(alphaAnimation);
    }

    static /* synthetic */ void o(d dVar) {
        dVar.x.a();
    }
}
