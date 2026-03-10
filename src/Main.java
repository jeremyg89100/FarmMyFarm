import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        farmableStage(primaryStage);
    }

    public void farmableStage(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/lobby.fxml"));
            Parent root = loader.load();

            primaryStage.setTitle("FarmMyFarm");
            primaryStage.setScene(new Scene(root, 900, 680));
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Impossible de charger le fichier FXML");
            e.printStackTrace();
        }
    }
}