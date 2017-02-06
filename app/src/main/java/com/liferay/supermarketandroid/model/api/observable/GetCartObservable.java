package com.liferay.supermarketandroid.model.api.observable;

import com.liferay.supermarketandroid.model.api.ApiConstants;
import com.liferay.supermarketandroid.model.model.CartItem;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * The type Get cart observable.
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
public class GetCartObservable extends GenericObservable {
    /**
     * Instantiates a new Get cart observable.
     */
    public GetCartObservable() {
        myObservable = apiDataRequest.getCart(token, ApiConstants.PATH_CART)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<CartItem>> getObservable() {
        return this.myObservable;
    }
}
