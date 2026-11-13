package com.adcolony.sdk;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import com.google.android.gms.drive.DriveFile;
import com.heyzap.http.AsyncHttpResponseHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
class cj {
    public AtomicBoolean a = new AtomicBoolean(true);
    public AtomicBoolean b = new AtomicBoolean(true);
    private Handler c = new Handler(Looper.getMainLooper());
    private PopupWindow d = null;
    private FrameLayout e = null;
    private WebView f = null;
    private int g = 0;
    private boolean h = false;
    private boolean i = false;
    private a j = new a();
    private b k = new b();
    private c l = new c();
    private ArrayList<ci> m = new ArrayList<>();
    private ci n = null;
    private Object o = new Object();

    cj() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String r() {
        return "AdColonyPubServices";
    }

    Object a() {
        return this.o;
    }

    void a(ci ciVar) {
        synchronized (this.o) {
            bv.aN().a("dismiss_toast", ciVar.s());
        }
    }

    void a(Map<String, Object> map) {
        synchronized (this.o) {
            ci ciVar = new ci(map);
            if (ciVar == null) {
                bw.b("IN-APP-MSG-QUEUE", "Couldn't create toast object from notification", true);
                return;
            }
            this.m.add(ciVar);
            bw.b("IN-APP-MSG-QUEUE", "toasted queue. queue size=" + this.m.size(), true);
            if (ciVar.r() == 0 && this.b.get()) {
                bw.b("IN-APP-MSG-QUEUE", "toast.displayType == NDT_MODAL && allowModalToasts. call showToasts()", true);
                b();
            } else if (ciVar.r() == 1 && this.a.get()) {
                bw.b("IN-APP-MSG-QUEUE", "toast.displayType == NDT_TOAST && allowNonModals. call showToasts()", true);
                b();
            }
        }
    }

    void b() {
        synchronized (this.o) {
            bw.b("IN-APP-MSG-QUEUE", "showToasts() called", true);
            this.h = true;
            if (!this.i) {
                if (this.m.size() > 0) {
                    this.i = true;
                    bw.b("IN-APP-MSG-QUEUE", "in showToasts(). Calling showToastsImpl()", true);
                    c();
                } else {
                    bw.b("IN-APP-MSG-QUEUE", "in showToasts(). do nothing bc !processing && toastQueue.size() <= 0", true);
                }
            } else {
                bw.b("IN-APP-MSG-QUEUE", "in showToasts(). _processing set so do nothing.", true);
                s();
            }
        }
    }

    private void s() {
        synchronized (this.o) {
            if (this.n != null && this.n.e > 0) {
                if (System.currentTimeMillis() - this.n.e > this.n.b()) {
                    a(this.n.a);
                } else {
                    c();
                }
            }
        }
    }

    void c() {
        synchronized (this.o) {
            bw.b("IN-APP-MSG-QUEUE", "showToastsImpl()", true);
            this.h = false;
            this.g = 0;
            if (!bv.aN().ax() || !bv.aN().w()) {
                bw.b("IN-APP-MSG-QUEUE", "showToastsImpl(): !appVisible, aborting", true);
                d();
            } else {
                bw.b("IN-APP-MSG-QUEUE", "calling displayToast() YvolverToastMgr", true);
                e();
            }
        }
    }

    void d() {
        synchronized (this.o) {
            bw.b("IN-APP-MSG-QUEUE", "finishShowingToasts()", true);
            this.i = false;
            if (this.h) {
                bw.b("IN-APP-MSG-QUEUE", "finishShowingToasts() _processingRequest. Calling showToastsImpl", true);
                c();
            } else {
                bw.b("IN-APP-MSG-QUEUE", "finishShowingToasts() else. do nothing.", true);
            }
        }
    }

