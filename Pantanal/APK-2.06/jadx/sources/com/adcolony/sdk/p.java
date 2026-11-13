package com.adcolony.sdk;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/* JADX INFO: loaded from: classes.dex */
class p implements GLSurfaceView.Renderer {
    static BitmapFactory.Options a = new BitmapFactory.Options();
    static ByteBuffer b;
    o c;
    String d;
    int e;
    int f;
    boolean g;
    boolean h;
    c j;
    int k;
    int l;
    ArrayList<z> i = new ArrayList<>();
    ArrayList<a> m = new ArrayList<>();
    HashMap<Integer, a> n = new HashMap<>();

    p(o oVar, boolean z, String str) {
        System.out.println("AdColony new ADCGLViewRenderer");
        this.c = oVar;
        this.g = z;
        this.d = str;
        this.j = com.adcolony.sdk.a.m.g.a.get(str);
        this.e = oVar.b;
        this.f = this.j.l;
        this.j.o.add(com.adcolony.sdk.a.a("RenderView.create_image", new ac() { // from class: com.adcolony.sdk.p.1
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                p.this.a(zVar);
            }
        }, true));
        this.j.o.add(com.adcolony.sdk.a.a("RenderView.load_texture", new ac() { // from class: com.adcolony.sdk.p.2
            @Override // com.adcolony.sdk.ac
            public void a(z zVar) {
                p.this.b(zVar);
            }
        }, true));
        this.j.p.add("RenderView.create_image");
        this.j.p.add("RenderView.load_texture");
    }

    synchronized void a() {
        if (!this.h) {
            this.h = true;
            synchronized (ADCNative.a) {
                ADCNative.nativeDeleteSceneController(this.f, this.e);
            }
        }
    }

    protected void finalize() throws Throwable {
        a();
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public synchronized void onDrawFrame(GL10 unused) {
        com.adcolony.sdk.a.a();
        synchronized (ADCNative.a) {
            if (!this.h) {
                b();
                ADCNative.nativeRender(this.f, this.e, this.k, this.l);
            }
        }
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public synchronized void onSurfaceCreated(GL10 unused, EGLConfig config) {
        synchronized (ADCNative.a) {
            if (!this.h) {
                ADCNative.nativeCreateSceneController(this.f, this.e);
            }
        }
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        this.k = width;
        this.l = height;
    }

    void a(z zVar) {
    }

    synchronized void b(z zVar) {
        this.i.add(zVar);
    }

    synchronized void b() {
        for (int i = 0; i < this.i.size(); i++) {
            z zVar = this.i.get(i);
            a aVarA = null;
            if (zVar.b.has("pixels")) {
                String strB = s.b(zVar.b, "pixels");
                int[] iArr = new int[strB.length() / 4];
                int length = strB.length() - 4;
                int length2 = iArr.length;
                while (length >= 0) {
                    char cCharAt = strB.charAt(length);
                    char cCharAt2 = strB.charAt(length + 1);
                    char cCharAt3 = strB.charAt(length + 2);
                    char cCharAt4 = strB.charAt(length + 3);
                    length += 4;
                    length2--;
                    iArr[length2] = (cCharAt << 24) | (cCharAt2 << 16) | (cCharAt3 << '\b') | cCharAt4;
                }
                int iC = s.c(zVar.b, "width");
                int iC2 = s.c(zVar.b, "height");
                if (iC * iC2 == iArr.length) {
                    aVarA = a(s.c(zVar.b, "texture_id"), s.b(zVar.b, "filepath"), iArr, iC, iC2);
                }
            } else if (zVar.b.has("bytes")) {
                String strB2 = s.b(zVar.b, "bytes");
                byte[] bArr = new byte[strB2.length()];
                int length3 = strB2.length();
                while (true) {
                    length3--;
                    if (length3 < 0) {
                        break;
                    } else {
                        bArr[length3] = (byte) strB2.charAt(length3);
                    }
                }
                aVarA = a(s.c(zVar.b, "texture_id"), s.b(zVar.b, "filepath"), bArr);
            } else if (zVar.b.has("filepath")) {
                aVarA = a(s.c(zVar.b, "texture_id"), s.b(zVar.b, "filepath"));
            }
            if (aVarA == null) {
                s.a(zVar.b, "success", false);
            } else {
                s.a(zVar.b, "success", aVarA.d);
                s.b(zVar.b, "image_width", aVarA.f);
                s.b(zVar.b, "image_height", aVarA.g);
                s.b(zVar.b, "texture_width", aVarA.h);
                s.b(zVar.b, "texture_height", aVarA.i);
                zVar.a(zVar.b).b();
            }
        }
        this.i.clear();
    }

    a a(int i, String str) {
        Bitmap bitmapDecodeStream;
        a.inScaled = false;
        if (str.startsWith("file:///android_asset/")) {
            try {
                bitmapDecodeStream = BitmapFactory.decodeStream(com.adcolony.sdk.a.l.getAssets().open(str.substring("file:///android_asset/".length())), null, a);
            } catch (IOException e) {
                u.f.b(e.toString());
                bitmapDecodeStream = null;
            }
        } else {
            bitmapDecodeStream = BitmapFactory.decodeFile(str, a);
        }
        if (bitmapDecodeStream == null) {
            u.f.a("Failed to load ").a(str).b(" - using white 16x16 as placeholder.");
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmapCreateBitmap.eraseColor(-1);
            a aVarA = a(i, str, bitmapCreateBitmap);
            aVarA.d = false;
            return aVarA;
        }
        return a(i, str, bitmapDecodeStream);
    }

    a a(int i, String str, byte[] bArr) {
        a.inScaled = false;
        Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        if (bitmapDecodeByteArray != null) {
            return a(i, str, bitmapDecodeByteArray);
        }
        u.f.a("Failed to load ").a(str).b(" bytes - using white 16x16 as placeholder.");
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        bitmapCreateBitmap.eraseColor(-1);
        a aVarA = a(i, str, bitmapCreateBitmap);
        aVarA.d = false;
        return aVarA;
    }

    a a(int i, String str, int[] iArr, int i2, int i3) {
        return a(i, str, Bitmap.createBitmap(iArr, i2, i3, Bitmap.Config.ARGB_8888));
    }

    a a(int i, String str, Bitmap bitmap) {
        a aVar = new a(i, str, bitmap.getWidth(), bitmap.getHeight());
        int i2 = aVar.h * aVar.i * 4;
        if (b == null || b.capacity() < i2) {
            b = ByteBuffer.allocateDirect(i2 >= 4194304 ? i2 : 4194304);
            b.order(ByteOrder.nativeOrder());
        }
        b.rewind();
        bitmap.copyPixelsToBuffer(b);
        this.m.add(aVar);
        this.n.put(Integer.valueOf(i), aVar);
        synchronized (ADCNative.a) {
            ADCNative.nativeCreateTexture(this.f, this.e, i, str, b, aVar.f, aVar.g, aVar.h, aVar.i);
        }
        return aVar;
    }

    static class a {
        static int a = 1;
        int b;
        String c;
        int f;
        int g;
        int i;
        boolean d = true;
        int e = this.e;
        int e = this.e;
        int h = 1;

        a(int i, String str, int i2, int i3) {
            this.b = i;
            this.c = str;
            this.f = i2;
            this.g = i3;
            while (this.h < i2) {
                this.h <<= 1;
            }
            this.i = 1;
            while (this.i < i3) {
                this.i <<= 1;
            }
        }
    }
}
