package com.google.android.gms.internal;

import android.os.Parcel;
import com.adcolony.sdk.AdColonyUserMetadata;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.people.Person;
import com.tapjoy.TapjoyConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.anddev.andengine.util.constants.TimeConstants;

/* JADX INFO: loaded from: classes.dex */
public final class ih extends ga implements SafeParcelable, Person {
    public static final ii CREATOR = new ii();
    private static final HashMap<String, ga.a<?, ?>> UI = new HashMap<>();
    private String HA;
    private final Set<Integer> UJ;
    private String VH;
    private a VI;
    private String VJ;
    private String VK;
    private int VL;
    private b VM;
    private String VN;
    private c VO;
    private boolean VP;
    private String VQ;
    private d VR;
    private String VS;
    private int VT;
    private List<f> VU;
    private List<g> VV;
    private int VW;
    private int VX;
    private String VY;
    private List<h> VZ;
    private boolean Wa;
    private int lZ;
    private String ro;
    private String wp;
    private final int xH;

    public static final class a extends ga implements SafeParcelable, Person.AgeRange {
        public static final ij CREATOR = new ij();
        private static final HashMap<String, ga.a<?, ?>> UI = new HashMap<>();
        private final Set<Integer> UJ;
        private int Wb;
        private int Wc;
        private final int xH;

        static {
            UI.put("max", ga.a.g("max", 2));
            UI.put("min", ga.a.g("min", 3));
        }

        public a() {
            this.xH = 1;
            this.UJ = new HashSet();
        }

        a(Set<Integer> set, int i, int i2, int i3) {
            this.UJ = set;
            this.xH = i;
            this.Wb = i2;
            this.Wc = i3;
        }

        @Override // com.google.android.gms.internal.ga
        protected boolean a(ga.a aVar) {
            return this.UJ.contains(Integer.valueOf(aVar.ff()));
        }

        @Override // com.google.android.gms.internal.ga
        protected Object aq(String str) {
            return null;
        }

        @Override // com.google.android.gms.internal.ga
        protected boolean ar(String str) {
            return false;
        }

        @Override // com.google.android.gms.internal.ga
        protected Object b(ga.a aVar) {
            switch (aVar.ff()) {
                case 2:
                    return Integer.valueOf(this.Wb);
                case 3:
                    return Integer.valueOf(this.Wc);
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + aVar.ff());
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            ij ijVar = CREATOR;
            return 0;
        }

        @Override // com.google.android.gms.internal.ga
        public HashMap<String, ga.a<?, ?>> eY() {
            return UI;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof a)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            a aVar = (a) obj;
            for (ga.a<?, ?> aVar2 : UI.values()) {
                if (a(aVar2)) {
                    if (aVar.a(aVar2) && b(aVar2).equals(aVar.b(aVar2))) {
                    }
                    return false;
                }
                if (aVar.a(aVar2)) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.android.gms.plus.model.people.Person.AgeRange
        public int getMax() {
            return this.Wb;
        }

        @Override // com.google.android.gms.plus.model.people.Person.AgeRange
        public int getMin() {
            return this.Wc;
        }

        int getVersionCode() {
            return this.xH;
        }

        @Override // com.google.android.gms.plus.model.people.Person.AgeRange
        public boolean hasMax() {
            return this.UJ.contains(2);
        }

        @Override // com.google.android.gms.plus.model.people.Person.AgeRange
        public boolean hasMin() {
            return this.UJ.contains(3);
        }

        public int hashCode() {
            int iHashCode = 0;
            Iterator<ga.a<?, ?>> it = UI.values().iterator();
            while (true) {
                int i = iHashCode;
                if (!it.hasNext()) {
                    return i;
                }
                ga.a<?, ?> next = it.next();
                if (a(next)) {
                    iHashCode = b(next).hashCode() + i + next.ff();
                } else {
                    iHashCode = i;
                }
            }
        }

        @Override // com.google.android.gms.common.data.Freezable
        public boolean isDataValid() {
            return true;
        }

        @Override // com.google.android.gms.common.data.Freezable
        /* JADX INFO: renamed from: jD, reason: merged with bridge method [inline-methods] */
        public a freeze() {
            return this;
        }

        Set<Integer> ja() {
            return this.UJ;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            ij ijVar = CREATOR;
            ij.a(this, out, flags);
        }
    }

    public static final class b extends ga implements SafeParcelable, Person.Cover {
        public static final ik CREATOR = new ik();
        private static final HashMap<String, ga.a<?, ?>> UI = new HashMap<>();
        private final Set<Integer> UJ;
        private a Wd;
        private C0076b We;
        private int Wf;
        private final int xH;

        public static final class a extends ga implements SafeParcelable, Person.Cover.CoverInfo {
            public static final il CREATOR = new il();
            private static final HashMap<String, ga.a<?, ?>> UI = new HashMap<>();
            private final Set<Integer> UJ;
            private int Wg;
            private int Wh;
            private final int xH;

            static {
                UI.put("leftImageOffset", ga.a.g("leftImageOffset", 2));
                UI.put("topImageOffset", ga.a.g("topImageOffset", 3));
            }

            public a() {
                this.xH = 1;
                this.UJ = new HashSet();
            }

            a(Set<Integer> set, int i, int i2, int i3) {
                this.UJ = set;
                this.xH = i;
                this.Wg = i2;
                this.Wh = i3;
            }

            @Override // com.google.android.gms.internal.ga
            protected boolean a(ga.a aVar) {
                return this.UJ.contains(Integer.valueOf(aVar.ff()));
            }

            @Override // com.google.android.gms.internal.ga
            protected Object aq(String str) {
                return null;
            }

            @Override // com.google.android.gms.internal.ga
            protected boolean ar(String str) {
                return false;
            }

