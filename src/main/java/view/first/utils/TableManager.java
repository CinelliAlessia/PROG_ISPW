package view.first.utils;

import engineering.bean.PlaylistBean;
import engineering.others.Printer;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class TableManager {
    private boolean isUpdatingTableView = true;

    /** Associa a ciascuna colonna i relativi metodi get di PlaylistBean
     * @param columns       è una lista di colonne, contiene le sole colonne semplici (no bottoni)
     * @param nameColumns   è una lista di stringhe, che viene utilizzata per recuperare i dati dai metodi get del PlaylistBean
     */
    public static void setColumnsTableView(List<TableColumn<PlaylistBean, ?>> columns, List<String> nameColumns) {

        // Collega i dati alle colonne della TableView
        int index = 0;
        for(TableColumn<PlaylistBean, ? > column:columns){
            column.setCellValueFactory(new PropertyValueFactory<>(nameColumns.get(index)));
            index++;
        }
    }

    /**
     * @param playlistTable è la tabella vera e propria
     * @param playlists     è la lista delle playlist da rappresentare
     */
    public static void updateTable(TableView<PlaylistBean> playlistTable, List<PlaylistBean> playlists) {

        List<PlaylistBean> currentPlaylists = playlistTable.getItems();     // Ottenere la lista attuale di playlist dalla TableView

        playlists.removeAll(currentPlaylists);                              // Rimuove le playlist già caricate, cosi da avere una lista di playlist nuove

        ObservableList<PlaylistBean> playlistData = FXCollections.observableArrayList(playlists);
        playlistTable.setItems(playlistData);                               // Aggiornare la TableView con la lista aggiornata di playlist
    }

    /**
     * @param playlistTable è la tabella vera e propria
     * @param playlists     è la lista delle playlist da rappresentare
     */
    public static void addInTable(TableView<PlaylistBean> playlistTable, List<PlaylistBean> playlists) {

        List<PlaylistBean> currentPlaylists = playlistTable.getItems();     // Ottenere la lista attuale di playlist dalla TableView

        playlists.removeAll(currentPlaylists);                              // Rimuove le playlist già caricate, cosi da avere una lista di playlist nuove
        currentPlaylists.addAll(playlists);

        ObservableList<PlaylistBean> playlistData = FXCollections.observableArrayList(currentPlaylists);
        playlistTable.setItems(playlistData);                               // Aggiornare la TableView con la lista aggiornata di playlist
    }

    public ObservableList<PlaylistBean> handler(TableView<PlaylistBean> playlistTable, List<PlaylistBean> playlistBeanList) {

        ObservableList<PlaylistBean> observableList = FXCollections.observableList(playlistBeanList);
        playlistTable.setItems(observableList);

        // Aggiunta di un listener di modifica al ObservableList
        observableList.addListener((ListChangeListener<PlaylistBean>) change -> {
            while (change.next()) {
                if (change.wasAdded() && isUpdatingTableView) { ///// non accade mai #########
                    Printer.logPrint(String.format("Elementi aggiunti: %s" , change.getAddedSubList()));
                    isUpdatingTableView = false;
                    playlistTable.getItems().addAll(change.getAddedSubList());
                    isUpdatingTableView = true;
                } else if (change.wasRemoved() && isUpdatingTableView) {
                    Printer.logPrint(String.format("Elementi rimossi: %s", change.getRemoved()));
                    isUpdatingTableView = false;
                    playlistTable.getItems().removeAll(change.getRemoved());
                    isUpdatingTableView = true;
                }
            }
        });

        return observableList;
    }

}
