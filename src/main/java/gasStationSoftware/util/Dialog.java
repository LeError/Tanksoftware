package gasStationSoftware.util;

import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import gasStationSoftware.controller.WindowController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public abstract class Dialog {

    @FXML
    protected void inputDialog(StackPane rootPane, AnchorPane dialogBodyContent, String title){
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(rootPane, dialogContent, JFXDialog.DialogTransition.CENTER);

        JFXButton btnSubmit = new JFXButton("Abschicken");
        btnSubmit.setGraphic(getICO(MaterialDesignIcon.CHECK));
        btnSubmit.setOnAction(event -> {
            dialog.close();
            processSubmit(dialogBodyContent);
        });

        JFXButton btnCancel = new JFXButton("Abbrechen");
        btnCancel.setGraphic(getICO(MaterialDesignIcon.CLOSE));
        btnCancel.setOnAction(event -> dialog.close());

        dialogContent.setHeading(new Label(title));
        dialogContent.setBody(dialogBodyContent);
        dialogContent.setActions(btnCancel, btnSubmit);
        dialog.show();
    }

    protected abstract void processSubmit(AnchorPane pane);

    public static JFXComboBox<String> getComboBox(ArrayList<String> content, String promptText, int prefWidth, int prefHeight, double topAnchor, double rightAnchor, double leftAnchor) {
        JFXComboBox<String> cb = new JFXComboBox<>();
        cb.getItems().addAll(content);
        cb.setPromptText(promptText);
        cb.setPrefSize(prefWidth, prefHeight);
        cb.setMinSize(prefWidth, prefHeight);
        cb.setMaxSize(prefWidth, prefHeight);
        AnchorPane.setTopAnchor(cb, topAnchor);
        AnchorPane.setRightAnchor(cb, rightAnchor);
        AnchorPane.setLeftAnchor(cb, leftAnchor);
        return cb;
    }

    public static JFXTextField getTextfield(int prefWidth, int prefHeight, boolean disable, double topAnchor, double rightAnchor, double leftAnchor) {
        JFXTextField txtField = new JFXTextField();
        txtField.setPrefSize(prefWidth, prefHeight);
        txtField.setMinSize(prefWidth, prefHeight);
        txtField.setMaxSize(prefWidth, prefHeight);
        txtField.setDisable(disable);
        AnchorPane.setTopAnchor(txtField, topAnchor);
        AnchorPane.setRightAnchor(txtField, rightAnchor);
        AnchorPane.setLeftAnchor(txtField, leftAnchor);
        return txtField;
    }

    public static JFXButton getButton(int prefWidth, int prefHeight, double topAnchor, double leftAnchor) {
        JFXButton btn = new JFXButton();
        btn.setPrefSize(prefWidth, prefHeight);
        btn.setMinSize(prefWidth, prefHeight);
        btn.setMaxSize(prefWidth, prefHeight);
        btn.setStyle(WindowController.getButtonStyle());
        AnchorPane.setTopAnchor(btn, topAnchor);
        AnchorPane.setLeftAnchor(btn, leftAnchor);
        return btn;
    }

    public static MaterialDesignIconView getICO(MaterialDesignIcon ico) {
        MaterialDesignIconView iconView = new MaterialDesignIconView(ico);
        iconView.setStyle(WindowController.getIconStyle());
        return iconView;
    }

    public static TableView getTable(int prefWidth, int prefHeight, double topAnchor, double leftAnchor) {
        TableView table = new TableView();
        table.setPrefSize(prefWidth, prefHeight);
        table.setMinSize(prefWidth, prefHeight);
        table.setMaxSize(prefWidth, prefHeight);
        AnchorPane.setTopAnchor(table, topAnchor);
        AnchorPane.setLeftAnchor(table, leftAnchor);
        return table;
    }

    public static TableColumn getColumn(String title, String property, double prefWidth, boolean resizeable) {
        TableColumn column = new TableColumn(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        column.setPrefWidth(prefWidth);
        column.setResizable(resizeable);
        return column;
    }

    public static AnchorPane getAnchorPane(int prefWidth, int prefHeight) {
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(prefWidth, prefHeight);
        pane.setMaxSize(prefWidth, prefHeight);
        pane.setMinSize(prefWidth, prefHeight);
        return pane;
    }

}
