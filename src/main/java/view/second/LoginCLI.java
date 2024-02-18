package view.second;

import controller.applicativo.*;
import engineering.bean.*;
import engineering.exceptions.*;
import engineering.others.Printer;

import java.util.Scanner;

/**
 * Questa classe gestisce l'interfaccia a riga di comando (CLI) per il login dell'applicazione.
 */
public class LoginCLI {

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Avvia l'interfaccia a riga di comando per il login.
     */
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
                    Printer.println("Grazie per aver utilizzato l'applicazione. Arrivederci!");
                    continueRunning = false;  // Imposta la condizione di uscita
                    break;
                default:
                    Printer.errorPrint("Scelta non valida. Riprova.");
            }
        }
    }

    /**
     * Stampa il menu iniziale per il login.
     */
    private void printMenu() {
        Printer.println("\n// ------- Seleziona un'opzione: ------- //");
        Printer.println("1: Login");
        Printer.println("2: Registrazione");
        Printer.println("3: Ingresso come Guest");
        Printer.println("0: Esci");
    }

    /**
     * Ottiene la scelta dell'utente.
     *
     * @return La scelta dell'utente.
     */
    private int getUserChoice() {
        Printer.print("Scelta: ");
        while (!scanner.hasNextInt()) { // Controllo se il valore inserito dall'utente è un intero
            Printer.errorPrint("! Inserisci un numero valido !");
            scanner.next(); // Consuma il valore errato non utilizzabile
        }
        return scanner.nextInt();
    }

    /**
     * Gestisce la scelta del login.
     */
    private void loginChoice() {
        Printer.print("Inserisci l'indirizzo email: ");
        String email = scanner.next();

        Printer.print("Inserisci la password: ");
        String password = scanner.next();

        try {
            LoginBean loginBean = new LoginBean(email, password);
            LoginCtrlApplicativo loginCtrlApp = new LoginCtrlApplicativo();

            // ----- Utilizzo controller applicativo -----
            loginCtrlApp.verificaCredenziali(loginBean);

            /* ----- Verrà eseguito se non ci sono eccezioni ----- */
            ClientBean clientBean = loginCtrlApp.loadUser(loginBean);

            /* ----- Passaggio al HomePageCLI e imposta il clientBean ----- */
            HomePageCLI<ClientBean> homePageCLI = new HomePageCLI<>();
            homePageCLI.setClientBean(clientBean);
            /* ----- Avvia il metodo start del HomePageCLInterface ----- */
            homePageCLI.start();

        } catch (IncorrectPassword | UserDoesNotExist | InvalidEmailException e) {
            Printer.errorPrint(String.format("! %s", e.getMessage()));
        }
    }

    /**
     * Non vengono effettuati controlli, si passa direttamente alla schermata di registrazione.
     */
    private void registerChoice() {
        // ----- Passo al RegisterCLInterface -----
        RegistrationCLI newCLI = new RegistrationCLI();
        newCLI.start();
    }

    /**
     * Non vengono effettuati controlli, si passa direttamente alla home page come Guest.
     */
    private void guestChoice() {
        Printer.println("Accesso come Guest.");

        // UserBean perché un guest non è sicuramente Supervisor
        HomePageCLI<UserBean> homePageCLI = new HomePageCLI<>();
        homePageCLI.setClientBean(null);
        homePageCLI.start();
    }
}
