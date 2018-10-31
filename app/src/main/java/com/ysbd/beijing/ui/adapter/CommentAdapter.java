package com.ysbd.beijing.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.ClipboardManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.ysbd.beijing.App;
import com.ysbd.beijing.R;
import com.ysbd.beijing.ui.bean.OpinionModel;
import com.ysbd.beijing.utils.DBUtils;
import com.ysbd.beijing.utils.SpUtils;
import com.ysbd.beijing.utils.WebServiceManager;
import com.ysbd.beijing.utils.WebServiceUtils;
import com.ysbd.beijing.view.LinearLayoutBaseAdapter;

import java.util.List;

/**
 * Created by lcjing on 2018/8/16.
 */

public class CommentAdapter extends LinearLayoutBaseAdapter {


    public CommentAdapter(Context context, List<?> list, String type) {
        super(context, list);
        this.type = type;
    }

    private boolean editable;

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCommentClick(CommentClick commentClick) {
        this.commentClick = commentClick;
    }

    private CommentClick commentClick;

    @Override
    public View getView(final int position) {
        final ViewHolder holder;
        View view = getLayoutInflater().inflate(R.layout.item_comments, null);
        holder = new ViewHolder(view);
        if (list.size() <= position) {
            return view;
        }
        final OpinionModel opinionModel = (OpinionModel) list.get(position);
        if (opinionModel.isParent()) {
            holder.llParent.setVisibility(View.VISIBLE);
            holder.llComment.setVisibility(View.GONE);
            String frameName = opinionModel.getOpinionFrameName() + "：";
//            holder.tvParentName.setText(frameName);
            if (!editable || opinionModel.getOpinionFrameMark() == null || opinionModel.getOpinionFrameMark().length() < 1 || !opinionModel.isAddable()) {
                holder.llParent.setVisibility(View.GONE);
                Log.e("错误11",String.valueOf(editable));
                Log.e("错误222",String.valueOf(opinionModel.getOpinionFrameMark()));
                Log.e("错误333",String.valueOf(opinionModel.getOpinionFrameMark().length() < 1 ));
                Log.e("错误444",String.valueOf(opinionModel.isAddable()));
            }
            holder.flAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    commentClick.add(position, type);
                }
            });

        } else {
            holder.llParent.setVisibility(View.GONE);
            holder.llComment.setVisibility(View.VISIBLE);
            if (opinionModel.getContent() != null) {
                holder.tvComment.setText(Html.fromHtml(opinionModel.getContent().replaceAll("&nbsp", "")));
            }
            holder.tvDate.setText(opinionModel.getCreateDate());
            holder.tvName.setVisibility(View.GONE);
            holder.ivName.setVisibility(View.VISIBLE);

            //加载意见签名图片
            final String url = WebServiceUtils.SIGNATURE_IMG + opinionModel.getUserId();
            Log.d(WebServiceManager.TAG, "name=" + opinionModel.getUserName());
            Log.d(WebServiceManager.TAG, "url=" + url);
            SimpleTarget<GlideDrawable> simpleTarget = new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
                    holder.ivName.setImageDrawable(resource);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    super.onLoadFailed(e, errorDrawable);
                    holder.ivName.setVisibility(View.GONE);
                    holder.tvName.setVisibility(View.VISIBLE);
                    holder.tvName.setText(opinionModel.getUserName());
                }
            };
            Glide.with(App.getContext()).
                    load(url).into(simpleTarget);

//            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//                    //添加ClipData对象到剪切板中
//                    clipboardManager.setText(url);
//                }
//            });
//            holder.ivName.setImageResource(DBUtils.getSign(opinionModel.getUserName()));
//            if (DBUtils.getSign(opinionModel.getUserName()) == 0) {
//                holder.tvName.setVisibility(View.VISIBLE);
//                holder.tvName.setText(opinionModel.getUserName());
//                holder.ivName.setVisibility(View.GONE);
//            } else {
//            }
            holder.flEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    commentClick.edit(position, type);
                }
            });

            holder.flDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    commentClick.delete(position, type);
                }
            });
            if (opinionModel.isEditable()) {
                opinionModel.setEditable(SpUtils.getInstance().getCommentEditable(opinionModel.getId()) && editable);
            }
            if (opinionModel.isEditable()) {
                holder.flEdit.setVisibility(View.VISIBLE);
                holder.flDel.setVisibility(View.VISIBLE);
                holder.tvLine.setVisibility(View.VISIBLE);
            } else {
                holder.flEdit.setVisibility(View.GONE);
                holder.flDel.setVisibility(View.GONE);
                holder.tvLine.setVisibility(View.GONE);
            }

        }
        return view;
    }

    private String type;

    class ErrorException extends Exception {
        ViewHolder viewHolder;
        OpinionModel opinionModel;

        public ErrorException(ViewHolder viewHolder, OpinionModel opinionModel) {
            this.viewHolder = viewHolder;
            this.opinionModel = opinionModel;
            viewHolder.ivName.setVisibility(View.GONE);
            viewHolder.tvName.setVisibility(View.VISIBLE);
            viewHolder.tvName.setText(opinionModel.getUserName());
            Log.d(WebServiceManager.TAG, "ErrorExceptionName=" + opinionModel.getUserName());
            Log.d(WebServiceManager.TAG, "ErrorExceptionId=" + opinionModel.getUserId() + getMessage());
        }
    }

    private class ViewHolder {
        TextView tvParentName, tvComment, tvName, tvDate;
        LinearLayout llParent, llComment, itemLayout;
        FrameLayout flAdd, flEdit, flDel;
        ImageView ivName;
        View tvLine;

        ViewHolder(View view) {
            tvParentName = view.findViewById(R.id.tvParentName);
            tvComment = view.findViewById(R.id.tvComment);
            tvName = view.findViewById(R.id.tvName);
            tvDate = view.findViewById(R.id.tvDate);
            llParent = view.findViewById(R.id.llParent);
            llComment = view.findViewById(R.id.llComment);
            flAdd = view.findViewById(R.id.flAdd);
            flEdit = view.findViewById(R.id.flEdit);
            flDel = view.findViewById(R.id.flDel);
            ivName = view.findViewById(R.id.ivName);
            tvLine = view.findViewById(R.id.tvLine);
            itemLayout = view.findViewById(R.id.itemComments);
        }
    }

    public interface CommentClick {
        void edit(int position, String type);

        void delete(int position, String type);

        void add(int position, String type);
    }


}
