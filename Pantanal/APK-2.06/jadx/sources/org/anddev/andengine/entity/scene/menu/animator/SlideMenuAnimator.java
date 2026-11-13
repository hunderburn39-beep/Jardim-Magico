package org.anddev.andengine.entity.scene.menu.animator;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.util.HorizontalAlign;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public class SlideMenuAnimator extends BaseMenuAnimator {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$anddev$andengine$util$HorizontalAlign;

    static /* synthetic */ int[] $SWITCH_TABLE$org$anddev$andengine$util$HorizontalAlign() {
        int[] iArr = $SWITCH_TABLE$org$anddev$andengine$util$HorizontalAlign;
        if (iArr == null) {
            iArr = new int[HorizontalAlign.valuesCustom().length];
            try {
                iArr[HorizontalAlign.CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[HorizontalAlign.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[HorizontalAlign.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            $SWITCH_TABLE$org$anddev$andengine$util$HorizontalAlign = iArr;
        }
        return iArr;
    }

    public SlideMenuAnimator() {
    }

    public SlideMenuAnimator(IEaseFunction pEaseFunction) {
        super(pEaseFunction);
    }

    public SlideMenuAnimator(HorizontalAlign pHorizontalAlign) {
        super(pHorizontalAlign);
    }

    public SlideMenuAnimator(HorizontalAlign pHorizontalAlign, IEaseFunction pEaseFunction) {
        super(pHorizontalAlign, pEaseFunction);
    }

    public SlideMenuAnimator(float pMenuItemSpacing) {
        super(pMenuItemSpacing);
    }

    public SlideMenuAnimator(float pMenuItemSpacing, IEaseFunction pEaseFunction) {
        super(pMenuItemSpacing, pEaseFunction);
    }

    public SlideMenuAnimator(HorizontalAlign pHorizontalAlign, float pMenuItemSpacing) {
        super(pHorizontalAlign, pMenuItemSpacing);
    }

    public SlideMenuAnimator(HorizontalAlign pHorizontalAlign, float pMenuItemSpacing, IEaseFunction pEaseFunction) {
        super(pHorizontalAlign, pMenuItemSpacing, pEaseFunction);
    }

    @Override // org.anddev.andengine.entity.scene.menu.animator.IMenuAnimator
    public void buildAnimations(ArrayList<IMenuItem> pMenuItems, float pCameraWidth, float pCameraHeight) {
        float offsetX;
        IEaseFunction easeFunction = this.mEaseFunction;
        float maximumWidth = getMaximumWidth(pMenuItems);
        float overallHeight = getOverallHeight(pMenuItems);
        float baseX = (pCameraWidth - maximumWidth) * 0.5f;
        float baseY = (pCameraHeight - overallHeight) * 0.5f;
        float offsetY = BitmapDescriptorFactory.HUE_RED;
        int menuItemCount = pMenuItems.size();
        for (int i = 0; i < menuItemCount; i++) {
            IMenuItem menuItem = pMenuItems.get(i);
            switch ($SWITCH_TABLE$org$anddev$andengine$util$HorizontalAlign()[this.mHorizontalAlign.ordinal()]) {
                case 1:
                    offsetX = BitmapDescriptorFactory.HUE_RED;
                    break;
                case 2:
                default:
                    offsetX = (maximumWidth - menuItem.getWidthScaled()) * 0.5f;
                    break;
                case 3:
                    offsetX = maximumWidth - menuItem.getWidthScaled();
                    break;
            }
            MoveModifier moveModifier = new MoveModifier(1.0f, -maximumWidth, baseX + offsetX, baseY + offsetY, baseY + offsetY, easeFunction);
            moveModifier.setRemoveWhenFinished(false);
            menuItem.registerEntityModifier(moveModifier);
            offsetY += menuItem.getHeight() + this.mMenuItemSpacing;
        }
    }

    @Override // org.anddev.andengine.entity.scene.menu.animator.IMenuAnimator
    public void prepareAnimations(ArrayList<IMenuItem> pMenuItems, float pCameraWidth, float pCameraHeight) {
        float maximumWidth = getMaximumWidth(pMenuItems);
        float overallHeight = getOverallHeight(pMenuItems);
        float baseY = (pCameraHeight - overallHeight) * 0.5f;
        float menuItemSpacing = this.mMenuItemSpacing;
        float offsetY = BitmapDescriptorFactory.HUE_RED;
        int menuItemCount = pMenuItems.size();
        for (int i = 0; i < menuItemCount; i++) {
            IMenuItem menuItem = pMenuItems.get(i);
            menuItem.setPosition(-maximumWidth, baseY + offsetY);
            offsetY += menuItem.getHeight() + menuItemSpacing;
        }
    }
}
