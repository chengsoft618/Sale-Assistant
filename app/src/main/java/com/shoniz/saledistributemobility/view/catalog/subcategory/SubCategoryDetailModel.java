package com.shoniz.saledistributemobility.view.catalog.subcategory;

import com.shoniz.saledistributemobility.view.ordering.order.IsSelectedItem;

/**
 * Created by ferdos.s on 6/1/2017.
 */

public class SubCategoryDetailModel extends IsSelectedItem {

    public static class Column{
        public static final String SubCategoryDetailId="SubCategoryDetailId";
        public static final String SubCategoryId="SubCategoryId";
        public static final String CategoryId="CategoryId";
        public static final String Shortcut="Shortcut";
        public static final String Sort="Sort";
        public static final String ProductName="ProductName";
        public static final String SalePrice="SalePrice";
        public static final String NetWeight="NetWeight";
        public static final String ProductDescription="ProductDescription";


    }
    public int SubCategoryDetailId;
    public int SubCategoryId;
    public int CategoryId;
    public int Shortcut;
    public int Sort;
    public String Prefix="";
    public String ProductName;
    public String SalePrice;
    public String NetWeight;
    public String ProductDescription;
}
