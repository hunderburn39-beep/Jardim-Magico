package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.plus.model.moments.ItemScope;
import com.google.android.gms.plus.model.moments.Moment;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;

/* JADX INFO: loaded from: classes.dex */
public final class ie extends ga implements SafeParcelable, Moment {
    public static final Cif CREATOR = new Cif();
    private static final HashMap<String, ga.a<?, ?>> UI = new HashMap<>();
    private String Rd;
    private final Set<Integer> UJ;
    private ic VE;
    private ic VF;
    private String Vw;
    private String wp;
    private final int xH;

    static {
        UI.put(TMXConstants.TAG_TILE_ATTRIBUTE_ID, ga.a.j(TMXConstants.TAG_TILE_ATTRIBUTE_ID, 2));
        UI.put("result", ga.a.a("result", 4, ic.class));
        UI.put("startDate", ga.a.j("startDate", 5));
        UI.put("target", ga.a.a("target", 6, ic.class));
        UI.put("type", ga.a.j("type", 7));
    }

    public ie() {
        this.xH = 1;
        this.UJ = new HashSet();
    }

    ie(Set<Integer> set, int i, String str, ic icVar, String str2, ic icVar2, String str3) {
        this.UJ = set;
        this.xH = i;
        this.wp = str;
        this.VE = icVar;
        this.Vw = str2;
        this.VF = icVar2;
        this.Rd = str3;
    }

    public ie(Set<Integer> set, String str, ic icVar, String str2, ic icVar2, String str3) {
        this.UJ = set;
        this.xH = 1;
        this.wp = str;
        this.VE = icVar;
        this.Vw = str2;
        this.VF = icVar2;
        this.Rd = str3;
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
                return this.wp;
            case 3:
            default:
                throw new IllegalStateException("Unknown safe parcelable id=" + aVar.ff());
            case 4:
                return this.VE;
            case 5:
                return this.Vw;
            case 6:
                return this.VF;
            case 7:
                return this.Rd;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        Cif cif = CREATOR;
        return 0;
    }

    @Override // com.google.android.gms.internal.ga
    public HashMap<String, ga.a<?, ?>> eY() {
        return UI;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ie)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ie ieVar = (ie) obj;
        for (ga.a<?, ?> aVar : UI.values()) {
            if (a(aVar)) {
                if (ieVar.a(aVar) && b(aVar).equals(ieVar.b(aVar))) {
                }
                return false;
            }
            if (ieVar.a(aVar)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.google.android.gms.plus.model.moments.Moment
    public String getId() {
        return this.wp;
    }

    @Override // com.google.android.gms.plus.model.moments.Moment
    public ItemScope getResult() {
        return this.VE;
    }

    @Override // com.google.android.gms.plus.model.moments.Moment
    public String getStartDate() {
        return this.Vw;
    }

    @Override // com.google.android.gms.plus.model.moments.Moment
    public ItemScope getTarget() {
        return this.VF;
    }

    @Override // com.google.android.gms.plus.model.moments.Moment
    public String getType() {
        return this.Rd;
    }

    int getVersionCode() {
        return this.xH;
    }

    @Override // com.google.android.gms.plus.model.moments.Moment
    public boolean hasId() {
        return this.UJ.contains(2);
    }

    @Override // com.google.android.gms.plus.model.moments.Moment
    public boolean hasResult() {
        return this.UJ.contains(4);
    }

    @Override // com.google.android.gms.plus.model.moments.Moment
    public boolean hasStartDate() {
        return this.UJ.contains(5);
    }

    @Override // com.google.android.gms.plus.model.moments.Moment
    public boolean hasTarget() {
        return this.UJ.contains(6);
    }

    @Override // com.google.android.gms.plus.model.moments.Moment
    public boolean hasType() {
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

    Set<Integer> ja() {
        return this.UJ;
    }

    ic jr() {
        return this.VE;
    }

    ic js() {
        return this.VF;
    }

    @Override // com.google.android.gms.common.data.Freezable
    /* JADX INFO: renamed from: jt, reason: merged with bridge method [inline-methods] */
    public ie freeze() {
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        Cif cif = CREATOR;
        Cif.a(this, out, flags);
    }
}
