package com.shoniz.saledistributemobility.view.ordering.detail.printissue;

import android.content.Context;
import android.graphics.Bitmap;

import org.json.JSONException;

public class IssueBitmapFactory {

    public static Bitmap createCustomerBitmap(Context context, String printJson) throws JSONException {
            IssueBitmapsMaker bitmapsMaker = new IssueBitmapsMaker(
                    context, printJson);
            Bitmap body = bitmapsMaker.GetBitmap();
            Bitmap header = bitmapsMaker.printCustomerVersion();
            Bitmap sign = bitmapsMaker.printSignCustomer();
            Bitmap wholeIssue = bitmapsMaker.combineImages(header, body, sign);
            return wholeIssue;
    }

    public static Bitmap createCompanyBitmap(Context context, String printJson) throws JSONException {
        IssueBitmapsMaker bitmapsMaker = new IssueBitmapsMaker(
                context, printJson);
        Bitmap body = bitmapsMaker.GetBitmap();
        Bitmap header = bitmapsMaker.printCompanyVersion();
        Bitmap wholeIssue = bitmapsMaker.combineImages(header, body);
        return wholeIssue;
    }
}
