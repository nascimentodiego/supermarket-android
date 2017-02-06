package com.liferay.supermarketandroid.view.createAccount;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.liferay.supermarketandroid.R;
import com.liferay.supermarketandroid.view.base.activity.BaseActivity;
import com.liferay.supermarketandroid.view.createAccount.CreateAccountFragment.OnCreateAccountFragmentListener;

/**
 * The type Create account activity.
 */
public class CreateAccountActivity extends BaseActivity implements OnCreateAccountFragmentListener {
    private CreateAccountFragment createAccountFragment;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        buildToolbar();
        buildFragment(savedInstanceState);
    }

    @Override
    protected void buildFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            createAccountFragment = (CreateAccountFragment)
                    fragmentManager.findFragmentByTag(CreateAccountFragment.class.getSimpleName());

        } else {
            createAccountFragment = new CreateAccountFragment();

            fragmentManager.beginTransaction()
                    .add(R.id.container, createAccountFragment)
                    .commit();
        }
    }

    @Override
    public void onShowProgress() {
        showProgressDialog();
    }

    @Override
    public void onHideProgress() {
        hideProgressDialog();
    }

    @Override
    public void onErrorCreateAccountFragmentListener() {
        hideProgressDialog();
    }
}
