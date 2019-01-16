package receipt;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import core.ReceiptStatus;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Payment;
import entities.Receipt;
import entities.ReceiptItem;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.Notifications;
import screen.AbstractMainScreen;
import screen.MainScreen;

/**
 *
 * @author hatred
 */
public class NewReceiptView extends AbstractMainScreen {

    public static final String FXML_LOCATION = "/layout/new-receipt.fxml";

    @FXML
    private Button addBtn;
    @FXML
    private JFXListView<ReceiptRow> receiptListView;
    private NewReceiptPresenter presenter;
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
    private JFXTextField subtotalField;
    @FXML
    private JFXTextField discountField;
    @FXML
    private JFXTextField totalField;
    @FXML
    private JFXTextField paidField;
    @FXML
    private JFXTextField changeField;
    @FXML
    private JFXComboBox<ReceiptStatus> statusCombo;
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private JFXButton saveBtn;
    @FXML
    private JFXButton resetBtn;
    @FXML
    private TextField customerName;
    @FXML
    private TextField customerContact;
    @FXML
    private TextArea addressField;
    @FXML
    private JFXCheckBox printCheck;
    @FXML
    private Label totalLabel;
    private boolean edit;
    private Receipt receipt;

    public NewReceiptView(String LOCATION, MainScreen mainScreen) throws IOException {
        super(LOCATION, mainScreen);
    }

    @FXML
    private void initialize() {
        this.presenter = new NewReceiptPresenter(this);
        this.statusCombo.setItems(presenter.status);
        this.receiptListView.setItems(presenter.receiptItems);

//        rateField.getValidators().add(doubleValidator);
        addBtn.disableProperty().bind(particularField.textProperty().isEmpty()
                .or(rateField.textProperty().isEmpty())
                .or(qtyField.textProperty().isEmpty()).or(amountField.textProperty().isEmpty())
        );

        rateField.focusedProperty().addListener((val, old, newval) -> {
            if (!newval) {
                rateCalculate();
            }
        });
        qtyField.focusedProperty().addListener((val, old, newval) -> {
            if (!newval) {
                qtyCalculate();
            }
        });
        paidField.focusedProperty().addListener((val, old, newval) -> {
            if (!newval) {
                paidCalculate();
            }
        });
        discountField.focusedProperty().addListener((val, old, newval) -> {
            if (!newval) {
                discountCalculate();
            }
        });
        totalField.focusedProperty().addListener((val, old, newval) -> {
            if (!newval) {
                calculateTotal();
            }
        });

//        totalField.textProperty().addListener((val,old,newval)->{
//            if (totalField.getText().matches("\\d+(\\.\\d*)?|\\.\\d+")) {
//                 String ch = DecimalFormat.getCurrencyInstance().format(Double.parseDouble(totalField.getText()));
//            totalLabel.setText(ch);
//            }
//        });
        datePicker.setValue(LocalDate.now());
        saveBtn.disableProperty().bind(totalField.textProperty().isEmpty());
    }

    @FXML
    private void handleAdd(ActionEvent event) {
//        doubleValidator.validate();
        if (!validInput()) {
            return;
        }
        ReceiptItem item = new ReceiptItem();
        item.setParticular(particularField.getText().trim());
        item.setRate(Double.parseDouble(rateField.getText()));
        item.setQuantity(Double.parseDouble(qtyField.getText()));
        item.setUnits(unitField.getText());
        item.setAmount(Double.parseDouble(amountField.getText()));

        ReceiptRow row = new ReceiptRow(item, (ReceiptRow row1) -> {
            presenter.receiptItems.remove(row1);
            calculateTotal();
        });
        presenter.receiptItems.add(row);
        calculateTotal();
        event.consume();
        clearParticularEntries();
        particularField.requestFocus();
    }

    @FXML
    private void handleSubtotal(ActionEvent event) {
        calculateTotal();
    }

    @FXML
    private void handleDiscount(ActionEvent event) {
        calculateTotal();
    }

    @FXML
    private void handleTotal(ActionEvent event) {
        try {
            Double.parseDouble(totalField.getText());
        } catch (NumberFormatException ex) {
            totalField.setText("0");
            totalField.selectAll();

        }
        calculateTotal();
    }

