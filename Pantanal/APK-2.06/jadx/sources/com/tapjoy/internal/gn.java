package com.tapjoy.internal;

import com.tapjoy.TapjoyConstants;
import java.util.ArrayList;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class gn extends gm {
    public static final bm d = new bm() { // from class: com.tapjoy.internal.gn.1
        @Override // com.tapjoy.internal.bm
        public final /* synthetic */ Object a(br brVar) {
            return new gn(brVar);
        }
    };
    public ArrayList a = new ArrayList();
    public Map b;
    public float c;

    public gn(br brVar) {
        brVar.h();
        String strB = null;
        String strB2 = null;
        while (brVar.j()) {
            String strL = brVar.l();
            if ("layouts".equals(strL)) {
                brVar.a(this.a, gw.d);
            } else if ("meta".equals(strL)) {
                this.b = brVar.d();
            } else if ("max_show_time".equals(strL)) {
                this.c = (float) brVar.p();
            } else if ("ad_content".equals(strL)) {
                strB2 = brVar.b();
            } else if (TapjoyConstants.TJC_REDIRECT_URL.equals(strL)) {
                strB = brVar.b();
            } else {
                brVar.s();
            }
        }
        brVar.i();
        if (this.a != null) {
            for (gw gwVar : this.a) {
                if (gwVar.c != null) {
                    for (gv gvVar : gwVar.c) {
                        if (gvVar.i == null) {
                            gvVar.i = strB2;
                        }
                        if (gvVar.h == null) {
                            gvVar.h = strB;
                        }
                    }
                }
            }
        }
    }
}
