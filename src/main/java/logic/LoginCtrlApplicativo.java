package logic;

public class LoginCtrlApplicativo {
    // implemento la logica dello use case
    public boolean verificaCredenziali(String username, String password) {
        // Esempio di verifica delle credenziali rigida
        // Credenziali errate
        return username.equals("admin") && password.equals("1234"); // Credenziali corrette
    }
}