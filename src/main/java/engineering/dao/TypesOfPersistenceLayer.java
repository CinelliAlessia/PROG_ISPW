package engineering.dao;

import controllerApplicativo.RegistrazioneCtrlApplicativo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum TypesOfPersistenceLayer {
    mySQL {
        @Override
        public UserDAO createUserDAO() {
            return new UserDAO_mySQL();
        }
        @Override
        public PlaylistDAO createPlaylistDAO() {
            return new PlaylistDAO_mySQL();
        }
    },
    JSON {
        @Override
        public UserDAO createUserDAO() {
            return new UserDAO_JSON();
        }
        @Override
        public PlaylistDAO createPlaylistDAO() {
            return new PlaylistDAO_JSON();
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
/*  Utilizza enum per ottenere un'istanza del DAO
TypesOfPersistenceLayer persistenceLayer = TypesOfPersistenceLayer.JDBC;
AlbumDAO albumDAO = persistenceLayer.createDAO();
 */