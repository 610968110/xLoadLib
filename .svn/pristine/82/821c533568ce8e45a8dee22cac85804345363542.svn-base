//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package lbx.xloadlib.callback;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import java.io.File;

import lbx.xloadlib.service.DownloadService;

public abstract class BaseDownLoadCallback implements ServiceConnection {
    private DownloadService mService;

    public BaseDownLoadCallback() {
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        this.mService = ((DownloadService.MyBinder)service).getService();
        this.registerSuccess();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
    }

    public DownloadService serviceInstance() {
        return this.mService;
    }

    public abstract void registerSuccess();

    public void onStart(String key) {
    }

    public void onWait(String key) {
    }

    public void onCancelled(String key) {
    }

    public void onLoading(String key, long total, long current, float percent, boolean isUploading) {
    }

    public abstract void onSuccess(String var1, File var2);

    public abstract void onFailure(String var1, String var2);

    public void onFinished(String key, boolean isSuccess) {
    }
}
