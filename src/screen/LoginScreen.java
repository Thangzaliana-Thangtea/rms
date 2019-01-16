
package screen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import core.ReceiptExecutors;
import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import receiptmanagement.ReceiptManagement;
import util.TbcPreferences;


public class LoginScreen extends AbstractScreen{
    public static final String FXML_LOCATION="/layout/login.fxml";
    
    @FXML
    private Label errLabel;
    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXButton loginBtn;
    @FXML
    private JFXButton exitBtn;
    
    TbcPreferences pref;
   
    ReceiptManagement rm;
    public LoginScreen(ReceiptManagement rm, String FXML_LOCATION) throws IOException {
        super(FXML_LOCATION);
        this.pref = new TbcPreferences();
        this.rm=rm;
    }
    @FXML
    public void handleLogin(ActionEvent evn){
        String uname = usernameField.getText();
        String pwd = passwordField.getText();
        if (pref.getMasterPassword().equals(pwd)&& pref.getMasterUsername().equals(uname)) {
            errLabel.setText("");
            rm.showMain();
        }else{
            errLabel.setText("Invalid credential");
        }
        evn.consume();
    }
    @FXML
    public void handleExit(ActionEvent evn){
        ReceiptExecutors.getInstance().getExecutor().shutdownNow();
        Platform.exit();
        evn.consume();
    }
    @FXML
    public void handleUser(ActionEvent evn){
        passwordField.requestFocus();
    }
    @FXML
    public void handlePassword(ActionEvent evn){
        handleLogin(evn);
    }
}
