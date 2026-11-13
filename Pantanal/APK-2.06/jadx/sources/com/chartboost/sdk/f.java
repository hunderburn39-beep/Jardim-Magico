package com.chartboost.sdk;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Model.CBError;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public abstract class f {
    protected JSONObject e;
    public final com.chartboost.sdk.Model.a f;
    public final Handler a = CBUtility.c();
    public boolean b = false;
    protected final List<b> c = new ArrayList();
    protected final List<b> d = new ArrayList();
    public final Map<View, Runnable> h = new IdentityHashMap();
    protected boolean i = true;
    protected boolean j = true;
    private a l = null;
    protected com.chartboost.sdk.Libraries.e g = CBUtility.a();
    private boolean k = false;

    public interface b {
        boolean a();
    }

    protected abstract a b(Context context);

    void a(a aVar) {
        this.l = aVar;
        this.k = false;
    }

    public abstract class a extends RelativeLayout {
        com.chartboost.sdk.Libraries.e a;
        private boolean c;
        private int d;
        private int e;
        private int f;
        private int g;

        protected abstract void a(int i, int i2);

        public a(Context context) {
            super(context);
            this.c = false;
            this.d = -1;
            this.e = -1;
            this.f = -1;
            this.g = -1;
            this.a = null;
            f.this.a(this);
            setFocusableInTouchMode(true);
            requestFocus();
        }

        @Override // android.view.View
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            this.f = w;
            this.g = h;
            if (this.d != -1 && this.e != -1 && f.this.f != null && f.this.f.b == com.chartboost.sdk.Model.a.b.NATIVE) {
                a();
            }
        }

        private boolean b(int i, int i2) {
            boolean z = true;
            if (f.this.f != null && f.this.f.b == com.chartboost.sdk.Model.a.b.WEB) {
                return true;
            }
            if (this.c) {
                return false;
            }
            com.chartboost.sdk.Libraries.e eVarA = CBUtility.a();
            if (this.d == i && this.e == i2 && this.a == eVarA) {
                return true;
            }
            this.c = true;
            try {
                if (f.this.i && eVarA.a()) {
                    f.this.g = eVarA;
                } else if (f.this.j && eVarA.b()) {
                    f.this.g = eVarA;
                }
                a(i, i2);
                post(new Runnable() { // from class: com.chartboost.sdk.f.a.1
                    @Override // java.lang.Runnable
                    public void run() {
                        a.this.requestLayout();
                    }
                });
                this.d = i;
                this.e = i2;
                this.a = eVarA;
            } catch (Exception e) {
                CBLogging.b("CBViewProtocol", "Exception raised while layouting Subviews", e);
                com.chartboost.sdk.Tracking.a.a(getClass(), "tryLayout", e);
                z = false;
            }
            this.c = false;
            return z;
        }

        public final void a() {
            a(false);
        }

        public final void a(boolean z) {
            if (z) {
                this.a = null;
            }
            a((Activity) com.chartboost.sdk.impl.g.a().a(this));
        }

        public void b() {
        }

        public boolean a(Activity activity) {
            int height;
            int width;
            if (this.f == -1 || this.g == -1) {
                try {
                    width = getWidth();
                    height = getHeight();
                    if (width == 0 || height == 0) {
                        View viewFindViewById = activity.getWindow().findViewById(R.id.content);
                        if (viewFindViewById == null) {
                            viewFindViewById = activity.getWindow().getDecorView();
                        }
                        width = viewFindViewById.getWidth();
                        height = viewFindViewById.getHeight();
                    }
                } catch (Exception e) {
                    height = 0;
                    width = 0;
                }
                if (width == 0 || height == 0) {
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    width = displayMetrics.widthPixels;
                    height = displayMetrics.heightPixels;
                }
                this.f = width;
                this.g = height;
            }
            return b(this.f, this.g);
        }

        @Override // android.view.ViewGroup, android.view.View
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            synchronized (f.this.h) {
                Iterator<Runnable> it = f.this.h.values().iterator();
                while (it.hasNext()) {
                    f.this.a.removeCallbacks(it.next());
                }
                f.this.h.clear();
            }
        }

        public final void a(View view) {
            int i = 200;
            if (200 == getId()) {
                i = 201;
            }
            int i2 = i;
            View viewFindViewById = findViewById(i);
            while (viewFindViewById != null) {
                i2++;
                viewFindViewById = findViewById(i2);
            }
            view.setId(i2);
            view.setSaveEnabled(false);
        }

        protected boolean c() {
            return f.a(getContext());
        }
    }

    public static boolean a(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) >= 4;
    }

    public f(com.chartboost.sdk.Model.a aVar) {
        this.f = aVar;
    }

    public com.chartboost.sdk.Libraries.e a() {
        return this.g;
    }

    public boolean a(JSONObject jSONObject) {
        this.e = com.chartboost.sdk.Libraries.d.a(jSONObject, "assets");
        if (this.e != null) {
            return true;
        }
        this.e = (JSONObject) g.a().a(new JSONObject());
        CBLogging.b("CBViewProtocol", "Media got from the response is null or empty");
        a(CBError.CBImpressionError.INVALID_RESPONSE);
        return false;
    }

    public void a(b bVar) {
        if (bVar.a()) {
            this.d.remove(bVar);
        }
        this.c.remove(bVar);
        if (this.c.isEmpty() && !b()) {
            CBLogging.b("CBViewProtocol", "Error while downloading the assets");
            a(CBError.CBImpressionError.ASSETS_DOWNLOAD_FAILURE);
        }
    }

    public boolean b() {
        if (!this.d.isEmpty()) {
            CBLogging.d("CBViewProtocol", "not completed loading assets for impression");
            return false;
        }
        i();
        return true;
    }

    public CBError.CBImpressionError c() {
        Activity activityH = this.f.a.f.h();
        if (activityH == null) {
            this.l = null;
            return CBError.CBImpressionError.NO_HOST_ACTIVITY;
        }
        if (!this.j && !this.i) {
            return CBError.CBImpressionError.WRONG_ORIENTATION;
        }
        if (this.l == null) {
            this.l = b(activityH);
        }
        if (this.f.b != com.chartboost.sdk.Model.a.b.NATIVE || this.l.a(activityH)) {
            return null;
        }
        this.l = null;
        return CBError.CBImpressionError.ERROR_CREATING_VIEW;
    }

    public void d() {
        f();
        synchronized (this.h) {
            Iterator<Runnable> it = this.h.values().iterator();
            while (it.hasNext()) {
                this.a.removeCallbacks(it.next());
            }
            this.h.clear();
        }
    }

    public a e() {
        return this.l;
    }

    public void f() {
        if (this.l != null) {
            this.l.b();
        }
        this.l = null;
    }

    public JSONObject g() {
        return this.e;
    }

    public void b(b bVar) {
        this.c.add(bVar);
        this.d.add(bVar);
    }

    public void a(CBError.CBImpressionError cBImpressionError) {
        this.f.a(cBImpressionError);
    }

    public void h() {
        if (!this.k) {
            this.k = true;
            this.f.b();
        }
    }

    protected void i() {
        this.f.c();
    }

    public boolean a(String str, JSONObject jSONObject) {
        return this.f.a(str, jSONObject);
    }

    public void a(boolean z, View view) {
        a(z, view, true);
    }

    public void a(final boolean z, final View view, boolean z2) {
        if ((z && view.getVisibility() == 0) || (!z && view.getVisibility() == 8)) {
            synchronized (this.h) {
                if (!this.h.containsKey(view)) {
                    return;
                }
            }
        }
        if (!z2) {
            view.setVisibility(z ? 0 : 8);
            view.setClickable(z);
        } else {
            Runnable runnable = new Runnable() { // from class: com.chartboost.sdk.f.1
                @Override // java.lang.Runnable
                public void run() {
                    if (!z) {
                        view.setVisibility(8);
                        view.setClickable(false);
                    }
                    synchronized (f.this.h) {
                        f.this.h.remove(view);
                    }
                }
            };
            if (this.f.b == com.chartboost.sdk.Model.a.b.WEB) {
            }
            this.f.a.j.a.a(z, view, 500L);
            a(view, runnable, 500L);
        }
    }

    public void a(View view, Runnable runnable, long j) {
        synchronized (this.h) {
            Runnable runnable2 = this.h.get(view);
            if (runnable2 != null) {
                this.a.removeCallbacks(runnable2);
            }
            this.h.put(view, runnable);
        }
        this.a.postDelayed(runnable, j);
    }

    public static int a(String str) {
        if (str == null) {
            return 0;
        }
        if (!str.startsWith("#")) {
            try {
                return Color.parseColor(str);
            } catch (IllegalArgumentException e) {
                str = "#" + str;
            }
        }
        if (str.length() == 4 || str.length() == 5) {
            StringBuilder sb = new StringBuilder((str.length() * 2) + 1);
            sb.append("#");
            for (int i = 0; i < str.length() - 1; i++) {
                sb.append(str.charAt(i + 1));
                sb.append(str.charAt(i + 1));
            }
            str = sb.toString();
        }
        try {
            return Color.parseColor(str);
        } catch (IllegalArgumentException e2) {
            CBLogging.d("CBViewProtocol", "error parsing color " + str, e2);
            return 0;
        }
    }

    public float j() {
        return BitmapDescriptorFactory.HUE_RED;
    }

    public float k() {
        return BitmapDescriptorFactory.HUE_RED;
    }

    public boolean l() {
        return false;
    }

    public void m() {
        if (this.b) {
            this.b = false;
        }
        if (e() != null && CBUtility.a() != e().a) {
            e().a(false);
        }
    }

    public void n() {
        this.b = true;
    }
}
