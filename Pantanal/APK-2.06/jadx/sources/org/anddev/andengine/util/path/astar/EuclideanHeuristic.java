package org.anddev.andengine.util.path.astar;

import android.util.FloatMath;
import org.anddev.andengine.util.path.ITiledMap;

/* JADX INFO: loaded from: classes.dex */
public class EuclideanHeuristic<T> implements IAStarHeuristic<T> {
    @Override // org.anddev.andengine.util.path.astar.IAStarHeuristic
    public float getExpectedRestCost(ITiledMap<T> pTileMap, T pEntity, int pTileFromX, int pTileFromY, int pTileToX, int pTileToY) {
        float dX = pTileToX - pTileFromX;
        float dY = pTileToY - pTileFromY;
        return FloatMath.sqrt((dX * dX) + (dY * dY));
    }
}
