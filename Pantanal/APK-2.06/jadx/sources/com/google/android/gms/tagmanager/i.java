package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
class i extends df {
    private static final String ID = com.google.android.gms.internal.a.ARBITRARY_PIXEL.toString();
    private static final String URL = com.google.android.gms.internal.b.URL.toString();
    private static final String WC = com.google.android.gms.internal.b.ADDITIONAL_PARAMS.toString();
    private static final String WD = com.google.android.gms.internal.b.UNREPEATABLE.toString();
    static final String WE = "gtm_" + ID + "_unrepeatable";
    private static final Set<String> WF = new HashSet();
    private final a WG;
    private final Context mContext;

    public interface a {
        aq jY();
    }

    public i(final Context context) {
        this(context, new a() { // from class: com.google.android.gms.tagmanager.i.1
            @Override // com.google.android.gms.tagmanager.i.a
            public aq jY() {
                return y.F(context);
            }
        });
    }

    i(Context context, a aVar) {
        super(ID, URL);
        this.WG = aVar;
        this.mContext = context;
    }

    private synchronized boolean bj(String str) {
        boolean z = true;
        synchronized (this) {
            if (!bl(str)) {
                if (bk(str)) {
                    WF.add(str);
                } else {
                    z = false;
                }
            }
        }
        return z;
    }

    boolean bk(String str) {
        return this.mContext.getSharedPreferences(WE, 0).contains(str);
    }

    boolean bl(String str) {
        return WF.contains(str);
    }

    @Override // com.google.android.gms.tagmanager.df
    public void z(Map<String, com.google.android.gms.internal.d.a> map) {
        String strJ = map.get(WD) != null ? dh.j(map.get(WD)) : null;
        if (strJ == null || !bj(strJ)) {
            Uri.Builder builderBuildUpon = Uri.parse(dh.j(map.get(URL))).buildUpon();
            com.google.android.gms.internal.d.a aVar = map.get(WC);
            if (aVar != null) {
                Object objO = dh.o(aVar);
                if (!(objO instanceof List)) {
                    bh.w("ArbitraryPixel: additional params not a list: not sending partial hit: " + builderBuildUpon.build().toString());
                    return;
                }
                for (Object obj : (List) objO) {
                    if (!(obj instanceof Map)) {
                        bh.w("ArbitraryPixel: additional params contains non-map: not sending partial hit: " + builderBuildUpon.build().toString());
                        return;
                    }
                    for (Map.Entry entry : ((Map) obj).entrySet()) {
                        builderBuildUpon.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
                    }
                }
            }
            String string = builderBuildUpon.build().toString();
            this.WG.jY().bz(string);
            bh.y("ArbitraryPixel: url = " + string);
            if (strJ != null) {
                synchronized (i.class) {
                    WF.add(strJ);
                    cy.a(this.mContext, WE, strJ, "true");
                }
            }
        }
    }
}
