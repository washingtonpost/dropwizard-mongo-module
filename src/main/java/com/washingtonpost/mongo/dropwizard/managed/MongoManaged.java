package com.washingtonpost.mongo.dropwizard.managed;

import com.mongodb.Mongo;
import io.dropwizard.lifecycle.Managed;
import javax.inject.Inject;

/**
 * <p>"Managed" wrapper of the Mongo client (bridges the Dropwizard lifecycle with MongoClient 
 * startup/shutdown controls</p>
 */
public class MongoManaged implements Managed {

    private final Mongo mongo;

    @Inject
    public MongoManaged(Mongo mongo) {
        this.mongo = mongo;
    }
    
    @Override
    public void start() throws Exception {
        // Nothing needed here because initialization of the client is done on construction
    }

    @Override
    public void stop() throws Exception {
        this.mongo.close();
    }
}
