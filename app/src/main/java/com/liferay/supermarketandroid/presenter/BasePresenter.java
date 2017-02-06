package com.liferay.supermarketandroid.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by diego on 30/01/17.
 */
public interface BasePresenter{

    /**
     * Subscribe.
     */
    void subscribe();

    /**
     * Un subscribe.
     */
    void unSubscribe();

    /**
     * On save instance state.
     *
     * @param outState the out state
     */
    void onSaveInstanceState(@NonNull Bundle outState);

    /**
     * On restore instance state.
     *
     * @param savedInstanceState the saved instance state
     */
    void onRestoreInstanceState(@NonNull Bundle savedInstanceState);
}
