package engineering.pattern.abstract_factory;

import engineering.dao.*;

public class MySQLDAOFactory extends DAOFactory {

    @Override
    public ClientDAO createClientDAO() {
        return new ClientDAOMySQL();
    }

    @Override
    public PlaylistDAO createPlaylistDAO() {
        return new PlaylistDAOMySQL();
    }

    @Override
    public NoticeDAO createNoticeDAO() {
        return new NoticeDAOMySQL();
    }

}
