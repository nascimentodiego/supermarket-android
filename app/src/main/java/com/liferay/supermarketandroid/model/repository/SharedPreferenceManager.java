package com.liferay.supermarketandroid.model.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.liferay.supermarketandroid.App;
import com.liferay.supermarketandroid.util.StringUtils;

/**
 * The type Shared preference manager.
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
public class SharedPreferenceManager {

    private static SharedPreferenceManager instance;

    private static final String PREFERENCE_NAME = "super_marker_preference";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_USER_ID = "user_id";
    /**
     * The constant ZERO_CONSTANT.
     */
    public static final long ZERO_CONSTANT = 0;


    private static SharedPreferences getPreferences() {
        return App.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    private SharedPreferenceManager() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public synchronized static SharedPreferenceManager getInstance() {
        if (instance == null) {
            instance = new SharedPreferenceManager();
        }
        return instance;
    }

    /**
     * Gets access token.
     *
     * @return the access token
     */
    public String getAccessToken() {
        return getPreferences().getString(KEY_ACCESS_TOKEN, StringUtils.EMPTY_STRING);
    }

    /**
     * Sets access token.
     *
     * @param token the token
     */
    public void setAccessToken(String token) {
        getPreferences().edit().putString(KEY_ACCESS_TOKEN, token).commit();
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public long getUserId() {
        return getPreferences().getLong(KEY_USER_ID, ZERO_CONSTANT);
    }

    /**
     * Sets user id.
     *
     * @param id the id
     */
    public void setUserId(long id) {
        getPreferences().edit().putLong(KEY_USER_ID, id).commit();
    }

    /**
     * Clear.
     */
    public void clear() {
        setAccessToken(StringUtils.EMPTY_STRING);
        setUserId(ZERO_CONSTANT);
    }
}
