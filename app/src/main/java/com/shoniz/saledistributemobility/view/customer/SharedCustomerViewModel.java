package com.shoniz.saledistributemobility.view.customer;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.shoniz.saledistributemobility.catalog.CategoryModel;
import com.shoniz.saledistributemobility.catalog.ResourceBusiness;
import com.shoniz.saledistributemobility.catalog.ResourceData;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.view.catalog.subcategory.SubCategoryDetailModel;
import com.shoniz.saledistributemobility.view.customer.cardindex.ChangeSender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SharedCustomerViewModel extends ViewModel {

    private static List<CategoryModel> categoryModels;
    private static List<CategoryModel> subCategoryModels;
    // public    MutableLiveData<Integer> pos=new MutableLiveData<>();
    public MutableLiveData<ChangeSender> changeSender = new MutableLiveData<>();
    @Inject
    CommonPackage commonPackage;
    // public    MutableLiveData<Integer> shortcut=new MutableLiveData<>();
    private String shortcutString = "0";
    private int categoryId;
    private int subcategoryId;
    private String preShortcutString = "0";
    private int preCategoryId;

    private  ChangeSender preChangeSender;
    private int preSubcategoryId;

    @Inject
    public SharedCustomerViewModel() {
    }

    public String getPreShortcutString() {
        return preShortcutString;
    }

    public int getPreCategoryId() {
        return preCategoryId;
    }

    public int getPreSubcategoryId() {
        return preSubcategoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        preCategoryId = this.categoryId;
        this.categoryId = categoryId;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        preSubcategoryId = this.subcategoryId;
        this.subcategoryId = subcategoryId;
    }

    public void setShortcutSender(String shortcut, ChangeSender changeSender) throws IOException {
        preShortcutString = shortcutString;
        preCategoryId = categoryId;
        preSubcategoryId = subcategoryId;
        preChangeSender=this.changeSender.getValue();
        this.shortcutString = shortcut;

        SubCategoryDetailModel subCategoryDetailModel = ResourceBusiness.getShortcutInfo(commonPackage.getContext(), Integer.parseInt(shortcut));
        setCategoryId(subCategoryDetailModel.CategoryId);
        setSubcategoryId(subCategoryDetailModel.SubCategoryId);
        if (!preShortcutString.equals(shortcutString) || preChangeSender!=changeSender)
            this.changeSender.setValue(changeSender);
    }

    public String getShortcutString() {
        return shortcutString;
    }

    public int getShortcut() {
        return Integer.parseInt(shortcutString);
    }

    public List<CategoryModel> getCategoryModels() throws IOException {
        if (categoryModels != null)
            return categoryModels;

        return ResourceData.getCategory(commonPackage.getContext(), 1);

    }

    public List<CategoryModel> getSubCategoryModels(int categoryId) throws IOException {
//        if (categoryModels != null)
//            return categoryModels;
        return ResourceData.getCategory(commonPackage.getContext(), categoryId);
    }

    List<OnCatalogResizeListener> catalogResizeListeners = new ArrayList<>();
    public void addCatalogResizeListener(OnCatalogResizeListener listener){
        catalogResizeListeners.add(listener);
    }

    public void resizeCatalog(){
        if (catalogResizeListeners != null && catalogResizeListeners.size() > 0) {
            if (isResizedCatalog) {
                for(OnCatalogResizeListener catalogResizeListener : catalogResizeListeners) {
                    catalogResizeListener.onCatalogReduceSize();
                    isResizedCatalog = false;
                }
            }
            else {
                for(OnCatalogResizeListener catalogResizeListener : catalogResizeListeners) {
                    catalogResizeListener.onCatalogIncreaseSize();
                    isResizedCatalog = true;
                }
            }
        }
    }

    boolean isResizedCatalog = false;

    public interface OnCatalogResizeListener {
        void onCatalogIncreaseSize();
        void onCatalogReduceSize();
    }

}
