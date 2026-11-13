package com.google.android.gms.internal;

import android.os.Parcel;
import com.fyber.ads.banners.BannerAd;
import com.google.ads.AdSize;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.moments.ItemScope;
import com.tapjoy.TapjoyConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.anddev.andengine.entity.layer.tiled.tmx.util.constants.TMXConstants;
import org.anddev.andengine.opengl.texture.compressed.pvr.PVRTexture;
import org.anddev.andengine.util.constants.TimeConstants;

/* JADX INFO: loaded from: classes.dex */
public final class ic extends ga implements SafeParcelable, ItemScope {
    public static final id CREATOR = new id();
    private static final HashMap<String, ga.a<?, ?>> UI = new HashMap<>();
    private String HD;
    private double NX;
    private double NY;
    private String Rd;
    private final Set<Integer> UJ;
    private ic UK;
    private List<String> UL;
    private ic UM;
    private String UN;
    private String UO;
    private String UP;
    private List<ic> UQ;
    private int UR;
    private List<ic> US;
    private ic UT;
    private List<ic> UU;
    private String UV;
    private String UW;
    private ic UX;
    private String UY;
    private String UZ;
    private String VA;
    private String VB;
    private String VC;
    private String VD;
    private List<ic> Va;
    private String Vb;
    private String Vc;
    private String Vd;
    private String Ve;
    private String Vf;
    private String Vg;
    private String Vh;
    private String Vi;
    private ic Vj;
    private String Vk;
    private String Vl;
    private String Vm;
    private ic Vn;
    private ic Vo;
    private ic Vp;
    private List<ic> Vq;
    private String Vr;
    private String Vs;
    private String Vt;
    private String Vu;
    private ic Vv;
    private String Vw;
    private String Vx;
    private String Vy;
    private ic Vz;
    private String lY;
    private String mName;
    private String ro;
    private String wp;
    private final int xH;

    static {
        UI.put("about", ga.a.a("about", 2, ic.class));
        UI.put("additionalName", ga.a.k("additionalName", 3));
        UI.put("address", ga.a.a("address", 4, ic.class));
        UI.put("addressCountry", ga.a.j("addressCountry", 5));
        UI.put("addressLocality", ga.a.j("addressLocality", 6));
        UI.put("addressRegion", ga.a.j("addressRegion", 7));
        UI.put("associated_media", ga.a.b("associated_media", 8, ic.class));
        UI.put("attendeeCount", ga.a.g("attendeeCount", 9));
        UI.put("attendees", ga.a.b("attendees", 10, ic.class));
        UI.put("audio", ga.a.a("audio", 11, ic.class));
        UI.put("author", ga.a.b("author", 12, ic.class));
        UI.put("bestRating", ga.a.j("bestRating", 13));
        UI.put("birthDate", ga.a.j("birthDate", 14));
        UI.put("byArtist", ga.a.a("byArtist", 15, ic.class));
        UI.put("caption", ga.a.j("caption", 16));
        UI.put("contentSize", ga.a.j("contentSize", 17));
        UI.put("contentUrl", ga.a.j("contentUrl", 18));
        UI.put("contributor", ga.a.b("contributor", 19, ic.class));
        UI.put("dateCreated", ga.a.j("dateCreated", 20));
        UI.put("dateModified", ga.a.j("dateModified", 21));
        UI.put("datePublished", ga.a.j("datePublished", 22));
        UI.put(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, ga.a.j(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, 23));
        UI.put("duration", ga.a.j("duration", 24));
        UI.put("embedUrl", ga.a.j("embedUrl", 25));
        UI.put("endDate", ga.a.j("endDate", 26));
        UI.put("familyName", ga.a.j("familyName", 27));
        UI.put("gender", ga.a.j("gender", 28));
        UI.put("geo", ga.a.a("geo", 29, ic.class));
        UI.put("givenName", ga.a.j("givenName", 30));
        UI.put("height", ga.a.j("height", 31));
        UI.put(TMXConstants.TAG_TILE_ATTRIBUTE_ID, ga.a.j(TMXConstants.TAG_TILE_ATTRIBUTE_ID, 32));
        UI.put(TMXConstants.TAG_IMAGE, ga.a.j(TMXConstants.TAG_IMAGE, 33));
        UI.put("inAlbum", ga.a.a("inAlbum", 34, ic.class));
        UI.put("latitude", ga.a.h("latitude", 36));
        UI.put("location", ga.a.a("location", 37, ic.class));
        UI.put("longitude", ga.a.h("longitude", 38));
        UI.put("name", ga.a.j("name", 39));
        UI.put("partOfTVSeries", ga.a.a("partOfTVSeries", 40, ic.class));
        UI.put("performers", ga.a.b("performers", 41, ic.class));
        UI.put("playerType", ga.a.j("playerType", 42));
        UI.put("postOfficeBoxNumber", ga.a.j("postOfficeBoxNumber", 43));
        UI.put("postalCode", ga.a.j("postalCode", 44));
        UI.put("ratingValue", ga.a.j("ratingValue", 45));
        UI.put("reviewRating", ga.a.a("reviewRating", 46, ic.class));
        UI.put("startDate", ga.a.j("startDate", 47));
        UI.put("streetAddress", ga.a.j("streetAddress", 48));
        UI.put("text", ga.a.j("text", 49));
        UI.put("thumbnail", ga.a.a("thumbnail", 50, ic.class));
        UI.put(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_THUMBNAIL_URL, ga.a.j(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_THUMBNAIL_URL, 51));
        UI.put("tickerSymbol", ga.a.j("tickerSymbol", 52));
        UI.put("type", ga.a.j("type", 53));
        UI.put("url", ga.a.j("url", 54));
        UI.put("width", ga.a.j("width", 55));
        UI.put("worstRating", ga.a.j("worstRating", 56));
    }

