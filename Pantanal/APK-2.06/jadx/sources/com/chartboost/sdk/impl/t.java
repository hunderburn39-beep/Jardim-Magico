package com.chartboost.sdk.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.chartboost.sdk.Libraries.CBUtility;
import com.tapjoy.TJAdUnitConstants;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"ViewConstructor"})
public class t extends s {
    private final z a;
    private ImageView b;

    public t(z zVar, Context context) {
        super(context);
        this.a = zVar;
        this.b = new ImageView(context);
        addView(this.b, new LinearLayout.LayoutParams(-1, -1));
    }

    @Override // com.chartboost.sdk.impl.s
    public void a(JSONObject jSONObject, int i) {
        boolean zA = CBUtility.a().a();
        String[] strArr = new String[2];
        strArr[0] = "assets";
        strArr[1] = zA ? "portrait" : TJAdUnitConstants.String.LANDSCAPE;
        JSONObject jSONObjectA = com.chartboost.sdk.Libraries.d.a(jSONObject, strArr);
        if (jSONObjectA != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            this.a.f.a.k.a(jSONObjectA.optString("url"), jSONObjectA.optString("checksum"), null, this.b, bundle);
        }
    }

    @Override // com.chartboost.sdk.impl.s
    public int a() {
        return CBUtility.a(110, getContext());
    }
}
