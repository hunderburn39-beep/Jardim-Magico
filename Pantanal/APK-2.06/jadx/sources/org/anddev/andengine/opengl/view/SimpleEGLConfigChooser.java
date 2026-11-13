package org.anddev.andengine.opengl.view;

/* JADX INFO: loaded from: classes.dex */
class SimpleEGLConfigChooser extends ComponentSizeChooser {
    public SimpleEGLConfigChooser(boolean pWithDepthBuffer) {
        super(4, 4, 4, 0, pWithDepthBuffer ? 16 : 0, 0);
        this.mRedSize = 5;
        this.mGreenSize = 6;
        this.mBlueSize = 5;
    }
}
