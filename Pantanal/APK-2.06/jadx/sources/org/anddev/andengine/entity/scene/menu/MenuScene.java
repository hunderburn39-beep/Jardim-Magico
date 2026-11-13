package org.anddev.andengine.entity.scene.menu;

import java.util.ArrayList;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.scene.CameraScene;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.menu.animator.IMenuAnimator;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.input.touch.TouchEvent;

/* JADX INFO: loaded from: classes.dex */
public class MenuScene extends CameraScene implements Scene.IOnAreaTouchListener, Scene.IOnSceneTouchListener {
    private IMenuAnimator mMenuAnimator;
    private final ArrayList<IMenuItem> mMenuItems;
    private IOnMenuItemClickListener mOnMenuItemClickListener;
    private IMenuItem mSelectedMenuItem;

    public interface IOnMenuItemClickListener {
        boolean onMenuItemClicked(MenuScene menuScene, IMenuItem iMenuItem, float f, float f2);
    }

    public MenuScene() {
        this(null, null);
    }

    public MenuScene(IOnMenuItemClickListener pOnMenuItemClickListener) {
        this(null, pOnMenuItemClickListener);
    }

    public MenuScene(Camera pCamera) {
        this(pCamera, null);
    }

    public MenuScene(Camera pCamera, IOnMenuItemClickListener pOnMenuItemClickListener) {
        super(pCamera);
        this.mMenuItems = new ArrayList<>();
        this.mMenuAnimator = IMenuAnimator.DEFAULT;
        this.mOnMenuItemClickListener = pOnMenuItemClickListener;
        setOnSceneTouchListener(this);
        setOnAreaTouchListener(this);
    }

    public IOnMenuItemClickListener getOnMenuItemClickListener() {
        return this.mOnMenuItemClickListener;
    }

    public void setOnMenuItemClickListener(IOnMenuItemClickListener pOnMenuItemClickListener) {
        this.mOnMenuItemClickListener = pOnMenuItemClickListener;
    }

    public int getMenuItemCount() {
        return this.mMenuItems.size();
    }

    public void addMenuItem(IMenuItem pMenuItem) {
        this.mMenuItems.add(pMenuItem);
        attachChild(pMenuItem);
        registerTouchArea(pMenuItem);
    }

    @Override // org.anddev.andengine.entity.scene.Scene
    public MenuScene getChildScene() {
        return (MenuScene) super.getChildScene();
    }

    @Override // org.anddev.andengine.entity.scene.Scene
    public void setChildScene(Scene pChildScene, boolean pModalDraw, boolean pModalUpdate, boolean pModalTouch) throws IllegalArgumentException {
        if (pChildScene instanceof MenuScene) {
            super.setChildScene(pChildScene, pModalDraw, pModalUpdate, pModalTouch);
            return;
        }
        throw new IllegalArgumentException("MenuScene accepts only MenuScenes as a ChildScene.");
    }

    @Override // org.anddev.andengine.entity.scene.Scene
    public void clearChildScene() {
        if (getChildScene() != null) {
            getChildScene().reset();
            super.clearChildScene();
        }
    }

    public void setMenuAnimator(IMenuAnimator pMenuAnimator) {
        this.mMenuAnimator = pMenuAnimator;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // org.anddev.andengine.entity.scene.Scene.IOnAreaTouchListener
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, Scene.ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        IMenuItem menuItem = (IMenuItem) pTouchArea;
        switch (pSceneTouchEvent.getAction()) {
            case 0:
            case 2:
                if (this.mSelectedMenuItem != null && this.mSelectedMenuItem != menuItem) {
                    this.mSelectedMenuItem.onUnselected();
                }
                this.mSelectedMenuItem = menuItem;
                this.mSelectedMenuItem.onSelected();
                return true;
            case 1:
                if (this.mOnMenuItemClickListener != null) {
                    boolean zOnMenuItemClicked = this.mOnMenuItemClickListener.onMenuItemClicked(this, menuItem, pTouchAreaLocalX, pTouchAreaLocalY);
                    menuItem.onUnselected();
                    this.mSelectedMenuItem = null;
                    return zOnMenuItemClicked;
                }
                return true;
            case 3:
                menuItem.onUnselected();
                this.mSelectedMenuItem = null;
                return true;
            default:
                return true;
        }
    }

    @Override // org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if (this.mSelectedMenuItem != null) {
            this.mSelectedMenuItem.onUnselected();
            this.mSelectedMenuItem = null;
            return false;
        }
        return false;
    }

    @Override // org.anddev.andengine.entity.scene.Scene
    public void back() {
        super.back();
        reset();
    }

    @Override // org.anddev.andengine.entity.scene.Scene, org.anddev.andengine.entity.Entity, org.anddev.andengine.engine.handler.IUpdateHandler
    public void reset() {
        super.reset();
        ArrayList<IMenuItem> menuItems = this.mMenuItems;
        for (int i = menuItems.size() - 1; i >= 0; i--) {
            menuItems.get(i).reset();
        }
        prepareAnimations();
    }

    public void closeMenuScene() {
        back();
    }

    public void buildAnimations() {
        prepareAnimations();
        float cameraWidthRaw = this.mCamera.getWidthRaw();
        float cameraHeightRaw = this.mCamera.getHeightRaw();
        this.mMenuAnimator.buildAnimations(this.mMenuItems, cameraWidthRaw, cameraHeightRaw);
    }

    public void prepareAnimations() {
        float cameraWidthRaw = this.mCamera.getWidthRaw();
        float cameraHeightRaw = this.mCamera.getHeightRaw();
        this.mMenuAnimator.prepareAnimations(this.mMenuItems, cameraWidthRaw, cameraHeightRaw);
    }
}
