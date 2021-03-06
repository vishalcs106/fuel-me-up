# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }

-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}

-dontwarn android.support.**

-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }


-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }
-keep public class * implements butterknife.internal.ViewBinder { public <init>(); }

-keep class com.github.mikephil.charting.** { *; }
-keeppackagenames org.jsoup.nodes

-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

# If you do not use SQLCipher:
-dontwarn org.greenrobot.greendao.database.**

-keep class com.facebook.stetho.** { *; }
-dontwarn com.facebook.stetho.**
-dontwarn okio.**

-keep class com.android.fuelmeup.model {
  *;
}



-dontwarn retrofit2.Platform$Java8

-keep public class * extends android.app.AppCompatActivity
-keep public class * extends android.support.v4.app.Fragment


-keep public class * extends android.app.Application



-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-dontwarn rx.**

-keepattributes *Annotation*
-keepattributes Signature
-keepattributes EnclosingMethod
-keep class sun.misc.Unsafe { *; }


-keep class com.google.gson.stream.** { *; }

-keep public class com.google.gson.**
-keep public class com.google.gson.** {public private protected *;}
-keepattributes *Annotation*,Signature
-keep class com.mypackage.ActivityMonitor.ClassMultiPoints.** { *; }
-keep public class com.mypackage.ActivityMonitor$ClassMultiPoints     { public protected *; }
-keep public class com.mypackage.ActivityMonitor$ClassMultiPoints$ClassPoints { public protected *; }
-keep public class com.mypackage.ActivityMonitor$ClassMultiPoints$ClassPoints$ClassPoint { public protected *; }

-keepclassmembers public class com.android.fuelmeup.model.FuelStation { private <fields>; }
-keepclassmembers public class com.android.fuelmeup.model.FuelStation { private <methods>; }
-keepclassmembers public class com.android.fuelmeup.model.FuelStation { public <fields>; }
-keepclassmembers public class com.android.fuelmeup.model.FuelStation { public <methods>; }

-keepclassmembers enum * { *; }

-printmapping mapping.txt

-keep class rx.internal.util.unsafe.**