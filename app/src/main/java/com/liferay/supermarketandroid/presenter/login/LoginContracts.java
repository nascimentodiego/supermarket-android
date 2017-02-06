package com.liferay.supermarketandroid.presenter.login;

import com.liferay.supermarketandroid.model.model.User;
import com.liferay.supermarketandroid.presenter.BasePresenter;

/**
 * The type Login contracts.
 */
public class LoginContracts {

    /**
     * The interface View.
     */
    public interface View {
        /**
         * Sets loading indicator.
         *
         * @param active the active
         */
        void setLoadingIndicator(boolean active);

        /**
         * Show login error.
         *
         * @param resStringId the res string id
         */
        void showLoginError(int resStringId);

        /**
         * Show success login.
         */
        void showSuccessLogin();

        /**
         * Call main activity.
         */
        void callMainActivity();

        /**
         * Call create account activity.
         */
        void callCreateAccountActivity();

    }

    /**
     * The interface Presenter.
     */
    public interface Presenter extends BasePresenter {
        /**
         * Do login.
         *
         * @param login       the login
         * @param password    the password
         * @param forceUpdate the force update
         */
        void doLogin(String login, String password, boolean forceUpdate);

        /**
         * Is access boolean.
         *
         * @return the boolean
         */
        boolean isAccess();

        /**
         * Check account.
         */
        void checkAccount();

        /**
         * Call main activity.
         */
        void callMainActivity();

        /**
         * Call create account activity.
         */
        void callCreateAccountActivity();

        /**
         * On success get user.
         *
         * @param result the result
         */
        void onSuccessGetUser(User result);

    }
}


