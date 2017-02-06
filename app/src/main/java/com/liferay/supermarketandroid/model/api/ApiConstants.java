package com.liferay.supermarketandroid.model.api;


/**
 * The type Api constants.
 */
public class ApiConstants {

    /**
     * The constant FIELD_USERNAME.
     */
//Constant's to Authentication
    public static final String FIELD_USERNAME = "username";
    /**
     * The constant FIELD_NAME.
     */
    public static final String FIELD_NAME = "name";
    /**
     * The constant FIELD_EMAIL.
     */
    public static final String FIELD_EMAIL = "email";
    /**
     * The constant FIELD_PASSWORD.
     */
    public static final String FIELD_PASSWORD = "password";
    /**
     * The constant FIELD_GRANT_TYPE.
     */
    public static final String FIELD_GRANT_TYPE = "grant_type";
    /**
     * The constant GRANT_TYPE.
     */
    public static final String GRANT_TYPE = "password";

    /**
     * The constant FIELD_PRODUCT_TITLE.
     */
//Constant's to cart
    public static final String FIELD_PRODUCT_TITLE = "productTitle";
    /**
     * The constant FIELD_PRODUCT_PRICE.
     */
    public static final String FIELD_PRODUCT_PRICE = "productPrice";
    /**
     * The constant FIELD_PRODUCT_FILENAME.
     */
    public static final String FIELD_PRODUCT_FILENAME = "productFilename";
    /**
     * The constant FIELD_PRODUCT_ID.
     */
    public static final String FIELD_PRODUCT_ID = "productId";
    /**
     * The constant FIELD_USER_ID.
     */
    public static final String FIELD_USER_ID = "userId";

    /**
     * The constant API_FILTER_BAKERY.
     */
    public static final String API_FILTER_BAKERY = "%7B%22type%22%3A%22bakery%22%7D";
    /**
     * The constant API_FILTER_FRUIT.
     */
    public static final String API_FILTER_FRUIT = "%7B%22type%22%3A%22fruit%22%7D";
    /**
     * The constant API_FILTER_DAIRY.
     */
    public static final String API_FILTER_DAIRY = "%7B%22type%22%3A%22dairy%22%7D";
    /**
     * The constant API_FILTER_VEGETABLE.
     */
    public static final String API_FILTER_VEGETABLE = "%7B%22type%22%3A%22vegetable%22%7D";
    /**
     * The constant API_FILTER_MEAT.
     */
    public static final String API_FILTER_MEAT = "%7B%22type%22%3A%22meat%22%7D";


    /**
     * The constant PATH_CART.
     */
//Path's
    public static final String PATH_CART = "cart";

    /**
     * The constant PATH_PUBLIC_IMGS.
     */
    public static final String PATH_PUBLIC_IMGS
            = "http://public.mobilesupermarket.wedeploy.io/assets/images/";

    /**
     * Gets value by type url encode.
     *
     * @param urlScapeEncode the url scape encode
     * @return the value by type url encode
     */
    public static String getValueByTypeUrlEncode(String urlScapeEncode) {
        if (urlScapeEncode == null)
            return null;

        if (urlScapeEncode.equals(API_FILTER_BAKERY))
            return "bakery";

        if (urlScapeEncode.equals(API_FILTER_FRUIT))
            return "fruit";

        if (urlScapeEncode.equals(API_FILTER_DAIRY))
            return "dairy";

        if (urlScapeEncode.equals(API_FILTER_VEGETABLE))
            return "vegetable";

        if (urlScapeEncode.equals(API_FILTER_MEAT))
            return "meat";

        return null;
    }


}
