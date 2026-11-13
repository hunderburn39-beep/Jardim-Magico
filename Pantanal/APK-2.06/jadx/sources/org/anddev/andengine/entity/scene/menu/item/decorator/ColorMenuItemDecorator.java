package org.anddev.andengine.entity.scene.menu.item.decorator;

import org.anddev.andengine.entity.scene.menu.item.IMenuItem;

/* JADX INFO: loaded from: classes.dex */
public class ColorMenuItemDecorator extends BaseMenuItemDecorator {
    private final float mSelectedBlue;
    private final float mSelectedGreen;
    private final float mSelectedRed;
    private final float mUnselectedBlue;
    private final float mUnselectedGreen;
    private final float mUnselectedRed;

    public ColorMenuItemDecorator(IMenuItem pMenuItem, float pSelectedRed, float pSelectedGreen, float pSelectedBlue, float pUnselectedRed, float pUnselectedGreen, float pUnselectedBlue) {
        super(pMenuItem);
        this.mSelectedRed = pSelectedRed;
        this.mSelectedGreen = pSelectedGreen;
        this.mSelectedBlue = pSelectedBlue;
        this.mUnselectedRed = pUnselectedRed;
        this.mUnselectedGreen = pUnselectedGreen;
        this.mUnselectedBlue = pUnselectedBlue;
        pMenuItem.setColor(this.mUnselectedRed, this.mUnselectedGreen, this.mUnselectedBlue);
    }

    @Override // org.anddev.andengine.entity.scene.menu.item.decorator.BaseMenuItemDecorator
    public void onMenuItemSelected(IMenuItem pMenuItem) {
        pMenuItem.setColor(this.mSelectedRed, this.mSelectedGreen, this.mSelectedBlue);
    }

    @Override // org.anddev.andengine.entity.scene.menu.item.decorator.BaseMenuItemDecorator
    public void onMenuItemUnselected(IMenuItem pMenuItem) {
        pMenuItem.setColor(this.mUnselectedRed, this.mUnselectedGreen, this.mUnselectedBlue);
    }

    @Override // org.anddev.andengine.entity.scene.menu.item.decorator.BaseMenuItemDecorator
    public void onMenuItemReset(IMenuItem pMenuItem) {
        pMenuItem.setColor(this.mUnselectedRed, this.mUnselectedGreen, this.mUnselectedBlue);
    }
}
