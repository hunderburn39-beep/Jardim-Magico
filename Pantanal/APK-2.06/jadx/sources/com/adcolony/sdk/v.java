package com.adcolony.sdk;

import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
class v {
    private Date a;
    private int b;
    private String c;
    private String d;

    public v(Date date, int i, String str, String str2) {
        this.a = date;
        this.b = i;
        this.c = str;
        this.d = str2;
    }

    public String a() {
        switch (this.b) {
            case 2:
                return "Verbose";
            case 3:
                return "Debug";
            case 4:
                return "Info";
            case 5:
                return "Warn";
            case 6:
                return "Error";
            case 7:
                return "Assert";
            default:
                return "UNKNOWN LOG LEVEL";
        }
    }

    public int b() {
        return this.b;
    }

    public String c() {
        return this.d;
    }

    public String d() {
        return this.c;
    }

    public Date e() {
        return this.a;
    }

    public String toString() {
        return e().toString() + " " + a() + "/" + d() + ": " + c();
    }
}
