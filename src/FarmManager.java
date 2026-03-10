import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import vegetables.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FarmManager {
    private String selectedSeedName = null;
    private FarmableField farm;
    private Player player;
    private Barn barn;
    private Button[][] field = new Button[20][20];
    private List<PlotSave> currentBarnData = new ArrayList<>();
    private Barn barnInstance = null;

    public void updateBarnData(List<PlotSave> data) {
        this.currentBarnData = data;
    }

    public List<PlotSave> getBarnData() {
        return this.currentBarnData;
    }

    public void setFarm(FarmableField farm) {
        this.farm = farm;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setBarn(Barn barn) { this.barn = barn; }

    public Barn getOrCreateBarn(Player player, FarmableField farm, Inventory inventory) {
        System.out.println("FarmManager instance = " + this.hashCode());
        System.out.println("barnInstance = " + barnInstance);
        if (barnInstance == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/barn.fxml"));
                Parent root = loader.load();
                barnInstance = loader.getController();
                barnInstance.initData(player, farm, inventory);
                barnInstance.setManager(this);

                Stage barnStage = new Stage();
                barnStage.setTitle("Etabli");
                barnStage.setScene(new Scene(root, 755, 510));
                barnStage.setOnCloseRequest(e -> barnStage.hide()); // hide instead of destroy
                barnInstance.setStage(barnStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return barnInstance;
    }

    public Vegetables createVegetableFromName(String seedName) {
        if (seedName.equals("Graine de Blé")) {
            return new Wheat();
        }
        if (seedName.equals("Graine de Poivron")) {
            return new BellPeper();
        }
        if (seedName.equals("Graine d'Aubergine")) {
            return new Eggplant();
        }
        if (seedName.equals("Graine de Patate")) {
            return new Potato();
        }
        if (seedName.equals("Graine de Tomate")) {
            return new Tomato();
        }
        if(seedName.equals("Graine de Mais")) {
            return new Corn();
        }
            else return null;
    }

    public String convertVegetableNameToSeedName(String name) {
        return switch (name) {
            case "Tomato" -> "Graine de Tomate";
            case "Wheat" -> "Graine de Blé";
            case "Eggplant" -> "Graine d'Aubergine";
            case "Bellpeper" -> "Graine de Poivron";
            case "Potato" -> "Graine de Patate";
            case "Corn" -> "Graine de Mais";
            default -> name;
        };
    }

    public void plant(int row, int columns, String vegetableName) {
        String fullName = convertVegetableNameToSeedName(vegetableName);

        if (player.hasSeed(fullName) && farm.getPlot(row, columns).getGraphic() == null) {
            PlotState state = new PlotState(vegetableName, 0);
            farm.getPlot(row, columns).setUserData(state);
            Vegetables newVegetable = createVegetableFromName(fullName);
            setPlotImg(row, columns, "/img/graine.png");
            player.withdrawSeed(fullName);
            growthVegetables(row, columns, newVegetable);

            if (farm != null) {
                farm.refreshInventoryUI();
            }
        } else if (farm.getPlot(row, columns) != null) {
            System.out.println("Parcelle déjà occupée");
        } else {
            System.out.println("Plus de graine !");
        }
    }

    public void growthVegetables(int row, int columns, Vegetables vege) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            vege.currentGrowth++;

            PlotState state = (PlotState) farm.getPlot(row, columns).getUserData();
            if (state != null) {
                state.currentGrowth = vege.currentGrowth;
            }
            if (vege.currentGrowth == vege.growthTime / 3) {
                setPlotImg(row, columns, "/img/pousse.png");
            }
            if (vege.currentGrowth == vege.growthTime  * 2 / 3) {
                setPlotImg(row, columns, "/img/pousse2.png");
            }
            if (vege.currentGrowth == vege.growthTime) {
                setPlotImg(row, columns, vege.imgPath);
                Button plot = farm.getPlot(row, columns);
                plot.setOnMouseClicked(event -> {
                    pickUpVegetables(row, columns, vege);
                });
            }
        }));
        timeline.setCycleCount(vege.growthTime);
        timeline.play();
    }

    public void pickUpVegetables(int row, int columns, Vegetables vege) {
        if (farm.getPlot(row, columns).getGraphic() == null) {
            return;
        }
        player.addVegetablesAfterGrowth(vege);
        farm.getPlot(row, columns).setGraphic(null);
        farm.getPlot(row, columns).setStyle("-fx-background-color: #A0522D; -fx-background-radius: 0;");

        farm.getPlot(row, columns).setOnMouseClicked(null);

        farm.getPlot(row, columns).setOnAction(event -> {
            farm.buyingField(row, columns);
        });
        farm.refreshInventoryUI();
        if (barnInstance != null) {
            barnInstance.updateDisplayBarnInventory();
        }
    }

    public void setPlotImg(int row, int columns, String path) {
        Image img = new Image(getClass().getResourceAsStream(path));
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(20);
        imgView.setPreserveRatio(true);
        farm.getPlot(row, columns).setGraphic(imgView);
    }

    public void setSelectedSeed(String seedName) {
        this.selectedSeedName = seedName;
    }

    public String getSelectedSeedName() {
        return this.selectedSeedName;
    }

    //Restore
    public void loadGame(String fileName) {
        GameSaver loader = new GameSaver();
        SaveData data = loader.loadAll(fileName);

        if (data != null) {
            this.player.setName(data.playerName);
            this.player.setMoney(data.money);
            this.player.restoreInventory(data.inventory);
            this.farm.restoreField(data.fieldPlots);
            this.currentBarnData = data.barnPlots;

            Barn barn = getOrCreateBarn(this.player, this.farm, this.farm.getInventory());
            barn.restoreBarn(data.barnPlots);

            this.farm.refreshInventoryUI();
            System.out.println("Chargement réussi");
        }
    }
}