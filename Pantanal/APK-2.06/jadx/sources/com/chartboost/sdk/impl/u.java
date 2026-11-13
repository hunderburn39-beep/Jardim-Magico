package com.chartboost.sdk.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chartboost.sdk.Libraries.CBUtility;
import com.google.android.gms.plus.PlusShare;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"ViewConstructor"})
public class u extends s {
    final bf a;
    View.OnClickListener b;
    private z c;
    private TextView d;
    private TextView e;
    private TextView f;
    private LinearLayout g;
    private x h;
    private int i;
    private Point j;
    private com.chartboost.sdk.Libraries.i k;

    public u(z zVar, Context context) {
        super(context);
        this.c = zVar;
        this.g = new LinearLayout(context);
        this.g.setOrientation(1);
        setGravity(16);
        boolean zA = com.chartboost.sdk.f.a(context);
        this.d = new TextView(context);
        this.d.setTypeface(null, 1);
        this.d.setTextSize(2, zA ? 21.0f : 16.0f);
        this.d.setTextColor(-16777216);
        this.d.setSingleLine();
        this.d.setEllipsize(TextUtils.TruncateAt.END);
        this.e = new TextView(context);
        this.e.setTextSize(2, zA ? 16.0f : 10.0f);
        this.e.setTextColor(-16777216);
        this.e.setSingleLine();
        this.e.setEllipsize(TextUtils.TruncateAt.END);
        this.f = new TextView(context);
        this.f.setTextSize(2, zA ? 18.0f : 11.0f);
        this.f.setTextColor(-16777216);
        this.f.setMaxLines(2);
        this.f.setEllipsize(TextUtils.TruncateAt.END);
        this.a = new bf(context) { // from class: com.chartboost.sdk.impl.u.1
            @Override // com.chartboost.sdk.impl.bf
            protected void a(MotionEvent motionEvent) {
                u.this.b.onClick(u.this.a);
            }
        };
        this.a.a(ImageView.ScaleType.FIT_CENTER);
        this.h = new x(context);
        setFocusable(false);
        setGravity(16);
        addView(this.h);
        addView(this.g, new LinearLayout.LayoutParams(0, -2, 1.0f));
        addView(this.a);
        setBackgroundColor(0);
        this.g.addView(this.d, new LinearLayout.LayoutParams(-1, -2));
        this.g.addView(this.e, new LinearLayout.LayoutParams(-1, -2));
        this.g.addView(this.f, new LinearLayout.LayoutParams(-1, -1));
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener clickListener) {
        super.setOnClickListener(clickListener);
        this.b = clickListener;
    }

    @Override // com.chartboost.sdk.impl.s
    public void a(JSONObject jSONObject, int i) {
        int iA;
        this.d.setText(jSONObject.optString("name", "Unknown App"));
        String strOptString = jSONObject.optString("publisher");
        if (strOptString.isEmpty()) {
            this.e.setVisibility(8);
        } else {
            this.e.setText(strOptString);
        }
        String strOptString2 = jSONObject.optString(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION);
        if (strOptString2.isEmpty()) {
            this.f.setVisibility(8);
        } else {
            this.f.setText(strOptString2);
        }
        this.i = jSONObject.isNull("border-color") ? -4802890 : com.chartboost.sdk.f.a(jSONObject.optString("border-color"));
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("offset");
        if (jSONObjectOptJSONObject != null) {
            this.j = new Point(jSONObjectOptJSONObject.optInt(TMXConstants.TAG_OBJECT_ATTRIBUTE_X), jSONObjectOptJSONObject.optInt(TMXConstants.TAG_OBJECT_ATTRIBUTE_Y));
        } else {
            this.j = new Point(0, 0);
        }
        this.k = null;
        if (!jSONObject.isNull("deep-link") && al.a(jSONObject.optString("deep-link"))) {
            if (this.c.t.e()) {
                this.k = this.c.t;
            } else {
                this.a.a("Play");
            }
        } else if (this.c.s.e()) {
            this.k = this.c.s;
        } else {
            this.a.a("Install");
        }
        int iA2 = CBUtility.a(com.chartboost.sdk.f.a(getContext()) ? 14 : 7, getContext());
        if (this.k != null) {
            this.a.a(this.k);
            iA = (iA2 * 2) + Math.round((this.k.b() * c()) / this.k.c());
        } else {
            this.a.a().setTextColor(-14571545);
            int iA3 = CBUtility.a(8, getContext());
            this.a.a().setPadding(iA3, iA3, iA3, iA3);
            iA = CBUtility.a(100, getContext());
        }
        this.a.setLayoutParams(new LinearLayout.LayoutParams(iA, c()));
        removeView(this.h);
        this.h = new x(getContext());
        addView(this.h, 0);
        a(this.h, i, com.chartboost.sdk.Libraries.d.a(jSONObject, "assets", "icon"));
        this.h.a(this.i);
        this.h.a(0.16666667f);
        b();
    }

    private void a(be beVar, int i, JSONObject jSONObject) {
        if (jSONObject != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            this.c.f.a.k.a(jSONObject.optString("url"), jSONObject.optString("checksum"), null, beVar, bundle);
        }
    }

    protected void b() {
        int iA = CBUtility.a(com.chartboost.sdk.f.a(getContext()) ? 14 : 7, getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(a() - (iA * 2), a() - (iA * 2));
        layoutParams.setMargins(iA, iA, iA, iA);
        this.g.setPadding(0, iA, 0, iA);
        this.a.setPadding((this.j.x * 2) + iA, this.j.y * 2, iA, 0);
        this.h.setLayoutParams(layoutParams);
        this.h.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    @Override // com.chartboost.sdk.impl.s
    public int a() {
        int i = 134;
        if (CBUtility.a().b()) {
            if (!com.chartboost.sdk.f.a(getContext())) {
                i = 75;
            }
        } else if (!com.chartboost.sdk.f.a(getContext())) {
            i = 77;
        }
        return CBUtility.a(i, getContext());
    }

    private int c() {
        int i = 74;
        if (CBUtility.a().b()) {
            if (!com.chartboost.sdk.f.a(getContext())) {
                i = 41;
            }
        } else if (!com.chartboost.sdk.f.a(getContext())) {
            i = 41;
        }
        return CBUtility.a(i, getContext());
    }
}
