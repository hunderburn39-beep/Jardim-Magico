package com.chartboost.sdk.Libraries;

import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class h {
    public final File a;
    public final File b;
    public final File c;
    public final File d;
    public final File e;
    public final File f;
    public final File g;

    h(File file) {
        this.a = new File(file, ".chartboost");
        if (!this.a.exists()) {
            this.a.mkdirs();
        }
        this.b = a(this.a, g.a.StyleSheets.toString());
        this.c = a(this.a, g.a.Html.toString());
        this.d = a(this.a, g.a.Images.toString());
        this.e = a(this.a, g.a.Javascript.toString());
        this.f = a(this.a, g.a.TemplateMetaData.toString());
        this.g = a(this.a, g.a.Videos.toString());
    }

    private static File a(File file, String str) {
        File file2 = new File(file, str);
        if (!file2.exists()) {
            file2.mkdir();
        }
        return file2;
    }
}
