package lbx.xloadlib.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class FileUtils {

    public static String getDefaultPath(Context context) {
        String path = getSDCardPath();
        return !TextUtils.isEmpty(path)?Environment.getExternalStorageDirectory().getAbsolutePath():getFilePath(context);
    }

    public static String getSDCardPath() {
        return Environment.getExternalStorageState().equals("mounted")?Environment.getExternalStorageDirectory().getAbsolutePath():null;
    }

    private static String getFilePath(Context context) {
        return context.getFilesDir().getAbsolutePath();
    }

    public static String makeDirs(String path) {
        File savePathFile = new File(path);
        if(!savePathFile.exists() || !savePathFile.isDirectory()) {
            savePathFile.mkdirs();
        }

        return path;
    }

    public static class CopyFileUtils {
        public CopyFileUtils() {
        }

        public static void copyFile2FilesDir(Context context, String fileName) {
            File filesDir = context.getFilesDir();
            File targetFile = new File(filesDir, fileName);
            LogUtils.e("filesDir =" + filesDir);
            if(targetFile.exists()) {
                LogUtils.e(fileName + "文件已存在");
            } else {
                InputStream in = null;
                FileOutputStream out = null;

                try {
                    AssetManager e = context.getAssets();
                    in = e.open(fileName);
                    out = new FileOutputStream(targetFile);
                    boolean len = false;
                    byte[] buffer = new byte[1024];

                    int len1;
                    while((len1 = in.read(buffer)) != -1) {
                        out.write(buffer, 0, len1);
                    }

                    LogUtils.e(fileName + "拷贝完成");
                } catch (IOException var17) {
                    var17.printStackTrace();
                    LogUtils.e("没有文件：" + fileName + "   " + var17.toString());
                } finally {
                    try {
                        if(in != null) {
                            in.close();
                        }

                        if(out != null) {
                            out.close();
                        }
                    } catch (Exception var16) {
                        var16.printStackTrace();
                    }

                }

            }
        }

        public static String copyRawFile2FilesDir(Context context, int fileId, String saveName) {
            File filesDir = context.getFilesDir();
            File targetFile = new File(filesDir, saveName);
            LogUtils.e("filesDir =" + filesDir);
            if(targetFile.exists()) {
                LogUtils.e(targetFile.getAbsolutePath() + "文件已存在");
                return targetFile.getAbsolutePath();
            } else {
                InputStream in = null;
                FileOutputStream out = null;

                try {
                    in = context.getResources().openRawResource(fileId);
                    out = new FileOutputStream(targetFile);
                    boolean e = false;
                    byte[] buffer = new byte[1024];

                    int e1;
                    while((e1 = in.read(buffer)) != -1) {
                        out.write(buffer, 0, e1);
                    }

                    LogUtils.e(saveName + "拷贝完成");
                } catch (IOException var17) {
                    var17.printStackTrace();
                    LogUtils.e("没有文件：" + fileId + "   " + var17.toString());
                } finally {
                    try {
                        if(in != null) {
                            in.close();
                        }

                        if(out != null) {
                            out.close();
                        }
                    } catch (Exception var16) {
                        var16.printStackTrace();
                    }

                }

                LogUtils.e("targetFile.getAbsolutePath() = " + targetFile.getAbsolutePath());
                return targetFile.getAbsolutePath();
            }
        }

        public static boolean copyFile(String oldPath, String newPath, String newName) {
            return copyFile(oldPath, newPath, newName, (Handler)null, -1);
        }

        public static boolean copyFile(String oldPath, String newPath, String newName, Handler handler, int msgWht) {
            FileUtils.makeDirs(newPath);

            try {
                int e = 0;
                boolean byteread = false;
                File oldfile = new File(oldPath);
                if(oldfile.exists()) {
                    double maxSize = FileUtils.FileSizeUtils.getFileOrFilesSize(oldPath, 3);
                    FileInputStream inStream = new FileInputStream(oldPath);
                    FileOutputStream fs = new FileOutputStream(newPath + "/" + newName);
                    byte[] buffer = new byte[105472];
                    float length = 0.0F;

                    int byteread1;
                    while((byteread1 = inStream.read(buffer)) != -1) {
                        e += byteread1;
                        System.out.println(e);
                        fs.write(buffer, 0, byteread1);
                        length += 0.1F;
                        if(handler != null) {
                            Message message = Message.obtain();
                            Bundle bundle = new Bundle();
                            bundle.putFloat("finishSize", length);
                            bundle.putDouble("maxSize", maxSize);
                            message.what = msgWht;
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }
                    }

                    inStream.close();
                    return true;
                } else {
                    return false;
                }
            } catch (Exception var16) {
                System.out.println("复制单个文件操作出错");
                var16.printStackTrace();
                return false;
            }
        }
    }

    public static class OpenFileUtil {
        private static final String[][] MIME_MapTable = new String[][]{{".3gp", "video/3gpp"}, {".apk", "application/vnd.android.package-archive"}, {".asf", "video/x-ms-asf"}, {".avi", "video/x-msvideo"}, {".bin", "application/octet-stream"}, {".bmp", "image/bmp"}, {".c", "text/plain"}, {".class", "application/octet-stream"}, {".conf", "text/plain"}, {".cpp", "text/plain"}, {".doc", "application/msword"}, {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"}, {".xls", "application/vnd.ms-excel"}, {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}, {".exe", "application/octet-stream"}, {".gif", "image/gif"}, {".gtar", "application/x-gtar"}, {".gz", "application/x-gzip"}, {".h", "text/plain"}, {".htm", "text/html"}, {".html", "text/html"}, {".jar", "application/java-archive"}, {".java", "text/plain"}, {".jpeg", "image/jpeg"}, {".jpg", "image/jpeg"}, {".js", "application/x-javascript"}, {".log", "text/plain"}, {".m3u", "audio/x-mpegurl"}, {".m4a", "audio/mp4a-latm"}, {".m4b", "audio/mp4a-latm"}, {".m4p", "audio/mp4a-latm"}, {".m4u", "video/vnd.mpegurl"}, {".m4v", "video/x-m4v"}, {".mov", "video/quicktime"}, {".mp2", "audio/x-mpeg"}, {".mp3", "audio/x-mpeg"}, {".mp4", "video/mp4"}, {".mpc", "application/vnd.mpohun.certificate"}, {".mpe", "video/mpeg"}, {".mpeg", "video/mpeg"}, {".mpg", "video/mpeg"}, {".mpg4", "video/mp4"}, {".mpga", "audio/mpeg"}, {".msg", "application/vnd.ms-outlook"}, {".ogg", "audio/ogg"}, {".pdf", "application/pdf"}, {".png", "image/png"}, {".pps", "application/vnd.ms-powerpoint"}, {".ppt", "application/vnd.ms-powerpoint"}, {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"}, {".prop", "text/plain"}, {".rc", "text/plain"}, {".rmvb", "audio/x-pn-realaudio"}, {".rtf", "application/rtf"}, {".sh", "text/plain"}, {".tar", "application/x-tar"}, {".tgz", "application/x-compressed"}, {".txt", "text/plain"}, {".wav", "audio/x-wav"}, {".wma", "audio/x-ms-wma"}, {".wmv", "audio/x-ms-wmv"}, {".wps", "application/vnd.ms-works"}, {".xml", "text/plain"}, {".z", "application/x-compress"}, {".zip", "application/x-zip-compressed"}, {"", "*/*"}};

        public OpenFileUtil() {
        }

        public static void openFile(String path, Context c) {
            File file = new File(path);
            Intent intent = new Intent();
            intent.addFlags(268435456);
            intent.setAction("android.intent.action.VIEW");
            String type = getMIMEType(file);
            intent.setDataAndType(Uri.fromFile(file), type);
            c.startActivity(intent);
        }

        private static String getMIMEType(File file) {
            String type = "*/*";
            String fName = file.getName();
            int dotIndex = fName.lastIndexOf(".");
            if(dotIndex < 0) {
                return type;
            } else {
                String end = fName.substring(dotIndex, fName.length()).toLowerCase();
                if(TextUtils.isEmpty(end)) {
                    return type;
                } else {
                    for(int i = 0; i < MIME_MapTable.length; ++i) {
                        if(end.equals(MIME_MapTable[i][0])) {
                            type = MIME_MapTable[i][1];
                        }
                    }

                    return type;
                }
            }
        }
    }

    public static class FileSizeUtils {
        public static final int SIZETYPE_B = 1;
        public static final int SIZETYPE_KB = 2;
        public static final int SIZETYPE_MB = 3;
        public static final int SIZETYPE_GB = 4;

        public FileSizeUtils() {
        }

        public static double getFileOrFilesSize(String filePath, int sizeType) {
            File file = new File(filePath);
            long blockSize = 0L;

            try {
                blockSize = file.isDirectory()?getFileSizes(file):getFileSize(file);
            } catch (Exception var6) {
                var6.printStackTrace();
                Log.e("获取文件大小", "获取失败!");
            }

            return FormatFileSize(blockSize, sizeType);
        }

        private static double FormatFileSize(long fileS, int sizeType) {
            DecimalFormat df = new DecimalFormat("#.00");
            double fileSizeLong = 0.0D;
            switch(sizeType) {
            case 1:
                fileSizeLong = Double.valueOf(df.format((double)fileS)).doubleValue();
                break;
            case 2:
                fileSizeLong = Double.valueOf(df.format((double)fileS / 1024.0D)).doubleValue();
                break;
            case 3:
                fileSizeLong = Double.valueOf(df.format((double)fileS / 1048576.0D)).doubleValue();
                break;
            case 4:
                fileSizeLong = Double.valueOf(df.format((double)fileS / 1.073741824E9D)).doubleValue();
            }

            return fileSizeLong;
        }

        public static String getAutoFileOrFilesSize(String filePath) {
            File file = new File(filePath);
            long blockSize = 0L;

            try {
                blockSize = file.isDirectory()?getFileSizes(file):getFileSize(file);
            } catch (Exception var5) {
                var5.printStackTrace();
                Log.e("获取文件大小", "获取失败!");
            }

            return FormatFileSize(blockSize);
        }

        private static String FormatFileSize(long fileS) {
            if(fileS == 0L) {
                return "0 KB";
            } else {
                String end;
                Double fileSizeString;
                if(fileS < 1024L) {
                    fileSizeString = Double.valueOf((double)fileS);
                    end = " B";
                } else if(fileS < 1048576L) {
                    fileSizeString = Double.valueOf((double)fileS / 1024.0D);
                    end = " KB";
                } else if(fileS < 1073741824L) {
                    fileSizeString = Double.valueOf((double)fileS / 1048576.0D);
                    end = " MB";
                } else {
                    fileSizeString = Double.valueOf((double)fileS / 1.073741824E9D);
                    end = " GB";
                }

                fileSizeString = Double.valueOf((double)Math.round(fileSizeString.doubleValue() * 100.0D) / 100.0D);
                String size = fileSizeString + end;
                return size;
            }
        }

        public static String FormatFileSize2String(long fileS) {
            DecimalFormat df = new DecimalFormat("######0.00");
            String fileSizeString = "";
            String wrongSize = "0";
            if(fileS == 0L) {
                return wrongSize;
            } else {
                if(fileS < 1024L) {
                    fileSizeString = df.format((double)fileS) + "B";
                } else if(fileS < 1048576L) {
                    fileSizeString = df.format((double)fileS / 1024.0D) + "KB";
                } else if(fileS < 1073741824L) {
                    fileSizeString = df.format((double)fileS / 1048576.0D) + "MB";
                } else {
                    fileSizeString = df.format((double)fileS / 1.073741824E9D) + "G";
                }

                return fileSizeString;
            }
        }

        public static long getFileSize(File file) throws Exception {
            long size = 0L;
            if(file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                size = (long)fis.available();
            } else {
                file.createNewFile();
                Log.e("获取文件大小", "文件不存在!");
            }

            return size;
        }

        private static long getFileSizes(File f) throws Exception {
            long size = 0L;
            File[] flist = f.listFiles();

            for(int i = 0; i < flist.length; ++i) {
                if(flist[i].isDirectory()) {
                    size += getFileSizes(flist[i]);
                } else {
                    size += getFileSize(flist[i]);
                }
            }

            return size;
        }
    }
}
