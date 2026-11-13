package com.chartboost.sdk.Libraries;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class f {
    private static final p a = new p();
    private static final n b;
    private static final j c;
    private static final d d;
    private static final l e;

    public static abstract class e extends a {
    }

    public static abstract class a {
        private String a = null;

        public abstract String a();

        public abstract boolean a(Object obj);

        public boolean a(Object obj, StringBuilder sb) {
            boolean zA = a(obj);
            if (!zA) {
                sb.append(this.a != null ? this.a : a());
            }
            return zA;
        }
    }

    private static class i extends a {
        private final Class<?> a;

        public i(Class<?> cls) {
            this.a = cls;
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public boolean a(Object obj) {
            return this.a.isInstance(obj);
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public String a() {
            return "object must be an instance of " + this.a.getName() + ".";
        }
    }

    public static a a(Class<?> cls) {
        return new i(cls);
    }

    private static class p extends i {
        public p() {
            super(String.class);
        }
    }

    static {
        b = new n();
        c = new j();
        d = new d();
        e = new l();
    }

    public static a a() {
        return a;
    }

    private static class n extends a {
        private n() {
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public boolean a(Object obj) {
            return (obj instanceof Number) || Integer.TYPE.isInstance(obj) || Long.TYPE.isInstance(obj) || Short.TYPE.isInstance(obj) || Float.TYPE.isInstance(obj) || Double.TYPE.isInstance(obj) || Byte.TYPE.isInstance(obj);
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public String a() {
            return "object must be a number (primitive type or derived from Number).";
        }
    }

    public static a b() {
        return b;
    }

    private static class j extends a {
        private j() {
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public boolean a(Object obj) {
            return Integer.class.isInstance(obj) || Long.class.isInstance(obj) || Short.class.isInstance(obj) || Byte.class.isInstance(obj) || BigInteger.class.isInstance(obj) || Integer.TYPE.isInstance(obj) || Long.TYPE.isInstance(obj) || Short.TYPE.isInstance(obj) || Byte.TYPE.isInstance(obj);
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public String a() {
            return "object must be a number w/o decimals (int, long, short, or byte).";
        }
    }

    private static class d extends a {
        private d() {
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public boolean a(Object obj) {
            return Boolean.class.isInstance(obj) || Boolean.TYPE.isInstance(obj);
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public String a() {
            return "object must be a boolean.";
        }
    }

    public static a c() {
        return d;
    }

    private static class l extends a {
        private l() {
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public boolean a(Object obj) {
            return obj == null || obj == JSONObject.NULL;
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public String a() {
            return "object must be null.";
        }
    }

    private static class m extends l {
        private final a a;

        public m(a aVar) {
            super();
            this.a = aVar;
        }

        @Override // com.chartboost.sdk.Libraries.f.l, com.chartboost.sdk.Libraries.f.a
        public boolean a(Object obj) {
            if (super.a(obj)) {
                return true;
            }
            return this.a.a(obj);
        }

        @Override // com.chartboost.sdk.Libraries.f.l, com.chartboost.sdk.Libraries.f.a
        public String a() {
            return "object must be either null or " + this.a.a();
        }
    }

    public static a a(a aVar) {
        return new m(aVar);
    }

    private static class b extends a {
        private b() {
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public boolean a(Object obj) {
            return (obj instanceof List) || (obj instanceof JSONArray);
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public String a() {
            return "object must be an array.";
        }
    }

    private static class c extends b {
        private final a a;

        public c(a aVar) {
            super();
            this.a = aVar;
        }

        @Override // com.chartboost.sdk.Libraries.f.b, com.chartboost.sdk.Libraries.f.a
        public boolean a(Object obj) {
            if (obj instanceof List) {
                List list = (List) obj;
                for (int i = 0; i < list.size(); i++) {
                    if (!this.a.a(list.get(i))) {
                        return false;
                    }
                }
                return true;
            }
            if (!(obj instanceof JSONArray)) {
                return false;
            }
            JSONArray jSONArray = (JSONArray) obj;
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                if (!this.a.a(jSONArray.opt(i2))) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.chartboost.sdk.Libraries.f.b, com.chartboost.sdk.Libraries.f.a
        public String a() {
            return "object must be an array of objects matching: <" + this.a.a() + ">";
        }
    }

    public static a b(a aVar) {
        return new c(aVar);
    }

    private static class o extends a {
        private final a[] a;

        public o(a[] aVarArr) {
            this.a = aVarArr;
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public boolean a(Object obj) {
            for (a aVar : this.a) {
                if (aVar.a(obj)) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public String a() {
            StringBuilder sb = new StringBuilder();
            sb.append("object must match one of the following: ");
            for (int i = 0; i < this.a.length; i++) {
                sb.append("<");
                sb.append(this.a[i].a());
                sb.append(">");
                if (i < this.a.length - 1) {
                    sb.append(", ");
                }
            }
            return sb.toString();
        }
    }

    public static a a(a... aVarArr) {
        return new o(aVarArr);
    }

    private static class q extends a {
        protected String a = null;
        private final a[] b;

        public q(a[] aVarArr) {
            this.b = aVarArr;
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public boolean a(Object obj) {
            for (a aVar : this.b) {
                if (!aVar.a(obj)) {
                    this.a = "object failed to match: <" + aVar.a() + ">";
                    return false;
                }
            }
            return true;
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public String a() {
            if (this.a != null) {
                return this.a;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("object must match ALL of the following: ");
            for (int i = 0; i < this.b.length; i++) {
                sb.append("<");
                sb.append(this.b[i].a());
                sb.append(">");
                if (i < this.b.length - 1) {
                    sb.append(", ");
                }
            }
            return sb.toString();
        }
    }

    public static a b(a... aVarArr) {
        return new q(aVarArr);
    }

    private static class h extends a {
        private final Object[] a;

        public h(Object[] objArr) {
            this.a = objArr;
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public boolean a(Object obj) {
            for (Object obj2 : this.a) {
                if (obj == null) {
                    if (obj2 == null) {
                        return true;
                    }
                } else if (obj.equals(obj2)) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public String a() {
            StringBuilder sb = new StringBuilder();
            sb.append("object must equal one of the following: ");
            for (int i = 0; i < this.a.length; i++) {
                sb.append("<");
                sb.append(this.a[i].toString());
                sb.append(">");
                if (i < this.a.length - 1) {
                    sb.append(", ");
                }
            }
            return sb.toString();
        }
    }

    public static a a(Object... objArr) {
        return new h(objArr);
    }

    public static class k {
        final String a;
        final a b;

        public k(String str, a aVar) {
            this.a = str;
            this.b = aVar;
        }
    }

    public static k a(String str, a aVar) {
        return new k(str, aVar);
    }

    /* JADX INFO: renamed from: com.chartboost.sdk.Libraries.f$f, reason: collision with other inner class name */
    private static class C0010f extends a {
        protected final k[] a;
        protected String b = null;

        public C0010f(k[] kVarArr) {
            this.a = kVarArr;
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public boolean a(Object obj) {
            if (obj instanceof Map) {
                Map map = (Map) obj;
                for (Map.Entry entry : map.entrySet()) {
                    if (!(entry.getKey() instanceof String)) {
                        this.b = "key '" + entry.getKey().toString() + "' is not a string";
                        return false;
                    }
                }
                if (this.a != null && this.a.length >= 1) {
                    for (k kVar : this.a) {
                        String str = kVar.a;
                        a aVar = kVar.b;
                        if (!map.containsKey(str)) {
                            if (!aVar.a(null)) {
                                this.b = "no key for required mapping '" + str + "' : <" + aVar.a() + ">";
                                return false;
                            }
                        } else if (!aVar.a(map.get(str))) {
                            this.b = "key '" + str + "' fails to match: <" + aVar.a() + ">";
                            return false;
                        }
                    }
                }
                return true;
            }
            if (!(obj instanceof JSONObject)) {
                return false;
            }
            JSONObject jSONObject = (JSONObject) obj;
            if (this.a != null && this.a.length >= 1) {
                for (k kVar2 : this.a) {
                    String str2 = kVar2.a;
                    a aVar2 = kVar2.b;
                    try {
                        if (!aVar2.a(jSONObject.get(str2))) {
                            this.b = "key '" + str2 + "' fails to match: <" + aVar2.a() + ">";
                            return false;
                        }
                        continue;
                    } catch (JSONException e) {
                        if (!aVar2.a(null)) {
                            this.b = "no key for required mapping '" + str2 + "' : <" + aVar2.a() + ">";
                            return false;
                        }
                    }
                }
            }
            return true;
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public String a() {
            if (this.b != null) {
                return this.b;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("object must contain the following key-value schema: {\n");
            for (int i = 0; i < this.a.length; i++) {
                sb.append("<");
                sb.append(this.a[i].a);
                sb.append(": [");
                sb.append(this.a[i].b.a());
                sb.append("]>");
                if (i < this.a.length - 1) {
                    sb.append(",\n");
                }
            }
            sb.append("}");
            return sb.toString();
        }
    }

    public static a a(k... kVarArr) {
        return new C0010f(kVarArr);
    }

    private static class g extends a {
        private final C0010f b;
        private String c = null;
        private final Set<String> a = new HashSet();

        public g(C0010f c0010f) {
            this.b = c0010f;
            for (int i = 0; i < this.b.a.length; i++) {
                this.a.add(this.b.a[i].a);
            }
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public boolean a(Object obj) {
            if (!this.b.a(obj)) {
                this.c = this.b.b;
                return false;
            }
            if (obj instanceof Map) {
                for (Object obj2 : ((Map) obj).keySet()) {
                    if (!this.a.contains(obj2)) {
                        this.c = "key '" + obj2 + "' is not allowed in this dictionary";
                        return false;
                    }
                }
                return true;
            }
            if (obj instanceof JSONObject) {
                Iterator<String> itKeys = ((JSONObject) obj).keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    if (!this.a.contains(next)) {
                        this.c = "key '" + next + "' is not allowed in this dictionary";
                        return false;
                    }
                }
                return true;
            }
            this.c = "object must be a dictionary";
            return false;
        }

        @Override // com.chartboost.sdk.Libraries.f.a
        public String a() {
            if (this.c != null) {
                return this.c;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("object must EXACTLY MATCH the following key-value schema: {\n");
            for (int i = 0; i < this.b.a.length; i++) {
                sb.append("<");
                sb.append(this.b.a[i].a);
                sb.append(": [");
                sb.append(this.b.a[i].b.a());
                sb.append("]>");
                if (i < this.b.a.length - 1) {
                    sb.append(",\n");
                }
            }
            sb.append("}");
            return sb.toString();
        }
    }

    public static a b(k... kVarArr) {
        return new g(new C0010f(kVarArr));
    }

    public static boolean c(a aVar) {
        return (aVar instanceof C0010f) || (aVar instanceof g) || (aVar instanceof o);
    }
}
