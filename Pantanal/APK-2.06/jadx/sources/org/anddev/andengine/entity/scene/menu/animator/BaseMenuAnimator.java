package org.anddev.andengine.entity.scene.menu.animator;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.util.HorizontalAlign;
import org.anddev.andengine.util.modifier.ease.IEaseFunction;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseMenuAnimator implements IMenuAnimator {
    protected static final float DURATION = 1.0f;
    private static final HorizontalAlign HORIZONTALALIGN_DEFAULT = HorizontalAlign.CENTER;
    private static final float MENUITEMSPACING_DEFAULT = 1.0f;
    protected final IEaseFunction mEaseFunction;
    protected final HorizontalAlign mHorizontalAlign;
    protected final float mMenuItemSpacing;

    public BaseMenuAnimator() {
        this(1.0f);
    }

    public BaseMenuAnimator(IEaseFunction pEaseFunction) {
        this(1.0f, pEaseFunction);
    }

    public BaseMenuAnimator(float pMenuItemSpacing) {
        this(HORIZONTALALIGN_DEFAULT, pMenuItemSpacing);
    }

    public BaseMenuAnimator(float pMenuItemSpacing, IEaseFunction pEaseFunction) {
        this(HORIZONTALALIGN_DEFAULT, pMenuItemSpacing, pEaseFunction);
    }

    public BaseMenuAnimator(HorizontalAlign pHorizontalAlign) {
        this(pHorizontalAlign, 1.0f);
    }

    public BaseMenuAnimator(HorizontalAlign pHorizontalAlign, IEaseFunction pEaseFunction) {
        this(pHorizontalAlign, 1.0f, pEaseFunction);
    }

    public BaseMenuAnimator(HorizontalAlign pHorizontalAlign, float pMenuItemSpacing) {
        this(pHorizontalAlign, pMenuItemSpacing, IEaseFunction.DEFAULT);
    }

    public BaseMenuAnimator(HorizontalAlign pHorizontalAlign, float pMenuItemSpacing, IEaseFunction pEaseFunction) {
        this.mHorizontalAlign = pHorizontalAlign;
        this.mMenuItemSpacing = pMenuItemSpacing;
        this.mEaseFunction = pEaseFunction;
    }

    protected float getMaximumWidth(ArrayList<IMenuItem> pMenuItems) {
        float maximumWidth = Float.MIN_VALUE;
        for (int i = pMenuItems.size() - 1; i >= 0; i--) {
            IMenuItem menuItem = pMenuItems.get(i);
            maximumWidth = Math.max(maximumWidth, menuItem.getWidthScaled());
        }
        return maximumWidth;
    }

    protected float getOverallHeight(ArrayList<IMenuItem> pMenuItems) {
        float overallHeight = BitmapDescriptorFactory.HUE_RED;
        for (int i = pMenuItems.size() - 1; i >= 0; i--) {
            IMenuItem menuItem = pMenuItems.get(i);
            overallHeight += menuItem.getHeight();
        }
        return overallHeight + ((pMenuItems.size() - 1) * this.mMenuItemSpacing);
    }
}
