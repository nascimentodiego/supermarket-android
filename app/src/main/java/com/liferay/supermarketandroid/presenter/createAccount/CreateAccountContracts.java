package com.liferay.supermarketandroid.presenter.createAccount;

import com.liferay.supermarketandroid.presenter.BasePresenter;

/**
 * The type Create account contracts.
 */
public class CreateAccountContracts {

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
         * Show error.
         *
         * @param resStringId the res string id
         */
        void showError(int resStringId);

        /**
         * Show success create account.
         */
        void showSuccessCreateAccount();

        /**
         * Call login activity.
         */
        void callLoginActivity();
    }

    /**
     * The interface Presenter.
     */
    public interface Presenter extends BasePresenter {
        /**
         * Create account.
         *
         * @param email       the email
         * @param login       the login
         * @param password    the password
         * @param forceUpdate the force update
         */
        void createAccount(String email, String login, String password, boolean forceUpdate);

        /**
         * Call login activity.
         */
        void callLoginActivity();

    }
}
