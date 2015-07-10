package com.washingtonpost.mongo.dropwizard;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.DB;
import com.mongodb.Mongo;
import java.net.UnknownHostException;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Guice module for providing Mongo objects based on Dropwizard configuration classes</p>
 */
public class MongoModule extends AbstractModule {
    private static final Logger logger = LoggerFactory.getLogger(MongoModule.class);

    private Mongo mongo;
    private DB db;

    @Override
    protected void configure() {
    }

    /**
     * @param factory The application's Mongo factory
     * @return The Mongo client, given the connection parameters defined in {@code configuration}
     * @throws UnknownHostException Thrown if the server can not be found.
     */
    @Provides
    @Named("mongo")
    public Mongo provideMongo(@Named("mongoFactory") MongoFactory factory) throws UnknownHostException {
        if (mongo == null) {
            mongo = factory.buildClient();
        }
        return mongo;
    }

    /**
     * @param factory The application's Mongo configuration
     * @return Assuming the application has been configured with a property for dbName, this method will
     * invoke the "getDB( )" on the Mongo client and return the provided DB.
     * @throws UnknownHostException Thrown if the server can not be found.
     */
    @Provides
    @Named("db")
    public DB provideMongoDB(@Named("mongoFactory") MongoFactory factory) throws UnknownHostException {
        logger.info("MongoFactory configuration = {}", factory);
        if (db == null) {
            db = factory.buildDB();
        }
        return db;
    }
}
