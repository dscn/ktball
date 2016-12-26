package com.newer.kt.Refactor.utils;

import android.util.SparseArray;

/**
 * Created by huangbo on 2016/10/2.
 */

public class AdapterItemUtil {

    private SparseArray<Object> typeSArr = new SparseArray<>();

    /**
     * @param type item的类型
     *
     * @return 通过object类型的type来得到int类型的type
     */
    public int getIntType(Object type) {
        int index = typeSArr.indexOfValue(type);
        if (index == -1) {
            index = typeSArr.size();
            // 如果没用这个type，就存入这个type
            typeSArr.put(index, type);
        }
        return index;
    }

}
