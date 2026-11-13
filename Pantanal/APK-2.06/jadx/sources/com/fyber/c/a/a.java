package com.fyber.c.a;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fyber.Fyber;
import com.fyber.utils.StringUtils;
import com.fyber.utils.d;
import com.fyber.utils.r;
import com.fyber.utils.y;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: compiled from: MicroBrowser.java */
/* JADX INFO: loaded from: classes.dex */
public final class a extends LinearLayout {
    private WebView a;
    private boolean b;
    private AtomicBoolean c;
    private AtomicBoolean d;
    private b e;
    private InterfaceC0020a f;
    private c g;

    /* JADX INFO: renamed from: com.fyber.c.a.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: MicroBrowser.java */
    public interface InterfaceC0020a {
        void a();
    }

    /* JADX INFO: compiled from: MicroBrowser.java */
    public interface b {
        void a();
    }

    /* JADX INFO: compiled from: MicroBrowser.java */
    public interface c {
        boolean a(a aVar, String str);
    }

    private a(Activity activity) {
        super(activity);
        this.b = true;
        this.c = new AtomicBoolean(false);
        this.d = new AtomicBoolean(false);
        setContentDescription("microBrowser");
        setBackgroundColor(Color.parseColor("#333333"));
        setOrientation(1);
        RelativeLayout relativeLayout = new RelativeLayout(activity);
        relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        relativeLayout.setPadding(0, 10, 0, 10);
        relativeLayout.setBackgroundColor(0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(11);
        layoutParams.addRule(15);
        com.fyber.c.b.b bVar = new com.fyber.c.b.b(activity);
        bVar.setLayoutParams(layoutParams);
        bVar.setOnClickListener(new View.OnClickListener() { // from class: com.fyber.c.a.a.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                a.a(a.this);
            }
        });
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams2.addRule(14);
        final TextView textView = new TextView(activity);
        textView.setLayoutParams(layoutParams2);
        textView.setGravity(17);
        textView.setTextSize(1, 17.0f);
        textView.setTextColor(-1);
        textView.setSingleLine();
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setId(12345);
        textView.setContentDescription("microBrowserTitle");
        int iApplyDimension = (int) TypedValue.applyDimension(1, 50.0f, getResources().getDisplayMetrics());
        int iApplyDimension2 = (int) TypedValue.applyDimension(1, 5.0f, getResources().getDisplayMetrics());
        textView.setPadding(iApplyDimension, iApplyDimension2, iApplyDimension, iApplyDimension2);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams3.addRule(14);
        layoutParams3.addRule(3, 12345);
        final TextView textView2 = new TextView(activity);
        textView2.setGravity(17);
        textView2.setLayoutParams(layoutParams3);
        textView2.setTextSize(1, 13.0f);
        textView2.setTextColor(-1);
        textView2.setText(r.a(Fyber.Settings.UIStringIdentifier.RV_LOADING_MESSAGE));
        textView2.setContentDescription("microBrowserUrl");
        relativeLayout.addView(textView);
        relativeLayout.addView(textView2);
        relativeLayout.addView(bVar);
        addView(relativeLayout);
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-1, -1);
        this.a = new WebView(activity);
        this.a.setLayoutParams(layoutParams4);
        this.a.setScrollBarStyle(0);
        this.a.getSettings().setJavaScriptEnabled(true);
        y.a(this.a.getSettings());
        y.a(this.a);
        this.a.setWebChromeClient(new WebChromeClient());
        this.a.setWebViewClient(new WebViewClient() { // from class: com.fyber.c.a.a.2
            @Override // android.webkit.WebViewClient
            public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
                boolean z = a.this.g != null && a.this.g.a(a.this, str);
                if (!z) {
                    textView2.setText(r.a(Fyber.Settings.UIStringIdentifier.RV_LOADING_MESSAGE));
                }
                return z;
            }

            @Override // android.webkit.WebViewClient
            public final void onReceivedError(WebView webView, int i, String str, String str2) {
                super.onReceivedError(webView, i, str, str2);
                if (a.this.f != null) {
                    a.this.f.a();
                }
            }

            @Override // android.webkit.WebViewClient
            public final void onPageFinished(WebView webView, String str) {
                if (a.this.c.compareAndSet(true, false)) {
                    webView.clearHistory();
                    a.this.d.set(true);
                }
                String title = webView.getTitle();
                if (StringUtils.notNullNorEmpty(title)) {
                    textView.setText(title);
                    textView2.setText(webView.getUrl());
                } else {
                    textView.setText("");
                    textView2.setText("");
                }
            }

            @Override // android.webkit.WebViewClient
            public final void onScaleChanged(WebView webView, float f, float f2) {
                if (webView != null) {
                    webView.invalidate();
                }
            }
        });
        addView(this.a);
    }

    public a(Activity activity, String str) {
        this(activity);
        a(str);
    }

    private void a(final String str) {
        Fyber.getConfigs();
        Fyber.a.a(new d() { // from class: com.fyber.c.a.a.3
            @Override // com.fyber.utils.d
            public final void a() {
                a.this.a.loadUrl(str);
            }
        });
    }

    public final void a() {
        WebBackForwardList webBackForwardListCopyBackForwardList = this.a.copyBackForwardList();
        if (webBackForwardListCopyBackForwardList.getSize() > 1) {
            this.c.set(true);
            a(webBackForwardListCopyBackForwardList.getItemAtIndex(0).getUrl());
        }
    }

    public final void b() {
        a("about:blank");
    }

    public final boolean c() {
        if (!this.b || !this.a.canGoBack()) {
            return false;
        }
        this.a.goBack();
        return true;
    }

    @Override // android.view.View
    protected final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (view == this) {
            this.b = i == 0;
            if (this.b && this.d.compareAndSet(true, false)) {
                this.a.onResume();
                this.a.reload();
            }
        }
    }

    public final void a(b bVar) {
        this.e = bVar;
    }

    public final void a(c cVar) {
        this.g = cVar;
    }

    public final void a(InterfaceC0020a interfaceC0020a) {
        this.f = interfaceC0020a;
    }

    static /* synthetic */ void a(a aVar) {
        aVar.a.onPause();
        if (aVar.e == null) {
            return;
        }
        aVar.e.a();
    }
}