    public ic() {
        this.xH = 1;
        this.UJ = new HashSet();
    }

    ic(Set<Integer> set, int i, ic icVar, List<String> list, ic icVar2, String str, String str2, String str3, List<ic> list2, int i2, List<ic> list3, ic icVar3, List<ic> list4, String str4, String str5, ic icVar4, String str6, String str7, String str8, List<ic> list5, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, ic icVar5, String str18, String str19, String str20, String str21, ic icVar6, double d, ic icVar7, double d2, String str22, ic icVar8, List<ic> list6, String str23, String str24, String str25, String str26, ic icVar9, String str27, String str28, String str29, ic icVar10, String str30, String str31, String str32, String str33, String str34, String str35) {
        this.UJ = set;
        this.xH = i;
        this.UK = icVar;
        this.UL = list;
        this.UM = icVar2;
        this.UN = str;
        this.UO = str2;
        this.UP = str3;
        this.UQ = list2;
        this.UR = i2;
        this.US = list3;
        this.UT = icVar3;
        this.UU = list4;
        this.UV = str4;
        this.UW = str5;
        this.UX = icVar4;
        this.UY = str6;
        this.UZ = str7;
        this.lY = str8;
        this.Va = list5;
        this.Vb = str9;
        this.Vc = str10;
        this.Vd = str11;
        this.HD = str12;
        this.Ve = str13;
        this.Vf = str14;
        this.Vg = str15;
        this.Vh = str16;
        this.Vi = str17;
        this.Vj = icVar5;
        this.Vk = str18;
        this.Vl = str19;
        this.wp = str20;
        this.Vm = str21;
        this.Vn = icVar6;
        this.NX = d;
        this.Vo = icVar7;
        this.NY = d2;
        this.mName = str22;
        this.Vp = icVar8;
        this.Vq = list6;
        this.Vr = str23;
        this.Vs = str24;
        this.Vt = str25;
        this.Vu = str26;
        this.Vv = icVar9;
        this.Vw = str27;
        this.Vx = str28;
        this.Vy = str29;
        this.Vz = icVar10;
        this.VA = str30;
        this.VB = str31;
        this.Rd = str32;
        this.ro = str33;
        this.VC = str34;
        this.VD = str35;
    }

