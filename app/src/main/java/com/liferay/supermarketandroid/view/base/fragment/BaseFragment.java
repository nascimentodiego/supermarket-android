package com.liferay.supermarketandroid.view.base.fragment;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * The type Base fragment.
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
public class BaseFragment extends Fragment {
    /**
     * The constant TIME_TO_CLICK.
     */
    protected static final long TIME_TO_CLICK = 500;
    /**
     * The Subscriptions.
     */
    protected List<Subscription> Subscriptions = new ArrayList<>();

    /**
     * Register subscription.
     *
     * @param subs the subs
     */
    protected void registerSubscription(Subscription... subs) {
        if (subs != null) {
            for (Subscription sub : subs) {
                Subscriptions.add(sub);
            }
        }
    }

    private void unRegisterSubscribe() {
        if (Subscriptions != null) {
            for (Subscription sub : Subscriptions) {
                if (sub != null && sub.isUnsubscribed()) {
                    sub.unsubscribe();
                }
            }
        }
    }


    /**
     * Unregister sbscription.
     *
     * @param subscription the subscription
     */
    protected void unregisterSubscription(Subscription subscription) {
        if (subscription != null) {
            if (subscription != null
                    && subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unRegisterSubscribe();
    }
}
