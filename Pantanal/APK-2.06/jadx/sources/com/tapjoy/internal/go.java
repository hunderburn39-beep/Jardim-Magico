package com.tapjoy.internal;

import android.graphics.Rect;
import com.tapjoy.TapjoyConstants;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
public final class go {
    public static final bm h = new bm() { // from class: com.tapjoy.internal.go.1
        @Override // com.tapjoy.internal.bm
        public final /* synthetic */ Object a(br brVar) {
            gj gjVarA = null;
            boolean zN = false;
            String strM = "";
            brVar.h();
            String strB = null;
            String strB2 = null;
            String strM2 = null;
            Rect rect = null;
            while (brVar.j()) {
                String strL = brVar.l();
                if ("region".equals(strL)) {
                    rect = (Rect) bn.b.a(brVar);
                } else if (TMXConstants.TAG_PROPERTY_ATTRIBUTE_VALUE.equals(strL)) {
                    strM2 = brVar.m();
                } else if (TapjoyConstants.TJC_FULLSCREEN_AD_DISMISS_URL.equals(strL)) {
                    zN = brVar.n();
                } else if ("url".equals(strL)) {
                    strM = brVar.m();
                } else if (TapjoyConstants.TJC_REDIRECT_URL.equals(strL)) {
                    strB2 = brVar.b();
                } else if ("ad_content".equals(strL)) {
                    strB = brVar.b();
                } else if (gj.a(strL)) {
                    gjVarA = gj.a(strL, brVar);
                } else {
                    brVar.s();
                }
            }
            brVar.i();
            return new go(rect, strM2, zN, strM, strB2, strB, gjVarA);
        }
    };
    public final Rect a;
    public final String b;
    public final boolean c;
    public final String d;
    public String e;
    public String f;
    public final fg g;

    go(Rect rect, String str, boolean z, String str2, String str3, String str4, fg fgVar) {
        this.a = rect;
        this.b = str;
        this.c = z;
        this.d = str2;
        this.e = str3;
        this.f = str4;
        this.g = fgVar;
    }
}
