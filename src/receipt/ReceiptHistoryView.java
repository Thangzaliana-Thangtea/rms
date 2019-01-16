
package receipt;

import com.jfoenix.controls.JFXTextField;
import entities.Payment;
import entities.Receipt;
import entities.ReceiptItem;
import java.io.IOException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.Notifications;
import payments.PaymentDialog;
import screen.AbstractMainScreen;
import screen.MainScreen;


public class ReceiptHistoryView extends AbstractMainScreen{
    public static final String FXML_LOATION="/layout/receipt-history.fxml";
   
    @FXML
    CheckListView<Receipt> receiptListView;
    
    private ReceiptHistoryPresenter presenter;
    @FXML
    private Button newReceiptBtn;
    @FXML
    private Button moreMenu;
    @FXML
    private Button payBtn;
    @FXML
    private SplitMenuButton splitMenu;
    @FXML
    private JFXTextField searchField;
    @FXML
    private Label nameLabel;
    @FXML
    private Label contactLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label subtotalLabel;
    @FXML
    private Label discountLabel;
    @FXML
    private Label totalLabel;
    @FXML
    private Button delBtn;
    @FXML
    private Button editBtn;
    
    @FXML
    public TableView<Payment> paymentTable;
    @FXML
    private TableView<ReceiptItem> receiptTable;
    @FXML
    private TableColumn<ReceiptItem, Integer> snCol;
    @FXML
    private TableColumn<ReceiptItem, String> particularCol;
    @FXML
    private TableColumn<ReceiptItem, Double> rateCol;
    @FXML
    private TableColumn<ReceiptItem, Double> qtyCol;
    @FXML
    private TableColumn<ReceiptItem, String> unitCol;
    @FXML
    private TableColumn<ReceiptItem, Double> amountCol;
    
    public ReceiptHistoryView(String LOCATION, MainScreen mainScreen) throws IOException {
        super(LOCATION, mainScreen);
    }
    @FXML
    private void initialize(){
        this.presenter=new ReceiptHistoryPresenter(this);
        this.receiptListView.setItems(presenter.itemList);
        this.presenter.loadAll();
        
        particularCol.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getParticular()));
        unitCol.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getParticular()));
        rateCol.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getRate()));
        qtyCol.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getQuantity()));
        
        amountCol.setCellValueFactory(c->new SimpleObjectProperty<>(c.getValue().getAmount()));
        
        receiptListView.setOnKeyReleased(e->{
            if (e.getCode().equals(KeyCode.ENTER)) {
                Receipt receipt = receiptListView.getSelectionModel().getSelectedItem();
                showOverview(receipt);
            }
        });
    }
    @FXML
    private void handleRefresh(ActionEvent event) {
//        presenter.billItems.forEach(cnsmr);
    }

    void showError(String error, String message) {
        Notifications.create().title(error).text(message).showError();
    }

    @FXML
    private void handlePay(ActionEvent event) {
        Receipt receipt = receiptListView.getSelectionModel().getSelectedItem();
        if (receipt==null) {
            showError("No selection", "Please select receipt from a list");
            return;
        }
        new PaymentDialog.Builder().setBill(receipt)
                .setListener(item->{
                    presenter.createPayment(receipt,item);
                })
                .build().show(mainScreen.rootStack);
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String param = searchField.getText();
        presenter.search(param);
    }

    @FXML
    private void handleDel(ActionEvent event) {
        Receipt receipt = receiptListView.getSelectionModel().getSelectedItem();
        if (receipt == null) {
            showError("No selection", "Please select receipt from a list");
            return;
        }
        presenter.delete(receipt);
    }

    @FXML
    private void handleEdit(ActionEvent event) {
        Receipt receipt = receiptListView.getSelectionModel().getSelectedItem();
        if (receipt == null) {
            showError("No selection", "Please select receipt from a list");
            return;
        }
        mainScreen.editReceipt(receipt);
    }

    void showInfo(String head, String msg) {
        Notifications.create().title(head).text(msg).showInformation();
    }

    private void showOverview(Receipt receipt) {
        nameLabel.setText(receipt.getCustomerName());
        addressLabel.setText(receipt.getCustomerAddress());
        contactLabel.setText(receipt.getCustomerContact());
        
        subtotalLabel.setText(receipt.getAmount()-receipt.getDiscount()+"");
        discountLabel.setText(receipt.getDiscount()+"");
        totalLabel.setText(receipt.getAmount()+"");
        
        statusLabel.setText(receipt.getStatus()+"");
        
        receiptTable.getItems().clear();
        if (receipt.getItems()!=null || receipt.getItems().isEmpty()) {
            receiptTable.getItems().addAll(receipt.getItems());
        }
        receiptTable.refresh();
    }

    @FXML
    private void handlePayments(Event event) {
        Receipt item = receiptListView.getSelectionModel().getSelectedItem();
        if (item==null) {
            showError("No Selection", "Please select receipt from a list");
            return;
        }
        paymentTable.getItems().clear();
        paymentTable.getItems().addAll(item.getPayments());
    }
    
}
