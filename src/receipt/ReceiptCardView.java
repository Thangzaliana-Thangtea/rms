/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package receipt;

import com.jfoenix.controls.JFXPopup;
import core.ClickListener;
import entities.Receipt;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import menus.ReceiptMenu;
import menus.ReceiptMenuEnum;

public class ReceiptCardView extends AnchorPane{

    private final Receipt recipt;

    public interface ReceiptMenuListener{
        public void onClick(Receipt recipt, ReceiptMenuEnum setting);
    }
    
    @FXML
    private Label idLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label customerLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Button menuBtn;

    ReceiptMenuListener listener;
    public ReceiptCardView(Receipt receipt,ReceiptMenuListener listener) {
        super();
        this.recipt=receipt;
        
        this.listener=listener;
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/layout/receipt-card.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ReceiptCardView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void initialize(){
        this.idLabel.setText("ID:"+this.recipt.getId() + "");
        this.customerLabel.setText("Cust:"+this.recipt.getCustomerName());
        this.amountLabel.setText("Amount:"+this.recipt.getAmount() + "");
        this.statusLabel.setText(this.recipt.getStatus() + "");
        this.dateLabel.setText("Date:"+new SimpleDateFormat("dd/MM/yy").format(recipt.getVoucherDate()));
        
        String style="-fx-padding:5;"
                + "-fx-background-radius:20;"
                + "-fx-text-fill:white;";
        
        switch(this.recipt.getStatus()){
            case PAID:
                style+="-fx-background-color:#396;";
                break;
            case PARTIAL_PAID:
                style+="-fx-background-color:#456;";
                break;
            case PENDING:
                style += "-fx-background-color:#F44336;";
                break;
            default:
                style += "-fx-background-color:#f8f8f8;";
                break;
        }
        this.statusLabel.setStyle(style);
        this.statusLabel.setCursor(Cursor.HAND);
        this.statusLabel.setOnMouseClicked(ev->{
            listener.onClick(recipt, ReceiptMenuEnum.STAT);
        });
    
        
    }
    @FXML
    private void handleMenu(ActionEvent event) {
        ReceiptMenu menu=new ReceiptMenu.Builder()
                .setListener(setting->{
                    listener.onClick(recipt, setting);
                })
                .build();
        menu.show(menuBtn, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT);
    }

    
    
    
}
