package com.google.android.gms.games.internal.api;

import android.content.Intent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.games.leaderboard.ScoreSubmissionData;

/* JADX INFO: loaded from: classes.dex */
public final class LeaderboardsImpl implements Leaderboards {

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.LeaderboardsImpl$10, reason: invalid class name */
    class AnonymousClass10 extends LoadScoresImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ String JT;
        final /* synthetic */ String Kp;
        final /* synthetic */ int Kq;
        final /* synthetic */ int Kr;
        final /* synthetic */ int Ks;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a(this, this.JT, this.Kp, this.Kq, this.Kr, this.Ks, this.JQ);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.LeaderboardsImpl$11, reason: invalid class name */
    class AnonymousClass11 extends LoadScoresImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ String JT;
        final /* synthetic */ String Kp;
        final /* synthetic */ int Kq;
        final /* synthetic */ int Kr;
        final /* synthetic */ int Ks;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.b(this, this.JT, this.Kp, this.Kq, this.Kr, this.Ks, this.JQ);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.LeaderboardsImpl$8, reason: invalid class name */
    class AnonymousClass8 extends LoadMetadataImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ String JT;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.b(this, this.JT, this.JQ);
        }
    }

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.LeaderboardsImpl$9, reason: invalid class name */
    class AnonymousClass9 extends LoadMetadataImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ String JT;
        final /* synthetic */ String Kp;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.a(this, this.JT, this.Kp, this.JQ);
        }
    }

    private static abstract class LoadMetadataImpl extends Games.BaseGamesApiMethodImpl<Leaderboards.LeaderboardMetadataResult> {
        private LoadMetadataImpl() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: D, reason: merged with bridge method [inline-methods] */
        public Leaderboards.LeaderboardMetadataResult d(final Status status) {
            return new Leaderboards.LeaderboardMetadataResult() { // from class: com.google.android.gms.games.internal.api.LeaderboardsImpl.LoadMetadataImpl.1
                @Override // com.google.android.gms.games.leaderboard.Leaderboards.LeaderboardMetadataResult
                public LeaderboardBuffer getLeaderboards() {
                    return new LeaderboardBuffer(DataHolder.empty(14));
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

    private static abstract class LoadPlayerScoreImpl extends Games.BaseGamesApiMethodImpl<Leaderboards.LoadPlayerScoreResult> {
        private LoadPlayerScoreImpl() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: E, reason: merged with bridge method [inline-methods] */
        public Leaderboards.LoadPlayerScoreResult d(final Status status) {
            return new Leaderboards.LoadPlayerScoreResult() { // from class: com.google.android.gms.games.internal.api.LeaderboardsImpl.LoadPlayerScoreImpl.1
                @Override // com.google.android.gms.games.leaderboard.Leaderboards.LoadPlayerScoreResult
                public LeaderboardScore getScore() {
                    return null;
                }

                @Override // com.google.android.gms.common.api.Result
                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    private static abstract class LoadScoresImpl extends Games.BaseGamesApiMethodImpl<Leaderboards.LoadScoresResult> {
        private LoadScoresImpl() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: F, reason: merged with bridge method [inline-methods] */
        public Leaderboards.LoadScoresResult d(final Status status) {
            return new Leaderboards.LoadScoresResult() { // from class: com.google.android.gms.games.internal.api.LeaderboardsImpl.LoadScoresImpl.1
                @Override // com.google.android.gms.games.leaderboard.Leaderboards.LoadScoresResult
                public Leaderboard getLeaderboard() {
                    return null;
                }

                @Override // com.google.android.gms.games.leaderboard.Leaderboards.LoadScoresResult
                public LeaderboardScoreBuffer getScores() {
                    return new LeaderboardScoreBuffer(DataHolder.empty(14));
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

    protected static abstract class SubmitScoreImpl extends Games.BaseGamesApiMethodImpl<Leaderboards.SubmitScoreResult> {
        protected SubmitScoreImpl() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: G, reason: merged with bridge method [inline-methods] */
        public Leaderboards.SubmitScoreResult d(final Status status) {
            return new Leaderboards.SubmitScoreResult() { // from class: com.google.android.gms.games.internal.api.LeaderboardsImpl.SubmitScoreImpl.1
                @Override // com.google.android.gms.games.leaderboard.Leaderboards.SubmitScoreResult
                public ScoreSubmissionData getScoreData() {
                    return new ScoreSubmissionData(DataHolder.empty(14));
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

    @Override // com.google.android.gms.games.leaderboard.Leaderboards
    public Intent getAllLeaderboardsIntent(GoogleApiClient apiClient) {
        return Games.c(apiClient).gp();
    }

    @Override // com.google.android.gms.games.leaderboard.Leaderboards
    public Intent getLeaderboardIntent(GoogleApiClient apiClient, String leaderboardId) {
        return Games.c(apiClient).aA(leaderboardId);
    }

    @Override // com.google.android.gms.games.leaderboard.Leaderboards
    public PendingResult<Leaderboards.LoadPlayerScoreResult> loadCurrentPlayerLeaderboardScore(GoogleApiClient apiClient, final String leaderboardId, final int span, final int leaderboardCollection) {
        return apiClient.a(new LoadPlayerScoreImpl() { // from class: com.google.android.gms.games.internal.api.LeaderboardsImpl.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, (String) null, leaderboardId, span, leaderboardCollection);
            }
        });
    }

    @Override // com.google.android.gms.games.leaderboard.Leaderboards
    public PendingResult<Leaderboards.LeaderboardMetadataResult> loadLeaderboardMetadata(GoogleApiClient apiClient, final String leaderboardId, final boolean forceReload) {
        return apiClient.a(new LoadMetadataImpl() { // from class: com.google.android.gms.games.internal.api.LeaderboardsImpl.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, leaderboardId, forceReload);
            }
        });
    }

    @Override // com.google.android.gms.games.leaderboard.Leaderboards
    public PendingResult<Leaderboards.LeaderboardMetadataResult> loadLeaderboardMetadata(GoogleApiClient apiClient, final boolean forceReload) {
        return apiClient.a(new LoadMetadataImpl() { // from class: com.google.android.gms.games.internal.api.LeaderboardsImpl.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.b(this, forceReload);
            }
        });
    }

    @Override // com.google.android.gms.games.leaderboard.Leaderboards
    public PendingResult<Leaderboards.LoadScoresResult> loadMoreScores(GoogleApiClient apiClient, final LeaderboardScoreBuffer buffer, final int maxResults, final int pageDirection) {
        return apiClient.a(new LoadScoresImpl() { // from class: com.google.android.gms.games.internal.api.LeaderboardsImpl.6
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, buffer, maxResults, pageDirection);
            }
        });
    }

    @Override // com.google.android.gms.games.leaderboard.Leaderboards
    public PendingResult<Leaderboards.LoadScoresResult> loadPlayerCenteredScores(GoogleApiClient apiClient, String leaderboardId, int span, int leaderboardCollection, int maxResults) {
        return loadPlayerCenteredScores(apiClient, leaderboardId, span, leaderboardCollection, maxResults, false);
    }

    @Override // com.google.android.gms.games.leaderboard.Leaderboards
    public PendingResult<Leaderboards.LoadScoresResult> loadPlayerCenteredScores(GoogleApiClient apiClient, final String leaderboardId, final int span, final int leaderboardCollection, final int maxResults, final boolean forceReload) {
        return apiClient.a(new LoadScoresImpl() { // from class: com.google.android.gms.games.internal.api.LeaderboardsImpl.5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.b(this, leaderboardId, span, leaderboardCollection, maxResults, forceReload);
            }
        });
    }

    @Override // com.google.android.gms.games.leaderboard.Leaderboards
    public PendingResult<Leaderboards.LoadScoresResult> loadTopScores(GoogleApiClient apiClient, String leaderboardId, int span, int leaderboardCollection, int maxResults) {
        return loadTopScores(apiClient, leaderboardId, span, leaderboardCollection, maxResults, false);
    }

    @Override // com.google.android.gms.games.leaderboard.Leaderboards
    public PendingResult<Leaderboards.LoadScoresResult> loadTopScores(GoogleApiClient apiClient, final String leaderboardId, final int span, final int leaderboardCollection, final int maxResults, final boolean forceReload) {
        return apiClient.a(new LoadScoresImpl() { // from class: com.google.android.gms.games.internal.api.LeaderboardsImpl.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, leaderboardId, span, leaderboardCollection, maxResults, forceReload);
            }
        });
    }

    @Override // com.google.android.gms.games.leaderboard.Leaderboards
    public void submitScore(GoogleApiClient apiClient, String leaderboardId, long score) {
        submitScore(apiClient, leaderboardId, score, null);
    }

    @Override // com.google.android.gms.games.leaderboard.Leaderboards
    public void submitScore(GoogleApiClient apiClient, String leaderboardId, long score, String scoreTag) {
        Games.c(apiClient).a((a.d<Leaderboards.SubmitScoreResult>) null, leaderboardId, score, scoreTag);
    }

    @Override // com.google.android.gms.games.leaderboard.Leaderboards
    public PendingResult<Leaderboards.SubmitScoreResult> submitScoreImmediate(GoogleApiClient apiClient, String leaderboardId, long score) {
        return submitScoreImmediate(apiClient, leaderboardId, score, null);
    }

    @Override // com.google.android.gms.games.leaderboard.Leaderboards
    public PendingResult<Leaderboards.SubmitScoreResult> submitScoreImmediate(GoogleApiClient apiClient, final String leaderboardId, final long score, final String scoreTag) {
        return apiClient.b(new SubmitScoreImpl() { // from class: com.google.android.gms.games.internal.api.LeaderboardsImpl.7
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, leaderboardId, score, scoreTag);
            }
        });
    }
}
