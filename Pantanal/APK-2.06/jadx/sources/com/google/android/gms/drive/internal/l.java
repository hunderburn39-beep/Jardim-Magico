package com.google.android.gms.drive.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.CreateFileActivityBuilder;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.OpenFileActivityBuilder;
import com.google.android.gms.drive.query.Query;

/* JADX INFO: loaded from: classes.dex */
public class l implements DriveApi {

    static class a implements DriveApi.ContentsResult {
        private final Contents EA;
        private final Status wJ;

        public a(Status status, Contents contents) {
            this.wJ = status;
            this.EA = contents;
        }

        @Override // com.google.android.gms.drive.DriveApi.ContentsResult
        public Contents getContents() {
            return this.EA;
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.wJ;
        }
    }

    private static class b extends com.google.android.gms.drive.internal.c {
        private final com.google.android.gms.common.api.a.d<DriveApi.DriveIdResult> wH;

        public b(com.google.android.gms.common.api.a.d<DriveApi.DriveIdResult> dVar) {
            this.wH = dVar;
        }

        @Override // com.google.android.gms.drive.internal.c, com.google.android.gms.drive.internal.v
        public void a(OnMetadataResponse onMetadataResponse) throws RemoteException {
            this.wH.b(new c(Status.Bv, new com.google.android.gms.drive.internal.j(onMetadataResponse.fQ()).getDriveId()));
        }

        @Override // com.google.android.gms.drive.internal.c, com.google.android.gms.drive.internal.v
        public void m(Status status) throws RemoteException {
            this.wH.b(new c(status, null));
        }
    }

    static class c implements DriveApi.DriveIdResult {
        private final DriveId Ew;
        private final Status wJ;

        public c(Status status, DriveId driveId) {
            this.wJ = status;
            this.Ew = driveId;
        }

        @Override // com.google.android.gms.drive.DriveApi.DriveIdResult
        public DriveId getDriveId() {
            return this.Ew;
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.wJ;
        }
    }

    abstract class d extends m<DriveApi.DriveIdResult> {
        d() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: n, reason: merged with bridge method [inline-methods] */
        public DriveApi.DriveIdResult d(Status status) {
            return new c(status, null);
        }
    }

    static class e implements DriveApi.MetadataBufferResult {
        private final MetadataBuffer Ff;
        private final boolean Fg;
        private final Status wJ;

        public e(Status status, MetadataBuffer metadataBuffer, boolean z) {
            this.wJ = status;
            this.Ff = metadataBuffer;
            this.Fg = z;
        }

        @Override // com.google.android.gms.drive.DriveApi.MetadataBufferResult
        public MetadataBuffer getMetadataBuffer() {
            return this.Ff;
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.wJ;
        }
    }

    private static class f extends com.google.android.gms.drive.internal.c {
        private final com.google.android.gms.common.api.a.d<DriveApi.ContentsResult> wH;

        public f(com.google.android.gms.common.api.a.d<DriveApi.ContentsResult> dVar) {
            this.wH = dVar;
        }

        @Override // com.google.android.gms.drive.internal.c, com.google.android.gms.drive.internal.v
        public void a(OnContentsResponse onContentsResponse) throws RemoteException {
            this.wH.b(new a(Status.Bv, onContentsResponse.fI()));
        }

        @Override // com.google.android.gms.drive.internal.c, com.google.android.gms.drive.internal.v
        public void m(Status status) throws RemoteException {
            this.wH.b(new a(status, null));
        }
    }

    abstract class g extends m<DriveApi.ContentsResult> {
        g() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: o, reason: merged with bridge method [inline-methods] */
        public DriveApi.ContentsResult d(Status status) {
            return new a(status, null);
        }
    }

    static class h extends com.google.android.gms.drive.internal.c {
        private final com.google.android.gms.common.api.a.d<DriveApi.MetadataBufferResult> wH;

        public h(com.google.android.gms.common.api.a.d<DriveApi.MetadataBufferResult> dVar) {
            this.wH = dVar;
        }

        @Override // com.google.android.gms.drive.internal.c, com.google.android.gms.drive.internal.v
        public void a(OnListEntriesResponse onListEntriesResponse) throws RemoteException {
            this.wH.b(new e(Status.Bv, new MetadataBuffer(onListEntriesResponse.fN(), null), onListEntriesResponse.fO()));
        }

        @Override // com.google.android.gms.drive.internal.c, com.google.android.gms.drive.internal.v
        public void m(Status status) throws RemoteException {
            this.wH.b(new e(status, null, false));
        }
    }

