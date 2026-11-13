package com.fyber.utils.cookies;

import android.os.Parcel;
import android.os.Parcelable;
import com.fyber.utils.StringUtils;
import java.net.HttpCookie;

/* JADX INFO: loaded from: classes.dex */
public class ParcelableHttpCookie implements Parcelable {
    public static final Parcelable.Creator<ParcelableHttpCookie> CREATOR = new Parcelable.Creator<ParcelableHttpCookie>() { // from class: com.fyber.utils.cookies.ParcelableHttpCookie.1
        @Override // android.os.Parcelable.Creator
        public final /* bridge */ /* synthetic */ ParcelableHttpCookie[] newArray(int i) {
            return new ParcelableHttpCookie[i];
        }

        @Override // android.os.Parcelable.Creator
        public final /* synthetic */ ParcelableHttpCookie createFromParcel(Parcel parcel) {
            return new ParcelableHttpCookie(parcel);
        }
    };
    private HttpCookie a;

    public ParcelableHttpCookie(HttpCookie httpCookie) {
        if (httpCookie == null) {
            throw new IllegalArgumentException("Cookie can't be null");
        }
        this.a = httpCookie;
    }

    public ParcelableHttpCookie(Parcel parcel) {
        String string = parcel.readString();
        if (StringUtils.notNullNorEmpty(string)) {
            this.a = new HttpCookie(string, parcel.readString());
            this.a.setComment(parcel.readString());
            this.a.setCommentURL(parcel.readString());
            this.a.setDiscard(parcel.readByte() != 0);
            this.a.setDomain(parcel.readString());
            this.a.setMaxAge(parcel.readLong());
            this.a.setPath(parcel.readString());
            this.a.setPortlist(parcel.readString());
            this.a.setSecure(parcel.readByte() != 0);
            this.a.setVersion(parcel.readInt());
        }
    }

    public final HttpCookie a() {
        return this.a;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a.getName());
        parcel.writeString(this.a.getValue());
        parcel.writeString(this.a.getComment());
        parcel.writeString(this.a.getCommentURL());
        parcel.writeByte((byte) (this.a.getDiscard() ? 1 : 0));
        parcel.writeString(this.a.getDomain());
        parcel.writeLong(this.a.getMaxAge());
        parcel.writeString(this.a.getPath());
        parcel.writeString(this.a.getPortlist());
        parcel.writeByte((byte) (this.a.getSecure() ? 1 : 0));
        parcel.writeInt(this.a.getVersion());
    }
}
