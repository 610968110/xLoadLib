//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package lbx.xloadlib;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import org.xutils.x.Ext;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

import lbx.xloadlib.bean.DLInfo;
import lbx.xloadlib.callback.BaseDownLoadCallback;
import lbx.xloadlib.service.DownloadService;
import lbx.xloadlib.thread.DownloadThread;

public class xLoad {
    private static xLoad mDownload;
    private static DownloadBuilder mBuilder;
    private static ThreadPoolExecutor mPoll;
    private Map<String, BaseDownLoadCallback> mCallbackMap = new LinkedHashMap();

    public static xLoad getInstance() {
        if(mBuilder == null) {
            throw new NullPointerException("DownloadBuilder may bu null");
        } else {
            if(mDownload == null) {
                Class var0 = xLoad.class;
                synchronized(xLoad.class) {
                    if(mDownload == null) {
                        mDownload = new xLoad();
                    }
                }
            }

            return mDownload;
        }
    }

    private xLoad() {
        initDownloadPool();
    }

    private static void initDownloadPool() {
        if(mPoll == null) {
            synchronized(DownloadService.class) {
                if(mPoll == null) {
                    mPoll = new ThreadPoolExecutor(Config.getCoreNum(), Config.getMaxNum(), (long)Config.getOutTime(),
                            TimeUnit.SECONDS, new LinkedBlockingDeque(), new DownloadThread(), new AbortPolicy());
                }
            }
        }

    }

    public DownloadBuilder getBuilder() {
        return mBuilder;
    }

    public static xLoad build(Application app, DownloadBuilder builder) {
        int coreNum = builder.getCoreNum();
        int maxNum = builder.getMaxNum();
        int maxDownload = builder.getMaxDownload();
        int outTime = builder.getOutTime();
        mBuilder = builder;
        Ext.init(app);
        Ext.setDebug(false);
        Config.setCoreNum(coreNum);
        Config.setMaxNum(maxNum);
        Config.setMaxDownload(maxDownload);
        Config.setOutTime(outTime);
        return getInstance();
    }

    public void register(Activity activity, BaseDownLoadCallback callback) {
        this.mCallbackMap.put(activity.getClass().getName(), callback);
        Intent intent = new Intent(activity, DownloadService.class);
        if(!Config.SERVICE_RUNNING) {
            activity.startService(intent);
        }

        activity.bindService(intent, callback, Context.BIND_AUTO_CREATE);
    }

    public void unregister(Activity activity, BaseDownLoadCallback callback) {
        activity.unbindService(callback);
        this.mCallbackMap.remove(activity.getClass().getName());
    }

    public void start(Activity activity, String key, String url, String filepath, String fileName) {
        this.makeDirs(filepath);
        BaseDownLoadCallback callback = (BaseDownLoadCallback)this.mCallbackMap.get(activity.getClass().getName());
        if(callback != null && callback.serviceInstance() != null) {
            DownloadService service = callback.serviceInstance();
            if(!TextUtils.isEmpty(filepath)) {
                String e = "/";
                if(!filepath.endsWith(e)) {
                    filepath = filepath + e;
                }
            }

            service.prepare(this, key, url, filepath + fileName, activity);
        }

    }

    private void makeDirs(String path) {
        File file = new File(path);
        if(!file.exists()) {
            file.mkdirs();
        }

    }

    public Map<String, BaseDownLoadCallback> getCallbacks() {
        return this.mCallbackMap;
    }

    public void cancel(Activity activity, String key) {
        BaseDownLoadCallback callback = (BaseDownLoadCallback)this.mCallbackMap.get(activity.getClass().getName());
        if(callback != null && callback.serviceInstance() != null) {
            DownloadService service = callback.serviceInstance();
            service.cancel(key);
        }

    }

    public List<DLInfo> getWaittingTask(Activity activity) {
        Object waitList = new LinkedList();
        BaseDownLoadCallback callback = (BaseDownLoadCallback)this.mCallbackMap.get(activity.getClass().getName());
        if(callback != null && callback.serviceInstance() != null) {
            DownloadService service = callback.serviceInstance();
            waitList = service.getWaitList();
        }

        return (List)waitList;
    }

    public static DownloadModel DownloadModel() {
        initDownloadPool();
        return DownloadModel.getInstance(mPoll);
    }
}
