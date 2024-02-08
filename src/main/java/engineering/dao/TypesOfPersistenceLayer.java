package engineering.dao;

import controller.applicativo.RegistrazioneCtrlApplicativo;

import java.io.*;
import java.util.Properties;

public enum TypesOfPersistenceLayer {
    MYSQL {
        @Override
        public UserDAO createUserDAO() {
            return new UserDAOMySQL();
        }
        @Override
        public PlaylistDAO createPlaylistDAO() {
            return new PlaylistDAOMySQL();
        }
    },
    JSON {
        @Override
        public UserDAO createUserDAO() {
            return new UserDAOJSON();
        }
        @Override
        public PlaylistDAO createPlaylistDAO() {
            return new PlaylistDAOJSON();
        }
    };

    public abstract UserDAO createUserDAO();
    public abstract PlaylistDAO createPlaylistDAO();

    /** Recupera dal file config.properties il tipo di persistenza utilizzata,
     * se non è possibile come default viene utilizzato MYSQL */
    public static TypesOfPersistenceLayer getPreferredPersistenceType() {
        Properties properties = new Properties();

        try (InputStream input = RegistrazioneCtrlApplicativo.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (IOException e){
            e.fillInStackTrace();
        }

        String persistenceType = properties.getProperty("persistence.type", "MYSQL");

        // Restituisci l'istanza corretta della enum
        return TypesOfPersistenceLayer.valueOf(persistenceType);
    }
}