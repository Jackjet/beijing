package com.ysbd.beijing.fileEidter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.view.View;

import com.ntko.app.office.wps.params.WPSDisallowedActionList;
import com.ntko.app.office.wps.params.WPSMenuBarViewDisabledList;
import com.ntko.app.office.wps.params.WPSMenuBarViewHiddenList;
import com.ntko.app.office.wps.params.WPSWordParameters;
import com.ntko.app.pdf.PDFDrawType;
import com.ntko.app.pdf.PDFSettings;
import com.ntko.app.support.Params;
import com.ntko.app.support.WPSOfficeAgent;
import com.ntko.app.support.callback.WPSWordParametersCallback;
import com.ysbd.beijing.R;
import com.ysbd.beijing.utils.SpUtils;

import java.io.File;

/**
 * Created by lcjing on 2018/10/23.
 */

public class EditActivity2 extends OfficeSDKCompatActivity{

    private boolean firstStart=true;
    private String wpsAppURLPro = "http://mo.wps.cn/pc-app/Android/moffice_9.2.11_1033_ProCn00110_multidex_245481.apk";
    private String wpsAppURLCn = "http://dl.op.wpscdn.cn/dl/wps/mobile/apk/moffice_10.9.1_2052_cn00563_multidex_ff55315e45.apk";

    @Override
    protected void postOnPermissionsGranted() {
        setContentView(R.layout.activity_edit_file);

    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean isDocument=getIntent().getBooleanExtra("isDocument",false);
        String path=getIntent().getStringExtra("path");
        String filename=getIntent().getStringExtra("filename");
        String uploadurl =getIntent().getStringExtra("uploadurl");
             if (firstStart) {
                openLocalWord(path,filename);
            }else {
                setResult(101);
                finish();
            }

        firstStart=false;
    }

    public void openLocalWord(String path, String filename){
        WPSWordParameters parameters = new WPSWordParameters();
        parameters.setOpenInView(WPSWordParameters.OPEN_IN_NORMAL);
        parameters.setRevisionMode(true);
        parameters.setShowReviewPanel(true);
        parameters.setUsername(SpUtils.getInstance().getUserName());
//        parameters.setWpsAppDownloadURL(wpsAppURL);
//        initOfficeSDK();
        officeSDK.withParameters(new WPSWordParametersCallback() {
            @Override
            public WPSWordParameters prepare() {
                WPSWordParameters parameters = new WPSWordParameters();
                parameters.setOpenInView(WPSWordParameters.OPEN_IN_NORMAL);
                parameters.setUsePenMode(true);
                parameters.setInkColor(Color.DKGRAY);
                parameters.setRevisionMode(true);
                parameters.setUsername(SpUtils.getInstance().getUserName());
                // 如果WPS没有安装，下载并安装应用
//                parameters.setWpsAppDownloadURL(getWPSDownloadAddress());
                return parameters;
            }
        }).openLocalWordDocument(filename, path, Params.OfficeVersion.LATEST);
//        officeSDK.openLocalWordDocument(filename, path, uploadurl, createCustomFields(), Params.OfficeVersion.LATEST);

    }

    public void onOpenServerWord(String fileUrl,String uploadUrl) {

        // 文档下载地址

        officeSDK.withParameters(new WPSWordParametersCallback() {
            @Override
            public WPSWordParameters prepare() {
                WPSWordParameters parameters = new WPSWordParameters();
                // 批注模式
                //parameters.setOpenInView(WPSWordParameters.OPEN_IN_HAND_SIGNATURE);
                // WPS自动判断进入何种模式，通常编辑模式
//                parameters.setOpenInView(WPSWordParameters.OPEN_IN_NORMAL);
                // 只读模式
                //parameters.setOpenInView(WPSWordParameters.OPEN_IN_READONLY);
                // 阅读模式
                parameters.setOpenInView(WPSWordParameters.OPEN_IN_READ);
                // 编辑模式
                //parameters.setOpenInView(WPSWordParameters.EDIT_MODE);

                parameters.setRevisionMode(true);
                parameters.setShowReviewPanel(true);
                parameters.setUsername(SpUtils.getInstance().getUserName());
                // 如果WPS没有安装，下载并安装应用
                parameters.setWpsAppDownloadURL(getWPSDownloadAddress());

                WPSDisallowedActionList list = new WPSDisallowedActionList();
                list.AT_COPY = true;
                list.AT_CUT = true;
                list.AT_PASTE = true;
                list.AT_PRINT = true;
                list.AT_SHARE = true;// 不响应分享菜单的点击
                parameters.setDisallowedActionList(list);

                WPSMenuBarViewDisabledList disabledList = new WPSMenuBarViewDisabledList();
                disabledList.VT_FILE_SHARE = true;// 禁用文件分享菜单
                disabledList.VT_INSERT_PICTURE = true;
                disabledList.VT_INSERT_TABLE = true;
                disabledList.VT_INSERT_SHAPE = true;
                parameters.setMenuBarViewDisabledList(disabledList);

                WPSMenuBarViewHiddenList hiddenList = new WPSMenuBarViewHiddenList();
                hiddenList.VT_REVIEW_SPELLCHECK = true;
                hiddenList.VT_REVIEW_MODIFY_USERNAME = true;
                hiddenList.VT_FILE_SHARE = true;// 隐藏分享菜单
                parameters.setMenuBarViewHiddenList(hiddenList);

                return parameters;
            }
        }).openServerWordDocument("", fileUrl, uploadUrl, null, Params.OfficeVersion.LATEST);
    }

