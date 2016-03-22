package com.washingtonpost.mongo.dropwizard;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.net.UnknownHostException;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Guice module for providing Mongo objects based on Dropwizard configuration classes</p>
 */
public class MongoModule extends AbstractModule {
    private static final Logger logger = LoggerFactory.getLogger(MongoModule.class);

    private MongoClient mongoClient;
    private DB db;
    private MongoDatabase database;

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
        if (mongoClient == null) {
            mongoClient = factory.buildClient();
        }
        return mongoClient;
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
        if (db == null) {
            logger.info("Building new Mongo DB instance with MongoFactory configuration = {}", factory);
            db = factory.buildDB();
        }
        return db;
    }


    /**
     * @param factory The application's Mongo configuration
     * @return Assuming the application has been configured with a property for dbName, this method will
     * invoke the "getDatabase( )" on the Mongo client and return the provided Database, using
     * the Mongo 3.0 API.
     * @throws UnknownHostException Thrown if the server can not be found.
     */
    @Provides
    @Named("mongoDatabase")
    public MongoDatabase provideDatabase(@Named("mongoFactory") MongoFactory factory) throws UnknownHostException {
        if (database == null) {
            logger.info("Building new Mongo DB instance with MongoFactory configuration = {}", factory);
            database = factory.buildMongoDatabase();
        }
        return database;
    }


}
