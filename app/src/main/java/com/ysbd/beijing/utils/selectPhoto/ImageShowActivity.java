package com.ysbd.beijing.utils.selectPhoto;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.ysbd.beijing.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 建议在AndroidManifest.xml清单文件中给该activity设置状态栏的颜色
 * 颜色值和该activity的标题栏颜色一直即可（即：titleBgColorId）
 * <p>
 * 可通过isSingle来控制是多选还是单选，默认多选
 * 在onActivityResult中根据resultCode来获取数据   ArrayList<String> list = data.getStringArrayListExtra("data")
 * resultCode == RESULT_OK，点击了“完成”按钮；resultCode == RESULT_CANCELED，点击了“返回”按钮
 */
public class ImageShowActivity extends AppCompatActivity implements ImageViewerAdapter.OnItemChildViewClickListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private ImageView back;
    private TextView finishViewer;
    private List<ImageViewerModel> list;
    private GridLayoutManager gridLayoutManager;
    private ImageViewerAdapter adapter;
    private boolean isSingle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);
        recyclerView = findViewById(R.id.recyclerView_Viewer);
        back = findViewById(R.id.backViewer);
        finishViewer = findViewById(R.id.finishViewer);
        back.setOnClickListener(this);
        finishViewer.setOnClickListener(this);
        isSingle = getIntent().getBooleanExtra("isSingle", false);
        list = new ArrayList();
        gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new ImageViewerAdapter(this, list, this);
        recyclerView.setAdapter(adapter);
        getImage();
    }

    public void getImage() {
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            //获取图片的路径
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            if ((new File(path)).exists()) {
                //根据路径得到File，然后判断该文件是否存在，避免检索图片时得到错误的路径（有时会得到裁剪图片后的文件，猜测是内存缓存中的图片）
                list.add(new ImageViewerModel(path, false));
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 101:
                if (resultCode == RESULT_OK) {
                    int position = data.getIntExtra("position", 0);
                    boolean isSelect = data.getBooleanExtra("isSelect", false);
                    if (list.get(position).isSelect() && !isSelect) {
                        list.get(position).setSelect(isSelect);
                        adapter.notifyItemChanged(position);
                    } else if (!list.get(position).isSelect() && isSelect) {
                        list.get(position).setSelect(isSelect);
                        adapter.notifyItemChanged(position);
                    }
                }
                break;
        }
    }


    @Override
    public void onViewClick(View itemView, int position, int id) {
        switch (id) {
            case 0:
                if (isSingle) {
                    for (int i = 0; i < list.size(); i++) {
                        if (i != position && list.get(i).isSelect()) {
                            list.get(i).setSelect(false);
                            adapter.notifyItemChanged(i);
                        }
                    }
                    list.get(position).setSelect(!list.get(position).isSelect());
                    adapter.notifyItemChanged(position);
                } else {
                    boolean select = list.get(position).isSelect();
                    if (select) {
                        list.get(position).setSelect(false);
                    } else {
                        list.get(position).setSelect(true);
                    }
                    adapter.notifyItemChanged(position);
                }
                break;
            case 1:
                Intent intent = new Intent(ImageShowActivity.this, SingleImageShowActivity.class);
                intent.putExtra("url", list.get(position).getPath());
                intent.putExtra("position", position);
                intent.putExtra("isSelect", list.get(position).isSelect());
                startActivityForResult(intent, 101);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backViewer:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.finishViewer:
                ArrayList<String> selectList = new ArrayList<>();
                for (ImageViewerModel model : list) {
                    if (model.isSelect()) {
                        selectList.add(model.getPath());
                    }
                }
                Intent intent = new Intent();
                intent.putStringArrayListExtra("data", selectList);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
