# Bitmap的加载和Cache

## Bitmap的高效加载
  
    inSampleSize

## Android中的缓存策略

    1.内存缓存：LruCache
    2.磁盘缓存：DiskLruCache
    3.自定义ImageLoader：同步/异步加载，内存/磁盘缓存，网络/本地图片压缩，网络/本地图片加载

## ImageLoader的使用

    1.不要在getView中执行耗时操作，采用异步加载
    2.控制异步任务的执行频率，停止滑动时加载
    3.硬件加速
    4.getView方法去重

