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
public class ReceiptMenu extends JFXPopup {

    public static final String FXML_LOCATION = "/layout/receipt-menu-popup.fxml";

    private ClickListener<ReceiptMenuEnum> settingListener;

    public ReceiptMenu() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(FXML_LOCATION));
            loader.setController(this);
            Region region = loader.load();
            this.setPopupContent(region);
        } catch (IOException ex) {
            Logger.getLogger(ReceiptMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleView(ActionEvent event) {
        settingListener.OnClick(ReceiptMenuEnum.VIEW);
        hide();
        event.consume();
    }

    @FXML
    public void handleEdit(ActionEvent event) {
        settingListener.OnClick(ReceiptMenuEnum.EDIT);
        hide();
        event.consume();
    }

    @FXML
    public void handleDelete(ActionEvent event) {
        settingListener.OnClick(ReceiptMenuEnum.DELETE);
        hide();
        event.consume();
    }


    @FXML
    public void handlePayment(ActionEvent event) {
        settingListener.OnClick(ReceiptMenuEnum.PAYMENT);
        hide();

        event.consume();
    }
    @FXML
    public void handleViewPayment(ActionEvent event) {
        settingListener.OnClick(ReceiptMenuEnum.VIEW_PAYMENT);
        hide();

        event.consume();
    }

    public static class Builder {

        private ClickListener<ReceiptMenuEnum> listener;

        public Builder setListener(ClickListener<ReceiptMenuEnum> listener) {
            this.listener = listener;
            return this;
        }

        public ReceiptMenu build() {
            ReceiptMenu menu = new ReceiptMenu();
            menu.settingListener = this.listener;
            return menu;
        }
    }

}
