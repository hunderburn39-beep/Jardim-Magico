package com.google.android.gms.drive.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;

/* JADX INFO: loaded from: classes.dex */
public class o extends r implements DriveFile {

    private abstract class a extends m<Status> {
        private a() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
        public Status d(Status status) {
            return status;
        }
    }

    private abstract class b extends m<Status> {
        private b() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
        public Status d(Status status) {
            return status;
        }
    }

    private static class c extends com.google.android.gms.drive.internal.c {
        private final DriveFile.DownloadProgressListener Ft;
        private final com.google.android.gms.common.api.a.d<DriveApi.ContentsResult> wH;

        public c(com.google.android.gms.common.api.a.d<DriveApi.ContentsResult> dVar, DriveFile.DownloadProgressListener downloadProgressListener) {
            this.wH = dVar;
            this.Ft = downloadProgressListener;
        }

        @Override // com.google.android.gms.drive.internal.c, com.google.android.gms.drive.internal.v
        public void a(OnContentsResponse onContentsResponse) throws RemoteException {
            this.wH.b(new l.a(Status.Bv, onContentsResponse.fI()));
        }

        @Override // com.google.android.gms.drive.internal.c, com.google.android.gms.drive.internal.v
        public void a(OnDownloadProgressResponse onDownloadProgressResponse) throws RemoteException {
            if (this.Ft != null) {
                this.Ft.onProgress(onDownloadProgressResponse.fJ(), onDownloadProgressResponse.fK());
            }
        }

        @Override // com.google.android.gms.drive.internal.c, com.google.android.gms.drive.internal.v
        public void m(Status status) throws RemoteException {
            this.wH.b(new l.a(status, null));
        }
    }

    private abstract class d extends m<DriveApi.ContentsResult> {
        private d() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: o, reason: merged with bridge method [inline-methods] */
        public DriveApi.ContentsResult d(Status status) {
            return new l.a(status, null);
        }
    }

    public o(DriveId driveId) {
        super(driveId);
    }

    @Override // com.google.android.gms.drive.DriveFile
    public PendingResult<Status> commitAndCloseContents(GoogleApiClient apiClient, final Contents contents) {
        if (contents == null) {
            throw new IllegalArgumentException("Contents must be provided.");
        }
        return apiClient.b(new b() { // from class: com.google.android.gms.drive.internal.o.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(n nVar) throws RemoteException {
                contents.close();
                nVar.fE().a(new CloseContentsRequest(contents, true), new al(this));
            }
        });
    }

    @Override // com.google.android.gms.drive.DriveFile
    public PendingResult<Status> commitAndCloseContents(GoogleApiClient apiClient, final Contents contents, final MetadataChangeSet changeSet) {
        if (contents == null) {
            throw new IllegalArgumentException("Contents must be provided.");
        }
        return apiClient.b(new a() { // from class: com.google.android.gms.drive.internal.o.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(n nVar) throws RemoteException {
                contents.close();
                nVar.fE().a(new CloseContentsAndUpdateMetadataRequest(o.this.Ew, changeSet.fD(), contents), new al(this));
            }
        });
    }

    @Override // com.google.android.gms.drive.DriveFile
    public PendingResult<Status> discardContents(GoogleApiClient apiClient, Contents contents) {
        return Drive.DriveApi.discardContents(apiClient, contents);
    }

    @Override // com.google.android.gms.drive.DriveFile
    public PendingResult<DriveApi.ContentsResult> openContents(GoogleApiClient apiClient, final int mode, final DriveFile.DownloadProgressListener listener) {
        if (mode == 268435456 || mode == 536870912 || mode == 805306368) {
            return apiClient.a(new d() { // from class: com.google.android.gms.drive.internal.o.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.google.android.gms.common.api.a.b
                public void a(n nVar) throws RemoteException {
                    nVar.fE().a(new OpenContentsRequest(o.this.getDriveId(), mode), new c(this, listener));
                }
            });
        }
        throw new IllegalArgumentException("Invalid mode provided.");
    }
}
