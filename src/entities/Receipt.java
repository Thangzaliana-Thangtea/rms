
package entities;

import core.ReceiptStatus;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "receipt")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity()
@Table(name = "RECEIPTS")
@NamedQueries({
    @NamedQuery(name = "Receipt.FindAll",query = "SELECT r FROM Receipt r ORDER BY r.id DESC"),
    @NamedQuery(name = "Receipt.latest",query = "SELECT r FROM Receipt r ORDER BY r.id  DESC"),
    @NamedQuery(name = "Receipt.oldest",query = "SELECT r FROM Receipt r ORDER BY r.id  ASC"),
    @NamedQuery(name = "Receipt.byid",query = "SELECT r FROM Receipt r WHERE r.id = :id"),
    @NamedQuery(name = "Receipt.dates",query = "SELECT r FROM Receipt r WHERE r.voucherDate BETWEEN :from AND :to"),
    @NamedQuery(name = "Receipt.search",query = "SELECT r FROM Receipt r WHERE r.customerName LIKE :param")
})
public class Receipt implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Temporal(TemporalType.DATE)
    private Date voucherDate;
    
    private String customerName;
    private String customerAddress;
    private String customerContact;
    
    private double discount;
    private double amount;
    
    @XmlElementWrapper(name = "items")
    @OneToMany(mappedBy = "receipt",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<ReceiptItem> items;
    
    @XmlElementWrapper(name = "payments")
    @OneToMany(mappedBy = "receipt",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Payment> payments;

    @Enumerated(EnumType.STRING)
    private ReceiptStatus status;
  
    public Receipt() {
        this.customerName="";
        this.customerAddress="";
        this.customerContact="";
        this.discount=0;
        this.amount=0;
        this.voucherDate=new Date(System.currentTimeMillis());
//        this.check=new SimpleBooleanProperty(false);
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public Date getVoucherDate() {
        return voucherDate;
    }

    public ReceiptStatus getStatus() {
        return status;
    }

    public void setStatus(ReceiptStatus status) {
        this.status = status;
    }

    
    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    
    public void setVoucherDate(Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<ReceiptItem> getItems() {
        return items;
    }

    public void setItems(List<ReceiptItem> items) {
        this.items = items;
    }
    
    
}