    public ic(Set<Integer> set, ic icVar, List<String> list, ic icVar2, String str, String str2, String str3, List<ic> list2, int i, List<ic> list3, ic icVar3, List<ic> list4, String str4, String str5, ic icVar4, String str6, String str7, String str8, List<ic> list5, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, ic icVar5, String str18, String str19, String str20, String str21, ic icVar6, double d, ic icVar7, double d2, String str22, ic icVar8, List<ic> list6, String str23, String str24, String str25, String str26, ic icVar9, String str27, String str28, String str29, ic icVar10, String str30, String str31, String str32, String str33, String str34, String str35) {
        this.UJ = set;
        this.xH = 1;
        this.UK = icVar;
        this.UL = list;
        this.UM = icVar2;
        this.UN = str;
        this.UO = str2;
        this.UP = str3;
        this.UQ = list2;
        this.UR = i;
        this.US = list3;
        this.UT = icVar3;
        this.UU = list4;
        this.UV = str4;
        this.UW = str5;
        this.UX = icVar4;
        this.UY = str6;
        this.UZ = str7;
        this.lY = str8;
        this.Va = list5;
        this.Vb = str9;
        this.Vc = str10;
        this.Vd = str11;
        this.HD = str12;
        this.Ve = str13;
        this.Vf = str14;
        this.Vg = str15;
        this.Vh = str16;
        this.Vi = str17;
        this.Vj = icVar5;
        this.Vk = str18;
        this.Vl = str19;
        this.wp = str20;
        this.Vm = str21;
        this.Vn = icVar6;
        this.NX = d;
        this.Vo = icVar7;
        this.NY = d2;
        this.mName = str22;
        this.Vp = icVar8;
        this.Vq = list6;
        this.Vr = str23;
        this.Vs = str24;
        this.Vt = str25;
        this.Vu = str26;
        this.Vv = icVar9;
        this.Vw = str27;
        this.Vx = str28;
        this.Vy = str29;
        this.Vz = icVar10;
        this.VA = str30;
        this.VB = str31;
        this.Rd = str32;
        this.ro = str33;
        this.VC = str34;
        this.VD = str35;
    }

    @Override // com.google.android.gms.internal.ga
    protected boolean a(ga.a aVar) {
        return this.UJ.contains(Integer.valueOf(aVar.ff()));
    }

    @Override // com.google.android.gms.internal.ga
    protected Object aq(String str) {
        return null;
    }

    @Override // com.google.android.gms.internal.ga
    protected boolean ar(String str) {
        return false;
    }

