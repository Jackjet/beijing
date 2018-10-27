package com.ysbd.beijing.directorDesktop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysbd.beijing.R;
import com.ysbd.beijing.directorDesktop.bean.AttachmentBean;
import com.ysbd.beijing.directorDesktop.utils.FileQueryUtil;

import java.util.List;

/**
 * Created by niuhuahua on 2018/3/15.
 */

public class AttachmentAdapter extends BaseAdapter {
    private Context context;
    private List<AttachmentBean> attachmentBeans;

    public boolean isShowCatalog() {
        return showCatalog;
    }

    public void setShowCatalog(boolean showCatalog) {
        this.showCatalog = showCatalog;
    }

    private boolean showCatalog=false;

    public AttachmentAdapter(Context context, List<AttachmentBean> attachmentBeans) {
        this.context = context;
        this.attachmentBeans = attachmentBeans;
    }

    @Override
    public int getCount() {
        return attachmentBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return attachmentBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHold hold;
        if (view==null) {
            hold=new ViewHold();
            view= LayoutInflater.from(context).inflate(R.layout.item_attachmenet,null);
            hold.tvExtension=view.findViewById(R.id.tvExtension);
            hold.tvName=view.findViewById(R.id.tvName);
            hold.tvCate=view.findViewById(R.id.tvCate);
            hold.tvDir=view.findViewById(R.id.tvDir);
            hold.llDir=view.findViewById(R.id.llDir);
            hold.imageView=view.findViewById(R.id.civ);
            hold.iv=view.findViewById(R.id.iv);
            view.setTag(hold);
        }else {
            hold=(ViewHold)view.getTag();
        }


        if (showCatalog) {
            hold.llDir.setVisibility(View.VISIBLE);
            hold.tvCate.setText(attachmentBeans.get(i).getCatalog().substring(2));
            String replace="/storage/emulated/0/bjczjdesk/desk"+ FileQueryUtil.getCatalogId(attachmentBeans.get(i).getCatalog());
            String dir=attachmentBeans.get(i).getParent().replace(replace,"局长桌面");
            for (int i1 = 0; i1 < 3; i1++) {
                int index=dir.indexOf("/0");
                if (index<0) {
                    index=dir.indexOf("/1");
                }
                if (index>0) {
                    dir=dir.substring(0,index+1)+dir.substring(index+3);
                }
            }
            dir=dir.replaceAll("/01","/");
            hold.tvDir.setText(dir);
        }else {
            hold.llDir.setVisibility(View.GONE);
        }
        int backgroundResource=R.mipmap.small_blue;
        if (attachmentBeans.size()<=i) {
            return view;
        }
        if(attachmentBeans.get(i).isFile()){
            hold.iv.setVisibility(View.VISIBLE);
            hold.imageView.setVisibility(View.GONE);
            hold.tvExtension.setVisibility(View.GONE);
        }
        else {
            hold.iv.setVisibility(View.GONE);
            hold.imageView.setVisibility(View.VISIBLE);
            hold.tvExtension.setVisibility(View.VISIBLE);
            switch (attachmentBeans.get(i).getExtension()){
                case "doc":
                case "docx":
                    backgroundResource= R.mipmap.small_blue_deep;
                    break;
                case "pdf":
                    backgroundResource=R.mipmap.small_red;
                    break;
                case "jpg":
                case "xlsx":
                    backgroundResource=R.mipmap.small_green_noo;
                    break;
                case "zip":
                    backgroundResource=R.mipmap.small_purp;
                    break;
            }

            hold.imageView.setImageResource(backgroundResource);

            hold.tvExtension.setText(attachmentBeans.get(i).getExtension());
        }


        String name=attachmentBeans.get(i).getName();
        if(name.length()>2){
            try {
                int a=Integer.parseInt(name.substring(0,2));
                if (a>0) {
                    name=name.substring(2);
                }
            }catch (Exception ignore){

            }

        }
        hold.tvName.setText(name);


        return view;
    }

    private class ViewHold{
        ImageView imageView,iv;
        TextView tvName,tvDir,tvCate,tvExtension;
        LinearLayout llDir;
//        ColorTextView tvExtension;
    }

}
