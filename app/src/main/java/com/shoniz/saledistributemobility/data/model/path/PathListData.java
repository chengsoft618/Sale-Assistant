package com.shoniz.saledistributemobility.data.model.path;

import android.arch.persistence.room.Embedded;

import com.shoniz.saledistributemobility.data.MetaData;
import com.shoniz.saledistributemobility.data.model.message.MessageEntity;
import com.shoniz.saledistributemobility.data.model.path.db.PathEntity;

public class PathListData extends MetaData<Integer> {

    public PathListData(PathEntity pathEntity) {
        this.pathEntity = pathEntity;
    }

    @Embedded
    public PathEntity pathEntity;

    @Override
    public Integer getId() {
        return pathEntity.PathCode;
    }
}
