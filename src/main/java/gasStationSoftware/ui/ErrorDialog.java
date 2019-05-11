package gasStationSoftware.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import gasStationSoftware.controller.WindowController;
import gasStationSoftware.util.Dialog;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ErrorDialog {

    public ErrorDialog(StackPane rootPane, String title, String errorText) {
        inputDialog(rootPane, title, errorText);
    }

    @FXML
    protected void inputDialog(StackPane rootPane, String title, String errorText){
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(rootPane, dialogContent, JFXDialog.DialogTransition.CENTER);

        JFXTextArea txtErrorText = new JFXTextArea(errorText);
        txtErrorText.setMinSize(490, 80);
        txtErrorText.setMaxSize(490, 80);
        txtErrorText.setPrefSize(490, 80);
        txtErrorText.setDisable(true);
        AnchorPane.setTopAnchor(txtErrorText, 10d);
        AnchorPane.setLeftAnchor(txtErrorText, 5d);
        AnchorPane.setRightAnchor(txtErrorText, 5d);

        JFXButton btnCancel = new JFXButton("OK");
        btnCancel.setStyle(WindowController.getButtonStyle());
        btnCancel.setOnAction(event -> dialog.close());

        AnchorPane dialogBodyContent = Dialog.getAnchorPane(500, 100);
        dialogBodyContent.getChildren().add(txtErrorText);

        dialogContent.setHeading(new Label("ERROR: " + title));
        dialogContent.setBody(dialogBodyContent);
        dialogContent.setActions(btnCancel);
        dialog.setMaxSize(dialogBodyContent.getMaxWidth() + 50, dialogBodyContent.getMaxHeight());
        dialog.show();
    }

}
