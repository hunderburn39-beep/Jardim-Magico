package com.chartboost.sdk.impl;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Model.CBError;
import com.tapjoy.TJAdUnitConstants;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class z extends com.chartboost.sdk.f {
    List<JSONObject> k;
    final com.chartboost.sdk.Libraries.i l;
    final com.chartboost.sdk.Libraries.i m;
    final com.chartboost.sdk.Libraries.i n;
    final com.chartboost.sdk.Libraries.i o;
    final com.chartboost.sdk.Libraries.i p;
    final com.chartboost.sdk.Libraries.i q;
    final com.chartboost.sdk.Libraries.i r;
    protected final com.chartboost.sdk.Libraries.i s;
    protected final com.chartboost.sdk.Libraries.i t;
    int u;
    Integer v;
    int w;
    int x;
    String y;
    private Set<com.chartboost.sdk.Libraries.i> z;

    public class a extends com.chartboost.sdk.f.a {
        private bf d;
        private be e;
        private final TextView f;
        private final RelativeLayout g;
        private ListView h;
        private final C0015a i;

        private a(Context context) {
            super(context);
            setBackgroundColor(-1);
            this.e = new be(context);
            this.d = new bf(context) { // from class: com.chartboost.sdk.impl.z.a.1
                @Override // com.chartboost.sdk.impl.bf
                protected void a(MotionEvent motionEvent) {
                    z.this.h();
                }
            };
            this.d.setContentDescription("CBClose");
            this.f = new TextView(context);
            this.f.setBackgroundColor(z.this.w);
            this.f.setText(z.this.y);
            this.f.setTextColor(z.this.x);
            this.f.setTextSize(2, c() ? 30.0f : 18.0f);
            this.f.setGravity(17);
            this.h = new ListView(context);
            this.h.setBackgroundColor(-1);
            this.h.setDividerHeight(0);
            a(this.h);
            addView(this.h);
            this.e.setFocusable(false);
            this.d.setFocusable(false);
            this.d.setClickable(true);
            this.e.setScaleType(ImageView.ScaleType.CENTER_CROP);
            this.d.a(ImageView.ScaleType.FIT_CENTER);
            this.g = new RelativeLayout(context);
            this.g.addView(this.e, new RelativeLayout.LayoutParams(-1, -1));
            this.g.addView(this.f, new RelativeLayout.LayoutParams(-1, -1));
            addView(this.g, new RelativeLayout.LayoutParams(-1, -1));
            addView(this.d, new RelativeLayout.LayoutParams(-1, -1));
            a(this.g);
            this.i = new C0015a(context);
        }

        @Override // com.chartboost.sdk.f.a
        protected void a(int i, int i2) {
            com.chartboost.sdk.Libraries.i iVar;
            com.chartboost.sdk.Libraries.i iVar2;
            int iA;
            Context context = getContext();
            com.chartboost.sdk.Libraries.e eVarA = CBUtility.a();
            if (eVarA.a() && z.this.o.e()) {
                iVar = z.this.o;
            } else if (eVarA.b() && z.this.p.e()) {
                iVar = z.this.p;
            } else {
                iVar = z.this.r.e() ? z.this.r : null;
            }
            if (iVar != null) {
                z.this.u = iVar.i();
                if (iVar.h() < i) {
                    z.this.u = Math.round(z.this.u * (i / iVar.h()));
                }
                this.f.setVisibility(8);
                this.e.a(iVar);
            } else {
                z.this.u = CBUtility.a(c() ? 80 : 40, context);
                this.f.setVisibility(0);
            }
            if (z.this.v != null) {
                z.this.u = CBUtility.a(z.this.v.intValue(), context);
            }
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-1, z.this.u);
            if (z.this.m.e() && eVarA.a()) {
                iVar2 = z.this.m;
            } else if (z.this.n.e() && eVarA.b()) {
                iVar2 = z.this.n;
            } else {
                iVar2 = z.this.l.e() ? z.this.l : null;
            }
            if (iVar2 != null) {
                this.d.a(iVar2, layoutParams2);
                iA = CBUtility.a(c() ? 14 : 7, context);
            } else {
                this.d.a("X");
                this.d.a().setTextSize(2, c() ? 26.0f : 16.0f);
                this.d.a().setTextColor(z.this.x);
                this.d.a().setTypeface(Typeface.SANS_SERIF, 1);
                layoutParams2.width = z.this.u / 2;
                layoutParams2.height = z.this.u / 3;
                iA = CBUtility.a(c() ? 30 : 20, context);
            }
            int iRound = Math.round((z.this.u - layoutParams2.height) / 2.0f);
            layoutParams2.rightMargin = iA;
            layoutParams2.topMargin = iRound;
            layoutParams2.addRule(11);
            layoutParams.width = -1;
            layoutParams.height = -1;
            layoutParams.addRule(3, this.g.getId());
            this.h.setAdapter((ListAdapter) this.i);
            this.h.setLayoutParams(layoutParams);
            this.d.setLayoutParams(layoutParams2);
            this.g.setLayoutParams(layoutParams3);
        }

        @Override // com.chartboost.sdk.f.a
        public void b() {
            super.b();
            this.d = null;
            this.e = null;
            this.h = null;
        }

        /* JADX INFO: renamed from: com.chartboost.sdk.impl.z$a$a, reason: collision with other inner class name */
        public class C0015a extends ArrayAdapter<JSONObject> {
            private final Context b;

            public C0015a(Context context) {
                super(context, 0, z.this.k);
                this.b = context;
            }

            @Override // android.widget.ArrayAdapter, android.widget.Adapter
            public View getView(int position, View convertView, ViewGroup parent) {
                final s sVar;
                AbsListView.LayoutParams layoutParams;
                s sVarNewInstance;
                int i = 0;
                final JSONObject item = getItem(position);
                String strOptString = item.optString("type");
                if (convertView == null) {
                    b[] bVarArrValues = b.values();
                    int length = bVarArrValues.length;
                    while (true) {
                        if (i >= length) {
                            sVarNewInstance = null;
                            break;
                        }
                        b bVar = bVarArrValues[i];
                        if (!strOptString.equals(bVar.e)) {
                            i++;
                        } else {
                            try {
                                sVarNewInstance = bVar.f.getConstructor(z.class, Context.class).newInstance(z.this, this.b);
                                break;
                            } catch (Exception e) {
                                CBLogging.b(this, "error in more apps list", e);
                                com.chartboost.sdk.Tracking.a.a(getClass(), "getView cell constructor", e);
                                sVarNewInstance = null;
                            }
                        }
                    }
                    sVar = sVarNewInstance;
                } else if (convertView instanceof s) {
                    sVar = (s) convertView;
                } else {
                    return convertView;
                }
                if (sVar == null) {
                    return new View(getContext());
                }
                sVar.a(item, position);
                ViewGroup.LayoutParams layoutParams2 = sVar.getLayoutParams();
                if (layoutParams2 != null && (layoutParams2 instanceof AbsListView.LayoutParams)) {
                    layoutParams = (AbsListView.LayoutParams) layoutParams2;
                    layoutParams.width = -1;
                    layoutParams.height = sVar.a();
                } else {
                    layoutParams = new AbsListView.LayoutParams(-1, sVar.a());
                }
                sVar.setLayoutParams(layoutParams);
                sVar.setOnClickListener(new View.OnClickListener() { // from class: com.chartboost.sdk.impl.z.a.a.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        String strOptString2 = item.optString("deep-link");
                        if (strOptString2.isEmpty() || !al.a(strOptString2)) {
                            strOptString2 = item.optString("link");
                        }
                        if (Build.VERSION.SDK_INT >= 11) {
                            com.chartboost.sdk.Libraries.d.a(item, TMXConstants.TAG_OBJECT_ATTRIBUTE_X, Float.valueOf(a.this.getX()));
                            com.chartboost.sdk.Libraries.d.a(item, TMXConstants.TAG_OBJECT_ATTRIBUTE_Y, Float.valueOf(a.this.getY()));
                            com.chartboost.sdk.Libraries.d.a(item, "width", Integer.valueOf(sVar.getHeight()));
                            com.chartboost.sdk.Libraries.d.a(item, "height", Integer.valueOf(sVar.getWidth()));
                        }
                        z.this.a(strOptString2, item);
                    }
                });
                return sVar;
            }

            @Override // android.widget.ArrayAdapter, android.widget.Adapter
            public int getCount() {
                return z.this.k.size();
            }

            @Override // android.widget.ArrayAdapter, android.widget.Adapter
            /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
            public JSONObject getItem(int i) {
                return z.this.k.get(i);
            }

            @Override // android.widget.BaseAdapter, android.widget.Adapter
            public int getItemViewType(int position) {
                String strOptString = getItem(position).optString("type");
                b[] bVarArrValues = b.values();
                for (int i = 0; i < bVarArrValues.length; i++) {
                    if (strOptString.equals(bVarArrValues[i].e)) {
                        return i;
                    }
                }
                return 0;
            }

            @Override // android.widget.BaseAdapter, android.widget.Adapter
            public int getViewTypeCount() {
                return b.values().length;
            }
        }
    }

    public z(com.chartboost.sdk.Model.a aVar) {
        super(aVar);
        this.k = new ArrayList();
        this.r = new com.chartboost.sdk.Libraries.i(this);
        this.p = new com.chartboost.sdk.Libraries.i(this);
        this.o = new com.chartboost.sdk.Libraries.i(this);
        this.q = new com.chartboost.sdk.Libraries.i(this);
        this.l = new com.chartboost.sdk.Libraries.i(this);
        this.n = new com.chartboost.sdk.Libraries.i(this);
        this.m = new com.chartboost.sdk.Libraries.i(this);
        this.t = new com.chartboost.sdk.Libraries.i(this);
        this.s = new com.chartboost.sdk.Libraries.i(this);
    }

    @Override // com.chartboost.sdk.f
    protected com.chartboost.sdk.f.a b(Context context) {
        return new a(context);
    }

    @Override // com.chartboost.sdk.f
    public boolean a(JSONObject jSONObject) {
        if (!super.a(jSONObject)) {
            return false;
        }
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("cells");
        if (jSONArrayOptJSONArray == null) {
            a(CBError.CBImpressionError.INVALID_RESPONSE);
            return false;
        }
        this.z = new HashSet();
        for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
            JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
            if (jSONObjectOptJSONObject != null) {
                this.k.add(jSONObjectOptJSONObject);
                String strOptString = jSONObjectOptJSONObject.optString("type");
                if (strOptString.equals("regular")) {
                    JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject("assets");
                    if (jSONObjectOptJSONObject2 != null) {
                        a(jSONObjectOptJSONObject2, "icon");
                    }
                } else if (strOptString.equals("featured")) {
                    JSONObject jSONObjectOptJSONObject3 = jSONObjectOptJSONObject.optJSONObject("assets");
                    if (jSONObjectOptJSONObject3 != null) {
                        a(jSONObjectOptJSONObject3, "portrait");
                        a(jSONObjectOptJSONObject3, TJAdUnitConstants.String.LANDSCAPE);
                    }
                } else if (strOptString.equals("webview")) {
                }
            }
        }
        this.l.a(TJAdUnitConstants.String.CLOSE);
        this.n.a("close-landscape");
        this.m.a("close-portrait");
        this.r.a("header-center");
        this.o.a("header-portrait");
        this.p.a("header-landscape");
        this.q.a("header-tile");
        this.t.a("play-button");
        this.s.a("install-button");
        this.v = !this.e.isNull("header-height") ? Integer.valueOf(this.e.optInt("header-height")) : null;
        if (this.v != null) {
            this.u = this.v.intValue();
        } else {
            this.u = a(com.chartboost.sdk.i.m) ? 80 : 40;
        }
        this.w = !this.e.isNull("background-color") ? a(this.e.optString("background-color")) : -14571545;
        this.y = !this.e.isNull("header-text") ? this.e.optString("header-text") : "More Free Games";
        this.x = !this.e.isNull("text-color") ? a(this.e.optString("text-color")) : -1;
        return true;
    }

    private void a(JSONObject jSONObject, String str) {
        if (jSONObject != null && !jSONObject.isNull(str)) {
            com.chartboost.sdk.Libraries.i iVar = new com.chartboost.sdk.Libraries.i(this);
            this.z.add(iVar);
            iVar.a(jSONObject, str, new Bundle());
        }
    }

    @Override // com.chartboost.sdk.f
    public void d() {
        super.d();
        this.k = null;
        Iterator<com.chartboost.sdk.Libraries.i> it = this.z.iterator();
        while (it.hasNext()) {
            it.next().d();
        }
        this.z.clear();
        this.l.d();
        this.n.d();
        this.m.d();
        this.r.d();
        this.q.d();
        this.o.d();
        this.p.d();
        this.t.d();
        this.s.d();
    }

    private enum b {
        FEATURED("featured", t.class),
        REGULAR("regular", u.class),
        WEBVIEW("webview", w.class),
        VIDEO("video", v.class);

        final String e;
        final Class<? extends s> f;

        b(String str, Class cls) {
            this.e = str;
            this.f = cls;
        }
    }
}
