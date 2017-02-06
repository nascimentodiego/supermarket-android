package com.liferay.supermarketandroid;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.liferay.supermarketandroid.model.api.observable.GetAccessTokenObservableOauth;
import com.liferay.supermarketandroid.model.model.AccessToken;
import com.liferay.supermarketandroid.model.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;

import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Diego on 14/01/2017.
 */
@RunWith(AndroidJUnit4.class)
public class ApiOauthTest {

    private static final String TAG = ApiOauthTest.class.getSimpleName() ;
    private GetAccessTokenObservableOauth getAccessTokenObservable;

    /**
     * Use app context.
     *
     * @throws Exception the exception
     */
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.liferay.supermarketandroid", appContext.getPackageName());
    }

    /**
     * Test get access token.
     */
    @Test
    public void test_getAccessToken() {
        if (getAccessTokenObservable == null) {
            User user = new User();
            user.username = "dfn@email.com";
            user.password = "imortal2";
            user.grant_type = "password";

            getAccessTokenObservable = new GetAccessTokenObservableOauth(user);
        }
        TestSubscriber<AccessToken> testSubscriber = new TestSubscriber<>();

        getAccessTokenObservable.getObservable().subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
    }
}
