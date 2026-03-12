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
import vegetables.Vegetables;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FarmableField {
    private Market market;
    private Player player;
    private FarmManager manager;
    private Inventory inventory;
    public Button marketButton;
    private Vegetables vegetables;
    private Button[][] field = new Button[20][20];
    private int buyingField = 0;
    private int currentCost = 500;
    private boolean[][] isBought = new boolean[15][15];
    private Barn barn;

    @FXML public AnchorPane land;
    @FXML public AnchorPane inventoryControl;
    @FXML public GridPane fieldCrop;

    public void setManager(FarmManager manager) {
        this.manager = manager;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setBarn(Barn barn) { this.barn = barn; }

    public Inventory getInventory() { return this.inventory; }

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
                plot.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, " +
                        "rgba(139, 69, 19, 0.5), rgba(111, 55, 15, 0.5)); " +
                        "-fx-background-radius: 0;");
                land.setStyle("-fx-background-image: url('/img/farmField.jpg');");

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
        if (plot != null && !isBought[row][columns]) {
            if  (player.money >= currentCost) {
                isBought[row][columns] = true;
                buyingField += 1;
                player.money -= currentCost;
                refreshInventoryUI();
                currentCost = 500 + (buyingField * (500 * 10 / 100));
                plot.setGraphic(null);
                plot.setStyle("-fx-background-color: #A0522D; -fx-background-radius: 0; -fx-opacity: 0.5");
                System.out.println(player.money);
            } else System.out.println("Pas assez d'argent");
        }
        else {
            String seed = manager.getSelectedSeedName();
            if (seed != null && !seed.isEmpty()) {
                System.out.println("Essai de plantation " + seed);
                manager.plant(row, columns, seed);
            } else {
                System.out.println("Aucune graine sélectionnée dans le manager");
            }
        }
    }

    public void displayInventory() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/inventory.fxml"));
            Node inventoryNode = loader.load();

            this.inventory = loader.getController();
            this.inventory.setPlayer(player);
            this.inventory.setManager(manager);
            this.inventory.setFarm(this);
            this.inventory.updateDisplay();

            this.inventory.setBarn(barn);


            inventoryControl.getChildren().add(inventoryNode);

            AnchorPane.setLeftAnchor(inventoryNode, 150.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshInventoryUI() {
        if (inventory != null) {
            inventory.updateDisplay();
        } else {
            System.out.println("L'objet 'inventory' est null dans FarmableField ");
        }
    }

    //Save

    public List<PlotSave> convertFieldToData() {
        List<PlotSave> plotSaveList = new ArrayList<>();

        for (int row = 0; row < 15; row++) {
            for (int columns = 0; columns < 15 ; columns++) {
                if(this.isBought[row][columns]) {
                    PlotSave plotSave = new PlotSave();
                    plotSave.row = row;
                    plotSave.column = columns;
                    plotSave.isBought = true;

                    PlotState state = getPlotState(row, columns);

                    if (state != null) {
                        plotSave.itemName = state.itemName;
                        plotSave.currentGrowth = state.currentGrowth;
                    } else {
                        plotSave.itemName = null;
                        plotSave.currentGrowth = 0;
                    }
                    plotSaveList.add(plotSave);
                }
            }
        }
        return plotSaveList;
    }

    public PlotState getPlotState(int row, int col) {
        Button plot = getPlot(row, col);
        if (plot != null && plot.getUserData() instanceof PlotState) {
            return (PlotState) plot.getUserData();
        }
        return null;
    }

    //Restore
    public void restoreField(List<PlotSave> fieldData) {
        for (int r = 0; r < 15; r++) {
            for (int c = 0; c < 15; c++) {
                resetPlotUI(field[r][c]);
                this.isBought[r][c] = false;
            }
        }

        for (PlotSave plot : fieldData) {
            this.isBought[plot.row][plot.column] = true;

            Button currentButton = field[plot.row][plot.column];
            currentButton.setGraphic(null);
            currentButton.setStyle("-fx-background-color: rgba(160, 82, 45, 0.7); -fx-background-radius: 0;");
            if (plot.itemName != null) {
                String seedName = manager.convertVegetableNameToSeedName(plot.itemName);
                Vegetables vegetable = manager.createVegetableFromName(seedName);

                if (vegetable == null) {
                    System.err.println("Légume introuvable pour : " + plot.itemName);
                    continue;
                }

                vegetable.currentGrowth = plot.currentGrowth;
                field[plot.row][plot.column].setUserData(new PlotState(plot.itemName, vegetable.currentGrowth));
                manager.growthVegetables(plot.row, plot.column, vegetable);
            }
        }
    }

    public void resetPlotUI(Button plot) {
        plot.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, " +
                "rgba(139, 69, 19, 0.5), rgba(111, 55, 15, 0.5)); " +
                "-fx-background-radius: 0;");

        Image lock = new Image(getClass().getResourceAsStream("/img/lock.png"));
        ImageView lockView = new ImageView(lock);
        lockView.setFitHeight(20);
        lockView.setPreserveRatio(true);
        plot.setGraphic(lockView);

        plot.setUserData(null);
        plot.setOnAction(event -> {
            buyingField(GridPane.getRowIndex(plot), GridPane.getColumnIndex(plot));
        });
    }

    public void initialize() {
        buttonField();

        // Wait for the interface to load
        Platform.runLater(() -> {
            displayInventory();
        } );
    }
}
