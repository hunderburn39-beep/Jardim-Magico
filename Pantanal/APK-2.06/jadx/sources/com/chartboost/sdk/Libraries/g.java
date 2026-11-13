package com.chartboost.sdk.Libraries;

import android.content.Context;
import android.text.TextUtils;
import com.chartboost.sdk.impl.bq;
import com.google.android.gms.games.request.Requests;
import com.tapjoy.TJAdUnitConstants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class g {
    private static h a;
    private static h b;
    private static File c;
    private static File d;
    private static com.chartboost.sdk.impl.g e;
    private final boolean f;

    public enum a {
        TemplateMetaData("templates"),
        Videos("videos"),
        Images("images"),
        StyleSheets("css"),
        Javascript("js"),
        Html(TJAdUnitConstants.String.HTML),
        VideoCompletion("videoCompletionEvents"),
        Session("session"),
        Track("track"),
        RequestManager(Requests.EXTRA_REQUESTS);

        private final String k;

        a(String str) {
            this.k = str;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.k;
        }
    }

    public static synchronized void a(Context context, com.chartboost.sdk.Model.c cVar) {
        File[] fileArrListFiles;
        e = com.chartboost.sdk.impl.g.a();
        a = new h(context.getCacheDir());
        b = new h(e.b());
        d = new File(a.a, a.Track.toString());
        c = new File(a.a, a.Session.toString());
        h[] hVarArr = {a, b};
        int length = hVarArr.length;
        for (int i = 0; i < length; i++) {
            h hVar = hVarArr[i];
            try {
                boolean z = hVar == a;
                if (z || a()) {
                    long jCurrentTimeMillis = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(cVar.s);
                    File file = new File(hVar.a, "templates");
                    if (file.exists() && (fileArrListFiles = file.listFiles()) != null) {
                        for (File file2 : fileArrListFiles) {
                            if (file2.isDirectory()) {
                                File[] fileArrListFiles2 = file2.listFiles();
                                if (fileArrListFiles2 != null) {
                                    for (File file3 : fileArrListFiles2) {
                                        if ((z || file3.lastModified() < jCurrentTimeMillis) && !file3.delete()) {
                                            CBLogging.b("CBFileCache", "Unable to delete " + file3.getPath());
                                        }
                                    }
                                }
                                File[] fileArrListFiles3 = file2.listFiles();
                                if (fileArrListFiles3 != null && fileArrListFiles3.length == 0 && !file2.delete()) {
                                    CBLogging.b("CBFileCache", "Unable to delete " + file2.getPath());
                                }
                            }
                        }
                    }
                    File file4 = new File(hVar.a, ".adId");
                    if (file4.exists() && ((z || file4.lastModified() < jCurrentTimeMillis) && !file4.delete())) {
                        CBLogging.b("CBFileCache", "Unable to delete " + file4.getPath());
                    }
                }
            } catch (Exception e2) {
                CBLogging.b("CBFileCache", "Exception while cleaning up templates directory at " + hVar.f.getPath(), e2);
                e2.printStackTrace();
            }
        }
    }

    public g(boolean z) {
        this.f = z;
    }

    public synchronized void a(File file, String str, byte[] bArr) {
        if (file != null) {
            File file2 = null;
            if (!TextUtils.isEmpty(str)) {
                file2 = new File(file.getPath(), str);
            }
            a(file, file2, bArr);
        }
    }

    public synchronized void a(File file, File file2, byte[] bArr) {
        if (file != null && bArr != null) {
            if (file2 == null) {
                file2 = new File(file.getPath(), Long.toString(System.nanoTime()));
            }
            try {
                bq.a(file2, bArr);
            } catch (IOException e2) {
                CBLogging.b("CBFileCache", "IOException attempting to write cache to disk", e2);
                com.chartboost.sdk.Tracking.a.a(getClass(), "writeToDisk(File, File, byte[])", e2);
            }
        }
    }

    public synchronized JSONObject a(File file) {
        JSONObject jSONObjectA;
        jSONObjectA = null;
        try {
            jSONObjectA = com.chartboost.sdk.impl.g.a().a(new String(bq.b(file)));
        } catch (Exception e2) {
            CBLogging.b("CBFileCache", "Error loading cache from disk", e2);
            com.chartboost.sdk.Tracking.a.a(getClass(), "readJSONObject", e2);
        }
        return jSONObjectA;
    }

    public synchronized byte[] b(File file) {
        byte[] bArrB = null;
        synchronized (this) {
            if (file != null) {
                try {
                    bArrB = bq.b(file);
                } catch (Exception e2) {
                    CBLogging.b("CBFileCache", "Error loading cache from disk", e2);
                    com.chartboost.sdk.Tracking.a.a(getClass(), "readByteArrayFromDisk", e2);
                }
            }
        }
        return bArrB;
    }

    public static String a(String str) {
        File file = new File(e().g, str);
        if (file.exists()) {
            return file.getPath();
        }
        return null;
    }

    public static boolean a() {
        String strC = e.c();
        if (strC != null && strC.equals("mounted") && !com.chartboost.sdk.i.n) {
            return true;
        }
        CBLogging.e("CBFileCache", "External Storage unavailable");
        return false;
    }

    public boolean b(String str) {
        if (c() == null || str == null) {
            return false;
        }
        return new File(c(), str).exists();
    }

    public File b() {
        return c;
    }

    public File c() {
        return d().d;
    }

    public h d() {
        return (this.f && a()) ? b : a;
    }

    public static h e() {
        return a() ? b : a;
    }

    public static long c(File file) {
        long j = 0;
        if (file != null) {
            try {
                if (file.isDirectory()) {
                    File[] fileArrListFiles = file.listFiles();
                    if (fileArrListFiles == null) {
                        return 0L;
                    }
                    int length = fileArrListFiles.length;
                    int i = 0;
                    while (i < length) {
                        long jC = c(fileArrListFiles[i]) + j;
                        i++;
                        j = jC;
                    }
                    return j;
                }
            } catch (Exception e2) {
                com.chartboost.sdk.Tracking.a.a(g.class, "getFolderSize", e2);
                return 0L;
            }
        }
        if (file == null) {
            return 0L;
        }
        return file.length();
    }

    public static JSONObject f() {
        JSONObject jSONObject = (JSONObject) com.chartboost.sdk.g.a().a(new JSONObject());
        d.a(jSONObject, ".chartboost-external-folder-size", Long.valueOf(c(b.a)));
        d.a(jSONObject, ".chartboost-internal-folder-size", Long.valueOf(c(a.a)));
        File file = e().a;
        String[] list = file.list();
        if (list != null && list.length > 0) {
            for (String str : list) {
                File file2 = new File(file, str);
                JSONObject jSONObject2 = (JSONObject) com.chartboost.sdk.g.a().a(new JSONObject());
                d.a(jSONObject2, file2.getName() + "-size", Long.valueOf(c(file2)));
                String[] list2 = file2.list();
                if (list2 != null) {
                    d.a(jSONObject2, "count", Integer.valueOf(list2.length));
                }
                d.a(jSONObject, file2.getName(), jSONObject2);
            }
        }
        return jSONObject;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0044 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:28:0x004f A[Catch: FileNotFoundException -> 0x0026, IOException -> 0x0033, TRY_LEAVE, TryCatch #6 {FileNotFoundException -> 0x0026, IOException -> 0x0033, blocks: (B:2:0x0000, B:7:0x001d, B:14:0x002f, B:10:0x0022, B:24:0x0046, B:28:0x004f, B:27:0x004b, B:25:0x0049), top: B:40:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:32:0x0046 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static void d(File file) throws Throwable {
        Throwable th;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            Throwable th2 = null;
            try {
                randomAccessFile.seek(0L);
                int i = randomAccessFile.read();
                randomAccessFile.seek(0L);
                randomAccessFile.write(i);
                if (randomAccessFile != null) {
                    if (0 == 0) {
                        randomAccessFile.close();
                        return;
                    }
                    try {
                        randomAccessFile.close();
                    } catch (Throwable th3) {
                        th2.addSuppressed(th3);
                    }
                }
            } catch (Throwable th4) {
                try {
                    throw th4;
                } catch (Throwable th5) {
                    th2 = th4;
                    th = th5;
                    if (randomAccessFile != null) {
                        if (th2 != null) {
                            randomAccessFile.close();
                        } else {
                            randomAccessFile.close();
                        }
                    }
                    throw th;
                }
            }
        } catch (FileNotFoundException e2) {
            CBLogging.b("CBFileCache", "File not found when attempting to touch", e2);
        } catch (IOException e3) {
            CBLogging.b("CBFileCache", "IOException when attempting to touch file", e3);
        }
    }
}
