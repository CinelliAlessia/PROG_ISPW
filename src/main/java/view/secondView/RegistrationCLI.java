package view.secondView;

import controller.applicativo.*;
import engineering.bean.*;
import engineering.exceptions.*;
import view.secondView.utils.ITAStringCLI;

import java.io.*;
import java.util.*;

// In questa interfaccia noto che il modo in cui è stato costruito il controller applicativo,
// mi rende impossibile invocare metodi diversi da loadUser
// questo rende complicata la gestione di questa interfaccia, che essendo diversa ha bisogno di ulteriori controlli,
// che purtroppo vengono effettuati solo quando si prova a inserire l'utente.
// Esempio: non posso verificare se la password è già in uso immediatamente, perché non posseggo il metodo sull'applicativo: searchEmail
public class RegistrationCLI {
    private final String genreListFile = ITAStringCLI.GENERES_FILE_PATH;
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("// ------- Registrazione ------- //");
        System.out.print("Nome utente: ");
        String username = scanner.next();

        System.out.print("Email: ");
        String email = scanner.next();

        String password = null;
        String confirmPassword;
        boolean retry = true;

        // Controllo se le due password sono identiche
        while (retry) {

            System.out.print("Password: ");
            password = scanner.next();

            System.out.print("Conferma password: ");
            confirmPassword = scanner.next();

            if(!password.equals(confirmPassword)){
                System.err.println("Le password non coincidono. Riprova.");
            } else {
                retry = false;
            }
        }

        // Richiedi generi musicali disponibili all'utente
        System.out.println("Generi musicali disponibili:");
        Map<Integer, String> availableGenres = getAvailableGenres();
        printGenres(availableGenres);

        // Richiedi all'utente di selezionare i generi preferiti
        System.out.print("Inserisci i numeri corrispondenti ai generi musicali preferiti (separati da virgola): ");
        String genreInput = scanner.next();

        List<String> preferences = extractGenres(availableGenres, genreInput);

        try {
            LoginBean regBean = new LoginBean(username, email, password, preferences);
            RegistrazioneCtrlApplicativo registrazioneCtrlApp = new RegistrazioneCtrlApplicativo();

            // ----- Utilizzo controller applicativo -----
            UserBean userBean = new UserBean(email);
            registrazioneCtrlApp.registerUser(regBean, userBean);
            System.out.println("Registrazione utente avvenuta con successo!");

            /* ----- Passaggio al HomePageCLI e imposta il clientBean ----- */
            HomePageCLI<ClientBean> homePageCLI = new HomePageCLI<>();
            homePageCLI.setClientBean(userBean);

            /* ----- Avvia il metodo start del HomePageCLInterface ----- */
            homePageCLI.start();

        } catch (EmailAlreadyInUse | UsernameAlreadyInUse | InvalidEmailException e) {
            System.err.println(e.getMessage());
        }
    }

    /** Legge dal file dei generi musicali e genera una hash map (Intero, Stringa) che poi verrà
     * stampata da printGenres() */
    private Map<Integer, String> getAvailableGenres() {
        // Restituisci una mappa di generi musicali disponibili letti da un file
        Map<Integer, String> availableGenres = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(genreListFile))) {
            String line;
            int index = 1;
            while ((line = br.readLine()) != null) {
                // Aggiungi il genere alla mappa con un numero di indice
                availableGenres.put(index, line.trim());
                index++;
            }
        } catch (IOException e) {
            // Gestisci l'eccezione qui senza lanciarla di nuovo
            System.err.println(STR."Errore durante la lettura del file: \{e.getMessage()}");
        }

        return availableGenres;
    }

    private void printGenres(Map<Integer, String> genres) {
        // Stampa i generi musicali disponibili
        genres.forEach((key, value) -> System.out.println(STR."\{key}: \{value}"));
    }

    /** Parse dei generi inseriti dall'utente e controllo di corretto inserimento */
    private List<String> extractGenres(Map<Integer, String> availableGenres, String genreInput) {
        // Estrai i generi musicali selezionati dall'utente
        List<String> preferences = new ArrayList<>();
        String[] genreIndices = genreInput.split(",");
        for (String index : genreIndices) {
            try {
                int genreIndex = Integer.parseInt(index.trim());
                if (availableGenres.containsKey(genreIndex)) {
                    preferences.add(availableGenres.get(genreIndex));
                } else {
                    System.err.println(STR."Numero genere non valido: \{index}");
                }
            } catch (NumberFormatException e) {
                System.err.println(STR."Input non valido: \{index}");
            }
        }
        return preferences;
    }
}

