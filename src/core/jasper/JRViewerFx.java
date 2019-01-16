/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.jasper;

import java.io.InputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JasperPrint;

public class JRViewerFx {

    private JasperPrint jasperPrint;
    private JRViewerFxMode printMode;

    private Stage stage;

    public void start(Stage primaryStage) throws Exception {
        this.stage=new Stage(StageStyle.UTILITY);
        this.stage.initOwner(primaryStage);
        this.stage.initModality(Modality.WINDOW_MODAL);
        
        InputStream fxmlStream = null;
        try {
            fxmlStream = getClass().getResourceAsStream("/layout/fx-viewer.fxml");
            FXMLLoader loader = new FXMLLoader();
            Parent page = (Parent) loader.load(fxmlStream);
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.setTitle("Jasper Viewer for JavaFx");
            stage.show();
            Object o = loader.getController();
            if (o instanceof JRViewerFxController) {
                JRViewerFxController jrViewerFxController = (JRViewerFxController) o;
                jrViewerFxController.setJasperPrint(jasperPrint);
                jrViewerFxController.show();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public JRViewerFx(JasperPrint jasperPrint, JRViewerFxMode printMode, Stage primaryStage) {
        this.jasperPrint = jasperPrint;
        this.printMode = printMode;
        try {
            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
