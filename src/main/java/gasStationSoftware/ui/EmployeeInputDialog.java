package gasStationSoftware.ui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gasStationSoftware.controller.WindowController;
import gasStationSoftware.models.UserRole;
import gasStationSoftware.util.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.time.ZoneId;
import java.util.Date;

public class EmployeeInputDialog extends Dialog {

    private boolean newEntry = true;
    private JFXTextField txtEmployeeNumber, txtFirstName, txtSurName;
    private JFXDatePicker employmentDate;
    private JFXComboBox employeeRole;
    private JFXPasswordField employeePass;

    /**
     * Konstruktor zur erstellung des Dialogs
     * @param rootPane Stackpane det Application
     * @param windowController Controller der UI
     * @author Robin Herder
     */
    public EmployeeInputDialog(StackPane rootPane, WindowController windowController) {
        super(windowController);
        AnchorPane pane = create();
        inputDialog(rootPane, pane, "Anlegen Mitarbeiter");
    }

    /**
     * Konstruktor zur erstellung des Dialogs mit werten
     * @param rootPane Stackpane det Application
     * @param windowController Controller der UI
     * @param employeeNumber Persnummer der zu editierenden Angestelten
     * @param firstName Vorname der zu editierenden Angestelten
     * @param surName Nachname der zu editierenden Angestelten
     * @param employmentDate Einstellungsdatum der zu editierenden Angestelten
     * @param employeeRoleIdx Rolle der zu editierenden Angestelten
     * @author Robin Herder
     */
    public EmployeeInputDialog(StackPane rootPane, WindowController windowController, int employeeNumber, String firstName, String surName, Date employmentDate, int employeeRoleIdx) {
        super(windowController);

        AnchorPane pane = create();
        newEntry = false;

        txtEmployeeNumber.setDisable(true);
        txtEmployeeNumber.setText(String.valueOf(employeeNumber));

        txtFirstName.setText(firstName);
        txtSurName.setText(surName);

        this.employmentDate.setDisable(true);
        this.employmentDate.setValue(employmentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        String role;
        switch (employeeRoleIdx) {
            case 0:
                role = UserRole.admin.getRole();
                break;
            case 1:
                role = UserRole.employee.getRole();
                break;
            default:
                role = UserRole.assistant.getRole();
        }
        employeeRole.getSelectionModel().select(role);

        inputDialog(rootPane, pane, "Anlegen Mitarbeiter");
    }

    /**
     * Startet auswertung des Dialogs
     * @param pane AnchorPane des Dialogs
     * @author Robin Herder
     */
    @Override
    protected void processSubmit(AnchorPane pane) {
        if(newEntry){
            windowController.processEmployee(pane);
        } else {
            windowController.processExistingEmployee(pane);
        }
    }

    /**
     * Erstellen der UI Elemente des Dialogs
     * @return pane
     * @author Robin Herder
     */
    private AnchorPane create() {
        txtEmployeeNumber = getTextfield(140, 30, true, 10d, 5d, 5d);
        txtEmployeeNumber.setText(String.valueOf(windowController.getFreeEmployeeNumber()));

        txtFirstName = getTextfield(200, 30, false, 60d, 5d, 5d);
        txtFirstName.setPromptText("Vorname");

        txtSurName = getTextfield(200, 30, false, 110d, 5d, 5d);
        txtSurName.setPromptText("Nachname");

        employmentDate = new JFXDatePicker();
        employmentDate.setMinSize(200, 30);
        employmentDate.setPrefSize(200, 30);
        employmentDate.setMaxSize(200, 30);
        AnchorPane.setTopAnchor(employmentDate, 160d);
        AnchorPane.setLeftAnchor(employmentDate, 5d);
        AnchorPane.setRightAnchor(employmentDate, 5d);

        employeeRole = getComboBox(windowController.getUserRoles(), "Nutzer Rolle", 200, 30, 210d, 5d, 5d);

        employeePass = new JFXPasswordField();
        employeePass.setMinSize(200, 30);
        employeePass.setPrefSize(200, 30);
        employeePass.setMaxSize(200, 30);
        employeePass.setPromptText("Passwort");
        AnchorPane.setTopAnchor(employeePass, 260d);
        AnchorPane.setLeftAnchor(employeePass, 5d);
        AnchorPane.setRightAnchor(employeePass, 5d);

        AnchorPane pane = getAnchorPane(210, 310);
        pane.getChildren().addAll(txtEmployeeNumber, txtFirstName, txtSurName, employmentDate, employeeRole, employeePass);
        return pane;
    }

}
