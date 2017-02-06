package com.liferay.supermarketandroid.presenter.createAccount;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.liferay.supermarketandroid.R;
import com.liferay.supermarketandroid.model.api.OnSubscribeListener;
import com.liferay.supermarketandroid.model.api.observable.PostUserObservable;
import com.liferay.supermarketandroid.model.model.User;
import com.liferay.supermarketandroid.model.repository.SharedPreferenceManager;
import com.liferay.supermarketandroid.util.StringUtils;

import rx.Subscription;

/**
 * The type Create account presenter.
 */
public class CreateAccountPresenter implements CreateAccountContracts.Presenter, OnSubscribeListener<User> {


    @NonNull
    private final CreateAccountContracts.View mView;


    private PostUserObservable postUserObservable;
    /**
     * The Create account subscription.
     */
    Subscription createAccountSubscription;
    private boolean wasShowingDialog;

    /**
     * Instantiates a new Create account presenter.
     *
     * @param createAccountView the create account view
     */
    public CreateAccountPresenter(@NonNull CreateAccountContracts.View createAccountView) {
        this.mView = createAccountView;

    }

    @Override
    public void createAccount(String email, String login, String password, boolean forceUpdate) {
        User user = new User();
        user.name = login;
        user.email = email;
        user.password = password;

        if (!canPostForm(user)) {
            mView.showError(R.string.create_account_filled_fields);
            return;
        }
        if (postUserObservable == null) {
            postUserObservable = new PostUserObservable(user);
        }

        wasShowingDialog = true;
        createAccountSubscription = postUserObservable.getObservable()
                .subscribe(this::onNext,
                        this::onError,
                        this::onCompleted);

        mView.setLoadingIndicator(true);
    }

    private boolean canPostForm(User user) {
        boolean filledEmptyFields = user.name.equals(StringUtils.EMPTY_STRING)
                || user.email.equals(StringUtils.EMPTY_STRING)
                || user.password.equals(StringUtils.EMPTY_STRING);

        return !filledEmptyFields;
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
    public void callLoginActivity() {
        mView.callLoginActivity();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        unRegisterSubscription(createAccountSubscription);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("wasShowingDialog", wasShowingDialog);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        if (wasShowingDialog) {
            mView.setLoadingIndicator(true);
        }
    }

    @Override
    public void onNext(User result) {
        SharedPreferenceManager.getInstance().setUserId(result.id);
        wasShowingDialog = false;
        mView.showSuccessCreateAccount();
        mView.callLoginActivity();
    }

    @Override
    public void onError(Throwable throwable) {
        mView.showError(R.string.msg_error);
        wasShowingDialog = false;
    }

    @Override
    public void onCompleted() {

    }
}
