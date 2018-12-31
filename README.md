# SAF-Kotlin-Utils

[![@Tony沈哲 on weibo](https://img.shields.io/badge/weibo-%40Tony%E6%B2%88%E5%93%B2-blue.svg)](http://www.weibo.com/fengzhizi715)
[![License](https://img.shields.io/badge/license-Apache%202-lightgrey.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

用 Kolin 做的工具类库，包括 utils 和 extension

模块|saf-kotlin-utils|saf-kotlin-ext
---|:-------------:|:-------------:
最新版本|[ ![Download](https://api.bintray.com/packages/fengzhizi715/maven/saf-kotlin-utils/images/download.svg) ](https://bintray.com/fengzhizi715/maven/saf-kotlin-utils/_latestVersion)|[ ![Download](https://api.bintray.com/packages/fengzhizi715/maven/saf-kotlin-ext/images/download.svg) ](https://bintray.com/fengzhizi715/maven/saf-kotlin-ext/_latestVersion)|


# 一. 下载安装

Gradle:

```groovy
implementation 'com.safframework.utils:saf-kotlin-utils:0.1.1'
implementation 'com.safframework.utils:saf-kotlin-ext:0.1.1'
```

# 二. 功能

## 2.1 工具库的方法

### AppUtils

### FileUtils

### Platform


## 2.2 扩展库的扩展方法、扩展属性

### Boolean
* then()

### Bundle
* put()

### Context
* dp2px()
* sp2px()
* px2dp()
* dimen2px()
* string()
* color()
* inflateLayout()
* getAppVersion()
* getAppVersionCode()
* getPackageName()

## Throwable
* getStackTraceText()

## Closeable
* closeQuietly()
* use()

## Object
* TAG()

## SystemService
* accessibilityManager
* accountManager
* activityManager
* alarmManager
* appWidgetManager()
* appOpsManager()
* audioManager
* batteryManager()
* bluetoothAdapter()
* cameraManager()
* captioningManager()
* clipboardManager
* connectivityManager
* consumerIrManager()
* devicePolicyManager
* displayManager()
* downloadManager
* dropBoxManager
* inputMethodManager
* inputManager()
* jobScheduler()
* keyguardManager
* launcherApps()
* layoutInflater
* locationManager
* mediaProjectionManager()
* mediaRouter()
* mediaSessionManager()
* nfcManager
* notificationManager
* nsdManager()
* powerManager
* printManager()
* restrictionsManager()
* searchManager
* sensorManager
* storageManager
* telecomManager()
* telephonyManager
* textServicesManager
* tvInputManager()
* uiModeManager
* usbManager
* userManager()
* vibrator
* wallpaperService
* wifiP2pManager
* wifiManager
* windowService

## View
* hideKeyboard()
* showKeyboard()
* click()
* clickWithTrigger()
* longClick()
* toBitmap()


联系方式
===

Wechat：fengzhizi715


> Java与Android技术栈：每周更新推送原创技术文章，欢迎扫描下方的公众号二维码并关注，期待与您的共同成长和进步。

![](https://user-gold-cdn.xitu.io/2018/7/24/164cc729c7c69ac1?w=344&h=344&f=jpeg&s=9082)

License
-------

    Copyright (C) 2017 - present, Tony Shen.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.