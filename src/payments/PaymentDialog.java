/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payments;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.sun.deploy.util.DialogListener;
import core.BaseDialog;
import core.ClickListener;
import entities.Payment;
import entities.Receipt;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 *
 * @author hatred
 */
public class PaymentDialog extends BaseDialog<Payment> {
    public static final String FXML_LOCATION="/layout/payment_dialog.fxml";
    @FXML
    private Label titleLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label paidLabel;
    @FXML
    private Label remainingLabel;
    @FXML
    private JFXTextField amountField;
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private JFXTextArea remarkField;
    @FXML
    private JFXButton positiveBtn;
    @FXML
    private JFXButton negativeBtn;
    private Receipt bill;
    private Payment payment;
    
    
    public PaymentDialog(String resource, ClickListener listener) {
        super(resource, listener);
        this.setOnDialogOpened(ev->amountField.requestFocus());
        this.negativeBtn.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                amountField.requestFocus();
            }
        });
    }

    @FXML
    private void handleAmountField(ActionEvent event) {
        try{
            Double.parseDouble(amountField.getText());
        }catch(NumberFormatException dm){
            amountField.clear();
        }
    }

    @FXML
    private void handleDate(ActionEvent event) {
    }

    @FXML
    private void handlePositive(ActionEvent event) {
        if (amountField.getText().isEmpty()) {
            return ;
        }
        if (datePicker.valueProperty().isNull().get()) {
            return;
        }
        if (payment==null) {
            Payment newPayment=new Payment();
            newPayment.setAmount(new BigDecimal(amountField.getText().trim()));
            newPayment.setVoucherDate(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            newPayment.setReceipt(bill);
            newPayment.setRemark(remarkField.getText());
            bill.getPayments().add(newPayment);
            
            listener.OnClick(newPayment);
        }else{
            payment.setReceipt(bill);
            payment.setAmount(new BigDecimal(amountField.getText().trim()));
            payment.setVoucherDate(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            payment.setRemark(remarkField.getText()); 
           
            bill.getPayments().add(payment);
            listener.OnClick(payment);
        }
        close();
    }

    @FXML
    private void handleNegative(ActionEvent event) {
        close();
    }
    
    public static class Builder{

        private Receipt bill;
        private Payment p;
        private ClickListener<Payment> listener;
        private static final String PATTERN="dd/MM/yy";
        
        public Builder setListener(ClickListener<Payment>listener){
            this.listener=listener;
            return this;
        }
        public Builder setBill(Receipt b){
            this.bill=b;
            return this;
        }
        public Builder setPayment(Payment p){
            this.p=p;
            return this;
        }
        public PaymentDialog build(){
            PaymentDialog d=new PaymentDialog(FXML_LOCATION, null);
            if (this.p!=null) {
                d.payment=p;
                d.titleLabel.setText("Edit Payment");
                d.positiveBtn.setText("Update");
                d.amountField.setText(p.getAmount().doubleValue()+"");
                d.datePicker.setValue(p.getVoucherDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                d.remarkField.setText(p.getRemark());
            }
            d.bill=this.bill;
            d.listener=this.listener;
            d.idLabel.setText("Receipt id: "+bill.getId());
            d.dateLabel.setText("Date: "+new SimpleDateFormat(PATTERN).format(bill.getVoucherDate()));
            d.datePicker.setValue(LocalDate.now());
            
            double paid=0;
            for (Payment p : bill.getPayments()) {
                paid+=p.getAmount().doubleValue();
            }
            d.amountField.setText(bill.getAmount()-paid+"");
            d.amountLabel.setText("Amount: "+d.bill.getAmount());
            d.paidLabel.setText("Paid: "+paid);
            d.remainingLabel.setText("Balance: "+ (bill.getAmount()-paid));
            return d;
        }
    }
    
}
