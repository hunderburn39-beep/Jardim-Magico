package org.anddev.andengine.engine.camera;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.collision.RectangularShapeCollisionChecker;
import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.shape.RectangularShape;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.util.GLHelper;
import org.anddev.andengine.util.MathUtils;

/* JADX INFO: loaded from: classes.dex */
public class Camera implements IUpdateHandler {
    protected static final float[] VERTICES_TOUCH_TMP = new float[2];
    private IEntity mChaseEntity;
    private HUD mHUD;
    private float mMaxX;
    private float mMaxY;
    private float mMinX;
    private float mMinY;
    protected int mSurfaceHeight;
    protected int mSurfaceWidth;
    protected int mSurfaceX;
    protected int mSurfaceY;
    private float mNearZ = -1.0f;
    private float mFarZ = 1.0f;
    protected float mRotation = BitmapDescriptorFactory.HUE_RED;
    protected float mCameraSceneRotation = BitmapDescriptorFactory.HUE_RED;

    public Camera(float pX, float pY, float pWidth, float pHeight) {
        this.mMinX = pX;
        this.mMaxX = pX + pWidth;
        this.mMinY = pY;
        this.mMaxY = pY + pHeight;
    }

    public float getMinX() {
        return this.mMinX;
    }

    public float getMaxX() {
        return this.mMaxX;
    }

    public float getMinY() {
        return this.mMinY;
    }

    public float getMaxY() {
        return this.mMaxY;
    }

    public float getNearZClippingPlane() {
        return this.mNearZ;
    }

    public float getFarZClippingPlane() {
        return this.mFarZ;
    }

    public void setNearZClippingPlane(float pNearZClippingPlane) {
        this.mNearZ = pNearZClippingPlane;
    }

    public void setFarZClippingPlane(float pFarZClippingPlane) {
        this.mFarZ = pFarZClippingPlane;
    }

    public void setZClippingPlanes(float pNearZClippingPlane, float pFarZClippingPlane) {
        this.mNearZ = pNearZClippingPlane;
        this.mFarZ = pFarZClippingPlane;
    }

    public float getWidth() {
        return this.mMaxX - this.mMinX;
    }

    public float getHeight() {
        return this.mMaxY - this.mMinY;
    }

    public float getWidthRaw() {
        return this.mMaxX - this.mMinX;
    }

    public float getHeightRaw() {
        return this.mMaxY - this.mMinY;
    }

    public float getCenterX() {
        float minX = this.mMinX;
        return ((this.mMaxX - minX) * 0.5f) + minX;
    }

    public float getCenterY() {
        float minY = this.mMinY;
        return ((this.mMaxY - minY) * 0.5f) + minY;
    }

    public void setCenter(float pCenterX, float pCenterY) {
        float dX = pCenterX - getCenterX();
        float dY = pCenterY - getCenterY();
        this.mMinX += dX;
        this.mMaxX += dX;
        this.mMinY += dY;
        this.mMaxY += dY;
    }

    public void offsetCenter(float pX, float pY) {
        setCenter(getCenterX() + pX, getCenterY() + pY);
    }

    public HUD getHUD() {
        return this.mHUD;
    }

    public void setHUD(HUD pHUD) {
        this.mHUD = pHUD;
        pHUD.setCamera(this);
    }

    public boolean hasHUD() {
        return this.mHUD != null;
    }

    public void setChaseEntity(IEntity pChaseEntity) {
        this.mChaseEntity = pChaseEntity;
    }

    public float getRotation() {
        return this.mRotation;
    }

    public void setRotation(float pRotation) {
        this.mRotation = pRotation;
    }

    public float getCameraSceneRotation() {
        return this.mCameraSceneRotation;
    }

    public void setCameraSceneRotation(float pCameraSceneRotation) {
        this.mCameraSceneRotation = pCameraSceneRotation;
    }

    public int getSurfaceX() {
        return this.mSurfaceX;
    }

    public int getSurfaceY() {
        return this.mSurfaceY;
    }

    public int getSurfaceWidth() {
        return this.mSurfaceWidth;
    }

    public int getSurfaceHeight() {
        return this.mSurfaceHeight;
    }

    public void setSurfaceSize(int pSurfaceX, int pSurfaceY, int pSurfaceWidth, int pSurfaceHeight) {
        this.mSurfaceX = pSurfaceX;
        this.mSurfaceY = pSurfaceY;
        this.mSurfaceWidth = pSurfaceWidth;
        this.mSurfaceHeight = pSurfaceHeight;
    }

