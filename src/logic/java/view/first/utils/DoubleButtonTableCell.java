package view.first.utils;

import engineering.bean.PlaylistBean;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import view.first.PendingPlaylistCtrlGrafico;


// Classe TableCell personalizzata per aggiungere due bottoni nella cella
public class DoubleButtonTableCell extends TableCell<PlaylistBean, Boolean> {

    private final Button approveButton = new Button("V");
    private final Button rejectButton = new Button("X");

    public DoubleButtonTableCell(PendingPlaylistCtrlGrafico pendingPlaylistCtrlGrafico) {

        approveButton.setOnAction(e -> {
            TableRow<PlaylistBean> tableRow = getTableRow();
            if (tableRow != null) {
                PlaylistBean playlistBean = tableRow.getItem();
                handlePendingButton(pendingPlaylistCtrlGrafico, playlistBean, true);
            }
        });

        rejectButton.setOnAction(e -> {
            TableRow<PlaylistBean> tableRow = getTableRow();
            if (tableRow != null) {
                PlaylistBean playlistBean = tableRow.getItem();
                handlePendingButton(pendingPlaylistCtrlGrafico, playlistBean, false);
            }
        });


        approveButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-pref-height: 25px; -fx-pref-width: 25px; " +
                "-fx-min-width: -1; -fx-min-height: -1; -fx-background-radius: 50%; -fx-stroke: 50; -fx-border-radius: 50%;");

        rejectButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-pref-height: 25px; -fx-pref-width: 25px; " +
                "-fx-min-width: -1; -fx-min-height: -1; -fx-background-radius: 50%; -fx-stroke: 50; -fx-border-radius: 50%;");
    }

    public void handlePendingButton(PendingPlaylistCtrlGrafico pendingPlaylistCtrlGrafico, PlaylistBean playlistBean, boolean approve) {
        pendingPlaylistCtrlGrafico.handlerButton(playlistBean,approve);
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(createButtonBox(approveButton, rejectButton));
        }
    }

    private HBox createButtonBox(Button... buttons) {
        HBox buttonBox = new HBox(5); // 5 è lo spazio tra i bottoni
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(buttons);
        return buttonBox;
    }
}