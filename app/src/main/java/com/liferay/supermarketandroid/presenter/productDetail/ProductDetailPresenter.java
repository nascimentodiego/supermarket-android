package com.liferay.supermarketandroid.presenter.productDetail;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.liferay.supermarketandroid.R;
import com.liferay.supermarketandroid.model.api.OnSubscribeListener;
import com.liferay.supermarketandroid.model.api.observable.PostCartItemObservable;
import com.liferay.supermarketandroid.model.model.CartItem;
import com.liferay.supermarketandroid.model.model.Product;
import com.liferay.supermarketandroid.model.repository.SharedPreferenceManager;
import com.liferay.supermarketandroid.model.repository.sqlite.ProductDao;

import rx.Subscription;

/**
 * The type Product detail presenter.
 */
public class ProductDetailPresenter implements ProductDetailContracts.Presenter, OnSubscribeListener<CartItem> {

    @NonNull
    private final ProductDetailContracts.View mView;
    private PostCartItemObservable postCartItemObservable;
    private Subscription postSubscription;

    /**
     * The Was showing dialog.
     */
    boolean wasShowingDialog;

    /**
     * Instantiates a new Product detail presenter.
     *
     * @param mView the m view
     */
    public ProductDetailPresenter(@NonNull ProductDetailContracts.View mView) {
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
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        unRegisterSubscription(postSubscription);
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
    public void onNext(CartItem result) {
        mView.showSuccess();
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
    public void addItem(Product product) {

        CartItem item = new CartItem();
        item.productTitle = product.getTitle();
        item.productPrice = product.getPrice();
        item.productFilename = product.getFilename();
        item.productId = product.getId();
        item.userId = SharedPreferenceManager.getInstance().getUserId();

        postCartItemObservable = new PostCartItemObservable(item);

        postSubscription = postCartItemObservable.getObservable()
                .subscribe(this::onNext,
                        this::onError,
                        this::onCompleted);

        wasShowingDialog = true;
        mView.setLoadingIndicator(true);
    }

    @Override
    public void getProductById(long productId) {
        Product product = ProductDao.getInstance().getProductById(new Product(productId));
        mView.showProduct(product);
    }
}
