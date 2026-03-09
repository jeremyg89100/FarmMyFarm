import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Inventory {
    @FXML Text tomatoSeed;
    @FXML Text eggplantSeed;
    @FXML Text potatoSeed;
    @FXML Text bellpeperSeed;
    @FXML Text wheatSeed;
    @FXML Text tomato;
    @FXML Text eggplant;
    @FXML Text potato;
    @FXML Text bellpeper;
    @FXML Text wheat;
    @FXML Button marketButton;
    @FXML Button barnButton;
    @FXML Text money;
    @FXML Text cornSeed;
    @FXML Text corn;
    private Player player;
    private FarmManager manager;
    private FarmableField farm;
    private Barn barn;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setManager(FarmManager manager) {
        this.manager = manager;
    }

    public void setFarm(FarmableField farm) {this.farm = farm;}

    public void openMarket() {
        marketButton.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/market.fxml"));
                Parent root = loader.load();

                Market marketController = loader.getController();
                marketController.setPlayer(player);
                marketController.setInventory(this);
                marketController.displayMarket();

                //For the refresh of the UI
                marketController.setFarm(farm);

                Stage marketStage = new Stage();
                marketStage.setTitle("Marché");
                marketStage.setScene(new Scene(root, 600, 330));
                marketStage.show();
            } catch (IOException e) {
                System.err.println("Impossible de charger le fichier FXML");
                e.printStackTrace();
            }
        });
    }

    public void openBarn() {
        barnButton.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/barn.fxml"));
                Parent root = loader.load();

                Barn barnController = loader.getController();
                barnController.setPlayer(player);
                barnController.setFarm(farm);
                barnController.setInventory(this);
                barnController.updateDisplayBarnInventory();

                Stage barnStage = new Stage();
                barnStage.setTitle("Etabli");
                barnStage.setScene(new Scene(root, 755, 510));
                barnStage.show();
            } catch (IOException e) {
                System.err.println("Impossible de charger le fichier FXML");
                e.printStackTrace();
            }
        });
    }

    public void updateDisplay() {
        money.setFont(new Font("System", 20));
        money.setText(player.getMoney() + " $");
        tomatoSeed.setText("Graine de Tomate x" + player.getSeedCount("Graine de Tomate"));
        eggplantSeed.setText("Graine d'Aubergine x" + player.getSeedCount("Graine d'Aubergine"));
        potatoSeed.setText("Graine de Patate x" + player.getSeedCount("Graine de Patate"));
        bellpeperSeed.setText("Graine de Poivron x" + player.getSeedCount("Graine de Poivron"));
        wheatSeed.setText("Graine de Blé x" + player.getSeedCount("Graine de Blé"));
        cornSeed.setText("Graine de Mais x" + player.getSeedCount("Graine de Mais"));
        tomato.setText("Tomate x" + player.getVegetableCount("Tomate"));
        wheat.setText("Blé x" + player.getVegetableCount("Blé"));
        eggplant.setText("Aubergine x" + player.getVegetableCount("Aubergine"));
        bellpeper.setText("Poivron x" + player.getVegetableCount("Poivron"));
        potato.setText("Patate x" + player.getVegetableCount("Patate"));
        corn.setText("Mais x" + player.getVegetableCount("Mais"));
    }

    public void selectSeed(MouseEvent event) {
        ImageView selectedSeed = (ImageView) event.getSource();

        String selectedVegetables = (String) selectedSeed.getUserData();

        if (manager != null) {
            manager.setSelectedSeed(selectedVegetables);
            System.out.println("Select");
        }
    }
}