    public boolean isRotated() {
        return this.mRotation != BitmapDescriptorFactory.HUE_RED;
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void onUpdate(float pSecondsElapsed) {
        if (this.mHUD != null) {
            this.mHUD.onUpdate(pSecondsElapsed);
        }
        updateChaseEntity();
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
    }

    public void onDrawHUD(GL10 pGL) {
        if (this.mHUD != null) {
            this.mHUD.onDraw(pGL, this);
        }
    }

    public void updateChaseEntity() {
        if (this.mChaseEntity != null) {
            float[] centerCoordinates = this.mChaseEntity.getSceneCenterCoordinates();
            setCenter(centerCoordinates[0], centerCoordinates[1]);
        }
    }

    public boolean isLineVisible(Line pLine) {
        return RectangularShapeCollisionChecker.isVisible(this, pLine);
    }

    public boolean isRectangularShapeVisible(RectangularShape pRectangularShape) {
        return RectangularShapeCollisionChecker.isVisible(this, pRectangularShape);
    }

    public void onApplySceneMatrix(GL10 pGL) {
        GLHelper.setProjectionIdentityMatrix(pGL);
        pGL.glOrthof(getMinX(), getMaxX(), getMaxY(), getMinY(), this.mNearZ, this.mFarZ);
        float rotation = this.mRotation;
        if (rotation != BitmapDescriptorFactory.HUE_RED) {
            applyRotation(pGL, getCenterX(), getCenterY(), rotation);
        }
    }

    public void onApplySceneBackgroundMatrix(GL10 pGL) {
        GLHelper.setProjectionIdentityMatrix(pGL);
        float widthRaw = getWidthRaw();
        float heightRaw = getHeightRaw();
        pGL.glOrthof(BitmapDescriptorFactory.HUE_RED, widthRaw, heightRaw, BitmapDescriptorFactory.HUE_RED, this.mNearZ, this.mFarZ);
        float rotation = this.mRotation;
        if (rotation != BitmapDescriptorFactory.HUE_RED) {
            applyRotation(pGL, widthRaw * 0.5f, heightRaw * 0.5f, rotation);
        }
    }

    public void onApplyCameraSceneMatrix(GL10 pGL) {
        GLHelper.setProjectionIdentityMatrix(pGL);
        float widthRaw = getWidthRaw();
        float heightRaw = getHeightRaw();
        pGL.glOrthof(BitmapDescriptorFactory.HUE_RED, widthRaw, heightRaw, BitmapDescriptorFactory.HUE_RED, this.mNearZ, this.mFarZ);
        float cameraSceneRotation = this.mCameraSceneRotation;
        if (cameraSceneRotation != BitmapDescriptorFactory.HUE_RED) {
            applyRotation(pGL, widthRaw * 0.5f, heightRaw * 0.5f, cameraSceneRotation);
        }
    }

    private void applyRotation(GL10 pGL, float pRotationCenterX, float pRotationCenterY, float pAngle) {
        pGL.glTranslatef(pRotationCenterX, pRotationCenterY, BitmapDescriptorFactory.HUE_RED);
        pGL.glRotatef(pAngle, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 1.0f);
        pGL.glTranslatef(-pRotationCenterX, -pRotationCenterY, BitmapDescriptorFactory.HUE_RED);
    }

    public void convertSceneToCameraSceneTouchEvent(TouchEvent pSceneTouchEvent) {
        unapplySceneRotation(pSceneTouchEvent);
        applySceneToCameraSceneOffset(pSceneTouchEvent);
        applyCameraSceneRotation(pSceneTouchEvent);
    }

    public void convertCameraSceneToSceneTouchEvent(TouchEvent pCameraSceneTouchEvent) {
        unapplyCameraSceneRotation(pCameraSceneTouchEvent);
        unapplySceneToCameraSceneOffset(pCameraSceneTouchEvent);
        applySceneRotation(pCameraSceneTouchEvent);
    }

    protected void applySceneToCameraSceneOffset(TouchEvent pSceneTouchEvent) {
        pSceneTouchEvent.offset(-this.mMinX, -this.mMinY);
    }

    protected void unapplySceneToCameraSceneOffset(TouchEvent pCameraSceneTouchEvent) {
        pCameraSceneTouchEvent.offset(this.mMinX, this.mMinY);
    }

    private void applySceneRotation(TouchEvent pCameraSceneTouchEvent) {
        float rotation = -this.mRotation;
        if (rotation != BitmapDescriptorFactory.HUE_RED) {
            VERTICES_TOUCH_TMP[0] = pCameraSceneTouchEvent.getX();
            VERTICES_TOUCH_TMP[1] = pCameraSceneTouchEvent.getY();
            MathUtils.rotateAroundCenter(VERTICES_TOUCH_TMP, rotation, getCenterX(), getCenterY());
            pCameraSceneTouchEvent.set(VERTICES_TOUCH_TMP[0], VERTICES_TOUCH_TMP[1]);
        }
    }

    private void unapplySceneRotation(TouchEvent pSceneTouchEvent) {
        float rotation = this.mRotation;
        if (rotation != BitmapDescriptorFactory.HUE_RED) {
            VERTICES_TOUCH_TMP[0] = pSceneTouchEvent.getX();
            VERTICES_TOUCH_TMP[1] = pSceneTouchEvent.getY();
            MathUtils.revertRotateAroundCenter(VERTICES_TOUCH_TMP, rotation, getCenterX(), getCenterY());
            pSceneTouchEvent.set(VERTICES_TOUCH_TMP[0], VERTICES_TOUCH_TMP[1]);
        }
    }

    private void applyCameraSceneRotation(TouchEvent pSceneTouchEvent) {
        float cameraSceneRotation = -this.mCameraSceneRotation;
        if (cameraSceneRotation != BitmapDescriptorFactory.HUE_RED) {
            VERTICES_TOUCH_TMP[0] = pSceneTouchEvent.getX();
            VERTICES_TOUCH_TMP[1] = pSceneTouchEvent.getY();
            MathUtils.rotateAroundCenter(VERTICES_TOUCH_TMP, cameraSceneRotation, (this.mMaxX - this.mMinX) * 0.5f, (this.mMaxY - this.mMinY) * 0.5f);
            pSceneTouchEvent.set(VERTICES_TOUCH_TMP[0], VERTICES_TOUCH_TMP[1]);
        }
    }

    private void unapplyCameraSceneRotation(TouchEvent pCameraSceneTouchEvent) {
        float cameraSceneRotation = -this.mCameraSceneRotation;
        if (cameraSceneRotation != BitmapDescriptorFactory.HUE_RED) {
            VERTICES_TOUCH_TMP[0] = pCameraSceneTouchEvent.getX();
            VERTICES_TOUCH_TMP[1] = pCameraSceneTouchEvent.getY();
            MathUtils.revertRotateAroundCenter(VERTICES_TOUCH_TMP, cameraSceneRotation, (this.mMaxX - this.mMinX) * 0.5f, (this.mMaxY - this.mMinY) * 0.5f);
            pCameraSceneTouchEvent.set(VERTICES_TOUCH_TMP[0], VERTICES_TOUCH_TMP[1]);
        }
    }

    public void convertSurfaceToSceneTouchEvent(TouchEvent pSurfaceTouchEvent, int pSurfaceWidth, int pSurfaceHeight) {
        float relativeX;
        float relativeY;
        float surfaceTouchEventX = pSurfaceTouchEvent.getX();
        float surfaceTouchEventY = pSurfaceTouchEvent.getY();
        float rotation = this.mRotation;
        if (rotation == BitmapDescriptorFactory.HUE_RED) {
            relativeX = surfaceTouchEventX / pSurfaceWidth;
            relativeY = surfaceTouchEventY / pSurfaceHeight;
        } else if (rotation == 180.0f) {
            relativeX = 1.0f - (surfaceTouchEventX / pSurfaceWidth);
            relativeY = 1.0f - (surfaceTouchEventY / pSurfaceHeight);
        } else {
            VERTICES_TOUCH_TMP[0] = surfaceTouchEventX;
            VERTICES_TOUCH_TMP[1] = surfaceTouchEventY;
            MathUtils.rotateAroundCenter(VERTICES_TOUCH_TMP, rotation, pSurfaceWidth / 2, pSurfaceHeight / 2);
            relativeX = VERTICES_TOUCH_TMP[0] / pSurfaceWidth;
            relativeY = VERTICES_TOUCH_TMP[1] / pSurfaceHeight;
        }
        convertAxisAlignedSurfaceToSceneTouchEvent(pSurfaceTouchEvent, relativeX, relativeY);
    }

    private void convertAxisAlignedSurfaceToSceneTouchEvent(TouchEvent pSurfaceTouchEvent, float pRelativeX, float pRelativeY) {
        float minX = getMinX();
        float maxX = getMaxX();
        float minY = getMinY();
        float maxY = getMaxY();
        float x = minX + ((maxX - minX) * pRelativeX);
        float y = minY + ((maxY - minY) * pRelativeY);
        pSurfaceTouchEvent.set(x, y);
    }

    public void convertSceneToSurfaceTouchEvent(TouchEvent pSceneTouchEvent, int pSurfaceWidth, int pSurfaceHeight) {
        convertAxisAlignedSceneToSurfaceTouchEvent(pSceneTouchEvent, pSurfaceWidth, pSurfaceHeight);
        float rotation = this.mRotation;
        if (rotation != BitmapDescriptorFactory.HUE_RED) {
            if (rotation == 180.0f) {
                pSceneTouchEvent.set(pSurfaceWidth - pSceneTouchEvent.getX(), pSurfaceHeight - pSceneTouchEvent.getY());
                return;
            }
            VERTICES_TOUCH_TMP[0] = pSceneTouchEvent.getX();
            VERTICES_TOUCH_TMP[1] = pSceneTouchEvent.getY();
            MathUtils.revertRotateAroundCenter(VERTICES_TOUCH_TMP, rotation, pSurfaceWidth >> 1, pSurfaceHeight >> 1);
            pSceneTouchEvent.set(VERTICES_TOUCH_TMP[0], VERTICES_TOUCH_TMP[1]);
        }
    }

    private void convertAxisAlignedSceneToSurfaceTouchEvent(TouchEvent pSceneTouchEvent, int pSurfaceWidth, int pSurfaceHeight) {
        float minX = getMinX();
        float maxX = getMaxX();
        float minY = getMinY();
        float maxY = getMaxY();
        float relativeX = (pSceneTouchEvent.getX() - minX) / (maxX - minX);
        float relativeY = (pSceneTouchEvent.getY() - minY) / (maxY - minY);
        pSceneTouchEvent.set(pSurfaceWidth * relativeX, pSurfaceHeight * relativeY);
    }
}
