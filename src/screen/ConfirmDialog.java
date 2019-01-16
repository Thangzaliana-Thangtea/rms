/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import core.ClickListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 *
 * @author hatred
 */
public class ConfirmDialog<T> extends JFXDialog {

    public static final String FXML_LOCATION = "/layout/confirm-dialog.fxml";

    @FXML
    private Label titleLabel;
    @FXML
    private Label msgLabel;
    @FXML
    private JFXButton positiveBtn;
    @FXML
    private JFXButton negativeBtn;

    T model;
    ClickListener<T> positiveListener;

    public ConfirmDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(FXML_LOCATION));
            loader.setController(this);
            Region region = loader.load();
            setContent(region);
            this.setOnDialogOpened(ev -> positiveBtn.requestFocus());
            this.negativeBtn.focusedProperty().addListener((val, old, newval) -> {
                if (!newval) {
                    positiveBtn.requestFocus();
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(ConfirmDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handlePositive(ActionEvent event) {
        close();
        positiveListener.OnClick(model);
    }

    @FXML
    private void handleNegative(ActionEvent event) {
        close();
    }

    public static class Builder<T> {

        private String title;
        private String message;
        private String positiveLabel;
        private String negativeLabel;

        ClickListener<T> positiveListener;
        private T model;

        public Builder() {
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setOnPositiveClick(String label, ClickListener listener) {
            this.positiveListener = listener;
            this.positiveLabel = label;
            return this;
        }

        public ConfirmDialog build() {
            ConfirmDialog<T> d = new ConfirmDialog<>();
            d.positiveListener = this.positiveListener;
            d.positiveBtn.setText(positiveLabel);
            d.titleLabel.setText(title);
            d.msgLabel.setText(message);
            return d;
        }
    }

}
