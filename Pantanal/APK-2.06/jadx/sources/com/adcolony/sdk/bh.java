package com.adcolony.sdk;

import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import com.heyzap.http.AsyncHttpResponseHandler;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyConstants;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.GZIPOutputStream;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
abstract class bh {
    static final String a = "digitalPackages";
    static final String b = "digitalRedemptions";
    static final String c = "customRewards";
    static final String d = "googleIap";
    static final String e = "amazonIap";
    static final String f = "productId";
    static final String g = "consumable";
    static final String h = "invisible";
    static final String i = "transactionId";
    static final int j = 0;
    static final int k = 1;
    static final String l = "transactions";
    static Handler m = new Handler(Looper.getMainLooper());

    protected abstract String a();

    protected abstract void a(Map<String, Object> map);

    protected abstract void b();

    protected abstract void b(Map<String, Object> map);

    bh() {
    }

    /* JADX WARN: Code duplicated, block: B:101:0x02a3 A[Catch: all -> 0x005e, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:102:0x02a7 A[Catch: all -> 0x005e, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:104:0x02af A[Catch: all -> 0x005e, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:105:0x02b3 A[Catch: all -> 0x005e, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:107:0x02bb A[Catch: all -> 0x005e, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:108:0x02c3 A[Catch: all -> 0x005e, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:110:0x02cb A[Catch: all -> 0x005e, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:111:0x02d3 A[Catch: all -> 0x005e, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:113:0x02db A[Catch: all -> 0x005e, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:114:0x02e0 A[Catch: all -> 0x005e, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:115:0x0305 A[Catch: all -> 0x005e, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:80:0x0241 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:81:0x0243 A[Catch: all -> 0x005e, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:83:0x024b A[Catch: all -> 0x005e, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:86:0x0262 A[Catch: all -> 0x005e, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:87:0x026b A[Catch: all -> 0x005e, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:89:0x0273 A[Catch: all -> 0x005e, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:90:0x0277 A[Catch: all -> 0x005e, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:92:0x027f A[Catch: all -> 0x005e, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:93:0x0283 A[Catch: all -> 0x005e, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:95:0x028b A[Catch: all -> 0x005e, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:96:0x028f A[Catch: all -> 0x005e, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:98:0x0297 A[Catch: all -> 0x005e, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:99:0x029b A[Catch: all -> 0x005e, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:8:0x0011, B:10:0x0015, B:16:0x0023, B:22:0x002f, B:23:0x0033, B:25:0x0039, B:34:0x0069, B:36:0x0071, B:38:0x007f, B:39:0x0081, B:41:0x0089, B:43:0x0099, B:45:0x00a1, B:47:0x00a9, B:49:0x00ad, B:51:0x00b5, B:73:0x01e5, B:75:0x01ed, B:77:0x0222, B:79:0x022c, B:81:0x0243, B:83:0x024b, B:84:0x0253, B:86:0x0262, B:87:0x026b, B:89:0x0273, B:90:0x0277, B:92:0x027f, B:93:0x0283, B:95:0x028b, B:96:0x028f, B:98:0x0297, B:99:0x029b, B:101:0x02a3, B:102:0x02a7, B:104:0x02af, B:105:0x02b3, B:107:0x02bb, B:108:0x02c3, B:110:0x02cb, B:111:0x02d3, B:113:0x02db, B:114:0x02e0, B:115:0x0305, B:53:0x00bd, B:55:0x00c1, B:57:0x00cb, B:58:0x010f, B:60:0x0117, B:62:0x013a, B:63:0x015b, B:65:0x0163, B:66:0x0178, B:71:0x01cb, B:68:0x01a1, B:70:0x01a9, B:67:0x019c, B:15:0x001e, B:7:0x000d), top: B:117:0x0001 }] */
    public synchronized void a(String str, int i2, String str2, Map<String, String> map, Map<String, Object> map2, String str3, bk bkVar) {
        String str4;
        bn.a aVar = bn.a.YVOLVER_ERROR_NONE;
        if (str == null || str.isEmpty()) {
            aVar = bn.a.YVOLVER_ERROR_NULL_RESPONSE;
        } else if (i2 == 407) {
            aVar = bn.a.YVOLVER_ERROR_INVALID_SSL_DOMAIN;
        }
        if (aVar != bn.a.YVOLVER_ERROR_NONE) {
            bkVar.a(aVar);
        } else {
            if (aVar == bn.a.YVOLVER_ERROR_NONE && (i2 < 200 || i2 >= 300)) {
                aVar = bn.a.YVOLVER_ERROR_WEB_ERROR;
            }
            Map<String, Object> mapB = ck.b(str);
            if (mapB == null) {
                bv.aN().a(bn.a.YVOLVER_ERROR_WEB_ERROR, "Error Occurred in handleResponse: JSON is null, this is the response=" + str, true, AdColonyPubServices.ServiceAvailability.SERVICE_UNAVAILABLE);
                bkVar.a(bn.a.YVOLVER_ERROR_WEB_ERROR);
            } else {
                if (i2 >= 400 && i2 < 500 && mapB.containsKey(bn.an) && ((Integer) mapB.get(bn.an)).intValue() == 0) {
                    aVar = bn.a.YVOLVER_ERROR_NONE;
                }
                if (mapB.containsKey(bn.am)) {
                    String str5 = (String) mapB.get(bn.am);
                    if (str5.equals("bannedApp") || str5.equals("disabledApp") || str5.equals("serverMaintenance")) {
                        aVar = bn.a.YVOLVER_ERROR_NONE;
                    }
                }
                if ((str2 == null || (!str2.equals(d) && !str2.equals(e))) && aVar != bn.a.YVOLVER_ERROR_NONE) {
                    String str6 = "Error occurred in handleResponse: IAP";
                    if (mapB.containsKey(TJAdUnitConstants.String.VIDEO_ERROR)) {
                        String str7 = (String) mapB.get(TJAdUnitConstants.String.VIDEO_ERROR);
                        str6 = "Error occurred in handleResponse: IAP: " + str7;
                        bw.b(a(), "localErr=" + str7 + ", errMsg=" + str6, true);
                    }
                    if (mapB.containsKey("error_id")) {
                        String str8 = (String) mapB.get("error_id");
                        String str9 = str6 + ": contains error_id";
                        if (str8.equals("invalidConsumerKey")) {
                            str9 = str9 + ": InvalidConsumerKey";
                            aVar = bn.a.YVOLVER_ERROR_INVALIDCONSUMERKEY;
                            bv.aN().a(aVar, "Error Occurred in handleResponse: InvalidConsumerKey", false, AdColonyPubServices.ServiceAvailability.SERVICE_UNAVAILABLE);
                        } else if (str8.equals("bannedApp")) {
                            str9 = str9 + ": APP BANNED";
                            aVar = bn.a.YVOLVER_ERROR_APP_BANNED;
                            bv.aN().a(aVar, "Error Occurred in handleResponse: BANNED APP", false, AdColonyPubServices.ServiceAvailability.SERVICE_BANNED);
                        }
                        if (str8.equals("disabledApp")) {
                            str4 = str9 + ": APP DISABLED";
                            aVar = bn.a.YVOLVER_ERROR_APP_DISABLED;
                        } else {
                            str4 = str9 + ": " + str8;
                        }
                        bw.b(a(), "Error id=" + str8 + ", message=" + str4);
                    }
                    bkVar.a(aVar);
                } else if (str2 != null && !str2.equals("config")) {
                    bw.b(a(), "requireSig: " + bv.aN().aw().w(), true);
                    if (bv.aN().aw().w() && !bv.aN().a(mapB, str3, str2)) {
                        bv.aN().a(bn.a.YVOLVER_ERROR_MALFORMED_RESULTS, "Error Occurred in handleResponse: malformed results; signature not validated", true, AdColonyPubServices.ServiceAvailability.SERVICE_UNAVAILABLE);
                        bkVar.a(bn.a.YVOLVER_ERROR_MALFORMED_RESULTS);
                    } else if (str2 != null) {
                        if (str2.equals("config")) {
                            a(mapB, map, map2, str3, bkVar);
                        } else if (str2.equals("init")) {
                            a(mapB, map2, str3, bkVar);
                        } else if (str2.equals("digiStart")) {
                            a(mapB, map, map2, bkVar);
                        } else if (str2.equals("digiFinish")) {
                            a(mapB, map, bkVar);
                        } else if (str2.equals("customReward")) {
                            c(mapB, map, map2, bkVar);
                        } else if (str2.equals("logs")) {
                            d(mapB, map, map2, bkVar);
                        } else if (str2.equals("stats")) {
                            e(mapB, map, map2, bkVar);
                        } else if (str2.equals(d)) {
                            a(mapB, map, map2, aVar, bkVar);
                        } else if (str2.equals(e)) {
                            b(mapB, map, map2, aVar, bkVar);
                        } else if (str2.equals("serverReward")) {
                            b(mapB, map, map2, bkVar);
                        } else {
                            bv.aN().a("Invalid method attempted: " + str2, bn.b.YVOLVER_SEVERITY_ERROR, false, (Map<String, Object>) null);
                            bkVar.a(bn.a.YVOLVER_ERROR_API_SUBMISSION);
                        }
                        bv.aN().b(mapB, str3, str2);
                        if (str2.equals("init")) {
                            bv.aN().J();
                        }
                    } else {
                        bv.aN().a("handleResponseMethodNull", bn.b.YVOLVER_SEVERITY_ERROR, false, (Map<String, Object>) null);
                        bkVar.a(bn.a.YVOLVER_ERROR_API_SUBMISSION);
                    }
                } else if (str2 != null) {
                    if (str2.equals("config")) {
                        a(mapB, map, map2, str3, bkVar);
                    } else if (str2.equals("init")) {
                        a(mapB, map2, str3, bkVar);
                    } else if (str2.equals("digiStart")) {
                        a(mapB, map, map2, bkVar);
                    } else if (str2.equals("digiFinish")) {
                        a(mapB, map, bkVar);
                    } else if (str2.equals("customReward")) {
                        c(mapB, map, map2, bkVar);
                    } else if (str2.equals("logs")) {
                        d(mapB, map, map2, bkVar);
                    } else if (str2.equals("stats")) {
                        e(mapB, map, map2, bkVar);
                    } else if (str2.equals(d)) {
                        a(mapB, map, map2, aVar, bkVar);
                    } else if (str2.equals(e)) {
                        b(mapB, map, map2, aVar, bkVar);
                    } else if (str2.equals("serverReward")) {
                        b(mapB, map, map2, bkVar);
                    } else {
                        bv.aN().a("Invalid method attempted: " + str2, bn.b.YVOLVER_SEVERITY_ERROR, false, (Map<String, Object>) null);
                        bkVar.a(bn.a.YVOLVER_ERROR_API_SUBMISSION);
                    }
                    bv.aN().b(mapB, str3, str2);
                    if (str2.equals("init")) {
                        bv.aN().J();
                    }
                } else {
                    bv.aN().a("handleResponseMethodNull", bn.b.YVOLVER_SEVERITY_ERROR, false, (Map<String, Object>) null);
                    bkVar.a(bn.a.YVOLVER_ERROR_API_SUBMISSION);
                }
            }
        }
    }

