/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package receiptmanagement;

import core.ReceiptExecutors;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import screen.LoginScreen;
import screen.MainScreen;


public class ReceiptManagement extends Application {

    private LoginScreen loginScreen;
    private MainScreen mainScreen;
    private Scene primaryScene;

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setMinWidth(1350);
        primaryStage.setMinHeight(600);
//        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("Press ctrl+shift+f to exit fullscreen");
        

        this.mainScreen = new MainScreen(MainScreen.FXML_LOCATION);
        this.loginScreen = new LoginScreen(this, LoginScreen.FXML_LOCATION);

        primaryScene = new Scene(this.loginScreen.rootLayout, 900, 750);
        primaryScene.getStylesheets().add(this.getClass().getResource("/css/main.css").toExternalForm());
        primaryStage.setTitle("ZoTek");
        URL url = this.getClass().getResource("/icon/bill.png");
        primaryStage.getIcons().add(new Image(url.toString()));
        primaryStage.setScene(primaryScene);
        primaryStage.show();

        primaryScene.getAccelerators().put(new KeyCodeCombination(KeyCode.F, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN),
                () -> {
                    primaryStage.setFullScreen(!primaryStage.isFullScreen());
                }
        );
        
        primaryStage.setOnCloseRequest(ev -> ReceiptExecutors.getInstance().getExecutor().shutdownNow());
    }

    public void showMain() {
        primaryScene.setRoot(mainScreen.rootLayout);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
