package com.shoniz.saledistributemobility.service.update;

import com.shoniz.saledistributemobility.data.api.ApiNetworkException;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBasicEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBoughtEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerChequeEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerCreditEntity;
import com.shoniz.saledistributemobility.data.model.customer.api.ICustomerApi;
import com.shoniz.saledistributemobility.data.model.customer.db.ICustomerDao;
import com.shoniz.saledistributemobility.data.model.order.OrderDetailEntity;
import com.shoniz.saledistributemobility.data.model.order.OrderEntity;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.repository.update.IBasicUpdateRepository;
import com.shoniz.saledistributemobility.framework.repository.update.ICategoryUpdateRepository;
import com.shoniz.saledistributemobility.framework.repository.update.ICustomerUpdateRepository;
import com.shoniz.saledistributemobility.framework.repository.update.IDatabaseUpdateRepository;
import com.shoniz.saledistributemobility.framework.repository.update.IOrderUpdateRepository;
import com.shoniz.saledistributemobility.data.model.app.IAppRepository;
import com.shoniz.saledistributemobility.data.model.update.IAppUpdateListener;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.service.update.IAppUpdater;

import java.util.ArrayList;
import java.util.List;

public class AppUpdater implements IAppUpdater {



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

    @Override
    public void updateWholeInfoOfPerson(int personId) throws BaseException {
        customerUpdateRepository.updateWholeInfoOfPerson(personId);
    }

    @Override
    public void updateWholeInfoOfPath(int pathId) throws BaseException {
        customerUpdateRepository.updateWholeInfoOfPath(pathId);
    }

    public void checkUpdateRules() throws BaseException {
//        if (!settingRepository.isInitialedApplication() ||
//                settingRepository.isNotSaleReasonChecked())
//            return;
//        settingRepository.sync();
//        boolean isCardIndexEmpty = cardIndexRepository.getAllCardIndices().size() == 0;
//        boolean isCheckNotSendOrder = settingRepository.isCheckNotSendOrder();
//        //PrePathConditionType type = PrePathConditionType.None;
//
//        boolean isReasonSendAll = orderRepository.isReasonSendAll()==0;
//        if (!isReasonSendAll && (!isCardIndexEmpty && isCheckNotSendOrder)) {
//            throw new AppUpdateError(commonPackage,
//                    R.string.error_app_update_pre_path_condition_both);
//        }
//        if (!isCardIndexEmpty && isCheckNotSendOrder) {
//            throw new AppUpdateError(commonPackage,
//                    R.string.error_app_update_pre_path_condition_unsent);
//        }
//        if (!isReasonSendAll) {
//            throw new AppUpdateError(commonPackage,
//                    R.string.error_app_update_pre_path_condition_unvisited);
//        }
    }

    public AppUpdater(CommonPackage commonPackage,
                      ICategoryUpdateRepository categoryUpdateRepository,
                      IDatabaseUpdateRepository databaseUpdateRepository,
                      IBasicUpdateRepository basicUpdateRepository,
                      IOrderUpdateRepository orderUpdateRepository,
                      IAppRepository appRepository,
                      ISettingRepository settingRepository,
                      ICustomerUpdateRepository customerUpdateRepository) {
        this.commonPackage =commonPackage;
        this.categoryUpdateRepository = categoryUpdateRepository;
        this.databaseUpdateRepository = databaseUpdateRepository;
        this.basicUpdateRepository = basicUpdateRepository;
        this.orderUpdateRepository = orderUpdateRepository;
        this.appRepository = appRepository;
        this.settingRepository = settingRepository;
        this.customerUpdateRepository = customerUpdateRepository;
    }



    private CommonPackage commonPackage;
    private ICategoryUpdateRepository categoryUpdateRepository;
    private IDatabaseUpdateRepository databaseUpdateRepository;
    private IBasicUpdateRepository basicUpdateRepository;
    private IOrderUpdateRepository orderUpdateRepository;
    private IAppRepository appRepository;
    private ISettingRepository settingRepository;
    private ICustomerUpdateRepository customerUpdateRepository;
}
