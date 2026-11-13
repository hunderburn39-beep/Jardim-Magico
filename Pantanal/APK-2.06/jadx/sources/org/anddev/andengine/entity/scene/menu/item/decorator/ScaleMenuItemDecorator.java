package org.anddev.andengine.entity.scene.menu.item.decorator;

import org.anddev.andengine.entity.scene.menu.item.IMenuItem;

/* JADX INFO: loaded from: classes.dex */
public class ScaleMenuItemDecorator extends BaseMenuItemDecorator {
    private final float mSelectedScale;
    private final float mUnselectedScale;

    public ScaleMenuItemDecorator(IMenuItem pMenuItem, float pSelectedScale, float pUnselectedScale) {
        super(pMenuItem);
        this.mSelectedScale = pSelectedScale;
        this.mUnselectedScale = pUnselectedScale;
        pMenuItem.setScale(pUnselectedScale);
    }

    @Override // org.anddev.andengine.entity.scene.menu.item.decorator.BaseMenuItemDecorator
    public void onMenuItemSelected(IMenuItem pMenuItem) {
        setScale(this.mSelectedScale);
    }

    @Override // org.anddev.andengine.entity.scene.menu.item.decorator.BaseMenuItemDecorator
    public void onMenuItemUnselected(IMenuItem pMenuItem) {
        setScale(this.mUnselectedScale);
    }

    @Override // org.anddev.andengine.entity.scene.menu.item.decorator.BaseMenuItemDecorator
    public void onMenuItemReset(IMenuItem pMenuItem) {
        setScale(this.mUnselectedScale);
    }
}
