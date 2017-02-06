package com.liferay.supermarketandroid.presenter.productDetail;

import com.liferay.supermarketandroid.model.model.Product;
import com.liferay.supermarketandroid.presenter.BasePresenter;

/**
 * The type Product detail contracts.
 */
public class ProductDetailContracts {

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
         * Show success.
         */
        void showSuccess();

        /**
         * Show product.
         *
         * @param product the product
         */
        void showProduct(Product product);

    }

    /**
     * The interface Presenter.
     */
    public interface Presenter extends BasePresenter {
        /**
         * Add item.
         *
         * @param product the product
         */
        void addItem(Product product);

        /**
         * Gets product by id.
         *
         * @param productId the product id
         */
        void getProductById(long productId);
    }

}
