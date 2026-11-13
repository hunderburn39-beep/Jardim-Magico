package com.adcolony.sdk;

import android.location.Location;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import com.tapjoy.TJAdUnitConstants;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class AdColonyUserMetadata {
    public static final String USER_EDUCATION_ASSOCIATES_DEGREE = "associates_degree";
    public static final String USER_EDUCATION_BACHELORS_DEGREE = "bachelors_degree";
    public static final String USER_EDUCATION_GRADE_SCHOOL = "grade_school";
    public static final String USER_EDUCATION_GRADUATE_DEGREE = "graduate_degree";
    public static final String USER_EDUCATION_HIGH_SCHOOL_DIPLOMA = "high_school_diploma";
    public static final String USER_EDUCATION_SOME_COLLEGE = "some_college";
    public static final String USER_EDUCATION_SOME_HIGH_SCHOOL = "some_high_school";
    public static final String USER_FEMALE = "female";
    public static final String USER_MALE = "male";
    public static final String USER_MARRIED = "married";
    public static final String USER_SINGLE = "single";
    static final int a = 128;
    JSONArray b = s.b();
    JSONObject c = s.a();
    Location d;

    public AdColonyUserMetadata setUserGender(@NonNull String gender) {
        if (au.d(gender)) {
            setMetadata("gender", gender);
        }
        return this;
    }

    public String getUserGender() {
        return s.b(this.c, "gender");
    }

    public AdColonyUserMetadata setUserAge(@IntRange(from = 0, to = 130) int age) {
        setMetadata("age", age);
        return this;
    }

    public int getUserAge() {
        return s.c(this.c, "age");
    }

    public AdColonyUserMetadata setUserMaritalStatus(@NonNull String status) {
        if (au.d(status)) {
            setMetadata("marital_status", status);
        }
        return this;
    }

    public String getUserMaritalStatus() {
        return s.b(this.c, "marital_status");
    }

    public AdColonyUserMetadata setUserAnnualHouseholdIncome(@IntRange(from = 0) int income) {
        setMetadata("household_income", income);
        return this;
    }

    public int getUserAnnualHouseholdIncome() {
        return s.c(this.c, "household_income");
    }

    public AdColonyUserMetadata setUserEducation(@NonNull String education) {
        if (au.d(education)) {
            setMetadata("education", education);
        }
        return this;
    }

    public String getUserEducation() {
        return s.b(this.c, "education");
    }

    public AdColonyUserMetadata setUserZipCode(@NonNull String zip) {
        if (au.d(zip)) {
            setMetadata("zip", zip);
        }
        return this;
    }

    public String getUserZipCode() {
        return s.b(this.c, "zip");
    }

    public AdColonyUserMetadata setUserLocation(@NonNull Location location) {
        this.d = location;
        setMetadata("longitude", location.getLongitude());
        setMetadata("latitude", location.getLatitude());
        setMetadata(TJAdUnitConstants.String.SPEED, location.getSpeed());
        setMetadata(TJAdUnitConstants.String.ALTITUDE, location.getAltitude());
        setMetadata("time", location.getTime());
        setMetadata("accuracy", location.getAccuracy());
        return this;
    }

    public Location getUserLocation() {
        return this.d;
    }

    public AdColonyUserMetadata addUserInterest(@NonNull String interest) {
        if (au.d(interest)) {
            s.a(this.b, interest);
            s.a(this.c, "interests", this.b);
        }
        return this;
    }

    public AdColonyUserMetadata clearUserInterests() {
        this.b = s.b();
        s.a(this.c, "interests", this.b);
        return this;
    }

    public String[] getUserInterests() {
        String[] strArr = new String[this.b.length()];
        for (int i = 0; i < this.b.length(); i++) {
            strArr[i] = s.b(this.b, i);
        }
        return strArr;
    }

    public AdColonyUserMetadata setMetadata(@NonNull String key, boolean value) {
        if (au.d(key)) {
            s.a(this.c, key, value);
        }
        return this;
    }

    public Object getMetadata(@NonNull String key) {
        return s.a(this.c, key);
    }

    public AdColonyUserMetadata setMetadata(@NonNull String key, double value) {
        if (au.d(key)) {
            s.a(this.c, key, value);
        }
        return this;
    }

    public AdColonyUserMetadata setMetadata(@NonNull String key, @NonNull String value) {
        if (au.d(value) && au.d(key)) {
            s.a(this.c, key, value);
        }
        return this;
    }
}