    void e() {
        synchronized (this.o) {
            if (!bv.aN().ax() || !bv.aN().w()) {
                bw.b("IN-APP-MSG-QUEUE", "displayToast() aborting !appvisible || !ServiceAvailable", true);
                d();
                return;
            }
            bw.b("IN-APP-MSG-QUEUE", "displayToast()", true);
            if (cl.b().e()) {
                bw.b("IN-APP-MSG-QUEUE", "showToastModal - didn't start toast activity, bc overlay showing", true);
                this.i = false;
                this.h = false;
            } else if (!n().booleanValue()) {
                bw.b("IN-APP-MSG-QUEUE", "displayToast() !toastShowing", true);
                if (this.m.size() > 0 && this.g < this.m.size()) {
                    bw.b("IN-APP-MSG-QUEUE", "displayToast() _toastQueue.get()", true);
                    this.n = this.m.get(this.g);
                    if (2 == this.n.r()) {
                        bw.b("IN-APP-MSG-QUEUE", "displayToast() dismissToast bc NDT_NOT_SHOWN", true);
                        a(ci.a.NONE);
                    } else if (this.n.r() == 0 && this.b.get()) {
                        bw.b("IN-APP-MSG-QUEUE", "displayToast() showToastModal bc NDT_MODAL", true);
                        e(this.n);
                    } else if (1 == this.n.r() && this.a.get()) {
                        bw.b("IN-APP-MSG-QUEUE", "displayToast() showToastNonModal bc NDT_TOAST", true);
                        b(this.n);
                    } else {
                        bw.b("IN-APP-MSG-QUEUE", "displayToast() else. Trying to go to next toast. This one was: " + this.n, true);
                        this.g++;
                        this.n = null;
                        e();
                    }
                } else {
                    bw.b("IN-APP-MSG-QUEUE", "displayToast() _toastQueue.size() <= 0 && _processingIndex >= _toastQueue.size(). Calling finishShowingToasts()", true);
                    d();
                }
            } else {
                bw.b("IN-APP-MSG-QUEUE", "displayToast() toastIsShowing. do nothing.", true);
            }
        }
    }

