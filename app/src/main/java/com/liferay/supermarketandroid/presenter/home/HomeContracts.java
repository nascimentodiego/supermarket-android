package com.liferay.supermarketandroid.presenter.home;

import com.liferay.supermarketandroid.model.api.ApiConstants;
import com.liferay.supermarketandroid.model.model.Product;
import com.liferay.supermarketandroid.presenter.BasePresenter;

import java.util.List;

/**
 * The type Home contracts.
 */
public class HomeContracts {

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
         * Call product details.
         *
         * @param prod the prod
         */
        void callProductDetails(Product prod);

        /**
         * Populate list product.
         *
         * @param result the result
         */
        void populateListProduct(List<Product> result);
    }

    /**
     * The interface Presenter.
     */
    public interface Presenter extends BasePresenter {
        /**
         * Gets products.
         *
         * @param filter      the filter
         * @param forceUpdate the force update
         */
        void getProducts(TypeFilter filter, boolean forceUpdate);

        /**
         * Gets produt by position.
         *
         * @param position the position
         */
        void getProdutByPosition(int position);

        /**
         * The enum Type filter.
         */
        enum TypeFilter {
            /**
             * All type filter.
             */
            ALL(null),
            /**
             * Bakery type filter.
             */
            BAKERY(ApiConstants.API_FILTER_BAKERY),
            /**
             * Dairy type filter.
             */
            DAIRY(ApiConstants.API_FILTER_FRUIT),
            /**
             * Fruit type filter.
             */
            FRUIT(ApiConstants.API_FILTER_FRUIT),
            /**
             * Vegetable type filter.
             */
            VEGETABLE(ApiConstants.API_FILTER_VEGETABLE),
            /**
             * Meat type filter.
             */
            MEAT(ApiConstants.API_FILTER_MEAT);

            private String typeStr;

            TypeFilter(String str) {
                typeStr = str;
            }

            /**
             * Gets value.
             *
             * @return the value
             */
            public String getValue() {
                return typeStr;
            }
        }
    }


}
