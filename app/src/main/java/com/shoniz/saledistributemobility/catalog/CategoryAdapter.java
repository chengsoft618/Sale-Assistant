package com.shoniz.saledistributemobility.catalog;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.StringHelper;
import com.shoniz.saledistributemobility.utility.data.fileSystem.FileManager;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    private List<CategoryModel> models;

    private OnItemSelectedListener itemSelectedListener;

    public CategoryAdapter(List<CategoryModel> branchList, OnItemSelectedListener itemSelectedListener) {
        this.models = branchList;
        this.itemSelectedListener=itemSelectedListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
         View   v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category, viewGroup, false);
        return new ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ViewHolder holder,  int pos) {

        Context context=holder.constraintLayout.getContext();
        holder.mItem = models.get(pos);
        holder.txtTitle.setText(holder.mItem.CategoryName);
        String imagePath= FileManager.getImagePath(context);
        String fileNamePath = StringHelper.GenerateMessage(context.getString(R.string.path_resource),
                imagePath,"r", holder.mItem.ResourceFileId);

        if(models.get(holder.getAdapterPosition()).isSelected)
        {
            holder.mView.setBackgroundColor(holder.mView.getContext().getResources().getColor(R.color.md_deep_orange_100));
        }else
        {
            holder.mView.setBackgroundColor(holder.mView.getContext().getResources().getColor(R.color.transparent));
        }
        Glide.with(context)
                .load(fileNamePath)
                .into(holder.Icon);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    setSelectedItem(holder.getAdapterPosition());
                }catch (Exception e)
                {
                    e.getMessage();
                }

            }
        });
    }
    private void setSelectedItem(int pos)
    {
        CategoryModel mItem =models.get(pos);
        itemSelectedListener.onItemSelected(mItem);
        for (CategoryModel cardModel:models) {
            cardModel.isSelected=false;
        }
        models.get(pos).isSelected=true;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final TextView txtTitle;
        private final FrameLayout constraintLayout;
        private final ImageView Icon;
        private CategoryModel mItem;

        private ViewHolder(View view) {
            super(view);
            mView = view;
            txtTitle =   view.findViewById(R.id.txt_title);
            constraintLayout =  view.findViewById(R.id.whole_card);
            Icon =   view.findViewById(R.id.card_icon);
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(CategoryModel model);
    }
}


