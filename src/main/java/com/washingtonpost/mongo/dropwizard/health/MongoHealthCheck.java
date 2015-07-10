package com.washingtonpost.mongo.dropwizard.health;

import com.mongodb.Mongo;
import com.hubspot.dropwizard.guice.InjectableHealthCheck;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MongoHealthCheck extends InjectableHealthCheck {

    private final Mongo mongo;

    @Inject
    public MongoHealthCheck(Mongo mongo) {
        this.mongo = mongo;
    }

    @Override
    protected Result check() throws Exception {
        mongo.getDatabaseNames();
        return Result.healthy();
    }

    @Override
    public String getName() {
        return "mongo";
    }

}
