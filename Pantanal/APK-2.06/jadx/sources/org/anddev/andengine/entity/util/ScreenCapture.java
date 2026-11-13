package org.anddev.andengine.entity.util;

import android.graphics.Bitmap;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.StreamUtils;

/* JADX INFO: loaded from: classes.dex */
public class ScreenCapture extends Entity implements ScreenGrabber.IScreenGrabberCallback {
    private String mFilePath;
    private IScreenCaptureCallback mScreenCaptureCallback;
    private final ScreenGrabber mScreenGrabber = new ScreenGrabber();

    public interface IScreenCaptureCallback {
        void onScreenCaptureFailed(String str, Exception exc);

        void onScreenCaptured(String str);
    }

    @Override // org.anddev.andengine.entity.Entity
    protected void onManagedDraw(GL10 pGL, Camera pCamera) {
        this.mScreenGrabber.onManagedDraw(pGL, pCamera);
    }

    @Override // org.anddev.andengine.entity.Entity
    protected void onManagedUpdate(float pSecondsElapsed) {
    }

    @Override // org.anddev.andengine.entity.Entity, org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
    }

    @Override // org.anddev.andengine.entity.util.ScreenGrabber.IScreenGrabberCallback
    public void onScreenGrabbed(Bitmap pBitmap) {
        try {
            saveCapture(pBitmap, this.mFilePath);
            this.mScreenCaptureCallback.onScreenCaptured(this.mFilePath);
        } catch (FileNotFoundException e) {
            this.mScreenCaptureCallback.onScreenCaptureFailed(this.mFilePath, e);
        }
    }

    @Override // org.anddev.andengine.entity.util.ScreenGrabber.IScreenGrabberCallback
    public void onScreenGrabFailed(Exception pException) {
        this.mScreenCaptureCallback.onScreenCaptureFailed(this.mFilePath, pException);
    }

    public void capture(int pCaptureWidth, int pCaptureHeight, String pFilePath, IScreenCaptureCallback pScreenCaptureCallback) {
        capture(0, 0, pCaptureWidth, pCaptureHeight, pFilePath, pScreenCaptureCallback);
    }

    public void capture(int pCaptureX, int pCaptureY, int pCaptureWidth, int pCaptureHeight, String pFilePath, IScreenCaptureCallback pScreencaptureCallback) {
        this.mFilePath = pFilePath;
        this.mScreenCaptureCallback = pScreencaptureCallback;
        this.mScreenGrabber.grab(pCaptureX, pCaptureY, pCaptureWidth, pCaptureHeight, this);
    }

    private static void saveCapture(Bitmap pBitmap, String pFilePath) throws FileNotFoundException {
        FileOutputStream fos = null;
        try {
            FileOutputStream fos2 = new FileOutputStream(pFilePath);
            try {
                pBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos2);
            } catch (FileNotFoundException e) {
                e = e;
                fos = fos2;
                StreamUtils.flushCloseStream(fos);
                Debug.e("Error saving file to: " + pFilePath, e);
                throw e;
            }
        } catch (FileNotFoundException e2) {
            e = e2;
        }
    }
}
