package com.ysbd.beijing.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ysbd.beijing.R;
import com.ysbd.beijing.ui.bean.AttachmentBean;
import com.ysbd.beijing.view.LinearLayoutBaseAdapter;

import java.util.List;

/**
 * Created by lcjing on 2018/8/30.
 */

public class AttachmentAdapter extends LinearLayoutBaseAdapter {
    List<AttachmentBean> attachments;

    public AttachmentAdapter(Context context, List<AttachmentBean> list) {
        super(context, list);
        attachments=list;
    }

    @Override
    public View getView(int position) {
        View view=getLayoutInflater().inflate(R.layout.item_attachment,null);
        TextView tvName,tvTitle,tvEx,tvTime,tvDepart;
        tvName=view.findViewById(R.id.user);
        tvTitle=view.findViewById(R.id.title);
        tvEx=view.findViewById(R.id.extension);
        tvTime=view.findViewById(R.id.time);
        tvDepart=view.findViewById(R.id.depart);
        tvEx.setText(attachments.get(position).getAttachment_extension());
        tvTitle.setText(attachments.get(position).getAttachment_name());
        tvName.setText("");
        if (attachments.get(position).getAttachment_lastmodified()!=null) {
            tvTime.setText(attachments.get(position).getAttachment_lastmodified());
        }else
        tvTime.setText("");
        if (attachments.get(position).getDepartment_shortdn()!=null) {
            tvDepart.setText(attachments.get(position).getDepartment_shortdn());
        }else
        tvDepart.setText("");
        return view;
    }
}
