package com.shoniz.saledistributemobility.utility;

import android.content.Context;
import android.util.Base64;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * Created by ferdos.s on 5/18/2017.
 */

public class StringHelper {

    public static String GenerateMessageFromString(Context ctx,String resName, Object... args) {

        for (int i = 0; i < args.length; i++) {
            int id = ctx.getResources().getIdentifier(args[i].toString(), "string", ctx.getPackageName());
            args[i] = ctx.getString(id);
        }

        int textId = ctx.getResources().getIdentifier(resName, "string", ctx.getPackageName());

        return new MessageFormat(ctx.getString(textId)).format(args);
    }

    public static String GenerateMessage(String resName, Object... args) {
        return new MessageFormat(resName, Locale.ENGLISH).format(args);
    }
    public static String GenerateMessage(Context context,int resNameId, Object... args) {
        return new MessageFormat(context.getString(resNameId), Locale.ENGLISH).format(args);
    }

    public static String getBase64FromByte(byte[] image){
         return Base64.encodeToString( image, Base64.DEFAULT);
    }

    public static byte[] getByteFromBase64(String base64){
        return Base64.decode( base64, Base64.DEFAULT);
    }

}
