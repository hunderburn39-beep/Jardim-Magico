package com.tapjoy.mraid.view;

import android.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.URLUtil;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;
import com.google.android.gms.drive.DriveFile;
import com.heyzap.http.AsyncHttpResponseHandler;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyCache;
import com.tapjoy.TapjoyCachedAssetData;
import com.tapjoy.TapjoyErrorMessage;
import com.tapjoy.TapjoyHttpURLResponse;
import com.tapjoy.TapjoyLog;
import com.tapjoy.TapjoyURLConnection;
import com.tapjoy.TapjoyUtil;
import com.tapjoy.mraid.controller.Abstract;
import com.tapjoy.mraid.controller.Utility;
import com.tapjoy.mraid.listener.MraidViewListener;
import com.tapjoy.mraid.listener.Player;
import com.tapjoy.mraid.util.MraidPlayer;
import com.tapjoy.mraid.util.Utils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
public class MraidView extends BasicWebView implements ViewTreeObserver.OnGlobalLayoutListener {
    public static final String ACTION_KEY = "action";
    public static final String DIMENSIONS = "expand_dimensions";
    public static final String EXPAND_URL = "expand_url";
    public static final int MRAID_ID = 102;
    public static final String PLAYER_PROPERTIES = "player_properties";
    private static int[] c = {R.attr.maxWidth, R.attr.maxHeight};
    private static final String[] d = {".mp4", ".3gp", ".mpg"};
    private static MraidPlayer s;
    private VideoView A;
    private WebChromeClient.CustomViewCallback B;
    private ProgressBar C;
    private Handler D;
    private boolean E;
    WebViewClient a;
    WebChromeClient b;
    private customCloseState e;
    private boolean f;
    private boolean g;
    private Utility h;
    private float i;
    private int j;
    private boolean k;
    private int l;
    private int m;
    private int n;
    private int o;
    private PLACEMENT_TYPE p;
    private VIEW_STATE q;
    private MraidViewListener r;
    private int t;
    private int u;
    private Thread v;
    private boolean w;
    private int x;
    private Context y;
    private RelativeLayout z;

    public enum Action {
        PLAY_AUDIO,
        PLAY_VIDEO
    }

    public enum PLACEMENT_TYPE {
        INLINE,
        INTERSTITIAL
    }

    public enum VIEW_STATE {
        DEFAULT,
        LEFT_BEHIND,
        OPENED
    }

    public enum customCloseState {
        HIDDEN,
        OPEN,
        UNKNOWN
    }

    public MraidView(Context context, MraidViewListener listener) {
        super(context);
        this.e = customCloseState.UNKNOWN;
        this.f = false;
        this.q = VIEW_STATE.DEFAULT;
        this.t = 0;
        this.u = 0;
        this.v = null;
        this.w = false;
        this.D = new Handler() { // from class: com.tapjoy.mraid.view.MraidView.2
            @Override // android.os.Handler
            public final void handleMessage(Message msg) {
                Bundle data = msg.getData();
                switch (msg.what) {
                    case 1001:
                        switch (AnonymousClass6.a[MraidView.this.q.ordinal()]) {
                            case 1:
                                if (MraidView.this.p != PLACEMENT_TYPE.INLINE) {
                                    MraidView.f(MraidView.this);
                                }
                                break;
                        }
                        break;
                    case 1003:
                        MraidView.this.injectMraidJavaScript("window.mraidview.fireChangeEvent({ state: 'default' });");
                        MraidView.this.setVisibility(0);
                        break;
                    case 1006:
                        MraidView.this.q = VIEW_STATE.LEFT_BEHIND;
                        break;
                    case 1007:
                        MraidView.this.playVideoImpl(data);
                        break;
                    case 1008:
                        MraidView.this.playAudioImpl(data);
                        break;
                    case 1009:
                        MraidView.this.injectMraidJavaScript("window.mraidview.fireErrorEvent(\"" + data.getString(TJAdUnitConstants.String.MESSAGE) + "\", \"" + data.getString(MraidView.ACTION_KEY) + "\")");
                        break;
                    case 1010:
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) MraidView.this.getLayoutParams();
                        if (marginLayoutParams != null) {
                            MraidView.this.removeCloseImageButton();
                            marginLayoutParams.height = data.getInt("resize_height", marginLayoutParams.height);
                            marginLayoutParams.width = data.getInt("resize_width", marginLayoutParams.width);
                            String str = "window.mraidview.fireChangeEvent({ state: '" + MraidView.this.getState() + "', size: { width: " + ((int) (marginLayoutParams.width / MraidView.this.i)) + ", height: " + ((int) (marginLayoutParams.height / MraidView.this.i)) + "}});";
                            TapjoyLog.d("MRAIDView", "resize: injection: " + str);
                            MraidView.this.injectMraidJavaScript(str);
                            MraidView.this.requestLayout();
                            MraidView.c(MraidView.this, data.getString("resize_customClosePostition"));
                            if (MraidView.this.p != PLACEMENT_TYPE.INLINE && MraidView.this.e == customCloseState.OPEN) {
                                MraidView.this.showCloseImageButton();
                            }
                        }
                        if (MraidView.this.r != null) {
                            MraidView.this.r.onResize();
                        }
                        break;
                }
                super.handleMessage(msg);
            }
        };
        this.a = new WebViewClient() { // from class: com.tapjoy.mraid.view.MraidView.3
            @Override // android.webkit.WebViewClient
            public final void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (MraidView.this.r != null) {
                    MraidView.this.r.onPageStarted(view, url, favicon);
                }
            }

            @Override // android.webkit.WebViewClient
            public final void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (MraidView.this.r != null) {
                    MraidView.this.r.onReceivedError(view, errorCode, description, failingUrl);
                }
                TapjoyLog.d("MRAIDView", "error:" + description);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override // android.webkit.WebViewClient
            public final void onPageFinished(WebView view, String url) {
                if (MraidView.this.r != null) {
                    MraidView.this.r.onPageFinished(view, url);
                }
                MraidView.this.l = (int) (MraidView.this.getHeight() / MraidView.this.i);
                MraidView.this.m = (int) (MraidView.this.getWidth() / MraidView.this.i);
                MraidView.this.h.init(MraidView.this.i);
                MraidView.this.createCloseImageButton();
                if (MraidView.this.p == PLACEMENT_TYPE.INLINE) {
                    MraidView.this.removeCloseImageButton();
                }
            }

