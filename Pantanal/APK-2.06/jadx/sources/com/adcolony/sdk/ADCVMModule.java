package com.adcolony.sdk;

import android.app.Activity;
import com.heyzap.http.AsyncHttpResponseHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class ADCVMModule implements ad {
    boolean a;
    int b;
    ExecutorService c;
    private JSONArray d = s.b();
    private Runnable e;
    private Runnable f;
    private ADCJSVirtualMachine g;

    private class ADCJSVirtualMachine {
        private long b;
        private int c;

        private native long create(int i, byte[] bArr, byte[] bArr2);

        private native void delete(long j, int i);

        private native byte[] update(long j, int i, byte[] bArr);

        class a extends Exception {
            a(String str) {
                super(str);
            }
        }

        ADCJSVirtualMachine(int module_id, byte[] script, byte[] options) throws a {
            this.b = create(module_id, script, options);
            if (this.b < 0) {
                throw new a("Virtual machine could not be created.");
            }
            this.c = module_id;
        }

        void a() {
            delete(this.b, this.c);
        }

        byte[] a(byte[] bArr) throws a {
            byte[] bArrUpdate = update(this.b, this.c, bArr);
            if (bArrUpdate == null) {
                if (com.adcolony.sdk.a.l != null && (com.adcolony.sdk.a.l instanceof b)) {
                    au.a(new Runnable() { // from class: com.adcolony.sdk.ADCVMModule.ADCJSVirtualMachine.1
                        @Override // java.lang.Runnable
                        public void run() {
                            z zVar = new z("AdSession.finish_fullscreen_ad", 0);
                            s.b(zVar.b, "status", 1);
                            ((b) com.adcolony.sdk.a.l).a(zVar);
                        }
                    });
                }
                if (ADCVMModule.this.a) {
                    try {
                        StringBuilder sbAppend = new StringBuilder().append(com.adcolony.sdk.a.m.k.a);
                        h hVar = com.adcolony.sdk.a.m;
                        new File(sbAppend.append("7bf3a1e7bbd31e612eda3310c2cdb8075c43c6b5").toString()).delete();
                    } catch (Exception e) {
                    }
                }
                throw new a("Virtual machine error.");
            }
            return bArrUpdate;
        }
    }

    ADCVMModule(Activity activity, int module_id, String filepath, JSONObject info, ExecutorService es) {
        this.b = module_id;
        this.c = es;
        if (module_id == 1) {
        }
        u.b.b("----------------------------------------------------------------------");
        u.b.b("CREATING VM " + filepath);
        File file = new File(filepath);
        StringBuilder sb = new StringBuilder((int) file.length());
        if (module_id == 1) {
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath())));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
                sb.append('\n');
            }
            String string = info != null ? info.toString() : "";
            if (!a.m.F) {
                this.g = new ADCJSVirtualMachine(module_id, sb.toString().getBytes(), string.getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET));
            }
            bufferedReader.close();
        } catch (IOException e) {
            u.h.a("JavaScript file not found: ").b(filepath);
        } catch (Exception e2) {
            u.h.a("Unable to create virtual machine for: ").b(filepath);
        }
        u.b.b("----------------------------------------------------------------------");
    }

    @Override // com.adcolony.sdk.ad
    public int a() {
        return this.b;
    }

    @Override // com.adcolony.sdk.ad
    public void a(JSONObject jSONObject) {
        synchronized (this.d) {
            this.d.put(jSONObject);
        }
    }

    @Override // com.adcolony.sdk.ad
    public void b() {
        if (this.f == null) {
            this.f = new Runnable() { // from class: com.adcolony.sdk.ADCVMModule.1
                @Override // java.lang.Runnable
                public void run() {
                    if (!a.m.F) {
                        ADCVMModule.this.g.a();
                    }
                }
            };
        }
        this.c.submit(this.f);
        this.c.shutdown();
    }

    @Override // com.adcolony.sdk.ad
    public void c() {
        if (this.e == null) {
            this.e = new Runnable() { // from class: com.adcolony.sdk.ADCVMModule.2
                @Override // java.lang.Runnable
                public void run() {
                    String str;
                    JSONArray jSONArrayB;
                    String string = "";
                    synchronized (ADCVMModule.this.d) {
                        if (ADCVMModule.this.d.length() > 0) {
                            string = ADCVMModule.this.d.toString();
                            ADCVMModule.this.d = s.b();
                        }
                    }
                    if (a.m.F) {
                        str = null;
                    } else {
                        try {
                            str = new String(ADCVMModule.this.g.a(string.getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET)), AsyncHttpResponseHandler.DEFAULT_CHARSET);
                        } catch (Exception e) {
                            u.h.a("VM update failed: ").b(au.a(e));
                            a.m.d.a(ADCVMModule.this.b);
                            str = null;
                        }
                    }
                    if (str != null && str.length() > 2 && (jSONArrayB = s.b(str)) != null) {
                        for (int i = 0; i < jSONArrayB.length(); i++) {
                            JSONObject jSONObjectA = s.a(jSONArrayB, i);
                            if (jSONObjectA != null) {
                                a.m.d.a(jSONObjectA);
                            }
                        }
                    }
                }
            };
        }
        this.c.submit(this.e);
    }

    @Override // com.adcolony.sdk.ad
    public boolean d() {
        return this.a;
    }
}
