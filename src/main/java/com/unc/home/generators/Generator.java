package com.unc.home.generators;


import com.unc.home.smarthome.inventory.InventoryObject;

public interface Generator<T> {
    T generate(InventoryObject object);
}
