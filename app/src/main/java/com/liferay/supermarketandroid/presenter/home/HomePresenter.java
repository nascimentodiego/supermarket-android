package com.liferay.supermarketandroid.presenter.home;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.liferay.supermarketandroid.App;
import com.liferay.supermarketandroid.R;
import com.liferay.supermarketandroid.model.api.ApiConstants;
import com.liferay.supermarketandroid.model.api.OnSubscribeListener;
import com.liferay.supermarketandroid.model.api.observable.GetProductsObservable;
import com.liferay.supermarketandroid.model.model.Product;
import com.liferay.supermarketandroid.model.repository.sqlite.ProductDao;
import com.liferay.supermarketandroid.util.Util;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;

/**
 * The type Home presenter.
 */
public class HomePresenter implements HomeContracts.Presenter, OnSubscribeListener<List<Product>> {


    @NonNull
    private final HomeContracts.View mView;
    private GetProductsObservable getProductsObservable;
    private Subscription getProdSubscription, getFilterProductSubscription;

    private List<Product> productList;
    /**
     * The Was showing dialog.
     */
    boolean wasShowingDialog;

    /**
     * Instantiates a new Home presenter.
     *
     * @param mView the m view
     */
    public HomePresenter(@NonNull HomeContracts.View mView) {
        this.mView = mView;
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
    public void getProducts(TypeFilter filter, boolean forceUpdate) {
        if (!Util.isConnected(App.getContext())) {
            productList = ProductDao.getInstance()
                    .getProducts(ApiConstants.getValueByTypeUrlEncode(filter.getValue()));
            mView.populateListProduct(productList);
            return;
        }

        if (getProductsObservable == null || forceUpdate) {
            getProductsObservable = new GetProductsObservable(filter.getValue());
        }

        getProdSubscription = getProductsObservable.getObservable()
                .doOnSubscribe(() -> {
                    wasShowingDialog = true;
                    mView.setLoadingIndicator(true);
                })
                .subscribe(
                        this::onNext,
                        this::onError,
                        this::onCompleted);
    }

    @Override
    public void getProdutByPosition(int position) {
        mView.callProductDetails(productList.get(position));
    }

    @Override
    public void onNext(List<Product> result) {
        List<Product> listFiltered = new ArrayList<>();

        getFilterProductSubscription = Observable.from(result)
                .filter(product -> product.getTitle() == null ? false : true)
                .subscribe(product -> listFiltered.add(product),
                        error -> onError(error),
                        () -> {
                            productList = listFiltered;
                            ProductDao.getInstance().insertListProducts(listFiltered);

                            mView.populateListProduct(listFiltered);
                            mView.setLoadingIndicator(false);
                        });
    }

    @Override
    public void onError(Throwable throwable) {
        wasShowingDialog = false;
        mView.setLoadingIndicator(false);
        mView.showError(R.string.msg_error);
    }

    @Override
    public void onCompleted() {
        wasShowingDialog = false;
        mView.setLoadingIndicator(false);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        unRegisterSubscription(getProdSubscription);
        unRegisterSubscription(getFilterProductSubscription);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("wasShowingDialog", wasShowingDialog);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        mView.setLoadingIndicator(wasShowingDialog);
    }
}
