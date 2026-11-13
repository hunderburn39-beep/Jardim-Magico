package com.google.android.gms.plus.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.ff;
import com.google.android.gms.internal.fk;
import com.google.android.gms.internal.fm;
import com.google.android.gms.internal.gg;
import com.google.android.gms.internal.ie;
import com.google.android.gms.internal.ih;
import com.google.android.gms.plus.Moments;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.model.moments.Moment;
import com.google.android.gms.plus.model.moments.MomentBuffer;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/* JADX INFO: loaded from: classes.dex */
public class e extends ff<com.google.android.gms.plus.internal.d> implements GooglePlayServicesClient {
    private Person Ub;
    private final com.google.android.gms.plus.internal.h Uc;

    final class a extends com.google.android.gms.plus.internal.a {
        private final com.google.android.gms.common.api.a.d<Status> TG;

        public a(com.google.android.gms.common.api.a.d<Status> dVar) {
            this.TG = dVar;
        }

        @Override // com.google.android.gms.plus.internal.a, com.google.android.gms.plus.internal.b
        public void Z(Status status) {
            e.this.a(e.this.new d(this.TG, status));
        }
    }

    final class b extends com.google.android.gms.plus.internal.a {
        private final com.google.android.gms.common.api.a.d<Moments.LoadMomentsResult> TG;

        public b(com.google.android.gms.common.api.a.d<Moments.LoadMomentsResult> dVar) {
            this.TG = dVar;
        }

        @Override // com.google.android.gms.plus.internal.a, com.google.android.gms.plus.internal.b
        public void a(DataHolder dataHolder, String str, String str2) {
            DataHolder dataHolder2;
            Status status = new Status(dataHolder.getStatusCode(), null, dataHolder.getMetadata() != null ? (PendingIntent) dataHolder.getMetadata().getParcelable("pendingIntent") : null);
            if (status.isSuccess() || dataHolder == null) {
                dataHolder2 = dataHolder;
            } else {
                if (!dataHolder.isClosed()) {
                    dataHolder.close();
                }
                dataHolder2 = null;
            }
            e.this.a(e.this.new c(this.TG, status, dataHolder2, str, str2));
        }
    }

    final class c extends ff<com.google.android.gms.plus.internal.d>.d<com.google.android.gms.common.api.a.d<Moments.LoadMomentsResult>> implements Moments.LoadMomentsResult {
        private final String EM;
        private final String Ue;
        private MomentBuffer Uf;
        private final Status wJ;

        public c(com.google.android.gms.common.api.a.d<Moments.LoadMomentsResult> dVar, Status status, DataHolder dataHolder, String str, String str2) {
            super(dVar, dataHolder);
            this.wJ = status;
            this.EM = str;
            this.Ue = str2;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.d
        public void a(com.google.android.gms.common.api.a.d<Moments.LoadMomentsResult> dVar, DataHolder dataHolder) {
            this.Uf = dataHolder != null ? new MomentBuffer(dataHolder) : null;
            dVar.b(this);
        }

        @Override // com.google.android.gms.plus.Moments.LoadMomentsResult
        public MomentBuffer getMomentBuffer() {
            return this.Uf;
        }

        @Override // com.google.android.gms.plus.Moments.LoadMomentsResult
        public String getNextPageToken() {
            return this.EM;
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.wJ;
        }

        @Override // com.google.android.gms.plus.Moments.LoadMomentsResult
        public String getUpdated() {
            return this.Ue;
        }

        @Override // com.google.android.gms.common.api.Releasable
        public void release() {
            if (this.Uf != null) {
                this.Uf.close();
            }
        }
    }

    final class d extends ff<com.google.android.gms.plus.internal.d>.b<com.google.android.gms.common.api.a.d<Status>> {
        private final Status wJ;

