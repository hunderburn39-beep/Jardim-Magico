package com.google.android.gms.games.internal.api;

import android.content.Intent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.achievement.AchievementBuffer;
import com.google.android.gms.games.achievement.Achievements;
import com.google.android.gms.games.internal.GamesClientImpl;

/* JADX INFO: loaded from: classes.dex */
public final class AchievementsImpl implements Achievements {

    /* JADX INFO: renamed from: com.google.android.gms.games.internal.api.AchievementsImpl$10, reason: invalid class name */
    class AnonymousClass10 extends LoadImpl {
        final /* synthetic */ boolean JQ;
        final /* synthetic */ String JS;
        final /* synthetic */ String JT;

        @Override // com.google.android.gms.common.api.a.b
        public void a(GamesClientImpl gamesClientImpl) {
            gamesClientImpl.b(this, this.JS, this.JT, this.JQ);
        }
    }

    private static abstract class LoadImpl extends Games.BaseGamesApiMethodImpl<Achievements.LoadAchievementsResult> {
        private LoadImpl() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: t, reason: merged with bridge method [inline-methods] */
        public Achievements.LoadAchievementsResult d(final Status status) {
            return new Achievements.LoadAchievementsResult() { // from class: com.google.android.gms.games.internal.api.AchievementsImpl.LoadImpl.1
                @Override // com.google.android.gms.games.achievement.Achievements.LoadAchievementsResult
                public AchievementBuffer getAchievements() {
                    return new AchievementBuffer(DataHolder.empty(14));
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

    private static abstract class UpdateImpl extends Games.BaseGamesApiMethodImpl<Achievements.UpdateAchievementResult> {
        private final String wp;

        public UpdateImpl(String id) {
            this.wp = id;
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: u, reason: merged with bridge method [inline-methods] */
        public Achievements.UpdateAchievementResult d(final Status status) {
            return new Achievements.UpdateAchievementResult() { // from class: com.google.android.gms.games.internal.api.AchievementsImpl.UpdateImpl.1
                @Override // com.google.android.gms.games.achievement.Achievements.UpdateAchievementResult
                public String getAchievementId() {
                    return UpdateImpl.this.wp;
                }

                @Override // com.google.android.gms.common.api.Result
                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    @Override // com.google.android.gms.games.achievement.Achievements
    public Intent getAchievementsIntent(GoogleApiClient apiClient) {
        return Games.c(apiClient).gq();
    }

    @Override // com.google.android.gms.games.achievement.Achievements
    public void increment(GoogleApiClient apiClient, final String id, final int numSteps) {
        apiClient.b(new UpdateImpl(id) { // from class: com.google.android.gms.games.internal.api.AchievementsImpl.6
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a((a.d<Achievements.UpdateAchievementResult>) null, id, numSteps);
            }
        });
    }

    @Override // com.google.android.gms.games.achievement.Achievements
    public PendingResult<Achievements.UpdateAchievementResult> incrementImmediate(GoogleApiClient apiClient, final String id, final int numSteps) {
        return apiClient.b(new UpdateImpl(id) { // from class: com.google.android.gms.games.internal.api.AchievementsImpl.7
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.a(this, id, numSteps);
            }
        });
    }

    @Override // com.google.android.gms.games.achievement.Achievements
    public PendingResult<Achievements.LoadAchievementsResult> load(GoogleApiClient apiClient, final boolean forceReload) {
        return apiClient.a(new LoadImpl() { // from class: com.google.android.gms.games.internal.api.AchievementsImpl.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.c(this, forceReload);
            }
        });
    }

    @Override // com.google.android.gms.games.achievement.Achievements
    public void reveal(GoogleApiClient apiClient, final String id) {
        apiClient.b(new UpdateImpl(id) { // from class: com.google.android.gms.games.internal.api.AchievementsImpl.2
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.b((a.d<Achievements.UpdateAchievementResult>) null, id);
            }
        });
    }

    @Override // com.google.android.gms.games.achievement.Achievements
    public PendingResult<Achievements.UpdateAchievementResult> revealImmediate(GoogleApiClient apiClient, final String id) {
        return apiClient.b(new UpdateImpl(id) { // from class: com.google.android.gms.games.internal.api.AchievementsImpl.3
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.b(this, id);
            }
        });
    }

    @Override // com.google.android.gms.games.achievement.Achievements
    public void setSteps(GoogleApiClient apiClient, final String id, final int numSteps) {
        apiClient.b(new UpdateImpl(id) { // from class: com.google.android.gms.games.internal.api.AchievementsImpl.8
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.b((a.d<Achievements.UpdateAchievementResult>) null, id, numSteps);
            }
        });
    }

    @Override // com.google.android.gms.games.achievement.Achievements
    public PendingResult<Achievements.UpdateAchievementResult> setStepsImmediate(GoogleApiClient apiClient, final String id, final int numSteps) {
        return apiClient.b(new UpdateImpl(id) { // from class: com.google.android.gms.games.internal.api.AchievementsImpl.9
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.b(this, id, numSteps);
            }
        });
    }

    @Override // com.google.android.gms.games.achievement.Achievements
    public void unlock(GoogleApiClient apiClient, final String id) {
        apiClient.b(new UpdateImpl(id) { // from class: com.google.android.gms.games.internal.api.AchievementsImpl.4
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.c((a.d<Achievements.UpdateAchievementResult>) null, id);
            }
        });
    }

    @Override // com.google.android.gms.games.achievement.Achievements
    public PendingResult<Achievements.UpdateAchievementResult> unlockImmediate(GoogleApiClient apiClient, final String id) {
        return apiClient.b(new UpdateImpl(id) { // from class: com.google.android.gms.games.internal.api.AchievementsImpl.5
            @Override // com.google.android.gms.common.api.a.b
            public void a(GamesClientImpl gamesClientImpl) {
                gamesClientImpl.c(this, id);
            }
        });
    }
}
