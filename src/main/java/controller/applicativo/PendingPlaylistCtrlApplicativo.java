package controller.applicativo;

import engineering.bean.*;
import engineering.dao.*;
import engineering.exceptions.*;
import engineering.others.Printer;
import engineering.pattern.abstract_factory.DAOFactory;
import engineering.pattern.observer.PlaylistCollection;

import model.*;

import java.util.*;

public class PendingPlaylistCtrlApplicativo {

    public void approvePlaylist(PlaylistBean pB){
        PlaylistDAO dao = DAOFactory.getDAOFactory().createPlaylistDAO();

        Playlist playlist = new Playlist(pB.getEmail(), pB.getUsername(), pB.getPlaylistName(), pB.getLink(), pB.getPlaylistGenre(), pB.getApproved(), pB.getEmotional());
        playlist.setId(pB.getId());

        // Istanza di playlist ha ancora il parametro approved a false
        Playlist playlistApproved = dao.approvePlaylist(playlist);
        pB.setApproved(playlistApproved.getApproved());

        /* OBSERVER -> ADD PER FAR AGGIORNARE LA HOME PAGE */
        PlaylistCollection playlistCollection = PlaylistCollection.getInstance();
        playlistCollection.addPlaylist(playlist);
    }

    /** Recupera tutte le playlist globali, sia approvate che non */
    public List<PlaylistBean> retrievePlaylists(){

        PlaylistDAO dao = DAOFactory.getDAOFactory().createPlaylistDAO();

        // Recupero lista Playlist
        List<Playlist> playlists = dao.retrievePendingPlaylists();
        List<PlaylistBean> playlistsBean = new ArrayList<>();

        try{
            for (Playlist p : playlists){
                PlaylistBean pB = new PlaylistBean(p.getEmail(),p.getUsername(),p.getPlaylistName(),p.getLink(),p.getPlaylistGenre(),p.getApproved(),p.getEmotional());
                pB.setId(p.getId());

                playlistsBean.add(pB);
            }
        } catch (LinkIsNotValid e){
            Printer.logPrint(e.getMessage());
        }

        return playlistsBean;
    }

    public void rejectPlaylist(PlaylistBean pB) {
        PlaylistDAO dao = DAOFactory.getDAOFactory().createPlaylistDAO();

        Playlist playlist = new Playlist(pB.getEmail(), pB.getUsername(), pB.getPlaylistName(), pB.getLink(), pB.getPlaylistGenre(), pB.getApproved(), pB.getEmotional());
        playlist.setId(pB.getId());

        dao.deletePlaylist(playlist);
    }

    public void sendNotification(NoticeBean noticeBean) {

        NoticeDAO dao = DAOFactory.getDAOFactory().createNoticeDAO();

        Notice notice = new Notice(noticeBean.getTitle(),noticeBean.getBody(),noticeBean.getEmail());

        dao.addNotice(notice);
    }

}
