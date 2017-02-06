package com.liferay.supermarketandroid.model.api.observable;

import com.liferay.supermarketandroid.model.model.User;
import com.liferay.supermarketandroid.model.repository.SharedPreferenceManager;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * The type Get user observable.
 */
public class GetUserObservable extends GenericObservableOauth {
    /**
     * Instantiates a new Get user observable.
     */
    public GetUserObservable() {
        String token = "Bearer " + SharedPreferenceManager.getInstance().getAccessToken();
        myObservable = apiRequest.getUser(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).cache();
    }

    public Observable<User> getObservable() {
        return this.myObservable;
    }
}
