# utilsLibrary
A tools library for Android.

整理了一些安卓项目开发中常用的一些工具类，将持续更新，欢迎star.

### 初衷
每次新开项目时总是不可避免的从新项目中拷贝一些常用的工具类，拷贝到新项目的时候，又有很多包名导入错误，需要修改导包，
而工具类里经常又会用到 ```Context``` 参数，
个人比较懒，不想传参，一般会在工具类里写上以下代码来获取 ```Context```:
```
private Context context=xxApplication.getInstance();
```
采用这种方式又不可避免的需要修改 ```xxApplication``` 类名。(虽然可以全局替换，懒~

所以就想把常用的工具类整理出来，以 module 或者 aar 的方式使用，这样就能避免以上问题啦~，具体有哪些工具类，可以下载后查看api文档。

### 依赖
1,gradle
```
compile 'com.sundevin:utilslibrary:1.0.0'
```

2,下载 library,以 module 的方式导入。

### 使用

#### 初始化
在项目的 Application 进行初始化

```
UtilManager.init(this);
```

####  注意事项
##### 1,权限问题
目前已经添加的权限有以下几种,library 里还有关于蓝牙等一些不常用的方法，如果有使用到相关方法，可自行在项目里添加权限。
```
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.RECORD_AUDIO"/>
    <uses-permission android:name="android.permission.INTERNET"/>
```

##### 2,第三方依赖问题
工具类里有几个比较特殊的工具类,分别是汉字转拼音、Gson、FastJson,这三个工具类均需要依赖第三方库，
如果项目里有使用这其中的工具类，则需要在自己项目的 ```gradle.build``` 里添加相关依赖。
如：
```
    compile 'com.belerweb:pinyin4j:2.5.0'
    compile 'com.google.code.gson:gson:2.7'
    compile'com.alibaba:fastjson:1.2.8'
```
####  其他
library 里有本人写的，也有他人提供后由本人整理的，有些方法可能没有经过验证，可能会出现bug，如果出现bug，请及时提 issue，我会及时处理。
如果有比较好的工具类也可以共享出来，我会及时添加进去。

