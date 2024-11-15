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

-dontwarn java.lang.invoke.StringConcatFactory

-keep class com.example.core.di.** { *; }
-keepclassmembers class com.example.core.di.** { *; }

-keep class com.example.core.domain.** { *; }
-keepclassmembers class com.example.core.domain.** { *; }

-keep class com.example.core.data.** { *; }
-keepclassmembers class ccom.example.core.data.** { *; }

-keep class com.example.core.utils.** { *; }
-keepclassmembers class ccom.example.core.utils.** { *; }

-keepclassmembers class com.example.core.Result$* { *; }
-keep class com.example.core.Result { *; }
-keep class com.example.core.Result$Success { *; }
-keep class com.example.core.Result$Error { *; }