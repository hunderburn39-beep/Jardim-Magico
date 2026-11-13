package com.adcolony.sdk;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.webkit.WebView;
import com.adcolony.adcolonysdk.BuildConfig;
import com.heyzap.http.AsyncHttpResponseHandler;
import com.tapjoy.TapjoyConstants;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.Mac;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class ck {
    static final int a = 1;
    static boolean b = false;

    ck() {
    }

    private static String m() {
        return bv.class.getSimpleName();
    }

    static String a(Map<String, Object> map) {
        return new JSONObject(map).toString();
    }

    static String a(List<Object> list) {
        System.out.println(list.toString());
        return new JSONArray((Collection) list).toString();
    }

    static String b(Map<String, Object> map) {
        return new JSONObject(new TreeMap(map)).toString();
    }

    static List<Object> a(String str) {
        try {
            JSONArray jSONArray = new JSONArray(str);
            if (str == JSONObject.NULL) {
                return null;
            }
            return a(jSONArray);
        } catch (JSONException e) {
            bw.b(m(), "bad json: " + str + ", ", true);
            bw.a(m(), "Caught Exception->" + e.getMessage(), e);
            return null;
        }
    }

    static Map<String, Object> b(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (str == JSONObject.NULL) {
                return null;
            }
            return a(jSONObject);
        } catch (JSONException e) {
            bw.b(m(), "bad jason: " + str, true);
            bw.a(m(), "Caught Exception->" + e.getMessage(), e);
            return null;
        }
    }

    static Map<String, Object> a(JSONObject jSONObject) {
        HashMap map = new HashMap();
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            try {
                Object objA = jSONObject.get(next);
                if (objA instanceof JSONArray) {
                    objA = a((JSONArray) objA);
                } else if (objA instanceof JSONObject) {
                    objA = a((JSONObject) objA);
                }
                if (objA == null) {
                    bw.b(m(), "bad object: key: " + next + "object: " + jSONObject, true);
                    return null;
                }
                map.put(next, objA);
            } catch (JSONException e) {
                bw.a(m(), "bad object: " + jSONObject + ". Exception->" + e.getMessage(), e);
                return null;
            }
        }
        return map;
    }

    static List<Object> a(JSONArray jSONArray) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= jSONArray.length()) {
                return arrayList;
            }
            try {
                Object objA = jSONArray.get(i2);
                if (objA instanceof JSONArray) {
                    objA = a((JSONArray) objA);
                } else if (objA instanceof JSONObject) {
                    objA = a((JSONObject) objA);
                }
                if (objA == null) {
                    bw.b(m(), "bad array: " + jSONArray, true);
                    return null;
                }
                arrayList.add(objA);
                i = i2 + 1;
            } catch (JSONException e) {
                bw.a(m(), "bad array:" + jSONArray + ", Exception->" + e.getMessage(), e);
                return null;
            }
        }
    }

    static int a(char c) {
        return (Math.min(Math.max(0, 'a' - c), 1) * (32 - Math.max(0, 'a' - c))) + (Math.max(0, c - 'a') * Math.min(1, Math.max(0, c - '`')));
    }

    static String c(String str) {
        return new StringBuffer(str).reverse().toString();
    }

    static String d(String str) {
        try {
            return new String(Base64.decode(str, 0));
        } catch (Exception e) {
            return null;
        }
    }

    static String e(String str) {
        return d(str);
    }

    static String f(String str) {
        return g(str);
    }

    static String g(String str) {
        try {
            return new String(Base64.decode(str, 0), AsyncHttpResponseHandler.DEFAULT_CHARSET);
        } catch (Exception e) {
            return null;
        }
    }

    static String a(String str, String str2) {
        if (str2 != null) {
            try {
                byte[] bytes = str.getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET);
                SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "HmacSHA1");
                Mac mac = Mac.getInstance("HmacSHA1");
                mac.init(secretKeySpec);
                byte[] bArrDoFinal = mac.doFinal(bytes);
                StringBuffer stringBuffer = new StringBuffer();
                for (byte b2 : bArrDoFinal) {
                    String hexString = Integer.toHexString(b2 & 255);
                    if (hexString.length() == 1) {
                        stringBuffer.append('0');
                    }
                    stringBuffer.append(hexString);
                }
                return stringBuffer.toString();
            } catch (Exception e) {
                bw.a(m(), "Caught Exception->" + e.getMessage(), e);
                return "";
            }
        }
        bw.b(m(), "Empty secret key signing HMAC", true);
        return "";
    }

    static float a() {
        return bv.aN().l().getResources().getDisplayMetrics().density;
    }

    static int b() {
        return bv.aN().l().getResources().getDisplayMetrics().heightPixels;
    }

    static int c() {
        return bv.aN().l().getResources().getDisplayMetrics().widthPixels;
    }

    static int d() {
        return bv.aN().l().getResources().getDisplayMetrics().densityDpi;
    }

    static int a(int i) {
        return (int) (i * a());
    }

    static final String h(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            byte[] bArrDigest = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b2 : bArrDigest) {
                String hexString = Integer.toHexString(b2 & 255);
                while (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            bw.a(m(), "Caught Exception->" + e.getMessage(), e);
            return "";
        }
    }

    static String a(InputStream inputStream) {
        char[] charArray = "0123456789abcdef".toCharArray();
        try {
            byte[] bArr = new byte[4096];
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            while (true) {
                int i = inputStream.read(bArr);
                if (i == -1) {
                    break;
                }
                messageDigest.update(bArr, 0, i);
            }
            byte[] bArrDigest = messageDigest.digest();
            StringBuilder sb = new StringBuilder(32);
            for (byte b2 : bArrDigest) {
                sb.append(charArray[(b2 >> 4) & 15]);
                sb.append(charArray[b2 & 15]);
            }
            return sb.toString();
        } catch (Exception e) {
            bw.a(m(), "Caught Exception->" + e.getMessage(), e);
            return "";
        }
    }

    static String b(int i) {
        return bn.bh.indexOfKey(i) >= 0 ? bn.bh.get(i) : "Unmatched error code!";
    }

    static synchronized void a(Map<String, Object> map, String str, Context context) {
        bw.b(m(), "saveMap:" + str, true);
        if (str == null) {
            str = e();
            bw.b(m(), "new fileName:" + str, true);
        }
        FileOutputStream fileOutputStreamOpenFileOutput = null;
        try {
            fileOutputStreamOpenFileOutput = context.openFileOutput(str, 0);
        } catch (FileNotFoundException e) {
            bw.a(m(), "File not found. Exception->" + e.getMessage(), e);
        }
        if (bv.aN().g() != null) {
            try {
                SecretKey secretKeyJ = j(bv.aN().g());
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(1, secretKeyJ);
                SealedObject sealedObject = new SealedObject((Serializable) map, cipher);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new CipherOutputStream(fileOutputStreamOpenFileOutput, cipher));
                objectOutputStream.writeUTF("yvlr");
                objectOutputStream.writeInt(1);
                objectOutputStream.writeObject(sealedObject);
                objectOutputStream.flush();
                objectOutputStream.close();
            } catch (Exception e2) {
                bw.a(m(), "Failed to save file. Caught Exception->" + e2.getMessage(), e2);
            }
        }
    }

    static String a(Context context) {
        boolean z;
        File[] fileArrListFiles = context.getApplicationContext().getFilesDir().listFiles();
        String name = null;
        if (fileArrListFiles != null) {
            for (int i = 0; i < fileArrListFiles.length; i++) {
                if (fileArrListFiles[i].getName().endsWith(".ybin")) {
                    bw.b(m(), "file: " + fileArrListFiles[i].getName(), true);
                    if (bv.aN().g() != null) {
                        try {
                            SecretKey secretKeyJ = j(bv.aN().g());
                            Cipher cipher = Cipher.getInstance("AES");
                            cipher.init(2, secretKeyJ);
                            ObjectInputStream objectInputStream = new ObjectInputStream(new CipherInputStream(context.openFileInput(fileArrListFiles[i].getName()), cipher));
                            String utf = objectInputStream.readUTF();
                            objectInputStream.readInt();
                            objectInputStream.close();
                            if (utf.equals("yvlr")) {
                                name = fileArrListFiles[i].getName();
                            }
                        } catch (Exception e) {
                            bw.b(m(), "failed to read file: " + fileArrListFiles[i].getName(), true);
                            bw.a(m(), "Caught Exception->" + e.getMessage(), e);
                            if (bv.aN().h() != null && bv.aN().h().size() > 0) {
                                Iterator<String> it = bv.aN().h().iterator();
                                String name2 = name;
                                while (true) {
                                    if (!it.hasNext()) {
                                        name = name2;
                                        break;
                                    }
                                    try {
                                        SecretKey secretKeyJ2 = j(bv.aN().c(it.next()));
                                        Cipher cipher2 = Cipher.getInstance("AES");
                                        cipher2.init(2, secretKeyJ2);
                                        ObjectInputStream objectInputStream2 = new ObjectInputStream(new CipherInputStream(context.openFileInput(name2), cipher2));
                                        String utf2 = objectInputStream2.readUTF();
                                        objectInputStream2.readInt();
                                        objectInputStream2.close();
                                        name2 = utf2.equals("yvlr") ? fileArrListFiles[i].getName() : name2;
                                        z = false;
                                    } catch (Exception e2) {
                                        z = true;
                                    }
                                    if (!z) {
                                        name = name2;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            bw.b(m(), "no files found", true);
        }
        return name;
    }

    static String e() {
        return UUID.randomUUID().toString() + ".ybin";
    }

    /* JADX WARN: Code duplicated, block: B:52:0x00e8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:54:0x009e A[SYNTHETIC] */
    static synchronized Map<String, Object> a(String str, Context context) {
        Map<String, Object> map;
        Map<String, Object> map2;
        boolean z;
        Map<String, Object> map3 = null;
        if (bv.aN().g() == null) {
            map = null;
        } else {
            try {
                SecretKey secretKeyJ = j(bv.aN().g());
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(2, secretKeyJ);
                ObjectInputStream objectInputStream = new ObjectInputStream(new CipherInputStream(context.openFileInput(str), cipher));
                objectInputStream.readUTF();
                objectInputStream.readInt();
                map = (Map) ((SealedObject) objectInputStream.readObject()).getObject(cipher);
                try {
                    objectInputStream.close();
                } catch (Exception e) {
                    map3 = map;
                    e = e;
                    bw.b(m(), "incorrect key", true);
                    bw.a(m(), "Caught Exception->" + e.getMessage(), e);
                    if (bv.aN().h() != null && bv.aN().h().size() > 0) {
                        Iterator<String> it = bv.aN().h().iterator();
                        while (it.hasNext()) {
                            try {
                                SecretKey secretKeyJ2 = j(bv.aN().c(it.next()));
                                Cipher cipher2 = Cipher.getInstance("AES");
                                cipher2.init(2, secretKeyJ2);
                                ObjectInputStream objectInputStream2 = new ObjectInputStream(new CipherInputStream(context.openFileInput(str), cipher2));
                                objectInputStream2.readUTF();
                                objectInputStream2.readInt();
                                map2 = (Map) ((SealedObject) objectInputStream2.readObject()).getObject(cipher2);
                                try {
                                    objectInputStream2.close();
                                    map3 = map2;
                                    z = false;
                                } catch (Exception e2) {
                                    map3 = map2;
                                    z = true;
                                    if (!z) {
                                        a(map3, str, context);
                                    }
                                }
                            } catch (Exception e3) {
                                map2 = map3;
                            }
                            if (!z) {
                                a(map3, str, context);
                            }
                        }
                    }
                    map = map3;
                }
            } catch (Exception e4) {
                e = e4;
            }
        }
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }

    static synchronized Map<String, Object> a(String str, boolean z) {
        Map<String, Object> map;
        map = null;
        String strA = a(bv.aN().l());
        if (strA != null) {
            Map<String, Object> mapA = a(strA, bv.aN().l());
            if (mapA != null && mapA.containsKey(str)) {
                map = (Map) mapA.get(str);
            } else if (z) {
                map = new HashMap<>();
                if (mapA == null) {
                    new HashMap();
                }
            }
        } else if (z) {
            bw.b(m(), "fileStore is null. creating new entry", true);
            new HashMap();
            map = new HashMap<>();
        }
        return map;
    }

    static synchronized void a(String str, Map<String, Object> map) {
        Map map2;
        String strA = a(bv.aN().l());
        if (strA != null) {
            map2 = a(strA, bv.aN().l());
        } else {
            bw.b(m(), "filestore is null, saving new entry", true);
            map2 = new HashMap();
        }
        map2.put(str, map);
        a((Map<String, Object>) map2, strA, bv.aN().l());
    }

    static void i(String str) {
        Map<String, Object> mapA;
        String strA = a(bv.aN().l());
        if (strA != null && (mapA = a(strA, bv.aN().l())) != null && mapA.containsKey(str)) {
            mapA.remove(str);
            a(mapA, strA, bv.aN().l());
        }
    }

    static SecretKey j(String str) throws Exception {
        byte[] bArr = new byte[32];
        byte[] bytes = new byte[0];
        try {
            bytes = str.getBytes("ASCII");
        } catch (UnsupportedEncodingException e) {
            bw.a(m(), "Caught Exception->" + e.getMessage(), e);
        }
        if (bytes.length > 32) {
            throw new bs(bn.a.YVOLVER_ERROR_IO_FAILURE, "The number of bytes in the key is > 32.");
        }
        System.arraycopy(bytes, 0, bArr, 0, bytes.length);
        return new SecretKeySpec(bArr, "AES");
    }

    static String b(String str, Map<String, Object> map) {
        String str2 = "";
        Iterator it = new TreeSet(map.keySet()).iterator();
        while (true) {
            String str3 = str2;
            if (it.hasNext()) {
                String str4 = (String) it.next();
                str2 = (str3 + str4) + map.get(str4);
            } else {
                return a(str3, str);
            }
        }
    }

    static String a(String str, Map<String, Object> map, String[] strArr) {
        HashMap map2 = new HashMap();
        map2.putAll(map);
        return a(a(map2, strArr), str);
    }

    static String a(Map<String, Object> map, String[] strArr) {
        if (strArr != null) {
            for (String str : strArr) {
                if (map.containsKey(str)) {
                    map.remove(str);
                }
            }
        }
        Map<String, Object> mapC = c(map);
        TreeSet<String> treeSet = new TreeSet(mapC.keySet());
        StringBuilder sb = new StringBuilder();
        for (String str2 : treeSet) {
            sb.append(str2);
            sb.append(mapC.get(str2));
        }
        return sb.toString();
    }

    static Map<String, Object> c(Map<String, Object> map) {
        HashMap map2 = new HashMap();
        while (!map.isEmpty()) {
            HashMap map3 = new HashMap();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof Map) {
                    Map map4 = (Map) value;
                    if (!map4.isEmpty()) {
                        for (Map.Entry entry2 : map4.entrySet()) {
                            map3.put(key + "." + ((String) entry2.getKey()), entry2.getValue());
                        }
                    } else {
                        map2.put(key, "");
                    }
                } else if (value instanceof ArrayList) {
                    ArrayList arrayList = (ArrayList) value;
                    if (!arrayList.isEmpty()) {
                        for (int i = 0; i < arrayList.size(); i++) {
                            map3.put(key + "." + i, arrayList.get(i));
                        }
                    } else {
                        map2.put(key, "");
                    }
                } else if (value != null) {
                    if (value.equals(false)) {
                        map2.put(key, 0);
                    } else if (value.equals(true)) {
                        map2.put(key, 1);
                    } else if (!value.equals(null)) {
                        map2.put(key, value);
                    } else {
                        map2.put(key, "");
                    }
                } else {
                    map2.put(key, "");
                }
            }
            map = map3;
        }
        return map2;
    }

    static Map<String, String> k(String str) {
        HashMap map = new HashMap();
        for (String str2 : str.split(Pattern.quote("&"))) {
            String[] strArrSplit = str2.split(Pattern.quote("="), 2);
            if (strArrSplit.length == 2) {
                map.put(Uri.decode(strArrSplit[0]), Uri.decode(strArrSplit[1]));
            } else {
                map.put(Uri.decode(strArrSplit[0]), "");
            }
        }
        return map;
    }

    static String d(Map<String, String> map) {
        if (map.size() == 0) {
            return "";
        }
        String str = "?";
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            int i2 = i + 1;
            str = (i > 0 ? str + '&' : str) + Uri.encode(entry.getKey()) + '=' + Uri.encode(entry.getValue());
            i = i2;
        }
        return str;
    }

    static double a(Object obj) {
        return ((Number) obj).doubleValue() / 1000000.0d;
    }

    static synchronized void a(Map<String, Object> map, String str) {
        FileOutputStream fileOutputStreamOpenFileOutput;
        try {
            fileOutputStreamOpenFileOutput = bv.aN().l().openFileOutput(str, 0);
        } catch (FileNotFoundException e) {
            bw.a(m(), "FILE " + str + " NOT FOUND. Exception->" + e.getMessage(), e);
            fileOutputStreamOpenFileOutput = null;
        }
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStreamOpenFileOutput);
            objectOutputStream.writeObject(map);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e2) {
            bw.a(m(), "Caught Exception->" + e2.getMessage(), e2);
        }
    }

    static synchronized Map<String, Object> l(String str) {
        Map<String, Object> map;
        try {
            if (bv.aN().l().getFileStreamPath(str).exists()) {
                ObjectInputStream objectInputStream = new ObjectInputStream(bv.aN().l().openFileInput(str));
                map = (Map) objectInputStream.readObject();
                try {
                    objectInputStream.close();
                } catch (Exception e) {
                    bw.b(m(), "failed to load " + str, true);
                }
            } else {
                map = new HashMap<>();
            }
        } catch (Exception e2) {
            map = null;
        }
        return map;
    }

    static synchronized List<Map<String, Object>> m(String str) {
        ArrayList arrayList;
        try {
            if (bv.aN().l().getFileStreamPath(str).exists()) {
                ObjectInputStream objectInputStream = new ObjectInputStream(bv.aN().l().openFileInput(str));
                ArrayList arrayList2 = new ArrayList();
                while (true) {
                    try {
                        try {
                            arrayList2.add((Map) objectInputStream.readObject());
                        } catch (Exception e) {
                            arrayList = arrayList2;
                            bw.b(m(), "failed to load " + str, true);
                            return arrayList;
                        }
                    } catch (EOFException e2) {
                        objectInputStream.close();
                        arrayList = arrayList2;
                        return arrayList;
                    }
                }
            } else {
                arrayList = new ArrayList();
            }
        } catch (Exception e3) {
            arrayList = null;
        }
        return arrayList;
    }

    static synchronized void a(List<Map<String, Object>> list, String str) {
        FileOutputStream fileOutputStreamOpenFileOutput;
        try {
            fileOutputStreamOpenFileOutput = bv.aN().l().openFileOutput(str, 0);
        } catch (FileNotFoundException e) {
            bw.a(m(), "FILE " + str + " NOT FOUND. Exception->" + e.getMessage(), e);
            fileOutputStreamOpenFileOutput = null;
        }
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStreamOpenFileOutput);
            Iterator<Map<String, Object>> it = list.iterator();
            while (it.hasNext()) {
                objectOutputStream.writeObject(it.next());
            }
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e2) {
            bw.a(m(), "Caught Exception->" + e2.getMessage(), e2);
        }
    }

    static JSONObject e(Map<String, Object> map) {
        JSONObject jSONObject = new JSONObject();
        try {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof Map) {
                    Map map2 = (Map) entry.getValue();
                    JSONObject jSONObject2 = new JSONObject();
                    for (Map.Entry entry2 : map2.entrySet()) {
                        if (entry2.getValue() instanceof Map) {
                            jSONObject2.put((String) entry2.getKey(), e((Map<String, Object>) entry2.getValue()));
                        } else {
                            jSONObject2.put((String) entry2.getKey(), entry2.getValue());
                        }
                    }
                    jSONObject.put(entry.getKey(), jSONObject2);
                } else {
                    jSONObject.put(entry.getKey(), entry.getValue());
                }
            }
        } catch (JSONException e) {
            bw.a(m(), "error converting to json. Exception->" + e.getMessage(), e);
        }
        return jSONObject;
    }

    static JSONArray f(Map<String, Object> map) {
        JSONArray jSONArray = new JSONArray();
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            jSONArray.put(e((Map<String, Object>) it.next().getValue()));
        }
        return jSONArray;
    }

    static String a(String str, String str2, String str3, ArrayList<Map.Entry<String, String>> arrayList) {
        boolean z;
        boolean z2 = true;
        String strReplaceAll = UUID.randomUUID().toString().replaceAll("-", "");
        String string = Long.toString(System.currentTimeMillis() / 1000);
        TreeMap treeMap = new TreeMap();
        try {
            try {
                if (str.equals("POST")) {
                }
                treeMap.put("oauth_consumer_key", str2);
                treeMap.put("oauth_nonce", strReplaceAll);
                treeMap.put(TapjoyConstants.TJC_NOTIFICATION_OAUTH_SIGNATURE_METHOD, "HMAC-SHA1");
                treeMap.put("oauth_timestamp", string);
                treeMap.put("oauth_version", BuildConfig.VERSION_NAME);
                Iterator it = treeMap.entrySet().iterator();
                while (it.hasNext()) {
                    arrayList.add((Map.Entry) it.next());
                }
                Collections.sort(arrayList, new Comparator<Map.Entry<String, String>>() { // from class: com.adcolony.sdk.ck.1
                    @Override // java.util.Comparator
                    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                    public int compare(Map.Entry<String, String> entry, Map.Entry<String, String> entry2) {
                        return entry.getKey().compareToIgnoreCase(entry2.getKey());
                    }
                });
                StringBuilder sb = new StringBuilder();
                boolean z3 = true;
                for (Map.Entry<String, String> entry : arrayList) {
                    if (z3) {
                        z = false;
                    } else {
                        sb.append("&");
                        z = z3;
                    }
                    try {
                        sb.append(URLEncoder.encode(entry.getKey(), AsyncHttpResponseHandler.DEFAULT_CHARSET));
                        sb.append("=");
                        sb.append(URLEncoder.encode(entry.getValue(), AsyncHttpResponseHandler.DEFAULT_CHARSET));
                    } catch (UnsupportedEncodingException e) {
                        bw.a(m(), "Caught Exception->" + e.getMessage(), e);
                    }
                    z3 = z;
                }
                byte[] bytes = String.format("%s&%s&%s", str, URLEncoder.encode(str3, AsyncHttpResponseHandler.DEFAULT_CHARSET), URLEncoder.encode(sb.toString(), AsyncHttpResponseHandler.DEFAULT_CHARSET).replaceAll("%2B", "%2520")).getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET);
                SecretKeySpec secretKeySpec = new SecretKeySpec((bv.aN().g() + "&").getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET), "HmacSHA1");
                Mac mac = Mac.getInstance("HmacSHA1");
                mac.init(secretKeySpec);
                String strEncodeToString = Base64.encodeToString(mac.doFinal(bytes), 2);
                ArrayList arrayList2 = new ArrayList();
                treeMap.put(TapjoyConstants.TJC_NOTIFICATION_OAUTH_SIGNATURE, strEncodeToString);
                Iterator it2 = treeMap.entrySet().iterator();
                while (it2.hasNext()) {
                    arrayList2.add((Map.Entry) it2.next());
                }
                StringBuilder sb2 = new StringBuilder();
                Iterator it3 = arrayList2.iterator();
                while (true) {
                    boolean z4 = z2;
                    if (it3.hasNext()) {
                        Map.Entry entry2 = (Map.Entry) it3.next();
                        if (z4) {
                            z2 = false;
                        } else {
                            sb2.append(",");
                            z2 = z4;
                        }
                        try {
                            sb2.append(URLEncoder.encode((String) entry2.getKey(), AsyncHttpResponseHandler.DEFAULT_CHARSET));
                            sb2.append("=\"");
                            sb2.append(URLEncoder.encode((String) entry2.getValue(), AsyncHttpResponseHandler.DEFAULT_CHARSET)).append("\"");
                        } catch (UnsupportedEncodingException e2) {
                            bw.a(m(), "Caught Exception->" + e2.getMessage(), e2);
                        }
                    } else {
                        return "OAuth " + sb2.toString();
                    }
                }
            } catch (UnsupportedEncodingException e3) {
                bw.a(m(), "Caught Exception->" + e3.getMessage(), e3);
                return null;
            }
        } catch (InvalidKeyException e4) {
            bw.a(m(), "Caught Exception->" + e4.getMessage(), e4);
            return null;
        } catch (NoSuchAlgorithmException e5) {
            bw.a(m(), "Caught Exception->" + e5.getMessage(), e5);
            return null;
        } catch (Exception e6) {
            bw.a(m(), "Caught Exception->" + e6.getMessage(), e6);
            return null;
        }
    }

    static boolean f() {
        if (Build.VERSION.SDK_INT >= 14) {
            return true;
        }
        bw.b(m(), "OS is too old. Not supported.");
        return false;
    }

    static long g() {
        return System.currentTimeMillis();
    }

    static void a(long j) {
        bv.aN().a(j);
    }

    static long h() {
        return g() + bv.aN().aH();
    }

    static String i() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(Calendar.getInstance().getTime());
    }

    static String j() {
        g();
        long jH = h();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(new Date(jH));
    }

    static String b(long j) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(new Date(j));
    }

    static void k() {
        AlertDialog.Builder builder = new AlertDialog.Builder(bv.aN().l());
        builder.setTitle("Permission Needed");
        builder.setMessage("Yvolver needs permission to show pop up notifications over the application you are using. Please enable this permission on the next screen.");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() { // from class: com.adcolony.sdk.ck.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                ((Activity) bv.aN().l()).startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + bv.aN().l().getPackageName())), 2211);
            }
        });
        builder.create().show();
    }

    public static <T> Iterable<T> a(Iterable<T> iterable) {
        return iterable == null ? Collections.emptyList() : iterable;
    }

    public static boolean l() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) bv.aN().l().getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void a(WebView webView, String str) {
        if (!l()) {
            webView.getSettings().setCacheMode(1);
        } else {
            webView.getSettings().setCacheMode(-1);
        }
        webView.loadUrl(str);
    }
}
