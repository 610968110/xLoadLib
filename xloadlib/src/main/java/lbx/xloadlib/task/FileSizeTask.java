package lbx.xloadlib.task;


import java.io.File;
import java.util.concurrent.Callable;

import lbx.xloadlib.bean.RecFile;
import lbx.xloadlib.utils.FileUtils;
import lbx.xloadlib.xLoad;

public class FileSizeTask implements Callable<RecFile> {
    private RecFile recFile;

    public FileSizeTask(RecFile recFile) {
        this.recFile = recFile;
    }

    @Override
    public RecFile call() throws Exception {
        long totleSize = xLoad.DownloadModel().getHttpFileSizeOnMainThread(this.recFile.getUrl());
        long fileSize = FileUtils.FileSizeUtils.getFileSize(this.recFile.getFile());
        if (fileSize == 0L) {
            File file = this.recFile.getFile();
            File tmp = new File(file.getAbsolutePath() + ".tmp");
            fileSize = FileUtils.FileSizeUtils.getFileSize(tmp);
        }

        this.recFile.setTotleSize(totleSize);
        this.recFile.setCurrentSize(fileSize);
        this.recFile.setPercent((float) fileSize * 1.0F / (float) totleSize * 100.0F);
        this.recFile.setDownloadFinish(totleSize != 0 && totleSize <= fileSize);
        return this.recFile;
    }
}
