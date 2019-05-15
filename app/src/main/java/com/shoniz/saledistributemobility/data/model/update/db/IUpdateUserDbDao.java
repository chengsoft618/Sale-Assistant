package com.shoniz.saledistributemobility.data.model.update.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.shoniz.saledistributemobility.view.entity.FileResourceEntity;
import com.shoniz.saledistributemobility.view.entity.ImageVersionEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface IUpdateUserDbDao {
    @Query("DELETE FROM 'ImageVersion' WHERE Shortcut = :shortcut")
    void deleteImageVersion(int shortcut);

    @Insert(onConflict = REPLACE)
    void insertImageVersion(ImageVersionEntity imageVersionEntity);

    @Query("DELETE FROM 'FileResource' WHERE ResourceFileId = :resourceFileId")
    void deleteFileResource(int resourceFileId);

    @Insert(onConflict = REPLACE)
    void insertFileResource(FileResourceEntity fileResourceEntity);

    @Query("SELECT * FROM ImageVersion")
    List<ImageVersionEntity> getUpdatedShortcutsImages();

    @Query("SELECT * FROM FileResource")
    List<FileResourceEntity> getUpdatedResources();
}
