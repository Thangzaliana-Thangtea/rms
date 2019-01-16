/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import com.jfoenix.controls.JFXDialog;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

/**
 *
 * @author hatred
 */
public class BaseDialog<T> extends JFXDialog {

    protected ClickListener<T> listener;
    public BaseDialog(String resource,ClickListener listener) {
        try {
            this.listener=listener;
            
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource(resource));
            loader.setController(this);
            Region parent = loader.load();
            this.setContent(parent);
            this.setTransitionType(DialogTransition.TOP);
//            JFXDialogLayout l=new JFXDialogLayout();
//            Background bg=new Background(new BackgroundFill(Paint.valueOf("#fff"), new CornerRadii(12), new Insets(2)));
//            l.setBackground(bg);
//            l.setBody(parent);
//            
//            l.setActions(new Button("create"));
//            this.setContent();
        } catch (IOException ex) {
            Logger.getLogger(BaseDialog.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
}
