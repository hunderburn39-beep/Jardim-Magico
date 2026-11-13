package com.tapjoy.internal;

import android.os.Handler;
import android.os.Looper;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/* JADX INFO: loaded from: classes.dex */
public final class ek {
    public static Object a(Object obj, Class cls) {
        return Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new a(obj, Thread.currentThread(), Looper.myLooper()));
    }

    static class a implements InvocationHandler {
        private final Object a;
        private final Thread b;
        private final Looper c;

        public a(Object obj, Thread thread, Looper looper) {
            this.a = obj;
            this.b = thread;
            this.c = looper;
        }

        @Override // java.lang.reflect.InvocationHandler
        public final Object invoke(Object proxy, final Method method, final Object[] args) {
            if (this.b == Thread.currentThread()) {
                return method.invoke(this.a, args);
            }
            if (!method.getReturnType().equals(Void.TYPE)) {
                throw new UnsupportedOperationException("method not return void: " + method.getName());
            }
            Runnable runnable = new Runnable() { // from class: com.tapjoy.internal.ek.a.1
                @Override // java.lang.Runnable
                public final void run() {
                    try {
                        method.invoke(a.this.a, args);
                    } catch (IllegalAccessException e) {
                        throw cs.a(e);
                    } catch (IllegalArgumentException e2) {
                        throw cs.a(e2);
                    } catch (InvocationTargetException e3) {
                        throw cs.a(e3);
                    }
                }
            };
            if (this.c != null && new Handler(this.c).post(runnable)) {
                return null;
            }
            if (this.b == fm.b() && fm.a.a(runnable)) {
                return null;
            }
            Looper mainLooper = Looper.getMainLooper();
            if (mainLooper == null || !new Handler(mainLooper).post(runnable)) {
                return method.invoke(this.a, args);
            }
            return null;
        }
    }
}
