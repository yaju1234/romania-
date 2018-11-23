package com.craving.restaurant.interfaces;

/**
 * Created by webskitters on 2/2/18.
 */

public interface OnClickItemHome {
    void onListItemClick(Object o, int position);
    void onCategoryClick(int position, String cat_name, String cat_id);
}
