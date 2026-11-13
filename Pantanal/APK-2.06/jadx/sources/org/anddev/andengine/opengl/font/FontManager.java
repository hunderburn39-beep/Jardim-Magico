package org.anddev.andengine.opengl.font;

import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;

/* JADX INFO: loaded from: classes.dex */
public class FontManager {
    private final ArrayList<Font> mFontsManaged = new ArrayList<>();

    public synchronized void clear() {
        this.mFontsManaged.clear();
    }

    public synchronized void loadFont(Font pFont) {
        try {
            if (pFont == null) {
                throw new IllegalArgumentException("pFont must not be null!");
            }
            this.mFontsManaged.add(pFont);
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void loadFonts(FontLibrary pFontLibrary) {
        pFontLibrary.loadFonts(this);
    }

    public void loadFonts(Font... pFonts) {
        for (int i = pFonts.length - 1; i >= 0; i--) {
            loadFont(pFonts[i]);
        }
    }

    public synchronized void updateFonts(GL10 pGL) {
        ArrayList<Font> fonts = this.mFontsManaged;
        int fontCount = fonts.size();
        if (fontCount > 0) {
            for (int i = fontCount - 1; i >= 0; i--) {
                fonts.get(i).update(pGL);
            }
        }
    }

    public synchronized void reloadFonts() {
        ArrayList<Font> managedFonts = this.mFontsManaged;
        for (int i = managedFonts.size() - 1; i >= 0; i--) {
            managedFonts.get(i).reload();
        }
    }
}
