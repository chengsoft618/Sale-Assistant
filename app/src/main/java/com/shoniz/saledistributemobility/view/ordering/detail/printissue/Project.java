package com.shoniz.saledistributemobility.view.ordering.detail.printissue;

public class Project {
    public static String CenterString(String s, int width) {

        int padSize = width - s.length();
        int padStart = s.length() + padSize / 2;

        s = String.format("%" + padStart + "s", s);
        s = String.format("%-" + width + "s", s);

        return s;
    }

    public static String RightString(String s, int width) {

        int padSize = width - s.length();
        int padStart = s.length() + padSize;

        s = String.format("%" + padStart + "s", s);
        s = String.format("%-" + width + "s", s);

        return s;
    }

}
