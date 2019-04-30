package com.shoniz.saledistributemobility.location.data;

/**
 * Created by 921235 on 3/29/2018.
 */

public final class LocationQuery {
    public static final String AllLocations = "SELECT * FROM Location";
    public static final String LastLocation = "SELECT * FROM Location ORDER BY LocationDate desc ";
}
