package com.fyber.user;

import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.text.TextUtils;
import com.fyber.Fyber;
import com.fyber.utils.FyberLogger;
import com.fyber.utils.StringUtils;
import com.fyber.utils.h;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyConstants;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class User extends HashMap<String, Object> {
    private static final User g = new User();
    private static final long serialVersionUID = -5963403748409731798L;
    private String a;
    private boolean b = false;
    private Set<String> c = new HashSet();
    private Location d;
    private Location e;
    private Calendar f;

    private User() {
        this.c.add("age");
        this.c.add("birthdate");
        this.c.add("gender");
        this.c.add("sexual_orientation");
        this.c.add("ethnicity");
        this.c.add(TJAdUnitConstants.String.LAT);
        this.c.add("longt");
        this.c.add("marital_status");
        this.c.add("children");
        this.c.add("annual_household_income");
        this.c.add("education");
        this.c.add("zipcode");
        this.c.add("interests");
        this.c.add("iap");
        this.c.add("iap_amount");
        this.c.add("number_of_sessions");
        this.c.add("ps_time");
        this.c.add("last_session");
        this.c.add("connection");
        this.c.add(TapjoyConstants.TJC_NOTIFICATION_DEVICE_PREFIX);
        this.c.add(TapjoyConstants.TJC_APP_VERSION_NAME);
    }

    public static Integer getAge() {
        return (Integer) g.get("age");
    }

    public static void setAge(Integer num) {
        g.put("age", (Object) num);
    }

    public static Date getBirthdate() {
        return (Date) g.get("birthdate");
    }

    public static void setBirthdate(Date date) {
        g.put("birthdate", (Object) date);
    }

    public static UserGender getGender() {
        return (UserGender) g.get("gender");
    }

    public static void setGender(UserGender userGender) {
        g.put("gender", (Object) userGender);
    }

    public static UserSexualOrientation getSexualOrientation() {
        return (UserSexualOrientation) g.get("sexual_orientation");
    }

    public static void setSexualOrientation(UserSexualOrientation userSexualOrientation) {
        g.put("sexual_orientation", (Object) userSexualOrientation);
    }

    public static UserEthnicity getEthnicity() {
        return (UserEthnicity) g.get("ethnicity");
    }

    public static void setEthnicity(UserEthnicity userEthnicity) {
        g.put("ethnicity", (Object) userEthnicity);
    }

    public static Location getLocation() {
        return g.d;
    }

    public static void setLocation(Location location) {
        g.d = location;
        g.a(location);
    }

    public static UserMaritalStatus getMaritalStatus() {
        return (UserMaritalStatus) g.get("marital_status");
    }

    public static void setMaritalStatus(UserMaritalStatus userMaritalStatus) {
        g.put("marital_status", (Object) userMaritalStatus);
    }

    public static Integer getNumberOfChildrens() {
        return (Integer) g.get("children");
    }

    public static void setNumberOfChildrens(Integer num) {
        g.put("children", (Object) num);
    }

    public static Integer getAnnualHouseholdIncome() {
        return (Integer) g.get("annual_household_income");
    }

    public static void setAnnualHouseholdIncome(Integer num) {
        g.put("annual_household_income", (Object) num);
    }

    public static UserEducation getEducation() {
        return (UserEducation) g.get("education");
    }

    public static void setEducation(UserEducation userEducation) {
        g.put("education", (Object) userEducation);
    }

    public static String getZipcode() {
        return (String) g.get("zipcode");
    }

    public static void setZipcode(String str) {
        g.put("zipcode", (Object) str);
    }

    public static String[] getInterests() {
        return (String[]) g.get("interests");
    }

    public static void setInterests(String[] strArr) {
        g.put("interests", (Object) strArr);
    }

    public static Boolean getIap() {
        return (Boolean) g.get("iap");
    }

    public static void setIap(Boolean bool) {
        g.put("iap", (Object) bool);
    }

    public static Float getIapAmount() {
        return (Float) g.get("iap_amount");
    }

    public static void setIapAmount(Float f) {
        g.put("iap_amount", (Object) f);
    }

    public static Integer getNumberOfSessions() {
        return (Integer) g.get("number_of_sessions");
    }

    public static void setNumberOfSessions(Integer num) {
        g.put("number_of_sessions", (Object) num);
    }

    public static Long getPsTime() {
        return (Long) g.get("ps_time");
    }

    public static void setPsTime(Long l) {
        g.put("ps_time", (Object) l);
    }

    public static Long getLastSession() {
        return (Long) g.get("last_session");
    }

    public static void setLastSession(Long l) {
        g.put("last_session", (Object) l);
    }

    public static UserConnection getConnection() {
        return (UserConnection) g.get("connection");
    }

    public static void setConnection(UserConnection userConnection) {
        g.put("connection", (Object) userConnection);
    }

    public static String getDevice() {
        return (String) g.get(TapjoyConstants.TJC_NOTIFICATION_DEVICE_PREFIX);
    }

    public static void setDevice(String str) {
        g.put(TapjoyConstants.TJC_NOTIFICATION_DEVICE_PREFIX, (Object) str);
    }

    public static String getAppVersion() {
        return (String) g.get(TapjoyConstants.TJC_APP_VERSION_NAME);
    }

    public static void setAppVersion(String str) {
        g.put(TapjoyConstants.TJC_APP_VERSION_NAME, (Object) str);
    }

    public static void addCustomValue(String str, Object obj) {
        if (!g.c.contains(str)) {
            g.put(str, obj);
        } else {
            FyberLogger.v("User", str + " is a reserved key for this HashMap, please select another name.");
        }
    }

    public static Object getCustomValue(String str) {
        return g.get(str);
    }

    public static String mapToString() {
        String string;
        if (g.b) {
            FyberLogger.d("User", "User data has changed, recreating...");
            User user = g;
            h hVarA = Fyber.getConfigs().a();
            if (hVarA != null) {
                LocationManager locationManagerF = hVarA.f();
                if (user.d == null && locationManagerF != null) {
                    Calendar calendar = Calendar.getInstance();
                    if (user.f == null || calendar.after(user.f)) {
                        Iterator<String> it = hVarA.g().iterator();
                        while (it.hasNext()) {
                            Location lastKnownLocation = locationManagerF.getLastKnownLocation(it.next());
                            if (lastKnownLocation != null) {
                                if (user.e == null) {
                                    user.e = lastKnownLocation;
                                }
                                if (user.e != null && user.e.getTime() < lastKnownLocation.getTime()) {
                                    user.e = lastKnownLocation;
                                }
                            }
                        }
                        if (user.e != null) {
                            Calendar calendar2 = Calendar.getInstance();
                            calendar2.add(5, -1);
                            if (user.e.getTime() > calendar2.getTimeInMillis()) {
                                user.a(user.e);
                                user.f = calendar;
                                user.f.add(12, 10);
                            }
                        }
                    }
                }
            }
            Uri.Builder builder = new Uri.Builder();
            for (Map.Entry<String, Object> entry : g.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof Date) {
                    string = String.format(Locale.ENGLISH, "%tY/%tm/%td", value, value, value);
                } else if (value instanceof String[]) {
                    string = TextUtils.join(",", (String[]) value);
                } else if (value instanceof Location) {
                    Location location = (Location) value;
                    string = "latitude:" + Location.convert(location.getLatitude(), 0) + ",longitude:" + Location.convert(location.getLongitude(), 0) + ",accuracy:" + location.getAccuracy();
                } else {
                    string = value.toString();
                }
                builder.appendQueryParameter(key, string);
            }
            String string2 = builder.build().toString();
            g.a = string2.substring(1, string2.length());
            FyberLogger.d("User", "FybUSer data - " + g.a);
            g.b = false;
        }
        return g.a;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final Object put(String str, Object obj) {
        if (StringUtils.notNullNorEmpty(str) && obj != null) {
            if (!this.b) {
                Object obj2 = get(str);
                this.b = obj2 == null || !obj2.equals(obj);
            }
            return super.put(str, obj);
        }
        return null;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final Object remove(Object obj) {
        Object objRemove = super.remove(obj);
        this.b = objRemove != null;
        return objRemove;
    }

    private void a(Location location) {
        if (location != null) {
            put(TJAdUnitConstants.String.LAT, (Object) Location.convert(location.getLatitude(), 0));
            put("longt", (Object) Location.convert(location.getLongitude(), 0));
        } else {
            remove(TJAdUnitConstants.String.LAT);
            remove("longt");
        }
    }
}
