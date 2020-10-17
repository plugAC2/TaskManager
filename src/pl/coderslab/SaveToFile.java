package pl.coderslab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SaveToFile {
    public static void saveToFile(String pathName, List<String> dataToSave) {
        Path pathFile = Paths.get(pathName); //ładowanie pliku
        if (!Files.exists(pathFile)) {
            System.out.println("Plik nie istnieje!");
            System.exit(0);
        }
        try {
            Files.write(pathFile, dataToSave);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
