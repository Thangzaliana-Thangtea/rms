/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author hatred
 */
public class ReceiptItemModel {

    private final StringProperty particular = new SimpleStringProperty("");
    private DoubleProperty rate=new SimpleDoubleProperty(0);
    private DoubleProperty quantity=new SimpleDoubleProperty(0);
    private DoubleProperty amount=new SimpleDoubleProperty(0);
    private StringProperty unit=new SimpleStringProperty("Pcs");

    public String getParticular() {
        return particular.get();
    }

    public void setParticular(String value) {
        particular.set(value);
    }

    public StringProperty particularProperty() {
        return particular;
    }

    public DoubleProperty rateProperty(){
        return this.rate;
    }
    public DoubleProperty quantityProperty(){
        return this.quantity;
    }
    public DoubleProperty amountProperty(){
        return this.amount;
    }
    public StringProperty unitProperty(){
        return this.unit;
    }
    public double getRate() {
        return rate.get();
    }

    public void setRate(double rate) {
        this.rate.set(rate);
    }

    public double getQuantity() {
        return quantity.get();
    }

    public void setQuantity(double quantity) {
        this.quantity.set(quantity);
    }

    public Double getAmount() {
        return amount.get();
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public String getUnit() {
        return unit.get();
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }
    
}
