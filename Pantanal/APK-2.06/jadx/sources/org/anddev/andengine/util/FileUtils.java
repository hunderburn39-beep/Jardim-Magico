package org.anddev.andengine.util;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class FileUtils {
    public static void copyToExternalStorage(Context pContext, int pSourceResourceID, String pFilename) throws FileNotFoundException {
        copyToExternalStorage(pContext, pContext.getResources().openRawResource(pSourceResourceID), pFilename);
    }

    public static void copyToInternalStorage(Context pContext, int pSourceResourceID, String pFilename) throws FileNotFoundException {
        copyToInternalStorage(pContext, pContext.getResources().openRawResource(pSourceResourceID), pFilename);
    }

    public static void copyToExternalStorage(Context pContext, String pSourceAssetPath, String pFilename) throws IOException {
        copyToExternalStorage(pContext, pContext.getAssets().open(pSourceAssetPath), pFilename);
    }

    public static void copyToInternalStorage(Context pContext, String pSourceAssetPath, String pFilename) throws IOException {
        copyToInternalStorage(pContext, pContext.getAssets().open(pSourceAssetPath), pFilename);
    }

    private static void copyToInternalStorage(Context pContext, InputStream pInputStream, String pFilename) throws FileNotFoundException {
        StreamUtils.copyAndClose(pInputStream, new FileOutputStream(new File(pContext.getFilesDir(), pFilename)));
    }

    public static void copyToExternalStorage(Context pContext, InputStream pInputStream, String pFilePath) throws FileNotFoundException {
        if (isExternalStorageWriteable()) {
            String absoluteFilePath = getAbsolutePathOnExternalStorage(pContext, pFilePath);
            StreamUtils.copyAndClose(pInputStream, new FileOutputStream(absoluteFilePath));
            return;
        }
        throw new IllegalStateException("External Storage is not writeable.");
    }

    public static boolean isFileExistingOnExternalStorage(Context pContext, String pFilePath) {
        if (isExternalStorageReadable()) {
            String absoluteFilePath = getAbsolutePathOnExternalStorage(pContext, pFilePath);
            File file = new File(absoluteFilePath);
            return file.exists() && file.isFile();
        }
        throw new IllegalStateException("External Storage is not readable.");
    }

    public static boolean isDirectoryExistingOnExternalStorage(Context pContext, String pDirectory) {
        if (isExternalStorageReadable()) {
            String absoluteFilePath = getAbsolutePathOnExternalStorage(pContext, pDirectory);
            File file = new File(absoluteFilePath);
            return file.exists() && file.isDirectory();
        }
        throw new IllegalStateException("External Storage is not readable.");
    }

    public static boolean ensureDirectoriesExistOnExternalStorage(Context pContext, String pDirectory) {
        if (isDirectoryExistingOnExternalStorage(pContext, pDirectory)) {
            return true;
        }
        if (isExternalStorageWriteable()) {
            String absoluteDirectoryPath = getAbsolutePathOnExternalStorage(pContext, pDirectory);
            return new File(absoluteDirectoryPath).mkdirs();
        }
        throw new IllegalStateException("External Storage is not writeable.");
    }

    public static InputStream openOnExternalStorage(Context pContext, String pFilePath) throws FileNotFoundException {
        String absoluteFilePath = getAbsolutePathOnExternalStorage(pContext, pFilePath);
        return new FileInputStream(absoluteFilePath);
    }

    public static String[] getDirectoryListOnExternalStorage(Context pContext, String pFilePath) throws FileNotFoundException {
        String absoluteFilePath = getAbsolutePathOnExternalStorage(pContext, pFilePath);
        return new File(absoluteFilePath).list();
    }

    public static String[] getDirectoryListOnExternalStorage(Context pContext, String pFilePath, FilenameFilter pFilenameFilter) throws FileNotFoundException {
        String absoluteFilePath = getAbsolutePathOnExternalStorage(pContext, pFilePath);
        return new File(absoluteFilePath).list(pFilenameFilter);
    }

    public static String getAbsolutePathOnInternalStorage(Context pContext, String pFilePath) {
        return String.valueOf(pContext.getFilesDir().getAbsolutePath()) + pFilePath;
    }

    public static String getAbsolutePathOnExternalStorage(Context pContext, String pFilePath) {
        return Environment.getExternalStorageDirectory() + "/Android/data/" + pContext.getApplicationInfo().packageName + "/files/" + pFilePath;
    }

    public static boolean isExternalStorageWriteable() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return state.equals("mounted") || state.equals("mounted_ro");
    }

    public static void copyFile(File pIn, File pOut) throws IOException {
        FileInputStream fis = new FileInputStream(pIn);
        FileOutputStream fos = new FileOutputStream(pOut);
        try {
            StreamUtils.copy(fis, fos);
        } finally {
            StreamUtils.close(fis);
            StreamUtils.close(fos);
        }
    }

    public static boolean deleteDirectory(File pFileOrDirectory) {
        if (pFileOrDirectory.isDirectory()) {
            String[] children = pFileOrDirectory.list();
            for (String str : children) {
                boolean success = deleteDirectory(new File(pFileOrDirectory, str));
                if (!success) {
                    return false;
                }
            }
        }
        return pFileOrDirectory.delete();
    }
}
