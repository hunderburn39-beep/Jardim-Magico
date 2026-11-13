package org.anddev.andengine.entity.scene.menu.animator;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.util.HorizontalAlign;

/* JADX INFO: loaded from: classes.dex */
public class DirectMenuAnimator extends BaseMenuAnimator {
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

    public DirectMenuAnimator() {
    }

    public DirectMenuAnimator(HorizontalAlign pHorizontalAlign) {
        super(pHorizontalAlign);
    }

    public DirectMenuAnimator(float pMenuItemSpacing) {
        super(pMenuItemSpacing);
    }

    public DirectMenuAnimator(HorizontalAlign pHorizontalAlign, float pMenuItemSpacing) {
        super(pHorizontalAlign, pMenuItemSpacing);
    }

    @Override // org.anddev.andengine.entity.scene.menu.animator.IMenuAnimator
    public void buildAnimations(ArrayList<IMenuItem> pMenuItems, float pCameraWidth, float pCameraHeight) {
    }

    @Override // org.anddev.andengine.entity.scene.menu.animator.IMenuAnimator
    public void prepareAnimations(ArrayList<IMenuItem> pMenuItems, float pCameraWidth, float pCameraHeight) {
        float offsetX;
        float maximumWidth = getMaximumWidth(pMenuItems);
        float overallHeight = getOverallHeight(pMenuItems);
        float baseX = (pCameraWidth - maximumWidth) * 0.5f;
        float baseY = (pCameraHeight - overallHeight) * 0.5f;
        float menuItemSpacing = this.mMenuItemSpacing;
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
            menuItem.setPosition(baseX + offsetX, baseY + offsetY);
            offsetY += menuItem.getHeight() + menuItemSpacing;
        }
    }
}
