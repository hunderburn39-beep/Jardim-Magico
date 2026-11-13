package org.anddev.andengine.entity.util;

import android.graphics.Bitmap;
import android.support.v4.view.MotionEventCompat;
import java.nio.IntBuffer;
import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.Entity;

/* JADX INFO: loaded from: classes.dex */
public class ScreenGrabber extends Entity {
    private int mGrabHeight;
    private int mGrabWidth;
    private int mGrabX;
    private int mGrabY;
    private IScreenGrabberCallback mScreenGrabCallback;
    private boolean mScreenGrabPending = false;

    public interface IScreenGrabberCallback {
        void onScreenGrabFailed(Exception exc);

        void onScreenGrabbed(Bitmap bitmap);
    }

    @Override // org.anddev.andengine.entity.Entity
    protected void onManagedDraw(GL10 pGL, Camera pCamera) {
        if (this.mScreenGrabPending) {
            try {
                Bitmap screenGrab = grab(this.mGrabX, this.mGrabY, this.mGrabWidth, this.mGrabHeight, pGL);
                this.mScreenGrabCallback.onScreenGrabbed(screenGrab);
            } catch (Exception e) {
                this.mScreenGrabCallback.onScreenGrabFailed(e);
            }
            this.mScreenGrabPending = false;
        }
    }

    @Override // org.anddev.andengine.entity.Entity
    protected void onManagedUpdate(float pSecondsElapsed) {
    }

    @Override // org.anddev.andengine.entity.Entity, org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
    }

    public void grab(int pGrabWidth, int pGrabHeight, IScreenGrabberCallback pScreenGrabCallback) {
        grab(0, 0, pGrabWidth, pGrabHeight, pScreenGrabCallback);
    }

    public void grab(int pGrabX, int pGrabY, int pGrabWidth, int pGrabHeight, IScreenGrabberCallback pScreenGrabCallback) {
        this.mGrabX = pGrabX;
        this.mGrabY = pGrabY;
        this.mGrabWidth = pGrabWidth;
        this.mGrabHeight = pGrabHeight;
        this.mScreenGrabCallback = pScreenGrabCallback;
        this.mScreenGrabPending = true;
    }

    private static Bitmap grab(int pGrabX, int pGrabY, int pGrabWidth, int pGrabHeight, GL10 pGL) {
        int[] source = new int[(pGrabY + pGrabHeight) * pGrabWidth];
        IntBuffer sourceBuffer = IntBuffer.wrap(source);
        sourceBuffer.position(0);
        pGL.glReadPixels(pGrabX, 0, pGrabWidth, pGrabY + pGrabHeight, 6408, 5121, sourceBuffer);
        int[] pixels = new int[pGrabWidth * pGrabHeight];
        for (int y = 0; y < pGrabHeight; y++) {
            for (int x = 0; x < pGrabWidth; x++) {
                int pixel = source[((pGrabY + y) * pGrabWidth) + x];
                int blue = (16711680 & pixel) >> 16;
                int red = (pixel & MotionEventCompat.ACTION_MASK) << 16;
                int greenAlpha = pixel & (-16711936);
                pixels[(((pGrabHeight - y) - 1) * pGrabWidth) + x] = greenAlpha | red | blue;
            }
        }
        return Bitmap.createBitmap(pixels, pGrabWidth, pGrabHeight, Bitmap.Config.ARGB_8888);
    }
}
