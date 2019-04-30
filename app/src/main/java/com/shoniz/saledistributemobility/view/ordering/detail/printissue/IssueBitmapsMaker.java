package com.shoniz.saledistributemobility.view.ordering.detail.printissue;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.shoniz.saledistributemobility.R;

public class IssueBitmapsMaker {

    private float extralargeTextSize = 30f;
    private float largeTextSize = 24f;
    private float smallInfoTextSize = 18f;
    private float mediumTextSize = 22f;

    private String factorInfoLine1Right = " %s %-28.28s";
    private String factorInfoLine1Left = " %s %-28.28s";
    private String factorInfoLine2Right = " %s %-28.28s";
    private String factorInfoLine2Left = " %s %-28.28s";

    private String factorRowDetailHeader1 = "%1.1s%3.3s";
    private String factorRowDetailHeader2 = "%-55.55s";
    private String factorRowDetailHeader3 = "%3.3s%1.1S";
    private String factorRowDetailHeader4 = "%-12.12s";

    private String factorRowFooter = " %s %5s %s | %s : %4s  - %s : %4s ";

    private float colum1WidthDetail = 0;
    private float colum2WidthDetail = 0;
    private float colum3WidthDetail = 0;
    private float colum4WidthDetail = 0;

    private String factorRowDiscountAdditions1 = "%4s";
    private String factorRowDiscountAdditions2 = "%-20.20s";
    private String factorRowDiscountAdditions3 = "%8.8s";
    private String factorRowDiscountAdditions4 = "%-20.20s";
    private String factorRowDiscountAdditions5 = "%8.8S";

    private float colum1WidthDiscountAdditions = 0;
    private float colum2WidthDiscountAdditions = 0;
    private float colum3WidthDiscountAdditions = 0;
    private float colum4WidthDiscountAdditions = 0;
    private float colum5WidthDiscountAdditions = 0;

    private String DiscountPercent = " %s %12s   ";

    private int widthFactor = 570;
    private int midFactor = (int) (widthFactor / 2);
    private Typeface tfBMitraYekan;

    private final JSONObject printJson;
    private final Context context;

    public IssueBitmapsMaker(Context context, String str)
            throws JSONException {

        this.printJson = new JSONObject(str);
        this.context = context;
        tfBMitraYekan = Typeface.createFromAsset(context.getAssets(),
                "fonts/BMitra-Yekan.ttf");
    }


    public Bitmap GetBitmap() {
        Bitmap b = null;
        try {
            Bitmap bTitle = printTitle();
            Bitmap bHeader = printHeader(printJson
                    .getString(C.field.BranchName));
            Bitmap bFactorInfo = printFactorInfo(
                    printJson.getString(C.field.VisitorName),
                    printJson.getString(C.field.VisitorMobile),
                    printJson.getString(C.field.Id),
                    printJson.getString(C.field.date));
            Bitmap bCustomerInfo = printCustomerInfo(
                    printJson.getString(C.field.CustomerId) + " - "
                            + printJson.getString(C.field.CustomerName),
                    printJson.getString(C.field.CustomerMobile));
            Bitmap bRowHeader = printRowHeader();

            Bitmap bRows = printRows(printJson.getJSONArray(C.field.Detail));
            Bitmap bRowFooter = printRowFooter(
                    printJson.getString(C.field.Weight),
                    printJson.getString(C.field.CartonCount), printJson
                            .getString(C.field.PacketCount),
                    printJson.getString(C.field.TotalPrice));
            Bitmap b1DiscountHeader = printDiscountHeader1();
            Bitmap b2DiscountHeader = printDiscountHeader2();

            Bitmap bDiscount = printDiscountRows(
                    printJson.getJSONArray(C.field.Discount),
                    printJson.getJSONArray(C.field.Additions));

            Bitmap bTotalDiscountAdditions = printTotalDiscountAdditions(
                    printJson
                            .getString(C.field.TotalDiscount), printJson
                            .getString(C.field.TotalAdditions));

            Bitmap bPayable = printPayable(
                    printJson
                            .getString(C.field.Payable), printJson
                            .getString(C.field.PayableDiscription));
            Bitmap bPayableLetters = printPayableLetters(
                    printJson
                            .getString(C.field.PayableLetters));

            b = combineImages(bTitle, bHeader, bFactorInfo, bCustomerInfo,
                    bRowHeader, bRows, bRowFooter, b1DiscountHeader,
                    b2DiscountHeader, bDiscount, bTotalDiscountAdditions,
                    bPayable, bPayableLetters);

        } catch (Exception e) {
            e.getMessage();
        }

        return b;
    }

