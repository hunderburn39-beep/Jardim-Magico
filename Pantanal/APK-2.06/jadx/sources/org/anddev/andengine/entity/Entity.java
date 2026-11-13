package org.anddev.andengine.entity;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.UpdateHandlerList;
import org.anddev.andengine.entity.modifier.EntityModifierList;
import org.anddev.andengine.entity.modifier.IEntityModifier;
import org.anddev.andengine.util.ParameterCallable;
import org.anddev.andengine.util.SmartList;
import org.anddev.andengine.util.Transformation;

/* JADX INFO: loaded from: classes.dex */
public class Entity implements IEntity {
    private static final int CHILDREN_CAPACITY_DEFAULT = 4;
    private static final int ENTITYMODIFIERS_CAPACITY_DEFAULT = 4;
    private static final int UPDATEHANDLERS_CAPACITY_DEFAULT = 4;
    protected float mAlpha;
    protected float mBlue;
    protected SmartList<IEntity> mChildren;
    protected boolean mChildrenIgnoreUpdate;
    protected boolean mChildrenVisible;
    private EntityModifierList mEntityModifiers;
    protected float mGreen;
    protected boolean mIgnoreUpdate;
    private final float mInitialX;
    private final float mInitialY;
    private final Transformation mLocalToParentTransformation;
    private boolean mLocalToParentTransformationDirty;
    private final Transformation mLocalToSceneTransformation;
    private IEntity mParent;
    private final Transformation mParentToLocalTransformation;
    private boolean mParentToLocalTransformationDirty;
    protected float mRed;
    protected float mRotation;
    protected float mRotationCenterX;
    protected float mRotationCenterY;
    protected float mScaleCenterX;
    protected float mScaleCenterY;
    protected float mScaleX;
    protected float mScaleY;
    private final Transformation mSceneToLocalTransformation;
    private UpdateHandlerList mUpdateHandlers;
    private Object mUserData;
    protected boolean mVisible;
    protected float mX;
    protected float mY;
    protected int mZIndex;
    private static final float[] VERTICES_SCENE_TO_LOCAL_TMP = new float[2];
    private static final float[] VERTICES_LOCAL_TO_SCENE_TMP = new float[2];
    private static final ParameterCallable<IEntity> PARAMETERCALLABLE_DETACHCHILD = new ParameterCallable<IEntity>() { // from class: org.anddev.andengine.entity.Entity.1
        @Override // org.anddev.andengine.util.ParameterCallable
        public void call(IEntity pEntity) {
            pEntity.setParent(null);
            pEntity.onDetached();
        }
    };