    private String getWPSDownloadAddress() {
        String url = null;
        if (officeSDK != null) {
            WPSOfficeAgent.WPSAppEdition edition = officeSDK.getWPSAppEdition();
            url = (edition == WPSOfficeAgent.WPSAppEdition.PROFESSIONAL) ? wpsAppURLPro : wpsAppURLCn;
        }
        return url;
    }

    public void openSeverDocumentExtended(String fileUrl,String uploadURL){
        officeSDK.withParameters(new WPSWordParametersCallback() {
            @Override
            public WPSWordParameters prepare() {
                WPSWordParameters params = new WPSWordParameters();
                params.setUsername(SpUtils.getInstance().getUserName());
                params.setRevisionMode(true);
                return params;
            }
        })
                .openServerWordDocument("Demo Word", fileUrl, uploadURL, createCustomFields(), Params.OfficeVersion.LATEST);

    }
    public void onOpenLocalPDF(View view) {

        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String filePath = new File(directory, "广西壮族自治区发展和改革委员会.pdf").getAbsolutePath();

        Params params = new Params(Params.SourceType.LOCAL);
        params.setRawFileType(Params.RawFileType.PDF);
        params.setDocumentLocalAddress(filePath);
        params.setDocumentTitle("广西壮族自治区发展和改革委员会");
        params.setSessionUser("韩永刚");

        //params.setViewMode(Params.OPEN_IN_READONLY);
//
//        PDFSettings settings = new PDFSettings();
//        settings.setAnnotationColor(Color.BLACK);
//        settings.setAnnotationThickness(12.0f);
//        settings.setSaveAnnotationOnViewTouch(true);// 在电容笔模式下，当手指滑动时保存批注
//        settings.setDrawType(PDFDrawType.SIM);// 不支持手写笔的设备下设定绘制模式为 PDFDrawType.DEFAULT
//        settings.setShowProgressDialogOnUploading(false);// 是否显示上传进度条
//        // 当显示上传进度条时，取消上传的警告信息
//        settings.setWarningOnCancelUploading("文档内容必须和OA中保存的批注一致，请不要取消上传，否则会出现不可预知的问题！");

        // 自定义批注数据
//        CustomFields data = new CustomFields();
//        data.add(new CustomFieldKeyPair("testKey", "the value of testKey", "测试数据1"));
//        data.add(new CustomFieldKeyPair("name1", "the value of name1", "测试数据2"));
//        settings.setCustomAnnotationData(data);

//        // 一般情况下，在支持手写笔的设备上可以在手写笔和手指之间切换输入方式，但是也可以
//        // 强制使用手写笔，禁止在手写模式下使用手指书写
//        PDFUISpecification uiSpec = new PDFUISpecification();
//        uiSpec.setHideFingerSwitcher(true);
//        settings.setUiSpecification(uiSpec);

        // 处于第一页和最后一页时，滑动关闭文档
        // 检测到第一次越界滑动时，会提示用户 “再次（上|下|左|右）滑以关闭文档”
        // 如果用户再次上|下|左|右滑动便会关闭文档
        // 此功能同时适用于页面横屏和竖屏滑动模式
        // 同时滑动间隔超过5秒，重置滑动退出标记
//        settings.setEnableSlideOut(true);
//
//        officeSDK.openLocalPDFDocument(params, settings, createCustomFields());


        // 打开本地文档并设定PDF视图参数：
        PDFSettings settings = new PDFSettings();

        settings.setDrawType(PDFDrawType.DEFAULT);// 不支持手写笔的设备下设定绘制模式为 PDFDrawType.DEFAULT
        officeSDK.openLocalPDFDocument("spring-boot-reference", filePath, settings, createCustomFields(), "");

    }
}

