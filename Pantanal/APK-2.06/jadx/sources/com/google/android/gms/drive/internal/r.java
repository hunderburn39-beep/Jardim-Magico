package com.google.android.gms.drive.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.DriveEvent;

/* JADX INFO: loaded from: classes.dex */
public class r implements DriveResource {
    protected final DriveId Ew;

    private abstract class a extends m<DriveResource.MetadataResult> {
        private a() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: s, reason: merged with bridge method [inline-methods] */
        public DriveResource.MetadataResult d(Status status) {
            return new e(status, null);
        }
    }

    private static class b extends com.google.android.gms.drive.internal.c {
        private final com.google.android.gms.common.api.a.d<DriveApi.MetadataBufferResult> wH;

        public b(com.google.android.gms.common.api.a.d<DriveApi.MetadataBufferResult> dVar) {
            this.wH = dVar;
        }

        @Override // com.google.android.gms.drive.internal.c, com.google.android.gms.drive.internal.v
        public void a(OnListParentsResponse onListParentsResponse) throws RemoteException {
            this.wH.b(new l.e(Status.Bv, new MetadataBuffer(onListParentsResponse.fP(), null), false));
        }

        @Override // com.google.android.gms.drive.internal.c, com.google.android.gms.drive.internal.v
        public void m(Status status) throws RemoteException {
            this.wH.b(new l.e(status, null, false));
        }
    }

    private abstract class c extends m<DriveApi.MetadataBufferResult> {
        private c() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: p, reason: merged with bridge method [inline-methods] */
        public DriveApi.MetadataBufferResult d(Status status) {
            return new l.e(status, null, false);
        }
    }

    private static class d extends com.google.android.gms.drive.internal.c {
        private final com.google.android.gms.common.api.a.d<DriveResource.MetadataResult> wH;

        public d(com.google.android.gms.common.api.a.d<DriveResource.MetadataResult> dVar) {
            this.wH = dVar;
        }

        @Override // com.google.android.gms.drive.internal.c, com.google.android.gms.drive.internal.v
        public void a(OnMetadataResponse onMetadataResponse) throws RemoteException {
            this.wH.b(new e(Status.Bv, new j(onMetadataResponse.fQ())));
        }

        @Override // com.google.android.gms.drive.internal.c, com.google.android.gms.drive.internal.v
        public void m(Status status) throws RemoteException {
            this.wH.b(new e(status, null));
        }
    }

    private static class e implements DriveResource.MetadataResult {
        private final Metadata Fy;
        private final Status wJ;

        public e(Status status, Metadata metadata) {
            this.wJ = status;
            this.Fy = metadata;
        }

        @Override // com.google.android.gms.drive.DriveResource.MetadataResult
        public Metadata getMetadata() {
            return this.Fy;
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.wJ;
        }
    }

    private abstract class f extends m<DriveResource.MetadataResult> {
        private f() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: s, reason: merged with bridge method [inline-methods] */
        public DriveResource.MetadataResult d(Status status) {
            return new e(status, null);
        }
    }

    protected r(DriveId driveId) {
        this.Ew = driveId;
    }

    @Override // com.google.android.gms.drive.DriveResource
    public PendingResult<Status> addChangeListener(GoogleApiClient apiClient, DriveEvent.Listener<ChangeEvent> listener) {
        return ((n) apiClient.a(Drive.wx)).a(apiClient, this.Ew, 1, listener);
    }

    @Override // com.google.android.gms.drive.DriveResource
    public DriveId getDriveId() {
        return this.Ew;
    }

    @Override // com.google.android.gms.drive.DriveResource
    public PendingResult<DriveResource.MetadataResult> getMetadata(GoogleApiClient apiClient) {
        return apiClient.a(new a() { // from class: com.google.android.gms.drive.internal.r.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(n nVar) throws RemoteException {
                nVar.fE().a(new GetMetadataRequest(r.this.Ew), new d(this));
            }
        });
    }

    @Override // com.google.android.gms.drive.DriveResource
    public PendingResult<DriveApi.MetadataBufferResult> listParents(GoogleApiClient apiClient) {
        return apiClient.a(new c() { // from class: com.google.android.gms.drive.internal.r.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(n nVar) throws RemoteException {
                nVar.fE().a(new ListParentsRequest(r.this.Ew), new b(this));
            }
        });
    }

    @Override // com.google.android.gms.drive.DriveResource
    public PendingResult<Status> removeChangeListener(GoogleApiClient apiClient, DriveEvent.Listener<ChangeEvent> listener) {
        return ((n) apiClient.a(Drive.wx)).b(apiClient, this.Ew, 1, listener);
    }

    @Override // com.google.android.gms.drive.DriveResource
    public PendingResult<DriveResource.MetadataResult> updateMetadata(GoogleApiClient apiClient, final MetadataChangeSet changeSet) {
        if (changeSet == null) {
            throw new IllegalArgumentException("ChangeSet must be provided.");
        }
        return apiClient.b(new f() { // from class: com.google.android.gms.drive.internal.r.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(n nVar) throws RemoteException {
                nVar.fE().a(new UpdateMetadataRequest(r.this.Ew, changeSet.fD()), new d(this));
            }
        });
    }
}