    public Bitmap combineImages(Bitmap... bitmaps) {

        int height = 0;
        for (Bitmap bitmap : bitmaps) {
            height += bitmap.getHeight();
        }
        Bitmap image = Bitmap.createBitmap(widthFactor, height,
                Bitmap.Config.ARGB_8888);
        Canvas comboImage = new Canvas(image);
        float indicator = 0;
        for (Bitmap bitmap : bitmaps) {
            comboImage.drawBitmap(bitmap, 0f, indicator, null);
            indicator += bitmap.getHeight();
        }

        return image;
    }

    private Bitmap printTitle() throws JSONException {
        int indicator = 0;
        Paint paintHeader = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHeader.setColor(Color.BLACK);
        paintHeader.setTypeface(tfBMitraYekan);
        paintHeader.setTextAlign(Paint.Align.CENTER);
        paintHeader.setTextSize(largeTextSize);

        float baseline = (int) (-paintHeader.ascent() + 0.5f);
        int height = (int) (baseline + paintHeader.descent() + 0.5f);

        float baselineHeader = (int) (-paintHeader.ascent() + 0.5f);

        int heightBitmap = height + 6;
        Bitmap image = Bitmap.createBitmap(widthFactor, heightBitmap,
                Bitmap.Config.ARGB_8888);
        image.eraseColor(Color.WHITE);
        Canvas canvas = new Canvas(image);
        paintHeader.setStrokeWidth(3);
        canvas.drawLine(0, 0, widthFactor, 0, paintHeader);
        indicator = (int) baselineHeader + 3;
        String slogan =printJson.getString(C.field.Slogan);
        canvas.drawText(toPersianNumber(slogan), midFactor,
                indicator,
                paintHeader);
        canvas.drawLine(0, heightBitmap, widthFactor, heightBitmap,
                paintHeader);
        canvas.drawLine(0, 0, 0, heightBitmap, paintHeader);
        canvas.drawLine(570, 0, 570, heightBitmap, paintHeader);

        return image;

    }

    private Bitmap printHeader(String header) {
        int indicator = 0;

        Paint paintHeader = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHeader.setColor(Color.BLACK);
        paintHeader.setTypeface(tfBMitraYekan);
        paintHeader.setTextAlign(Paint.Align.CENTER);
        paintHeader.setTextSize(largeTextSize);

        float baseline = (int) (-paintHeader.ascent() + 0.5f);
        int height = (int) (baseline + paintHeader.descent() + 0.5f);

        float baselineHeader = (int) (-paintHeader.ascent() + 0.5f);

        Bitmap logo = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.shoniz_logo_factor);
        int logo_heghit = logo.getHeight();
        int heightBitmap = logo_heghit + height + 6;
        Bitmap image = Bitmap.createBitmap(widthFactor, heightBitmap,
                Bitmap.Config.ARGB_8888);
        image.eraseColor(Color.WHITE);
        Canvas canvas = new Canvas(image);
        paintHeader.setStrokeWidth(3);

        indicator = (int) (indicator + 3);
        canvas.drawLine(0, 0, widthFactor, 0, paintHeader);
        canvas.drawBitmap(logo, midFactor - (logo.getWidth() / 2
        ), indicator, paintHeader);
        indicator = (int) (logo_heghit + baselineHeader + 3);
        canvas.drawText(toPersianNumber(header), midFactor,
                indicator,
                paintHeader);
        canvas.drawLine(0, heightBitmap, widthFactor, heightBitmap, paintHeader);
        canvas.drawLine(0, 0, 0, heightBitmap, paintHeader);
        canvas.drawLine(570, 0, 570, heightBitmap, paintHeader);

