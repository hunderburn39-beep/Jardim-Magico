package org.anddev.andengine.collision;

import org.anddev.andengine.entity.primitive.Line;

/* JADX INFO: loaded from: classes.dex */
public class LineCollisionChecker extends ShapeCollisionChecker {
    public static boolean checkLineCollision(float pX1, float pY1, float pX2, float pY2, float pX3, float pY3, float pX4, float pY4) {
        if (BaseCollisionChecker.relativeCCW(pX1, pY1, pX2, pY2, pX4, pY4) * BaseCollisionChecker.relativeCCW(pX1, pY1, pX2, pY2, pX3, pY3) <= 0) {
            if (BaseCollisionChecker.relativeCCW(pX3, pY3, pX4, pY4, pX2, pY2) * BaseCollisionChecker.relativeCCW(pX3, pY3, pX4, pY4, pX1, pY1) <= 0) {
                return true;
            }
        }
        return false;
    }

    public static void fillVertices(Line pLine, float[] pVertices) {
        pVertices[0] = 0.0f;
        pVertices[1] = 0.0f;
        pVertices[2] = pLine.getX2() - pLine.getX1();
        pVertices[3] = pLine.getY2() - pLine.getY1();
        pLine.getLocalToSceneTransformation().transform(pVertices);
    }
}