    class b implements Runnable {
        b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (cj.this.o) {
                if (cj.this.f != null && cj.this.e != null && cj.this.n != null) {
                    if (bv.aN().ax() && cj.this.a.get()) {
                        cj.this.f.startAnimation(cj.this.c(cj.this.n));
                        cj.this.e.setVisibility(0);
                    } else {
                        cj.this.g();
                    }
                }
            }
        }
    }

    class c implements Runnable {
        c() {
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (cj.this.o) {
                bw.b(cj.this.r(), "show paused was executed.", true);
                if (cj.this.f != null && cj.this.e != null) {
                    if (bv.aN().ax() && cj.this.a.get()) {
                        cj.this.e.setVisibility(0);
                        cj.this.n.d = System.currentTimeMillis();
                        if (1 == cj.this.n.r()) {
                            cj.this.c.postDelayed(cj.this.j, cj.this.n.b());
                        }
                    } else {
                        cj.this.g();
                    }
                }
            }
        }
    }

    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (cj.this.o) {
                cj.this.c.removeCallbacks(cj.this.j);
                if (cj.this.e == null || cj.this.f == null || cj.this.n.r() != 1) {
                    if (cj.this.e != null && cj.this.f != null) {
                        cj.this.e.setVisibility(4);
                    }
                    cj.this.a(ci.a.NATURAL_TIMEOUT);
                } else {
                    cj.this.f.startAnimation(cj.this.d(cj.this.n));
                    bw.b(cj.this.r(), "animate exit", true);
                }
            }
        }
    }

    void f() {
        synchronized (this.o) {
            this.e.setVisibility(4);
            try {
                if (this.d != null && this.d.isShowing()) {
                    this.d.dismiss();
                    this.d = null;
                }
            } catch (Exception e) {
                bw.b(r(), "exception in dismissFromNewActivity dismissing toast. msg=" + e.getLocalizedMessage(), e, false, false);
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            bw.b(r(), "current Time: " + jCurrentTimeMillis, true);
            bw.b(r(), "toastVisibleTime=" + this.n.d + ", previouslyVisibleCount=" + (this.n.d > 0 ? (jCurrentTimeMillis - this.n.d) + 0 : 0L), true);
            if (this.n.r() == 1) {
                bw.b(r(), "duration: " + this.n.b(), true);
            }
            this.c.removeCallbacks(this.j);
            this.c.removeCallbacks(this.k);
            this.c.removeCallbacks(this.l);
            this.n.a = ci.a.ACTIVITY_HIDDEN;
        }
    }

    void g() {
        synchronized (this.o) {
            bw.b(r(), "pauseToast()", true);
            long jCurrentTimeMillis = System.currentTimeMillis();
            bw.b(r(), "pauseToast() currentTime: " + jCurrentTimeMillis, true);
            bw.b(r(), "pauseToast() toastVisibleTime=" + this.n.d + ", previouslyVisibleCount=" + (this.n.d > 0 ? 0 + (jCurrentTimeMillis - this.n.d) : 0L), true);
            if (this.n.r() == 1) {
                bw.b(r(), "pauseToast() duration: " + this.n.b(), true);
            }
            this.c.removeCallbacks(this.j);
            this.c.removeCallbacks(this.k);
            this.c.removeCallbacks(this.l);
        }
    }

    void h() {
        synchronized (this.o) {
            bw.b(r(), "enterBackground()", true);
            if (n().booleanValue() && !o().booleanValue()) {
                a(ci.a.ACTIVITY_HIDDEN);
            }
        }
    }

    void i() {
        synchronized (this.o) {
            bw.b(r(), "pauseFromBackground()", true);
            if (n().booleanValue() || o().booleanValue()) {
                g();
            }
        }
    }

    void j() {
        synchronized (this.o) {
            bw.b(r(), "resumeFromBackground()", true);
            b();
        }
    }

    void a(String str) {
        synchronized (this.o) {
            bw.b(r(), "Removing toast with sourceId: " + str, true);
            int i = 0;
            while (i < this.m.size() && !this.m.get(i).q().equals(str)) {
                i++;
            }
            if (i < this.m.size()) {
                this.m.remove(i);
            } else {
                bw.b(r(), "Couldn't find toast (sourceId: " + str + ") in queue during removal", true);
            }
        }
    }

    void k() {
        synchronized (this.o) {
            a(ci.a.FROM_JS);
        }
    }

    void l() {
        synchronized (this.o) {
            this.n.w();
            a(ci.a.FROM_JS);
        }
    }

    void m() {
        synchronized (this.o) {
            if (this.n != null) {
                this.n.w();
            }
            cl.b().a("", false, false);
        }
    }

    void a(String str, String str2, ci ciVar) {
        synchronized (this.o) {
            if (ciVar != null) {
                ciVar.w();
                cl.b().a(str + str2, false, true, ciVar.r(), ciVar.q());
            } else {
                cl.b().a(str + str2, false, true, ciVar.r(), ciVar.q());
            }
            throw th;
        }
    }

    void a(ci.a aVar) {
        synchronized (this.o) {
            if (n().booleanValue()) {
                bw.b(r(), "Dismissing toast after " + this.n.d + "ms. Reason = " + this.n.a.toString(), true);
                this.n.v();
                this.n.b = ci.c.DISMISSING;
                this.n.a = aVar;
                this.n.g.a(new bk() { // from class: com.adcolony.sdk.cj.1
                    @Override // com.adcolony.sdk.bk
                    void a(bn.a aVar2) {
                        synchronized (cj.this.o) {
                            cj.this.a(cj.this.n.q());
                            cj.this.a(cj.this.n);
                            cj.this.n = null;
                            cj.this.c.postDelayed(new Runnable() { // from class: com.adcolony.sdk.cj.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    cj.this.e();
                                }
                            }, bv.aN().aw().d());
                        }
                    }
                });
            }
        }
    }

    public Boolean n() {
        Boolean boolValueOf;
        synchronized (this.o) {
            boolValueOf = Boolean.valueOf(this.n != null);
        }
        return boolValueOf;
    }

    public Boolean o() {
        Boolean boolValueOf;
        synchronized (this.o) {
            boolValueOf = Boolean.valueOf(this.n != null && this.n.r() == 0);
        }
        return boolValueOf;
    }

    /* JADX INFO: renamed from: com.adcolony.sdk.cj$2, reason: invalid class name */
    class AnonymousClass2 implements Runnable {
        final /* synthetic */ ci a;

        AnonymousClass2(ci ciVar) {
            this.a = ciVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (cj.this.o) {
                bw.b(cj.this.r(), "showToastNonModal()", true);
                this.a.c = 0L;
                this.a.d = 0L;
                this.a.b = ci.c.QUEUED;
                Context contextK = bv.aN().k();
                cj.this.f = new WebView(contextK);
                cj.this.f.setAlpha(1.0f);
                cj.this.f.setBackgroundColor(0);
                cj.this.f.setVerticalScrollBarEnabled(false);
                cj.this.f.setHorizontalScrollBarEnabled(false);
                cj.this.f.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                Context contextL = bv.aN().l();
                cj.this.f.getSettings().setDomStorageEnabled(true);
                cj.this.f.getSettings().setAppCachePath(contextL.getCacheDir().getAbsolutePath());
                cj.this.f.getSettings().setAllowFileAccess(true);
                cj.this.f.getSettings().setAppCacheEnabled(true);
                cj.this.f.getSettings().setJavaScriptEnabled(true);
                cj.this.f.getSettings().setCacheMode(-1);
                cj.this.f.addJavascriptInterface(new ba(new b(cj.this.f, this.a)), "Android");
                if (bv.aN().au().c() > 7) {
                    cj.this.f.setWebChromeClient(new WebChromeClient() { // from class: com.adcolony.sdk.cj.2.1
                        @Override // android.webkit.WebChromeClient
                        public boolean onConsoleMessage(ConsoleMessage cm) {
                            bw.b(cj.this.r(), cm.message() + " -- From line " + cm.lineNumber() + " of " + cm.sourceId(), true);
                            return true;
                        }
                    });
                } else {
                    cj.this.f.setWebChromeClient(new WebChromeClient() { // from class: com.adcolony.sdk.cj.2.2
                        @Override // android.webkit.WebChromeClient
                        public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                            bw.b(cj.this.r(), message + " -- From line " + lineNumber + " of " + sourceID, true);
                        }
                    });
                }
                cj.this.f.setWebViewClient(new WebViewClient() { // from class: com.adcolony.sdk.cj.2.3
                    @Override // android.webkit.WebViewClient
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        synchronized (cj.this.o) {
                            if (cj.this.e != null && cj.this.f != null) {
                                cj.this.c.postDelayed(cj.this.k, bv.aN().aw().d());
                            }
                        }
                    }
                });
                cj.this.f.loadDataWithBaseURL("http://www.yvolver.com", this.a.c(), "text/html", AsyncHttpResponseHandler.DEFAULT_CHARSET, null);
                if (cj.this.n != null) {
                    cj.this.n.a();
                }
                cj.this.e = new FrameLayout(contextK);
                cj.this.e.setVisibility(4);
                cj.this.e.addView(cj.this.f);
                cj.this.d = new PopupWindow(contextK);
                cj.this.d.setBackgroundDrawable(new ColorDrawable(0));
                cj.this.d.setContentView(cj.this.e);
                cj.this.d.setWidth(ck.a(this.a.i()));
                cj.this.d.setHeight(ck.a(this.a.j()));
                cj.this.d.showAtLocation(cj.this.f, this.a.h(this.a.d()), 0, 0);
                this.a.g = new a(cj.this.f, null);
            }
        }

        /* JADX INFO: renamed from: com.adcolony.sdk.cj$2$b */
        class b implements cn {
            private WebView b;
            private ci c;

            public b(WebView webView, ci ciVar) {
                this.b = null;
                this.c = null;
                this.b = webView;
                this.c = ciVar;
            }

            @Override // com.adcolony.sdk.cn
            public WebView a() {
                return this.b;
            }

            @Override // com.adcolony.sdk.cn
            @SuppressLint({"RtlHardcoded"})
            public void a(Map<String, Object> map) {
            }

            @Override // com.adcolony.sdk.cn
            public void b() {
                synchronized (cj.this.o) {
                    AnonymousClass2.this.a.w();
                    cj.this.a(ci.a.FROM_JS);
                }
            }

            @Override // com.adcolony.sdk.cn
            public void c() {
                synchronized (cj.this.o) {
                    bw.b(cj.this.r(), "openCatalog()", true);
                    a(null, null);
                }
            }

            @Override // com.adcolony.sdk.cn
            public void a(String str, String str2) {
                synchronized (cj.this.o) {
                    bw.b(cj.this.r(), "openCatalogItem was called", true);
                    AnonymousClass2.this.a.w();
                    if (str == null) {
                        str = "";
                    }
                    if (str2 == null) {
                        str2 = "";
                    }
                    HashMap map = new HashMap();
                    map.put("path", str);
                    map.put("params", str2);
                    if (cj.this.n().booleanValue()) {
                        this.c.f = map;
                        cj.this.a(ci.a.REDIRECT_TO_CATALOG);
                    }
                    cj.this.a(str, str2, this.c);
                }
            }
        }

        /* JADX INFO: renamed from: com.adcolony.sdk.cj$2$a */
        class a implements ci.d {
            private WebView b;
            private Context c;

            public a(WebView webView, Context context) {
                this.b = null;
                this.c = null;
                this.b = webView;
                this.c = context;
            }

            @Override // com.adcolony.sdk.ci.d
            public WebView a() {
                return this.b;
            }

            @Override // com.adcolony.sdk.ci.d
            public Context b() {
                return this.c;
            }

            @Override // com.adcolony.sdk.ci.d
            public void c() {
                bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.cj.2.a.1
                    @Override // java.lang.Runnable
                    public void run() {
                        synchronized (cj.this.o) {
                            if (a.this.b != null) {
                                ck.a(a.this.b, "javascript:finishDigitalRedemptionTransaction()");
                            }
                        }
                    }
                });
            }

            @Override // com.adcolony.sdk.ci.d
            public void a(final bk bkVar) {
                bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.cj.2.a.2
                    @Override // java.lang.Runnable
                    public void run() {
                        synchronized (cj.this.o) {
                            bw.b(cj.this.r(), "handleCloseView() called", true);
                            if (cj.this.n != null) {
                                if (2 != cj.this.n.r()) {
                                    cj.this.c.removeCallbacks(cj.this.j);
                                    if (cj.this.f != null) {
                                        ViewGroup viewGroup = (ViewGroup) cj.this.f.getParent();
                                        cj.this.f.removeAllViews();
                                        viewGroup.removeAllViews();
                                        viewGroup.invalidate();
                                    }
                                    try {
                                        if (cj.this.f != null) {
                                            cj.this.f.stopLoading();
                                            cj.this.f.invalidate();
                                            cj.this.f.removeAllViews();
                                            cj.this.e.removeView(cj.this.f);
                                            cj.this.e.invalidate();
                                            cj.this.f.destroy();
                                        }
                                        try {
                                            cj.this.e.setVisibility(8);
                                        } catch (Exception e) {
                                            bw.b(cj.this.r(), "toastLayout not attached to window mgr", true);
                                        }
                                    } catch (Exception e2) {
                                        bw.a(cj.this.r(), "Caught Exception->" + e2.getMessage(), e2);
                                    }
                                    try {
                                        if (cj.this.d != null && cj.this.d.isShowing()) {
                                            cj.this.d.dismiss();
                                        }
                                    } catch (Exception e3) {
                                        bw.b(cj.this.r(), "Exception in dismissToast dismissing toast");
                                    }
                                    bkVar.a(bn.a.YVOLVER_ERROR_NONE);
                                }
                                cj.this.e = null;
                                cj.this.f = null;
                                cj.this.d = null;
                            }
                        }
                    }
                });
            }
        }
    }

    @TargetApi(19)
    void b(ci ciVar) {
        bv.aN().as().post(new AnonymousClass2(ciVar));
    }

    Animation c(final ci ciVar) {
        Animation animationA;
        synchronized (this.o) {
            animationA = ciVar.a(ciVar.e());
            animationA.setDuration(ciVar.f());
            animationA.setAnimationListener(new Animation.AnimationListener() { // from class: com.adcolony.sdk.cj.3
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation arg0) {
                    synchronized (cj.this.o) {
                        ciVar.d = System.currentTimeMillis();
                        cj.this.c.postDelayed(cj.this.j, ciVar.b());
                    }
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation arg0) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation arg0) {
                }
            });
        }
        return animationA;
    }

    Animation d(ci ciVar) {
        Animation animationG;
        synchronized (this.o) {
            animationG = ciVar.g(ciVar.g());
            animationG.setDuration(ciVar.h());
            animationG.setFillAfter(true);
            animationG.setAnimationListener(new Animation.AnimationListener() { // from class: com.adcolony.sdk.cj.4
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    cj.this.a(ci.a.NATURAL_TIMEOUT);
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                }
            });
        }
        return animationG;
    }

    void e(ci ciVar) {
        bv.aN().as().post(new Runnable() { // from class: com.adcolony.sdk.cj.5
            @Override // java.lang.Runnable
            public void run() {
                synchronized (cj.this.o) {
                    Intent intent = new Intent(bv.aN().l(), (Class<?>) AdColonyPubServicesModalActivity.class);
                    intent.addFlags(67108864);
                    intent.addFlags(DriveFile.MODE_WRITE_ONLY);
                    intent.addFlags(DriveFile.MODE_READ_ONLY);
                    bv.aN().l().startActivity(intent);
                }
            }
        });
    }

    public ci p() {
        ci ciVar;
        synchronized (this.o) {
            ciVar = this.n;
        }
        return ciVar;
    }

    public void q() {
        synchronized (this.o) {
            if (this.n != null) {
                this.n.g.c();
            }
        }
    }

    public void a(int i, String str) {
        WebView webViewA;
        synchronized (this.o) {
            if (this.n != null && (webViewA = this.n.g.a()) != null) {
                cm.a(webViewA, i, str);
            }
        }
    }
}
