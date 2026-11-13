package com.tapjoy.internal;

import android.graphics.Point;
import android.graphics.Rect;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
public final class bn {
    public static final bm a = new bm() { // from class: com.tapjoy.internal.bn.1
        @Override // com.tapjoy.internal.bm
        public final /* synthetic */ Object a(br brVar) {
            Point point = new Point();
            brVar.h();
            while (brVar.j()) {
                String strL = brVar.l();
                if (TMXConstants.TAG_OBJECT_ATTRIBUTE_X.equals(strL)) {
                    point.x = brVar.r();
                } else if (TMXConstants.TAG_OBJECT_ATTRIBUTE_Y.equals(strL)) {
                    point.y = brVar.r();
                } else {
                    brVar.s();
                }
            }
            brVar.i();
            return point;
        }
    };
    public static final bm b = new bm() { // from class: com.tapjoy.internal.bn.2
        @Override // com.tapjoy.internal.bm
        public final /* synthetic */ Object a(br brVar) {
            Rect rect = new Rect();
            switch (AnonymousClass3.a[brVar.k().ordinal()]) {
                case 1:
                    brVar.f();
                    rect.left = brVar.r();
                    rect.top = brVar.r();
                    rect.right = brVar.r();
                    rect.bottom = brVar.r();
                    while (brVar.j()) {
                        brVar.s();
                    }
                    brVar.g();
                    return rect;
                case 2:
                    brVar.h();
                    while (brVar.j()) {
                        String strL = brVar.l();
                        if ("left".equals(strL)) {
                            rect.left = brVar.r();
                        } else if ("top".equals(strL)) {
                            rect.top = brVar.r();
                        } else if ("right".equals(strL)) {
                            rect.right = brVar.r();
                        } else if ("bottom".equals(strL)) {
                            rect.bottom = brVar.r();
                        } else {
                            brVar.s();
                        }
                    }
                    brVar.i();
                    return rect;
                default:
                    throw new IllegalStateException("Unexpected token: " + brVar.k());
            }
        }
    };

    /* JADX INFO: renamed from: com.tapjoy.internal.bn$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] a = new int[bw.values().length];

        static {
            try {
                a[bw.BEGIN_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[bw.BEGIN_OBJECT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }
}
