package com.shoniz.saledistributemobility.catalog;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.utility.StringHelper;
import com.shoniz.saledistributemobility.utility.data.fileSystem.FileManager;
import com.shoniz.saledistributemobility.view.catalog.subcategory.SubCategoryDetailModel;

import java.util.List;

public class ShortcutLargeAdapter extends RecyclerView.Adapter<ShortcutLargeAdapter.ViewHolder>{

    private List<SubCategoryDetailModel> models;
    private OnShortcutAdapterListener listener;

    public ShortcutLargeAdapter(List<SubCategoryDetailModel> branchList, ViewGroup view, OnShortcutAdapterListener listener) {
        this.models = branchList;
        this.listener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_shortcut, null, false);
        RecyclerView.LayoutParams rlp = new RecyclerView.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        rootView.setLayoutParams(rlp);
        return new ViewHolder(rootView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ViewHolder holder,  int pos) {
        try {
            Context context=holder.Icon.getContext();
            holder.mItem = models.get(pos);
            String imagePath= FileManager.getImagePath(context);
            String fileNamePath = StringHelper.GenerateMessage(context.getString(R.string.path_resource),
                   imagePath, holder.mItem .Prefix, holder.mItem.Shortcut);

            Glide.with(context)
                    .load(fileNamePath)
                    .into(holder.Icon);

           // holder.Icon.setImageURI(Uri.parse(fileNamePath));
            if(holder.mItem .Prefix.equals("")){
                holder.layoutProductDescription.setVisibility(View.VISIBLE);
                holder.txtDescription.setText(holder.mItem.ProductDescription);
                holder.txtWeight.setText(StringHelper.GenerateMessage(context,R.string.weight, Integer.parseInt(holder.mItem.NetWeight)));
                holder.txtProductName.setText(holder.mItem.ProductName);
                holder.txtPrice.setText(StringHelper.GenerateMessage(context,R.string.price, Integer.parseInt(holder.mItem.SalePrice)));
            }else {
                holder.layoutProductDescription.setVisibility(View.INVISIBLE);
            }

            holder.Icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                        listener.onItemSelected(pos);
                }});

            /*holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // notifyDataSetChanged();
                }
            })*/
        } catch (Exception e) {
             e.getMessage();
        }

    }




    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final ImageView Icon;
        private SubCategoryDetailModel mItem;
        private TextView txtDescription;
        private TextView txtPrice;
        private TextView txtWeight;
        private TextView txtProductName;
        private LinearLayout layoutProductDescription;

        private ViewHolder(View view) {
            super(view);

            mView = view;
            Icon =   view.findViewById(R.id.card_icon);
            txtDescription =   view.findViewById(R.id.txt_description);
            txtPrice =   view.findViewById(R.id.txt_price);
            txtWeight =   view.findViewById(R.id.txt_weight);
            txtProductName =   view.findViewById(R.id.txt_product_name);
            layoutProductDescription =   view.findViewById(R.id.layout_product_description);
        }
    }

    public interface OnShortcutAdapterListener {
        void onItemSelected(int pos);
    }


}


