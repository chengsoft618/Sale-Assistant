package com.shoniz.saledistributemobility.view.path.pathlist;

import android.view.View;

import com.shoniz.saledistributemobility.data.model.path.PathListData;
import com.shoniz.saledistributemobility.infrastructure.recycler.IRecyclerListener;

public interface IPathRecyclerListener extends IRecyclerListener {
    void onPathClick(View view, PathListData pathListData);
    void onPathDownloadClick(View view, PathListData pathListData);
}
