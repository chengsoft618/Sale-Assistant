package com.shoniz.saledistributemobility.data.model.path;

import com.shoniz.saledistributemobility.data.model.path.db.IPathDao;
import com.shoniz.saledistributemobility.data.model.path.db.PathEntity;

import java.util.List;

import javax.inject.Inject;

public class PathRepository implements IPathRepository {

    @Override
    public List<PathListData> getAllPaths() {
        return pathDao.getAllPaths();
    }

    @Override
    public PathEntity getPath(int pathId) {
        return pathDao.getPath(pathId);
    }

    @Inject
    public PathRepository(IPathDao pathDao) {
        this.pathDao = pathDao;
    }

    private IPathDao pathDao;
}
