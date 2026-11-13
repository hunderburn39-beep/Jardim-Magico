package com.tapjoy.internal;

import android.graphics.PointF;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.tapjoy.TJAdUnitConstants;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class gw {
    public static final bm d = new bm() { // from class: com.tapjoy.internal.gw.1
        @Override // com.tapjoy.internal.bm
        public final /* synthetic */ Object a(br brVar) {
            return new gw(brVar);
        }
    };
    public af a;
    public PointF b;
    public ArrayList c = new ArrayList();

    public gw(br brVar) {
        this.a = af.UNSPECIFIED;
        brVar.h();
        while (brVar.j()) {
            String strL = brVar.l();
            if (TJAdUnitConstants.String.BUTTONS.equals(strL)) {
                if (brVar.k() == bw.BEGIN_ARRAY) {
                    brVar.a(this.c, gv.n);
                } else {
                    brVar.s();
                }
            } else if ("window_aspect_ratio".equals(strL)) {
                if (brVar.a()) {
                    PointF pointF = new PointF();
                    brVar.h();
                    while (brVar.j()) {
                        String strL2 = brVar.l();
                        if ("width".equals(strL2)) {
                            pointF.x = (float) brVar.p();
                        } else if ("height".equals(strL2)) {
                            pointF.y = (float) brVar.p();
                        } else {
                            brVar.s();
                        }
                    }
                    brVar.i();
                    if (pointF.x != BitmapDescriptorFactory.HUE_RED && pointF.y != BitmapDescriptorFactory.HUE_RED) {
                        this.b = pointF;
                    }
                } else {
                    brVar.s();
                }
            } else if ("orientation".equals(strL)) {
                String strM = brVar.m();
                if (TJAdUnitConstants.String.LANDSCAPE.equals(strM)) {
                    this.a = af.LANDSCAPE;
                } else if ("portrait".equals(strM)) {
                    this.a = af.PORTRAIT;
                }
            } else {
                brVar.s();
            }
        }
        brVar.i();
    }
}
