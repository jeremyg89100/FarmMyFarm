import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Lobby {
    @FXML Pane lobbyRoot;
    @FXML Button loadButton;
    @FXML Button newGameButton;

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
}
