package com.tri.erp.spring.commons.helpers;

import java.util.Collection;

/**
 * Created by TSI Admin on 9/16/2014.
 */
public class Checker {

    public static boolean collectionIsEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }
}
