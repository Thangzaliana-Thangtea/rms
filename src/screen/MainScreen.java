package screen;

import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import core.ReceiptExecutors;
import dashboard.DashboardView;
import entities.Receipt;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import menus.SettingMenu;
import receipt.NewReceiptView;
import receipt.ReceiptGridView;
import setting.CredentialDialog;
import setting.PreferencesDialog;
import setting.StoreSettingDialog;

public class MainScreen extends AbstractScreen {

    public static final String FXML_LOCATION = "/layout/main.fxml";
    @FXML
    public StackPane rootStack;
    @FXML
    private JFXTextField searchField;
    @FXML
    private Button settingBtn;
    @FXML
    private ToggleButton newReceiptBtn;
    @FXML
    private ToggleGroup mainMenu;
    @FXML
    private ToggleButton receiptHistoryBtn;
    @FXML
    private ToggleButton dashboardBtn;
    @FXML
    private BorderPane borderPane;
    @FXML
    private ImageView imageView;

    public MainScreen(String LOCATION) throws IOException {
        super(LOCATION);
    }

    @FXML
    private void initialize(){
        Image image=new Image(getClass().getResource("/icon/bill.png").toExternalForm());
        imageView.setImage(image);
        handleDashboard(new ActionEvent());
    }
  

    @FXML
    private void handleSetting(ActionEvent event) {

        new SettingMenu.Builder()
                .setListener(option -> {
                    switch(option){
                        case STORE:
                            new StoreSettingDialog.Builder()
                                    .build().show(rootStack);
                            break;
                        case CREDENTIAL:
                            new CredentialDialog.Builder().build().show(rootStack);
                            break;
                        case PREF:
                            new PreferencesDialog.Builder().build().show(rootStack);
                            break;
                        default:
                            break;
                    }
                }).build().show(settingBtn, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT);

        
    }

    @FXML
    private void handleNew(ActionEvent event) {
        try {
            this.newReceiptBtn.setSelected(true);
            NewReceiptView view = new NewReceiptView(NewReceiptView.FXML_LOCATION, this);
            this.borderPane.setCenter(view.rootLayout);
        } catch (IOException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void handleDashboard(ActionEvent event) {
        try {
            this.dashboardBtn.setSelected(true);
            DashboardView view = new DashboardView(DashboardView.FXML, this);
            this.borderPane.setCenter(view.rootLayout);
        } catch (IOException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleHistory(ActionEvent event) {
        try {
            this.receiptHistoryBtn.setSelected(true);
            ReceiptGridView view = new ReceiptGridView(ReceiptGridView.FXML, this);
            this.borderPane.setCenter(view.rootLayout);
        } catch (IOException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    @FXML
    private void handleShutdown(ActionEvent event) {
       
        ReceiptExecutors.getInstance().getExecutor().shutdownNow();
        Platform.exit();
        event.consume();

    }

    public void editReceipt(Receipt receipt) {
        try {
            this.newReceiptBtn.setSelected(true);
            NewReceiptView view = new NewReceiptView(NewReceiptView.FXML_LOCATION, this);
           
            view.setReceipt(receipt);
//            view.ti
            this.borderPane.setCenter(view.rootLayout);
        } catch (IOException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
