package com.adcolony.sdk;

/* JADX INFO: loaded from: classes.dex */
class r {
    private String a;
    private String b;
    private String c;
    private String d = "%s_%s_%s";

    public r(String str, String str2, String str3) {
        this.a = a(str);
        this.b = a(str2);
        this.c = a(str3);
    }

    private String a(String str) {
        return str.replaceAll("\\.", "-");
    }

    public String a() {
        return String.format(this.d, b(), c(), d());
    }

    public String b() {
        return this.a;
    }

    public String c() {
        return this.b;
    }

    public String d() {
        return this.c;
    }
}
