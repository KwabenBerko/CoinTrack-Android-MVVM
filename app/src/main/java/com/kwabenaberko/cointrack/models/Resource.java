package com.kwabenaberko.cointrack.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Kwabena Berko on 8/23/2018.
 */

public class Resource<T> {
    @NonNull
    private boolean isLoading;
    @Nullable
    private T data;

    public Resource(@NonNull boolean isLoading, @Nullable T data){
        this.isLoading = isLoading;
        this.data = data;
    }

    public static <T> Resource<T> loading(boolean isLoading){
        return new Resource<>(isLoading, null);
    }

    public static <T> Resource<T> success(T data){
        return new Resource<>(false, data);
    }
}
