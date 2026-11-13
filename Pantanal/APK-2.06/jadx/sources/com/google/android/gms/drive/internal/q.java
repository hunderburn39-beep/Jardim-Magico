package com.google.android.gms.drive.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;

/* JADX INFO: loaded from: classes.dex */
public class q extends r implements DriveFolder {

    private static class a extends com.google.android.gms.drive.internal.c {
        private final com.google.android.gms.common.api.a.d<DriveFolder.DriveFileResult> wH;

        public a(com.google.android.gms.common.api.a.d<DriveFolder.DriveFileResult> dVar) {
            this.wH = dVar;
        }

        @Override // com.google.android.gms.drive.internal.c, com.google.android.gms.drive.internal.v
        public void a(OnDriveIdResponse onDriveIdResponse) throws RemoteException {
            this.wH.b(new d(Status.Bv, new o(onDriveIdResponse.getDriveId())));
        }

        @Override // com.google.android.gms.drive.internal.c, com.google.android.gms.drive.internal.v
        public void m(Status status) throws RemoteException {
            this.wH.b(new d(status, null));
        }
    }

    private static class b extends com.google.android.gms.drive.internal.c {
        private final com.google.android.gms.common.api.a.d<DriveFolder.DriveFolderResult> wH;

        public b(com.google.android.gms.common.api.a.d<DriveFolder.DriveFolderResult> dVar) {
            this.wH = dVar;
        }

        @Override // com.google.android.gms.drive.internal.c, com.google.android.gms.drive.internal.v
        public void a(OnDriveIdResponse onDriveIdResponse) throws RemoteException {
            this.wH.b(new e(Status.Bv, new q(onDriveIdResponse.getDriveId())));
        }

        @Override // com.google.android.gms.drive.internal.c, com.google.android.gms.drive.internal.v
        public void m(Status status) throws RemoteException {
            this.wH.b(new e(status, null));
        }
    }

    private abstract class c extends m<DriveFolder.DriveFolderResult> {
        private c() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: r, reason: merged with bridge method [inline-methods] */
        public DriveFolder.DriveFolderResult d(Status status) {
            return new e(status, null);
        }
    }

    private static class d implements DriveFolder.DriveFileResult {
        private final DriveFile Fv;
        private final Status wJ;

        public d(Status status, DriveFile driveFile) {
            this.wJ = status;
            this.Fv = driveFile;
        }

        @Override // com.google.android.gms.drive.DriveFolder.DriveFileResult
        public DriveFile getDriveFile() {
            return this.Fv;
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.wJ;
        }
    }

    private static class e implements DriveFolder.DriveFolderResult {
        private final DriveFolder Fw;
        private final Status wJ;

        public e(Status status, DriveFolder driveFolder) {
            this.wJ = status;
            this.Fw = driveFolder;
        }

        @Override // com.google.android.gms.drive.DriveFolder.DriveFolderResult
        public DriveFolder getDriveFolder() {
            return this.Fw;
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.wJ;
        }
    }

    public q(DriveId driveId) {
        super(driveId);
    }

    @Override // com.google.android.gms.drive.DriveFolder
    public PendingResult<DriveFolder.DriveFileResult> createFile(GoogleApiClient apiClient, final MetadataChangeSet changeSet, final Contents contents) {
        if (changeSet == null) {
            throw new IllegalArgumentException("MetatadataChangeSet must be provided.");
        }
        if (contents == null) {
            throw new IllegalArgumentException("Contents must be provided.");
        }
        if (DriveFolder.MIME_TYPE.equals(changeSet.getMimeType())) {
            throw new IllegalArgumentException("May not create folders (mimetype: application/vnd.google-apps.folder) using this method. Use DriveFolder.createFolder() instead.");
        }
        return apiClient.b(new m<DriveFolder.DriveFileResult>() { // from class: com.google.android.gms.drive.internal.q.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(n nVar) throws RemoteException {
                contents.close();
                nVar.fE().a(new CreateFileRequest(q.this.getDriveId(), changeSet.fD(), contents), new a(this));
            }

            @Override // com.google.android.gms.common.api.a.AbstractC0032a
            /* JADX INFO: renamed from: q, reason: merged with bridge method [inline-methods] */
            public DriveFolder.DriveFileResult d(Status status) {
                return new d(status, null);
            }
        });
    }

    @Override // com.google.android.gms.drive.DriveFolder
    public PendingResult<DriveFolder.DriveFolderResult> createFolder(GoogleApiClient apiClient, final MetadataChangeSet changeSet) {
        if (changeSet == null) {
            throw new IllegalArgumentException("MetatadataChangeSet must be provided.");
        }
        if (changeSet.getMimeType() == null || changeSet.getMimeType().equals(DriveFolder.MIME_TYPE)) {
            return apiClient.b(new c() { // from class: com.google.android.gms.drive.internal.q.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.google.android.gms.common.api.a.b
                public void a(n nVar) throws RemoteException {
                    nVar.fE().a(new CreateFolderRequest(q.this.getDriveId(), changeSet.fD()), new b(this));
                }
            });
        }
        throw new IllegalArgumentException("The mimetype must be of type application/vnd.google-apps.folder");
    }

    @Override // com.google.android.gms.drive.DriveFolder
    public PendingResult<DriveApi.MetadataBufferResult> listChildren(GoogleApiClient apiClient) {
        return queryChildren(apiClient, null);
    }

    @Override // com.google.android.gms.drive.DriveFolder
    public PendingResult<DriveApi.MetadataBufferResult> queryChildren(GoogleApiClient apiClient, Query query) {
        Query.Builder builderAddFilter = new Query.Builder().addFilter(Filters.in(SearchableField.PARENTS, getDriveId()));
        if (query != null) {
            if (query.getFilter() != null) {
                builderAddFilter.addFilter(query.getFilter());
            }
            builderAddFilter.setPageToken(query.getPageToken());
            builderAddFilter.a(query.fV());
        }
        return new l().query(apiClient, builderAddFilter.build());
    }
}
