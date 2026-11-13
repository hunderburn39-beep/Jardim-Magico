package org.anddev.andengine.entity.scene.menu.animator;

import java.util.ArrayList;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;

/* JADX INFO: loaded from: classes.dex */
public interface IMenuAnimator {
    public static final IMenuAnimator DEFAULT = new AlphaMenuAnimator();

    void buildAnimations(ArrayList<IMenuItem> arrayList, float f, float f2);

    void prepareAnimations(ArrayList<IMenuItem> arrayList, float f, float f2);
}
