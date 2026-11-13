package org.anddev.andengine.entity.scene.menu.item;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.font.Font;

/* JADX INFO: loaded from: classes.dex */
public class TextMenuItem extends Text implements IMenuItem {
    private final int mID;

    public TextMenuItem(int pID, Font pFont, String pText) {
        super(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, pFont, pText);
        this.mID = pID;
    }

    @Override // org.anddev.andengine.entity.scene.menu.item.IMenuItem
    public int getID() {
        return this.mID;
    }

    @Override // org.anddev.andengine.entity.scene.menu.item.IMenuItem
    public void onSelected() {
    }

    @Override // org.anddev.andengine.entity.scene.menu.item.IMenuItem
    public void onUnselected() {
    }
}
