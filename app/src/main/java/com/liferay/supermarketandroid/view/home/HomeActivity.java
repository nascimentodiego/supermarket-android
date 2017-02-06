package com.liferay.supermarketandroid.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;

import com.liferay.supermarketandroid.R;
import com.liferay.supermarketandroid.model.model.Product;
import com.liferay.supermarketandroid.view.productDetail.ProductDetailActivity;
import com.liferay.supermarketandroid.view.base.activity.BaseOAuthActivity;
import com.liferay.supermarketandroid.view.checkout.CheckoutActivity;


/**
 * The type Main activity.
 */
public class HomeActivity extends BaseOAuthActivity
        implements HomeFragment.OnProductListFragmentListener {

    /**
     * The constant EXTRA_PRODUCT.
     */
    public static final String EXTRA_PRODUCT =
            "com.liferay.supermarketandroid.view.home.MainActivity.EXTRA_PRODUCT";

    private HomeFragment homeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildToolbar();
        buildFragment(savedInstanceState);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent it = new Intent(HomeActivity.this, CheckoutActivity.class);
            startActivity(it);
        });

        findViewById(R.id.minIcon).bringToFront();
    }

    @Override
    protected void buildFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            homeFragment = (HomeFragment)
                    fragmentManager.findFragmentByTag(HomeFragment.class.getSimpleName());
        } else {
            homeFragment = new HomeFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.container, homeFragment, HomeFragment.class.getSimpleName())
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onProductSelected(Product product) {
        Intent it = new Intent(this, ProductDetailActivity.class);
        it.putExtra(EXTRA_PRODUCT, product.getId());
        startActivity(it);
    }
}
