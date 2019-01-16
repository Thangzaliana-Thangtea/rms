/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setting;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import core.BaseDialog;
import core.ClickListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.controlsfx.control.Notifications;
import util.TbcPreferences;

/**
 *
 * @author hatred
 */
public class CredentialDialog extends BaseDialog<Object> {

    public static final String FXML_LOCATION = "/layout/credential-info.fxml";

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextField passwordField;

    @FXML
    private JFXButton saveBtn;
    @FXML
    private JFXButton closeBtn;

    TbcPreferences pref;

    public CredentialDialog(String resource, ClickListener listener) {
        super(resource, listener);
        this.pref = new TbcPreferences();

        this.nameField.setText(pref.getMasterUsername());
        this.passwordField.setText(pref.getMasterPassword());
        this.saveBtn.disableProperty().bind(nameField.textProperty().isEmpty()
                .or(passwordField.textProperty().isEmpty()));
        
    }

    @FXML
    private void handleSave(ActionEvent event) {

        this.pref.setMasterPassword(passwordField.getText());
        this.pref.setMasterUsername(nameField.getText());

        Notifications.create().darkStyle().title("Credential Setting")
                .text("Credential info is updated").showInformation();
        close();
        event.consume();
    }

    @FXML
    private void handleClose(ActionEvent event) {
        close();
        event.consume();

    }

    public static class Builder {

        public CredentialDialog build() {
            CredentialDialog d = new CredentialDialog(FXML_LOCATION, null);
            return d;
        }
    }

}
