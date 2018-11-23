package com.craving.restaurant.interfaces;

/**
 * Created by webskitters on 2/2/18.
 */

public interface OnCartItemClick {
    void onCartItemClick(Object o, String prodId, String dispensaryId, String dispensaryName, String dispensaryDistance);
    void onCountClick(Object o, int count, String prodId, String dispensaryId);
}
