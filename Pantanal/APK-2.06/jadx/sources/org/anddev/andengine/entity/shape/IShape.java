package org.anddev.andengine.entity.shape;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.scene.Scene;

/* JADX INFO: loaded from: classes.dex */
public interface IShape extends IEntity, Scene.ITouchArea {
    boolean collidesWith(IShape iShape);

    float getBaseHeight();

    float getBaseWidth();

    float getHeight();

    float getHeightScaled();

    float getWidth();

    float getWidthScaled();

    boolean isCullingEnabled();

    void setBlendFunction(int i, int i2);

    void setCullingEnabled(boolean z);
}
