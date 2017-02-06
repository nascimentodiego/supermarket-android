package com.liferay.supermarketandroid.view.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.liferay.supermarketandroid.R;
import com.liferay.supermarketandroid.model.model.CartItem;
import com.liferay.supermarketandroid.model.model.Product;
import com.liferay.supermarketandroid.view.base.activity.BaseOAuthActivity;
import com.liferay.supermarketandroid.view.home.HomeActivity;
import com.liferay.supermarketandroid.view.productDetail.ProductDetailActivity;

/**
 * The type Checkout activity.
 */
/*
 * Copyright (C) 2017 Diego Figueredo do Nascimento.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class CheckoutActivity extends BaseOAuthActivity implements CheckoutFragment.OnCheckoutFragmentListener {

    private CheckoutFragment checkoutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        buildToolbar();
        buildFragment(savedInstanceState);


        findViewById(R.id.minIcon).bringToFront();
    }

    @Override
    protected void buildFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            checkoutFragment = (CheckoutFragment)
                    fragmentManager.findFragmentByTag(CheckoutFragment.class.getSimpleName());
        } else {
            checkoutFragment = new CheckoutFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.container, checkoutFragment, CheckoutFragment.class.getSimpleName())
                    .commit();
        }
    }

    @Override
    public void onCartItemSelected(CartItem item) {
        Intent it = new Intent(this, ProductDetailActivity.class);
        it.putExtra(HomeActivity.EXTRA_PRODUCT, item.productId);
        startActivity(it);
    }

}
