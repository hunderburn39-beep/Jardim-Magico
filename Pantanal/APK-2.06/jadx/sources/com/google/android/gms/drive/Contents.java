package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class Contents implements SafeParcelable {
    public static final Parcelable.Creator<Contents> CREATOR = new a();
    final ParcelFileDescriptor Cj;
    final int Eu;
    final int Ev;
    final DriveId Ew;
    final int xH;
    private boolean mClosed = false;
    private boolean Ex = false;
    private boolean Ey = false;

    Contents(int versionCode, ParcelFileDescriptor parcelFileDescriptor, int requestId, int mode, DriveId driveId) {
        this.xH = versionCode;
        this.Cj = parcelFileDescriptor;
        this.Eu = requestId;
        this.Ev = mode;
        this.Ew = driveId;
    }

    public void close() {
        this.mClosed = true;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int fA() {
        return this.Eu;
    }

    public DriveId getDriveId() {
        return this.Ew;
    }

    public InputStream getInputStream() {
        if (this.mClosed) {
            throw new IllegalStateException("Contents have been closed, cannot access the input stream.");
        }
        if (this.Ev != 268435456) {
            throw new IllegalStateException("getInputStream() can only be used with contents opened with MODE_READ_ONLY.");
        }
        if (this.Ex) {
            throw new IllegalStateException("getInputStream() can only be called once per Contents instance.");
        }
        this.Ex = true;
        return new FileInputStream(this.Cj.getFileDescriptor());
    }

    public int getMode() {
        return this.Ev;
    }

    public OutputStream getOutputStream() {
        if (this.mClosed) {
            throw new IllegalStateException("Contents have been closed, cannot access the output stream.");
        }
        if (this.Ev != 536870912) {
            throw new IllegalStateException("getOutputStream() can only be used with contents opened with MODE_WRITE_ONLY.");
        }
        if (this.Ey) {
            throw new IllegalStateException("getOutputStream() can only be called once per Contents instance.");
        }
        this.Ey = true;
        return new FileOutputStream(this.Cj.getFileDescriptor());
    }

    public ParcelFileDescriptor getParcelFileDescriptor() {
        if (this.mClosed) {
            throw new IllegalStateException("Contents have been closed, cannot access the output stream.");
        }
        return this.Cj;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        a.a(this, dest, flags);
    }
}
