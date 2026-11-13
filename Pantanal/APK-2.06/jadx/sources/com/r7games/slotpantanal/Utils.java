package com.r7games.slotpantanal;

import android.annotation.SuppressLint;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class Utils {
    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while (i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

    public byte[] long2bytearray(long l) {
        byte[] b = new byte[8];
        ByteBuffer buf = ByteBuffer.wrap(b);
        buf.putLong(l);
        return b;
    }

    public byte[] clong2bytearray(long l, char c) {
        byte[] b = new byte[9];
        ByteBuffer buf = ByteBuffer.wrap(b);
        buf.putLong(l);
        b[8] = (byte) c;
        return b;
    }

    public byte[] int2bytearray(int i) {
        byte[] b = new byte[4];
        ByteBuffer buf = ByteBuffer.wrap(b);
        buf.putInt(i);
        return b;
    }

    public long bytearray2long(byte[] b) {
        ByteBuffer buf = ByteBuffer.wrap(b);
        return buf.getLong();
    }

    public long cbytearray2long(byte[] b) {
        byte[] bn = new byte[8];
        for (int i = 0; i < bn.length; i++) {
            bn[i] = b[i];
        }
        ByteBuffer buf = ByteBuffer.wrap(bn);
        return buf.getLong();
    }

    public int bytearray2int(byte[] b) {
        ByteBuffer buf = ByteBuffer.wrap(b);
        return buf.getInt();
    }

    public String[] SendMessageArray(String paramString) {
        try {
            URL url = new URL("http://r7apps.online/play/" + paramString);
            HttpURLConnection mUrlConnection = (HttpURLConnection) url.openConnection();
            mUrlConnection.setDoInput(true);
            InputStream is = new BufferedInputStream(mUrlConnection.getInputStream());
            String str = readStream(is);
            return str.split("#");
        } catch (Exception localIOException) {
            localIOException.printStackTrace();
            return null;
        }
    }

    @SuppressLint({"DefaultLocale"})
    public String formatSeconds(int segundos) {
        int segundo = segundos % 60;
        int minutos = segundos / 60;
        int minuto = minutos % 60;
        String hms = String.format("%02dm:%02ds", Integer.valueOf(minuto), Integer.valueOf(segundo));
        System.out.println(hms);
        return hms;
    }

    public String md5(String paramString) {
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.reset();
            localMessageDigest.update(paramString.getBytes());
            byte[] arrayOfByte = localMessageDigest.digest();
            int i = arrayOfByte.length;
            StringBuilder localStringBuilder = new StringBuilder(i << 1);
            for (int j = 0; j < i; j++) {
                localStringBuilder.append(Character.forDigit((arrayOfByte[j] & 240) >> 4, 16));
                localStringBuilder.append(Character.forDigit(arrayOfByte[j] & 15, 16));
            }
            return localStringBuilder.toString();
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
            localNoSuchAlgorithmException.printStackTrace();
            return null;
        }
    }

    public HashMap<String, Long> sortByComparator(HashMap<String, Long> unsortMap) {
        List<Map.Entry> list = new LinkedList(unsortMap.entrySet());
        Collections.sort(list, new Comparator() { // from class: com.r7games.slotpantanal.Utils.1
            @Override // java.util.Comparator
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) o2).getValue()).compareTo(((Map.Entry) o1).getValue());
            }
        });
        HashMap sortedMap = new LinkedHashMap();
        for (Map.Entry entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public LinkedHashMap<String, Long> HashMapByValuesL(LinkedHashMap<String, Long> passedMap) {
        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Long> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);
        LinkedHashMap<String, Long> sortedMap = new LinkedHashMap<>();
        for (Object val : mapValues) {
            for (Object key : mapKeys) {
                String comp1 = passedMap.get(key).toString();
                String comp2 = val.toString();
                if (comp1.equals(comp2)) {
                    passedMap.remove(key);
                    mapKeys.remove(key);
                    sortedMap.put((String) key, (Long) val);
                    break;
                }
            }
        }
        return sortedMap;
    }

    public String idioma() {
        String idioma = Locale.getDefault().getLanguage();
        if (!idioma.equalsIgnoreCase("pt") && !idioma.equalsIgnoreCase("es")) {
            return "en";
        }
        return idioma;
    }
}