    public synchronized void a(String str, Map<String, String> map, String str2, boolean z, Map<String, Object> map2, int i2, bk bkVar) {
        if (bv.aN().x()) {
            bw.b(a(), "callEndpoint", true);
            a(bv.aN().r(), str, map, str2, z, map2, i2, bkVar);
        }
    }

    public synchronized void a(String str, String str2, Map<String, String> map, String str3, boolean z, Map<String, Object> map2, int i2, bk bkVar) {
        String strA;
        byte[] bytes;
        boolean z2;
        HttpURLConnection httpURLConnection;
        int responseCode;
        String responseMessage;
        boolean z3;
        String string;
        bn.a aVar;
        String str4;
        BufferedInputStream bufferedInputStream;
        bn.a aVar2 = bn.a.YVOLVER_ERROR_NONE;
        String str5 = "";
        int i3 = 0;
        int iZ = bv.aN().aw().z();
        byte[] bArr = null;
        bw.b(a(), "callEndpointWithApiKey: " + (str2 == null ? "NULL" : str2) + ", timeout: " + iZ, true);
        if (str2 == null) {
            bw.b(a(), "URL null network call.", true);
            bkVar.a(bn.a.YVOLVER_ERROR_API_SUBMISSION);
        } else {
            if (z) {
                CookieHandler.setDefault(new CookieManager());
            }
            if (i2 == 0) {
                bw.b(a(), "GET was called", true);
                strA = ck.a("GET", str, str2, new ArrayList());
            } else if (i2 != 1) {
                strA = "";
            } else {
                bw.b(a(), "POST was called", true);
                ArrayList<Map.Entry> arrayList = new ArrayList();
                TreeMap treeMap = new TreeMap();
                String string2 = null;
                if (str3 != null && !str3.equals("config")) {
                    string2 = Integer.toString(bv.aN().aw().m());
                }
                if (str3 != null && !str3.equals("config") && string2 != null && str3 != null && !str3.equals("config") && string2 != null) {
                    treeMap.put("config_version", string2);
                }
                treeMap.put("device_id", bv.aN().au().j());
                if (map != null) {
                    treeMap.putAll(map);
                }
                Iterator it = treeMap.entrySet().iterator();
                while (it.hasNext()) {
                    arrayList.add((Map.Entry) it.next());
                }
                StringBuilder sb = new StringBuilder();
                boolean z4 = true;
                for (Map.Entry entry : arrayList) {
                    if (z4) {
                        z2 = false;
                    } else {
                        sb.append("&");
                        z2 = z4;
                    }
                    try {
                        sb.append(URLEncoder.encode((String) entry.getKey(), AsyncHttpResponseHandler.DEFAULT_CHARSET));
                        sb.append("=");
                        sb.append(URLEncoder.encode((String) entry.getValue(), AsyncHttpResponseHandler.DEFAULT_CHARSET));
                    } catch (UnsupportedEncodingException e2) {
                        bw.a(a(), "Caught Exception->" + e2.getMessage(), e2);
                    }
                    z4 = z2;
                }
                try {
                    bytes = sb.toString().getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET);
                } catch (UnsupportedEncodingException e3) {
                    bw.a(a(), "Caught Exception->" + e3.getMessage(), e3);
                    bytes = null;
                }
                bArr = bytes;
                strA = ck.a("POST", str, str2, arrayList);
            }
            bw.b(a(), "urlConnection initialized", true);
            HttpURLConnection httpURLConnection2 = null;
            try {
                URL url = new URL(str2);
                String strE = bv.aN().e(str);
                if (str2.substring(0, 5).equals("https")) {
                    httpURLConnection = (HttpsURLConnection) url.openConnection();
                } else {
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                }
                try {
                    httpURLConnection.setReadTimeout(iZ);
                    httpURLConnection.setConnectTimeout(iZ);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setRequestProperty("User-Agent", strE);
                    httpURLConnection.setRequestProperty("Content-Encoding", "gzip");
                    httpURLConnection.setChunkedStreamingMode(0);
                    if (i2 != 1) {
                        httpURLConnection.setRequestProperty("Authorization", strA);
                    } else if (i2 == 1) {
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setRequestProperty("Authorization", strA);
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(bufferedOutputStream);
                        gZIPOutputStream.write(bArr);
                        gZIPOutputStream.flush();
                        gZIPOutputStream.close();
                        bufferedOutputStream.close();
                    }
                    responseCode = httpURLConnection.getResponseCode();
                    try {
                        responseMessage = httpURLConnection.getResponseMessage();
                        try {
                            if (responseCode >= 400) {
                                bufferedInputStream = new BufferedInputStream(httpURLConnection.getErrorStream());
                            } else {
                                bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                            }
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
                            StringBuffer stringBuffer = new StringBuffer();
                            while (true) {
                                String line = bufferedReader.readLine();
                                if (line == null) {
                                    break;
                                } else {
                                    stringBuffer.append(line);
                                }
                            }
                            bufferedInputStream.close();
                            i3 = responseCode;
                            string = stringBuffer.toString();
                            aVar = aVar2;
                            str4 = "A fatal error occurred when calling Endpoint";
                            z3 = false;
                        } catch (SocketTimeoutException e4) {
                            aVar = bn.a.YVOLVER_ERROR_TIMEOUT;
                            str4 = "A fatal error occurred when calling Endpoint";
                            z3 = false;
                            int i4 = responseCode;
                            string = responseMessage;
                            i3 = i4;
                        } catch (SSLHandshakeException e5) {
                            str5 = responseMessage;
                            i3 = responseCode;
                            httpURLConnection2 = httpURLConnection;
                            e = e5;
                            z3 = true;
                            HttpURLConnection httpURLConnection3 = httpURLConnection2;
                            string = str5;
                            aVar = bn.a.YVOLVER_ERROR_SSL;
                            str4 = "A SSLHandshake error occured when calling enpoint->" + e.getMessage();
                            httpURLConnection = httpURLConnection3;
                        } catch (SSLPeerUnverifiedException e6) {
                            str5 = responseMessage;
                            i3 = responseCode;
                            httpURLConnection2 = httpURLConnection;
                            e = e6;
                            z3 = true;
                            HttpURLConnection httpURLConnection4 = httpURLConnection2;
                            string = str5;
                            aVar = bn.a.YVOLVER_ERROR_SSL;
                            str4 = "A SSLPeerUnverified error occurred while calling endpoint->" + e.getMessage();
                            httpURLConnection = httpURLConnection4;
                        } catch (Exception e7) {
                            str5 = responseMessage;
                            i3 = responseCode;
                            httpURLConnection2 = httpURLConnection;
                            e = e7;
                            z3 = true;
                            HttpURLConnection httpURLConnection5 = httpURLConnection2;
                            string = str5;
                            aVar = bn.a.YVOLVER_ERROR_API_SUBMISSION;
                            str4 = "An exception occurred while calling enpoint ->" + e.getMessage();
                            httpURLConnection = httpURLConnection5;
                        }
                    } catch (SocketTimeoutException e8) {
                        responseMessage = "";
                    } catch (SSLHandshakeException e9) {
                        i3 = responseCode;
                        httpURLConnection2 = httpURLConnection;
                        e = e9;
                    } catch (SSLPeerUnverifiedException e10) {
                        i3 = responseCode;
                        httpURLConnection2 = httpURLConnection;
                        e = e10;
                    } catch (Exception e11) {
                        i3 = responseCode;
                        httpURLConnection2 = httpURLConnection;
                        e = e11;
                    }
                } catch (SocketTimeoutException e12) {
                    responseCode = 0;
                    responseMessage = "";
                } catch (SSLHandshakeException e13) {
                    httpURLConnection2 = httpURLConnection;
                    e = e13;
                } catch (SSLPeerUnverifiedException e14) {
                    httpURLConnection2 = httpURLConnection;
                    e = e14;
                } catch (Exception e15) {
                    httpURLConnection2 = httpURLConnection;
                    e = e15;
                }
            } catch (SocketTimeoutException e16) {
                httpURLConnection = null;
                responseCode = 0;
                responseMessage = "";
            } catch (SSLHandshakeException e17) {
                e = e17;
            } catch (SSLPeerUnverifiedException e18) {
                e = e18;
            } catch (Exception e19) {
                e = e19;
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (aVar == bn.a.YVOLVER_ERROR_NONE) {
                a(string, i3, str3, map, map2, str, bkVar);
            } else {
                String str6 = "An error occurred when calling endpoint: " + aVar;
                if (z3) {
                    bv.aN().a(aVar, str4, true, AdColonyPubServices.ServiceAvailability.SERVICE_UNAVAILABLE);
                } else {
                    bw.b(a(), str6);
                }
                bkVar.a(aVar);
            }
        }
    }

