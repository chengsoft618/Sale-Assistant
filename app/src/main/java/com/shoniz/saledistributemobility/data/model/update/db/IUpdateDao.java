package com.shoniz.saledistributemobility.data.model.update.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.shoniz.saledistributemobility.data.model.order.OrderEntity;
import com.shoniz.saledistributemobility.view.entity.CategoryEntity;
import com.shoniz.saledistributemobility.view.entity.FileResourceEntity;
import com.shoniz.saledistributemobility.view.entity.ImageVersionEntity;
import com.shoniz.saledistributemobility.view.entity.ProfileCategoryEntity;
import com.shoniz.saledistributemobility.view.entity.SubCategoryDetailEntity;
import com.shoniz.saledistributemobility.view.entity.SubCategoryEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface IUpdateDao {
    @Query("DELETE FROM 'ProfileCategory' WHERE ProfileCategoryId = :profileCategoryId")
    void deleteProfileCategory(int profileCategoryId);

    @Insert(onConflict = REPLACE)
    void insertProfileCategory(ProfileCategoryEntity profileCategoryEntity);

    @Query("DELETE FROM 'Category' WHERE CategoryId = :categoryId")
    void deleteCategory(int categoryId);

    @Insert(onConflict = REPLACE)
    void insertCategory(CategoryEntity categoryEntity);

    @Query("DELETE FROM 'subcategory' WHERE SubCategoryId = :subCategoryId")
    void deleteSubCategory(int subCategoryId);

    @Insert(onConflict = REPLACE)
    void insertSubCategory(SubCategoryEntity subCategoryEntity);

    @Query("DELETE FROM 'subcategorydetail' WHERE SubCategoryDetailId = :subCategoryDetailId")
    void deleteSubCategoryDetail(int subCategoryDetailId);

    @Insert(onConflict = REPLACE)
    void insertSubCategoryDetail(SubCategoryDetailEntity subCategoryDetailId);

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
