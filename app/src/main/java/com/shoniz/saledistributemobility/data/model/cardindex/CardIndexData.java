package com.shoniz.saledistributemobility.data.model.cardindex;

import android.arch.persistence.room.Embedded;

import com.shoniz.saledistributemobility.data.model.customer.CustomerBasicEntity;
import com.shoniz.saledistributemobility.view.entity.CardIndexEntity;

public class CardIndexData {

    @Embedded(prefix = "c_")
    public CustomerBasicEntity customerBasicEntity;

    @Embedded
    public CardIndexEntity cardIndexEntity;
}
