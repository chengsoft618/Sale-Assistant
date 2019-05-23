package com.shoniz.saledistributemobility.catalog;

import com.shoniz.saledistributemobility.view.ordering.order.IsSelectedItem;

/**
 * Created by ferdos.s on 6/1/2017.
 */

public class CategoryModel extends IsSelectedItem {

    public static class Column{
        public static final String ProfileCategoryId="ProfileCategoryId";
        public static final String CategoryId="CategoryId";
        public static final String CategoryName="CategoryName";
        public static final String ResourceFileId="ResourceFileId";
        public static final String Sort="Sort";


    }
    public int ProfileCategoryId;
    public int CategoryId;
    public int ResourceFileId;
    public int Sort;
    public String CategoryName;

}
