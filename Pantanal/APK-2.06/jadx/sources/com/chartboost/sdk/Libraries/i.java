package com.chartboost.sdk.Libraries;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import com.chartboost.sdk.impl.am;
import java.io.File;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class i implements com.chartboost.sdk.f.b {
    private a b;
    private final com.chartboost.sdk.f c;
    private String d;
    private float e = 1.0f;
    final am.b a = new am.b() { // from class: com.chartboost.sdk.Libraries.i.1
        @Override // com.chartboost.sdk.impl.am.b
        public void a(a aVar, Bundle bundle) {
            i.this.a(aVar);
        }
    };

    public static class a {
        private final String b;
        private final File c;
        private final g e;
        private int f = -1;
        private int g = -1;
        private Bitmap d = null;
        private int a = 1;

        public a(String str, File file, g gVar) {
            this.c = file;
            this.b = str;
            this.e = gVar;
        }

        public Bitmap a() {
            if (this.d == null) {
                b();
            }
            return this.d;
        }

        public void b() {
            if (this.d == null) {
                CBLogging.a("MemoryBitmap", "Loading image '" + this.b + "' from cache");
                byte[] bArrB = this.e.b(this.c);
                if (bArrB == null) {
                    CBLogging.b("MemoryBitmap", "decode() - bitmap not found");
                    return;
                }
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(bArrB, 0, bArrB.length, options);
                BitmapFactory.Options options2 = new BitmapFactory.Options();
                options2.inJustDecodeBounds = false;
                options2.inDither = false;
                options2.inPurgeable = true;
                options2.inInputShareable = true;
                options2.inTempStorage = new byte[32768];
                options2.inSampleSize = 1;
                while (options2.inSampleSize < 32) {
                    try {
                        this.d = BitmapFactory.decodeByteArray(bArrB, 0, bArrB.length, options2);
                        break;
                    } catch (Exception e) {
                        CBLogging.b("MemoryBitmap", "Exception raised decoding bitmap", e);
                        com.chartboost.sdk.Tracking.a.a(getClass(), "decodeByteArray", e);
                    } catch (OutOfMemoryError e2) {
                        CBLogging.b("MemoryBitmap", "OutOfMemoryError suppressed - trying larger sample size", e2);
                        options2.inSampleSize *= 2;
                    }
                }
                this.a = options2.inSampleSize;
            }
        }

        public int c() {
            return this.a;
        }

        public void d() {
            try {
                if (this.d != null && !this.d.isRecycled()) {
                    this.d.recycle();
                }
            } catch (Exception e) {
                com.chartboost.sdk.Tracking.a.a(getClass(), "recycle", e);
            }
            this.d = null;
        }

        public int e() {
            if (this.d != null) {
                return this.d.getWidth();
            }
            if (this.f >= 0) {
                return this.f;
            }
            g();
            return this.f;
        }

        public int f() {
            if (this.d != null) {
                return this.d.getHeight();
            }
            if (this.g >= 0) {
                return this.g;
            }
            g();
            return this.g;
        }

        private void g() {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(this.c.getAbsolutePath(), options);
                this.f = options.outWidth;
                this.g = options.outHeight;
            } catch (Exception e) {
                CBLogging.b("MemoryBitmap", "Error decoding file size", e);
                com.chartboost.sdk.Tracking.a.a(getClass(), "decodeSize", e);
            }
        }
    }

    void a(a aVar) {
        this.b = aVar;
        this.c.a(this);
    }

    public i(com.chartboost.sdk.f fVar) {
        this.c = fVar;
    }

    public int b() {
        return this.b.e() * this.b.c();
    }

    public int c() {
        return this.b.f() * this.b.c();
    }

    public void a(String str) {
        a(this.c.g(), str, new Bundle());
    }

    public void a(JSONObject jSONObject, String str, final Bundle bundle) {
        final JSONObject jSONObjectA = d.a(jSONObject, str);
        this.d = str;
        if (jSONObjectA != null) {
            final String strOptString = jSONObjectA.optString("url");
            this.e = (float) jSONObjectA.optDouble("scale", 1.0d);
            if (!strOptString.isEmpty()) {
                this.c.b(this);
                final com.chartboost.sdk.f fVar = this.c;
                CBUtility.c().post(new Runnable() { // from class: com.chartboost.sdk.Libraries.i.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            fVar.f.a.k.a(strOptString, jSONObjectA.optString("checksum"), i.this.a, null, bundle == null ? new Bundle() : bundle);
                        } catch (Exception e) {
                            com.chartboost.sdk.Tracking.a.a(i.class, "processAsset Runnable.run", e);
                        }
                    }
                });
            }
        }
    }

    @Override // com.chartboost.sdk.f.b
    public boolean a() {
        return e();
    }

    public void d() {
        if (this.b != null) {
            this.b.d();
        }
    }

    public boolean e() {
        return this.b != null;
    }

    public Bitmap f() {
        if (this.b != null) {
            return this.b.a();
        }
        return null;
    }

    public float g() {
        return this.e;
    }

    public int h() {
        return Math.round(b() / this.e);
    }

    public int i() {
        return Math.round(c() / this.e);
    }
}
