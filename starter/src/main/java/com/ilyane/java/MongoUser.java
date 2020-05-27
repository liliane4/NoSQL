package com.ilyane.java;

import java.awt.Menu;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.bson.BsonDocument;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.MongoCollection;



public class MongoUser {
	
	private MongoDatabase database;
	
	public MongoUser(MongoClient client, String database)
	{
		this.database = client.getDatabase(database);
	}

	public void connection() {
	MongoIterable <String> collections = database.listCollectionNames();
    
    for (String collectionNames: collections)
    {
        System.out.println(collectionNames);
    }
    
    boolean choose = false;
    
    String chooseCollection = "";
    
	Scanner sc = new Scanner(System.in);
	
	while(!choose)
    {
        System.out.println("Choisir une collection, ou mettre 'fin' :");
        chooseCollection = sc.nextLine();
        
        for (String collectionNames: collections)
        {
            if(chooseCollection.equals(collectionNames))
            {
                System.out.println("Choisi : " + chooseCollection);
                choose = true;
            }
            else if(chooseCollection.equals("fin"))
            {
            	System.out.println("fin");
            	choose = true;
            }
            else
            {
            	System.out.println("Vous ne pouvez pas choisir de collection.");
            	choose = false;
            }
        }
    }
	
    
	MongoCollection<Document> collection = database.getCollection(chooseCollection);
    
	choose = false;
	
	while(!choose)
    {
        System.out.println("Taper 'rechercher un document', ou 'insérer un document', ou 'fin'.");
         String choix = sc.nextLine();
        
        	if(choix.equals("rechercher un document"))
            {
                System.out.println("Choisi de rechercher un document");
                choose = true;
            }
            else if(chooseCollection.equals("insérer un document"))
            {
            	System.out.println("Choisi d'insérer un document");
            	choose = true;
            }
            else if(chooseCollection.equals("fin"))
            {
            	System.out.println("fin");
            	choose = true;
            }
            else
            {
            	System.out.println("Choix n'existe pas.");
            	choose = false;
            } 

    }
}

public void searchDocument(MongoCollection<Document> collection)
{
	FindIterable<Document> documents = collection.find();
	ArrayList<String> availableKeys = new ArrayList<>();;
	
	for(Document doc : documents)
	{
		for(String key : doc.keySet())
		{
			if(availableKeys.contains(key))
			{
				availableKeys.add(key);
				System.out.println(key);
			}
		}
	}
	
	Scanner sc = new Scanner(System.in);
	
	boolean choose = false;
	String chooseField = "";
	
	while(!choose)
    {
		chooseField = sc.nextLine();

		if(availableKeys.contains(chooseField))
		{
			System.out.printf("Vous avez choisi %s.", chooseField);
			choose = true;
		}
		else
		{
			System.out.println("Le champ choisi n'existe pas. Veuillez réessayer.");
			choose = false;
		}
    }
	
	choose = false;
	String chooseOperators = "";
	
	String operators [] = {"inf", "inf egal", "sup", "sup egal", "string egal", "boolean egal", "boolean non egal"};
	
	System.out.println("Veuillez choisir un opérateur parmis ceux ci-dessous :");
	
	for(int i = 0 ; i < operators.length ; i++)
	{
		System.out.printf("%s \n", operators[i]);
	}
	
	chooseOperators = sc.nextLine();
	
	String Operators = "";
	
			if(chooseOperators.equals("inf"))
	    	{
	    		Operators = "$lt";
	    	}
	    	
	    	if(chooseOperators.equals("inf egal"))
	    	{
	    		Operators = "$lte";
	    	}
	    	
	    	if(chooseOperators.equals("sup"))
	    	{
	    		Operators = "$gt";
	    	}
	    	
	    	if(chooseOperators.equals("sup egal"))
	    	{
	    		Operators = "$gte";
	    	}
	    	
	    	if(chooseOperators.equals("egal"))
	    	{
	    		operators = "$eq";
	    	}
	    	
	    	if(chooseOperators.equals("bool egal"))
	    	{
	    		operators = "=";
	    	}
	    	
	    	if(chooseOperators.equals("bool non egal"))
	    	{
	    		operators = "!=";
	    	}
	 
	System.out.println("Quelle est la valeur avec laquelle vous souhaitez comparer ?");
	
	String Compare = "";
	
	BasicDBObject query = null;
	
	if(!operators.equals("egal") || !operators.equals("true") || !operators.equals("false"))
	{
		query = new BasicDBObject(chooseField,
                new BasicDBObject(operators, Compare));
	}
	else
	{
		if(operators.equals("egal"))
		{
			
		}
		else if(operators.equals("true"))
		{
			
		}
		else if(operators.equals("false"))
		{
			
		}
	}
	
	Class<?> cls = null;
    try
    {
    	cls = Class.forName(fieldsType.get(chooseField));
    }
    catch (ClassNotFoundException e)
    {
    	e.printStackTrace();
    	cls = String.class;
    }
    
	BsonDocument query = null;
	    	
	if(cls == String.class)
	{
		query = new BsonDocument(chooseField, new BsonDocument(operators, new BsonString(Compare)));
	}
	else if(cls == Integer.class)
	{
		int dataInt = Integer.parseInt(Compare);
		query = new BsonDocument(chooseField, new BsonDocument(operators, new BsonInt32(dataInt)));
	}
	else if(cls == Boolean.class)
	{
		boolean dataBoolean = Boolean.parseBoolean(Compare);
		query = new BsonDocument(chooseField, new BsonDocument(operators, new BsonBoolean(dataBoolean)));
	}
	
	FindIterable<Document> resultats = collection.find(query);
	
	System.out.println("Recherche en cours...");
	
	for(Document result : resultats)
	{
		System.out.println(resultats);
		
	}

}

public void insertDocument(MongoCollection<Document> collection)
{
	FindIterable<Document> documents = collection.find();
	ArrayList<String> availableKeys = new ArrayList<>();;
	
    
	String insert = "Insert";

    for(Document doc : documents)
    	
    	if (result.equals(insert)) {
        insertDocument(collection);
    } 		else if (insertDocument("Continue to insert ?").equals("yes")) {
            insertDocument(collection);
        }  
	
}



}
