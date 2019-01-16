/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setting;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import core.BaseDialog;
import core.ClickListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import util.TbcPreferences;

public class PreferencesDialog extends BaseDialog<Object> {

    public static final String FXML_LOCATION = "/layout/preferences-dialog.fxml";

    @FXML
    private JFXRadioButton small;
    @FXML
    private JFXRadioButton large;
    @FXML
    private ToggleGroup templateGroup;
    @FXML
    private JFXButton closeBtn;
    @FXML
    JFXCheckBox importCheck;

    TbcPreferences pref;

    public PreferencesDialog(String resource, ClickListener listener) {
        super(resource, listener);
        
    }

    @FXML
    private void initialize(){
        this.pref = new TbcPreferences();
        this.small.setUserData(1);
        this.large.setUserData(2);
        int choice = pref.getTemplate();
        if (choice==1) {
            small.setSelected(true);
        }else{
            large.setSelected(true);
        }
        this.importCheck.setSelected(pref.isReplace());
        this.templateGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
            Integer choice1 = (Integer) newValue.getUserData();
            pref.setTemplate(choice1);
        });
        this.importCheck.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            pref.setReplaceItem(newValue);
        });
    }

    @FXML
    private void handleClose(ActionEvent event) {
        close();
        event.consume();
    }
    public static class Builder {

        public PreferencesDialog build() {
            PreferencesDialog d = new PreferencesDialog(FXML_LOCATION, null);
            return d;
        }
    }

}
