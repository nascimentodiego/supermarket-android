package com.liferay.supermarketandroid.model.api.observable;


import com.liferay.supermarketandroid.model.api.ApiRequest;
import com.liferay.supermarketandroid.model.api.ServiceClient;

import rx.Observable;

/**
 * The type Generic observable oauth.
 *
 * @param <T> the type parameter
 */
public class GenericObservableOauth<T> {
    /**
     * The My observable.
     */
    protected Observable<T> myObservable;
    /**
     * The Api request.
     */
    protected ApiRequest apiRequest;

    /**
     * Instantiates a new Generic observable oauth.
     */
    public GenericObservableOauth() {
        this.apiRequest = ServiceClient.getInstance().createServiceOauth(ApiRequest.class);
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
