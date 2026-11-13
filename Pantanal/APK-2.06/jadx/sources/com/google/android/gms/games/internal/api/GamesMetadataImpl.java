package com.google.android.gms.games.internal.api;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameBuffer;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesMetadata;
import com.google.android.gms.games.internal.GamesClientImpl;

/* JADX INFO: loaded from: classes.dex */
public final class GamesMetadataImpl implements GamesMetadata {

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl$10, reason: invalid class name */
    class AnonymousClass10 extends LoadExtendedGamesImpl {
        final /* synthetic */ String Ka;
        final /* synthetic */ int Kb;
        final /* synthetic */ boolean Kc;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a((a.d<GamesMetadata.LoadExtendedGamesResult>) this, this.Ka, this.Kb, false, true, false, this.Kc);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl$11, reason: invalid class name */
    class AnonymousClass11 extends LoadExtendedGamesImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ String Ka;
        final /* synthetic */ int Kb;
        final /* synthetic */ boolean Kc;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a((a.d<GamesMetadata.LoadExtendedGamesResult>) this, this.Ka, this.Kb, true, false, this.JQ, this.Kc);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl$12, reason: invalid class name */
    class AnonymousClass12 extends LoadExtendedGamesImpl {
        final /* synthetic */ String Ka;
        final /* synthetic */ int Kb;
        final /* synthetic */ boolean Kc;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a((a.d<GamesMetadata.LoadExtendedGamesResult>) this, this.Ka, this.Kb, true, true, false, this.Kc);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl$13, reason: invalid class name */
    class AnonymousClass13 extends LoadExtendedGamesImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ int Kb;
        final /* synthetic */ String Kd;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.c(this, this.Kd, this.Kb, false, this.JQ);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl$14, reason: invalid class name */
    class AnonymousClass14 extends LoadExtendedGamesImpl {
        final /* synthetic */ int Kb;
        final /* synthetic */ String Kd;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.c(this, this.Kd, this.Kb, true, false);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl$15, reason: invalid class name */
    class AnonymousClass15 extends LoadGameSearchSuggestionsImpl {
        final /* synthetic */ String Kd;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.k(this, this.Kd);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl$2, reason: invalid class name */
    class AnonymousClass2 extends LoadExtendedGamesImpl {
        final /* synthetic */ String JT;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.i(this, this.JT);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl$3, reason: invalid class name */
    class AnonymousClass3 extends LoadGameInstancesImpl {
        final /* synthetic */ String JT;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.j(this, this.JT);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl$4, reason: invalid class name */
    class AnonymousClass4 extends LoadExtendedGamesImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ int Kb;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.b(this, null, this.Kb, false, this.JQ);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl$5, reason: invalid class name */
    class AnonymousClass5 extends LoadExtendedGamesImpl {
        final /* synthetic */ int Kb;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.b(this, null, this.Kb, true, false);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl$6, reason: invalid class name */
    class AnonymousClass6 extends LoadExtendedGamesImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ String JS;
        final /* synthetic */ int Kb;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.b(this, this.JS, this.Kb, false, this.JQ);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl$7, reason: invalid class name */
    class AnonymousClass7 extends LoadExtendedGamesImpl {
        final /* synthetic */ String JS;
        final /* synthetic */ int Kb;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.b(this, this.JS, this.Kb, true, false);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl$8, reason: invalid class name */
    class AnonymousClass8 extends LoadExtendedGamesImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ int Kb;
        final /* synthetic */ int Ke;
        final /* synthetic */ boolean Kf;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a(this, this.Kb, this.Ke, this.Kf, this.JQ);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.GamesMetadataImpl$9, reason: invalid class name */
    class AnonymousClass9 extends LoadExtendedGamesImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ String Ka;
        final /* synthetic */ int Kb;
        final /* synthetic */ boolean Kc;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a((a.d<GamesMetadata.LoadExtendedGamesResult>) this, this.Ka, this.Kb, false, false, this.JQ, this.Kc);
        }
    }

    private static abstract class LoadExtendedGamesImpl extends Games.BaseGamesApiMethodImpl<GamesMetadata.LoadExtendedGamesResult> {
        private LoadExtendedGamesImpl() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: y, reason: merged with bridge method [inline-methods] */
        public GamesMetadata.LoadExtendedGamesResult d(final Status status) {
            return new GamesMetadata.LoadExtendedGamesResult() { // from class: com.google.android.gms.games.internal.api.GamesMetadataImpl.LoadExtendedGamesImpl.1
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

    private static abstract class LoadGameInstancesImpl extends Games.BaseGamesApiMethodImpl<GamesMetadata.LoadGameInstancesResult> {
        private LoadGameInstancesImpl() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: z, reason: merged with bridge method [inline-methods] */
        public GamesMetadata.LoadGameInstancesResult d(final Status status) {
            return new GamesMetadata.LoadGameInstancesResult() { // from class: com.google.android.gms.games.internal.api.GamesMetadataImpl.LoadGameInstancesImpl.1
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

    private static abstract class LoadGameSearchSuggestionsImpl extends Games.BaseGamesApiMethodImpl<GamesMetadata.LoadGameSearchSuggestionsResult> {
        private LoadGameSearchSuggestionsImpl() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: A, reason: merged with bridge method [inline-methods] */
        public GamesMetadata.LoadGameSearchSuggestionsResult d(final Status status) {
            return new GamesMetadata.LoadGameSearchSuggestionsResult() { // from class: com.google.android.gms.games.internal.api.GamesMetadataImpl.LoadGameSearchSuggestionsImpl.1
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

    private static abstract class LoadGamesImpl extends Games.BaseGamesApiMethodImpl<GamesMetadata.LoadGamesResult> {
        private LoadGamesImpl() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: B, reason: merged with bridge method [inline-methods] */
        public GamesMetadata.LoadGamesResult d(final Status status) {
            return new GamesMetadata.LoadGamesResult() { // from class: com.google.android.gms.games.internal.api.GamesMetadataImpl.LoadGamesImpl.1
                @Override // com.google.android.gms.games.GamesMetadata.LoadGamesResult
                public GameBuffer getGames() {
                    return new GameBuffer(DataHolder.empty(14));
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

    @Override // com.google.android.gms.games.GamesMetadata
    public Game getCurrentGame(GoogleApiClient apiClient) {
        return Games.c(apiClient).go();
    }

    @Override // com.google.android.gms.games.GamesMetadata
    public PendingResult<GamesMetadata.LoadGamesResult> loadGame(GoogleApiClient apiClient) {
        return apiClient.a(new LoadGamesImpl() { // from class: com.google.android.gms.games.internal.api.GamesMetadataImpl.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.g(this);
            }
        });
    }
}
