package com.liferay.supermarketandroid.presenter.checkout;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.liferay.supermarketandroid.R;
import com.liferay.supermarketandroid.model.api.OnSubscribeListener;
import com.liferay.supermarketandroid.model.api.observable.DeleteCartItemObservable;
import com.liferay.supermarketandroid.model.api.observable.GetCartObservable;
import com.liferay.supermarketandroid.model.model.CartItem;

import java.util.List;

import rx.Subscription;

/**
 * The type Checkout presenter.
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
public class CheckoutPresenter implements CheckoutContracts.Presenter, OnSubscribeListener<List<CartItem>> {

    @NonNull
    private final CheckoutContracts.View mView;
    private GetCartObservable getCartObservable;
    private DeleteCartItemObservable deleteCartItemObservable;
    private Subscription getCarSubscription, deleteSubscription;

    private List<CartItem> cartItems;
    /**
     * The Was showing dialog.
     */
    boolean wasShowingDialog;


    /**
     * Instantiates a new Checkout presenter.
     *
     * @param mView the m view
     */
    public CheckoutPresenter(@NonNull CheckoutContracts.View mView) {
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
    public void getProducts() {
        getCartObservable = new GetCartObservable();

        getCarSubscription = getCartObservable.getObservable()
                .subscribe(this::onNext,
                        this::onError,
                        this::onCompleted);

        wasShowingDialog = true;
        mView.setLoadingIndicator(true);
    }

    @Override
    public void deleteCartItem(CartItem item) {
        deleteCartItemObservable = new DeleteCartItemObservable(item);

        deleteSubscription = deleteCartItemObservable.getObservable()
                .subscribe(resCartItem -> mView.showSuccessDeleteItem(resCartItem),
                        this::onError,
                        () -> mView.showSuccess());

        wasShowingDialog = true;
        mView.setLoadingIndicator(true);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        unRegisterSubscription(getCarSubscription);
        unRegisterSubscription(deleteSubscription);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("wasShowingDialog", wasShowingDialog);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        mView.setLoadingIndicator(wasShowingDialog);
    }

    @Override
    public void onNext(List<CartItem> result) {
        cartItems = result;
        double total = sum();
        mView.populateListProduct(result, total);
        mView.setLoadingIndicator(false);
    }

    /**
     * Sum double.
     *
     * @return the double
     */
    public double sum() {
        double total = 0d;
        for (CartItem item : cartItems) {
            total = total + item.productPrice;
        }
        return total;
    }

    @Override
    public void onError(Throwable throwable) {
        mView.showError(R.string.msg_error);
        wasShowingDialog = false;
    }

    @Override
    public void onCompleted() {
        mView.showSuccess();
        wasShowingDialog = false;
    }
}
