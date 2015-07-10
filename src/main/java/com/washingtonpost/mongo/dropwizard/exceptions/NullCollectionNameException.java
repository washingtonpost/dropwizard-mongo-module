package com.washingtonpost.mongo.dropwizard.exceptions;

/**
 * Exception thrown if the {@link com.washingtonpost.mongo.dropwizard.MongoFactory} attempts to build
 * a DBCollection object and the configured collection name is null.
 * 
 * Repurposed from https://github.com/eeb/dropwizard-mongo
 */
public class NullCollectionNameException extends RuntimeException {

    public NullCollectionNameException() {
        super("Attempt made to create a DBCollection object when the configured collection name was null or invalid");
    }
}
