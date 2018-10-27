package com.ysbd.beijing.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.ysbd.beijing.App;
import com.ysbd.beijing.R;
import com.zhy.base.fileprovider.FileProvider7;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    private static FileUtils fileUtils;

    public static FileUtils getInstance() {
        if (fileUtils == null) {
            fileUtils = new FileUtils();
        }
        return fileUtils;
    }

    public static String getFileExtense(String fileName){
        int index=fileName.lastIndexOf(".");
        String end ="";
        if (index>0) {
            end = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        }
        return end;
    }

    public static String getFileType(File file) {
        if (!file.exists()) return null;
        /* 取得扩展名 */
        String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase();
        /* 依扩展名的类型决定MimeType */
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") ||
                end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            return "audio/*";
        } else if (end.equals("3gp") || end.equals("mp4")) {
            return "audio/*";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") ||
                end.equals("jpeg") || end.equals("bmp")) {
            return "image/*";
        } else if (end.equals("apk")) {
            return "application/vnd.android.package-archive";
        } else if (end.equals("ppt")) {
            return "application/vnd.ms-powerpoint";
        } else if (end.equals("xls")) {
            return "application/vnd.ms-excel";
        } else if (end.equals("doc") || end.equals("docx")) {
            return "application/msword";
        } else if (end.equals("pdf")) {
            return "application/pdf";
        } else if (end.equals("chm")) {
            return "application/x-chm";
        } else if (end.equals("txt")) {
            return "";
        } else {
            return "*/*";
        }
    }

    private String path = Environment.getExternalStorageDirectory().toString() + "/bjczj1";
    private String documentPath = Environment.getExternalStorageDirectory().toString() + "/document";

    public void saveToFile(String fileName, InputStream in) throws IOException {
        FileOutputStream fos;
        BufferedInputStream bis;
        byte[] buf = new byte[1024];//接受1024个字节
        int size;
        bis = new BufferedInputStream(in);
        fos = new FileOutputStream(fileName);
        while ((size = bis.read(buf)) != -1)
            fos.write(buf, 0, size);
        fos.close();
        bis.close();
    }

    public File makeDocumentDir() {
        File fileDir = new File(path+ "/document");
        if (fileDir.exists()) {
            return fileDir;
        } else {
            fileDir.mkdirs();
            return fileDir;
        }
    }
    public File makeDir() {
        File fileDir = new File(path);
        if (fileDir.exists()) {
            return fileDir;
        } else {
            fileDir.mkdirs();
            return fileDir;
        }
    }
    public File makeDir(String dir) {
        String dirPath=path+File.separator+dir;
        File fileDir = new File(dirPath);
        if (fileDir.exists()) {
            return fileDir;
        } else {
            fileDir.mkdirs();
            return fileDir;
        }
    }

    public String getExtension(String name){
        String end=name.substring(name.lastIndexOf(".") + 1,name.length()).toLowerCase();
        return end;
    }

    public boolean isFileExists(String fileName) {
        String path = getInstance().makeDir().getPath() + File.separator + fileName;
        File file = new File(path);
        boolean exists = file.exists();
        return exists;
    }

    public Intent openFile(String filePath, Context context) {

        File file = new File(filePath);
        if (!file.exists()) return null;
        /* 取得扩展名 */
        String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase();
        /* 依扩展名的类型决定MimeType */
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") ||
                end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            return getAudioFileIntent(filePath, context);
        } else if (end.equals("3gp") || end.equals("mp4")) {
            return getAudioFileIntent(filePath, context);
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") ||
                end.equals("jpeg") || end.equals("bmp")) {
            return getImageFileIntent(filePath, context);
        } else if (end.equals("apk")) {
            return getApkFileIntent(filePath, context);
        } else if (end.equals("ppt")) {
            return getPptFileIntent(filePath, context);
        } else if (end.equals("xls")) {
            return getExcelFileIntent(filePath, context);
        } else if (end.equals("doc") || end.equals("docx")) {
            return getWordFileIntent(filePath, context);
        } else if (end.equals("pdf")) {
            return getPdfFileIntent(filePath, context);
        } else if (end.equals("chm")) {
            return getChmFileIntent(filePath, context);
        } else if (end.equals("txt")) {
            return getTextFileIntent(filePath, context);
        } else {
            return getAllIntent(filePath, context);
        }
    }

    //Android获取一个用于打开APK文件的intent
    public Intent getAllIntent(String param, Context context) {

        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = FileProvider7.getUriForFile(context, new File(param));
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "*/*");
        return intent;
    }

    //Android获取一个用于打开APK文件的intent
    public Intent getApkFileIntent(String param, Context context) {

        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = FileProvider7.getUriForFile(context, new File(param));
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        return intent;
    }

    //Android获取一个用于打开VIDEO文件的intent
    public Intent getVideoFileIntent(String param, Context context) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = FileProvider7.getUriForFile(context, new File(param));
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    //Android获取一个用于打开AUDIO文件的intent
    public Intent getAudioFileIntent(String param, Context context) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = FileProvider7.getUriForFile(context, new File(param));
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    //Android获取一个用于打开Html文件的intent
    public Intent getHtmlFileIntent(String param, Context context) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = FileProvider7.getUriForFile(context, new File(param));
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    //Android获取一个用于打开图片文件的intent
    public Intent getImageFileIntent(String param, Context context) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = FileProvider7.getUriForFile(context, new File(param));
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    //Android获取一个用于打开PPT文件的intent
    public Intent getPptFileIntent(String param, Context context) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = FileProvider7.getUriForFile(context, new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    //Android获取一个用于打开Excel文件的intent
    public Intent getExcelFileIntent(String param, Context context) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = FileProvider7.getUriForFile(context, new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    //Android获取一个用于打开Word文件的intent
    public Intent getWordFileIntent(String param, Context context) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        Uri uri = FileProvider7.getUriForFile(context, new File(param));
        Uri uri = FileProvider.getUriForFile(context, App.getContext().getString(R.string.provider),new File(param));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    //Android获取一个用于打开CHM文件的intent
    public Intent getChmFileIntent(String param, Context context) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = FileProvider7.getUriForFile(context, new File(param));
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    //Android获取一个用于打开文本文件的intent
    public Intent getTextFileIntent(String param, boolean paramBoolean) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (paramBoolean) {
            Uri uri1 = Uri.parse(param);
            intent.setDataAndType(uri1, "text/plain");
        } else {
            Uri uri2 = Uri.fromFile(new File(param));
            intent.setDataAndType(uri2, "text/plain");
        }
        return intent;
    }


    //Android获取一个用于打开文本文件的intent
    public Intent getTextFileIntent(String param, Context context) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = FileProvider7.getUriForFile(context, new File(param));
        intent.setDataAndType(uri, "text/plain");
        return intent;
    }

    //Android获取一个用于打开PDF文件的intent
    public Intent getPdfFileIntent(String param, Context context) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = FileProvider7.getUriForFile(context, new File(param));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }


}
