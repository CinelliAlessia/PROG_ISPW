package engineering.pattern.abstract_factory;

import engineering.dao.*;
import engineering.pattern.abstract_factory.DAOFactory;

public class JsonDAODAOFactory extends DAOFactory {
    @Override
    public ClientDAO createClientDAO() {
        return new ClientDAOJSON();
    }

    @Override
    public PlaylistDAO createPlaylistDAO() {
        return new PlaylistDAOJSON();
    }

    @Override
    public NoticeDAO createNoticeDAO() {
        return new NoticeDAOJSON();
    }
}
