package demo.GUI.Alerts;

import javafx.scene.control.Alert;
import org.springframework.stereotype.Service;

/**
 * @author Artur Kuzmik on 18.30.6
 */

@Service
public class AlertsImpl implements Alerts {

    @Override
    public void addEmptyErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("These data are incomplete.");
        alert.showAndWait();
    }

    @Override
    public void addDataErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("The past date is indicated.");
        alert.showAndWait();
    }

    @Override
    public void deleteErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("There is no Customer with such id.");
        alert.showAndWait();
    }

    @Override
    public void sortInfoAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("No sorting option selected.");
        alert.showAndWait();
    }
}
