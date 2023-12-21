package engineering.dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.User;
import org.bson.Document;


public class UserDAOmongo {

    private static final String USER_EMAIL = "userEmail";
    private static final String USER_NAME = "name";



    public void insertUser(User user){
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("Spotify");
        MongoCollection<Document> collection = database.getCollection("User");

        Document document = new Document("nome", "Alessia")
                .append("cognome", "Doe")
                .append("età", 30)
                .append("email", "john.doe@example.com");

        collection.insertOne(document);
        mongoClient.close();
    }
}
