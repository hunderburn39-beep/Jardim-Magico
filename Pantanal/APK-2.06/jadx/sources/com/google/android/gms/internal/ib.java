package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import java.util.Collection;

/* JADX INFO: loaded from: classes.dex */
public final class ib implements People {

    private static abstract class a extends Plus.a<People.LoadPeopleResult> {
        private a() {
        }

        @Override // com.google.android.gms.common.api.a.AbstractC0032a
        /* JADX INFO: renamed from: ab, reason: merged with bridge method [inline-methods] */
        public People.LoadPeopleResult d(final Status status) {
            return new People.LoadPeopleResult() { // from class: com.google.android.gms.internal.ib.a.1
                @Override // com.google.android.gms.plus.People.LoadPeopleResult
                public String getNextPageToken() {
                    return null;
                }

                @Override // com.google.android.gms.plus.People.LoadPeopleResult
                public PersonBuffer getPersonBuffer() {
                    return null;
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

    @Override // com.google.android.gms.plus.People
    public Person getCurrentPerson(GoogleApiClient googleApiClient) {
        return Plus.a(googleApiClient, Plus.wx).getCurrentPerson();
    }

    @Override // com.google.android.gms.plus.People
    public PendingResult<People.LoadPeopleResult> load(GoogleApiClient googleApiClient, final Collection<String> personIds) {
        return googleApiClient.a(new a() { // from class: com.google.android.gms.internal.ib.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(com.google.android.gms.plus.internal.e eVar) {
                eVar.a(this, personIds);
            }
        });
    }

    @Override // com.google.android.gms.plus.People
    public PendingResult<People.LoadPeopleResult> load(GoogleApiClient googleApiClient, final String... personIds) {
        return googleApiClient.a(new a() { // from class: com.google.android.gms.internal.ib.5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(com.google.android.gms.plus.internal.e eVar) {
                eVar.d(this, personIds);
            }
        });
    }

    @Override // com.google.android.gms.plus.People
    public PendingResult<People.LoadPeopleResult> loadConnected(GoogleApiClient googleApiClient) {
        return googleApiClient.a(new a() { // from class: com.google.android.gms.internal.ib.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(com.google.android.gms.plus.internal.e eVar) {
                eVar.m(this);
            }
        });
    }

    @Override // com.google.android.gms.plus.People
    public PendingResult<People.LoadPeopleResult> loadVisible(GoogleApiClient googleApiClient, final int orderBy, final String pageToken) {
        return googleApiClient.a(new a() { // from class: com.google.android.gms.internal.ib.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(com.google.android.gms.plus.internal.e eVar) {
                a(eVar.a(this, orderBy, pageToken));
            }
        });
    }

    @Override // com.google.android.gms.plus.People
    public PendingResult<People.LoadPeopleResult> loadVisible(GoogleApiClient googleApiClient, final String pageToken) {
        return googleApiClient.a(new a() { // from class: com.google.android.gms.internal.ib.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.common.api.a.b
            public void a(com.google.android.gms.plus.internal.e eVar) {
                a(eVar.o(this, pageToken));
            }
        });
    }
}
