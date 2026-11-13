package com.adcolony.sdk;

import com.heyzap.http.AsyncHttpResponseHandler;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.zip.GZIPInputStream;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class n {
    private LinkedList<Runnable> a = new LinkedList<>();
    private boolean b;

    n() {
    }

    boolean a(z zVar) {
        String strB = s.b(zVar.b, "filepath");
        a.m.k.b();
        JSONObject jSONObjectA = s.a();
        try {
            int iC = s.c(zVar.b, "offset");
            int iC2 = s.c(zVar.b, "size");
            boolean zD = s.d(zVar.b, "gunzip");
            String strB2 = s.b(zVar.b, "output_filepath");
            InputStream anVar = new an(new FileInputStream(strB), iC, iC2);
            if (zD) {
                anVar = new GZIPInputStream(anVar, 1024);
            }
            if (strB2.equals("")) {
                StringBuilder sb = new StringBuilder(anVar.available());
                byte[] bArr = new byte[1024];
                while (true) {
                    int i = anVar.read(bArr, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    sb.append(new String(bArr, 0, i, "ISO-8859-1"));
                }
                s.b(jSONObjectA, "size", sb.length());
                s.a(jSONObjectA, "data", sb.toString());
            } else {
                FileOutputStream fileOutputStream = new FileOutputStream(strB2);
                byte[] bArr2 = new byte[1024];
                int i2 = 0;
                while (true) {
                    int i3 = anVar.read(bArr2, 0, 1024);
                    if (i3 < 0) {
                        break;
                    }
                    fileOutputStream.write(bArr2, 0, i3);
                    i2 += i3;
                }
                fileOutputStream.close();
                s.b(jSONObjectA, "size", i2);
            }
            anVar.close();
            s.a(jSONObjectA, "success", true);
            zVar.a(jSONObjectA).b();
            return true;
        } catch (IOException e) {
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            return false;
        } catch (OutOfMemoryError e2) {
            u.g.b("Out of memory error - disabling AdColony.");
            a.m.b(true);
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            return false;
        }
    }

    boolean b(z zVar) {
        String strB = s.b(zVar.b, "filepath");
        String strB2 = s.b(zVar.b, "bundle_path");
        JSONArray jSONArrayG = s.g(zVar.b, "bundle_filenames");
        a.m.k.b();
        JSONObject jSONObjectA = s.a();
        try {
            File file = new File(strB2);
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            byte[] bArr = new byte[4];
            byte[] bArr2 = new byte[32];
            randomAccessFile.readInt();
            int i = randomAccessFile.readInt();
            JSONArray jSONArray = new JSONArray();
            byte[] bArr3 = new byte[1024];
            for (int i2 = 0; i2 < i; i2++) {
                randomAccessFile.seek((i2 * 44) + 8);
                randomAccessFile.read(bArr2);
                new String(bArr2);
                randomAccessFile.readInt();
                int i3 = randomAccessFile.readInt();
                int i4 = randomAccessFile.readInt();
                jSONArray.put(i4);
                try {
                    String str = strB + jSONArrayG.get(i2);
                    randomAccessFile.seek(i3);
                    FileOutputStream fileOutputStream = new FileOutputStream(str);
                    int i5 = i4 / 1024;
                    int i6 = i4 % 1024;
                    for (int i7 = 0; i7 < i5; i7++) {
                        randomAccessFile.read(bArr3, 0, 1024);
                        fileOutputStream.write(bArr3, 0, 1024);
                    }
                    randomAccessFile.read(bArr3, 0, i6);
                    fileOutputStream.write(bArr3, 0, i6);
                    fileOutputStream.close();
                } catch (JSONException e) {
                    u.g.a("Could extract file name at index ").a(i2).a(" unpacking ad unit bundle at").b(strB2);
                    s.a(jSONObjectA, "success", false);
                    zVar.a(jSONObjectA).b();
                    return false;
                }
            }
            randomAccessFile.close();
            file.delete();
            s.a(jSONObjectA, "success", true);
            s.a(jSONObjectA, "file_sizes", jSONArray);
            zVar.a(jSONObjectA).b();
            return true;
        } catch (IOException e2) {
            u.h.a("Failed to find or open ad unit bundle at path: ").b(strB2);
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            return false;
        } catch (OutOfMemoryError e3) {
            u.g.b("Out of memory error - disabling AdColony.");
            a.m.b(true);
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            return false;
        }
    }

    void a() {
        a.a("FileSystem.save", new ac() { // from class: com.adcolony.sdk.n.1
            @Override // com.adcolony.sdk.ac
            public void a(final z zVar) {
                n.this.a(new Runnable() { // from class: com.adcolony.sdk.n.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        n.this.c(zVar);
                        n.this.b();
                    }
                });
            }
        });
        a.a("FileSystem.delete", new ac() { // from class: com.adcolony.sdk.n.2
            @Override // com.adcolony.sdk.ac
            public void a(final z zVar) {
                n.this.a(new Runnable() { // from class: com.adcolony.sdk.n.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        n.this.a(zVar, new File(s.b(zVar.b, "filepath")));
                        n.this.b();
                    }
                });
            }
        });
        a.a("FileSystem.listing", new ac() { // from class: com.adcolony.sdk.n.3
            @Override // com.adcolony.sdk.ac
            public void a(final z zVar) {
                n.this.a(new Runnable() { // from class: com.adcolony.sdk.n.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        n.this.d(zVar);
                        n.this.b();
                    }
                });
            }
        });
        a.a("FileSystem.load", new ac() { // from class: com.adcolony.sdk.n.4
            @Override // com.adcolony.sdk.ac
            public void a(final z zVar) {
                n.this.a(new Runnable() { // from class: com.adcolony.sdk.n.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        n.this.e(zVar);
                        n.this.b();
                    }
                });
            }
        });
        a.a("FileSystem.rename", new ac() { // from class: com.adcolony.sdk.n.5
            @Override // com.adcolony.sdk.ac
            public void a(final z zVar) {
                n.this.a(new Runnable() { // from class: com.adcolony.sdk.n.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        n.this.f(zVar);
                        n.this.b();
                    }
                });
            }
        });
        a.a("FileSystem.exists", new ac() { // from class: com.adcolony.sdk.n.6
            @Override // com.adcolony.sdk.ac
            public void a(final z zVar) {
                n.this.a(new Runnable() { // from class: com.adcolony.sdk.n.6.1
                    @Override // java.lang.Runnable
                    public void run() {
                        n.this.g(zVar);
                        n.this.b();
                    }
                });
            }
        });
        a.a("FileSystem.extract", new ac() { // from class: com.adcolony.sdk.n.7
            @Override // com.adcolony.sdk.ac
            public void a(final z zVar) {
                n.this.a(new Runnable() { // from class: com.adcolony.sdk.n.7.1
                    @Override // java.lang.Runnable
                    public void run() {
                        n.this.a(zVar);
                        n.this.b();
                    }
                });
            }
        });
        a.a("FileSystem.unpack_bundle", new ac() { // from class: com.adcolony.sdk.n.8
            @Override // com.adcolony.sdk.ac
            public void a(final z zVar) {
                n.this.a(new Runnable() { // from class: com.adcolony.sdk.n.8.1
                    @Override // java.lang.Runnable
                    public void run() {
                        n.this.b(zVar);
                        n.this.b();
                    }
                });
            }
        });
    }

    boolean c(z zVar) {
        String strB = s.b(zVar.b, "filepath");
        String strB2 = s.b(zVar.b, "data");
        String strB3 = s.b(zVar.b, TMXConstants.TAG_DATA_ATTRIBUTE_ENCODING);
        boolean z = strB3 != null && strB3.equals("utf8");
        a.m.k.b();
        JSONObject jSONObjectA = s.a();
        try {
            a(strB, strB2, z);
            s.a(jSONObjectA, "success", true);
            zVar.a(jSONObjectA).b();
            return true;
        } catch (IOException e) {
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            return false;
        }
    }

    void a(String str, String str2, boolean z) throws IOException {
        BufferedWriter bufferedWriter = z ? new BufferedWriter(new OutputStreamWriter(new FileOutputStream(str), AsyncHttpResponseHandler.DEFAULT_CHARSET)) : new BufferedWriter(new OutputStreamWriter(new FileOutputStream(str)));
        bufferedWriter.write(str2);
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    boolean a(z zVar, File file) {
        a.m.k.b();
        JSONObject jSONObjectA = s.a();
        try {
            if (!file.isDirectory() || file.list().length == 0) {
                file.delete();
            } else {
                for (String str : file.list()) {
                    a(zVar, new File(file, str));
                }
                if (file.list().length == 0) {
                    file.delete();
                }
            }
            s.a(jSONObjectA, "success", true);
            zVar.a(jSONObjectA).b();
            return true;
        } catch (Exception e) {
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            return false;
        }
    }

    boolean d(z zVar) {
        String strB = s.b(zVar.b, "filepath");
        a.m.k.b();
        JSONObject jSONObjectA = s.a();
        String[] list = new File(strB).list();
        if (list != null) {
            JSONArray jSONArrayB = s.b();
            for (String str : list) {
                JSONObject jSONObjectA2 = s.a();
                s.a(jSONObjectA2, "filename", str);
                if (new File(strB + str).isDirectory()) {
                    s.a(jSONObjectA2, "is_folder", true);
                } else {
                    s.a(jSONObjectA2, "is_folder", false);
                }
                s.a(jSONArrayB, jSONObjectA2);
            }
            s.a(jSONObjectA, "success", true);
            s.a(jSONObjectA, "entries", jSONArrayB);
            zVar.a(jSONObjectA).b();
            return true;
        }
        s.a(jSONObjectA, "success", false);
        zVar.a(jSONObjectA).b();
        return false;
    }

    String e(z zVar) {
        String strB = s.b(zVar.b, "filepath");
        String strB2 = s.b(zVar.b, TMXConstants.TAG_DATA_ATTRIBUTE_ENCODING);
        boolean z = strB2 != null && strB2.equals("utf8");
        a.m.k.b();
        JSONObject jSONObjectA = s.a();
        try {
            StringBuilder sbA = a(strB, z);
            s.a(jSONObjectA, "success", true);
            s.a(jSONObjectA, "data", sbA.toString());
            zVar.a(jSONObjectA).b();
            return sbA.toString();
        } catch (IOException e) {
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            return "";
        }
    }

    StringBuilder a(String str, boolean z) throws IOException {
        File file = new File(str);
        StringBuilder sb = new StringBuilder((int) file.length());
        BufferedReader bufferedReader = z ? new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath()), AsyncHttpResponseHandler.DEFAULT_CHARSET)) : new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath())));
        while (true) {
            String line = bufferedReader.readLine();
            if (line != null) {
                sb.append(line);
                sb.append("\n");
            } else {
                bufferedReader.close();
                return sb;
            }
        }
    }

    boolean f(z zVar) {
        boolean z = true;
        String strB = s.b(zVar.b, "filepath");
        String strB2 = s.b(zVar.b, "new_filepath");
        a.m.k.b();
        JSONObject jSONObjectA = s.a();
        try {
            if (new File(strB).renameTo(new File(strB2))) {
                s.a(jSONObjectA, "success", true);
                zVar.a(jSONObjectA).b();
            } else {
                s.a(jSONObjectA, "success", false);
                zVar.a(jSONObjectA).b();
                z = false;
            }
            return z;
        } catch (Exception e) {
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            return false;
        }
    }

    boolean g(z zVar) {
        String strB = s.b(zVar.b, "filepath");
        a.m.k.b();
        JSONObject jSONObjectA = s.a();
        try {
            boolean zExists = new File(strB).exists();
            s.a(jSONObjectA, "result", zExists);
            s.a(jSONObjectA, "success", true);
            zVar.a(jSONObjectA).b();
            return zExists;
        } catch (Exception e) {
            s.a(jSONObjectA, "result", false);
            s.a(jSONObjectA, "success", false);
            zVar.a(jSONObjectA).b();
            e.printStackTrace();
            return false;
        }
    }

    void a(Runnable runnable) {
        if (this.a.isEmpty() && !this.b) {
            this.b = true;
            runnable.run();
        } else {
            this.a.push(runnable);
        }
    }

    void b() {
        this.b = false;
        if (!this.a.isEmpty()) {
            this.b = true;
            this.a.removeLast().run();
        }
    }
}
