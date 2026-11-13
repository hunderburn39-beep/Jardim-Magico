package org.anddev.andengine.engine.handler.collision;

import java.util.ArrayList;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.util.ListUtils;

/* JADX INFO: loaded from: classes.dex */
public class CollisionHandler implements IUpdateHandler {
    private final IShape mCheckShape;
    private final ICollisionCallback mCollisionCallback;
    private final ArrayList<? extends IShape> mTargetStaticEntities;

    public CollisionHandler(ICollisionCallback pCollisionCallback, IShape pCheckShape, IShape pTargetShape) throws IllegalArgumentException {
        this(pCollisionCallback, pCheckShape, (ArrayList<? extends IShape>) ListUtils.toList(pTargetShape));
    }

    public CollisionHandler(ICollisionCallback pCollisionCallback, IShape pCheckShape, ArrayList<? extends IShape> pTargetStaticEntities) throws IllegalArgumentException {
        if (pCollisionCallback == null) {
            throw new IllegalArgumentException("pCollisionCallback must not be null!");
        }
        if (pCheckShape == null) {
            throw new IllegalArgumentException("pCheckShape must not be null!");
        }
        if (pTargetStaticEntities == null) {
            throw new IllegalArgumentException("pTargetStaticEntities must not be null!");
        }
        this.mCollisionCallback = pCollisionCallback;
        this.mCheckShape = pCheckShape;
        this.mTargetStaticEntities = pTargetStaticEntities;
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void onUpdate(float pSecondsElapsed) {
        IShape checkShape = this.mCheckShape;
        ArrayList<? extends IShape> staticEntities = this.mTargetStaticEntities;
        int staticEntityCount = staticEntities.size();
        for (int i = 0; i < staticEntityCount; i++) {
            if (checkShape.collidesWith(staticEntities.get(i))) {
                boolean proceed = this.mCollisionCallback.onCollision(checkShape, staticEntities.get(i));
                if (!proceed) {
                    return;
                }
            }
        }
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
    }
}
