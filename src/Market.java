import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import vegetables.*;


public class Market {
    private Player player;
    //private Vegetables vegetables;
    private FarmableField farm;
    private Inventory inventory;

    @FXML private Pane root;
    @FXML private TableView<Vegetables> market;
    @FXML private TableColumn<Vegetables, String> colSeedName;
    @FXML private TableColumn<Vegetables, String> colVegetableName;
    @FXML private TableColumn<Vegetables, Integer> colSeedPrice;
    @FXML private TableColumn<Vegetables, Integer> colPriceAfterGrowth;

    public void setFarm(FarmableField farm) {
        this.farm = farm;
    }
    public void setInventory(Inventory inventory) {this.inventory = inventory;}

    public void displayMarket() {
        colSeedName.setCellValueFactory(new PropertyValueFactory<>("seedName"));
        colVegetableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSeedPrice.setCellValueFactory(new PropertyValueFactory<>("buyingCost"));
        colPriceAfterGrowth.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));

        Potato potatoes = new Potato();
        Tomato tomatoes = new Tomato();
        Bean beans = new Bean();
        Eggplant eggplants = new Eggplant();
        BellPeper bellPepers = new BellPeper();

        market.getItems().add(potatoes);
        market.getItems().add(tomatoes);
        market.getItems().add(beans);
        market.getItems().add(bellPepers);
        market.getItems().add(eggplants);

        // Make the height of each cell bigger
        market.setFixedCellSize(60);

        Button acheter = new Button("Acheter");
        acheter.setId("acheter");
        acheter.setLayoutX(490);
        acheter.setLayoutY(100);
        acheter.setOnAction(this::buySeed);
        root.getChildren().add(acheter);

        Button vendre = new Button("Vendre");
        vendre.setLayoutX(490);
        vendre.setLayoutY(200);
        vendre.setOnAction(this::sellVegetables);
        root.getChildren().add(vendre);
    }

    public void buySeed(ActionEvent event) {
        Vegetables select = market.getSelectionModel().getSelectedItem();

        if (select != null && player.money >= select.buyingCost) {
            player.money -= select.buyingCost;
            player.addSeed(select);

            if (farm != null) {
                farm.refreshInventoryUI();
            }
        } else {System.out.println("Echec de l'achat");}
    }

    public void sellVegetables(ActionEvent event) {
        Vegetables select = market.getSelectionModel().getSelectedItem();

        if (select != null) {
            String name = select.getName();
            if (player.getVegetableCount(name) > 0) {
                player.money += select.sellingPrice;
                player.removeVegetable(name);

                if (inventory != null) {
                    inventory.updateDisplay();
                }

                if (farm != null) {
                    farm.refreshInventoryUI();
                }
            } else {System.out.println("Echec de la vente");}
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
