package com.liferay.supermarketandroid.model.api;

import com.liferay.supermarketandroid.util.SuperMarketLog;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * The type Service client.
 */
public class ServiceClient {

    /**
     * The constant API_BASE_URL_AUTH.
     */
    public static final String API_BASE_URL_AUTH = "http://auth.mobilesupermarket.wedeploy.io/";
    /**
     * The constant API_BASE_URL.
     */
    public static final String API_BASE_URL = "http://data.mobilesupermarket.wedeploy.io/";

    private static ServiceClient instance;

    private ServiceClient() {

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized ServiceClient getInstance() {
        if (instance == null) {
            instance = new ServiceClient();
        }

        return instance;
    }

    private static Retrofit.Builder builder_oauth =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL_AUTH)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    /**
     * Create service oauth s.
     *
     * @param <S>          the type parameter
     * @param serviceClass the service class
     * @return the s
     */
    public <S> S createServiceOauth(Class<S> serviceClass) {
        OkHttpClient.Builder httpClient = createOkHttp();
        httpClient.interceptors().add(chain -> {
            Response response = chain.proceed(chain.request());
            SuperMarketLog.print("Response: " + response.message());

            return response;
        });
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder_oauth.client(client).build();

        return retrofit.create(serviceClass);
    }

    /**
     * Create service s.
     *
     * @param <S>          the type parameter
     * @param serviceClass the service class
     * @return the s
     */
    public <S> S createService(Class<S> serviceClass) {
        OkHttpClient.Builder httpClient = createOkHttp();
        httpClient.interceptors().add(chain -> {
            Response response = null;
            try {
                response = chain.proceed(chain.request());
                SuperMarketLog.print("Response: " + response.message());
            } catch (Exception e) {
                throw e;
            }
            return response;
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();

        return retrofit.create(serviceClass);
    }


    /**
     * Create ok http ok http client . builder.
     *
     * @return the ok http client . builder
     */
    public OkHttpClient.Builder createOkHttp() {
        return new OkHttpClient.Builder();
    }

}
