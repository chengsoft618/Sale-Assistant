package com.shoniz.saledistributemobility.view.customer.cardindex;

import android.content.Context;
import android.database.Cursor;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.customer.UnvisitedReasonData;
import com.shoniz.saledistributemobility.utility.data.sqlite.DBHelper;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.StringHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class CardIndexOldDb {

    public static List<CardIndexDetailModel> getNewCardIndexByPersonId(Context context, Integer personId) throws IOException {
        //TODO : Change this method
        DBHelper db = null;
        Cursor cursor = null;
        List<CardIndexDetailModel> lst;
        try {
            db = Common.getSaleDataBase(context);
            List<Long> orderNoInHistory = getHistoryDates(context, personId, db);
            cursor = getCardIndexDetailsCursor(context, orderNoInHistory, db, personId);
            lst=makeCardIndexDetailList(cursor);
        } finally {

            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return lst;
    }

    private static Cursor getCardIndexDetailsCursor(Context context, List<Long> orderNoInHistory, DBHelper db, int personId){

            String command;
            command = StringHelper.GenerateMessage(context, R.string.card_index_all_query,
                    personId + "",
                    orderNoInHistory.size() > 0 ? orderNoInHistory.get(0).toString() : "0",
                    orderNoInHistory.size() > 1 ? orderNoInHistory.get(1).toString() : "0",
                    orderNoInHistory.size() > 2 ? orderNoInHistory.get(2).toString() : "0");

            return db.select(command);

    }

    private static List<CardIndexDetailModel> makeCardIndexDetailList(Cursor cursor) {
        try {
            List<CardIndexDetailModel> cardIndexDetailModelList = new ArrayList<>();
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    CardIndexDetailModel cardIndexDetailModel = new CardIndexDetailModel();

                    cardIndexDetailModel.Shortcut = cursor.getString(cursor.getColumnIndex(CardIndexDetailModel.Column.Shortcut));
                    cardIndexDetailModel.ProductName = cursor.getString(cursor.getColumnIndex(CardIndexDetailModel.Column.Product_Name));
                    cardIndexDetailModel.CartonPrice = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Carton_Price));
                    cardIndexDetailModel.PackagePrice = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Package_Price));
                    cardIndexDetailModel.ExistenceCarton = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Existence_Carton));
                    cardIndexDetailModel.ExistencePackage = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Existence_Package));
                    cardIndexDetailModel.RequestCarton = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Request_Carton));
                    cardIndexDetailModel.RequestPackage = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Request_Package));

                    cardIndexDetailModel.CardIndexHistory.OrderNo1 = cursor.getLong(cursor.getColumnIndex(CardIndexDetailModel.HistoryColumn.OrderNo1));
                    cardIndexDetailModel.CardIndexHistory.OrderNo2 = cursor.getLong(cursor.getColumnIndex(CardIndexDetailModel.HistoryColumn.OrderNo2));
                    cardIndexDetailModel.CardIndexHistory.OrderNo3 = cursor.getLong(cursor.getColumnIndex(CardIndexDetailModel.HistoryColumn.OrderNo3));
                    cardIndexDetailModel.CardIndexHistory.QtyCarton1 = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.HistoryColumn.QtyCarton1));
                    cardIndexDetailModel.CardIndexHistory.QtyCarton2 = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.HistoryColumn.QtyCarton2));
                    cardIndexDetailModel.CardIndexHistory.QtyCarton3 = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.HistoryColumn.QtyCarton3));
                    cardIndexDetailModel.CardIndexHistory.QtyPackge1 = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.HistoryColumn.QtyPackge1));
                    cardIndexDetailModel.CardIndexHistory.QtyPackge2 = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.HistoryColumn.QtyPackge2));
                    cardIndexDetailModel.CardIndexHistory.QtyPackge3 = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.HistoryColumn.QtyPackge3));
                    cardIndexDetailModel.CardIndexHistory.Shortcut = cursor.getString(cursor.getColumnIndex(CardIndexDetailModel.HistoryColumn.Shortcut));

                    cardIndexDetailModelList.add(cardIndexDetailModel);
                } while (cursor.moveToNext());
                return cardIndexDetailModelList;
            }
            return cardIndexDetailModelList;
        }
        finally {
//            if (cursor != null)
//                cursor.close();
        }
    }

    public static void deleteUnvisitedCustomerReason(Context context, int personId) throws IOException {
        DBHelper db = null;
        try {
            db = Common.getSaleDataBase(context);
            String command = StringHelper.GenerateMessage(
                    context,
                    R.string.delete_unvisited_customer_reason,
                    personId + ""
            );
            db.execSQL(command);
        }
        finally {
            if (db != null)
                db.close();
        }
    }
    public static List<Long> getHistoryDates(Context context, Integer personId, DBHelper db) {
        Cursor cursor = null;
        try {
            List<Long> orderNoInHistory = new ArrayList<>();
            String command = StringHelper.GenerateMessage(context, R.string.card_index_order_no_from_attached_database,
                    personId.toString());
            cursor = db.select(command);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    orderNoInHistory.add(cursor.getLong(cursor.getColumnIndex(CardIndexDetailModel.Column.Order_NO)));
                } while (cursor.moveToNext());
                cursor.close();
            }
            return orderNoInHistory;
        } finally {
//            if (db != null)
//                db.close();
//            if (cursor != null)
//                cursor.close();
        }
    }

    private static List<String> getDataBases(DBHelper db) {

        Cursor cursor = null;
        try {
            List<String> dbName = new ArrayList<>();
            String command = "PRAGMA database_list";
            cursor = db.select(command);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    dbName.add(cursor.getString(cursor.getColumnIndex("name")));
                } while (cursor.moveToNext());
                cursor.close();
            }
            return dbName;
        } finally {
            if (db != null)
                db.close();
            if (cursor != null)
                cursor.close();
        }
    }

    private static List<String> getTables(DBHelper db,String database) {

        Cursor cursor = null;
        try {
            List<String> dbName = new ArrayList<>();
            String command = "SELECT * FROM \""+database+"\".sqlite_master ";
            cursor = db.select(command);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    dbName.add(cursor.getString(cursor.getColumnIndex("name")));
                } while (cursor.moveToNext());
                cursor.close();
            }
            return dbName;
        } finally {
            if (db != null)
                db.close();
            if (cursor != null)
                cursor.close();
        }
    }

    public static List<String> getCardIndexDatesByPersonId(Context context, Integer personId) throws IOException {
        DBHelper db = null;
        CardIndexDetailModel model = new CardIndexDetailModel();
        List<String> cardIndexDates = new ArrayList<>();
        Cursor cursor = null;
        try {
            db = Common.getSaleDataBase(context);

            String command = StringHelper.GenerateMessage(context, R.string.card_index_dates,
                    personId.toString());

            cursor = db.select(command);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    cardIndexDates.add(cursor.getString(cursor.getColumnIndex(CardIndexModel.Column.RegDate)));
                } while (cursor.moveToNext());
            }
        }
        finally {
            if (db != null)
                db.close();
            if (cursor != null)
                cursor.close();
        }
        return cardIndexDates;
    }

    public static CardIndexModel getCardIndexByPersonId(Context context, int personId) throws IOException {
        DBHelper db = null;
        Cursor cursor = null;
        try {
            db = Common.getSaleDataBase(context);

            String command = StringHelper.GenerateMessage(context, R.string.get_card_index_by_person,
                    personId+"");
            cursor = db.select(command);
            CardIndexModel cardIndexModel = null;

            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                cardIndexModel = new CardIndexModel();
                cardIndexModel.PersonID = cursor.getInt(cursor.getColumnIndex(CardIndexModel.Column.PersonID));
                cardIndexModel.NeedDate =cursor.getString(cursor.getColumnIndex(CardIndexModel.Column.NeedDate));
                cardIndexModel.ChequeDuration =cursor.getInt(cursor.getColumnIndex(CardIndexModel.Column.ChequeDuration));
                cardIndexModel.OrderNo =cursor.getLong(cursor.getColumnIndex(CardIndexModel.Column.OrderNo));
                cardIndexModel.AccDesc =cursor.getString(cursor.getColumnIndex(CardIndexModel.Column.AccDesc));
                cardIndexModel.SaleDesc =cursor.getString(cursor.getColumnIndex(CardIndexModel.Column.SaleDesc));
                cardIndexModel.ErrorMessage =cursor.getString(cursor.getColumnIndex(CardIndexModel.Column.ErrorMessage));
                cardIndexModel.AddressID =cursor.getInt(cursor.getColumnIndex(CardIndexModel.Column.AddressID));
            }
            return cardIndexModel;
        }
        finally {
            if(cursor != null)
                cursor.close();
            if(db != null)
                db.close();
        }

    }



    public static CardIndexDetailModel getCardIndexDetailByShortcut(Context context, int personId, String shortcut) throws IOException {
        DBHelper db = null;
        Cursor cursor = null;
        try {
            db = Common.getSaleDataBase(context);

            String command = StringHelper.GenerateMessage(context, R.string.get_card_index_detail_by_person_and_shortcut,
                    personId+"",
                    shortcut);
            cursor = db.select(command);
            CardIndexDetailModel cardIndexDetailModel = null;
            if(cursor.getCount() > 0) {
                cardIndexDetailModel = new CardIndexDetailModel();
                cursor.moveToFirst();
                cardIndexDetailModel.Shortcut = cursor.getString(cursor.getColumnIndex(CardIndexDetailModel.Column.Shortcut));
                cardIndexDetailModel.RequestCarton = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Request_Carton));
                cardIndexDetailModel.RequestPackage = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Request_Package));
                cardIndexDetailModel.ExistenceCarton = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Existence_Carton));
                cardIndexDetailModel.ExistencePackage = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Existence_Package));
                cardIndexDetailModel.PersonId = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.PersonId));
            }
            return cardIndexDetailModel;

        }
        finally {
            if(cursor != null)
                cursor.close();
            if(db != null)
                db.close();
        }

    }

    public static void insertCardIndex(Context context, CardIndexModel cardIndexModel) throws IOException {
        DBHelper db = null;
        try {
            db = Common.getSaleDataBase(context);

            String command = StringHelper.GenerateMessage(
                    context,
                    R.string.new_card_index,
                    cardIndexModel.NeedDate,
                    cardIndexModel.OrderNo+"",
                    cardIndexModel.ChequeDuration+"",
                    cardIndexModel.PersonID+"",
                    cardIndexModel.SaleDesc+"",
                    cardIndexModel.AccDesc+"",
                    cardIndexModel.RegDate+""
            );
            db.execSQL(command);
        }
        finally {
            if(db != null)
                db.close();
        }
    }

    public static void updateCardIndexDescriptions(Context context, CardIndexModel cardIndexModel) throws IOException {
        DBHelper db = null;
        try {
            db = Common.getSaleDataBase(context);

            String command = StringHelper.GenerateMessage(
                    context,
                    R.string.update_card_index_descriptions,
                    cardIndexModel.SaleDesc,
                    cardIndexModel.AccDesc,
                    cardIndexModel.PersonID+""
            );
            db.execSQL(command);
        }
        finally {
            if(db != null)
                db.close();
        }
    }

    public static void insertCardIndexDetail(Context context, CardIndexDetailModel cardIndexDetailModel) throws IOException {
        DBHelper db = null;
          try {
            db = Common.getSaleDataBase(context);
            String command = StringHelper.GenerateMessage(
                    context,
                    R.string.insert_card_index_detail,
                    cardIndexDetailModel.Shortcut,
                    cardIndexDetailModel.RequestCarton+"",
                    cardIndexDetailModel.RequestPackage+"",
                    cardIndexDetailModel.ExistenceCarton+"",
                    cardIndexDetailModel.ExistencePackage+"",
                    cardIndexDetailModel.PersonId+""
            );
             db.execSQL(command);
        }
        finally {
            if(db != null)
                db.close();
        }
    }

    public static void saveChequeDuration(Context context, int personID, int chequeDuration) throws IOException {
        DBHelper db = null;
        try {
            db = Common.getSaleDataBase(context);

            String command = StringHelper.GenerateMessage(
                    context,
                    R.string.update_card_index_cheque_duration,
                    chequeDuration+"",
                    personID+""
            );
            db.execSQL(command);
        }
        finally {

            if(db != null)
                db.close();
        }
    }

    public static void saveDescription(Context context, int personID, String accDesc,String saleDesc,int addressID) throws IOException {
        DBHelper db = null;
        try {
            db = Common.getSaleDataBase(context);

            String command = StringHelper.GenerateMessage(
                    context,
                    R.string.update_card_index_descriptions,
                    accDesc,saleDesc,
                    personID+"",addressID+""
            );
            db.execSQL(command);
        }
        finally {

            if(db != null)
                db.close();
        }
    }

    public static void saveOrderNo(Context context, int personID, int orderNo) throws IOException {
        DBHelper db = null;
        try {
            db = Common.getSaleDataBase(context);

            String command = StringHelper.GenerateMessage(
                    context,
                    R.string.update_card_index_order_no,
                    orderNo+"",
                    personID+""
            );
              db.execSQL(command);
        }
        finally {

            if(db != null)
                db.close();
        }
    }

    public static void saveRequestAmount(Context context, CardIndexParam cardIndexParam) throws Exception {
        saveAmount(context, cardIndexParam, R.string.update_card_index_request);
    }

    public static void saveExistenceAmount(Context context, CardIndexParam cardIndexParam) throws Exception {
        saveAmount(context, cardIndexParam, R.string.update_card_index_existence);
    }

    private static void saveAmount(Context context, CardIndexParam cardIndexParam, int queryId)throws Exception {
        DBHelper db = null;
        try {
            db = Common.getSaleDataBase(context);

            String command = StringHelper.GenerateMessage(
                    context,
                    queryId,
                    cardIndexParam.Shortcut,
                    cardIndexParam.Carton+"",
                    cardIndexParam.Package+"",
                    cardIndexParam.PersonId+""
            );
            db.execSQL(command);
        }
        finally {
            if(db != null)
                db.close();
        }
    }

    public static void clearZeroAmountRecord(Context context) throws IOException {
        DBHelper db = null;
        try {
            db = Common.getSaleDataBase(context);
            String command = StringHelper.GenerateMessage(context, R.string.delete_zero_request_existence);
            db.execSQL(command);
        } finally {

            if (db != null)
                db.close();
        }
    }

    public static CardIndexDto getCardIndexDto(Context context, int personId) throws IOException {
        DBHelper db = null;
        Cursor cursor = null;
        CardIndexDto cardIndexDto;
        try {
            db = Common.getSaleDataBase(context);

            CardIndexModel cardIndexModel = getCardIndexByPersonId(context, personId);
            if(cardIndexModel!=null){
                cardIndexDto = new CardIndexDto();
                cardIndexDto.ChequeDuration = cardIndexModel.ChequeDuration;
                cardIndexDto.NeedDate = cardIndexModel.NeedDate;
                cardIndexDto.AccDesc = cardIndexModel.AccDesc;
                cardIndexDto.SaleDesc = cardIndexModel.SaleDesc;
                cardIndexDto.PersonId=personId;
                cardIndexDto.OrderNo=cardIndexModel.OrderNo;
                cardIndexDto.AddressID=cardIndexModel.AddressID;
                String command = StringHelper.GenerateMessage(context, R.string.card_index_details_by_person_id,
                        personId+ "");

                cursor = db.select(command);

                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    do {
                        String shortcut  = cursor.getString(cursor.getColumnIndex(CardIndexDetailModel.Column.Shortcut));
                        int requestCarton = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Request_Carton));
                        int requestPackage = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Request_Package));
                        int existenceCarton = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Existence_Carton));
                        int existencePackage = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Existence_Package));

                        if(requestCarton != 0) cardIndexDto.addToRequireList(shortcut, requestCarton);
                        if(requestPackage != 0) cardIndexDto.addToRequireList("0" + shortcut, requestPackage);
                        if(existenceCarton != 0) cardIndexDto.addToExistence( shortcut, existenceCarton);
                        if(existencePackage != 0) cardIndexDto.addToExistence("0" +shortcut, existencePackage);
                    } while (cursor.moveToNext());
                }
                return cardIndexDto;
            }
         return null;
        } finally {
            if (db != null)
                db.close();
            if (cursor != null)
                cursor.close();
        }
    }

    public static CardIndexDto isCardIndexEmpty(Context context, int personId) throws IOException {
        DBHelper db = null;
        Cursor cursor = null;
        CardIndexDto cardIndexDto;
        try {
            db = Common.getSaleDataBase(context);

            CardIndexModel cardIndexModel = getCardIndexByPersonId(context, personId);
            if(cardIndexModel!=null){
                cardIndexDto = new CardIndexDto();
                cardIndexDto.ChequeDuration = cardIndexModel.ChequeDuration;
                cardIndexDto.NeedDate = cardIndexModel.NeedDate;
                cardIndexDto.AccDesc = cardIndexModel.AccDesc;
                cardIndexDto.SaleDesc = cardIndexModel.SaleDesc;
                cardIndexDto.PersonId=personId;
                cardIndexDto.OrderNo=cardIndexModel.OrderNo;
                cardIndexDto.AddressID=cardIndexModel.AddressID;
                String command = StringHelper.GenerateMessage(context, R.string.card_index_details_by_person_id,
                        personId+ "");

                cursor = db.select(command);

                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    do {
                        String shortcut  = cursor.getString(cursor.getColumnIndex(CardIndexDetailModel.Column.Shortcut));
                        int requestCarton = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Request_Carton));
                        int requestPackage = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Request_Package));
                        int existenceCarton = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Existence_Carton));
                        int existencePackage = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Existence_Package));

                        if(requestCarton != 0) cardIndexDto.addToRequireList(shortcut, requestCarton);
                        if(requestPackage != 0) cardIndexDto.addToRequireList("0" + shortcut, requestPackage);
                        if(existenceCarton != 0) cardIndexDto.addToExistence( shortcut, existenceCarton);
                        if(existencePackage != 0) cardIndexDto.addToExistence("0" +shortcut, existencePackage);
                    } while (cursor.moveToNext());
                }
                return cardIndexDto;
            }
            return null;
        } finally {
            if (db != null)
                db.close();
            if (cursor != null)
                cursor.close();
        }
    }

