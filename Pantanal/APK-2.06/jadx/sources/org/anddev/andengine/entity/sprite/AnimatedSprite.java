package org.anddev.andengine.entity.sprite;

import java.util.Arrays;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.opengl.vertex.RectangleVertexBuffer;
import org.anddev.andengine.util.MathUtils;
import org.anddev.andengine.util.constants.TimeConstants;

/* JADX INFO: loaded from: classes.dex */
public class AnimatedSprite extends TiledSprite implements TimeConstants {
    private static final int LOOP_CONTINUOUS = -1;
    private long mAnimationDuration;
    private IAnimationListener mAnimationListener;
    private long mAnimationProgress;
    private boolean mAnimationRunning;
    private int mFirstTileIndex;
    private int mFrameCount;
    private long[] mFrameEndsInNanoseconds;
    private int[] mFrames;
    private int mInitialLoopCount;
    private int mLoopCount;

    public interface IAnimationListener {
        void onAnimationEnd(AnimatedSprite animatedSprite);
    }

    public AnimatedSprite(float pX, float pY, TiledTextureRegion pTiledTextureRegion) {
        super(pX, pY, pTiledTextureRegion);
    }

    public AnimatedSprite(float pX, float pY, float pTileWidth, float pTileHeight, TiledTextureRegion pTiledTextureRegion) {
        super(pX, pY, pTileWidth, pTileHeight, pTiledTextureRegion);
    }

    public AnimatedSprite(float pX, float pY, TiledTextureRegion pTiledTextureRegion, RectangleVertexBuffer pRectangleVertexBuffer) {
        super(pX, pY, pTiledTextureRegion, pRectangleVertexBuffer);
    }

    public AnimatedSprite(float pX, float pY, float pTileWidth, float pTileHeight, TiledTextureRegion pTiledTextureRegion, RectangleVertexBuffer pRectangleVertexBuffer) {
        super(pX, pY, pTileWidth, pTileHeight, pTiledTextureRegion, pRectangleVertexBuffer);
    }

    public boolean isAnimationRunning() {
        return this.mAnimationRunning;
    }

