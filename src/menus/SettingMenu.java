/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menus;

import com.jfoenix.controls.JFXPopup;
import core.ClickListener;
import core.ReceiptExecutors;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

/**
 *
 * @author hatred
 */
public class SettingMenu extends JFXPopup {

    public static final String FXML_LOCATION = "/layout/setting-popup.fxml";

    private ClickListener<SettingMenuEnum> settingListener;

    public SettingMenu() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(FXML_LOCATION));
            loader.setController(this);
            Region region = loader.load();
            this.setPopupContent(region);
        } catch (IOException ex) {
            Logger.getLogger(SettingMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleStore(ActionEvent event) {
        hide();
        settingListener.OnClick(SettingMenuEnum.STORE);
        event.consume();
    }

    @FXML
    public void handleCredential(ActionEvent event) {
        hide();
        settingListener.OnClick(SettingMenuEnum.CREDENTIAL);
        event.consume();
    }

    @FXML
    public void handleExit(ActionEvent event) {
        hide();
        ReceiptExecutors.getInstance().getExecutor().shutdownNow();
        Platform.exit();
    }
    
    @FXML
    public void handlePref(ActionEvent event) {
        hide();
        settingListener.OnClick(SettingMenuEnum.PREF);
        event.consume();
    }


    public static class Builder {

        private ClickListener<SettingMenuEnum> listener;

        public Builder setListener(ClickListener<SettingMenuEnum> listener) {
            this.listener = listener;
            return this;
        }

        public SettingMenu build() {
            SettingMenu menu = new SettingMenu();
            menu.settingListener = this.listener;
            return menu;
        }
    }

}
