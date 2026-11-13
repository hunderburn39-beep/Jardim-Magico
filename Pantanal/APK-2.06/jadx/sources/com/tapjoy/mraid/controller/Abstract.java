package com.tapjoy.mraid.controller;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.tapjoy.mraid.util.NavigationStringEnum;
import com.tapjoy.mraid.util.TransitionStringEnum;
import com.tapjoy.mraid.view.MraidView;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes.dex */
public abstract class Abstract {
    public static final String EXIT = "exit";
    public static final String FULL_SCREEN = "fullscreen";
    public static final String STYLE_NORMAL = "normal";
    protected MraidView a;
    protected Context b;

    public abstract void stopAllListeners();

    public static class PlayerProperties extends ReflectedParcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.tapjoy.mraid.controller.Abstract.PlayerProperties.1
            @Override // android.os.Parcelable.Creator
            public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
                return new PlayerProperties[i];
            }

            @Override // android.os.Parcelable.Creator
            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                return new PlayerProperties(parcel);
            }
        };
        public boolean audioMuted;
        public boolean autoPlay;
        public boolean doLoop;
        public boolean inline;
        public boolean showControl;
        public String startStyle;
        public String stopStyle;

        public PlayerProperties() {
            this.showControl = true;
            this.autoPlay = true;
            this.audioMuted = false;
            this.doLoop = false;
            this.stopStyle = Abstract.STYLE_NORMAL;
            this.startStyle = Abstract.STYLE_NORMAL;
            this.inline = false;
        }

        public PlayerProperties(Parcel in) {
            super(in);
        }

        public void setStopStyle(String style) {
            this.stopStyle = style;
        }

        public void setProperties(boolean audioMuted, boolean autoPlay, boolean controls, boolean inline, boolean loop, String startStyle, String stopStyle) {
            this.autoPlay = autoPlay;
            this.showControl = controls;
            this.doLoop = loop;
            this.audioMuted = audioMuted;
            this.startStyle = startStyle;
            this.stopStyle = stopStyle;
            this.inline = inline;
        }

        public void muteAudio() {
            this.audioMuted = true;
        }

        public boolean isAutoPlay() {
            return this.autoPlay;
        }

        public boolean showControl() {
            return this.showControl;
        }

        public boolean doLoop() {
            return this.doLoop;
        }

        public boolean doMute() {
            return this.audioMuted;
        }

        public boolean exitOnComplete() {
            return this.stopStyle.equalsIgnoreCase(Abstract.EXIT);
        }

        public boolean isFullScreen() {
            return this.startStyle.equalsIgnoreCase(Abstract.FULL_SCREEN);
        }
    }

    public static class Dimensions extends ReflectedParcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.tapjoy.mraid.controller.Abstract.Dimensions.1
            @Override // android.os.Parcelable.Creator
            public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
                return new Dimensions[i];
            }

            @Override // android.os.Parcelable.Creator
            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                return new Dimensions(parcel);
            }
        };
        public int height;
        public int width;
        public int x;
        public int y;

        public Dimensions() {
            this.x = -1;
            this.y = -1;
            this.width = -1;
            this.height = -1;
        }

        protected Dimensions(Parcel in) {
            super(in);
        }
    }

    public static class Properties extends ReflectedParcelable {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: com.tapjoy.mraid.controller.Abstract.Properties.1
            @Override // android.os.Parcelable.Creator
            public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
                return new Properties[i];
            }

            @Override // android.os.Parcelable.Creator
            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                return new Properties(parcel);
            }
        };
        public int backgroundColor;
        public float backgroundOpacity;
        public boolean isModal;
        public boolean lockOrientation;
        public boolean useBackground;
        public boolean useCustomClose;

        protected Properties(Parcel in) {
            super(in);
        }

        public Properties() {
            this.useBackground = false;
            this.backgroundColor = 0;
            this.backgroundOpacity = BitmapDescriptorFactory.HUE_RED;
            this.isModal = false;
            this.lockOrientation = false;
            this.useCustomClose = false;
        }
    }

    public Abstract(MraidView adView, Context context) {
        this.a = adView;
        this.b = context;
    }

    public static class ReflectedParcelable implements Parcelable {
        public ReflectedParcelable() {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        protected ReflectedParcelable(Parcel in) {
            for (Field field : getClass().getDeclaredFields()) {
                try {
                    Class<?> type = field.getType();
                    if (type.isEnum()) {
                        String string = type.toString();
                        if (string.equals("class com.tapjoy.mraid.util.NavigationStringEnum")) {
                            field.set(this, NavigationStringEnum.fromString(in.readString()));
                        } else if (string.equals("class com.tapjoy.mraid.util.TransitionStringEnum")) {
                            field.set(this, TransitionStringEnum.fromString(in.readString()));
                        }
                    } else if (!(field.get(this) instanceof Parcelable.Creator)) {
                        field.set(this, in.readValue(null));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return;
                } catch (IllegalArgumentException e2) {
                    e2.printStackTrace();
                    return;
                }
            }
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags1) {
            Field[] fields = getClass().getDeclaredFields();
            for (Field field : fields) {
                try {
                    Class<?> type = field.getType();
                    if (type.isEnum()) {
                        String string = type.toString();
                        if (string.equals("class com.tapjoy.mraid.util.NavigationStringEnum")) {
                            out.writeString(((NavigationStringEnum) field.get(this)).getText());
                        } else if (string.equals("class com.tapjoy.mraid.util.TransitionStringEnum")) {
                            out.writeString(((TransitionStringEnum) field.get(this)).getText());
                        }
                    } else {
                        Object obj = field.get(this);
                        if (!(obj instanceof Parcelable.Creator)) {
                            out.writeValue(obj);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return;
                } catch (IllegalArgumentException e2) {
                    e2.printStackTrace();
                    return;
                }
            }
        }
    }
}
