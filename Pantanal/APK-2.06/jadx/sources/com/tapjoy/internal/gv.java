package com.tapjoy.internal;

import com.tapjoy.TapjoyConstants;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
public final class gv {
    public static final bm n = new bm() { // from class: com.tapjoy.internal.gv.1
        @Override // com.tapjoy.internal.bm
        public final /* synthetic */ Object a(br brVar) {
            return new gv(brVar);
        }
    };
    public gx a;
    public gx b;
    public gx c;
    public gx d;
    public int e;
    public int f;
    public String g;
    public String h;
    public String i;
    public boolean j;
    public String k;
    public gt l;
    public gt m;

    public gv(br brVar) {
        this.e = 9;
        this.f = 10;
        this.j = false;
        brVar.h();
        while (brVar.j()) {
            String strL = brVar.l();
            if (TMXConstants.TAG_OBJECT_ATTRIBUTE_X.equals(strL)) {
                this.a = gx.a(brVar.m());
            } else if (TMXConstants.TAG_OBJECT_ATTRIBUTE_Y.equals(strL)) {
                this.b = gx.a(brVar.m());
            } else if ("width".equals(strL)) {
                this.c = gx.a(brVar.m());
            } else if ("height".equals(strL)) {
                this.d = gx.a(brVar.m());
            } else if ("url".equals(strL)) {
                this.g = brVar.m();
            } else if (TapjoyConstants.TJC_REDIRECT_URL.equals(strL)) {
                this.h = brVar.m();
            } else if ("ad_content".equals(strL)) {
                this.i = brVar.m();
            } else if (TapjoyConstants.TJC_FULLSCREEN_AD_DISMISS_URL.equals(strL)) {
                this.j = brVar.n();
            } else if (TMXConstants.TAG_PROPERTY_ATTRIBUTE_VALUE.equals(strL)) {
                this.k = brVar.m();
            } else if (TMXConstants.TAG_IMAGE.equals(strL)) {
                this.l = (gt) gt.c.a(brVar);
            } else if ("image_clicked".equals(strL)) {
                this.m = (gt) gt.c.a(brVar);
            } else if ("align".equals(strL)) {
                String strM = brVar.m();
                if ("left".equals(strM)) {
                    this.e = 9;
                } else if ("right".equals(strM)) {
                    this.e = 11;
                } else if ("center".equals(strM)) {
                    this.e = 14;
                } else {
                    brVar.s();
                }
            } else if ("valign".equals(strL)) {
                String strM2 = brVar.m();
                if ("top".equals(strM2)) {
                    this.f = 10;
                } else if ("middle".equals(strM2)) {
                    this.f = 15;
                } else if ("bottom".equals(strM2)) {
                    this.f = 12;
                } else {
                    brVar.s();
                }
            } else {
                brVar.s();
            }
        }
        brVar.i();
    }
}
