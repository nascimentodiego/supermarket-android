package com.liferay.supermarketandroid.util;

import android.util.Log;

import com.liferay.supermarketandroid.BuildConfig;

/**
 * The type Super market log.
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
public class SuperMarketLog {
    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final String DEFAULT_TAG = "supermaket";

    /**
     * Print.
     *
     * @param tag     the tag
     * @param message the message
     */
    public static void print(String tag, String message) {
        if (DEBUG) {
            Log.i(tag, message);
        }
    }

    /**
     * Print.
     *
     * @param message the message
     */
    public static void print(String message) {
        if (DEBUG) {
            Log.i(DEFAULT_TAG, message);
        }
    }

    /**
     * Print error.
     *
     * @param tag the tag
     * @param e   the e
     */
    public static void printError(String tag, Exception e) {
        if (DEBUG) {
            Log.e(tag, e.getMessage());
        }
    }
}
