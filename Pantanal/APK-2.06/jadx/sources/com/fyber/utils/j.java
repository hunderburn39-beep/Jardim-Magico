package com.fyber.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.List;

/* JADX INFO: compiled from: HttpConnectionFiles.java */
/* JADX INFO: loaded from: classes.dex */
public final class j extends b<j, a> {
    private final File i;
    private boolean j;

    private j(String str, File file) throws MalformedURLException, IllegalArgumentException {
        super(str);
        this.j = false;
        if (file == null) {
            throw new IllegalArgumentException("The localFile parameter is required");
        }
        this.i = file;
        this.f = false;
        this.g = false;
        this.h = false;
    }

    public static j a(String str, File file) throws MalformedURLException, IllegalArgumentException {
        return new j(str, file);
    }

    public final j a(boolean z) {
        this.j = z;
        return this;
    }

    @Override // com.fyber.utils.b
    /* JADX INFO: renamed from: e, reason: merged with bridge method [inline-methods] */
    public final j a() throws IOException {
        if (this.j) {
            a("Range", "bytes=" + this.i.length() + "-");
        }
        return (j) super.a();
    }

    @Override // com.fyber.utils.b
    protected final InputStream c(HttpURLConnection httpURLConnection) throws IOException {
        if (this.c < 400) {
            return httpURLConnection.getInputStream();
        }
        return null;
    }

    /* JADX INFO: compiled from: HttpConnectionFiles.java */
    public class a {
        private boolean b;
        private InputStream c;
        private final HttpURLConnection d;
        private boolean e = true;

        public a(HttpURLConnection httpURLConnection) {
            this.b = false;
            this.c = null;
            this.d = httpURLConnection;
            try {
                this.c = j.this.c(httpURLConnection);
                if (this.c != null) {
                    this.b = true;
                } else {
                    FyberLogger.i("HttpConnectionFiles", "There was an error, the file will not be saved locally");
                    j.b(httpURLConnection);
                }
            } catch (IOException e) {
                FyberLogger.d("HttpConnectionFiles", "An error occurred. Aborting file save operation");
            }
        }

        /* JADX WARN: Code duplicated, block: B:38:0x00ff  */
        public final boolean a() throws b, C0028a {
            boolean z;
            if (this.b) {
                try {
                    if (j.this.i == null) {
                        return false;
                    }
                    try {
                        List<String> listA = j.this.a("Content-Length");
                        if (listA != null && !listA.isEmpty()) {
                            if (j.this.i.getParentFile().getUsableSpace() < Long.parseLong(listA.get(0))) {
                                throw new C0028a();
                            }
                        }
                        FyberLogger.i("HttpConnectionFiles", "Download: " + j.this.a.toExternalForm() + " to local file: " + j.this.i.getAbsolutePath());
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(this.c, 1024);
                        if (j.this.j) {
                            if (j.this.c == 206 || j.this.d.containsKey("Content-Range")) {
                                z = true;
                            } else {
                                z = false;
                            }
                        } else {
                            z = false;
                        }
                        FileOutputStream fileOutputStream = new FileOutputStream(j.this.i, z);
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int i = bufferedInputStream.read(bArr);
                            if (i <= 0 || !this.e) {
                                break;
                            }
                            fileOutputStream.write(bArr, 0, i);
                        }
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        bufferedInputStream.close();
                        boolean z2 = this.e;
                        j.b(this.d);
                        return z2;
                    } catch (IOException e) {
                        FyberLogger.i("HttpConnectionFiles", "File downloading from URL: " + j.this.a + " has been interrupted.");
                        FyberLogger.e("HttpConnectionFiles", "An error occurred while downloading: " + e.getMessage());
                        j.b(this.d);
                        return false;
                    }
                } catch (Throwable th) {
                    j.b(this.d);
                    throw th;
                }
            }
            throw new b();
        }

        public final void b() {
            this.e = false;
        }

        /* JADX INFO: renamed from: com.fyber.utils.j$a$a, reason: collision with other inner class name */
        /* JADX INFO: compiled from: HttpConnectionFiles.java */
        public class C0028a extends Throwable {
            public C0028a() {
            }
        }

        /* JADX INFO: compiled from: HttpConnectionFiles.java */
        public class b extends Throwable {
            public b() {
            }
        }
    }

    @Override // com.fyber.utils.b
    protected final /* synthetic */ a a(HttpURLConnection httpURLConnection) throws IOException {
        return new a(httpURLConnection);
    }
}
