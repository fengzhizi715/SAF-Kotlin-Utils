# SAF-Kotlin-Utils

[![@Tony沈哲 on weibo](https://img.shields.io/badge/weibo-%40Tony%E6%B2%88%E5%93%B2-blue.svg)](http://www.weibo.com/fengzhizi715)
[![License](https://img.shields.io/badge/license-Apache%202-lightgrey.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

用 Kolin 做的工具类库，包括 utils 和 extension

模块|saf-kotlin-utils|saf-kotlin-ext
---|:-------------:|:-------------:
最新版本|[ ![Download](https://api.bintray.com/packages/fengzhizi715/maven/saf-kotlin-utils/images/download.svg) ](https://bintray.com/fengzhizi715/maven/saf-kotlin-utils/_latestVersion)|[ ![Download](https://api.bintray.com/packages/fengzhizi715/maven/saf-kotlin-ext/images/download.svg) ](https://bintray.com/fengzhizi715/maven/saf-kotlin-ext/_latestVersion)|


# 下载安装

Gradle:

```groovy
implementation 'com.safframework.utils:saf-kotlin-utils:0.0.4.5'
implementation 'com.safframework.utils:saf-kotlin-ext:0.0.3.4'
```

## 工具库的方法


## 扩展库的方法

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

## View
* hideKeyboard()
* showKeyboard()
* click()
* clickWithTrigger()
* longClick()
* toBitmap()