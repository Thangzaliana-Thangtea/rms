/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import javafx.embed.swing.SwingNode;
import javafx.scene.layout.StackPane;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author hatred
 */
public class ReceiptPane extends StackPane{

    private SwingNode swingNode;
    public ReceiptPane() {
        super();
        this.swingNode=new SwingNode();
        
        this.getChildren().add(swingNode);
    }
   
    
}
