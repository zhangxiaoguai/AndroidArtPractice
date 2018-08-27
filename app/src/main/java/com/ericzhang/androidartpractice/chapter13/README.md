# 综合技术

## 使用CrashHandler来获取应用的crash信息

    Thread类中setDefaultUncaughtExceptionHandler
    代码中被catch的异常不会交给CrashHandler处理，CrashHandler只能收到那些未被捕获的异常
    
## 使用multidex来解决方法数越界

    Android中单个dex文件所能包含的最大方法数为65536
    
    1.build.gradle中defaultConfig中添加multiDexEnable true
    2.在dependencies中添加implementation 'com.android.support:multidex:1.0.3'
    3.三种方法：
        3.1如果没有替换Application类，则清单文件<application>标签中添加android:name="android.support.multidex.MultiDexApplication"
        3.2如果替换了Application类，使自定义的Application类继承MultiDexApplication
        3.3如果继承了Application类，但是无法使自定义的Application类继承MultiDexApplication，则重写attachBaseContext()方法并在其中调用MultiDex.install(this)来启用Dalvik可执行分包
    
    之后运行会生成多个classes.dex：classes.dex，classes2.dex，......
    
    弊端（局限性）：
        1.应用启动速度降低
        2.辅助dex文件较大可能会导致应用anr错误
        3.4.0版本可能会出错
        4.分包配置发出非常庞大的内存分配请求可能会在运行期间发生崩溃
    
    如果在启动区间需要的任何类未在主Dex中找到，应用会奔溃：java.lang.NoClassDefFoundError
    声明主Dex文件中需要的类：multiDexKeepFile file('maindexlist.txt')，multiDexKeepProguard file('maindexlist.pro')
    
## Android动态加载技术

  [DL动态加载框架技术文档](https://blog.csdn.net/singwhatiwanna/article/details/40283117)
 
  [dynamic-load-apk](https://github.com/singwhatiwanna/dynamic-load-apk)
    
    1.插件apk中资源的访问
    2.插件apk中Activity生命周期管理
    3.插件ClassLoader的管理

## 反编译初步
  
  [Android反编译学习](https://www.jianshu.com/p/4bcd38215181)
  
  apktool：反编译apk资源文件，二次打包apk
  
  dex2jar：反编译apk源码，之后可以通过jd-gui查看






