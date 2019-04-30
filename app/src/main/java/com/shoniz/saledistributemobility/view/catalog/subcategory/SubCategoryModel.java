package com.shoniz.saledistributemobility.view.catalog.subcategory;
/**
 * Created by ferdos.s on 6/1/2017.
 */

public class SubCategoryModel {

    public static class Column{
        public static final String CategoryId="CategoryId";
        public static final String SubCategoryId="SubCategoryId";
        public static final String SubCategoryName="SubCategoryName";
        public static final String ResourceFileId="ResourceFileId";
        public static final String Sort="Sort";
        public static final String CurrentShortcut ="CurrentShortcut";
    }
    public int CategoryId;
    public int SubCategoryId;
    public int ResourceFileId;
    public int Sort;
    public int CurrentShortcut;
    public String SubCategoryName;
}
