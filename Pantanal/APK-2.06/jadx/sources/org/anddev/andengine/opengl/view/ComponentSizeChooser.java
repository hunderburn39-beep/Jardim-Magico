package org.anddev.andengine.opengl.view;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;

/* JADX INFO: loaded from: classes.dex */
public class ComponentSizeChooser extends BaseConfigChooser {
    protected int mAlphaSize;
    protected int mBlueSize;
    protected int mDepthSize;
    protected int mGreenSize;
    protected int mRedSize;
    protected int mStencilSize;
    private final int[] mValue;

    @Override // org.anddev.andengine.opengl.view.BaseConfigChooser, org.anddev.andengine.opengl.view.EGLConfigChooser
    public /* bridge */ /* synthetic */ EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay) {
        return super.chooseConfig(egl10, eGLDisplay);
    }

    public ComponentSizeChooser(int pRedSize, int pGreenSize, int pBlueSize, int pAlphaSize, int pDepthSize, int pStencilSize) {
        super(new int[]{12324, pRedSize, 12323, pGreenSize, 12322, pBlueSize, 12321, pAlphaSize, 12325, pDepthSize, 12326, pStencilSize, 12344});
        this.mValue = new int[1];
        this.mRedSize = pRedSize;
        this.mGreenSize = pGreenSize;
        this.mBlueSize = pBlueSize;
        this.mAlphaSize = pAlphaSize;
        this.mDepthSize = pDepthSize;
        this.mStencilSize = pStencilSize;
    }

    @Override // org.anddev.andengine.opengl.view.BaseConfigChooser
    public EGLConfig chooseConfig(EGL10 pEGL, EGLDisplay pEGLDisplay, EGLConfig[] pEGLConfigs) {
        EGLConfig closestConfig = null;
        int closestDistance = 1000;
        int length = pEGLConfigs.length;
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < length) {
                EGLConfig config = pEGLConfigs[i2];
                int r = findConfigAttrib(pEGL, pEGLDisplay, config, 12324, 0);
                int g = findConfigAttrib(pEGL, pEGLDisplay, config, 12323, 0);
                int b = findConfigAttrib(pEGL, pEGLDisplay, config, 12322, 0);
                int a = findConfigAttrib(pEGL, pEGLDisplay, config, 12321, 0);
                int d = findConfigAttrib(pEGL, pEGLDisplay, config, 12325, 0);
                int s = findConfigAttrib(pEGL, pEGLDisplay, config, 12326, 0);
                int distance = Math.abs(r - this.mRedSize) + Math.abs(g - this.mGreenSize) + Math.abs(b - this.mBlueSize) + Math.abs(a - this.mAlphaSize) + Math.abs(d - this.mDepthSize) + Math.abs(s - this.mStencilSize);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestConfig = config;
                }
                i = i2 + 1;
            } else {
                return closestConfig;
            }
        }
    }

    private int findConfigAttrib(EGL10 pEGL, EGLDisplay pEGLDisplay, EGLConfig pEGLConfig, int pAttribute, int pDefaultValue) {
        if (pEGL.eglGetConfigAttrib(pEGLDisplay, pEGLConfig, pAttribute, this.mValue)) {
            int pDefaultValue2 = this.mValue[0];
            return pDefaultValue2;
        }
        return pDefaultValue;
    }
}
