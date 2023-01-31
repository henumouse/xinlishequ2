# xinlishequ2
菜狗的毕设项目，使用传统安卓Adapter开发模式写的，借鉴前（dddd）先问下我。
没有用本地数据库....直接用了Bmob的云数据库，一个月15真黑。
开发版本为Android10，因为9.0后面已经不支持明文传输了，配置项目的时候会有
W/System.err: java.io.IOException: Cleartext HTTP traffic to not permitted错误。
设置单独的网络安全配置文件，需在「res」目录下新建一个「xml」文件夹，并在该文件夹中创建一个「network_security_config.xml」文件，内容如下：
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <base-config cleartextTrafficPermitted="true" />
</network-security-config>

然后在 AndroidManifest 中引用该网络安全配置文件：
<application ...
    android:networkSecurityConfig="@xml/network_security_config">
    ...
</application>

尽管这样看来使用单独的网络安全配置文件似乎有点儿繁琐，但有个好处是可以针对特定网域和特定应用配置自定义信任锚、配置用于调试的 CA 等，拥有更大的配置权限。
其他的权限获取：
    <!--允许联网 -->
    <uses-permission android:name="android.permission.INTERNET" />
    <!--获取GSM（2g）、WCDMA（联通3g）等网络状态的信息  -->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <!--获取wifi网络状态的信息 -->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <!--获取sd卡写的权限，用于文件上传和下载-->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <!--允许读取手机状态 用于创建BmobInstallation-->
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
