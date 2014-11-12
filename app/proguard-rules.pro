# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/nait/programy/adt-bundle-linux-x86_64-20140702/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# BUTTERKNIFE
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }
-keepnames class * { @butterknife.InjectView *;}

# DAGGER
-keepclassmembers,allowobfuscation class * {
    @javax.inject.* *;
    @dagger.* *;
    <init>();
}
-keep class dagger.* { *; }
-keep class javax.inject.* { *; }
-keep class **$$ModuleAdapter
-keep class **$$InjectAdapter
-keep class **$$StaticInjection
-keep class * extends dagger.internal.Binding
-keep class * extends dagger.internal.ModuleAdapter
-keep class * extends dagger.internal.StaticInjection
# Gradle includes dagger-compiler and javawriter in the final package
-dontwarn dagger.internal.codegen.**
-dontwarn com.squareup.javawriter.**
-dontwarn javax.annotation.**
-dontwarn sun.misc.Unsafe
-dontwarn java.nio.file.**
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement