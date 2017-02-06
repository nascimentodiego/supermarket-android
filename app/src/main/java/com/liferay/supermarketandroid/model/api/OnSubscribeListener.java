package com.liferay.supermarketandroid.model.api;

/**
 * Created by Diego on 14/01/2017.
 *
 * @param <T> the type parameter
 */
public interface OnSubscribeListener<T> {
    /**
     * On next.
     *
     * @param result the result
     */
//Observable<T> onNext(T result);
    void onNext(T result);

    /**
     * On error.
     *
     * @param throwable the throwable
     */
    void onError(Throwable throwable);

    /**
     * On completed.
     */
    void onCompleted();
}
