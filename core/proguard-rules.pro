# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
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

-keep class com.kakao.sdk.**.model.* { <fields>; }

-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception
-dontwarn org.bouncycastle.jsse.**
-dontwarn org.conscrypt.*
-dontwarn org.openjsse.**


# kakao
-keep class com.kakao.** { *; }

-keepclassmembers enum * { *; }

# firebase
-keep class com.firebase.** { *; }

-keep class androidx.viewpager2.widget.ViewPager2.** { *; }

# coroutines
-dontwarn kotlinx.coroutines.**

-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

-keep class com.airbnb.lottie.** {*;}


-keepclassmembers class * extends android.app.Activity {
     public void *(android.view.View);
 }

-keepclasseswithmembernames class * {
 native <methods>;
 }


# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Retain service method parameters when optimizing.
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keepclassmembernames interface * {
    @retrofit2.http.* <methods>;
}

-keepattributes Signature
-keepattributes *Annotation*

-dontwarn java.nio.file.*
-dontwarn okio.**

-keepnames class * extends android.os.Parcelable
-keepnames class * extends java.io.Serializable

-keep class com.mument_android.data.mapper.** { *; }
-keep class com.mument_android.data.dto.** { *; }
-keep class com.mument_android.data.local.converter.** { *; }
-keep class com.mument_android.data.local.recentlist.** { *; }
-keep class com.mument_android.data.local.todaymument.** { *; }
-keep class com.mument_android.data.util.** { *; }
-keep class com.mument_android.data.network.** { *; }


-keep class com.mument_android.core_dependent.util.** { *; }

-keep class com.mument_android.core.base.BaseMapper
-keep class com.mument_android.core.model.** { *; }
-keep class com.mument_android.core.network.** { *; }
-keep class com.mument_android.core.util.** { *; }
