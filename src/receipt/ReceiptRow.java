/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package receipt;

import core.ClickListener;
import entities.ReceiptItem;
import java.io.IOException;
import java.text.DecimalFormat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import static receipt.ReceiptRow.FXML;

/**
 *
 * @author hatred
 */
public class ReceiptRow extends HBox {

    public static final String FXML = "/layout/receipt-row.fxml";

    public ReceiptItem item;
    private ClickListener<ReceiptRow> delListener;
    @FXML
    private TextField particularField;
    @FXML
    private TextField qtyField;
    @FXML
    private TextField rateField;
    @FXML
    private TextField unitField;
    @FXML
    private TextField amountField;
    @FXML
    private Button delBtn;

    public ReceiptRow(ReceiptItem item, ClickListener<ReceiptRow> listener) {
        try {
            this.item = item;
            this.delListener = listener;

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource(FXML));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
//            Logger.getLogger(ReceiptRow.class.getName()).log(Level.FINE, ex);
        }
    }

    @FXML
    public void initialize() {
        particularField.setText(item.getParticular());
        rateField.setText(item.getRate() + "");
        qtyField.setText(item.getQuantity() + "");
        unitField.setText(item.getUnits());
        amountField.setText(item.getAmount() + "");

        rateField.focusedProperty().addListener((val, old, newval) -> {
            rateCalculate();
        });
        qtyField.focusedProperty().addListener((val, old, newval) -> {
            qtyCalculate();
        });

    }

    @FXML
    public void handleDel(ActionEvent event) {
        delListener.OnClick(this);
    }

    private void calculateAmount() {
        double rate = rateField.getText().matches("\\d+(\\.\\d*)?|\\.\\d+") == true
                ? Double.parseDouble(rateField.getText())
                : 0;
        double qty = qtyField.getText().matches("\\d+(\\.\\d*)?|\\.\\d+") == true
                ? Double.parseDouble(qtyField.getText())
                : 0;
        String format = DecimalFormat.getInstance().format(qty * rate);
        amountField.setText(format);
    }

    private void onUnitRelease(KeyEvent event) {
        String text = unitField.getText();
        item.setUnits(text);
    }

    private void qtyCalculate() {
        String text = qtyField.getText().trim();
        try {
            double val = Double.parseDouble(text);
            qtyField.setText(val + "");
        } catch (NumberFormatException ex) {
            qtyField.setText("0");
        }
        calculateAmount();
    }
    private void amountCalculate() {
        DecimalFormat fm=new DecimalFormat("#.#");
        String text = amountField.getText().trim();
        double val=0;
        try {
            val = Double.parseDouble(text);
            amountField.setText(val + "");
        } catch (NumberFormatException ex) {
            double rate = Double.parseDouble(rateField.getText());
            double qty = Double.parseDouble(qtyField.getText());
            amountField.setText(fm.format(rate*qty));
            
        }
        calculateAmount();
    }

    private void rateCalculate() {
        String text = rateField.getText().trim();
        try {
            double val = Double.parseDouble(text);
            rateField.setText(val + "");
        } catch (NumberFormatException ex) {
            rateField.setText("0");
        }
        calculateAmount();
    }

}
