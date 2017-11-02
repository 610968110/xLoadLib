
package lbx.xloadlib;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lbx.xloadlib.bean.RecFile;
import lbx.xloadlib.callback.AbstractDownloadFinishCallback;
import lbx.xloadlib.callback.BaseDownloadFinishCallback;
import lbx.xloadlib.task.FileSizeTask;
import lbx.xloadlib.utils.FileUtils;

public class DownloadModel {
    private ThreadPoolExecutor mPoll;
    private CompletionService<RecFile> mService;
    private static DownloadModel INSTANCE;

    protected static DownloadModel getInstance(ThreadPoolExecutor poolExecutor) {
        if (INSTANCE == null) {
            synchronized (DownloadModel.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DownloadModel(poolExecutor);
                }
            }
        }

        return INSTANCE;
    }

    private DownloadModel(ThreadPoolExecutor poll) {
        this.mPoll = poll;
        this.mService = new ExecutorCompletionService(this.mPoll);
    }

    public long getHttpFileSize(final String url) {
        byte s = 1;
        final CountDownLatch countDownLatch = new CountDownLatch(s);
        final long[] lenght = new long[]{-1L};
        this.mPoll.execute(new Runnable() {
            @Override
            public void run() {
                lenght[0] = DownloadModel.this.getHttpFileSizeOnMainThread(url);
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await(5L, TimeUnit.SECONDS);
        } catch (InterruptedException var6) {
            var6.printStackTrace();
        }

        return lenght[0];
    }

    public long getHttpFileSizeOnMainThread(String url) {
        long lenght = 0L;
        short SC_OK = 200;

        try {
            URL e = null;
            e = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) e.openConnection();
            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode == SC_OK) {
                lenght = (long) conn.getContentLength();
            }
        } catch (MalformedURLException var8) {
            var8.printStackTrace();
        } catch (IOException var9) {
            var9.printStackTrace();
        }

        return lenght;
    }

    public boolean httpFileDownloadFinish(String url, File file) {
        try {
            return this.getHttpFileSize(url) <= FileUtils.FileSizeUtils.getFileSize(file);
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }

    public void httpFileDownloadFinishWithProgress(List<RecFile> recFiles, AbstractDownloadFinishCallback callback) throws InterruptedException, ExecutionException {
        ArrayList list = new ArrayList();
        if (recFiles != null && recFiles.size() > 0) {
            for (int i = 0; i < recFiles.size(); ++i) {
                this.mService.submit(new FileSizeTask((RecFile) recFiles.get(i)));
                RecFile recFile = (RecFile) this.mService.take().get();
                list.add(recFile);
                if (callback != null) {
                    callback.result(recFiles.size(), i, recFile.getPercent(), recFile.getKey(), recFile.isDownloadFinish());
                }
            }
        }

        if (callback != null) {
            callback.finish(list);
        }

    }

    public void httpFileDownloadFinish(List<RecFile> recFiles, BaseDownloadFinishCallback callback) throws InterruptedException, ExecutionException {
        if (recFiles != null && recFiles.size() > 0) {
            List<FileSizeTask> list = new ArrayList<>();
            for (int i = 0; i < recFiles.size(); i++) {
                RecFile suc = recFiles.get(i);
                list.add(new FileSizeTask(suc));
            }

            List<Future<RecFile>> futures = mPoll.invokeAll(list);
            List<RecFile> suc = new ArrayList<>();
            List<RecFile> err = new ArrayList<>();

            for (int j = 0; j < futures.size(); ++j) {
                Future<RecFile> fileFuture = futures.get(j);
                if (fileFuture.isDone()) {
                    RecFile file =  fileFuture.get();
                    if (file.isDownloadFinish()) {
                        suc.add(file);
                    } else {
                        err.add(file);
                    }

                    if (callback != null) {
                        callback.result(recFiles.size(), j, file.getPercent(), file.getKey(), file.isDownloadFinish());
                    }
                }
            }

            if (callback != null) {
                callback.scanDesc(suc, err);
                callback.finish(recFiles);
            }
        }

    }
}