            @Override // android.webkit.WebViewClient
            public final boolean shouldOverrideUrlLoading(WebView view, String url) {
                boolean zShouldOverrideUrlLoading = true;
                TapjoyLog.d("MRAIDView", "shouldOverrideUrlLoading: " + url);
                if (MraidView.this.r != null && MraidView.this.r.shouldOverrideUrlLoading(view, url)) {
                    return true;
                }
                Uri uri = Uri.parse(url);
                try {
                    if (url.startsWith("mraid")) {
                        zShouldOverrideUrlLoading = super.shouldOverrideUrlLoading(view, url);
                    } else if (url.startsWith("tel:")) {
                        Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(url));
                        intent.addFlags(DriveFile.MODE_READ_ONLY);
                        MraidView.this.getContext().startActivity(intent);
                    } else if (url.startsWith("mailto:")) {
                        Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(url));
                        intent2.addFlags(DriveFile.MODE_READ_ONLY);
                        MraidView.this.getContext().startActivity(intent2);
                    } else {
                        Intent intent3 = new Intent();
                        intent3.setAction("android.intent.action.VIEW");
                        intent3.setData(uri);
                        intent3.addFlags(DriveFile.MODE_READ_ONLY);
                        MraidView.this.getContext().startActivity(intent3);
                    }
                    return zShouldOverrideUrlLoading;
                } catch (Exception e) {
                    try {
                        Intent intent4 = new Intent();
                        intent4.setAction("android.intent.action.VIEW");
                        intent4.setData(uri);
                        intent4.addFlags(DriveFile.MODE_READ_ONLY);
                        MraidView.this.getContext().startActivity(intent4);
                        return zShouldOverrideUrlLoading;
                    } catch (Exception e2) {
                        return false;
                    }
                }
            }

            @Override // android.webkit.WebViewClient
            public final WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (TapjoyCache.getInstance() != null) {
                    TapjoyCachedAssetData cachedDataForURL = TapjoyCache.getInstance().getCachedDataForURL(url);
                    if (cachedDataForURL != null) {
                        WebResourceResponse webResourceResponseB = MraidView.b(cachedDataForURL);
                        if (webResourceResponseB != null) {
                            TapjoyLog.d("MRAIDView", "Reading request for " + url + " from cache -- localPath: " + cachedDataForURL.getLocalFilePath());
                            return webResourceResponseB;
                        }
                    } else {
                        TapjoyLog.d("MRAIDView", "No cached data for " + url);
                    }
                }
                return super.shouldInterceptRequest(view, url);
            }

            @Override // android.webkit.WebViewClient
            public final void onLoadResource(WebView view, String url) {
            }
        };
        this.b = new WebChromeClient() { // from class: com.tapjoy.mraid.view.MraidView.4
            @Override // android.webkit.WebChromeClient
            public final boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                TapjoyLog.d("MRAIDView", message);
                return false;
            }

            @Override // android.webkit.WebChromeClient
            public final void onCloseWindow(WebView w) {
                super.onCloseWindow(w);
                MraidView.f(MraidView.this);
            }

            @Override // android.webkit.WebChromeClient
            public final void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
                TapjoyLog.d("MRAIDView", "-- onShowCustomView --");
                super.onShowCustomView(view, callback);
                MraidView.this.B = callback;
                if (view instanceof FrameLayout) {
                    FrameLayout frameLayout = (FrameLayout) view;
                    if ((frameLayout.getFocusedChild() instanceof VideoView) && (MraidView.this.y instanceof Activity)) {
                        Activity activity = (Activity) MraidView.this.y;
                        MraidView.this.A = (VideoView) frameLayout.getFocusedChild();
                        frameLayout.removeView(MraidView.this.A);
                        if (MraidView.this.z == null) {
                            MraidView.this.z = new RelativeLayout(MraidView.this.y);
                            MraidView.this.z.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                            MraidView.this.z.setBackgroundColor(-16777216);
                        }
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
                        layoutParams.addRule(13);
                        MraidView.this.A.setLayoutParams(layoutParams);
                        MraidView.this.C = new ProgressBar(MraidView.this.y, null, R.attr.progressBarStyleLarge);
                        MraidView.this.C.setVisibility(0);
                        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
                        layoutParams2.addRule(13);
                        MraidView.this.C.setLayoutParams(layoutParams2);
                        MraidView.this.z.addView(MraidView.this.A);
                        MraidView.this.z.addView(MraidView.this.C);
                        activity.getWindow().addContentView(MraidView.this.z, new ViewGroup.LayoutParams(-1, -1));
                        new Thread(MraidView.this.new c()).start();
                        MraidView.this.setVisibility(8);
                        MraidView.this.A.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.tapjoy.mraid.view.MraidView.4.1
                            @Override // android.media.MediaPlayer.OnPreparedListener
                            public final void onPrepared(MediaPlayer mp) {
                                TapjoyLog.i("MRAIDView", "** ON PREPARED **");
                                TapjoyLog.i("MRAIDView", "isPlaying: " + mp.isPlaying());
                                if (!mp.isPlaying()) {
                                    mp.start();
                                }
                            }
                        });
                        MraidView.this.A.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.tapjoy.mraid.view.MraidView.4.2
                            @Override // android.media.MediaPlayer.OnCompletionListener
                            public final void onCompletion(MediaPlayer mp) {
                                TapjoyLog.i("MRAIDView", "** ON COMPLETION **");
                                MraidView.this.videoViewCleanup();
                            }
                        });
                        MraidView.this.A.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.tapjoy.mraid.view.MraidView.4.3
                            @Override // android.media.MediaPlayer.OnErrorListener
                            public final boolean onError(MediaPlayer mp, int what, int extra) {
                                TapjoyLog.i("MRAIDView", "** ON ERROR **");
                                MraidView.this.videoViewCleanup();
                                return false;
                            }
                        });
                        MraidView.this.A.start();
                    }
                }
            }

            @Override // android.webkit.WebChromeClient
            public final void onHideCustomView() {
                super.onHideCustomView();
            }

            @Override // android.webkit.WebChromeClient
            public final boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return MraidView.this.r != null ? MraidView.this.r.onConsoleMessage(consoleMessage) : super.onConsoleMessage(consoleMessage);
            }
        };
        setListener(listener);
        this.y = context;
        initialize();
    }

    public void setListener(MraidViewListener listener) {
        this.r = listener;
    }

    public void removeListener() {
        this.r = null;
    }

    public MraidView(Context context) {
        super(context);
        this.e = customCloseState.UNKNOWN;
        this.f = false;
        this.q = VIEW_STATE.DEFAULT;
        this.t = 0;
        this.u = 0;
        this.v = null;
        this.w = false;
        this.D = new Handler() { // from class: com.tapjoy.mraid.view.MraidView.2
            @Override // android.os.Handler
            public final void handleMessage(Message msg) {
                Bundle data = msg.getData();
                switch (msg.what) {
                    case 1001:
                        switch (AnonymousClass6.a[MraidView.this.q.ordinal()]) {
                            case 1:
                                if (MraidView.this.p != PLACEMENT_TYPE.INLINE) {
                                    MraidView.f(MraidView.this);
                                }
                                break;
                        }
                        break;
                    case 1003:
                        MraidView.this.injectMraidJavaScript("window.mraidview.fireChangeEvent({ state: 'default' });");
                        MraidView.this.setVisibility(0);
                        break;
                    case 1006:
                        MraidView.this.q = VIEW_STATE.LEFT_BEHIND;
                        break;
                    case 1007:
                        MraidView.this.playVideoImpl(data);
                        break;
                    case 1008:
                        MraidView.this.playAudioImpl(data);
                        break;
                    case 1009:
                        MraidView.this.injectMraidJavaScript("window.mraidview.fireErrorEvent(\"" + data.getString(TJAdUnitConstants.String.MESSAGE) + "\", \"" + data.getString(MraidView.ACTION_KEY) + "\")");
                        break;
                    case 1010:
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) MraidView.this.getLayoutParams();
                        if (marginLayoutParams != null) {
                            MraidView.this.removeCloseImageButton();
                            marginLayoutParams.height = data.getInt("resize_height", marginLayoutParams.height);
                            marginLayoutParams.width = data.getInt("resize_width", marginLayoutParams.width);
                            String str = "window.mraidview.fireChangeEvent({ state: '" + MraidView.this.getState() + "', size: { width: " + ((int) (marginLayoutParams.width / MraidView.this.i)) + ", height: " + ((int) (marginLayoutParams.height / MraidView.this.i)) + "}});";
                            TapjoyLog.d("MRAIDView", "resize: injection: " + str);
                            MraidView.this.injectMraidJavaScript(str);
                            MraidView.this.requestLayout();
                            MraidView.c(MraidView.this, data.getString("resize_customClosePostition"));
                            if (MraidView.this.p != PLACEMENT_TYPE.INLINE && MraidView.this.e == customCloseState.OPEN) {
                                MraidView.this.showCloseImageButton();
                            }
                        }
                        if (MraidView.this.r != null) {
                            MraidView.this.r.onResize();
                        }
                        break;
                }
                super.handleMessage(msg);
            }
        };
        this.a = new WebViewClient() { // from class: com.tapjoy.mraid.view.MraidView.3
            @Override // android.webkit.WebViewClient
            public final void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (MraidView.this.r != null) {
                    MraidView.this.r.onPageStarted(view, url, favicon);
                }
            }

            @Override // android.webkit.WebViewClient
            public final void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (MraidView.this.r != null) {
                    MraidView.this.r.onReceivedError(view, errorCode, description, failingUrl);
                }
                TapjoyLog.d("MRAIDView", "error:" + description);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override // android.webkit.WebViewClient
            public final void onPageFinished(WebView view, String url) {
                if (MraidView.this.r != null) {
                    MraidView.this.r.onPageFinished(view, url);
                }
                MraidView.this.l = (int) (MraidView.this.getHeight() / MraidView.this.i);
                MraidView.this.m = (int) (MraidView.this.getWidth() / MraidView.this.i);
                MraidView.this.h.init(MraidView.this.i);
                MraidView.this.createCloseImageButton();
                if (MraidView.this.p == PLACEMENT_TYPE.INLINE) {
                    MraidView.this.removeCloseImageButton();
                }
            }

            @Override // android.webkit.WebViewClient
            public final boolean shouldOverrideUrlLoading(WebView view, String url) {
                boolean zShouldOverrideUrlLoading = true;
                TapjoyLog.d("MRAIDView", "shouldOverrideUrlLoading: " + url);
                if (MraidView.this.r != null && MraidView.this.r.shouldOverrideUrlLoading(view, url)) {
                    return true;
                }
                Uri uri = Uri.parse(url);
                try {
                    if (url.startsWith("mraid")) {
                        zShouldOverrideUrlLoading = super.shouldOverrideUrlLoading(view, url);
                    } else if (url.startsWith("tel:")) {
                        Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(url));
                        intent.addFlags(DriveFile.MODE_READ_ONLY);
                        MraidView.this.getContext().startActivity(intent);
                    } else if (url.startsWith("mailto:")) {
                        Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(url));
                        intent2.addFlags(DriveFile.MODE_READ_ONLY);
                        MraidView.this.getContext().startActivity(intent2);
                    } else {
                        Intent intent3 = new Intent();
                        intent3.setAction("android.intent.action.VIEW");
                        intent3.setData(uri);
                        intent3.addFlags(DriveFile.MODE_READ_ONLY);
                        MraidView.this.getContext().startActivity(intent3);
                    }
                    return zShouldOverrideUrlLoading;
                } catch (Exception e) {
                    try {
                        Intent intent4 = new Intent();
                        intent4.setAction("android.intent.action.VIEW");
                        intent4.setData(uri);
                        intent4.addFlags(DriveFile.MODE_READ_ONLY);
                        MraidView.this.getContext().startActivity(intent4);
                        return zShouldOverrideUrlLoading;
                    } catch (Exception e2) {
                        return false;
                    }
                }
            }

            @Override // android.webkit.WebViewClient
            public final WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (TapjoyCache.getInstance() != null) {
                    TapjoyCachedAssetData cachedDataForURL = TapjoyCache.getInstance().getCachedDataForURL(url);
                    if (cachedDataForURL != null) {
                        WebResourceResponse webResourceResponseB = MraidView.b(cachedDataForURL);
                        if (webResourceResponseB != null) {
                            TapjoyLog.d("MRAIDView", "Reading request for " + url + " from cache -- localPath: " + cachedDataForURL.getLocalFilePath());
                            return webResourceResponseB;
                        }
                    } else {
                        TapjoyLog.d("MRAIDView", "No cached data for " + url);
                    }
                }
                return super.shouldInterceptRequest(view, url);
            }

            @Override // android.webkit.WebViewClient
            public final void onLoadResource(WebView view, String url) {
            }
        };
        this.b = new WebChromeClient() { // from class: com.tapjoy.mraid.view.MraidView.4
            @Override // android.webkit.WebChromeClient
            public final boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                TapjoyLog.d("MRAIDView", message);
                return false;
            }

            @Override // android.webkit.WebChromeClient
            public final void onCloseWindow(WebView w) {
                super.onCloseWindow(w);
                MraidView.f(MraidView.this);
            }

            @Override // android.webkit.WebChromeClient
            public final void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
                TapjoyLog.d("MRAIDView", "-- onShowCustomView --");
                super.onShowCustomView(view, callback);
                MraidView.this.B = callback;
                if (view instanceof FrameLayout) {
                    FrameLayout frameLayout = (FrameLayout) view;
                    if ((frameLayout.getFocusedChild() instanceof VideoView) && (MraidView.this.y instanceof Activity)) {
                        Activity activity = (Activity) MraidView.this.y;
                        MraidView.this.A = (VideoView) frameLayout.getFocusedChild();
                        frameLayout.removeView(MraidView.this.A);
                        if (MraidView.this.z == null) {
                            MraidView.this.z = new RelativeLayout(MraidView.this.y);
                            MraidView.this.z.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                            MraidView.this.z.setBackgroundColor(-16777216);
                        }
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
                        layoutParams.addRule(13);
                        MraidView.this.A.setLayoutParams(layoutParams);
                        MraidView.this.C = new ProgressBar(MraidView.this.y, null, R.attr.progressBarStyleLarge);
                        MraidView.this.C.setVisibility(0);
                        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
                        layoutParams2.addRule(13);
                        MraidView.this.C.setLayoutParams(layoutParams2);
                        MraidView.this.z.addView(MraidView.this.A);
                        MraidView.this.z.addView(MraidView.this.C);
                        activity.getWindow().addContentView(MraidView.this.z, new ViewGroup.LayoutParams(-1, -1));
                        new Thread(MraidView.this.new c()).start();
                        MraidView.this.setVisibility(8);
                        MraidView.this.A.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.tapjoy.mraid.view.MraidView.4.1
                            @Override // android.media.MediaPlayer.OnPreparedListener
                            public final void onPrepared(MediaPlayer mp) {
                                TapjoyLog.i("MRAIDView", "** ON PREPARED **");
                                TapjoyLog.i("MRAIDView", "isPlaying: " + mp.isPlaying());
                                if (!mp.isPlaying()) {
                                    mp.start();
                                }
                            }
                        });
                        MraidView.this.A.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.tapjoy.mraid.view.MraidView.4.2
                            @Override // android.media.MediaPlayer.OnCompletionListener
                            public final void onCompletion(MediaPlayer mp) {
                                TapjoyLog.i("MRAIDView", "** ON COMPLETION **");
                                MraidView.this.videoViewCleanup();
                            }
                        });
                        MraidView.this.A.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.tapjoy.mraid.view.MraidView.4.3
                            @Override // android.media.MediaPlayer.OnErrorListener
                            public final boolean onError(MediaPlayer mp, int what, int extra) {
                                TapjoyLog.i("MRAIDView", "** ON ERROR **");
                                MraidView.this.videoViewCleanup();
                                return false;
                            }
                        });
                        MraidView.this.A.start();
                    }
                }
            }

            @Override // android.webkit.WebChromeClient
            public final void onHideCustomView() {
                super.onHideCustomView();
            }

            @Override // android.webkit.WebChromeClient
            public final boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return MraidView.this.r != null ? MraidView.this.r.onConsoleMessage(consoleMessage) : super.onConsoleMessage(consoleMessage);
            }
        };
        this.y = context;
        initialize();
    }

    public void setPlacementType(PLACEMENT_TYPE type) {
        if (type.equals(PLACEMENT_TYPE.INLINE) || type.equals(PLACEMENT_TYPE.INTERSTITIAL)) {
            this.p = type;
        } else {
            TapjoyLog.d("MRAIDView", "Incorrect placement type.");
        }
        if (type.equals(PLACEMENT_TYPE.INLINE)) {
            if (this.v == null || !this.v.isAlive()) {
                this.v = new Thread(new b());
                this.v.start();
            }
        }
    }

    public PLACEMENT_TYPE getPlacementType() {
        return this.p;
    }

    public void createCloseImageButton() {
        injectMraidJavaScript("window.mraidview.createCss();");
        TapjoyLog.d("MRAIDView", "Creating close button.");
    }

    public void removeCloseImageButton() {
        injectMraidJavaScript("document.getElementById(\"closeButton\").style.visibility=\"hidden\";");
        TapjoyLog.d("MRAIDView", "Removing close button.");
        this.e = customCloseState.HIDDEN;
    }

    public void showCloseImageButton() {
        injectMraidJavaScript("document.getElementById(\"closeButton\").style.visibility=\"visible\";");
        TapjoyLog.d("MRAIDView", "Showing close button.");
        this.e = customCloseState.OPEN;
    }

    public customCloseState getCloseButtonState() {
        return this.e;
    }

    public boolean isMraid() {
        return this.f;
    }

    public void setMaxSize(int w, int h) {
        this.h.setMaxSize(w, h);
    }

    public void injectMraidJavaScript(String str) {
        if (str != null && this.f) {
            loadUrl("javascript:" + str);
        }
    }

    @Override // android.webkit.WebView
    public void loadUrl(final String url) {
        ((Activity) this.y).runOnUiThread(new Runnable() { // from class: com.tapjoy.mraid.view.MraidView.1
            @Override // java.lang.Runnable
            public final void run() {
                byte b2 = 0;
                if (URLUtil.isValidUrl(url)) {
                    if (url.startsWith("javascript:")) {
                        if (Build.VERSION.SDK_INT >= 19) {
                            try {
                                MraidView.super.evaluateJavascript(url.replaceFirst("javascript:", ""), null);
                                return;
                            } catch (Exception e) {
                                TapjoyLog.e("MRAIDView", "Exception in evaluateJavascript. Device not supported. " + e.toString());
                                return;
                            }
                        }
                        MraidView.super.loadUrl(url);
                        return;
                    }
                    new a(MraidView.this, b2).execute(url);
                    return;
                }
                MraidView.this.loadDataWithBaseURL(null, "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\"><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\"><title>Connection not Established</title></head><h2>Connection Not Properly Established</h2><body></body></html>", "text/html", "utf-8", null);
            }
        });
    }

    public void loadUrlStandard(String url) {
        super.loadUrl(url);
    }

    public boolean hasMraidTag(String html) {
        return Pattern.compile("<\\s*script[^>]+mraid\\.js").matcher(html).find();
    }

    @Override // android.webkit.WebView
    public void loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl) {
        if (data != null) {
            StringBuffer stringBuffer = new StringBuffer();
            int iIndexOf = data.indexOf("<html>");
            this.f = false;
            int iIndexOf2 = data.indexOf("mraid.js");
            if (iIndexOf2 > 0 && hasMraidTag(data)) {
                this.f = true;
                int i = iIndexOf2;
                while (true) {
                    if (i < 0) {
                        i = iIndexOf2;
                        break;
                    } else if (data.substring(i, i + 7).equals("<script")) {
                        break;
                    } else {
                        i--;
                    }
                }
                for (int i2 = 0; i2 < data.length(); i2++) {
                    if (data.substring(iIndexOf2 + i2, iIndexOf2 + i2 + 2).equalsIgnoreCase("/>")) {
                        iIndexOf2 = iIndexOf2 + i2 + 2;
                        break;
                    } else {
                        if (data.substring(iIndexOf2 + i2, iIndexOf2 + i2 + 9).equalsIgnoreCase("</script>")) {
                            iIndexOf2 = iIndexOf2 + i2 + 9;
                            break;
                        }
                    }
                }
                if (iIndexOf < 0) {
                    TapjoyLog.d("MRAIDView", "wrapping fragment");
                    stringBuffer.append("<html>");
                    stringBuffer.append("<head>");
                    stringBuffer.append("<meta name='viewport' content='user-scalable=no initial-scale=1.0' />");
                    stringBuffer.append("<title>Advertisement</title>");
                    stringBuffer.append("</head>");
                    stringBuffer.append("<body style=\"margin:0; padding:0; overflow:hidden; background-color:transparent;\">");
                    stringBuffer.append("<div align=\"center\"> ");
                    stringBuffer.append(data.substring(0, i));
                    stringBuffer.append("<script type=text/javascript>");
                    String strCopyTextFromJarIntoString = (String) TapjoyUtil.getResource("mraid.js");
                    if (strCopyTextFromJarIntoString == null) {
                        strCopyTextFromJarIntoString = TapjoyUtil.copyTextFromJarIntoString("js/mraid.js", getContext());
                    }
                    stringBuffer.append(strCopyTextFromJarIntoString);
                    stringBuffer.append("</script>");
                    stringBuffer.append(data.substring(iIndexOf2));
                } else {
                    int iIndexOf3 = data.indexOf("<head>");
                    if (iIndexOf3 != -1) {
                        String strCopyTextFromJarIntoString2 = (String) TapjoyUtil.getResource("mraid.js");
                        if (strCopyTextFromJarIntoString2 == null) {
                            strCopyTextFromJarIntoString2 = TapjoyUtil.copyTextFromJarIntoString("js/mraid.js", getContext());
                        }
                        stringBuffer.append(data.substring(0, iIndexOf3 + 6));
                        stringBuffer.append("<script type='text/javascript'>");
                        stringBuffer.append(strCopyTextFromJarIntoString2);
                        stringBuffer.append("</script>");
                        stringBuffer.append(data.substring(iIndexOf3 + 6));
                    }
                }
                TapjoyLog.d("MRAIDView", "injected js/mraid.js");
            } else {
                stringBuffer.append(data);
            }
            super.loadDataWithBaseURL(baseUrl, stringBuffer.toString(), mimeType, encoding, historyUrl);
        }
    }

    @Override // android.webkit.WebView
    public void clearView() {
        reset();
        super.clearView();
    }

    public void reset() {
        invalidate();
        this.h.deleteOldAds();
        this.h.stopAllListeners();
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (this.E) {
            layoutParams.height = this.n;
            layoutParams.width = this.o;
        }
        setVisibility(0);
        requestLayout();
    }

    public MraidView(Context context, AttributeSet set) {
        super(context, set);
        this.e = customCloseState.UNKNOWN;
        this.f = false;
        this.q = VIEW_STATE.DEFAULT;
        this.t = 0;
        this.u = 0;
        this.v = null;
        this.w = false;
        this.D = new Handler() { // from class: com.tapjoy.mraid.view.MraidView.2
            @Override // android.os.Handler
            public final void handleMessage(Message msg) {
                Bundle data = msg.getData();
                switch (msg.what) {
                    case 1001:
                        switch (AnonymousClass6.a[MraidView.this.q.ordinal()]) {
                            case 1:
                                if (MraidView.this.p != PLACEMENT_TYPE.INLINE) {
                                    MraidView.f(MraidView.this);
                                }
                                break;
                        }
                        break;
                    case 1003:
                        MraidView.this.injectMraidJavaScript("window.mraidview.fireChangeEvent({ state: 'default' });");
                        MraidView.this.setVisibility(0);
                        break;
                    case 1006:
                        MraidView.this.q = VIEW_STATE.LEFT_BEHIND;
                        break;
                    case 1007:
                        MraidView.this.playVideoImpl(data);
                        break;
                    case 1008:
                        MraidView.this.playAudioImpl(data);
                        break;
                    case 1009:
                        MraidView.this.injectMraidJavaScript("window.mraidview.fireErrorEvent(\"" + data.getString(TJAdUnitConstants.String.MESSAGE) + "\", \"" + data.getString(MraidView.ACTION_KEY) + "\")");
                        break;
                    case 1010:
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) MraidView.this.getLayoutParams();
                        if (marginLayoutParams != null) {
                            MraidView.this.removeCloseImageButton();
                            marginLayoutParams.height = data.getInt("resize_height", marginLayoutParams.height);
                            marginLayoutParams.width = data.getInt("resize_width", marginLayoutParams.width);
                            String str = "window.mraidview.fireChangeEvent({ state: '" + MraidView.this.getState() + "', size: { width: " + ((int) (marginLayoutParams.width / MraidView.this.i)) + ", height: " + ((int) (marginLayoutParams.height / MraidView.this.i)) + "}});";
                            TapjoyLog.d("MRAIDView", "resize: injection: " + str);
                            MraidView.this.injectMraidJavaScript(str);
                            MraidView.this.requestLayout();
                            MraidView.c(MraidView.this, data.getString("resize_customClosePostition"));
                            if (MraidView.this.p != PLACEMENT_TYPE.INLINE && MraidView.this.e == customCloseState.OPEN) {
                                MraidView.this.showCloseImageButton();
                            }
                        }
                        if (MraidView.this.r != null) {
                            MraidView.this.r.onResize();
                        }
                        break;
                }
                super.handleMessage(msg);
            }
        };
        this.a = new WebViewClient() { // from class: com.tapjoy.mraid.view.MraidView.3
            @Override // android.webkit.WebViewClient
            public final void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (MraidView.this.r != null) {
                    MraidView.this.r.onPageStarted(view, url, favicon);
                }
            }

            @Override // android.webkit.WebViewClient
            public final void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (MraidView.this.r != null) {
                    MraidView.this.r.onReceivedError(view, errorCode, description, failingUrl);
                }
                TapjoyLog.d("MRAIDView", "error:" + description);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override // android.webkit.WebViewClient
            public final void onPageFinished(WebView view, String url) {
                if (MraidView.this.r != null) {
                    MraidView.this.r.onPageFinished(view, url);
                }
                MraidView.this.l = (int) (MraidView.this.getHeight() / MraidView.this.i);
                MraidView.this.m = (int) (MraidView.this.getWidth() / MraidView.this.i);
                MraidView.this.h.init(MraidView.this.i);
                MraidView.this.createCloseImageButton();
                if (MraidView.this.p == PLACEMENT_TYPE.INLINE) {
                    MraidView.this.removeCloseImageButton();
                }
            }

            @Override // android.webkit.WebViewClient
            public final boolean shouldOverrideUrlLoading(WebView view, String url) {
                boolean zShouldOverrideUrlLoading = true;
                TapjoyLog.d("MRAIDView", "shouldOverrideUrlLoading: " + url);
                if (MraidView.this.r != null && MraidView.this.r.shouldOverrideUrlLoading(view, url)) {
                    return true;
                }
                Uri uri = Uri.parse(url);
                try {
                    if (url.startsWith("mraid")) {
                        zShouldOverrideUrlLoading = super.shouldOverrideUrlLoading(view, url);
                    } else if (url.startsWith("tel:")) {
                        Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(url));
                        intent.addFlags(DriveFile.MODE_READ_ONLY);
                        MraidView.this.getContext().startActivity(intent);
                    } else if (url.startsWith("mailto:")) {
                        Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(url));
                        intent2.addFlags(DriveFile.MODE_READ_ONLY);
                        MraidView.this.getContext().startActivity(intent2);
                    } else {
                        Intent intent3 = new Intent();
                        intent3.setAction("android.intent.action.VIEW");
                        intent3.setData(uri);
                        intent3.addFlags(DriveFile.MODE_READ_ONLY);
                        MraidView.this.getContext().startActivity(intent3);
                    }
                    return zShouldOverrideUrlLoading;
                } catch (Exception e) {
                    try {
                        Intent intent4 = new Intent();
                        intent4.setAction("android.intent.action.VIEW");
                        intent4.setData(uri);
                        intent4.addFlags(DriveFile.MODE_READ_ONLY);
                        MraidView.this.getContext().startActivity(intent4);
                        return zShouldOverrideUrlLoading;
                    } catch (Exception e2) {
                        return false;
                    }
                }
            }

            @Override // android.webkit.WebViewClient
            public final WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (TapjoyCache.getInstance() != null) {
                    TapjoyCachedAssetData cachedDataForURL = TapjoyCache.getInstance().getCachedDataForURL(url);
                    if (cachedDataForURL != null) {
                        WebResourceResponse webResourceResponseB = MraidView.b(cachedDataForURL);
                        if (webResourceResponseB != null) {
                            TapjoyLog.d("MRAIDView", "Reading request for " + url + " from cache -- localPath: " + cachedDataForURL.getLocalFilePath());
                            return webResourceResponseB;
                        }
                    } else {
                        TapjoyLog.d("MRAIDView", "No cached data for " + url);
                    }
                }
                return super.shouldInterceptRequest(view, url);
            }

            @Override // android.webkit.WebViewClient
            public final void onLoadResource(WebView view, String url) {
            }
        };
        this.b = new WebChromeClient() { // from class: com.tapjoy.mraid.view.MraidView.4
            @Override // android.webkit.WebChromeClient
            public final boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                TapjoyLog.d("MRAIDView", message);
                return false;
            }

            @Override // android.webkit.WebChromeClient
            public final void onCloseWindow(WebView w) {
                super.onCloseWindow(w);
                MraidView.f(MraidView.this);
            }

            @Override // android.webkit.WebChromeClient
            public final void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
                TapjoyLog.d("MRAIDView", "-- onShowCustomView --");
                super.onShowCustomView(view, callback);
                MraidView.this.B = callback;
                if (view instanceof FrameLayout) {
                    FrameLayout frameLayout = (FrameLayout) view;
                    if ((frameLayout.getFocusedChild() instanceof VideoView) && (MraidView.this.y instanceof Activity)) {
                        Activity activity = (Activity) MraidView.this.y;
                        MraidView.this.A = (VideoView) frameLayout.getFocusedChild();
                        frameLayout.removeView(MraidView.this.A);
                        if (MraidView.this.z == null) {
                            MraidView.this.z = new RelativeLayout(MraidView.this.y);
                            MraidView.this.z.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                            MraidView.this.z.setBackgroundColor(-16777216);
                        }
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
                        layoutParams.addRule(13);
                        MraidView.this.A.setLayoutParams(layoutParams);
                        MraidView.this.C = new ProgressBar(MraidView.this.y, null, R.attr.progressBarStyleLarge);
                        MraidView.this.C.setVisibility(0);
                        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
                        layoutParams2.addRule(13);
                        MraidView.this.C.setLayoutParams(layoutParams2);
                        MraidView.this.z.addView(MraidView.this.A);
                        MraidView.this.z.addView(MraidView.this.C);
                        activity.getWindow().addContentView(MraidView.this.z, new ViewGroup.LayoutParams(-1, -1));
                        new Thread(MraidView.this.new c()).start();
                        MraidView.this.setVisibility(8);
                        MraidView.this.A.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.tapjoy.mraid.view.MraidView.4.1
                            @Override // android.media.MediaPlayer.OnPreparedListener
                            public final void onPrepared(MediaPlayer mp) {
                                TapjoyLog.i("MRAIDView", "** ON PREPARED **");
                                TapjoyLog.i("MRAIDView", "isPlaying: " + mp.isPlaying());
                                if (!mp.isPlaying()) {
                                    mp.start();
                                }
                            }
                        });
                        MraidView.this.A.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.tapjoy.mraid.view.MraidView.4.2
                            @Override // android.media.MediaPlayer.OnCompletionListener
                            public final void onCompletion(MediaPlayer mp) {
                                TapjoyLog.i("MRAIDView", "** ON COMPLETION **");
                                MraidView.this.videoViewCleanup();
                            }
                        });
                        MraidView.this.A.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.tapjoy.mraid.view.MraidView.4.3
                            @Override // android.media.MediaPlayer.OnErrorListener
                            public final boolean onError(MediaPlayer mp, int what, int extra) {
                                TapjoyLog.i("MRAIDView", "** ON ERROR **");
                                MraidView.this.videoViewCleanup();
                                return false;
                            }
                        });
                        MraidView.this.A.start();
                    }
                }
            }

            @Override // android.webkit.WebChromeClient
            public final void onHideCustomView() {
                super.onHideCustomView();
            }

            @Override // android.webkit.WebChromeClient
            public final boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return MraidView.this.r != null ? MraidView.this.r.onConsoleMessage(consoleMessage) : super.onConsoleMessage(consoleMessage);
            }
        };
        initialize();
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(set, c);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, -1);
        int dimensionPixelSize2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(1, -1);
        if (dimensionPixelSize > 0 && dimensionPixelSize2 > 0) {
            this.h.setMaxSize(dimensionPixelSize, dimensionPixelSize2);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX INFO: renamed from: com.tapjoy.mraid.view.MraidView$6, reason: invalid class name */
    static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] a = new int[VIEW_STATE.values().length];

        static {
            try {
                a[VIEW_STATE.DEFAULT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static WebResourceResponse b(TapjoyCachedAssetData tapjoyCachedAssetData) {
        if (tapjoyCachedAssetData == null) {
            return null;
        }
        try {
            return new WebResourceResponse(tapjoyCachedAssetData.getMimeType(), AsyncHttpResponseHandler.DEFAULT_CHARSET, new FileInputStream(tapjoyCachedAssetData.getLocalFilePath()));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public boolean videoPlaying() {
        return this.A != null;
    }

    public void videoViewCleanup() {
        if (this.z != null) {
            ((ViewGroup) this.z.getParent()).removeView(this.z);
            this.z.setVisibility(8);
            this.z = null;
        }
        try {
            if (this.A != null) {
                this.A.stopPlayback();
            }
            if (this.B != null) {
                this.B.onCustomViewHidden();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.A = null;
        this.B = null;
        if (this != null) {
            setVisibility(0);
        }
        loadUrl("javascript:try{Tapjoy.AdUnit.dispatchEvent('videoend')}catch(e){}");
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public void initialize() {
        setPlacementType(PLACEMENT_TYPE.INTERSTITIAL);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        this.i = displayMetrics.density;
        this.g = false;
        this.h = new Utility(this, getContext());
        addJavascriptInterface(this.h, "MRAIDUtilityControllerBridge");
        setWebViewClient(this.a);
        setWebChromeClient(this.b);
        this.j = getContentViewHeight();
        if (getViewTreeObserver() != null) {
            getViewTreeObserver().addOnGlobalLayoutListener(this);
        }
        WindowManager windowManager = (WindowManager) getContext().getSystemService("window");
        this.t = windowManager.getDefaultDisplay().getWidth();
        this.u = windowManager.getDefaultDisplay().getHeight();
        if (getContext() instanceof Activity) {
            this.x = ((Activity) getContext()).getRequestedOrientation();
        }
    }

    class b implements Runnable {
        public b() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            while (!MraidView.this.w) {
                try {
                    Thread.sleep(250L);
                    MraidView.m(MraidView.this);
                } catch (Exception e) {
                }
            }
        }
    }

    class c implements Runnable {
        public c() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            int i = 0;
            while (MraidView.this.A != null && !MraidView.this.A.isPlaying()) {
                try {
                    Thread.sleep(50L);
                    i += 50;
                    if (i >= 10000) {
                        break;
                    }
                } catch (Exception e) {
                }
            }
            ((Activity) MraidView.this.y).runOnUiThread(new Runnable() { // from class: com.tapjoy.mraid.view.MraidView.c.1
                @Override // java.lang.Runnable
                public final void run() {
                    if (MraidView.this.C != null) {
                        MraidView.this.C.setVisibility(8);
                    }
                    new Thread(c.this.new a()).start();
                }
            });
        }

        class a implements Runnable {
            private boolean b = false;

            public a() {
            }

            @Override // java.lang.Runnable
            public final void run() {
                while (MraidView.this.A != null) {
                    try {
                        Thread.sleep(100L);
                        if (this.b != MraidView.this.A.isPlaying()) {
                            this.b = MraidView.this.A.isPlaying();
                            MraidView.this.loadUrl("javascript:try{Tapjoy.AdUnit.dispatchEvent('" + (this.b ? "videoplay" : "videopause") + "')}catch(e){}");
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    public void addJavascriptObject(Object obj, String name) {
        addJavascriptInterface(obj, name);
    }

    private int getContentViewHeight() {
        View viewFindViewById = getRootView().findViewById(R.id.content);
        if (viewFindViewById != null) {
            return viewFindViewById.getHeight();
        }
        return -1;
    }

    public VIEW_STATE getViewState() {
        return this.q;
    }

    public String getState() {
        return this.q.toString().toLowerCase();
    }

    public void resizeOrientation(int width, int height, String customClosePosition, boolean allowOffScreen) {
        this.t = width;
        this.u = height;
        TapjoyLog.d("MRAIDView", "resizeOrientation to dimensions: " + width + TMXConstants.TAG_OBJECT_ATTRIBUTE_X + height);
        Message messageObtainMessage = this.D.obtainMessage(1010);
        Bundle bundle = new Bundle();
        bundle.putInt("resize_width", width);
        bundle.putInt("resize_height", height);
        bundle.putBoolean("resize_allowOffScreen", allowOffScreen);
        bundle.putString("resize_customClosePostition", customClosePosition);
        messageObtainMessage.setData(bundle);
        this.D.sendMessage(messageObtainMessage);
    }

    public void close() {
        this.D.sendEmptyMessage(1001);
    }

    public void show() {
        this.D.sendEmptyMessage(1003);
    }

    public ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) getContext().getSystemService("connectivity");
    }

    public void open(String url, boolean back, boolean forward, boolean refresh) {
        boolean z;
        String str = null;
        if (a(url)) {
            str = url;
            z = true;
        } else {
            TapjoyHttpURLResponse redirectFromURL = new TapjoyURLConnection().getRedirectFromURL(url);
            TapjoyLog.i("MRAIDView", "redirect: " + redirectFromURL.redirectURL + ", " + redirectFromURL.statusCode);
            if (redirectFromURL == null || redirectFromURL.redirectURL == null || redirectFromURL.redirectURL.length() <= 0 || !a(redirectFromURL.redirectURL)) {
                z = false;
            } else {
                str = redirectFromURL.redirectURL;
                z = true;
            }
        }
        if (!z) {
            TapjoyLog.d("MRAIDView", "Mraid Browser open:" + url);
            Intent intent = new Intent(getContext(), (Class<?>) Browser.class);
            intent.putExtra(Browser.URL_EXTRA, url);
            intent.putExtra(Browser.SHOW_BACK_EXTRA, back);
            intent.putExtra(Browser.SHOW_FORWARD_EXTRA, forward);
            intent.putExtra(Browser.SHOW_REFRESH_EXTRA, refresh);
            intent.addFlags(DriveFile.MODE_READ_ONLY);
            getContext().startActivity(intent);
            return;
        }
        Abstract.Dimensions dimensions = new Abstract.Dimensions();
        dimensions.x = 0;
        dimensions.y = 0;
        dimensions.width = getWidth();
        dimensions.height = getHeight();
        playVideo(str, false, true, true, false, dimensions, Abstract.FULL_SCREEN, Abstract.EXIT);
    }

    private static boolean a(String str) {
        for (String str2 : d) {
            if (str.endsWith(str2)) {
                return true;
            }
        }
        return false;
    }

    public void setOrientationProperties(boolean allowOrientationChange, String forceOrientation) {
        int i;
        if (allowOrientationChange) {
            i = -1;
        } else {
            i = forceOrientation.equals(TJAdUnitConstants.String.LANDSCAPE) ? 0 : 1;
        }
        ((Activity) getContext()).setRequestedOrientation(i);
    }

    public void openMap(String POI, boolean fullscreen) {
        TapjoyLog.d("MRAIDView", "Opening Map Url " + POI);
        String POI2 = Utils.convert(POI.trim());
        if (fullscreen) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(POI2));
                intent.setFlags(DriveFile.MODE_READ_ONLY);
                getContext().startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void playAudioImpl(Bundle data) {
        Abstract.PlayerProperties playerProperties = (Abstract.PlayerProperties) data.getParcelable(PLAYER_PROPERTIES);
        String string = data.getString(EXPAND_URL);
        MraidPlayer player = getPlayer();
        player.setPlayData(playerProperties, string);
        player.setLayoutParams(new ViewGroup.LayoutParams(1, 1));
        ((ViewGroup) getParent()).addView(player);
        player.playAudio();
    }

    public void playAudio(String url, boolean autoPlay, boolean controls, boolean loop, boolean position, String startStyle, String stopStyle) {
        Abstract.PlayerProperties playerProperties = new Abstract.PlayerProperties();
        playerProperties.setProperties(false, autoPlay, controls, position, loop, startStyle, stopStyle);
        Bundle bundle = new Bundle();
        bundle.putString(ACTION_KEY, Action.PLAY_AUDIO.toString());
        bundle.putString(EXPAND_URL, url);
        bundle.putParcelable(PLAYER_PROPERTIES, playerProperties);
        if (playerProperties.isFullScreen()) {
            try {
                Intent intent = new Intent(getContext(), (Class<?>) ActionHandler.class);
                intent.putExtras(bundle);
                getContext().startActivity(intent);
                return;
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                return;
            }
        }
        Message messageObtainMessage = this.D.obtainMessage(1008);
        messageObtainMessage.setData(bundle);
        this.D.sendMessage(messageObtainMessage);
    }

    public void playVideoImpl(Bundle data) {
        Abstract.PlayerProperties playerProperties = (Abstract.PlayerProperties) data.getParcelable(PLAYER_PROPERTIES);
        Abstract.Dimensions dimensions = (Abstract.Dimensions) data.getParcelable(DIMENSIONS);
        String string = data.getString(EXPAND_URL);
        MraidPlayer player = getPlayer();
        player.setPlayData(playerProperties, string);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(dimensions.width, dimensions.height);
        layoutParams.topMargin = dimensions.x;
        layoutParams.leftMargin = dimensions.y;
        player.setLayoutParams(layoutParams);
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setId(101);
        frameLayout.setPadding(dimensions.x, dimensions.y, 0, 0);
        ((FrameLayout) getRootView().findViewById(R.id.content)).addView(frameLayout, new FrameLayout.LayoutParams(-1, -1));
        frameLayout.addView(player);
        setVisibility(4);
        player.setListener(new Player() { // from class: com.tapjoy.mraid.view.MraidView.5
            @Override // com.tapjoy.mraid.listener.Player
            public final void onPrepared() {
            }

            @Override // com.tapjoy.mraid.listener.Player
            public final void onError() {
                onComplete();
            }

            @Override // com.tapjoy.mraid.listener.Player
            public final void onComplete() {
                FrameLayout frameLayout2 = (FrameLayout) MraidView.this.getRootView().findViewById(101);
                ((ViewGroup) frameLayout2.getParent()).removeView(frameLayout2);
                MraidView.this.setVisibility(0);
            }
        });
        player.playVideo();
    }

    public void playVideo(String url, boolean audioMuted, boolean autoPlay, boolean controls, boolean loop, Abstract.Dimensions d2, String startStyle, String stopStyle) {
        Message messageObtainMessage = this.D.obtainMessage(1007);
        Abstract.PlayerProperties playerProperties = new Abstract.PlayerProperties();
        playerProperties.setProperties(audioMuted, autoPlay, controls, false, loop, startStyle, stopStyle);
        Bundle bundle = new Bundle();
        bundle.putString(EXPAND_URL, url);
        bundle.putString(ACTION_KEY, Action.PLAY_VIDEO.toString());
        bundle.putParcelable(PLAYER_PROPERTIES, playerProperties);
        if (d2 != null) {
            bundle.putParcelable(DIMENSIONS, d2);
        }
        if (playerProperties.isFullScreen()) {
            try {
                Intent intent = new Intent(getContext(), (Class<?>) ActionHandler.class);
                intent.putExtras(bundle);
                intent.setFlags(DriveFile.MODE_READ_ONLY);
                getContext().startActivity(intent);
                return;
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                return;
            }
        }
        if (d2 != null) {
            messageObtainMessage.setData(bundle);
            this.D.sendMessage(messageObtainMessage);
        }
    }

    public boolean isPageFinished() {
        return this.g;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        boolean z = this.k;
        if (!this.k && this.j >= 0 && getContentViewHeight() >= 0 && this.j != getContentViewHeight()) {
            z = true;
            injectMraidJavaScript("window.mraidview.fireChangeEvent({ keyboardState: true});");
        }
        if (this.k && this.j >= 0 && getContentViewHeight() >= 0 && this.j == getContentViewHeight()) {
            z = false;
            injectMraidJavaScript("window.mraidview.fireChangeEvent({ keyboardState: false});");
        }
        if (this.j < 0) {
            this.j = getContentViewHeight();
        }
        this.k = z;
    }

    public String getSize() {
        return "{ width: " + ((int) Math.ceil(getWidth() / this.i)) + ", height: " + ((int) Math.ceil(getHeight() / this.i)) + "}";
    }

    @Override // android.webkit.WebView, android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        if (!this.E) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            this.n = layoutParams.height;
            this.o = layoutParams.width;
            this.E = true;
        }
        this.w = false;
        if (this.v == null || !this.v.isAlive()) {
            this.v = new Thread(new b());
            this.v.start();
        }
        super.onAttachedToWindow();
    }

    @Override // android.webkit.WebView
    public WebBackForwardList saveState(Bundle outState) {
        return super.saveState(outState);
    }

    @Override // android.webkit.WebView
    public WebBackForwardList restoreState(Bundle savedInstanceState) {
        return super.restoreState(savedInstanceState);
    }

    public void raiseError(String strMsg, String action) {
        Message messageObtainMessage = this.D.obtainMessage(1009);
        Bundle bundle = new Bundle();
        bundle.putString(TJAdUnitConstants.String.MESSAGE, strMsg);
        bundle.putString(ACTION_KEY, action);
        messageObtainMessage.setData(bundle);
        this.D.sendMessage(messageObtainMessage);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        this.w = true;
        this.h.stopAllListeners();
        try {
            if (this.A != null) {
                this.A.stopPlayback();
            }
            if (this.B != null) {
                this.B.onCustomViewHidden();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDetachedFromWindow();
    }

    MraidPlayer getPlayer() {
        if (s != null) {
            s.releasePlayer();
        }
        MraidPlayer mraidPlayer = new MraidPlayer(getContext());
        s = mraidPlayer;
        return mraidPlayer;
    }

    class a extends AsyncTask {
        TapjoyHttpURLResponse a;
        TapjoyURLConnection b;
        String c;

        private a() {
        }

        /* synthetic */ a(MraidView mraidView, byte b) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public Void doInBackground(String... strArr) {
            this.c = strArr[0];
            try {
                this.b = new TapjoyURLConnection();
                this.a = this.b.getResponseFromURL(this.c);
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override // android.os.AsyncTask
        protected final /* synthetic */ void onPostExecute(Object obj) {
            try {
                if (this.a.statusCode == 0 || this.a.response == null) {
                    TapjoyLog.e("MRAIDView", new TapjoyErrorMessage(TapjoyErrorMessage.ErrorType.NETWORK_ERROR, "Connection not properly established"));
                    if (MraidView.this.r != null) {
                        MraidView.this.r.onReceivedError(MraidView.this, 0, "Connection not properly established", this.c);
                    }
                } else if (this.a.statusCode == 302 && this.a.redirectURL != null && this.a.redirectURL.length() > 0) {
                    TapjoyLog.i("MRAIDView", "302 redirectURL detected: " + this.a.redirectURL);
                    MraidView.this.loadUrlStandard(this.a.redirectURL);
                } else {
                    MraidView.this.loadDataWithBaseURL(this.c, this.a.response, "text/html", "utf-8", this.c);
                }
            } catch (Exception e) {
                TapjoyLog.w("MRAIDView", "error in loadURL " + e);
                e.printStackTrace();
            }
        }
    }

    public void setContext(Context context) {
        this.y = context;
    }

    static /* synthetic */ void c(MraidView mraidView, String str) {
        if (str != null) {
            String str2 = null;
            if (str.equals("top-right")) {
                str2 = "document.getElementById(\"closeButton\").style.right = 1;document.getElementById(\"closeButton\").style.top = 1;document.getElementById(\"closeButton\").style.bottom = mraid.getSize().height -36;document.getElementById(\"closeButton\").style.left = mraid.getSize().width -36";
            } else if (str.equals("top-center")) {
                str2 = "document.getElementById(\"closeButton\").style.right = mraid.getSize().width/2 - 18;document.getElementById(\"closeButton\").style.top = 1;document.getElementById(\"closeButton\").style.bottom = mraid.getSize().height -36;document.getElementById(\"closeButton\").style.left = mraid.getSize().width/2 -18";
            } else if (str.equals("top-left")) {
                str2 = "document.getElementById(\"closeButton\").style.right = mraid.getSize().width -36;document.getElementById(\"closeButton\").style.top = 1;document.getElementById(\"closeButton\").style.bottom = mraid.getSize().height -36;document.getElementById(\"closeButton\").style.left = 1";
            } else if (str.equals("center")) {
                str2 = "document.getElementById(\"closeButton\").style.right = mraid.getSize().width/2 - 18;document.getElementById(\"closeButton\").style.top = mraid.getSize().height/2 -18;document.getElementById(\"closeButton\").style.bottom = mraid.getSize().height/2 -18;document.getElementById(\"closeButton\").style.left = mraid.getSize().width/2 -18";
            } else if (str.equals("bottom-right")) {
                str2 = "document.getElementById(\"closeButton\").style.right = 1;document.getElementById(\"closeButton\").style.top = mraid.getSize().height -36;document.getElementById(\"closeButton\").style.bottom = 1;document.getElementById(\"closeButton\").style.left = mraid.getSize().width -36";
            } else if (str.equals("bottom-left")) {
                str2 = "document.getElementById(\"closeButton\").style.left = 1;document.getElementById(\"closeButton\").style.bottom = 1;document.getElementById(\"closeButton\").style.right = mraid.getSize().width -36;document.getElementById(\"closeButton\").style.top = mraid.getSize().height-36;";
            } else if (str.equals("bottom-center")) {
                str2 = "document.getElementById(\"closeButton\").style.bottom = 1;document.getElementById(\"closeButton\").style.right = mraid.getSize().width -36document.getElementById(\"closeButton\").style.right = mraid.getSize().width/2 -18;document.getElementById(\"closeButton\").style.top = mraid.getSize().height-36;";
            }
            if (str2 != null) {
                mraidView.injectMraidJavaScript(str2);
            } else {
                TapjoyLog.d("MRAIDView", "Reposition of close button failed.");
            }
        }
    }

    static /* synthetic */ void f(MraidView mraidView) {
        try {
            if (mraidView.r != null) {
                mraidView.r.onClose();
            }
            ((ViewGroup) mraidView.getParent()).removeView(mraidView);
        } catch (Exception e) {
        }
    }

    static /* synthetic */ void m(MraidView mraidView) {
        WindowManager windowManager = (WindowManager) mraidView.getContext().getSystemService("window");
        int width = windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight();
        if ((width == mraidView.t && height == mraidView.u) || mraidView.getPlacementType() != PLACEMENT_TYPE.INTERSTITIAL) {
            return;
        }
        mraidView.resizeOrientation(width, height, "top-right", true);
    }
}
