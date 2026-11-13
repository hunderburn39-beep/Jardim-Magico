package com.moat.analytics.mobile.tjy;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
class ay implements InvocationHandler {
    private static final Object[] a = new Object[0];
    private final ap b;
    private final ba c;
    private final bc d;
    private final LinkedList e;
    private final LinkedList f;
    private boolean g;
    private Object h;

    ay(ap apVar, ba baVar, bc bcVar) {
        com.moat.analytics.mobile.tjy.base.asserts.a.a(apVar);
        com.moat.analytics.mobile.tjy.base.asserts.a.a(baVar);
        com.moat.analytics.mobile.tjy.base.asserts.a.a(bcVar);
        this.b = apVar;
        this.c = baVar;
        this.d = bcVar;
        apVar.a(new az(this));
        this.e = new LinkedList();
        this.f = new LinkedList();
    }

    static Object a(ap apVar, ba baVar, bc bcVar) {
        Class clsA = bcVar.a();
        return Proxy.newProxyInstance(clsA.getClassLoader(), new Class[]{clsA}, new ay(apVar, baVar, bcVar));
    }

    private Object a(Method method) {
        try {
            if (Boolean.TYPE.equals(method.getReturnType())) {
                return true;
            }
        } catch (Exception e) {
            com.moat.analytics.mobile.tjy.base.exception.a.a(e);
        }
        return null;
    }

    private Object a(Method method, Object[] objArr) throws IllegalAccessException, InvocationTargetException {
        if (Object.class.equals(method.getDeclaringClass())) {
            String name = method.getName();
            Class clsA = this.d.a();
            if ("getClass".equals(name)) {
                return clsA;
            }
            if (!"toString".equals(name)) {
                return method.invoke(this, objArr);
            }
            Object objInvoke = method.invoke(this, objArr);
            return String.valueOf(objInvoke).replace(ay.class.getName(), clsA.getName());
        }
        if (this.g && this.h == null) {
            c();
            return a(method);
        }
        if (this.b.a() == ar.ON) {
            b();
            if (this.h != null) {
                return method.invoke(this.h, objArr);
            }
        }
        if (this.b.a() == ar.OFF && (!this.g || this.h != null)) {
            b(method, objArr);
        }
        return a(method);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        if (!this.g) {
            try {
                this.h = this.c.a().c(null);
            } catch (Exception e) {
            }
            this.g = true;
        }
        if (this.h == null) {
            return;
        }
        LinkedList<LinkedList> linkedList = new LinkedList();
        linkedList.add(this.e);
        linkedList.add(this.f);
        for (LinkedList<bb> linkedList2 : linkedList) {
            for (bb bbVar : linkedList2) {
                try {
                    Object[] objArr = new Object[bbVar.b.length];
                    WeakReference[] weakReferenceArr = bbVar.b;
                    int length = weakReferenceArr.length;
                    int i = 0;
                    int i2 = 0;
                    while (i < length) {
                        objArr[i2] = weakReferenceArr[i].get();
                        i++;
                        i2++;
                    }
                    bbVar.d.invoke(this.h, objArr);
                } catch (Exception e2) {
                }
            }
            linkedList2.clear();
        }
    }

    private void b(Method method, Object[] objArr) {
        az azVar = null;
        if (this.e.size() < 5) {
            this.e.add(new bb(this, method, objArr, azVar));
            return;
        }
        if (this.f.size() >= 10) {
            this.f.removeFirst();
        }
        this.f.add(new bb(this, method, objArr, azVar));
    }

    private void c() {
        this.e.clear();
        this.f.clear();
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) {
        try {
            return a(method, objArr);
        } catch (Exception e) {
            com.moat.analytics.mobile.tjy.base.exception.a.a(e);
            return a(method);
        }
    }
}
