package com.ilyane.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

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
	
	public ArrayList<String> getFields(String collection)
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
        Map<String, String> fieldsType = new HashMap<>();

        this.getMongoDatabase().getCollection(collection).find().forEach(x -> {
            x.keySet().forEach(y -> {
                if (!fieldsType.containsKey(y)) {
                    fieldsType.put(y, x.get(y) == null ? "null" : x.get(y).getClass().getName());
                }
            });
        });

        return fieldsType;
    }

    public long deleteDocument(String collection, Document document)
    {
        return this.getMongoDatabase().getCollection(collection).deleteMany(document).getDeletedCount();
    }

    private MongoDatabase getMongoDatabase() {
		
		return null;
	}

	public long deleteDocument(String collection, FindIterable<org.bson.Document> documents)
    {
        long result = 0;

        for (org.bson.Document document: documents) {
            result += deleteDocument(collection, document);
        }

        return result;
    }

    public long updateDocument(String collection)
    {
        Bson value = null;
		return this.getMongoDatabase().getCollection(collection).updateMany(Filters.eq(updateDocument(null)), value).getModifiedCount();
    }
   
    
    
} 
