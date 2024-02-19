package view.second.utils;

import engineering.others.Printer;

import java.io.*;
import java.util.*;

public class GenreManager {

    /** Legge dal file dei generi musicali e genera una hash map (Intero, Stringa) che poi verrà
     * stampata da printGenres() */
    public Map<Integer, String> getAvailableGenres() {
        // Restituisci una mappa di generi musicali disponibili letti da un file
        Map<Integer, String> availableGenres = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(StringCLI.GENERES_FILE_PATH))) {
            int index = 1;
            String line;
            while ((line = br.readLine()) != null) {
                // Aggiungi il genere alla mappa con un numero di indice
                availableGenres.put(index, line.trim());
                index++;
            }
        } catch (IOException e) {
            // Gestisci l'eccezione qui senza lanciarla di nuovo
            Printer.errorPrint(String.format("Errore durante la lettura del file: %s", e.getMessage()));
        }
        return availableGenres;
    }

    public void printGenres(Map<Integer, String> genres) {
        // Stampa i generi musicali disponibili
        genres.forEach((key, value) -> Printer.println(key + ":" + value));
    }

    /** Parse dei generi inseriti dall'utente e controllo di corretto inserimento */
    public List<String> extractGenres(Map<Integer, String> availableGenres, String genreInput) {
        // Estrai i generi musicali selezionati dall'utente

        String[] genreIndices = genreInput.split(",");
        List<String> preferences = new ArrayList<>();

        for (String index : genreIndices) {
            try {
                int genreIndex = Integer.parseInt(index.trim());
                if (availableGenres.containsKey(genreIndex)) {
                    preferences.add(availableGenres.get(genreIndex));
                } else {
                    Printer.errorPrint(String.format(" ! Numero genere non valido: %s !", index));
                }
            } catch (NumberFormatException e) {
                Printer.errorPrint(String.format(" ! Input non valido: %s !", index));

            }
        }
        return preferences;
    }
}
