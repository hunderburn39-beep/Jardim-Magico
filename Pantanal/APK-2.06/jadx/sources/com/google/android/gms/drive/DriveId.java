package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.internal.y;
import com.google.android.gms.internal.fq;
import com.google.android.gms.internal.ks;
import com.google.android.gms.internal.kt;

/* JADX INFO: loaded from: classes.dex */
public class DriveId implements SafeParcelable {
    public static final Parcelable.Creator<DriveId> CREATOR = new d();
    final String EH;
    final long EI;
    final long EJ;
    private volatile String EK;
    final int xH;

    DriveId(int versionCode, String resourceId, long sqlId, long databaseInstanceId) {
        this.EK = null;
        this.xH = versionCode;
        this.EH = resourceId;
        fq.z(!"".equals(resourceId));
        fq.z((resourceId == null && sqlId == -1) ? false : true);
        this.EI = sqlId;
        this.EJ = databaseInstanceId;
    }

    public DriveId(String resourceId, long sqlId, long databaseInstanceId) {
        this(1, resourceId, sqlId, databaseInstanceId);
    }

    public static DriveId aw(String str) {
        fq.f(str);
        return new DriveId(str, -1L, -1L);
    }

    public static DriveId decodeFromString(String s) {
        fq.b(s.startsWith("DriveId:"), "Invalid DriveId: " + s);
        return f(Base64.decode(s.substring("DriveId:".length()), 10));
    }

    static DriveId f(byte[] bArr) {
        try {
            y yVarG = y.g(bArr);
            return new DriveId(yVarG.versionCode, "".equals(yVarG.FC) ? null : yVarG.FC, yVarG.FD, yVarG.FE);
        } catch (ks e) {
            throw new IllegalArgumentException();
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final String encodeToString() {
        if (this.EK == null) {
            this.EK = "DriveId:" + Base64.encodeToString(fC(), 10);
        }
        return this.EK;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DriveId)) {
            return false;
        }
        DriveId driveId = (DriveId) obj;
        if (driveId.EJ != this.EJ) {
            Log.w("DriveId", "Attempt to compare invalid DriveId detected. Has local storage been cleared?");
            return false;
        }
        if (driveId.EI == -1 && this.EI == -1) {
            return driveId.EH.equals(this.EH);
        }
        return driveId.EI == this.EI;
    }

    final byte[] fC() {
        y yVar = new y();
        yVar.versionCode = this.xH;
        yVar.FC = this.EH == null ? "" : this.EH;
        yVar.FD = this.EI;
        yVar.FE = this.EJ;
        return kt.d(yVar);
    }

    public String getResourceId() {
        return this.EH;
    }

    public int hashCode() {
        return this.EI == -1 ? this.EH.hashCode() : (String.valueOf(this.EJ) + String.valueOf(this.EI)).hashCode();
    }

    public String toString() {
        return encodeToString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        d.a(this, out, flags);
    }
}
