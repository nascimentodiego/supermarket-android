package com.liferay.supermarketandroid.presenter.checkout;

import com.liferay.supermarketandroid.model.model.CartItem;
import com.liferay.supermarketandroid.presenter.BasePresenter;

import java.util.List;

/**
 * The type Checkout contracts.
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
public class CheckoutContracts {

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
         * Show success delete item.
         *
         * @param item the item
         */
        void showSuccessDeleteItem(CartItem item);

        /**
         * Populate list product.
         *
         * @param result the result
         * @param total  the total
         */
        void populateListProduct(List<CartItem> result, double total);
    }

    /**
     * The interface Presenter.
     */
    public interface Presenter extends BasePresenter {
        /**
         * Gets products.
         */
        void getProducts();

        /**
         * Delete cart item.
         *
         * @param item the item
         */
        void deleteCartItem(CartItem item);
    }
}
