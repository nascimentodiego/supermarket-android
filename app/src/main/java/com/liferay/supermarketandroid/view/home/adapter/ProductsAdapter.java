package com.liferay.supermarketandroid.view.home.adapter;

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
import com.liferay.supermarketandroid.model.model.Product;
import com.liferay.supermarketandroid.util.StringUtils;

import java.util.List;

/**
 * The type Products adapter.
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
public class ProductsAdapter extends ArrayAdapter<Product> {

    /**
     * Instantiates a new Products adapter.
     *
     * @param context     the context
     * @param productList the product list
     */
    public ProductsAdapter(Context context, List<Product> productList) {
        super(context, 0, productList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product obj = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.adapter_products, parent, false);
        }

        ImageView imgProduct = (ImageView) convertView.findViewById(R.id.imgProduct);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.tx_productTitle);
        TextView txtPrice = (TextView) convertView.findViewById(R.id.tx_productPrice);

        txtTitle.setText(obj.getTitle());
        String valueFormat = getContext().getString(R.string.currency_symbol) +
                StringUtils.formatCurrency(obj.getPrice());
        txtPrice.setText(valueFormat);

        Glide.with(getContext())
                .load(ApiConstants.PATH_PUBLIC_IMGS + obj.getFilename())
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProduct);

        return convertView;
    }
}
