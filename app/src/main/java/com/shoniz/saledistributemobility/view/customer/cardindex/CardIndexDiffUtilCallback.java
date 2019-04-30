package com.shoniz.saledistributemobility.view.customer.cardindex;

import android.support.v7.util.DiffUtil;

import java.util.Hashtable;
import java.util.List;

public class CardIndexDiffUtilCallback extends DiffUtil.Callback {
    private final List<CardIndexDetailModel> oldList;
    private final List<CardIndexDetailModel> newList;

    private final Hashtable<Integer, Integer> shortcutsAvailabilityOld;
    private final Hashtable<Integer, Integer> shortcutsAvailabilityNew;

    public CardIndexDiffUtilCallback(List<CardIndexDetailModel> oldList,
                                     List<CardIndexDetailModel> newList,
                                     Hashtable<Integer, Integer> shortcutsAvailabilityOld,
                                     Hashtable<Integer, Integer> shortcutsAvailabilityNew) {
        this.oldList = oldList;
        this.newList = newList;
        this.shortcutsAvailabilityOld = shortcutsAvailabilityOld;
        this.shortcutsAvailabilityNew = shortcutsAvailabilityNew;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        CardIndexDetailModel oldItem =oldList.get(oldItemPosition);
        CardIndexDetailModel newItem =newList.get(newItemPosition);
        return oldItem.Shortcut.equals(newItem.Shortcut);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        CardIndexDetailModel oldItem =oldList.get(oldItemPosition);
        CardIndexDetailModel newItem =newList.get(newItemPosition);
        int availableOld=-1;
        int availableNew=-1;
        int shortcut = Integer.parseInt(newItem.Shortcut);
        if (shortcutsAvailabilityNew.containsKey(shortcut)) {
            availableOld=shortcutsAvailabilityNew.get(shortcut);
        }

        if (shortcutsAvailabilityOld.containsKey(shortcut)) {
            availableNew=shortcutsAvailabilityOld.get(shortcut);
        }


        return oldItem.Shortcut.equals(newItem.Shortcut)
                && oldItem.IsSelected == newItem.IsSelected
                && oldItem.ExistenceCarton == newItem.ExistenceCarton
                && oldItem.ExistencePackage == newItem.ExistencePackage
                && oldItem.RequestCarton == newItem.RequestCarton
                && oldItem.RequestPackage == newItem.RequestPackage
                && availableNew==availableOld;
    }
}
