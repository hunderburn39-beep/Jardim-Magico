package com.google.android.gms.internal;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class ab implements ViewTreeObserver.OnGlobalLayoutListener, ViewTreeObserver.OnScrollChangedListener {
    private static final long lw = TimeUnit.MILLISECONDS.toNanos(100);
    private HashSet<y> lA;
    private final Object li;
    private final WeakReference<dh> ll;
    private WeakReference<ViewTreeObserver> lm;
    private final WeakReference<View> ln;
    private final z lo;
    private final Context lp;
    private final ad lq;
    private boolean lr;
    private final WindowManager ls;
    private final PowerManager lt;
    private final KeyguardManager lu;
    private ac lv;
    private long lx;
    private boolean ly;
    private BroadcastReceiver lz;

    public ab(ak akVar, dh dhVar) {
        this(akVar, dhVar, dhVar.oj.bK(), dhVar.oj, new ae(dhVar.oj.getContext(), dhVar.oj.bK()));
    }

    public ab(ak akVar, dh dhVar, dx dxVar, View view, ad adVar) {
        this.li = new Object();
        this.lx = Long.MIN_VALUE;
        this.lA = new HashSet<>();
        this.ll = new WeakReference<>(dhVar);
        this.ln = new WeakReference<>(view);
        this.lm = new WeakReference<>(null);
        this.ly = true;
        this.lo = new z(Integer.toString(dhVar.hashCode()), dxVar, akVar.lS, dhVar.qs);
        this.lq = adVar;
        this.ls = (WindowManager) view.getContext().getSystemService("window");
        this.lt = (PowerManager) view.getContext().getApplicationContext().getSystemService("power");
        this.lu = (KeyguardManager) view.getContext().getSystemService("keyguard");
        this.lp = view.getContext().getApplicationContext();
        a(adVar);
        this.lq.a(new ad.a() { // from class: com.google.android.gms.internal.ab.1
            @Override // com.google.android.gms.internal.ad.a
            public void ay() {
                ab.this.lr = true;
                ab.this.d(false);
                ab.this.ap();
            }
        });
        b(this.lq);
        dw.x("Tracking ad unit: " + this.lo.ao());
    }

    protected int a(int i, DisplayMetrics displayMetrics) {
        return (int) (i / displayMetrics.density);
    }

    public void a(ac acVar) {
        synchronized (this.li) {
            this.lv = acVar;
        }
    }

    protected void a(ad adVar) {
        adVar.d("http://googleads.g.doubleclick.net/mads/static/sdk/native/sdk-core-v40.html");
    }

    protected void a(dz dzVar, Map<String, String> map) {
        d(false);
    }

    public void a(y yVar) {
        this.lA.add(yVar);
    }

    protected void a(JSONObject jSONObject) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject2 = new JSONObject();
        jSONArray.put(jSONObject);
        jSONObject2.put("units", jSONArray);
        this.lq.a("AFMA_updateActiveView", jSONObject2);
    }

    protected boolean a(View view, boolean z) {
        return view.getVisibility() == 0 && z && view.isShown() && this.lt.isScreenOn() && !this.lu.inKeyguardRestrictedInputMode();
    }

    protected void ap() {
        synchronized (this.li) {
            if (this.lz != null) {
                return;
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            this.lz = new BroadcastReceiver() { // from class: com.google.android.gms.internal.ab.2
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    ab.this.d(false);
                }
            };
            this.lp.registerReceiver(this.lz, intentFilter);
        }
    }

    protected void aq() {
        synchronized (this.li) {
            if (this.lz != null) {
                this.lp.unregisterReceiver(this.lz);
                this.lz = null;
            }
        }
    }

    public void ar() {
        synchronized (this.li) {
            if (this.ly) {
                av();
                aq();
                try {
                    a(ax());
                } catch (JSONException e) {
                    dw.b("JSON Failure while processing active view data.", e);
                }
                this.ly = false;
                as();
                dw.x("Untracked ad unit: " + this.lo.ao());
            }
        }
    }

    protected void as() {
        if (this.lv != null) {
            this.lv.a(this);
        }
    }

    public boolean at() {
        boolean z;
        synchronized (this.li) {
            z = this.ly;
        }
        return z;
    }

    protected void au() {
        View view = this.ln.get();
        if (view == null) {
            return;
        }
        ViewTreeObserver viewTreeObserver = this.lm.get();
        ViewTreeObserver viewTreeObserver2 = view.getViewTreeObserver();
        if (viewTreeObserver2 != viewTreeObserver) {
            this.lm = new WeakReference<>(viewTreeObserver2);
            viewTreeObserver2.addOnScrollChangedListener(this);
            viewTreeObserver2.addOnGlobalLayoutListener(this);
        }
    }

    protected void av() {
        ViewTreeObserver viewTreeObserver = this.lm.get();
        if (viewTreeObserver == null || !viewTreeObserver.isAlive()) {
            return;
        }
        viewTreeObserver.removeOnScrollChangedListener(this);
        viewTreeObserver.removeGlobalOnLayoutListener(this);
    }

    protected JSONObject aw() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("afmaVersion", this.lo.am()).put("activeViewJSON", this.lo.an()).put("timestamp", TimeUnit.NANOSECONDS.toMillis(System.nanoTime())).put("adFormat", this.lo.al()).put("hashCode", this.lo.ao());
        return jSONObject;
    }

    protected JSONObject ax() throws JSONException {
        JSONObject jSONObjectAw = aw();
        jSONObjectAw.put("doneReasonCode", "u");
        return jSONObjectAw;
    }

    protected void b(ad adVar) {
        adVar.a("/updateActiveView", new bb() { // from class: com.google.android.gms.internal.ab.3
            @Override // com.google.android.gms.internal.bb
            public void b(dz dzVar, Map<String, String> map) {
                ab.this.a(dzVar, map);
            }
        });
        adVar.a("/activeViewPingSent", new bb() { // from class: com.google.android.gms.internal.ab.4
            @Override // com.google.android.gms.internal.bb
            public void b(dz dzVar, Map<String, String> map) {
                if (map.containsKey("pingType") && "unloadPing".equals(map.get("pingType"))) {
                    ab.this.c(ab.this.lq);
                    dw.x("Unregistered GMSG handlers for: " + ab.this.lo.ao());
                }
            }
        });
        adVar.a("/visibilityChanged", new bb() { // from class: com.google.android.gms.internal.ab.5
            @Override // com.google.android.gms.internal.bb
            public void b(dz dzVar, Map<String, String> map) {
                if (map.containsKey("isVisible")) {
                    ab.this.c(Boolean.valueOf("1".equals(map.get("isVisible")) || "true".equals(map.get("isVisible"))).booleanValue());
                }
            }
        });
        adVar.a("/viewabilityChanged", ba.mG);
    }

    protected JSONObject c(View view) throws JSONException {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        view.getLocationInWindow(new int[2]);
        JSONObject jSONObjectAw = aw();
        DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();
        Rect rect = new Rect();
        rect.left = iArr[0];
        rect.top = iArr[1];
        rect.right = rect.left + view.getWidth();
        rect.bottom = rect.top + view.getHeight();
        Rect rect2 = new Rect();
        rect2.right = this.ls.getDefaultDisplay().getWidth();
        rect2.bottom = this.ls.getDefaultDisplay().getHeight();
        Rect rect3 = new Rect();
        boolean globalVisibleRect = view.getGlobalVisibleRect(rect3, null);
        Rect rect4 = new Rect();
        view.getLocalVisibleRect(rect4);
        jSONObjectAw.put("viewBox", new JSONObject().put("top", a(rect2.top, displayMetrics)).put("bottom", a(rect2.bottom, displayMetrics)).put("left", a(rect2.left, displayMetrics)).put("right", a(rect2.right, displayMetrics))).put("adBox", new JSONObject().put("top", a(rect.top, displayMetrics)).put("bottom", a(rect.bottom, displayMetrics)).put("left", a(rect.left, displayMetrics)).put("right", a(rect.right, displayMetrics))).put("globalVisibleBox", new JSONObject().put("top", a(rect3.top, displayMetrics)).put("bottom", a(rect3.bottom, displayMetrics)).put("left", a(rect3.left, displayMetrics)).put("right", a(rect3.right, displayMetrics))).put("localVisibleBox", new JSONObject().put("top", a(rect4.top, displayMetrics)).put("bottom", a(rect4.bottom, displayMetrics)).put("left", a(rect4.left, displayMetrics)).put("right", a(rect4.right, displayMetrics))).put("screenDensity", displayMetrics.density).put("isVisible", a(view, globalVisibleRect));
        return jSONObjectAw;
    }

    protected void c(ad adVar) {
        adVar.e("/viewabilityChanged");
        adVar.e("/visibilityChanged");
        adVar.e("/activeViewPingSent");
        adVar.e("/updateActiveView");
    }

    protected void c(boolean z) {
        Iterator<y> it = this.lA.iterator();
        while (it.hasNext()) {
            it.next().a(this, z);
        }
    }

    protected void d(boolean z) {
        synchronized (this.li) {
            if (this.lr && this.ly) {
                long jNanoTime = System.nanoTime();
                if (!z || this.lx + lw <= jNanoTime) {
                    this.lx = jNanoTime;
                    dh dhVar = this.ll.get();
                    View view = this.ln.get();
                    if (view == null || dhVar == null) {
                        ar();
                        return;
                    }
                    try {
                        a(c(view));
                    } catch (JSONException e) {
                        dw.b("Active view update failed.", e);
                    }
                    au();
                    as();
                }
            }
        }
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        d(false);
    }

    @Override // android.view.ViewTreeObserver.OnScrollChangedListener
    public void onScrollChanged() {
        d(true);
    }
}
