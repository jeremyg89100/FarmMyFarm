import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Lobby {
    private View view = new View();
    @FXML Pane lobbyRoot;
    @FXML Button loadButton;
    @FXML Text title;


    public void handleLoadGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/farmableField.fxml"));
            Parent gameRoot = loader.load();
            FarmableField farm = loader.getController();

            Player player = new Player("Jérémy");
            FarmManager manager = new FarmManager();
            Barn barn = new Barn();

            setupGameObjects(player, farm, manager, barn);
            manager.loadGame("save");

            Stage stage = (Stage) loadButton.getScene().getWindow();
            stage.getScene().setRoot(gameRoot);
            stage.setTitle("FarmMyFarm");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupGameObjects(Player player, FarmableField farm, FarmManager manager, Barn barn) {
        manager.setPlayer(player);
        manager.setFarm(farm);
        manager.setBarn(barn);

        farm.setManager(manager);
        farm.setPlayer(player);
        farm.setBarn(barn);

        barn.setPlayer(player);
        barn.setFarm(farm);
    }

    public void lobbyBackground() {
        lobbyRoot.setStyle("-fx-background-image: url('/img/lobby.jpg');" +
                "-fx-background-size: cover;" +
                "-fx-background-position: center;"
                );
        title.setText("Farm my Farm");
        title.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 50px; -fx-fill: white;");

        if (!lobbyRoot.getChildren().contains(title)) {
            lobbyRoot.getChildren().add(title);
        }

    }

    @FXML
    public void initialize() {
        lobbyBackground();
        view.viewButton(loadButton, 53, 109, "Charger");
    }
}
