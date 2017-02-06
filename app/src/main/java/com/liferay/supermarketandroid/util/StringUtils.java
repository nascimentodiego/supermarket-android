package com.liferay.supermarketandroid.util;

import java.text.DecimalFormat;

/**
 * The type String utils.
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
public class StringUtils {
    /**
     * The constant EMPTY_STRING.
     */
    public static final String EMPTY_STRING = "";
    /**
     * The constant VALUE_DEFAULT_TO_CURRENCY.
     */
    public static final String VALUE_DEFAULT_TO_CURRENCY = "#0.00";


    /**
     * Format currency string.
     *
     * @param value the value
     * @return the string
     */
    public static String formatCurrency(double value) {
        DecimalFormat twoPlaces = new DecimalFormat(VALUE_DEFAULT_TO_CURRENCY);
        return twoPlaces.format(value);
    }


}
