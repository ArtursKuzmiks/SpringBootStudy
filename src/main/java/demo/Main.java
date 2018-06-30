package demo;

import demo.controller.AppConfig.AbstractAppController;
import demo.controller.AppConfig.ConfigController;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Artur Kuzmik on 18.9.6
 */

@SpringBootApplication
public class Main extends AbstractAppController {

    @Autowired
    @Qualifier("mainView")
    private ConfigController.ViewHolder viewHolder;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(viewHolder.getView()));
        primaryStage.setResizable(true);
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launchApp(Main.class,args);
    }
}
