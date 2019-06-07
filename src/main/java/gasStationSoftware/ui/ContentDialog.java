package gasStationSoftware.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import gasStationSoftware.controller.WindowController;
import gasStationSoftware.models.Document;
import gasStationSoftware.util.Dialog;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.ArrayList;

public class ContentDialog {

    private Document doc;
    private WindowController windowController;

    /**
     * Constructor zur erzeugung des Dialogs
     * @param rootPane StackPane der Anwendung
     * @param title Title des Dialogs
     * @param content angezeigter content
     * @param doc document das angezeigt wird
     * @author Robin Herder
     */
    public ContentDialog(StackPane rootPane, WindowController windowController, String title, ArrayList<String> content, Document doc) {
        String line = "";
        for (String contentLine : content) {
            line += contentLine + "\n";
        }
        inputDialog(rootPane, title, line);
        this.doc = doc;
        this.windowController = windowController;
    }

    /**
     * Erstellung des Elemente des Dialogs
     * @param rootPane StackPane der Anwendung
     * @param title Title des Dialogs
     * @param content Fehler der Angezeigt wird
     * @author Robin Herder
     */
    @FXML
    protected void inputDialog(StackPane rootPane, String title, String content){
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(rootPane, dialogContent, JFXDialog.DialogTransition.CENTER);

        JFXTextArea txtMessage = new JFXTextArea(content);
        txtMessage.setEditable(false);
        txtMessage.setMinSize(490, 350);
        txtMessage.setMaxSize(490, 350);
        txtMessage.setPrefSize(490, 350);
        AnchorPane.setTopAnchor(txtMessage, 10d);
        AnchorPane.setLeftAnchor(txtMessage, 5d);
        AnchorPane.setRightAnchor(txtMessage, 5d);

        JFXButton btnCancel = new JFXButton("OK");
        btnCancel.setStyle(WindowController.getButtonStyle());
        btnCancel.setOnAction(event -> {
            dialog.close();
        });

        JFXButton btnExport = new JFXButton("Exportieren");
        btnExport.setStyle(WindowController.getButtonStyle());
        btnExport.setOnAction(event -> {
            try {
                windowController.export(doc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        AnchorPane dialogBodyContent = Dialog.getAnchorPane(500, 370);
        dialogBodyContent.getChildren().add(txtMessage);

        dialogContent.setHeading(new Label("Angezeigt: " + title));
        dialogContent.setBody(dialogBodyContent);
        dialogContent.setActions(btnExport, btnCancel);
        dialog.setMaxSize(dialogBodyContent.getMaxWidth() + 50, dialogBodyContent.getMaxHeight());
        dialog.show();
    }

}
