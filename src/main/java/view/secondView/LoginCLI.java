package view.secondView;

import controller.applicativo.*;
import engineering.bean.*;
import engineering.exceptions.*;

import java.util.Scanner;

public class LoginCLI {

    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        boolean continueRunning = true;

        while (continueRunning) {
            printMenu();
            int choice = getUserChoice(); // Attesa bloccante

            switch (choice) {
                case 1:
                    loginChoice();
                    break;
                case 2:
                    registerChoice();
                    break;
                case 3:
                    guestChoice();
                    break;
                case 0:
                    System.out.println("Grazie per aver utilizzato l'applicazione. Arrivederci!");
                    continueRunning = false;  // Imposta la condizione di uscita
                    break;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        }
    }

    /** Print del menù iniziale per procedere con l'accesso */
    private void printMenu() {
        System.out.println("// ------- Seleziona un'opzione: ------- //");
        System.out.println("1: Login");
        System.out.println("2: Registrazione");
        System.out.println("3: Ingresso come Guest");
        System.out.println("0: Esci");
    }

    /** Attesa Bloccante della scelta dell'utente */
    private int getUserChoice() {
        System.out.print("Scelta: ");
        while (!scanner.hasNextInt()) { // Controllo se il valore inserito dall'utente è un intero
            System.err.println("Inserisci un numero valido.");
            scanner.next(); // Consuma il valore errato non utilizzabile
        }
        return scanner.nextInt();
    }

    /** Funzione per accedere alla home page se le credenziali inserite sono corrette */
    private void loginChoice() {
        System.out.print("Inserisci l'indirizzo email: ");
        String email = scanner.next();

        System.out.print("Inserisci la password: ");
        String password = scanner.next();

        try {
            LoginBean loginBean = new LoginBean(email, password);
            LoginCtrlApplicativo loginCtrlApp = new LoginCtrlApplicativo();

            // ----- Utilizzo controller applicativo -----
            loginCtrlApp.verificaCredenziali(loginBean);

            /* ----- Verrà eseguito se non ci sono eccezioni ----- */
            ClientBean clientBean = loginCtrlApp.loadUser(loginBean);
            System.out.println("Login riuscito!");

            /* ----- Passaggio al HomePageCLI e imposta il clientBean ----- */
            HomePageCLI homePageCLI = new HomePageCLI();
            homePageCLI.setClientBean(clientBean);

            /* ----- Avvia il metodo start del HomePageCLInterface ----- */
            homePageCLI.start();

        } catch (IncorrectPassword | UserDoesNotExist | InvalidEmailException e) {
            System.err.println(e.getMessage());
        }
    }

    /** Non vengono effettuati controlli, si passa direttamente alla schermata di registrazione */
    private void registerChoice() {
        // ----- Passo al RegisterCLInterface -----
        RegistrationCLI newCLI = new RegistrationCLI();
        newCLI.start();
    }

    /** Non vengono effettuati controlli, si passa direttamente alla home page */
    private void guestChoice() {
        System.out.println("Accesso come Guest.");
        HomePageCLI homePageCLI = new HomePageCLI();
        homePageCLI.setClientBean(null);
        homePageCLI.start();
    }
}
