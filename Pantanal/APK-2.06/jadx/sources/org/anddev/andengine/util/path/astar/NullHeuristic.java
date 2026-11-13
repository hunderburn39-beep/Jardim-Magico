package org.anddev.andengine.util.path.astar;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.anddev.andengine.util.path.ITiledMap;

/* JADX INFO: loaded from: classes.dex */
public class NullHeuristic<T> implements IAStarHeuristic<T> {
    @Override // org.anddev.andengine.util.path.astar.IAStarHeuristic
    public float getExpectedRestCost(ITiledMap<T> pTiledMap, T pEntity, int pTileFromX, int pTileFromY, int pTileToX, int pTileToY) {
        return BitmapDescriptorFactory.HUE_RED;
    }
}
