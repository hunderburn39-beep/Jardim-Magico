package com.tapjoy;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.heyzap.http.AsyncHttpResponseHandler;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.xml.parsers.DocumentBuilderFactory;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes.dex */
public class TapjoyUtil {
    private static String a = null;
    private static HashMap b = new HashMap();

    public static String SHA1(String text) {
        return a("SHA-1", text);
    }

    public static String SHA256(String text) {
        return a("SHA-256", text);
    }

    private static String a(String str, String str2) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(str);
        messageDigest.update(str2.getBytes("iso-8859-1"), 0, str2.length());
        byte[] bArrDigest = messageDigest.digest();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bArrDigest.length; i++) {
            int i2 = (bArrDigest[i] >>> 4) & 15;
            int i3 = 0;
            while (true) {
                if (i2 >= 0 && i2 <= 9) {
                    stringBuffer.append((char) (i2 + 48));
                } else {
                    stringBuffer.append((char) ((i2 - 10) + 97));
                }
                int i4 = bArrDigest[i] & 15;
                int i5 = i3 + 1;
                if (i3 > 0) {
                    break;
                }
                i3 = i5;
                i2 = i4;
            }
        }
        return stringBuffer.toString();
    }

    public static Document buildDocument(String xml) {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET)));
        } catch (Exception e) {
            TapjoyLog.e("TapjoyUtil", "buildDocument exception: " + e.toString());
            return null;
        }
    }

    public static String getNodeTrimValue(NodeList nodeList) {
        Element element = (Element) nodeList.item(0);
        if (element == null) {
            return null;
        }
        NodeList childNodes = element.getChildNodes();
        int length = childNodes.getLength();
        String str = "";
        for (int i = 0; i < length; i++) {
            Node nodeItem = childNodes.item(i);
            if (nodeItem != null) {
                str = str + nodeItem.getNodeValue();
            }
        }
        if (str == null || str.equals("")) {
            return null;
        }
        return str.trim();
    }

    public static void deleteFileOrDirectory(File fileOrDirectory) {
        File[] fileArrListFiles;
        if (fileOrDirectory != null) {
            if (fileOrDirectory.isDirectory() && (fileArrListFiles = fileOrDirectory.listFiles()) != null && fileArrListFiles.length > 0) {
                for (File file : fileArrListFiles) {
                    deleteFileOrDirectory(file);
                }
            }
            TapjoyLog.d("TapjoyUtil", "****************************************");
            TapjoyLog.d("TapjoyUtil", "deleteFileOrDirectory: " + fileOrDirectory.getAbsolutePath());
            TapjoyLog.d("TapjoyUtil", "****************************************");
            fileOrDirectory.delete();
        }
    }

    public static long fileOrDirectorySize(File fileOrDirectory) {
        long jFileOrDirectorySize;
        long j = 0;
        for (File file : fileOrDirectory.listFiles()) {
            if (file.isFile()) {
                jFileOrDirectorySize = file.length();
            } else {
                jFileOrDirectorySize = fileOrDirectorySize(file);
            }
            j += jFileOrDirectorySize;
        }
        return j;
    }

    public static void writeFileToDevice(BufferedInputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[1024];
        while (true) {
            int i = inputStream.read(bArr);
            if (i != -1) {
                outputStream.write(bArr, 0, i);
            } else {
                return;
            }
        }
    }

    public static Bitmap createBitmapFromView(View v) {
        Bitmap bitmapCreateBitmap = null;
        if (v == null || v.getLayoutParams().width <= 0 || v.getLayoutParams().height <= 0) {
            return null;
        }
        try {
            bitmapCreateBitmap = Bitmap.createBitmap(v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
            v.draw(canvas);
            return bitmapCreateBitmap;
        } catch (Exception e) {
            TapjoyLog.d("TapjoyUtil", "error creating bitmap: " + e.toString());
            return bitmapCreateBitmap;
        }
    }

    public static View scaleDisplayAd(View adView, int targetWidth) {
        int i = adView.getLayoutParams().width;
        int i2 = adView.getLayoutParams().height;
        TapjoyLog.d("TapjoyUtil", "wxh: " + i + TMXConstants.TAG_OBJECT_ATTRIBUTE_X + i2);
        if (i > targetWidth) {
            int iIntValue = Double.valueOf(Double.valueOf(Double.valueOf(targetWidth).doubleValue() / Double.valueOf(i).doubleValue()).doubleValue() * 100.0d).intValue();
            ((WebView) adView).getSettings().setSupportZoom(true);
            ((WebView) adView).setPadding(0, 0, 0, 0);
            ((WebView) adView).setVerticalScrollBarEnabled(false);
            ((WebView) adView).setHorizontalScrollBarEnabled(false);
            ((WebView) adView).setInitialScale(iIntValue);
            adView.setLayoutParams(new ViewGroup.LayoutParams(targetWidth, (i2 * targetWidth) / i));
        }
        return adView;
    }

    public static void safePut(Map map, String key, String value, boolean encode) {
        if (key != null && key.length() > 0 && value != null && value.length() > 0) {
            if (encode) {
                map.put(Uri.encode(key), Uri.encode(value));
            } else {
                map.put(key, value);
            }
        }
    }

    public static String convertURLParams(Map map, boolean encode) {
        String str = "";
        Iterator it = map.entrySet().iterator();
        while (true) {
            String str2 = str;
            if (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                if (str2.length() > 0) {
                    str2 = str2 + "&";
                }
                if (encode) {
                    str = str2 + Uri.encode((String) entry.getKey()) + "=" + Uri.encode((String) entry.getValue());
                } else {
                    str = str2 + ((String) entry.getKey()) + "=" + ((String) entry.getValue());
                }
            } else {
                return str2;
            }
        }
    }

    public static Map convertURLParams(String source, boolean decode) {
        HashMap map = new HashMap();
        String strDecode = "";
        String strDecode2 = "";
        boolean z = false;
        for (int i = 0; i < source.length() && i != -1; i++) {
            char cCharAt = source.charAt(i);
            if (!z) {
                if (cCharAt == '=') {
                    strDecode2 = decode ? Uri.decode(strDecode) : strDecode;
                    strDecode = "";
                    z = true;
                } else {
                    strDecode = strDecode + cCharAt;
                }
            } else if (z) {
                if (cCharAt == '&') {
                    String strDecode3 = decode ? Uri.decode(strDecode) : strDecode;
                    strDecode = "";
                    map.put(strDecode2, strDecode3);
                    z = false;
                } else {
                    strDecode = strDecode + cCharAt;
                }
            }
        }
        if (z && strDecode.length() > 0) {
            if (decode) {
                strDecode = Uri.decode(strDecode);
            }
            map.put(strDecode2, strDecode);
        }
        return map;
    }

    public static String copyTextFromJarIntoString(String source) {
        return copyTextFromJarIntoString(source, null);
    }

    /* JADX WARN: Code duplicated, block: B:54:0x00b8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r2v18, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v23 */
    /* JADX WARN: Type inference failed for: r2v24 */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v7 */
    public static Bitmap loadBitmapFromJar(String str, Context context) throws Throwable {
        Bitmap bitmapDecodeStream;
        ?? r1 = 0;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        byte[] bArr = (byte[]) getResource(str);
        if (bArr != null) {
            BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            bitmapDecodeStream = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        } else {
            bitmapDecodeStream = null;
        }
        if (bitmapDecodeStream == null) {
            ?? inputStream = "com/tapjoy/res/";
            String str2 = "com/tapjoy/res/" + str;
            try {
                try {
                    URL resource = TapjoyUtil.class.getClassLoader().getResource(str2);
                    try {
                        if (resource == null) {
                            AssetManager assets = context.getAssets();
                            BitmapFactory.decodeStream(assets.open(str2), null, options);
                            inputStream = assets.open(str2);
                        } else {
                            String file = resource.getFile();
                            if (file.startsWith("jar:")) {
                                file = file.substring(4);
                            }
                            if (file.startsWith("file:")) {
                                file = file.substring(5);
                            }
                            int iIndexOf = file.indexOf("!");
                            if (iIndexOf > 0) {
                                file = file.substring(0, iIndexOf);
                            }
                            JarFile jarFile = new JarFile(file);
                            JarEntry jarEntry = jarFile.getJarEntry(str2);
                            BitmapFactory.decodeStream(jarFile.getInputStream(jarEntry), null, options);
                            inputStream = jarFile.getInputStream(jarEntry);
                        }
                        bitmapDecodeStream = BitmapFactory.decodeStream(inputStream);
                        if (inputStream != 0) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                            }
                        }
                    } catch (Exception e2) {
                        e = e2;
                        e.printStackTrace();
                        if (inputStream != 0) {
                            try {
                                inputStream.close();
                            } catch (IOException e3) {
                            }
                        }
                        return null;
                    }
                } catch (Throwable th) {
                    th = th;
                    r1 = "com/tapjoy/res/";
                    if (r1 != 0) {
                        try {
                            r1.close();
                        } catch (IOException e4) {
                        }
                    }
                    throw th;
                }
            } catch (Exception e5) {
                e = e5;
                inputStream = 0;
            } catch (Throwable th2) {
                th = th2;
                if (r1 != 0) {
                    r1.close();
                }
                throw th;
            }
        }
        float deviceScreenDensityScale = TapjoyConnectCore.getDeviceScreenDensityScale();
        return bitmapDecodeStream != null ? Bitmap.createScaledBitmap(bitmapDecodeStream, (int) (options.outWidth * deviceScreenDensityScale), (int) (deviceScreenDensityScale * options.outHeight), true) : bitmapDecodeStream;
    }

    /* JADX WARN: Code duplicated, block: B:48:0x00a0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r0v7, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    public static String copyTextFromJarIntoString(String str, Context context) {
        Throwable th;
        InputStream inputStream;
        String string = 0;
        string = 0;
        string = 0;
        byte[] bArr = new byte[1024];
        StringBuffer stringBuffer = new StringBuffer();
        URL resource = TapjoyUtil.class.getClassLoader().getResource(str);
        try {
            try {
                if (context == null || resource != null) {
                    String file = resource.getFile();
                    if (file.startsWith("jar:")) {
                        file = file.substring(4);
                    }
                    if (file.startsWith("file:")) {
                        file = file.substring(5);
                    }
                    int iIndexOf = file.indexOf("!");
                    if (iIndexOf > 0) {
                        file = file.substring(0, iIndexOf);
                    }
                    JarFile jarFile = new JarFile(file);
                    inputStream = jarFile.getInputStream(jarFile.getJarEntry(str));
                } else {
                    inputStream = context.getAssets().open(str);
                }
                while (true) {
                    try {
                        int i = inputStream.read(bArr);
                        if (i <= 0) {
                            break;
                        }
                        stringBuffer.append(new String(bArr).substring(0, i));
                    } catch (Exception e) {
                        e = e;
                        TapjoyLog.d("TapjoyUtil", "file exception: " + e.toString());
                        e.printStackTrace();
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (Exception e2) {
                            }
                        }
                    }
                }
                string = stringBuffer.toString();
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e3) {
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (0 != 0) {
                    try {
                        string.close();
                    } catch (Exception e4) {
                    }
                }
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            inputStream = null;
        } catch (Throwable th3) {
            th = th3;
            if (0 != 0) {
                string.close();
            }
            throw th;
        }
        return string;
    }

    public static void setResource(String key, Object value) {
        b.put(key, value);
    }

    public static Object getResource(String key) {
        return b.get(key);
    }

    public static String getRedirectDomain(String hostURL) {
        if (hostURL == null) {
            return "";
        }
        return hostURL.substring(hostURL.indexOf("//") + 2, hostURL.lastIndexOf("/"));
    }

    public static String determineMimeType(String url) {
        String strSubstring = "";
        if (url.endsWith(".")) {
            url = url.substring(0, url.length() - 1);
        }
        if (url.lastIndexOf(46) != -1) {
            strSubstring = url.substring(url.lastIndexOf(46) + 1);
        }
        if (strSubstring.equals("css")) {
            return "text/css";
        }
        if (strSubstring.equals("js")) {
            return "text/javascript";
        }
        if (strSubstring.equals(TJAdUnitConstants.String.HTML)) {
            return "text/html";
        }
        return "application/octet-stream";
    }

    public static Map jsonToStringMap(JSONObject json) {
        HashMap map = new HashMap();
        if (json != JSONObject.NULL) {
            return toStringMap(json);
        }
        return map;
    }

    public static Map toStringMap(JSONObject object) {
        HashMap map = new HashMap();
        Iterator<String> itKeys = object.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            map.put(next, object.get(next).toString());
        }
        return map;
    }

    public static void runOnMainThread(Runnable task) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            task.run();
        } else {
            new Handler(Looper.getMainLooper()).post(task);
        }
    }
}
