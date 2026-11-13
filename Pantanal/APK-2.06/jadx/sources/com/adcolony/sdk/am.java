package com.adcolony.sdk;

import android.os.StatFs;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
class am {
    String a;
    String b;
    String c;
    String d;
    File e;
    File f;
    File g;

    am() {
    }

    boolean a() {
        u.d.b("Configuring storage");
        this.a = c() + "/adc3/";
        this.b = this.a + "media/";
        this.e = new File(this.b);
        if (!this.e.isDirectory()) {
            this.e.delete();
            this.e.mkdirs();
        }
        if (!this.e.isDirectory()) {
            a.m.b(true);
            return false;
        }
        if (a(this.b) < 2.097152E7d) {
            u.e.b("Not enough memory available at media path, disabling AdColony.");
            a.m.b(true);
            return false;
        }
        this.c = c() + "/adc3/data/";
        this.f = new File(this.c);
        if (!this.f.isDirectory()) {
            this.f.delete();
        }
        this.f.mkdirs();
        this.d = this.a + "tmp/";
        this.g = new File(this.d);
        if (!this.g.isDirectory()) {
            this.g.delete();
            this.g.mkdirs();
        }
        return true;
    }

    boolean b() {
        if (this.e == null || this.f == null || this.g == null) {
            return false;
        }
        if (!this.e.isDirectory()) {
            this.e.delete();
        }
        if (!this.f.isDirectory()) {
            this.f.delete();
        }
        if (!this.g.isDirectory()) {
            this.g.delete();
        }
        this.e.mkdirs();
        this.f.mkdirs();
        this.g.mkdirs();
        return true;
    }

    String c() {
        return a.l == null ? "" : a.l.getFilesDir().getAbsolutePath();
    }

    double a(String str) {
        try {
            StatFs statFs = new StatFs(str);
            return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
        } catch (RuntimeException e) {
            return 0.0d;
        }
    }
}
