package com.shoniz.saledistributemobility.data.model.path;

import com.shoniz.saledistributemobility.data.model.path.db.PathEntity;

import java.util.List;

public interface IPathRepository {
    List<PathListData> getAllPaths();
    PathEntity getPath(int pathId);

}
