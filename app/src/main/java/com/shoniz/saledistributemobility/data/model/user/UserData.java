package com.shoniz.saledistributemobility.data.model.user;

import android.arch.persistence.room.Embedded;

public class UserData {

    @Embedded
    public UserEntity user;

    @Embedded(prefix = "r_")
    public RoleEntity role;

}
