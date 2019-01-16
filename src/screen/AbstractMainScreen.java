/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 *
 * @author hatred
 */
public class AbstractMainScreen {

    public Parent rootLayout;

    public MainScreen mainScreen;
    public AbstractMainScreen(String LOCATION,MainScreen mainScreen) throws IOException {
        try {
            this.mainScreen=mainScreen;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource(LOCATION));
            loader.setController(this);
            rootLayout = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AbstractMainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
