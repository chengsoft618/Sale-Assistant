package com.shoniz.saledistributemobility.infrastructure.wialon;

import android.location.Location;

import com.avitadev.newsimorgh.wialonObjects.globalPacket;
import com.shoniz.saledistributemobility.data.model.location.LocationEntity;
import com.shoniz.saledistributemobility.location.LocationHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class Wialon {
    public static String Url = "http://logo_factor.wialon.ir";
    private static String header = "#L#";
    private static String version = "2.0";
    private static String divider = ";";
    private static String footer = "\r\n";
    private static String NA = "NA";

    public static String makeLoginMessage(long imei) {
        String message = version + divider + imei + divider + NA + divider;
        checkSum crcNew = new checkSum();
        int crc = crcNew.getCode(message);
        String _crc = Integer.toHexString(crc);
        String loginPacket = header + message + _crc + footer;
        return loginPacket;
    }

    public static String makeLocationMessage(Location location, HashMap<String, String> messages) {
        return makeLocationMessage(
                LocationHelper.convertLocationToLocationEntity(location),
                messages
        );
    }

    public static String makeLocationMessage(LocationEntity location, HashMap<String, String> messages) {
        double lt = 0;
        double lg = 0;
        String height = "0";
        String course = "0";
        String speed = "0";

        lt = location.getLatitude();
        lg = location.getlongitude();
        height = (int) location.getAltitude() + "";
        course = (int) location.getBearing() + "";
        speed = (int) (location.getSpeed() * 3.6) + "";

        String lat2, lon2, lat1, lon1;
        if (lt > 0) {
            lat2 = "N";
        } else {
            lat2 = "S";
        }
        if (lg > 0) {
            lon2 = "E";
        } else {
            lon2 = "W";
        }

        int degree = (int) lt;
        double decimal = (10 * lt - 10 * degree) / 10;
        double min = decimal * 60;

        int degree2 = (int) lg;
        double decimal2 = (10 * lg - 10 * degree2) / 10;
        double min2 = decimal2 * 60;

        String degrees;
        String degree2s;
        if (degree < 10) {
            degrees = "0" + degree;
        } else {
            degrees = degree + "";
        }
        if (degree2 < 10) {
            degree2s = "00" + degree2;
        } else if (degree2 > 10 && degree2 < 100) {
            degree2s = "0" + degree2 + "";
        } else {
            degree2s = degree2 + "";
        }
        String newlat = degrees + min;
        String newlong = degree2s + min2;

        lat1 = newlat;
        lon1 = newlong;

        // long lcTime = (new Date()).getTime();
//        try {
//            lcTime = location.getTime();
//        } catch (Exception e) {
//            // TODO: handle exception
//        }

        Date d = new Date(location.getLocationDate());
        Locale l = new Locale("es", "ES");
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy", l);
        final TimeZone utc = TimeZone.getTimeZone("UTC");
        sdf.setTimeZone(utc);
        String date = sdf.format(d);
        sdf = new SimpleDateFormat("HHmmss", l);
        sdf.setTimeZone(utc);
        String time = sdf.format(d);

        int nSatellites = location.getSatellites();

        String sats = nSatellites + "";
        String hdop;
        String inPuts = NA;
        String outPuts = NA;
        String adc = "";
        String ibutton = NA;
        globalPacket packet;
        int hacc = 0;
        try {
            hacc = (int) location.getAccuracy();
        } catch (Exception e) {
            // TODO: handle exception
        }

        hdop = ((int) (hacc / 5)) + "";
        String basicMsg = date + ";" + time + ";" + lat1 + ";" + lat2 + ";"
                + lon1 + ";" +
                lon2 + ";" + speed + ";" + course + ";" + height + ";" + sats
                + ";";

        basicMsg = basicMsg + hdop + ";" + inPuts + ";" + outPuts + ";" + adc
                + ";" + inPuts + ";";

        //Typeparam 1:int , 2:long ,3:string
        String messageInfo = "";
        for (String messageKey : messages.keySet()) {
            messageInfo += messageKey + ":3:" + messages.get(messageKey) + ";";
        }

        String fullMsg = basicMsg + messageInfo;//+orderInfo;
        checkSum crcNew = new checkSum();
        int crc = crcNew.getCode(fullMsg);
        String _crc = Integer.toHexString(crc);
        return "#D#" + fullMsg + _crc + footer;
    }

}
