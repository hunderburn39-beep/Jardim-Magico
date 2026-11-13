package com.google.android.gms.cast;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class ApplicationMetadata implements SafeParcelable {
    public static final Parcelable.Creator<ApplicationMetadata> CREATOR = new a();
    String mName;
    private final int xH;
    String xI;
    List<WebImage> xJ;
    List<String> xK;
    String xL;
    Uri xM;

    private ApplicationMetadata() {
        this.xH = 1;
        this.xJ = new ArrayList();
        this.xK = new ArrayList();
    }

    ApplicationMetadata(int versionCode, String applicationId, String name, List<WebImage> images, List<String> namespaces, String senderAppIdentifier, Uri senderAppLaunchUrl) {
        this.xH = versionCode;
        this.xI = applicationId;
        this.mName = name;
        this.xJ = images;
        this.xK = namespaces;
        this.xL = senderAppIdentifier;
        this.xM = senderAppLaunchUrl;
    }

    public boolean areNamespacesSupported(List<String> namespaces) {
        return this.xK != null && this.xK.containsAll(namespaces);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Uri dz() {
        return this.xM;
    }

    public String getApplicationId() {
        return this.xI;
    }

    public List<WebImage> getImages() {
        return this.xJ;
    }

    public String getName() {
        return this.mName;
    }

    public String getSenderAppIdentifier() {
        return this.xL;
    }

    int getVersionCode() {
        return this.xH;
    }

    public boolean isNamespaceSupported(String namespace) {
        return this.xK != null && this.xK.contains(namespace);
    }

    public String toString() {
        return this.mName;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        a.a(this, out, flags);
    }
}
