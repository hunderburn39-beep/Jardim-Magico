package com.google.android.gms.internal;

import android.content.Context;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ae implements ad {
    private final dz lC;

    public ae(Context context, dx dxVar) {
        this.lC = dz.a(context, new ak(), false, false, null, dxVar);
    }

    @Override // com.google.android.gms.internal.ad
    public void a(final ad.a aVar) {
        this.lC.bI().a(new ea.a() { // from class: com.google.android.gms.internal.ae.1
            @Override // com.google.android.gms.internal.ea.a
            public void a(dz dzVar) {
                aVar.ay();
            }
        });
    }

    @Override // com.google.android.gms.internal.ad
    public void a(String str, bb bbVar) {
        this.lC.bI().a(str, bbVar);
    }

    @Override // com.google.android.gms.internal.ad
    public void a(String str, JSONObject jSONObject) {
        this.lC.a(str, jSONObject);
    }

    @Override // com.google.android.gms.internal.ad
    public void d(String str) {
        this.lC.loadUrl(str);
    }

    @Override // com.google.android.gms.internal.ad
    public void e(String str) {
        this.lC.bI().a(str, (bb) null);
    }
}
