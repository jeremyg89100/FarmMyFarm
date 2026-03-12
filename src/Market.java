import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import vegetables.*;


public class Market {
    private Player player;
    private FarmableField farm;
    private Inventory inventory;
    private View view = new View();

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
        Wheat wheats = new Wheat();
        Eggplant eggplants = new Eggplant();
        BellPeper bellPepers = new BellPeper();
        Corn corn = new Corn();

        market.getItems().add(potatoes);
        market.getItems().add(tomatoes);
        market.getItems().add(wheats);
        market.getItems().add(bellPepers);
        market.getItems().add(eggplants);
        market.getItems().add(corn);

        // Make the height of each cell bigger
        market.setFixedCellSize(50);

        Button acheter = new Button("");
        acheter.setId("acheter");
        acheter.setLayoutX(450);
        acheter.setLayoutY(100);
        acheter.setOnAction(this::buySeed);
        view.viewButton(acheter, 40, 80, "Acheter");
        root.getChildren().add(acheter);

        Button vendre = new Button("");
        vendre.setLayoutX(450);
        vendre.setLayoutY(200);
        vendre.setOnAction(this::sellVegetables);
        view.viewButton(vendre, 40, 70, "Vendre");
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

    public void initialize() {
        view.viewMarketTableView(market);
        view.viewMarketTableView(root);
    }
}
