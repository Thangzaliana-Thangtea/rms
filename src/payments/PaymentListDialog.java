/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payments;

import com.jfoenix.controls.JFXButton;
import core.BaseDialog;
import core.ClickListener;
import core.ReceiptStatus;
import core.SingleButtonCell;
import entities.Payment;
import entities.Receipt;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

public class PaymentListDialog extends BaseDialog<Receipt> {

    public static final String LOC = "/layout/payment-list-dialog.fxml";

    @FXML
    private TableView<Payment> paymentTable;
    @FXML
    private TableColumn<Payment, String> dateCol;
    @FXML
    private TableColumn<Payment, Double> amountCol;
    @FXML
    private TableColumn<Payment, String> remCol;
    @FXML
    private TableColumn<Payment, Payment> actionCol;
    @FXML
    private JFXButton negativeBtn;
    @FXML
    private JFXButton positiveBtn;
    @FXML
    private Label idLabel;

    Receipt receipt;

    public PaymentListDialog(String resource, ClickListener listener) {
        super(resource, listener);
    }

    @FXML
    private void initialize() {
        this.dateCol.setCellValueFactory(c -> {
            Payment value = c.getValue();
            String str = new SimpleDateFormat("dd/MM/yy").format(value.getVoucherDate());
            return new SimpleStringProperty(str);
        });
        this.amountCol.setEditable(true);
        this.remCol.setEditable(true);
        this.amountCol.setCellFactory(TextFieldTableCell.<Payment, Double>forTableColumn(new DoubleStringConverter()));
        this.remCol.setCellFactory(TextFieldTableCell.forTableColumn());

        this.remCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getRemark()));
        this.amountCol.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getAmount().doubleValue()));
        this.actionCol.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue()));
        this.actionCol.setCellFactory((TableColumn<Payment, Payment> param) -> new SingleButtonCell<Payment>(item -> {
            paymentTable.getItems().remove(item);
        }));
        this.paymentTable.itemsProperty().addListener((ObservableValue<? extends ObservableList<Payment>> observable, ObservableList<Payment> oldValue, ObservableList<Payment> newValue) -> {
            if (newValue.isEmpty()) {
                positiveBtn.setDisable(true);
            } else {
                positiveBtn.setDisable(false);
            }
        });

    }

    @FXML
    private void handleNegative(ActionEvent event) {
        close();
    }

    @FXML
    private void handlePositive(ActionEvent event) {

        double paidAmount = 0;
        for (Payment item : paymentTable.getItems()) {
            paidAmount = item.getAmount().doubleValue();
        }
        double amount = this.receipt.getAmount();
        if (paidAmount >= amount) {
            receipt.setStatus(ReceiptStatus.PAID);
        }
        if (paidAmount <= amount && paidAmount > 0) {
            receipt.setStatus(ReceiptStatus.PARTIAL_PAID);
        }
        if (paidAmount <= 0) {
            receipt.setStatus(ReceiptStatus.PENDING);
        }

        List<Payment> collect = paymentTable.getItems().stream().collect(Collectors.toList());
        receipt.setPayments(collect);
        listener.OnClick(receipt);
        close();
    }

    @FXML
    private void amountEdit(TableColumn.CellEditEvent<Payment, Double> event) {
        Payment payment = event.getRowValue();
        Double newval = event.getNewValue();
        payment.setAmount(new BigDecimal(newval));
    }

    @FXML
    private void remarkEdit(TableColumn.CellEditEvent<Payment, String> event) {
        Payment payment = event.getRowValue();
        String newval = event.getNewValue();
        payment.setRemark(newval);
    }

    public static class Builder {

        private ClickListener<Receipt> listener;
        private Receipt receipt;

        public Builder setReceipt(Receipt receipt) {
            this.receipt = receipt;
            return this;
        }

        public Builder setListener(ClickListener<Receipt> listener) {
            this.listener = listener;
            return this;
        }

        public PaymentListDialog build() {
            PaymentListDialog d = new PaymentListDialog(LOC, this.listener);
            d.paymentTable.getItems().addAll(receipt.getPayments());
            d.idLabel.setText("RECEIPT ID =" + receipt.getId());
            d.listener = this.listener;
            d.receipt = this.receipt;

            return d;
        }
    }

}
