package org.anddev.andengine.opengl.view;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;

/* JADX INFO: loaded from: classes.dex */
abstract class BaseConfigChooser implements EGLConfigChooser {
    protected final int[] mConfigSpec;

    abstract EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr);

    public BaseConfigChooser(int[] pConfigSpec) {
        this.mConfigSpec = pConfigSpec;
    }

    @Override // org.anddev.andengine.opengl.view.EGLConfigChooser
    public EGLConfig chooseConfig(EGL10 pEGL, EGLDisplay pEGLDisplay) {
        int[] num_config = new int[1];
        pEGL.eglChooseConfig(pEGLDisplay, this.mConfigSpec, null, 0, num_config);
        int numConfigs = num_config[0];
        if (numConfigs <= 0) {
            throw new IllegalArgumentException("No configs match configSpec");
        }
        EGLConfig[] configs = new EGLConfig[numConfigs];
        pEGL.eglChooseConfig(pEGLDisplay, this.mConfigSpec, configs, numConfigs, num_config);
        EGLConfig config = chooseConfig(pEGL, pEGLDisplay, configs);
        if (config == null) {
            throw new IllegalArgumentException("No config chosen");
        }
        return config;
    }
}
