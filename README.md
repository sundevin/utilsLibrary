
# utilsLibrary
A tools library for Android.

[ ![Download](https://api.bintray.com/packages/sundevin/UtilsLibrary/utilsLibrary/images/download.svg) ](https://bintray.com/sundevin/UtilsLibrary/utilsLibrary/_latestVersion)[![Lisense](https://img.shields.io/badge/License-Apache%202-lightgrey.svg)](https://www.apache.org/licenses/LICENSE-2.0)

整理了一些安卓项目开发中常用的一些工具类，将持续更新，欢迎 star.

### 说明
每次新开项目时总是不可避免的从新项目中拷贝一些常用的工具类，拷贝到新项目的时候，又有很多包名导入错误，需要修改导包，
而工具类里经常又会用到 `Context` 参数，
个人比较懒，不想传参，一般会在工具类里写上以下代码来获取 `Context`:
```
    private Context context=xxApplication.getInstance();
```
采用这种方式又不可避免的需要修改 `xxApplication` 类名。(虽然可以全局替换，懒~
所以把常用的工具类整理出来，以 module 或者 aar 的方式使用，这样就能避免以上问题啦~，具体有哪些工具类，可以下载后查看api文档。

---
### 更新日志
- 2.0.2
```
    2017/12/13
    ActivityUtils  显示状态栏
    更改 shapeUtils 为 ShapeFactory，代码生成 shape 更方便。
```
- 2.0.1
```
    2017/11/30
    1,解决 application 标签冲突问题
    2,加入以下工具类：
    ActivityUtils 状态栏颜色设置，透明状态栏，隐藏状态栏，禁止截屏，禁止黑屏，view截图
    DeviceInfo 虚拟按键是否存在及高度，
    ImageLoader（Glide 图片加载）
    进程名称获取及主进程判断（AppInfo）
    MainLooper（主线程切换）
    扬声器判断（SpeakerUtils）
    下载图片（BitmapUtils）
    补充时间转换的方法（DateUtils）
    虚拟按键高度
    日志开关修改为set方式
    增加代码生成shape（shapeUtils）
```
- 1.0.2
```
    2017/5/16
    删除不常用的权限 uses-permission android:name="android.permission.RECORD_AUDIO"
    minSdkVersion 由19改为14
```
---
### 依赖
1, gradle (推荐)
```
dependencies {
    //latestVersion 替换为最新版本号
    compile 'com.sundevin:utilslibrary:latestVersion'
}
```

2,下载 library,以 module 的方式导入。

### 初始化
在项目的 `Application` 进行初始化
```
    UtilManager.init(this);
```
###  注意事项
#### 1,权限问题
目前已经添加的权限有以下几种,library 里还有关于蓝牙、麦克风等一些不常用的方法，如果有使用到相关方法，可自行在项目里添加权限。
```
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.INTERNET"/>
```

#### 2,第三方依赖问题
工具类里有几个比较特殊的工具类,分别是汉字转拼音、`Gson`、`FastJson` 和图片加载，这几个工具类均需要依赖第三方库，
如果项目里有使用这其中的工具类，则需要在自己项目的 `gradle.buil` 里添加相关依赖。
如：
```
    compile 'com.belerweb:pinyin4j:2.5.0'
    compile 'com.google.code.gson:gson:2.7'
    compile 'com.alibaba:fastjson:1.2.8'
    compile 'com.github.bumptech.glide:glide:3.7.0'
```
####  其他
library 里有本人写的，也有他人提供后由本人整理的，有些方法可能没有经过验证，可能会出现 bug，如果出现 bug，请及时提 issue，我会及时处理。
如果有比较好的工具类也可以共享出来，我会及时添加进去。


