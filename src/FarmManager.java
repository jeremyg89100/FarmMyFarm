import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import vegetables.Vegetables;

public class FarmManager {
    private FarmableField farm;
    private Vegetables vegetables;

    public void growthVegetables(int row, int columns) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (vegetables.currentGrowth == vegetables.growthTime / 3) {
                Image growingPhase1 = new Image(getClass().getResourceAsStream("/img/pousse.png"));
                ImageView seedsView = new ImageView(growingPhase1);
                seedsView.setFitHeight(20);
                seedsView.setPreserveRatio(true);
                farm.getPlot(row, columns).setGraphic(seedsView);
            }
            if (vegetables.currentGrowth == vegetables.growthTime  * 2 / 3) {
                Image growingPhase2 = new Image(getClass().getResourceAsStream("/img/pousse2.png"));
                ImageView seedsView2 = new ImageView(growingPhase2);
                seedsView2.setFitHeight(20);
                seedsView2.setPreserveRatio(true);
                farm.getPlot(row, columns).setGraphic(seedsView2);
            }
        }));
        timeline.setCycleCount(vegetables.growthTime);
    }
}