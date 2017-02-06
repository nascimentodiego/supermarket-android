package com.liferay.supermarketandroid.view.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.liferay.supermarketandroid.R;
import com.liferay.supermarketandroid.presenter.login.LoginContracts;
import com.liferay.supermarketandroid.presenter.login.LoginPresenter;
import com.liferay.supermarketandroid.util.SuperMarketLog;
import com.liferay.supermarketandroid.view.createAccount.CreateAccountActivity;
import com.liferay.supermarketandroid.view.base.fragment.BaseFragment;
import com.liferay.supermarketandroid.view.home.HomeActivity;

import java.util.concurrent.TimeUnit;

import rx.Subscription;

/**
 * The type Login fragment.
 */
public class LoginFragment extends BaseFragment implements LoginContracts.View {

    private View root;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnCreateAccount;

    private Subscription buttonSubLogin, buttonSubCreate;

    private LoginContracts.Presenter mPresenter;
    private OnLoginFragmentListener mOnLoginFragmentListener;

    /**
     * Instantiates a new Login fragment.
     */
    public LoginFragment() {
        mPresenter = new LoginPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_login, container, false);
        initView();

        mPresenter.checkAccount();

        return root;
    }

    private void initView() {
        edtEmail = (EditText) root.findViewById(R.id.edt_email);
        edtPassword = (EditText) root.findViewById(R.id.edt_password);
        Button btnLogin = (Button) root.findViewById(R.id.btnLogin);
        btnCreateAccount = (Button) root.findViewById(R.id.btnCreateNewAccount);

        buttonSubCreate = RxView.clicks(btnCreateAccount)
                .throttleFirst(TIME_TO_CLICK, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> mPresenter.callCreateAccountActivity(),
                        Throwable::printStackTrace,
                        () -> SuperMarketLog.print("OnCompleted"));

        buttonSubLogin = RxView.clicks(btnLogin)
                .throttleFirst(TIME_TO_CLICK, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> mPresenter.doLogin(edtEmail.getText().toString().trim(),
                        edtPassword.getText().toString().trim(), false),
                        Throwable::printStackTrace,
                        () -> SuperMarketLog.print("OnCompleted"));

        registerSubscription(buttonSubLogin, buttonSubCreate);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mPresenter.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginFragmentListener) {
            mOnLoginFragmentListener = (OnLoginFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLoginFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnLoginFragmentListener = null;
        unregisterSubscription(buttonSubLogin);
        unregisterSubscription(buttonSubCreate);
    }


    @Override
    public void setLoadingIndicator(boolean active) {
        mOnLoginFragmentListener.onShowProgress();
    }

    @Override
    public void showLoginError(int resStringId) {
        Snackbar.make(root, getResources().getText(resStringId), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        mOnLoginFragmentListener.onErrorLoginFragmentListener();
    }

    @Override
    public void showSuccessLogin() {
        mOnLoginFragmentListener.onHideProgress();
        mPresenter.callMainActivity();
    }

    @Override
    public void callMainActivity() {
        Intent it = new Intent(getActivity(), HomeActivity.class);
        getActivity().startActivity(it);
        getActivity().finish();
    }

    @Override
    public void callCreateAccountActivity() {
        Intent it = new Intent(getActivity(), CreateAccountActivity.class);
        getActivity().startActivity(it);
        getActivity().finish();
    }


    /**
     * The interface On login fragment listener.
     */
    public interface OnLoginFragmentListener {

        /**
         * On show progress.
         */
        void onShowProgress();

        /**
         * On hide progress.
         */
        void onHideProgress();

        /**
         * On error login fragment listener.
         */
        void onErrorLoginFragmentListener();

    }
}