    public synchronized void a(Map<String, Object> map, Map<String, Object> map2, String str, bk bkVar) {
        List<Map<String, Object>> list;
        if (map.containsKey("assets") && (list = (List) map.get("assets")) != null) {
            bv.aN().av().a(list);
        }
        a(map);
        bkVar.a(bn.a.YVOLVER_ERROR_NONE);
    }

    public synchronized void a(Map<String, Object> map, Map<String, String> map2, Map<String, Object> map3, bk bkVar) {
        String str;
        boolean z = false;
        int i2 = 1;
        synchronized (this) {
            String str2 = map2.get("product_id");
            if (map.containsKey(g)) {
                int i3 = Integer.parseInt((String) map.get(g));
                i2 = i3;
                z = i3 == 1;
            }
            if (!map.containsKey("transaction_id")) {
                str = null;
            } else {
                str = (String) map.get("transaction_id");
            }
            Map<String, Object> mapA = ck.a(b, true);
            HashMap map4 = new HashMap();
            map4.put("status", Integer.valueOf(ce.c));
            map4.put(g, Integer.valueOf(i2));
            map4.put(i, str);
            mapA.put(str, map4);
            ck.a(b, mapA);
            bv.aN().aB().a("");
            HashMap map5 = new HashMap();
            map5.put(f, str2);
            map5.put(g, Boolean.valueOf(z));
            map5.put(i, str);
            bkVar.a(bn.a.YVOLVER_ERROR_NONE);
        }
    }

