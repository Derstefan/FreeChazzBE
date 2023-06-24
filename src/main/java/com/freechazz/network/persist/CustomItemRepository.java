package com.freechazz.network.persist;

public interface CustomItemRepository {
    void updateItemQuantity(String itemName, float newQuantity);
}