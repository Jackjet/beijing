package com.ysbd.beijing.directorDesktop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysbd.beijing.R;
import com.ysbd.beijing.directorDesktop.Desk2Activity;
import com.ysbd.beijing.directorDesktop.FileActivity;
import com.ysbd.beijing.directorDesktop.bean.Desk2Bean;
import com.ysbd.beijing.fileEidter.FileReaderActivity;
import com.ysbd.beijing.view.SwipeMenuLayout;

import java.util.List;
import java.util.Map;

/**
 * Created by lcjing on 2018/7/24.
 */

public class Desk2Adapter extends BaseAdapter {
    private List<Map<String, Desk2Bean>> list;
    private Context context;
    private boolean showMore;


    public Desk2Adapter(List<Map<String, Desk2Bean>> be, Context context) {
        this.list = be;
        this.context = context;
    }


    public boolean isShowMore() {
        return showMore;
    }

    public void setShowMore(boolean showMore) {
        this.showMore = showMore;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_desk2, null);
            viewHolder = new ViewHolder();
            viewHolder.p = convertView.findViewById(R.id.p);
            viewHolder.c = convertView.findViewById(R.id.c);
            viewHolder.llC2 = convertView.findViewById(R.id.llC2);
            viewHolder.llC1 = convertView.findViewById(R.id.llC1);
            viewHolder.tvP = convertView.findViewById(R.id.tvP);
            viewHolder.tvC1 = convertView.findViewById(R.id.tvC1);
            viewHolder.tvC2 = convertView.findViewById(R.id.tvC2);
            viewHolder.ivC1 = convertView.findViewById(R.id.ivC1);
            viewHolder.ivC2 = convertView.findViewById(R.id.ivC2);
            viewHolder.tvMore = convertView.findViewById(R.id.tvMore);
            viewHolder.smlContent = convertView.findViewById(R.id.smlContent);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (list.get(position).get("1").isParent()) {
            viewHolder.c.setVisibility(View.GONE);
            viewHolder.smlContent.setVisibility(View.VISIBLE);
            String index=list.get(position).get("1").getName().substring(0,2);
            String name=list.get(position).get("1").getName();
            try {
                Integer integer=Integer.parseInt(index);
                if (integer>0) {
                    name=name.substring(2);
                }
            }catch (Exception ignore){

            }
            viewHolder.tvP.setText(name);
            if (showMore) {
                if (list.get(position).get("1").isHasMore()) {
                    viewHolder.tvMore.setVisibility(View.VISIBLE);
                    viewHolder.tvMore.setOnClickListener(new click(list.get(position).get("1")));
                } else {
                    viewHolder.tvMore.setVisibility(View.GONE);
                }
            } else {
                viewHolder.tvMore.setVisibility(View.GONE);
            }
        } else {
            viewHolder.c.setVisibility(View.VISIBLE);
            viewHolder.smlContent.setVisibility(View.GONE);
            String name = list.get(position).get("1").getName().replace("." + list.get(position).get("1").getEx(), "");
            if (list.get(position).get("1").isOrder()) {
                name = name.substring(2);
            }
            viewHolder.tvC1.setText(name);
            viewHolder.llC1.setOnClickListener(new click(list.get(position).get("1")));
            ViewGroup.LayoutParams p = viewHolder.tvC1.getLayoutParams();
            p.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            viewHolder.tvC1.setLayoutParams(p);
            ViewGroup.LayoutParams lp = viewHolder.llC1.getLayoutParams();
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            viewHolder.llC1.setLayoutParams(lp);
            if (list.get(position).get("1").isDir()) {
                viewHolder.ivC1.setImageResource(R.mipmap.triangle_blue);
            } else {
                viewHolder.ivC1.setImageResource(R.mipmap.triangle);
            }
            if (list.get(position).get("2") != null) {
                String name2 = list.get(position).get("2").getName().replace("." + list.get(position).get("2").getEx(), "");
                viewHolder.llC2.setVisibility(View.VISIBLE);
                if (list.get(position).get("2").isOrder()) {
                    name2 = name2.substring(2);
                }
                viewHolder.tvC2.setText(name2);
                viewHolder.llC2.setOnClickListener(new click(list.get(position).get("2")));
                if (list.get(position).get("1").getName().length() > list.get(position).get("2").getName().length()) {
                    ViewGroup.LayoutParams p2 = viewHolder.tvC2.getLayoutParams();
                    p2.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    viewHolder.tvC2.setLayoutParams(p2);
                    ViewGroup.LayoutParams lp2 = viewHolder.llC2.getLayoutParams();
                    lp2.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    viewHolder.llC2.setLayoutParams(lp2);
                } else {
                    p.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    viewHolder.tvC1.setLayoutParams(p);
                    lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    viewHolder.llC1.setLayoutParams(lp);
                    ViewGroup.LayoutParams p2 = viewHolder.tvC2.getLayoutParams();
                    p2.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    viewHolder.tvC2.setLayoutParams(p2);
                    ViewGroup.LayoutParams lp2 = viewHolder.llC2.getLayoutParams();
                    lp2.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    viewHolder.llC2.setLayoutParams(lp2);
                }
//                switch (list.get(position).get("2").getEx()){
//                    case "doc":
//                    case "docx":
//                        viewHolder.ivC2.setImageResource(R.mipmap.word);
//                        break;
//                    case "xls":
//                    case "xlsx":
//                        viewHolder.ivC2.setImageResource(R.mipmap.excel);
//                        break;
//                    case "pdf":
//                        viewHolder.ivC2.setImageResource(R.mipmap.pdf);
//                        break;
//                }
                if (list.get(position).get("2").isDir()) {
                    viewHolder.ivC2.setImageResource(R.mipmap.triangle_blue);
                } else {
                    viewHolder.ivC2.setImageResource(R.mipmap.triangle);
                }
            } else {
                viewHolder.llC2.setVisibility(View.INVISIBLE);
            }
        }
        return convertView;
    }

    private class ViewHolder {
        LinearLayout p, c, llC1, llC2;
        TextView tvP, tvC1, tvC2, tvMore;
        ImageView ivC1, ivC2;
        SwipeMenuLayout smlContent;

    }

    private class click implements View.OnClickListener {

        Desk2Bean bean;

        public click(Desk2Bean bean) {
            this.bean = bean;
        }

        @Override
        public void onClick(View v) {
            if (bean.isParent()) {
                Intent intent = new Intent(context, Desk2Activity.class);
                intent.putExtra("id", bean.getName());
                intent.putExtra("name", bean.getName());
                intent.putExtra("rout", bean.getRoute());
                context.startActivity(intent);
            } else if (bean.isDir()) {//跳转文件夹页面
                Intent intent = new Intent(context, FileActivity.class);
                String name = bean.getName();
                if (bean.isOrder()) {
                    name = name.substring(2);
                }
                intent.putExtra("id", bean.getName());
                intent.putExtra("name", name);
                intent.putExtra("rout", bean.getRoute());
                context.startActivity(intent);
            } else {//直接打开文件
                Intent intent = new Intent(context, FileReaderActivity.class);
                intent.putExtra("filePath", bean.getRoute());
                intent.putExtra("fileName", bean.getName());
                intent.putExtra("delete", false);
                context.startActivity(intent);
            }
        }
    }

}
