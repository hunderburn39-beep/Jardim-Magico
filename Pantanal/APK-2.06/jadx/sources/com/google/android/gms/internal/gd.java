package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class gd implements SafeParcelable {
    public static final ge CREATOR = new ge();
    private final HashMap<String, HashMap<String, ga.a<?, ?>>> Ei;
    private final ArrayList<a> Ej;
    private final String Ek;
    private final int xH;

    public static class a implements SafeParcelable {
        public static final gf CREATOR = new gf();
        final ArrayList<b> El;
        final String className;
        final int versionCode;

        a(int i, String str, ArrayList<b> arrayList) {
            this.versionCode = i;
            this.className = str;
            this.El = arrayList;
        }

        a(String str, HashMap<String, ga.a<?, ?>> map) {
            this.versionCode = 1;
            this.className = str;
            this.El = b(map);
        }

        private static ArrayList<b> b(HashMap<String, ga.a<?, ?>> map) {
            if (map == null) {
                return null;
            }
            ArrayList<b> arrayList = new ArrayList<>();
            for (String str : map.keySet()) {
                arrayList.add(new b(str, map.get(str)));
            }
            return arrayList;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            gf gfVar = CREATOR;
            return 0;
        }

        HashMap<String, ga.a<?, ?>> fp() {
            HashMap<String, ga.a<?, ?>> map = new HashMap<>();
            int size = this.El.size();
            for (int i = 0; i < size; i++) {
                b bVar = this.El.get(i);
                map.put(bVar.eM, bVar.Em);
            }
            return map;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            gf gfVar = CREATOR;
            gf.a(this, out, flags);
        }
    }

    public static class b implements SafeParcelable {
        public static final gc CREATOR = new gc();
        final ga.a<?, ?> Em;
        final String eM;
        final int versionCode;

        b(int i, String str, ga.a<?, ?> aVar) {
            this.versionCode = i;
            this.eM = str;
            this.Em = aVar;
        }

        b(String str, ga.a<?, ?> aVar) {
            this.versionCode = 1;
            this.eM = str;
            this.Em = aVar;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            gc gcVar = CREATOR;
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            gc gcVar = CREATOR;
            gc.a(this, out, flags);
        }
    }

    gd(int i, ArrayList<a> arrayList, String str) {
        this.xH = i;
        this.Ej = null;
        this.Ei = b(arrayList);
        this.Ek = (String) fq.f(str);
        fl();
    }

    public gd(Class<? extends ga> cls) {
        this.xH = 1;
        this.Ej = null;
        this.Ei = new HashMap<>();
        this.Ek = cls.getCanonicalName();
    }

    private static HashMap<String, HashMap<String, ga.a<?, ?>>> b(ArrayList<a> arrayList) {
        HashMap<String, HashMap<String, ga.a<?, ?>>> map = new HashMap<>();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            a aVar = arrayList.get(i);
            map.put(aVar.className, aVar.fp());
        }
        return map;
    }

    public void a(Class<? extends ga> cls, HashMap<String, ga.a<?, ?>> map) {
        this.Ei.put(cls.getCanonicalName(), map);
    }

    public HashMap<String, ga.a<?, ?>> au(String str) {
        return this.Ei.get(str);
    }

    public boolean b(Class<? extends ga> cls) {
        return this.Ei.containsKey(cls.getCanonicalName());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        ge geVar = CREATOR;
        return 0;
    }

    public void fl() {
        Iterator<String> it = this.Ei.keySet().iterator();
        while (it.hasNext()) {
            HashMap<String, ga.a<?, ?>> map = this.Ei.get(it.next());
            Iterator<String> it2 = map.keySet().iterator();
            while (it2.hasNext()) {
                map.get(it2.next()).a(this);
            }
        }
    }

    public void fm() {
        for (String str : this.Ei.keySet()) {
            HashMap<String, ga.a<?, ?>> map = this.Ei.get(str);
            HashMap<String, ga.a<?, ?>> map2 = new HashMap<>();
            for (String str2 : map.keySet()) {
                map2.put(str2, map.get(str2).fb());
            }
            this.Ei.put(str, map2);
        }
    }

    ArrayList<a> fn() {
        ArrayList<a> arrayList = new ArrayList<>();
        for (String str : this.Ei.keySet()) {
            arrayList.add(new a(str, this.Ei.get(str)));
        }
        return arrayList;
    }

    public String fo() {
        return this.Ek;
    }

    int getVersionCode() {
        return this.xH;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String str : this.Ei.keySet()) {
            sb.append(str).append(":\n");
            HashMap<String, ga.a<?, ?>> map = this.Ei.get(str);
            for (String str2 : map.keySet()) {
                sb.append("  ").append(str2).append(": ");
                sb.append(map.get(str2));
            }
        }
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        ge geVar = CREATOR;
        ge.a(this, out, flags);
    }
}
