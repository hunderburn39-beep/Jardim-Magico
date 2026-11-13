package org.anddev.andengine.entity.scene.menu.item.decorator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.IEntityModifier;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.util.Transformation;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseMenuItemDecorator implements IMenuItem {
    private final IMenuItem mMenuItem;

    protected abstract void onMenuItemReset(IMenuItem iMenuItem);

    protected abstract void onMenuItemSelected(IMenuItem iMenuItem);

    protected abstract void onMenuItemUnselected(IMenuItem iMenuItem);

    public BaseMenuItemDecorator(IMenuItem pMenuItem) {
        this.mMenuItem = pMenuItem;
    }

    @Override // org.anddev.andengine.entity.scene.menu.item.IMenuItem
    public int getID() {
        return this.mMenuItem.getID();
    }

    @Override // org.anddev.andengine.entity.scene.menu.item.IMenuItem
    public final void onSelected() {
        this.mMenuItem.onSelected();
        onMenuItemSelected(this.mMenuItem);
    }

    @Override // org.anddev.andengine.entity.scene.menu.item.IMenuItem
    public final void onUnselected() {
        this.mMenuItem.onUnselected();
        onMenuItemUnselected(this.mMenuItem);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getX() {
        return this.mMenuItem.getX();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getY() {
        return this.mMenuItem.getY();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setPosition(IEntity pOtherEntity) {
        this.mMenuItem.setPosition(pOtherEntity);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setPosition(float pX, float pY) {
        this.mMenuItem.setPosition(pX, pY);
    }

    @Override // org.anddev.andengine.entity.shape.IShape
    public float getBaseWidth() {
        return this.mMenuItem.getBaseWidth();
    }

    @Override // org.anddev.andengine.entity.shape.IShape
    public float getBaseHeight() {
        return this.mMenuItem.getBaseHeight();
    }

    @Override // org.anddev.andengine.entity.shape.IShape
    public float getWidth() {
        return this.mMenuItem.getWidth();
    }

    @Override // org.anddev.andengine.entity.shape.IShape
    public float getWidthScaled() {
        return this.mMenuItem.getWidthScaled();
    }

    @Override // org.anddev.andengine.entity.shape.IShape
    public float getHeight() {
        return this.mMenuItem.getHeight();
    }

    @Override // org.anddev.andengine.entity.shape.IShape
    public float getHeightScaled() {
        return this.mMenuItem.getHeightScaled();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getInitialX() {
        return this.mMenuItem.getInitialX();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getInitialY() {
        return this.mMenuItem.getInitialY();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getRed() {
        return this.mMenuItem.getRed();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getGreen() {
        return this.mMenuItem.getGreen();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getBlue() {
        return this.mMenuItem.getBlue();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getAlpha() {
        return this.mMenuItem.getAlpha();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setAlpha(float pAlpha) {
        this.mMenuItem.setAlpha(pAlpha);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setColor(float pRed, float pGreen, float pBlue) {
        this.mMenuItem.setColor(pRed, pGreen, pBlue);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setColor(float pRed, float pGreen, float pBlue, float pAlpha) {
        this.mMenuItem.setColor(pRed, pGreen, pBlue, pAlpha);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean isRotated() {
        return this.mMenuItem.isRotated();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getRotation() {
        return this.mMenuItem.getRotation();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setRotation(float pRotation) {
        this.mMenuItem.setRotation(pRotation);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getRotationCenterX() {
        return this.mMenuItem.getRotationCenterX();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getRotationCenterY() {
        return this.mMenuItem.getRotationCenterY();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setRotationCenterX(float pRotationCenterX) {
        this.mMenuItem.setRotationCenterX(pRotationCenterX);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setRotationCenterY(float pRotationCenterY) {
        this.mMenuItem.setRotationCenterY(pRotationCenterY);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setRotationCenter(float pRotationCenterX, float pRotationCenterY) {
        this.mMenuItem.setRotationCenter(pRotationCenterX, pRotationCenterY);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean isScaled() {
        return this.mMenuItem.isScaled();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getScaleX() {
        return this.mMenuItem.getScaleX();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getScaleY() {
        return this.mMenuItem.getScaleY();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setScale(float pScale) {
        this.mMenuItem.setScale(pScale);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setScale(float pScaleX, float pScaleY) {
        this.mMenuItem.setScale(pScaleX, pScaleY);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setScaleX(float pScaleX) {
        this.mMenuItem.setScaleX(pScaleX);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setScaleY(float pScaleY) {
        this.mMenuItem.setScaleY(pScaleY);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getScaleCenterX() {
        return this.mMenuItem.getScaleCenterX();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getScaleCenterY() {
        return this.mMenuItem.getScaleCenterY();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setScaleCenterX(float pScaleCenterX) {
        this.mMenuItem.setScaleCenterX(pScaleCenterX);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setScaleCenterY(float pScaleCenterY) {
        this.mMenuItem.setScaleCenterY(pScaleCenterY);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setScaleCenter(float pScaleCenterX, float pScaleCenterY) {
        this.mMenuItem.setScaleCenter(pScaleCenterX, pScaleCenterY);
    }

    @Override // org.anddev.andengine.entity.shape.IShape
    public boolean collidesWith(IShape pOtherShape) {
        return this.mMenuItem.collidesWith(pOtherShape);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float[] getSceneCenterCoordinates() {
        return this.mMenuItem.getSceneCenterCoordinates();
    }

    @Override // org.anddev.andengine.entity.shape.IShape
    public boolean isCullingEnabled() {
        return this.mMenuItem.isCullingEnabled();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void registerEntityModifier(IEntityModifier pEntityModifier) {
        this.mMenuItem.registerEntityModifier(pEntityModifier);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean unregisterEntityModifier(IEntityModifier pEntityModifier) {
        return this.mMenuItem.unregisterEntityModifier(pEntityModifier);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean unregisterEntityModifiers(IEntityModifier.IEntityModifierMatcher pEntityModifierMatcher) {
        return this.mMenuItem.unregisterEntityModifiers(pEntityModifierMatcher);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void clearEntityModifiers() {
        this.mMenuItem.clearEntityModifiers();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setInitialPosition() {
        this.mMenuItem.setInitialPosition();
    }

    @Override // org.anddev.andengine.entity.shape.IShape
    public void setBlendFunction(int pSourceBlendFunction, int pDestinationBlendFunction) {
        this.mMenuItem.setBlendFunction(pSourceBlendFunction, pDestinationBlendFunction);
    }

    @Override // org.anddev.andengine.entity.shape.IShape
    public void setCullingEnabled(boolean pCullingEnabled) {
        this.mMenuItem.setCullingEnabled(pCullingEnabled);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public int getZIndex() {
        return this.mMenuItem.getZIndex();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setZIndex(int pZIndex) {
        this.mMenuItem.setZIndex(pZIndex);
    }

    @Override // org.anddev.andengine.opengl.IDrawable
    public void onDraw(GL10 pGL, Camera pCamera) {
        this.mMenuItem.onDraw(pGL, pCamera);
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void onUpdate(float pSecondsElapsed) {
        this.mMenuItem.onUpdate(pSecondsElapsed);
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
        this.mMenuItem.reset();
        onMenuItemReset(this.mMenuItem);
    }

    @Override // org.anddev.andengine.entity.scene.Scene.ITouchArea
    public boolean contains(float pX, float pY) {
        return this.mMenuItem.contains(pX, pY);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float[] convertLocalToSceneCoordinates(float pX, float pY) {
        return this.mMenuItem.convertLocalToSceneCoordinates(pX, pY);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float[] convertLocalToSceneCoordinates(float pX, float pY, float[] pReuse) {
        return this.mMenuItem.convertLocalToSceneCoordinates(pX, pY, pReuse);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float[] convertLocalToSceneCoordinates(float[] pCoordinates) {
        return this.mMenuItem.convertLocalToSceneCoordinates(pCoordinates);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float[] convertLocalToSceneCoordinates(float[] pCoordinates, float[] pReuse) {
        return this.mMenuItem.convertLocalToSceneCoordinates(pCoordinates, pReuse);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float[] convertSceneToLocalCoordinates(float pX, float pY) {
        return this.mMenuItem.convertSceneToLocalCoordinates(pX, pY);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float[] convertSceneToLocalCoordinates(float pX, float pY, float[] pReuse) {
        return this.mMenuItem.convertSceneToLocalCoordinates(pX, pY, pReuse);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float[] convertSceneToLocalCoordinates(float[] pCoordinates) {
        return this.mMenuItem.convertSceneToLocalCoordinates(pCoordinates);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float[] convertSceneToLocalCoordinates(float[] pCoordinates, float[] pReuse) {
        return this.mMenuItem.convertSceneToLocalCoordinates(pCoordinates, pReuse);
    }

    @Override // org.anddev.andengine.entity.scene.Scene.ITouchArea
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        return this.mMenuItem.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public int getChildCount() {
        return this.mMenuItem.getChildCount();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void attachChild(IEntity pEntity) {
        this.mMenuItem.attachChild(pEntity);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean attachChild(IEntity pEntity, int pIndex) {
        return this.mMenuItem.attachChild(pEntity, pIndex);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public IEntity getFirstChild() {
        return this.mMenuItem.getFirstChild();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public IEntity getLastChild() {
        return this.mMenuItem.getLastChild();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public IEntity getChild(int pIndex) {
        return this.mMenuItem.getChild(pIndex);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public int getChildIndex(IEntity pEntity) {
        return this.mMenuItem.getChildIndex(pEntity);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean setChildIndex(IEntity pEntity, int pIndex) {
        return this.mMenuItem.setChildIndex(pEntity, pIndex);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public IEntity findChild(IEntity.IEntityMatcher pEntityMatcher) {
        return this.mMenuItem.findChild(pEntityMatcher);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public ArrayList<IEntity> query(IEntity.IEntityMatcher pEntityMatcher) {
        return this.mMenuItem.query(pEntityMatcher);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public <L extends List<IEntity>> L query(IEntity.IEntityMatcher iEntityMatcher, L l) {
        return (L) this.mMenuItem.query(iEntityMatcher, l);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public <S extends IEntity> ArrayList<S> queryForSubclass(IEntity.IEntityMatcher pEntityMatcher) throws ClassCastException {
        return this.mMenuItem.queryForSubclass(pEntityMatcher);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public <L extends List<S>, S extends IEntity> L queryForSubclass(IEntity.IEntityMatcher iEntityMatcher, L l) throws ClassCastException {
        return (L) this.mMenuItem.queryForSubclass(iEntityMatcher, l);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean swapChildren(IEntity pEntityA, IEntity pEntityB) {
        return this.mMenuItem.swapChildren(pEntityA, pEntityB);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean swapChildren(int pIndexA, int pIndexB) {
        return this.mMenuItem.swapChildren(pIndexA, pIndexB);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void sortChildren() {
        this.mMenuItem.sortChildren();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void sortChildren(Comparator<IEntity> pEntityComparator) {
        this.mMenuItem.sortChildren(pEntityComparator);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean detachSelf() {
        return this.mMenuItem.detachSelf();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean detachChild(IEntity pEntity) {
        return this.mMenuItem.detachChild(pEntity);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public IEntity detachChild(IEntity.IEntityMatcher pEntityMatcher) {
        return this.mMenuItem.detachChild(pEntityMatcher);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean detachChildren(IEntity.IEntityMatcher pEntityMatcher) {
        return this.mMenuItem.detachChildren(pEntityMatcher);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void detachChildren() {
        this.mMenuItem.detachChildren();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void callOnChildren(IEntity.IEntityCallable pEntityCallable) {
        callOnChildren(pEntityCallable);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void callOnChildren(IEntity.IEntityMatcher pEntityMatcher, IEntity.IEntityCallable pEntityCallable) {
        this.mMenuItem.callOnChildren(pEntityMatcher, pEntityCallable);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public Transformation getLocalToSceneTransformation() {
        return this.mMenuItem.getLocalToSceneTransformation();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public Transformation getSceneToLocalTransformation() {
        return this.mMenuItem.getSceneToLocalTransformation();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean hasParent() {
        return this.mMenuItem.hasParent();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public IEntity getParent() {
        return this.mMenuItem.getParent();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setParent(IEntity pEntity) {
        this.mMenuItem.setParent(pEntity);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean isVisible() {
        return this.mMenuItem.isVisible();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setVisible(boolean pVisible) {
        this.mMenuItem.setVisible(pVisible);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean isChildrenVisible() {
        return this.mMenuItem.isChildrenVisible();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setChildrenVisible(boolean pChildrenVisible) {
        this.mMenuItem.setChildrenVisible(pChildrenVisible);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean isIgnoreUpdate() {
        return this.mMenuItem.isIgnoreUpdate();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setIgnoreUpdate(boolean pIgnoreUpdate) {
        this.mMenuItem.setIgnoreUpdate(pIgnoreUpdate);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean isChildrenIgnoreUpdate() {
        return this.mMenuItem.isChildrenIgnoreUpdate();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setChildrenIgnoreUpdate(boolean pChildrenIgnoreUpdate) {
        this.mMenuItem.setChildrenIgnoreUpdate(pChildrenIgnoreUpdate);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setUserData(Object pUserData) {
        this.mMenuItem.setUserData(pUserData);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public Object getUserData() {
        return this.mMenuItem.getUserData();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void onAttached() {
        this.mMenuItem.onAttached();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void onDetached() {
        this.mMenuItem.onDetached();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void registerUpdateHandler(IUpdateHandler pUpdateHandler) {
        this.mMenuItem.registerUpdateHandler(pUpdateHandler);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean unregisterUpdateHandler(IUpdateHandler pUpdateHandler) {
        return this.mMenuItem.unregisterUpdateHandler(pUpdateHandler);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void clearUpdateHandlers() {
        this.mMenuItem.clearUpdateHandlers();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean unregisterUpdateHandlers(IUpdateHandler.IUpdateHandlerMatcher pUpdateHandlerMatcher) {
        return this.mMenuItem.unregisterUpdateHandlers(pUpdateHandlerMatcher);
    }
}
