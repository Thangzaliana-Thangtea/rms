/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report;

import entities.Receipt;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.controlsfx.dialog.ExceptionDialog;

/**
 *
 * @author hatred
 */
public class DirectPrint {

    private static final long serialVersionUID = 2L;
    public static final String CUSTNAME = "cust_name";
    public static final String DESIGNATION = "designation";
    public static final String DRIVER = "driver";
    public static final String CONTACT = "contact";
    public static final String VEHICLE = "vehicle";
    public static final String VEHICLE_TYPE = "vehicle_type";

    public static final String STORE_NAME = "store_name";
    public static final String STORE_DESCRIPTION = "store_description";
    public static final String STORE_ADDRESS = "store_address";
    public static final String STORE_CONTACT = "store_contact";

    public static final String BILL_ID = "bill_id";
    public static final String BILL_DATE = "bill_date";
    public static final String DUE_DATE = "due_date";
    public static final String TAX = "tax";
    public static final String DISCOUNT = "discount";
    public static final String AMOUNT = "amount";
    public static final String AMOUNT_WORD = "amount_word";

//    private TbcPreferences pref;

    public DirectPrint() {
//        this.pref = new TbcPreferences();
    }

    public void printBill(Receipt bill) {

        InputStream receipt = getClass().getResourceAsStream("/reports/small-bill.jasper");

        Map parameters = new HashMap();

       
        parameters.put("REPORT_LOCALE", new Locale("en", "in"));

//        parameters.put(STORE_NAME, pref.getRentalName());
//        parameters.put(STORE_DESCRIPTION, pref.getRentalDesc());
//        parameters.put(STORE_ADDRESS, pref.getRentalAddress());
//        parameters.put(STORE_CONTACT, pref.getContact());

        JasperPrint jasperPrint;
        try {
            jasperPrint = JasperFillManager.fillReport(receipt, parameters,
                    new JRBeanCollectionDataSource(null));
            boolean printReport = JasperPrintManager.printReport(jasperPrint, true);
        } catch (JRException e1) {
            ExceptionDialog d = new ExceptionDialog(e1);
            d.showAndWait();
        }
    }

}
