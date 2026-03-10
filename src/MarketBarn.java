import animals.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class MarketBarn {
        private Player player;
        private FarmableField farm;
        private Barn barn;

        @FXML private Pane root;
        @FXML private TableView<Animal> market;
        @FXML private TableColumn<Animal, String> colAnimalName;
        @FXML private TableColumn<Animal, String> colResourceName;
        @FXML private TableColumn<Animal, Integer> colAnimalPrice;
        @FXML private TableColumn<Animal, Integer> colResourcePrice;

        public void setFarm(FarmableField farm) {
            this.farm = farm;
        }
        public void setBarn(Barn barn) {this.barn = barn;}

        public void displayMarketBarn() {
            colAnimalName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colResourceName.setCellValueFactory(new PropertyValueFactory<>("resource"));
            colAnimalPrice.setCellValueFactory(new PropertyValueFactory<>("buyingCost"));
            colResourcePrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));

            Cow cow = new Cow();
            Chicken chicken = new Chicken();
            Pig pig = new Pig();
            Sheep sheep = new Sheep();

            market.getItems().add(cow);
            market.getItems().add(chicken);
            market.getItems().add(pig);
            market.getItems().add(sheep);

            // Make the height of each cell bigger
            market.setFixedCellSize(50);

            Button acheter = new Button("Acheter");
            acheter.setId("acheter");
            acheter.setLayoutX(490);
            acheter.setLayoutY(100);
            acheter.setOnAction(this::buyAnimal);
            root.getChildren().add(acheter);

            Button vendre = new Button("Vendre");
            vendre.setLayoutX(490);
            vendre.setLayoutY(200);
            vendre.setOnAction(this::sellResources);
            root.getChildren().add(vendre);
        }

        public void buyAnimal(ActionEvent event) {
            Animal select = market.getSelectionModel().getSelectedItem();

            if (select != null && player.money >= select.buyingCost) {
                player.money -= select.buyingCost;
                player.addAnimal(select);

                if (barn != null) {
                    barn.updateDisplayBarnInventory();
                }
            } else {System.out.println("Echec de l'achat");}
        }

        public void sellResources(ActionEvent event) {
            Animal select = market.getSelectionModel().getSelectedItem();

            if (select != null) {
                String resourceName = select.resource;
                if (player.getResourceCount(resourceName) > 0) {
                    player.money += select.sellingPrice;
                    player.removeVegetable(resourceName);

                    if (barn != null) {
                        barn.updateDisplayBarnInventory();
                    }

                    if (farm != null) {
                        farm.refreshInventoryUI();
                    }
                } else {System.out.println("Echec de la vente, pas de " + resourceName);}
            }
        }

        public void setPlayer(Player player) {
            this.player = player;
        }
    }

