/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import com.sun.media.jfxmedia.logging.Logger;
import entities.Receipt;
import entities.ReceiptBackup;
import entities.ReceiptItem;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.TbcPreferences;

public class BackupService {

    public File toXML(File loc, Receipt... receipts) throws PropertyException, JAXBException {
        ReceiptBackup backup = new ReceiptBackup();
        backup.setReceipts(Arrays.asList(receipts));
        JAXBContext context = JAXBContext.newInstance(ReceiptBackup.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(backup, loc);
//            writer.write(toJson);
        return loc;

    }

    public List<Receipt> fromXml(File loc) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(ReceiptBackup.class);
        Unmarshaller unMarshaller = context.createUnmarshaller();
        ReceiptBackup backup = (ReceiptBackup) unMarshaller.unmarshal(loc);

        TbcPreferences pref = new TbcPreferences();

        if (pref.isReplace()) {
            for (Receipt receipt : backup.getReceipts()) {
                EntityManagerFactory fac = Persistence.createEntityManagerFactory(Config.PU);
                EntityManager em = fac.createEntityManager();
                em.getTransaction().begin();

                em.persist(receipt);

                em.getTransaction().commit();
                em.close();
                fac.close();
            }
        }

        return backup.getReceipts();

    }

    public File toExcel(File loc, Receipt[] data) throws FileNotFoundException, IOException {
        List<ReceiptItem> items = new ArrayList<>();
        for (Receipt receipt : data) {
            items.addAll(receipt.getItems());
        }
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("zotek_receipt");
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("DATE");
        headerRow.createCell(2).setCellValue("PARTICULAR");
        headerRow.createCell(3).setCellValue("RATE");
        headerRow.createCell(4).setCellValue("QTY");
        headerRow.createCell(5).setCellValue("UNIT");
        headerRow.createCell(6).setCellValue("AMOUNT");
        for (int i = 0; i < items.size(); i++) {
            ReceiptItem r = items.get(i);
            Row row = sheet.createRow(i);

            row.createCell(0).setCellValue(r.getId());
            String dateStr = new SimpleDateFormat("dd/MM/yy").format(r.getReceipt().getVoucherDate());

            row.createCell(1).setCellValue(dateStr);
            row.createCell(2).setCellValue(r.getParticular());
            row.createCell(3).setCellValue(r.getRate());
            row.createCell(4).setCellValue(r.getQuantity());
            row.createCell(5).setCellValue(r.getUnits());
            row.createCell(6).setCellValue(r.getAmount());

        }
        wb.write(new FileOutputStream(loc));
        return loc;
    }

}
