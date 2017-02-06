package com.liferay.supermarketandroid.model.repository.sqlite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.liferay.supermarketandroid.App;
import com.liferay.supermarketandroid.util.SuperMarketLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * The type Super market sql helper.
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
public class SuperMarketSqlHelper extends SQLiteOpenHelper {

    // Update file must use this name "script_supermarket_from_[oldVersion]_to_[newVersion]"
    private static final String MASK_TO_SCRIPT_FILENAME = "script_supermarket_from_%d_to_%d.sql";
    private static final String DATABASE_NAME = "supermarket_database";
    private static final int VERSION = 5;
    private static final String SQL_CREATE = "create_script_supermarket.sql";
    private static final int INT_STEP = 1;

    private static final String REG_COMMENT_EXPRESSION = "--";
    private static final String REG_EXPRESSION = ";";

    /**
     * Instantiates a new Super market sql helper.
     */
    public SuperMarketSqlHelper() {
        super(App.getContext(), DATABASE_NAME, null, VERSION);
    }

    /**
     * Instantiates a new Super market sql helper.
     *
     * @param context the context
     */
    public SuperMarketSqlHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        executeSqlCommand(db, SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = oldVersion; i < newVersion; ++i) {
            @SuppressLint("DefaultLocale")
            String script = String.format(MASK_TO_SCRIPT_FILENAME, i, (i + INT_STEP));
            executeSqlCommand(db, script);
        }
    }

    private void executeSqlCommand(SQLiteDatabase sqLiteDatabase, String sqlFileName) {
        try {
            //Get all sql instructions from the SQL_NAME_ASSETS file.
            String[] sqlInstructions = getStatementSql(App.getContext(), sqlFileName);

            for (final String sql : sqlInstructions) {
                if (sql != null && !sql.isEmpty()) {
                    sqLiteDatabase.execSQL(sql);
                }
            }
        } catch (IllegalArgumentException e) {
            SuperMarketLog.print("There is a problem on sql:" + e.getMessage());
        }
    }

    /**
     * Get statement sql string [ ].
     *
     * @param context   the context
     * @param fileNames the file names
     * @return the string [ ]
     */
    public static String[] getStatementSql(Context context, final String fileNames) {
        final StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream;
        BufferedReader sqlFile = null;
        try {
            inputStream = context.getAssets().open(fileNames);
            final InputStreamReader reader = new InputStreamReader(inputStream,
                    Charset.defaultCharset());
            sqlFile = new BufferedReader(reader);
            String buffer;
            while ((buffer = sqlFile.readLine()) != null) {
                //Ignore comment in sql
                if (!buffer.startsWith(REG_COMMENT_EXPRESSION)) {
                    stringBuilder.append(buffer);
                }
            }

        } catch (final IOException e) {
            SuperMarketLog.printError("Error opening SQL file", e);
        } finally {
            if (sqlFile != null) {
                try {
                    sqlFile.close();
                } catch (final IOException e) {
                    SuperMarketLog.printError("Error closing SQL file", e);
                }
            }
        }

        return stringBuilder.toString().split(REG_EXPRESSION);
    }
}
