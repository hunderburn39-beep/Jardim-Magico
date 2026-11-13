package org.anddev.andengine.opengl.texture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import javax.microedition.khronos.opengles.GL10;
import org.anddev.andengine.util.Debug;

/* JADX INFO: loaded from: classes.dex */
public class TextureManager {
    private final HashSet<ITexture> mTexturesManaged = new HashSet<>();
    private final ArrayList<ITexture> mTexturesLoaded = new ArrayList<>();
    private final ArrayList<ITexture> mTexturesToBeLoaded = new ArrayList<>();
    private final ArrayList<ITexture> mTexturesToBeUnloaded = new ArrayList<>();

    protected synchronized void clear() {
        this.mTexturesToBeLoaded.clear();
        this.mTexturesLoaded.clear();
        this.mTexturesManaged.clear();
    }

    public synchronized boolean loadTexture(ITexture pTexture) {
        boolean z;
        if (this.mTexturesManaged.contains(pTexture)) {
            this.mTexturesToBeUnloaded.remove(pTexture);
            z = false;
        } else {
            this.mTexturesManaged.add(pTexture);
            this.mTexturesToBeLoaded.add(pTexture);
            z = true;
        }
        return z;
    }

    public synchronized boolean unloadTexture(ITexture pTexture) {
        boolean z;
        if (this.mTexturesManaged.contains(pTexture)) {
            if (this.mTexturesLoaded.contains(pTexture)) {
                this.mTexturesToBeUnloaded.add(pTexture);
            } else if (this.mTexturesToBeLoaded.remove(pTexture)) {
                this.mTexturesManaged.remove(pTexture);
            }
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    public void loadTextures(ITexture... pTextures) {
        for (int i = pTextures.length - 1; i >= 0; i--) {
            loadTexture(pTextures[i]);
        }
    }

    public void unloadTextures(ITexture... pTextures) {
        for (int i = pTextures.length - 1; i >= 0; i--) {
            unloadTexture(pTextures[i]);
        }
    }

    public synchronized void reloadTextures() {
        HashSet<ITexture> managedTextures = this.mTexturesManaged;
        for (ITexture texture : managedTextures) {
            texture.setLoadedToHardware(false);
        }
        this.mTexturesToBeLoaded.addAll(this.mTexturesLoaded);
        this.mTexturesLoaded.clear();
        this.mTexturesManaged.removeAll(this.mTexturesToBeUnloaded);
        this.mTexturesToBeUnloaded.clear();
    }

    /* JADX WARN: Code duplicated, block: B:11:0x001d A[Catch: all -> 0x0047, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x000f, B:20:0x0030, B:22:0x003c, B:25:0x0043, B:7:0x0013, B:9:0x0019, B:30:0x004a, B:32:0x0056, B:33:0x0059, B:35:0x0060, B:11:0x001d, B:13:0x0023, B:36:0x0064, B:38:0x0070, B:39:0x0073, B:17:0x002b), top: B:41:0x0001, inners: #1, #2 }] */
    /* JADX WARN: Code duplicated, block: B:13:0x0023 A[Catch: all -> 0x0047, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x000f, B:20:0x0030, B:22:0x003c, B:25:0x0043, B:7:0x0013, B:9:0x0019, B:30:0x004a, B:32:0x0056, B:33:0x0059, B:35:0x0060, B:11:0x001d, B:13:0x0023, B:36:0x0064, B:38:0x0070, B:39:0x0073, B:17:0x002b), top: B:41:0x0001, inners: #1, #2 }] */
    /* JADX WARN: Code duplicated, block: B:16:0x0029 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:17:0x002b A[Catch: all -> 0x0047, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x000f, B:20:0x0030, B:22:0x003c, B:25:0x0043, B:7:0x0013, B:9:0x0019, B:30:0x004a, B:32:0x0056, B:33:0x0059, B:35:0x0060, B:11:0x001d, B:13:0x0023, B:36:0x0064, B:38:0x0070, B:39:0x0073, B:17:0x002b), top: B:41:0x0001, inners: #1, #2 }] */
    /* JADX WARN: Code duplicated, block: B:30:0x004a A[Catch: all -> 0x0047, TRY_ENTER, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x000f, B:20:0x0030, B:22:0x003c, B:25:0x0043, B:7:0x0013, B:9:0x0019, B:30:0x004a, B:32:0x0056, B:33:0x0059, B:35:0x0060, B:11:0x001d, B:13:0x0023, B:36:0x0064, B:38:0x0070, B:39:0x0073, B:17:0x002b), top: B:41:0x0001, inners: #1, #2 }] */
    /* JADX WARN: Code duplicated, block: B:36:0x0064 A[Catch: all -> 0x0047, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x000f, B:20:0x0030, B:22:0x003c, B:25:0x0043, B:7:0x0013, B:9:0x0019, B:30:0x004a, B:32:0x0056, B:33:0x0059, B:35:0x0060, B:11:0x001d, B:13:0x0023, B:36:0x0064, B:38:0x0070, B:39:0x0073, B:17:0x002b), top: B:41:0x0001, inners: #1, #2 }] */
    /* JADX WARN: Code duplicated, block: B:38:0x0070 A[Catch: all -> 0x0047, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x000f, B:20:0x0030, B:22:0x003c, B:25:0x0043, B:7:0x0013, B:9:0x0019, B:30:0x004a, B:32:0x0056, B:33:0x0059, B:35:0x0060, B:11:0x001d, B:13:0x0023, B:36:0x0064, B:38:0x0070, B:39:0x0073, B:17:0x002b), top: B:41:0x0001, inners: #1, #2 }] */
    /* JADX WARN: Code duplicated, block: B:45:0x0056 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:53:0x0059 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:9:0x0019 A[Catch: all -> 0x0047, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x000f, B:20:0x0030, B:22:0x003c, B:25:0x0043, B:7:0x0013, B:9:0x0019, B:30:0x004a, B:32:0x0056, B:33:0x0059, B:35:0x0060, B:11:0x001d, B:13:0x0023, B:36:0x0064, B:38:0x0070, B:39:0x0073, B:17:0x002b), top: B:41:0x0001, inners: #1, #2 }] */
    public synchronized void updateTextures(GL10 pGL) {
        int texturesToBeLoadedCount;
        int texturesToBeUnloadedCount;
        int i;
        ITexture textureToBeUnloaded;
        int i2;
        ITexture textureToBeLoaded;
        HashSet<ITexture> texturesManaged = this.mTexturesManaged;
        ArrayList<ITexture> texturesLoaded = this.mTexturesLoaded;
        ArrayList<ITexture> texturesToBeLoaded = this.mTexturesToBeLoaded;
        ArrayList<ITexture> texturesToBeUnloaded = this.mTexturesToBeUnloaded;
        int textursLoadedCount = texturesLoaded.size();
        if (textursLoadedCount > 0) {
            for (int i3 = textursLoadedCount - 1; i3 >= 0; i3--) {
                ITexture textureToBeReloaded = texturesLoaded.get(i3);
                if (textureToBeReloaded.isUpdateOnHardwareNeeded()) {
                    try {
                        textureToBeReloaded.reloadToHardware(pGL);
                    } catch (IOException e) {
                        Debug.e(e);
                    }
                }
            }
            texturesToBeLoadedCount = texturesToBeLoaded.size();
            if (texturesToBeLoadedCount > 0) {
                for (i2 = texturesToBeLoadedCount - 1; i2 >= 0; i2--) {
                    textureToBeLoaded = texturesToBeLoaded.remove(i2);
                    if (!textureToBeLoaded.isLoadedToHardware()) {
                        try {
                            textureToBeLoaded.loadToHardware(pGL);
                        } catch (IOException e2) {
                            Debug.e(e2);
                        }
                    }
                    texturesLoaded.add(textureToBeLoaded);
                }
                texturesToBeUnloadedCount = texturesToBeUnloaded.size();
                if (texturesToBeUnloadedCount > 0) {
                    for (i = texturesToBeUnloadedCount - 1; i >= 0; i--) {
                        textureToBeUnloaded = texturesToBeUnloaded.remove(i);
                        if (textureToBeUnloaded.isLoadedToHardware()) {
                            textureToBeUnloaded.unloadFromHardware(pGL);
                        }
                        texturesLoaded.remove(textureToBeUnloaded);
                        texturesManaged.remove(textureToBeUnloaded);
                    }
                }
                if (texturesToBeLoadedCount <= 0 || texturesToBeUnloadedCount > 0) {
                    System.gc();
                }
            } else {
                texturesToBeUnloadedCount = texturesToBeUnloaded.size();
                if (texturesToBeUnloadedCount > 0) {
                    while (i >= 0) {
                        textureToBeUnloaded = texturesToBeUnloaded.remove(i);
                        if (textureToBeUnloaded.isLoadedToHardware()) {
                            textureToBeUnloaded.unloadFromHardware(pGL);
                        }
                        texturesLoaded.remove(textureToBeUnloaded);
                        texturesManaged.remove(textureToBeUnloaded);
                    }
                }
                if (texturesToBeLoadedCount <= 0) {
                    System.gc();
                } else {
                    System.gc();
                }
            }
        } else {
            texturesToBeLoadedCount = texturesToBeLoaded.size();
            if (texturesToBeLoadedCount > 0) {
                while (i2 >= 0) {
                    textureToBeLoaded = texturesToBeLoaded.remove(i2);
                    if (!textureToBeLoaded.isLoadedToHardware()) {
                        textureToBeLoaded.loadToHardware(pGL);
                    }
                    texturesLoaded.add(textureToBeLoaded);
                }
                texturesToBeUnloadedCount = texturesToBeUnloaded.size();
                if (texturesToBeUnloadedCount > 0) {
                    while (i >= 0) {
                        textureToBeUnloaded = texturesToBeUnloaded.remove(i);
                        if (textureToBeUnloaded.isLoadedToHardware()) {
                            textureToBeUnloaded.unloadFromHardware(pGL);
                        }
                        texturesLoaded.remove(textureToBeUnloaded);
                        texturesManaged.remove(textureToBeUnloaded);
                    }
                }
                if (texturesToBeLoadedCount <= 0) {
                    System.gc();
                } else {
                    System.gc();
                }
            } else {
                texturesToBeUnloadedCount = texturesToBeUnloaded.size();
                if (texturesToBeUnloadedCount > 0) {
                    while (i >= 0) {
                        textureToBeUnloaded = texturesToBeUnloaded.remove(i);
                        if (textureToBeUnloaded.isLoadedToHardware()) {
                            textureToBeUnloaded.unloadFromHardware(pGL);
                        }
                        texturesLoaded.remove(textureToBeUnloaded);
                        texturesManaged.remove(textureToBeUnloaded);
                    }
                }
                if (texturesToBeLoadedCount <= 0) {
                    System.gc();
                } else {
                    System.gc();
                }
            }
        }
        throw th;
    }
}
