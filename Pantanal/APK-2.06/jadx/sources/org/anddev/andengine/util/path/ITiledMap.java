package org.anddev.andengine.util.path;

/* JADX INFO: loaded from: classes.dex */
public interface ITiledMap<T> {
    float getStepCost(T t, int i, int i2, int i3, int i4);

    int getTileColumns();

    int getTileRows();

    boolean isTileBlocked(T t, int i, int i2);

    void onTileVisitedByPathFinder(int i, int i2);
}
