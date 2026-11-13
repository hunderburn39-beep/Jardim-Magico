package org.anddev.andengine.collision;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.shape.RectangularShape;
import org.anddev.andengine.util.MathUtils;

/* JADX INFO: loaded from: classes.dex */
public class RectangularShapeCollisionChecker extends ShapeCollisionChecker {
    private static final int LINE_VERTEX_COUNT = 2;
    private static final int RECTANGULARSHAPE_VERTEX_COUNT = 4;
    private static final float[] VERTICES_CONTAINS_TMP = new float[8];
    private static final float[] VERTICES_COLLISION_TMP_A = new float[8];
    private static final float[] VERTICES_COLLISION_TMP_B = new float[8];

    public static boolean checkContains(RectangularShape pRectangularShape, float pX, float pY) {
        fillVertices(pRectangularShape, VERTICES_CONTAINS_TMP);
        return ShapeCollisionChecker.checkContains(VERTICES_CONTAINS_TMP, 8, pX, pY);
    }

    public static boolean isVisible(Camera pCamera, RectangularShape pRectangularShape) {
        fillVertices(pCamera, VERTICES_COLLISION_TMP_A);
        fillVertices(pRectangularShape, VERTICES_COLLISION_TMP_B);
        return ShapeCollisionChecker.checkCollision(8, VERTICES_COLLISION_TMP_A, 8, VERTICES_COLLISION_TMP_B);
    }

    public static boolean isVisible(Camera pCamera, Line pLine) {
        fillVertices(pCamera, VERTICES_COLLISION_TMP_A);
        LineCollisionChecker.fillVertices(pLine, VERTICES_COLLISION_TMP_B);
        return ShapeCollisionChecker.checkCollision(8, VERTICES_COLLISION_TMP_A, 4, VERTICES_COLLISION_TMP_B);
    }

    public static boolean checkCollision(RectangularShape pRectangularShapeA, RectangularShape pRectangularShapeB) {
        fillVertices(pRectangularShapeA, VERTICES_COLLISION_TMP_A);
        fillVertices(pRectangularShapeB, VERTICES_COLLISION_TMP_B);
        return ShapeCollisionChecker.checkCollision(8, VERTICES_COLLISION_TMP_A, 8, VERTICES_COLLISION_TMP_B);
    }

    public static boolean checkCollision(RectangularShape pRectangularShape, Line pLine) {
        fillVertices(pRectangularShape, VERTICES_COLLISION_TMP_A);
        LineCollisionChecker.fillVertices(pLine, VERTICES_COLLISION_TMP_B);
        return ShapeCollisionChecker.checkCollision(8, VERTICES_COLLISION_TMP_A, 4, VERTICES_COLLISION_TMP_B);
    }

    public static void fillVertices(RectangularShape pRectangularShape, float[] pVertices) {
        float right = pRectangularShape.getWidth();
        float bottom = pRectangularShape.getHeight();
        pVertices[0] = 0.0f;
        pVertices[1] = 0.0f;
        pVertices[2] = right;
        pVertices[3] = 0.0f;
        pVertices[4] = right;
        pVertices[5] = bottom;
        pVertices[6] = 0.0f;
        pVertices[7] = bottom;
        pRectangularShape.getLocalToSceneTransformation().transform(pVertices);
    }

    private static void fillVertices(Camera pCamera, float[] pVertices) {
        pVertices[0] = pCamera.getMinX();
        pVertices[1] = pCamera.getMinY();
        pVertices[2] = pCamera.getMaxX();
        pVertices[3] = pCamera.getMinY();
        pVertices[4] = pCamera.getMaxX();
        pVertices[5] = pCamera.getMaxY();
        pVertices[6] = pCamera.getMinX();
        pVertices[7] = pCamera.getMaxY();
        MathUtils.rotateAroundCenter(pVertices, pCamera.getRotation(), pCamera.getCenterX(), pCamera.getCenterY());
    }
}