        return image;
    }

    private Bitmap printFactorInfo(String visitorName, String visitorMobile,
                                   String id, String date) {
        int indicator = 0;

        Paint paintHeader = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHeader.setColor(Color.BLACK);
        paintHeader.setTypeface(tfBMitraYekan);

        paintHeader.setTextSize(largeTextSize);

        float baseline = (int) (-paintHeader.ascent() + 0.5f);
        int height = (int) (baseline + paintHeader.descent() + 0.5f);

        int heightBitmap = height * 2 + 24;
        Bitmap image = Bitmap.createBitmap(widthFactor, heightBitmap,
                Bitmap.Config.ARGB_8888);
        image.eraseColor(Color.WHITE);
        Canvas canvas = new Canvas(image);
        paintHeader.setStrokeWidth(3);

        indicator = (int) (baseline + 12);
        canvas.drawLine(0, 0, widthFactor, 0, paintHeader);

        paintHeader.setTextAlign(Paint.Align.RIGHT);
        String str = String.format(factorInfoLine1Right, "شماره : ", id);
        canvas.drawText(toPersianNumber(str), widthFactor,
                indicator,
                paintHeader);

        paintHeader.setTextAlign(Paint.Align.RIGHT);
        str = String.format(factorInfoLine1Left, "فروشنده : ", visitorName);
        canvas.drawText(toPersianNumber(str), midFactor,
                indicator,
                paintHeader);

        indicator = (int) (indicator + baseline + 12);
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        str = String.format(factorInfoLine2Right, "تاریخ :", date);
        canvas.drawText(toPersianNumber(str), widthFactor,
                indicator,
                paintHeader);
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        str = String.format(factorInfoLine2Left, "شماره تماس :", visitorMobile);
        canvas.drawText(toPersianNumber(str), midFactor,
                indicator,
                paintHeader);
        canvas.drawLine(0, heightBitmap, widthFactor, heightBitmap, paintHeader);
        canvas.drawLine(0, 0, 0, heightBitmap, paintHeader);
        canvas.drawLine(570, 0, 570, heightBitmap, paintHeader);

        return image;
    }

    private Bitmap printCustomerInfo(String customerName, String CustomerPhone) {
        int indicator = 0;

        Paint paintHeader = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHeader.setColor(Color.BLACK);
        paintHeader.setTypeface(tfBMitraYekan);

        paintHeader.setTextSize(smallInfoTextSize);

        float baseline = (int) (-paintHeader.ascent() + 0.5f);
        int height = (int) (baseline + paintHeader.descent() + 0.5f);

        int heightBitmap = height + 12;
        Bitmap image = Bitmap.createBitmap(widthFactor, heightBitmap,
                Bitmap.Config.ARGB_8888);
        image.eraseColor(Color.WHITE);
        Canvas canvas = new Canvas(image);
        paintHeader.setStrokeWidth(3);

        indicator = (int) (baseline + 6);
        canvas.drawLine(0, 0, widthFactor, 0, paintHeader);

        paintHeader.setTextAlign(Paint.Align.RIGHT);
        String str = String.format(factorInfoLine1Right, "نام مشتری : ",
                customerName);
        canvas.drawText(toPersianNumber(str), widthFactor,
                indicator,
                paintHeader);

        paintHeader.setTextAlign(Paint.Align.RIGHT);
        str = String
                .format(factorInfoLine1Left, "شماره تماس : ", CustomerPhone);
        canvas.drawText(toPersianNumber(str), midFactor,
                indicator,
                paintHeader);

        canvas.drawLine(0, heightBitmap, widthFactor, heightBitmap, paintHeader);
        canvas.drawLine(0, 0, 0, heightBitmap, paintHeader);
        canvas.drawLine(570, 0, 570, heightBitmap, paintHeader);

        return image;
    }

    private Bitmap printRowHeader() {
        int indicator = 0;
        int indicatorWidth = widthFactor - 3;

        Paint paintHeader = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHeader.setColor(Color.BLACK);
        paintHeader.setTypeface(tfBMitraYekan);

        paintHeader.setTextSize(mediumTextSize);

        float baseline = (int) (-paintHeader.ascent() + 0.5f);
        int height = (int) (baseline + paintHeader.descent() + 0.5f);

        int heightBitmap = height + 12;
        Bitmap image = Bitmap.createBitmap(widthFactor, heightBitmap,
                Bitmap.Config.ARGB_8888);
        image.eraseColor(Color.WHITE);
        Canvas canvas = new Canvas(image);
        paintHeader.setStrokeWidth(1);

        indicator = (int) (baseline + 6);
        canvas.drawLine(0, 0, widthFactor, 0, paintHeader);
        // Column 1
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        String str = Project.CenterString("ردیف", 4);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);
        colum1WidthDetail = paintHeader.measureText(str) + 5;
        indicatorWidth -= (int) (colum1WidthDetail + 3);
        canvas.drawLine(indicatorWidth, 0, indicatorWidth, heightBitmap,
                paintHeader);
        // Column 2
        indicatorWidth -= (int) (3);
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        str = Project.CenterString("نام کـــــالا", 55);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);
        colum2WidthDetail = paintHeader.measureText(str);
        indicatorWidth -= (int) (colum2WidthDetail + 3);
        canvas.drawLine(indicatorWidth, 0, indicatorWidth, heightBitmap,
                paintHeader);
        // Column 3
        indicatorWidth -= (int) (3);
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        str = Project.CenterString("تعداد", 9);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);
        colum3WidthDetail = paintHeader.measureText(str);
        indicatorWidth -= (int) (colum3WidthDetail + 3);
        canvas.drawLine(indicatorWidth, 0, indicatorWidth, heightBitmap,
                paintHeader);
        // Column 4
        indicatorWidth -= (int) (3);
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        str = Project.CenterString("مبلغ کل", 20);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);
        colum4WidthDetail = paintHeader.measureText(str);
        canvas.drawLine(0, heightBitmap, widthFactor, heightBitmap, paintHeader);
        canvas.drawLine(0, 0, 0, heightBitmap, paintHeader);
        canvas.drawLine(570, 0, 570, heightBitmap, paintHeader);

        return image;
    }

    private Bitmap printRow(String row, String reward, String unit,
                            String productName, String qty, String sumPrice) {
        int indicator = 0;
        int indicatorWidth = widthFactor - 3;

        Paint paintHeader = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHeader.setColor(Color.BLACK);
        paintHeader.setTypeface(tfBMitraYekan);

        paintHeader.setTextSize(mediumTextSize);

        float baseline = (int) (-paintHeader.ascent() + 0.5f);
        int height = (int) (baseline + paintHeader.descent() + 0.5f);

        int heightBitmap = height + 12;
        Bitmap image = Bitmap.createBitmap(widthFactor, heightBitmap,
                Bitmap.Config.ARGB_8888);
        image.eraseColor(Color.WHITE);
        Canvas canvas = new Canvas(image);
        paintHeader.setStrokeWidth(1);

        indicator = (int) (baseline + 6);
        canvas.drawLine(0, 0, widthFactor, 0, paintHeader);
        // Column 1
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        String str = String
                .format(factorRowDetailHeader1, reward, row);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);

        indicatorWidth -= (int) (colum1WidthDetail + 3);
        canvas.drawLine(indicatorWidth, 0, indicatorWidth, heightBitmap,
                paintHeader);
        // Column 2
        indicatorWidth -= (int) (3);
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        str = String
                .format(factorRowDetailHeader2, productName);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);
        indicatorWidth -= (int) (colum2WidthDetail + 3);
        canvas.drawLine(indicatorWidth, 0, indicatorWidth, heightBitmap,
                paintHeader);
        // Column 3
        indicatorWidth -= (int) (3);
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        str = String
                .format(factorRowDetailHeader3, qty, unit);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);

        indicatorWidth -= (int) (colum3WidthDetail + 3);
        canvas.drawLine(indicatorWidth, 0, indicatorWidth, heightBitmap,
                paintHeader);
        // Column 4
        indicatorWidth = (int) (colum4WidthDetail + 15);
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        str = Project.RightString(sumPrice, 20);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);

        canvas.drawLine(0, heightBitmap, widthFactor, heightBitmap, paintHeader);
        canvas.drawLine(0, 0, 0, heightBitmap, paintHeader);
        canvas.drawLine(570, 0, 570, heightBitmap, paintHeader);

        return image;
    }


    private Bitmap printRows(JSONArray arr) {
        Bitmap image = null;
        try {

            int height = 0;
            List<Bitmap> bitmaps = new LinkedList<Bitmap>();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject jr = (JSONObject) arr.get(i);
                Bitmap bitmap = printRow(jr.getString(C.field.Row),
                        jr.getString(C.field.Reward),
                        jr.getString(C.field.Unit),
                        jr.getString(C.field.ProductName),
                        jr.getString(C.field.QTY),
                        jr.getString(C.field.SumPrice));
                bitmaps.add(bitmap);
                height += bitmap.getHeight();
            }
            image = Bitmap.createBitmap(widthFactor, height,
                    Bitmap.Config.ARGB_8888);
            Canvas comboImage = new Canvas(image);
            float indicator = 0;
            for (Bitmap bitmap : bitmaps) {
                comboImage.drawBitmap(bitmap, 0f, indicator, null);
                indicator += bitmap.getHeight();
            }

        } catch (Exception e) {
            e.getMessage();
        }

        return image;
    }

    private Bitmap printRowFooter(String weight, String cartonCount,
                                  String packetCount, String totalPrice) {
        int indicator = 0;
        int indicatorWidth = widthFactor - 3;

        Paint paintHeader = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHeader.setColor(Color.BLACK);
        paintHeader.setTypeface(tfBMitraYekan);

        paintHeader.setTextSize(mediumTextSize);

        float baseline = (int) (-paintHeader.ascent() + 0.5f);
        int height = (int) (baseline + paintHeader.descent() + 0.5f);

        int heightBitmap = height + 16;
        Bitmap image = Bitmap.createBitmap(widthFactor, heightBitmap,
                Bitmap.Config.ARGB_8888);
        image.eraseColor(Color.WHITE);
        Canvas canvas = new Canvas(image);
        paintHeader.setStrokeWidth(1);

        indicator = (int) (baseline + 6);
        canvas.drawLine(0, 0, widthFactor, 0, paintHeader);
        // Column 1
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        String str = String
                .format(factorRowFooter, " وزن ", weight, " کیلوگرم ", "کارتن",
                        cartonCount, "بسته", packetCount);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);

        indicatorWidth -= (int) (colum1WidthDetail
                + colum2WidthDetail
                + colum3WidthDetail + 3 * 5);

        canvas.drawLine(indicatorWidth, 0, indicatorWidth, heightBitmap - 4,
                paintHeader);

        // Column 4
        indicatorWidth = (int) (colum4WidthDetail + 15);
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        str = Project.RightString(totalPrice, 20);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);

        canvas.drawLine(0, heightBitmap - 4, widthFactor, heightBitmap - 4,
                paintHeader);
        canvas.drawLine(0, 0, 0, heightBitmap - 4, paintHeader);
        canvas.drawLine(570, 0, 570, heightBitmap - 4, paintHeader);

        return image;
    }

    private Bitmap printDiscountHeader2() {
        int indicator = 0;
        int indicatorWidth = widthFactor - 3;

        Paint paintHeader = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHeader.setColor(Color.BLACK);
        paintHeader.setTypeface(tfBMitraYekan);

        paintHeader.setTextSize(mediumTextSize);

        float baseline = (int) (-paintHeader.ascent() + 0.5f);
        int height = (int) (baseline + paintHeader.descent() + 0.5f);

        int heightBitmap = height + 12;
        Bitmap image = Bitmap.createBitmap(widthFactor, heightBitmap,
                Bitmap.Config.ARGB_8888);
        image.eraseColor(Color.WHITE);
        Canvas canvas = new Canvas(image);
        paintHeader.setStrokeWidth(1);

        indicator = (int) (baseline + 6);
        canvas.drawLine(0, 0, widthFactor, 0, paintHeader);
        // Column 1
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        String str = Project.CenterString("ردیف", 4);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);
        colum1WidthDiscountAdditions = paintHeader.measureText(str) + 5;
        indicatorWidth -= (int) (colum1WidthDiscountAdditions + 3);
        canvas.drawLine(indicatorWidth, 0, indicatorWidth, heightBitmap,
                paintHeader);
        // Column 2
        indicatorWidth -= (int) (3);
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        str = Project.CenterString("عنوان", 27);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);
        colum2WidthDiscountAdditions = paintHeader.measureText(str);
        indicatorWidth -= (int) (colum2WidthDiscountAdditions + 3);
        canvas.drawLine(indicatorWidth, 0, indicatorWidth, heightBitmap,
                paintHeader);
        // Column 3
        indicatorWidth -= (int) (3);
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        str = Project.CenterString("مبلغ", 17);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);

        indicatorWidth = (int) (widthFactor - 3 - colum3WidthDiscountAdditions - 5);
        paintHeader.setStrokeWidth(4);
        canvas.drawLine(indicatorWidth, 0, indicatorWidth, heightBitmap,
                paintHeader);
        paintHeader.setStrokeWidth(1);
        // Column 4
        indicatorWidth -= (int) (3);
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        str = Project.CenterString("عنوان", 27);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);
        colum4WidthDiscountAdditions = paintHeader.measureText(str);
        indicatorWidth -= (int) (colum4WidthDiscountAdditions + 3);
        canvas.drawLine(indicatorWidth, 0, indicatorWidth, heightBitmap,
                paintHeader);
        // Column 5
        indicatorWidth -= (int) (3);
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        str = Project.CenterString("مبلغ", 17);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);
        colum5WidthDiscountAdditions = paintHeader.measureText(str);
        canvas.drawLine(0, heightBitmap, widthFactor, heightBitmap, paintHeader);
        canvas.drawLine(0, 0, 0, heightBitmap, paintHeader);
        canvas.drawLine(570, 0, 570, heightBitmap, paintHeader);

        return image;
    }

    private Bitmap printDiscountHeader1() {
        int indicator = 0;
        int indicatorWidth = widthFactor - 3;

        Paint paintHeader = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHeader.setColor(Color.BLACK);
        paintHeader.setTypeface(tfBMitraYekan);

        paintHeader.setTextSize(mediumTextSize);

        float baseline = (int) (-paintHeader.ascent() + 0.5f);
        int height = (int) (baseline + paintHeader.descent() + 0.5f);

        int heightBitmap = height + 12;
        Bitmap image = Bitmap.createBitmap(widthFactor, heightBitmap,
                Bitmap.Config.ARGB_8888);
        image.eraseColor(Color.WHITE);
        Canvas canvas = new Canvas(image);
        paintHeader.setStrokeWidth(1);

        indicator = (int) (baseline + 6);
        canvas.drawLine(0, 0, widthFactor, 0, paintHeader);
        // Column 1
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        String str = Project.CenterString("کسورات", 52);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);
        colum3WidthDiscountAdditions = paintHeader.measureText(str) + 5;
        indicatorWidth -= (int) (colum3WidthDiscountAdditions + 5);
        paintHeader.setStrokeWidth(4);
        canvas.drawLine(indicatorWidth, 0, indicatorWidth, heightBitmap,
                paintHeader);
        paintHeader.setStrokeWidth(1);

        // Column 2
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        str = Project.CenterString("اضافات", 46);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);

        canvas.drawLine(0, heightBitmap, widthFactor, heightBitmap, paintHeader);
        canvas.drawLine(0, 0, 0, heightBitmap, paintHeader);
        canvas.drawLine(570, 0, 570, heightBitmap, paintHeader);
        return image;
    }

    private Bitmap printDiscountRows(JSONArray arrd, JSONArray arra) {
        Bitmap image = null;
        try {

            int height = 0, len = 0;
            if (arrd.length() > arra.length())
                len = arrd.length();
            else
                len = arra.length();
            List<Bitmap> bitmaps = new LinkedList<Bitmap>();
            for (int i = 0; i < len; i++) {
                String dDiscountName = "";
                String dRow = "";
                String dSumPrice = "";

                String aAdditionsName = "";
                String aSumPrice = "";
                if (!arrd.isNull(i))
                {
                    JSONObject jrd = (JSONObject) arrd.get(i);
                    if (jrd.has(C.field.DiscountName))
                    {
                        dRow = jrd.getString(C.field.Row);
                        dDiscountName = jrd.getString(C.field.DiscountName);
                        dSumPrice = jrd.getString(C.field.SumPrice);
                    }
                }
                if (!arra.isNull(i))
                {
                    JSONObject jra = (JSONObject) arra.get(i);
                    if (jra.has(C.field.AdditionsName))
                    {
                        aAdditionsName = jra.getString(C.field.AdditionsName)
                                .replace("ماليات بر", "");
                        aSumPrice = jra.getString(C.field.SumPrice);
                    }
                }

                Bitmap bitmap = printDiscountAdditionsRow(
                        dRow,
                        dDiscountName,
                        dSumPrice, aAdditionsName, aSumPrice);
                bitmaps.add(bitmap);
                height += bitmap.getHeight();
            }
            image = Bitmap.createBitmap(widthFactor, height,
                    Bitmap.Config.ARGB_8888);
            Canvas comboImage = new Canvas(image);
            float indicator = 0;
            for (Bitmap bitmap : bitmaps) {
                comboImage.drawBitmap(bitmap, 0f, indicator, null);
                indicator += bitmap.getHeight();
            }

        } catch (Exception e) {
            e.getMessage();
        }

        return image;
    }

    private Bitmap printDiscountAdditionsRow(String row, String discountName,
                                             String sumPriceDiscount, String additionsName,
                                             String sumPriceAdditions) {
        int indicator = 0;
        int indicatorWidth = widthFactor - 3;

        Paint paintHeader = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHeader.setColor(Color.BLACK);
        paintHeader.setTypeface(tfBMitraYekan);

        paintHeader.setTextSize(mediumTextSize);

        float baseline = (int) (-paintHeader.ascent() + 0.5f);
        int height = (int) (baseline + paintHeader.descent() + 0.5f);

        int heightBitmap = height + 12;
        Bitmap image = Bitmap.createBitmap(widthFactor, heightBitmap,
                Bitmap.Config.ARGB_8888);
        image.eraseColor(Color.WHITE);
        Canvas canvas = new Canvas(image);
        paintHeader.setStrokeWidth(1);

        indicator = (int) (baseline + 6);
        canvas.drawLine(0, 0, widthFactor, 0, paintHeader);
        // Column 1
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        String str = String.format(factorRowDiscountAdditions1, row);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);

        indicatorWidth -= (int) (colum1WidthDiscountAdditions + 3);
        canvas.drawLine(indicatorWidth, 0, indicatorWidth, heightBitmap,
                paintHeader);
        // Column 2
        indicatorWidth -= (int) (3);
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        str = String.format(factorRowDiscountAdditions2, discountName);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);

        indicatorWidth -= (int) (colum2WidthDiscountAdditions + 3);
        canvas.drawLine(indicatorWidth, 0, indicatorWidth, heightBitmap,
                paintHeader);
        // Column 3
        indicatorWidth = (int) (widthFactor - 3 - colum3WidthDiscountAdditions - 5);
        paintHeader.setTextAlign(Paint.Align.LEFT);
        str = String.format(factorRowDiscountAdditions3, sumPriceDiscount);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);
        paintHeader.setStrokeWidth(4);
        canvas.drawLine(indicatorWidth, 0, indicatorWidth, heightBitmap,
                paintHeader);
        paintHeader.setStrokeWidth(1);
        // Column 4
        indicatorWidth -= (int) (3);
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        str = String.format(factorRowDiscountAdditions4, additionsName);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);

        indicatorWidth -= (int) (colum4WidthDiscountAdditions + 3);
        canvas.drawLine(indicatorWidth, 0, indicatorWidth, heightBitmap,
                paintHeader);
        // Column 5
        indicatorWidth -= (int) (3);
        paintHeader.setTextAlign(Paint.Align.LEFT);
        str = String.format(factorRowDiscountAdditions5, sumPriceAdditions);
        indicatorWidth -= (int) (colum5WidthDiscountAdditions + 3);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);

        canvas.drawLine(0, heightBitmap, widthFactor, heightBitmap, paintHeader);
        canvas.drawLine(0, 0, 0, heightBitmap, paintHeader);
        canvas.drawLine(570, 0, 570, heightBitmap, paintHeader);

        return image;
    }

    private Bitmap printTotalDiscountAdditions(String totalDiscount,
                                               String totalAdditions) {
        int indicator = 0;
        int indicatorWidth = widthFactor - 3;

        Paint paintHeader = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHeader.setColor(Color.BLACK);
        paintHeader.setTypeface(tfBMitraYekan);

        paintHeader.setTextSize(mediumTextSize);

        float baseline = (int) (-paintHeader.ascent() + 0.5f);
        int height = (int) (baseline + paintHeader.descent() + 0.5f);

        int heightBitmap = height + 16;
        Bitmap image = Bitmap.createBitmap(widthFactor, heightBitmap,
                Bitmap.Config.ARGB_8888);
        image.eraseColor(Color.WHITE);
        Canvas canvas = new Canvas(image);
        paintHeader.setStrokeWidth(1);

        indicator = (int) (baseline + 6);
        canvas.drawLine(0, 0, widthFactor, 0, paintHeader);
        // Column 1
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        String str = Project.CenterString("جمع کسورات : " + totalDiscount, 52);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);

        indicatorWidth -= (int) (colum3WidthDiscountAdditions + 5);
        paintHeader.setStrokeWidth(4);
        canvas.drawLine(indicatorWidth, 0, indicatorWidth, heightBitmap - 4,
                paintHeader);
        paintHeader.setStrokeWidth(1);

        // Column 2**
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        str = Project.CenterString("جمع اضافات : " + totalAdditions, 46);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);

        canvas.drawLine(0, heightBitmap - 4, widthFactor, heightBitmap - 4,
                paintHeader);
        canvas.drawLine(0, 0, 0, heightBitmap - 4, paintHeader);
        canvas.drawLine(570, 0, 570, heightBitmap - 4, paintHeader);
        return image;
    }

    private Bitmap printPayable(String payable, String PayableDiscription) {

        int indicator = 0;
        int indicatorWidth = widthFactor - 3;

        Paint paintHeader = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHeader.setColor(Color.BLACK);
        paintHeader.setTypeface(tfBMitraYekan);

        paintHeader.setTextSize(mediumTextSize);

        float baseline = (int) (-paintHeader.ascent() + 0.5f);
        int height = (int) (baseline + paintHeader.descent() + 0.5f);

        int heightBitmap = height + 16;
        Bitmap image = Bitmap.createBitmap(widthFactor, heightBitmap,
                Bitmap.Config.ARGB_8888);
        image.eraseColor(Color.WHITE);
        Canvas canvas = new Canvas(image);
        paintHeader.setStrokeWidth(1);

        indicator = (int) (baseline + 6);
        canvas.drawLine(0, 0, widthFactor, 0, paintHeader);
        // Column 1
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        String str = String
                .format(DiscountPercent, PayableDiscription, "");
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);

        indicatorWidth -= (int) (colum1WidthDetail
                + colum2WidthDetail
                + colum3WidthDetail + 3 * 5);

        canvas.drawLine(indicatorWidth, 0, indicatorWidth, heightBitmap - 4,
                paintHeader);

        // Column 4

        indicatorWidth = (int) (colum4WidthDetail + 15);
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        str = Project.RightString(payable, 20);
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);

        canvas.drawLine(0, heightBitmap - 4, widthFactor, heightBitmap - 4,
                paintHeader);
        canvas.drawLine(0, 0, 0, heightBitmap - 4, paintHeader);
        canvas.drawLine(570, 0, 570, heightBitmap - 4, paintHeader);

        return image;
    }

    private Bitmap printPayableLetters(String payableLetters) {
        int indicator = 0;
        int indicatorWidth = widthFactor - 3;

        Paint paintHeader = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHeader.setColor(Color.WHITE);
        paintHeader.setTypeface(tfBMitraYekan);

        paintHeader.setTextSize(largeTextSize);

        float baseline = (int) (-paintHeader.ascent() + 0.5f);
        int height = (int) (baseline + paintHeader.descent() + 0.5f);

        int heightBitmap = height + 16;
        Bitmap image = Bitmap.createBitmap(widthFactor, heightBitmap,
                Bitmap.Config.ARGB_8888);
        image.eraseColor(Color.BLACK);
        Canvas canvas = new Canvas(image);
        paintHeader.setStrokeWidth(1);

        indicator = (int) (baseline + 6);
        canvas.drawLine(0, 0, widthFactor, 0, paintHeader);
        // Column 1
        paintHeader.setTextAlign(Paint.Align.RIGHT);
        String str = String
                .format(DiscountPercent, payableLetters, "");
        canvas.drawText(toPersianNumber(str), indicatorWidth,
                indicator,
                paintHeader);

        indicatorWidth -= (int) (colum1WidthDetail
                + colum2WidthDetail
                + colum3WidthDetail + 3 * 5);
        return image;
    }

    public Bitmap printSignCustomer() {

        int indicator = 0;

        Paint paintHeader = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHeader.setColor(Color.BLACK);
        paintHeader.setTypeface(tfBMitraYekan);

        paintHeader.setTextSize(extralargeTextSize);

        float baseline = (int) (-paintHeader.ascent() + 0.5f);
        int height = (int) (baseline + paintHeader.descent() + 0.5f);

        int heightBitmap = height + 16;
        Bitmap image = Bitmap.createBitmap(widthFactor, heightBitmap,
                Bitmap.Config.ARGB_8888);
        image.eraseColor(Color.WHITE);
        Canvas canvas = new Canvas(image);
        paintHeader.setStrokeWidth(1);

        indicator = (int) (baseline + 6);

        // Column 1
        paintHeader.setTextAlign(Paint.Align.CENTER);

        canvas.drawText(toPersianNumber("امضای خریدار"), midFactor,
                indicator,
                paintHeader);

        return image;
    }

    public Bitmap printCustomerVersion() {
        int indicator = 0;

        Paint paintHeader = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHeader.setColor(Color.BLACK);
        paintHeader.setTypeface(tfBMitraYekan);

        paintHeader.setTextSize(extralargeTextSize);

        float baseline = (int) (-paintHeader.ascent() + 0.5f);
        int height = (int) (baseline + paintHeader.descent() + 0.5f);

        int heightBitmap = height + 16;
        Bitmap image = Bitmap.createBitmap(widthFactor, heightBitmap,
                Bitmap.Config.ARGB_8888);
        image.eraseColor(Color.WHITE);
        Canvas canvas = new Canvas(image);
        paintHeader.setStrokeWidth(1);

        indicator = (int) (baseline + 6);

        // Column 1
        paintHeader.setTextAlign(Paint.Align.CENTER);

        canvas.drawText(toPersianNumber("نسخه مشتری"), midFactor,
                indicator,
                paintHeader);

        return image;
    }

    public Bitmap printCompanyVersion() {
        int indicator = 0;

        Paint paintHeader = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHeader.setColor(Color.BLACK);
        paintHeader.setTypeface(tfBMitraYekan);

        paintHeader.setTextSize(extralargeTextSize);

        float baseline = (int) (-paintHeader.ascent() + 0.5f);
        int height = (int) (baseline + paintHeader.descent() + 0.5f);

        int heightBitmap = height + 16;
        Bitmap image = Bitmap.createBitmap(widthFactor, heightBitmap,
                Bitmap.Config.ARGB_8888);
        image.eraseColor(Color.WHITE);
        Canvas canvas = new Canvas(image);
        paintHeader.setStrokeWidth(1);

        indicator = (int) (baseline + 6);

        // Column 1
        paintHeader.setTextAlign(Paint.Align.CENTER);

        canvas.drawText(toPersianNumber("نسخه شرکت شونیز"), midFactor,
                indicator,
                paintHeader);

        return image;
    }

    private String toPersianNumber(String input) {
        String[] persian = { "۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹" };

        for (int j = 0; j < persian.length; j++)
            input = input.replace("" + j, persian[j]);

        return input;
    }

}
