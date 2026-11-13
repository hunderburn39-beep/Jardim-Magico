package com.tapjoy;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.webkit.ConsoleMessage;
import android.webkit.WebView;
import android.widget.VideoView;
import com.r7games.slotpantanal.utils.IabHelper;
import com.tapjoy.internal.cr;
import com.tapjoy.internal.gi;
import com.tapjoy.mraid.listener.MraidViewListener;
import com.tapjoy.mraid.view.BasicWebView;
import com.tapjoy.mraid.view.MraidView;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class TJAdUnit implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnPreparedListener {
    public static TJVideoListener publisherVideoListener;
    private TJAdUnitWebViewListener a;
    private TJAdUnitVideoListener b;
    private Context c;
    private TJAdUnitActivity d;
    private TJAdUnitJSBridge e;
    private BasicWebView f;
    private MraidView g;
    private VideoView h;
    private int i;
    private boolean j;
    private boolean k;
    private ScheduledFuture m;
    private AudioManager n;
    private int p;
    private Runnable q;
    private boolean r;
    private boolean s;
    private volatile boolean t;
    private boolean u;
    private Handler x;
    private Timer l = new Timer();
    private int o = 0;
    private int v = -1;
    private final Runnable w = new Runnable() { // from class: com.tapjoy.TJAdUnit.1
        @Override // java.lang.Runnable
        public final void run() {
            int streamVolume = TJAdUnit.this.n.getStreamVolume(3);
            if (TJAdUnit.this.o != streamVolume) {
                TJAdUnit.this.o = streamVolume;
                TJAdUnit.c(TJAdUnit.this);
            }
        }
    };

    public interface TJAdUnitVideoListener {
        void onVideoCompleted();

        void onVideoError(String str);

        void onVideoStart();
    }

    public interface TJAdUnitWebViewListener {
        void onClosed();

        void onContentReady();
    }

    static /* synthetic */ boolean e(TJAdUnit tJAdUnit) {
        tJAdUnit.t = true;
        return true;
    }

    static /* synthetic */ int p(TJAdUnit tJAdUnit) {
        tJAdUnit.i = 0;
        return 0;
    }

    static /* synthetic */ boolean s(TJAdUnit tJAdUnit) {
        tJAdUnit.u = true;
        return true;
    }

    public TJAdUnit() {
    }

    public TJAdUnit(Context ctx) {
        setContext(ctx);
    }

    public boolean preload(TJPlacementData placementData) {
        if (this.q != null || !placementData.isPrerenderingRequested() || !TJPlacementManager.canPreRenderPlacement()) {
            fireContentReady();
            return false;
        }
        TapjoyLog.i("TJAdUnit", "Pre-rendering ad unit for placement: " + placementData.getPlacementName());
        TJPlacementManager.incrementPlacementPreRenderCount();
        load(placementData);
        return true;
    }

    public void load(final TJPlacementData placementData) {
        if (!this.t) {
            this.q = new Runnable() { // from class: com.tapjoy.TJAdUnit.2
                @Override // java.lang.Runnable
                public final void run() {
                    if (!TJAdUnit.this.t) {
                        TJAdUnit.e(TJAdUnit.this);
                        TJAdUnit.this.f = new BasicWebView(TJAdUnit.this.c);
                        TJAdUnit.this.f.loadDataWithBaseURL(null, "<!DOCTYPE html><html><head><title>Tapjoy Background Webview</title></head></html>", "text/html", "utf-8", null);
                        TJAdUnit.this.g = new MraidView(TJAdUnit.this.c);
                        TJAdUnit.this.g.setListener(new a(TJAdUnit.this, (byte) 0));
                        TJAdUnit.this.h = new VideoView(TJAdUnit.this.c);
                        TJAdUnit.this.h.setOnCompletionListener(TJAdUnit.this);
                        TJAdUnit.this.h.setOnErrorListener(TJAdUnit.this);
                        TJAdUnit.this.h.setOnPreparedListener(TJAdUnit.this);
                        TJAdUnit.this.h.setVisibility(4);
                        TJAdUnit.this.e = new TJAdUnitJSBridge(TJAdUnit.this.c, TJAdUnit.this);
                        if (!cr.c(placementData.getRedirectURL())) {
                            if (placementData.isPreloadDisabled()) {
                                TJAdUnit.this.g.postUrl(placementData.getRedirectURL(), null);
                                return;
                            } else {
                                TJAdUnit.this.g.loadUrl(placementData.getRedirectURL());
                                return;
                            }
                        }
                        if (placementData.getBaseURL() != null && placementData.getHttpResponse() != null) {
                            TJAdUnit.this.g.loadDataWithBaseURL(placementData.getBaseURL(), placementData.getHttpResponse(), "text/html", "utf-8", null);
                        } else {
                            TapjoyLog.e("TJAdUnit", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SDK_ERROR, "Error loading ad unit content"));
                        }
                    }
                }
            };
            TapjoyUtil.runOnMainThread(this.q);
        }
    }

    public void resume(TJAdUnitSaveStateData saveStateData) {
        if (this.e.didLaunchOtherActivity) {
            TapjoyLog.d("TJAdUnit", "onResume bridge.didLaunchOtherActivity callbackID: " + this.e.otherActivityCallbackID);
            this.e.invokeJSCallback(this.e.otherActivityCallbackID, Boolean.TRUE);
            this.e.didLaunchOtherActivity = false;
        }
        if (saveStateData != null) {
            this.i = saveStateData.seekTime;
            this.h.seekTo(this.i);
        }
    }

    public void invokeBridgeCallback(String callbackID, Object... argArray) {
        if (this.e != null && callbackID != null) {
            this.e.invokeJSCallback(callbackID, argArray);
        }
    }

    public void destroy() {
        this.e.destroy();
        if (this.f != null) {
            this.f.removeAllViews();
            this.f = null;
        }
        if (this.g != null) {
            this.g.removeAllViews();
            this.g = null;
        }
        this.l.cancel();
        this.u = false;
        this.t = false;
        this.s = false;
        this.c = null;
        this.d = null;
        a();
        if (this.a != null) {
            this.a.onClosed();
        }
    }

    public void setVisible(boolean visible) {
        this.s = visible;
        if (!this.s || !this.u) {
            return;
        }
        this.e.display();
    }

    public void fireContentReady() {
        if (this.a != null) {
            this.a.onContentReady();
        }
    }

    public void closeRequested(boolean shouldForceClose) {
        if (this.g != null && this.g.videoPlaying()) {
            this.g.videoViewCleanup();
        } else {
            this.e.closeRequested(Boolean.valueOf(shouldForceClose));
        }
    }

    public void setOrientation(int requestedOrientation) {
        int i = 0;
        if (this.d == null) {
            return;
        }
        if (this.d != null) {
            int rotation = this.d.getWindowManager().getDefaultDisplay().getRotation();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            this.d.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int i2 = displayMetrics.widthPixels;
            int i3 = displayMetrics.heightPixels;
            if (((rotation != 0 && rotation != 2) || i3 <= i2) && ((rotation != 1 && rotation != 3) || i2 <= i3)) {
                switch (rotation) {
                    case 0:
                        break;
                    case 1:
                        i = 1;
                        break;
                    case 2:
                        i = 8;
                        break;
                    case 3:
                        i = 9;
                        break;
                    default:
                        TapjoyLog.w("TJAdUnit", "Unknown screen orientation. Defaulting to landscape.");
                        break;
                }
            } else {
                switch (rotation) {
                    case 0:
                        i = 1;
                        break;
                    case 1:
                        break;
                    case 2:
                        i = 9;
                        break;
                    case 3:
                        i = 8;
                        break;
                    default:
                        i = 1;
                        break;
                }
            }
        } else {
            i = -1;
        }
        if (this.v != -1) {
            i = this.v;
        }
        if ((a(i) && a(requestedOrientation)) || (b(i) && b(requestedOrientation))) {
            requestedOrientation = this.v;
        }
        this.d.setRequestedOrientation(requestedOrientation);
        this.v = requestedOrientation;
        this.r = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        TapjoyLog.d("TJAdUnit", "detachVolumeListener");
        if (this.m != null) {
            this.m.cancel(false);
            this.m = null;
        }
        this.n = null;
    }

    private static boolean a(int i) {
        return i == 0 || i == 8 || i == 6 || i == 11;
    }

    private static boolean b(int i) {
        return i == 1 || i == 9 || i == 7 || i == 12;
    }

    public void setContext(Context ctx) {
        this.c = ctx;
        if (this.g != null) {
            this.g.setContext(this.c);
        }
        if (this.e != null) {
            this.e.setContext(this.c);
        }
        if (this.c instanceof TJAdUnitActivity) {
            this.d = (TJAdUnitActivity) this.c;
        }
    }

    public void setBackgroundColor(final String hexColor, final TJAdUnitJSBridge.AdUnitAsyncTaskListner adUnitAsyncTaskListner) {
        TapjoyUtil.runOnMainThread(new Runnable() { // from class: com.tapjoy.TJAdUnit.3
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    TapjoyLog.d("TJAdUnit", "setBackgroundColor: " + hexColor);
                    TJAdUnit.this.f.setBackgroundColor(Color.parseColor(hexColor));
                    adUnitAsyncTaskListner.onComplete(true);
                } catch (Exception e) {
                    TapjoyLog.d("TJAdUnit", "Error setting background color. backgroundWebView: " + TJAdUnit.this.f + ", hexColor: " + hexColor);
                    adUnitAsyncTaskListner.onComplete(false);
                }
            }
        });
    }

    public void setBackgroundContent(final String backgroundHTML, final TJAdUnitJSBridge.AdUnitAsyncTaskListner adUnitAsyncTaskListner) {
        TapjoyUtil.runOnMainThread(new Runnable() { // from class: com.tapjoy.TJAdUnit.4
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    TapjoyLog.d("TJAdUnit", "setBackgroundContent: " + backgroundHTML);
                    TJAdUnit.this.f.loadDataWithBaseURL(null, backgroundHTML, "text/html", "utf-8", null);
                    adUnitAsyncTaskListner.onComplete(true);
                } catch (Exception e) {
                    TapjoyLog.d("TJAdUnit", "Error setting background content. backgroundWebView: " + TJAdUnit.this.f + ", content: " + backgroundHTML);
                    adUnitAsyncTaskListner.onComplete(false);
                }
            }
        });
    }

    public void setWebViewListener(TJAdUnitWebViewListener adUnitWebViewListener) {
        this.a = adUnitWebViewListener;
    }

    public void setVideoListener(TJAdUnitVideoListener adUnitVideoListener) {
        this.b = adUnitVideoListener;
    }

    public int getOrientation() {
        return this.v;
    }

    public boolean isAdUnitConstructed() {
        return this.t;
    }

    public boolean isLockedOrientation() {
        return this.r;
    }

    public BasicWebView getBackgroundWebView() {
        return this.f;
    }

    public MraidView getWebView() {
        return this.g;
    }

    public boolean getCloseRequested() {
        return this.e.closeRequested;
    }

    public void loadVideoUrl(final String videoURL, final TJAdUnitJSBridge.AdUnitAsyncTaskListner adUnitAsyncTaskListner) {
        TapjoyUtil.runOnMainThread(new Runnable() { // from class: com.tapjoy.TJAdUnit.5
            @Override // java.lang.Runnable
            public final void run() {
                if (TJAdUnit.this.h != null) {
                    TapjoyLog.i("TJAdUnit", "loadVideoUrl: " + videoURL);
                    TJAdUnit.this.h.setVisibility(0);
                    TJAdUnit.this.h.setVideoPath(videoURL);
                    TJAdUnit.this.h.seekTo(0);
                    adUnitAsyncTaskListner.onComplete(true);
                    return;
                }
                adUnitAsyncTaskListner.onComplete(false);
            }
        });
    }

    public boolean playVideo() {
        TapjoyLog.i("TJAdUnit", "playVideo");
        if (this.h == null) {
            return false;
        }
        this.h.start();
        if (this.x == null) {
            this.x = new Handler(Looper.getMainLooper());
        }
        this.x.postDelayed(new Runnable() { // from class: com.tapjoy.TJAdUnit.6
            @Override // java.lang.Runnable
            public final void run() {
                if (TJAdUnit.this.h.getCurrentPosition() != 0) {
                    if (!TJAdUnit.this.k) {
                        TJAdUnit.this.k = true;
                        TJAdUnit.this.fireOnVideoStart();
                    }
                    TJAdUnit.this.e.onVideoStarted(TJAdUnit.this.i);
                    TJAdUnit.m(TJAdUnit.this);
                    return;
                }
                TJAdUnit.this.x.postDelayed(this, 200L);
            }
        }, 200L);
        return true;
    }

    public boolean pauseVideo() {
        if (this.h == null || !this.h.isPlaying()) {
            return false;
        }
        this.l.cancel();
        this.h.pause();
        this.i = this.h.getCurrentPosition();
        TapjoyLog.i("TJAdUnit", "Video paused at: " + this.i);
        this.e.onVideoPaused(this.i);
        return true;
    }

    public void clearVideo(final TJAdUnitJSBridge.AdUnitAsyncTaskListner adUnitAsyncTaskListner) {
        if (this.h != null) {
            TapjoyUtil.runOnMainThread(new Runnable() { // from class: com.tapjoy.TJAdUnit.7
                @Override // java.lang.Runnable
                public final void run() {
                    TJAdUnit.this.l.cancel();
                    TJAdUnit.this.h.setVisibility(4);
                    TJAdUnit.this.h.stopPlayback();
                    TJAdUnit.this.k = false;
                    TJAdUnit.p(TJAdUnit.this);
                    adUnitAsyncTaskListner.onComplete(true);
                }
            });
        } else {
            adUnitAsyncTaskListner.onComplete(false);
        }
    }

    public void attachVolumeListener(boolean isAttached, int interval) {
        TapjoyLog.d("TJAdUnit", "attachVolumeListener: isAttached=" + isAttached + "; interval=" + interval);
        a();
        if (isAttached) {
            this.n = (AudioManager) this.c.getSystemService("audio");
            this.o = this.n.getStreamVolume(3);
            this.p = this.n.getStreamMaxVolume(3);
            this.m = gi.a.scheduleWithFixedDelay(this.w, interval, interval, TimeUnit.MILLISECONDS);
        }
    }

    public VideoView getVideoView() {
        return this.h;
    }

    public int getVideoSeekTime() {
        return this.i;
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mp) {
        TapjoyLog.i("TJAdUnit", "video -- onPrepared");
        final int duration = this.h.getDuration();
        final int measuredWidth = this.h.getMeasuredWidth();
        final int measuredHeight = this.h.getMeasuredHeight();
        if (this.i > 0 && this.h.getCurrentPosition() != this.i) {
            mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() { // from class: com.tapjoy.TJAdUnit.8
                @Override // android.media.MediaPlayer.OnSeekCompleteListener
                public final void onSeekComplete(MediaPlayer mp2) {
                    TJAdUnit.this.e.onVideoReady(duration, measuredWidth, measuredHeight);
                }
            });
        } else {
            this.e.onVideoReady(duration, measuredWidth, measuredHeight);
        }
        mp.setOnInfoListener(this);
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mp, int what, int extra) {
        String str;
        String str2;
        TapjoyLog.e("TJAdUnit", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.SDK_ERROR, "Error encountered when instantiating the VideoView: " + what + " - " + extra));
        this.j = true;
        this.l.cancel();
        switch (what) {
            case 100:
                str = "MEDIA_ERROR_SERVER_DIED";
                break;
            default:
                str = "MEDIA_ERROR_UNKNOWN";
                break;
        }
        String str3 = str + " -- ";
        switch (extra) {
            case IabHelper.IABHELPER_INVALID_CONSUMPTION /* -1010 */:
                str2 = "MEDIA_ERROR_UNSUPPORTED";
                break;
            case IabHelper.IABHELPER_MISSING_TOKEN /* -1007 */:
                str2 = "MEDIA_ERROR_MALFORMED";
                break;
            case IabHelper.IABHELPER_SEND_INTENT_FAILED /* -1004 */:
                str2 = str3 + "MEDIA_ERROR_IO";
                break;
            case -110:
                str2 = "MEDIA_ERROR_TIMED_OUT";
                break;
            default:
                str2 = "MEDIA_ERROR_EXTRA_UNKNOWN";
                break;
        }
        this.e.onVideoError(str2);
        fireOnVideoError(str2);
        return what == 1 || extra == -1004;
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mp) {
        TapjoyLog.i("TJAdUnit", "video -- onCompletion");
        this.l.cancel();
        if (!this.j) {
            this.e.onVideoCompletion();
            fireOnVideoComplete();
        }
        this.j = false;
    }

    public void fireOnVideoStart() {
        TapjoyLog.v("TJAdUnit", "Firing onVideoStart");
        if (getPublisherVideoListener() != null) {
            getPublisherVideoListener().onVideoStart();
        }
        if (this.b != null) {
            this.b.onVideoStart();
        }
    }

    public void fireOnVideoError(String errorMessage) {
        TapjoyLog.e("TJAdUnit", "Firing onVideoError with error: " + errorMessage);
        if (getPublisherVideoListener() != null) {
            getPublisherVideoListener().onVideoError(3);
        }
        if (this.b != null) {
            this.b.onVideoError(errorMessage);
        }
    }

    public void fireOnVideoComplete() {
        TapjoyLog.v("TJAdUnit", "Firing onVideoComplete");
        if (getPublisherVideoListener() != null) {
            getPublisherVideoListener().onVideoComplete();
        }
        if (this.b != null) {
            this.b.onVideoCompleted();
        }
    }

    public String getVolume() {
        return String.format("%.2f", Double.valueOf(((double) this.o) / ((double) this.p)));
    }

    public boolean isMuted() {
        return this.o == 0;
    }

    @Override // android.media.MediaPlayer.OnInfoListener
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        String str = "";
        switch (what) {
            case 3:
                str = "MEDIA_INFO_VIDEO_RENDERING_START";
                break;
            case 700:
                str = "MEDIA_INFO_VIDEO_TRACK_LAGGING";
                break;
            case 701:
                str = "MEDIA_INFO_BUFFERING_START";
                break;
            case 702:
                str = "MEDIA_INFO_BUFFERING_END";
                break;
            case 801:
                str = "MEDIA_INFO_NOT_SEEKABLE";
                break;
        }
        this.e.onVideoInfo(str);
        return false;
    }

    public TJVideoListener getPublisherVideoListener() {
        return publisherVideoListener;
    }

    class a implements MraidViewListener {
        private a() {
        }

        /* synthetic */ a(TJAdUnit tJAdUnit, byte b) {
            this();
        }

        @Override // com.tapjoy.mraid.listener.MraidViewListener
        public final boolean onResizeClose() {
            return false;
        }

        @Override // com.tapjoy.mraid.listener.MraidViewListener
        public final boolean onResize() {
            return false;
        }

        @Override // com.tapjoy.mraid.listener.MraidViewListener
        public final boolean onReady() {
            return false;
        }

        @Override // com.tapjoy.mraid.listener.MraidViewListener
        public final boolean onEventFired() {
            return false;
        }

        @Override // com.tapjoy.mraid.listener.MraidViewListener
        public final boolean onClose() {
            if (TJAdUnit.this.d != null) {
                TJAdUnit.this.d.handleClose();
                return false;
            }
            return false;
        }

        @Override // com.tapjoy.mraid.listener.MraidViewListener
        @TargetApi(8)
        public final boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            if (TJAdUnit.this.e.closeRequested) {
                String[] strArr = {"Uncaught", "uncaught", "Error", TJAdUnitConstants.String.VIDEO_ERROR, "not defined"};
                TapjoyLog.d("TJAdUnit", "shouldClose...");
                if (TJAdUnit.this.d != null) {
                    for (int i = 0; i < 5; i++) {
                        if (consoleMessage.message().contains(strArr[i])) {
                            TJAdUnit.this.d.handleClose();
                            break;
                        }
                    }
                }
            }
            return true;
        }

        @Override // com.tapjoy.mraid.listener.MraidViewListener
        public final void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            if (TJAdUnit.this.d != null) {
                TJAdUnit.this.d.showErrorDialog();
            }
        }

        @Override // com.tapjoy.mraid.listener.MraidViewListener
        public final void onPageStarted(WebView view, String url, Bitmap favicon) {
            TapjoyLog.d("TJAdUnit", "onPageStarted: " + url);
            if (TJAdUnit.this.e != null) {
                TJAdUnit.this.e.allowRedirect = true;
                TJAdUnit.this.e.customClose = false;
                TJAdUnit.this.e.closeRequested = false;
                TJAdUnit.this.a();
            }
        }

        @Override // com.tapjoy.mraid.listener.MraidViewListener
        public final void onPageFinished(WebView view, String url) {
            TapjoyLog.d("TJAdUnit", "onPageFinished: " + url);
            if (TJAdUnit.this != null && TJAdUnit.this.g != null && TJAdUnit.this.g.isMraid()) {
                TJAdUnit.this.e.allowRedirect = false;
            }
            if (TJAdUnit.this.d != null) {
                TJAdUnit.this.d.setProgressSpinnerVisibility(false);
            }
            TJAdUnit.s(TJAdUnit.this);
            if (TJAdUnit.this.s) {
                TJAdUnit.u(TJAdUnit.this);
            }
            TJAdUnit.this.e.flushMessageQueue();
        }

        @Override // com.tapjoy.mraid.listener.MraidViewListener
        @TargetApi(9)
        public final boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!a()) {
                if (TJAdUnit.this.d == null) {
                    return true;
                }
                TJAdUnit.this.d.showErrorDialog();
                return true;
            }
            TapjoyLog.d("TJAdUnit", "interceptURL: " + url);
            if (TJAdUnit.this != null && TJAdUnit.this.g != null && TJAdUnit.this.g.isMraid() && url.contains("mraid")) {
                return false;
            }
            if (!a(url)) {
                if (TJAdUnit.this.e.allowRedirect) {
                    return false;
                }
                view.loadUrl(url);
                return true;
            }
            TapjoyLog.d("TJAdUnit", "Open redirecting URL:" + url);
            ((MraidView) view).loadUrlStandard(url);
            return true;
        }

        private static boolean a(String str) {
            try {
                String host = new URL(TapjoyConfig.TJC_SERVICE_URL).getHost();
                return (host != null && str.contains(host)) || str.contains(TapjoyConnectCore.getRedirectDomain()) || str.contains(TapjoyUtil.getRedirectDomain(TapjoyConnectCore.getPlacementURL()));
            } catch (MalformedURLException e) {
                return false;
            }
        }

        private boolean a() {
            try {
                NetworkInfo activeNetworkInfo = TJAdUnit.this.g.getConnectivityManager().getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected();
            } catch (Exception e) {
                return false;
            }
        }
    }

    static /* synthetic */ void c(TJAdUnit tJAdUnit) {
        tJAdUnit.e.onVolumeChanged();
    }

    static /* synthetic */ void m(TJAdUnit tJAdUnit) {
        tJAdUnit.l.cancel();
        tJAdUnit.l = new Timer();
        tJAdUnit.l.schedule(new TimerTask() { // from class: com.tapjoy.TJAdUnit.9
            @Override // java.util.TimerTask, java.lang.Runnable
            public final void run() {
                TJAdUnit.this.e.onVideoProgress(TJAdUnit.this.h.getCurrentPosition());
            }
        }, 500L, 500L);
    }

    static /* synthetic */ void u(TJAdUnit tJAdUnit) {
        tJAdUnit.e.display();
    }
}
