package org.anddev.andengine.opengl.texture.atlas.bitmap.source;

import android.content.Context;
import java.io.File;
import org.anddev.andengine.util.FileUtils;

/* JADX INFO: loaded from: classes.dex */
public class InternalStorageFileBitmapTextureAtlasSource extends FileBitmapTextureAtlasSource {
    public InternalStorageFileBitmapTextureAtlasSource(Context pContext, String pFilePath) {
        super(new File(FileUtils.getAbsolutePathOnInternalStorage(pContext, pFilePath)));
    }
}
