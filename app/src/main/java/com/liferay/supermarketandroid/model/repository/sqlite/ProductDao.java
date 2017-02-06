package com.liferay.supermarketandroid.model.repository.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;

import com.liferay.supermarketandroid.model.model.Product;
import com.liferay.supermarketandroid.util.SuperMarketLog;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Product dao.
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
public class ProductDao {
    private SQLiteDatabase mDatabase;
    private static ProductDao intance;

    private ProductDao() {
        SuperMarketSqlHelper helper = new SuperMarketSqlHelper();
        mDatabase = helper.getWritableDatabase();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized ProductDao getInstance() {
        if (intance == null) {
            intance = new ProductDao();
        }
        return intance;
    }


    /**
     * Insert list products long.
     *
     * @param productList the product list
     * @return the long
     */
    public long insertListProducts(@NonNull List<Product> productList) {
        long insertedId = DatabaseConstants.DEFAULT_DB_INT;

        for (Product product : productList) {
            ContentValues values = getProductContentValues(product);
            if (values.size() > DatabaseConstants.EMPTY_LIST) {
                if (!existProduct(product)) {
                    insertedId += mDatabase.insert(DatabaseConstants.TB_PRODUCTS, null, values);
                } else {
                    updateProduct(product);
                }
            }
        }

        return insertedId;
    }

    /**
     * Update product int.
     *
     * @param product the product
     * @return the int
     */
    public int updateProduct(Product product) {

        int updatedRows = DatabaseConstants.DEFAULT_DB_INT;

        if (product != null) {
            ContentValues values = getProductContentValues(product);

            mDatabase.beginTransaction();
            try {
                updatedRows = mDatabase.update(DatabaseConstants.TB_PRODUCTS, values,
                        DatabaseConstants.FIELD_ID + " = ?", new String[]{String.valueOf(product.getLocalId())});
                mDatabase.setTransactionSuccessful();
            } finally {
                mDatabase.endTransaction();
            }
        }

        return updatedRows;
    }

    private ContentValues getProductContentValues(Product product) {
        ContentValues values = new ContentValues();

        if (product != null) {
//            values.put(DatabaseConstants.FIELD_LOCAL_ID, product.getLocalId());
            values.put(DatabaseConstants.FIELD_ID, product.getId());
            values.put(DatabaseConstants.FIELD_DESCRIPTION, product.getDescription());
            values.put(DatabaseConstants.FIELD_TITLE, product.getTitle());
            values.put(DatabaseConstants.FIELD_FILENAME, product.getFilename());
            values.put(DatabaseConstants.FIELD_PRICE, product.getPrice());
            values.put(DatabaseConstants.FIELD_RATING, product.getRating());
            values.put(DatabaseConstants.FIELD_TYPE, product.getType());
        }

        return values;
    }

    /**
     * Exist product boolean.
     *
     * @param prod the prod
     * @return the boolean
     */
    public boolean existProduct(Product prod) {

        boolean thereIsProduct = false;
        Cursor cursor = mDatabase.query(DatabaseConstants.TB_PRODUCTS, null,
                DatabaseConstants.FIELD_ID + DatabaseConstants.CLAUSE_WHERE, new
                        String[]{String.valueOf(prod.getId())}, null, null, null);

        if (cursor != null) {
            thereIsProduct = cursor.moveToFirst();
            cursor.close();
        }

        return thereIsProduct;
    }


    /**
     * Gets products.
     *
     * @param type the type
     * @return the products
     */
    public List<Product> getProducts(String type) {
        List<Product> productList = null;
        Cursor cursor = null;
        try {
            String where = type != null ? DatabaseConstants.FIELD_TYPE + DatabaseConstants.CLAUSE_WHERE : null;
            String[] values = type != null ? new String[]{String.valueOf(type)} : null;

            cursor = mDatabase.query(DatabaseConstants.TB_PRODUCTS, null, where, values,
                    null, null, null);
            productList = getProductsFromCursor(cursor);
        } catch (SQLiteException exception) {
            SuperMarketLog.printError("Error get message", exception);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return productList;
    }

    private List<Product> getProductsFromCursor(Cursor cursor) {
        List<Product> productList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DatabaseConstants.FIELD_ID);
            int idLocalIndex = cursor.getColumnIndex(DatabaseConstants.FIELD_LOCAL_ID);
            int descriptionIndex = cursor.getColumnIndex(DatabaseConstants.FIELD_DESCRIPTION);
            int titleIndex = cursor.getColumnIndex(DatabaseConstants.FIELD_TITLE);
            int typeIndex = cursor.getColumnIndex(DatabaseConstants.FIELD_TYPE);
            int ratingIndex = cursor.getColumnIndex(DatabaseConstants.FIELD_RATING);
            int filenameIndex = cursor.getColumnIndex(DatabaseConstants.FIELD_FILENAME);
            int priceIndex = cursor.getColumnIndex(DatabaseConstants.FIELD_PRICE);

            do {
                long id = cursor.getLong(idIndex);
                long localId = cursor.getLong(idLocalIndex);
                String title = cursor.getString(titleIndex);
                String type = cursor.getString(typeIndex);
                String description = cursor.getString(descriptionIndex);
                String filename = cursor.getString(filenameIndex);
                int rating = cursor.getInt(ratingIndex);
                double price = cursor.getLong(priceIndex);

                Product product = new Product();
                product.setId(id);
                product.setLocalId(localId);
                product.setTitle(title);
                product.setDescription(description);
                product.setFilename(filename);
                product.setType(type);
                product.setRating(rating);
                product.setPrice(price);

                productList.add(product);

                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        return productList;
    }

    /**
     * Gets product by id.
     *
     * @param prod the prod
     * @return the product by id
     */
    public Product getProductById(Product prod) {
        Cursor cursor = mDatabase.query(DatabaseConstants.TB_PRODUCTS, null,
                DatabaseConstants.FIELD_ID + DatabaseConstants.CLAUSE_WHERE, new
                        String[]{String.valueOf(prod.getId())}, null, null, null);
        Product product = null;
        if (cursor != null && cursor.moveToFirst()) {
            product = new Product();

            int idIndex = cursor.getColumnIndex(DatabaseConstants.FIELD_ID);
            int idLocalIndex = cursor.getColumnIndex(DatabaseConstants.FIELD_LOCAL_ID);
            int descriptionIndex = cursor.getColumnIndex(DatabaseConstants.FIELD_DESCRIPTION);
            int titleIndex = cursor.getColumnIndex(DatabaseConstants.FIELD_TITLE);
            int typeIndex = cursor.getColumnIndex(DatabaseConstants.FIELD_TYPE);
            int ratingIndex = cursor.getColumnIndex(DatabaseConstants.FIELD_RATING);
            int filenameIndex = cursor.getColumnIndex(DatabaseConstants.FIELD_FILENAME);
            int priceIndex = cursor.getColumnIndex(DatabaseConstants.FIELD_PRICE);

            do {
                long id = cursor.getLong(idIndex);
                long localId = cursor.getLong(idLocalIndex);
                String title = cursor.getString(titleIndex);
                String type = cursor.getString(typeIndex);
                String description = cursor.getString(descriptionIndex);
                String filename = cursor.getString(filenameIndex);
                int rating = cursor.getInt(ratingIndex);
                double price = cursor.getLong(priceIndex);


                product.setId(id);
                product.setLocalId(localId);
                product.setTitle(title);
                product.setDescription(description);
                product.setFilename(filename);
                product.setType(type);
                product.setRating(rating);
                product.setPrice(price);

                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        if (cursor != null) {
            cursor.close();
        }
        return product;
    }


}
