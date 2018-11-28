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
#-keep public class [com.zhenxing.loanapp].R$*{
#public static final int *;
#}

-keep interface com.zhenxing.loanapp.bean.** { *; }
-keep class com.zhenxing.loanapp.bean.** { *; }
-keep class android.os.MessageQueue.**{*;}
-keep class com.zhenxing.loanapp.adapter.**{*;}
-keep class com.umeng.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keep public class **.R$*{
   public static final int *;
}

-keepattributes *Annotation*

-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

#-keep com.squareup.okhttp.**{*;}
-dontwarn com.squareup.okhttp.**
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

-keep class com.lcodecorex.tkrefreshlayout.**{*;}
-dontwarn com.lcodecorex.tkrefreshlayout.**

-dontwarn okio.**
-dontwarn rx.**
-dontwarn com.google.protobuf.**

-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**

# xml 中的 onClick 属性对应的方法
 -keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
 }

 -keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
 }