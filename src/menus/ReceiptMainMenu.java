/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menus;

import com.jfoenix.controls.JFXPopup;
import core.ClickListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

/**
 *
 * @author hatred
 */
public class ReceiptMainMenu extends JFXPopup {

    public static final String FXML_LOCATION = "/layout/menu-popup.fxml";

    private ClickListener<MenuEnum> settingListener;

    public ReceiptMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(FXML_LOCATION));
            loader.setController(this);
            Region region = loader.load();
            this.setPopupContent(region);
        } catch (IOException ex) {
            Logger.getLogger(ReceiptMainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleLatest(ActionEvent event) {
        hide();
        settingListener.OnClick(MenuEnum.LATEST);
        event.consume();
        
    }

    @FXML
    private void handleOldest(ActionEvent event) {
        hide();
        settingListener.OnClick(MenuEnum.OLDEST);
        event.consume();
    }

    @FXML
    private void handleExcel(ActionEvent event) {
        hide();
        settingListener.OnClick(MenuEnum.EXCEL);
        event.consume();
    }
    @FXML
    private void handleDates(ActionEvent event) {
        hide();
        settingListener.OnClick(MenuEnum.DATE);
        event.consume();
    }

    @FXML
    private void handleExportXML(ActionEvent event) {
        hide();
        settingListener.OnClick(MenuEnum.XML_EXPORT);
        event.consume();
    }

    @FXML
    private void handleImport(ActionEvent event) {
        hide();
        settingListener.OnClick(MenuEnum.XML_IMPORT);
        event.consume();
    }

    public static class Builder {

        private ClickListener<MenuEnum> listener;

        public Builder setListener(ClickListener<MenuEnum> listener) {
            this.listener = listener;
            return this;
        }

        public ReceiptMainMenu build() {
            ReceiptMainMenu menu = new ReceiptMainMenu();
            menu.settingListener = this.listener;
            return menu;
        }
    }

}