            @Override // com.google.android.gms.internal.ga
            protected Object b(ga.a aVar) {
                switch (aVar.ff()) {
                    case 2:
                        return Integer.valueOf(this.Wg);
                    case 3:
                        return Integer.valueOf(this.Wh);
                    default:
                        throw new IllegalStateException("Unknown safe parcelable id=" + aVar.ff());
                }
            }

            @Override // android.os.Parcelable
            public int describeContents() {
                il ilVar = CREATOR;
                return 0;
            }

            @Override // com.google.android.gms.internal.ga
            public HashMap<String, ga.a<?, ?>> eY() {
                return UI;
            }

            public boolean equals(Object obj) {
                if (!(obj instanceof a)) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                a aVar = (a) obj;
                for (ga.a<?, ?> aVar2 : UI.values()) {
                    if (a(aVar2)) {
                        if (aVar.a(aVar2) && b(aVar2).equals(aVar.b(aVar2))) {
                        }
                        return false;
                    }
                    if (aVar.a(aVar2)) {
                        return false;
                    }
                }
                return true;
            }

            @Override // com.google.android.gms.plus.model.people.Person.Cover.CoverInfo
            public int getLeftImageOffset() {
                return this.Wg;
            }

            @Override // com.google.android.gms.plus.model.people.Person.Cover.CoverInfo
            public int getTopImageOffset() {
                return this.Wh;
            }

            int getVersionCode() {
                return this.xH;
            }

            @Override // com.google.android.gms.plus.model.people.Person.Cover.CoverInfo
            public boolean hasLeftImageOffset() {
                return this.UJ.contains(2);
            }

            @Override // com.google.android.gms.plus.model.people.Person.Cover.CoverInfo
            public boolean hasTopImageOffset() {
                return this.UJ.contains(3);
            }

            public int hashCode() {
                int iHashCode = 0;
                Iterator<ga.a<?, ?>> it = UI.values().iterator();
                while (true) {
                    int i = iHashCode;
                    if (!it.hasNext()) {
                        return i;
                    }
                    ga.a<?, ?> next = it.next();
                    if (a(next)) {
                        iHashCode = b(next).hashCode() + i + next.ff();
                    } else {
                        iHashCode = i;
                    }
                }
            }

            @Override // com.google.android.gms.common.data.Freezable
            public boolean isDataValid() {
                return true;
            }

            @Override // com.google.android.gms.common.data.Freezable
            /* JADX INFO: renamed from: jH, reason: merged with bridge method [inline-methods] */
            public a freeze() {
                return this;
            }

            Set<Integer> ja() {
                return this.UJ;
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel out, int flags) {
                il ilVar = CREATOR;
                il.a(this, out, flags);
            }
        }

        /* JADX INFO: renamed from: com.google.android.gms.internal.ih$b$b, reason: collision with other inner class name */
        public static final class C0076b extends ga implements SafeParcelable, Person.Cover.CoverPhoto {
            public static final im CREATOR = new im();
            private static final HashMap<String, ga.a<?, ?>> UI = new HashMap<>();
            private final Set<Integer> UJ;
            private int kr;
            private int ks;
            private String ro;
            private final int xH;

            static {
                UI.put("height", ga.a.g("height", 2));
                UI.put("url", ga.a.j("url", 3));
                UI.put("width", ga.a.g("width", 4));
            }

            public C0076b() {
                this.xH = 1;
                this.UJ = new HashSet();
            }

            C0076b(Set<Integer> set, int i, int i2, String str, int i3) {
                this.UJ = set;
                this.xH = i;
                this.ks = i2;
                this.ro = str;
                this.kr = i3;
            }

            @Override // com.google.android.gms.internal.ga
            protected boolean a(ga.a aVar) {
                return this.UJ.contains(Integer.valueOf(aVar.ff()));
            }

            @Override // com.google.android.gms.internal.ga
            protected Object aq(String str) {
                return null;
            }

            @Override // com.google.android.gms.internal.ga
            protected boolean ar(String str) {
                return false;
            }

            @Override // com.google.android.gms.internal.ga
            protected Object b(ga.a aVar) {
                switch (aVar.ff()) {
                    case 2:
                        return Integer.valueOf(this.ks);
                    case 3:
                        return this.ro;
                    case 4:
                        return Integer.valueOf(this.kr);
                    default:
                        throw new IllegalStateException("Unknown safe parcelable id=" + aVar.ff());
                }
            }

            @Override // android.os.Parcelable
            public int describeContents() {
                im imVar = CREATOR;
                return 0;
            }

            @Override // com.google.android.gms.internal.ga
            public HashMap<String, ga.a<?, ?>> eY() {
                return UI;
            }

            public boolean equals(Object obj) {
                if (!(obj instanceof C0076b)) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                C0076b c0076b = (C0076b) obj;
                for (ga.a<?, ?> aVar : UI.values()) {
                    if (a(aVar)) {
                        if (c0076b.a(aVar) && b(aVar).equals(c0076b.b(aVar))) {
                        }
                        return false;
                    }
                    if (c0076b.a(aVar)) {
                        return false;
                    }
                }
                return true;
            }

            @Override // com.google.android.gms.plus.model.people.Person.Cover.CoverPhoto
            public int getHeight() {
                return this.ks;
            }

            @Override // com.google.android.gms.plus.model.people.Person.Cover.CoverPhoto
            public String getUrl() {
                return this.ro;
            }

            int getVersionCode() {
                return this.xH;
            }

            @Override // com.google.android.gms.plus.model.people.Person.Cover.CoverPhoto
            public int getWidth() {
                return this.kr;
            }

            @Override // com.google.android.gms.plus.model.people.Person.Cover.CoverPhoto
            public boolean hasHeight() {
                return this.UJ.contains(2);
            }

            @Override // com.google.android.gms.plus.model.people.Person.Cover.CoverPhoto
            public boolean hasUrl() {
                return this.UJ.contains(3);
            }

            @Override // com.google.android.gms.plus.model.people.Person.Cover.CoverPhoto
            public boolean hasWidth() {
                return this.UJ.contains(4);
            }

