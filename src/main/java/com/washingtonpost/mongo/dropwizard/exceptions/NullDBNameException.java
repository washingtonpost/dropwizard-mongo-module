package com.washingtonpost.mongo.dropwizard.exceptions;

/**
 * Exception thrown if the {@link com.washingtonpost.mongo.dropwizard.MongoFactory} attempts to build
 * a DB object and the configured database name is null.
 * 
 * Repurposed from https://github.com/eeb/dropwizard-mongo
 */
public class NullDBNameException extends RuntimeException {

    public NullDBNameException() {
        super("Attempt made to create a DB object when the configured database name was null or invalid");
    }
}