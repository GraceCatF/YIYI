package com.clubank.device.data.remote;

import java.io.Serializable;

/**
 * Created by fengyq on 2017/2/28.
 */
public class ApiResponse<T> implements Serializable {
    public int total_count = 0;
    public boolean incomplete_results;
    protected T items;

    public T getitems() {
        return items;
    }


}

