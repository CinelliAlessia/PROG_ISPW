package engineering.others;

import engineering.bean.PlaylistBean;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class TableManager {

    private TableManager(){}

    /**
     * @param playlistTable è la tabella vera e propria
     * @param columns       contiene le sole colonne semplici
     * @param nameColumns   è per recuperare dalle get del PlaylistBean
     * @param playlists     è la lista delle playlist da rappresentare
     * @param playlistGenre è una colonna che gestisce un bottone, tutte le tabelle avranno questa colonna -> Puo essere tolta ma ad alessia cosi non
     *                      piace molto
     */
    public static void createTable(TableView<PlaylistBean> playlistTable, List<TableColumn<PlaylistBean, ?>> columns, List<String> nameColumns, List<PlaylistBean> playlists, TableColumn<PlaylistBean, List<String>> playlistGenre) {

        // Collega i dati alle colonne della TableView
        int index = 0;
        for( TableColumn<PlaylistBean, ? > column:columns){
            column.setCellValueFactory(new PropertyValueFactory<>(nameColumns.get(index)));
            index++;
        }

        // Aggiungi le playlist alla TableView
        ObservableList<PlaylistBean> playlistData = FXCollections.observableArrayList(playlists);
        playlistTable.setItems(playlistData);

        // Configura la colonna "Generi musicali"
        /*
        playlistGenre.setCellFactory(col -> new TableCell<>() {
            final Button button = new Button("Dettagli");
            {
                button.setOnAction(event -> {
                    // Deve uscire un PopUp
                    System.out.println("Tasto Dettagli premuto");
                });
            }

            @Override
            protected void updateItem(List<String> genres, boolean empty) {
                super.updateItem(genres, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });*/

    }

}
