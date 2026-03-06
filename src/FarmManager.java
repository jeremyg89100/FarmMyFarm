import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import vegetables.*;

public class FarmManager {
    private String selectedSeedName = null;
    private FarmableField farm;
    private Vegetables vegetables;
    private Player player;
    private Market market;
    private Inventory inventory;
    private Button[][] field = new Button[20][20];

    public void setFarm(FarmableField farm) {
        this.farm = farm;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Vegetables createVegetableFromName(String seedName) {
        if (seedName.equals("Graine de Haricot")) {
            return new Bean();
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
        } else return null;
    }

    public String convertUserDataToSeedName(String name) {
        return switch (name) {
            case "Tomato" -> "Graine de Tomate";
            case "Bean" -> "Graine de Haricot";
            case "Eggplant" -> "Graine d'Aubergine";
            case "Bellpeper" -> "Graine de Poivron";
            case "Potato" -> "Graine de Patate";
            default -> name;
        };
    }

    public void plant(int row, int columns, String userDataName) {
        String fullName = convertUserDataToSeedName(userDataName);
        if (player.hasSeed(fullName) && farm.getPlot(row, columns).getGraphic() == null) {
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

        farm.getPlot(row, columns).setOnAction(event -> {
            farm.buyingField(row, columns);
        });
        farm.refreshInventoryUI();
    }

    private void setPlotImg(int row, int columns, String path) {
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
}