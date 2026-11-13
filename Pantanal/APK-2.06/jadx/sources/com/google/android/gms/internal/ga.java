package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public abstract class ga {

    public static class a<I, O> implements SafeParcelable {
        public static final gb CREATOR = new gb();
        protected final int DY;
        protected final boolean DZ;
        protected final int Ea;
        protected final boolean Eb;
        protected final String Ec;
        protected final int Ed;
        protected final Class<? extends ga> Ee;
        protected final String Ef;
        private gd Eg;
        private b<I, O> Eh;
        private final int xH;

        a(int i, int i2, boolean z, int i3, boolean z2, String str, int i4, String str2, fv fvVar) {
            this.xH = i;
            this.DY = i2;
            this.DZ = z;
            this.Ea = i3;
            this.Eb = z2;
            this.Ec = str;
            this.Ed = i4;
            if (str2 == null) {
                this.Ee = null;
                this.Ef = null;
            } else {
                this.Ee = gg.class;
                this.Ef = str2;
            }
            if (fvVar == null) {
                this.Eh = null;
            } else {
                this.Eh = (b<I, O>) fvVar.eU();
            }
        }

        protected a(int i, boolean z, int i2, boolean z2, String str, int i3, Class<? extends ga> cls, b<I, O> bVar) {
            this.xH = 1;
            this.DY = i;
            this.DZ = z;
            this.Ea = i2;
            this.Eb = z2;
            this.Ec = str;
            this.Ed = i3;
            this.Ee = cls;
            if (cls == null) {
                this.Ef = null;
            } else {
                this.Ef = cls.getCanonicalName();
            }
            this.Eh = bVar;
        }

        public static a a(String str, int i, b<?, ?> bVar, boolean z) {
            return new a(bVar.eW(), z, bVar.eX(), false, str, i, null, bVar);
        }

        public static <T extends ga> a<T, T> a(String str, int i, Class<T> cls) {
            return new a<>(11, false, 11, false, str, i, cls, null);
        }

        public static <T extends ga> a<ArrayList<T>, ArrayList<T>> b(String str, int i, Class<T> cls) {
            return new a<>(11, true, 11, true, str, i, cls, null);
        }

        public static a<Integer, Integer> g(String str, int i) {
            return new a<>(0, false, 0, false, str, i, null, null);
        }

        public static a<Double, Double> h(String str, int i) {
            return new a<>(4, false, 4, false, str, i, null, null);
        }

        public static a<Boolean, Boolean> i(String str, int i) {
            return new a<>(6, false, 6, false, str, i, null, null);
        }

        public static a<String, String> j(String str, int i) {
            return new a<>(7, false, 7, false, str, i, null, null);
        }

        public static a<ArrayList<String>, ArrayList<String>> k(String str, int i) {
            return new a<>(7, true, 7, true, str, i, null, null);
        }

        public void a(gd gdVar) {
            this.Eg = gdVar;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            gb gbVar = CREATOR;
            return 0;
        }

        public int eW() {
            return this.DY;
        }

        public int eX() {
            return this.Ea;
        }

        public a<I, O> fb() {
            return new a<>(this.xH, this.DY, this.DZ, this.Ea, this.Eb, this.Ec, this.Ed, this.Ef, fj());
        }

        public boolean fc() {
            return this.DZ;
        }

        public boolean fd() {
            return this.Eb;
        }

        public String fe() {
            return this.Ec;
        }

        public int ff() {
            return this.Ed;
        }

        public Class<? extends ga> fg() {
            return this.Ee;
        }

        String fh() {
            if (this.Ef == null) {
                return null;
            }
            return this.Ef;
        }

        public boolean fi() {
            return this.Eh != null;
        }

        fv fj() {
            if (this.Eh == null) {
                return null;
            }
            return fv.a(this.Eh);
        }

        public HashMap<String, a<?, ?>> fk() {
            fq.f(this.Ef);
            fq.f(this.Eg);
            return this.Eg.au(this.Ef);
        }

        public I g(O o) {
            return this.Eh.g(o);
        }

        public int getVersionCode() {
            return this.xH;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Field\n");
            sb.append("            versionCode=").append(this.xH).append('\n');
            sb.append("                 typeIn=").append(this.DY).append('\n');
            sb.append("            typeInArray=").append(this.DZ).append('\n');
            sb.append("                typeOut=").append(this.Ea).append('\n');
            sb.append("           typeOutArray=").append(this.Eb).append('\n');
            sb.append("        outputFieldName=").append(this.Ec).append('\n');
            sb.append("      safeParcelFieldId=").append(this.Ed).append('\n');
            sb.append("       concreteTypeName=").append(fh()).append('\n');
            if (fg() != null) {
                sb.append("     concreteType.class=").append(fg().getCanonicalName()).append('\n');
            }
            sb.append("          converterName=").append(this.Eh == null ? "null" : this.Eh.getClass().getCanonicalName()).append('\n');
            return sb.toString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            gb gbVar = CREATOR;
            gb.a(this, out, flags);
        }
    }

    public interface b<I, O> {
        int eW();

        int eX();

        I g(O o);
    }

    private void a(StringBuilder sb, a aVar, Object obj) {
        if (aVar.eW() == 11) {
            sb.append(aVar.fg().cast(obj).toString());
        } else {
            if (aVar.eW() != 7) {
                sb.append(obj);
                return;
            }
            sb.append("\"");
            sb.append(gp.av((String) obj));
            sb.append("\"");
        }
    }

    private void a(StringBuilder sb, a aVar, ArrayList<Object> arrayList) {
        sb.append("[");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(",");
            }
            Object obj = arrayList.get(i);
            if (obj != null) {
                a(sb, aVar, obj);
            }
        }
        sb.append("]");
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected <O, I> I a(a<I, O> aVar, Object obj) {
        return ((a) aVar).Eh != null ? aVar.g(obj) : obj;
    }

    protected boolean a(a aVar) {
        if (aVar.eX() == 11) {
            return aVar.fd() ? at(aVar.fe()) : as(aVar.fe());
        }
        return ar(aVar.fe());
    }

    protected abstract Object aq(String str);

    protected abstract boolean ar(String str);

    protected boolean as(String str) {
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    protected boolean at(String str) {
        throw new UnsupportedOperationException("Concrete type arrays not supported");
    }

    protected Object b(a aVar) {
        String strFe = aVar.fe();
        if (aVar.fg() == null) {
            return aq(aVar.fe());
        }
        fq.a(aq(aVar.fe()) == null, "Concrete field shouldn't be value object: " + aVar.fe());
        HashMap<String, Object> mapFa = aVar.fd() ? fa() : eZ();
        if (mapFa != null) {
            return mapFa.get(strFe);
        }
        try {
            return getClass().getMethod("get" + Character.toUpperCase(strFe.charAt(0)) + strFe.substring(1), new Class[0]).invoke(this, new Object[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public abstract HashMap<String, a<?, ?>> eY();

    public HashMap<String, Object> eZ() {
        return null;
    }

    public HashMap<String, Object> fa() {
        return null;
    }

    public String toString() {
        HashMap<String, a<?, ?>> mapEY = eY();
        StringBuilder sb = new StringBuilder(100);
        for (String str : mapEY.keySet()) {
            a<?, ?> aVar = mapEY.get(str);
            if (a(aVar)) {
                Object objA = a(aVar, b(aVar));
                if (sb.length() == 0) {
                    sb.append("{");
                } else {
                    sb.append(",");
                }
                sb.append("\"").append(str).append("\":");
                if (objA != null) {
                    switch (aVar.eX()) {
                        case 8:
                            sb.append("\"").append(gj.d((byte[]) objA)).append("\"");
                            break;
                        case 9:
                            sb.append("\"").append(gj.e((byte[]) objA)).append("\"");
                            break;
                        case 10:
                            gq.a(sb, (HashMap) objA);
                            break;
                        default:
                            if (aVar.fc()) {
                                a(sb, (a) aVar, (ArrayList<Object>) objA);
                            } else {
                                a(sb, aVar, objA);
                            }
                            break;
                    }
                } else {
                    sb.append("null");
                }
            }
        }
        if (sb.length() > 0) {
            sb.append("}");
        } else {
            sb.append("{}");
        }
        return sb.toString();
    }
}
