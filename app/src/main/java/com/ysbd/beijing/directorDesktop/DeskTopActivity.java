package com.ysbd.beijing.directorDesktop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ysbd.beijing.R;
import com.ysbd.beijing.adapter.AppListsAdapter;
import com.ysbd.beijing.adapter.DividerGridItemDecoration;
import com.ysbd.beijing.base.BaseRecyclerViewAdapter;
import com.ysbd.beijing.bean.AppListsModel;
import com.ysbd.beijing.directorDesktop.bean.AttachmentBean;
import com.ysbd.beijing.directorDesktop.bean.CatalogBean;
import com.ysbd.beijing.directorDesktop.utils.CatalogUtils;
import com.ysbd.beijing.directorDesktop.utils.FileQueryUtil;
import com.ysbd.beijing.utils.FileUtils;
import com.ysbd.beijing.utils.SpUtils;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//局长桌面 （文件分类列表）
public class DeskTopActivity extends AppCompatActivity implements BaseRecyclerViewAdapter.OnChildViewClickListener {
    @BindView(R.id.app_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    private AppListsAdapter adapter;
    private ArrayList<AppListsModel.AppModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desk_top);
        ButterKnife.bind(this);
        list = new ArrayList<>();
        adapter = new AppListsAdapter(this, list, this);
        //页面要区分pad的手机
        int spanCount = 3;
        if (SpUtils.getInstance().getScreenWidth() > 400) {
            spanCount = 4;
        }
        FileUtils.getInstance().makeDir();
        String path = Environment.getExternalStorageDirectory().toString()+File.separator + "bjczjdesk";
        DataSupport.deleteAll(AttachmentBean.class);
        List< CatalogBean > catalogBeans= CatalogUtils.getCatalogs2();
        for (int i = 0; i < catalogBeans.size(); i++) {
            String cPath=path+File.separator+catalogBeans.get(i).getCatalog();
            FileQueryUtil.getAllFile(new File(cPath),catalogBeans.get(i).getName());
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(this, 4, R.color.holo_gray_bright));
        recyclerView.setAdapter(adapter);
//        AppListsModel.AppModel shouru = new AppListsModel.AppModel(null, "收入", null, null, true, "16");
//        shouru.setAppLogo(R.mipmap.shouru);
//        list.add(shouru);
//        AppListsModel.AppModel zhichu = new AppListsModel.AppModel(null, "支出", null, null, true, "15");
//        zhichu.setAppLogo(R.mipmap.zhichu);
//        list.add(zhichu);
//        AppListsModel.AppModel bumenhao = new AppListsModel.AppModel(null, "部门号", null, null, true, "");
//        bumenhao.setAppLogo(R.mipmap.sub);
//        list.add(bumenhao);
//        AppListsModel.AppModel tongzhi = new AppListsModel.AppModel(null, "通知", null, null, true, "1");
//        tongzhi.setAppLogo(R.mipmap.c_tz);
//        list.add(tongzhi);
//        AppListsModel.AppModel gonggao = new AppListsModel.AppModel(null, "公告", null, null, true, "1");
//        gonggao.setAppLogo(R.mipmap.c_gg);
//        list.add(gonggao);
        AppListsModel.AppModel shouru = new AppListsModel.AppModel(null, "收入", null, null, true, "收入");
        shouru.setAppLogo(R.mipmap.shouru);
        list.add(shouru);
        AppListsModel.AppModel zhichu = new AppListsModel.AppModel(null, "支出", null, null, true, "支出");
        zhichu.setAppLogo(R.mipmap.zhichu);
        list.add(zhichu);
        AppListsModel.AppModel bumenhao = new AppListsModel.AppModel(null, "部门号", null, null, true, "");
        bumenhao.setAppLogo(R.mipmap.sub);
        list.add(bumenhao);
        AppListsModel.AppModel tongzhi = new AppListsModel.AppModel(null, "通知", null, null, true, "");
        tongzhi.setAppLogo(R.mipmap.c_tz);
        list.add(tongzhi);
        AppListsModel.AppModel gonggao = new AppListsModel.AppModel(null, "公告", null, null, true, "");
        gonggao.setAppLogo(R.mipmap.c_gg);
        list.add(gonggao);
    }

    @Override
    public void onViewClick(int position, int id) {
//        Intent intent = new Intent(this, SubMainActivity.class);
//        intent.putExtra("id", list.get(position).getProcessDefinitionKey());
//        intent.putExtra("name", list.get(position).getItemName());
//        startActivity(intent);
        FileUtils.getInstance().makeDir(list.get(position).getProcessDefinitionKey());
        Intent intent = new Intent(this, FileActivity.class);
        intent.putExtra("id", list.get(position).getProcessDefinitionKey());
        intent.putExtra("name", list.get(position).getItemName());
        String path = Environment.getExternalStorageDirectory().toString() +File.separator+ "bjczjdesk"+File.separator+list.get(position).getProcessDefinitionKey();
        intent.putExtra("rout",path);
        startActivity(intent);
    }

    @OnClick(R.id.rlBack)
    public void onViewClicked() {
        finish();
    }
}
