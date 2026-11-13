package com.adcolony.sdk;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class u {
    static u a = new u(3, false);
    static u b = new u(3, true);
    static u c = new u(2, false);
    static u d = new u(2, true);
    static u e = new u(1, false);
    static u f = new u(1, true);
    static u g = new u(0, false);
    static u h = new u(0, true);
    int i;
    boolean j;
    StringBuilder k = new StringBuilder();

    u(int i, boolean z) {
        this.i = i;
        this.j = z;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x002e A[Catch: all -> 0x0050, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0005, B:9:0x0020, B:11:0x0024, B:14:0x002e, B:16:0x0037), top: B:21:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:16:0x0037 A[Catch: all -> 0x0050, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0005, B:9:0x0020, B:11:0x0024, B:14:0x002e, B:16:0x0037), top: B:21:0x0001 }] */
    synchronized u a(char c2) {
        if (a.m == null) {
            Log.d("AdColony", "" + c2);
        } else if (this.j) {
            w wVar = a.m.j;
            if (w.a) {
                this.k.append(c2);
                if (c2 == '\n') {
                    w wVar2 = a.m.j;
                    w.a(this.i, this.k.toString(), this.j);
                    this.k.setLength(0);
                }
            }
        } else {
            this.k.append(c2);
            if (c2 == '\n') {
                w wVar3 = a.m.j;
                w.a(this.i, this.k.toString(), this.j);
                this.k.setLength(0);
            }
        }
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001b A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:15:0x001d A[Catch: all -> 0x0037, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0005, B:9:0x000d, B:11:0x0011, B:15:0x001d, B:17:0x0026, B:19:0x002d), top: B:24:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:17:0x0026 A[Catch: all -> 0x0037, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0005, B:9:0x000d, B:11:0x0011, B:15:0x001d, B:17:0x0026, B:19:0x002d), top: B:24:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:19:0x002d A[Catch: all -> 0x0037, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0005, B:9:0x000d, B:11:0x0011, B:15:0x001d, B:17:0x0026, B:19:0x002d), top: B:24:0x0001 }] */
    synchronized u a(String str) {
        int length;
        int i;
        if (a.m == null) {
            Log.d("AdColony", str);
        } else if (this.j) {
            w wVar = a.m.j;
            if (w.a) {
                if (str == null) {
                    this.k.append("null");
                } else {
                    length = str.length();
                    for (i = 0; i < length; i++) {
                        a(str.charAt(i));
                    }
                }
            }
        } else if (str == null) {
            this.k.append("null");
        } else {
            length = str.length();
            while (i < length) {
                a(str.charAt(i));
            }
        }
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0021 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:16:0x0023 A[Catch: all -> 0x0032, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0005, B:9:0x0011, B:11:0x0015, B:13:0x0019, B:16:0x0023, B:18:0x002a), top: B:23:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:18:0x002a A[Catch: all -> 0x0032, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0005, B:9:0x0011, B:11:0x0015, B:13:0x0019, B:16:0x0023, B:18:0x002a), top: B:23:0x0001 }] */
    synchronized u a(Object obj) {
        if (a.m == null) {
            Log.d("AdColony", obj.toString());
        } else if (this.j) {
            if (this.j) {
                w wVar = a.m.j;
                if (w.a) {
                    if (obj == null) {
                        a("null");
                    } else {
                        a(obj.toString());
                    }
                }
            }
        } else if (obj == null) {
            a("null");
        } else {
            a(obj.toString());
        }
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x002e A[Catch: all -> 0x0036, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0005, B:9:0x0020, B:11:0x0024, B:14:0x002e), top: B:19:0x0001 }] */
    synchronized u a(double d2) {
        if (a.m == null) {
            Log.d("AdColony", "" + d2);
        } else if (this.j) {
            w wVar = a.m.j;
            if (w.a) {
                au.a(d2, 2, this.k);
            }
        } else {
            au.a(d2, 2, this.k);
        }
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x002e A[Catch: all -> 0x0035, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0005, B:9:0x0020, B:11:0x0024, B:14:0x002e), top: B:19:0x0001 }] */
    synchronized u a(int i) {
        if (a.m == null) {
            Log.d("AdColony", "" + i);
        } else if (this.j) {
            w wVar = a.m.j;
            if (w.a) {
                this.k.append(i);
            }
        } else {
            this.k.append(i);
        }
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x002e A[Catch: all -> 0x0035, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0005, B:9:0x0020, B:11:0x0024, B:14:0x002e), top: B:19:0x0001 }] */
    synchronized u a(boolean z) {
        if (a.m == null) {
            Log.d("AdColony", "" + z);
        } else if (this.j) {
            w wVar = a.m.j;
            if (w.a) {
                this.k.append(z);
            }
        } else {
            this.k.append(z);
        }
        return this;
    }

    synchronized u b(Object obj) {
        u uVarA;
        if (a.m == null) {
            Log.d("AdColony", obj.toString());
            uVarA = this;
        } else {
            a(obj);
            uVarA = a('\n');
        }
        return uVarA;
    }

    synchronized u b(double d2) {
        u uVarA;
        if (a.m == null) {
            Log.d("AdColony", "" + d2);
            uVarA = this;
        } else {
            a(d2);
            uVarA = a('\n');
        }
        return uVarA;
    }

    synchronized u b(int i) {
        u uVarA;
        if (a.m == null) {
            Log.d("AdColony", "" + i);
            uVarA = this;
        } else {
            a(i);
            uVarA = a('\n');
        }
        return uVarA;
    }

    synchronized u b(boolean z) {
        u uVarA;
        if (a.m == null) {
            Log.d("AdColony", "" + z);
            uVarA = this;
        } else {
            a(z);
            uVarA = a('\n');
        }
        return uVarA;
    }
}
