/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package receipt;


import com.sun.deploy.util.DialogListener;
import core.BaseDialog;
import core.ClickListener;
import core.ReceiptStatus;
import entities.Receipt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

/**
 *
 * @author hatred
 */
public class StatusDialog extends BaseDialog<Receipt> {

    public static final String FXML_LOC = "/layout/billstatus_dialog.fxml";

    @FXML
    ComboBox<ReceiptStatus> statusCombo;
    private ClickListener<Receipt> plistener;
    private Receipt bill;

    public StatusDialog(String resource, ClickListener listener) {
        super(resource, listener);
        this.statusCombo.getItems().addAll(ReceiptStatus.PENDING, ReceiptStatus.PARTIAL_PAID, ReceiptStatus.PAID);
        this.setOnDialogOpened(ev -> statusCombo.requestFocus());
    }

    @FXML
    public void handlePositive(ActionEvent e) {
        bill.setStatus(statusCombo.getSelectionModel().getSelectedItem());
        plistener.OnClick(bill);
        close();
    }

    public static class Builder {

        private Receipt bill;
        private ClickListener listener;

        public Builder setReceipt(Receipt bill) {
            this.bill = bill;
            return this;
        }

        public Builder setOnPositiveListener(ClickListener<Receipt> listner) {
            this.listener = listner;
            return this;
        }

        public StatusDialog build() {
            StatusDialog d = new StatusDialog(FXML_LOC, null);
            d.statusCombo.getSelectionModel().select(bill.getStatus());
            d.bill = this.bill;
            d.plistener = this.listener;
            return d;
        }
    }
}
