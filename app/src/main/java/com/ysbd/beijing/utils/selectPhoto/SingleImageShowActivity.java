package com.ysbd.beijing.utils.selectPhoto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ysbd.beijing.R;
import com.ysbd.beijing.utils.Glide.GlideUtils;


public class SingleImageShowActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout bottomLayout;
    private ImageView back;
    private LinearLayout selectViewer;
    private ImageView selectorViewer;
    private TextView okViewer;
    private ImageView showViewer;

    private boolean isSelect;
    private String url;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_image_show);
        bottomLayout = findViewById(R.id.singleBottomViewer);
        back = findViewById(R.id.singleBackViewer);
        selectViewer = findViewById(R.id.singleSelectViewer);
        selectorViewer = findViewById(R.id.singleSelectorViewer);
        okViewer = findViewById(R.id.singleOKViewer);
        showViewer = findViewById(R.id.singleShowViewer);
        back.setOnClickListener(this);
        selectViewer.setOnClickListener(this);
        okViewer.setOnClickListener(this);
        isSelect = getIntent().getBooleanExtra("isSelect", false);
        url = getIntent().getStringExtra("url");
        position = getIntent().getIntExtra("position", 0);
        if (isSelect){
            bottomLayout.setVisibility(View.VISIBLE);
            selectorViewer.setImageResource(R.drawable.imageviewer_select);
        }else {
            bottomLayout.setVisibility(View.GONE);
            selectorViewer.setImageResource(R.drawable.imageviewer_un_select);
        }
        GlideUtils.getInstence().load(this,url,showViewer);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.singleBackViewer:
                finish();
                break;
            case R.id.singleSelectViewer:
                if (isSelect){
                    isSelect = false;
                    bottomLayout.setVisibility(View.GONE);
                    selectorViewer.setImageResource(R.drawable.imageviewer_un_select);
                }else {
                    isSelect = true;
                    bottomLayout.setVisibility(View.VISIBLE);
                    selectorViewer.setImageResource(R.drawable.imageviewer_select);
                }
                break;
            case R.id.singleOKViewer:
                Intent intent = new Intent();
                intent.putExtra("position",position);
                intent.putExtra("isSelect",isSelect);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }
}
