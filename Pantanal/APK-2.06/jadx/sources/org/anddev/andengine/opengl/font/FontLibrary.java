package org.anddev.andengine.opengl.font;

import android.util.SparseArray;
import org.anddev.andengine.util.Library;

/* JADX INFO: loaded from: classes.dex */
public class FontLibrary extends Library<Font> {
    public FontLibrary() {
    }

    public FontLibrary(int pInitialCapacity) {
        super(pInitialCapacity);
    }

    void loadFonts(FontManager pFontManager) {
        SparseArray<T> sparseArray = this.mItems;
        for (int i = sparseArray.size() - 1; i >= 0; i--) {
            Font font = (Font) sparseArray.valueAt(i);
            if (font != null) {
                pFontManager.loadFont(font);
            }
        }
    }
}