    abstract class i extends m<DriveApi.MetadataBufferResult> {
        i() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: p, reason: merged with bridge method [inline-methods] */
        public DriveApi.MetadataBufferResult d(Status status) {
            return new e(status, null, false);
        }
    }

    static abstract class j extends m<Status> {
        j() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
        public Status d(Status status) {
            return status;
        }
    }

    static class k extends j {
        k(GoogleApiClient googleApiClient, Status status) {
            a(new com.google.android.gms.common.api.a.c(((n) googleApiClient.a(Drive.wx)).getLooper()));
            b(status);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(n nVar) {
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.drive.internal.l$l, reason: collision with other inner class name */
    abstract class AbstractC0036l extends m<Status> {
        AbstractC0036l() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
        public Status d(Status status) {
            return status;
        }
    }

    @Override // com.google.android.gms.drive.DriveApi
    public PendingResult<Status> discardContents(GoogleApiClient apiClient, final Contents contents) {
        return apiClient.b(new j() { // from class: com.google.android.gms.drive.internal.l.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(n nVar) throws RemoteException {
                nVar.fE().a(new CloseContentsRequest(contents, false), new al(this));
            }
        });
    }

    @Override // com.google.android.gms.drive.DriveApi
    public PendingResult<DriveApi.DriveIdResult> fetchDriveId(GoogleApiClient apiClient, final String resourceId) {
        return apiClient.a(new d() { // from class: com.google.android.gms.drive.internal.l.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(n nVar) throws RemoteException {
                nVar.fE().a(new GetMetadataRequest(DriveId.aw(resourceId)), new b(this));
            }
        });
    }

    @Override // com.google.android.gms.drive.DriveApi
    public DriveFolder getAppFolder(GoogleApiClient apiClient) {
        if (!apiClient.isConnected()) {
            throw new IllegalStateException("Client must be connected");
        }
        DriveId driveIdFG = ((n) apiClient.a(Drive.wx)).fG();
        if (driveIdFG != null) {
            return new q(driveIdFG);
        }
        return null;
    }

    @Override // com.google.android.gms.drive.DriveApi
    public DriveFile getFile(GoogleApiClient apiClient, DriveId id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must be provided.");
        }
        if (apiClient.isConnected()) {
            return new o(id);
        }
        throw new IllegalStateException("Client must be connected");
    }

    @Override // com.google.android.gms.drive.DriveApi
    public DriveFolder getFolder(GoogleApiClient apiClient, DriveId id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must be provided.");
        }
        if (apiClient.isConnected()) {
            return new q(id);
        }
        throw new IllegalStateException("Client must be connected");
    }

    @Override // com.google.android.gms.drive.DriveApi
    public DriveFolder getRootFolder(GoogleApiClient apiClient) {
        if (apiClient.isConnected()) {
            return new q(((n) apiClient.a(Drive.wx)).fF());
        }
        throw new IllegalStateException("Client must be connected");
    }

    @Override // com.google.android.gms.drive.DriveApi
    public PendingResult<DriveApi.ContentsResult> newContents(GoogleApiClient apiClient) {
        return apiClient.a(new g() { // from class: com.google.android.gms.drive.internal.l.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(n nVar) throws RemoteException {
                nVar.fE().a(new CreateContentsRequest(), new f(this));
            }
        });
    }

    @Override // com.google.android.gms.drive.DriveApi
    public CreateFileActivityBuilder newCreateFileActivityBuilder() {
        return new CreateFileActivityBuilder();
    }

    @Override // com.google.android.gms.drive.DriveApi
    public OpenFileActivityBuilder newOpenFileActivityBuilder() {
        return new OpenFileActivityBuilder();
    }

    @Override // com.google.android.gms.drive.DriveApi
    public PendingResult<DriveApi.MetadataBufferResult> query(GoogleApiClient apiClient, final Query query) {
        if (query == null) {
            throw new IllegalArgumentException("Query must be provided.");
        }
        return apiClient.a(new i() { // from class: com.google.android.gms.drive.internal.l.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(n nVar) throws RemoteException {
                nVar.fE().a(new QueryRequest(query), new h(this));
            }
        });
    }

    @Override // com.google.android.gms.drive.DriveApi
    public PendingResult<Status> requestSync(GoogleApiClient client) {
        return client.b(new AbstractC0036l() { // from class: com.google.android.gms.drive.internal.l.5
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(n nVar) throws RemoteException {
                nVar.fE().a(new al(this));
            }
        });
    }
}
