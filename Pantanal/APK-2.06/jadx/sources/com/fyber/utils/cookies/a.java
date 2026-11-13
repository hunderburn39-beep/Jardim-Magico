package com.fyber.utils.cookies;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: PersistentHttpCookieStore.java */
/* JADX INFO: loaded from: classes.dex */
public final class a implements CookieStore {
    private final ConcurrentHashMap<URI, List<HttpCookie>> a = new ConcurrentHashMap<>();
    private final SharedPreferences b;

    public a(Context context) {
        this.b = context.getSharedPreferences("FyberCookiePrefsFile", 0);
        String string = this.b.getString("names", null);
        if (string != null) {
            for (String str : TextUtils.split(string, ",")) {
                String string2 = this.b.getString("cookie_" + str, null);
                if (StringUtils.notNullNorEmpty(string2)) {
                    this.a.put(URI.create(str), a(string2));
                }
            }
        }
    }

    @Override // java.net.CookieStore
    public final void add(URI uri, HttpCookie httpCookie) {
        if (httpCookie != null && !httpCookie.hasExpired()) {
            URI uriA = a(uri);
            List<HttpCookie> arrayList = this.a.get(uriA);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.a.put(uriA, arrayList);
            } else {
                arrayList.remove(httpCookie);
            }
            arrayList.add(httpCookie);
            this.b.edit().putString("names", TextUtils.join(",", this.a.keySet())).putString("cookie_" + uriA, a(arrayList)).commit();
        }
    }

    private List<HttpCookie> a(String str) {
        LinkedList linkedList = new LinkedList();
        for (String str2 : TextUtils.split(str, ",")) {
            HttpCookie httpCookieB = b(str2);
            if (httpCookieB != null && !httpCookieB.hasExpired()) {
                linkedList.add(httpCookieB);
            }
        }
        return linkedList;
    }

    private static String a(List<HttpCookie> list) {
        StringBuilder sb = new StringBuilder();
        for (HttpCookie httpCookie : list) {
            if (httpCookie != null && !httpCookie.hasExpired()) {
                ParcelableHttpCookie parcelableHttpCookie = new ParcelableHttpCookie(httpCookie);
                Parcel parcelObtain = Parcel.obtain();
                parcelableHttpCookie.writeToParcel(parcelObtain, 0);
                sb.append(a(parcelObtain.marshall())).append(",");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override // java.net.CookieStore
    public final List<HttpCookie> get(URI uri) {
        ArrayList arrayList = new ArrayList();
        List<HttpCookie> list = this.a.get(uri);
        if (list != null) {
            Iterator<HttpCookie> it = list.iterator();
            while (it.hasNext()) {
                HttpCookie next = it.next();
                if (next != null) {
                    if (next.hasExpired()) {
                        it.remove();
                    } else {
                        arrayList.add(next);
                    }
                }
            }
        }
        for (Map.Entry<URI, List<HttpCookie>> entry : this.a.entrySet()) {
            if (!uri.equals(entry.getKey())) {
                Iterator<HttpCookie> it2 = entry.getValue().iterator();
                while (it2.hasNext()) {
                    HttpCookie next2 = it2.next();
                    if (next2 != null && HttpCookie.domainMatches(next2.getDomain(), uri.getHost())) {
                        if (next2.hasExpired()) {
                            it2.remove();
                        } else if (!arrayList.contains(next2)) {
                            arrayList.add(next2);
                        }
                    }
                }
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    @Override // java.net.CookieStore
    public final List<HttpCookie> getCookies() {
        ArrayList arrayList = new ArrayList();
        Iterator<List<HttpCookie>> it = this.a.values().iterator();
        while (it.hasNext()) {
            Iterator<HttpCookie> it2 = it.next().iterator();
            while (it2.hasNext()) {
                HttpCookie next = it2.next();
                if (next.hasExpired()) {
                    it2.remove();
                } else if (!arrayList.contains(next)) {
                    arrayList.add(next);
                }
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    @Override // java.net.CookieStore
    public final List<URI> getURIs() {
        ArrayList arrayList = new ArrayList(this.a.keySet());
        arrayList.remove((Object) null);
        return Collections.unmodifiableList(arrayList);
    }

    @Override // java.net.CookieStore
    public final boolean remove(URI uri, HttpCookie httpCookie) {
        URI uriA = a(uri);
        List<HttpCookie> list = this.a.get(uriA);
        if (list != null) {
            boolean zRemove = list.remove(httpCookie);
            if (zRemove) {
                SharedPreferences.Editor editorEdit = this.b.edit();
                if (list.isEmpty()) {
                    editorEdit.remove("cookie_" + uriA);
                } else {
                    editorEdit.putString("cookie_" + uriA, a(list));
                }
                editorEdit.commit();
            }
            return zRemove;
        }
        return false;
    }

    @Override // java.net.CookieStore
    public final boolean removeAll() {
        SharedPreferences.Editor editorEdit = this.b.edit();
        Iterator<URI> it = this.a.keySet().iterator();
        while (it.hasNext()) {
            editorEdit.remove("cookie_" + it.next());
        }
        editorEdit.remove("names");
        editorEdit.commit();
        boolean z = !this.a.isEmpty();
        this.a.clear();
        return z;
    }

    private static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            int i = b & 255;
            if (i < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(i));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

    private static URI a(URI uri) {
        if (uri == null) {
            return null;
        }
        try {
            return new URI(uri.getScheme(), uri.getHost(), null, null);
        } catch (URISyntaxException e) {
            return uri;
        }
    }

    /* JADX INFO: renamed from: com.fyber.utils.cookies.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: PersistentHttpCookieStore.java */
    private class C0027a extends ObjectInputStream {
        public C0027a(InputStream inputStream) throws IOException {
            super(inputStream);
        }

        @Override // java.io.ObjectInputStream
        protected final ObjectStreamClass readClassDescriptor() throws ClassNotFoundException, IOException {
            super.readClassDescriptor();
            return ObjectStreamClass.lookup(b.class);
        }
    }

    private HttpCookie b(String str) {
        int length = str.length();
        byte[] bArr = new byte[(length / 2) + 1];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        Parcelable.Creator<ParcelableHttpCookie> creator = ParcelableHttpCookie.CREATOR;
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.unmarshall(bArr, 0, bArr.length);
        parcelObtain.setDataPosition(0);
        HttpCookie httpCookieA = creator.createFromParcel(parcelObtain).a();
        if (httpCookieA == null) {
            try {
                return ((b) new C0027a(new ByteArrayInputStream(bArr)).readObject()).a();
            } catch (IOException | ClassNotFoundException e) {
                FyberLogger.d("PersistentHttpCookieStore", "Exception in decodeCookie - " + e.getMessage());
            }
        }
        return httpCookieA;
    }
}
