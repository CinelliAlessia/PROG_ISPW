package logic;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/*Ecco alcuni concetti chiave:

Documenti: Ogni elemento in una collezione è un documento. Un documento è un insieme di coppie chiave-valore, dove i valori possono essere stringhe, numeri, array, altri documenti, ecc.

Collezioni: Una collezione è un insieme di documenti. I documenti in una collezione non devono avere uno schema fisso, il che significa che i documenti nella stessa collezione possono avere campi diversi.

Database: Un database può contenere molte collezioni. Un database è un contenitore fisico per collezioni di dati.*/

public class MongoDBConnector {
    public static void main(String[] args) {
        // Crea un oggetto MongoClient
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        // Ottieni un oggetto MongoDatabase
        MongoDatabase database = mongoClient.getDatabase("Spotify");

        // Ottieni una collezione (sostituisci "miaCollezione" con il nome desiderato)
        MongoCollection<Document> collection = database.getCollection("User");
        /*In MongoDB, una collezione è un raggruppamento di documenti. Puoi pensare a una collezione come a un
        contenitore logico di documenti,7 dove ogni documento rappresenta un record indipendente di dati. Le collezioni
        sono l'equivalente dei tavoli in un database relazionale.*/

        // Crea un nuovo documento da inserire nella collezione
        Document document = new Document("nome", "Alessia")
                .append("cognome", "Doe")
                .append("età", 30)
                .append("email", "john.doe@example.com");

        // Inserisci il documento nella collezione
        collection.insertOne(document);

        // Chiudi la connessione
        mongoClient.close();
    }
}
