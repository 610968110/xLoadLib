package lbx.xload;

import android.app.Application;

import lbx.xloadlib.DownloadBuilder;
import lbx.xloadlib.xLoad;

/**
 * @author lbx
 * @date 2017/10/31.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DownloadBuilder builder = new DownloadBuilder()
                //设置核心线程数
                .setCoreNum(5)
                //设置下载线程回收时间
                .setThreadRecycleTime(30)
                //设置同时下载数量
                .setMaxTask(5);
        xLoad download = xLoad.build(this, builder);
    }
}
