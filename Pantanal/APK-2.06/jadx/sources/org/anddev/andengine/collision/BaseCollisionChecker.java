package org.anddev.andengine.collision;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* JADX INFO: loaded from: classes.dex */
public class BaseCollisionChecker {
    public static boolean checkAxisAlignedRectangleCollision(float pLeftA, float pTopA, float pRightA, float pBottomA, float pLeftB, float pTopB, float pRightB, float pBottomB) {
        return pLeftA < pRightB && pLeftB < pRightA && pTopA < pBottomB && pTopB < pBottomA;
    }

    public static int relativeCCW(float pX1, float pY1, float pX2, float pY2, float pPX, float pPY) {
        float pX3 = pX2 - pX1;
        float pY3 = pY2 - pY1;
        float pPX2 = pPX - pX1;
        float pPY2 = pPY - pY1;
        float ccw = (pPX2 * pY3) - (pPY2 * pX3);
        if (ccw == BitmapDescriptorFactory.HUE_RED) {
            ccw = (pPX2 * pX3) + (pPY2 * pY3);
            if (ccw > BitmapDescriptorFactory.HUE_RED) {
                ccw = ((pPX2 - pX3) * pX3) + ((pPY2 - pY3) * pY3);
                if (ccw < BitmapDescriptorFactory.HUE_RED) {
                    ccw = BitmapDescriptorFactory.HUE_RED;
                }
            }
        }
        if (ccw < BitmapDescriptorFactory.HUE_RED) {
            return -1;
        }
        return ccw > BitmapDescriptorFactory.HUE_RED ? 1 : 0;
    }
}
