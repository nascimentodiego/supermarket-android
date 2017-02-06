package com.liferay.supermarketandroid.model.api.observable;

import com.liferay.supermarketandroid.model.api.ApiConstants;
import com.liferay.supermarketandroid.model.model.AccessToken;
import com.liferay.supermarketandroid.model.model.User;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * The type Get access token observable oauth.
 */
public class GetAccessTokenObservableOauth extends GenericObservableOauth {

    /**
     * Instantiates a new Get access token observable oauth.
     *
     * @param user the user
     */
    public GetAccessTokenObservableOauth(User user) {
        myObservable = apiRequest.getAccessToken(user.username,user.password, ApiConstants.GRANT_TYPE)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).cache();
    }

    public Observable<AccessToken> getObservable() {
        return this.myObservable;
    }
}
