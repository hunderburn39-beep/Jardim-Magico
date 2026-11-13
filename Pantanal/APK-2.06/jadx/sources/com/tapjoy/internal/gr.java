package com.tapjoy.internal;

import android.graphics.Point;
import com.tapjoy.TJAdUnitConstants;
import java.net.URL;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
public final class gr {
    public static final bm d = new bm() { // from class: com.tapjoy.internal.gr.1
        private static Point b(br brVar) {
            Point point = null;
            brVar.h();
            while (brVar.j()) {
                if ("offset".equals(brVar.l())) {
                    brVar.h();
                    int iR = 0;
                    int iR2 = 0;
                    while (brVar.j()) {
                        String strL = brVar.l();
                        if (TMXConstants.TAG_OBJECT_ATTRIBUTE_X.equals(strL)) {
                            iR2 = brVar.r();
                        } else if (TMXConstants.TAG_OBJECT_ATTRIBUTE_Y.equals(strL)) {
                            iR = brVar.r();
                        } else {
                            brVar.s();
                        }
                    }
                    brVar.i();
                    point = new Point(iR2, iR);
                } else {
                    brVar.s();
                }
            }
            brVar.i();
            return point;
        }

        @Override // com.tapjoy.internal.bm
        public final /* synthetic */ Object a(br brVar) {
            gt gtVar = null;
            brVar.h();
            Point pointB = null;
            Point pointB2 = null;
            while (brVar.j()) {
                String strL = brVar.l();
                if (TMXConstants.TAG_IMAGE.equals(strL)) {
                    String strM = brVar.m();
                    if (!cr.c(strM)) {
                        gtVar = new gt(new URL(strM));
                    }
                } else if (TJAdUnitConstants.String.LANDSCAPE.equals(strL)) {
                    pointB2 = b(brVar);
                } else if ("portrait".equals(strL)) {
                    pointB = b(brVar);
                } else {
                    brVar.s();
                }
            }
            brVar.i();
            return new gr(gtVar, pointB2, pointB);
        }
    };
    public final gt a;
    public final Point b;
    public final Point c;

    public gr(gt gtVar, Point point, Point point2) {
        this.a = gtVar;
        this.b = point;
        this.c = point2;
    }
}
