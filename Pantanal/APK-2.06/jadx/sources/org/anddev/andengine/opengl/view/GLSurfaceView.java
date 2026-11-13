package org.anddev.andengine.opengl.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

/* JADX INFO: loaded from: classes.dex */
public class GLSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    public static final int DEBUG_CHECK_GL_ERROR = 1;
    public static final int DEBUG_LOG_GL_CALLS = 2;
    public static final int RENDERMODE_CONTINUOUSLY = 1;
    public static final int RENDERMODE_WHEN_DIRTY = 0;
    private static final Semaphore sEglSemaphore = new Semaphore(1);
    private int mDebugFlags;
    private EGLConfigChooser mEGLConfigChooser;
    private GLThread mGLThread;
    private GLWrapper mGLWrapper;
    private boolean mHasSurface;
    private int mRenderMode;
    private Renderer mRenderer;
    private int mSurfaceHeight;
    private int mSurfaceWidth;

    public interface Renderer {
        void onDrawFrame(GL10 gl10);

        void onSurfaceChanged(GL10 gl10, int i, int i2);

        void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig);
    }

    public GLSurfaceView(Context context) {
        super(context);
        init();
    }

    public GLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        holder.setType(2);
        this.mRenderMode = 1;
    }

    public void setGLWrapper(GLWrapper glWrapper) {
        this.mGLWrapper = glWrapper;
    }

    public void setDebugFlags(int debugFlags) {
        this.mDebugFlags = debugFlags;
    }

    public int getDebugFlags() {
        return this.mDebugFlags;
    }

    public void setRenderer(Renderer renderer) {
        if (this.mRenderer != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
        this.mRenderer = renderer;
    }

    public void setEGLConfigChooser(EGLConfigChooser configChooser) {
        if (this.mRenderer != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
        this.mEGLConfigChooser = configChooser;
    }

    public void setEGLConfigChooser(boolean needDepth) {
        setEGLConfigChooser(new SimpleEGLConfigChooser(needDepth));
    }

    public void setEGLConfigChooser(int redSize, int greenSize, int blueSize, int alphaSize, int depthSize, int stencilSize) {
        setEGLConfigChooser(new ComponentSizeChooser(redSize, greenSize, blueSize, alphaSize, depthSize, stencilSize));
    }

    public void setRenderMode(int renderMode) {
        this.mRenderMode = renderMode;
        if (this.mGLThread != null) {
            this.mGLThread.setRenderMode(renderMode);
        }
    }

    public int getRenderMode() {
        return this.mRenderMode;
    }

    public void requestRender() {
        this.mGLThread.requestRender();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder holder) {
        if (this.mGLThread != null) {
            this.mGLThread.surfaceCreated();
        }
        this.mHasSurface = true;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (this.mGLThread != null) {
            this.mGLThread.surfaceDestroyed();
        }
        this.mHasSurface = false;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (this.mGLThread != null) {
            this.mGLThread.onWindowResize(w, h);
        }
        this.mSurfaceWidth = w;
        this.mSurfaceHeight = h;
    }

    public void onPause() {
        this.mGLThread.onPause();
        this.mGLThread.requestExitAndWait();
        this.mGLThread = null;
    }

    public void onResume() {
        if (this.mEGLConfigChooser == null) {
            this.mEGLConfigChooser = new SimpleEGLConfigChooser(true);
        }
        this.mGLThread = new GLThread(this.mRenderer);
        this.mGLThread.start();
        this.mGLThread.setRenderMode(this.mRenderMode);
        if (this.mHasSurface) {
            this.mGLThread.surfaceCreated();
        }
        if (this.mSurfaceWidth > 0 && this.mSurfaceHeight > 0) {
            this.mGLThread.onWindowResize(this.mSurfaceWidth, this.mSurfaceHeight);
        }
        this.mGLThread.onResume();
    }

    public void queueEvent(Runnable r) {
        if (this.mGLThread != null) {
            this.mGLThread.queueEvent(r);
        }
    }

    class GLThread extends Thread {
        private EglHelper mEglHelper;
        private boolean mHasSurface;
        private boolean mPaused;
        private final Renderer mRenderer;
        private final ArrayList<Runnable> mEventQueue = new ArrayList<>();
        private boolean mDone = false;
        private int mWidth = 0;
        private int mHeight = 0;
        private boolean mRequestRender = true;
        private int mRenderMode = 1;
        private boolean mSizeChanged = true;

        GLThread(Renderer renderer) {
            this.mRenderer = renderer;
            setName("GLThread");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                try {
                    GLSurfaceView.sEglSemaphore.acquire();
                    try {
                        guardedRun();
                    } catch (InterruptedException e) {
                    }
                } finally {
                    GLSurfaceView.sEglSemaphore.release();
                }
            } catch (InterruptedException e2) {
                GLSurfaceView.sEglSemaphore.release();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:27:0x0051, code lost:
        
            if (r3 == false) goto L29;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x0053, code lost:
        
            r10.mEglHelper.start();
            r6 = true;
            r0 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x005a, code lost:
        
            if (r0 == false) goto L31;
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x005c, code lost:
        
            r1 = (javax.microedition.khronos.opengles.GL10) r10.mEglHelper.createSurface(r10.this$0.getHolder());
            r5 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x006b, code lost:
        
            if (r6 == false) goto L33;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x006d, code lost:
        
            r10.mRenderer.onSurfaceCreated(r1, r10.mEglHelper.mEglConfig);
            r6 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0077, code lost:
        
            if (r5 == false) goto L35;
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x0079, code lost:
        
            r10.mRenderer.onSurfaceChanged(r1, r7, r2);
            r5 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x007f, code lost:
        
            if (r7 <= 0) goto L45;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x0081, code lost:
        
            if (r2 <= 0) goto L46;
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x0083, code lost:
        
            r10.mRenderer.onDrawFrame(r1);
            r10.mEglHelper.swap();
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void guardedRun() throws InterruptedException {
            this.mEglHelper = GLSurfaceView.this.new EglHelper();
            this.mEglHelper.start();
            GL10 gl = null;
            boolean tellRendererSurfaceCreated = true;
            boolean tellRendererSurfaceChanged = true;
            while (!this.mDone) {
                boolean needStart = false;
                synchronized (this) {
                    while (true) {
                        Runnable r = getEvent();
                        if (r == null) {
                            break;
                        } else {
                            r.run();
                        }
                    }
                    if (this.mPaused) {
                        this.mEglHelper.finish();
                        needStart = true;
                    }
                    while (needToWait()) {
                        wait();
                    }
                    if (!this.mDone) {
                        boolean changed = this.mSizeChanged;
                        int w = this.mWidth;
                        int h = this.mHeight;
                        this.mSizeChanged = false;
                        this.mRequestRender = false;
                    }
                }
            }
            this.mEglHelper.finish();
        }

        private boolean needToWait() {
            if (this.mDone) {
                return false;
            }
            if (this.mPaused || !this.mHasSurface) {
                return true;
            }
            return this.mWidth <= 0 || this.mHeight <= 0 || !(this.mRequestRender || this.mRenderMode == 1);
        }

        public void setRenderMode(int renderMode) {
            if (renderMode < 0 || renderMode > 1) {
                throw new IllegalArgumentException("renderMode");
            }
            synchronized (this) {
                this.mRenderMode = renderMode;
                if (renderMode == 1) {
                    notify();
                }
            }
        }

        public int getRenderMode() {
            int i;
            synchronized (this) {
                i = this.mRenderMode;
            }
            return i;
        }

        public void requestRender() {
            synchronized (this) {
                this.mRequestRender = true;
                notify();
            }
        }

        public void surfaceCreated() {
            synchronized (this) {
                this.mHasSurface = true;
                notify();
            }
        }

        public void surfaceDestroyed() {
            synchronized (this) {
                this.mHasSurface = false;
                notify();
            }
        }

        public void onPause() {
            synchronized (this) {
                this.mPaused = true;
            }
        }

        public void onResume() {
            synchronized (this) {
                this.mPaused = false;
                notify();
            }
        }

        public void onWindowResize(int w, int h) {
            synchronized (this) {
                this.mWidth = w;
                this.mHeight = h;
                this.mSizeChanged = true;
                notify();
            }
        }

        public void requestExitAndWait() {
            synchronized (this) {
                this.mDone = true;
                notify();
            }
            try {
                join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public void queueEvent(Runnable r) {
            synchronized (this) {
                this.mEventQueue.add(r);
            }
        }

        private Runnable getEvent() {
            synchronized (this) {
                if (this.mEventQueue.size() > 0) {
                    return this.mEventQueue.remove(0);
                }
                return null;
            }
        }
    }

    class EglHelper {
        EGL10 mEgl;
        EGLConfig mEglConfig;
        EGLContext mEglContext;
        EGLDisplay mEglDisplay;
        EGLSurface mEglSurface;

        public EglHelper() {
        }

        public void start() {
            this.mEgl = (EGL10) EGLContext.getEGL();
            this.mEglDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            int[] version = new int[2];
            this.mEgl.eglInitialize(this.mEglDisplay, version);
            this.mEglConfig = GLSurfaceView.this.mEGLConfigChooser.chooseConfig(this.mEgl, this.mEglDisplay);
            this.mEglContext = this.mEgl.eglCreateContext(this.mEglDisplay, this.mEglConfig, EGL10.EGL_NO_CONTEXT, null);
            this.mEglSurface = null;
        }

        public GL createSurface(SurfaceHolder holder) {
            if (this.mEglSurface != null) {
                this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                this.mEgl.eglDestroySurface(this.mEglDisplay, this.mEglSurface);
            }
            this.mEglSurface = this.mEgl.eglCreateWindowSurface(this.mEglDisplay, this.mEglConfig, holder, null);
            this.mEgl.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.mEglContext);
            GL gl = this.mEglContext.getGL();
            if (GLSurfaceView.this.mGLWrapper != null) {
                return GLSurfaceView.this.mGLWrapper.wrap(gl);
            }
            return gl;
        }

        public boolean swap() {
            this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface);
            return this.mEgl.eglGetError() != 12302;
        }

        public void finish() {
            if (this.mEglSurface != null) {
                this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                this.mEgl.eglDestroySurface(this.mEglDisplay, this.mEglSurface);
                this.mEglSurface = null;
            }
            if (this.mEglContext != null) {
                this.mEgl.eglDestroyContext(this.mEglDisplay, this.mEglContext);
                this.mEglContext = null;
            }
            if (this.mEglDisplay != null) {
                this.mEgl.eglTerminate(this.mEglDisplay);
                this.mEglDisplay = null;
            }
        }
    }
}
