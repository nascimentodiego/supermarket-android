package com.liferay.supermarketandroid.model.api.observable;

import com.liferay.supermarketandroid.model.api.ApiDataRequest;
import com.liferay.supermarketandroid.model.api.ServiceClient;
import com.liferay.supermarketandroid.model.repository.SharedPreferenceManager;

import rx.Observable;

/**
 * Created by Diego on 14/01/2017.
 *
 * @param <T> the type parameter
 */
public class GenericObservable<T> {

    /**
     * The My observable.
     */
    protected Observable<T> myObservable;
    /**
     * The Api data request.
     */
    protected ApiDataRequest apiDataRequest;
    /**
     * The Token.
     */
    protected String token;

    /**
     * Instantiates a new Generic observable.
     */
    public GenericObservable() {
        token = "Bearer " + SharedPreferenceManager.getInstance().getAccessToken();
        this.apiDataRequest = ServiceClient.getInstance().createService(ApiDataRequest.class);
    }

    /**
     * Gets observable.
     *
     * @return the observable
     */
    public Observable<T> getObservable() {
        return this.myObservable;
    }
}