    public Entity() {
        this(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
    }

    public Entity(float pX, float pY) {
        this.mVisible = true;
        this.mIgnoreUpdate = false;
        this.mChildrenVisible = true;
        this.mChildrenIgnoreUpdate = false;
        this.mZIndex = 0;
        this.mRed = 1.0f;
        this.mGreen = 1.0f;
        this.mBlue = 1.0f;
        this.mAlpha = 1.0f;
        this.mRotation = BitmapDescriptorFactory.HUE_RED;
        this.mRotationCenterX = BitmapDescriptorFactory.HUE_RED;
        this.mRotationCenterY = BitmapDescriptorFactory.HUE_RED;
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.mScaleCenterX = BitmapDescriptorFactory.HUE_RED;
        this.mScaleCenterY = BitmapDescriptorFactory.HUE_RED;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
        this.mLocalToParentTransformation = new Transformation();
        this.mParentToLocalTransformation = new Transformation();
        this.mLocalToSceneTransformation = new Transformation();
        this.mSceneToLocalTransformation = new Transformation();
        this.mInitialX = pX;
        this.mInitialY = pY;
        this.mX = pX;
        this.mY = pY;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean isVisible() {
        return this.mVisible;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setVisible(boolean pVisible) {
        this.mVisible = pVisible;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean isChildrenVisible() {
        return this.mChildrenVisible;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setChildrenVisible(boolean pChildrenVisible) {
        this.mChildrenVisible = pChildrenVisible;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean isIgnoreUpdate() {
        return this.mIgnoreUpdate;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setIgnoreUpdate(boolean pIgnoreUpdate) {
        this.mIgnoreUpdate = pIgnoreUpdate;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean isChildrenIgnoreUpdate() {
        return this.mChildrenIgnoreUpdate;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setChildrenIgnoreUpdate(boolean pChildrenIgnoreUpdate) {
        this.mChildrenIgnoreUpdate = pChildrenIgnoreUpdate;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean hasParent() {
        return this.mParent != null;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public IEntity getParent() {
        return this.mParent;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setParent(IEntity pEntity) {
        this.mParent = pEntity;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public int getZIndex() {
        return this.mZIndex;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setZIndex(int pZIndex) {
        this.mZIndex = pZIndex;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getX() {
        return this.mX;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getY() {
        return this.mY;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getInitialX() {
        return this.mInitialX;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getInitialY() {
        return this.mInitialY;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setPosition(IEntity pOtherEntity) {
        setPosition(pOtherEntity.getX(), pOtherEntity.getY());
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setPosition(float pX, float pY) {
        this.mX = pX;
        this.mY = pY;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setInitialPosition() {
        this.mX = this.mInitialX;
        this.mY = this.mInitialY;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getRotation() {
        return this.mRotation;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean isRotated() {
        return this.mRotation != BitmapDescriptorFactory.HUE_RED;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setRotation(float pRotation) {
        this.mRotation = pRotation;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getRotationCenterX() {
        return this.mRotationCenterX;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getRotationCenterY() {
        return this.mRotationCenterY;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setRotationCenterX(float pRotationCenterX) {
        this.mRotationCenterX = pRotationCenterX;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setRotationCenterY(float pRotationCenterY) {
        this.mRotationCenterY = pRotationCenterY;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setRotationCenter(float pRotationCenterX, float pRotationCenterY) {
        this.mRotationCenterX = pRotationCenterX;
        this.mRotationCenterY = pRotationCenterY;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean isScaled() {
        return (this.mScaleX == 1.0f && this.mScaleY == 1.0f) ? false : true;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getScaleX() {
        return this.mScaleX;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getScaleY() {
        return this.mScaleY;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setScaleX(float pScaleX) {
        this.mScaleX = pScaleX;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setScaleY(float pScaleY) {
        this.mScaleY = pScaleY;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setScale(float pScale) {
        this.mScaleX = pScale;
        this.mScaleY = pScale;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setScale(float pScaleX, float pScaleY) {
        this.mScaleX = pScaleX;
        this.mScaleY = pScaleY;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getScaleCenterX() {
        return this.mScaleCenterX;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getScaleCenterY() {
        return this.mScaleCenterY;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setScaleCenterX(float pScaleCenterX) {
        this.mScaleCenterX = pScaleCenterX;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setScaleCenterY(float pScaleCenterY) {
        this.mScaleCenterY = pScaleCenterY;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setScaleCenter(float pScaleCenterX, float pScaleCenterY) {
        this.mScaleCenterX = pScaleCenterX;
        this.mScaleCenterY = pScaleCenterY;
        this.mLocalToParentTransformationDirty = true;
        this.mParentToLocalTransformationDirty = true;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getRed() {
        return this.mRed;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getGreen() {
        return this.mGreen;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getBlue() {
        return this.mBlue;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float getAlpha() {
        return this.mAlpha;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setAlpha(float pAlpha) {
        this.mAlpha = pAlpha;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setColor(float pRed, float pGreen, float pBlue) {
        this.mRed = pRed;
        this.mGreen = pGreen;
        this.mBlue = pBlue;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setColor(float pRed, float pGreen, float pBlue, float pAlpha) {
        this.mRed = pRed;
        this.mGreen = pGreen;
        this.mBlue = pBlue;
        this.mAlpha = pAlpha;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public int getChildCount() {
        if (this.mChildren == null) {
            return 0;
        }
        return this.mChildren.size();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public IEntity getChild(int pIndex) {
        if (this.mChildren == null) {
            return null;
        }
        return this.mChildren.get(pIndex);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public int getChildIndex(IEntity pEntity) {
        if (this.mChildren == null || pEntity.getParent() != this) {
            return -1;
        }
        return this.mChildren.indexOf(pEntity);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean setChildIndex(IEntity pEntity, int pIndex) {
        if (this.mChildren == null || pEntity.getParent() != this) {
            return false;
        }
        try {
            this.mChildren.remove(pEntity);
            this.mChildren.add(pIndex, pEntity);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    @Override // org.anddev.andengine.entity.IEntity
    public IEntity getFirstChild() {
        if (this.mChildren == null) {
            return null;
        }
        return this.mChildren.get(0);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public IEntity getLastChild() {
        if (this.mChildren == null) {
            return null;
        }
        return this.mChildren.get(this.mChildren.size() - 1);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean detachSelf() {
        IEntity parent = this.mParent;
        if (parent != null) {
            return parent.detachChild(this);
        }
        return false;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void detachChildren() {
        if (this.mChildren != null) {
            this.mChildren.clear(PARAMETERCALLABLE_DETACHCHILD);
        }
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void attachChild(IEntity pEntity) throws IllegalStateException {
        if (pEntity.hasParent()) {
            throw new IllegalStateException("pEntity already has a parent!");
        }
        if (this.mChildren == null) {
            allocateChildren();
        }
        this.mChildren.add(pEntity);
        pEntity.setParent(this);
        pEntity.onAttached();
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean attachChild(IEntity pEntity, int pIndex) throws IllegalStateException {
        if (pEntity.hasParent()) {
            throw new IllegalStateException("pEntity already has a parent!");
        }
        if (this.mChildren == null) {
            allocateChildren();
        }
        try {
            this.mChildren.add(pIndex, pEntity);
            pEntity.setParent(this);
            pEntity.onAttached();
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    @Override // org.anddev.andengine.entity.IEntity
    public IEntity findChild(IEntity.IEntityMatcher pEntityMatcher) {
        if (this.mChildren == null) {
            return null;
        }
        return this.mChildren.find(pEntityMatcher);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public ArrayList<IEntity> query(IEntity.IEntityMatcher pEntityMatcher) {
        return (ArrayList) query(pEntityMatcher, new ArrayList());
    }

    @Override // org.anddev.andengine.entity.IEntity
    public <L extends List<IEntity>> L query(IEntity.IEntityMatcher pEntityMatcher, L pResult) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            IEntity item = this.mChildren.get(i);
            if (pEntityMatcher.matches(item)) {
                pResult.add(item);
            }
            item.query(pEntityMatcher, pResult);
        }
        return pResult;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public <S extends IEntity> ArrayList<S> queryForSubclass(IEntity.IEntityMatcher pEntityMatcher) throws ClassCastException {
        return (ArrayList) queryForSubclass(pEntityMatcher, new ArrayList());
    }

    @Override // org.anddev.andengine.entity.IEntity
    public <L extends List<S>, S extends IEntity> L queryForSubclass(IEntity.IEntityMatcher pEntityMatcher, L pResult) throws ClassCastException {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            IEntity item = this.mChildren.get(i);
            if (pEntityMatcher.matches(item)) {
                pResult.add(item);
            }
            item.queryForSubclass(pEntityMatcher, pResult);
        }
        return pResult;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean swapChildren(IEntity pEntityA, IEntity pEntityB) {
        return swapChildren(getChildIndex(pEntityA), getChildIndex(pEntityB));
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean swapChildren(int pIndexA, int pIndexB) {
        try {
            Collections.swap(this.mChildren, pIndexA, pIndexB);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void sortChildren() {
        if (this.mChildren != null) {
            ZIndexSorter.getInstance().sort(this.mChildren);
        }
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void sortChildren(Comparator<IEntity> pEntityComparator) {
        if (this.mChildren != null) {
            ZIndexSorter.getInstance().sort(this.mChildren, pEntityComparator);
        }
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean detachChild(IEntity pEntity) {
        if (this.mChildren == null) {
            return false;
        }
        return this.mChildren.remove(pEntity, PARAMETERCALLABLE_DETACHCHILD);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public IEntity detachChild(IEntity.IEntityMatcher pEntityMatcher) {
        if (this.mChildren == null) {
            return null;
        }
        return this.mChildren.remove(pEntityMatcher, PARAMETERCALLABLE_DETACHCHILD);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean detachChildren(IEntity.IEntityMatcher pEntityMatcher) {
        if (this.mChildren == null) {
            return false;
        }
        return this.mChildren.removeAll(pEntityMatcher, PARAMETERCALLABLE_DETACHCHILD);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void callOnChildren(IEntity.IEntityCallable pEntityCallable) {
        if (this.mChildren != null) {
            this.mChildren.call(pEntityCallable);
        }
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void callOnChildren(IEntity.IEntityMatcher pEntityMatcher, IEntity.IEntityCallable pEntityCallable) {
        if (this.mChildren != null) {
            this.mChildren.call(pEntityMatcher, pEntityCallable);
        }
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void registerUpdateHandler(IUpdateHandler pUpdateHandler) {
        if (this.mUpdateHandlers == null) {
            allocateUpdateHandlers();
        }
        this.mUpdateHandlers.add(pUpdateHandler);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean unregisterUpdateHandler(IUpdateHandler pUpdateHandler) {
        if (this.mUpdateHandlers == null) {
            return false;
        }
        return this.mUpdateHandlers.remove(pUpdateHandler);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean unregisterUpdateHandlers(IUpdateHandler.IUpdateHandlerMatcher pUpdateHandlerMatcher) {
        if (this.mUpdateHandlers == null) {
            return false;
        }
        return this.mUpdateHandlers.removeAll(pUpdateHandlerMatcher);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void clearUpdateHandlers() {
        if (this.mUpdateHandlers != null) {
            this.mUpdateHandlers.clear();
        }
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void registerEntityModifier(IEntityModifier pEntityModifier) {
        if (this.mEntityModifiers == null) {
            allocateEntityModifiers();
        }
        this.mEntityModifiers.add(pEntityModifier);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean unregisterEntityModifier(IEntityModifier pEntityModifier) {
        if (this.mEntityModifiers == null) {
            return false;
        }
        return this.mEntityModifiers.remove(pEntityModifier);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public boolean unregisterEntityModifiers(IEntityModifier.IEntityModifierMatcher pEntityModifierMatcher) {
        if (this.mEntityModifiers == null) {
            return false;
        }
        return this.mEntityModifiers.removeAll(pEntityModifierMatcher);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void clearEntityModifiers() {
        if (this.mEntityModifiers != null) {
            this.mEntityModifiers.clear();
        }
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float[] getSceneCenterCoordinates() {
        return convertLocalToSceneCoordinates(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
    }

    public Transformation getLocalToParentTransformation() {
        Transformation localToParentTransformation = this.mLocalToParentTransformation;
        if (this.mLocalToParentTransformationDirty) {
            localToParentTransformation.setToIdentity();
            float scaleX = this.mScaleX;
            float scaleY = this.mScaleY;
            if (scaleX != 1.0f || scaleY != 1.0f) {
                float scaleCenterX = this.mScaleCenterX;
                float scaleCenterY = this.mScaleCenterY;
                localToParentTransformation.postTranslate(-scaleCenterX, -scaleCenterY);
                localToParentTransformation.postScale(scaleX, scaleY);
                localToParentTransformation.postTranslate(scaleCenterX, scaleCenterY);
            }
            float rotation = this.mRotation;
            if (rotation != BitmapDescriptorFactory.HUE_RED) {
                float rotationCenterX = this.mRotationCenterX;
                float rotationCenterY = this.mRotationCenterY;
                localToParentTransformation.postTranslate(-rotationCenterX, -rotationCenterY);
                localToParentTransformation.postRotate(rotation);
                localToParentTransformation.postTranslate(rotationCenterX, rotationCenterY);
            }
            localToParentTransformation.postTranslate(this.mX, this.mY);
            this.mLocalToParentTransformationDirty = false;
        }
        return localToParentTransformation;
    }

    public Transformation getParentToLocalTransformation() {
        Transformation parentToLocalTransformation = this.mParentToLocalTransformation;
        if (this.mParentToLocalTransformationDirty) {
            parentToLocalTransformation.setToIdentity();
            parentToLocalTransformation.postTranslate(-this.mX, -this.mY);
            float rotation = this.mRotation;
            if (rotation != BitmapDescriptorFactory.HUE_RED) {
                float rotationCenterX = this.mRotationCenterX;
                float rotationCenterY = this.mRotationCenterY;
                parentToLocalTransformation.postTranslate(-rotationCenterX, -rotationCenterY);
                parentToLocalTransformation.postRotate(-rotation);
                parentToLocalTransformation.postTranslate(rotationCenterX, rotationCenterY);
            }
            float scaleX = this.mScaleX;
            float scaleY = this.mScaleY;
            if (scaleX != 1.0f || scaleY != 1.0f) {
                float scaleCenterX = this.mScaleCenterX;
                float scaleCenterY = this.mScaleCenterY;
                parentToLocalTransformation.postTranslate(-scaleCenterX, -scaleCenterY);
                parentToLocalTransformation.postScale(1.0f / scaleX, 1.0f / scaleY);
                parentToLocalTransformation.postTranslate(scaleCenterX, scaleCenterY);
            }
            this.mParentToLocalTransformationDirty = false;
        }
        return parentToLocalTransformation;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public Transformation getLocalToSceneTransformation() {
        Transformation localToSceneTransformation = this.mLocalToSceneTransformation;
        localToSceneTransformation.setTo(getLocalToParentTransformation());
        IEntity parent = this.mParent;
        if (parent != null) {
            localToSceneTransformation.postConcat(parent.getLocalToSceneTransformation());
        }
        return localToSceneTransformation;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public Transformation getSceneToLocalTransformation() {
        Transformation sceneToLocalTransformation = this.mSceneToLocalTransformation;
        sceneToLocalTransformation.setTo(getParentToLocalTransformation());
        IEntity parent = this.mParent;
        if (parent != null) {
            sceneToLocalTransformation.postConcat(parent.getSceneToLocalTransformation());
        }
        return sceneToLocalTransformation;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float[] convertLocalToSceneCoordinates(float pX, float pY) {
        return convertLocalToSceneCoordinates(pX, pY, VERTICES_LOCAL_TO_SCENE_TMP);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float[] convertLocalToSceneCoordinates(float pX, float pY, float[] pReuse) {
        pReuse[0] = pX;
        pReuse[1] = pY;
        getLocalToSceneTransformation().transform(pReuse);
        return pReuse;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float[] convertLocalToSceneCoordinates(float[] pCoordinates) {
        return convertSceneToLocalCoordinates(pCoordinates, VERTICES_LOCAL_TO_SCENE_TMP);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float[] convertLocalToSceneCoordinates(float[] pCoordinates, float[] pReuse) {
        pReuse[0] = pCoordinates[0];
        pReuse[1] = pCoordinates[1];
        getLocalToSceneTransformation().transform(pReuse);
        return pReuse;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float[] convertSceneToLocalCoordinates(float pX, float pY) {
        return convertSceneToLocalCoordinates(pX, pY, VERTICES_SCENE_TO_LOCAL_TMP);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float[] convertSceneToLocalCoordinates(float pX, float pY, float[] pReuse) {
        pReuse[0] = pX;
        pReuse[1] = pY;
        getSceneToLocalTransformation().transform(pReuse);
        return pReuse;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float[] convertSceneToLocalCoordinates(float[] pCoordinates) {
        return convertSceneToLocalCoordinates(pCoordinates, VERTICES_SCENE_TO_LOCAL_TMP);
    }

    @Override // org.anddev.andengine.entity.IEntity
    public float[] convertSceneToLocalCoordinates(float[] pCoordinates, float[] pReuse) {
        pReuse[0] = pCoordinates[0];
        pReuse[1] = pCoordinates[1];
        getSceneToLocalTransformation().transform(pReuse);
        return pReuse;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void onAttached() {
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void onDetached() {
    }

    @Override // org.anddev.andengine.entity.IEntity
    public Object getUserData() {
        return this.mUserData;
    }

    @Override // org.anddev.andengine.entity.IEntity
    public void setUserData(Object pUserData) {
        this.mUserData = pUserData;
    }

    @Override // org.anddev.andengine.opengl.IDrawable
    public final void onDraw(GL10 pGL, Camera pCamera) {
        if (this.mVisible) {
            onManagedDraw(pGL, pCamera);
        }
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public final void onUpdate(float pSecondsElapsed) {
        if (!this.mIgnoreUpdate) {
            onManagedUpdate(pSecondsElapsed);
        }
    }

    @Override // org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
        this.mVisible = true;
        this.mIgnoreUpdate = false;
        this.mChildrenVisible = true;
        this.mChildrenIgnoreUpdate = false;
        this.mX = this.mInitialX;
        this.mY = this.mInitialY;
        this.mRotation = BitmapDescriptorFactory.HUE_RED;
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.mRed = 1.0f;
        this.mGreen = 1.0f;
        this.mBlue = 1.0f;
        this.mAlpha = 1.0f;
        if (this.mEntityModifiers != null) {
            this.mEntityModifiers.reset();
        }
        if (this.mChildren != null) {
            ArrayList<IEntity> entities = this.mChildren;
            for (int i = entities.size() - 1; i >= 0; i--) {
                entities.get(i).reset();
            }
        }
    }

    protected void doDraw(GL10 pGL, Camera pCamera) {
    }

    private void allocateEntityModifiers() {
        this.mEntityModifiers = new EntityModifierList(this, 4);
    }

    private void allocateChildren() {
        this.mChildren = new SmartList<>(4);
    }

    private void allocateUpdateHandlers() {
        this.mUpdateHandlers = new UpdateHandlerList(4);
    }

    protected void onApplyTransformations(GL10 pGL) {
        applyTranslation(pGL);
        applyRotation(pGL);
        applyScale(pGL);
    }

    protected void applyTranslation(GL10 pGL) {
        pGL.glTranslatef(this.mX, this.mY, BitmapDescriptorFactory.HUE_RED);
    }

    protected void applyRotation(GL10 pGL) {
        float rotation = this.mRotation;
        if (rotation != BitmapDescriptorFactory.HUE_RED) {
            float rotationCenterX = this.mRotationCenterX;
            float rotationCenterY = this.mRotationCenterY;
            pGL.glTranslatef(rotationCenterX, rotationCenterY, BitmapDescriptorFactory.HUE_RED);
            pGL.glRotatef(rotation, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 1.0f);
            pGL.glTranslatef(-rotationCenterX, -rotationCenterY, BitmapDescriptorFactory.HUE_RED);
        }
    }

    protected void applyScale(GL10 pGL) {
        float scaleX = this.mScaleX;
        float scaleY = this.mScaleY;
        if (scaleX != 1.0f || scaleY != 1.0f) {
            float scaleCenterX = this.mScaleCenterX;
            float scaleCenterY = this.mScaleCenterY;
            pGL.glTranslatef(scaleCenterX, scaleCenterY, BitmapDescriptorFactory.HUE_RED);
            pGL.glScalef(scaleX, scaleY, 1.0f);
            pGL.glTranslatef(-scaleCenterX, -scaleCenterY, BitmapDescriptorFactory.HUE_RED);
        }
    }

    protected void onManagedDraw(GL10 pGL, Camera pCamera) {
        pGL.glPushMatrix();
        onApplyTransformations(pGL);
        doDraw(pGL, pCamera);
        onDrawChildren(pGL, pCamera);
        pGL.glPopMatrix();
    }

    protected void onDrawChildren(GL10 pGL, Camera pCamera) {
        if (this.mChildren != null && this.mChildrenVisible) {
            onManagedDrawChildren(pGL, pCamera);
        }
    }

    public void onManagedDrawChildren(GL10 pGL, Camera pCamera) {
        ArrayList<IEntity> children = this.mChildren;
        int childCount = children.size();
        for (int i = 0; i < childCount; i++) {
            children.get(i).onDraw(pGL, pCamera);
        }
    }

    protected void onManagedUpdate(float pSecondsElapsed) {
        if (this.mEntityModifiers != null) {
            this.mEntityModifiers.onUpdate(pSecondsElapsed);
        }
        if (this.mUpdateHandlers != null) {
            this.mUpdateHandlers.onUpdate(pSecondsElapsed);
        }
        if (this.mChildren != null && !this.mChildrenIgnoreUpdate) {
            ArrayList<IEntity> entities = this.mChildren;
            int entityCount = entities.size();
            for (int i = 0; i < entityCount; i++) {
                entities.get(i).onUpdate(pSecondsElapsed);
            }
        }
    }
}
