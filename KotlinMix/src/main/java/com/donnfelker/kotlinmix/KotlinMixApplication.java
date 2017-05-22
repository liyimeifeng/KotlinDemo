package com.donnfelker.kotlinmix;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class KotlinMixApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        /**
         * 使用realm数据库，一种替代SQLite的数据库，轻量级
         */
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .name("kotlinmix.realm")
                .build();

        Realm.setDefaultConfiguration(config);

        Realm.deleteRealm(config);
    }
}
