package com.freechazz.server.persist;

public interface CustomItemRepository {
    void updateItemQuantity(String itemName, float newQuantity);
}