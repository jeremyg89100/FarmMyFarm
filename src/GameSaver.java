import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameSaver {
    private File getSaveFile(String fileName) {
        String path = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "FarmMyFarm";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, fileName + ".json");
    }

    public void saveAll(Player p, List<PlotSave> barnData, FarmableField f, String fileName) {
        SaveData data = new SaveData();
        data.playerName = p.getName();
        data.money = p.getMoney();
        data.barnPlots = barnData;
        data.fieldPlots = f.convertFieldToData();
        data.inventory = p.getInventoryList();

        File file = getSaveFile(fileName);
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, data);

            System.out.println("Le fichier a été créé ici : " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public SaveData loadAll(String fileName) {
        File file = new File(System.getProperty("user.home") + File.separator + "Documents" + File.separator + "FarmMyFarm" + File.separator + fileName + ".json");

        if (!file.exists()) {
            System.err.println("Aucune sauvegarde trouvée à : " + file.getAbsolutePath());
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            SaveData data = mapper.readValue(file, SaveData.class);
            System.out.println("Sauvegarde chargée avec succès !");
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
