import animals.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import vegetables.*;

import java.awt.*;
import java.io.IOException;

import static java.awt.Color.red;

public class Barn {
    private Button[][] field = new Button[10][10];
    private String selectedAnimalName = null;
    private int buyingField = 0;
    private int currentCost = 500;
    private boolean[][] isBought = new boolean[10][10];
    private Player player;
    private Inventory inventory;
    private FarmableField farm;
    private FarmManager manager;
    private String selectedFoodName = "";

    @FXML private SplitPane root;
    @FXML AnchorPane animalBarn;
    @FXML AnchorPane barn;
    @FXML AnchorPane barnInventory;
    @FXML GridPane barnField;
    @FXML Button buyingAnimals;
    @FXML Text cow;
    @FXML Text chicken;
    @FXML Text pig;
    @FXML Text sheep;
    @FXML Text tomato;
    @FXML Text wheat;
    @FXML Text bellpeper;
    @FXML Text potato;
    @FXML Text eggplant;
    @FXML Text corn;
    @FXML Text money;
    @FXML Text milk;
    @FXML Text wool;
    @FXML Text egg;
    @FXML Text meat;

    //Getter and Setter
    public void setPlayer(Player player) {this.player = player;}

    public void setInventory(Inventory inventory) {this.inventory = inventory;}

    public String getSelectedFoodName() { return selectedFoodName;}

    public void setFarm(FarmableField farm) { this.farm = farm; }

    public void setSelectedAnimal(String animalName) {
        this.selectedAnimalName = animalName;
    }
    public String getSelectedAnimalName() {
        return this.selectedAnimalName;
    }
    public Button getPlot(int row, int columns) {
        return field[row][columns];
    }

    public void setPlotImg(int row, int columns, String path) {
        Image img = new Image(getClass().getResourceAsStream(path));
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(20);
        imgView.setPreserveRatio(true);
        getPlot(row, columns).setGraphic(imgView);
    }

    // Barn
    public void barnField() {
        double btnWidth = 59.0;
        double btnHeight = 34.5;

        Image lock = new Image(getClass().getResourceAsStream("/img/lock.png"));

        barnField.setHgap(2);
        barnField.setVgap(2);

        for (int row = 0; row < 10; row++ ) {
            for (int columns = 0; columns < 10 ; columns++) {
                Button plot = new Button();
                plot.setMinWidth(btnWidth);
                plot.setMinHeight(btnHeight);
                plot.setStyle("-fx-background-color: linear-gradient(to bottom right, #2ecc71 0%, #27ae60 50%, #1e8449 100%); " +
                        "-fx-background-radius: 0; " +
                        "-fx-border-color: rgba(0,0,0,0.1); " +
                        "-fx-border-width: 0.5;");

                ImageView lockView = new ImageView(lock);
                lockView.setFitHeight(20);
                lockView.setPreserveRatio(true);
                plot.setGraphic(lockView);

                field[row][columns] = plot;
                barnField.add(plot, columns, row);

                final int r = row;
                final int c = columns;

                plot.setOnAction(event -> {
                    buyingFieldBarn(r,c);
                });
            }
        }
    }

    public void buyingFieldBarn(int row, int columns) {
        Button plot = field[row][columns];
        if (plot != null && !isBought[row][columns]) {
            if  (player.money >= currentCost) {
                isBought[row][columns] = true;
                buyingField += 1;
                player.money -= currentCost;
                currentCost = 500 + (buyingField * (500 * 10 / 100));
                plot.setGraphic(null);
                System.out.println(player.money);
                updateDisplayBarnInventory();
            } else System.out.println("Pas assez d'argent");
        }
        else {
            String animalName = getSelectedAnimalName();
            if (animalName != null && !animalName.isEmpty()) {
                System.out.println("DEBUG: Essai de plantation " + animalName);
                putAnimals(row, columns, animalName);
            } else {
                System.out.println("DEBUG: Aucune graine sélectionnée dans le manager");
            }
        }
   }

