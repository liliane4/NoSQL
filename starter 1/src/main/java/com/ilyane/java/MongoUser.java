package com.ilyane.java;

import java.awt.Menu;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
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
	
	String operatores = "";
	
			if(chooseOperators.equals("inf"))
	    	{
	    		operatores = "$lt";
	    	}
	    	
	    	if(chooseOperators.equals("inf egal"))
	    	{
	    		operatores = "$lte";
	    	}
	    	
	    	if(chooseOperators.equals("sup"))
	    	{
	    		operatores = "$gt";
	    	}
	    	
	    	if(chooseOperators.equals("sup egal"))
	    	{
	    		operatores = "$gte";
	    	}
	    	
	    	if(chooseOperators.equals("boolean"))
	    	{
	    		operatores = "$eq";
	    	}
	    	if(chooseOperators.equals("boolean"))
	    	{
	    		operatores = "$ne";
	    	}
	 
	System.out.println("Quelle est la valeur avec laquelle vous souhaitez comparer ?");
	
	String Compare = "";
	
	BasicDBObject query = null;
	
/*	if(!operators.equals("egal") || !operators.equals("true") || !operators.equals("false"))
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
	} */
	
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
	Bson filter = getFilter(field, cls);
	    	
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

public void Operation(String collection, FindIterable<Document> documentsFound) {
    
	ArrayList<String> menu = new ArrayList<String>(){
		{
	        add("Delete All selected documents");
	        add("Delete One document selected");
	        add("Update all documents selected");
	        add("Update One document selected");
		}
	};

    Integer resultat;

    long numberChange = 0;

    switch (resultat) {
        
    	case 1:
            numberChange = Mongo.deleteDocument(collection, documentsFound);
            System.out.println( numberChange + " document deleted.");
            break;
        
    	case 2:
            ArrayList<String> StringItem = new ArrayList<>();
            documentsFound.forEach(x -> { StringItem.add(x.toJson()); 
            });

            String itemDelete = Mongo.numberMongoUser(StringItem, "What document do you want to delete ?");
            numberChange = Mongo.deleteDocument(collection, Document.parse(itemDelete));
            System.out.println( numberChange + " document deleted.");
            break;
        
    	case 3:
    		String itemToUpdate = Menu.numberMenuUser(listStringItem, "Which document do you want to update ?");
            documentToUpdate = Document.parse(itemToUpdate);
            break;
        case 4:
        	String fieldToUpdate = Menu.numberMenuUser(mongos.getFields(collection), "Which field do you want to update ?");
            String value = Menu.numberMenuUser(mongos.getFields(collection), "Which value ?");
            break;
            
            Document filter = new Document();
            filter.append("$set", 33);
            

            if (documentToUpdate != null)
            {
                numberItemsChange = mongos.updateDocument(collection, documentToUpdate, null);
            } else {
                numberItemsChange = mongos.updateDocument(collection, documentsFound,null);
            }

            System.out.println( numberItemsChange + " documents updated.");
            break;
        case 0:
        default:
            break;
    }
}

public Bson searchString(String field)
{
    ArrayList<String> operators = new ArrayList<String>() {{
        add("$eq"); add("$ne");
    }};

    String operator = Menu.numberMenuUser(operators, "Which operator do you want to use ?");
    String value = ScannerSingleton.getInstance().getInputString("Insert value");
    value = ScannerSingleton.getInstance().getInput();

    Bson filter;
    switch (operator) {
        case "$eq":
            filter = Filters.eq(field, value);
            break;
        case "$ne":
            filter = Filters.ne(field, value);
            break;
        default:
            filter = null;
            break;
    }

    return filter;
}

public Bson searchInteger(String field)
{
    ArrayList<String> operators = new ArrayList<String>() {{
        add("$eq"); add("$ne"); add("$in"); add("$lt"); add("$lte"); add("$gt"); add("$gte");
    }};

    String operator = Menu.numberMenuUser(operators, "Which operator do you want to use ?");
    Integer value = ScannerSingleton.getInstance().getInputNumber("Insert value");

    Bson filter = null;

    switch (operator) {
        case "$eq":
            filter = Filters.eq(field, value);
            break;
        case "$ne":
            filter = Filters.ne(field, value);
            break;
        case "$in":
            filter = Filters.in(field, value);
            break;
        case "$lt":
            filter = Filters.lt(field, value);
            break;
        case "$lte":
            filter = Filters.lte(field, value);
            break;
        case "$gt":
            filter = Filters.gt(field, value);
            break;
        case "$gte":
            filter = Filters.gte(field, value);
            break;
    }

    return filter;
}

public Bson searchDouble(String field)
{
    ArrayList<String> operators = new ArrayList<String>() {{
        add("$eq"); add("$ne"); add("$in"); add("$lt"); add("$lte"); add("$gt"); add("$gte");
    }};

    String operator = Menu.numberMenuUser(operators, "Which operator do you want to use ?");
    Double value = ScannerSingleton.getInstance().getInputDouble("Insert value");

    Bson filter = null;

    switch (operator) {
        case "$eq":
            filter = Filters.eq(field, value);
            break;
        case "$ne":
            filter = Filters.ne(field, value);
            break;
        case "$in":
            filter = Filters.in(field, value);
            break;
        case "$lt":
            filter = Filters.lt(field, value);
            break;
        case "$lte":
            filter = Filters.lte(field, value);
            break;
        case "$gt":
            filter = Filters.gt(field, value);
            break;
        case "$gte":
            filter = Filters.gte(field, value);
            break;
    }

    return filter;
}

private Bson searchBoolean(String field)
{
    ArrayList<String> operators = new ArrayList<String>() {{
        add("$eq"); add("$ne");
    }};

    String operator = Menu.numberMenuUser(operators, "Which operator do you want to use ?");
    Boolean value = Boolean.valueOf(ScannerSingleton.getInstance().getInputString("Insert value"));
    value = Boolean.valueOf(ScannerSingleton.getInstance().getInput());

    Bson filter = null;

    switch (operator) {
        case "$eq":
            filter = Filters.eq(field, value);
            break;
        case "$ne":
            filter = Filters.ne(field, value);
            break;
    }

    return filter;
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
