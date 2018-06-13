# xLoadLob

该框架是基于xutils3基础上进行二次封装的下载任务框架，用户可在任何地方注册监听，
下载文件，实时查看文件下载进度，封装了检查文件是否下载完成的功能，支持多线程
断点下载。

一、引入
===
在build中引入：
````Xml
 compile 'org.xutils:xutils:3.5.0'
 compile 'com.lbx:xLoad:1.0.0'
````

二、初始化
===
在Application的onCreate方法中：
````Java
        DownloadBuilder builder = new DownloadBuilder()
                //设置核心线程数
                .setCoreNum(5)
                //设置下载线程回收时间
                .setThreadRecycleTime(30)
                //设置同时下载数量
                .setMaxTask(5);
        xLoad download = xLoad.build(this, builder);
````

三、使用
===
````Java
        //注册监听
        xLoad.getInstance().register(this, mCallback);
````

````Java
        //开始下载文件 可多次调用这个方法并行下载多个文件
        xLoad.getInstance().start(this, "key,用于区别下载的文件", "url", "下载后存储的路径", "下载后存储的文件名");
````

其中callback为：

````Java
private BaseDownLoadCallback mCallback = new BaseDownLoadCallback() {
    /**
     * 注册监听成功的回调
     */
    @Override
    public void registerSuccess() {
    }
    /**
     * 开始下载
     * 某个文件开始下载时回调
     * @param key key
     */
    @Override
    public void onStart(String key) {
    }
    /**
     * 等待下载
     * 某个文件等待下载中时回调
     * 当同时下载的任务数量超过初始化时设置的并行下载数量，会使下载任务进入等待状态，完成一个下载任务后会自动开始下载
     * @param key key
     */
    @Override
    public void onWait(String key) {
    }
    /**
     * 取消下载
     * 某个文件取消下载时回调
     * @param key key
     */
    @Override
    public void onCancelled(String key) {
    }
    /**
     * 下载中的进度，会回调多次
     * @param key key
     * @param total 总大小
     * @param current 当前已下载大小
     * @param percent 下载完成百分比
     * @param isUploading 是否下载完成
     */
    @Override
    public void onLoading(String key, long total, long current, float percent, boolean isUploading) {
    }
    /**
     * 下载失败回调
     * @param key key
     * @param e 失败原因
     */
    @Override
    public void onFailure(String key, String e) {
    }
    /**
     * 下载成功回调
     * @param key key
     * @param file 下载完成的文件
     */
    @Override
    public void onSuccess(String key, File file) {
    }
    /**
     * 下载失败回调和下载成功回调 回调后 都会调用此方法
     * @param key key
     * @param isSuccess 是否下载成功
     */
    @Override
    public void onFinished(String key, boolean isSuccess) {
    }
};
````


````Java
        xLoad.getInstance().cancel(this, "key，取消哪个下载任务");
````

````Java
 //list：传入需要扫描的文件
 xLoad.DownloadModel().httpFileDownloadFinish(list, new BaseDownloadFinishCallback() {
     /**
      * 全部扫描完后调用
      * 扫描文件，查看文件是否已经下载完成
      * @param suc 下载完成
      * @param err 下载未完成
      */
     @Override
     public void scanDesc(List<RecFile> suc, List<RecFile> err) {
         //RecFile中的字段
         //File file; 文件
         //String url; 下载地址
         //String key; key
         //long totleSize; 总大小
         //long currentSize; 当前已下载大小
         //float percent; 下载完成百分比
         //boolean downloadFinish; 是否下载完成
     }
     /**
      * 每扫描玩list中的一个文件调用一次
      * @param total 总大小
      * @param pos list中的pos
      * @param percent 下载完成百分比
      * @param key key
      * @param isFinish 是否下载完成
      */
     @Override
     public void result(int total, int pos, float percent, String key, boolean isFinish) {
     }
     /**
      * 全部扫描完后调用
      * @param files 所有下载成功的文件集合
      */
     @Override
     public void finish(List<RecFile> files) {
     }
 });
````

````Java
        //注销监听
        xLoad.getInstance().unregister(this, mCallback);
````