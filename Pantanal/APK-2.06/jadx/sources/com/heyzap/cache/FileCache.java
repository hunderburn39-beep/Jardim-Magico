package com.heyzap.cache;

import android.content.Context;
import com.heyzap.common.concurrency.ExecutorPool;
import com.heyzap.internal.Logger;
import com.heyzap.internal.Utils;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class FileCache {
    public static void cleanupOldCache(final Context context) {
        ExecutorPool.getInstance().execute(new Runnable() { // from class: com.heyzap.cache.FileCache.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Logger.debug("FileCache - Scrubbing old heyzap cache");
                    File cacheDir = new File(Utils.getCacheDirAbsolutePath(context));
                    FileCache.deleteDirectory(cacheDir);
                } catch (Exception e) {
                    Logger.debug("FileCache - Exception deleting cache: ", e);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void deleteDirectory(File dir) {
        try {
            Logger.debug("FileCache - cleaning directory: " + dir.getCanonicalPath());
            File[] files = dir.listFiles();
            if (files == null) {
                Logger.debug("FileCache - null files, returning");
                return;
            }
            Logger.debug("FileCache - Found " + files.length + " files");
            for (File f : files) {
                Logger.debug("FileCache - Deleting cache file: " + f.getCanonicalPath());
                try {
                    if (f.isDirectory()) {
                        deleteDirectory(f);
                        f.delete();
                    } else {
                        f.delete();
                    }
                } catch (Exception e) {
                    Logger.debug("Exception deleting file: " + f.getCanonicalPath(), e);
                }
            }
        } catch (Exception e2) {
            Logger.debug("FileCache - Exception deleting cache: ", e2);
        }
    }
}
