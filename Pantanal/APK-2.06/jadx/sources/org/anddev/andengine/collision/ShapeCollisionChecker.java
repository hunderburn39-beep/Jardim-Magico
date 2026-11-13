package org.anddev.andengine.collision;

/* JADX INFO: loaded from: classes.dex */
public class ShapeCollisionChecker extends BaseCollisionChecker {
    public static boolean checkCollision(int pVerticesALength, float[] pVerticesA, int pVerticesBLength, float[] pVerticesB) {
        for (int a = pVerticesALength - 4; a >= 0; a -= 2) {
            if (checkCollisionSub(a, a + 2, pVerticesA, pVerticesB, pVerticesBLength)) {
                return true;
            }
        }
        return checkCollisionSub(pVerticesALength + (-2), 0, pVerticesA, pVerticesB, pVerticesBLength) || checkContains(pVerticesA, pVerticesALength, pVerticesB[0], pVerticesB[1]) || checkContains(pVerticesB, pVerticesBLength, pVerticesA[0], pVerticesA[1]);
    }

    private static boolean checkCollisionSub(int pVertexIndexA1, int pVertexIndexA2, float[] pVerticesA, float[] pVerticesB, int pVerticesBLength) {
        float vertexA1X = pVerticesA[pVertexIndexA1 + 0];
        float vertexA1Y = pVerticesA[pVertexIndexA1 + 1];
        float vertexA2X = pVerticesA[pVertexIndexA2 + 0];
        float vertexA2Y = pVerticesA[pVertexIndexA2 + 1];
        for (int b = pVerticesBLength - 4; b >= 0; b -= 2) {
            if (LineCollisionChecker.checkLineCollision(vertexA1X, vertexA1Y, vertexA2X, vertexA2Y, pVerticesB[b + 0], pVerticesB[b + 1], pVerticesB[b + 2 + 0], pVerticesB[b + 2 + 1])) {
                return true;
            }
        }
        return LineCollisionChecker.checkLineCollision(vertexA1X, vertexA1Y, vertexA2X, vertexA2Y, pVerticesB[pVerticesBLength + (-2)], pVerticesB[pVerticesBLength + (-1)], pVerticesB[0], pVerticesB[1]);
    }

    public static boolean checkContains(float[] pVertices, int pVerticesLength, float pX, float pY) {
        int edgeResultSum = 0;
        for (int i = pVerticesLength - 4; i >= 0; i -= 2) {
            int edgeResult = BaseCollisionChecker.relativeCCW(pVertices[i], pVertices[i + 1], pVertices[i + 2], pVertices[i + 3], pX, pY);
            if (edgeResult == 0) {
                return true;
            }
            edgeResultSum += edgeResult;
        }
        int edgeResult2 = BaseCollisionChecker.relativeCCW(pVertices[pVerticesLength - 2], pVertices[pVerticesLength - 1], pVertices[0], pVertices[1], pX, pY);
        if (edgeResult2 == 0) {
            return true;
        }
        int edgeResultSum2 = edgeResultSum + edgeResult2;
        int vertexCount = pVerticesLength / 2;
        return edgeResultSum2 == vertexCount || edgeResultSum2 == (-vertexCount);
    }
}
