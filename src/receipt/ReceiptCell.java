/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package receipt;

import entities.Receipt;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import menus.ReceiptMenu;
import org.controlsfx.control.GridCell;

public class ReceiptCell extends GridCell<Receipt>{

    private final ReceiptCardView.ReceiptMenuListener listener;

    public ReceiptCell(ReceiptCardView.ReceiptMenuListener listener) {
        super();
        this.listener=listener;
    }

    @Override
    protected void updateItem(Receipt item, boolean empty) {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
        if (empty) {
            setText("");
            setGraphic(null);
        }else{
            ReceiptCardView view=new ReceiptCardView(item, listener);
            setGraphic(view);
        }
        
    }
    
    
}