            public int hashCode() {
                int iHashCode = 0;
                Iterator<ga.a<?, ?>> it = UI.values().iterator();
                while (true) {
                    int i = iHashCode;
                    if (!it.hasNext()) {
                        return i;
                    }
                    ga.a<?, ?> next = it.next();
                    if (a(next)) {
                        iHashCode = b(next).hashCode() + i + next.ff();
                    } else {
                        iHashCode = i;
                    }
                }
            }

            @Override // com.google.android.gms.common.data.Freezable
            public boolean isDataValid() {
                return true;
            }

            @Override // com.google.android.gms.common.data.Freezable
            /* JADX INFO: renamed from: jI, reason: merged with bridge method [inline-methods] */
            public C0076b freeze() {
                return this;
            }

            Set<Integer> ja() {
                return this.UJ;
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel out, int flags) {
                im imVar = CREATOR;
                im.a(this, out, flags);
            }
        }

        static {
            UI.put("coverInfo", ga.a.a("coverInfo", 2, a.class));
            UI.put("coverPhoto", ga.a.a("coverPhoto", 3, C0076b.class));
            UI.put("layout", ga.a.a("layout", 4, new fx().f("banner", 0), false));
        }

        public b() {
            this.xH = 1;
            this.UJ = new HashSet();
        }

        b(Set<Integer> set, int i, a aVar, C0076b c0076b, int i2) {
            this.UJ = set;
            this.xH = i;
            this.Wd = aVar;
            this.We = c0076b;
            this.Wf = i2;
        }

        @Override // com.google.android.gms.internal.ga
        protected boolean a(ga.a aVar) {
            return this.UJ.contains(Integer.valueOf(aVar.ff()));
        }

        @Override // com.google.android.gms.internal.ga
        protected Object aq(String str) {
            return null;
        }

        @Override // com.google.android.gms.internal.ga
        protected boolean ar(String str) {
            return false;
        }

        @Override // com.google.android.gms.internal.ga
        protected Object b(ga.a aVar) {
            switch (aVar.ff()) {
                case 2:
                    return this.Wd;
                case 3:
                    return this.We;
                case 4:
                    return Integer.valueOf(this.Wf);
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + aVar.ff());
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            ik ikVar = CREATOR;
            return 0;
        }

