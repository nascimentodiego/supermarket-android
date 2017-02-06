package com.liferay.supermarketandroid.view.createAccount;

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
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.liferay.supermarketandroid.App;
import com.liferay.supermarketandroid.R;
import com.liferay.supermarketandroid.presenter.createAccount.CreateAccountContracts;
import com.liferay.supermarketandroid.presenter.createAccount.CreateAccountPresenter;
import com.liferay.supermarketandroid.view.base.fragment.BaseFragment;
import com.liferay.supermarketandroid.view.login.LoginActivity;

import java.util.concurrent.TimeUnit;

import rx.Subscription;

/**
 * The type Create account fragment.
 */
public class CreateAccountFragment extends BaseFragment implements CreateAccountContracts.View {


    private View root;
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnCreateAccount;
    /**
     * The Button sub login.
     */
    Subscription buttonSubLogin;
    /**
     * The Button sub create.
     */
    Subscription buttonSubCreate;

    private CreateAccountContracts.Presenter mPresenter;
    private OnCreateAccountFragmentListener mOnCreateAccountFragmentListener;


    /**
     * Instantiates a new Create account fragment.
     */
    public CreateAccountFragment() {
        mPresenter = new CreateAccountPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_create_account, container, false);
        initView();

        return root;
    }

    private void initView() {
        edtName = (EditText) root.findViewById(R.id.edt_name);
        edtEmail = (EditText) root.findViewById(R.id.edt_email);
        edtPassword = (EditText) root.findViewById(R.id.edt_password);
        Button btnLogin = (Button) root.findViewById(R.id.btnLogin);
        btnCreateAccount = (Button) root.findViewById(R.id.btnCreateNewAccount);

        buttonSubCreate = RxView.clicks(btnCreateAccount)
                .throttleFirst(TIME_TO_CLICK, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> mPresenter.createAccount(
                        edtEmail.getText().toString().trim(),
                        edtName.getText().toString().trim(),
                        edtPassword.getText().toString().trim(), true));

        buttonSubLogin = RxView.clicks(btnLogin)
                .throttleFirst(TIME_TO_CLICK, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> mPresenter.callLoginActivity());

        registerSubscription(buttonSubLogin);
        registerSubscription(buttonSubCreate);
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
        if (context instanceof OnCreateAccountFragmentListener) {
            mOnCreateAccountFragmentListener = (OnCreateAccountFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLoginFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnCreateAccountFragmentListener = null;
        unregisterSubscription(buttonSubLogin);
        unregisterSubscription(buttonSubCreate);
    }


    @Override
    public void setLoadingIndicator(boolean active) {
        mOnCreateAccountFragmentListener.onShowProgress();
    }

    @Override
    public void showError(int resStringId) {
        Snackbar.make(root, getResources().getText(resStringId), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        mOnCreateAccountFragmentListener.onErrorCreateAccountFragmentListener();
    }

    @Override
    public void showSuccessCreateAccount() {
        Toast.makeText(App.getContext(), "Sucess !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void callLoginActivity() {
        Intent it = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(it);
        getActivity().finish();
    }

    /**
     * The interface On login fragment listener.
     */
    public interface OnCreateAccountFragmentListener {

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
        void onErrorCreateAccountFragmentListener();

    }
}
