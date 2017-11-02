//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package lbx.xloadlib.callback;


import java.util.List;

import lbx.xloadlib.bean.RecFile;

public abstract class BaseDownloadFinishCallback extends AbstractDownloadFinishCallback {

    @Override
    public void result(int total, int pos, float percent, String key, boolean isFinish) {
    }

    public abstract void scanDesc(List<RecFile> suc, List<RecFile> err);

    @Override
    public void finish(List<RecFile> files) {
    }
}