        @Override // com.google.android.gms.internal.ga
        public HashMap<String, ga.a<?, ?>> eY() {
            return UI;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof b)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            b bVar = (b) obj;
            for (ga.a<?, ?> aVar : UI.values()) {
                if (a(aVar)) {
                    if (bVar.a(aVar) && b(aVar).equals(bVar.b(aVar))) {
                    }
                    return false;
                }
                if (bVar.a(aVar)) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Cover
        public Person.Cover.CoverInfo getCoverInfo() {
            return this.Wd;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Cover
        public Person.Cover.CoverPhoto getCoverPhoto() {
            return this.We;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Cover
        public int getLayout() {
            return this.Wf;
        }

        int getVersionCode() {
            return this.xH;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Cover
        public boolean hasCoverInfo() {
            return this.UJ.contains(2);
        }

        @Override // com.google.android.gms.plus.model.people.Person.Cover
        public boolean hasCoverPhoto() {
            return this.UJ.contains(3);
        }

        @Override // com.google.android.gms.plus.model.people.Person.Cover
        public boolean hasLayout() {
            return this.UJ.contains(4);
        }

        public int hashCode() {
            int iHashCode = 0;
            Iterator<ga.a<?, ?>> it = UI.values().iterator();
            while (true) {
                int i = iHashCode;
                if (!it.hasNext()) {
                    return i;
                }
                ga.a<?, ?> next = it.next();
                if (a(next)) {
                    iHashCode = b(next).hashCode() + i + next.ff();
                } else {
                    iHashCode = i;
                }
            }
        }

        @Override // com.google.android.gms.common.data.Freezable
        public boolean isDataValid() {
            return true;
        }

        a jE() {
            return this.Wd;
        }

        C0076b jF() {
            return this.We;
        }

        @Override // com.google.android.gms.common.data.Freezable
        /* JADX INFO: renamed from: jG, reason: merged with bridge method [inline-methods] */
        public b freeze() {
            return this;
        }

        Set<Integer> ja() {
            return this.UJ;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            ik ikVar = CREATOR;
            ik.a(this, out, flags);
        }
    }

    public static final class c extends ga implements SafeParcelable, Person.Image {
        public static final in CREATOR = new in();
        private static final HashMap<String, ga.a<?, ?>> UI = new HashMap<>();
        private final Set<Integer> UJ;
        private String ro;
        private final int xH;

        static {
            UI.put("url", ga.a.j("url", 2));
        }

        public c() {
            this.xH = 1;
            this.UJ = new HashSet();
        }

        public c(String str) {
            this.UJ = new HashSet();
            this.xH = 1;
            this.ro = str;
            this.UJ.add(2);
        }

        c(Set<Integer> set, int i, String str) {
            this.UJ = set;
            this.xH = i;
            this.ro = str;
        }

        @Override // com.google.android.gms.internal.ga
        protected boolean a(ga.a aVar) {
            return this.UJ.contains(Integer.valueOf(aVar.ff()));
        }

        @Override // com.google.android.gms.internal.ga
        protected Object aq(String str) {
            return null;
        }

        @Override // com.google.android.gms.internal.ga
        protected boolean ar(String str) {
            return false;
        }

        @Override // com.google.android.gms.internal.ga
        protected Object b(ga.a aVar) {
            switch (aVar.ff()) {
                case 2:
                    return this.ro;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + aVar.ff());
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            in inVar = CREATOR;
            return 0;
        }

        @Override // com.google.android.gms.internal.ga
        public HashMap<String, ga.a<?, ?>> eY() {
            return UI;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof c)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            c cVar = (c) obj;
            for (ga.a<?, ?> aVar : UI.values()) {
                if (a(aVar)) {
                    if (cVar.a(aVar) && b(aVar).equals(cVar.b(aVar))) {
                    }
                    return false;
                }
                if (cVar.a(aVar)) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Image
        public String getUrl() {
            return this.ro;
        }

        int getVersionCode() {
            return this.xH;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Image
        public boolean hasUrl() {
            return this.UJ.contains(2);
        }

        public int hashCode() {
            int iHashCode = 0;
            Iterator<ga.a<?, ?>> it = UI.values().iterator();
            while (true) {
                int i = iHashCode;
                if (!it.hasNext()) {
                    return i;
                }
                ga.a<?, ?> next = it.next();
                if (a(next)) {
                    iHashCode = b(next).hashCode() + i + next.ff();
                } else {
                    iHashCode = i;
                }
            }
        }

        @Override // com.google.android.gms.common.data.Freezable
        public boolean isDataValid() {
            return true;
        }

        @Override // com.google.android.gms.common.data.Freezable
        /* JADX INFO: renamed from: jJ, reason: merged with bridge method [inline-methods] */
        public c freeze() {
            return this;
        }

        Set<Integer> ja() {
            return this.UJ;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            in inVar = CREATOR;
            in.a(this, out, flags);
        }
    }

    public static final class d extends ga implements SafeParcelable, Person.Name {
        public static final io CREATOR = new io();
        private static final HashMap<String, ga.a<?, ?>> UI = new HashMap<>();
        private final Set<Integer> UJ;
        private String Vh;
        private String Vk;
        private String Wi;
        private String Wj;
        private String Wk;
        private String Wl;
        private final int xH;

        static {
            UI.put("familyName", ga.a.j("familyName", 2));
            UI.put("formatted", ga.a.j("formatted", 3));
            UI.put("givenName", ga.a.j("givenName", 4));
            UI.put("honorificPrefix", ga.a.j("honorificPrefix", 5));
            UI.put("honorificSuffix", ga.a.j("honorificSuffix", 6));
            UI.put("middleName", ga.a.j("middleName", 7));
        }

        public d() {
            this.xH = 1;
            this.UJ = new HashSet();
        }

        d(Set<Integer> set, int i, String str, String str2, String str3, String str4, String str5, String str6) {
            this.UJ = set;
            this.xH = i;
            this.Vh = str;
            this.Wi = str2;
            this.Vk = str3;
            this.Wj = str4;
            this.Wk = str5;
            this.Wl = str6;
        }

        @Override // com.google.android.gms.internal.ga
        protected boolean a(ga.a aVar) {
            return this.UJ.contains(Integer.valueOf(aVar.ff()));
        }

        @Override // com.google.android.gms.internal.ga
        protected Object aq(String str) {
            return null;
        }

        @Override // com.google.android.gms.internal.ga
        protected boolean ar(String str) {
            return false;
        }

        @Override // com.google.android.gms.internal.ga
        protected Object b(ga.a aVar) {
            switch (aVar.ff()) {
                case 2:
                    return this.Vh;
                case 3:
                    return this.Wi;
                case 4:
                    return this.Vk;
                case 5:
                    return this.Wj;
                case 6:
                    return this.Wk;
                case 7:
                    return this.Wl;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + aVar.ff());
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            io ioVar = CREATOR;
            return 0;
        }

        @Override // com.google.android.gms.internal.ga
        public HashMap<String, ga.a<?, ?>> eY() {
            return UI;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof d)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            d dVar = (d) obj;
            for (ga.a<?, ?> aVar : UI.values()) {
                if (a(aVar)) {
                    if (dVar.a(aVar) && b(aVar).equals(dVar.b(aVar))) {
                    }
                    return false;
                }
                if (dVar.a(aVar)) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Name
        public String getFamilyName() {
            return this.Vh;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Name
        public String getFormatted() {
            return this.Wi;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Name
        public String getGivenName() {
            return this.Vk;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Name
        public String getHonorificPrefix() {
            return this.Wj;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Name
        public String getHonorificSuffix() {
            return this.Wk;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Name
        public String getMiddleName() {
            return this.Wl;
        }

        int getVersionCode() {
            return this.xH;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Name
        public boolean hasFamilyName() {
            return this.UJ.contains(2);
        }

        @Override // com.google.android.gms.plus.model.people.Person.Name
        public boolean hasFormatted() {
            return this.UJ.contains(3);
        }

        @Override // com.google.android.gms.plus.model.people.Person.Name
        public boolean hasGivenName() {
            return this.UJ.contains(4);
        }

        @Override // com.google.android.gms.plus.model.people.Person.Name
        public boolean hasHonorificPrefix() {
            return this.UJ.contains(5);
        }

        @Override // com.google.android.gms.plus.model.people.Person.Name
        public boolean hasHonorificSuffix() {
            return this.UJ.contains(6);
        }

        @Override // com.google.android.gms.plus.model.people.Person.Name
        public boolean hasMiddleName() {
            return this.UJ.contains(7);
        }

        public int hashCode() {
            int iHashCode = 0;
            Iterator<ga.a<?, ?>> it = UI.values().iterator();
            while (true) {
                int i = iHashCode;
                if (!it.hasNext()) {
                    return i;
                }
                ga.a<?, ?> next = it.next();
                if (a(next)) {
                    iHashCode = b(next).hashCode() + i + next.ff();
                } else {
                    iHashCode = i;
                }
            }
        }

        @Override // com.google.android.gms.common.data.Freezable
        public boolean isDataValid() {
            return true;
        }

        @Override // com.google.android.gms.common.data.Freezable
        /* JADX INFO: renamed from: jK, reason: merged with bridge method [inline-methods] */
        public d freeze() {
            return this;
        }

        Set<Integer> ja() {
            return this.UJ;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            io ioVar = CREATOR;
            io.a(this, out, flags);
        }
    }

    public static class e {
        public static int bi(String str) {
            if (str.equals("person")) {
                return 0;
            }
            if (str.equals("page")) {
                return 1;
            }
            throw new IllegalArgumentException("Unknown objectType string: " + str);
        }
    }

    public static final class f extends ga implements SafeParcelable, Person.Organizations {
        public static final ip CREATOR = new ip();
        private static final HashMap<String, ga.a<?, ?>> UI = new HashMap<>();
        private String EB;
        private String HD;
        private int LF;
        private final Set<Integer> UJ;
        private String Vg;
        private String Vw;
        private String Wm;
        private String Wn;
        private boolean Wo;
        private String mName;
        private final int xH;

        static {
            UI.put("department", ga.a.j("department", 2));
            UI.put(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, ga.a.j(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, 3));
            UI.put("endDate", ga.a.j("endDate", 4));
            UI.put("location", ga.a.j("location", 5));
            UI.put("name", ga.a.j("name", 6));
            UI.put("primary", ga.a.i("primary", 7));
            UI.put("startDate", ga.a.j("startDate", 8));
            UI.put("title", ga.a.j("title", 9));
            UI.put("type", ga.a.a("type", 10, new fx().f("work", 0).f("school", 1), false));
        }

        public f() {
            this.xH = 1;
            this.UJ = new HashSet();
        }

        f(Set<Integer> set, int i, String str, String str2, String str3, String str4, String str5, boolean z, String str6, String str7, int i2) {
            this.UJ = set;
            this.xH = i;
            this.Wm = str;
            this.HD = str2;
            this.Vg = str3;
            this.Wn = str4;
            this.mName = str5;
            this.Wo = z;
            this.Vw = str6;
            this.EB = str7;
            this.LF = i2;
        }

        @Override // com.google.android.gms.internal.ga
        protected boolean a(ga.a aVar) {
            return this.UJ.contains(Integer.valueOf(aVar.ff()));
        }

        @Override // com.google.android.gms.internal.ga
        protected Object aq(String str) {
            return null;
        }

        @Override // com.google.android.gms.internal.ga
        protected boolean ar(String str) {
            return false;
        }

        @Override // com.google.android.gms.internal.ga
        protected Object b(ga.a aVar) {
            switch (aVar.ff()) {
                case 2:
                    return this.Wm;
                case 3:
                    return this.HD;
                case 4:
                    return this.Vg;
                case 5:
                    return this.Wn;
                case 6:
                    return this.mName;
                case 7:
                    return Boolean.valueOf(this.Wo);
                case 8:
                    return this.Vw;
                case 9:
                    return this.EB;
                case 10:
                    return Integer.valueOf(this.LF);
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + aVar.ff());
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            ip ipVar = CREATOR;
            return 0;
        }

        @Override // com.google.android.gms.internal.ga
        public HashMap<String, ga.a<?, ?>> eY() {
            return UI;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof f)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            f fVar = (f) obj;
            for (ga.a<?, ?> aVar : UI.values()) {
                if (a(aVar)) {
                    if (fVar.a(aVar) && b(aVar).equals(fVar.b(aVar))) {
                    }
                    return false;
                }
                if (fVar.a(aVar)) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Organizations
        public String getDepartment() {
            return this.Wm;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Organizations
        public String getDescription() {
            return this.HD;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Organizations
        public String getEndDate() {
            return this.Vg;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Organizations
        public String getLocation() {
            return this.Wn;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Organizations
        public String getName() {
            return this.mName;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Organizations
        public String getStartDate() {
            return this.Vw;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Organizations
        public String getTitle() {
            return this.EB;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Organizations
        public int getType() {
            return this.LF;
        }

        int getVersionCode() {
            return this.xH;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Organizations
        public boolean hasDepartment() {
            return this.UJ.contains(2);
        }

        @Override // com.google.android.gms.plus.model.people.Person.Organizations
        public boolean hasDescription() {
            return this.UJ.contains(3);
        }

        @Override // com.google.android.gms.plus.model.people.Person.Organizations
        public boolean hasEndDate() {
            return this.UJ.contains(4);
        }

        @Override // com.google.android.gms.plus.model.people.Person.Organizations
        public boolean hasLocation() {
            return this.UJ.contains(5);
        }

        @Override // com.google.android.gms.plus.model.people.Person.Organizations
        public boolean hasName() {
            return this.UJ.contains(6);
        }

        @Override // com.google.android.gms.plus.model.people.Person.Organizations
        public boolean hasPrimary() {
            return this.UJ.contains(7);
        }

        @Override // com.google.android.gms.plus.model.people.Person.Organizations
        public boolean hasStartDate() {
            return this.UJ.contains(8);
        }

        @Override // com.google.android.gms.plus.model.people.Person.Organizations
        public boolean hasTitle() {
            return this.UJ.contains(9);
        }

        @Override // com.google.android.gms.plus.model.people.Person.Organizations
        public boolean hasType() {
            return this.UJ.contains(10);
        }

        public int hashCode() {
            int iHashCode = 0;
            Iterator<ga.a<?, ?>> it = UI.values().iterator();
            while (true) {
                int i = iHashCode;
                if (!it.hasNext()) {
                    return i;
                }
                ga.a<?, ?> next = it.next();
                if (a(next)) {
                    iHashCode = b(next).hashCode() + i + next.ff();
                } else {
                    iHashCode = i;
                }
            }
        }

        @Override // com.google.android.gms.common.data.Freezable
        public boolean isDataValid() {
            return true;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Organizations
        public boolean isPrimary() {
            return this.Wo;
        }

        @Override // com.google.android.gms.common.data.Freezable
        /* JADX INFO: renamed from: jL, reason: merged with bridge method [inline-methods] */
        public f freeze() {
            return this;
        }

        Set<Integer> ja() {
            return this.UJ;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            ip ipVar = CREATOR;
            ip.a(this, out, flags);
        }
    }

    public static final class g extends ga implements SafeParcelable, Person.PlacesLived {
        public static final iq CREATOR = new iq();
        private static final HashMap<String, ga.a<?, ?>> UI = new HashMap<>();
        private final Set<Integer> UJ;
        private boolean Wo;
        private String mValue;
        private final int xH;

        static {
            UI.put("primary", ga.a.i("primary", 2));
            UI.put(TMXConstants.TAG_PROPERTY_ATTRIBUTE_VALUE, ga.a.j(TMXConstants.TAG_PROPERTY_ATTRIBUTE_VALUE, 3));
        }

        public g() {
            this.xH = 1;
            this.UJ = new HashSet();
        }

        g(Set<Integer> set, int i, boolean z, String str) {
            this.UJ = set;
            this.xH = i;
            this.Wo = z;
            this.mValue = str;
        }

        @Override // com.google.android.gms.internal.ga
        protected boolean a(ga.a aVar) {
            return this.UJ.contains(Integer.valueOf(aVar.ff()));
        }

        @Override // com.google.android.gms.internal.ga
        protected Object aq(String str) {
            return null;
        }

        @Override // com.google.android.gms.internal.ga
        protected boolean ar(String str) {
            return false;
        }

        @Override // com.google.android.gms.internal.ga
        protected Object b(ga.a aVar) {
            switch (aVar.ff()) {
                case 2:
                    return Boolean.valueOf(this.Wo);
                case 3:
                    return this.mValue;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + aVar.ff());
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            iq iqVar = CREATOR;
            return 0;
        }

        @Override // com.google.android.gms.internal.ga
        public HashMap<String, ga.a<?, ?>> eY() {
            return UI;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof g)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            g gVar = (g) obj;
            for (ga.a<?, ?> aVar : UI.values()) {
                if (a(aVar)) {
                    if (gVar.a(aVar) && b(aVar).equals(gVar.b(aVar))) {
                    }
                    return false;
                }
                if (gVar.a(aVar)) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.android.gms.plus.model.people.Person.PlacesLived
        public String getValue() {
            return this.mValue;
        }

        int getVersionCode() {
            return this.xH;
        }

        @Override // com.google.android.gms.plus.model.people.Person.PlacesLived
        public boolean hasPrimary() {
            return this.UJ.contains(2);
        }

        @Override // com.google.android.gms.plus.model.people.Person.PlacesLived
        public boolean hasValue() {
            return this.UJ.contains(3);
        }

        public int hashCode() {
            int iHashCode = 0;
            Iterator<ga.a<?, ?>> it = UI.values().iterator();
            while (true) {
                int i = iHashCode;
                if (!it.hasNext()) {
                    return i;
                }
                ga.a<?, ?> next = it.next();
                if (a(next)) {
                    iHashCode = b(next).hashCode() + i + next.ff();
                } else {
                    iHashCode = i;
                }
            }
        }

        @Override // com.google.android.gms.common.data.Freezable
        public boolean isDataValid() {
            return true;
        }

        @Override // com.google.android.gms.plus.model.people.Person.PlacesLived
        public boolean isPrimary() {
            return this.Wo;
        }

        @Override // com.google.android.gms.common.data.Freezable
        /* JADX INFO: renamed from: jM, reason: merged with bridge method [inline-methods] */
        public g freeze() {
            return this;
        }

        Set<Integer> ja() {
            return this.UJ;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            iq iqVar = CREATOR;
            iq.a(this, out, flags);
        }
    }

    public static final class h extends ga implements SafeParcelable, Person.Urls {
        public static final ir CREATOR = new ir();
        private static final HashMap<String, ga.a<?, ?>> UI = new HashMap<>();
        private int LF;
        private final Set<Integer> UJ;
        private String Wp;
        private final int Wq;
        private String mValue;
        private final int xH;

        static {
            UI.put(PlusShare.KEY_CALL_TO_ACTION_LABEL, ga.a.j(PlusShare.KEY_CALL_TO_ACTION_LABEL, 5));
            UI.put("type", ga.a.a("type", 6, new fx().f("home", 0).f("work", 1).f("blog", 2).f(Scopes.PROFILE, 3).f("other", 4).f("otherProfile", 5).f("contributor", 6).f("website", 7), false));
            UI.put(TMXConstants.TAG_PROPERTY_ATTRIBUTE_VALUE, ga.a.j(TMXConstants.TAG_PROPERTY_ATTRIBUTE_VALUE, 4));
        }

        public h() {
            this.Wq = 4;
            this.xH = 2;
            this.UJ = new HashSet();
        }

        h(Set<Integer> set, int i, String str, int i2, String str2, int i3) {
            this.Wq = 4;
            this.UJ = set;
            this.xH = i;
            this.Wp = str;
            this.LF = i2;
            this.mValue = str2;
        }

        @Override // com.google.android.gms.internal.ga
        protected boolean a(ga.a aVar) {
            return this.UJ.contains(Integer.valueOf(aVar.ff()));
        }

        @Override // com.google.android.gms.internal.ga
        protected Object aq(String str) {
            return null;
        }

        @Override // com.google.android.gms.internal.ga
        protected boolean ar(String str) {
            return false;
        }

        @Override // com.google.android.gms.internal.ga
        protected Object b(ga.a aVar) {
            switch (aVar.ff()) {
                case 4:
                    return this.mValue;
                case 5:
                    return this.Wp;
                case 6:
                    return Integer.valueOf(this.LF);
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + aVar.ff());
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            ir irVar = CREATOR;
            return 0;
        }

        @Override // com.google.android.gms.internal.ga
        public HashMap<String, ga.a<?, ?>> eY() {
            return UI;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof h)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            h hVar = (h) obj;
            for (ga.a<?, ?> aVar : UI.values()) {
                if (a(aVar)) {
                    if (hVar.a(aVar) && b(aVar).equals(hVar.b(aVar))) {
                    }
                    return false;
                }
                if (hVar.a(aVar)) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Urls
        public String getLabel() {
            return this.Wp;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Urls
        public int getType() {
            return this.LF;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Urls
        public String getValue() {
            return this.mValue;
        }

        int getVersionCode() {
            return this.xH;
        }

        @Override // com.google.android.gms.plus.model.people.Person.Urls
        public boolean hasLabel() {
            return this.UJ.contains(5);
        }

        @Override // com.google.android.gms.plus.model.people.Person.Urls
        public boolean hasType() {
            return this.UJ.contains(6);
        }

        @Override // com.google.android.gms.plus.model.people.Person.Urls
        public boolean hasValue() {
            return this.UJ.contains(4);
        }

        public int hashCode() {
            int iHashCode = 0;
            Iterator<ga.a<?, ?>> it = UI.values().iterator();
            while (true) {
                int i = iHashCode;
                if (!it.hasNext()) {
                    return i;
                }
                ga.a<?, ?> next = it.next();
                if (a(next)) {
                    iHashCode = b(next).hashCode() + i + next.ff();
                } else {
                    iHashCode = i;
                }
            }
        }

        @Override // com.google.android.gms.common.data.Freezable
        public boolean isDataValid() {
            return true;
        }

        @Deprecated
        public int jN() {
            return 4;
        }

        @Override // com.google.android.gms.common.data.Freezable
        /* JADX INFO: renamed from: jO, reason: merged with bridge method [inline-methods] */
        public h freeze() {
            return this;
        }

        Set<Integer> ja() {
            return this.UJ;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            ir irVar = CREATOR;
            ir.a(this, out, flags);
        }
    }

    static {
        UI.put("aboutMe", ga.a.j("aboutMe", 2));
        UI.put("ageRange", ga.a.a("ageRange", 3, a.class));
        UI.put("birthday", ga.a.j("birthday", 4));
        UI.put("braggingRights", ga.a.j("braggingRights", 5));
        UI.put("circledByCount", ga.a.g("circledByCount", 6));
        UI.put("cover", ga.a.a("cover", 7, b.class));
        UI.put("currentLocation", ga.a.j("currentLocation", 8));
        UI.put("displayName", ga.a.j("displayName", 9));
        UI.put("gender", ga.a.a("gender", 12, new fx().f(AdColonyUserMetadata.USER_MALE, 0).f(AdColonyUserMetadata.USER_FEMALE, 1).f("other", 2), false));
        UI.put(TMXConstants.TAG_TILE_ATTRIBUTE_ID, ga.a.j(TMXConstants.TAG_TILE_ATTRIBUTE_ID, 14));
        UI.put(TMXConstants.TAG_IMAGE, ga.a.a(TMXConstants.TAG_IMAGE, 15, c.class));
        UI.put("isPlusUser", ga.a.i("isPlusUser", 16));
        UI.put("language", ga.a.j("language", 18));
        UI.put("name", ga.a.a("name", 19, d.class));
        UI.put("nickname", ga.a.j("nickname", 20));
        UI.put("objectType", ga.a.a("objectType", 21, new fx().f("person", 0).f("page", 1), false));
        UI.put("organizations", ga.a.b("organizations", 22, f.class));
        UI.put("placesLived", ga.a.b("placesLived", 23, g.class));
        UI.put("plusOneCount", ga.a.g("plusOneCount", 24));
        UI.put("relationshipStatus", ga.a.a("relationshipStatus", 25, new fx().f(AdColonyUserMetadata.USER_SINGLE, 0).f("in_a_relationship", 1).f("engaged", 2).f(AdColonyUserMetadata.USER_MARRIED, 3).f("its_complicated", 4).f("open_relationship", 5).f("widowed", 6).f("in_domestic_partnership", 7).f("in_civil_union", 8), false));
        UI.put("tagline", ga.a.j("tagline", 26));
        UI.put("url", ga.a.j("url", 27));
        UI.put("urls", ga.a.b("urls", 28, h.class));
        UI.put("verified", ga.a.i("verified", 29));
    }

    public ih() {
        this.xH = 2;
        this.UJ = new HashSet();
    }

    public ih(String str, String str2, c cVar, int i, String str3) {
        this.xH = 2;
        this.UJ = new HashSet();
        this.HA = str;
        this.UJ.add(9);
        this.wp = str2;
        this.UJ.add(14);
        this.VO = cVar;
        this.UJ.add(15);
        this.VT = i;
        this.UJ.add(21);
        this.ro = str3;
        this.UJ.add(27);
    }

    ih(Set<Integer> set, int i, String str, a aVar, String str2, String str3, int i2, b bVar, String str4, String str5, int i3, String str6, c cVar, boolean z, String str7, d dVar, String str8, int i4, List<f> list, List<g> list2, int i5, int i6, String str9, String str10, List<h> list3, boolean z2) {
        this.UJ = set;
        this.xH = i;
        this.VH = str;
        this.VI = aVar;
        this.VJ = str2;
        this.VK = str3;
        this.VL = i2;
        this.VM = bVar;
        this.VN = str4;
        this.HA = str5;
        this.lZ = i3;
        this.wp = str6;
        this.VO = cVar;
        this.VP = z;
        this.VQ = str7;
        this.VR = dVar;
        this.VS = str8;
        this.VT = i4;
        this.VU = list;
        this.VV = list2;
        this.VW = i5;
        this.VX = i6;
        this.VY = str9;
        this.ro = str10;
        this.VZ = list3;
        this.Wa = z2;
    }

    public static ih i(byte[] bArr) {
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.unmarshall(bArr, 0, bArr.length);
        parcelObtain.setDataPosition(0);
        ih ihVarCreateFromParcel = CREATOR.createFromParcel(parcelObtain);
        parcelObtain.recycle();
        return ihVarCreateFromParcel;
    }

    @Override // com.google.android.gms.internal.ga
    protected boolean a(ga.a aVar) {
        return this.UJ.contains(Integer.valueOf(aVar.ff()));
    }

    @Override // com.google.android.gms.internal.ga
    protected Object aq(String str) {
        return null;
    }

    @Override // com.google.android.gms.internal.ga
    protected boolean ar(String str) {
        return false;
    }

    @Override // com.google.android.gms.internal.ga
    protected Object b(ga.a aVar) {
        switch (aVar.ff()) {
            case 2:
                return this.VH;
            case 3:
                return this.VI;
            case 4:
                return this.VJ;
            case 5:
                return this.VK;
            case 6:
                return Integer.valueOf(this.VL);
            case 7:
                return this.VM;
            case 8:
                return this.VN;
            case 9:
                return this.HA;
            case 10:
            case 11:
            case 13:
            case 17:
            default:
                throw new IllegalStateException("Unknown safe parcelable id=" + aVar.ff());
            case 12:
                return Integer.valueOf(this.lZ);
            case 14:
                return this.wp;
            case 15:
                return this.VO;
            case 16:
                return Boolean.valueOf(this.VP);
            case 18:
                return this.VQ;
            case 19:
                return this.VR;
            case TapjoyConstants.MEDIATION_TIMEOUT_INTERVAL /* 20 */:
                return this.VS;
            case 21:
                return Integer.valueOf(this.VT);
            case 22:
                return this.VU;
            case 23:
                return this.VV;
            case TimeConstants.HOURSPERDAY /* 24 */:
                return Integer.valueOf(this.VW);
            case 25:
                return Integer.valueOf(this.VX);
            case 26:
                return this.VY;
            case 27:
                return this.ro;
            case 28:
                return this.VZ;
            case 29:
                return Boolean.valueOf(this.Wa);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        ii iiVar = CREATOR;
        return 0;
    }

    @Override // com.google.android.gms.internal.ga
    public HashMap<String, ga.a<?, ?>> eY() {
        return UI;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ih)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ih ihVar = (ih) obj;
        for (ga.a<?, ?> aVar : UI.values()) {
            if (a(aVar)) {
                if (ihVar.a(aVar) && b(aVar).equals(ihVar.b(aVar))) {
                }
                return false;
            }
            if (ihVar.a(aVar)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public String getAboutMe() {
        return this.VH;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public Person.AgeRange getAgeRange() {
        return this.VI;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public String getBirthday() {
        return this.VJ;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public String getBraggingRights() {
        return this.VK;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public int getCircledByCount() {
        return this.VL;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public Person.Cover getCover() {
        return this.VM;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public String getCurrentLocation() {
        return this.VN;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public String getDisplayName() {
        return this.HA;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public int getGender() {
        return this.lZ;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public String getId() {
        return this.wp;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public Person.Image getImage() {
        return this.VO;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public String getLanguage() {
        return this.VQ;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public Person.Name getName() {
        return this.VR;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public String getNickname() {
        return this.VS;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public int getObjectType() {
        return this.VT;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public List<Person.Organizations> getOrganizations() {
        return (ArrayList) this.VU;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public List<Person.PlacesLived> getPlacesLived() {
        return (ArrayList) this.VV;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public int getPlusOneCount() {
        return this.VW;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public int getRelationshipStatus() {
        return this.VX;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public String getTagline() {
        return this.VY;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public String getUrl() {
        return this.ro;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public List<Person.Urls> getUrls() {
        return (ArrayList) this.VZ;
    }

    int getVersionCode() {
        return this.xH;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasAboutMe() {
        return this.UJ.contains(2);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasAgeRange() {
        return this.UJ.contains(3);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasBirthday() {
        return this.UJ.contains(4);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasBraggingRights() {
        return this.UJ.contains(5);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasCircledByCount() {
        return this.UJ.contains(6);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasCover() {
        return this.UJ.contains(7);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasCurrentLocation() {
        return this.UJ.contains(8);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasDisplayName() {
        return this.UJ.contains(9);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasGender() {
        return this.UJ.contains(12);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasId() {
        return this.UJ.contains(14);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasImage() {
        return this.UJ.contains(15);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasIsPlusUser() {
        return this.UJ.contains(16);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasLanguage() {
        return this.UJ.contains(18);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasName() {
        return this.UJ.contains(19);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasNickname() {
        return this.UJ.contains(20);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasObjectType() {
        return this.UJ.contains(21);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasOrganizations() {
        return this.UJ.contains(22);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasPlacesLived() {
        return this.UJ.contains(23);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasPlusOneCount() {
        return this.UJ.contains(24);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasRelationshipStatus() {
        return this.UJ.contains(25);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasTagline() {
        return this.UJ.contains(26);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasUrl() {
        return this.UJ.contains(27);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasUrls() {
        return this.UJ.contains(28);
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean hasVerified() {
        return this.UJ.contains(29);
    }

    public int hashCode() {
        int iHashCode = 0;
        Iterator<ga.a<?, ?>> it = UI.values().iterator();
        while (true) {
            int i = iHashCode;
            if (!it.hasNext()) {
                return i;
            }
            ga.a<?, ?> next = it.next();
            if (a(next)) {
                iHashCode = b(next).hashCode() + i + next.ff();
            } else {
                iHashCode = i;
            }
        }
    }

    @Override // com.google.android.gms.common.data.Freezable
    public boolean isDataValid() {
        return true;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean isPlusUser() {
        return this.VP;
    }

    @Override // com.google.android.gms.plus.model.people.Person
    public boolean isVerified() {
        return this.Wa;
    }

    List<g> jA() {
        return this.VV;
    }

    List<h> jB() {
        return this.VZ;
    }

    @Override // com.google.android.gms.common.data.Freezable
    /* JADX INFO: renamed from: jC, reason: merged with bridge method [inline-methods] */
    public ih freeze() {
        return this;
    }

    Set<Integer> ja() {
        return this.UJ;
    }

    a jv() {
        return this.VI;
    }

    b jw() {
        return this.VM;
    }

    c jx() {
        return this.VO;
    }

    d jy() {
        return this.VR;
    }

    List<f> jz() {
        return this.VU;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        ii iiVar = CREATOR;
        ii.a(this, out, flags);
    }
}
