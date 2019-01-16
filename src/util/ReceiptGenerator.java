/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import core.JasperViewerFX;
import entities.Receipt;
import java.util.HashMap;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


/**
 *
 * @author hatred
 */
public class ReceiptGenerator {
    public static JasperViewerFX generateSmall(Stage stage,Receipt receipt){
        TbcPreferences pref = new TbcPreferences();

        HashMap map = new HashMap();
        map.put("id", receipt.getId());
        map.put("sname", pref.getStoreName());
        map.put("scontact", pref.getStoreContact());
        map.put("saddress", pref.getStoreAddress());

        map.put("cname", receipt.getCustomerName());
        map.put("ccontact", receipt.getCustomerContact());
        map.put("caddress", receipt.getCustomerAddress());

        double total = receipt.getAmount();
        double discount = receipt.getDiscount();
        double subtotal = total + discount;

        map.put("amount", total);
        map.put("discount", discount);
        map.put("subtotal", subtotal);

        map.put("rdate", receipt.getVoucherDate());
        JRBeanCollectionDataSource col = new JRBeanCollectionDataSource(receipt.getItems());
        JasperViewerFX fview = new JasperViewerFX(stage, "Title", "/reports/small-bill.jasper", map, col);
        
        return fview;

    }
    public static JasperViewerFX generateLarge(Stage stage,Receipt receipt){
        TbcPreferences pref = new TbcPreferences();

        HashMap map = new HashMap();
        map.put("id", receipt.getId());
        map.put("sname", pref.getStoreName());
        map.put("scontact", pref.getStoreContact());
        map.put("saddress", pref.getStoreAddress());

        map.put("cname", receipt.getCustomerName());
        map.put("ccontact", receipt.getCustomerContact());
        map.put("caddress", receipt.getCustomerAddress());

        double total = receipt.getAmount();
        double discount = receipt.getDiscount();
        double subtotal = total + discount;

        map.put("amount", total);
        map.put("discount", discount);
        map.put("subtotal", subtotal);

        map.put("rdate", receipt.getVoucherDate());
        JRBeanCollectionDataSource col = new JRBeanCollectionDataSource(receipt.getItems());
        JasperViewerFX fview = new JasperViewerFX(stage, "Title", "/reports/mta-report.jasper", map, col);
        
        return fview;

    }
}