    @Override // org.anddev.andengine.entity.Entity
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
        if (this.mAnimationRunning) {
            long nanoSecondsElapsed = (long) (1.0E9f * pSecondsElapsed);
            this.mAnimationProgress += nanoSecondsElapsed;
            if (this.mAnimationProgress > this.mAnimationDuration) {
                this.mAnimationProgress %= this.mAnimationDuration;
                if (this.mInitialLoopCount != -1) {
                    this.mLoopCount--;
                }
            }
            if (this.mInitialLoopCount == -1 || this.mLoopCount >= 0) {
                int currentFrameIndex = calculateCurrentFrameIndex();
                if (this.mFrames == null) {
                    setCurrentTileIndex(this.mFirstTileIndex + currentFrameIndex);
                    return;
                } else {
                    setCurrentTileIndex(this.mFrames[currentFrameIndex]);
                    return;
                }
            }
            this.mAnimationRunning = false;
            if (this.mAnimationListener != null) {
                this.mAnimationListener.onAnimationEnd(this);
            }
        }
    }

    public void stopAnimation() {
        this.mAnimationRunning = false;
    }

    public void stopAnimation(int pTileIndex) {
        this.mAnimationRunning = false;
        setCurrentTileIndex(pTileIndex);
    }

    private int calculateCurrentFrameIndex() {
        long animationProgress = this.mAnimationProgress;
        long[] frameEnds = this.mFrameEndsInNanoseconds;
        int frameCount = this.mFrameCount;
        for (int i = 0; i < frameCount; i++) {
            if (frameEnds[i] > animationProgress) {
                return i;
            }
        }
        int i2 = frameCount - 1;
        return i2;
    }

    public AnimatedSprite animate(long pFrameDurationEach) {
        return animate(pFrameDurationEach, true);
    }

    public AnimatedSprite animate(long pFrameDurationEach, boolean pLoop) {
        return animate(pFrameDurationEach, pLoop ? -1 : 0, (IAnimationListener) null);
    }

    public AnimatedSprite animate(long pFrameDurationEach, int pLoopCount) {
        return animate(pFrameDurationEach, pLoopCount, (IAnimationListener) null);
    }

    public AnimatedSprite animate(long pFrameDurationEach, boolean pLoop, IAnimationListener pAnimationListener) {
        return animate(pFrameDurationEach, pLoop ? -1 : 0, pAnimationListener);
    }

    public AnimatedSprite animate(long pFrameDurationEach, int pLoopCount, IAnimationListener pAnimationListener) {
        long[] frameDurations = new long[getTextureRegion().getTileCount()];
        Arrays.fill(frameDurations, pFrameDurationEach);
        return animate(frameDurations, pLoopCount, pAnimationListener);
    }

    public AnimatedSprite animate(long[] pFrameDurations) {
        return animate(pFrameDurations, true);
    }

    public AnimatedSprite animate(long[] pFrameDurations, boolean pLoop) {
        return animate(pFrameDurations, pLoop ? -1 : 0, (IAnimationListener) null);
    }

    public AnimatedSprite animate(long[] pFrameDurations, int pLoopCount) {
        return animate(pFrameDurations, pLoopCount, (IAnimationListener) null);
    }

    public AnimatedSprite animate(long[] pFrameDurations, boolean pLoop, IAnimationListener pAnimationListener) {
        return animate(pFrameDurations, pLoop ? -1 : 0, pAnimationListener);
    }

    public AnimatedSprite animate(long[] pFrameDurations, int pLoopCount, IAnimationListener pAnimationListener) {
        return animate(pFrameDurations, 0, getTextureRegion().getTileCount() - 1, pLoopCount, pAnimationListener);
    }

    public AnimatedSprite animate(long[] pFrameDurations, int pFirstTileIndex, int pLastTileIndex, boolean pLoop) {
        return animate(pFrameDurations, pFirstTileIndex, pLastTileIndex, pLoop ? -1 : 0, null);
    }

    public AnimatedSprite animate(long[] pFrameDurations, int pFirstTileIndex, int pLastTileIndex, int pLoopCount) {
        return animate(pFrameDurations, pFirstTileIndex, pLastTileIndex, pLoopCount, null);
    }

    public AnimatedSprite animate(long[] pFrameDurations, int[] pFrames, int pLoopCount) {
        return animate(pFrameDurations, pFrames, pLoopCount, (IAnimationListener) null);
    }

    public AnimatedSprite animate(long[] pFrameDurations, int[] pFrames, int pLoopCount, IAnimationListener pAnimationListener) {
        int frameCount = pFrames.length;
        if (pFrameDurations.length != frameCount) {
            throw new IllegalArgumentException("pFrameDurations must have the same length as pFrames.");
        }
        return init(pFrameDurations, frameCount, pFrames, 0, pLoopCount, pAnimationListener);
    }

    public AnimatedSprite animate(long[] pFrameDurations, int pFirstTileIndex, int pLastTileIndex, int pLoopCount, IAnimationListener pAnimationListener) {
        if (pLastTileIndex - pFirstTileIndex < 1) {
            throw new IllegalArgumentException("An animation needs at least two tiles to animate between.");
        }
        int frameCount = (pLastTileIndex - pFirstTileIndex) + 1;
        if (pFrameDurations.length != frameCount) {
            throw new IllegalArgumentException("pFrameDurations must have the same length as pFirstTileIndex to pLastTileIndex.");
        }
        return init(pFrameDurations, frameCount, null, pFirstTileIndex, pLoopCount, pAnimationListener);
    }

    private AnimatedSprite init(long[] pFrameDurations, int frameCount, int[] pFrames, int pFirstTileIndex, int pLoopCount, IAnimationListener pAnimationListener) {
        this.mFrameCount = frameCount;
        this.mAnimationListener = pAnimationListener;
        this.mInitialLoopCount = pLoopCount;
        this.mLoopCount = pLoopCount;
        this.mFrames = pFrames;
        this.mFirstTileIndex = pFirstTileIndex;
        if (this.mFrameEndsInNanoseconds == null || this.mFrameCount > this.mFrameEndsInNanoseconds.length) {
            this.mFrameEndsInNanoseconds = new long[this.mFrameCount];
        }
        long[] frameEndsInNanoseconds = this.mFrameEndsInNanoseconds;
        MathUtils.arraySumInto(pFrameDurations, frameEndsInNanoseconds, TimeConstants.NANOSECONDSPERMILLISECOND);
        long lastFrameEnd = frameEndsInNanoseconds[this.mFrameCount - 1];
        this.mAnimationDuration = lastFrameEnd;
        this.mAnimationProgress = 0L;
        this.mAnimationRunning = true;
        return this;
    }
}
