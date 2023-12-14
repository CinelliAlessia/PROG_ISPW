package logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrazioneCtrlApplicativo {
    public boolean verificaPassword(String password, String confermaPassword) {
        if (password.equals(confermaPassword)) {
            // La password e la conferma password corrispondono
            // Esegui azioni appropriate (visualizza un messaggio, ecc.)
            return true;
        } else {
            // La registrazione può procedere
            // Chiamata al modello o al sistema di persistenza per salvare i dati
            return false;
        }
    }

    public boolean verificaEmailCorrect(String email) {
        /*Controllo basico se ha almeno una @ e un punto dopo la @? */
        // Definisci il pattern per una email valida
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Crea un oggetto Pattern
        Pattern pattern = Pattern.compile(emailRegex);

        // Crea un oggetto Matcher con la stringa email da verificare
        Matcher matcher = pattern.matcher(email);

        // Verifica se il formato dell'email è valido
        return matcher.matches();
    }

    //DA IMPLEMENTARE
    public boolean verificaRegistrazioneEsistente(String password, String confermaPassword) {
        if (password.equals(confermaPassword)) {
            // La password e la conferma password non corrispondono
            // Esegui azioni appropriate (visualizza un messaggio, ecc.)
            return true;
        } else {
            // La registrazione può procedere
            // Chiamata al modello o al sistema di persistenza per salvare i dati
            return false;
        }
    }

    public void registerUser(String name, String email, String password){

    }
}
