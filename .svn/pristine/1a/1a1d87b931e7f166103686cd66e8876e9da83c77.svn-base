package lbx.xloadlib;


import lbx.xloadlib.utils.PhoneUtil;

public class DownloadBuilder {
    private int coreNum = 5;
    private int maxNum = 10;
    private int maxDownload = 5;
    private int outTime = 60;
    private int i = 2;

    public DownloadBuilder() {
    }

    public DownloadBuilder setCoreNum(int coreNum) {
        if(coreNum > PhoneUtil.getCpuCoreNum() / this.i) {
            coreNum = PhoneUtil.getCpuCoreNum() / this.i;
        }

        this.coreNum = coreNum;
        this.maxDownload = coreNum;
        return this;
    }

    public DownloadBuilder setMaxTask(int maxDownload) {
        this.maxDownload = maxDownload;
        return this;
    }

    public DownloadBuilder setThreadRecycleTime(int second) {
        if(second > 0) {
            this.outTime = second;
        }

        return this;
    }

    protected int getCoreNum() {
        return this.coreNum;
    }

    protected int getMaxNum() {
        return this.maxNum;
    }

    protected int getMaxDownload() {
        return this.maxDownload;
    }

    protected int getOutTime() {
        return this.outTime;
    }
}