    public synchronized void b(Map<String, Object> map, Map<String, String> map2, Map<String, Object> map3, bk bkVar) {
    }

    public synchronized void a(Map<String, Object> map, Map<String, String> map2, bk bkVar) {
        boolean z;
        if (map.containsKey(l)) {
            ArrayList arrayList = (ArrayList) map.get(l);
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                int iIntValue = ((Integer) ((Map) arrayList.get(i2)).get(bn.an)).intValue();
                String str = (String) ((Map) arrayList.get(i2)).get("transaction_id");
                boolean zBooleanValue = map.containsKey(TapjoyConstants.TJC_RETRY) ? ((Boolean) map.get(TapjoyConstants.TJC_RETRY)).booleanValue() : true;
                if (iIntValue == 0) {
                    z = true;
                } else {
                    z = (iIntValue == 100 || !zBooleanValue) ? false : false;
                }
                arrayList2.add(new Pair(str, Boolean.valueOf(z)));
            }
            if (cl.b().e()) {
                if (cl.b().c() != null) {
                    cl.b().c().e();
                } else {
                    bv.aN().a(new RuntimeException("Tried to call packageComplete when overlayIsVisible, but OverlayActivity is null"), "YvolverBaseNetworking.handleRedemptionFinish");
                }
            } else if (bv.aN().aC().n().booleanValue()) {
                bv.aN().aC().q();
            }
            bv.aN().aB().a(arrayList2);
        }
        bkVar.a(bn.a.YVOLVER_ERROR_NONE);
    }

    public synchronized void c(Map<String, Object> map, Map<String, String> map2, Map<String, Object> map3, bk bkVar) {
        String str = (String) map3.get("rewardKey");
        if (map.containsKey(TapjoyConstants.TJC_RETRY) && !((Boolean) map.get(TapjoyConstants.TJC_RETRY)).booleanValue()) {
            bw.b(a(), "retry false code was executed", true);
            if (ck.a(c, false) != null) {
                Map<String, Object> mapA = ck.a(c, false);
                mapA.remove(str);
                ck.a(c, mapA);
            }
        }
        bkVar.a(bn.a.YVOLVER_ERROR_NONE);
    }

    public synchronized void d(Map<String, Object> map, Map<String, String> map2, Map<String, Object> map3, bk bkVar) {
        int iIntValue = ((Integer) map3.get("lastQueuedMessageId")).intValue();
        int iIntValue2 = ((Integer) map3.get("latestMessageLogged")).intValue();
        if (map.containsKey("status") && map.get("status").equals(0)) {
            bv.aN().aF().b(iIntValue);
            bw.b(a(), "lastMessage: " + bv.aN().aF().a, true);
            if (iIntValue < iIntValue2) {
                bv.aN().aA().a(true);
            }
        }
        bv.aN().aA().b(false);
        bkVar.a(bn.a.YVOLVER_ERROR_NONE);
    }

    public synchronized void a(ArrayList<Object> arrayList) {
        bw.b(a(), "parseStats() called, " + arrayList.size() + " items.", true);
        ArrayList<HashMap<String, Object>> arrayList2 = new ArrayList<>();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            HashMap map = (HashMap) arrayList.get(i2);
            HashMap<String, Object> map2 = new HashMap<>();
            for (Map.Entry entry : map.entrySet()) {
                map2.put((String) entry.getKey(), entry.getValue());
            }
            arrayList2.add(map2);
        }
        bv.aN().ay().a(arrayList2);
    }

    public synchronized void e(Map<String, Object> map, Map<String, String> map2, Map<String, Object> map3, bk bkVar) {
        Collection<Map<String, Object>> collection = (Collection) map3.get("success");
        Collection<Map<String, Object>> collection2 = (Collection) map3.get("failed");
        if (map.containsKey("stats")) {
            ArrayList<Object> arrayList = (ArrayList) map.get("stats");
            bw.b(a(), "handleStats stats:" + arrayList, true);
            a(arrayList);
        } else {
            bw.b(a(), "handleStats, no stats in results", true);
        }
        if (map.containsKey(TapjoyConstants.TJC_RETRY) && !((Boolean) map.get(TapjoyConstants.TJC_RETRY)).booleanValue()) {
            collection2.addAll(collection);
            bv.aN().ay().b(collection2);
            bv.aN().ay().a(collection);
        }
        bv.aN().ay().a(collection2);
        bv.aN().ay().a(0L);
        bkVar.a(bn.a.YVOLVER_ERROR_NONE);
    }

    public synchronized void a(Map<String, Object> map, Map<String, String> map2, Map<String, Object> map3, String str, bk bkVar) {
        bn.a aVar;
        String str2;
        int i2 = 0;
        synchronized (this) {
            bn.a aVar2 = bn.a.YVOLVER_ERROR_NONE;
            while (true) {
                int i3 = i2 + 1;
                if (i2 == 0) {
                    if (map.containsKey(bn.am) && (str2 = (String) map.get(bn.am)) != null && (str2.equals("bannedApp") || str2.equals("serverMaintenance"))) {
                        bv.aN().a(bn.a(str2));
                        if (str2.equals("bannedApp")) {
                            aVar2 = bn.a.YVOLVER_ERROR_APP_BANNED;
                            bv.aN().a(aVar2, "This app is banned.", false, AdColonyPubServices.ServiceAvailability.SERVICE_BANNED);
                        }
                        if (str2.equals("serverMaintenance")) {
                            aVar = bn.a.YVOLVER_ERROR_SERVER_MAINTENANCE;
                            break;
                        }
                    } else {
                        bv.aN().aw().a(map);
                        b(map);
                        bv.aN().m(bv.aN().aw().j());
                        if (map.containsKey("old_keys")) {
                            bv.aN().e((ArrayList<String>) map.get("old_keys"));
                        }
                        b();
                        if (bv.aN().aw().w() && !bv.aN().a(map, str, "config")) {
                            aVar = bn.a.YVOLVER_ERROR_MALFORMED_RESULTS;
                            bv.aN().a(aVar, "Malformed results detected from invalid JSON response", true, AdColonyPubServices.ServiceAvailability.SERVICE_UNAVAILABLE);
                            break;
                        }
                        i2 = i3;
                    }
                }
                aVar = aVar2;
                break;
            }
            bkVar.a(aVar);
        }
    }

    public synchronized void a(Map<String, Object> map, Map<String, String> map2, Map<String, Object> map3, bn.a aVar, bk bkVar) {
        int iIntValue = 0;
        synchronized (this) {
            bw.b(a(), "handleGoogleIap was called", true);
            String str = "";
            boolean zBooleanValue = map.containsKey(TapjoyConstants.TJC_RETRY) ? ((Boolean) map.get(TapjoyConstants.TJC_RETRY)).booleanValue() : true;
            if (map.containsKey("iap_product_id")) {
                str = (String) map.get("iap_product_id");
            }
            if (map.containsKey("currency_bonus")) {
                iIntValue = ((Integer) map.get("currency_bonus")).intValue();
                HashMap map4 = new HashMap();
                map4.put(TMXConstants.TAG_PROPERTY_ATTRIBUTE_VALUE, Integer.valueOf(iIntValue));
                map4.put("iap_transaction_id", map2.get("iap_transaction_id"));
                map4.put("iap_product_id", str);
                bv.aN().a("iapCurrencyBonusAvailable", bn.b.YVOLVER_SEVERITY_DEBUG, false, (Map<String, Object>) map4);
            }
            String str2 = map2.get("iap_transaction_id");
            if (aVar == bn.a.YVOLVER_ERROR_NONE) {
                if (!zBooleanValue) {
                    bv.aN().aB().f(map2.get("iap_transaction_id"));
                }
                bw.b(a(), "IAP reward success", true);
                bv.aN().a(iIntValue, str, str2, false);
                if (bv.aN().m()) {
                    bv.aN().aE().a(str, iIntValue, str2);
                    HashMap map5 = new HashMap();
                    map5.put(TMXConstants.TAG_PROPERTY_ATTRIBUTE_VALUE, Integer.valueOf(iIntValue));
                    map5.put("iap_transaction_id", map2.get("iap_transaction_id"));
                    map5.put("iap_product_id", str);
                    bv.aN().a("iapCurrencyBonusGranted", bn.b.YVOLVER_SEVERITY_DEBUG, false, (Map<String, Object>) map5);
                }
            } else {
                String str3 = "IAP Reward Failure: ";
                String str4 = "";
                if (map.containsKey("error_id")) {
                    String str5 = (String) map.get("error_id");
                    aVar = bn.a.YVOLVER_ERROR_API_SUBMISSION;
                    str3 = "IAP Reward Failure: errorId=" + str5;
                    if (str5.equals("invalidPayloadSignature")) {
                        aVar = bn.a.YVOLVER_ERROR_INVALID_IAP_PAYLOAD_SIGNATURE;
                        str3 = str3 + "Please check your Android secret key";
                    }
                    if (map.containsKey(TJAdUnitConstants.String.VIDEO_ERROR)) {
                        String str6 = (String) map.get(TJAdUnitConstants.String.VIDEO_ERROR);
                        str3 = str3 + str6;
                        str4 = str6;
                    }
                }
                bs bsVar = new bs(aVar, "IAP failure " + str4);
                if (bv.aN().m()) {
                    bv.aN().aE().a(map2.get("iap_product_id"), bsVar.getMessage());
                }
                bw.b(a(), str3);
                bv.aN().a(iIntValue, str, str2, aVar.ordinal(), str4);
            }
            bkVar.a(aVar);
        }
    }

    public synchronized void b(Map<String, Object> map, Map<String, String> map2, Map<String, Object> map3, bn.a aVar, bk bkVar) {
        synchronized (this) {
            boolean zBooleanValue = map.containsKey(TapjoyConstants.TJC_RETRY) ? ((Boolean) map.get(TapjoyConstants.TJC_RETRY)).booleanValue() : true;
            if (map.containsKey("currency_bonus")) {
            }
            String str = map2.get("iap_transaction_id");
            String str2 = map2.get("iap_product_id");
            if (aVar == bn.a.YVOLVER_ERROR_NONE) {
                if (!zBooleanValue) {
                    bv.aN().aB().g(str);
                }
                bw.b(a(), "IAP Reward Success", true);
                if (bv.aN().m()) {
                    bv.aN().aE().a(str2, 0, str);
                }
            } else {
                String str3 = "IAP Reward Failure: ";
                String str4 = "";
                if (map.containsKey("error_id")) {
                    String str5 = (String) map.get("error_id");
                    aVar = bn.a.YVOLVER_ERROR_API_SUBMISSION;
                    str3 = "IAP Reward Failure: errorId=" + str5;
                    if (str5.equals("invalidPayloadSignature")) {
                        aVar = bn.a.YVOLVER_ERROR_INVALID_IAP_PAYLOAD_SIGNATURE;
                        str3 = str3 + "Please check your Android secret key";
                    }
                    if (map.containsKey(TJAdUnitConstants.String.VIDEO_ERROR)) {
                        String str6 = (String) map.get(TJAdUnitConstants.String.VIDEO_ERROR);
                        str3 = str3 + str6;
                        str4 = str6;
                    }
                }
                bs bsVar = new bs(aVar, "IAP failure " + str4);
                if (bv.aN().m()) {
                    bv.aN().aE().a(map2.get("iap_product_id"), bsVar.getMessage());
                }
                bw.b(a(), str3);
                bv.aN().a(0, str2, str, aVar.ordinal(), str4);
            }
            bkVar.a(aVar);
        }
    }
}
