package org.anddev.andengine.engine.options;

/* JADX INFO: loaded from: classes.dex */
public class RenderOptions {
    private boolean mDisableExtensionVertexBufferObjects = false;
    private boolean mDisableExtensionDrawTexture = false;

    public boolean isDisableExtensionVertexBufferObjects() {
        return this.mDisableExtensionVertexBufferObjects;
    }

    public RenderOptions enableExtensionVertexBufferObjects() {
        return setDisableExtensionVertexBufferObjects(false);
    }

    public RenderOptions disableExtensionVertexBufferObjects() {
        return setDisableExtensionVertexBufferObjects(true);
    }

    public RenderOptions setDisableExtensionVertexBufferObjects(boolean pDisableExtensionVertexBufferObjects) {
        this.mDisableExtensionVertexBufferObjects = pDisableExtensionVertexBufferObjects;
        return this;
    }

    public boolean isDisableExtensionDrawTexture() {
        return this.mDisableExtensionDrawTexture;
    }

    public RenderOptions enableExtensionDrawTexture() {
        return setDisableExtensionDrawTexture(false);
    }

    public RenderOptions disableExtensionDrawTexture() {
        return setDisableExtensionDrawTexture(true);
    }

    public RenderOptions setDisableExtensionDrawTexture(boolean pDisableExtensionDrawTexture) {
        this.mDisableExtensionDrawTexture = pDisableExtensionDrawTexture;
        return this;
    }
}
