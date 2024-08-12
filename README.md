### Android动态修改应用图标 改变启动图标

注意：如果activity-alias方式有问题 可以换成子类继承的方式创建多个Activity使用别名的包名类名即可

app以后都要保持这些图标配置，不能乱删和乱禁用，小心桌面图标被禁了导致无图标无法进入app的情况，除非主启动页直接换了包路径或类名

使用：
```groovy
maven { url 'https://jitpack.io' }
//公仓
implementation 'com.gitee.mahongyin:HyAppIcon:0.0.1'
//私仓
implementation 'com.mhy:appicon:0.0.1'
```
清单文件配置的所有桌面图标，和默认启用的意图路径名 

android:enabled="false/true"  来设置默认启用的

```xml
清单文件配置需要换图标的别名或继承主启动页的子类 
		<activity
            android:name=".LaunchActivity" android:configChanges="density|fontScale|keyboard|keyboardHidden|layoutDirection|locale|orientation|screenLayout|screenSize|smallestScreenSize|uiMode"
            android:enableOnBackInvokedCallback="true"
            android:exported="true"
            android:hardwareAccelerated="true"
            android:launchMode="singleTop"
            android:theme="@style/Theme.HYAppIcon"
            android:windowSoftInputMode="adjustResize">

            <intent-filter>
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
		<!--别名-->
 			<activity-alias
            android:name=".faviconA"
            android:enabled="false"
            android:exported="true"
            android:icon="@mipmap/favicon2"
            android:label="灵动音乐"
            android:targetActivity=".LaunchActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity-alias>
        <!--和原主一样的别名-->
        <activity-alias
            android:name=".LaunchActivity"
            android:enabled="true"
            android:exported="true"
            android:icon="@mipmap/ic_launcher"
            android:label="原主"
            android:targetActivity=".LaunchActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity-alias>
		......
```

```java
 //将所有的桌面图标意图传给HyAppIconUtils
 ComponentName componentName2 = new ComponentName(this, "com.mhy.hyappicon.demo.faviconA");
 ComponentName componentName1 = new ComponentName(this, "com.mhy.hyappicon.demo.LaunchActivity");
 List<ComponentName> list = new ArrayList<>();
 list.add(componentName2);
 list.add(componentName1);//默认启用的那个
 
 HyAppIconUtils.initAllIconComponentName(list, componentName1);
 //设置换图标目标为2 
 void changeIcon(){
 	HyAppIconUtils.changeAppIcon(IndexActivity.this, componentName2));
 }
```

结束。
android10及以上 会立即退出app 【清栈】
android10以下会慢一些 得过一会才会退出app 【清栈】

如果需要启动时无感换图标。那么参考 [Android动态修改应用图标最佳实践 - 简书 (jianshu.com)](https://www.jianshu.com/p/8df4283701c6)

原理就是在**启动页**先跳转 中间过渡页[独立栈]，让过渡页去跳转home页即可
也就是把换图标的那些代码挪到中间过渡页中
```kotlin
//中间过渡activity
class TansparentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogCat.i("应用换标 过渡页")
        ......
        HyAppIconUtils.initAllIconComponentName(list, componentName1);
        //跳转主页 这里就会清理这个栈而不会影响启动的栈
        launchMain(intent, "icon")
        finish()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 切换图标...会清栈
        ......
        HyAppIconUtils.changeAppIcon(IndexActivity.this, componentName2);
    }

}
```

```xml
中间过渡 activity 配置 注意方向 android:screenOrientation="behind" 别写死成 android:screenOrientation="portrait" android 8.0 会崩溃

<activity android:name=".TansparentActivity"
            android:configChanges="keyboardHidden|orientation|screenSize|uiMode"
            android:screenOrientation="behind"
            android:theme="@style/AppThemeCompat.Translucent"
            android:launchMode="singleInstance"/>
    
     <style name="AppThemeCompat.Translucent" parent="AppTheme">
        <item name="android:background">@android:color/transparent</item>
        <item name="android:windowBackground">@android:color/transparent</item>
        <item name="android:colorBackgroundCacheHint">@null</item>
        <item name="android:windowIsTranslucent">true</item>
        <item name="android:windowActionBar">false</item>
        <item name="android:windowNoTitle">true</item>
        <item name="android:windowContentOverlay">@null</item>
    </style>
    
    <item name="android:windowFullscreen">true</item>
    <item name="android:windowTranslucentStatus">true</item>
    <item name="android:windowTranslucentNavigation">true</item>
    <item name="android:windowLayoutInDisplayCutoutMode">shortEdges</item>
    <item name="android:windowAnimationStyle">@null</item>
```



------

另外还可以更换 启动图  只需再清单文件设置对应theme的android:windowBackground  例如：

```xml
    <!-- 闪屏页面 -->
    <style name="splashscreen_Theme" parent="common_Theme.BaseAppTheme">
        <item name="android:windowFullscreen">true</item>
        <item name="android:windowTranslucentStatus">true</item>
        <item name="android:windowTranslucentNavigation">true</item>
        <item name="android:windowLayoutInDisplayCutoutMode" tools:ignore="NewApi">shortEdges</item>
        <item name="android:windowBackground">@drawable/launcher_background</item>

        <item name="android:windowAnimationStyle">@null</item>
    </style>

    <style name="splashscreen_Theme2" parent="common_Theme.BaseAppTheme">
        <item name="android:windowFullscreen">true</item>
        <item name="android:windowTranslucentStatus">true</item>
        <item name="android:windowTranslucentNavigation">true</item>
        <item name="android:windowLayoutInDisplayCutoutMode" tools:ignore="NewApi">shortEdges</item>
        <item name="android:windowBackground">@drawable/launcher_background2</item>

        <item name="android:windowAnimationStyle">@null</item>
    </style>
    在activity-alis里对应设置需要的主题即可
```