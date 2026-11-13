package org.anddev.andengine.entity.scene;

import android.annotation.SuppressLint;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.runnable.RunnableHandler;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.scene.background.IBackground;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.util.GLHelper;
import org.anddev.andengine.util.IMatcher;
import org.anddev.andengine.util.SmartList;

/* JADX INFO: loaded from: classes.dex */
public class Scene extends Entity {
    private static final int TOUCHAREAS_CAPACITY_DEFAULT = 4;
    protected Scene mChildScene;
    private boolean mChildSceneModalDraw;
    private boolean mChildSceneModalTouch;
    private boolean mChildSceneModalUpdate;
    private IOnAreaTouchListener mOnAreaTouchListener;
    private IOnSceneTouchListener mOnSceneTouchListener;
    protected Scene mParentScene;
    private float mSecondsElapsedTotal;
    protected SmartList<ITouchArea> mTouchAreas = new SmartList<>(4);
    private final RunnableHandler mRunnableHandler = new RunnableHandler();
    private IBackground mBackground = new ColorBackground(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
    private boolean mBackgroundEnabled = true;
    private boolean mOnAreaTouchTraversalBackToFront = true;
    private boolean mTouchAreaBindingEnabled = false;
    private final SparseArray<ITouchArea> mTouchAreaBindings = new SparseArray<>();
    private boolean mOnSceneTouchListenerBindingEnabled = false;
    private final SparseArray<IOnSceneTouchListener> mOnSceneTouchListenerBindings = new SparseArray<>();

    public interface IOnAreaTouchListener {
        boolean onAreaTouched(TouchEvent touchEvent, ITouchArea iTouchArea, float f, float f2);
    }

    public interface IOnSceneTouchListener {
        boolean onSceneTouchEvent(Scene scene, TouchEvent touchEvent);
    }

    public interface ITouchArea {

        public interface ITouchAreaMatcher extends IMatcher<ITouchArea> {
        }

        boolean contains(float f, float f2);

        float[] convertLocalToSceneCoordinates(float f, float f2);

        float[] convertSceneToLocalCoordinates(float f, float f2);

        boolean onAreaTouched(TouchEvent touchEvent, float f, float f2);
    }

    public Scene() {
    }

    @Deprecated
    public Scene(int pChildCount) {
        for (int i = 0; i < pChildCount; i++) {
            attachChild(new Entity());
        }
    }

    public float getSecondsElapsedTotal() {
        return this.mSecondsElapsedTotal;
    }

    public IBackground getBackground() {
        return this.mBackground;
    }

    public void setBackground(IBackground pBackground) {
        this.mBackground = pBackground;
    }

    public boolean isBackgroundEnabled() {
        return this.mBackgroundEnabled;
    }

    public void setBackgroundEnabled(boolean pEnabled) {
        this.mBackgroundEnabled = pEnabled;
    }

    public void setOnSceneTouchListener(IOnSceneTouchListener pOnSceneTouchListener) {
        this.mOnSceneTouchListener = pOnSceneTouchListener;
    }

    public IOnSceneTouchListener getOnSceneTouchListener() {
        return this.mOnSceneTouchListener;
    }

    public boolean hasOnSceneTouchListener() {
        return this.mOnSceneTouchListener != null;
    }

    public void setOnAreaTouchListener(IOnAreaTouchListener pOnAreaTouchListener) {
        this.mOnAreaTouchListener = pOnAreaTouchListener;
    }

    public IOnAreaTouchListener getOnAreaTouchListener() {
        return this.mOnAreaTouchListener;
    }

    public boolean hasOnAreaTouchListener() {
        return this.mOnAreaTouchListener != null;
    }

    private void setParentScene(Scene pParentScene) {
        this.mParentScene = pParentScene;
    }

    public boolean hasChildScene() {
        return this.mChildScene != null;
    }

    public Scene getChildScene() {
        return this.mChildScene;
    }

    public void setChildSceneModal(Scene pChildScene) {
        setChildScene(pChildScene, true, true, true);
    }

    public void setChildScene(Scene pChildScene) {
        setChildScene(pChildScene, false, false, false);
    }

    public void setChildScene(Scene pChildScene, boolean pModalDraw, boolean pModalUpdate, boolean pModalTouch) {
        pChildScene.setParentScene(this);
        this.mChildScene = pChildScene;
        this.mChildSceneModalDraw = pModalDraw;
        this.mChildSceneModalUpdate = pModalUpdate;
        this.mChildSceneModalTouch = pModalTouch;
    }

    public void clearChildScene() {
        this.mChildScene = null;
    }

    public void setOnAreaTouchTraversalBackToFront() {
        this.mOnAreaTouchTraversalBackToFront = true;
    }

    public void setOnAreaTouchTraversalFrontToBack() {
        this.mOnAreaTouchTraversalBackToFront = false;
    }

    public boolean isTouchAreaBindingEnabled() {
        return this.mTouchAreaBindingEnabled;
    }

    public void setTouchAreaBindingEnabled(boolean pTouchAreaBindingEnabled) {
        if (this.mTouchAreaBindingEnabled && !pTouchAreaBindingEnabled) {
            this.mTouchAreaBindings.clear();
        }
        this.mTouchAreaBindingEnabled = pTouchAreaBindingEnabled;
    }

    public boolean isOnSceneTouchListenerBindingEnabled() {
        return this.mOnSceneTouchListenerBindingEnabled;
    }

    public void setOnSceneTouchListenerBindingEnabled(boolean pOnSceneTouchListenerBindingEnabled) {
        if (this.mOnSceneTouchListenerBindingEnabled && !pOnSceneTouchListenerBindingEnabled) {
            this.mOnSceneTouchListenerBindings.clear();
        }
        this.mOnSceneTouchListenerBindingEnabled = pOnSceneTouchListenerBindingEnabled;
    }

    @Override // org.anddev.andengine.entity.Entity
    @SuppressLint({"WrongCall"})
    protected void onManagedDraw(GL10 pGL, Camera pCamera) {
        Scene childScene = this.mChildScene;
        if (childScene == null || !this.mChildSceneModalDraw) {
            if (this.mBackgroundEnabled) {
                pCamera.onApplySceneBackgroundMatrix(pGL);
                GLHelper.setModelViewIdentityMatrix(pGL);
                this.mBackground.onDraw(pGL, pCamera);
            }
            pCamera.onApplySceneMatrix(pGL);
            GLHelper.setModelViewIdentityMatrix(pGL);
            super.onManagedDraw(pGL, pCamera);
        }
        if (childScene != null) {
            childScene.onDraw(pGL, pCamera);
        }
    }

    @Override // org.anddev.andengine.entity.Entity
    protected void onManagedUpdate(float pSecondsElapsed) {
        this.mSecondsElapsedTotal += pSecondsElapsed;
        this.mRunnableHandler.onUpdate(pSecondsElapsed);
        Scene childScene = this.mChildScene;
        if (childScene == null || !this.mChildSceneModalUpdate) {
            this.mBackground.onUpdate(pSecondsElapsed);
            super.onManagedUpdate(pSecondsElapsed);
        }
        if (childScene != null) {
            childScene.onUpdate(pSecondsElapsed);
        }
    }

    public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
        Boolean handled;
        int touchAreaCount;
        Boolean handled2;
        Boolean handled3;
        try {
            int action = pSceneTouchEvent.getAction();
            boolean isActionDown = pSceneTouchEvent.isActionDown();
            if (!isActionDown) {
                if (this.mOnSceneTouchListenerBindingEnabled) {
                    IOnSceneTouchListener boundOnSceneTouchListener = this.mOnSceneTouchListenerBindings.get(pSceneTouchEvent.getPointerID());
                    if (boundOnSceneTouchListener != null) {
                        switch (action) {
                            case 1:
                            case 3:
                                this.mOnSceneTouchListenerBindings.remove(pSceneTouchEvent.getPointerID());
                                break;
                        }
                        Boolean handled4 = Boolean.valueOf(this.mOnSceneTouchListener.onSceneTouchEvent(this, pSceneTouchEvent));
                        if (handled4 != null && handled4.booleanValue()) {
                            return true;
                        }
                    }
                }
                if (this.mTouchAreaBindingEnabled) {
                    SparseArray<ITouchArea> touchAreaBindings = this.mTouchAreaBindings;
                    if (touchAreaBindings.size() > 0) {
                        Log.d("MIKE", "PASSEI POR AQUI MIKE");
                        ITouchArea boundTouchArea = touchAreaBindings.get(pSceneTouchEvent.getPointerID());
                        if (boundTouchArea != null) {
                            float sceneTouchEventX = pSceneTouchEvent.getX();
                            float sceneTouchEventY = pSceneTouchEvent.getY();
                            switch (action) {
                                case 1:
                                case 3:
                                    touchAreaBindings.remove(pSceneTouchEvent.getPointerID());
                                    break;
                            }
                            Boolean handled5 = onAreaTouchEvent(pSceneTouchEvent, sceneTouchEventX, sceneTouchEventY, boundTouchArea);
                            if (handled5 != null && handled5.booleanValue()) {
                                return true;
                            }
                        }
                    }
                }
            }
            Scene childScene = this.mChildScene;
            if (childScene != null) {
                boolean handledByChild = onChildSceneTouchEvent(pSceneTouchEvent);
                if (handledByChild) {
                    return true;
                }
                if (this.mChildSceneModalTouch) {
                    return false;
                }
            }
            float sceneTouchEventX2 = pSceneTouchEvent.getX();
            float sceneTouchEventY2 = pSceneTouchEvent.getY();
            ArrayList<ITouchArea> touchAreas = this.mTouchAreas;
            if (touchAreas != null && (touchAreaCount = touchAreas.size()) > 0) {
                if (this.mOnAreaTouchTraversalBackToFront) {
                    for (int i = 0; i < touchAreaCount; i++) {
                        ITouchArea touchArea = touchAreas.get(i);
                        if (touchArea.contains(sceneTouchEventX2, sceneTouchEventY2) && (handled3 = onAreaTouchEvent(pSceneTouchEvent, sceneTouchEventX2, sceneTouchEventY2, touchArea)) != null && handled3.booleanValue()) {
                            if (this.mTouchAreaBindingEnabled && isActionDown) {
                                this.mTouchAreaBindings.put(pSceneTouchEvent.getPointerID(), touchArea);
                            }
                            return true;
                        }
                    }
                } else {
                    for (int i2 = touchAreaCount - 1; i2 >= 0; i2--) {
                        ITouchArea touchArea2 = touchAreas.get(i2);
                        if (touchArea2.contains(sceneTouchEventX2, sceneTouchEventY2) && (handled2 = onAreaTouchEvent(pSceneTouchEvent, sceneTouchEventX2, sceneTouchEventY2, touchArea2)) != null && handled2.booleanValue()) {
                            if (this.mTouchAreaBindingEnabled && isActionDown) {
                                this.mTouchAreaBindings.put(pSceneTouchEvent.getPointerID(), touchArea2);
                            }
                            return true;
                        }
                    }
                }
            }
            if (this.mOnSceneTouchListener != null && (handled = Boolean.valueOf(this.mOnSceneTouchListener.onSceneTouchEvent(this, pSceneTouchEvent))) != null && handled.booleanValue()) {
                if (this.mOnSceneTouchListenerBindingEnabled && isActionDown) {
                    this.mOnSceneTouchListenerBindings.put(pSceneTouchEvent.getPointerID(), this.mOnSceneTouchListener);
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private Boolean onAreaTouchEvent(TouchEvent pSceneTouchEvent, float sceneTouchEventX, float sceneTouchEventY, ITouchArea touchArea) {
        float[] touchAreaLocalCoordinates = touchArea.convertSceneToLocalCoordinates(sceneTouchEventX, sceneTouchEventY);
        float touchAreaLocalX = touchAreaLocalCoordinates[0];
        float touchAreaLocalY = touchAreaLocalCoordinates[1];
        boolean handledSelf = touchArea.onAreaTouched(pSceneTouchEvent, touchAreaLocalX, touchAreaLocalY);
        if (handledSelf) {
            return Boolean.TRUE;
        }
        if (this.mOnAreaTouchListener != null) {
            return Boolean.valueOf(this.mOnAreaTouchListener.onAreaTouched(pSceneTouchEvent, touchArea, touchAreaLocalX, touchAreaLocalY));
        }
        return null;
    }

    protected boolean onChildSceneTouchEvent(TouchEvent pSceneTouchEvent) {
        return this.mChildScene.onSceneTouchEvent(pSceneTouchEvent);
    }

    @Override // org.anddev.andengine.entity.Entity, org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
        super.reset();
        clearChildScene();
    }

    @Override // org.anddev.andengine.entity.Entity, org.anddev.andengine.entity.IEntity
    public void setParent(IEntity pEntity) {
    }

    public void postRunnable(Runnable pRunnable) {
        this.mRunnableHandler.postRunnable(pRunnable);
    }

    public void registerTouchArea(ITouchArea pTouchArea) {
        this.mTouchAreas.add(pTouchArea);
    }

    public boolean unregisterTouchArea(ITouchArea pTouchArea) {
        return this.mTouchAreas.remove(pTouchArea);
    }

    public boolean unregisterTouchAreas(ITouchArea.ITouchAreaMatcher pTouchAreaMatcher) {
        return this.mTouchAreas.removeAll(pTouchAreaMatcher);
    }

    public void clearTouchAreas() {
        this.mTouchAreas.clear();
    }

    public ArrayList<ITouchArea> getTouchAreas() {
        return this.mTouchAreas;
    }

    public void back() {
        clearChildScene();
        if (this.mParentScene != null) {
            this.mParentScene.clearChildScene();
            this.mParentScene = null;
        }
    }
}
