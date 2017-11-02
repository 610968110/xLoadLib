
package lbx.xloadlib.callback;


import java.util.List;

import lbx.xloadlib.bean.RecFile;

public abstract class AbstractDownloadFinishCallback {
    public AbstractDownloadFinishCallback() {
    }

    public abstract void result(int var1, int var2, float var3, String var4, boolean var5);

    public abstract void finish(List<RecFile> var1);
}