    @FXML
    private void handleSave(ActionEvent event) {
        if (!valid()) {
            return;
        }
        if (edit) {
            receipt.setAmount(Double.parseDouble(totalField.getText()));
            receipt.setCustomerName(customerName.getText());
            receipt.setCustomerAddress(addressField.getText());
            receipt.setCustomerContact(customerContact.getText());
            receipt.setVoucherDate(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            receipt.setDiscount(Double.parseDouble(discountField.getText()));
            receipt.setStatus(statusCombo.getSelectionModel().getSelectedItem());

            List<ReceiptItem> items = new ArrayList<>();
            for (ReceiptRow item : presenter.receiptItems) {
                ReceiptItem ri = item.item;
                ri.setReceipt(receipt);
                items.add(ri);
            }

            List<Payment> paids = new ArrayList<>();
            double paid = paidField.getText().matches("\\d+(\\.\\d*)?|\\.\\d+") == true
                    ? Double.parseDouble(paidField.getText()) : 0;
            if (paid != 0) {
                Payment p = new Payment();
                p.setVoucherDate(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

                if (paid > receipt.getAmount()) {
                    p.setAmount(new BigDecimal(receipt.getAmount()));
                } else {
                    p.setAmount(new BigDecimal(paidField.getText().trim()));
                }
                p.setReceipt(receipt);

                paids.add(p);
            }

            receipt.setItems(items);
            presenter.updateReceipt(receipt, printCheck.isSelected());
        } else {

            Receipt receipt = new Receipt();
            receipt.setAmount(Double.parseDouble(totalField.getText()));
            receipt.setCustomerName(customerName.getText());
            receipt.setCustomerAddress(addressField.getText());
            receipt.setCustomerContact(customerContact.getText());
            receipt.setVoucherDate(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            receipt.setDiscount(Double.parseDouble(discountField.getText()));
            receipt.setStatus(statusCombo.getSelectionModel().getSelectedItem());

            List<ReceiptItem> items = new ArrayList<>();
            for (ReceiptRow item : presenter.receiptItems) {
                ReceiptItem ri = item.item;
                ri.setReceipt(receipt);
                items.add(ri);
            }

            receipt.setItems(items);

            double paid = paidField.getText().matches("\\d+(\\.\\d*)?|\\.\\d+") == true
                    ? Double.parseDouble(paidField.getText()) : 0;

            List<Payment> paids = new ArrayList<>();

            if (paid != 0) {
                Payment p = new Payment();
                p.setVoucherDate(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

                if (paid > receipt.getAmount()) {
                    p.setAmount(new BigDecimal(receipt.getAmount()));
                } else {
                    p.setAmount(new BigDecimal(paidField.getText().trim()));
                }
                p.setReceipt(receipt);

                paids.add(p);
            }
            receipt.setPayments(paids);
            presenter.createReceipt(receipt, printCheck.isSelected());
        }
        handleReset(event);

    }

    @FXML
    public void handleReset(ActionEvent event) {
        presenter.receiptItems.clear();
        subtotalField.setText("0");
        discountField.setText("0");
        totalField.setText("0");
        paidField.setText("0");
        changeField.setText("0");
        statusCombo.getSelectionModel().select(ReceiptStatus.PAID);
        printCheck.setSelected(false);
        receiptListView.refresh();

        event.consume();

    }

    private void qtyCalculate() {
        String text = qtyField.getText().trim();
        try {
            double val = Double.parseDouble(text);
            qtyField.setText(val + "");
            calculateAmount();
        } catch (NumberFormatException ex) {
            qtyField.setText("0");
        }
    }

    private void rateCalculate() {
        String text = rateField.getText().trim();
        try {
            double val = Double.parseDouble(text);
            rateField.setText(val + "");
            calculateAmount();
        } catch (NumberFormatException ex) {
            rateField.setText("0");
        }
    }

    private void calculateAmount() {
        double rate = rateField.getText().matches("\\d+(\\.\\d*)?|\\.\\d+") == true
                ? Double.parseDouble(rateField.getText())
                : 0;
        double qty = qtyField.getText().matches("\\d+(\\.\\d*)?|\\.\\d+") == true
                ? Double.parseDouble(qtyField.getText())
                : 0;
        DecimalFormat fm = new DecimalFormat("#.#");
        String format = fm.format(qty * rate);
        amountField.setText(format);
    }

    private void calculateTotal() {
        double subtotal = 0;
        double change = 0;

        for (ReceiptRow item : presenter.receiptItems) {
            ReceiptItem rp = item.item;
            subtotal += rp.getAmount();
        }
        double discount = discountField.getText().matches("\\d+(\\.\\d*)?|\\.\\d+") == true
                ? Double.parseDouble(discountField.getText()) : 0;
        double total = subtotal - discount;
        double paid = paidField.getText().matches("\\d+(\\.\\d*)?|\\.\\d+") == true
                ? Double.parseDouble(paidField.getText()) : 0;
        DecimalFormat format = new DecimalFormat("#.#");
        change = paid - total;
        if (change < 0) {
            change = 0;
        }

        subtotalField.setText(format.format(subtotal));
        discountField.setText(format.format(discount));
        paidField.setText(format.format(paid));
        changeField.setText(format.format(change));
        totalField.setText(format.format(total));
        
        String charge = NumberFormat.getCurrencyInstance(new Locale("en", "in")).format(total);
        totalLabel.setText(charge);
        if (paid == total || paid > total) {
            statusCombo.getSelectionModel().select(ReceiptStatus.PAID);
        }
        if (paid < total && paid > 0) {
            statusCombo.getSelectionModel().select(ReceiptStatus.PARTIAL_PAID);
        }
        if (paid < 0 || paid == 0) {
            statusCombo.getSelectionModel().select(ReceiptStatus.PENDING);
        }

    }

    @FXML
    private void handleParticularField(ActionEvent event) {
        rateField.requestFocus();
        event.consume();
    }

    @FXML
    private void handleRateField(ActionEvent event) {
        qtyField.requestFocus();
        event.consume();
    }

    @FXML
    private void handleQuantityField(ActionEvent event) {
        unitField.requestFocus();
        event.consume();
    }

    @FXML
    private void handleAmount(ActionEvent event) {
        handleAdd(event);
        event.consume();
    }

    @FXML
    private void handleUnit(ActionEvent event) {
        amountField.requestFocus();
        event.consume();
    }

    @FXML
    private void handlePaid(ActionEvent event) {
        paidCalculate();
        event.consume();
//        handleSave(event);
    }

    @FXML
    private void handleChange(ActionEvent event) {
        amountField.requestFocus();
        event.consume();
    }

    private boolean validInput() {
        if (particularField.getText().isEmpty()) {
            showError("Particular is required");
            return false;
        }
        try {
            Double.parseDouble(rateField.getText());
        } catch (NumberFormatException ex) {
            showError("Rate must be number");
            rateField.requestFocus();
            return false;
        }
        try {
            Double.parseDouble(qtyField.getText());
        } catch (NumberFormatException ex) {
            showError("Quantity must be number");
            qtyField.requestFocus();
            return false;
        }
        try {
            Double.parseDouble(amountField.getText());
        } catch (NumberFormatException ex) {
            amountField.requestFocus();
            showError("Amount must be number");
            return false;
        }

        return true;
    }

    private void showError(String msg) {
        Notifications.create().title("Validation Result")
                .text(msg)
                .graphic(new FontAwesomeIconView(FontAwesomeIcon.CLOSE))
                .showError();
    }

    private void clearParticularEntries() {
        this.particularField.setText("");
        this.rateField.setText("0");
        this.qtyField.setText("0");
//        this.unitField.setText("Pcs");
        this.amountField.setText("0");
    }

    private void paidCalculate() {
        calculateTotal();
    }

    private void discountCalculate() {
        double val = 0;
        try {
            val = Double.parseDouble(discountField.getText());
        } catch (NumberFormatException ex) {
            discountField.setText(val + "");
        }
        calculateTotal();
    }

    void showError(String result, String message) {
        Notifications.create()
                .title(result).text(message).showInformation();
    }

    void showInfo(String result, String message) {
        Notifications.create()
                .title(result).text(message).showInformation();
    }

    public void setReceipt(Receipt receipt) {
        this.edit = true;
        this.receipt = receipt;
        
        this.customerName.setText(receipt.getCustomerName());
        this.customerContact.setText(receipt.getCustomerContact());
        this.addressField.setText(receipt.getCustomerAddress());
        
        for (ReceiptItem item : receipt.getItems()) {
            ReceiptRow row = new ReceiptRow(item, (ReceiptRow item1) -> {
                presenter.receiptItems.remove(item1);
                calculateTotal();
            });
            this.presenter.receiptItems.add(row);

        }
        calculateTotal();
    }

    private boolean valid() {
        if (!discountField.getText().matches("\\d+(\\.\\d*)?|\\.\\d+")) {
            showError("Discount must be number");
            return false;
        }
        if (!totalField.getText().matches("\\d+(\\.\\d*)?|\\.\\d+")) {
            showError("Total amount must be number");
            return false;
        }
        if (!paidField.getText().matches("\\d+(\\.\\d*)?|\\.\\d+")) {
            showError("Paid amount must be number");
            return false;
        }
       
        return true;
    }

}
