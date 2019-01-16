package receipt;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXProgressBar;
import core.ClickListener;
import core.JasperViewerFX;
import entities.Receipt;
import java.io.File;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import menus.DateFilterDialog;
import menus.ReceiptMainMenu;
import menus.ReceiptMenuEnum;
import org.controlsfx.control.GridView;
import org.controlsfx.control.Notifications;
import payments.PaymentDialog;
import payments.PaymentListDialog;
import screen.AbstractMainScreen;
import screen.ConfirmDialog;
import screen.MainScreen;
import util.ReceiptGenerator;
import util.TbcPreferences;

public class ReceiptGridView extends AbstractMainScreen {

    public static final String FXML = "/layout/receipt-grid.fxml";

    @FXML
    GridView<Receipt> gridView;
    @FXML
    TextField searchField;
    @FXML
    JFXProgressBar bar;
    @FXML
    Button menu;
    

    ReceiptGridPresenter presenter;

    public ReceiptGridView(String LOCATION, MainScreen mainScreen) throws IOException {
        super(LOCATION, mainScreen);
    }

    @FXML
    private void initialize() {
        this.presenter = new ReceiptGridPresenter(this);
        this.gridView.setItems(presenter.items);
        this.gridView.setCellFactory((GridView<Receipt> param)
                -> new ReceiptCell((Receipt receipt, ReceiptMenuEnum menu) -> {
                    switch (menu) {
                        case VIEW:
                            printView(receipt);
                            break;
                        case EDIT:
                            editReceipt(receipt);
                            break;
                        case DELETE:
                            deleteReceipt(receipt);
                            break;
                        case PAYMENT:
                            doPayment(receipt);
                            break;
                        case VIEW_PAYMENT:
                            viewPayment(receipt);
                            break;
                        case STAT:
                            changeStatus(receipt);
                            break;
                        default:
                            break;
                    }
                }));

        presenter.all();
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String text = searchField.getText();
        presenter.search(text);
//        event.consume();
    }
    @FXML
    private void handleMenu(ActionEvent event) {
        new ReceiptMainMenu.Builder()
                .setListener(setting -> {
                    switch (setting) {
                        case LATEST:
                            presenter.sortLatest();
                            break;
                        case OLDEST:
                            presenter.sortOldest();
                            break;
                        case DATE:
                            new DateFilterDialog.Builder()
                                    .setFilterListener((from,to)->{
                                    presenter.filter(from,to);
                                    })
                                    .build().show(mainScreen.rootStack);
                            break;
                        case EXCEL:
                            FileChooser ch1 = new FileChooser();
                            ch1.setTitle("Export as excel");
                            ch1.getExtensionFilters().add(new FileChooser.ExtensionFilter("Microsoft excel", "*.xls"));
                            File f1 = ch1.showSaveDialog(rootLayout.getScene().getWindow());
                            if (f1 != null) {
                                ObservableList<Receipt> items = gridView.getItems();
                                Receipt[] data = new Receipt[items.size()];
                                items.toArray(data);
                                if (items.isEmpty()) {
                                    return;
                                }
                            presenter.exportAsExl(f1,data);
                                
                            }
                            break;
                        case XML_EXPORT:
                            FileChooser ch = new FileChooser();
                            ch.setTitle("Export as xml");
                            ch.getExtensionFilters().add(new FileChooser.ExtensionFilter("Extensible Markup Language(XML)", "*.xml"));
                            File f = ch.showSaveDialog(rootLayout.getScene().getWindow());
                            if (f != null) {
                                ObservableList<Receipt> items = gridView.getItems();
                                Receipt []data=new Receipt[items.size()];
                                items.toArray(data);
                                presenter.exportXml(f,data);
                            }
                            break;
                        case XML_IMPORT:
                            FileChooser chooser = new FileChooser();
                            chooser.setTitle("Import xml");
                            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Extended manipulation file", "*.xml"));
                            File fi = chooser.showOpenDialog(rootLayout.getScene().getWindow());
                            if (fi != null) {
                                presenter.imports(fi);
                            }
                            break;
                        default:
                            break;
                    }
                })
                .build().show(menu, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, 5, 5);
    }

    private void doPayment(Receipt receipt) {
        new PaymentDialog.Builder()
                .setBill(receipt)
                .setListener(item -> {
                    presenter.createPayment(receipt,item);
                })
                .build().show(mainScreen.rootStack);
    }

    private void deleteReceipt(Receipt receipt) {
        new ConfirmDialog.Builder<>()
                .setTitle("Confirm Delete")
                .setMessage("Are you sure ?")
                .setOnPositiveClick("Yes", (ClickListener) (Object item) -> {
                    presenter.delete(receipt);
                }).build().show(mainScreen.rootStack);
    }

    private void editReceipt(Receipt receipt) {
        mainScreen.editReceipt(receipt);
    }

    private void printView(Receipt receipt) {
        Stage owner = (Stage) rootLayout.getScene().getWindow();
        int choice = new TbcPreferences().getTemplate();
        JasperViewerFX viewer;
        if (choice == 1) {
            viewer = ReceiptGenerator.generateSmall(owner, receipt);
        } else {
            viewer = ReceiptGenerator.generateLarge(owner, receipt);
        }
        viewer.show();
    }

    private void viewPayment(Receipt receipt) {
        new PaymentListDialog.Builder()
                .setReceipt(receipt)
                .setListener(item -> {
                    presenter.updateReceipt(item);
                })
                .build().show(mainScreen.rootStack);
    }

    void showInfo(String title, String msg) {
        Notifications.create().title(title)
                .text(msg)
                .showInformation();
    }

    void showerror(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .showInformation();
    }

    private void changeStatus(Receipt receipt) {
        new StatusDialog.Builder()
                .setOnPositiveListener(item->{
                presenter.updateReceiptStatus(item);

                })
                .setReceipt(receipt).build().show(mainScreen.rootStack);
    }
}
