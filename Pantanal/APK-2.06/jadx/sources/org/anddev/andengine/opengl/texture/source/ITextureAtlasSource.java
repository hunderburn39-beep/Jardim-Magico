package org.anddev.andengine.opengl.texture.source;

import org.anddev.andengine.util.modifier.IModifier;

/* JADX INFO: loaded from: classes.dex */
public interface ITextureAtlasSource {
    ITextureAtlasSource deepCopy() throws IModifier.DeepCopyNotSupportedException;

    int getHeight();

    int getTexturePositionX();

    int getTexturePositionY();

    int getWidth();

    void setTexturePositionX(int i);

    void setTexturePositionY(int i);
}
