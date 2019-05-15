package com.shoniz.saledistributemobility.view.customer.cardindex;

import android.content.Context;

import com.shoniz.saledistributemobility.data.model.customer.UnvisitedReasonData;
import com.shoniz.saledistributemobility.utility.data.pref.AppPref;
import com.shoniz.saledistributemobility.utility.PersianCalendar;

import java.io.IOException;
import java.util.List;


public class CardIndexBusiness {
    public static void saveOrderNo(Context context, int personID, int orderNo) throws Exception {
        createCardIndexIfNotExist(context, personID);
        CardIndexOldDb.saveOrderNo(context, personID, orderNo);
    }

    public static void saveChequeDuration(Context context, int personID, int chequeDuration) throws Exception {
        createCardIndexIfNotExist(context, personID);
        CardIndexOldDb.saveChequeDuration(context, personID, chequeDuration);
    }

    public static void saveDescriptions(Context context, int personID, String accDesc,String saleDesc,int addressID) throws Exception {
        createCardIndexIfNotExist(context, personID);
        CardIndexOldDb.saveDescription(context, personID, accDesc,saleDesc,addressID);
    }

    public static void saveRequireAmount(Context context, CardIndexParam cardIndexParam) throws Exception {
        saveAmount(context, cardIndexParam, AmountType.Require);
        clearZeroAmountRecord(context);
    }

    public static void saveExistenceAmount(Context context, CardIndexParam cardIndexParam) throws Exception {
        saveAmount(context, cardIndexParam, AmountType.Existence);
        clearZeroAmountRecord(context);
    }



    public static void clearZeroAmountRecord(Context context) throws IOException {
        CardIndexOldDb.clearZeroAmountRecord(context);
    }
    public static CardIndexDto getCardIndexDto(Context context, int personId) throws IOException {
        return CardIndexOldDb.getCardIndexDto(context, personId);
    }

//    public static UnvisitedReasonData getReasonDto(Context context, int personId) throws IOException {
//        return CardIndexOldDb.getReasonDto(context, personId);
//    }

//    public static List<UnvisitedReasonData> getReasonDto(Context context, List<Integer> personIds) throws IOException {
//        return CardIndexOldDb.getReasonDto(context, personIds);
//    }

    private static void saveAmount(Context context, CardIndexParam cardIndexParam, AmountType amountType) throws Exception {
        createCardIndexIfNotExist(context, cardIndexParam.PersonId);

        if (CardIndexOldDb.getCardIndexDetailByShortcut(context, cardIndexParam.PersonId, cardIndexParam.Shortcut) != null) {
            if (amountType == AmountType.Require)
                CardIndexOldDb.saveRequestAmount(context, cardIndexParam);
            else
                CardIndexOldDb.saveExistenceAmount(context, cardIndexParam);
        }
        else {
            CardIndexDetailModel cardIndexDetailModel = new CardIndexDetailModel();
            cardIndexDetailModel.PersonId = cardIndexParam.PersonId;
            cardIndexDetailModel.Shortcut = cardIndexParam.Shortcut;

            if (amountType == AmountType.Require) {
                cardIndexDetailModel.RequestCarton = cardIndexParam.Carton;
                cardIndexDetailModel.RequestPackage = cardIndexParam.Package;
            }
            else{
                cardIndexDetailModel.ExistenceCarton = cardIndexParam.Carton;
                cardIndexDetailModel.ExistencePackage = cardIndexParam.Package;
            }

            CardIndexOldDb.insertCardIndexDetail(context, cardIndexDetailModel);
        }
    }

    private static void createCardIndexIfNotExist(Context context, int personId) throws IOException {
        if(CardIndexOldDb.getCardIndexByPersonId(context, personId) == null){
            CardIndexModel cardIndexModel = new CardIndexModel();
            cardIndexModel.OrderNo = 0;
            cardIndexModel.NeedDate = PersianCalendar.getPersianDate();
            cardIndexModel.ChequeDuration=30;
            try {
                cardIndexModel.ChequeDuration = AppPref.getChequeDuration(context);
            }catch (Exception ex){

            }
            cardIndexModel.PersonID = personId;
            CardIndexOldDb.insertCardIndex(context, cardIndexModel);
        }
    }

    public static void updateCardIndexDescriptions(Context context, int personId,String accDesc,String saleDesc) throws IOException {

        if(CardIndexOldDb.getCardIndexByPersonId(context, personId) == null){
            CardIndexModel cardIndexModel = new CardIndexModel();
            cardIndexModel.PersonID = personId;
            cardIndexModel.AccDesc=accDesc;
            cardIndexModel.SaleDesc=saleDesc;
            CardIndexOldDb.updateCardIndexDescriptions(context, cardIndexModel);
        }
    }

    public static void DeleteCardIndex(Context context,int personID) throws IOException {
        CardIndexOldDb.deleteCardIndex(context,personID);
        CardIndexOldDb.deleteCardIndexDetail(context,personID);
    }

    public static void UpdateErrorMessage(Context context, int personID,String error) throws IOException {
        CardIndexModel cardIndexModel = new CardIndexModel();
        cardIndexModel.PersonID = personID;
        cardIndexModel.ErrorMessage=error;
        CardIndexOldDb.updateCardIndexErrorMessage(context, cardIndexModel);
    }

/*    public static Dictionary getUnvisitedCustomerReasons(Context context) throws IOException {
        return CardIndexOldDb.getUnvisitedCustomerReasons(context);
    }*/

    public static void insertUnVisitedReason(Context context, int personId, int reasonId) throws IOException {
        CardIndexOldDb.deleteUnvisitedCustomerReason(context, personId);
        if(reasonId != 1)
            CardIndexOldDb.insertUnvisitedCustomerReason(context, personId, reasonId);

    }

    public static boolean IsEmptyCardIndex(Context context) throws IOException {
        return CardIndexOldDb.IsEmptyCardIndex(context);
    }

//    public static boolean isCardIndexEmpty(Context context, int personId) throws IOException {
//        return CardIndexOldDb.isEmptyCardIndex(context, personId);
//    }

    public static long getRequestAmount(Context context,int personID) throws IOException {
        return CardIndexOldDb.GetRequestAmount(context,personID);
    }

    public enum AmountType{
        Require,    Existence
    }


}
