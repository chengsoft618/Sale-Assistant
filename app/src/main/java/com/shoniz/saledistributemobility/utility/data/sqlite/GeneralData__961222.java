package com.shoniz.saledistributemobility.utility.data.sqlite;

/**
 * Created by ferdos.s on 9/25/2017.
 */

public class GeneralData__961222 {
    /*public static List<GRVModel.CardModel> MakeGeneralRecycleViewList(DBHelper db, String sqlCommand){
        List<GRVModel.CardModel> cardModels = new LinkedList<>();
        try {
            Cursor cursor = db.select(sqlCommand);
            if (cursor.getCount()>0) {
                cursor.moveToFirst();
                do {
                    GRVModel.DetailsCardModel cardModel = new GRVModel.DetailsCardModel(
                        cursor.getInt(cursor.getColumnIndex(GRVModel.OrderColumn.ID)),
                        cursor.getString(cursor.getColumnIndex(GRVModel.OrderColumn.TITLE)),
                        cursor.getString(cursor.getColumnIndex(GRVModel.OrderColumn.DETAIL)),
                        cursor.getInt(cursor.getColumnIndex(GRVModel.OrderColumn.ICON)),
                        false
                    );

                    cardModels.add(cardModel);

                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally {
            if (db != null)
                db.close();
        }
        return cardModels;
    }*/
}
