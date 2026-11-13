package org.anddev.andengine.util.path.astar;

import org.anddev.andengine.util.path.ITiledMap;

/* JADX INFO: loaded from: classes.dex */
public class ManhattanHeuristic<T> implements IAStarHeuristic<T> {
    @Override // org.anddev.andengine.util.path.astar.IAStarHeuristic
    public float getExpectedRestCost(ITiledMap<T> pTiledMap, T pEntity, int pTileFromX, int pTileFromY, int pTileToX, int pTileToY) {
        return Math.abs(pTileFromX - pTileToX) + Math.abs(pTileFromY - pTileToY);
    }
}
