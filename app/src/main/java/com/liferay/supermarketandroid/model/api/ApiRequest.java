package com.liferay.supermarketandroid.model.api;


import com.liferay.supermarketandroid.model.model.AccessToken;
import com.liferay.supermarketandroid.model.model.User;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * The interface Api request.
 */
public interface ApiRequest {

    /**
     * Gets access token.
     *
     * @param username   the username
     * @param password   the password
     * @param grant_type the grant type
     * @return the access token
     */
//OAuth API
    @FormUrlEncoded
    @POST("oauth/token")
    Observable<AccessToken> getAccessToken(@Field(ApiConstants.FIELD_USERNAME) String username,
                                           @Field(ApiConstants.FIELD_PASSWORD) String password,
                                           @Field(ApiConstants.FIELD_GRANT_TYPE) String grant_type);

    /**
     * Post user observable.
     *
     * @param name     the name
     * @param email    the email
     * @param password the password
     * @return the observable
     */
    @FormUrlEncoded
    @POST("users")
    Observable<User> postUser(
            @Field(ApiConstants.FIELD_NAME) String name,
            @Field(ApiConstants.FIELD_EMAIL) String email,
            @Field(ApiConstants.FIELD_PASSWORD) String password);

    /**
     * Gets user.
     *
     * @param authorization the authorization
     * @return the user
     */
    @GET("user")
    Observable<User> getUser(@Header("Authorization") String authorization);


}
