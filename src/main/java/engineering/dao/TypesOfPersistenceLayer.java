package engineering.dao;

import controller.applicativo.RegistrazioneCtrlApplicativo;

import java.io.IOException;
import java.io.InputStream;
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

    public static TypesOfPersistenceLayer getPreferredPersistenceType() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = RegistrazioneCtrlApplicativo.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        }

        String persistenceType = properties.getProperty("persistence.type", "MySQL");

        // Restituisci l'istanza corretta della enum
        return TypesOfPersistenceLayer.valueOf(persistenceType);
    }
}