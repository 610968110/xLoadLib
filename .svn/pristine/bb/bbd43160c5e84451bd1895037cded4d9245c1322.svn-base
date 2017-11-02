package lbx.xload;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import lbx.xloadlib.bean.RecFile;
import lbx.xloadlib.callback.BaseDownLoadCallback;
import lbx.xloadlib.callback.BaseDownloadFinishCallback;
import lbx.xloadlib.xLoad;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xLoad.getInstance().register(this, mCallback);
        xLoad.getInstance().start(this, "", "", "", "");
        xLoad.getInstance().cancel(this, "");
        try {
            xLoad.DownloadModel().httpFileDownloadFinish(null, new BaseDownloadFinishCallback() {

                @Override
                public void scanDesc(List<RecFile> suc, List<RecFile> err) {

                }

                @Override
                public void result(int total, int pos, float percent, String key, boolean isFinish) {
                    super.result(total, pos, percent, key, isFinish);
                }

                @Override
                public void finish(List<RecFile> files) {
                    super.finish(files);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        xLoad.getInstance().unregister(this, mCallback);
    }

    private BaseDownLoadCallback mCallback = new BaseDownLoadCallback() {
        @Override
        public void registerSuccess() {

        }

        @Override
        public void onStart(String key) {
            super.onStart(key);
        }

        @Override
        public void onWait(String key) {
            super.onWait(key);
        }

        @Override
        public void onCancelled(String key) {
            super.onCancelled(key);
        }

        @Override
        public void onLoading(String key, long total, long current, float percent, boolean isUploading) {
            super.onLoading(key, total, current, percent, isUploading);
        }

        @Override
        public void onFailure(String var1, String var2) {

        }

        @Override
        public void onSuccess(String var1, File var2) {

        }

        @Override
        public void onFinished(String key, boolean isSuccess) {
            super.onFinished(key, isSuccess);
        }
    };
}
