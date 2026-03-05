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
    private Vegetables vegetables;
    private Button acheter;

    @FXML private Pane root;
    @FXML private TableView<Vegetables> market;
    @FXML private TableColumn<Vegetables, String> colSeedName;
    @FXML private TableColumn<Vegetables, Integer> colSeedPrice;
    @FXML private TableColumn<Vegetables, Integer> colPriceAfterGrowth;

    public void displayMarket() {
        colSeedName.setCellValueFactory(new PropertyValueFactory<>("seedName"));
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
        acheter.setLayoutX(400);
        acheter.setLayoutY(280);
        acheter.setOnAction(this::buySeed);
        root.getChildren().add(acheter);
    }

    public void buySeed(ActionEvent event) {
        Vegetables select = market.getSelectionModel().getSelectedItem();

        if (select != null && player.money >= select.buyingCost) {
            player.money -= select.buyingCost;
            player.addSeed(select);
        } else {System.out.println("Echec de l'achat");}
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
