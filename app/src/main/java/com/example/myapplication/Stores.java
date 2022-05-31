package com.example.myapplication;

public class Stores {
    String StoreId;
    String StoreName;
    String StoreUrl;

    public Stores(){}

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getStoreUrl() {
        return StoreUrl;
    }

    public void setStoreUrl(String storeUrl) {
        StoreUrl = storeUrl;
    }

    public Stores(String storeId, String storeName, String storeUrl) {
        StoreId = storeId;
        StoreName = storeName;
        StoreUrl = storeUrl;
    }
}
