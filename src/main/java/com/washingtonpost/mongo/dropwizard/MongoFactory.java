package com.washingtonpost.mongo.dropwizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.washingtonpost.mongo.dropwizard.exceptions.NullDBNameException;
import java.net.UnknownHostException;
import org.apache.commons.lang3.StringUtils;
import sun.jvm.hotspot.utilities.CStringUtilities;

/**
 * An object of this class creates a single instance of the <code>MongoClient</code> object.
 * <p>
 * To use this class add it as a field with getters and setters to your Configuration class and call the buildClient
 * method in your applications run method. The resulting MongoClient can then be passed to your Resources.
 * <p>
 * An example of the yaml configuration:
 *     <pre>
 *     mongoClient:
 *         user: yourUser
 *         pass: yourPassword
 *         hosts: localhost:27017,192.168.1.12:27017
 *         dbName: foo
 *     </pre>
 */
public class MongoFactory {

    /**
     * Optional Username used to connect; if provided with a non-null password, an attempt to authenticate
     * to the provided {@code dbName} will be made.
     */
    @JsonProperty
    private String user;

    /**
     * Optional Password associated with the user; if provided with a non-null user, an attempt to authenticate
     * to the provided {@code dbName} will be made.
     */
    @JsonProperty
    private String pass;

    /**
     * At least one, but possibly many comma-separated (server:port) pairs to which the user/pass should connect
     * See http://docs.mongodb.org/manual/reference/connection-string/
     */
    @JsonProperty
    private String hosts;

    /**
     * Optional name of the database. This property is required to use the dbBuild method.
     */
    @JsonProperty
    private String dbName;

    /**
     * Optional "?options" string to append to the end of the connection URI.
     * See http://docs.mongodb.org/manual/reference/connection-string/
     */
    @JsonProperty
    private String options;

    /**
     * Optional flag that prevents any actual connections to the Mongo DB
     */
    @JsonProperty
    private boolean disabled;

    /**
     * Optional prefix to add to protocol connection
     * mongodb+prefix:// 
     */
    @JsonProperty
    private String prefix;

    /**
     * The mongo API documentation for <a href="https://api.mongodb.org/java/current/com/mongodb/MongoClient.html">
     * MongoClient</a> states that there should only be one object per JVM, so this property is only set once.
     */
    private MongoClient mongoClient;

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getHosts() {
        return this.hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public String getOptions() {
        return this.options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
        this.dbName = "foo"; // to avoid an NPE when guice calls "buildDB()"... it doesn't matter what this is
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * <p>Builds the MongoClient from a set of connections specified in the configuration file.</p>
     * <p>If both the {@code user} and {@code pass} are non-null and non-empty, the {@code MongoClient} that this
     * method returns will attempt to authenticate against the Mongo DB.</p>
     * @return A Mongo API {@code MongoClient} object.
     * @throws UnknownHostException Thrown if the server can not be found.
     */
    public MongoClient buildClient() throws UnknownHostException {
        if (this.disabled) {
            return new MongoClient();
        }

        if (this.mongoClient != null) {
            return mongoClient;
        }

        this.mongoClient = new MongoClient(buildMongoClientURI());

        return this.mongoClient;
    }

    /**
     * Builds a Mongo {@code DB} object from connection and db info set in a configuration file.
     * @return A Mongo Java API {@code DB} object.
     * @throws java.net.UnknownHostException if the host is unknown
     */
    public DB buildDB() throws UnknownHostException {
        return buildDB(this.dbName);
    }

    /**
     * Builds a Mongo {@code DB} object from connection and db info set in a configuration file, but against whatever DB is
     * provided in the {@code dbName} argument.
     * @param dbName The name of the mongo DB to connect to.
     * @return A Mongo Java API {@code DB} object.
     * @throws java.net.UnknownHostException if the host is unknown
     */
    public DB buildDB(String dbName) throws UnknownHostException {
        if (dbName == null && !this.disabled) {
            throw new NullDBNameException();
        }

        Mongo client = buildClient();
        return client.getDB(dbName);
    }

    /**
     * Builds a Mongo {@code MongoDatabase} object from connection and db info set in a configuration file.
     * @return A Mongo Java API {@code MongoDatabase} object.
     * @throws java.net.UnknownHostException if the host is unknown
     */
    public MongoDatabase buildMongoDatabase() throws UnknownHostException {
        return buildMongoDatabase(this.dbName);
    }

    /**
     * Builds a Mongo {@code MongoDatabase} object from connection and db info set in a configuration file, but against whatever DB is
     * provided in the {@code dbName} argument.
     * @param dbName The name of the mongo DB to connect to.
     * @return A Mongo Java API {@code MongoDatabase} object.
     * @throws java.net.UnknownHostException if the host is unknown
     */
    public MongoDatabase buildMongoDatabase(String dbName) throws UnknownHostException {
        if (dbName == null && !this.disabled) {
            throw new NullDBNameException();
        }

        MongoClient client = buildClient();
        if (client == null) {
            return null;
        }

        return client.getDatabase(dbName);
    }

    // Visible for testing
    MongoClientURI buildMongoClientURI() {
        Preconditions.checkState((StringUtils.isEmpty(this.user) &&  StringUtils.isEmpty(this.pass))
                             || (!StringUtils.isEmpty(this.user) && !StringUtils.isEmpty(this.pass)),
                "If you define a Mongo user, you must also define a Mongo pass, and vice-versa");
        Preconditions.checkNotNull(this.hosts, "Must define Mongo 'hosts' property");

        StringBuilder uriString = new StringBuilder("mongodb");

        if(!StringUtils.isEmpty(this.prefix)) {
            uriString.append("+")
                    .append(this.prefix);
        }

        uriString.append("://");

        if (!StringUtils.isEmpty(this.user)) {
            uriString.append(this.user)
                    .append(":")
                    .append(this.pass)
                    .append("@");
        }
        uriString.append(this.hosts);

        if (!StringUtils.isEmpty(this.dbName)) {
            uriString.append("/")
                    .append(this.dbName);
        }

        if (!StringUtils.isEmpty(this.options)) {
            if (StringUtils.isEmpty(this.dbName)) {
                // If we didn't slap a "/" at the end of the hosts because there's no DBName defined,
                // we need to do that here before sticking the "?options" in the connection URI
                uriString.append("/");
            }
            uriString.append("?")
                    .append(this.options);
        }

        return new MongoClientURI(uriString.toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("MongoFactory[");
        sb.append("hosts:'").append(this.hosts).append("', ");
        sb.append("dbName:'").append(this.dbName).append("', ");
        sb.append("user:'").append(this.user).append("', ");
        sb.append("pass:'").append(this.pass.replaceAll(".", "x")).append("', ");
        sb.append("options:'").append(this.options).append("'");
        sb.append("]");
        return sb.toString();
    }

}
