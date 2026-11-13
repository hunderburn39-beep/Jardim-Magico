package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.internal.it;
import com.google.android.gms.internal.ks;
import com.google.android.gms.internal.kt;
import com.heyzap.http.AsyncHttpResponseHandler;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
class cp implements o.f {
    private final String WJ;
    private bg<it.a> Zf;
    private final ExecutorService Zm = Executors.newSingleThreadExecutor();
    private final Context mContext;

    cp(Context context, String str) {
        this.mContext = context;
        this.WJ = str;
    }

    private cq.c a(ByteArrayOutputStream byteArrayOutputStream) {
        try {
            return ba.bG(byteArrayOutputStream.toString(AsyncHttpResponseHandler.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            bh.v("Tried to convert binary resource to string for JSON parsing; not UTF-8 format");
            return null;
        } catch (JSONException e2) {
            bh.z("Resource is a UTF-8 encoded string but doesn't contain a JSON container");
            return null;
        }
    }

    private cq.c k(byte[] bArr) {
        try {
            return cq.b(com.google.android.gms.internal.c.f.a(bArr));
        } catch (ks e) {
            bh.z("Resource doesn't contain a binary container");
            return null;
        } catch (cq.g e2) {
            bh.z("Resource doesn't contain a binary container");
            return null;
        }
    }

    @Override // com.google.android.gms.tagmanager.o.f
    public void a(bg<it.a> bgVar) {
        this.Zf = bgVar;
    }

    @Override // com.google.android.gms.tagmanager.o.f
    public void b(final it.a aVar) {
        this.Zm.execute(new Runnable() { // from class: com.google.android.gms.tagmanager.cp.2
            @Override // java.lang.Runnable
            public void run() {
                cp.this.c(aVar);
            }
        });
    }

    boolean c(it.a aVar) {
        boolean z = false;
        File fileLc = lc();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileLc);
            try {
                fileOutputStream.write(kt.d(aVar));
                z = true;
            } catch (IOException e) {
                bh.z("Error writing resource to disk. Removing resource from disk.");
                fileLc.delete();
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException e2) {
                    bh.z("error closing stream for writing resource to disk");
                }
            }
        } catch (FileNotFoundException e3) {
            bh.w("Error opening resource file for writing");
        }
        return z;
    }

    @Override // com.google.android.gms.tagmanager.o.f
    public cq.c ca(int i) {
        bh.y("Atttempting to load container from resource ID " + i);
        try {
            InputStream inputStreamOpenRawResource = this.mContext.getResources().openRawResource(i);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            cq.b(inputStreamOpenRawResource, byteArrayOutputStream);
            cq.c cVarA = a(byteArrayOutputStream);
            return cVarA != null ? cVarA : k(byteArrayOutputStream.toByteArray());
        } catch (Resources.NotFoundException e) {
            bh.z("No default container resource found.");
            return null;
        } catch (IOException e2) {
            bh.z("Error reading default container resource with ID " + i);
            return null;
        }
    }

    @Override // com.google.android.gms.tagmanager.o.f
    public void km() {
        this.Zm.execute(new Runnable() { // from class: com.google.android.gms.tagmanager.cp.1
            @Override // java.lang.Runnable
            public void run() {
                cp.this.lb();
            }
        });
    }

    void lb() {
        if (this.Zf == null) {
            throw new IllegalStateException("callback must be set before execute");
        }
        this.Zf.kl();
        bh.y("Start loading resource from disk ...");
        if ((cd.kT().kU() == cd.a.CONTAINER || cd.kT().kU() == cd.a.CONTAINER_DEBUG) && this.WJ.equals(cd.kT().getContainerId())) {
            this.Zf.a(bg.a.NOT_AVAILABLE);
            return;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(lc());
            try {
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    cq.b(fileInputStream, byteArrayOutputStream);
                    this.Zf.i(it.a.l(byteArrayOutputStream.toByteArray()));
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        bh.z("error closing stream for reading resource from disk");
                    }
                } catch (IOException e2) {
                    bh.z("error reading resource from disk");
                    this.Zf.a(bg.a.IO_ERROR);
                    try {
                        fileInputStream.close();
                    } catch (IOException e3) {
                        bh.z("error closing stream for reading resource from disk");
                    }
                }
                bh.y("Load resource from disk finished.");
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (IOException e4) {
                    bh.z("error closing stream for reading resource from disk");
                }
                throw th;
            }
        } catch (FileNotFoundException e5) {
            bh.v("resource not on disk");
            this.Zf.a(bg.a.NOT_AVAILABLE);
        }
    }

    File lc() {
        return new File(this.mContext.getDir("google_tagmanager", 0), "resource_" + this.WJ);
    }

    @Override // com.google.android.gms.common.api.Releasable
    public synchronized void release() {
        this.Zm.shutdown();
    }
}
