/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package setting;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import core.BaseDialog;
import core.ClickListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.controlsfx.control.Notifications;
import util.TbcPreferences;

/**
 *
 * @author hatred
 */
public class StoreSettingDialog extends BaseDialog<Object> {
    public static final String FXML_LOCATION="/layout/store-info.fxml";
    
    @FXML
    private JFXTextField nameField;
    
    @FXML
    private JFXTextField contactField;
   
    @FXML
    private JFXButton saveBtn;
    @FXML
    private JFXButton closeBtn;
    @FXML
    private JFXTextArea addressField;
    
    TbcPreferences pref;
    public StoreSettingDialog(String resource, ClickListener listener) {
        super(resource, listener);
        this.pref=new TbcPreferences();
        
        this.nameField.setText(pref.getStoreName());
        this.contactField.setText(pref.getStoreContact());
        this.addressField.setText(pref.getStoreAddress());
        
    }

    @FXML
    private void handleSave(ActionEvent event) {
        
        this.pref.setStoreName(nameField.getText().trim());
        this.pref.setContact(contactField.getText().trim());
        this.pref.setAddress(addressField.getText().trim());
        
        Notifications.create().darkStyle().title("Store setting")
                .text("Store info in updated").showInformation();
        close();
    }

    @FXML
    private void handleClose(ActionEvent event) {
        close();
    }
    
    public static class Builder{
        public StoreSettingDialog build(){
            StoreSettingDialog d=new StoreSettingDialog(FXML_LOCATION, null);
            return d;
        }
    }
    
}
