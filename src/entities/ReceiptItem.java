/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "ReceiptItems")
@NamedQueries({
    @NamedQuery(name = "ReceiptItem.Delete",query = "DELETE FROM ReceiptItem r where r.receipt.id = :id"),
    @NamedQuery(name = "ReceiptItem.findall",query = "SELECT r FROM ReceiptItem r")
})
public class ReceiptItem implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String particular;
    private double rate;
    private double quantity;
    private double amount;
    private String units;
    
    @XmlTransient
    @ManyToOne()
    @JoinColumn(name = "bill_id",nullable = true)
    private Receipt receipt;

    public ReceiptItem() {
        this.particular="";
        this.rate=0;
        this.quantity=1;
        this.amount=0;
        this.units="Pcs";
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    @Override
    public String toString() {
        return particular;
    }
    
    
    
}
