package com.shoniz.saledistributemobility.view.path.pathlist;

import android.view.View;


import com.shoniz.saledistributemobility.data.model.path.PathListData;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerItemViewModel;

public class PathItemViewModel extends RecyclerItemViewModel<PathListData,
        IPathRecyclerListener> {

    public boolean onPathClick(View v){
        getRecyclerListener().onPathClick(v, getModel());
        super.onClick(v);
        return false;
    }

    public boolean onPathDownloadClick(View v){
        getRecyclerListener().onPathDownloadClick(v, getModel());
        super.onClick(v);
        return false;
    }
}
