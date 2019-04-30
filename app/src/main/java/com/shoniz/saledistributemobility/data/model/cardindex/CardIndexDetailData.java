package com.shoniz.saledistributemobility.data.model.cardindex;

import android.arch.persistence.room.Embedded;

import com.shoniz.saledistributemobility.view.entity.CardIndexDetailEntity;
import com.shoniz.saledistributemobility.data.model.coding.CodingEntity;

public class CardIndexDetailData implements Comparable<CardIndexDetailData> {

    @Embedded(prefix = "c_")
    public CodingEntity codingEntity;

    @Embedded
    public CardIndexDetailEntity cardIndexDetailEntity;

    @Override
    public int compareTo(CardIndexDetailData cardIndexDetailData) {
        if (cardIndexDetailData == null
                || cardIndexDetailData.cardIndexDetailEntity != null
                || cardIndexDetailData.cardIndexDetailEntity.Shortcut.isEmpty()
                || cardIndexDetailEntity == null
                || cardIndexDetailEntity.Shortcut.isEmpty()
                )
            return 0;
        if (Integer.parseInt(cardIndexDetailData.cardIndexDetailEntity.Shortcut) ==
                Integer.parseInt(cardIndexDetailEntity.Shortcut)) return 0;
        if (Integer.parseInt(cardIndexDetailEntity.Shortcut) >
                Integer.parseInt(cardIndexDetailData.cardIndexDetailEntity.Shortcut)
                ) return 1;

        return -1;
    }
}
