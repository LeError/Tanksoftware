package gasStationSoftware.ui;

import com.jfoenix.controls.*;
import gasStationSoftware.controller.WindowController;
import gasStationSoftware.util.Dialog;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ErrorDialog {

    /**
     * Constructor zur erzeugung des Dialogs
     * @param rootPane StackPane der Anwendung
     * @param title Title des Dialogs
     * @param e Fehler der Angezeigt wird
     * @param exit Wahrheitswert ob programm beendet wird
     * @author Robin Herder
     */
    public ErrorDialog(StackPane rootPane, String title, Exception e, boolean exit) {
        inputDialog(rootPane, title, e.getStackTrace().toString(), exit);
    }

    /**
     * Erstellung des Elemente des Dialogs
     * @param rootPane StackPane der Anwendung
     * @param title Title des Dialogs
     * @param errorText Fehler der Angezeigt wird
     * @param exit Wahrheitswert ob programm beendet wird
     * @author Robin Herder
     */
    @FXML
    protected void inputDialog(StackPane rootPane, String title, String errorText, boolean exit){
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(rootPane, dialogContent, JFXDialog.DialogTransition.CENTER);

        JFXTextField txtErrorText = new JFXTextField(errorText);
        txtErrorText.setEditable(false);
        txtErrorText.setMinSize(490, 80);
        txtErrorText.setMaxSize(490, 80);
        txtErrorText.setPrefSize(490, 80);
        AnchorPane.setTopAnchor(txtErrorText, 10d);
        AnchorPane.setLeftAnchor(txtErrorText, 5d);
        AnchorPane.setRightAnchor(txtErrorText, 5d);

        JFXButton btnCancel = new JFXButton("OK");
        btnCancel.setStyle(WindowController.getButtonStyle());
        btnCancel.setOnAction(event -> {
            dialog.close();
            if(exit) {
                System.exit(0);
            }
        });

        AnchorPane dialogBodyContent = Dialog.getAnchorPane(500, 100);
        dialogBodyContent.getChildren().add(txtErrorText);

        dialogContent.setHeading(new Label("ERROR: " + title));
        dialogContent.setBody(dialogBodyContent);
        dialogContent.setActions(btnCancel);
        dialog.setMaxSize(dialogBodyContent.getMaxWidth() + 50, dialogBodyContent.getMaxHeight());
        dialog.show();
    }

}
