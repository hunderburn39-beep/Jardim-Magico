package com.google.android.gms.internal;

import android.graphics.Bitmap;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;
import com.google.android.gms.drive.metadata.SearchableMetadataField;
import com.google.android.gms.drive.metadata.SortableMetadataField;
import com.google.android.gms.plus.PlusShare;

/* JADX INFO: loaded from: classes.dex */
public class gs {
    public static final MetadataField<DriveId> FR = gu.Gx;
    public static final MetadataField<String> FS = new com.google.android.gms.drive.metadata.internal.j("alternateLink", 4300000);
    public static final MetadataField<String> FT = new com.google.android.gms.drive.metadata.internal.j(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, 4300000);
    public static final MetadataField<String> FU = new com.google.android.gms.drive.metadata.internal.j("embedLink", 4300000);
    public static final MetadataField<String> FV = new com.google.android.gms.drive.metadata.internal.j("fileExtension", 4300000);
    public static final MetadataField<Long> FW = new com.google.android.gms.drive.metadata.internal.e("fileSize", 4300000);
    public static final MetadataField<Boolean> FX = new com.google.android.gms.drive.metadata.internal.a("hasThumbnail", 4300000);
    public static final MetadataField<String> FY = new com.google.android.gms.drive.metadata.internal.j("indexableText", 4300000);
    public static final MetadataField<Boolean> FZ = new com.google.android.gms.drive.metadata.internal.a("isAppData", 4300000);
    public static final MetadataField<Boolean> Ga = new com.google.android.gms.drive.metadata.internal.a("isCopyable", 4300000);
    public static final MetadataField<Boolean> Gb = new com.google.android.gms.drive.metadata.internal.a("isEditable", 4100000);
    public static final a Gc = new a("isPinned", 4100000);
    public static final MetadataField<Boolean> Gd = new com.google.android.gms.drive.metadata.internal.a("isRestricted", 4300000);
    public static final MetadataField<Boolean> Ge = new com.google.android.gms.drive.metadata.internal.a("isShared", 4300000);
    public static final MetadataField<Boolean> Gf = new com.google.android.gms.drive.metadata.internal.a("isTrashable", 4400000);
    public static final MetadataField<Boolean> Gg = new com.google.android.gms.drive.metadata.internal.a("isViewed", 4300000);
    public static final b Gh = new b("mimeType", 4100000);
    public static final MetadataField<String> Gi = new com.google.android.gms.drive.metadata.internal.j("originalFilename", 4300000);
    public static final com.google.android.gms.drive.metadata.b<String> Gj = new com.google.android.gms.drive.metadata.internal.i("ownerNames", 4300000);
    public static final c Gk = new c("parents", 4100000);
    public static final d Gl = new d("quotaBytesUsed", 4300000);
    public static final e Gm = new e("starred", 4100000);
    public static final MetadataField<Bitmap> Gn = new com.google.android.gms.drive.metadata.internal.h<Bitmap>("thumbnail", 4400000) { // from class: com.google.android.gms.internal.gs.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.drive.metadata.a
        /* JADX INFO: renamed from: i, reason: merged with bridge method [inline-methods] */
        public Bitmap b(DataHolder dataHolder, int i, int i2) {
            throw new IllegalStateException("Thumbnail field is write only");
        }
    };
    public static final f Go = new f("title", 4100000);
    public static final g Gp = new g("trashed", 4100000);
    public static final MetadataField<String> Gq = new com.google.android.gms.drive.metadata.internal.j("webContentLink", 4300000);
    public static final MetadataField<String> Gr = new com.google.android.gms.drive.metadata.internal.j("webViewLink", 4300000);

    public static class a extends com.google.android.gms.drive.metadata.internal.a implements SearchableMetadataField<Boolean> {
        public a(String str, int i) {
            super(str, i);
        }
    }

    public static class b extends com.google.android.gms.drive.metadata.internal.j implements SearchableMetadataField<String> {
        public b(String str, int i) {
            super(str, i);
        }
    }

    public static class c extends com.google.android.gms.drive.metadata.internal.g<DriveId> implements SearchableCollectionMetadataField<DriveId> {
        public c(String str, int i) {
            super(str, i);
        }
    }

    public static class d extends com.google.android.gms.drive.metadata.internal.e implements SortableMetadataField<Long> {
        public d(String str, int i) {
            super(str, i);
        }
    }

    public static class e extends com.google.android.gms.drive.metadata.internal.a implements SearchableMetadataField<Boolean> {
        public e(String str, int i) {
            super(str, i);
        }
    }

    public static class f extends com.google.android.gms.drive.metadata.internal.j implements SearchableMetadataField<String>, SortableMetadataField<String> {
        public f(String str, int i) {
            super(str, i);
        }
    }

    public static class g extends com.google.android.gms.drive.metadata.internal.a implements SearchableMetadataField<Boolean> {
        public g(String str, int i) {
            super(str, i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.drive.metadata.internal.a, com.google.android.gms.drive.metadata.a
        /* JADX INFO: renamed from: d */
        public Boolean b(DataHolder dataHolder, int i, int i2) {
            return Boolean.valueOf(dataHolder.getInteger(getName(), i, i2) != 0);
        }
    }
}
