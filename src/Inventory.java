import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    View view = new View();
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
    @FXML Button save;
    @FXML Pane inventoryPane;
    @FXML private Button plot;
    private Player player;
    private FarmManager manager;
    private FarmableField farm;
    private Barn barn;
    private Inventory inventory;
    private ImageView lastSelected;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setManager(FarmManager manager) {
        this.manager = manager;
    }

    public void setFarm(FarmableField farm) {this.farm = farm;}

    public void setBarn(Barn barn) { this.barn = barn; }

    public void openMarket() {
        marketButton.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/market.fxml"));
                Parent root = loader.load();

                Market marketController = loader.getController();
                marketController.setPlayer(player);
                marketController.setInventory(this);
                marketController.setFarm(farm);
                marketController.displayMarket();

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
            System.out.println("manager instance = " + manager.hashCode());
            Barn barn = manager.getOrCreateBarn(player, farm, this);
            barn.show();
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
        }

        if (lastSelected != null) {
            lastSelected.setEffect(null);
        }

        manager.setSelectedSeed(selectedVegetables);
        lastSelected = selectedSeed;

        DropShadow border = new DropShadow();
        border.setColor(Color.WHITE);
        border.setRadius(5);
        border.setSpread(1.0);
        selectedSeed.setEffect(border);
    }

    //Save
    public void handleSave() {
        GameSaver saver = new GameSaver();

        List<PlotSave> data = manager.getBarnData();

        saver.saveAll(this.player, data, this.farm, "save");
    }

    public void initialize() {
        view.viewButton(save, 40, 100, "Sauvegarder" );
        view.viewButton(marketButton, 40, 80, "Marché");
        view.viewButton(barnButton, 40, 80, "Etabli");
        view.viewInventoryBack(inventoryPane, 2000, 500);
    }
}
