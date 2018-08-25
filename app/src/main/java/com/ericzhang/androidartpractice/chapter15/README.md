# Android性能优化

## 布局优化

    1.减少布局文件层级，使用高性能布局，LinearLayout优于RelativeLayout
    2.include/merge
    3.ViewStub
    
## 绘制优化
    
  主要是onDraw()方法
    
    1.onDraw()中不要创建新的局部变量。onDraw可能会被频繁调用，一瞬间产生大量临时变量，占用过多内存从而导致系统频繁GC导致效率低下
    2.onDraw()中不要做耗时的任务，也不能执行成千上万的循环操作
    
## 内存泄漏优化

  studio中Android Profiler分析Memory：Force GC，然后Dump Java Heap，按照包名查看自己写的类是否泄漏 
  LeakCanary

    1.静态变量导致内存泄漏：mContext = this，静态变量的生命周期远远长于Activity，导致Activity关闭之后静态变量mContext任然引用该Activity从而无法回收
        onDestroy时将mContext = null
    2.单例模式导致内存泄漏：单例生命周期和Application一样，该单例持有Activity的引用时
        Activity退出时将单例模式内的引用置为null（此例中为ArrayList<Listner>）
        如果需要使用Context，不要传入Activity的Context， 正确的做法是使用Application的Context
    3.属性动画导致内存泄漏：属性动画一直播放没有cancel()
        onDestroy的时候属性动画要cancel掉
    4.AsyncTask导致内存泄漏-->内部类持有外部Activity的引用
        内部类（AsynTask）设置为static（static的内部类不会持有对外部类的引用），之后对外部类Activity使用WeakReference引用。
    5.Handler导致内存泄漏：延迟发送消息，Activity退出之后消息还没执行，同4中内部类持有外部Activity的引用
        Handler设置为static然后持有对外部Activity的WeakReference，Activity的onDestroy中调用handler.removeCallbacksAndMessages(null)
    6.匿名内部类造成内存泄漏：子线程runnable中执行耗时操作，Activity结束后runnable还在执行-->和AsyncTask很相似
    7.各种流（文件流），各种Cursor没有关闭
    8.Activity中的变量创建等，最好遵循谁创建谁释放的原则

## 响应速度优化和ANR日志分析

  避免在主线程做耗时操作
  adb pull /data/anr/traces.txt：pid，cmd line
  BlockCanary

## ListView与Bitmap优化

  参考12章
  ViewHolder，静止时加载，getView方法去掉重复调用，开启硬件加速
  Bitmap压缩到控件大小，内存缓存与磁盘缓存
  
## 线程优化

  参考11章
  采用线程池，线程池构造配置参考AsyncTask源码

## 其他

  避免创建过多对象
  不要过多使用枚举，枚举占用内存空间比整型大
  常量使用static final来修饰
  使用Android特有的数据结构，SparseArray和Pair
  适当使用软引用SoftReference，弱引用WeakReference
  采用内存缓存与磁盘缓存
  采用静态内部类，避免潜在的内存泄漏