//    public static UnvisitedReasonData getReasonDto(Context context, int personId) throws IOException {
//        DBHelper db = null;
//        Cursor cursor = null;
//        try {
//            db = Common.getSaleDataBase(context);
//            String command = StringHelper.GenerateMessage(context, R.string.get_not_sall_reason_id,
//                    personId+"" );
//            cursor = db.select(command);
//            UnvisitedReasonData model =  new UnvisitedReasonData();
//            if(cursor.getCount() > 0) {
//                cursor.moveToFirst();
//                model.NotSallReasonID = cursor.getInt(cursor.getColumnIndex(UnvisitedReasonData.Column.NotSallReasonID));
//                model.PersianDate = cursor.getString(cursor.getColumnIndex(UnvisitedReasonData.Column.PersianDate));
//                model.PersonID = cursor.getInt(cursor.getColumnIndex(UnvisitedReasonData.Column.PersonID));
//            }
//            return model;
//        }
//        finally {
//            if(cursor != null)
//                cursor.close();
//            if(db != null)
//                db.close();
//        }
//    }

//    public static List<UnvisitedReasonData> getReasonDto(Context context, List<Integer> personIds) throws IOException {
//        DBHelper db = null;
//        Cursor cursor = null;
//        List<UnvisitedReasonData> models = new ArrayList<>();
//        try {
//            db = Common.getSaleDataBase(context);
//
//            String command = StringHelper.GenerateMessage(context, R.string.get_not_sall_reason_ids,
//                    Common.GetNumbersCommaFormat(personIds)+"" );
//            cursor = db.select(command);
//
//            if(cursor.getCount() > 0) {
//                cursor.moveToFirst();
//                do {
//                    UnvisitedReasonData model =  new UnvisitedReasonData();
//                    model.NotSallReasonID = cursor.getInt(cursor.getColumnIndex(UnvisitedReasonData.Column.NotSallReasonID));
//                    model.PersianDate = cursor.getString(cursor.getColumnIndex(UnvisitedReasonData.Column.PersianDate));
//                    model.PersonID = cursor.getInt(cursor.getColumnIndex(UnvisitedReasonData.Column.PersonID));
//                    models.add(model);
//                } while (cursor.moveToNext());
//
//            }
//            return models;
//        }
//        finally {
//            if(cursor != null)
//                cursor.close();
//            if(db != null)
//                db.close();
//        }
//    }


    public static void deleteCardIndex(Context context, int PersonID) throws IOException {
        DBHelper db = null;
        try {
            db = Common.getSaleDataBase(context);
            String command = StringHelper.GenerateMessage(
                    context,
                    R.string.delete_card_index,
                    PersonID+""
            );
            db.execSQL(command);
        }
        finally {
            if(db != null)
                db.close();
        }
    }

    public static void deleteCardIndexDetail(Context context,int PersonID) throws IOException {
        DBHelper db = null;
        try {
            db = Common.getSaleDataBase(context);
            String command = StringHelper.GenerateMessage(
                    context,
                    R.string.delete_card_index_detail,
                    PersonID+""
            );
            db.execSQL(command);
        }
        finally {
            if(db != null)
                db.close();
        }
    }

    public static void updateCardIndexErrorMessage(Context context, CardIndexModel cardIndexModel) throws IOException {
        DBHelper db = null;
        try {
            db = Common.getSaleDataBase(context);

            String command = StringHelper.GenerateMessage(
                    context,
                    R.string.update_card_index_error_message,
                    cardIndexModel.ErrorMessage,
                    cardIndexModel.PersonID+""
            );
            db.execSQL(command);
        }
        finally {
            if(db != null)
                db.close();
        }
    }

    public static void insertUnvisitedCustomerReason(Context context, int personId, int reasonId) throws IOException {
        DBHelper db = null;
        try {
            db = Common.getSaleDataBase(context);

            String command = StringHelper.GenerateMessage(
                    context,
                    R.string.insertUnvisitedCustomerReason,
                    personId + "",
                    reasonId + ""
            );
            db.execSQL(command);
        }
        finally {
            if(db != null)
                db.close();
        }
    }

    public static Dictionary getUnvisitedCustomerReasons(Context context) throws IOException {
        DBHelper db = null;
        Cursor cursor = null;
        Dictionary dictionary = new Hashtable();
        try {
            db = Common.getSaleDataBase(context);

            String command = StringHelper.GenerateMessage(
                    context,
                    R.string.getUnvisitedCustomerReasons
            );
            cursor = db.select(command);
            if(cursor.getCount() > 0)
            {
                cursor.moveToFirst();
                Integer personId = cursor.getInt(cursor.getColumnIndex("PersonId"));
                Integer reasonId = cursor.getInt(cursor.getColumnIndex("ReasonId"));
                dictionary.put(personId, reasonId);
            }
        }
        finally {

            if(cursor != null)
                cursor.close();
            if(db != null)
                db.close();
        }
        return dictionary;
    }

    public static List<CardIndexDetailModel> getAllCardIndises(Context context) throws IOException {
        DBHelper db = null;
        Cursor cursor = null;
        List<CardIndexDetailModel> cardIndexDetailModels = new ArrayList<>();

        try {
            db = Common.getSaleDataBase(context);

            String command = StringHelper.GenerateMessage(context, R.string.all_card_index_details);
            cursor = db.select(command);
            CardIndexDetailModel cardIndexDetailModel = null;
            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                do {
                    cardIndexDetailModel = new CardIndexDetailModel();
                    cardIndexDetailModel.Shortcut = cursor.getString(cursor.getColumnIndex(CardIndexDetailModel.Column.Shortcut));
                    cardIndexDetailModel.OrderNo = cursor.getLong(cursor.getColumnIndex(CardIndexDetailModel.Column.Order_NO));
                    cardIndexDetailModel.RequestCarton = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Request_Carton));
                    cardIndexDetailModel.RequestPackage = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Request_Package));
                    cardIndexDetailModel.ExistenceCarton = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Existence_Carton));
                    cardIndexDetailModel.ExistencePackage = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Existence_Package));
                    cardIndexDetailModel.PersonId = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.PersonId));

                    cardIndexDetailModels.add(cardIndexDetailModel);

                } while (cursor.moveToNext());


            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }

        return cardIndexDetailModels;
    }


    public static boolean IsEmptyCardIndex(Context context) throws IOException {
        DBHelper db = null;
        Cursor cursor = null;
        boolean flag=false;
        try {
            db = Common.getSaleDataBase(context);
            String command = StringHelper.GenerateMessage(context, R.string.get_cardIndex_count_record  );
            cursor = db.select(command);
            if(cursor.getCount() > 0) {
                flag=true;
            }
            return flag;
        }
        finally {
            if(cursor != null)
                cursor.close();
            if(db != null)
                db.close();
        }
    }

//    public static boolean isEmptyCardIndex(Context context, int personId) throws IOException {
//        DBHelper db = null;
//        Cursor cursor = null;
//
//            db = Common.getSaleDataBase(context);
//
//            String command = StringHelper.GenerateMessage(context, R.string.is_empty_card_index,
//                    personId+"");
//            cursor = db.select(command);
//            CardIndexModel cardIndexModel = null;
//
//            if(cursor.getCount() > 0) {
//                cursor.moveToFirst();
//                return cursor.getInt(cursor.getColumnIndex("itemCount")) == 0;
//            }
//            return false;
//        }
//        finally {
//            if(cursor != null)
//                cursor.close();
//            if(db != null)
//                db.close();
//        }
//
//    }

    public static long GetRequestAmount(Context context, int personID) throws IOException {
        DBHelper db = null;
        long amount=0;
        try {
            db = Common.getSaleDataBase(context);
            String command = StringHelper.GenerateMessage(context, R.string.get_request_amount ,""+personID );
            amount = Long.parseLong(db.executeScalar(command));
        }
        finally {

            if(db != null)
                db.close();
        }
        return amount;
    }
}