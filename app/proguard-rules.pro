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

#-dontnote android.net.http.*
#-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**

-verbose
-libraryjars <java.home>/lib/rt.jar(java/**,javax/**)

-dontwarn org.bouncycastle.**
-dontwarn org.apache.http.**
-dontwarn android.webkit.WebView
-dontwarn android.net.http.SslError
-dontwarn android.webkit.WebViewClient

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepattributes InnerClasses

-keep class com.softeksol.paisalo.jlgsourcing.entities.** { *;}

-keep class android.support.v4.* {
    <fields>;
    <methods>;
}

-keep class android.support.v7.* {
    <fields>;
    <methods>;
}

-keep public class android.net.http.SslError
-keep public class android.webkit.WebViewClient
-keep class com.google.gson.** { *; }
-keep class com.theartofdev.edmodo.cropper.** { *; }
-keep class com.journeyapps.barcodescanner.** { *; }
-keep class com.google.zxing.** { *; }
-keep class com.raizlabs.android.dbflow.**  { *; }
-keepattributes *Annotation*
-keepattributes Signature
