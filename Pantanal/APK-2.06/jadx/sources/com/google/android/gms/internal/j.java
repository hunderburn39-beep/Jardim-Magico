package com.google.android.gms.internal;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.heyzap.http.AsyncHttpResponseHandler;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public abstract class j extends i {
    private static Method jR;
    private static Method jS;
    private static Method jT;
    private static Method jU;
    private static Method jV;
    private static Method jW;
    private static String jX;
    private static p jY;
    private static long startTime = 0;
    static boolean jZ = false;

    static class a extends Exception {
        public a() {
        }

        public a(Throwable th) {
            super(th);
        }
    }

    protected j(Context context, n nVar, o oVar) {
        super(context, nVar, oVar);
    }

    static String a(Context context, n nVar) throws a {
        if (jT == null) {
            throw new a();
        }
        try {
            ByteBuffer byteBuffer = (ByteBuffer) jT.invoke(null, context);
            if (byteBuffer == null) {
                throw new a();
            }
            return nVar.a(byteBuffer.array(), true);
        } catch (IllegalAccessException e) {
            throw new a(e);
        } catch (InvocationTargetException e2) {
            throw new a(e2);
        }
    }

    static ArrayList<Long> a(MotionEvent motionEvent, DisplayMetrics displayMetrics) throws a {
        if (jU == null || motionEvent == null) {
            throw new a();
        }
        try {
            return (ArrayList) jU.invoke(null, motionEvent, displayMetrics);
        } catch (IllegalAccessException e) {
            throw new a(e);
        } catch (InvocationTargetException e2) {
            throw new a(e2);
        }
    }

    protected static synchronized void a(String str, Context context, n nVar) {
        if (!jZ) {
            try {
                jY = new p(nVar, null);
                jX = str;
                e(context);
                startTime = w().longValue();
                jZ = true;
            } catch (a e) {
            } catch (UnsupportedOperationException e2) {
            }
        }
    }

    static String b(Context context, n nVar) throws a {
        if (jW == null) {
            throw new a();
        }
        try {
            ByteBuffer byteBuffer = (ByteBuffer) jW.invoke(null, context);
            if (byteBuffer == null) {
                throw new a();
            }
            return nVar.a(byteBuffer.array(), true);
        } catch (IllegalAccessException e) {
            throw new a(e);
        } catch (InvocationTargetException e2) {
            throw new a(e2);
        }
    }

    private static String b(byte[] bArr, String str) throws a {
        try {
            return new String(jY.c(bArr, str), AsyncHttpResponseHandler.DEFAULT_CHARSET);
        } catch (p.a e) {
            throw new a(e);
        } catch (UnsupportedEncodingException e2) {
            throw new a(e2);
        }
    }

    static String d(Context context) throws a {
        if (jV == null) {
            throw new a();
        }
        try {
            String str = (String) jV.invoke(null, context);
            if (str == null) {
                throw new a();
            }
            return str;
        } catch (IllegalAccessException e) {
            throw new a(e);
        } catch (InvocationTargetException e2) {
            throw new a(e2);
        }
    }

    private static void e(Context context) throws a {
        try {
            byte[] bArrB = jY.b(r.getKey());
            byte[] bArrC = jY.c(bArrB, r.A());
            File cacheDir = context.getCacheDir();
            if (cacheDir == null && (cacheDir = context.getDir("dex", 0)) == null) {
                throw new a();
            }
            File fileCreateTempFile = File.createTempFile("ads", ".jar", cacheDir);
            FileOutputStream fileOutputStream = new FileOutputStream(fileCreateTempFile);
            fileOutputStream.write(bArrC, 0, bArrC.length);
            fileOutputStream.close();
            DexClassLoader dexClassLoader = new DexClassLoader(fileCreateTempFile.getAbsolutePath(), cacheDir.getAbsolutePath(), null, context.getClassLoader());
            Class clsLoadClass = dexClassLoader.loadClass(b(bArrB, r.B()));
            Class clsLoadClass2 = dexClassLoader.loadClass(b(bArrB, r.H()));
            Class clsLoadClass3 = dexClassLoader.loadClass(b(bArrB, r.F()));
            Class clsLoadClass4 = dexClassLoader.loadClass(b(bArrB, r.L()));
            Class clsLoadClass5 = dexClassLoader.loadClass(b(bArrB, r.D()));
            Class clsLoadClass6 = dexClassLoader.loadClass(b(bArrB, r.J()));
            jR = clsLoadClass.getMethod(b(bArrB, r.C()), new Class[0]);
            jS = clsLoadClass2.getMethod(b(bArrB, r.I()), new Class[0]);
            jT = clsLoadClass3.getMethod(b(bArrB, r.G()), Context.class);
            jU = clsLoadClass4.getMethod(b(bArrB, r.M()), MotionEvent.class, DisplayMetrics.class);
            jV = clsLoadClass5.getMethod(b(bArrB, r.E()), Context.class);
            jW = clsLoadClass6.getMethod(b(bArrB, r.K()), Context.class);
            String name = fileCreateTempFile.getName();
            fileCreateTempFile.delete();
            new File(cacheDir, name.replace(".jar", ".dex")).delete();
        } catch (p.a e) {
            throw new a(e);
        } catch (FileNotFoundException e2) {
            throw new a(e2);
        } catch (IOException e3) {
            throw new a(e3);
        } catch (ClassNotFoundException e4) {
            throw new a(e4);
        } catch (NoSuchMethodException e5) {
            throw new a(e5);
        } catch (NullPointerException e6) {
            throw new a(e6);
        }
    }

    static String v() throws a {
        if (jX == null) {
            throw new a();
        }
        return jX;
    }

    static Long w() throws a {
        if (jR == null) {
            throw new a();
        }
        try {
            return (Long) jR.invoke(null, new Object[0]);
        } catch (IllegalAccessException e) {
            throw new a(e);
        } catch (InvocationTargetException e2) {
            throw new a(e2);
        }
    }

    static String x() throws a {
        if (jS == null) {
            throw new a();
        }
        try {
            return (String) jS.invoke(null, new Object[0]);
        } catch (IllegalAccessException e) {
            throw new a(e);
        } catch (InvocationTargetException e2) {
            throw new a(e2);
        }
    }

    @Override // com.google.android.gms.internal.i
    protected void b(Context context) {
        try {
            try {
                a(1, x());
            } catch (a e) {
            }
            try {
                a(2, v());
            } catch (a e2) {
            }
            try {
                a(25, w().longValue());
            } catch (a e3) {
            }
            try {
                a(24, d(context));
            } catch (a e4) {
            }
        } catch (IOException e5) {
        }
    }

    @Override // com.google.android.gms.internal.i
    protected void c(Context context) {
        try {
            try {
                a(2, v());
            } catch (IOException e) {
                return;
            }
        } catch (a e2) {
        }
        try {
            a(1, x());
        } catch (a e3) {
        }
        try {
            long jLongValue = w().longValue();
            a(25, jLongValue);
            if (startTime != 0) {
                a(17, jLongValue - startTime);
                a(23, startTime);
            }
        } catch (a e4) {
        }
        try {
            ArrayList<Long> arrayListA = a(this.jN, this.jO);
            a(14, arrayListA.get(0).longValue());
            a(15, arrayListA.get(1).longValue());
            if (arrayListA.size() >= 3) {
                a(16, arrayListA.get(2).longValue());
            }
        } catch (a e5) {
        }
        try {
            a(27, a(context, this.jP));
        } catch (a e6) {
        }
        try {
            a(29, b(context, this.jP));
        } catch (a e7) {
        }
    }
}
