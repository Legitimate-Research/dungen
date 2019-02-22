package com.cd;

/**
 * Created by William on 5/21/2017.
 */
public interface IInventory {

    int getSize();

    void setStack(int index, PerkStack stack);

    PerkStack getStack(int index);

}
