package lbx.xload;

import android.app.Application;

import lbx.xloadlib.DownloadBuilder;
import lbx.xloadlib.xLoad;

/**
 * @author lbx
 * @date 2017/10/31.
 */

public class App  extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        DownloadBuilder builder = new DownloadBuilder()
                .setCoreNum(5)
                .setThreadRecycleTime(30)
                .setMaxTask(5);
        xLoad download = xLoad.build(this, builder);
    }
}
