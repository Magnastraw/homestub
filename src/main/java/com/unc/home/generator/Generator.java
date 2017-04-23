package com.unc.home.generator;


import com.unc.home.smarthome.inventory.InventoryObject;

public interface Generator<T> {
    T generate(InventoryObject object);
}
