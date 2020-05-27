package com.ilyane.java;

//import java.util.ArrayList;
import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoDatabase;

public class MongoConnection {
    
	public static MongoClient getMongoClient(final String host, final int port)
    {
    MongoClient mongoClient = MongoClients.create(
            MongoClientSettings.builder()
                    .applyToClusterSettings(builder ->
                            builder.hosts(Arrays.asList(new ServerAddress(host, port))))
                    .build());
    return mongoClient;


    }
	
/*	public ArrayList<String> getFields(String collection)
    {
        ArrayList<String> fields = new ArrayList<>();

        this.getMongoDatabase().getCollection(collection).find().forEach(x -> {
            x.keySet().forEach(y -> {
                if (!fields.contains(y)) {
                    fields.add(y);
                }
            });
        });

        return fields;
    }

    public Map<String, String> getFieldsType(String collection)
    {
        ArrayList<String> fields = new ArrayList<>();
        Map<String, String> fieldsType = new HashMap<>();

        this.getMongoDatabase().getCollection(collection).find().forEach(x -> {
            x.keySet().forEach(y -> {
                if (!fields.contains(y)) {
                    fields.add(y);
                    fieldsType.put(y, x.get(y) == null ? "null" : x.get(y).getClass().getName());
                }
            });
        });

        return fieldsType;
    } */
    
    
} 
