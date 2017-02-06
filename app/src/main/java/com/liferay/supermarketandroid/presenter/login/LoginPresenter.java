package com.liferay.supermarketandroid.presenter.login;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.liferay.supermarketandroid.R;
import com.liferay.supermarketandroid.model.api.OnSubscribeListener;
import com.liferay.supermarketandroid.model.api.observable.GetAccessTokenObservableOauth;
import com.liferay.supermarketandroid.model.api.observable.GetUserObservable;
import com.liferay.supermarketandroid.model.model.AccessToken;
import com.liferay.supermarketandroid.model.model.User;
import com.liferay.supermarketandroid.model.repository.SharedPreferenceManager;
import com.liferay.supermarketandroid.util.StringUtils;

import rx.Subscription;

/**
 * The type Login presenter.
 */
public class LoginPresenter implements LoginContracts.Presenter, OnSubscribeListener<AccessToken> {

    private GetAccessTokenObservableOauth getAccessTokenObservable;
    private GetUserObservable getUserObservable;

    @NonNull
    private final LoginContracts.View mLoginView;

    /**
     * The Get access token subscription.
     */
    Subscription getAccessTokenSubscription;
    /**
     * The Get user subscription.
     */
    Subscription getUserSubscription;

    private boolean wasShowingDialog;


    /**
     * Instantiates a new Login presenter.
     *
     * @param loginView the login view
     */
    public LoginPresenter(@NonNull LoginContracts.View loginView) {
        this.mLoginView = loginView;

    }

    @Override
    public void doLogin(String login, String password, boolean forceUpdate) {
        User user = new User();
        user.username = login;
        user.password = password;
        if (getAccessTokenObservable == null) {
            getAccessTokenObservable = new GetAccessTokenObservableOauth(user);
        }

        wasShowingDialog = true;

        getAccessTokenSubscription = getAccessTokenObservable.getObservable()
                .subscribe(this::onNext,
                        this::onError,
                        this::onCompleted);

        mLoginView.setLoadingIndicator(true);
    }

    @Override
    public boolean isAccess() {
        return !SharedPreferenceManager
                .getInstance()
                .getAccessToken()
                .equals(StringUtils.EMPTY_STRING)
                && !(SharedPreferenceManager
                .getInstance()
                .getUserId() == SharedPreferenceManager.ZERO_CONSTANT);
    }

    @Override
    public void checkAccount() {
        if (isAccess()) {
            mLoginView.callMainActivity();
        }
    }

    @Override
    public void callMainActivity() {
        mLoginView.callMainActivity();
    }

    @Override
    public void callCreateAccountActivity() {
        mLoginView.callCreateAccountActivity();
    }

    /**
     * Un register subscription.
     *
     * @param subscription the subscription
     */
    protected void unRegisterSubscription(Subscription subscription) {
        if (subscription != null) {
            if (subscription != null
                    && subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        unRegisterSubscription(getAccessTokenSubscription);
        unRegisterSubscription(getUserSubscription);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("wasShowingDialog", wasShowingDialog);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        if (wasShowingDialog) {
            mLoginView.setLoadingIndicator(true);
        }
    }

    @Override
    public void onNext(AccessToken result) {
        SharedPreferenceManager.getInstance().setAccessToken(result.access_token);
        registerGetUserSubscriber();
    }

    @Override
    public void onError(Throwable throwable) {
        mLoginView.showLoginError(R.string.msg_error);
        wasShowingDialog = false;
    }

    @Override
    public void onCompleted() {
    }

    /**
     * Register get user subscriber.
     */
    public void registerGetUserSubscriber() {
        if (getUserObservable == null) {
            getUserObservable = new GetUserObservable();
        }

        getUserSubscription = getUserObservable.getObservable()
                .subscribe(this::onSuccessGetUser,
                        this::onError,
                        this::onCompleted);
    }

    @Override
    public void onSuccessGetUser(User result) {
        SharedPreferenceManager.getInstance().setUserId(result.id);
        wasShowingDialog = false;
        mLoginView.showSuccessLogin();
    }
}