        public d(com.google.android.gms.common.api.a.d<Status> dVar, Status status) {
            super(dVar);
            this.wJ = status;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public void a(com.google.android.gms.common.api.a.d<Status> dVar) {
            if (dVar != null) {
                dVar.b(this.wJ);
            }
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.plus.internal.e$e, reason: collision with other inner class name */
    final class BinderC0129e extends com.google.android.gms.plus.internal.a {
        private final com.google.android.gms.common.api.a.d<People.LoadPeopleResult> TG;

        public BinderC0129e(com.google.android.gms.common.api.a.d<People.LoadPeopleResult> dVar) {
            this.TG = dVar;
        }

        @Override // com.google.android.gms.plus.internal.a, com.google.android.gms.plus.internal.b
        public void a(DataHolder dataHolder, String str) {
            DataHolder dataHolder2;
            Status status = new Status(dataHolder.getStatusCode(), null, dataHolder.getMetadata() != null ? (PendingIntent) dataHolder.getMetadata().getParcelable("pendingIntent") : null);
            if (status.isSuccess() || dataHolder == null) {
                dataHolder2 = dataHolder;
            } else {
                if (!dataHolder.isClosed()) {
                    dataHolder.close();
                }
                dataHolder2 = null;
            }
            e.this.a(e.this.new f(this.TG, status, dataHolder2, str));
        }
    }

    final class f extends ff<com.google.android.gms.plus.internal.d>.d<com.google.android.gms.common.api.a.d<People.LoadPeopleResult>> implements People.LoadPeopleResult {
        private final String EM;
        private PersonBuffer Ug;
        private final Status wJ;

        public f(com.google.android.gms.common.api.a.d<People.LoadPeopleResult> dVar, Status status, DataHolder dataHolder, String str) {
            super(dVar, dataHolder);
            this.wJ = status;
            this.EM = str;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.d
        public void a(com.google.android.gms.common.api.a.d<People.LoadPeopleResult> dVar, DataHolder dataHolder) {
            this.Ug = dataHolder != null ? new PersonBuffer(dataHolder) : null;
            dVar.b(this);
        }

        @Override // com.google.android.gms.plus.People.LoadPeopleResult
        public String getNextPageToken() {
            return this.EM;
        }

        @Override // com.google.android.gms.plus.People.LoadPeopleResult
        public PersonBuffer getPersonBuffer() {
            return this.Ug;
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.wJ;
        }

        @Override // com.google.android.gms.common.api.Releasable
        public void release() {
            if (this.Ug != null) {
                this.Ug.close();
            }
        }
    }

    final class g extends com.google.android.gms.plus.internal.a {
        private final com.google.android.gms.common.api.a.d<Status> TG;

        public g(com.google.android.gms.common.api.a.d<Status> dVar) {
            this.TG = dVar;
        }

        @Override // com.google.android.gms.plus.internal.a, com.google.android.gms.plus.internal.b
        public void e(int i, Bundle bundle) {
            e.this.a(e.this.new h(this.TG, new Status(i, null, bundle != null ? (PendingIntent) bundle.getParcelable("pendingIntent") : null)));
        }
    }

    final class h extends ff<com.google.android.gms.plus.internal.d>.b<com.google.android.gms.common.api.a.d<Status>> {
        private final Status wJ;

        public h(com.google.android.gms.common.api.a.d<Status> dVar, Status status) {
            super(dVar);
            this.wJ = status;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.ff.b
        /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
        public void a(com.google.android.gms.common.api.a.d<Status> dVar) {
            e.this.disconnect();
            if (dVar != null) {
                dVar.b(this.wJ);
            }
        }

        @Override // com.google.android.gms.internal.ff.b
        protected void dx() {
        }
    }

    public e(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, com.google.android.gms.plus.internal.h hVar) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, hVar.iP());
        this.Uc = hVar;
    }

    @Deprecated
    public e(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, com.google.android.gms.plus.internal.h hVar) {
        this(context, context.getMainLooper(), new ff.c(connectionCallbacks), new ff.g(onConnectionFailedListener), hVar);
    }

    public fk a(com.google.android.gms.common.api.a.d<People.LoadPeopleResult> dVar, int i, String str) {
        bT();
        BinderC0129e binderC0129e = new BinderC0129e(dVar);
        try {
            return eM().a(binderC0129e, 1, i, -1, str);
        } catch (RemoteException e) {
            binderC0129e.a(DataHolder.empty(8), (String) null);
            return null;
        }
    }

    @Override // com.google.android.gms.internal.ff
    protected void a(int i, IBinder iBinder, Bundle bundle) {
        if (i == 0 && bundle != null && bundle.containsKey("loaded_person")) {
            this.Ub = ih.i(bundle.getByteArray("loaded_person"));
        }
        super.a(i, iBinder, bundle);
    }

    public void a(com.google.android.gms.common.api.a.d<Moments.LoadMomentsResult> dVar, int i, String str, Uri uri, String str2, String str3) {
        bT();
        b bVar = dVar != null ? new b(dVar) : null;
        try {
            eM().a(bVar, i, str, uri, str2, str3);
        } catch (RemoteException e) {
            bVar.a(DataHolder.empty(8), (String) null, (String) null);
        }
    }

    public void a(com.google.android.gms.common.api.a.d<Status> dVar, Moment moment) {
        bT();
        a aVar = dVar != null ? new a(dVar) : null;
        try {
            eM().a(aVar, gg.a((ie) moment));
        } catch (RemoteException e) {
            if (aVar == null) {
                throw new IllegalStateException(e);
            }
            aVar.Z(new Status(8, null, null));
        }
    }

    public void a(com.google.android.gms.common.api.a.d<People.LoadPeopleResult> dVar, Collection<String> collection) {
        bT();
        BinderC0129e binderC0129e = new BinderC0129e(dVar);
        try {
            eM().a(binderC0129e, new ArrayList(collection));
        } catch (RemoteException e) {
            binderC0129e.a(DataHolder.empty(8), (String) null);
        }
    }

    @Override // com.google.android.gms.internal.ff
    protected void a(fm fmVar, ff.e eVar) throws RemoteException {
        Bundle bundleIX = this.Uc.iX();
        bundleIX.putStringArray("request_visible_actions", this.Uc.iQ());
        fmVar.a(eVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, this.Uc.iT(), this.Uc.iS(), eL(), this.Uc.getAccountName(), bundleIX);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.ff
    /* JADX INFO: renamed from: aR, reason: merged with bridge method [inline-methods] */
    public com.google.android.gms.plus.internal.d r(IBinder iBinder) {
        return com.google.android.gms.plus.internal.d.a.aQ(iBinder);
    }

    @Override // com.google.android.gms.internal.ff
    protected String bg() {
        return "com.google.android.gms.plus.service.START";
    }

    public boolean bg(String str) {
        return Arrays.asList(eL()).contains(str);
    }

    @Override // com.google.android.gms.internal.ff
    protected String bh() {
        return "com.google.android.gms.plus.internal.IPlusService";
    }

    public void clearDefaultAccount() {
        bT();
        try {
            this.Ub = null;
            eM().clearDefaultAccount();
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public void d(com.google.android.gms.common.api.a.d<People.LoadPeopleResult> dVar, String[] strArr) {
        a(dVar, Arrays.asList(strArr));
    }

    public String getAccountName() {
        bT();
        try {
            return eM().getAccountName();
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }

    public Person getCurrentPerson() {
        bT();
        return this.Ub;
    }

    public void l(com.google.android.gms.common.api.a.d<Moments.LoadMomentsResult> dVar) {
        a(dVar, 20, null, null, null, "me");
    }

    public void m(com.google.android.gms.common.api.a.d<People.LoadPeopleResult> dVar) {
        bT();
        BinderC0129e binderC0129e = new BinderC0129e(dVar);
        try {
            eM().a(binderC0129e, 2, 1, -1, null);
        } catch (RemoteException e) {
            binderC0129e.a(DataHolder.empty(8), (String) null);
        }
    }

    public void n(com.google.android.gms.common.api.a.d<Status> dVar) {
        bT();
        clearDefaultAccount();
        g gVar = new g(dVar);
        try {
            eM().b(gVar);
        } catch (RemoteException e) {
            gVar.e(8, null);
        }
    }

    public fk o(com.google.android.gms.common.api.a.d<People.LoadPeopleResult> dVar, String str) {
        return a(dVar, 0, str);
    }

    public void removeMoment(String momentId) {
        bT();
        try {
            eM().removeMoment(momentId);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }
}
