/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report;

import entities.Receipt;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JRViewer;

/**
 *
 * @author Sawmtea
 */
public class PrintReport extends JFrame {

    private static final long serialVersionUID = 2L;
    private static final String CUSTNAME="customerName";
    private static final String DESIGNATION="designation";
    private static final String DRIVER="driver";
    private static final String CONTACT="contact";
    private static final String VEHICLE="vehicle";
    private static final String VEHICLE_TYPE="vehicle_type";

    private static final String STORE_NAME="rentalName";
    private static final String STORE_DESCRIPTION="rentalGstin";
    private static final String STORE_ADDRESS="rentalAddress";
    private static final String STORE_CONTACT="rentalContact";
    
    private static final String BILL_ID="billId";
    private static final String BILL_DATE="date";
    private static final String DUE_DATE="due_date";
    private static final String TAX="tax";
    private static final String DISCOUNT="discount";
    private static final String TOTAL="total";
    private static final String SUBTOTAL="subtotal";
    private static final String AMOUNT_WORD="amountWord";
    private static final String STORE_GSTIN="rentalGstin";

    
    public PrintReport() {
        super();
    }
    
    
    public void printBill(Receipt bill) throws SQLException, JRException {
        InputStream receipt = getClass().getResourceAsStream("/report/small-print.jasper");

        Map parameters = new HashMap();
//
//        double total=bill.getAmount().doubleValue();
//        double tax=bill.getTax().doubleValue();
//        double discount=bill.getDiscount().doubleValue();
        
        parameters.put(BILL_ID, bill.getId());
        parameters.put(BILL_DATE, bill.getVoucherDate());
//        parameters.put(DUE_DATE, bill.getDueDate());
//        parameters.put(TAX,tax);
//        parameters.put(DISCOUNT, discount);
//        parameters.put(TOTAL, total);
//        parameters.put(SUBTOTAL, total+discount-tax);
//        parameters.put(AMOUNT_WORD, bill.getAmountInWord());
        
//        Tax taxRef = bill.getTaxRef();
//        parameters.put("taxName", taxRef==null?"NA":taxRef.getName());
////        customer info
//        Customer customer = bill.getCustomer();
        
        
        
        parameters.put("REPORT_LOCALE", new Locale("en", "in"));
        parameters.put("billId", bill.getId());
        
      
//        String connectionString="jdbc:mysql://"+pref.getServerName()+":"+pref.getPort()+"/"+pref.getDatabaseName();
//        Connection connection = DriverManager.getConnection(connectionString, pr);
//        DatabaseConnection con=new DatabaseConnection();
//        EntityManager em = con.createNewEntityManager();
//        em.getTransaction().begin();
//        Connection connection = em.unwrap(Connection.class);
        JRBeanCollectionDataSource col = new JRBeanCollectionDataSource(new ArrayList<>());
        JasperPrint jasperPrint;
            jasperPrint = JasperFillManager.fillReport(receipt, parameters,
                    col);
            JRViewer viewer = new JRViewer(jasperPrint);
            viewer.setOpaque(true);
            viewer.setVisible(true);
            viewer.setZoomRatio(0.50f);
            this.add(viewer);
            this.setSize(500, 700);
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            em.getTransaction().commit();
//            connection.closes
//            em.close();
    }

}
