package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class gg extends ga implements SafeParcelable {
    public static final gh CREATOR = new gh();
    private final gd Eg;
    private final Parcel En;
    private final int Eo;
    private int Ep;
    private int Eq;
    private final String mClassName;
    private final int xH;

    gg(int i, Parcel parcel, gd gdVar) {
        this.xH = i;
        this.En = (Parcel) fq.f(parcel);
        this.Eo = 2;
        this.Eg = gdVar;
        if (this.Eg == null) {
            this.mClassName = null;
        } else {
            this.mClassName = this.Eg.fo();
        }
        this.Ep = 2;
    }

    private gg(SafeParcelable safeParcelable, gd gdVar, String str) {
        this.xH = 1;
        this.En = Parcel.obtain();
        safeParcelable.writeToParcel(this.En, 0);
        this.Eo = 1;
        this.Eg = (gd) fq.f(gdVar);
        this.mClassName = (String) fq.f(str);
        this.Ep = 2;
    }

    public static <T extends ga & SafeParcelable> gg a(T t) {
        String canonicalName = t.getClass().getCanonicalName();
        return new gg(t, b(t), canonicalName);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void a(gd gdVar, ga gaVar) {
        Class<?> cls = gaVar.getClass();
        if (gdVar.b((Class<? extends ga>) cls)) {
            return;
        }
        HashMap<String, ga.a<?, ?>> mapEY = gaVar.eY();
        gdVar.a(cls, gaVar.eY());
        Iterator<String> it = mapEY.keySet().iterator();
        while (it.hasNext()) {
            ga.a<?, ?> aVar = mapEY.get(it.next());
            Class<? extends ga> clsFg = aVar.fg();
            if (clsFg != null) {
                try {
                    a(gdVar, clsFg.newInstance());
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException("Could not access object of type " + aVar.fg().getCanonicalName(), e);
                } catch (InstantiationException e2) {
                    throw new IllegalStateException("Could not instantiate an object of type " + aVar.fg().getCanonicalName(), e2);
                }
            }
        }
    }

    private void a(StringBuilder sb, int i, Object obj) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                sb.append(obj);
                return;
            case 7:
                sb.append("\"").append(gp.av(obj.toString())).append("\"");
                return;
            case 8:
                sb.append("\"").append(gj.d((byte[]) obj)).append("\"");
                return;
            case 9:
                sb.append("\"").append(gj.e((byte[]) obj));
                sb.append("\"");
                return;
            case 10:
                gq.a(sb, (HashMap) obj);
                return;
            case 11:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                throw new IllegalArgumentException("Unknown type = " + i);
        }
    }

    private void a(StringBuilder sb, ga.a<?, ?> aVar, Parcel parcel, int i) {
        switch (aVar.eX()) {
            case 0:
                b(sb, aVar, a(aVar, Integer.valueOf(com.google.android.gms.common.internal.safeparcel.a.g(parcel, i))));
                return;
            case 1:
                b(sb, aVar, a(aVar, com.google.android.gms.common.internal.safeparcel.a.j(parcel, i)));
                return;
            case 2:
                b(sb, aVar, a(aVar, Long.valueOf(com.google.android.gms.common.internal.safeparcel.a.i(parcel, i))));
                return;
            case 3:
                b(sb, aVar, a(aVar, Float.valueOf(com.google.android.gms.common.internal.safeparcel.a.k(parcel, i))));
                return;
            case 4:
                b(sb, aVar, a(aVar, Double.valueOf(com.google.android.gms.common.internal.safeparcel.a.l(parcel, i))));
                return;
            case 5:
                b(sb, aVar, a(aVar, com.google.android.gms.common.internal.safeparcel.a.m(parcel, i)));
                return;
            case 6:
                b(sb, aVar, a(aVar, Boolean.valueOf(com.google.android.gms.common.internal.safeparcel.a.c(parcel, i))));
                return;
            case 7:
                b(sb, aVar, a(aVar, com.google.android.gms.common.internal.safeparcel.a.n(parcel, i)));
                return;
            case 8:
            case 9:
                b(sb, aVar, a(aVar, com.google.android.gms.common.internal.safeparcel.a.q(parcel, i)));
                return;
            case 10:
                b(sb, aVar, a(aVar, c(com.google.android.gms.common.internal.safeparcel.a.p(parcel, i))));
                return;
            case 11:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                throw new IllegalArgumentException("Unknown field out type = " + aVar.eX());
        }
    }

    private void a(StringBuilder sb, String str, ga.a<?, ?> aVar, Parcel parcel, int i) {
        sb.append("\"").append(str).append("\":");
        if (aVar.fi()) {
            a(sb, aVar, parcel, i);
        } else {
            b(sb, aVar, parcel, i);
        }
    }

    private void a(StringBuilder sb, HashMap<String, ga.a<?, ?>> map, Parcel parcel) {
        HashMap<Integer, Map.Entry<String, ga.a<?, ?>>> mapC = c(map);
        sb.append('{');
        int iO = com.google.android.gms.common.internal.safeparcel.a.o(parcel);
        boolean z = false;
        while (parcel.dataPosition() < iO) {
            int iN = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
            Map.Entry<String, ga.a<?, ?>> entry = mapC.get(Integer.valueOf(com.google.android.gms.common.internal.safeparcel.a.R(iN)));
            if (entry != null) {
                if (z) {
                    sb.append(",");
                }
                a(sb, entry.getKey(), entry.getValue(), parcel, iN);
                z = true;
            }
        }
        if (parcel.dataPosition() != iO) {
            throw new com.google.android.gms.common.internal.safeparcel.a.C0035a("Overread allowed size end=" + iO, parcel);
        }
        sb.append('}');
    }

    private static gd b(ga gaVar) {
        gd gdVar = new gd(gaVar.getClass());
        a(gdVar, gaVar);
        gdVar.fm();
        gdVar.fl();
        return gdVar;
    }

    private void b(StringBuilder sb, ga.a<?, ?> aVar, Parcel parcel, int i) {
        if (aVar.fd()) {
            sb.append("[");
            switch (aVar.eX()) {
                case 0:
                    gi.a(sb, com.google.android.gms.common.internal.safeparcel.a.t(parcel, i));
                    break;
                case 1:
                    gi.a(sb, com.google.android.gms.common.internal.safeparcel.a.v(parcel, i));
                    break;
                case 2:
                    gi.a(sb, com.google.android.gms.common.internal.safeparcel.a.u(parcel, i));
                    break;
                case 3:
                    gi.a(sb, com.google.android.gms.common.internal.safeparcel.a.w(parcel, i));
                    break;
                case 4:
                    gi.a(sb, com.google.android.gms.common.internal.safeparcel.a.x(parcel, i));
                    break;
                case 5:
                    gi.a(sb, com.google.android.gms.common.internal.safeparcel.a.y(parcel, i));
                    break;
                case 6:
                    gi.a(sb, com.google.android.gms.common.internal.safeparcel.a.s(parcel, i));
                    break;
                case 7:
                    gi.a(sb, com.google.android.gms.common.internal.safeparcel.a.z(parcel, i));
                    break;
                case 8:
                case 9:
                case 10:
                    throw new UnsupportedOperationException("List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported");
                case 11:
                    Parcel[] parcelArrC = com.google.android.gms.common.internal.safeparcel.a.C(parcel, i);
                    int length = parcelArrC.length;
                    for (int i2 = 0; i2 < length; i2++) {
                        if (i2 > 0) {
                            sb.append(",");
                        }
                        parcelArrC[i2].setDataPosition(0);
                        a(sb, aVar.fk(), parcelArrC[i2]);
                    }
                    break;
                default:
                    throw new IllegalStateException("Unknown field type out.");
            }
            sb.append("]");
            return;
        }
        switch (aVar.eX()) {
            case 0:
                sb.append(com.google.android.gms.common.internal.safeparcel.a.g(parcel, i));
                return;
            case 1:
                sb.append(com.google.android.gms.common.internal.safeparcel.a.j(parcel, i));
                return;
            case 2:
                sb.append(com.google.android.gms.common.internal.safeparcel.a.i(parcel, i));
                return;
            case 3:
                sb.append(com.google.android.gms.common.internal.safeparcel.a.k(parcel, i));
                return;
            case 4:
                sb.append(com.google.android.gms.common.internal.safeparcel.a.l(parcel, i));
                return;
            case 5:
                sb.append(com.google.android.gms.common.internal.safeparcel.a.m(parcel, i));
                return;
            case 6:
                sb.append(com.google.android.gms.common.internal.safeparcel.a.c(parcel, i));
                return;
            case 7:
                sb.append("\"").append(gp.av(com.google.android.gms.common.internal.safeparcel.a.n(parcel, i))).append("\"");
                return;
            case 8:
                sb.append("\"").append(gj.d(com.google.android.gms.common.internal.safeparcel.a.q(parcel, i))).append("\"");
                return;
            case 9:
                sb.append("\"").append(gj.e(com.google.android.gms.common.internal.safeparcel.a.q(parcel, i)));
                sb.append("\"");
                return;
            case 10:
                Bundle bundleP = com.google.android.gms.common.internal.safeparcel.a.p(parcel, i);
                Set<String> setKeySet = bundleP.keySet();
                setKeySet.size();
                sb.append("{");
                boolean z = true;
                for (String str : setKeySet) {
                    if (!z) {
                        sb.append(",");
                    }
                    sb.append("\"").append(str).append("\"");
                    sb.append(":");
                    sb.append("\"").append(gp.av(bundleP.getString(str))).append("\"");
                    z = false;
                }
                sb.append("}");
                return;
            case 11:
                Parcel parcelB = com.google.android.gms.common.internal.safeparcel.a.B(parcel, i);
                parcelB.setDataPosition(0);
                a(sb, aVar.fk(), parcelB);
                return;
            default:
                throw new IllegalStateException("Unknown field type out");
        }
    }

    private void b(StringBuilder sb, ga.a<?, ?> aVar, Object obj) {
        if (aVar.fc()) {
            b(sb, aVar, (ArrayList<?>) obj);
        } else {
            a(sb, aVar.eW(), obj);
        }
    }

    private void b(StringBuilder sb, ga.a<?, ?> aVar, ArrayList<?> arrayList) {
        sb.append("[");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(",");
            }
            a(sb, aVar.eW(), arrayList.get(i));
        }
        sb.append("]");
    }

    public static HashMap<String, String> c(Bundle bundle) {
        HashMap<String, String> map = new HashMap<>();
        for (String str : bundle.keySet()) {
            map.put(str, bundle.getString(str));
        }
        return map;
    }

    private static HashMap<Integer, Map.Entry<String, ga.a<?, ?>>> c(HashMap<String, ga.a<?, ?>> map) {
        HashMap<Integer, Map.Entry<String, ga.a<?, ?>>> map2 = new HashMap<>();
        for (Map.Entry<String, ga.a<?, ?>> entry : map.entrySet()) {
            map2.put(Integer.valueOf(entry.getValue().ff()), entry);
        }
        return map2;
    }

    @Override // com.google.android.gms.internal.ga
    protected Object aq(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    @Override // com.google.android.gms.internal.ga
    protected boolean ar(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        gh ghVar = CREATOR;
        return 0;
    }

    @Override // com.google.android.gms.internal.ga
    public HashMap<String, ga.a<?, ?>> eY() {
        if (this.Eg == null) {
            return null;
        }
        return this.Eg.au(this.mClassName);
    }

    public Parcel fq() {
        switch (this.Ep) {
            case 0:
                this.Eq = com.google.android.gms.common.internal.safeparcel.b.p(this.En);
                com.google.android.gms.common.internal.safeparcel.b.F(this.En, this.Eq);
                this.Ep = 2;
                break;
            case 1:
                com.google.android.gms.common.internal.safeparcel.b.F(this.En, this.Eq);
                this.Ep = 2;
                break;
        }
        return this.En;
    }

    gd fr() {
        switch (this.Eo) {
            case 0:
                return null;
            case 1:
                return this.Eg;
            case 2:
                return this.Eg;
            default:
                throw new IllegalStateException("Invalid creation type: " + this.Eo);
        }
    }

    public int getVersionCode() {
        return this.xH;
    }

    @Override // com.google.android.gms.internal.ga
    public String toString() {
        fq.b(this.Eg, "Cannot convert to JSON on client side.");
        Parcel parcelFq = fq();
        parcelFq.setDataPosition(0);
        StringBuilder sb = new StringBuilder(100);
        a(sb, this.Eg.au(this.mClassName), parcelFq);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        gh ghVar = CREATOR;
        gh.a(this, out, flags);
    }
}
