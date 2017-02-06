package com.liferay.supermarketandroid.view.productDetail;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.liferay.supermarketandroid.R;
import com.liferay.supermarketandroid.view.base.activity.BaseOAuthActivity;
import com.liferay.supermarketandroid.view.home.HomeActivity;

/**
 * The type Product detail activity.
 */
public class ProductDetailActivity extends BaseOAuthActivity
        implements ProductDetailFragment.OnProductDetailFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        long idProduct = getIntent().getExtras().getLong(HomeActivity.EXTRA_PRODUCT, 0);

        ProductDetailFragment fragment = ProductDetailFragment.newInstance(idProduct);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, ProductDetailFragment.class.getSimpleName())
                .commit();
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
    public void onErrorProductDetailFragmentListener() {
        hideProgressDialog();
    }
}
