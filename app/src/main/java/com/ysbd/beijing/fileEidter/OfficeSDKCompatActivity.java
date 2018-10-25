package com.ysbd.beijing.fileEidter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.ntko.app.pdf.PDFStampItem;
import com.ntko.app.pdf.PDFViewContext;
import com.ntko.app.support.CustomFieldKeyPair;
import com.ntko.app.support.CustomFields;
import com.ntko.app.support.DocumentsCompatAgent;
import com.ntko.app.support.DocumentsLibrary;
import com.ntko.app.support.Params;
import com.ntko.app.support.appcompat.Product;
import com.ntko.app.support.callback.LifecycleListener;
import com.ntko.app.view.BasicActivity;

import java.util.Map;

/**
 * Created by senhai
 * on 2017/10/31
 */

public abstract class
 OfficeSDKCompatActivity extends BasicActivity implements DocumentsLibrary.DestroyCallback, PDFViewContext.StampsInfoRetrieveCallback {

    protected DocumentsCompatAgent officeSDK;

    protected void initOfficeSDK() {

        DocumentsLibrary library = DocumentsLibrary.getInstance();
        library.addSDKDestroyCallback(this);
        // 738017454533596  演示版本
        // 179360309699478  过期测试
        // 4724761286833  过期测试
        officeSDK = library.newSDKInstance(this)
                .withHttpHeaders(headers)
                .init(Product.OFFICE_ENT, "190650817316934", "502192907720")
                .setLifecycleListener(lifecycleListener);
    }

    private void destroySDK() {
        if (officeSDK != null) {
            DocumentsLibrary.getInstance().destroy(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initOfficeSDK();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroySDK();
    }

    protected CustomFields fields = createCustomFields();
    protected CustomFields headers = createCustomHeaders();

    public CustomFields createCustomFields() {
        CustomFields fields = new CustomFields();
        fields.add(new CustomFieldKeyPair("name", "ntkowhy"));
        fields.add(new CustomFieldKeyPair("age", "28"));
        return fields;
    }

    public CustomFields createCustomHeaders() {
        CustomFields fields = new CustomFields();
        fields.add(new CustomFieldKeyPair("customHeader1", "header1"));
        fields.add(new CustomFieldKeyPair("customHeader2", "header2"));
        return fields;
    }


    /**
     * 文档生命周期中的事件回调
     */
    protected final LifecycleListener lifecycleListener = new OfficeSDKLifecycleListener() {
        @Override
        public void onWPSClientServiceStopped() {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(OfficeSDKCompatActivity.this, "WPS 客户端服务已被操作系统终结，查阅文档功能将不可用。", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onDocumentOpened(int docType, Params.RawFileType fileType) {
            super.onDocumentOpened(docType, fileType);
            if (Params.RawFileType.PDF == fileType) {
                PDFViewContext pdfViewContext = officeSDK.getPDFViewContext();
                if (pdfViewContext != null) {
                    pdfViewContext.setStampsInfoRetrieveCallback(OfficeSDKCompatActivity.this);
                    pdfViewContext.retrieveStamps();
                }
            }
        }
    };

    @Override
    public void onStampsRetrieved(Map<Integer, SparseArray<PDFStampItem>> data) {
        for (int page : data.keySet()) {
            SparseArray<PDFStampItem> stampsArray = data.get(page);

            StringBuilder builder =
                    new StringBuilder("文档第" + (page + 1) + "页，" + stampsArray.size() + "个用户批注。")
                            .append("\n");

            for (int index = 0; index < stampsArray.size(); index++) {
                PDFStampItem stamp = stampsArray.valueAt(index);
                builder.append("\n").append(stamp.toString());
            }

            Log.d("软航移动", builder.toString());
        }
    }

    @Override
    public void onSDKDestroy(DocumentsLibrary.ContextReference reference) {
        if (reference.getTop().equals(this)) {
            Log.d("软航移动", "监测到销毁，初始化视图 ===> " + this.getClass().getSimpleName());
            initOfficeSDK();
        }
    }

    @Override
    public Context getSDKContext() {
        return this;
    }

}
