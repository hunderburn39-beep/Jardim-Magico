package org.anddev.andengine.opengl.texture.atlas.bitmap.source;

import android.content.Context;
import java.io.File;
import org.anddev.andengine.util.FileUtils;

/* JADX INFO: loaded from: classes.dex */
public class ExternalStorageFileBitmapTextureAtlasSource extends FileBitmapTextureAtlasSource {
    public ExternalStorageFileBitmapTextureAtlasSource(Context pContext, String pFilePath) {
        super(new File(FileUtils.getAbsolutePathOnExternalStorage(pContext, pFilePath)));
    }
}
