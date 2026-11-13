package com.google.android.gms.drive;

import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.internal.gs;
import com.google.android.gms.internal.gt;
import com.google.android.gms.internal.gv;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public abstract class Metadata implements Freezable<Metadata> {
    public static final int CONTENT_AVAILABLE_LOCALLY = 1;
    public static final int CONTENT_NOT_AVAILABLE_LOCALLY = 0;

    protected abstract <T> T a(MetadataField<T> metadataField);

    public String getAlternateLink() {
        return (String) a(gs.FS);
    }

    public int getContentAvailability() {
        Integer num = (Integer) a(gv.Gy);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public Date getCreatedDate() {
        return (Date) a(gt.Gs);
    }

    public String getDescription() {
        return (String) a(gs.FT);
    }

    public DriveId getDriveId() {
        return (DriveId) a(gs.FR);
    }

    public String getEmbedLink() {
        return (String) a(gs.FU);
    }

    public String getFileExtension() {
        return (String) a(gs.FV);
    }

    public long getFileSize() {
        return ((Long) a(gs.FW)).longValue();
    }

    public Date getLastViewedByMeDate() {
        return (Date) a(gt.Gt);
    }

    public String getMimeType() {
        return (String) a(gs.Gh);
    }

    public Date getModifiedByMeDate() {
        return (Date) a(gt.Gv);
    }

    public Date getModifiedDate() {
        return (Date) a(gt.Gu);
    }

    public String getOriginalFilename() {
        return (String) a(gs.Gi);
    }

    public long getQuotaBytesUsed() {
        return ((Long) a(gs.Gl)).longValue();
    }

    public Date getSharedWithMeDate() {
        return (Date) a(gt.Gw);
    }

    public String getTitle() {
        return (String) a(gs.Go);
    }

    public String getWebContentLink() {
        return (String) a(gs.Gq);
    }

    public String getWebViewLink() {
        return (String) a(gs.Gr);
    }

    public boolean isEditable() {
        Boolean bool = (Boolean) a(gs.Gb);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isFolder() {
        return DriveFolder.MIME_TYPE.equals(getMimeType());
    }

    public boolean isInAppFolder() {
        Boolean bool = (Boolean) a(gs.FZ);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isPinnable() {
        Boolean bool = (Boolean) a(gv.Gz);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isPinned() {
        Boolean bool = (Boolean) a(gs.Gc);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isRestricted() {
        Boolean bool = (Boolean) a(gs.Gd);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isShared() {
        Boolean bool = (Boolean) a(gs.Ge);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isStarred() {
        Boolean bool = (Boolean) a(gs.Gm);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isTrashed() {
        Boolean bool = (Boolean) a(gs.Gp);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isViewed() {
        Boolean bool = (Boolean) a(gs.Gg);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }
}
