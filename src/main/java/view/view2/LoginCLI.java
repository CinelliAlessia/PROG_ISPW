package view.view2;

import controller.applicativo.LoginCtrlApplicativo;
import engineering.bean.ClientBean;
import engineering.bean.LoginBean;
import engineering.exceptions.EmailIsNotValid;
import engineering.exceptions.IncorrectPassword;
import engineering.exceptions.UserDoesNotExist;

import java.io.IOException;
import java.util.Scanner;

public class LoginCLI {

    private final LoginCtrlApplicativo loginCtrlApp = new LoginCtrlApplicativo();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        boolean continueRunning = true;

        while (continueRunning) {
            printMenu();
            int choice = getUserChoice();

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
                case 4:
                    System.out.println("Grazie per aver utilizzato l'applicazione. Arrivederci!");
                    continueRunning = false;  // Imposta la condizione di uscita
                    break;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        }
    }

    private void printMenu() {
        System.out.println("// ------- Seleziona un'opzione: ------- //");
        System.out.println("1: Login");
        System.out.println("2: Registrazione");
        System.out.println("3: Ingresso come Guest");
        System.out.println("4: Esci");
    }

    private int getUserChoice() {
        System.out.print("Scelta: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Inserisci un numero valido.");
            scanner.next(); // consume non-int input
        }
        return scanner.nextInt();
    }

    private void loginChoice() {
        System.out.println("Inserisci l'indirizzo email:");
        String email = scanner.next();

        System.out.println("Inserisci la password:");
        String password = scanner.next();

        try {
            LoginBean loginBean = new LoginBean(email, password);
            // ----- Utilizzo controller applicativo -----
            if (loginCtrlApp.verificaCredenziali(loginBean)) {
                ClientBean clientBean = loginCtrlApp.loadUser(loginBean);
                System.out.println("Login riuscito!");

                /* ----- Passaggio al HomePageCLI e imposta il clientBean ----- */
                HomePageCLI homePageCLI = new HomePageCLI();
                homePageCLI.setClientBean(clientBean);

                /* ----- Avvia il metodo start del HomePageCLInterface ----- */
                homePageCLI.start();
            } else {
                System.out.println("Credenziali non valide. Riprova.");
                // Torna al menù con selezione scelta
                start();
            }
        } catch (IncorrectPassword | UserDoesNotExist | EmailIsNotValid e) {
            System.out.println(e.getMessage());
        }
    }

    private void registerChoice() {
        // ----- Passo al RegisterCLInterface -----
        RegistrationCLI newCLI = new RegistrationCLI();
        newCLI.start();
    }

    private void guestChoice() {
        System.out.println("Accesso come Guest.");
        HomePageCLI homePageCLI = new HomePageCLI();
        homePageCLI.setClientBean(null);
        homePageCLI.start();
    }
}
