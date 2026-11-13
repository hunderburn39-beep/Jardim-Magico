package com.fyber.a;

import android.support.annotation.NonNull;
import com.fyber.Fyber;
import com.fyber.requesters.RequestError;
import com.fyber.utils.StringUtils;

/* JADX INFO: compiled from: Credentials.java */
/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static a a = new a("", "");
    private final String b;
    private String c;
    private final String d;

    /* synthetic */ a(C0016a c0016a, byte b) {
        this(c0016a);
    }

    private a(String str, String str2) {
        this.b = str;
        this.c = str2;
        this.d = null;
    }

    private a(C0016a c0016a) {
        this.b = c0016a.a;
        this.c = c0016a.b;
        this.d = c0016a.c;
    }

    public final String a() {
        return this.b;
    }

    public final String b() {
        return this.c;
    }

    public final void a(@NonNull String str) {
        if (StringUtils.nullOrEmpty(str)) {
            throw new IllegalArgumentException("Invalid userId");
        }
        if (Fyber.getConfigs().d()) {
            this.c = str;
            return;
        }
        throw new IllegalStateException(RequestError.SDK_NOT_STARTED.getDescription());
    }

    public final String c() {
        return this.d;
    }

    public static boolean b(String str) {
        return StringUtils.notNullNorEmpty(str) && str.length() > 16;
    }

    public final String toString() {
        Object[] objArr = new Object[3];
        objArr[0] = this.b;
        objArr[1] = StringUtils.notNullNorEmpty(this.c) ? this.c : "N/A";
        objArr[2] = StringUtils.notNullNorEmpty(this.d) ? this.d : "N/A";
        return String.format("AppId - %s\nUserId - %s\nSecurityToken - %s", objArr);
    }

    /* JADX INFO: renamed from: com.fyber.a.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: Credentials.java */
    public static class C0016a {
        private String a;
        private String b;
        private String c;

        public C0016a(@NonNull String str) {
            this.a = StringUtils.trim(str);
        }

        public final C0016a a(String str) {
            this.a = StringUtils.trim(str);
            return this;
        }

        public final C0016a b(String str) {
            this.b = str;
            return this;
        }

        public final C0016a c(String str) {
            this.c = StringUtils.trim(str);
            return this;
        }

        public final a a() {
            return new a(this, (byte) 0);
        }
    }
}
