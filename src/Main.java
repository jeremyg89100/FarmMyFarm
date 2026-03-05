import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

import java.util.Stack;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        farmableStage(primaryStage);
    }

    public void farmableStage(Stage primaryStage) {
        Player player = new Player("Jérémy");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/farmableField.fxml"));
            Parent root = loader.load();

            FarmableField farmController = loader.getController();
            farmController.setPlayer(player);

            primaryStage.setTitle("FarmMyFarm");
            primaryStage.setScene(new Scene(root, 900, 680));
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Impossible de charger le fichier FXML");
            e.printStackTrace();
        }
    }
}