package com.liferay.supermarketandroid.view.checkout.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.liferay.supermarketandroid.R;
import com.liferay.supermarketandroid.model.api.ApiConstants;
import com.liferay.supermarketandroid.model.model.CartItem;

import java.util.List;

/**
 * The type Cart item adapter.
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
public class CartItemAdapter extends ArrayAdapter<CartItem> {
    /**
     * Instantiates a new Cart item adapter.
     *
     * @param context   the context
     * @param cartItems the cart items
     */
    public CartItemAdapter(Context context, List<CartItem> cartItems) {
        super(context, 0, cartItems);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        CartItem obj = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.adapter_cartitem, parent, false);
        }

        ImageView imgProduct = (ImageView) convertView.findViewById(R.id.imgCartItem);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.tx_productTitle);
        TextView txtPrice = (TextView) convertView.findViewById(R.id.tx_productPrice);

        txtTitle.setText(obj.productTitle);
        String price = getContext().getString(R.string.currency_symbol) + obj.productPrice;
        txtPrice.setText(price);

        Glide.with(getContext())
                .load(ApiConstants.PATH_PUBLIC_IMGS + obj.productFilename)
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProduct);

        return convertView;
    }
}