    @Override // com.google.android.gms.internal.ga
    protected Object b(ga.a aVar) {
        switch (aVar.ff()) {
            case 2:
                return this.UK;
            case 3:
                return this.UL;
            case 4:
                return this.UM;
            case 5:
                return this.UN;
            case 6:
                return this.UO;
            case 7:
                return this.UP;
            case 8:
                return this.UQ;
            case 9:
                return Integer.valueOf(this.UR);
            case 10:
                return this.US;
            case 11:
                return this.UT;
            case 12:
                return this.UU;
            case 13:
                return this.UV;
            case 14:
                return this.UW;
            case 15:
                return this.UX;
            case 16:
                return this.UY;
            case 17:
                return this.UZ;
            case 18:
                return this.lY;
            case 19:
                return this.Va;
            case TapjoyConstants.MEDIATION_TIMEOUT_INTERVAL /* 20 */:
                return this.Vb;
            case 21:
                return this.Vc;
            case 22:
                return this.Vd;
            case 23:
                return this.HD;
            case TimeConstants.HOURSPERDAY /* 24 */:
                return this.Ve;
            case 25:
                return this.Vf;
            case 26:
                return this.Vg;
            case 27:
                return this.Vh;
            case 28:
                return this.Vi;
            case 29:
                return this.Vj;
            case TimeConstants.DAYSPERMONTH /* 30 */:
                return this.Vk;
            case 31:
                return this.Vl;
            case 32:
                return this.wp;
            case 33:
                return this.Vm;
            case 34:
                return this.Vn;
            case 35:
            default:
                throw new IllegalStateException("Unknown safe parcelable id=" + aVar.ff());
            case 36:
                return Double.valueOf(this.NX);
            case 37:
                return this.Vo;
            case 38:
                return Double.valueOf(this.NY);
            case 39:
                return this.mName;
            case 40:
                return this.Vp;
            case 41:
                return this.Vq;
            case 42:
                return this.Vr;
            case 43:
                return this.Vs;
            case 44:
                return this.Vt;
            case 45:
                return this.Vu;
            case 46:
                return this.Vv;
            case 47:
                return this.Vw;
            case BannerAd.POSITION_TOP /* 48 */:
                return this.Vx;
            case 49:
                return this.Vy;
            case AdSize.PORTRAIT_AD_HEIGHT /* 50 */:
                return this.Vz;
            case 51:
                return this.VA;
            case PVRTexture.PVRTextureHeader.SIZE /* 52 */:
                return this.VB;
            case 53:
                return this.Rd;
            case 54:
                return this.ro;
            case 55:
                return this.VC;
            case 56:
                return this.VD;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        id idVar = CREATOR;
        return 0;
    }

    @Override // com.google.android.gms.internal.ga
    public HashMap<String, ga.a<?, ?>> eY() {
        return UI;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ic)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ic icVar = (ic) obj;
        for (ga.a<?, ?> aVar : UI.values()) {
            if (a(aVar)) {
                if (icVar.a(aVar) && b(aVar).equals(icVar.b(aVar))) {
                }
                return false;
            }
            if (icVar.a(aVar)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public ItemScope getAbout() {
        return this.UK;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public List<String> getAdditionalName() {
        return this.UL;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public ItemScope getAddress() {
        return this.UM;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getAddressCountry() {
        return this.UN;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getAddressLocality() {
        return this.UO;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getAddressRegion() {
        return this.UP;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public List<ItemScope> getAssociated_media() {
        return (ArrayList) this.UQ;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public int getAttendeeCount() {
        return this.UR;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public List<ItemScope> getAttendees() {
        return (ArrayList) this.US;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public ItemScope getAudio() {
        return this.UT;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public List<ItemScope> getAuthor() {
        return (ArrayList) this.UU;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getBestRating() {
        return this.UV;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getBirthDate() {
        return this.UW;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public ItemScope getByArtist() {
        return this.UX;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getCaption() {
        return this.UY;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getContentSize() {
        return this.UZ;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getContentUrl() {
        return this.lY;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public List<ItemScope> getContributor() {
        return (ArrayList) this.Va;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getDateCreated() {
        return this.Vb;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getDateModified() {
        return this.Vc;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getDatePublished() {
        return this.Vd;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getDescription() {
        return this.HD;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getDuration() {
        return this.Ve;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getEmbedUrl() {
        return this.Vf;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getEndDate() {
        return this.Vg;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getFamilyName() {
        return this.Vh;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getGender() {
        return this.Vi;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public ItemScope getGeo() {
        return this.Vj;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getGivenName() {
        return this.Vk;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getHeight() {
        return this.Vl;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getId() {
        return this.wp;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getImage() {
        return this.Vm;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public ItemScope getInAlbum() {
        return this.Vn;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public double getLatitude() {
        return this.NX;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public ItemScope getLocation() {
        return this.Vo;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public double getLongitude() {
        return this.NY;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getName() {
        return this.mName;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public ItemScope getPartOfTVSeries() {
        return this.Vp;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public List<ItemScope> getPerformers() {
        return (ArrayList) this.Vq;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getPlayerType() {
        return this.Vr;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getPostOfficeBoxNumber() {
        return this.Vs;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getPostalCode() {
        return this.Vt;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getRatingValue() {
        return this.Vu;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public ItemScope getReviewRating() {
        return this.Vv;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getStartDate() {
        return this.Vw;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getStreetAddress() {
        return this.Vx;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getText() {
        return this.Vy;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public ItemScope getThumbnail() {
        return this.Vz;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getThumbnailUrl() {
        return this.VA;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getTickerSymbol() {
        return this.VB;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getType() {
        return this.Rd;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getUrl() {
        return this.ro;
    }

    int getVersionCode() {
        return this.xH;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getWidth() {
        return this.VC;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public String getWorstRating() {
        return this.VD;
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasAbout() {
        return this.UJ.contains(2);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasAdditionalName() {
        return this.UJ.contains(3);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasAddress() {
        return this.UJ.contains(4);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasAddressCountry() {
        return this.UJ.contains(5);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasAddressLocality() {
        return this.UJ.contains(6);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasAddressRegion() {
        return this.UJ.contains(7);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasAssociated_media() {
        return this.UJ.contains(8);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasAttendeeCount() {
        return this.UJ.contains(9);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasAttendees() {
        return this.UJ.contains(10);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasAudio() {
        return this.UJ.contains(11);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasAuthor() {
        return this.UJ.contains(12);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasBestRating() {
        return this.UJ.contains(13);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasBirthDate() {
        return this.UJ.contains(14);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasByArtist() {
        return this.UJ.contains(15);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasCaption() {
        return this.UJ.contains(16);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasContentSize() {
        return this.UJ.contains(17);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasContentUrl() {
        return this.UJ.contains(18);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasContributor() {
        return this.UJ.contains(19);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasDateCreated() {
        return this.UJ.contains(20);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasDateModified() {
        return this.UJ.contains(21);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasDatePublished() {
        return this.UJ.contains(22);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasDescription() {
        return this.UJ.contains(23);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasDuration() {
        return this.UJ.contains(24);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasEmbedUrl() {
        return this.UJ.contains(25);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasEndDate() {
        return this.UJ.contains(26);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasFamilyName() {
        return this.UJ.contains(27);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasGender() {
        return this.UJ.contains(28);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasGeo() {
        return this.UJ.contains(29);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasGivenName() {
        return this.UJ.contains(30);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasHeight() {
        return this.UJ.contains(31);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasId() {
        return this.UJ.contains(32);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasImage() {
        return this.UJ.contains(33);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasInAlbum() {
        return this.UJ.contains(34);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasLatitude() {
        return this.UJ.contains(36);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasLocation() {
        return this.UJ.contains(37);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasLongitude() {
        return this.UJ.contains(38);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasName() {
        return this.UJ.contains(39);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasPartOfTVSeries() {
        return this.UJ.contains(40);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasPerformers() {
        return this.UJ.contains(41);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasPlayerType() {
        return this.UJ.contains(42);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasPostOfficeBoxNumber() {
        return this.UJ.contains(43);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasPostalCode() {
        return this.UJ.contains(44);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasRatingValue() {
        return this.UJ.contains(45);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasReviewRating() {
        return this.UJ.contains(46);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasStartDate() {
        return this.UJ.contains(47);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasStreetAddress() {
        return this.UJ.contains(48);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasText() {
        return this.UJ.contains(49);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasThumbnail() {
        return this.UJ.contains(50);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasThumbnailUrl() {
        return this.UJ.contains(51);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasTickerSymbol() {
        return this.UJ.contains(52);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasType() {
        return this.UJ.contains(53);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasUrl() {
        return this.UJ.contains(54);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasWidth() {
        return this.UJ.contains(55);
    }

    @Override // com.google.android.gms.plus.model.moments.ItemScope
    public boolean hasWorstRating() {
        return this.UJ.contains(56);
    }

    public int hashCode() {
        int iHashCode = 0;
        Iterator<ga.a<?, ?>> it = UI.values().iterator();
        while (true) {
            int i = iHashCode;
            if (!it.hasNext()) {
                return i;
            }
            ga.a<?, ?> next = it.next();
            if (a(next)) {
                iHashCode = b(next).hashCode() + i + next.ff();
            } else {
                iHashCode = i;
            }
        }
    }

    @Override // com.google.android.gms.common.data.Freezable
    public boolean isDataValid() {
        return true;
    }

    Set<Integer> ja() {
        return this.UJ;
    }

    ic jb() {
        return this.UK;
    }

    ic jc() {
        return this.UM;
    }

    List<ic> jd() {
        return this.UQ;
    }

    List<ic> je() {
        return this.US;
    }

    ic jf() {
        return this.UT;
    }

    List<ic> jg() {
        return this.UU;
    }

    ic jh() {
        return this.UX;
    }

    List<ic> ji() {
        return this.Va;
    }

    ic jj() {
        return this.Vj;
    }

    ic jk() {
        return this.Vn;
    }

    ic jl() {
        return this.Vo;
    }

    ic jm() {
        return this.Vp;
    }

    List<ic> jn() {
        return this.Vq;
    }

    ic jo() {
        return this.Vv;
    }

    ic jp() {
        return this.Vz;
    }

    @Override // com.google.android.gms.common.data.Freezable
    /* JADX INFO: renamed from: jq, reason: merged with bridge method [inline-methods] */
    public ic freeze() {
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        id idVar = CREATOR;
        id.a(this, out, flags);
    }
}
