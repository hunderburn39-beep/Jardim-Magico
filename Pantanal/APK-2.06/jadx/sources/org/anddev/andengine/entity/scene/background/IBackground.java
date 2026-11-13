package org.anddev.andengine.entity.scene.background;

import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.opengl.IDrawable;
import org.anddev.andengine.util.modifier.IModifier;

/* JADX INFO: loaded from: classes.dex */
public interface IBackground extends IDrawable, IUpdateHandler {
    void addBackgroundModifier(IModifier<IBackground> iModifier);

    void clearBackgroundModifiers();

    boolean removeBackgroundModifier(IModifier<IBackground> iModifier);

    void setColor(float f, float f2, float f3);

    void setColor(float f, float f2, float f3, float f4);
}
