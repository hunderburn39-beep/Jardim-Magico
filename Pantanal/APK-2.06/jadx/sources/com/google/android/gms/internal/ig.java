package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.plus.model.moments.ItemScope;
import com.google.android.gms.plus.model.moments.Moment;

/* JADX INFO: loaded from: classes.dex */
public final class ig extends com.google.android.gms.common.data.b implements Moment {
    private ie VG;

    public ig(DataHolder dataHolder, int i) {
        super(dataHolder, i);
    }

    private ie ju() {
        synchronized (this) {
            if (this.VG == null) {
                byte[] byteArray = getByteArray("momentImpl");
                Parcel parcelObtain = Parcel.obtain();
                parcelObtain.unmarshall(byteArray, 0, byteArray.length);
                parcelObtain.setDataPosition(0);
                this.VG = ie.CREATOR.createFromParcel(parcelObtain);
                parcelObtain.recycle();
            }
        }
        return this.VG;
    }

    @Override // com.google.android.gms.plus.model.moments.Moment
    public String getId() {
        return ju().getId();
    }

    @Override // com.google.android.gms.plus.model.moments.Moment
    public ItemScope getResult() {
        return ju().getResult();
    }

    @Override // com.google.android.gms.plus.model.moments.Moment
    public String getStartDate() {
        return ju().getStartDate();
    }

    @Override // com.google.android.gms.plus.model.moments.Moment
    public ItemScope getTarget() {
        return ju().getTarget();
    }

    @Override // com.google.android.gms.plus.model.moments.Moment
    public String getType() {
        return ju().getType();
    }

    @Override // com.google.android.gms.plus.model.moments.Moment
    public boolean hasId() {
        return ju().hasId();
    }

    @Override // com.google.android.gms.plus.model.moments.Moment
    public boolean hasResult() {
        return ju().hasId();
    }

    @Override // com.google.android.gms.plus.model.moments.Moment
    public boolean hasStartDate() {
        return ju().hasStartDate();
    }

    @Override // com.google.android.gms.plus.model.moments.Moment
    public boolean hasTarget() {
        return ju().hasTarget();
    }

    @Override // com.google.android.gms.plus.model.moments.Moment
    public boolean hasType() {
        return ju().hasType();
    }

    @Override // com.google.android.gms.common.data.Freezable
    /* JADX INFO: renamed from: jt, reason: merged with bridge method [inline-methods] */
    public ie freeze() {
        return ju();
    }
}
