//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package lbx.xloadlib.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lbx.xloadlib.Config;
import lbx.xloadlib.bean.DLInfo;
import lbx.xloadlib.callback.BaseDownLoadCallback;
import lbx.xloadlib.xLoad;


/**
 * @author lbx
 */
public class DownloadService extends Service {
    private Map<String, DLInfo> mMap;
    private List<DLInfo> mWaitList;

    public DownloadService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        this.initDownloadPool();
        return new DownloadService.MyBinder();
    }

    @Override
    public void onCreate() {
        Config.SERVICE_RUNNING = true;
        super.onCreate();
        this.initDownloadPool();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.initDownloadPool();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Config.SERVICE_RUNNING = false;
        super.onDestroy();
    }

    public void initDownloadPool() {
        if (this.mMap == null || this.mWaitList == null) {
            Class var1 = DownloadService.class;
            synchronized (DownloadService.class) {
                if (this.mMap == null || this.mWaitList == null) {
                    this.mMap = new LinkedHashMap();
                    this.mWaitList = new LinkedList();
                }
            }
        }

    }

    public void prepare(xLoad xLoad, String key, String url, String path, Activity activity) {
        String name = activity.getClass().getName();
        if (!this.taskIsRunning(key)) {
            if (!this.taskIsWaiting(key, url)) {
                if (this.mMap.keySet().size() >= Config.getMaxDownload()) {
                    DLInfo cancelable1 = new DLInfo(activity, xLoad, null, xLoad.getCallbacks().get(name), key, url, path);
                    this.mWaitList.add(cancelable1);
                    Map dlInfo1 = xLoad.getCallbacks();
                    Iterator var9 = dlInfo1.keySet().iterator();

                    while (var9.hasNext()) {
                        String k = (String) var9.next();
                        BaseDownLoadCallback callback = (BaseDownLoadCallback) dlInfo1.get(k);
                        if (callback != null) {
                            callback.onWait(key);
                        }
                    }

                } else {
                    Callback.Cancelable cancelable = this.download(xLoad, key, url, path);
                    DLInfo dlInfo = new DLInfo(activity, xLoad, cancelable,  xLoad.getCallbacks().get(name), key, url, path);
                    if (this.mMap.keySet().size() < Config.getMaxDownload()) {
                        this.mMap.put(key, dlInfo);
                    }

                }
            }
        }
    }

    private Callback.Cancelable download(final xLoad xLoad, final String key, String url, String path) {
        RequestParams params = new RequestParams(url);
        params.setSaveFilePath(path);
        params.setAutoRename(false);
        return x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onStarted() {
                Map callbacks = xLoad.getCallbacks();
                Iterator var2 = callbacks.keySet().iterator();

                while (var2.hasNext()) {
                    String k = (String) var2.next();
                    BaseDownLoadCallback callback = (BaseDownLoadCallback) callbacks.get(k);
                    if (callback != null) {
                        callback.onStart(key);
                    }
                }

            }

            @Override
            public void onSuccess(File result) {
                DownloadService.this.mMap.remove(key);
                DownloadService.this.addRunningTask();
                Map callbacks = xLoad.getCallbacks();
                Iterator var3 = callbacks.keySet().iterator();

                while (var3.hasNext()) {
                    String k = (String) var3.next();
                    BaseDownLoadCallback callback = (BaseDownLoadCallback) callbacks.get(k);
                    if (callback != null) {
                        callback.onSuccess(key, result);
                        callback.onFinished(key, true);
                    }
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                DownloadService.this.mMap.remove(key);
                DownloadService.this.addRunningTask();
                Map callbacks = xLoad.getCallbacks();
                Iterator var4 = callbacks.keySet().iterator();

                while (var4.hasNext()) {
                    String k = (String) var4.next();
                    BaseDownLoadCallback callback = (BaseDownLoadCallback) callbacks.get(k);
                    if (callback != null) {
                        callback.onFailure(key, ex.toString());
                        callback.onFinished(key, false);
                    }
                }

            }

            @Override
            public void onCancelled(CancelledException cex) {
                DownloadService.this.mMap.remove(key);
                DownloadService.this.addRunningTask();
                Map callbacks = xLoad.getCallbacks();
                Iterator var3 = callbacks.keySet().iterator();

                while (var3.hasNext()) {
                    String k = (String) var3.next();
                    BaseDownLoadCallback callback = (BaseDownLoadCallback) callbacks.get(k);
                    if (callback != null) {
                        callback.onCancelled(key);
                    }
                }

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                Map callbacks = xLoad.getCallbacks();
                Iterator var7 = callbacks.keySet().iterator();

                while (var7.hasNext()) {
                    String k = (String) var7.next();
                    BaseDownLoadCallback callback = (BaseDownLoadCallback) callbacks.get(k);
                    if (callback != null) {
                        float percent = (float) current * 1.0F / (float) total * 100.0F;
                        callback.onLoading(key, total, current, percent, isDownloading);
                    }
                }

            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onWaiting() {
            }
        });
    }

    private boolean taskIsWaiting(String key, String url) {
        for (int i = 0; i < this.mWaitList.size(); ++i) {
            DLInfo info1 = (DLInfo) this.mWaitList.get(i);
            if (info1.getUrl().equals(url)) {
                info1.getCallback().onWait(key);
                return true;
            }
        }

        return false;
    }

    private boolean taskIsRunning(String key) {
        DLInfo info = (DLInfo) this.mMap.get(key);
        if (info != null) {
            info.getCallback().onStart(key);
            info.getCallback().onFailure(key, "task is running");
            info.getCallback().onFinished(key, false);
            return true;
        } else {
            return false;
        }
    }

    private void addRunningTask() {
        if (this.mWaitList.size() > 0) {
            DLInfo dlInfo = (DLInfo) this.mWaitList.get(0);
            this.mWaitList.remove(0);
            this.prepare(dlInfo.getXdownload(), dlInfo.getKey(), dlInfo.getUrl(), dlInfo.getPath(), dlInfo.getActivity());
        }

    }

    public void cancel(String key) {
        DLInfo info = (DLInfo) this.mMap.get(key);
        if (info != null) {
            info.getCancelable().cancel();
        } else {
            boolean exit = false;

            int i;
            for (i = 0; i < this.mWaitList.size(); ++i) {
                if (((DLInfo) this.mWaitList.get(i)).getKey().equals(key)) {
                    exit = true;
                    break;
                }
            }

            if (exit) {
                DLInfo info1 = (DLInfo) this.mWaitList.get(i);
                this.mWaitList.remove(i);
                info1.getCallback().onCancelled(info1.getKey());
            }
        }

    }

    public List<DLInfo> getWaitList() {
        return this.mWaitList;
    }

    public class MyBinder extends Binder {
        public MyBinder() {
        }

        public DownloadService getService() {
            return DownloadService.this;
        }
    }
}
