package com.google.android.gms.games.internal.api;

import android.content.Intent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerBuffer;
import com.google.android.gms.games.Players;
import com.google.android.gms.games.internal.GamesClientImpl;

/* JADX INFO: loaded from: classes.dex */
public final class PlayersImpl implements Players {

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.PlayersImpl$10, reason: invalid class name */
    class AnonymousClass10 extends LoadPlayersImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ int Kb;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.b((a.d<Players.LoadPlayersResult>) this, this.Kb, false, this.JQ);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.PlayersImpl$11, reason: invalid class name */
    class AnonymousClass11 extends LoadPlayersImpl {
        final /* synthetic */ int Kb;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.b((a.d<Players.LoadPlayersResult>) this, this.Kb, true, false);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.PlayersImpl$12, reason: invalid class name */
    class AnonymousClass12 extends LoadPlayersImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ int Kb;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.c(this, this.Kb, false, this.JQ);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.PlayersImpl$13, reason: invalid class name */
    class AnonymousClass13 extends LoadPlayersImpl {
        final /* synthetic */ int Kb;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.c(this, this.Kb, true, false);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.PlayersImpl$14, reason: invalid class name */
    class AnonymousClass14 extends LoadPlayersImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ int Kb;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.d(this, this.Kb, false, this.JQ);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.PlayersImpl$15, reason: invalid class name */
    class AnonymousClass15 extends LoadPlayersImpl {
        final /* synthetic */ int Kb;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.d(this, this.Kb, true, false);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.PlayersImpl$16, reason: invalid class name */
    class AnonymousClass16 extends LoadPlayersImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ int Kb;
        final /* synthetic */ String Kd;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.d(this, this.Kd, this.Kb, false, this.JQ);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.PlayersImpl$17, reason: invalid class name */
    class AnonymousClass17 extends LoadPlayersImpl {
        final /* synthetic */ int Kb;
        final /* synthetic */ String Kd;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.d(this, this.Kd, this.Kb, true, false);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.PlayersImpl$18, reason: invalid class name */
    class AnonymousClass18 extends LoadPlayersImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ String JT;
        final /* synthetic */ int KJ;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a(this, this.JT, this.KJ, this.JQ);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.PlayersImpl$19, reason: invalid class name */
    class AnonymousClass19 extends LoadExtendedPlayersImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ int Kb;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.e(this, this.Kb, false, this.JQ);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.PlayersImpl$2, reason: invalid class name */
    class AnonymousClass2 extends LoadPlayersImpl {
        final /* synthetic */ String[] KK;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a(this, this.KK);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.PlayersImpl$20, reason: invalid class name */
    class AnonymousClass20 extends LoadExtendedPlayersImpl {
        final /* synthetic */ int Kb;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.e(this, this.Kb, true, false);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.PlayersImpl$21, reason: invalid class name */
    class AnonymousClass21 extends LoadOwnerCoverPhotoUrisImpl {
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.h(this);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.PlayersImpl$7, reason: invalid class name */
    class AnonymousClass7 extends LoadPlayersImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ String JT;
        final /* synthetic */ int Kb;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a((a.d<Players.LoadPlayersResult>) this, "playedWith", this.JT, this.Kb, false, this.JQ);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.PlayersImpl$8, reason: invalid class name */
    class AnonymousClass8 extends LoadPlayersImpl {
        final /* synthetic */ String JT;
        final /* synthetic */ int Kb;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a((a.d<Players.LoadPlayersResult>) this, "playedWith", this.JT, this.Kb, true, false);
        }
    }

