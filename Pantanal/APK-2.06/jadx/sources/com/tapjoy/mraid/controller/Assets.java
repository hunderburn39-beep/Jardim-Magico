package com.tapjoy.mraid.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.os.StatFs;
import android.webkit.JavascriptInterface;
import com.tapjoy.TapjoyLog;
import com.tapjoy.internal.cy;
import com.tapjoy.internal.eh;
import com.tapjoy.mraid.view.MraidView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.jar.JarFile;

/* JADX INFO: loaded from: classes.dex */
public class Assets extends Abstract {
    private static final char[] d = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private int c;

    public Assets(MraidView adView, Context c) {
        super(adView, c);
        this.c = 0;
    }

    @JavascriptInterface
    public void storePictureInit(final String URI) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.b);
        builder.setMessage("Are you sure you want to save file from " + URI + " to your SD card?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() { // from class: com.tapjoy.mraid.controller.Assets.1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialog, int id) {
                Assets.this.storePicture(URI);
            }
        });
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) null);
        builder.show();
    }

    public void storePicture(String URI) {
        TapjoyLog.d("MRAID Assets", "Storing media from " + URI + " to device photo album.  Output directory: " + Environment.getExternalStorageDirectory() + " state: " + Environment.getExternalStorageState());
        this.c++;
        try {
            URL url = new URL(URI);
            String str = "MraidMedia" + this.c + ".jpg";
            File file = new File(Environment.getExternalStorageDirectory().toString() + "/" + str);
            long jCurrentTimeMillis = System.currentTimeMillis();
            TapjoyLog.d("MRAID Assets", "download beginning");
            TapjoyLog.d("MRAID Assets", "download url:" + url);
            TapjoyLog.d("MRAID Assets", "downloaded file name:" + str);
            InputStream inputStream = eh.a(url).getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            cy.a(inputStream, fileOutputStream);
            fileOutputStream.close();
            TapjoyLog.d("MRAID Assets", "download ready in" + ((System.currentTimeMillis() - jCurrentTimeMillis) / 1000) + " sec");
        } catch (IOException e) {
            TapjoyLog.d("MRAID Assets", "Error: " + e);
        }
    }

    /* JADX WARN: Code duplicated, block: B:47:0x0082 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public String copyTextFromJarIntoAssetDir(String alias, String source) throws Throwable {
        InputStream inputStream;
        Throwable th;
        String strWriteToDisk = null;
        try {
            URL resource = Assets.class.getClassLoader().getResource(source);
            if (resource == null) {
                inputStream = this.b.getAssets().open(source);
            } else {
                String file = resource.getFile();
                if (file.startsWith("jar:")) {
                    file = file.substring(4);
                }
                if (file.startsWith("file:")) {
                    file = file.substring(5);
                }
                int iIndexOf = file.indexOf("!");
                if (iIndexOf > 0) {
                    file = file.substring(0, iIndexOf);
                }
                JarFile jarFile = new JarFile(file);
                inputStream = jarFile.getInputStream(jarFile.getJarEntry(source));
            }
            try {
                try {
                    strWriteToDisk = writeToDisk(inputStream, alias, false);
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Exception e) {
                        }
                    }
                } catch (Exception e2) {
                    e = e2;
                    TapjoyLog.e("MRAID Assets", "copyTextFromJarIntoAssetDir: " + e.toString());
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Exception e3) {
                        }
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e4) {
                    }
                }
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            inputStream = null;
        } catch (Throwable th3) {
            inputStream = null;
            th = th3;
            if (inputStream != null) {
                inputStream.close();
            }
            throw th;
        }
        return strWriteToDisk;
    }

    public void addAsset(String alias, String url) {
        InputStream inputStreamA = null;
        try {
            inputStreamA = eh.a(url);
            writeToDisk(inputStreamA, alias, false);
            this.a.injectMraidJavaScript("MraidAdController.addedAsset('" + alias + "' )");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStreamA != null) {
                try {
                    inputStreamA.close();
                } catch (Exception e2) {
                }
            }
        }
    }

    public int cacheRemaining() {
        StatFs statFs = new StatFs(this.b.getFilesDir().getPath());
        return statFs.getFreeBlocks() * statFs.getBlockSize();
    }

    public String writeToDisk(InputStream in, String file, boolean storeInHashedDirectory) {
        MessageDigest messageDigest;
        String str;
        FileOutputStream assetOutputString = null;
        byte[] bArr = new byte[1024];
        if (storeInHashedDirectory) {
            try {
                messageDigest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                messageDigest = null;
            }
        } else {
            messageDigest = null;
        }
        try {
            assetOutputString = getAssetOutputString(file);
            while (true) {
                int i = in.read(bArr);
                if (i <= 0) {
                    break;
                }
                if (storeInHashedDirectory && messageDigest != null) {
                    messageDigest.update(bArr);
                }
                assetOutputString.write(bArr, 0, i);
            }
            assetOutputString.flush();
            if (assetOutputString != null) {
                try {
                    assetOutputString.close();
                } catch (Exception e2) {
                }
            }
            String strA = a();
            if (!storeInHashedDirectory || messageDigest == null) {
                str = strA;
            } else {
                byte[] bArrDigest = messageDigest.digest();
                char[] cArr = new char[bArrDigest.length * 2];
                int i2 = 0;
                for (int i3 = 0; i3 < bArrDigest.length; i3++) {
                    int i4 = i2 + 1;
                    cArr[i2] = d[(bArrDigest[i3] >>> 4) & 15];
                    i2 = i4 + 1;
                    cArr[i4] = d[bArrDigest[i3] & 15];
                }
                String str2 = new String(cArr);
                File file2 = new File(strA + File.separator + file);
                new File(strA + File.separator + "ad").mkdir();
                File file3 = new File(strA + File.separator + "ad" + File.separator + str2);
                file3.mkdir();
                file2.renameTo(new File(file3, file2.getName()));
                str = file3.getPath() + File.separator;
            }
            return str + file;
        } catch (Throwable th) {
            if (assetOutputString != null) {
                try {
                    assetOutputString.close();
                } catch (Exception e3) {
                }
            }
            throw th;
        }
    }

    private String a() {
        return this.b.getFilesDir().getPath();
    }

    public FileOutputStream getAssetOutputString(String asset) {
        File fileA = a(b(asset));
        fileA.mkdirs();
        return new FileOutputStream(new File(fileA, c(asset)));
    }

    public void removeAsset(String asset) {
        File fileA = a(b(asset));
        fileA.mkdirs();
        new File(fileA, c(asset)).delete();
        this.a.injectMraidJavaScript("MraidAdController.assetRemoved('" + asset + "' )");
    }

    private File a(String str) {
        return new File(this.b.getFilesDir().getPath() + File.separator + str);
    }

    private static String b(String str) {
        if (str.lastIndexOf(File.separatorChar) < 0) {
            return "/";
        }
        return str.substring(0, str.lastIndexOf(File.separatorChar));
    }

    private static String c(String str) {
        if (str.lastIndexOf(File.separatorChar) >= 0) {
            return str.substring(str.lastIndexOf(File.separatorChar) + 1);
        }
        return str;
    }

    public String getAssetPath() {
        return "file://" + this.b.getFilesDir() + "/";
    }

    public static boolean deleteDirectory(String path) {
        if (path != null) {
            return deleteDirectory(new File(path));
        }
        return false;
    }

    public static boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] fileArrListFiles = path.listFiles();
            for (int i = 0; i < fileArrListFiles.length; i++) {
                if (fileArrListFiles[i].isDirectory()) {
                    deleteDirectory(fileArrListFiles[i]);
                } else {
                    fileArrListFiles[i].delete();
                }
            }
        }
        return path.delete();
    }

    public void deleteOldAds() {
        deleteDirectory(new File(a() + File.separator + "ad"));
    }

    @Override // com.tapjoy.mraid.controller.Abstract
    public void stopAllListeners() {
    }
}
