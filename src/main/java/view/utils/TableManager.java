package view.utils;

import engineering.bean.PlaylistBean;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class TableManager {

    private TableManager(){}

    /**
     * @param playlistTable è la tabella vera e propria
     * @param columns       è una lista di colonne, contiene le sole colonne semplici (no bottoni)
     * @param nameColumns   è una lista di stringhe, che viene utilizzata per recuperare i dati dai metodi get del PlaylistBean
     * @param playlists     è la lista delle playlist da rappresentare
     */
    public static void createTable(TableView<PlaylistBean> playlistTable, List<TableColumn<PlaylistBean, ?>> columns, List<String> nameColumns, List<PlaylistBean> playlists) {

        // Collega i dati alle colonne della TableView
        int index = 0;
        for( TableColumn<PlaylistBean, ? > column:columns){
            column.setCellValueFactory(new PropertyValueFactory<>(nameColumns.get(index)));
            index++;
        }

        updateTable(playlistTable, playlists);
    }

    public static void updateTable(TableView<PlaylistBean> playlistTable, List<PlaylistBean> playlists) {

        List<PlaylistBean> currentPlaylists = playlistTable.getItems();     // Ottenere la lista attuale di playlist dalla TableView
        System.out.println("Table Manager update: playlist corrente" + currentPlaylists);
        System.out.println("Table Manager update: playlist totale nuova" + playlists);

        playlists.removeAll(currentPlaylists);                              // Rimuove le playlist già caricate, cosi da avere una lista di playlist nuove
        // ### Problema se viene rimossa una playlist
        System.out.println("Table Manager update: playlist da aggiungere" + playlists);

        ObservableList<PlaylistBean> playlistData = FXCollections.observableArrayList(playlists);
        playlistTable.setItems(playlistData);                               // Aggiornare la TableView con la lista aggiornata di playlist

        List<PlaylistBean> newPlaylist = playlistTable.getItems();          // Ottenere la lista attuale di playlist dalla TableView
        System.out.println("Table Manager update: playlist in table" + newPlaylist);

    }

}
