package gasStationSoftware.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
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

    protected WindowController windowController;
    protected static String iconsStyle;
    protected static String buttonsStyle;

    /**
     * Constructor Dialog
     * @param windowController
     * @author Robin Herder
     */
    protected Dialog(WindowController windowController) {
        this.windowController = windowController;
        this.iconsStyle = windowController.getIconStyle();
        this.buttonsStyle = windowController.getButtonStyle();
    }

    /**
     *
     * @param rootPane
     * @param dialogBodyContent
     * @param title
     * @author Robin Herder
     */
    @FXML
    protected void inputDialog(StackPane rootPane, AnchorPane dialogBodyContent, String title){
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(rootPane, dialogContent, JFXDialog.DialogTransition.CENTER);

        JFXButton btnSubmit = new JFXButton("Abschicken");
        btnSubmit.setGraphic(getICO(MaterialDesignIcon.CHECK));
        btnSubmit.setStyle(buttonsStyle);
        btnSubmit.setGraphic(getICO(MaterialDesignIcon.CHECK));
        btnSubmit.setOnAction(event -> {
            dialog.close();
            processSubmit(dialogBodyContent);
        });

        JFXButton btnCancel = new JFXButton("Abbrechen");
        btnCancel.setGraphic(getICO(MaterialDesignIcon.CLOSE));
        btnCancel.setStyle(buttonsStyle);
        btnCancel.setGraphic(getICO(MaterialDesignIcon.CLOSE));
        btnCancel.setOnAction(event -> dialog.close());

        dialogContent.setHeading(new Label(title));
        dialogContent.setBody(dialogBodyContent);
        dialogContent.setActions(btnCancel, btnSubmit);
        dialog.setMaxSize(dialogBodyContent.getMaxWidth() + 50, dialogBodyContent.getMaxHeight());
        dialog.show();
    }

    /**
     *
     * @param pane
     * @author Robin Herder
     */
    protected abstract void processSubmit(AnchorPane pane);

    /**
     *
     * @param content
     * @param promptText
     * @param prefWidth
     * @param prefHeight
     * @param topAnchor
     * @param rightAnchor
     * @param leftAnchor
     * @return
     * @author Robin Herder
     */
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

    /**
     *
     * @param prefWidth
     * @param prefHeight
     * @param disable
     * @param topAnchor
     * @param rightAnchor
     * @param leftAnchor
     * @return
     * @author Robin Herder
     */
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

    /**
     * @param prefWidth
     * @param prefHeight
     * @param topAnchor
     * @param leftAnchor
     * @return
     * @author Robin Herder
     */
    public static JFXButton getButton(int prefWidth, int prefHeight, double topAnchor, double leftAnchor) {
        JFXButton btn = new JFXButton();
        btn.setPrefSize(prefWidth, prefHeight);
        btn.setMinSize(prefWidth, prefHeight);
        btn.setMaxSize(prefWidth, prefHeight);
        btn.setStyle(buttonsStyle);
        AnchorPane.setTopAnchor(btn, topAnchor);
        AnchorPane.setLeftAnchor(btn, leftAnchor);
        return btn;
    }

    /**
     *
     * @param ico
     * @return
     * @author Robin Herder
     */
    public static MaterialDesignIconView getICO(MaterialDesignIcon ico) {
        MaterialDesignIconView iconView = new MaterialDesignIconView(ico);
        iconView.setStyle(iconsStyle);
        return iconView;
    }

    /**
     *
     * @param prefWidth
     * @param prefHeight
     * @param topAnchor
     * @param leftAnchor
     * @return
     * @author Robin Herder
     */
    public static TableView getTable(int prefWidth, int prefHeight, double topAnchor, double leftAnchor) {
        TableView table = new TableView();
        table.setPrefSize(prefWidth, prefHeight);
        table.setMinSize(prefWidth, prefHeight);
        table.setMaxSize(prefWidth, prefHeight);
        AnchorPane.setTopAnchor(table, topAnchor);
        AnchorPane.setLeftAnchor(table, leftAnchor);
        return table;
    }

    /**
     *
     * @param title
     * @param property
     * @param prefWidth
     * @param resizeable
     * @return TableColumn
     * @author Robin Herder
     */
    public static TableColumn getColumn(String title, String property, double prefWidth, boolean resizeable) {
        TableColumn column = new TableColumn(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        column.setPrefWidth(prefWidth);
        column.setResizable(resizeable);
        return column;
    }

    public static Label getLabel(String text, double prefWidth, double prefHeight, double leftAnchor,
    double topAnchor) {
        Label label = new Label(text);
        label.setPrefSize(prefWidth, prefHeight);
        label.setMinSize(prefWidth, prefHeight);
        label.setMaxSize(prefWidth, prefHeight);
        AnchorPane.setLeftAnchor(label, leftAnchor);
        AnchorPane.setTopAnchor(label, topAnchor);
        return label;
    }

    public static JFXColorPicker getColorPicker(double prefWidth, double prefHeight, double leftAnchor,
    double topAnchor) {
        JFXColorPicker colorPicker = new JFXColorPicker();
        colorPicker.setPrefSize(prefWidth, prefHeight);
        colorPicker.setMinSize(prefWidth, prefHeight);
        colorPicker.setMaxSize(prefWidth, prefHeight);
        AnchorPane.setLeftAnchor(colorPicker, leftAnchor);
        AnchorPane.setTopAnchor(colorPicker, topAnchor);
        return colorPicker;
    }

    /**
     *
     * @param prefWidth
     * @param prefHeight
     * @return AnchorPane
     * @author Robin Herder
     */
    public static AnchorPane getAnchorPane(int prefWidth, int prefHeight) {
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(prefWidth, prefHeight);
        pane.setMaxSize(prefWidth, prefHeight);
        pane.setMinSize(prefWidth, prefHeight);
        return pane;
    }

}
