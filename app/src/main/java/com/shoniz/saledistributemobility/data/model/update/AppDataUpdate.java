package com.shoniz.saledistributemobility.data.model.update;

import com.shoniz.saledistributemobility.data.model.cardindex.ICardIndexRepository;
import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;

import javax.inject.Inject;

public class AppDataUpdate {

//    private CommonPackage commonPackage;
//    private IAppUpdateRepository appUpdateRepository;
//    private ISettingRepository settingRepository;
//    private IOrderRepository orderRepository;
//    private ICardIndexRepository cardIndexRepository;
//
//    @Inject
//    public AppDataUpdate(CommonPackage commonPackage,
//                         IAppUpdateRepository appUpdateRepository,
//                         ISettingRepository settingRepository,
//                         IOrderRepository orderRepository,
//                         ICardIndexRepository cardIndexRepository) {
//        this.commonPackage = commonPackage;
//        this.appUpdateRepository = appUpdateRepository;
//        this.settingRepository = settingRepository;
//        this.orderRepository = orderRepository;
//        this.cardIndexRepository = cardIndexRepository;
//
//    }



//    public void setUpdateListener(CategoryUpdateRepository.IAppUpdateListener appUpdateListener) {
//        appUpdateRepository.setAppUpdateListener(appUpdateListener);
//    }

//    public void updateOrder() throws BaseException {
//        appUpdateRepository.updateOrder();
//    }

//    public void updateWholeData() throws BaseException {
//        checkUpdateRules();
//        appUpdateRepository.updateWholeData();
//    }
//
//    public void updateWholeDataForNewVersion() throws Exception {
//        appUpdateRepository.updateWholeData();
//    }

//    public void updatePathData(int pathId) throws BaseException, ConnectionException, OldApiException, IOException, ApiException {
//        checkUpdateRules();
//        appUpdateRepository.updatePathData(pathId);
//    }
//
//    public void updateCatalog() throws ConnectionException, OldApiException, BaseException, IOException {
//        appUpdateRepository.updateCatalogData();
//    }
//
//    public enum PrePathConditionType {
//        None,
//        UnSend,
//        UnVisited,
//        Both
//    }

//    public void updatePrePath() throws BaseException, ConnectionException, OldApiException, IOException, ApiException {
//        checkUpdateRules();
//        appUpdateRepository.updatePrePathData();
//    }
}
