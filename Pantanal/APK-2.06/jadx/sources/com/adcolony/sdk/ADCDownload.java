package com.adcolony.sdk;

import com.google.android.gms.wallet.WalletConstants;
import com.heyzap.http.AsyncHttpResponseHandler;
import com.tapjoy.TJAdUnitConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class ADCDownload implements Runnable {
    boolean e;
    int f;
    int g;
    private HttpURLConnection i;
    private InputStream j;
    private JSONObject k;
    private z l;
    private Listener m;
    private String n;
    private Map<String, List<String>> o;
    String a = "";
    String b = "";
    String c = "";
    String d = "";
    ExecutorService h = Executors.newSingleThreadExecutor();

    public interface Listener {
        void on_download_finished(ADCDownload aDCDownload, z zVar, Map<String, List<String>> map);
    }

    ADCDownload(z message, Listener listener) {
        this.l = message;
        this.m = listener;
        if (a()) {
            try {
                this.h.submit(this);
            } catch (RejectedExecutionException e) {
                this.e = false;
                listener.on_download_finished(this, message, this.o);
            }
        }
    }

    boolean a() {
        try {
            this.a = s.b(this.l.b, "url");
            this.c = s.b(this.l.b, "filepath");
            this.n = s.b(this.l.b, "content");
            this.k = s.f(this.l.b, TJAdUnitConstants.String.VIDEO_INFO);
            this.d = s.b(this.l.b, "content_type");
            s.b(this.l.b, TMXConstants.TAG_DATA_ATTRIBUTE_ENCODING);
            s.d(this.l.b, "encrypt");
            boolean zD = s.d(this.l.b, "no_redirect");
            if (this.a.startsWith("file://")) {
                if (this.a.startsWith("file:///android_asset/")) {
                    if (a.l == null) {
                        return false;
                    }
                    this.j = a.l.getAssets().open(this.a.substring("file:///android_asset/".length()));
                } else {
                    this.j = new FileInputStream(this.a.substring("file://".length()));
                }
                return true;
            }
            HttpURLConnection.setFollowRedirects(!zD);
            this.i = (HttpURLConnection) new URL(this.a).openConnection();
            return true;
        } catch (MalformedURLException e) {
            u.h.a("MalformedURLException: ").b(e.toString());
            this.e = true;
            this.m.on_download_finished(this, this.l, null);
            return false;
        } catch (IOException e2) {
            u.f.a("Download of ").a(this.a).a(" failed: ").b(e2.toString());
            this.e = false;
            this.g = WalletConstants.ERROR_CODE_INVALID_PARAMETERS;
            this.m.on_download_finished(this, this.l, null);
            return false;
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (this.j != null) {
                b();
                return;
            }
            if (this.l.a.equals("WebServices.get") || this.l.a.equals("WebServices.download")) {
                this.g = this.i.getResponseCode();
                int contentLength = this.i.getContentLength();
                this.f = contentLength;
                int i = contentLength != -1 ? contentLength : 1024;
                InputStream inputStream = this.i.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                this.o = this.i.getHeaderFields();
                if (this.l.a.equals("WebServices.get")) {
                    StringBuilder sb = new StringBuilder(i);
                    while (true) {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        sb.append(line);
                        sb.append("\n");
                    }
                    inputStream.close();
                    this.b = sb.toString();
                    this.f = this.b.length();
                } else {
                    byte[] bArr = new byte[1024];
                    FileOutputStream fileOutputStream = new FileOutputStream(this.c);
                    while (true) {
                        int i2 = inputStream.read(bArr);
                        if (i2 == -1) {
                            break;
                        } else {
                            fileOutputStream.write(bArr, 0, i2);
                        }
                    }
                    fileOutputStream.close();
                }
                u.d.a("Downloaded ").b(this.a);
                this.e = true;
                this.m.on_download_finished(this, this.l, this.o);
                return;
            }
            this.i.setDoOutput(true);
            this.i.setFixedLengthStreamingMode(this.n.length());
            this.i.getRequestMethod();
            this.i.setRequestProperty("Accept-Charset", AsyncHttpResponseHandler.DEFAULT_CHARSET);
            if (!this.d.equals("")) {
                this.i.setRequestProperty("Content-Type", this.d);
            }
            new PrintStream(this.i.getOutputStream()).print(this.n);
            this.i.connect();
            int responseCode = this.i.getResponseCode();
            this.g = responseCode;
            if (responseCode == 200) {
                InputStream inputStream2 = this.i.getInputStream();
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream2, AsyncHttpResponseHandler.DEFAULT_CHARSET));
                StringBuilder sb2 = new StringBuilder(inputStream2.available());
                this.o = this.i.getHeaderFields();
                while (true) {
                    String line2 = bufferedReader2.readLine();
                    if (line2 != null) {
                        sb2.append(line2);
                        sb2.append("\n");
                    } else {
                        bufferedReader2.close();
                        inputStream2.close();
                        this.b = sb2.toString();
                        this.f = this.b.length();
                        this.e = true;
                        this.m.on_download_finished(this, this.l, this.o);
                        return;
                    }
                }
            }
            this.e = false;
            this.m.on_download_finished(this, this.l, this.o);
        } catch (IOException e) {
            this.g = WalletConstants.ERROR_CODE_INVALID_PARAMETERS;
            u.f.a("Download of ").a(this.a).a(" failed: ").b(e.toString());
        } catch (OutOfMemoryError e2) {
            u.g.b("Out of memory error - disabling AdColony.");
            a.m.b(true);
        } catch (MalformedURLException e3) {
            u.h.a("MalformedURLException: ").b(e3.toString());
        }
    }

    void b() throws IOException {
        if (this.c.length() == 0) {
            int iAvailable = this.j.available();
            if (iAvailable <= 1) {
                iAvailable = 1024;
            }
            StringBuilder sb = new StringBuilder(iAvailable);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.j), 1024);
            for (int i = bufferedReader.read(); i != -1; i = bufferedReader.read()) {
                sb.append((char) i);
            }
            this.b = sb.toString();
            this.f = this.b.length();
        } else {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(this.c).getAbsolutePath());
            this.f = 0;
            byte[] bArr = new byte[1024];
            int i2 = this.j.read(bArr, 0, 1024);
            while (i2 != -1) {
                this.f += i2;
                fileOutputStream.write(bArr, 0, i2);
                i2 = this.j.read(bArr, 0, 1024);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
        }
        this.j.close();
        this.e = true;
        this.m.on_download_finished(this, this.l, null);
    }
}
