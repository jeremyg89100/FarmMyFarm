import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;

public class View {
    public void viewButton(Button targetButton, int height, int width, String text) {
        Image img = new Image("/img/button.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(height);
        view.setFitWidth(width);
        view.setSmooth(false);
        targetButton.setGraphic(view);
        targetButton.setText(text);
        targetButton.setContentDisplay(ContentDisplay.CENTER);
        targetButton.setBackground(Background.EMPTY);
        targetButton.setBorder(Border.EMPTY);
        targetButton.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
    }

    public void viewInventoryBack(Pane pane, int height, int width) {
        Image img = new Image("/img/fondBoisH.png");

        double zoomedHeight = height * 0.5;
        double zoomedWidth = width * 0.5;
        double currentX = -180;

        while (currentX < pane.getWidth() || currentX < 2000) {
            ImageView view = new ImageView(img);
            view.setFitHeight(zoomedHeight);
            view.setFitWidth(zoomedWidth);
            view.setPreserveRatio(true);
            view.setSmooth(false);

            view.setLayoutX(currentX);
            view.setLayoutY(0);

            pane.getChildren().add(0, view);
            pane.setStyle("-fx-stroke: white; -fx-stroke-width: 2px; -fx-font-weight: bold;");

            currentX += zoomedWidth;

            if (pane.getWidth() > 0 && currentX > pane.getWidth()) break;
        }
    }

    public void viewBarnInventory(AnchorPane anchorPane) {
        Image img = new Image(getClass().getResourceAsStream("/img/fondBoisH.png"),2048, 1152, true, false);
        ImagePattern pattern = new ImagePattern(img, 0, 0, 256, 144, false);
        anchorPane.setBackground(new Background(new BackgroundFill(pattern, CornerRadii.EMPTY, Insets.EMPTY)));
        anchorPane.setStyle("-fx-stroke: white; -fx-stroke-width: 2px; -fx-font-weight: bold;");
    }

    public void viewBarnAnimalInventory(AnchorPane anchorPane) {
        Image img = new Image(getClass().getResourceAsStream("/img/fondBoisV.png"),1152, 2048, true, false);
        ImagePattern pattern = new ImagePattern(img, 0, 0, 144, 256, false);
        anchorPane.setBackground(new Background(new BackgroundFill(pattern, CornerRadii.EMPTY, Insets.EMPTY)));
        anchorPane.setStyle("-fx-stroke: white; -fx-stroke-width: 2px; -fx-font-weight: bold;");
    }

    public void viewMarketTableView(Region root) {
        Image img = new Image(getClass().getResourceAsStream("/img/fondBoisH.png"),2048, 1152, true, false);
        ImagePattern pattern = new ImagePattern(img, 0, 0, 256, 144, false);
        root.setBackground(new Background(new BackgroundFill(pattern, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setStyle("-fx-stroke: white; -fx-stroke-width: 2px; -fx-font-weight: bold;");
    }
}
