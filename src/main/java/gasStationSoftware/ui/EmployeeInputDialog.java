package gasStationSoftware.ui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gasStationSoftware.controller.WindowController;
import gasStationSoftware.util.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class EmployeeInputDialog extends Dialog {

    public EmployeeInputDialog(StackPane rootPane, WindowController windowController) {
        super(windowController);

        JFXTextField txtEmployeeNumber = getTextfield(140, 30, true, 10d, 5d, 5d);
        txtEmployeeNumber.setText(String.valueOf(windowController.getFreeEmployeeNumber()));

        JFXTextField txtFirstName = getTextfield(200, 30, false, 60d, 5d, 5d);
        txtFirstName.setPromptText("Vorname");

        JFXTextField txtSurName = getTextfield(200, 30, false, 110d, 5d, 5d);
        txtSurName.setPromptText("Nachname");

        JFXDatePicker employmentDate = new JFXDatePicker();
        employmentDate.setMinSize(200, 30);
        employmentDate.setPrefSize(200, 30);
        employmentDate.setMaxSize(200, 30);
        AnchorPane.setTopAnchor(employmentDate, 160d);
        AnchorPane.setLeftAnchor(employmentDate, 5d);
        AnchorPane.setRightAnchor(employmentDate, 5d);

        JFXComboBox employeeRole = getComboBox(windowController.getUserRoles(), "Nutzer Rolle", 200, 30, 210d, 5d, 5d);

        JFXPasswordField employeePass = new JFXPasswordField();
        employeePass.setMinSize(200, 30);
        employeePass.setPrefSize(200, 30);
        employeePass.setMaxSize(200, 30);
        employeePass.setPromptText("Paswort");
        AnchorPane.setTopAnchor(employeePass, 260d);
        AnchorPane.setLeftAnchor(employeePass, 5d);
        AnchorPane.setRightAnchor(employeePass, 5d);

        AnchorPane pane = getAnchorPane(210, 310);
        pane.getChildren().addAll(txtEmployeeNumber, txtFirstName, txtSurName, employmentDate, employeeRole, employeePass);
        inputDialog(rootPane, pane, "Anlegen Mitarbeiter");
    }

    @Override
    protected void processSubmit(AnchorPane pane) {
        windowController.processEmployee(pane);
    }

}
