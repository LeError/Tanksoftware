package gasStationSoftware.ui;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXTextField;
import gasStationSoftware.controller.WindowController;
import gasStationSoftware.util.Dialog;
import gasStationSoftware.util.Utility;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.awt.*;

public class ThemeDialog
extends Dialog {

    private Label lblMenuBar, lblContentPaneBackground, lblIcons, lblDividerMenuBar, lblFontContent, lblButtonsBackground, lblButtonsFont, lblDividerContent;
    private JFXColorPicker cpMenuBar, cpContentPaneBackground, cpIcons, cpDividerMenuBar, cpFontContent, cpButtonsBackground, cpButtonsFont, cpDividerContent;
    private JFXTextField txtTitle;
    private boolean newEntry = true;
    private String title;

    public ThemeDialog(StackPane rootPane, WindowController windowController) {
        super(windowController);

        AnchorPane pane = create();

        txtTitle.setPromptText("Theme Name");

        inputDialog(rootPane, pane, "Theme erstellen");
    }

    public ThemeDialog(StackPane rootPane, WindowController windowController, Color menuBar,
    Color contentPaneBackground, Color icons, Color dividerMenuBar, Color fontContent, Color buttonBackground,
    Color buttonFont, Color dividerContent, String title) {
        super(windowController);

        newEntry = false;
        AnchorPane pane = create();
        this.title = title;

        cpMenuBar.setValue(Utility.getFXColor(menuBar));
        cpContentPaneBackground.setValue(Utility.getFXColor(contentPaneBackground));
        cpIcons.setValue(Utility.getFXColor(icons));
        cpDividerMenuBar.setValue(Utility.getFXColor(dividerMenuBar));
        cpFontContent.setValue(Utility.getFXColor(fontContent));
        cpButtonsBackground.setValue(Utility.getFXColor(buttonBackground));
        cpButtonsFont.setValue(Utility.getFXColor(buttonFont));
        cpDividerContent.setValue(Utility.getFXColor(dividerContent));

        txtTitle.setText(title);
        txtTitle.setDisable(true);

        inputDialog(rootPane, pane, "Theme bearbeiten");
    }

    @Override protected void processSubmit(AnchorPane pane) {
        if (newEntry) {
            windowController.processThemeInput(pane);
        } else {
            windowController.processExistingTheme(pane, title);
        }
    }

    private AnchorPane create() {
        lblMenuBar = getLabel("Menüleiste:", 150, 30, 5, 10);
        lblContentPaneBackground = getLabel("Hintergrund:", 150, 30, 5, 50);
        lblIcons = getLabel("Icons:", 150, 30, 5, 90);
        lblDividerMenuBar = getLabel("Trenner Menü:", 150, 30, 5, 130);
        lblFontContent = getLabel("Schrift:", 150, 30, 5, 170);
        lblButtonsBackground = getLabel("Buttons:", 150, 30, 5, 210);
        lblButtonsFont = getLabel("Buttons Schrift:", 150, 30, 5, 250);
        lblDividerContent = getLabel("Trenner:", 150, 30, 5, 290);

        cpMenuBar = getColorPicker(100, 30, 195, 10);
        cpContentPaneBackground = getColorPicker(100, 30, 195, 50);
        cpIcons = getColorPicker(100, 30, 195, 90);
        cpDividerMenuBar = getColorPicker(100, 30, 195, 130);
        cpFontContent = getColorPicker(100, 30, 195, 170);
        cpButtonsBackground = getColorPicker(100, 30, 195, 210);
        cpButtonsFont = getColorPicker(100, 30, 195, 250);
        cpDividerContent = getColorPicker(100, 30, 195, 290);

        txtTitle = getTextfield(290, 30, false, 330, 5, 5);

        AnchorPane pane = getAnchorPane(300, 380);
        pane.getChildren()
        .addAll(lblMenuBar, lblContentPaneBackground, lblIcons, lblDividerMenuBar, lblFontContent, lblButtonsBackground,
        lblButtonsFont, lblDividerContent, cpMenuBar, cpContentPaneBackground, cpIcons, cpDividerMenuBar, cpFontContent,
        cpButtonsBackground, cpButtonsFont, cpDividerContent, txtTitle);
        return pane;
    }
}
