package com.shoniz.saledistributemobility.service.update;

import com.shoniz.saledistributemobility.framework.repository.update.IBasicUpdateRepository;
import com.shoniz.saledistributemobility.framework.repository.update.ICategoryUpdateRepository;
import com.shoniz.saledistributemobility.framework.repository.update.IDatabaseUpdateRepository;
import com.shoniz.saledistributemobility.framework.repository.update.IOrderUpdateRepository;
import com.shoniz.saledistributemobility.data.model.app.IAppRepository;
import com.shoniz.saledistributemobility.data.model.update.IAppUpdateListener;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.service.update.IAppUpdater;

public class AppUpdater implements IAppUpdater {

    public AppUpdater(CommonPackage commonPackage,
                      ICategoryUpdateRepository categoryUpdateRepository,
                      IDatabaseUpdateRepository databaseUpdateRepository,
                      IBasicUpdateRepository basicUpdateRepository,
                      IOrderUpdateRepository orderUpdateRepository,
                      IAppRepository appRepository,
                      ISettingRepository settingRepository) {
        this.commonPackage =commonPackage;
        this.categoryUpdateRepository = categoryUpdateRepository;
        this.databaseUpdateRepository = databaseUpdateRepository;
        this.basicUpdateRepository = basicUpdateRepository;
        this.orderUpdateRepository = orderUpdateRepository;
        this.appRepository = appRepository;
        this.settingRepository = settingRepository;
    }

    @Override
    public void updateWholeData() throws BaseException{
        updatePrePath();
        updateCatalog();
        updateUser();
    }

    @Override
    public void updatePrePath() throws BaseException{
        databaseUpdateRepository.syncSaleDb();
    }

    @Override
    public void updateCatalog() throws BaseException{
//        List<ProductImageModel> productImages categoryUpdateRepository.getChangedProductImages();
//        List<ResourceModel> resources categoryUpdateRepository.getResourceChanges();
        categoryUpdateRepository.syncProfileCategoryAll();
        categoryUpdateRepository.syncCategoryAll();
        categoryUpdateRepository.syncSubCategoryAll();
        categoryUpdateRepository.syncSubCategoryDetailAll();
        categoryUpdateRepository.syncShortcutImage();
        categoryUpdateRepository.syncResourceFile();
    }

    @Override
    public void updateUser() throws BaseException {
        basicUpdateRepository.updateEmployee();
        basicUpdateRepository.updateUsers();
       //basicUpdateRepository.updateEmployee();
    }

    @Override
    public void updateOrder() throws BaseException {
        orderUpdateRepository.updateOrder();
    }

    @Override
    public void setAppUpdateListener(IAppUpdateListener appUpdateListener) {
        categoryUpdateRepository.setAppUpdateListener(appUpdateListener);
        orderUpdateRepository.setAppUpdateListener(appUpdateListener);
        basicUpdateRepository.setAppUpdateListener(appUpdateListener);
        databaseUpdateRepository.setAppUpdateListener(appUpdateListener);
    }

    @Override
    public void setAppInitializationStatus() {
        settingRepository.setIsInitialedApplication(true);
        settingRepository.setCurrentVersionName(commonPackage.getUtility().getVersionName());
    }

    public void checkUpdateRules() throws BaseException {
//        if (!settingRepository.isInitialedApplication() ||
//                settingRepository.isNotSaleReasonChecked())
//            return;
//        settingRepository.sync();
//        boolean isEmptyCardIndex = cardIndexRepository.getAllCardIndices().size() == 0;
//        boolean isCheckNotSendOrder = settingRepository.isCheckNotSendOrder();
//        //PrePathConditionType type = PrePathConditionType.None;
//
//        boolean isReasonSendAll = orderRepository.isReasonSendAll()==0;
//        if (!isReasonSendAll && (!isEmptyCardIndex && isCheckNotSendOrder)) {
//            throw new AppUpdateError(commonPackage,
//                    R.string.error_app_update_pre_path_condition_both);
//        }
//        if (!isEmptyCardIndex && isCheckNotSendOrder) {
//            throw new AppUpdateError(commonPackage,
//                    R.string.error_app_update_pre_path_condition_unsent);
//        }
//        if (!isReasonSendAll) {
//            throw new AppUpdateError(commonPackage,
//                    R.string.error_app_update_pre_path_condition_unvisited);
//        }
    }

    private CommonPackage commonPackage;
    private ICategoryUpdateRepository categoryUpdateRepository;
    private IDatabaseUpdateRepository databaseUpdateRepository;
    private IBasicUpdateRepository basicUpdateRepository;
    private IOrderUpdateRepository orderUpdateRepository;
    private IAppRepository appRepository;
    private ISettingRepository settingRepository;
}
