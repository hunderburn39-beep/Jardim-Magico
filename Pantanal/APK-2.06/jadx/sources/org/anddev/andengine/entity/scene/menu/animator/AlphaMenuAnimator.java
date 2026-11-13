package org.anddev.andengine.entity.scene.menu.animator;

import java.util.ArrayList;
import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.util.HorizontalAlign;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public class AlphaMenuAnimator extends BaseMenuAnimator {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$anddev$andengine$util$HorizontalAlign = null;
    private static final float ALPHA_FROM = 0.0f;
    private static final float ALPHA_TO = 1.0f;

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

    public AlphaMenuAnimator() {
    }

    public AlphaMenuAnimator(IEaseFunction pEaseFunction) {
        super(pEaseFunction);
    }

    public AlphaMenuAnimator(HorizontalAlign pHorizontalAlign) {
        super(pHorizontalAlign);
    }

    public AlphaMenuAnimator(HorizontalAlign pHorizontalAlign, IEaseFunction pEaseFunction) {
        super(pHorizontalAlign, pEaseFunction);
    }

    public AlphaMenuAnimator(float pMenuItemSpacing) {
        super(pMenuItemSpacing);
    }

    public AlphaMenuAnimator(float pMenuItemSpacing, IEaseFunction pEaseFunction) {
        super(pMenuItemSpacing, pEaseFunction);
    }

    public AlphaMenuAnimator(HorizontalAlign pHorizontalAlign, float pMenuItemSpacing) {
        super(pHorizontalAlign, pMenuItemSpacing);
    }

    public AlphaMenuAnimator(HorizontalAlign pHorizontalAlign, float pMenuItemSpacing, IEaseFunction pEaseFunction) {
        super(pHorizontalAlign, pMenuItemSpacing, pEaseFunction);
    }

    @Override // org.anddev.andengine.entity.scene.menu.animator.IMenuAnimator
    public void buildAnimations(ArrayList<IMenuItem> pMenuItems, float pCameraWidth, float pCameraHeight) {
        IEaseFunction easeFunction = this.mEaseFunction;
        int menuItemCount = pMenuItems.size();
        for (int i = menuItemCount - 1; i >= 0; i--) {
            AlphaModifier alphaModifier = new AlphaModifier(1.0f, 0.0f, 1.0f, easeFunction);
            alphaModifier.setRemoveWhenFinished(false);
            pMenuItems.get(i).registerEntityModifier(alphaModifier);
        }
    }

    @Override // org.anddev.andengine.entity.scene.menu.animator.IMenuAnimator
    public void prepareAnimations(ArrayList<IMenuItem> pMenuItems, float pCameraWidth, float pCameraHeight) {
        float offsetX;
        float maximumWidth = getMaximumWidth(pMenuItems);
        float overallHeight = getOverallHeight(pMenuItems);
        float baseX = (pCameraWidth - maximumWidth) * 0.5f;
        float baseY = (pCameraHeight - overallHeight) * 0.5f;
        float menuItemSpacing = this.mMenuItemSpacing;
        float offsetY = 0.0f;
        int menuItemCount = pMenuItems.size();
        for (int i = 0; i < menuItemCount; i++) {
            IMenuItem menuItem = pMenuItems.get(i);
            switch ($SWITCH_TABLE$org$anddev$andengine$util$HorizontalAlign()[this.mHorizontalAlign.ordinal()]) {
                case 1:
                    offsetX = 0.0f;
                    break;
                case 2:
                default:
                    offsetX = (maximumWidth - menuItem.getWidthScaled()) * 0.5f;
                    break;
                case 3:
                    offsetX = maximumWidth - menuItem.getWidthScaled();
                    break;
            }
            menuItem.setPosition(baseX + offsetX, baseY + offsetY);
            menuItem.setAlpha(0.0f);
            offsetY += menuItem.getHeight() + menuItemSpacing;
        }
    }
}
