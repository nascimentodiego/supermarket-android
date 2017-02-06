package com.liferay.supermarketandroid.model.model;

/**
 * The type User.
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
public class User {
    /**
     * The Id.
     */
    public long id;
    /**
     * The Local id.
     */
    public long localId;
    /**
     * The Created at.
     */
    public long createdAt;
    /**
     * The Name.
     */
    public String name;
    /**
     * The Username.
     */
    public String username;
    /**
     * The Email.
     */
    public String email;
    /**
     * The Password.
     */
    public String password;
    /**
     * The Grant type.
     */
    public String grant_type;
}
