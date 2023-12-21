package engineering.dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.User;
import org.bson.Document;


public class UserDAOMONGO implements UserDAO{

    private static final String USER_EMAIL = "userEmail";
    private static final String USER_NAME = "name";
    private static final String USER_PASSWORD = "password";
    private static final String USER_PREF = "pref";
    private static final String HOST = "localhost";
    private static final int PORT = 27017;



    public void insertUser(User user){
        MongoClient mongoClient = new MongoClient(HOST, PORT);
        MongoDatabase database = mongoClient.getDatabase("Spotify");
        MongoCollection<Document> collection = database.getCollection("User");

        Document document = new Document(USER_NAME, user.getNome())
                .append(USER_EMAIL, user.getEmail())
                .append(USER_PASSWORD, user.getPass())
                .append(USER_PREF, user.getPref());

        collection.insertOne(document);
        mongoClient.close();
    }
}
