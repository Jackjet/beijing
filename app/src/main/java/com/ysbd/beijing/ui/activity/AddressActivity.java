package com.ysbd.beijing.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ysbd.beijing.BaseActivity;
import com.ysbd.beijing.R;
import com.ysbd.beijing.bean.AddressBean;
import com.ysbd.beijing.ui.adapter.TreeAdapter;
import com.ysbd.beijing.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressActivity extends BaseActivity {

    @BindView(R.id.lv_contract)
    ListView lvContract;
    TreeAdapter adapter;
    List<AddressBean> roots = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address2);
        ButterKnife.bind(this);

        //无人员的部门不显示
        List<AddressBean> rootDepart = DBUtils.getRootDepart();
        for (AddressBean bean : rootDepart) {
            List<AddressBean> child = DBUtils.getChild(bean.getNodeGuid());
            if (child != null && child.size() != 0) {
                bean.setLevel(1);
                bean.setParent(true);
                bean.setOpen(false);
                roots.add(bean);
            }
        }

//          无人员的部门显示
//        roots.addAll(DBUtils.getRootDepart());
//        for (int i = 0; i < roots.size(); i++) {
//            roots.get(i).setLevel(1);
//            roots.get(i).setParent(true);
//            roots.get(i).setOpen(false);
//        }
        adapter = new TreeAdapter(this, roots, 1);
        lvContract.setOnItemClickListener(new MyOnItemClick());
        lvContract.setAdapter(adapter);
    }

    private int clickPosition;


    private class MyOnItemClick implements AdapterView.OnItemClickListener {
        List<AddressBean> mu = new ArrayList<>();

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            clickPosition = position;
            if (roots.get(position).isParent()) {//点击部门
                if (roots.get(position).isOpen()) {//如果是展开的 将其关闭
                    roots.get(position).setOpen(false);

                    for (int i = position + 1; i < roots.size(); i++) {//将列表项 后边属于他的子项清除
                        if (roots.get(i).getLevel() > roots.get(position).getLevel()) {
                            mu.add(roots.get(i));
                        } else if (roots.get(i).getLevel() <= roots.get(position).getLevel()) {
                            break;
                        }
                    }
                    roots.removeAll(mu);
                    mu.clear();
                    adapter.notifyDataSetChanged();
                } else {//如果是关闭的 将其展开
                    roots.get(position).setOpen(true);
                    mu.addAll(DBUtils.getChild(roots.get(position).getNodeGuid()));
                    for (int i = 0; i < mu.size(); i++) {
                        mu.get(i).setParent(mu.get(i).getImageUrl().contains("p"));
                        mu.get(i).setLevel(roots.get(position).getLevel() + 1);
                    }
                    roots.addAll(position + 1, mu);
                    mu.clear();
                    adapter.notifyDataSetChanged();
                }
            } else {//点击联系人
                Intent intent = new Intent(AddressActivity.this, PersonActivity.class);
                intent.putExtra("userId", roots.get(position).getNodeGuid());
                intent.putExtra("depart", DBUtils.getPersonNameById(roots.get(position).getParentNodeGuid()));
                intent.putExtra("name", roots.get(position).getNodeName());
                intent.putExtra("job", roots.get(position).getJobtitles());
                startActivity(intent);
            }
        }
    }


    @OnClick({R.id.fl_back, R.id.fl_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_back:
                finish();
                break;
            case R.id.fl_search:
                startActivity(new Intent(this, SearchAddressActivity.class));
                break;
        }
    }
}
