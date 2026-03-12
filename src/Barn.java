import animals.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

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
    private Stage stage;
    private View view = new View();
    private ImageView lastSelected;

    @FXML private Button plot;
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
    @FXML AnchorPane animalBarn3;
    @FXML SplitPane animalBarn2;

    //Getter and Setter
    public void setPlayer(Player player) {this.player = player;}

    public void setStage(Stage stage) { this.stage = stage; }

    public void setInventory(Inventory inventory) {this.inventory = inventory;}

    public void setManager(FarmManager manager) { this.manager = manager; }

    public void setSelectedFoodName(String foodName) { this.selectedFoodName = foodName;}

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
        double btnWidth = 57;
        double btnHeight = 34;

        Image lock = new Image(getClass().getResourceAsStream("/img/lock.png"));

        barnField.setHgap(2);
        barnField.setVgap(2);
        barnField.setAlignment(Pos.CENTER);

        for (int row = 0; row < 10; row++ ) {
            for (int columns = 0; columns < 10 ; columns++) {
                Button plot = new Button();
                plot.setMinWidth(btnWidth);
                plot.setMinHeight(btnHeight);
                plot.setStyle("-fx-background-color: rgba(173,235,179, 0.5);" +
                        "-fx-background-radius: 0; " +
                        "-fx-border-color: rgba(0,0,0,0.1); " +
                        "-fx-border-width: 0.5;");

                ImageView lockView = new ImageView(lock);
                lockView.setFitHeight(20);
                lockView.setPreserveRatio(true);
                plot.setGraphic(lockView);
                animalBarn2.setStyle("-fx-background-image: url('/img/barnField2.jpg');" +
                        "-fx-background-size: 661px 510px ; " +
                        "-fx-background-repeat: no-repeat; " +
                        "-fx-background-position: left;");

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
                manager.updateBarnData(convertBarnToData());
                updateDisplayBarnInventory();
                farm.refreshInventoryUI();;
            } else System.out.println("Pas assez d'argent");
        }
        else {
            String animalName = getSelectedAnimalName();
            if (animalName != null && !animalName.isEmpty()) {
                putAnimals(row, columns, animalName);
            } else {
                System.out.println("Test Aucune graine sélectionnée dans le manager");
            }
        }
   }

    public void updateDisplayBarnInventory() {
        if (money == null) return;
        money.setFont(new Font("System", 20));
        if (money != null) money.setText(player.getMoney() + " $");
        if (tomato != null) tomato.setText("Tomate x" + player.getVegetableCount("Tomate"));
        if (wheat != null) wheat.setText("Blé x" + player.getVegetableCount("Blé"));
        if (eggplant != null) eggplant.setText("Aubergine x" + player.getVegetableCount("Aubergine"));
        if (bellpeper != null) bellpeper.setText("Poivron x" + player.getVegetableCount("Poivron"));
        if (potato != null) potato.setText("Patate x" + player.getVegetableCount("Patate"));
        if (corn != null) corn.setText("Mais x" + player.getVegetableCount("Mais"));
        if (cow != null) cow.setText("Vache x" +player.getAnimalCount("Vache"));
        if (chicken != null) chicken.setText("Poule x" +player.getAnimalCount("Poule"));
        if (pig != null) pig.setText("Cochon x" +player.getAnimalCount("Cochon"));
        if (sheep != null)sheep.setText("Mouton x" +player.getAnimalCount("Mouton"));
        if (meat != null)meat.setText("Viande x" +player.getResourceCount("Viande"));
        if (milk != null) milk.setText("Lait x" +player.getResourceCount("Lait"));
        if (wool != null) wool.setText("Laine x" +player.getResourceCount("Laine"));
        if (egg != null) egg.setText("Oeuf x" +player.getResourceCount("Oeuf"));

        if (plot != null) {
            plot.setStyle("-fx-background-color: rgba(80,200,120, 0.5)");
        }
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

    public void show() {
        if (stage != null) {
            updateDisplayBarnInventory();
            stage.show();
        }
    }

    //Animal
    public void tryToFeed(int row, int columns, Animal animal) {
        String food = getSelectedFoodName();

        if (animal.feed(food)) {
            String inventoryName = convertFoodNameToInventoryName(food);
            player.removeVegetable(inventoryName);
            farm.refreshInventoryUI();
            updateDisplayBarnInventory();
            getPlot(row, columns).setOnAction(null);
            startGrowth(row, columns, animal);
        } else {
            System.out.println("Cet animal ne veut pas de : " + food);
        }
    }

    private String convertFoodNameToInventoryName(String food) {
        return switch (food) {
            case "Wheat" -> "Blé";
            case "Tomato" -> "Tomate";
            case "Eggplant" -> "Aubergine";
            case "Bellpeper" -> "Poivron";
            case "Potato" -> "Patate";
            case "Corn" -> "Mais";
            default -> food;
        };
    }
    public void startGrowth(int row, int columns, Animal animal) {
        PlotState newState = new PlotState(animal.getName(), 0);
        getPlot(row, columns).setUserData(newState);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            animal.currentGrowth++;

            PlotState currentState = (PlotState) getPlot(row, columns).getUserData();
            if (currentState != null) {
                currentState.currentGrowth = animal.currentGrowth;
            }

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
            if (lastSelected != null) {
                lastSelected.setEffect(null); // ou setStyle("")
            }
            lastSelected = selectedAnimalView;
            setSelectedAnimal(selectedAnimal);
            DropShadow border = new DropShadow();
            border.setColor(Color.WHITE);
            border.setRadius(5);
            border.setSpread(1.0);
            selectedAnimalView.setEffect(border);
        }
    }

    public void selectFood(MouseEvent event) {
        ImageView selectedFoodView = (ImageView) event.getSource();
        String food = (String) selectedFoodView.getUserData();
        this.selectedFoodName = food;

        if (lastSelected != null) {
            lastSelected.setEffect(null);
        }

        lastSelected = selectedFoodView;
        setSelectedAnimal(food);
        DropShadow border = new DropShadow();
        border.setColor(Color.WHITE);
        border.setRadius(5);
        border.setSpread(1.0);
        selectedFoodView.setEffect(border);
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

            PlotState state = new PlotState(animalName, 0);
            getPlot(row, columns).setUserData(state);

            setPlotImg(row, columns, "/img/" + animalName + ".png");
            player.withdrawAnimal(animalName);

            getPlot(row, columns).setOnAction(event -> {
                tryToFeed(row, columns, newAnimal);
            });
            manager.updateBarnData(convertBarnToData());
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
        if (getPlot(row, columns).getGraphic() == null) {
            return;
        }

        player.addResourcesAfterGrowth(animal);

        getPlot(row, columns).setStyle("-fx-background-color: rgba(173,235,179, 0.5);" +
                "-fx-background-radius: 0; " +
                "-fx-border-color: rgba(0,0,0,0.1); " +
                "-fx-border-width: 0.5;");

        animal.life--;

        if (animal.life <= 0) {
            getPlot(row, columns).setGraphic(null);
            getPlot(row, columns).setUserData(null);

            getPlot(row, columns).setOnAction(event -> {
                String animalName = getSelectedAnimalName();
                if (animalName != null && !animalName.isEmpty()) {
                    putAnimals(row, columns, animalName);
                }
            });
        } else {
            PlotState state = (PlotState) getPlot(row, columns).getUserData();
            if (state != null) state.currentGrowth = 0;
            animal.currentGrowth = 0;

            getPlot(row, columns).setOnAction(event -> {
                tryToFeed(row, columns, animal);
            });
        }
        updateDisplayBarnInventory();
        manager.updateBarnData(convertBarnToData());
    }

    // Save
    public List<PlotSave> convertBarnToData() {
        List<PlotSave> plotSaveList = new ArrayList<>();

        for (int row = 0; row < 10; row++) {
            for (int columns = 0; columns < 10 ; columns++) {
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
    public void restoreBarn(List<PlotSave> barnData) {
        if (field[0][0] == null) {
            System.err.println("Erreur, le tableau 'field' n'est pas initialisé !");
            return;
        }
        // Making a new clean barnField with locks
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                resetBarnPlotUI(field[r][c]);
                this.isBought[r][c] = false;
            }
        }

        //If the plot was bought, get rid of the lock
        for (PlotSave plot : barnData) {
            this.isBought[plot.row][plot.column] = true;
            Button currentButton = field[plot.row][plot.column];
            currentButton.setGraphic(null);
            currentButton.setStyle("-fx-background-color: rgba(80,200,120, 0.5)");

            // If there was an animal, put it on the plot
            if (plot.itemName != null) {
                Animal animal = createAnimalFromName(plot.itemName);
                if (animal != null) {
                    animal.currentGrowth = plot.currentGrowth;
                    setPlotImg(plot.row, plot.column, "/img/" + plot.itemName + ".png");
                    currentButton.setUserData(new PlotState(plot.itemName, plot.currentGrowth));
                    final int r = plot.row, c = plot.column;
                    currentButton.setOnAction(event -> tryToFeed(r, c, animal));
                }
            }
        }
    }

    public void resetBarnPlotUI(Button plot) {
        if (plot == null) {
            return;
        }

        plot.setStyle("-fx-background-color: rgba(173,235,179, 0.5);" +
                "-fx-background-radius: 0; " +
                "-fx-border-color: rgba(0,0,0,0.1); " +
                "-fx-border-width: 0.5;");

        try {
            Image lock = new Image(getClass().getResourceAsStream("/img/lock.png"));
            ImageView lockView = new ImageView(lock);
            lockView.setFitHeight(20);
            lockView.setPreserveRatio(true);
            plot.setGraphic(lockView);
        } catch (Exception e) {
            System.err.println("Erreur : Impossible de charger l'image du cadenas.");
            plot.setGraphic(null);
        }

        plot.setUserData(null);
        //Get the emplacement of the plot and put back the onAction to buy the plot
        plot.setOnAction(event -> {
            Integer rowIndex = GridPane.getRowIndex(plot);
            Integer colIndex = GridPane.getColumnIndex(plot);
            if (rowIndex != null && colIndex != null) {
                buyingFieldBarn(rowIndex, colIndex);
            }
        });
    }

    public void initData(Player player, FarmableField farm, Inventory inventory) {
        this.player = player;
        this.farm = farm;
        this.inventory = inventory;
        updateDisplayBarnInventory();
    }

    @FXML
    public void initialize() {
        barnField();
        view.viewButton(buyingAnimals, 40, 80, "Ferme");
        view.viewBarnInventory(barn);
        view.viewBarnAnimalInventory(animalBarn3);
    }
}
