package com.google.android.gms.internal;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.heyzap.http.AsyncHttpResponseHandler;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/* JADX INFO: loaded from: classes.dex */
public abstract class i implements h {
    protected MotionEvent jN;
    protected DisplayMetrics jO;
    protected n jP;
    private o jQ;

    protected i(Context context, n nVar, o oVar) {
        this.jP = nVar;
        this.jQ = oVar;
        try {
            this.jO = context.getResources().getDisplayMetrics();
        } catch (UnsupportedOperationException e) {
            this.jO = new DisplayMetrics();
            this.jO.density = 1.0f;
        }
    }

    private String a(Context context, String str, boolean z) {
        byte[] bArrU;
        try {
            synchronized (this) {
                t();
                if (z) {
                    c(context);
                } else {
                    b(context);
                }
                bArrU = u();
            }
            return bArrU.length == 0 ? Integer.toString(5) : a(bArrU, str);
        } catch (UnsupportedEncodingException e) {
            return Integer.toString(7);
        } catch (IOException e2) {
            return Integer.toString(3);
        } catch (NoSuchAlgorithmException e3) {
            return Integer.toString(7);
        }
    }

    private void t() {
        this.jQ.reset();
    }

    private byte[] u() throws IOException {
        return this.jQ.z();
    }

    @Override // com.google.android.gms.internal.h
    public String a(Context context) {
        return a(context, (String) null, false);
    }

    @Override // com.google.android.gms.internal.h
    public String a(Context context, String str) {
        return a(context, str, true);
    }

    String a(byte[] bArr, String str) throws NoSuchAlgorithmException, IOException {
        byte[] bArrArray;
        if (bArr.length > 239) {
            t();
            a(20, 1L);
            bArr = u();
        }
        if (bArr.length < 239) {
            byte[] bArr2 = new byte[239 - bArr.length];
            new SecureRandom().nextBytes(bArr2);
            bArrArray = ByteBuffer.allocate(240).put((byte) bArr.length).put(bArr).put(bArr2).array();
        } else {
            bArrArray = ByteBuffer.allocate(240).put((byte) bArr.length).put(bArr).array();
        }
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(bArrArray);
        byte[] bArrArray2 = ByteBuffer.allocate(256).put(messageDigest.digest()).put(bArrArray).array();
        byte[] bArr3 = new byte[256];
        new f().a(bArrArray2, bArr3);
        if (str != null && str.length() > 0) {
            a(str, bArr3);
        }
        return this.jP.a(bArr3, true);
    }

    @Override // com.google.android.gms.internal.h
    public void a(int i, int i2, int i3) {
        if (this.jN != null) {
            this.jN.recycle();
        }
        this.jN = MotionEvent.obtain(0L, i3, 1, i * this.jO.density, i2 * this.jO.density, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0, 0);
    }

    protected void a(int i, long j) throws IOException {
        this.jQ.b(i, j);
    }

    protected void a(int i, String str) throws IOException {
        this.jQ.b(i, str);
    }

    @Override // com.google.android.gms.internal.h
    public void a(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            if (this.jN != null) {
                this.jN.recycle();
            }
            this.jN = MotionEvent.obtain(motionEvent);
        }
    }

    void a(String str, byte[] bArr) throws UnsupportedEncodingException {
        if (str.length() > 32) {
            str = str.substring(0, 32);
        }
        new km(str.getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET)).m(bArr);
    }

    protected abstract void b(Context context);

    protected abstract void c(Context context);
}
