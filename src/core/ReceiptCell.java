/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import com.jfoenix.controls.JFXTextField;
import entities.ReceiptItemModel;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

/**
 *
 * @author hatred
 */
public class ReceiptCell extends ListCell<ReceiptItemModel>{

    JFXTextField particularField;
    JFXTextField unitField;
    JFXTextField rateField;
    JFXTextField quantityField;
    JFXTextField amountField;
    Button btn;
    
    HBox layout;
    ClickListener<ReceiptItemModel> listener;
    
    public ReceiptCell(ClickListener<ReceiptItemModel> listener) {
        super();
        this.listener=listener;
        this.particularField=new JFXTextField();
        this.rateField=new JFXTextField();
        this.quantityField=new JFXTextField();
        this.amountField=new JFXTextField();
        this.unitField=new JFXTextField();
        this.btn=new Button("x");
        
        this.layout=new HBox(10);
        
        this.layout.setAlignment(Pos.CENTER_LEFT);
        
        this.particularField.setPrefWidth(350);
        this.rateField.setPrefWidth(160);
        this.quantityField.setPrefWidth(160);
        this.amountField.setPrefWidth(160);
        
        this.particularField.setEditable(true);
        
        this.layout.getStyleClass().add("card");
    }

    @Override
    public void startEdit() {
        super.startEdit(); //To change body of generated methods, choose Tools | Templates.
        
    }
    
    
    @Override
    protected void updateItem(ReceiptItemModel item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        }else{
            this.particularField.textProperty().bind(item.particularProperty());
            this.rateField.textProperty().bind(Bindings.format("%f", item.rateProperty()));
            this.quantityField.textProperty().bind(Bindings.format("%f", item.rateProperty()));
            this.unitField.textProperty().bind(item.unitProperty());
            this.amountField.textProperty().bind(Bindings.format("%f", item.rateProperty()));
            
            this.btn.setOnAction(event->listener.OnClick(item));
            this.layout.getChildren().addAll(particularField,rateField,quantityField,amountField,btn);
            setGraphic(layout);
        }
    }
    
    
    
}
