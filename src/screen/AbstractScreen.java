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
public class AbstractScreen {

    public Parent rootLayout;

    public AbstractScreen(String LOCATION) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource(LOCATION));
            loader.setController(this);
            rootLayout = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AbstractScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
