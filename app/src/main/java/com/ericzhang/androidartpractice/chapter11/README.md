# Android中的线程和线程池

## Android中的线程形态

### AsyncTask

    使用：
    1.onPreExecute()：主线程执行，异步任务执行前调用
    2.doInBackground(Params...params)：线程池中执行，用于执行异步任务，通过publishProgress()来调用onProgressUpdate()发布任务执行进度，最后返回结果后调用onPostExecute()方法
    3.onProgressUpdate(Progress...values)：主线程执行，用于更新异步任务执行进度
    4.onPostExecute(Result result)：主线程执行，异步任务执行完成后调用，result是doInBackground()返回的值
    5.onCancelled()和onCancelled(Result result)：主线程执行，调用取消后先调用onCancelled()后调用onCancelled(Result result)

    弊端：
    1.AsyncTask的类在4.1之前必须在主线程中加载
    2.AsyncTask对象必须在主线程中创建（存疑）
    3.execute方法必须在主线程中调用（存疑）
    4.不要在程序中直接调用onPreExecute(),onPostExecute(),doInBackground()和onProgressUpdate()
    5.一个AsyncTask对象只能执行一次，即只能调用一次execute方法
    6.AsyncTask默认是串行执行传入的参数任务(除了Android1.6)

### HandlerThread

### IntentService

