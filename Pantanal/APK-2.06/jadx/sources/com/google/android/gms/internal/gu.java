package com.google.android.gms.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.DriveId;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class gu extends com.google.android.gms.drive.metadata.internal.h<DriveId> {
    public static final gu Gx = new gu();

    private gu() {
        super("driveId", Arrays.asList("sqlId", "resourceId"), 4100000);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.drive.metadata.a
    /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
    public DriveId b(DataHolder dataHolder, int i, int i2) {
        long j = dataHolder.getMetadata().getLong("dbInstanceId");
        String string = dataHolder.getString("resourceId", i, i2);
        if (string != null && string.startsWith("generated-android-")) {
            string = null;
        }
        return new DriveId(string, Long.valueOf(dataHolder.getLong("sqlId", i, i2)).longValue(), j);
    }
}
