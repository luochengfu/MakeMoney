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

# 不压缩输入的类文件
-dontshrink
-optimizationpasses 5
#混淆时不适用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames
#指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses
#不做预校验，preverify是proguard的四个步骤之一，Android不需要precerify,去掉这一步能够加快混淆速度。
-dontpreverify
 # 混淆时是否记录日志
-verbose
#google推荐算法
-optimizations !code/simplification/arithmetic,!code/simplication/cast,!field/*,!class/mergin/*
-ignorewarnings#忽略警告 也可以用
-dontwarn
-allowaccessmodification
-dontskipnonpubliclibraryclassmembers
#避免混淆Annotation、内部类、泛型、匿名类
-keepattributes *Annotation*,InnerClasses,Signature,EnclosingMethod
#抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
#重命名抛出异常时的文件名称
-renamesourcefileattribute SourceFile

#处理support包
-dontnote android.support.**
-dontwarn android.support.**

#保留本地native方法不被混淆
-keepclasseswithmembernames class * {
     native <methods>;
}



#保留枚举类不被混淆
-keep public class * implements java.io.Serializable {*;}
-keep class * implements android.os.Parcelable {
public static final android.os.Parcelable$Creator *;
}

#第三方jar包不被混淆
-keep class com.github.test.** {*;}

#保留自定义的Test类和类成员不被混淆
-keep class class.lily.Test {*;}

#保留自定义的xlog文件夹下面的类、类成员和方法不被混淆
-keep class com.text.xlog.** {
<fields>;
<methods>;
}

#assume no side effects;删除android.util.Log输出的日志
-assumenosideeffects class android.util.Log {
public static *** v(...);
public static *** d(...);
public static *** i(...);
public static *** w(...);
public static *** e(...);
}

#保留keep注解的类名和方法
-keep,allowobfuscation @interface android.support.annotation.Keep
-keep @android.support.annotation.Keep class *
-keepclassmember class * {
@android.support.annotation.Keep *;
}
#反射用到的类不混淆（否则混淆可能出现问题）
-keepattributes EnclosingMethod
#有用到WebView的JS调用也需要保证写的接口方法不混淆，原则和第一条一样。
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}
#Parcelable的子类和Creator静态成员变量不混淆，否则会产生Android.os.BadParcelableException异常；
-keep class * implements Android.os.Parcelable {
    public static final Android.os.Parcelable$Creator *;
}
#使用enum类型时需要注意避免以下两个方法混淆，因为enum类的特殊性，以下两个方法会被反射调用。
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#注解不混淆
-keep class * extends java.lang.annotation.Annotation {*;}
#泛型不混淆
-keepattributes Signature
#内部类不混淆
-keepattributes InnerClasses

#otto
-keepclassmembers class ** {
    @com.squareup.Subscribe public *;
    @com.squareup.otto.Produce public *;
}
#universal-image-loader
-dontwarn com.nostra13.universalimageloader.**
-keep class com.nostra13.universalimageloader.** {*;}
#OkHttp
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** {*;}
-keep interface com.squareup.okhttp.** {*;}
-dontwarn okio.**
#ineoldandroids
-dontwarn com.nineoldandroids.*
-keep class com.nineoldandroids.** {*;}

# EventBus 3.0 start
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

-keep class org.simple.eventbus.** {*;}
-keepclassmembers class ** {
    public void onEvent*(**);
    void onEvent*(**);
}

# EventBus 3.0 end

#支付宝
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{
    public *;
}
-keep class com.alipay.sdk.app.AuthTask{
    public *;
}

#Socket.io
-keep class socket.io-client.
-keepclasseswithmembers,allowshrinking class socket.io-client.* {*;}
-keep class io.socket.
-keepclasseswithmembers,allowshrinking class io.socket.* {*;}
#网络层混淆
-keep class com.xxx.xxx.http.** {*;}
#数据模型混淆
-keep class com.tudouni.makemoney.model.** {*;}

#友盟混淆配置
-keep class com.umeng.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class **.R$* {
 *;
}

#极光推送
-dontoptimize

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

#==================gson && protobuf==========================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-keep class com.google.protobuf.** {*;}

-dontwarn com.squareup.picasso.**

-libraryjars libs/aliyun-oss-sdk-android-2.3.0.jar
-libraryjars libs/umeng-debug-1.0.0.jar
-libraryjars libs/umeng-share-core-6.9.1.jar
-libraryjars libs/umeng-share-QQ-simplify-6.9.1.jar
-libraryjars libs/umeng-share-wechat-simplify-6.9.1.jar
-libraryjars libs/umeng-sharetool-6.9.1.jar
-libraryjars libs/xutils-3.5.0.jar

-keep class * extends java.lang.annotation.Annotation { *; }
-keep interface * extends java.lang.annotation.Annotation { *; }

#保留使用xUtils的方法和类，并且不要混淆名字
-keep @com.lidroid.xutils.db.annotation.Table class *
-keepclassmembers class * {
    @com.lidroid.xutils.db.annotation.* <fields>;
}
-keepclassmembers, allowobfuscation class * {
@com.lidroid.xutils.view.annotation.* <fields>;
    @com.lidroid.xutils.view.annotation.event.* <methods>;
}

#支付宝 jar
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}

