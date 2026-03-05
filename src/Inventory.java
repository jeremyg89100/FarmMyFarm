import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class Inventory {
    @FXML Text tomatoSeed;
    @FXML Text eggplantSeed;
    @FXML Text potatoSeed;
    @FXML Text bellpeperSeed;
    @FXML Text beanSeed;
    private Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void updateDisplay() {
        tomatoSeed.setText("Graine de Tomate x" + player.getSeedCount("Graine de Tomate"));
        eggplantSeed.setText("Graine d'Aubergine x" + player.getSeedCount("Graine d'Aubergine"));
        potatoSeed.setText("Graine de Patate x" + player.getSeedCount("Graine de Patate"));
        bellpeperSeed.setText("Graine de Poivron x" + player.getSeedCount("Graine de Poivron"));
        beanSeed.setText("Graine de Haricot x" + player.getSeedCount("Graine de haricot"));
    }
}
