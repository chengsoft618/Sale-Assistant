package com.shoniz.saledistributemobility.data.model.cardindex;



import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;

import java.io.IOException;
import java.util.List;

public interface ICardIndexRepository {
    List<CardIndexDetailData> getCardIndexDetail(int personId);
    List<CardIndexData> getAllCardIndices();
//    void removeUnchangedCardindexForEdit(IOrderRepository orderRepository,
//                                         ISettingRepository settingRepository, ICardIndexRepository cardIndexRepository,
//                                         CommonPackage commonPackage) throws IOException;

}
