package com.google.android.gms.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.google.android.gms.drive.DriveFile;
import java.util.HashMap;
import java.util.Map;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class dz extends WebView implements DownloadListener {
    private final Object li;
    private final WindowManager ls;
    private ak nq;
    private final dx nr;
    private final l oJ;
    private final ea ru;
    private final a rv;
    private cc rw;
    private boolean rx;
    private boolean ry;

    private static class a extends MutableContextWrapper {
        private Context lp;
        private Activity rz;

        public a(Context context) {
            super(context);
            setBaseContext(context);
        }

        @Override // android.content.MutableContextWrapper
        public void setBaseContext(Context base) {
            this.lp = base.getApplicationContext();
            this.rz = base instanceof Activity ? (Activity) base : null;
            super.setBaseContext(this.lp);
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public void startActivity(Intent intent) {
            if (this.rz != null) {
                this.rz.startActivity(intent);
            } else {
                intent.setFlags(DriveFile.MODE_READ_ONLY);
                this.lp.startActivity(intent);
            }
        }
    }

    private dz(a aVar, ak akVar, boolean z, boolean z2, l lVar, dx dxVar) {
        super(aVar);
        this.li = new Object();
        this.rv = aVar;
        this.nq = akVar;
        this.rx = z;
        this.oJ = lVar;
        this.nr = dxVar;
        this.ls = (WindowManager) getContext().getSystemService("window");
        setBackgroundColor(0);
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        dq.a(aVar, dxVar.rq, settings);
        if (Build.VERSION.SDK_INT >= 17) {
            dt.a(getContext(), settings);
        } else if (Build.VERSION.SDK_INT >= 11) {
            ds.a(getContext(), settings);
        }
        setDownloadListener(this);
        if (Build.VERSION.SDK_INT >= 11) {
            this.ru = new ec(this, z2);
        } else {
            this.ru = new ea(this, z2);
        }
        setWebViewClient(this.ru);
        if (Build.VERSION.SDK_INT >= 14) {
            setWebChromeClient(new ed(this));
        } else if (Build.VERSION.SDK_INT >= 11) {
            setWebChromeClient(new eb(this));
        }
        bM();
    }

    public static dz a(Context context, ak akVar, boolean z, boolean z2, l lVar, dx dxVar) {
        return new dz(new a(context), akVar, z, z2, lVar, dxVar);
    }

    private void bM() {
        synchronized (this.li) {
            if (this.rx || this.nq.lT) {
                if (Build.VERSION.SDK_INT < 14) {
                    dw.v("Disabling hardware acceleration on an overlay.");
                    bN();
                } else {
                    dw.v("Enabling hardware acceleration on an overlay.");
                    bO();
                }
            } else if (Build.VERSION.SDK_INT < 18) {
                dw.v("Disabling hardware acceleration on an AdView.");
                bN();
            } else {
                dw.v("Enabling hardware acceleration on an AdView.");
                bO();
            }
        }
    }

    private void bN() {
        synchronized (this.li) {
            if (!this.ry && Build.VERSION.SDK_INT >= 11) {
                ds.d(this);
            }
            this.ry = true;
        }
    }

    private void bO() {
        synchronized (this.li) {
            if (this.ry && Build.VERSION.SDK_INT >= 11) {
                ds.e(this);
            }
            this.ry = false;
        }
    }

    public ak R() {
        ak akVar;
        synchronized (this.li) {
            akVar = this.nq;
        }
        return akVar;
    }

    public void a(Context context, ak akVar) {
        synchronized (this.li) {
            this.rv.setBaseContext(context);
            this.rw = null;
            this.nq = akVar;
            this.rx = false;
            dq.b(this);
            loadUrl("about:blank");
            this.ru.reset();
        }
    }

    public void a(ak akVar) {
        synchronized (this.li) {
            this.nq = akVar;
            requestLayout();
        }
    }

    public void a(cc ccVar) {
        synchronized (this.li) {
            this.rw = ccVar;
        }
    }

    public void a(String str, Map<String, ?> map) {
        try {
            b(str, dq.p(map));
        } catch (JSONException e) {
            dw.z("Could not convert parameters to JSON.");
        }
    }

    public void a(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        String string = jSONObject.toString();
        StringBuilder sb = new StringBuilder();
        sb.append("javascript:" + str + "(");
        sb.append(string);
        sb.append(");");
        loadUrl(sb.toString());
    }

    public void b(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        String string = jSONObject.toString();
        StringBuilder sb = new StringBuilder();
        sb.append("javascript:AFMA_ReceiveMessage('");
        sb.append(str);
        sb.append("'");
        sb.append(",");
        sb.append(string);
        sb.append(");");
        dw.y("Dispatching AFMA event: " + ((Object) sb));
        loadUrl(sb.toString());
    }

    public void bE() {
        if (bI().bP()) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            Display defaultDisplay = this.ls.getDefaultDisplay();
            defaultDisplay.getMetrics(displayMetrics);
            try {
                b("onScreenInfoChanged", new JSONObject().put("width", displayMetrics.widthPixels).put("height", displayMetrics.heightPixels).put("density", displayMetrics.density).put("rotation", defaultDisplay.getRotation()));
            } catch (JSONException e) {
                dw.b("Error occured while obtaining screen information.", e);
            }
        }
    }

    public void bF() {
        HashMap map = new HashMap(1);
        map.put("version", this.nr.rq);
        a("onhide", map);
    }

    public void bG() {
        HashMap map = new HashMap(1);
        map.put("version", this.nr.rq);
        a("onshow", map);
    }

    public cc bH() {
        cc ccVar;
        synchronized (this.li) {
            ccVar = this.rw;
        }
        return ccVar;
    }

    public ea bI() {
        return this.ru;
    }

    public l bJ() {
        return this.oJ;
    }

    public dx bK() {
        return this.nr;
    }

    public boolean bL() {
        boolean z;
        synchronized (this.li) {
            z = this.rx;
        }
        return z;
    }

    @Override // android.webkit.DownloadListener
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long size) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.parse(url), mimeType);
            getContext().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            dw.v("Couldn't find an Activity to view url/mimetype: " + url + " / " + mimeType);
        }
    }

    @Override // android.webkit.WebView, android.widget.AbsoluteLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        synchronized (this.li) {
            if (isInEditMode() || this.rx) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                return;
            }
            int mode = View.MeasureSpec.getMode(widthMeasureSpec);
            int size = View.MeasureSpec.getSize(widthMeasureSpec);
            int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
            int size2 = View.MeasureSpec.getSize(heightMeasureSpec);
            int i = (mode == Integer.MIN_VALUE || mode == 1073741824) ? size : Integer.MAX_VALUE;
            int i2 = (mode2 == Integer.MIN_VALUE || mode2 == 1073741824) ? size2 : Integer.MAX_VALUE;
            if (this.nq.widthPixels > i || this.nq.heightPixels > i2) {
                dw.z("Not enough space to show ad. Needs " + this.nq.widthPixels + TMXConstants.TAG_OBJECT_ATTRIBUTE_X + this.nq.heightPixels + " pixels, but only has " + size + TMXConstants.TAG_OBJECT_ATTRIBUTE_X + size2 + " pixels.");
                if (getVisibility() != 8) {
                    setVisibility(4);
                }
                setMeasuredDimension(0, 0);
            } else {
                if (getVisibility() != 8) {
                    setVisibility(0);
                }
                setMeasuredDimension(this.nq.widthPixels, this.nq.heightPixels);
            }
        }
    }

    @Override // android.webkit.WebView, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (this.oJ != null) {
            this.oJ.a(event);
        }
        return super.onTouchEvent(event);
    }

    public void p(boolean z) {
        synchronized (this.li) {
            this.rx = z;
            bM();
        }
    }

    public void setContext(Context context) {
        this.rv.setBaseContext(context);
    }
}
