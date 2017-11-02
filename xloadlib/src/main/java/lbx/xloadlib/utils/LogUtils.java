package lbx.xloadlib.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LogUtils {
    public static final int LEVEL_NONE = 0;
    public static final int LEVEL_ERROR = 1;
    public static final int LEVEL_WARN = 2;
    public static final int LEVEL_INFO = 3;
    public static final int LEVEL_DEBUG = 4;
    public static final int LEVEL_VERBOSE = 5;
    private static String mTag = "xys";
    private static int mDebuggable = 5;

    public static void v(String msg) {
        if(mDebuggable >= 5) {
            if(msg.length() < 4000) {
                Log.v(mTag, msg);
            } else {
                String msg1 = msg.substring(0, 4000);
                String msg2 = msg.substring(4000, msg.length());
                Log.v(mTag, msg1);
                Log.v(mTag, msg2);
            }
        }

    }

    public static void d(String msg) {
        if(mDebuggable >= 4) {
            if(msg.length() < 4000) {
                Log.d(mTag, msg);
            } else {
                String msg1 = msg.substring(0, 4000);
                String msg2 = msg.substring(4000, msg.length());
                Log.d(mTag, msg1);
                Log.d(mTag, msg2);
            }
        }

    }

    public static void i(String msg) {
        if(mDebuggable >= 3) {
            if(msg.length() < 4000) {
                Log.i(mTag, msg);
            } else {
                String msg1 = msg.substring(0, 4000);
                String msg2 = msg.substring(4000, msg.length());
                Log.i(mTag, msg1);
                Log.i(mTag, msg2);
            }
        }

    }

    public static void w(String msg) {
        if(mDebuggable >= 2) {
            if(msg.length() < 4000) {
                Log.w(mTag, msg);
            } else {
                String msg1 = msg.substring(0, 4000);
                String msg2 = msg.substring(4000, msg.length());
                Log.w(mTag, msg1);
                Log.w(mTag, msg2);
            }
        }

    }

    public static void w(Throwable tr) {
        if(mDebuggable >= 2) {
            Log.w(mTag, "", tr);
        }

    }

    public static void w(String msg, Throwable tr) {
        if(mDebuggable >= 2 && null != msg) {
            if(msg.length() < 4000) {
                Log.w(mTag, msg);
            } else {
                String msg1 = msg.substring(0, 4000);
                String msg2 = msg.substring(4000, msg.length());
                Log.w(mTag, msg1);
                Log.w(mTag, msg2);
            }
        }

    }

    public static void e(String msg) {
        if(mDebuggable >= 1) {
            if(msg.length() <= 3000) {
                Log.e(mTag, msg);
            } else {
                String msg1;
                String msg2;
                if(msg.length() < 6000) {
                    msg1 = msg.substring(0, 3000);
                    msg2 = msg.substring(3000, msg.length());
                    Log.e(mTag, "长度 6000  开始");
                    Log.e(mTag, msg1);
                    Log.e(mTag, msg2);
                    Log.e(mTag, "长度 6000  结束");
                } else {
                    String msg3;
                    if(msg.length() < 9000) {
                        msg1 = msg.substring(0, 3000);
                        msg2 = msg.substring(3000, 6000);
                        msg3 = msg.substring(6000, msg.length());
                        Log.e(mTag, "长度 9000  开始");
                        Log.e(mTag, msg1);
                        Log.e(mTag, msg2);
                        Log.e(mTag, msg3);
                        Log.e(mTag, "长度 9000  结束");
                    } else {
                        String msg4;
                        if(msg.length() < 12000) {
                            msg1 = msg.substring(0, 3000);
                            msg2 = msg.substring(3000, 6000);
                            msg3 = msg.substring(6000, 9000);
                            msg4 = msg.substring(9000, msg.length());
                            Log.e(mTag, "长度 12000  开始");
                            Log.e(mTag, msg1);
                            Log.e(mTag, msg2);
                            Log.e(mTag, msg3);
                            Log.e(mTag, msg4);
                            Log.e(mTag, "长度 12000  结束");
                        } else {
                            msg1 = msg.substring(0, 3000);
                            msg2 = msg.substring(3000, 6000);
                            msg3 = msg.substring(6000, 9000);
                            msg4 = msg.substring(9000, 12000);
                            String msg5 = msg.substring(12000, msg.length());
                            Log.e(mTag, "长度 12000 以上  开始");
                            Log.e(mTag, msg1);
                            Log.e(mTag, msg2);
                            Log.e(mTag, msg3);
                            Log.e(mTag, msg4);
                            Log.e(mTag, msg5);
                            Log.e(mTag, "长度 12000 以上  结束");
                        }
                    }
                }
            }
        }

    }

    public static void e(Throwable tr) {
        if(mDebuggable >= 1) {
            Log.e(mTag, "", tr);
        }

    }

    public static void e(String msg, Throwable tr) {
        if(mDebuggable >= 1 && null != msg) {
            if(msg.length() < 4000) {
                Log.e(mTag, msg);
            } else {
                String msg1 = msg.substring(0, 4000);
                String msg2 = msg.substring(4000, msg.length());
                Log.e(mTag, msg1);
                Log.e(mTag, msg2);
            }
        }

    }

    public static void writeFileData(Context c, String fileName, String message) {
        try {
            FileOutputStream e = c.openFileOutput(fileName, 0);
            byte[] bytes = message.getBytes();
            e.write(bytes);
            e.close();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public static String writeSDCardData(String data, String fileName) {
        if(Environment.getExternalStorageState().equals("mounted")) {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            File file = new File(path, fileName);
            FileOutputStream s = null;

            String var6;
            try {
                s = new FileOutputStream(file);
                s.write(data.getBytes());
                return "success";
            } catch (FileNotFoundException var18) {
                var18.printStackTrace();
                var6 = "FileNotFoundException:" + var18.toString();
                return var6;
            } catch (IOException var19) {
                var19.printStackTrace();
                var6 = "IOException:" + var19.toString();
            } finally {
                if(s != null) {
                    try {
                        s.close();
                    } catch (IOException var17) {
                        var17.printStackTrace();
                    }
                }

            }

            return var6;
        } else {
            return "没有SD卡";
        }
    }
}
