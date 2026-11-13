package com.google.android.gms.drive.query;

import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;
import com.google.android.gms.drive.metadata.SearchableMetadataField;
import com.google.android.gms.drive.metadata.SearchableOrderedMetadataField;
import com.google.android.gms.internal.gs;
import com.google.android.gms.internal.gt;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public class SearchableField {
    public static final SearchableMetadataField<String> TITLE = gs.Go;
    public static final SearchableMetadataField<String> MIME_TYPE = gs.Gh;
    public static final SearchableMetadataField<Boolean> TRASHED = gs.Gp;
    public static final SearchableCollectionMetadataField<DriveId> PARENTS = gs.Gk;
    public static final SearchableOrderedMetadataField<Date> GE = gt.Gw;
    public static final SearchableMetadataField<Boolean> STARRED = gs.Gm;
    public static final SearchableOrderedMetadataField<Date> MODIFIED_DATE = gt.Gu;
    public static final SearchableOrderedMetadataField<Date> LAST_VIEWED_BY_ME = gt.Gt;
    public static final SearchableMetadataField<Boolean> IS_PINNED = gs.Gc;
}
