package com.spring.web.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by clj on 2017/7/29.
 * Description:
 */
public class IterableUtils {
    private IterableUtils(){}

    public static <T> Set<T> Iterable2Set(Iterable<T> iterable){
        Set<T> set = new HashSet<>();
        for(T t: iterable){
            set.add(t);
        }
        return set;
    }
}