    public void updateDisplayBarnInventory() {
        money.setFont(new Font("System", 20));
        money.setText(player.getMoney() + " $");
        tomato.setText("Tomate x" + player.getVegetableCount("Tomate"));
        wheat.setText("Blé x" + player.getVegetableCount("Blé"));
        eggplant.setText("Aubergine x" + player.getVegetableCount("Aubergine"));
        bellpeper.setText("Poivron x" + player.getVegetableCount("Poivron"));
        potato.setText("Patate x" + player.getVegetableCount("Patate"));
        corn.setText("Mais x" + player.getVegetableCount("Mais"));
        cow.setText("Vache x" +player.getAnimalCount("Vache"));
        chicken.setText("Poule x" +player.getAnimalCount("Poule"));
        pig.setText("Cochon x" +player.getAnimalCount("Cochon"));
        sheep.setText("Mouton x" +player.getAnimalCount("Mouton"));
        meat.setText("Viande x" +player.getResourceCount("Viande"));
        milk.setText("Lait x" +player.getResourceCount("Lait"));
        wool.setText("Laine x" +player.getResourceCount("Laine"));
        egg.setText("Oeuf x" +player.getResourceCount("Oeuf"));
    }

    public void openMarketBarn() {
        buyingAnimals.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/marketBarn.fxml"));
                Parent root = loader.load();

                MarketBarn marketBarnController = loader.getController();
                marketBarnController.setPlayer(player);
                marketBarnController.setBarn(this);
                marketBarnController.displayMarketBarn();

                //For the refresh of the UI
                marketBarnController.setFarm(farm);

                Stage marketStage = new Stage();
                marketStage.setTitle("Ferme");
                marketStage.setScene(new Scene(root, 600, 330));
                marketStage.show();
            } catch (IOException e) {
                System.err.println("Impossible de charger le fichier FXML");
                e.printStackTrace();
            }
        });
    }


    //Animal
    public void tryToFeed(int row, int columns, Animal animal) {
        String food = getSelectedFoodName();

        if (animal.feed(food)) {
            System.out.println("L'animal a mangé");
            player.removeVegetable(food);
            farm.refreshInventoryUI();
            updateDisplayBarnInventory();

            getPlot(row, columns).setOnAction(null);

            startGrowth(row, columns, animal);
        } else {
            System.out.println("Cet animal ne veut pas de : " + food);
        }
    }

    public void startGrowth(int row, int columns, Animal animal) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            animal.currentGrowth++;

            if (animal.currentGrowth < animal.growthTime / 3) {
                getPlot(row, columns).setStyle("-fx-background-color: #e74c3c;");
            } else if (animal.currentGrowth < animal.growthTime * 2 / 3) {
                getPlot(row, columns).setStyle("-fx-background-color: #f1c40f;");
            }

            if (animal.currentGrowth >= animal.growthTime) {
                getPlot(row, columns).setStyle("-fx-background-color: #2ecc71; -fx-border-color: white;");

                getPlot(row, columns).setOnAction(event -> {
                    pickUpResources(row, columns, animal);
                });
            }
        }));
        timeline.setCycleCount(animal.growthTime);
        timeline.play();
    }


    public void selectAnimal(MouseEvent event) {
        ImageView selectedAnimalView = (ImageView) event.getSource();

        String selectedAnimal = (String) selectedAnimalView.getUserData();

        if (barn != null) {
            setSelectedAnimal(selectedAnimal);
            System.out.println("Select animal");
        }
    }

    public Animal createAnimalFromName(String animalName) {
        if (animalName.equals("Vache")) {
            return new Cow();
        }
        if (animalName.equals("Poule")) {
            return new Chicken();
        }
        if (animalName.equals("Cochon")) {
            return new Pig();
        }
        if (animalName.equals("Mouton")) {
            return new Sheep();
        }
        else return null;
    }

    public void putAnimals(int row, int columns, String animalName) {
        if (player.hasAnimal(animalName) && isBought[row][columns] && getPlot(row, columns).getGraphic() == null) {
            Animal newAnimal = createAnimalFromName(animalName);
            setPlotImg(row, columns, "/img/" + animalName + ".png");
            player.withdrawAnimal(animalName);

            getPlot(row, columns).setOnAction(event -> {
                tryToFeed(row, columns, newAnimal);
            });

            if (barn != null) {
                updateDisplayBarnInventory();
            }
        } else if (getPlot(row, columns) != null) {
            System.out.println("Parcelle déjà occupée");
        } else {
            System.out.println("Plus de graine !");
        }
    }


    public void pickUpResources(int row, int columns, Animal animal) {
        if (farm.getPlot(row, columns).getGraphic() == null) {
            return;
        }
        player.addResourcesAfterGrowth(animal);
        if (animal.life == 0) {
            getPlot(row, columns).setGraphic(null);
        }

        getPlot(row, columns).setOnMouseClicked(null);

        getPlot(row, columns).setOnAction(event -> {
            buyingFieldBarn(row, columns);
        });
        updateDisplayBarnInventory();
    }

    @FXML
    public void initialize() {
        barnField();
    }
}
