package org.anddev.andengine.opengl.buffer;

import javax.microedition.khronos.opengles.GL11;
import org.anddev.andengine.opengl.util.FastFloatBuffer;
import org.anddev.andengine.opengl.util.GLHelper;

/* JADX INFO: loaded from: classes.dex */
public abstract class BufferObject {
    private static final int[] HARDWAREBUFFERID_FETCHER = new int[1];
    protected final int[] mBufferData;
    private final int mDrawType;
    protected final FastFloatBuffer mFloatBuffer;
    private int mHardwareBufferID = -1;
    private boolean mHardwareBufferNeedsUpdate = true;
    private boolean mLoadedToHardware;
    private boolean mManaged;

    public BufferObject(int pCapacity, int pDrawType, boolean pManaged) {
        this.mDrawType = pDrawType;
        this.mManaged = pManaged;
        this.mBufferData = new int[pCapacity];
        this.mFloatBuffer = new FastFloatBuffer(pCapacity);
        if (pManaged) {
            loadToActiveBufferObjectManager();
        }
    }

    public boolean isManaged() {
        return this.mManaged;
    }

    public void setManaged(boolean pManaged) {
        this.mManaged = pManaged;
    }

    public FastFloatBuffer getFloatBuffer() {
        return this.mFloatBuffer;
    }

    public int getHardwareBufferID() {
        return this.mHardwareBufferID;
    }

    public boolean isLoadedToHardware() {
        return this.mLoadedToHardware;
    }

    void setLoadedToHardware(boolean pLoadedToHardware) {
        this.mLoadedToHardware = pLoadedToHardware;
    }

    public void setHardwareBufferNeedsUpdate() {
        this.mHardwareBufferNeedsUpdate = true;
    }

    public void selectOnHardware(GL11 pGL11) {
        int hardwareBufferID = this.mHardwareBufferID;
        if (hardwareBufferID != -1) {
            GLHelper.bindBuffer(pGL11, hardwareBufferID);
            if (this.mHardwareBufferNeedsUpdate) {
                this.mHardwareBufferNeedsUpdate = false;
                synchronized (this) {
                    GLHelper.bufferData(pGL11, this.mFloatBuffer.mByteBuffer, this.mDrawType);
                }
            }
        }
    }

    public void loadToActiveBufferObjectManager() {
        BufferObjectManager.getActiveInstance().loadBufferObject(this);
    }

    public void unloadFromActiveBufferObjectManager() {
        BufferObjectManager.getActiveInstance().unloadBufferObject(this);
    }

    public void loadToHardware(GL11 pGL11) {
        this.mHardwareBufferID = generateHardwareBufferID(pGL11);
        this.mLoadedToHardware = true;
    }

    public void unloadFromHardware(GL11 pGL11) {
        deleteBufferOnHardware(pGL11);
        this.mHardwareBufferID = -1;
        this.mLoadedToHardware = false;
    }

    private void deleteBufferOnHardware(GL11 pGL11) {
        GLHelper.deleteBuffer(pGL11, this.mHardwareBufferID);
    }

    private int generateHardwareBufferID(GL11 pGL11) {
        pGL11.glGenBuffers(1, HARDWAREBUFFERID_FETCHER, 0);
        return HARDWAREBUFFERID_FETCHER[0];
    }
}
