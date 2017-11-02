package lbx.xloadlib.bean;

import android.app.Activity;

import org.xutils.common.Callback;

import java.io.Serializable;

import lbx.xloadlib.callback.BaseDownLoadCallback;
import lbx.xloadlib.xLoad;


public class DLInfo implements Serializable {
    private Callback.Cancelable cancelable;
    private BaseDownLoadCallback callback;
    private String key;
    private Activity activity;
    private String url;
    private String path;
    private xLoad xdownload;

    public DLInfo(Activity activity, xLoad xdownload, Callback.Cancelable cancelable, BaseDownLoadCallback callback, String key, String url, String path) {
        this.url = url;
        this.xdownload = xdownload;
        this.path = path;
        this.activity = activity;
        this.cancelable = cancelable;
        this.callback = callback;
        this.key = key;
    }



    public Callback.Cancelable getCancelable() {
        return this.cancelable;
    }

    public void setCancelable(Callback.Cancelable httpHandler) {
        this.cancelable = httpHandler;
    }

    public BaseDownLoadCallback getCallback() {
        return this.callback;
    }

    public void setCallback(BaseDownLoadCallback callback) {
        this.callback = callback;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public xLoad getXdownload() {
        return this.xdownload;
    }

    public void setXdownload(xLoad xdownload) {
        this.xdownload = xdownload;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
