package com.google.android.gms.identity.intents;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.identity.intents.model.CountrySpecification;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class UserAddressRequest implements SafeParcelable {
    public static final Parcelable.Creator<UserAddressRequest> CREATOR = new a();
    List<CountrySpecification> Ny;
    private final int xH;

    public final class Builder {
        private Builder() {
        }

        public Builder addAllowedCountrySpecification(CountrySpecification countrySpecification) {
            if (UserAddressRequest.this.Ny == null) {
                UserAddressRequest.this.Ny = new ArrayList();
            }
            UserAddressRequest.this.Ny.add(countrySpecification);
            return this;
        }

        public Builder addAllowedCountrySpecifications(Collection<CountrySpecification> countrySpecifications) {
            if (UserAddressRequest.this.Ny == null) {
                UserAddressRequest.this.Ny = new ArrayList();
            }
            UserAddressRequest.this.Ny.addAll(countrySpecifications);
            return this;
        }

        public UserAddressRequest build() {
            if (UserAddressRequest.this.Ny != null) {
                UserAddressRequest.this.Ny = Collections.unmodifiableList(UserAddressRequest.this.Ny);
            }
            return UserAddressRequest.this;
        }
    }

    UserAddressRequest() {
        this.xH = 1;
    }

    UserAddressRequest(int versionCode, List<CountrySpecification> allowedCountrySpecifications) {
        this.xH = versionCode;
        this.Ny = allowedCountrySpecifications;
    }

    public static Builder newBuilder() {
        UserAddressRequest userAddressRequest = new UserAddressRequest();
        userAddressRequest.getClass();
        return new Builder();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.xH;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        a.a(this, out, flags);
    }
}
