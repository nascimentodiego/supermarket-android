package com.liferay.supermarketandroid.model.api.observable;

import com.liferay.supermarketandroid.model.model.User;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * The type Post user observable.
 */
public class PostUserObservable extends GenericObservableOauth {
    /**
     * Instantiates a new Post user observable.
     *
     * @param user the user
     */
    public PostUserObservable(User user) {
        myObservable = apiRequest.postUser(user.name, user.email, user.password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).cache();
    }

    public Observable<User> getObservable() {
        return this.myObservable;
    }
}
