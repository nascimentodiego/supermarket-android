package com.liferay.supermarketandroid.model.api;

import com.liferay.supermarketandroid.model.model.CartItem;
import com.liferay.supermarketandroid.model.model.Product;

import java.util.List;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * The interface Api data request.
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
public interface ApiDataRequest {

    /**
     * Gets product.
     *
     * @param authorization the authorization
     * @param filter        the filter
     * @return the product
     */
//Web API
    @GET("products")
    Observable<List<Product>> getProduct(@Header("Authorization") String authorization,
                                         @Query(value = "filter", encoded = true) String filter);


    /**
     * Gets cart.
     *
     * @param authorization the authorization
     * @param path          the path
     * @return the cart
     */
    @GET("{pathGetCart}")
    Observable<List<CartItem>> getCart(@Header("Authorization") String authorization,
                                       @Path("pathGetCart") String path);

    /**
     * Post cart item observable.
     *
     * @param authorization the authorization
     * @param path          the path
     * @param title         the title
     * @param price         the price
     * @param filename      the filename
     * @param productId     the product id
     * @param userId        the user id
     * @return the observable
     */
    @FormUrlEncoded
    @POST("{path}")
    Observable<CartItem> postCartItem(@Header("Authorization") String authorization,
                                      @Path("path") String path,
                                      @Field(ApiConstants.FIELD_PRODUCT_TITLE) String title,
                                      @Field(ApiConstants.FIELD_PRODUCT_PRICE) double price,
                                      @Field(ApiConstants.FIELD_PRODUCT_FILENAME) String filename,
                                      @Field(ApiConstants.FIELD_PRODUCT_ID) long productId,
                                      @Field(ApiConstants.FIELD_USER_ID) long userId);

    /**
     * Delete cart item observable.
     *
     * @param authorization the authorization
     * @param cartItemId    the cart item id
     * @return the observable
     */
    @DELETE("cart/{cartItemId}")
    Observable<CartItem> deleteCartItem(@Header("Authorization") String authorization,
                                        @Path("cartItemId") long cartItemId);
}
