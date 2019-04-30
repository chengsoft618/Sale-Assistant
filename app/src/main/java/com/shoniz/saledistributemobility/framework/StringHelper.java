package com.shoniz.saledistributemobility.framework;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Locale;

public class StringHelper {
    public static String generateRecyclerItem(String subject, String value, String postfix){
        if(value==null || value.equals(""))
            return "";
        String str = "";
        if(subject != null && !subject.equals(""))
            str = subject + " ";
        str += value;
        if(postfix != null && !postfix.equals(""))
            str += " " + postfix;
        return str;
    }
    public static String generateRecyclerItem(String subject, long value, String postfix){
        return generateRecyclerItem(subject, String.valueOf(value), postfix);
    }
    public static String generateRecyclerItem(String subject, float value, String postfix){
        return generateRecyclerItem(subject, String.valueOf(value), postfix);
    }

    public static String getDateFromLong(long value){
        if (value==0)
            return "";

        else{
            String val= ""+ value;
            val= val.substring(0,14);
            return new StringBuilder(val)
                    .insert(12 , ":")
                    .insert(10 , ":")
                    .insert(8," ")
                    .insert(6,"/")
                    .insert(4,"/")
                    .toString();
        }
    }

    public static String getCurrencyFormat(long price){
        return String.format(Locale.US,"%,d",price);
    }

    public static < T > String serialize(T obj){
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static < T > T deserialize(String obj){
        Gson gson = new Gson();
        Type token = new TypeToken<T>(){}.getType();
        return gson.fromJson(obj, token);
    }
}
