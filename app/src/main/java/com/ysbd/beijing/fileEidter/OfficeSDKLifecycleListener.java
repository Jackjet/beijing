package com.ysbd.beijing.fileEidter;

import android.text.TextUtils;
import android.util.Log;

import com.ntko.app.pdf.PDFStampData;
import com.ntko.app.support.LifecycleListenerImpl;
import com.ntko.app.support.Params;
import com.ntko.app.vm.BatchStampAddResult;
import com.ntko.app.vm.DefaultStampAddResult;
import com.ntko.app.vm.PDFStampAddResult;

import java.util.HashMap;
import java.util.Map;

public class OfficeSDKLifecycleListener extends LifecycleListenerImpl {

    @Override
    public void onStartOpenDocument(String filePath, boolean isEncrypted) {
        Log.d("软航移动", "打开文件 ===> " + filePath);
    }

    @Override
    public void onDocumentOpened(int docType, Params.RawFileType fileType) {
        Log.d("软航移动", "文件打开成功");
    }

    @Override
    public void onDocumentOpenFailed(Throwable ex) {
        Log.d("软航移动", "文件打开失败: " + ex.getMessage());
    }

    @Override
    public void onDocumentClosed(String localPath, boolean isModified) {
        Log.d("软航移动", "文件已经关闭：" + localPath + "，变动: " + isModified);
    }

    @Override
    public void onSaveDocument(String localPath) {
        Log.d("软航移动", "文件保存成功 ===> " + localPath);
    }

    @Override
    public void onStartUpload(String any, String path, String title, String uploadUrl) {
        Log.d("软航移动", "文件开始上传 ===> " + uploadUrl);
    }

    @Override
    public void onUploadCanceled(String filePath, String uploadURL, String reason) {
        Log.d("软航移动", "文件取消 ===> filePath: " + filePath);
        Log.d("软航移动", "文件取消 ===> uploadURL: " + uploadURL);
        Log.d("软航移动", "文件取消 ===> reason: " + reason);
    }

    @Override
    public void onUploadFailed(String filePath, String failure, int code, Throwable cause) {
        Log.d("软航移动", "文件上传失败 ===> filePath: " + filePath);
        Log.d("软航移动", "文件上传失败 ===> failure: " + failure);
        Log.d("软航移动", "文件上传失败 ===> code: " + code);
        if (cause != null) {
            cause.printStackTrace();
        }
    }

    @Override
    public void onUploadSucceed(String filePath, String responseString) {
        Log.d("软航移动", "文件上传成功");
    }

    @Override
    public void onAddStampToDocument(PDFStampAddResult result) {
        switch (result.getMod()) {
            case PDFStampAddResult.BATCH_MOD:
                if (result instanceof BatchStampAddResult) {
                    BatchStampAddResult batch = (BatchStampAddResult) result;
                    Log.d("软航移动", "添加批注到PDF文档，用户：" + batch.getUser() +
                            "，批注编号列表：" + TextUtils.join(", ", batch.getStampIdsBatch()) +
                            "，批注页面列表：" + TextUtils.join(", ", batch.getPagesBatch()) +
                            "，批注图像文件：" + batch.getStampFile() +
                            "，自定义数据: " + batch.getData());
                }
                break;
            case PDFStampAddResult.STAMP_MOD:
                if (result instanceof DefaultStampAddResult) {
                    DefaultStampAddResult data = (DefaultStampAddResult) result;
                    Log.d("软航移动", "添加批注到PDF文档，用户：" + data.getUser() +
                            "，批注编号：" + data.getStampId() +
                            "，批注页面：" + data.getPage() +
                            "，批注图像文件：" + data.getStampFile() +
                            "，页面图像文件：" + data.getPageSnapshot() +
                            "，自定义数据: " + data.getData());
                }
                break;
        }
    }


    @Override
    public void onDeleteStamp(String stampId, PDFStampData customData, int page) {
        super.onDeleteStamp(stampId, customData, page);
        Log.d("软航移动", "删除批注" + "，批注编号：" + stampId + "，自定义数据：" + customData);

    }

    @Override
    public void onDownloadStart(String address) {
        Log.d("软航移动", "文件下载开始 ===> " + address);
    }

    @Override
    public void onDownloadFailed(int errorCode, String errorMessage, Throwable cause) {
        Log.d("软航移动", "文件下载失败 ===> " + errorCode + ":" + errorMessage);
        if (cause != null) {
            cause.printStackTrace();
        }
    }

    @Override
    public void onDownloadCanceled() {
        Log.d("软航移动", "已经取消文件下载");
    }

    @Override
    public void onDownloadComplete(String path) {
        Log.d("软航移动", "文件下载成功 ===> " + path);
    }

    @Override
    public Map<String, String> getAnnotationProperties() {
        return mAnnotationProperties;
    }

    @Override
    public Map<String, String> getAnnotationPropertyNames() {
        return mAnnotationPropertyNames;
    }

    private final Map<String, String> mAnnotationProperties = new HashMap<>();
    private final Map<String, String> mAnnotationPropertyNames = new HashMap<>();
}