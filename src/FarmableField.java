import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FarmableField {
    private Market market;
    private Inventory inventory;
    public Button marketButton;
    private Button[][] field = new Button[20][20];
    private Player player;
    private int buyingField = 0;
    private int currentCost = 500;
    private boolean[][] isBought = new boolean[15][15];

    @FXML public AnchorPane land;
    @FXML public AnchorPane inventoryControl;
    @FXML public GridPane fieldCrop;

    public void setPlayer(Player player) {
        this.player = player;
        buttonField();

        // Wait for the interface to load
        Platform.runLater(() -> {
            displayInventory();
        } );
    }

    public Button getPlot(int row, int columns) {
        return field[row][columns];
    }

    public void buttonField() {
        double btnWidth = 55.5;
        double btnHeight = 33.5;

        Image lock = new Image(getClass().getResourceAsStream("/img/lock.png"));

        fieldCrop.setHgap(2);
        fieldCrop.setVgap(2);

        for (int row = 0; row < 15; row++ ) {
            for (int columns = 0; columns < 15 ; columns++) {
                Button plot = new Button();
                plot.setMinWidth(btnWidth);
                plot.setMinHeight(btnHeight);
                plot.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #8B4513, #6F370F); " +
                        "-fx-background-radius: 0;");
                land.setStyle("-fx-background-color: green");

                ImageView lockView = new ImageView(lock);
                lockView.setFitHeight(20);
                lockView.setPreserveRatio(true);
                plot.setGraphic(lockView);

                field[row][columns] = plot;
                fieldCrop.add(plot, columns, row);

                final int r = row;
                final int c = columns;

                plot.setOnAction(event -> {
                    buyingField(r,c);
                });
            }
        }
    }
    public void buyingField(int row, int columns) {
        Button plot = field[row][columns];
        if (plot != null && !isBought[row][columns] && player.money > currentCost) {
            isBought[row][columns] = true;
            buyingField +=1;
            player.money -= currentCost;
            currentCost = 500 + (buyingField * ( 500 * 10 / 100));
            plot.setGraphic(null);
            plot.setStyle("-fx-background-color: #A0522D; -fx-background-radius: 0;");
            System.out.println(player.money);
        }
        else System.out.println("Déjà acheté ou pas assez d'argent");
    }

    public void openMarket() {
        marketButton.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/market.fxml"));
                Parent root = loader.load();

                Market marketController = loader.getController();
                marketController.setPlayer(player);
                marketController.displayMarket();

                //For the refresh of the UI
                marketController.setFarm(this);

                Stage marketStage = new Stage();
                marketStage.setTitle("Marché");
                marketStage.setScene(new Scene(root, 524, 380));
                marketStage.show();
            } catch (IOException e) {
                System.err.println("Impossible de charger le fichier FXML");
                e.printStackTrace();
            }
        });
    }

    public void displayInventory() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/inventory.fxml"));
            Node inventoryNode = loader.load();

            this.inventory = loader.getController();
            this.inventory.setPlayer(player);
            this.inventory.updateDisplay();

            inventoryControl.getChildren().add(inventoryNode);

            AnchorPane.setLeftAnchor(inventoryNode, 150.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshInventoryUI() {
        if (inventory != null) {
            inventory.updateDisplay();
        }
    }

    public void initialize() {
    }
}
