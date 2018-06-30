package demo.controller.AppConfig;

import demo.GUI.MainGuiController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Artur Kuzmik on 18.22.6
 */

@Configuration
@Profile("dev")
public class ConfigController {

    @Bean(name = "mainView")
    public ViewHolder getMainView() throws IOException {
        return loadView("fxml/MainGui.fxml");
    }

    @Bean
    public MainGuiController getMainGuiController() throws IOException {
        return (MainGuiController) getMainView().getController();
    }

    private ViewHolder loadView(String url) throws IOException {
        try (InputStream fxmlStream = getClass().getClassLoader().getResourceAsStream(url)) {
            FXMLLoader loader = new FXMLLoader();
            loader.load(fxmlStream);
            return new ViewHolder(loader.getRoot(), loader.getController());
        }
    }


    public class ViewHolder {
        private Parent view;
        private Object controller;

        public ViewHolder(Parent view, Object controller) {
            this.view = view;
            this.controller = controller;
        }

        public Parent getView() {
            return view;
        }

        public void setView(Parent view) {
            this.view = view;
        }

        public Object getController() {
            return controller;
        }

        public void setController(Object controller) {
            this.controller = controller;
        }
    }
}
