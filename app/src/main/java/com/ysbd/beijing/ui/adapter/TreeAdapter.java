package com.ysbd.beijing.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysbd.beijing.R;
import com.ysbd.beijing.bean.AddressBean;
import com.ysbd.beijing.utils.DBUtils;

import java.util.List;

/**
 * Created by lcjing on 2018/5/21.
 */

public class TreeAdapter extends BaseAdapter {
    private Context mContext;
    private List<AddressBean> mList;
    private int type;//1.单 2.群(有复选框)


    public TreeAdapter(Context context, List<AddressBean> list, int type) {
        this.mContext = context;
        this.mList = list;
        this.type = type;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (mContext == null) {
            return null;
        }
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.org_item, parent, false);
            vh = new ViewHolder();
            vh.name = convertView.findViewById(R.id.tvName);
            vh.icon = convertView.findViewById(R.id.ivDir);
            vh.avatar = convertView.findViewById(R.id.ivAvatar);
            vh.tvPersonName = convertView.findViewById(R.id.tvPersonName);
            vh.parent = convertView.findViewById(R.id.llParent);
            vh.child = convertView.findViewById(R.id.llChild);
            vh.tvLeft1 = convertView.findViewById(R.id.tvLeft1);
            vh.tvLeft2 = convertView.findViewById(R.id.tvLeft2);
            vh.tvLeft3 = convertView.findViewById(R.id.tvLeft3);
            vh.cb1 = convertView.findViewById(R.id.cb1);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final AddressBean data = mList.get(position);
        convertView.setVisibility(View.VISIBLE);
        if (data.isParent()) {
            List<AddressBean> child = DBUtils.getChild(data.getNodeGuid());
            if (child!=null&& child.size()!=0) {
                vh.child.setVisibility(View.GONE);
                vh.parent.setVisibility(View.VISIBLE);
                // 判断是否有下级菜单
                if (data.isOpen()) {
                    // 有下级菜单，且为展开状态
                    vh.icon.setImageResource(R.mipmap.abc_ic_go1);
                } else {
                    // 有下级菜单，且为关闭状态
                    vh.icon.setImageResource(R.mipmap.abc_ic_go);
                }

                vh.name.setText(data.getNodeName());
            }else {
                convertView.setVisibility(View.GONE);
                vh.child.setVisibility(View.GONE);
                vh.parent.setVisibility(View.GONE);
            }
            if (data.getNodeName().equals("预算研究会")||data.getNodeName().equals("统评处")) {
                Log.e("====",""+data.toString());
            }
        } else {
            vh.child.setVisibility(View.VISIBLE);
            vh.parent.setVisibility(View.GONE);
            String name=data.getNodeName();
            if (data.getJobtitles()!=null&&data.getJobtitles().length()>0) {
                name=name+"("+data.getJobtitles()+")";
            }
            vh.tvPersonName.setText(name);
            if (type==1) {
                if(data.isSelect()){
                    vh.tvPersonName.setText(name+"(已选择)");
                }
            }
            if (type == 2) {
                vh.cb1.setVisibility(View.VISIBLE);
                vh.cb1.setChecked(data.isSelect());
            } else {
                vh.cb1.setVisibility(View.GONE);
            }
        }
        vh.name.setText(data.getNodeName());
        // 把图标进行缩进，
        switch (data.getLevel()) {
            case 1:
                vh.tvLeft1.setVisibility(View.GONE);
                vh.tvLeft2.setVisibility(View.GONE);
                vh.tvLeft3.setVisibility(View.GONE);
                break;
            case 2:
                vh.tvLeft1.setVisibility(View.VISIBLE);
                vh.tvLeft2.setVisibility(View.GONE);
                vh.tvLeft3.setVisibility(View.GONE);
                break;
            case 3:
                vh.tvLeft1.setVisibility(View.VISIBLE);
                vh.tvLeft2.setVisibility(View.VISIBLE);
                vh.tvLeft3.setVisibility(View.GONE);
                break;
            case 4:

                vh.tvLeft1.setVisibility(View.VISIBLE);
                vh.tvLeft2.setVisibility(View.VISIBLE);
                vh.tvLeft3.setVisibility(View.VISIBLE);
                break;

        }
        return convertView;
    }

    class ViewHolder {
        TextView name, tvPersonName,tvLeft1, tvLeft2, tvLeft3;
        ImageView avatar;
        ImageView icon;
        LinearLayout parent, child;
        CheckBox cb1;
    }
}
