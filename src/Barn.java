import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class Barn {
    private Button[][] field = new Button[10][10];
    private int buyingField = 0;
    private int currentCost = 500;
    private boolean[][] isBought = new boolean[10][10];
    private Player player;
    private Inventory inventory;
    private FarmableField farm;

    @FXML private SplitPane root;
    @FXML AnchorPane animalBarn;
    @FXML AnchorPane barn;
    @FXML AnchorPane barnInventory;
    @FXML GridPane barnField;

    public void setPlayer(Player player) {this.player = player;}
    public void setInventory(Inventory inventory) {this.inventory = inventory;}

    public void setFarm(FarmableField farm) { this.farm = farm; }

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
                farm.refreshInventoryUI();
            } else System.out.println("Pas assez d'argent");
        }
//        else {
//            String seed = manager.getSelectedSeedName();
//            if (seed != null && !seed.isEmpty()) {
//                System.out.println("DEBUG: Essai de plantation " + seed);
//                manager.plant(row, columns, seed);
//            } else {
//                System.out.println("DEBUG: Aucune graine sélectionnée dans le manager");
//            }
//        }
   }
    @FXML
    public void initialize() {
        barnField();
    }
}