    private static abstract class LoadExtendedPlayersImpl extends Games.BaseGamesApiMethodImpl<Players.LoadExtendedPlayersResult> {
        private LoadExtendedPlayersImpl() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: K, reason: merged with bridge method [inline-methods] */
        public Players.LoadExtendedPlayersResult d(final Status status) {
            return new Players.LoadExtendedPlayersResult() { // from class: com.google.android.gms.games.internal.api.PlayersImpl.LoadExtendedPlayersImpl.1
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

    private static abstract class LoadOwnerCoverPhotoUrisImpl extends Games.BaseGamesApiMethodImpl<Players.LoadOwnerCoverPhotoUrisResult> {
        private LoadOwnerCoverPhotoUrisImpl() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: L, reason: merged with bridge method [inline-methods] */
        public Players.LoadOwnerCoverPhotoUrisResult d(final Status status) {
            return new Players.LoadOwnerCoverPhotoUrisResult() { // from class: com.google.android.gms.games.internal.api.PlayersImpl.LoadOwnerCoverPhotoUrisImpl.1
                @Override // com.google.android.gms.common.api.Result
                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    private static abstract class LoadPlayersImpl extends Games.BaseGamesApiMethodImpl<Players.LoadPlayersResult> {
        private LoadPlayersImpl() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: M, reason: merged with bridge method [inline-methods] */
        public Players.LoadPlayersResult d(final Status status) {
            return new Players.LoadPlayersResult() { // from class: com.google.android.gms.games.internal.api.PlayersImpl.LoadPlayersImpl.1
                @Override // com.google.android.gms.games.Players.LoadPlayersResult
                public PlayerBuffer getPlayers() {
                    return new PlayerBuffer(DataHolder.empty(14));
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

    @Override // com.google.android.gms.games.Players
    public Player getCurrentPlayer(GoogleApiClient apiClient) {
        return Games.c(apiClient).gn();
    }

    @Override // com.google.android.gms.games.Players
    public String getCurrentPlayerId(GoogleApiClient apiClient) {
        return Games.c(apiClient).gm();
    }

    @Override // com.google.android.gms.games.Players
    public Intent getPlayerSearchIntent(GoogleApiClient apiClient) {
        return Games.c(apiClient).gw();
    }

    @Override // com.google.android.gms.games.Players
    public PendingResult<Players.LoadPlayersResult> loadConnectedPlayers(GoogleApiClient apiClient, final boolean forceReload) {
        return apiClient.a(new LoadPlayersImpl() { // from class: com.google.android.gms.games.internal.api.PlayersImpl.9
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, forceReload);
            }
        });
    }

    @Override // com.google.android.gms.games.Players
    public PendingResult<Players.LoadPlayersResult> loadInvitablePlayers(GoogleApiClient apiClient, final int pageSize, final boolean forceReload) {
        return apiClient.a(new LoadPlayersImpl() { // from class: com.google.android.gms.games.internal.api.PlayersImpl.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a((a.d<Players.LoadPlayersResult>) this, pageSize, false, forceReload);
            }
        });
    }

    @Override // com.google.android.gms.games.Players
    public PendingResult<Players.LoadPlayersResult> loadMoreInvitablePlayers(GoogleApiClient apiClient, final int pageSize) {
        return apiClient.a(new LoadPlayersImpl() { // from class: com.google.android.gms.games.internal.api.PlayersImpl.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a((a.d<Players.LoadPlayersResult>) this, pageSize, true, false);
            }
        });
    }

    @Override // com.google.android.gms.games.Players
    public PendingResult<Players.LoadPlayersResult> loadMoreRecentlyPlayedWithPlayers(GoogleApiClient apiClient, final int pageSize) {
        return apiClient.a(new LoadPlayersImpl() { // from class: com.google.android.gms.games.internal.api.PlayersImpl.6
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a((a.d<Players.LoadPlayersResult>) this, "playedWith", pageSize, true, false);
            }
        });
    }

    @Override // com.google.android.gms.games.Players
    public PendingResult<Players.LoadPlayersResult> loadPlayer(GoogleApiClient apiClient, final String playerId) {
        return apiClient.a(new LoadPlayersImpl() { // from class: com.google.android.gms.games.internal.api.PlayersImpl.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, playerId);
            }
        });
    }

    @Override // com.google.android.gms.games.Players
    public PendingResult<Players.LoadPlayersResult> loadRecentlyPlayedWithPlayers(GoogleApiClient apiClient, final int pageSize, final boolean forceReload) {
        return apiClient.a(new LoadPlayersImpl() { // from class: com.google.android.gms.games.internal.api.PlayersImpl.5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a((a.d<Players.LoadPlayersResult>) this, "playedWith", pageSize, false, forceReload);
            }
        });
    }
}
