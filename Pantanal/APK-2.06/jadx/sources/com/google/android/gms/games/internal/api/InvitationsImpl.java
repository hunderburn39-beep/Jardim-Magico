package com.google.android.gms.games.internal.api;

import android.content.Intent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.multiplayer.InvitationBuffer;
import com.google.android.gms.games.multiplayer.Invitations;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;

/* JADX INFO: loaded from: classes.dex */
public final class InvitationsImpl implements Invitations {

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.InvitationsImpl$2, reason: invalid class name */
    class AnonymousClass2 extends LoadInvitationsImpl {
        final /* synthetic */ String JT;
        final /* synthetic */ int Kk;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.c(this, this.JT, this.Kk);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.InvitationsImpl$3, reason: invalid class name */
    class AnonymousClass3 extends LoadInvitationsImpl {
        final /* synthetic */ String Km;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.l(this, this.Km);
        }
    }

    private static abstract class LoadInvitationsImpl extends Games.BaseGamesApiMethodImpl<Invitations.LoadInvitationsResult> {
        private LoadInvitationsImpl() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: C, reason: merged with bridge method [inline-methods] */
        public Invitations.LoadInvitationsResult d(final Status status) {
            return new Invitations.LoadInvitationsResult() { // from class: com.google.android.gms.games.internal.api.InvitationsImpl.LoadInvitationsImpl.1
                @Override // com.google.android.gms.games.multiplayer.Invitations.LoadInvitationsResult
                public InvitationBuffer getInvitations() {
                    return new InvitationBuffer(DataHolder.empty(14));
                }

                @Override // com.google.android.gms.common.api.Result
                public Status getStatus() {
                    return status;
                }

                @Override // com.google.android.gms.common.api.Releasable
                public void release() {
                }
            };
        }
    }

    @Override // com.google.android.gms.games.multiplayer.Invitations
    public Intent getInvitationInboxIntent(GoogleApiClient apiClient) {
        return Games.c(apiClient).gs();
    }

    @Override // com.google.android.gms.games.multiplayer.Invitations
    public PendingResult<Invitations.LoadInvitationsResult> loadInvitations(GoogleApiClient apiClient) {
        return loadInvitations(apiClient, 0);
    }

    @Override // com.google.android.gms.games.multiplayer.Invitations
    public PendingResult<Invitations.LoadInvitationsResult> loadInvitations(GoogleApiClient apiClient, final int sortOrder) {
        return apiClient.a(new LoadInvitationsImpl() { // from class: com.google.android.gms.games.internal.api.InvitationsImpl.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.c(this, sortOrder);
            }
        });
    }

    @Override // com.google.android.gms.games.multiplayer.Invitations
    public void registerInvitationListener(GoogleApiClient apiClient, OnInvitationReceivedListener listener) {
        Games.c(apiClient).a(listener);
    }

    @Override // com.google.android.gms.games.multiplayer.Invitations
    public void unregisterInvitationListener(GoogleApiClient apiClient) {
        Games.c(apiClient).gt();
    }
}
